
package DATA;

import UI.*;
import static UI.UI.in;

import USER.*;
import java.util.ArrayList;

public class MakeUpSchedule {

    // ROOM REQUEST FIELDS
    private static int nextMakeUpID = 1;
    public static ArrayList<MakeUpSchedule> requests = new ArrayList<>();
    public static ArrayList<MakeUpSchedule> archiveMakeUpClasses = new ArrayList<>();

    private String makeupID;
    private String facultyID;       // FK -> Faculty.facultyID
    private String roomCode;        // Nullable FK -> Room.roomCode
    private String requestType;
    private String requestedDate;
    private String startTime;
    private String endTime;
    private String reason;
    private String status;
    private String reviewedBy;      // Nullable FK -> DepartmentHead.departmentHeadID
    private String reviewedAt;

    // ROOM REQUEST CONSTRUCTOR
    public MakeUpSchedule(String facultyID, String roomCode, String requestType,
                          String requestedDate, String startTime, String endTime,
                          String reason) {
        this.makeupID = String.format("RR%04d", nextMakeUpID++);
        this.facultyID = facultyID;
        this.roomCode = roomCode;
        this.requestType = requestType;
        this.requestedDate = requestedDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.reason = reason;
        this.status = "Pending";
        this.reviewedBy = null;
        this.reviewedAt = null;
    }

    static {
        requests.add(new MakeUpSchedule("F0001", "RM001", "Reservation", "10/26/2026",
                "8:00 AM", "9:00 AM", "Additional laboratory session"));
        requests.add(new MakeUpSchedule("F0002", "RM004", "Room Change", "10/27/2026",
                "10:00 AM", "11:00 AM", "Original room unavailable"));
        requests.add(new MakeUpSchedule("F0003", "RM003", "Make-up Class", "10/28/2026",
                "1:00 PM", "2:00 PM", "Replacement lecture session"));
        requests.add(new MakeUpSchedule("F0004", "RM005", "Reservation", "10/29/2026",
                "2:00 PM", "3:00 PM", "Additional review class"));
        requests.add(new MakeUpSchedule("F0006", "RM008", "Make-up Class", "11/2/2026",
                "10:00 AM", "11:00 AM", "Missed laboratory session"));
        requests.add(new MakeUpSchedule("F0007", "RM002", "Reservation", "11/03/2026",
                "1:00 PM", "2:00 PM", "Additional computer laboratory use"));
        requests.add(new MakeUpSchedule("F0010", "RM010", "Reservation", "11/06/2026",
                "10:00 AM", "11:00 AM", "Additional science laboratory session"));
        requests.add(new MakeUpSchedule("F0011", "RM004", "Room Change", "11/09/2026",
                "12:00 PM", "1:00 PM", "Scheduling conflict in original room"));
        requests.add(new MakeUpSchedule("F0013", "RM013", "Reservation", "11/11/2026",
                "8:00 AM", "9:00 AM", "Additional biology laboratory session"));
        requests.add(new MakeUpSchedule("F0014", "RM005", "Room Change", "11/12/2026",
                "9:00 AM", "10:00 AM", "Room needed for a larger class"));
    }

    // ROOM REQUEST GETTER METHODS
    public String getMakeUpID() {
        return makeupID;
    }
    public String getFacultyID() {
        return facultyID;
    }
    public String getRoomCode() {
        return roomCode;
    }
    public String getRequestType() {
        return requestType;
    }
    public String getRequestedDate() {
        return requestedDate;
    }
    public String getStartTime() {
        return startTime;
    }
    public String getEndTime() {
        return endTime;
    }
    public String getReason() {
        return reason;
    }
    public String getStatus() {
        return status;
    }
    public String getReviewedBy() {
        return reviewedBy;
    }
    public String getReviewedAt() {
        return reviewedAt;
    }

    // FIND ROOM REQUEST BY PRIMARY KEY
    public static MakeUpSchedule findRequest(String requestID) {

        if (requestID == null || requestID.trim().isEmpty())
            return null;

        requestID = requestID.trim();

        for (MakeUpSchedule request : requests) {
            if (requestID.equalsIgnoreCase(request.makeupID)) {
                return request;
            }
        }

        return null;
    }

    // RETRIEVE REQUESTS BY FACULTY FOREIGN KEY
    public static ArrayList<MakeUpSchedule> getRequestsByFaculty(String facultyID) {

        ArrayList<MakeUpSchedule> result = new ArrayList<>();

        if (facultyID == null || facultyID.trim().isEmpty())
            return result;

        facultyID = facultyID.trim();

        for (MakeUpSchedule request : requests) {
            if (facultyID.equalsIgnoreCase(request.facultyID)) {
                result.add(request);
            }
        }

        return result;
    }

    // RETRIEVE REQUESTS BY STATUS
    public static ArrayList<MakeUpSchedule> getRequestsByStatus(String status) {

        ArrayList<MakeUpSchedule> result = new ArrayList<>();

        if (status == null || status.trim().isEmpty())
            return result;

        status = status.trim();

        for (MakeUpSchedule request : requests) {
            if (status.equalsIgnoreCase(request.status)) {
                result.add(request);
            }
        }

        return result;
    }

    // RETRIEVE RELATED FACULTY THROUGH FOREIGN KEY
    public Faculty getFaculty() {
        return Faculty.findFaculty(facultyID);
    }

    // RETRIEVE RELATED ROOM THROUGH FOREIGN KEY
    public Room getRoom() {
        return roomCode == null ? null : Room.findRoom(roomCode);
    }

    // REVIEW ROOM REQUEST
    public void reviewRequest(String departmentHeadID, String decision,
                              String reviewedAt) {

        if (departmentHeadID == null || departmentHeadID.trim().isEmpty()) {
            throw new IllegalArgumentException("Department head ID is required.");
        }

        departmentHeadID = departmentHeadID.trim();

        if (DepartmentHead.findDepartmentHead(departmentHeadID) == null) {
            throw new IllegalArgumentException("Invalid department head ID.");
        }

        if (decision == null || decision.trim().isEmpty()) {
            throw new IllegalArgumentException("Decision is required.");
        }

        decision = decision.trim();

        if (decision.equalsIgnoreCase("Approved")) {
            decision = "Approved";
        } else if (decision.equalsIgnoreCase("Rejected")) {
            decision = "Rejected";
        } else {
            throw new IllegalArgumentException(
                    "Decision must be Approved or Rejected.");
        }

        if (!"Pending".equalsIgnoreCase(status)) {
            throw new IllegalStateException(
                    "Only pending requests can be reviewed.");
        }

        if (reviewedAt == null || reviewedAt.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Review date and time are required.");
        }

        this.status = decision;
        this.reviewedBy = departmentHeadID;
        this.reviewedAt = reviewedAt.trim();
    }

    public static void displayMakeUpSchedules() {
        UI.header("ROOM REQUESTS");

        if (MakeUpSchedule.requests.isEmpty()) {
            UI.liner("  No room requests found.");
        } else {
            for (MakeUpSchedule request : MakeUpSchedule.requests) {
                UI.liner(String.format(
                        "  %-8s %-10s %-16s %-12s %-10s",
                        request.getMakeUpID(),
                        request.getFacultyID(),
                        request.getRequestType(),
                        request.getRequestedDate(),
                        request.getStatus()
                ));
                UI.separator();
            }
        }

        UI.footer();
    }

    public static void displayMakeUpSchedule(String requestID) {
        MakeUpSchedule request = MakeUpSchedule.findRequest(requestID);

        if (request == null) {
            UI.print("Request not found");
            return;
        }

        UI.header("ROOM REQUEST DETAILS");
        UI.liner("  REQUEST ID    : " + request.getMakeUpID());
        UI.liner("  FACULTY ID    : " + request.getFacultyID());
        UI.liner("  ROOM CODE     : "
                + (request.getRoomCode() == null ? "N/A" : request.getRoomCode()));
        UI.liner("  REQUEST TYPE  : " + request.getRequestType());
        UI.liner("  DATE          : " + request.getRequestedDate());
        UI.liner("  START TIME    : " + request.getStartTime());
        UI.liner("  END TIME      : " + request.getEndTime());
        UI.liner("  REASON        : " + request.getReason());
        UI.liner("  STATUS        : " + request.getStatus());
        UI.liner("  REVIEWED BY   : "
                + (request.getReviewedBy() == null ? "N/A" : request.getReviewedBy()));
        UI.liner("  REVIEWED AT   : "
                + (request.getReviewedAt() == null ? "N/A" : request.getReviewedAt()));
        UI.footer();
    }

    public static void manageMakeUpSchedules() {
        int choice = -1;
        String input = "";

        UI.manageUI("Room Requests");

        do {
            System.out.print("  Select > ");

            try {
                input = in.nextLine().trim();

                if (input.isEmpty()) {
                    UI.print("Invalid Input");
                    continue;
                }

                choice = Integer.parseInt(input);

                if (!(choice >= 1 && choice <= 4) && choice != 0)
                    UI.print("Invalid Input");

            } catch (NumberFormatException e) {
                UI.print("Invalid Input");
                choice = -1;
            }

        } while (!(choice >= 1 && choice <= 4) && choice != 0);

        switch (choice) {
            case 1:
                createMakeUpSchedule();
                break;
            case 2:
                editMakeUpSchedule();
                break;
            case 3:
                archiveMakeUpSchedule();
                break;
            case 4:
                displayMakeUpSchedules();
                break;
            case 0:
                // Return to previous menu
                break;
        }
    }
    public static void createMakeUpSchedule() {
        String facultyID = "", roomCode = "", requestType = "";
        String requestedDate = "", startTime = "", endTime = "", reason = "";
        String choice = "";
        boolean submit = false;

        while (!submit) {
            makeUpScheduleManage("CREATE", "AUTO-GENERATED",
                    facultyID, roomCode, requestType, requestedDate,
                    startTime, endTime, reason, "Pending");

            do {
                System.out.print("  Select > ");
                choice = in.nextLine().trim().toUpperCase();

                if (!choice.matches("[1-7X]") && !choice.equals("/"))
                    UI.print("Invalid Choice");

            } while (!choice.matches("[1-7X]") && !choice.equals("/"));

            switch (choice) {
                case "1":
                    do {
                        System.out.print("   Enter Faculty ID > ");
                        facultyID = in.nextLine().trim().toUpperCase();

                        if (facultyID.isEmpty()
                                || Faculty.findFaculty(facultyID) == null)
                            UI.print("Faculty does not exist");

                    } while (facultyID.isEmpty()
                            || Faculty.findFaculty(facultyID) == null);
                    break;

                case "2":
                    do {
                        System.out.print("   Enter Room Code (leave blank if none) > ");
                        roomCode = in.nextLine().trim().toUpperCase();

                        if (!roomCode.isEmpty()
                                && Room.findRoom(roomCode) == null)
                            UI.print("Room does not exist");

                    } while (!roomCode.isEmpty()
                            && Room.findRoom(roomCode) == null);
                    break;

                case "3":
                    do {
                        System.out.print(
                                "   Enter Request Type (Reservation/Room Change/Make-up Class) > "
                        );
                        requestType = in.nextLine().trim();

                        if (!requestType.equalsIgnoreCase("Reservation")
                                && !requestType.equalsIgnoreCase("Room Change")
                                && !requestType.equalsIgnoreCase("Make-up Class"))
                            UI.print("Invalid Request Type");

                    } while (!requestType.equalsIgnoreCase("Reservation")
                            && !requestType.equalsIgnoreCase("Room Change")
                            && !requestType.equalsIgnoreCase("Make-up Class"));
                    break;

                case "4":
                    do {
                        System.out.print("   Enter Requested Date (MM/DD/YYYY) > ");
                        requestedDate = in.nextLine().trim();

                        try {
                            java.time.LocalDate.parse(
                                    requestedDate,
                                    java.time.format.DateTimeFormatter
                                            .ofPattern("MM/dd/uuuu")
                                            .withResolverStyle(
                                                    java.time.format.ResolverStyle.STRICT
                                            )
                            );
                        } catch (java.time.format.DateTimeParseException e) {
                            UI.print("Invalid Date. Use MM/DD/YYYY format.");
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
                            UI.print("Invalid time format");
                            startTime = "";
                        }

                    } while (startTime.isEmpty());
                    break;

                case "6":
                    do {
                        System.out.print("   Enter End Time (HH:mm) > ");
                        endTime = in.nextLine().trim();

                        try {
                            int start = Room.convertToMinutes(startTime);
                            int end = Room.convertToMinutes(endTime);

                            if (end <= start) {
                                UI.print("End time must be after start time");
                                endTime = "";
                            }

                        } catch (IllegalArgumentException e) {
                            UI.print("Invalid time format");
                            endTime = "";
                        }

                    } while (endTime.isEmpty());
                    break;

                case "7":
                    do {
                        System.out.print("   Enter Reason > ");
                        reason = in.nextLine().trim();

                        if (reason.isEmpty())
                            UI.print("Reason is required");

                    } while (reason.isEmpty());
                    break;

                case "/":
                    if (facultyID.isEmpty() || requestType.isEmpty()
                            || requestedDate.isEmpty() || startTime.isEmpty()
                            || endTime.isEmpty() || reason.isEmpty()) {
                        UI.print("Details are incomplete");
                        break;
                    }

                    try {
                        MakeUpSchedule request = new MakeUpSchedule(
                                facultyID,
                                roomCode.isEmpty() ? null : roomCode,
                                requestType,
                                requestedDate,
                                startTime,
                                endTime,
                                reason
                        );

                        UI.print("Room request successfully created");
                        displayMakeUpSchedule(request.getMakeUpID());
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
    public static void editMakeUpSchedule() {
        String input = "", choice = "", departmentHeadID = "";
        String decision = "", reviewedAt = "";
        boolean submit = false;

        UI.header("EDIT ROOM REQUEST");

        do {
            System.out.print("  Enter Request ID > ");
            input = in.nextLine().trim().toUpperCase();

            if (input.isEmpty() || MakeUpSchedule.findRequest(input) == null)
                UI.print("Request does not exist");

        } while (input.isEmpty() || MakeUpSchedule.findRequest(input) == null);

        MakeUpSchedule request = MakeUpSchedule.findRequest(input);

        if (request == null) {
            UI.print("Request not found");
            return;
        }

        while (!submit) {
            makeUpScheduleManage("EDIT", request.getMakeUpID(),
                    request.getFacultyID(), request.getRoomCode() == null
                            ? "N/A" : request.getRoomCode(),
                    request.getRequestType(), request.getRequestedDate(),
                    request.getStartTime(), request.getEndTime(),
                    request.getReason(), request.getStatus());

            do {
                System.out.print("  Select (1 = Review Request, / = Save, X = Cancel) > ");
                choice = in.nextLine().trim().toUpperCase();

                if (!choice.matches("[1X]") && !choice.equals("/"))
                    UI.print("Invalid Choice");

            } while (!choice.matches("[1X]") && !choice.equals("/"));

            switch (choice) {
                case "1":
                    do {
                        System.out.print("   Enter Department Head ID > ");
                        departmentHeadID = in.nextLine().trim().toUpperCase();

                        if (departmentHeadID.isEmpty()
                                || DepartmentHead.findDepartmentHead(departmentHeadID) == null)
                            UI.print("Department head does not exist");

                    } while (departmentHeadID.isEmpty()
                            || DepartmentHead.findDepartmentHead(departmentHeadID) == null);

                    do {
                        System.out.print("   Enter Decision (Approved/Rejected) > ");
                        decision = in.nextLine().trim();

                        if (!decision.equalsIgnoreCase("Approved")
                                && !decision.equalsIgnoreCase("Rejected"))
                            UI.print("Invalid Decision");

                    } while (!decision.equalsIgnoreCase("Approved")
                            && !decision.equalsIgnoreCase("Rejected"));

                    reviewedAt = java.time.LocalDateTime.now().format(
                            java.time.format.DateTimeFormatter.ofPattern(
                                    "MM/dd/uuuu hh:mm a",
                                    java.util.Locale.US
                            )
                    );

                    try {
                        request.reviewRequest(
                                departmentHeadID,
                                decision,
                                reviewedAt
                        );

                        UI.print("Room request successfully reviewed");
                        displayMakeUpSchedule(request.getMakeUpID());
                        submit = true;

                    } catch (IllegalArgumentException | IllegalStateException e) {
                        UI.print(e.getMessage());
                    }
                    break;

                case "/":
                    UI.print("No changes to save");
                    submit = true;
                    break;

                case "X":
                    submit = true;
                    break;
            }
        }
    }
    public static void archiveMakeUpSchedule() {
        String input, choice;

        UI.print("DELETE ROOM REQUEST");

        do {
            System.out.print("  Enter Request ID > ");
            input = in.nextLine().trim().toUpperCase();

            if (input.isEmpty() || MakeUpSchedule.findRequest(input) == null)
                UI.print("Request does not exist");

        } while (input.isEmpty() || MakeUpSchedule.findRequest(input) == null);

        MakeUpSchedule request = MakeUpSchedule.findRequest(input);

        if (request == null) {
            UI.print("Request not found");
            return;
        }

        displayMakeUpSchedule(request.getMakeUpID());

        do {
            System.out.print("  Confirm Request Deletion? (Y/N) > ");
            choice = in.nextLine().trim().toUpperCase();

            if (!choice.matches("[YN]"))
                UI.print("Invalid Input");

        } while (!choice.matches("[YN]"));

        if (choice.equals("Y")) {
            archiveMakeUpClasses.add(request);
            MakeUpSchedule.requests.remove(request);
            UI.print("Room request successfully deleted");
        }
    }
    private static void makeUpScheduleManage(
            String text,
            String requestID,
            String facultyID,
            String roomCode,
            String requestType,
            String requestedDate,
            String startTime,
            String endTime,
            String reason,
            String status) {

        UI.header(text.toUpperCase() + ": ROOM REQUEST INFORMATION");

        UI.liner(String.format(" [1] FACULTY ID      : %s", facultyID));
        UI.liner(String.format(" [2] ROOM CODE       : %s", roomCode));
        UI.liner(String.format(" [3] REQUEST TYPE    : %s", requestType));
        UI.liner(String.format(" [4] REQUESTED DATE  : %s", requestedDate));
        UI.liner(String.format(" [5] START TIME      : %s", startTime));
        UI.liner(String.format(" [6] END TIME        : %s", endTime));
        UI.liner(String.format(" [7] REASON          : %s", reason));
        UI.liner(String.format("     REQUEST ID      : %s", requestID));
        UI.liner(String.format("     STATUS          : %s", status));
        UI.liner("");

        UI.option();
        UI.footer();
    }
}