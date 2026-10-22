
package FUNCTION;

import DATA.*;
import USER.*;
import UI.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;
import static UI.UI.in;

public class Request {

    // ROOM REQUEST FIELDS
    private static int nextRequestID = 1;
    public static ArrayList<Request> requests = new ArrayList<>();


    private String requestID;
    private String facultyID;
    private String roomCode;
    private String requestedDate;
    private String startTime;
    private String endTime;
    private String reason;
    private String status;
    private String reviewedBy;
    private String reviewedAt;
    private String rejectionReason;

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("MM/dd/uuuu")
                    .withResolverStyle(java.time.format.ResolverStyle.STRICT);

    // ROOM REQUEST CONSTRUCTOR
    public Request(String facultyID, String roomCode, String requestedDate,
                   String startTime, String endTime, String reason) {

        if (Faculty.findFaculty(facultyID) == null)
            throw new IllegalArgumentException("Invalid faculty ID.");

        if (Room.findRoom(roomCode) == null)
            throw new IllegalArgumentException("Invalid room code.");

        if (requestedDate == null || startTime == null
                || endTime == null || reason == null
                || requestedDate.trim().isEmpty()
                || startTime.trim().isEmpty()
                || endTime.trim().isEmpty()
                || reason.trim().isEmpty()) {
            throw new IllegalArgumentException("Request details cannot be empty.");
        }

        try {
            LocalDate date = LocalDate.parse(requestedDate.trim(), DATE_FORMAT);

            if (date.isBefore(LocalDate.now()))
                throw new IllegalArgumentException("Request date cannot be in the past.");

        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date. Use MM/dd/yyyy.");
        }

        int start, end;

        try {
            start = Room.convertToMinutes(startTime);
            end = Room.convertToMinutes(endTime);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid time format.");
        }

        if (start >= end)
            throw new IllegalArgumentException("Start time must be before end time.");

        this.requestID = String.format("RR%04d", nextRequestID++);
        this.facultyID = facultyID.trim();
        this.roomCode = roomCode.trim();
        this.requestedDate = requestedDate.trim();
        this.startTime = startTime.trim();
        this.endTime = endTime.trim();
        this.reason = reason.trim();
        this.status = "Pending";
        this.reviewedBy = null;
        this.reviewedAt = null;
        this.rejectionReason = null;

        requests.add(this);
    }

    // REQUEST GETTER METHODS
    public String getRequestID() { return requestID; }
    public String getFacultyID() { return facultyID; }
    public String getRoomCode() { return roomCode; }
    public String getRequestedDate() { return requestedDate; }
    public String getStartTime() { return startTime; }
    public String getEndTime() { return endTime; }
    public String getReason() { return reason; }
    public String getStatus() { return status; }
    public String getReviewedBy() { return reviewedBy; }
    public String getReviewedAt() { return reviewedAt; }
    public String getRejectionReason() { return rejectionReason; }

    // FIND REQUEST
    public static Request findRequest(String requestID) {
        if (requestID == null || requestID.trim().isEmpty())
            return null;

        for (Request request : requests) {
            if (request.requestID.equalsIgnoreCase(requestID.trim()))
                return request;
        }

        return null;
    }

    // GET REQUESTS BY FACULTY
    public static ArrayList<Request> getRequestsByFaculty(String facultyID) {
        ArrayList<Request> result = new ArrayList<>();

        if (facultyID == null)
            return result;

        for (Request request : requests) {
            if (request.facultyID.equalsIgnoreCase(facultyID.trim()))
                result.add(request);
        }

        return result;
    }

    // SUBMIT ROOM REQUEST
    public static void submitRequest(String facultyID) {
        Faculty faculty = Faculty.findFaculty(facultyID);

        if (faculty == null) {
            UI.print("Faculty not found.");
            return;
        }

        String roomCode, requestedDate, startTime, endTime, reason;

        UI.header("REQUEST ROOM RESERVATION");
        UI.liner("  FACULTY ID : " + faculty.getUserID());
        UI.liner("  NAME       : " + faculty.getUserName());
        UI.separator();

        Room.displayRooms();

        System.out.print("  Enter Room Code > ");
        roomCode = in.nextLine().trim();

        System.out.print("  Enter Date (MM/dd/yyyy) > ");
        requestedDate = in.nextLine().trim();

        System.out.print("  Enter Start Time > ");
        startTime = in.nextLine().trim();

        System.out.print("  Enter End Time > ");
        endTime = in.nextLine().trim();

        System.out.print("  Enter Reason > ");
        reason = in.nextLine().trim();

        try {
            Request request = new Request(facultyID, roomCode,
                    requestedDate, startTime, endTime, reason);

            UI.print("Room request submitted successfully.");
            UI.print("Request ID: " + request.getRequestID());

        } catch (IllegalArgumentException e) {
            UI.print(e.getMessage());
        }
    }

    // DISPLAY REQUEST DETAILS
    public static void displayRequest(Request request) {
        if (request == null)
            return;

        Faculty faculty = Faculty.findFaculty(request.facultyID);
        Room room = Room.findRoom(request.roomCode);

        UI.header("ROOM RESERVATION REQUEST");
        UI.liner("  REQUEST ID : " + request.requestID);
        UI.liner("  FACULTY ID : " + request.facultyID);
        UI.liner("  FACULTY    : "
                + (faculty == null ? "Not found" : faculty.getUserName()));
        UI.liner("  ROOM CODE  : " + request.roomCode);
        UI.liner("  ROOM       : "
                + (room == null ? "Not found" : room.getRoomDescription()));
        UI.liner("  DATE       : " + request.requestedDate);
        UI.liner("  TIME       : " + request.startTime + " - " + request.endTime);
        UI.liner("  STATUS     : " + request.status);
        UI.separator();
        UI.liner("  REASON     : " + request.reason);

        if (request.reviewedBy != null) {
            UI.liner("  REVIEWED BY: " + request.reviewedBy);
            UI.liner("  REVIEWED AT: " + request.reviewedAt);
        }

        if (request.rejectionReason != null)
            UI.liner("  REJECTION  : " + request.rejectionReason);

        UI.footer();
    }

    // DEPARTMENT HEAD REQUEST INBOX
    public static void viewRequests(String departmentHeadID) {
        DepartmentHead head =
                DepartmentHead.findDepartmentHead(departmentHeadID);

        if (head == null) {
            UI.print("Department head not found.");
            return;
        }

        ArrayList<Request> collegeRequests = new ArrayList<>();

        for (Request request : requests) {
            Faculty faculty = Faculty.findFaculty(request.facultyID);

            if (faculty != null
                    && head.getCollegeCode().equalsIgnoreCase(
                    Faculty.getCollegeCode(faculty))) {
                collegeRequests.add(request);
            }
        }

        UI.header("ROOM REQUEST INBOX");
        UI.liner("  DEPARTMENT HEAD : " + head.getUserName());
        UI.liner("  COLLEGE CODE    : " + head.getCollegeCode());
        UI.separator();

        if (collegeRequests.isEmpty()) {
            UI.liner("  No room requests found.");
            UI.footer();
            return;
        }

        for (int i = 0; i < collegeRequests.size(); i++) {
            Request request = collegeRequests.get(i);

            UI.liner(String.format("  [%d] %s | %s | %s | %s",
                    i + 1, request.requestID, request.facultyID,
                    request.roomCode, request.status));
            UI.liner(String.format("      %s | %s - %s",
                    request.requestedDate, request.startTime, request.endTime));
        }

        UI.separator();
        UI.liner("  [0] Back");
        UI.footer();

        String input;
        int choice = -1;

        do {
            System.out.print("  Select Request > ");
            input = in.nextLine().trim();

            try {
                choice = Integer.parseInt(input);

                if (choice < 0 || choice > collegeRequests.size()) {
                    UI.print("Invalid choice.");
                    choice = -1;
                }
            } catch (NumberFormatException e) {
                UI.print("Invalid choice.");
                choice = -1;
            }
        } while (choice == -1);

        if (choice == 0)
            return;

        Request selected = collegeRequests.get(choice - 1);

        displayRequest(selected);

        if (!selected.status.equals("Pending")) {
            UI.print("This request has already been reviewed.");
            return;
        }

        reviewRequest(departmentHeadID, selected.requestID);
    }

    // REVIEW REQUEST
    public static void reviewRequest(String departmentHeadID, String requestID) {
        DepartmentHead head =
                DepartmentHead.findDepartmentHead(departmentHeadID);
        Request request = findRequest(requestID);

        if (head == null || request == null) {
            UI.print("Department head or request not found.");
            return;
        }

        Faculty faculty = Faculty.findFaculty(request.facultyID);

        if (faculty == null
                || !head.getCollegeCode().equalsIgnoreCase(
                Faculty.getCollegeCode(faculty))) {
            UI.print("You cannot review requests outside your college.");
            return;
        }

        if (!request.status.equals("Pending")) {
            UI.print("This request has already been reviewed.");
            return;
        }

        String decision;

        do {
            System.out.print("  Approve or Reject? (A/R) > ");
            decision = in.nextLine().trim().toUpperCase();

            if (!decision.equals("A") && !decision.equals("R"))
                UI.print("Invalid choice.");
        } while (!decision.equals("A") && !decision.equals("R"));

        String rejectionReason = null;

        if (decision.equals("R")) {
            System.out.print("  Rejection Reason > ");
            rejectionReason = in.nextLine().trim();

            if (rejectionReason.isEmpty()) {
                UI.print("Rejection reason is required.");
                return;
            }
        }

        String reviewedAt = LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("MM/dd/uuuu hh:mm a"));

        if (decision.equals("A")) {
            Room room = Room.findRoom(request.roomCode);

            if (room == null) {
                UI.print("Requested room no longer exists.");
                return;
            }

            LocalDate date = LocalDate.parse(
                    request.requestedDate, DATE_FORMAT);

            String day = date.getDayOfWeek()
                    .getDisplayName(TextStyle.SHORT, Locale.ENGLISH);

            if (!Room.verifyAvailability(request.roomCode, day,
                    request.startTime, request.endTime)) {
                UI.print("Room is not available for the requested time.");
                return;
            }

            try {
                MakeUpSchedule approved = new MakeUpSchedule(
                        request.facultyID,
                        request.roomCode,
                        "Reservation",
                        request.requestedDate,
                        request.startTime,
                        request.endTime,
                        request.reason);

                approved.reviewRequest(departmentHeadID,
                        "Approved", reviewedAt);

                request.status = "Approved";
                request.reviewedBy = departmentHeadID;
                request.reviewedAt = reviewedAt;

                UI.print("Request approved successfully.");
                UI.print("Make-up schedule record created: "
                        + approved.getRequestID());

            } catch (IllegalArgumentException | IllegalStateException e) {
                UI.print("Could not approve request: " + e.getMessage());
            }

        } else {
            request.status = "Rejected";
            request.reviewedBy = departmentHeadID;
            request.reviewedAt = reviewedAt;
            request.rejectionReason = rejectionReason;

            UI.print("Request rejected.");
        }
    }
}