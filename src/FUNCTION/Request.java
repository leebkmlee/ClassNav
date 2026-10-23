
package FUNCTION;

import DATA.*;
import USER.*;
import UI.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
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

    static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("MM/dd/uuuu")
                    .withResolverStyle(ResolverStyle.STRICT);

    // INTERNAL TIME FORMAT
    public static final DateTimeFormatter TIME_FORMAT =
            DateTimeFormatter.ofPattern("HH:mm");

    // DISPLAY AND INPUT TIME FORMAT
    static final DateTimeFormatter DISPLAY_TIME_FORMAT =
            DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH);

    // CONVERT INPUT TIME TO INTERNAL 24-HOUR FORMAT
    static String convertTime(String time) {

        if (time == null || time.trim().isEmpty())
            throw new IllegalArgumentException("Time cannot be empty.");

        try {
            LocalTime parsedTime = LocalTime.parse(
                    time.trim(), DISPLAY_TIME_FORMAT);

            return parsedTime.format(TIME_FORMAT);

        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                    "Invalid time. Use hh:mm AM/PM, such as 02:30 PM.");
        }
    }

    // FORMAT INTERNAL TIME FOR DISPLAY
    static String formatTime(String time) {

        if (time == null || time.trim().isEmpty())
            return "";

        try {
            return LocalTime.parse(time.trim(), TIME_FORMAT)
                    .format(DISPLAY_TIME_FORMAT);

        } catch (DateTimeParseException e) {
            // Also support existing records already stored in AM/PM format
            try {
                return LocalTime.parse(time.trim(), DISPLAY_TIME_FORMAT)
                        .format(DISPLAY_TIME_FORMAT);
            } catch (DateTimeParseException ex) {
                return time;
            }
        }
    }

    // CHECK IF DEPARTMENT HEAD CAN ACCESS A REQUEST
    private static boolean canReviewRequest(
            DepartmentHead head, Request request) {

        if (head == null || request == null
                || head.getCollegeCode() == null
                || head.getCollegeCode().trim().isEmpty()) {
            return false;
        }

        Faculty faculty = Faculty.findFaculty(request.facultyID);

        if (faculty == null)
            return false;

        String facultyCollegeCode = Faculty.getCollegeCode(faculty);

        return facultyCollegeCode != null
                && !facultyCollegeCode.trim().isEmpty()
                && head.getCollegeCode().equalsIgnoreCase(
                facultyCollegeCode);
    }

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
            throw new IllegalArgumentException(
                    "Request details cannot be empty.");
        }

        LocalDate date;

        try {
            date = LocalDate.parse(requestedDate.trim(), DATE_FORMAT);

            if (date.isBefore(LocalDate.now()))
                throw new IllegalArgumentException(
                        "Request date cannot be in the past.");

        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                    "Invalid date. Use MM/dd/yyyy.");
        }

        String convertedStart = convertTime(startTime);
        String convertedEnd = convertTime(endTime);

        // CONVERT INTERNAL 24-HOUR TIME DIRECTLY TO MINUTES
        LocalTime parsedStart = LocalTime.parse(convertedStart, TIME_FORMAT);
        LocalTime parsedEnd = LocalTime.parse(convertedEnd, TIME_FORMAT);

        int start = parsedStart.getHour() * 60 + parsedStart.getMinute();
        int end = parsedEnd.getHour() * 60 + parsedEnd.getMinute();

        if (start >= end)
            throw new IllegalArgumentException(
                    "Start time must be before end time.");

        this.requestID = String.format("RR%04d", nextRequestID++);
        this.facultyID = facultyID.trim();
        this.roomCode = roomCode.trim();
        this.requestedDate = date.format(DATE_FORMAT);
        this.startTime = convertedStart;
        this.endTime = convertedEnd;
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

        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        String currentDate = today.format(DATE_FORMAT);
        String currentTime = now.format(TIME_FORMAT);
        String checkEndTime = now.plusMinutes(1).format(TIME_FORMAT);

        UI.liner("  DATE : " + currentDate);
        UI.liner("  TIME : " + now.format(DISPLAY_TIME_FORMAT));
        UI.footer();

        // DISPLAY ROOMS AVAILABLE RIGHT NOW
        boolean available = false;

        UI.header("ROOMS AVAILABLE RIGHT NOW");

        for (Room room : Room.rooms) {

            if (room == null)
                continue;

            if (Room.verifyAvailability(
                    room.getRoomCode(),
                    currentDate,
                    currentTime,
                    checkEndTime)) {

                UI.liner("  " + room.getRoomCode()
                        + " - " + room.getRoomDescription());
                UI.liner("  Type: " + room.getRoomType()
                        + " | Capacity: " + room.getCapacity());
                UI.liner("  Floor: "
                        + Room.getFloorText(room.getFloorNumber()));
                UI.liner("");

                available = true;
            }
        }

        if (!available)
            UI.liner("  No rooms are currently available.");

        UI.footer();

        if (!available) return;

        // ENTER RESERVATION DETAILS
        UI.header("REQUEST ROOM RESERVATION");

        System.out.print("  Enter Room Code > ");
        roomCode = in.nextLine().trim();

        System.out.print("  Enter Date (MM/dd/yyyy) > ");
        requestedDate = in.nextLine().trim();

        System.out.print("  Enter Start Time (hh:mm AM/PM) > ");
        startTime = in.nextLine().trim();

        System.out.print("  Enter End Time (hh:mm AM/PM) > ");
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
        UI.liner("  TIME       : " + formatTime(request.startTime)
                + " - " + formatTime(request.endTime));
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

        // ONLY INCLUDE REQUESTS FROM THE SAME COLLEGE
        ArrayList<Request> collegeRequests = new ArrayList<>();

        for (Request request : requests) {
            if (canReviewRequest(head, request))
                collegeRequests.add(request);
        }

        UI.header("ROOM REQUEST INBOX");
        UI.liner("  DEPARTMENT HEAD : " + head.getUserName());
        UI.liner("  COLLEGE CODE    : " + head.getCollegeCode());
        UI.separator();

        UI.liner("  ROOM REQUESTS");
        UI.separator();

        if (collegeRequests.isEmpty()) {
            UI.liner("  No room requests found for your college.");
            UI.footer();
            return;
        }

        for (int i = 0; i < collegeRequests.size(); i++) {

            Request request = collegeRequests.get(i);

            UI.liner(String.format("  [%d] %s | %s | %s | %s",
                    i + 1,
                    request.requestID,
                    request.facultyID,
                    request.roomCode,
                    request.status));

            UI.liner(String.format("      %s | %s - %s",
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

        // RECHECK COLLEGE ACCESS BEFORE DISPLAYING DETAILS
        if (!canReviewRequest(head, selected)) {
            UI.print("You cannot access requests outside your college.");
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

        DepartmentHead head =
                DepartmentHead.findDepartmentHead(departmentHeadID);

        Request request = findRequest(requestID);

        if (head == null || request == null) {
            UI.print("Department head or request not found.");
            return;
        }

        // SECURITY CHECK: ONLY THE SAME COLLEGE CAN REVIEW
        if (!canReviewRequest(head, request)) {
            UI.print("You cannot review requests outside your college.");
            return;
        }

        if (!request.status.equalsIgnoreCase("Pending")) {
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

        String reviewedAt = LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("MM/dd/uuuu hh:mm a",
                        Locale.ENGLISH));

        if (decision.equals("A")) {

            Room room = Room.findRoom(request.roomCode);

            if (room == null) {
                UI.print("Requested room no longer exists.");
                return;
            }

            LocalDate date;

            try {
                date = LocalDate.parse(request.requestedDate, DATE_FORMAT);
            } catch (DateTimeParseException e) {
                UI.print("The request contains an invalid date.");
                return;
            }

            // CHECK AVAILABILITY FOR THE REQUESTED DATE AND TIME
            if (!Room.verifyAvailability(
                    request.roomCode,
                    date.format(DATE_FORMAT),
                    request.startTime,
                    request.endTime)) {
                UI.print("Room is not available for the requested time.");
                return;
            }

            MakeUpSchedule approved = null;

            try {
                approved = new MakeUpSchedule(
                        request.facultyID,
                        request.roomCode,
                        "Reservation",
                        date.format(DATE_FORMAT),
                        request.startTime,
                        request.endTime,
                        request.reason
                );

                approved.reviewRequest(departmentHeadID,
                        "Approved", reviewedAt);

                request.status = "Approved";
                request.reviewedBy = departmentHeadID;
                request.reviewedAt = reviewedAt;

                UI.print("Request approved successfully.");
                UI.print("Make-up schedule record created: "
                        + approved.getRequestID());

            } catch (IllegalArgumentException | IllegalStateException e) {

                // REMOVE THE RECORD IF APPROVAL FAILS
                if (approved != null)
                    MakeUpSchedule.requests.remove(approved);

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

    // VIEW REQUESTS BY DEPARTMENT HEAD'S COLLEGE
    public static void viewRequest(String departmentHeadID) {

        DepartmentHead head =
                DepartmentHead.findDepartmentHead(departmentHeadID);

        if (head == null) {
            UI.print("Department head not found.");
            return;
        }

        ArrayList<Request> collegeRequests = new ArrayList<>();

        // FILTER REQUESTS USING THE FACULTY'S COLLEGE CODE
        for (Request request : requests) {
            if (canReviewRequest(head, request))
                collegeRequests.add(request);
        }

        UI.header("ROOM REQUEST LIST");
        UI.liner("  DEPARTMENT HEAD : " + head.getUserName());
        UI.liner("  COLLEGE CODE    : " + head.getCollegeCode());
        UI.separator();

        if (collegeRequests.isEmpty()) {
            UI.liner("  No room requests available for your college.");
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

            UI.liner("      " + r.getRequestedDate() + " | "
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

            // RECHECK COLLEGE ACCESS BEFORE DISPLAYING DETAILS
            if (!canReviewRequest(head, selectedRequest)) {
                UI.print("You cannot access requests outside your college.");
                return;
            }

            displayRequest(selectedRequest);

        } else {
            UI.print("Invalid request selection.");
        }
    }
}