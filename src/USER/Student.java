package USER;

import DATA.*;
import UI.*;
import FUNCTION.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Locale;

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
            UI.header("CLASSNAV | STUDENT", false);
            UI.liner("  MY ACADEMIC RECORDS", false);
            UI.liner("  [1] View My Profile", false);
            UI.liner("  [2] Edit Password", false);
            UI.liner("  [3] View My Class Schedule", false);
            UI.separator(false);
            UI.liner("  ROOMS AND BUILDINGS", false);
            UI.liner("  [4] View Available Rooms", false);
            UI.liner("  [5] Locate a Room", false);
            UI.separator(false);
            UI.liner("  COMMUNICATION", false);
            UI.liner("  [6] Inform Faculty About Room Availability", false);
            UI.liner("  [7] View My Notifications", false);
            UI.separator(false);
            UI.liner("  [X] Log Out", false);
            UI.footer(false);

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

        if (p == null) {
            UI.print("Program not found. Student program was not changed.");
            return;
        }

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
            if (u != null && userID.equalsIgnoreCase(u.getUserID()) && u instanceof Student)
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
            UI.print("Student not found.");
            return;
        }
        ArrayList<Schedule> schedules = new ArrayList<>();
        ArrayList<String> displayedSchedules = new ArrayList<>();
        for (CourseRegistration registration : CourseRegistration.getRegistrationsByStudent(st.getUserID())) {
            if (registration == null) continue;
            CourseOffering offering = CourseOffering.findOffering(registration.getOfferingID());
            if (offering == null) continue;
            for (Schedule schedule : Schedule.getSchedulesByOffering(offering.getOfferingID())) {
                if (schedule == null || !isScheduleActive(schedule)
                        || displayedSchedules.contains(schedule.getScheduleID())) continue;
                displayedSchedules.add(schedule.getScheduleID());
                schedules.add(schedule);
            }
        }
        schedules.sort((a, b) -> {
            int day = Integer.compare(getDayOrder(a.getDay()), getDayOrder(b.getDay()));
            if (day != 0) return day;
            return Integer.compare(convertToMinutes(a.getStartTime()), convertToMinutes(b.getStartTime()));
        });
        UI.header("STUDENT WEEKLY TIMETABLE", true);
        UI.liner("  STUDENT ID: " + st.getUserID() + " | DATE: " + LocalDate.now(), true);
        if (schedules.isEmpty()) UI.liner("  No active schedules found for this week.", true);
        else {
            String format = "  %-12s %-14s %-14s %-18s %-12s %-22s %-12s %-25s %-20s %-20s";
            UI.liner(String.format(format, "SCHEDULE", "OFFERING", "COURSE", "SECTION", "DAY", "TIME",
                    "ROOM", "FACULTY", "BUILDING", "EFFECTIVE DATES"), true);
            UI.separator(true);
            int count = 0;
            for (Schedule schedule : schedules) {
                CourseOffering offering = CourseOffering.findOffering(schedule.getOfferingID());
                Course course = offering == null ? null : Course.findCourse(offering.getCourseCode());
                Faculty faculty = offering == null ? null : Faculty.findFaculty(offering.getFacultyID());
                Section section = offering == null ? null : Section.findSection(offering.getSectionID());
                Room room = Room.findRoom(schedule.getRoomCode());
                Building building = room == null ? null : Building.findBuilding(room.getBuildingCode());
                UI.liner(String.format(format, schedule.getScheduleID(), schedule.getOfferingID(),
                        course == null ? "-" : course.getCourseCode(),
                        UI.fitText(section == null ? "-" : Section.getYearSection(section), 18), schedule.getDay(),
                        UI.fitText(UI.formatTime(schedule.getStartTime()) + " - " + UI.formatTime(schedule.getEndTime()), 22),
                        schedule.getRoomCode(), UI.fitText(faculty == null ? "-" : faculty.getUserName(), 25),
                        UI.fitText(building == null ? "-" : building.getBuildingName(), 20),
                        UI.fitText(schedule.getEffectiveFrom() + " - " + schedule.getEffectiveTo(), 20)), true);
                count++;
            }
            UI.separator(true);
            UI.liner("  TOTAL SCHEDULES: " + count, true);
        }
        UI.footer(true);
        UI.returnButton();
    }

    // INFORM FACULTY USING A SPECIFIC DATE AND THE STUDENT'S REGISTERED OFFERINGS
    public void informFaculty() {
        Student student = Student.findStudent(getUserID());
        if (student == null) {
            UI.print("Student not found.");
            return;
        }

        ArrayList<CourseOffering> registeredOfferings = new ArrayList<>();
        for (CourseRegistration registration : CourseRegistration.getRegistrationsByStudent(student.getUserID())) {
            if (registration == null) continue;
            CourseOffering offering = CourseOffering.findOffering(registration.getOfferingID());
            if (offering != null && Faculty.findFaculty(offering.getFacultyID()) != null
                    && !registeredOfferings.contains(offering)) registeredOfferings.add(offering);
        }
        if (registeredOfferings.isEmpty()) {
            UI.print("No active course offerings with assigned Faculty members were found.");
            return;
        }

        UI.header("FACULTY MEMBERS IN MY REGISTERED CLASSES", true);
        UI.liner(String.format("  %-14s %-16s %-40s %-18s %-18s %-18s %-8s %-39s",
                "OFFERING ID", "FACULTY ID", "FACULTY NAME", "COURSE", "SECTION", "ACADEMIC YEAR", "TERM", "COURSE DESCRIPTION"), true);
        UI.separator(true);
        for (CourseOffering offering : registeredOfferings) {
            Faculty faculty = Faculty.findFaculty(offering.getFacultyID());
            Course course = Course.findCourse(offering.getCourseCode());
            Section section = Section.findSection(offering.getSectionID());
            UI.liner(String.format("  %-14s %-16s %-40s %-18s %-18s %-18s %-8s %-39s",
                    offering.getOfferingID(), offering.getFacultyID(),
                    UI.fitText(faculty == null ? "Faculty unavailable" : faculty.getUserName(), 40),
                    UI.fitText(course == null ? offering.getCourseCode() : course.getCourseCode(), 18),
                    UI.fitText(section == null ? "-" : Section.getYearSection(section), 18),
                    offering.getAcademicYear(), offering.getTerm(),
                    UI.fitText(course == null ? "Course unavailable" : course.getCourseDescription(), 39)), true);
        }
        UI.footer(true);

        String offeringID;
        CourseOffering selectedOffering = null;
        do {
            UI.select("Select Offering", false);
            offeringID = in.nextLine().trim();
            if (offeringID.equalsIgnoreCase("X")) return;
            for (CourseOffering offering : registeredOfferings) {
                if (offering.getOfferingID().equalsIgnoreCase(offeringID)) {
                    selectedOffering = offering;
                    break;
                }
            }
            if (selectedOffering == null) UI.print("Select an offering from your registered classes.");
        } while (selectedOffering == null);

        LocalDate meetingDate = null;
        String dateInput;
        do {
            UI.select("Enter Meeting Date (MM/dd/yyyy)", false);
            dateInput = in.nextLine().trim();
            if (dateInput.equalsIgnoreCase("X")) return;
            meetingDate = UI.parseDate(dateInput);
            if (meetingDate == null) UI.print("Invalid date. Use MM/dd/yyyy.");
            else if (meetingDate.isBefore(LocalDate.now())) {
                UI.print("Past dates cannot be selected.");
                meetingDate = null;
            }
        } while (meetingDate == null);

        String meetingDay = meetingDate.getDayOfWeek().getDisplayName(
                java.time.format.TextStyle.FULL, Locale.ENGLISH);
        ArrayList<Object> meetings = new ArrayList<>();
        ArrayList<String> cancelledIDs = new ArrayList<>();

        for (CancelledSchedule cancellation : CancelledSchedule.cancellations) {
            if (cancellation != null && cancellation.getAffectedDate() != null
                    && cancellation.getAffectedDate().equals(meetingDate.format(DATE_FORMAT)))
                cancelledIDs.add(cancellation.getScheduleID());
        }
        for (Schedule schedule : Schedule.getSchedulesByOffering(selectedOffering.getOfferingID())) {
            if (schedule == null || schedule.getDay() == null
                    || !schedule.getDay().equalsIgnoreCase(meetingDay)
                    || cancelledIDs.contains(schedule.getScheduleID())) continue;
            LocalDate from = UI.parseDate(schedule.getEffectiveFrom());
            LocalDate to = UI.parseDate(schedule.getEffectiveTo());
            if (from != null && to != null && !meetingDate.isBefore(from) && !meetingDate.isAfter(to))
                meetings.add(schedule);
        }
        for (MakeUpSchedule makeUp : MakeUpSchedule.makeUpSchedules) {
            if (makeUp != null && "Approved".equalsIgnoreCase(makeUp.getStatus())
                    && makeUp.getOfferingID() != null
                    && makeUp.getOfferingID().equalsIgnoreCase(selectedOffering.getOfferingID())
                    && meetingDate.format(DATE_FORMAT).equals(makeUp.getRequestedDate()))
                meetings.add(makeUp);
        }

        if (meetings.isEmpty()) {
            UI.print("No active or approved meetings were found for this Faculty");
            return;
        }

        UI.header("FACULTY MEETINGS ON " + meetingDate.format(DATE_FORMAT), true);
        UI.liner(String.format("  %-5s %-14s %-16s %-20s %-22s %-14s %-81s",
                "NO.", "TYPE", "ID", "COURSE", "TIME", "ROOM", "BUILDING / LOCATION"), true);
        UI.separator(true);
        for (int i = 0; i < meetings.size(); i++) {
            Object meeting = meetings.get(i);
            String type, id, startTime, endTime, roomCode;
            if (meeting instanceof Schedule schedule) {
                type = "Regular";
                id = schedule.getScheduleID();
                startTime = schedule.getStartTime();
                endTime = schedule.getEndTime();
                roomCode = schedule.getRoomCode();
            } else {
                MakeUpSchedule makeUp = (MakeUpSchedule) meeting;
                type = "Make-up";
                id = makeUp.getMakeUpID();
                startTime = makeUp.getStartTime();
                endTime = makeUp.getEndTime();
                roomCode = makeUp.getRoomCode();
            }
            Room room = Room.findRoom(roomCode);
            Building building = room == null ? null : Building.findBuilding(room.getBuildingCode());
            UI.liner(String.format("  %-5d %-14s %-16s %-20s %-22s %-14s %-81s",
                    i + 1, type, id, selectedOffering.getCourseCode(),
                    UI.fitText(UI.formatTime(startTime) + " - " + UI.formatTime(endTime), 22),
                    roomCode == null ? "-" : roomCode,
                    UI.fitText(building == null ? "-" : building.getBuildingName(), 81)), true);
        }
        UI.footer(true);

        int selected = -1;
        String choice;
        do {
            UI.select("Select Meeting Number", false);
            choice = in.nextLine().trim();
            if (choice.equalsIgnoreCase("X")) return;
            try {
                selected = Integer.parseInt(choice);
                if (selected < 1 || selected > meetings.size()) {
                    UI.print("Choose a meeting number from the displayed list.");
                    selected = -1;
                }
            } catch (NumberFormatException e) {
                UI.print("Enter a valid meeting number.");
                selected = -1;
            }
        } while (selected == -1);

        Object meeting = meetings.get(selected - 1);
        String startTime, endTime, roomCode, meetingID;
        if (meeting instanceof Schedule schedule) {
            startTime = schedule.getStartTime();
            endTime = schedule.getEndTime();
            roomCode = schedule.getRoomCode();
            meetingID = schedule.getScheduleID();
        } else {
            MakeUpSchedule makeUp = (MakeUpSchedule) meeting;
            startTime = makeUp.getStartTime();
            endTime = makeUp.getEndTime();
            roomCode = makeUp.getRoomCode();
            meetingID = makeUp.getMakeUpID();
        }

        Room selectedRoom = Room.findRoom(roomCode);
        if (selectedRoom == null) {
            UI.print("The selected meeting's room is no longer available in the active room list.");
            return;
        }
        Building building = Building.findBuilding(selectedRoom.getBuildingCode());
        if (building == null) {
            UI.print("The selected meeting's building could not be found.");
            return;
        }

        ArrayList<Room> availableRooms = new ArrayList<>();
        for (Room room : Room.rooms) {
            if (room != null && Room.verifyAvailability(room.getRoomCode(),
                    meetingDate.format(DATE_FORMAT), startTime, endTime)) availableRooms.add(room);
        }

        UI.header("OTHER ROOMS AVAILABLE AT THE SAME TIME", true);
        UI.liner("  MEETING: " + meetingID + " | " + selectedOffering.getCourseCode()
                + " | " + UI.formatTime(startTime) + " - " + UI.formatTime(endTime), true);
        if (availableRooms.isEmpty()) {
            UI.liner("  No alternative rooms are available for this date and time.", true);
        } else {
            UI.separator(true);
            UI.liner(String.format("  %-12s %-92s %-35s %-14s %-21s",
                    "ROOM CODE", "DESCRIPTION", "BUILDING", "FLOOR", "CAPACITY"), true);
            UI.separator(true);
            for (Room room : availableRooms) {
                Building roomBuilding = Building.findBuilding(room.getBuildingCode());
                UI.liner(String.format("  %-12s %-92s %-35s %-14s %-21d",
                        room.getRoomCode(), UI.fitText(room.getRoomDescription(), 92),
                        UI.fitText(roomBuilding == null ? "-" : roomBuilding.getBuildingName(), 35),
                        UI.fitText(Room.getFloorText(room.getFloorNumber()), 14), room.getCapacity()), true);
            }
        }
        UI.footer(true);

        String message = "Student requests confirmation or assistance for the scheduled meeting "
                + meetingID + " on " + meetingDate.format(DATE_FORMAT) + ".";
        if (availableRooms.isEmpty()) {
            message += " No alternative rooms are currently available for this time.";
        } else {
            StringBuilder roomList = new StringBuilder();
            for (Room room : availableRooms) {
                if (roomList.length() > 0) roomList.append(", ");
                roomList.append(room.getRoomCode());
            }
            message += " Alternative available rooms: " + roomList + ".";
        }

        UI.select("Inform " + Faculty.findFaculty(selectedOffering.getFacultyID()).getUserName() + "? (Y/N)", false);
        String confirm = in.nextLine().trim().toUpperCase(Locale.ENGLISH);
        if (confirm.equals("X")) return;
        while (!confirm.matches("[YN]")) {
            UI.print("Enter Y or N.");
            UI.select("Confirm (Y/N)", false);
            confirm = in.nextLine().trim().toUpperCase(Locale.ENGLISH);
            if (confirm.equals("X")) return;
        }
        if (!confirm.equals("Y")) {
            UI.print("Notification cancelled.");
            return;
        }

        Faculty faculty = Faculty.findFaculty(selectedOffering.getFacultyID());
        Course course = Course.findCourse(selectedOffering.getCourseCode());
        if (faculty == null || course == null) {
            UI.print("Faculty or course information is no longer available.");
            return;
        }

        if (Notification.sendNotification(student.getUserID(), faculty.getUserID(), course.getCourseCode(),
                selectedRoom.getRoomCode(), building.getBuildingName(), meetingDay,
                startTime, endTime, message, selectedOffering.getOfferingID(), null,
                meetingDate.format(DATE_FORMAT))) {
            UI.print("Faculty has been informed successfully.");
        }
    }
}