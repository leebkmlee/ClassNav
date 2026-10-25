package FUNCTION;

import DATA.*;
import USER.*;
import UI.*;
import static UI.UI.*;

import java.time.*;
import java.time.format.*;
import java.util.*;

import static UI.UI.in;

public class Request {

    // FIELDS
    private static int nextRequestID = 1;
    public static ArrayList<Request> requests = new ArrayList<>();

    private final String requestID, facultyID, roomCode, targetCollegeCode, requestedDate, startTime, endTime, reason;
    private String status, reviewedBy, reviewedAt, rejectionReason;

    // CONSTRUCTOR
    public Request(String facultyID, String roomCode, String requestedDate,
                   String startTime, String endTime, String reason) {
        Room room = Room.findRoom(roomCode);

        LocalDate date = null;

        try {
            date = LocalDate.parse(requestedDate.trim(), DATE_FORMAT);
        } catch (DateTimeParseException e) {
            UI.print("Invalid date. Use MM/dd/yyyy.");
        }

        assert date != null;
        String convertedStart = convertTime(startTime);
        String convertedEnd = convertTime(endTime);

        this.requestID = String.format("RR%04d", nextRequestID++);
        this.facultyID = facultyID.trim();
        this.roomCode = roomCode.trim();
        this.targetCollegeCode = room.getCollegeCode().trim();
        this.requestedDate = date.format(DATE_FORMAT);
        this.startTime = convertedStart;
        this.endTime = convertedEnd;
        this.reason = reason.trim();
        this.status = "Pending";
        this.reviewedBy = null;
        this.reviewedAt = null;
        this.rejectionReason = null;
    }

    // DUMMY DATA
    static {
        requests.add(new Request("F0001", "RM001", "10/26/2026",
                "8:00 AM", "9:00 AM", "Room needed for class"));

        requests.add(new Request("F0002", "RM002", "10/27/2026",
                "9:00 AM", "10:00 AM", "Room change due to equipment"));

        requests.add(new Request("F0003", "RM003", "10/28/2026",
                "10:00 AM", "11:00 AM", "Make-up session"));

        requests.add(new Request("F0004", "RM004", "10/29/2026",
                "11:00 AM", "12:00 PM", "Room needed for class"));

        requests.add(new Request("F0006", "RM008", "11/02/2026",
                "1:00 PM", "2:00 PM", "Make-up session"));

        requests.add(new Request("F0007", "RM002", "11/03/2026",
                "8:00 AM", "9:00 AM", "Room needed for class"));

        requests.add(new Request("F0010", "RM010", "11/06/2026",
                "11:00 AM", "12:00 PM", "Room needed for class"));

        requests.add(new Request("F0011", "RM004", "11/09/2026",
                "9:00 AM", "10:00 AM", "Room change due to equipment"));

        requests.add(new Request("F0013", "RM013", "11/11/2026",
                "8:00 AM", "9:00 AM", "Room needed for class"));

        requests.add(new Request("F0014", "RM005", "11/12/2026",
                "9:00 AM", "10:00 AM", "Room change due to equipment"));
    }

    // REQUEST GETTER METHODS
    public String getRequestID() {
        return requestID;
    }
    public String getFacultyID() {
        return facultyID;
    }
    public String getRoomCode() {
        return roomCode;
    }
    public String getTargetCollegeCode() {
        return targetCollegeCode;
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
    public String getRejectionReason() {
        return rejectionReason;
    }

    // FIND REQUEST
    public static Request findRequest(String requestID) {

        if (requestID == null || requestID.trim().isEmpty())
            return null;

        for (Request request : requests) {

            if (request != null
                    && request.requestID.equalsIgnoreCase(requestID.trim())) {
                return request;
            }
        }

        return null;
    }

    // GET REQUESTS BY FACULTY
    public static ArrayList<Request> getRequestsByFaculty(String facultyID) {

        ArrayList<Request> result = new ArrayList<>();

        if (facultyID == null || facultyID.trim().isEmpty())
            return result;

        for (Request request : requests) {

            if (request != null
                    && request.facultyID.equalsIgnoreCase(facultyID.trim())) {
                result.add(request);
            }
        }

        return result;
    }

    // SUBMIT ROOM REQUEST
    public static void submitRequest(String facultyID) {

        Faculty f = Faculty.findFaculty(facultyID);

        if (f == null) {
            UI.print("Faculty not found.");
            return;
        }

        String facultyCollegeCode = Faculty.getCollegeCode(f);

        String requestedDate, startTime, endTime, roomCode, reason;
        LocalDate date;
        String convertedStart, convertedEnd;

        UI.header("REQUEST ROOM RESERVATION");
        UI.liner("  FACULTY ID : " + f.getUserID());
        UI.liner("  NAME       : " + f.getUserName());
        UI.liner("  COLLEGE    : "
                + (facultyCollegeCode == null ? "Not assigned" : facultyCollegeCode));
        UI.footer();

        // ENTER FUTURE RESERVATION DATE
        do {
            System.out.print("  Enter Date (MM/dd/yyyy) > ");
            requestedDate = in.nextLine().trim();

            try {
                date = LocalDate.parse(requestedDate, DATE_FORMAT);

                if (date.isBefore(LocalDate.now())) {
                    UI.print("Request date cannot be in the past.");
                    date = null;
                }

            } catch (DateTimeParseException e) {
                UI.print("Invalid date. Use MM/dd/yyyy.");
                date = null;
            }

        } while (date == null);

        // ENTER REQUEST TIME
        do {
            System.out.print("  Enter Start Time (hh:mm AM/PM) > ");
            startTime = in.nextLine().trim();

            System.out.print("  Enter End Time (hh:mm AM/PM) > ");
            endTime = in.nextLine().trim();

            try {
                convertedStart = convertTime(startTime);
                convertedEnd = convertTime(endTime);

                LocalTime start = LocalTime.parse(convertedStart, TIME_FORMAT);
                LocalTime end = LocalTime.parse(convertedEnd, TIME_FORMAT);

                if (!start.isBefore(end)) {
                    UI.print("Start time must be before end time.");
                    convertedStart = null;
                }

                // A request for today must still be in the future.
                if (date.equals(LocalDate.now())
                        && !start.isAfter(LocalTime.now())) {
                    UI.print("The requested start time must be in the future.");
                    convertedStart = null;
                }

            } catch (IllegalArgumentException | DateTimeParseException e) {
                UI.print(e.getMessage() == null ? "Invalid time." : e.getMessage());
                convertedStart = null;
                convertedEnd = null;
            }

        } while (convertedStart == null || convertedEnd == null);

        // DISPLAY ROOMS AVAILABLE FOR THE REQUESTED DATE AND TIME
        UI.header("ROOMS AVAILABLE FOR REQUESTED TIME");
        UI.liner("  DATE : " + date.format(DATE_FORMAT));
        UI.liner("  TIME : " + formatTime(convertedStart)
                + " - " + formatTime(convertedEnd));

        boolean available = false;

        for (Room room : Room.rooms) {

            if (room == null)
                continue;

            if (!Room.verifyAvailability(room.getRoomCode(),
                    date.format(DATE_FORMAT), convertedStart, convertedEnd))
                continue;

            UI.separator();
            UI.liner("  " + room.getRoomCode() + " - " + room.getRoomDescription());
            UI.liner("  Type: " + room.getRoomType()
                    + " | Capacity: " + room.getCapacity());
            UI.liner("  Floor: " + Room.getFloorText(room.getFloorNumber()));
            UI.liner("  College: " + room.getCollegeCode());

            available = true;
        }

        if (!available) {
            UI.liner("  No rooms are available for this date and time.");
            UI.footer();
            return;
        }

        UI.footer();

        // SELECT ROOM
        System.out.print("  Enter Room Code > ");
        roomCode = in.nextLine().trim();

        Room room = Room.findRoom(roomCode);

        if (room == null) {
            UI.print("Room not found.");
            return;
        }

        // RECHECK SELECTED ROOM AVAILABILITY
        if (!Room.verifyAvailability(roomCode, date.format(DATE_FORMAT),
                convertedStart, convertedEnd)) {
            UI.print("The selected room is not available for that time.");
            return;
        }

        // FIND THE DEPARTMENT HEAD AFTER ROOM SELECTION
        String targetCollegeCode = room.getCollegeCode();

        if (targetCollegeCode == null || targetCollegeCode.trim().isEmpty()) {
            UI.print("The selected room has no assigned college.");
            UI.print("Your request cannot be submitted.");
            return;
        }

        DepartmentHead head = findDepartmentHeadByCollege(targetCollegeCode);

        if (head == null) {
            UI.print("No department head is assigned to the room's college ("
                    + targetCollegeCode + ").");
            UI.print("Your request cannot be submitted.");
            return;
        }

        // SHOW WHERE THE REQUEST WILL BE ROUTED
        UI.header("REQUEST ROUTING");
        UI.liner("  SELECTED ROOM : " + room.getRoomCode());
        UI.liner("  ROOM          : " + room.getRoomDescription());
        UI.liner("  ROOM COLLEGE  : " + targetCollegeCode);
        UI.liner("  ROUTED TO     : " + head.getUserName());
        UI.footer();

        do {
            System.out.print("  Enter Reason > ");
            reason = in.nextLine().trim();
            if (reason.isEmpty()) UI.print("Reason cannot be empty.");

        } while (reason.isEmpty());

        requests.add(new Request(facultyID, roomCode, date.format(DATE_FORMAT), convertedStart, convertedEnd, reason));
        UI.print("Room request submitted successfully.");
        displayRequest(findRequest(requests.getLast().getRequestID()));
    }

    // DISPLAY REQUEST DETAILS
    public static void displayRequest(Request request) {

        if (request == null)
            return;

        Faculty faculty = Faculty.findFaculty(request.facultyID);
        Room room = Room.findRoom(request.roomCode);

        UI.header("ROOM RESERVATION REQUEST");
        UI.liner("  REQUEST ID       : " + request.requestID);
        UI.liner("  FACULTY ID       : " + request.facultyID);
        UI.liner("  FACULTY          : " + faculty.getUserName());
        UI.liner("  FACULTY COLLEGE  : " +  Faculty.getCollegeCode(faculty));
        UI.liner("  ROOM CODE        : " + request.roomCode);
        UI.liner("  ROOM             : " + room.getRoomDescription());
        UI.liner("  ROOM COLLEGE     : " + request.targetCollegeCode);
        UI.liner("  DATE             : " + request.requestedDate);
        UI.liner("  TIME             : " + formatTime(request.startTime) + " - " + formatTime(request.endTime));
        UI.liner("  STATUS           : " + request.status);
        UI.separator();
        UI.liner("  REASON         : " + request.reason);

        if (request.reviewedBy != null) {
            UI.liner("  REVIEWED BY    : " + request.reviewedBy);
            UI.liner("  REVIEWED AT    : " + request.reviewedAt);
        }

        if (request.rejectionReason != null)
            UI.liner("  REJECTION      : " + request.rejectionReason);

        UI.footer();
    }

    // FIND THE DEPARTMENT HEAD RESPONSIBLE FOR A COLLEGE
    private static DepartmentHead findDepartmentHeadByCollege(String collegeCode) {

        if (collegeCode == null || collegeCode.trim().isEmpty())
            return null;

        for (User user : User.users) {

            if (!(user instanceof DepartmentHead))
                continue;

            DepartmentHead head = (DepartmentHead) user;

            if (head.getCollegeCode() != null
                    && head.getCollegeCode().equalsIgnoreCase(
                    collegeCode.trim())) {
                return head;
            }
        }

        return null;
    }

    // CHECK IF DEPARTMENT HEAD CAN ACCESS A REQUEST
    private static boolean canReviewRequest(DepartmentHead head, Request request) {

        if (head == null || request == null
                || head.getCollegeCode() == null
                || head.getCollegeCode().trim().isEmpty()
                || request.targetCollegeCode == null
                || request.targetCollegeCode.trim().isEmpty()) {
            return false;
        }

        // Access is based on the room's assigned college,
        // not the requesting faculty member's college.
        return head.getCollegeCode().trim().equalsIgnoreCase(
                request.targetCollegeCode.trim());
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

        // ONLY INCLUDE REQUESTS ROUTED TO THIS DEPARTMENT HEAD'S COLLEGE
        for (Request request : requests) {

            if (canReviewRequest(head, request))
                collegeRequests.add(request);
        }

        UI.header("ROOM REQUEST INBOX");
        UI.liner("  DEPARTMENT HEAD : " + head.getUserName());
        UI.liner("  COLLEGE CODE    : " + head.getCollegeCode());
        UI.separator();

        if (collegeRequests.isEmpty()) {
            UI.liner("  No room makeUpSchedules found for your college.");
            UI.footer();
            return;
        }

        for (int i = 0; i < collegeRequests.size(); i++) {

            Request request = collegeRequests.get(i);

            UI.liner(String.format("  [%d] %s | Faculty: %s | Room: %s | %s",
                    i + 1,
                    request.requestID,
                    request.facultyID,
                    request.roomCode,
                    request.status));

            UI.liner(String.format("      College: %s | %s | %s - %s",
                    request.targetCollegeCode,
                    request.requestedDate,
                    formatTime(request.startTime),
                    formatTime(request.endTime)));

            if (i < collegeRequests.size() - 1)
                UI.separator();
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

        if (!canReviewRequest(head, selected)) {
            UI.print("You cannot access makeUpSchedules outside your college.");
            return;
        }

        displayRequest(selected);

        if (!selected.status.equalsIgnoreCase("Pending")) {
            UI.print("This request has already been reviewed.");
            return;
        }

        reviewRequest(departmentHeadID, selected.requestID);
    }

    // REVIEW REQUEST
    public static void reviewRequest(String departmentHeadID, String requestID) {

        DepartmentHead head = DepartmentHead.findDepartmentHead(departmentHeadID);

        Request r = findRequest(requestID);

        if (head == null || r == null) {
            UI.print("Department head or request not found.");
            return;
        }

        // ONLY THE DEPARTMENT HEAD OF THE ROOM'S COLLEGE CAN REVIEW
        if (!canReviewRequest(head, r)) {
            UI.print("You cannot review makeUpSchedules outside your college.");
            return;
        }

        if (!"Pending".equalsIgnoreCase(r.status)) {
            UI.print("This request has already been reviewed.");
            return;
        }

        String decision;

        do {
            System.out.print("  Approve or Reject? (A/R) > ");
            decision = in.nextLine().trim().toUpperCase(Locale.ENGLISH);

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

        String reviewedAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/uuuu hh:mm a", Locale.ENGLISH));

        // VALIDATE REVIEW DETAILS
        if (DepartmentHead.findDepartmentHead(departmentHeadID) == null) {
            UI.print("Invalid department head ID.");
            return;
        }

        if (reviewedAt.trim().isEmpty()) {
            UI.print("Review date and time are required.");
            return;
        }

        if (decision.equals("A")) {
            Room room = Room.findRoom(r.roomCode);

            if (room == null) {
                UI.print("Requested room no longer exists.");
                return;
            }

            LocalDate date;

            try {
                date = LocalDate.parse(r.requestedDate, DATE_FORMAT);
            } catch (DateTimeParseException e) {
                UI.print("The request contains an invalid date.");
                return;
            }

            // ENSURE THE ROOM STILL BELONGS TO THE COLLEGE THAT RECEIVED THE REQUEST
            if (room.getCollegeCode() == null || !room.getCollegeCode().equalsIgnoreCase(r.targetCollegeCode)) {
                UI.print("The room's college ownership has changed.");
                return;
            }

            // RECHECK AVAILABILITY
            if (!Room.verifyAvailability(r.roomCode, date.format(DATE_FORMAT), r.startTime, r.endTime)) {
                UI.print("Room is not available for the requested time.");
                UI.print("The request remains pending.");
                return;
            }

            MakeUpSchedule approved = null;

            try {
                approved = new MakeUpSchedule(r.facultyID, r.roomCode, "Reservation", date.format(DATE_FORMAT),
                        r.startTime, r.endTime, r.reason, departmentHeadID.trim(), reviewedAt.trim());

                MakeUpSchedule.makeUpSchedules.add(approved);

                r.status = "Approved";
                r.reviewedBy = departmentHeadID.trim();
                r.reviewedAt = reviewedAt.trim();

                UI.print("Request approved successfully.");
            } catch (IllegalArgumentException | IllegalStateException e) {
                if (approved != null) MakeUpSchedule.makeUpSchedules.remove(approved);
                UI.print("Could not approve request: " + e.getMessage());
            }

        } else {
            r.status = "Rejected";
            r.reviewedBy = departmentHeadID.trim();
            r.reviewedAt = reviewedAt.trim();
            r.rejectionReason = rejectionReason;

            UI.print("Request rejected.");
        }
    }

    // VIEW REQUESTS BY DEPARTMENT HEAD'S COLLEGE
    public static void viewRequest(String departmentHeadID) {

        DepartmentHead head =
                DepartmentHead.findDepartmentHead(departmentHeadID);

        if (head == null) {
            UI.print("Department head not found.");
            return;
        }

        ArrayList<Request> collegeRequests = new ArrayList<>();

        // FILTER BY THE COLLEGE STORED ON EACH REQUEST
        for (Request request : requests) {

            if (canReviewRequest(head, request))
                collegeRequests.add(request);
        }

        UI.header("ROOM REQUEST LIST");
        UI.liner("  DEPARTMENT HEAD : " + head.getUserName());
        UI.liner("  COLLEGE CODE    : " + head.getCollegeCode());
        UI.separator();

        if (collegeRequests.isEmpty()) {
            UI.liner("  No room makeUpSchedules available for your college.");
            UI.footer();
            return;
        }

        for (int i = 0; i < collegeRequests.size(); i++) {

            Request r = collegeRequests.get(i);

            UI.liner(String.format(
                    "  [%d] %s | Faculty: %s | Room: %s | Status: %s",
                    i + 1,
                    r.getRequestID(),
                    r.getFacultyID(),
                    r.getRoomCode(),
                    r.getStatus()));

            UI.liner("      Room College: " + r.getTargetCollegeCode()
                    + " | " + r.getRequestedDate() + " | "
                    + formatTime(r.getStartTime()) + " - "
                    + formatTime(r.getEndTime()));

            if (i < collegeRequests.size() - 1)
                UI.separator();
        }

        UI.separator();
        UI.liner("  [0] Back");
        UI.footer();

        System.out.print("  Select Request to view > ");
        String input = in.nextLine().trim();

        int choice;

        try {
            choice = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            UI.print("Invalid input.");
            return;
        }

        if (choice == 0)
            return;

        if (choice >= 1 && choice <= collegeRequests.size()) {

            Request selectedRequest = collegeRequests.get(choice - 1);

            if (!canReviewRequest(head, selectedRequest)) {
                UI.print("You cannot access makeUpSchedules outside your college.");
                return;
            }

            displayRequest(selectedRequest);

        } else {
            UI.print("Invalid request selection.");
        }
    }

    // VIEW REQUESTS BY FACULTY ID
    public static void viewMyRequests(String facultyID) {
        Faculty faculty = Faculty.findFaculty(facultyID);

        if (faculty == null) {
            UI.print("Faculty not found.");
            return;
        }

        ArrayList<Request> facultyRequests = getRequestsByFaculty(faculty.getUserID());

        UI.header("MY ROOM REQUESTS");
        UI.liner("  FACULTY ID   : " + faculty.getUserID());
        UI.liner("  FACULTY NAME : " + faculty.getUserName());
        UI.liner("  TOTAL REQUESTS : " + facultyRequests.size());

        if (facultyRequests.isEmpty()) {
            UI.liner("");
            UI.liner("       No room requests found.");
            UI.liner("       Your submitted requests will appear here.");
            UI.liner("");
        } else {
            for (Request request : facultyRequests) {
                String status = request.getStatus() == null ? "Unknown" : request.getStatus();
                UI.separator();
                UI.liner("  REQUEST #" + request.getRequestID());
                UI.liner(String.format("  %-12s : %.48s", "STATUS", status));
                UI.liner(String.format("  %-12s : %.48s", "ROOM",
                        request.getRoomCode() == null ? "-" : request.getRoomCode()));
                UI.liner(String.format("  %-12s : %.48s", "DATE",
                        request.getRequestedDate() == null ? "-" : request.getRequestedDate()));
                UI.liner(String.format("  %-12s : %.48s", "TIME",
                        formatTime(request.getStartTime()) + " - "
                                + formatTime(request.getEndTime())));
                UI.liner(String.format("  %-12s : %.48s", "REASON",
                        request.getReason() == null || request.getReason().trim().isEmpty()
                                ? "-" : request.getReason()));

                if (request.getReviewedBy() != null && !request.getReviewedBy().trim().isEmpty()) {
                    UI.liner("  REVIEW DETAILS");
                    UI.liner(String.format("  %-12s : %.48s", "REVIEWED BY", request.getReviewedBy()));
                    UI.liner(String.format("  %-12s : %.48s", "REVIEWED AT", request.getReviewedAt() == null
                                    ? "-" : request.getReviewedAt()));
                }

                if (request.getRejectionReason() != null && !request.getRejectionReason().trim().isEmpty()) {
                    UI.liner(String.format("  %-12s : %.48s",
                            "REJECTION", request.getRejectionReason()));
                }
            }
        }

        UI.footer();
    }
}