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

    private final String makeupID, facultyID, roomCode, requestType, requestedDate, startTime, endTime, reason,
                         status, reviewedBy, reviewedAt;

    public static ArrayList<MakeUpSchedule> makeUpSchedules = new ArrayList<>();
    public static ArrayList<MakeUpSchedule> archiveMakeUpSchedules = new ArrayList<>();

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("MM/dd/uuuu")
                    .withResolverStyle(ResolverStyle.STRICT);

    // ROOM REQUEST CONSTRUCTOR
    public MakeUpSchedule(String facultyID, String roomCode, String requestType,
                          String requestedDate, String startTime, String endTime,
                          String reason, String reviewedBy, String reviewedAt) {
        LocalDate date = LocalDate.parse(requestedDate.trim(), DATE_FORMAT);
        this.makeupID = String.format("RR%04d", nextMakeUpID++);
        this.facultyID = facultyID.trim().toUpperCase();
        this.roomCode = roomCode == null || roomCode.trim().isEmpty() ? null : roomCode.trim().toUpperCase();
        this.requestType = requestType.trim();
        this.requestedDate = date.format(DATE_FORMAT);
        this.startTime = startTime.trim();
        this.endTime = endTime.trim();
        this.reason = reason.trim();
        this.status = "Approved";
        this.reviewedBy = reviewedBy != null ? reviewedBy.trim() : null;
        this.reviewedAt = reviewedAt != null ? reviewedAt.trim() : null;
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
    }

    // GETTER METHODS
    public String getMakeUpID() { return makeupID; }
    public String getFacultyID() { return facultyID; }
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

        UI.header("ROOM REQUEST DETAILS");
        UI.liner("  REQUEST ID    : " + request.getMakeUpID());
        UI.liner("  FACULTY ID    : " + request.getFacultyID());
        UI.liner("  ROOM CODE     : "
                + (request.getRoomCode() == null
                ? "N/A" : request.getRoomCode()));
        UI.liner("  REQUEST TYPE  : " + request.getRequestType());
        UI.liner("  DATE          : " + request.getRequestedDate());
        UI.liner("  START TIME    : " + request.getStartTime());
        UI.liner("  END TIME      : " + request.getEndTime());
        UI.liner("  REASON        : " + request.getReason());
        UI.liner("  STATUS        : " + request.getStatus());
        UI.liner("  REVIEWED BY   : "
                + (request.getReviewedBy() == null
                ? "N/A" : request.getReviewedBy()));
        UI.liner("  REVIEWED AT   : "
                + (request.getReviewedAt() == null
                ? "N/A" : request.getReviewedAt()));
        UI.footer();
    }
    public static void displayMakeUpSchedules() {

        UI.header("ROOM REQUESTS");

        if (makeUpSchedules.isEmpty()) {
            UI.liner("  No room makeUpSchedules found.");
        } else {
            for (MakeUpSchedule request : makeUpSchedules) {
                UI.liner(String.format(
                        "  %-12s %-12s %-16s %-13s %-10s",
                        request.getMakeUpID(),
                        request.getFacultyID(),
                        request.getRequestType(),
                        request.getRequestedDate(),
                        request.getStatus()));
            }
        }

        UI.footer();
    }
    public static void displayArchiveMakeUpSchedules() {

        UI.header("ARCHIVE MAKE-UP SCHEDULE");

        if (archiveMakeUpSchedules.isEmpty()) {
            UI.liner("  No archived make-up schedules found.");
        } else {
            for (MakeUpSchedule request : archiveMakeUpSchedules) {
                UI.liner(String.format(
                        "  %-12s %-12s %-16s %-13s %-10s",
                        request.getMakeUpID(),
                        request.getFacultyID(),
                        request.getRequestType(),
                        request.getRequestedDate(),
                        request.getStatus()));
                UI.separator();
            }
        }

        UI.footer();
    }

    // CREATE ROOM REQUEST
    public static void createMakeUpSchedule() {

        String facultyID = "", roomCode = "", requestType = "";
        String requestedDate = "", startTime = "", endTime = "", reason = "";
        String choice;
        boolean submit = false;

        while (!submit) {

            makeUpScheduleManage("CREATE", "AUTO-GENERATED",
                    facultyID, roomCode, requestType, requestedDate,
                    startTime, endTime, reason, "Pending");

            do {
                System.out.print("  Select > ");
                choice = in.nextLine().trim().toUpperCase();

                if (!choice.matches("[1-7X]") && !choice.equals("/"))
                    UI.print("Invalid choice.");

            } while (!choice.matches("[1-7X]") && !choice.equals("/"));

            switch (choice) {

                case "1":
                    do {
                        System.out.print("   Enter Faculty ID > ");
                        facultyID = in.nextLine().trim().toUpperCase();

                        if (Faculty.findFaculty(facultyID) == null)
                            UI.print("Faculty does not exist.");

                    } while (Faculty.findFaculty(facultyID) == null);
                    break;

                case "2":
                    do {
                        System.out.print(
                                "   Enter Room Code (leave blank if none) > ");
                        roomCode = in.nextLine().trim().toUpperCase();

                        if (!roomCode.isEmpty()
                                && Room.findRoom(roomCode) == null)
                            UI.print("Room does not exist.");

                    } while (!roomCode.isEmpty()
                            && Room.findRoom(roomCode) == null);
                    break;

                case "3":
                    do {
                        System.out.print(
                                "   Enter Request Type (Reservation/Room Change/Make-up Class) > ");
                        requestType = in.nextLine().trim();

                        if (!requestType.equalsIgnoreCase("Reservation")
                                && !requestType.equalsIgnoreCase("Room Change")
                                && !requestType.equalsIgnoreCase("Make-up Class"))
                            UI.print("Invalid request type.");

                    } while (!requestType.equalsIgnoreCase("Reservation")
                            && !requestType.equalsIgnoreCase("Room Change")
                            && !requestType.equalsIgnoreCase("Make-up Class"));
                    break;

                case "4":
                    do {
                        System.out.print(
                                "   Enter Requested Date (MM/DD/YYYY) > ");
                        requestedDate = in.nextLine().trim();

                        try {
                            LocalDate date = LocalDate.parse(
                                    requestedDate, DATE_FORMAT);

                            if (date.isBefore(LocalDate.now())) {
                                UI.print("Date cannot be in the past.");
                                requestedDate = "";
                            }
                        } catch (DateTimeParseException e) {
                            UI.print("Invalid date. Use MM/DD/YYYY format.");
                            requestedDate = "";
                        }

                    } while (requestedDate.isEmpty());
                    break;

                case "5":
                    do {
                        System.out.print("   Enter Start Time (HH:mm) > ");
                        startTime = in.nextLine().trim();

                        try {
                            Room.convertToMinutes(startTime);
                        } catch (IllegalArgumentException e) {
                            UI.print("Invalid time format.");
                            startTime = "";
                        }

                    } while (startTime.isEmpty());
                    break;

                case "6":
                    do {
                        System.out.print("   Enter End Time (HH:mm) > ");
                        endTime = in.nextLine().trim();

                        try {
                            if (Room.convertToMinutes(endTime)
                                    <= Room.convertToMinutes(startTime)) {
                                UI.print("End time must be after start time.");
                                endTime = "";
                            }
                        } catch (IllegalArgumentException e) {
                            UI.print("Invalid time format.");
                            endTime = "";
                        }

                    } while (endTime.isEmpty());
                    break;

                case "7":
                    do {
                        System.out.print("   Enter Reason > ");
                        reason = in.nextLine().trim();

                        if (reason.isEmpty())
                            UI.print("Reason is required.");

                    } while (reason.isEmpty());
                    break;

                case "/":
                    if (facultyID.isEmpty() || requestType.isEmpty()
                            || requestedDate.isEmpty() || startTime.isEmpty()
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

        MakeUpSchedule r;

        do {
            System.out.print("  Enter Request ID > ");
            String input = in.nextLine().trim().toUpperCase();

            r = findMakeUpID(input);

            if (r == null)
                UI.print("Request does not exist.");

        } while (r == null);

        displayMakeUpSchedule(r.getMakeUpID());

        String choice;

        do {
            System.out.print("  Confirm Request Deletion? (Y/N) > ");
            choice = in.nextLine().trim().toUpperCase();

            if (!choice.matches("[YN]"))
                UI.print("Invalid input.");

        } while (!choice.matches("[YN]"));

        if (choice.equals("Y")) {
            archiveMakeUpSchedules.add(r);
            makeUpSchedules.remove(r);
            UI.print("Room request successfully deleted.");
        }
    }
    private static void makeUpScheduleManage(String text, String requestID, String facultyID, String roomCode,
            String requestType, String requestedDate, String startTime, String endTime, String reason, String status) {
        UI.header(text.toUpperCase() + ": ROOM REQUEST INFORMATION");
        UI.liner(String.format("     REQUEST ID      : %s", requestID));
        UI.liner(String.format(" [1] FACULTY ID      : %s", facultyID));
        UI.liner(String.format(" [2] ROOM CODE       : %s", roomCode));
        UI.liner(String.format(" [3] REQUEST TYPE    : %s", requestType));
        UI.liner(String.format(" [4] REQUESTED DATE  : %s", requestedDate));
        UI.liner(String.format(" [5] START TIME      : %s", startTime));
        UI.liner(String.format(" [6] END TIME        : %s", endTime));
        UI.liner(String.format(" [7] REASON          : %s", reason));
        UI.liner(String.format("     STATUS          : %s", status));
        UI.liner("");

        UI.option();
        UI.footer();
    }
}