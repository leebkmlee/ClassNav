package USER;

import DATA.*;
import UI.*;
import FUNCTION.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

import static DATA.Enrollment.enrollments;
import static DATA.Room.convertToMinutes;
import static DATA.Section.sections;

public class Student extends User {

    private static int nextStudentID = 1;
    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("MM/dd/uuuu")
                    .withResolverStyle(ResolverStyle.STRICT);

    private String enrollmentStatus;
    private String programCode; // foreign key

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
        this.programCode = Program.getProgram(p);
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

        this.programCode = Program.getProgram(p);
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

    public static void locateRoom(String roomCode) {
        Room r = Room.findRoom(roomCode);

        if (r == null) {
            UI.print("Room not found");
            return;
        }

        Building b = Building.findBuilding(Room.getBuildingCode(r));

        if (b == null) {
            UI.print("Building not found");
            return;
        }

        UI.header("ROOM INFORMATION");
        System.out.printf("│%-" + innerWidth + "s│%n", "  ROOM DETAILS");
        UI.separator();

        System.out.printf("│%-" + innerWidth + "s│%n",
                String.format("  ROOM CODE         : %s", Room.getRoom(r)));

        System.out.printf("│%-" + innerWidth + "s│%n",
                String.format("  ROOM DESCRIPTION  : %s", Room.getRoomDescription(r)));

        UI.separator();
        System.out.printf("│%-" + innerWidth + "s│%n", "  ROOM LOCATION");
        UI.separator();

        System.out.printf("│%-" + innerWidth + "s│%n",
                String.format("  BUILDING NAME     : %s", Building.getBuildingName(b)));

        int floor = Room.getFloorNumber(r);
        String floorText;

        if (floor == 1) floorText = "1st Floor";
        else if (floor == 2) floorText = "2nd Floor";
        else if (floor == 3) floorText = "3rd Floor";
        else floorText = floor + "th Floor";

        System.out.printf("│%-" + innerWidth + "s│%n",
                String.format("  FLOOR NUMBER      : %s", floorText));

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

    private static boolean isScheduleActiveToday(Schedule schedule) {
        if (schedule == null
                || schedule.getEffectiveFrom() == null
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

    private static boolean isRoomAvailableToday(String roomCode, String day,
                                                String startTime, String endTime) {
        Room room = Room.findRoom(roomCode);

        if (room == null || !isValidDay(day)
                || !isValidTimeRange(startTime, endTime))
            return false;

        String normalizedDay = normalizeDay(day);
        int requestedStart = convertToMinutes(startTime);
        int requestedEnd = convertToMinutes(endTime);

        for (Schedule s : Schedule.schedules) {
            if (s == null || !isScheduleActiveToday(s))
                continue;

            if (s.getRoomCode() == null || s.getDay() == null)
                continue;

            if (roomCode.trim().equalsIgnoreCase(s.getRoomCode())
                    && normalizedDay.equalsIgnoreCase(s.getDay())) {

                try {
                    int existingStart = convertToMinutes(s.getStartTime());
                    int existingEnd = convertToMinutes(s.getEndTime());

                    if (requestedStart < existingEnd
                            && requestedEnd > existingStart)
                        return false;
                } catch (RuntimeException e) {
                    // Treat an invalid active schedule as a possible conflict.
                    return false;
                }
            }
        }

        return true;
    }

    public static void displaySchedules(String studentID) {
        Student st = Student.findStudent(studentID);

        if (st == null) {
            UI.print("Student not found");
            return;
        }

        UI.header("STUDENT SCHEDULE");
        System.out.printf("│%-" + innerWidth + "s│%n",
                String.format("  STUDENT ID : %s", st.getUserID()));
        System.out.printf("│%-" + innerWidth + "s│%n",
                String.format("  DATE       : %s", LocalDate.now()));
        UI.separator();

        boolean found = false;

        for (Enrollment e : enrollments) {
            if (e == null || Enrollment.getStudentNumber(e) == null)
                continue;

            if (st.getUserID().equalsIgnoreCase(Enrollment.getStudentNumber(e))) {
                String sectionID = Enrollment.getSectionID(e);
                Section section = Section.findSection(sectionID);

                if (section == null)
                    continue;

                for (CourseOffering o : CourseOffering.getOfferingsBySection(sectionID)) {
                    if (o == null)
                        continue;

                    for (Schedule s : Schedule.getSchedulesByOffering(o.getOfferingID())) {
                        if (s == null || !isScheduleActiveToday(s))
                            continue;

                        found = true;

                        Course c = Course.findCourseByID(o.getCourseID());
                        Room r = Room.findRoom(s.getRoomCode());

                        Building b = r == null ? null
                                : Building.findBuilding(Room.getBuildingCode(r));

                        System.out.printf("│%-" + innerWidth + "s│%n",
                                String.format("  SCHEDULE ID   : %s", s.getScheduleID()));

                        System.out.printf("│%-" + innerWidth + "s│%n",
                                String.format("  COURSE        : %s",
                                        c == null ? "Course not found" : c.getCourseCode()));

                        System.out.printf("│%-" + innerWidth + "s│%n",
                                String.format("  SECTION       : %s",
                                        Section.getYearSection(section)));

                        System.out.printf("│%-" + innerWidth + "s│%n",
                                String.format("  DAY           : %s", s.getDay()));

                        System.out.printf("│%-" + innerWidth + "s│%n",
                                String.format("  TIME          : %s - %s",
                                        s.getStartTime(), s.getEndTime()));

                        System.out.printf("│%-" + innerWidth + "s│%n",
                                String.format("  ROOM CODE     : %s", s.getRoomCode()));

                        System.out.printf("│%-" + innerWidth + "s│%n",
                                String.format("  BUILDING NAME : %s",
                                        b == null ? "Building not found"
                                                : Building.getBuildingName(b)));

                        UI.separator();
                    }
                }
            }
        }

        if (!found)
            System.out.printf("│%-" + innerWidth + "s│%n",
                    "  No active schedules found for today.");

        UI.footer();
    }

    public static void viewAvailableRooms(String day, String startTime, String endTime) {
        if (!isValidDay(day)) {
            UI.print("Invalid day");
            return;
        }

        if (!isValidTimeRange(startTime, endTime)) {
            UI.print("Invalid time range");
            return;
        }

        day = normalizeDay(day);

        int scheduledStart = convertToMinutes(startTime);
        int scheduledEnd = convertToMinutes(endTime);

        UI.header("AVAILABLE ROOMS");
        System.out.printf("│%-" + innerWidth + "s│%n",
                String.format("  CHECK DATE   : %s", LocalDate.now()));
        System.out.printf("│%-" + innerWidth + "s│%n",
                String.format("  DAY          : %s", day));
        System.out.printf("│%-" + innerWidth + "s│%n",
                String.format("  START TIME   : %s", startTime));
        System.out.printf("│%-" + innerWidth + "s│%n",
                String.format("  END TIME     : %s", endTime));
        UI.separator();

        boolean found = false;

        for (Room r : Room.rooms) {
            String roomCode = Room.getRoom(r);
            boolean available = true;

            for (Schedule s : Schedule.schedules) {
                if (s == null || !isScheduleActiveToday(s))
                    continue;

                if (s.getRoomCode() == null || s.getDay() == null)
                    continue;

                if (roomCode.equalsIgnoreCase(s.getRoomCode())
                        && day.equalsIgnoreCase(s.getDay())) {

                    try {
                        int existingStart = convertToMinutes(s.getStartTime());
                        int existingEnd = convertToMinutes(s.getEndTime());

                        if (scheduledStart < existingEnd
                                && scheduledEnd > existingStart) {
                            available = false;
                            break;
                        }
                    } catch (RuntimeException e) {
                        // An invalid active schedule must not make the room appear free.
                        available = false;
                        break;
                    }
                }
            }

            if (available) {
                found = true;

                int floor = Room.getFloorNumber(r);
                String floorText;

                if (floor == 1) floorText = "1st Floor";
                else if (floor == 2) floorText = "2nd Floor";
                else if (floor == 3) floorText = "3rd Floor";
                else floorText = floor + "th Floor";

                String room = String.format("  %-10s %-32s %-15s %-4s",
                        Room.getRoom(r), Room.getRoomDescription(r),
                        floorText, Room.getBuildingCode(r));

                System.out.printf("│%-" + innerWidth + "s│%n", room);
            }
        }

        if (!found)
            System.out.printf("│%-" + innerWidth + "s│%n",
                    "  NO AVAILABLE ROOMS FOUND");

        UI.footer();
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

        String courseID = "";
        String facultyID = "";
        CourseOffering selectedOffering = null;
        boolean found = false;

        do {
            System.out.print("  Enter Course ID > ");
            courseID = in.nextLine().trim();

            found = false;
            selectedOffering = null;

            for (Enrollment e : enrollments) {
                if (e == null || Enrollment.getStudentNumber(e) == null)
                    continue;

                if (student.getUserID().equalsIgnoreCase(Enrollment.getStudentNumber(e))) {
                    String sectionID = Enrollment.getSectionID(e);

                    for (CourseOffering o : CourseOffering.getOfferingsBySection(sectionID)) {
                        if (o != null && o.getCourseID() != null
                                && o.getCourseID().equalsIgnoreCase(courseID)) {
                            selectedOffering = o;
                            found = true;
                            break;
                        }
                    }
                }

                if (found)
                    break;
            }

            if (!found)
                UI.print("Course not found in your enrolled section");

        } while (!found);

        UI.header("INFORM FACULTY");

        boolean available = isRoomAvailableToday(roomCode, day, startTime, endTime);
        String choice, message;

        if (available) {
            System.out.printf("│%-" + innerWidth + "s│%n", "  Room is available");
            message = "Room is available";
        } else {
            System.out.printf("│%-" + innerWidth + "s│%n", "  Room is occupied");
            message = "Room is occupied";
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

                Course c = Course.findCourseByID(selectedOffering.getCourseID());

                if (c == null) {
                    UI.print("Course not found");
                    return;
                }

                Notification.notifications.add(new Notification(
                        student.getUserID(), f.getUserName(), c.getCourseCode(), roomCode,
                        buildingName, day, startTime, endTime, message));

                UI.print("Successfully informed faculty");
                break;

            case "X":
                break;
        }
    }
}