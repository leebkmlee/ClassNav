
package DATA;

import UI.*;
import static UI.UI.*;
import java.util.*;
import java.time.*;
import java.time.format.*;

import static UI.UI.in;

public class Schedule {

    // FIELDS
    private static int nextScheduleID = 1;

    private final String scheduleID;
    private String offeringID, day, startTime, endTime, effectiveFrom, effectiveTo, roomCode;

    public static ArrayList<Schedule> schedules = new ArrayList<>();
    public static ArrayList<Schedule> archiveSchedules = new ArrayList<>();

    // CONSTRUCTOR
    public Schedule(String offeringID, String day, String startTime, String endTime,
                    String effectiveFrom, String effectiveTo, String roomCode) {
        this.scheduleID = String.format("SCH%04d", nextScheduleID++);
        this.offeringID = offeringID;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.effectiveFrom = effectiveFrom;
        this.effectiveTo = effectiveTo;
        this.roomCode = roomCode;
    }

    // DUMMY DATA
    static {
        schedules.add(new Schedule("COF001", "Monday", "8:00 AM", "9:30 AM", "10/23/2026", "12/18/2026", "RM001"));
        schedules.add(new Schedule("COF002", "Tuesday", "10:00 AM", "11:30 AM", "10/23/2026", "12/18/2026", "RM002"));
        schedules.add(new Schedule("COF003", "Wednesday", "12:00 PM", "1:30 PM", "10/23/2026", "12/18/2026", "RM011"));
        schedules.add(new Schedule("COF004", "Thursday", "2:00 PM", "3:30 PM", "10/23/2026", "12/18/2026", "RM004"));
        schedules.add(new Schedule("COF005", "Friday", "4:00 PM", "5:30 PM", "10/23/2026", "12/18/2026", "RM005"));
        schedules.add(new Schedule("COF006", "Monday", "9:45 AM", "11:15 AM", "10/23/2026", "12/18/2026", "RM002"));
        schedules.add(new Schedule("COF007", "Tuesday", "1:00 PM", "2:30 PM", "10/23/2026", "12/18/2026", "RM004"));
        schedules.add(new Schedule("COF008", "Wednesday", "8:00 AM", "9:30 AM", "10/23/2026", "12/18/2026", "RM001"));
        schedules.add(new Schedule("COF009", "Thursday", "10:00 AM", "11:30 AM", "10/23/2026", "12/18/2026", "RM005"));
        schedules.add(new Schedule("COF010", "Friday", "8:00 AM", "9:30 AM", "10/23/2026", "12/18/2026", "RM003"));
        schedules.add(new Schedule("COF011", "Monday", "1:00 PM", "2:30 PM", "10/23/2026", "12/18/2026", "RM004"));
        schedules.add(new Schedule("COF012", "Tuesday", "8:00 AM", "9:30 AM", "10/23/2026", "12/18/2026", "RM002"));
        schedules.add(new Schedule("COF013", "Wednesday", "2:00 PM", "3:30 PM", "10/23/2026", "12/18/2026", "RM005"));
        schedules.add(new Schedule("COF014", "Thursday", "8:00 AM", "9:30 AM", "10/23/2026", "12/18/2026", "RM013"));
        schedules.add(new Schedule("COF015", "Friday", "10:00 AM", "11:30 AM", "10/23/2026", "12/18/2026", "RM004"));
        schedules.add(new Schedule("COF016", "Monday", "10:00 AM", "11:30 AM", "10/23/2026", "12/18/2026", "RM006"));
        schedules.add(new Schedule("COF017", "Tuesday", "2:00 PM", "3:30 PM", "10/23/2026", "12/18/2026", "RM007"));
        schedules.add(new Schedule("COF018", "Wednesday", "10:00 AM", "12:00 PM", "10/23/2026", "12/18/2026", "RM008"));
        schedules.add(new Schedule("COF019", "Thursday", "12:00 PM", "1:30 PM", "10/23/2026", "12/18/2026", "RM004"));
        schedules.add(new Schedule("COF020", "Friday", "1:00 PM", "3:00 PM", "10/23/2026", "12/18/2026", "RM013"));
        schedules.add(new Schedule("COF001", "Wednesday", "8:00 AM", "9:30 AM", "10/23/2026", "12/18/2026", "RM002"));
        schedules.add(new Schedule("COF002", "Thursday", "10:00 AM", "11:30 AM", "10/23/2026", "12/18/2026", "RM001"));
        schedules.add(new Schedule("COF003", "Friday", "8:00 AM", "9:30 AM", "10/23/2026", "12/18/2026", "RM012"));
        schedules.add(new Schedule("COF004", "Monday", "2:00 PM", "3:30 PM", "10/23/2026", "12/18/2026", "RM005"));
        schedules.add(new Schedule("COF005", "Tuesday", "4:00 PM", "5:30 PM", "10/23/2026", "12/18/2026", "RM004"));

        schedules.add(new Schedule("COF021","Monday","8:00 AM","9:30 AM","10/26/2026","12/18/2026","RM006"));
        schedules.add(new Schedule("COF022","Monday","9:45 AM","11:15 AM","10/26/2026","12/18/2026","RM007"));
        schedules.add(new Schedule("COF023","Tuesday","8:00 AM","9:30 AM","10/26/2026","12/18/2026","RM008"));
        schedules.add(new Schedule("COF024","Tuesday","9:45 AM","11:15 AM","10/26/2026","12/18/2026","RM009"));
        schedules.add(new Schedule("COF025","Wednesday","8:00 AM","9:30 AM","10/26/2026","12/18/2026","RM010"));
        schedules.add(new Schedule("COF026","Wednesday","9:45 AM","11:15 AM","10/26/2026","12/18/2026","RM012"));
        schedules.add(new Schedule("COF027","Thursday","8:00 AM","9:30 AM","10/26/2026","12/18/2026","RM013"));
        schedules.add(new Schedule("COF028","Thursday","9:45 AM","11:15 AM","10/26/2026","12/18/2026","RM014"));
        schedules.add(new Schedule("COF029","Friday","8:00 AM","9:30 AM","10/26/2026","12/18/2026","RM006"));
        schedules.add(new Schedule("COF030","Friday","9:45 AM","11:15 AM","10/26/2026","12/18/2026","RM007"));
        schedules.add(new Schedule("COF021","Tuesday","11:30 AM","1:00 PM","10/26/2026","12/18/2026","RM006"));
        schedules.add(new Schedule("COF022","Wednesday","11:30 AM","1:00 PM","10/26/2026","12/18/2026","RM007"));
        schedules.add(new Schedule("COF023","Thursday","11:30 AM","1:00 PM","10/26/2026","12/18/2026","RM008"));
        schedules.add(new Schedule("COF024","Friday","11:30 AM","1:00 PM","10/26/2026","12/18/2026","RM009"));
        schedules.add(new Schedule("COF025","Monday","11:30 AM","1:00 PM","10/26/2026","12/18/2026","RM010"));
        schedules.add(new Schedule("COF026","Tuesday","1:00 PM","2:30 PM","10/26/2026","12/18/2026","RM012"));
        schedules.add(new Schedule("COF027","Wednesday","1:00 PM","2:30 PM","10/26/2026","12/18/2026","RM013"));
        schedules.add(new Schedule("COF028","Thursday","1:00 PM","2:30 PM","10/26/2026","12/18/2026","RM014"));
        schedules.add(new Schedule("COF029","Friday","1:00 PM","2:30 PM","10/26/2026","12/18/2026","RM006"));
        schedules.add(new Schedule("COF030","Monday","1:00 PM","2:30 PM","10/26/2026","12/18/2026","RM007"));
        schedules.add(new Schedule("COF021","Wednesday","2:00 PM","3:30 PM","10/26/2026","12/18/2026","RM001"));
        schedules.add(new Schedule("COF022","Thursday","2:00 PM","3:30 PM","10/26/2026","12/18/2026","RM002"));
        schedules.add(new Schedule("COF023","Friday","2:00 PM","3:30 PM","10/26/2026","12/18/2026","RM003"));
        schedules.add(new Schedule("COF024","Monday","3:00 PM","4:30 PM","10/26/2026","12/18/2026","RM004"));
        schedules.add(new Schedule("COF025","Tuesday","3:00 PM","4:30 PM","10/26/2026","12/18/2026","RM005"));
    }

    // GETTERS
    public String getScheduleID() {
        return scheduleID;
    }
    public String getOfferingID() {
        return offeringID;
    }
    public String getDay() {
        return day;
    }
    public String getStartTime() {
        return startTime;
    }
    public String getEndTime() {
        return endTime;
    }
    public String getEffectiveFrom() {
        return effectiveFrom;
    }
    public String getEffectiveTo() {
        return effectiveTo;
    }
    public String getRoomCode() {
        return roomCode;
    }

    // FIND METHODS
    public static Schedule findSchedule(String scheduleID) {

        if (scheduleID == null || scheduleID.trim().isEmpty())
            return null;

        for (Schedule s : schedules) {
            if (s != null && scheduleID.trim().equalsIgnoreCase(s.getScheduleID()))
                return s;
        }

        return null;
    }

    // RETRIEVE SCHEDULES BY COURSE OFFERING
    public static ArrayList<Schedule> getSchedulesByOffering(String offeringID) {

        ArrayList<Schedule> result = new ArrayList<>();

        if (CourseOffering.findOffering(offeringID) == null)
            return result;

        for (Schedule s : schedules) {
            if (s != null && offeringID.trim().equalsIgnoreCase(s.getOfferingID()))
                result.add(s);
        }

        return result;
    }

    // RETRIEVE SCHEDULES BY ROOM
    public static ArrayList<Schedule> getSchedulesByRoom(String roomCode) {

        ArrayList<Schedule> result = new ArrayList<>();

        if (Room.findRoom(roomCode) == null)
            return result;

        for (Schedule s : schedules) {
            if (s != null && roomCode.trim().equalsIgnoreCase(s.getRoomCode()))
                result.add(s);
        }

        return result;
    }

    // CHECKS FOR ROOM CONFLICTS
    private static boolean hasRoomConflict(String roomCode, String day, String startTime, String endTime,
                                           String effectiveFrom, String effectiveTo, String excludedScheduleID) {
        int requestedStart;
        int requestedEnd;

        try {
            requestedStart = Room.convertToMinutes(startTime);
            requestedEnd = Room.convertToMinutes(endTime);
        } catch (RuntimeException e) {
            return true;
        }

        LocalDate requestedFrom = parseDate(effectiveFrom);
        LocalDate requestedTo = parseDate(effectiveTo);

        if (requestedStart < 0 || requestedEnd < 0
                || requestedStart >= requestedEnd
                || requestedFrom == null || requestedTo == null
                || requestedFrom.isAfter(requestedTo)) {
            return true;
        }

        for (Schedule s : schedules) {

            if (s == null)
                continue;

            if (excludedScheduleID != null
                    && excludedScheduleID.equalsIgnoreCase(s.getScheduleID()))
                continue;

            if (s.getRoomCode() == null || s.getDay() == null)
                continue;

            if (!roomCode.equalsIgnoreCase(s.getRoomCode().trim())
                    || !day.equalsIgnoreCase(s.getDay().trim()))
                continue;

            LocalDate existingFrom = parseDate(s.getEffectiveFrom());
            LocalDate existingTo = parseDate(s.getEffectiveTo());

            if (existingFrom == null || existingTo == null)
                continue;

            // Effective date ranges must overlap.
            boolean datesOverlap =
                    !requestedFrom.isAfter(existingTo)
                            && !requestedTo.isBefore(existingFrom);

            if (!datesOverlap)
                continue;

            int existingStart;
            int existingEnd;

            try {
                existingStart = Room.convertToMinutes(s.getStartTime());
                existingEnd = Room.convertToMinutes(s.getEndTime());
            } catch (RuntimeException e) {
                continue;
            }

            if (existingStart < 0 || existingEnd < 0
                    || existingStart >= existingEnd)
                continue;

            // Time ranges overlap if either schedule starts before
            // the other one ends.
            if (requestedStart < existingEnd
                    && requestedEnd > existingStart)
                return true;
        }

        return false;
    }

    // DISPLAY METHODS
    public static void displaySchedule(String scheduleID) {

        Schedule s = findSchedule(scheduleID);

        if (s == null) {
            UI.print("Schedule not found.");
            return;
        }

        CourseOffering offering = CourseOffering.findOffering(s.getOfferingID());

        UI.header("SCHEDULE DETAILS");
        UI.liner("  SCHEDULE ID   : " + s.getScheduleID());
        UI.liner("  OFFERING ID   : " + s.getOfferingID());

        if (offering != null) {

            Section section = Section.findSection(offering.getSectionID());
            Course course = Course.findCourse(offering.getCourseCode());

            UI.liner("  SECTION         : "
                    + (section == null ? "Section not found"
                    : Section.getYearSection(section)));

            UI.liner("  COURSE          : "
                    + (course == null ? "Course not found"
                    : course.getCourseCode()));

            UI.liner("  FACULTY ID      : " + offering.getFacultyID());
            UI.liner("  ACADEMIC YEAR   : " + offering.getAcademicYear());
            UI.liner("  TERM            : " + offering.getTerm());

        } else {
            UI.liner("  Course offering not found");
        }

        UI.liner("  ROOM CODE       : " + s.getRoomCode());
        UI.liner("  DAY             : " + s.getDay());
        UI.liner("  START TIME      : " + s.getStartTime());
        UI.liner("  END TIME        : " + s.getEndTime());
        UI.liner("  EFFECTIVE FROM  : " + s.getEffectiveFrom());
        UI.liner("  EFFECTIVE TO    : " + s.getEffectiveTo());

        UI.footer();
        UI.returnButton();
    }
    public static void displaySchedules() {

        UI.header("CLASS SCHEDULES");

        if (schedules.isEmpty()) {
            UI.liner("  No schedules found.");

        } else {
            for (int i = 0; i < schedules.size(); i++) {

                Schedule s = schedules.get(i);

                if (s == null)
                    continue;

                UI.liner(String.format("  SCHEDULE ID : %s", s.getScheduleID()));
                UI.liner(String.format("  OFFERING ID : %s", s.getOfferingID()));
                UI.liner(String.format("  DAY         : %s", s.getDay()));
                UI.liner(String.format("  TIME        : %s - %s",
                        s.getStartTime(), s.getEndTime()));
                UI.liner(String.format("  ROOM        : %s", s.getRoomCode()));
                UI.liner(String.format("  EFFECTIVE   : %s - %s",
                        s.getEffectiveFrom(), s.getEffectiveTo()));

                if (i < schedules.size() - 1)
                    UI.separator();
            }
        }

        UI.footer();
        UI.returnButton();
    }
    public static void displayArchivedSchedules() {

        UI.header("ARCHIVE CLASS SCHEDULES");

        if (archiveSchedules.isEmpty()) {
            UI.liner("  No archive schedules found.");

        } else {
            for (int i = 0; i < archiveSchedules.size(); i++) {

                Schedule s = archiveSchedules.get(i);

                if (s == null)
                    continue;

                UI.liner(String.format("  SCHEDULE ID : %s", s.getScheduleID()));
                UI.liner(String.format("  OFFERING ID : %s", s.getOfferingID()));
                UI.liner(String.format("  DAY         : %s", s.getDay()));
                UI.liner(String.format("  TIME        : %s - %s",
                        s.getStartTime(), s.getEndTime()));
                UI.liner(String.format("  ROOM        : %s", s.getRoomCode()));
                UI.liner(String.format("  EFFECTIVE   : %s - %s",
                        s.getEffectiveFrom(), s.getEffectiveTo()));

                if (i < archiveSchedules.size() - 1)
                    UI.separator();
            }
        }

        UI.footer();
        returnButton();
    }

    public static void createSchedule() {
        String offeringID = "", day = "", startTime = "", endTime = "";
        String effectiveFrom = "", effectiveTo = "", roomCode = "";
        String input;
        boolean submit = false;

        while (!submit) {
            scheduleManage("CREATE", String.format("SCH%04d", (schedules.size() + 1)), offeringID, day,
                    startTime, endTime, effectiveFrom, effectiveTo, roomCode);

            do {
                UI.select("Select", false);
                input = in.nextLine().trim().toUpperCase();

                if (!input.matches("[1-7X/]")) UI.print("Invalid Choice");
            } while (!input.matches("[1-7X/]"));

            switch (input) {
                case "1":
                    do {
                        UI.select("Enter Offering ID", true);
                        input = in.nextLine().trim();

                        if (input.equalsIgnoreCase("X")) {
                            input = "";
                            offeringID = "";
                            break;
                        }
                        else if (input.isEmpty()) UI.print("Offering ID is required");
                        else if (CourseOffering.findOffering(input) == null) UI.print("Course offering does not exist");
                    } while (input.isEmpty() || CourseOffering.findOffering(input) == null);

                    offeringID = input;
                    break;

                case "2":
                    String normalizedDay;

                    do {
                        UI.select("Enter Day", true);
                        input = in.nextLine().trim();

                        normalizedDay = normalizeDay(input);

                        if (input.equalsIgnoreCase("X")) {
                            normalizedDay = "";
                            day = "";
                            break;
                        }
                        else if (input.isEmpty()) UI.print("Day is required");
                        else if (normalizedDay == null) UI.print("Invalid day. Enter a valid weekday");

                    } while (normalizedDay == null);

                    day = normalizedDay;
                    break;

                case "3":
                    boolean validStart;

                    do {
                        validStart = true;

                        UI.select("Enter Start Time (e.g. 8:30 AM)", true);
                        input = in.nextLine().trim();

                        try {
                            int start = Room.convertToMinutes(input);
                            int end = Room.convertToMinutes(endTime);

                            if (input.equalsIgnoreCase("X")) {
                                input = "";
                                startTime = "";
                                break;
                            }
                            else if (input.isEmpty()) {
                                UI.print("Start time is required");
                                validStart = false;
                            } else if (start < 0) {
                                UI.print("Invalid start time");
                                validStart = false;
                            } else if (end < 0) {
                                UI.print("Current end time is invalid");
                                validStart = false;
                            } else if (start >= end) {
                                UI.print("Start time must be earlier than end time");
                                validStart = false;
                            }

                        } catch (RuntimeException e) {
                            UI.print("Invalid start time");
                            validStart = false;
                        }

                    } while (!validStart);

                    startTime = input;
                    break;

                case "4":
                    boolean validEnd;

                    do {
                        validEnd = true;

                        UI.select("Enter End Time (e.g. 10:00 AM)", true);
                        input = in.nextLine().trim();

                        try {
                            int start = Room.convertToMinutes(startTime);
                            int end = Room.convertToMinutes(input);

                            if (input.equalsIgnoreCase("X")) {
                                input = "";
                                endTime = "";
                                break;
                            }
                            else if (input.isEmpty()) {
                                UI.print("End time is required");
                                validEnd = false;
                            } else if (end < 0) {
                                UI.print("Invalid end time");
                                validEnd = false;
                            } else if (start < 0) {
                                UI.print("Current start time is invalid");
                                validEnd = false;
                            } else if (start >= end) {
                                UI.print("End time must be later than start time");
                                validEnd = false;
                            }

                        } catch (RuntimeException e) {
                            UI.print("Invalid end time");
                            validEnd = false;
                        }

                    } while (!validEnd);

                    endTime = input;
                    break;

                case "5":
                    LocalDate from;

                    do {
                        UI.select("Enter Effective From (MM/dd/yyyy)", true);
                        input = in.nextLine().trim();

                        from = parseDate(input);

                        if (input.equalsIgnoreCase("X")) {
                            input = "";
                            effectiveFrom = "";
                            break;
                        }
                        else if (input.isEmpty())
                            UI.print("Effective start date is required");
                        else if (from == null)
                            UI.print("Invalid date. Use MM/dd/yyyy");
                        else if (parseDate(effectiveTo) != null
                                && from.isAfter(parseDate(effectiveTo)))
                            UI.print("Effective start date must not be after the end date");

                    } while (from == null || (parseDate(effectiveTo) != null && from.isAfter(parseDate(effectiveTo))));

                    effectiveFrom = input;
                    break;

                case "6":
                    LocalDate to;

                    do {
                        UI.select("Enter Effective To (MM/dd/yyyy)", false);
                        input = in.nextLine().trim();

                        to = parseDate(input);

                        if (input.equalsIgnoreCase("X")) {
                            input = "";
                            effectiveTo = "";
                            break;
                        }
                        else if (input.isEmpty())
                            UI.print("Effective end date is required");
                        else if (to == null)
                            UI.print("Invalid date. Use MM/dd/yyyy");
                        else if (parseDate(effectiveFrom) != null
                                && to.isBefore(parseDate(effectiveFrom)))
                            UI.print("Effective end date must not be before start date");

                    } while (to == null
                            || (parseDate(effectiveFrom) != null
                            && to.isBefore(parseDate(effectiveFrom))));

                    effectiveTo = input;
                    break;

                case "7":
                    do {
                        Room.displayRooms();
                        UI.select("Enter Room Code", false);
                        input = in.nextLine().trim();

                        if (input.equalsIgnoreCase("X")) {
                            roomCode = "";
                            input = "";
                            break;
                        }
                        else if (input.isEmpty()) UI.print("Room code is required");
                        else if (Room.findRoom(input) == null) UI.print("Room does not exist");
                    } while (input.isEmpty() || Room.findRoom(input) == null);

                    roomCode = input;
                    break;

                case "/":
                    if (offeringID.isEmpty() || day.isEmpty() || startTime.isEmpty() ||
                            endTime.isEmpty() || effectiveFrom.isEmpty() || effectiveTo.isEmpty() ||
                            roomCode.isEmpty()) {
                        UI.print("Details are incomplete");
                    } else if (hasRoomConflict(roomCode, day, startTime, endTime,
                            effectiveFrom, effectiveTo, null)) {
                        UI.print("Room is occupied during the selected schedule");
                    } else {
                        schedules.add(new Schedule(offeringID, day, startTime, endTime,
                                effectiveFrom, effectiveTo, roomCode));
                        UI.print("Schedule successfully created");
                        submit = true;
                    }
                    break;

                case "X":
                    submit = true;
                    break;
            }
        }
    }
    public static void editSchedule() {
        String input, choice;
        boolean submit = false;

        UI.print("EDIT EXISTING SCHEDULE");

        Schedule s;

        do {
            System.out.print("  Enter Schedule ID > ");
            input = in.nextLine().trim();

            s = Schedule.findSchedule(input);

            if (s == null)
                UI.print("Schedule does not exist");
        } while (s == null);

        String offeringID = s.getOfferingID();
        String day = s.getDay();
        String startTime = s.getStartTime();
        String endTime = s.getEndTime();
        String effectiveFrom = s.getEffectiveFrom();
        String effectiveTo = s.getEffectiveTo();
        String roomCode = s.getRoomCode();

        while (!submit) {
            scheduleManage("EDIT", s.getScheduleID(), offeringID, day,
                    startTime, endTime, effectiveFrom, effectiveTo, roomCode);

            do {
                System.out.print("  Select > ");
                choice = in.nextLine().trim().toUpperCase();

                if (!choice.matches("[1-7X/]"))
                    UI.print("Invalid Choice");
            } while (!choice.matches("[1-7X/]"));

            switch (choice) {
                case "1":
                    do {
                        UI.select("Enter Offering ID", true);
                        input = in.nextLine().trim();

                        if (input.equalsIgnoreCase("X")) {
                            offeringID = s.getOfferingID();
                            break;
                        }
                        else if (input.isEmpty()) UI.print("Offering ID is required");
                        else if (CourseOffering.findOffering(input) == null) UI.print("Course offering does not exist");
                    } while (input.isEmpty() || CourseOffering.findOffering(input) == null);

                    offeringID = input;
                    break;

                case "2":
                    String normalizedDay;

                    do {
                        UI.select("Enter Day", false);
                        input = in.nextLine().trim();

                        normalizedDay = normalizeDay(input);

                        if (input.equalsIgnoreCase("X")) {
                            day = s.getDay();
                            break;
                        }
                        else if (input.isEmpty()) UI.print("Day is required");
                        else if (normalizedDay == null) UI.print("Invalid day. Enter a valid weekday");

                    } while (normalizedDay == null);

                    day = normalizedDay;
                    break;

                case "3":
                    boolean validStart;

                    do {
                        validStart = true;

                        UI.select("Enter Start Time (e.g. 8:30 AM)", true);
                        input = in.nextLine().trim();

                        try {
                            int start = Room.convertToMinutes(input);
                            int end = Room.convertToMinutes(endTime);

                            if (input.equalsIgnoreCase("X")) {
                                startTime = s.getStartTime();
                                break;
                            }
                            else if (input.isEmpty()) {
                                UI.print("Start time is required");
                                validStart = false;
                            } else if (start < 0) {
                                UI.print("Invalid start time");
                                validStart = false;
                            } else if (end < 0) {
                                UI.print("Current end time is invalid");
                                validStart = false;
                            } else if (start >= end) {
                                UI.print("Start time must be earlier than end time");
                                validStart = false;
                            }

                        } catch (RuntimeException e) {
                            UI.print("Invalid start time");
                            validStart = false;
                        }

                    } while (!validStart);

                    startTime = input;
                    break;

                case "4":
                    boolean validEnd;

                    do {
                        validEnd = true;

                        UI.select("Enter End Time (e.g. 10:00 AM)", true);
                        input = in.nextLine().trim();

                        try {
                            int start = Room.convertToMinutes(startTime);
                            int end = Room.convertToMinutes(input);

                            if (input.equalsIgnoreCase("X")) {
                                endTime = s.getEndTime();
                                break;
                            }
                            else if (input.isEmpty()) {
                                UI.print("End time is required");
                                validEnd = false;
                            } else if (end < 0) {
                                UI.print("Invalid end time");
                                validEnd = false;
                            } else if (start < 0) {
                                UI.print("Current start time is invalid");
                                validEnd = false;
                            } else if (start >= end) {
                                UI.print("End time must be later than start time");
                                validEnd = false;
                            }

                        } catch (RuntimeException e) {
                            UI.print("Invalid end time");
                            validEnd = false;
                        }

                    } while (!validEnd);

                    endTime = input;
                    break;

                case "5":
                    LocalDate from;

                    do {
                        UI.select("Enter Effective From (MM/dd/yyyy)", true);
                        input = in.nextLine().trim();

                        from = parseDate(input);

                        if (input.equalsIgnoreCase("X")) {
                            effectiveFrom = s.getEffectiveFrom();
                            break;
                        }
                        else if (input.isEmpty())
                            UI.print("Effective start date is required");
                        else if (from == null)
                            UI.print("Invalid date. Use MM/dd/yyyy");
                        else if (parseDate(effectiveTo) != null
                                && from.isAfter(parseDate(effectiveTo)))
                            UI.print("Effective start date must not be after the end date");

                    } while (from == null || (parseDate(effectiveTo) != null && from.isAfter(parseDate(effectiveTo))));

                    effectiveFrom = input;
                    break;

                case "6":
                    LocalDate to;

                    do {
                        UI.select("Enter Effective To (MM/dd/yyyy)", true);
                        input = in.nextLine().trim();

                        to = parseDate(input);

                        if (input.equalsIgnoreCase("X")) {
                            effectiveTo = s.getEffectiveTo();
                            break;
                        }
                        else if (input.isEmpty())
                            UI.print("Effective end date is required");
                        else if (to == null)
                            UI.print("Invalid date. Use MM/dd/yyyy");
                        else if (parseDate(effectiveFrom) != null
                                && to.isBefore(parseDate(effectiveFrom)))
                            UI.print("Effective end date must not be before start date");

                    } while (to == null
                            || (parseDate(effectiveFrom) != null
                            && to.isBefore(parseDate(effectiveFrom))));

                    effectiveTo = input;
                    break;

                case "7":
                    do {
                        Room.displayRooms();
                        UI.select("Enter Room Code", true);
                        input = in.nextLine().trim();

                        if (input.equalsIgnoreCase("X")) {
                            roomCode = s.getRoomCode();
                            break;
                        }
                        else if (input.isEmpty())
                            UI.print("Room code is required");
                        else if (Room.findRoom(input) == null)
                            UI.print("Room does not exist");

                    } while (input.isEmpty() || Room.findRoom(input) == null);

                    roomCode = input;
                    break;

                case "/":
                    if (hasRoomConflict(roomCode, day, startTime, endTime,
                            effectiveFrom, effectiveTo, s.getScheduleID())) {
                        UI.print("Room is occupied during the selected schedule");
                    } else {
                        s.offeringID = offeringID.trim(); s.day = day; s.startTime = startTime.trim();
                        s.endTime = endTime.trim(); s.effectiveFrom = effectiveFrom.trim();
                        s.effectiveTo = effectiveTo.trim(); s.roomCode = roomCode.trim();

                        UI.print("Schedule successfully edited");
                        submit = true;
                    }
                    break;

                case "X":
                    submit = true;
                    break;
            }
        }
    }
    public static void archiveSchedule() {
        String input, choice;

        UI.print("DELETE EXISTING SCHEDULE");

        do {
            UI.select("Enter Schedule ID", false);
            input = in.nextLine().trim();
            if (input.equalsIgnoreCase("X")) return;
            else if (input.isEmpty()) UI.print("Schedule ID is required");
            else if (findSchedule(input) == null) UI.print("Schedule does not exist");
        } while (findSchedule(input) == null);

        Schedule s = findSchedule(input);

        scheduleManage("DELETE", s.getScheduleID(), s.getOfferingID(),
                s.getDay(), s.getStartTime(), s.getEndTime(),
                s.getEffectiveFrom(), s.getEffectiveTo(), s.getRoomCode());

        do {
            System.out.print("  Confirm Schedule Deletion? (Y/N) > ");
            choice = in.nextLine().trim().toUpperCase();

            if (!choice.matches("[YN]"))
                UI.print("Invalid Input");
        } while (!choice.matches("[YN]"));

        if (choice.equals("Y")) {
            archiveSchedules.add(s);
            schedules.remove(s);
            UI.print("Schedule successfully archived");
        }
        else UI.print("Archiving cancelled");
    }
    private static void scheduleManage(String text, String scheduleID,
                                       String offeringID, String day, String startTime, String endTime,
                                       String effectiveFrom, String effectiveTo, String roomCode) {

        UI.header(text.toUpperCase() + ": SCHEDULE INFORMATION");
        UI.liner("     SCHEDULE ID        : " + scheduleID);
        UI.liner(" [1] OFFERING ID        : " + offeringID);
        UI.liner(" [2] DAY                : " + day);
        UI.liner(" [3] START TIME         : " + startTime);
        UI.liner(" [4] END TIME           : " + endTime);
        UI.liner(" [5] EFFECTIVE FROM     : " + effectiveFrom);
        UI.liner(" [6] EFFECTIVE TO       : " + effectiveTo);
        UI.liner(" [7] ROOM CODE          : " + roomCode);
        UI.liner("");
        UI.option();
        UI.footer();
    }
}