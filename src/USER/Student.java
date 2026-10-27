package USER;

import DATA.*;
import UI.*;
import FUNCTION.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import static DATA.Room.convertToMinutes;
import static DATA.Room.findRoom;
import static UI.UI.*;

public class Student extends User {

    private static int nextStudentID = 1;

    private String enrollmentStatus;
    private String programCode;

    public Student(String lastName, String firstName, String middleInitial, String emailAddress,
                   String password, String address, int postalCode, String birthDate, char gender,
                   String enrollmentStatus, String programCode) {

        super(String.format("S%04d", nextStudentID++), lastName, firstName, middleInitial, emailAddress,
                password, address, postalCode, birthDate, gender);
        this.enrollmentStatus = enrollmentStatus;
        this.programCode = programCode;
    }

    @Override
    public void menu() {
        String select;

        while (true) {
            UI.header("CLASSNAV | STUDENT");
            UI.liner("  MY ACADEMIC RECORDS");
            UI.liner("  [1] View My Profile");
            UI.liner("  [2] Edit Password");
            UI.liner("  [3] View My Class Schedule");
            UI.separator();
            UI.liner("  ROOMS AND BUILDINGS");
            UI.liner("  [4] View Available Rooms");
            UI.liner("  [5] Locate a Room");
            UI.separator();
            UI.liner("  COMMUNICATION");
            UI.liner("  [6] Inform Faculty About Room Availability");
            UI.liner("  [7] View My Notifications");
            UI.separator();
            UI.liner("  [X] Log Out");
            UI.footer();

            System.out.print("  Select > ");
            select = in.nextLine().trim().toUpperCase();

            switch (select) {
                case "1": User.viewProfile(getUserID(),true); break;
                case "2": User.editPassword(getUserID()); break;
                case "3": Student.displaySchedules(getUserID()); break;
                case "4": User.viewAvailableRooms(); break;
                case "5": User.locateRoom(); break;
                case "6": informFaculty(); break;
                case "7": viewMyNotifications(); break;
                case "X": UI.print("You have been logged out."); return;
                default: UI.print("Invalid Choice"); break;
            }
        }
    }

    // SETTER METHODS
    public void setEnrollmentStatus(String enrollmentStatus) {
        if (enrollmentStatus == null || enrollmentStatus.isBlank()) {
            UI.print("Enrollment status cannot be blank");
            return;
        }
        this.enrollmentStatus = enrollmentStatus.trim();
    }
    public void setProgramCode(String programCode) {
        Program p = Program.findProgram(programCode);

        if (p == null)
            throw new IllegalArgumentException("Program not found");

        this.programCode = p.getProgramCode();
    }

    // GETTER METHODS
    public String getEnrollmentStatus() {
        return enrollmentStatus;
    }
    public String getProgramCode() {
        return programCode;
    }
    private static int getDayOrder(String day) {
        if (day == null) return 8;
        for (int i = 0; i < DAYS.length; i++) if (DAYS[i].equalsIgnoreCase(day.trim())) return i + 1;
        return 8;
    }

    // FIND METHOD
    public static Student findStudent(String userID) {
        if (userID == null) return null;

        userID = userID.trim();

        for (User u : users) {
            if (userID.equalsIgnoreCase(u.getUserID()) && u instanceof Student)
                return (Student) u;
        }

        return null;
    }

    // BOOLEAN METHODS
    private static boolean isValidDay(String day) {
        if (day == null) return false;

        for (String validDay : DAYS) {
            if (validDay.equalsIgnoreCase(day.trim()))
                return true;
        }

        return false;
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
        if (schedule == null || schedule.getEffectiveFrom() == null || schedule.getEffectiveTo() == null)
            return false;
        try {
            LocalDate effectiveFrom = LocalDate.parse(schedule.getEffectiveFrom(), DATE_FORMAT);
            LocalDate effectiveTo = LocalDate.parse(schedule.getEffectiveTo(), DATE_FORMAT);
            LocalDate today = LocalDate.now();
            return !today.isBefore(effectiveFrom) && !today.isAfter(effectiveTo);
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    // DISPLAY METHOD
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
                else UI.liner("");

                CourseOffering o = CourseOffering.findOffering(s.getOfferingID());
                Course c = o == null ? null : Course.findCourse(o.getCourseCode());
                Section section = o == null ? null : Section.findSection(o.getSectionID());
                Room r = Room.findRoom(s.getRoomCode());

                Building b = r == null ? null : Building.findBuilding(r.getBuildingCode());

                UI.liner(String.format("  TIME          : %s - %s",
                        s.getStartTime(), s.getEndTime()));
                UI.liner(String.format("  COURSE        : %s",
                        c == null ? "Course not found" : c.getCourseCode()));
                UI.liner(String.format("  FACULTY       : %s", o == null ? "Faculty not found" :
                        Faculty.findFaculty(o.getFacultyID()).getUserName()));
                UI.liner(String.format("  SECTION       : %s",
                        section == null ? "Section not found" : Section.getYearSection(section)));
                UI.liner(String.format("  ROOM CODE     : %s", s.getRoomCode()));
                UI.liner(String.format("  BUILDING NAME : %s", b == null ? "Building not found" : b.getBuildingName()));
            }
        }

        UI.footer();
        UI.returnButton();
    }

    // INFORM FACULTY FORM
    public void informFaculty() {
        String roomCode = "", day = "", startTime = "", endTime = "", choice;
        boolean submit = false;

        while (!submit) {
            UI.header("INFORM FACULTY");
            UI.liner(" [V] VIEW AVAILABLE ROOMS ");
            UI.separator();
            UI.liner(" [1] ROOM CODE   : " + (roomCode.isEmpty() ? " " : roomCode));
            UI.liner(" [2] DAY         : " + (day.isEmpty() ? " " : day));
            UI.liner(" [3] START TIME  : " + (startTime.isEmpty() ? " " : startTime));
            UI.liner(" [4] END TIME    : " + (endTime.isEmpty() ? " " : endTime));
            UI.liner("");
            UI.option();
            UI.footer();

            do {
                UI.select("Select", false);
                choice = in.nextLine().trim().toUpperCase();

                if (!choice.matches("[1-3XV]") && !choice.equals("/")) UI.print("Invalid Choice");
            } while (!choice.matches("[1-3XV]") && !choice.equals("/") && !choice.equals("X"));

            switch (choice) {
                case "1":
                    do {
                        UI.select("Enter Room Code", true);
                        roomCode = in.nextLine().trim();

                        if (roomCode.equalsIgnoreCase("X")) {
                            roomCode = "";
                            break;
                        }
                        else if (roomCode.isEmpty()) UI.print("Room Code is required");
                        else if (Room.findRoom(roomCode) == null) UI.print("Room not found");
                    } while (roomCode.isEmpty() || findRoom(roomCode) == null);
                    break;

                case "2":
                    do {
                        UI.select("Enter Day", true);
                        day = in.nextLine().trim();

                        if (day.equalsIgnoreCase("X")) {
                            day = "";
                            break;
                        }
                        else if (day.isEmpty()) UI.print("Day is required");
                        else if (!isValidDay(day)) UI.print("Invalid day");
                    } while (!isValidDay(day) || day.isEmpty());

                    day = normalizeDay(day);
                    break;

                case "3":
                    do {
                        UI.select("Enter Start Time", true);
                        startTime = in.nextLine().trim();
                        if (startTime.equalsIgnoreCase("X")) startTime = "";
                        else if (startTime.isEmpty()) UI.print("Start Time is required");
                        else {
                            UI.select("Enter End Time", true);
                            endTime = in.nextLine().trim();
                            if (endTime.equalsIgnoreCase("X")) endTime = "";
                            else if (endTime.isEmpty()) UI.print("End Time is required");
                            else if (!isValidTimeRange(startTime, endTime)) UI.print("Invalid time range");
                        }
                    } while (!isValidTimeRange(startTime, endTime));
                    break;

                case "V": viewAvailableRooms(); break;

                case "/":
                    if (roomCode.isEmpty() || day.isEmpty() || startTime.isEmpty() || endTime.isEmpty()) {
                        UI.print("Incomplete details");
                        break;
                    }

                    Room room = Room.findRoom(roomCode);
                    Building building = room == null ? null : Building.findBuilding(room.getBuildingCode());

                    if (room == null || building == null) {
                        UI.print("Room or building not found");
                        break;
                    }

                    informFaculty(getUserID(), building.getBuildingName(), roomCode, day, startTime, endTime);

                    submit = true;
                    break;

                case "X":
                    submit = true;
                    break;
            }
        }
    }
    public static void informFaculty(String studentID, String buildingName, String roomCode, String day,
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
            UI.select("Enter Course Code", true);
            courseCode = in.nextLine().trim();

            found = false;
            selectedOffering = null;

            for (CourseRegistration cr :
                    CourseRegistration.getRegistrationsByStudent(student.getUserID())) {

                if (cr == null)
                    continue;

                CourseOffering o = CourseOffering.findOffering(cr.getOfferingID());

                if (o != null && o.getCourseCode() != null && o.getCourseCode().equalsIgnoreCase(courseCode)) {
                    selectedOffering = o;
                    found = true;
                    break;
                }
            }

            if (!found) UI.print("Course not found in your registered offerings");

        } while (!found);

        UI.header("INFORM FACULTY");

        boolean available = false;
        LocalDate today = LocalDate.now();

        if (today.getDayOfWeek().name().equalsIgnoreCase(day)) {
            available = Room.verifyAvailability(roomCode, today.format(DATE_FORMAT), startTime, endTime);
        }

        String choice, message;

        if (available) {
            UI.liner("  Room is available");
            message = "Room is available";
        } else {
            UI.liner("  Room is occupied or unavailable");
            message = "Room is occupied or unavailable";
        }

        UI.liner("  Inform Faculty?");
        UI.liner("");
        UI.option();
        UI.footer();

        do {
            UI.select("Select", false);
            choice = in.nextLine().trim().toUpperCase();
            if (!choice.equals("/") && !choice.equals("X")) UI.print("Invalid Input");
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