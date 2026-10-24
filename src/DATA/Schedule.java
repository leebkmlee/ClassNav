
package DATA;

import UI.*;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Locale;

import static UI.UI.in;

public class Schedule {

    private static int nextScheduleID = 1;

    private String scheduleID;
    private String offeringID;
    private String day;
    private String startTime;
    private String endTime;
    private String effectiveFrom;
    private String effectiveTo;
    private String roomCode;

    public static ArrayList<Schedule> schedules = new ArrayList<>();

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("MM/dd/uuuu")
                    .withResolverStyle(ResolverStyle.STRICT);

    // SCHEDULE CONSTRUCTOR

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
    }

    // VALIDATION HELPERS

    private static String normalizeDay(String day) {

        if (day == null)
            return null;

        switch (day.trim().toLowerCase(Locale.ROOT)) {
            case "monday":
                return "Monday";
            case "tuesday":
                return "Tuesday";
            case "wednesday":
                return "Wednesday";
            case "thursday":
                return "Thursday";
            case "friday":
                return "Friday";
            case "saturday":
                return "Saturday";
            case "sunday":
                return "Sunday";
            default:
                return null;
        }
    }

    private static LocalDate parseDate(String date) {

        if (date == null || date.trim().isEmpty())
            return null;

        try {
            return LocalDate.parse(date.trim(), DATE_FORMAT);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    private static boolean hasRoomConflict(String roomCode, String day,
                                           String startTime, String endTime,
                                           String effectiveFrom, String effectiveTo,
                                           String excludedScheduleID) {

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

    // STATIC GETTERS

    public static String getSchedule(Schedule s) {
        return s == null ? null : s.getScheduleID();
    }

    public static String getOfferingID(Schedule s) {
        return s == null ? null : s.getOfferingID();
    }

    public static String getDay(Schedule s) {
        return s == null ? null : s.getDay();
    }

    public static String getStartTime(Schedule s) {
        return s == null ? null : s.getStartTime();
    }

    public static String getEndTime(Schedule s) {
        return s == null ? null : s.getEndTime();
    }

    public static String getEffectiveFrom(Schedule s) {
        return s == null ? null : s.getEffectiveFrom();
    }

    public static String getEffectiveTo(Schedule s) {
        return s == null ? null : s.getEffectiveTo();
    }

    public static String getRoomCode(Schedule s) {
        return s == null ? null : s.getRoomCode();
    }

    public static int getNextID() {
        return nextScheduleID;
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

    public static boolean existScheduleID(String scheduleID) {
        return findSchedule(scheduleID) != null;
    }

    // ADD A SCHEDULE

    public static boolean addSchedule(String offeringID, String day,
                                      String startTime, String endTime,
                                      String effectiveFrom, String effectiveTo,
                                      String roomCode) {

        try {
            Schedule s = new Schedule(offeringID, day, startTime, endTime,
                    effectiveFrom, effectiveTo, roomCode);

            schedules.add(s);
            return true;

        } catch (IllegalArgumentException e) {
            UI.print(e.getMessage());
            return false;
        }
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

    // DISPLAY ALL SCHEDULES

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
    }

    // DISPLAY ONE SCHEDULE WITH COURSE OFFERING DETAILS

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

            UI.liner("  SECTION       : "
                    + (section == null ? "Section not found"
                    : Section.getYearSection(section)));

            UI.liner("  COURSE        : "
                    + (course == null ? "Course not found"
                    : course.getCourseCode()));

            UI.liner("  FACULTY ID    : " + offering.getFacultyID());
            UI.liner("  ACADEMIC YEAR : " + offering.getAcademicYear());
            UI.liner("  TERM          : " + offering.getTerm());

        } else {
            UI.liner("  Course offering not found");
        }

        UI.liner("  ROOM CODE     : " + s.getRoomCode());
        UI.liner("  DAY           : " + s.getDay());
        UI.liner("  START TIME    : " + s.getStartTime());
        UI.liner("  END TIME      : " + s.getEndTime());
        UI.liner("  EFFECTIVE FROM: " + s.getEffectiveFrom());
        UI.liner("  EFFECTIVE TO  : " + s.getEffectiveTo());

        UI.footer();
    }

    public boolean updateSchedule(String offeringID, String day, String startTime, String endTime,
                                  String effectiveFrom, String effectiveTo, String roomCode) {
        try {
            if (offeringID == null || offeringID.trim().isEmpty())
                throw new IllegalArgumentException("Course offering ID is required.");

            if (CourseOffering.findOffering(offeringID) == null)
                throw new IllegalArgumentException("Course offering not found.");

            if (roomCode == null || roomCode.trim().isEmpty()
                    || Room.findRoom(roomCode.trim()) == null)
                throw new IllegalArgumentException("Room not found.");

            if (day == null || day.trim().isEmpty())
                throw new IllegalArgumentException("Day is required.");

            if (startTime == null || startTime.trim().isEmpty()
                    || endTime == null || endTime.trim().isEmpty())
                throw new IllegalArgumentException("Start and end times are required.");

            if (effectiveFrom == null || effectiveFrom.trim().isEmpty()
                    || effectiveTo == null || effectiveTo.trim().isEmpty())
                throw new IllegalArgumentException("Effective dates are required.");

            day = normalizeDay(day);
            offeringID = offeringID.trim();
            roomCode = roomCode.trim();
            startTime = startTime.trim();
            endTime = endTime.trim();
            effectiveFrom = effectiveFrom.trim();
            effectiveTo = effectiveTo.trim();

            int start = Room.convertToMinutes(startTime);
            int end = Room.convertToMinutes(endTime);

            if (start >= end)
                throw new IllegalArgumentException("End time must be later than start time.");

            LocalDate from = parseDate(effectiveFrom);
            LocalDate to = parseDate(effectiveTo);

            if (from.isAfter(to))
                throw new IllegalArgumentException("Effective end date must not be before start date.");

            if (hasRoomConflict(roomCode, day, startTime, endTime,
                    effectiveFrom, effectiveTo, this.scheduleID))
                throw new IllegalArgumentException("Room is occupied during the selected schedule.");

            this.offeringID = offeringID;
            this.day = day;
            this.startTime = startTime;
            this.endTime = endTime;
            this.effectiveFrom = effectiveFrom;
            this.effectiveTo = effectiveTo;
            this.roomCode = roomCode;

            return true;

        } catch (IllegalArgumentException e) {
            UI.print(e.getMessage());
            return false;
        }
    }

    public static void createSchedule() {
        String offeringID = "", day = "", startTime = "", endTime = "";
        String effectiveFrom = "", effectiveTo = "", roomCode = "";
        String choice;
        boolean submit = false;

        while (!submit) {
            scheduleManage("EDIT", String.format("SCH%04d", (schedules.size() + 1)), offeringID, day,
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
                        System.out.print("   Enter Offering ID > ");
                        offeringID = in.nextLine().trim();
                        if (CourseOffering.findOffering(offeringID) == null) {
                            UI.print("Course offering does not exist");
                            offeringID = "";
                        }
                    } while (CourseOffering.findOffering(offeringID) == null);
                    break;

                case "2":
                    System.out.print("   Enter Day > ");
                    day = in.nextLine().trim();
                    break;

                case "3":
                    System.out.print("   Enter Start Time (e.g. 8:30 AM) > ");
                    startTime = in.nextLine().trim();
                    break;

                case "4":
                    System.out.print("   Enter End Time (e.g. 10:00 AM) > ");
                    endTime = in.nextLine().trim();
                    break;

                case "5":
                    System.out.print("   Enter Effective From (MM/dd/yyyy) > ");
                    effectiveFrom = in.nextLine().trim();
                    break;

                case "6":
                    System.out.print("   Enter Effective To (MM/dd/yyyy) > ");
                    effectiveTo = in.nextLine().trim();
                    break;

                case "7":
                    System.out.print("   Enter Room Code > ");
                    roomCode = in.nextLine().trim();

                    if (Room.findRoom(roomCode) == null) {
                        UI.print("Room does not exist");
                        roomCode = "";
                    }
                    break;

                case "/":
                    if (offeringID.isEmpty() || day.isEmpty()
                            || startTime.isEmpty() || endTime.isEmpty()
                            || effectiveFrom.isEmpty() || effectiveTo.isEmpty()
                            || roomCode.isEmpty()) {
                        UI.print("Details are incomplete");
                        break;
                    }

                    try {
                        Schedule s = new Schedule(offeringID, day, startTime, endTime,
                                effectiveFrom, effectiveTo, roomCode);

                        schedules.add(s);
                        UI.print("Schedule successfully created");
                        Schedule.displaySchedule(s.getScheduleID());
                        submit = true;

                    } catch (IllegalArgumentException e) {
                        UI.print(e.getMessage());
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
                    CourseOffering.displayOfferings();
                    System.out.print("   Enter Offering ID > ");
                    input = in.nextLine().trim();

                    if (CourseOffering.findOffering(input) != null)
                        offeringID = input;
                    else
                        UI.print("Course offering does not exist");
                    break;

                case "2":
                    System.out.print("   Enter Day > ");
                    day = in.nextLine().trim();
                    break;

                case "3":
                    System.out.print("   Enter Start Time > ");
                    startTime = in.nextLine().trim();
                    break;

                case "4":
                    System.out.print("   Enter End Time > ");
                    endTime = in.nextLine().trim();
                    break;

                case "5":
                    System.out.print("   Enter Effective From (MM/dd/yyyy) > ");
                    effectiveFrom = in.nextLine().trim();
                    break;

                case "6":
                    System.out.print("   Enter Effective To (MM/dd/yyyy) > ");
                    effectiveTo = in.nextLine().trim();
                    break;

                case "7":
                    System.out.print("   Enter Room Code > ");
                    input = in.nextLine().trim();

                    if (Room.findRoom(input) != null)
                        roomCode = input;
                    else
                        UI.print("Room does not exist");
                    break;

                case "/":
                    if (s.updateSchedule(offeringID, day, startTime, endTime,
                            effectiveFrom, effectiveTo, roomCode)) {
                        UI.print("Schedule successfully edited");
                        Schedule.displaySchedule(s.getScheduleID());
                        submit = true;
                    }
                    break;

                case "X":
                    submit = true;
                    break;
            }
        }
    }
    public static void deleteSchedule() {
        String input, choice;

        UI.print("DELETE EXISTING SCHEDULE");

        Schedule s;

        do {
            System.out.print("  Enter Schedule ID > ");
            input = in.nextLine().trim();

            s = Schedule.findSchedule(input);

            if (s == null)
                UI.print("Schedule does not exist");
        } while (s == null);

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
            schedules.remove(s);
            UI.print("Schedule successfully deleted");
        }
    }
    private static void scheduleManage(String text, String scheduleID,
                                       String offeringID, String day, String startTime, String endTime,
                                       String effectiveFrom, String effectiveTo, String roomCode) {

        UI.header(text.toUpperCase() + ": SCHEDULE INFORMATION");
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