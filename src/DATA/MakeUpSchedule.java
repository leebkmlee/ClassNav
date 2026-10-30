package DATA;

import UI.*;
import static UI.UI.*;
import USER.*;

import java.util.*;
import java.time.*;
import java.time.format.*;

public class MakeUpSchedule {

    // ROOM REQUEST FIELDS
    private static int nextMakeUpID = 1;

    private final String makeupID, facultyID, offeringID, requestID, roomCode, requestType, requestedDate,
            startTime, endTime, reason, status, reviewedBy, reviewedAt;
    private final boolean valid;

    public static ArrayList<MakeUpSchedule> makeUpSchedules = new ArrayList<>();
    public static ArrayList<MakeUpSchedule> archiveMakeUpSchedules = new ArrayList<>();

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("MM/dd/uuuu")
                    .withResolverStyle(ResolverStyle.STRICT);

    // ROOM REQUEST CONSTRUCTOR
    public MakeUpSchedule(String facultyID, String roomCode, String requestType,
                          String requestedDate, String startTime, String endTime,
                          String reason, String reviewedBy, String reviewedAt) {
        this(facultyID, roomCode, requestType, requestedDate, startTime, endTime,
                reason, reviewedBy, reviewedAt, "", "");
    }

    public MakeUpSchedule(String facultyID, String roomCode, String requestType,
                          String requestedDate, String startTime, String endTime,
                          String reason, String reviewedBy, String reviewedAt,
                          String offeringID, String requestID) {
        Faculty faculty = Faculty.findFaculty(facultyID);
        CourseOffering offering = offeringID == null || offeringID.trim().isEmpty()
                ? null : CourseOffering.findOffering(offeringID);
        Room room = Room.findRoom(roomCode);
        LocalDate date = null;
        String convertedStart = UI.convertTime(startTime);
        String convertedEnd = UI.convertTime(endTime);

        try {
            if (requestedDate != null && !requestedDate.trim().isEmpty())
                date = LocalDate.parse(requestedDate.trim(), DATE_FORMAT);
        } catch (DateTimeParseException e) {
            UI.print("Invalid make-up schedule date. Use MM/dd/yyyy.");
        }

        boolean offeringValid = offeringID == null || offeringID.trim().isEmpty()
                || (offering != null && offering.getFacultyID() != null
                && offering.getFacultyID().equalsIgnoreCase(facultyID)
                && Course.findCourse(offering.getCourseCode()) != null
                && Section.findSection(offering.getSectionID()) != null);
        boolean detailsValid = faculty != null && room != null && date != null && offeringValid
                && requestType != null && !requestType.trim().isEmpty()
                && reason != null && !reason.trim().isEmpty()
                && !convertedStart.isEmpty() && !convertedEnd.isEmpty();
        if (detailsValid) {
            try {
                detailsValid = LocalTime.parse(convertedStart, TIME_FORMAT)
                        .isBefore(LocalTime.parse(convertedEnd, TIME_FORMAT));
            } catch (DateTimeParseException e) {
                detailsValid = false;
            }
        }

        this.makeupID = String.format("MS%04d", nextMakeUpID++);
        this.facultyID = faculty == null ? safeText(facultyID) : faculty.getUserID();
        this.offeringID = offering == null ? "" : offering.getOfferingID();
        this.requestID = safeNullableText(requestID);
        this.roomCode = room == null ? safeText(roomCode) : room.getRoomCode();
        this.requestType = safeText(requestType);
        this.requestedDate = date == null ? "" : date.format(DATE_FORMAT);
        this.startTime = convertedStart == null ? "" : convertedStart;
        this.endTime = convertedEnd == null ? "" : convertedEnd;
        this.reason = safeText(reason);
        this.reviewedBy = safeNullableText(reviewedBy);
        this.reviewedAt = safeNullableText(reviewedAt);
        this.valid = detailsValid;
        this.status = valid ? "Approved" : "Invalid";
        if (!valid) UI.print("Make-up schedule was not created because one or more details are invalid.");
    }

    private static String safeText(String value) {
        return value == null ? "" : value.trim();
    }

    private static String safeNullableText(String value) {
        return value == null || value.trim().isEmpty() ? null : value.trim();
    }

    public boolean isValid() {
        return valid;
    }

    // SAMPLE ROOM REQUESTS
    static {
        makeUpSchedules.add(new MakeUpSchedule("F0001", "RM001", "Reservation",
                "10/26/2026", "8:00 AM", "9:00 AM",
                "Additional laboratory session", null, null));
        makeUpSchedules.add(new MakeUpSchedule("F0002", "RM004", "Room Change",
                "10/27/2026", "10:00 AM", "11:00 AM",
                "Original room unavailable", null, null));
        makeUpSchedules.add(new MakeUpSchedule("F0003", "RM003",
                "Make-up Class",
                "10/28/2026", "1:00 PM", "2:00 PM",
                "Replacement lecture session", null, null));
        makeUpSchedules.add(new MakeUpSchedule("F0004", "RM005",
                "Reservation",
                "10/29/2026", "2:00 PM", "3:00 PM",
                "Additional review class", null, null));
        makeUpSchedules.add(new MakeUpSchedule("F0006", "RM008", "Make-up Class",
                "11/02/2026", "10:00 AM", "11:00 AM",
                "Missed laboratory session", null, null));
        makeUpSchedules.add(new MakeUpSchedule("F0007", "RM002",
                "Reservation",
                "11/03/2026", "1:00 PM", "2:00 PM",
                "Additional computer laboratory use", null, null));
        makeUpSchedules.add(new MakeUpSchedule("F0010", "RM010",
                "Reservation",
                "11/06/2026", "10:00 AM", "11:00 AM",
                "Additional science laboratory session", null, null));
        makeUpSchedules.add(new MakeUpSchedule("F0011", "RM004",
                "Room Change",
                "11/09/2026", "12:00 PM", "1:00 PM",
                "Scheduling conflict in original room", null, null));
        makeUpSchedules.add(new MakeUpSchedule("F0013", "RM013",
                "Reservation",
                "11/11/2026", "8:00 AM", "9:00 AM",
                "Additional biology laboratory session", null, null));
        makeUpSchedules.add(new MakeUpSchedule("F0014", "RM005",
                "Room Change",
                "11/12/2026", "9:00 AM", "10:00 AM",
                "Room needed for a larger class", null, null));

        makeUpSchedules.add(new MakeUpSchedule("F0015","RM006","Reservation","11/13/2026","8:00 AM","9:00 AM","Additional lecture session",null,null));
        makeUpSchedules.add(new MakeUpSchedule("F0012","RM007","Room Change","11/16/2026","9:00 AM","10:00 AM","Original room unavailable",null,null));
        makeUpSchedules.add(new MakeUpSchedule("F0013","RM009","Make-up Class","11/17/2026","10:00 AM","11:00 AM","Replacement laboratory session",null,null));
        makeUpSchedules.add(new MakeUpSchedule("F0014","RM012","Reservation","11/18/2026","1:00 PM","2:00 PM","Additional review class",null,null));
        makeUpSchedules.add(new MakeUpSchedule("F0009","RM014","Room Change","11/19/2026","2:00 PM","3:00 PM","Scheduling conflict in original room",null,null));
    }

    // GETTER METHODS
    public String getMakeUpID() { return makeupID; }
    public String getFacultyID() { return facultyID; }
    public String getOfferingID() { return offeringID; }
    public String getRequestID() { return requestID; }
    public String getRoomCode() { return roomCode; }
    public String getRequestType() { return requestType; }
    public String getRequestedDate() { return requestedDate; }
    public String getStartTime() { return startTime; }
    public String getEndTime() { return endTime; }
    public String getReason() { return reason; }
    public String getStatus() { return status; }
    public String getReviewedBy() { return reviewedBy; }
    public String getReviewedAt() { return reviewedAt; }

    // FIND METHOD
    public static MakeUpSchedule findMakeUpID(String makeupID) {

        if (makeupID == null || makeupID.trim().isEmpty())
            return null;

        for (MakeUpSchedule request : makeUpSchedules) {
            if (makeupID.trim().equalsIgnoreCase(request.makeupID))
                return request;
        }

        return null;
    }

    // RETRIEVE REQUESTS BY FACULTY FOREIGN KEY
    public static ArrayList<MakeUpSchedule> getRequestsByFaculty(String facultyID) {

        ArrayList<MakeUpSchedule> result = new ArrayList<>();

        if (facultyID == null || facultyID.trim().isEmpty())
            return result;

        for (MakeUpSchedule request : makeUpSchedules) {
            if (facultyID.trim().equalsIgnoreCase(request.facultyID))
                result.add(request);
        }

        return result;
    }

    // RETRIEVE REQUESTS BY STATUS
    public static ArrayList<MakeUpSchedule> getRequestsByStatus(String status) {

        ArrayList<MakeUpSchedule> result = new ArrayList<>();

        if (status == null || status.trim().isEmpty())
            return result;

        for (MakeUpSchedule request : makeUpSchedules) {
            if (status.trim().equalsIgnoreCase(request.status))
                result.add(request);
        }

        return result;
    }

    // DISPLAY METHODS
    public static void displayMakeUpSchedule(String requestID) {

        MakeUpSchedule request = findMakeUpID(requestID);

        if (request == null) {
            UI.print("Request not found.");
            return;
        }

        UI.header("ROOM REQUEST DETAILS", false);
        UI.liner("  REQUEST ID    : " + request.getMakeUpID(), false);
        UI.liner("  FACULTY ID    : " + request.getFacultyID(), false);
        UI.liner("  ROOM CODE     : " + request.getRoomCode(), false);
        UI.liner("  REQUEST TYPE  : " + request.getRequestType(), false);
        UI.liner("  DATE          : " + request.getRequestedDate(), false);
        UI.liner("  START TIME    : " + request.getStartTime(), false);
        UI.liner("  END TIME      : " + request.getEndTime(), false);
        UI.liner("  REASON        : " + request.getReason(), false);
        UI.liner("  STATUS        : " + request.getStatus(), false);
        UI.liner("  REVIEWED BY   : " + (request.getReviewedBy() == null ? "N/A" : request.getReviewedBy()), false);
        UI.liner("  REVIEWED AT   : " + (request.getReviewedAt() == null ? "N/A" : request.getReviewedAt()), false);
        UI.footer(false);
        UI.returnButton();
    }
    public static void displayMakeUpSchedules() {
        UI.header("MAKE-UP SCHEDULES", true);
        if (makeUpSchedules.isEmpty()) UI.liner("  No make-up schedules found.", true);
        else {
            String format = "  %-10s %-10s %-12s %-10s %-10s %-16s %-8s %-10s %-18s %-10s %-54s";
            UI.liner(String.format(format, "MAKE-UP ID", "REQUEST ID", "OFFERING ID", "FACULTY ID", "COURSE",
                    "SECTION", "ROOM", "DATE", "TIME", "STATUS", "REASON"), true);
            UI.separator(true);
            int count = 0;
            for (MakeUpSchedule schedule : makeUpSchedules) {
                if (schedule == null) continue;
                CourseOffering offering = CourseOffering.findOffering(schedule.getOfferingID());
                Course course = offering == null ? null : Course.findCourse(offering.getCourseCode());
                Section section = offering == null ? null : Section.findSection(offering.getSectionID());
                UI.liner(String.format(format, schedule.getMakeUpID(),
                        UI.fitText(schedule.getRequestID() == null ? "-" : schedule.getRequestID(), 12),
                        UI.fitText(schedule.getOfferingID(), 14), schedule.getFacultyID(),
                        UI.fitText(course == null ? "-" : course.getCourseCode(), 14),
                        UI.fitText(section == null ? "-" : Section.getYearSection(section), 18),
                        schedule.getRoomCode(), schedule.getRequestedDate(),
                        UI.fitText(UI.formatTime(schedule.getStartTime()) + " - " + UI.formatTime(schedule.getEndTime()), 20),
                        schedule.getStatus(), schedule.getReason()), true);
                count++;
            }
            UI.separator(true);
            UI.liner("  TOTAL MAKE-UP SCHEDULES: " + count, true);
        }
        UI.footer(true);
        UI.returnButton();
    }
    public static void displayArchiveMakeUpSchedules() {
        UI.header("ARCHIVED MAKE-UP SCHEDULES", true);
        if (archiveMakeUpSchedules.isEmpty()) UI.liner("  No archived make-up schedules found.", true);
        else {
            String format = "  %-10s %-10s %-12s %-10s %-10s %-16s %-8s %-10s %-18s %-10s %-54s";
            UI.liner(String.format(format, "MAKE-UP ID", "REQUEST ID", "OFFERING ID", "FACULTY ID", "COURSE",
                    "SECTION", "ROOM", "DATE", "TIME", "STATUS", "REASON"), true);
            UI.separator(true);
            int count = 0;
            for (MakeUpSchedule schedule : archiveMakeUpSchedules) {
                if (schedule == null) continue;
                CourseOffering offering = CourseOffering.findOffering(schedule.getOfferingID());
                Course course = offering == null ? null : Course.findCourse(offering.getCourseCode());
                Section section = offering == null ? null : Section.findSection(offering.getSectionID());
                UI.liner(String.format(format, schedule.getMakeUpID(),
                        UI.fitText(schedule.getRequestID() == null ? "-" : schedule.getRequestID(), 12),
                        UI.fitText(schedule.getOfferingID(), 14), schedule.getFacultyID(),
                        UI.fitText(course == null ? "-" : course.getCourseCode(), 14),
                        UI.fitText(section == null ? "-" : Section.getYearSection(section), 18),
                        schedule.getRoomCode(), schedule.getRequestedDate(),
                        UI.fitText(UI.formatTime(schedule.getStartTime()) + " - " + UI.formatTime(schedule.getEndTime()), 20),
                        schedule.getStatus(), schedule.getReason()), true);
                count++;
            }
            UI.separator(true);
            UI.liner("  TOTAL ARCHIVED MAKE-UP SCHEDULES: " + count, true);
        }
        UI.footer(true);
        UI.returnButton();
    }

    // CREATE ROOM REQUEST
    public static void createMakeUpSchedule() {

        String facultyID = "", roomCode = "", requestType = "", requestedDate = "", startTime = "", endTime = "",
                reason = "", choice;
        boolean submit = false;

        while (!submit) {

            makeUpScheduleManage("CREATE", "", facultyID, roomCode, requestType, requestedDate,
                    startTime, endTime, reason, "Pending");

            do {
                UI.select("Select", false);
                choice = in.nextLine().trim().toUpperCase();
                if (choice.equalsIgnoreCase("X")) return;
                else if (!choice.matches("[1-7X]") && !choice.equals("/")) UI.print("Invalid choice.");
            } while (!choice.matches("[1-7X]") && !choice.equals("/"));

            switch (choice) {
                case "1":
                    do {
                        UI.select("Enter Faculty ID", true);
                        facultyID = in.nextLine().trim().toUpperCase();

                        if (facultyID.equalsIgnoreCase("X")) {
                            facultyID = "";
                            break;
                        }
                        else if (facultyID.isEmpty()) UI.print("Faculty ID is required");
                        else if (Faculty.findFaculty(facultyID) == null) UI.print("Faculty does not exist.");
                    } while (Faculty.findFaculty(facultyID) == null || facultyID.isEmpty());
                    break;

                case "2":
                    do {
                        UI.select("Enter Room Code", true);
                        roomCode = in.nextLine().trim().toUpperCase();

                        if (roomCode.equalsIgnoreCase("X")) {
                            roomCode = "";
                            break;
                        }
                        else if (roomCode.isEmpty()) UI.print("Room Code is required");
                        else if (Room.findRoom(roomCode) == null) UI.print("Room does not exist.");
                    } while (!roomCode.isEmpty() && Room.findRoom(roomCode) == null);
                    break;

                case "3":
                    do {
                        UI.select("Enter Request Type (Makeup/Event/Exam/Meeting)", true);
                        requestType = in.nextLine().trim().toUpperCase();

                        if (requestType.equalsIgnoreCase("X")) {
                            requestType = "";
                            break;
                        }
                        else if (!requestType.matches("MAKEUP|EVENT|EXAM|MEETING"))
                            UI.print("Invalid request type");
                    } while (!requestType.matches("MAKEUP|EVENT|EXAM|MEETING"));
                    break;

                case "4":
                    do {
                        UI.select("Enter Requested Date (MM/DD/YYYY)", true);
                        requestedDate = in.nextLine().trim();

                        if (requestedDate.equalsIgnoreCase("X")) {
                            requestedDate = "";
                            break;
                        }
                        else if (requestedDate.isEmpty()) UI.print("Requested Date is required");
                        else {
                            try {
                                LocalDate date = LocalDate.parse(requestedDate, DATE_FORMAT);

                                if (date.isBefore(LocalDate.now())) {
                                    UI.print("Date cannot be in the past.");
                                    requestedDate = "";
                                }
                            } catch (DateTimeParseException e) {
                                UI.print("Invalid date. Use MM/DD/YYYY format.");
                                requestedDate = "";
                            }
                        }
                    } while (requestedDate.isEmpty());
                    break;

                case "5":
                    do {
                        UI.select("Enter Start Time (HH:mm)", true);
                        startTime = in.nextLine().trim();

                        if (startTime.equalsIgnoreCase("X")) {
                            startTime = "";
                            break;
                        }
                        else if (startTime.isEmpty()) UI.print("Start Time is required");
                        else {
                            try {
                                Room.convertToMinutes(startTime);
                            } catch (IllegalArgumentException e) {
                                UI.print("Invalid time format.");
                                startTime = "";
                            }
                        }
                    } while (startTime.isEmpty());
                    break;

                case "6":
                    do {
                        UI.select("Enter End Time (HH:mm)", true);
                        endTime = in.nextLine().trim();

                        if (endTime.equalsIgnoreCase("X")) {
                            endTime = "";
                            break;
                        }
                        else if (endTime.isEmpty()) UI.print("End Time is required");
                        else {
                            try {
                                if (Room.convertToMinutes(endTime) <= Room.convertToMinutes(startTime)) {
                                    UI.print("End time must be after start time.");
                                    endTime = "";
                                }
                            } catch (IllegalArgumentException e) {
                                UI.print("Invalid time format.");
                                endTime = "";
                            }
                        }
                    } while (endTime.isEmpty());
                    break;

                case "7":
                    do {
                        UI.select("Enter Reason", true);
                        reason = in.nextLine().trim();

                        if (reason.equalsIgnoreCase("X")) {
                            reason = "";
                            break;
                        }
                        else if (reason.isEmpty()) UI.print("Reason is required.");
                    } while (reason.isEmpty());
                    break;

                case "/":
                    if (facultyID.isEmpty() || requestType.isEmpty() || requestedDate.isEmpty() || startTime.isEmpty()
                            || endTime.isEmpty() || reason.isEmpty()) {
                        UI.print("Details are incomplete.");
                        break;
                    }

                    MakeUpSchedule request = new MakeUpSchedule(facultyID, roomCode, requestType, requestedDate,
                            startTime, endTime, reason, null, null);

                    makeUpSchedules.add(request);

                    UI.print("Room request successfully created.");
                    displayMakeUpSchedule(request.getMakeUpID());
                    submit = true;
                    break;

                case "X":
                    submit = true;
                    break;
            }
        }
    }
    public static void archiveMakeUpSchedule() {

        UI.print("DELETE ROOM REQUEST");

        String input;

        do {
            UI.select("Enter Request ID", false);
            input = in.nextLine().trim().toUpperCase();
            if (input.equalsIgnoreCase("X")) return;
            else if (input.isEmpty()) UI.print("Request ID is required");
            else if (findMakeUpID(input) == null) UI.print("Request ID does not exist");
        } while (input.isEmpty() || findMakeUpID(input) == null);

        MakeUpSchedule r = findMakeUpID(input);
        if (r == null) {
            UI.print("Request does not exist.");
            return;
        }

        displayMakeUpSchedule(r.getMakeUpID());

        String choice;

        do {
            UI.select("Confirm Request Deletion? (Y/N)", false);
            choice = in.nextLine().trim().toUpperCase();
            if (choice.equals("X")) return;

            if (!choice.matches("[YN]")) UI.print("Invalid input.");
        } while (!choice.matches("[YN]"));

        if (choice.equals("Y")) {
            archiveMakeUpSchedules.add(r);
            makeUpSchedules.remove(r);
            UI.print("Room request successfully deleted.");
        }
    }
    private static void makeUpScheduleManage(String text, String requestID, String facultyID, String roomCode,
                                             String requestType, String requestedDate, String startTime, String endTime, String reason, String status) {
        UI.header(text.toUpperCase() + ": ROOM REQUEST INFORMATION", false);
        UI.liner(String.format("     REQUEST ID      : %s", requestID), false);
        UI.liner(String.format(" [1] FACULTY ID      : %s", facultyID), false);
        UI.liner(String.format(" [2] ROOM CODE       : %s", roomCode), false);
        UI.liner(String.format(" [3] REQUEST TYPE    : %s", requestType), false);
        UI.liner(String.format(" [4] REQUESTED DATE  : %s", requestedDate), false);
        UI.liner(String.format(" [5] START TIME      : %s", startTime), false);
        UI.liner(String.format(" [6] END TIME        : %s", endTime), false);
        UI.liner(String.format(" [7] REASON          : %s", reason), false);
        UI.liner(String.format("     STATUS          : %s", status), false);
        UI.liner("", false);

        UI.option();
        UI.footer(false);
    }
}