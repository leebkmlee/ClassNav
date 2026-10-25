package USER;

import DATA.*;
import UI.*;
import FUNCTION.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;

import static DATA.Room.convertToMinutes;
import static FUNCTION.Request.TIME_FORMAT;
import static UI.UI.in;

public class Student extends User {

    private static int nextStudentID = 1;
    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("MM/dd/uuuu")
                    .withResolverStyle(ResolverStyle.STRICT);

    private String enrollmentStatus;
    private String programCode;

    private static final String[] DAYS = {
            "Monday", "Tuesday", "Wednesday", "Thursday",
            "Friday", "Saturday", "Sunday"
    };

    public Student(String lastName, String firstName, String middleInitial, String emailAddress,
                   String password, String address, int postalCode, String birthDate, char gender,
                   String enrollmentStatus, String programCode) {

        super(String.format("S%04d", nextStudentID++), lastName, firstName, middleInitial,
                emailAddress, password, address, postalCode, birthDate, gender);

        Program p = Program.findProgram(programCode);

        if (p == null)
            throw new IllegalArgumentException("Program not found");

        if (enrollmentStatus == null || enrollmentStatus.isBlank())
            throw new IllegalArgumentException("Enrollment status cannot be blank");

        this.enrollmentStatus = enrollmentStatus.trim();
        this.programCode = p.getProgramCode();
    }

    @Override
    public void menu() {
        String select;
        boolean valid;

        do {
            super.menu();
            UI.liner(" [3] View Schedule");
            UI.liner(" [4] View Available Rooms");
            UI.liner(" [5] Locate Room");
            UI.liner(" [6] Inform Faculty");
            UI.liner("");
            UI.liner(" [X] Log out");
            UI.footer();

            System.out.print("  Select > ");
            select = in.nextLine().trim().toUpperCase();

            valid = select.matches("[1-6X]");

            if (!valid)
                UI.print("Invalid Choice");

        } while (!valid);

        switch (select) {
            case "1":
                User.viewProfile(getUserID());
                menu();
                break;

            case "2":
                User.editUser(getUserID());
                menu();
                break;

            case "3":
                Student.displaySchedules(getUserID());
                menu();
                break;

            case "4":
                Student.viewAvailableRooms();
                menu();
                break;

            case "5":
                Student.locateRoom();
                menu();
                break;

            case "6":
                informFaculty();
                menu();
                break;

            case "X":
                System.out.println("  Exiting ...");
                System.exit(0);
                break;
        }
    }

    public void setEnrollmentStatus(String enrollmentStatus) {
        if (enrollmentStatus == null || enrollmentStatus.isBlank())
            throw new IllegalArgumentException("Enrollment status cannot be blank");
        this.enrollmentStatus = enrollmentStatus.trim();
    }

    public String getEnrollmentStatus() {
        return enrollmentStatus;
    }

    public String getProgramCode() {
        return programCode;
    }

    public void setProgramCode(String programCode) {
        Program p = Program.findProgram(programCode);

        if (p == null)
            throw new IllegalArgumentException("Program not found");

        this.programCode = p.getProgramCode();
    }

    public static Student findStudent(String userID) {
        if (userID == null) return null;

        userID = userID.trim();

        for (User u : users) {
            if (userID.equalsIgnoreCase(u.getUserID()) && u instanceof Student)
                return (Student) u;
        }

        return null;
    }

    public static String getEnrollmentStatus(User u) {
        if (u == null) return null;

        Student s = Student.findStudent(u.getUserID());
        if (s == null) return null;

        return s.getEnrollmentStatus();
    }

    public static String getProgramCode(User u) {
        if (u == null) return null;

        Student s = Student.findStudent(u.getUserID());
        if (s == null) return null;

        return s.getProgramCode();
    }

    // LOCATE ROOM FORM

    public static void locateRoom() {
        String roomCode = "";
        String choice;
        boolean submit = false;

        while (!submit) {
            UI.header("LOCATE ROOM");
            UI.liner("  ROOM CODE : " + (roomCode.isEmpty() ? " " : roomCode));
            UI.option();
            UI.footer();

            do {
                System.out.print("  Select > ");
                choice = in.nextLine().trim().toUpperCase();

                if (!choice.matches("[1X]") && !choice.equals("/"))
                    UI.print("Invalid Choice");

            } while (!choice.matches("[1X]") && !choice.equals("/") && !choice.equals("X"));

            switch (choice) {
                case "1":
                    System.out.print("  Enter Room Code > ");
                    roomCode = in.nextLine().trim();

                    if (roomCode.isEmpty())
                        UI.print("Room code cannot be blank");
                    break;

                case "/":
                    if (roomCode.isBlank()) {
                        UI.print("Please enter a room code");
                        break;
                    }

                    locateRoom(roomCode);
                    submit = true;
                    break;

                case "X":
                    submit = true;
                    break;
            }
        }
    }

    public static void locateRoom(String roomCode) {
        Room r = Room.findRoom(roomCode);

        if (r == null) {
            UI.print("Room not found");
            return;
        }

        Building b = Building.findBuilding(r.getBuildingCode());

        if (b == null) {
            UI.print("Building not found");
            return;
        }

        UI.header("ROOM INFORMATION");
        UI.liner("  ROOM DETAILS");
        UI.separator();

        UI.liner(String.format("  ROOM CODE         : %s", r.getRoomCode()));
        UI.liner(String.format("  ROOM DESCRIPTION  : %s", r.getRoomDescription()));

        UI.separator();
        UI.liner("  ROOM LOCATION");
        UI.separator();

        UI.liner(String.format("  BUILDING NAME     : %s", b.getBuildingName()));
        UI.liner(String.format("  FLOOR NUMBER      : %s", Room.getFloorText(r.getFloorNumber())));

        UI.footer();
    }

    private static boolean isValidDay(String day) {
        if (day == null) return false;

        for (String validDay : DAYS) {
            if (validDay.equalsIgnoreCase(day.trim()))
                return true;
        }

        return false;
    }

    private static String normalizeDay(String day) {
        if (day == null) return null;

        for (String validDay : DAYS) {
            if (validDay.equalsIgnoreCase(day.trim()))
                return validDay;
        }

        return null;
    }

    private static boolean isValidTimeRange(String startTime, String endTime) {
        try {
            if (startTime == null || endTime == null
                    || startTime.isBlank() || endTime.isBlank())
                return false;

            int start = convertToMinutes(startTime);
            int end = convertToMinutes(endTime);

            return start >= 0 && end <= 1440 && start < end;
        } catch (RuntimeException e) {
            return false;
        }
    }

    private static boolean isScheduleActive(Schedule schedule) {
        if (schedule == null || schedule.getEffectiveFrom() == null
                || schedule.getEffectiveTo() == null)
            return false;

        try {
            LocalDate effectiveFrom = LocalDate.parse(
                    schedule.getEffectiveFrom(), DATE_FORMAT);

            LocalDate effectiveTo = LocalDate.parse(
                    schedule.getEffectiveTo(), DATE_FORMAT);

            LocalDate today = LocalDate.now();

            return !today.isBefore(effectiveFrom)
                    && !today.isAfter(effectiveTo);
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private static int getDayOrder(String day) {
        if (day == null) return 8;

        for (int i = 0; i < DAYS.length; i++) {
            if (DAYS[i].equalsIgnoreCase(day.trim()))
                return i + 1;
        }

        return 8;
    }

    public static void displaySchedules(String studentID) {
        Student st = Student.findStudent(studentID);

        if (st == null) {
            UI.print("Student not found");
            return;
        }

        UI.header("STUDENT WEEKLY TIMETABLE");
        UI.liner(String.format("  STUDENT ID    : %s", st.getUserID()));
        UI.liner(String.format("  DATE          : %s", LocalDate.now()));
        UI.separator();

        ArrayList<Schedule> schedules = new ArrayList<>();
        ArrayList<String> displayedSchedules = new ArrayList<>();

        for (CourseRegistration cr :
                CourseRegistration.getRegistrationsByStudent(st.getUserID())) {

            if (cr == null)
                continue;

            CourseOffering o = CourseOffering.findOffering(cr.getOfferingID());

            if (o == null)
                continue;

            for (Schedule s : Schedule.getSchedulesByOffering(o.getOfferingID())) {
                if (s == null || !isScheduleActive(s)
                        || displayedSchedules.contains(s.getScheduleID()))
                    continue;

                displayedSchedules.add(s.getScheduleID());
                schedules.add(s);
            }
        }

        schedules.sort((s1, s2) -> {
            int dayComparison = Integer.compare(
                    getDayOrder(s1.getDay()), getDayOrder(s2.getDay()));

            if (dayComparison != 0)
                return dayComparison;

            return Integer.compare(
                    convertToMinutes(s1.getStartTime()),
                    convertToMinutes(s2.getStartTime()));
        });

        if (schedules.isEmpty()) {
            UI.liner("  No active schedules found for this week.");
        } else {
            String currentDay = "";

            for (Schedule s : schedules) {
                String day = normalizeDay(s.getDay());

                if (day == null)
                    day = s.getDay();

                if (!day.equalsIgnoreCase(currentDay)) {
                    if (!currentDay.isEmpty())
                        UI.separator();

                    UI.liner(String.format("  %s", day.toUpperCase()));
                    UI.separator();
                    currentDay = day;
                }

                CourseOffering o = CourseOffering.findOffering(s.getOfferingID());
                Course c = o == null ? null : Course.findCourse(o.getCourseCode());
                Section section = o == null ? null : Section.findSection(o.getSectionID());
                Room r = Room.findRoom(s.getRoomCode());

                Building b = r == null ? null
                        : Building.findBuilding(r.getBuildingCode());

                UI.liner(String.format("  TIME          : %s - %s",
                        s.getStartTime(), s.getEndTime()));
                UI.liner(String.format("  COURSE        : %s",
                        c == null ? "Course not found" : c.getCourseCode()));
                UI.liner(String.format("  SECTION       : %s",
                        section == null ? "Section not found" : Section.getYearSection(section)));
                UI.liner(String.format("  ROOM CODE     : %s", s.getRoomCode()));
                UI.liner(String.format("  BUILDING NAME : %s",
                        b == null ? "Building not found" : b.getBuildingName()));
                UI.liner("");
            }
        }

        UI.footer();
    }

    // VIEW AVAILABLE ROOMS FORM

    public static void viewAvailableRooms() {

        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        String currentDate = today.format(DATE_FORMAT);
        String currentTime = now.format(TIME_FORMAT);
        String checkEndTime = now.plusMinutes(1).format(TIME_FORMAT);

        boolean available = false;

        UI.header("ROOMS AVAILABLE RIGHT NOW");

        for (Room room : Room.rooms) {

            if (room == null)
                continue;

            if (Room.verifyAvailability(
                    room.getRoomCode(),
                    currentDate,
                    currentTime,
                    checkEndTime)) {

                UI.liner("  " + room.getRoomCode()
                        + " - " + room.getRoomDescription());
                UI.liner("  Type: " + room.getRoomType()
                        + " | Capacity: " + room.getCapacity());
                UI.liner("  Floor: "
                        + Room.getFloorText(room.getFloorNumber()));
                UI.liner("");

                available = true;
            }
        }

        if (!available)
            UI.liner("  No rooms are currently available.");

        UI.footer();
    }

    // INFORM FACULTY FORM

    public void informFaculty() {
        String roomCode = "", day = "", startTime = "", endTime = "", choice;
        boolean submit = false;

        while (!submit) {
            UI.header("INFORM FACULTY");
            UI.liner(" [1] ROOM CODE  : " + (roomCode.isEmpty() ? " " : roomCode));
            UI.liner(" [2] DAY        : " + (day.isEmpty() ? " " : day));
            UI.liner(" [3] START TIME : " + (startTime.isEmpty() ? " " : startTime));
            UI.liner(" [4] END TIME   : " + (endTime.isEmpty() ? " " : endTime));
            UI.option();
            UI.footer();

            do {
                System.out.print("  Select > ");
                choice = in.nextLine().trim().toUpperCase();

                if (!choice.matches("[1-3X]") && !choice.equals("/"))
                    UI.print("Invalid Choice");

            } while (!choice.matches("[1-3X]") && !choice.equals("/") && !choice.equals("X"));

            switch (choice) {
                case "1":
                    System.out.print("  Enter Room Code > ");
                    roomCode = in.nextLine().trim();

                    if (Room.findRoom(roomCode) == null) {
                        UI.print("Room not found");
                        roomCode = "";
                    }
                    break;

                case "2":
                    do {
                        System.out.print("  Enter Day > ");
                        day = in.nextLine().trim();

                        if (!isValidDay(day))
                            UI.print("Invalid day");

                    } while (!isValidDay(day));

                    day = normalizeDay(day);
                    break;

                case "3":
                    do {
                        System.out.print("  Enter Start Time > ");
                        startTime = in.nextLine().trim();

                        System.out.print("  Enter End Time > ");
                        endTime = in.nextLine().trim();

                        if (!isValidTimeRange(startTime, endTime))
                            UI.print("Invalid time range");

                    } while (!isValidTimeRange(startTime, endTime));
                    break;

                case "/":
                    if (roomCode.isEmpty() || day.isEmpty()
                            || startTime.isEmpty() || endTime.isEmpty()) {
                        UI.print("Please complete all required fields");
                        break;
                    }

                    Room room = Room.findRoom(roomCode);
                    Building building = room == null ? null
                            : Building.findBuilding(room.getBuildingCode());

                    if (room == null || building == null) {
                        UI.print("Room or building not found");
                        break;
                    }

                    informFaculty(
                            getUserID(),
                            building.getBuildingName(),
                            roomCode,
                            day,
                            startTime,
                            endTime);

                    submit = true;
                    break;

                case "X":
                    submit = true;
                    break;
            }
        }
    }

    public static void informFaculty(String studentID, String buildingName,
                                     String roomCode, String day,
                                     String startTime, String endTime) {

        Student student = Student.findStudent(studentID);

        if (student == null) {
            UI.print("Student not found");
            return;
        }

        if (Room.findRoom(roomCode) == null) {
            UI.print("Room not found");
            return;
        }

        if (!isValidDay(day) || !isValidTimeRange(startTime, endTime)) {
            UI.print("Invalid day or time range");
            return;
        }

        day = normalizeDay(day);

        if (CourseRegistration.getRegistrationsByStudent(student.getUserID()).isEmpty()) {
            UI.print("No active course registrations found");
            return;
        }

        String courseCode;
        String facultyID;
        CourseOffering selectedOffering = null;
        boolean found;

        do {
            System.out.print("  Enter Course Code > ");
            courseCode = in.nextLine().trim();

            found = false;
            selectedOffering = null;

            for (CourseRegistration cr :
                    CourseRegistration.getRegistrationsByStudent(student.getUserID())) {

                if (cr == null)
                    continue;

                CourseOffering o = CourseOffering.findOffering(cr.getOfferingID());

                if (o != null && o.getCourseCode() != null
                        && o.getCourseCode().equalsIgnoreCase(courseCode)) {
                    selectedOffering = o;
                    found = true;
                    break;
                }
            }

            if (!found)
                UI.print("Course not found in your registered offerings");

        } while (!found);

        UI.header("INFORM FACULTY");

        boolean available = false;
        LocalDate today = LocalDate.now();

        if (today.getDayOfWeek().name().equalsIgnoreCase(day)) {
            available = Room.verifyAvailability(
                    roomCode, today.format(DATE_FORMAT), startTime, endTime);
        }

        String choice, message;

        if (available) {
            UI.liner("  Room is available");
            message = "Room is available";
        } else {
            UI.liner("  Room is occupied or unavailable");
            message = "Room is occupied or unavailable";
        }

        UI.liner("Inform Faculty?");
        UI.option();

        do {
            System.out.print("  Select > ");
            choice = in.nextLine().trim().toUpperCase();

            if (!choice.equals("/") && !choice.equals("X"))
                UI.print("Invalid Input");

        } while (!choice.equals("/") && !choice.equals("X"));

        switch (choice) {
            case "/":
                facultyID = selectedOffering.getFacultyID();

                Faculty f = Faculty.findFaculty(facultyID);

                if (f == null) {
                    UI.print("Faculty not found");
                    return;
                }

                Course c = Course.findCourse(selectedOffering.getCourseCode());

                if (c == null) {
                    UI.print("Course not found");
                    return;
                }

                Notification.notifications.add(new Notification(
                        student.getUserID(), f.getUserID(), c.getCourseCode(), roomCode,
                        buildingName, day, startTime, endTime, message));

                UI.print("Successfully informed faculty");
                break;

            case "X":
                break;
        }
    }
}