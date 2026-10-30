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

    private final String requestID, facultyID, offeringID, roomCode, targetCollegeCode, requestedDate, startTime, endTime, reason;
    private final boolean valid;
    private String status, reviewedBy, reviewedAt, rejectionReason;

    // CONSTRUCTOR
    public Request(String facultyID, String roomCode, String requestedDate,
                   String startTime, String endTime, String reason) {
        this(facultyID, "", roomCode, requestedDate, startTime, endTime, reason);
    }

    public Request(String facultyID, String offeringID, String roomCode, String requestedDate,
                   String startTime, String endTime, String reason) {
        Faculty faculty = Faculty.findFaculty(facultyID);
        CourseOffering offering = offeringID == null || offeringID.trim().isEmpty()
                ? null : CourseOffering.findOffering(offeringID);
        Room room = Room.findRoom(roomCode);
        LocalDate date = UI.parseDate(requestedDate);
        String convertedStart = convertTime(startTime);
        String convertedEnd = convertTime(endTime);
        boolean offeringValid = offeringID == null || offeringID.trim().isEmpty()
                || (offering != null && offering.getFacultyID() != null
                && offering.getFacultyID().equalsIgnoreCase(facultyID)
                && Course.findCourse(offering.getCourseCode()) != null
                && Section.findSection(offering.getSectionID()) != null);
        boolean detailsValid = faculty != null && room != null && date != null && offeringValid
                && convertedStart != null && !convertedStart.isEmpty()
                && convertedEnd != null && !convertedEnd.isEmpty()
                && reason != null && !reason.trim().isEmpty();

        if (detailsValid) {
            try {
                detailsValid = LocalTime.parse(convertedStart, TIME_FORMAT)
                        .isBefore(LocalTime.parse(convertedEnd, TIME_FORMAT));
            } catch (DateTimeParseException e) {
                detailsValid = false;
            }
        }

        this.requestID = String.format("RR%04d", nextRequestID++);
        this.facultyID = faculty == null ? safeText(facultyID) : faculty.getUserID();
        this.offeringID = offering == null ? "" : offering.getOfferingID();
        this.roomCode = room == null ? safeText(roomCode) : room.getRoomCode();
        this.targetCollegeCode = room == null ? "" : safeText(room.getCollegeCode());
        this.requestedDate = date == null ? "" : date.format(DATE_FORMAT);
        this.startTime = convertedStart == null ? "" : convertedStart;
        this.endTime = convertedEnd == null ? "" : convertedEnd;
        this.reason = safeText(reason);
        this.valid = detailsValid && !this.targetCollegeCode.isEmpty();
        this.status = valid ? "Pending" : "Invalid";
        this.reviewedBy = null;
        this.reviewedAt = null;
        this.rejectionReason = null;

        if (!valid) UI.print("Request could not be created because one or more details are invalid.");
    }

    private static String safeText(String value) {
        return value == null ? "" : value.trim();
    }

    public boolean isValid() {
        return valid;
    }

    // DUMMY DATA
    static {
        requests.add(new Request("F0001", "RM001", "10/28/2026",
                "9:00 AM", "10:00 AM", "Room needed for class"));
        requests.add(new Request("F0002", "RM002", "10/28/2026",
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

        requests.add(new Request("F0009","RM006","11/13/2026","6:00 PM","7:00 PM","Additional lecture session"));
        requests.add(new Request("F0011","RM007","11/16/2026","9:00 AM","10:00 AM","Room needed for class"));
        requests.add(new Request("F0012","RM009","11/17/2026","10:00 AM","11:00 AM","Make-up laboratory session"));
        requests.add(new Request("F0014","RM012","11/18/2026","1:00 PM","2:00 PM","Room change due to equipment"));
        requests.add(new Request("F0015","RM014","11/19/2026","2:00 PM","3:00 PM","Additional review class"));
    }

    // REQUEST GETTER METHODS
    public String getRequestID() {
        return requestID;
    }
    public String getFacultyID() {
        return facultyID;
    }
    public String getOfferingID() {
        return offeringID;
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
        if (f.getEmploymentStatus() == null || !f.getEmploymentStatus().equalsIgnoreCase("ACTIVE")) {
            UI.print("Only active faculty members may submit room requests.");
            return;
        }

        String facultyCollegeCode = f.getCollegeCode();

        String requestedDate, startTime, endTime, roomCode, reason;
        LocalDate date;
        String convertedStart, convertedEnd;

        UI.header("REQUEST ROOM RESERVATION", false);
        UI.liner("  FACULTY ID : " + f.getUserID(), false);
        UI.liner("  NAME       : " + f.getUserName(), false);
        UI.liner("  COLLEGE    : "
                + (facultyCollegeCode == null ? "Not assigned" : facultyCollegeCode), false);
        UI.footer(false);

        // SELECT A COURSE OFFERING ASSIGNED TO THIS FACULTY MEMBER
        ArrayList<CourseOffering> facultyOfferings = CourseOffering.getOfferingsByFaculty(f.getUserID());
        if (facultyOfferings.isEmpty()) {
            UI.print("No course offerings are assigned to your account. A room request cannot be submitted.");
            return;
        }

        UI.header("MY COURSE OFFERINGS", true);
        UI.liner(String.format("  %-14s %-14s %-22s %-18s %-14s %-8s %-82s",
                "OFFERING ID", "COURSE", "SECTION", "ACADEMIC YEAR", "FACULTY ID", "TERM", "COURSE DESCRIPTION"), true);
        UI.separator(true);
        for (CourseOffering offering : facultyOfferings) {
            if (offering == null) continue;
            Section section = Section.findSection(offering.getSectionID());
            UI.liner(String.format("  %-14s %-14s %-22s %-18s %-14s %-8d %-82s",
                    offering.getOfferingID(), offering.getCourseCode(),
                    section == null ? "-" : Section.getYearSection(section),
                    offering.getAcademicYear(), offering.getFacultyID(), offering.getTerm(),
                    UI.fitText(Course.findCourse(offering.getCourseCode()) == null ? "Course unavailable"
                            : Course.findCourse(offering.getCourseCode()).getCourseDescription(), 82)), true);
        }
        UI.footer(true);

        String offeringID;
        CourseOffering selectedOffering;
        do {
            UI.select("Enter Offering ID", false);
            offeringID = in.nextLine().trim();
            if (offeringID.equalsIgnoreCase("X")) return;
            selectedOffering = CourseOffering.findOffering(offeringID);
            if (selectedOffering == null || !selectedOffering.getFacultyID().equalsIgnoreCase(f.getUserID()))
                UI.print("Select a course offering assigned to your account.");
        } while (selectedOffering == null || !selectedOffering.getFacultyID().equalsIgnoreCase(f.getUserID()));

        // ENTER FUTURE RESERVATION DATE
        do {
            UI.select("Enter Date (MM/dd/yyyy)",true);
            requestedDate = in.nextLine().trim();
            if(requestedDate.equalsIgnoreCase("X")){
                return;
            }

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
            UI.select("Enter Start Time (hh:mm AM/PM)", true);
            startTime = in.nextLine().trim();
            if (startTime.equalsIgnoreCase("X")) return;

            UI.select("Enter End Time (hh:mm AM/PM)", true);
            endTime = in.nextLine().trim();
            if (endTime.equalsIgnoreCase("X")) return;

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
        UI.header("ROOMS AVAILABLE FOR REQUESTED TIME", true);
        UI.liner("  DATE: " + date.format(DATE_FORMAT) + " | TIME: "
                + formatTime(convertedStart) + " - " + formatTime(convertedEnd), true);
        String roomFormat = "  %-12s %-57s %-24s %-14s %-14s %-32s %-19s";
        UI.liner(String.format(roomFormat, "ROOM CODE", "DESCRIPTION", "ROOM TYPE", "CAPACITY",
                "FLOOR", "BUILDING", "COLLEGE"), true);
        UI.separator(true);
        int availableCount = 0;
        for (Room availableRoom : Room.rooms) {
            if (availableRoom == null || !Room.verifyAvailability(availableRoom.getRoomCode(),
                    date.format(DATE_FORMAT), convertedStart, convertedEnd)) continue;
            Building roomBuilding = Building.findBuilding(availableRoom.getBuildingCode());
            UI.liner(String.format(roomFormat, availableRoom.getRoomCode(),
                    UI.fitText(availableRoom.getRoomDescription(), 57),
                    UI.fitText(availableRoom.getRoomType(), 24), availableRoom.getCapacity() + " seats",
                    UI.fitText(Room.getFloorText(availableRoom.getFloorNumber()), 14),
                    UI.fitText(roomBuilding == null ? "Unavailable" : roomBuilding.getBuildingName(), 32),
                    availableRoom.getCollegeCode()), true);
            availableCount++;
        }
        UI.separator(true);
        UI.liner("  AVAILABLE ROOMS: " + availableCount, true);
        UI.footer(true);

        if (availableCount == 0) {
            UI.print("No rooms are available for this date and time.");
            return;
        }

        // SELECT A ROOM THAT IS STILL AVAILABLE
        Room room;
        do {
            UI.select("Enter Room Code", false);
            roomCode = in.nextLine().trim();
            if (roomCode.equalsIgnoreCase("X")) return;
            room = Room.findRoom(roomCode);
            if (room == null) UI.print("Room not found or archived.");
            else if (!Room.verifyAvailability(roomCode, date.format(DATE_FORMAT), convertedStart, convertedEnd)) {
                UI.print("The selected room is not available for that time.");
                room = null;
            }
        } while (room == null);

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
        UI.header("REQUEST ROUTING", false);
        UI.liner("  SELECTED ROOM : " + room.getRoomCode(), false);
        UI.liner("  ROOM          : " + room.getRoomDescription(), false);
        UI.liner("  ROOM COLLEGE  : " + targetCollegeCode, false);
        UI.liner("  ROUTED TO     : " + head.getUserName(), false);
        UI.footer(false);

        do {
            UI.select("Enter Reason", true);
            reason = in.nextLine().trim();
            if (reason.equalsIgnoreCase("X")) return;
            if (reason.isEmpty()) UI.print("Reason cannot be empty.");

        } while (reason.isEmpty());

        Request request = new Request(facultyID, selectedOffering.getOfferingID(), roomCode,
                date.format(DATE_FORMAT), convertedStart, convertedEnd, reason);
        if (!request.isValid()) return;

        String buildingName = "";
        Building building = Building.findBuilding(room.getBuildingCode());
        if (building != null) buildingName = building.getBuildingName();

        String message = "New room request " + request.getRequestID() + " requires your review.";
        if (!Notification.sendNotification(f.getUserID(), head.getUserID(), selectedOffering.getCourseCode(), room.getRoomCode(),
                buildingName, date.getDayOfWeek().getDisplayName(java.time.format.TextStyle.FULL, Locale.ENGLISH),
                convertedStart, convertedEnd, message, selectedOffering.getOfferingID(), request.getRequestID(),
                date.format(DATE_FORMAT))) {
            UI.print("The request was not submitted because the Department Head could not be notified.");
            return;
        }

        requests.add(request);
        UI.print("Room request submitted successfully and routed to " + head.getUserName() + ".");
        displayRequest(request.getRequestID(), true);
    }

    // DISPLAY REQUEST DETAILS
    public static void displayRequest(String requestID,boolean print) {

        Request request = findRequest(requestID);
        if (request == null) {
            UI.print("Request not found.");
            return;
        }

        Faculty faculty = Faculty.findFaculty(request.facultyID);
        Room room = Room.findRoom(request.roomCode);

        UI.header("ROOM RESERVATION REQUEST", false);
        UI.liner("  REQUEST ID       : " + request.requestID, false);
        UI.liner("  FACULTY ID       : " + request.facultyID, false);
        UI.liner("  FACULTY          : " + (faculty == null ? "Faculty unavailable" : faculty.getUserName()), false);
        UI.liner("  FACULTY COLLEGE  : " + (faculty == null ? "N/A" : faculty.getCollegeCode()), false);
        Building requestBuilding = room == null ? null : Building.findBuilding(room.getBuildingCode());
        UI.liner("  ROOM CODE        : " + request.roomCode, false);
        UI.liner("  ROOM             : " + (room == null ? "Room unavailable or archived" : room.getRoomDescription()), false);
        UI.liner("  BUILDING CODE    : " + (room == null ? "-" : room.getBuildingCode()), false);
        UI.liner("  BUILDING         : " + (requestBuilding == null ? "-" : requestBuilding.getBuildingName()), false);
        UI.liner("  ROOM COLLEGE     : " + request.targetCollegeCode, false);
        CourseOffering offering = CourseOffering.findOffering(request.offeringID);
        Course course = offering == null ? null : Course.findCourse(offering.getCourseCode());
        Section section = offering == null ? null : Section.findSection(offering.getSectionID());
        UI.liner("  OFFERING ID      : " + (offering == null ? "-" : offering.getOfferingID()), false);
        UI.liner("  COURSE           : " + (course == null ? "-" : course.getCourseCode()), false);
        UI.liner("  SECTION          : " + (section == null ? "-" : Section.getYearSection(section)), false);
        UI.liner("  DATE             : " + request.requestedDate, false);
        UI.liner("  TIME             : " + formatTime(request.startTime) + " - " + formatTime(request.endTime), false);
        UI.liner("  STATUS           : " + request.status, false);
        UI.separator(false);
        UI.liner("  REASON           : " + request.reason, false);

        if (request.reviewedBy != null) {
            UI.liner("  REVIEWED BY      : " + request.reviewedBy, false);
            UI.liner("  REVIEWED AT      : " + request.reviewedAt, false);
        }

        if (request.rejectionReason != null)
            UI.liner("  REJECTION REASON : " + request.rejectionReason, false);

        UI.footer(false);
        if(print) UI.returnButton();
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
        DepartmentHead head = DepartmentHead.findDepartmentHead(departmentHeadID);

        if (head == null) {
            UI.print("Department Head not found.");
            return;
        }

        ArrayList<Request> collegeRequests = new ArrayList<>();
        for (Request request : requests) {
            if (request != null && canReviewRequest(head, request))
                collegeRequests.add(request);
        }

        UI.header("ROOM REQUEST INBOX", true);
        UI.liner("  DEPARTMENT HEAD: " + head.getUserName()
                + " | COLLEGE: " + head.getCollegeCode(), true);

        if (collegeRequests.isEmpty()) {
            UI.liner("  No room requests found for your college.", true);
            UI.footer(true);
            UI.returnButton();
            return;
        }

        String format = " %-4s %-10s %-12s %-22s %-10s %-12s %-9s %-12s %-20s %-11s %-48s";
        UI.liner(String.format(format, " NO.", "REQUEST", "FACULTY ID", "FACULTY NAME", "COURSE",
                "SECTION", "ROOM", "DATE", "TIME", "STATUS", "REASON"), true);
        UI.separator(true);

        for (int i = 0; i < collegeRequests.size(); i++) {
            Request request = collegeRequests.get(i);
            Faculty faculty = Faculty.findFaculty(request.getFacultyID());
            CourseOffering offering = CourseOffering.findOffering(request.getOfferingID());
            Section section = offering == null ? null : Section.findSection(offering.getSectionID());
            UI.liner(String.format(format, i + 1, request.getRequestID(), request.getFacultyID(),
                    UI.fitText(faculty == null ? "-" : faculty.getUserName(), 22),
                    offering == null ? "-" : offering.getCourseCode(),
                    UI.fitText(section == null ? "-" : Section.getYearSection(section), 12),
                    request.getRoomCode(), request.getRequestedDate(),
                    UI.fitText(formatTime(request.getStartTime()) + " - " + formatTime(request.getEndTime()), 20),
                    request.getStatus(), request.getReason()), true);
        }

        UI.footer(true);

        String input;
        int choice = -1;
        do {
            UI.select("Select Request Number", false);
            input = in.nextLine().trim();
            if (input.equalsIgnoreCase("X")) return;

            try {
                choice = Integer.parseInt(input);
                if (choice < 1 || choice > collegeRequests.size()) {
                    UI.print("Choose a listed request number.");
                    choice = -1;
                }
            } catch (NumberFormatException e) {
                UI.print("Enter a valid request number.");
                choice = -1;
            }
        } while (choice == -1);

        Request selected = collegeRequests.get(choice - 1);
        if (!canReviewRequest(head, selected)) {
            UI.print("You cannot access requests outside your college.");
            return;
        }

        displayRequest(selected.getRequestID(), false);
        if (!"Pending".equalsIgnoreCase(selected.getStatus())) {
            UI.print("This request has already been reviewed.");
            return;
        }

        reviewRequest(departmentHeadID, selected.getRequestID());
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
            UI.print("You cannot review requests outside your college.");
            return;
        }

        if (!"Pending".equalsIgnoreCase(r.status)) {
            UI.print("This request has already been reviewed.");
            return;
        }

        String decision;

        do {
            UI.select("Approve or Reject? (A/R)", false);
            decision = in.nextLine().trim().toUpperCase(Locale.ENGLISH);
            if (decision.equals("X")) {
                UI.print("Request review canceled. No changes were made.");
                return;
            }
            if (!decision.equals("A") && !decision.equals("R")) UI.print("Invalid choice.");
        } while (!decision.equals("A") && !decision.equals("R"));

        String rejectionReason = null;

        if (decision.equals("R")) {
            do {
                UI.select("Rejection Reason", false);
                rejectionReason = in.nextLine().trim();

                if (rejectionReason.equalsIgnoreCase("X")) {
                    UI.print("Request review canceled. No changes were made.");
                    return;
                }
                if (rejectionReason.isEmpty())
                    UI.print("Rejection reason is required.");

            } while (rejectionReason.isEmpty());
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
            if (r.offeringID.isEmpty()) {
                UI.print("This older request has no Course Offering reference and cannot create a course-linked schedule.");
                UI.print("The request remains pending. Submit a new request linked to one of your Course Offerings.");
                return;
            }
            CourseOffering requestOffering = CourseOffering.findOffering(r.offeringID);
            if (requestOffering == null || requestOffering.getFacultyID() == null
                    || !requestOffering.getFacultyID().equalsIgnoreCase(r.facultyID)
                    || Course.findCourse(requestOffering.getCourseCode()) == null
                    || Section.findSection(requestOffering.getSectionID()) == null) {
                UI.print("The selected course offering is no longer active or its relationships are incomplete.");
                UI.print("The request remains pending.");
                return;
            }

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

            // A PAST RESERVATION CANNOT BE APPROVED
            if (date.isBefore(LocalDate.now())) {
                UI.print("This request date has already passed and cannot be approved.");
                UI.print("Reject the request with a reason instead.");
                return;
            }

            LocalTime requestedStart = LocalTime.parse(r.startTime, TIME_FORMAT);
            if (date.equals(LocalDate.now()) && !requestedStart.isAfter(LocalTime.now())) {
                UI.print("The requested start time has already passed and cannot be approved.");
                UI.print("Reject the request with a reason instead.");
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

            Faculty requestingFaculty = Faculty.findFaculty(r.facultyID);
            if (requestingFaculty == null) {
                UI.print("The requesting faculty account is no longer active. Request remains pending.");
                return;
            }

            MakeUpSchedule approved = new MakeUpSchedule(r.facultyID, r.roomCode, "Reservation",
                    date.format(DATE_FORMAT), r.startTime, r.endTime, r.reason,
                    departmentHeadID.trim(), reviewedAt.trim(), r.offeringID, r.requestID);
            if (!approved.isValid()) {
                UI.print("The schedule could not be created. Request remains pending.");
                return;
            }

            String buildingName = "";
            Building building = Building.findBuilding(room.getBuildingCode());
            if (building != null) buildingName = building.getBuildingName();
            String message = "Your room request " + r.requestID + " was approved.";
            if (!Notification.sendNotification(head.getUserID(), requestingFaculty.getUserID(),
                    r.offeringID.isEmpty() ? "" : CourseOffering.findOffering(r.offeringID).getCourseCode(),
                    room.getRoomCode(), buildingName,
                    date.getDayOfWeek().getDisplayName(java.time.format.TextStyle.FULL, Locale.ENGLISH),
                    r.startTime, r.endTime, message, r.offeringID, r.requestID, date.format(DATE_FORMAT))) {
                UI.print("Approval was not committed because the faculty notification could not be sent.");
                return;
            }

            MakeUpSchedule.makeUpSchedules.add(approved);
            if (!r.offeringID.isEmpty()) {
                for (CourseRegistration registration : CourseRegistration.getRegistrationsByOffering(r.offeringID)) {
                    if (registration == null || registration.getStudentID() == null) continue;
                    Student enrolledStudent = Student.findStudent(registration.getStudentID());
                    if (enrolledStudent != null) {
                        Notification.sendNotification(head.getUserID(), enrolledStudent.getUserID(),
                                CourseOffering.findOffering(r.offeringID).getCourseCode(), room.getRoomCode(),
                                buildingName, date.getDayOfWeek().getDisplayName(
                                        java.time.format.TextStyle.FULL, Locale.ENGLISH),
                                r.startTime, r.endTime, "Approved room reservation for " + r.requestID + ".",
                                r.offeringID, r.requestID, date.format(DATE_FORMAT));
                    }
                }
            }
            r.status = "Approved";
            r.reviewedBy = departmentHeadID.trim();
            r.reviewedAt = reviewedAt.trim();
            UI.print("Request approved and faculty notified successfully.");

        } else {
            Faculty requestingFaculty = Faculty.findFaculty(r.facultyID);
            if (requestingFaculty == null) {
                UI.print("The requesting faculty account is no longer active. Request remains pending.");
                return;
            }

            String message = "Your room request " + r.requestID + " was rejected. Reason: " + rejectionReason;
            Room rejectedRoom = Room.findRoom(r.roomCode);
            Building rejectedBuilding = rejectedRoom == null ? null : Building.findBuilding(rejectedRoom.getBuildingCode());
            CourseOffering rejectedOffering = CourseOffering.findOffering(r.offeringID);
            LocalDate rejectedDate = UI.parseDate(r.requestedDate);
            String rejectedDay = rejectedDate == null ? "" : rejectedDate.getDayOfWeek().getDisplayName(
                    java.time.format.TextStyle.FULL, Locale.ENGLISH);
            if (!Notification.sendNotification(head.getUserID(), requestingFaculty.getUserID(),
                    rejectedOffering == null ? "" : rejectedOffering.getCourseCode(),
                    r.roomCode, rejectedBuilding == null ? "" : rejectedBuilding.getBuildingName(),
                    rejectedDay, r.startTime, r.endTime, message, r.offeringID, r.requestID, r.requestedDate)) {
                UI.print("Rejection was not committed because the faculty notification could not be sent.");
                return;
            }

            r.status = "Rejected";
            r.reviewedBy = departmentHeadID.trim();
            r.reviewedAt = reviewedAt.trim();
            r.rejectionReason = rejectionReason;
            UI.print("Request rejected and faculty notified.");
        }
        UI.returnButton();
    }

    // VIEW REQUESTS BY DEPARTMENT HEAD'S COLLEGE
    public static void viewRequest(String departmentHeadID) {
        DepartmentHead head = DepartmentHead.findDepartmentHead(departmentHeadID);
        if (head == null) {
            UI.print("Department Head not found.");
            return;
        }

        ArrayList<Request> collegeRequests = new ArrayList<>();
        for (Request request : requests) {
            if (request != null && canReviewRequest(head, request))
                collegeRequests.add(request);
        }

        UI.header("ROOM REQUEST LIST", true);
        UI.liner("  DEPARTMENT HEAD: " + head.getUserName()
                + " | COLLEGE: " + head.getCollegeCode(), true);

        if (collegeRequests.isEmpty()) {
            UI.liner("  No room requests found for your college.", true);
            UI.footer(true);
            UI.returnButton();
            return;
        }

        String format = " %-4s %-10s %-12s %-22s %-10s %-12s %-9s %-12s %-20s %-11s %-48s";
        UI.liner(String.format(format, " NO.", "REQUEST", "FACULTY ID", "FACULTY NAME", "COURSE",
                "SECTION", "ROOM", "DATE", "TIME", "STATUS", "REASON"), true);
        UI.separator(true);
        for (int i = 0; i < collegeRequests.size(); i++) {
            Request request = collegeRequests.get(i);
            Faculty faculty = Faculty.findFaculty(request.getFacultyID());
            CourseOffering offering = CourseOffering.findOffering(request.getOfferingID());
            Section section = offering == null ? null : Section.findSection(offering.getSectionID());
            UI.liner(String.format(format, i + 1, request.getRequestID(), request.getFacultyID(),
                    UI.fitText(faculty == null ? "-" : faculty.getUserName(), 22),
                    offering == null ? "-" : offering.getCourseCode(),
                    UI.fitText(section == null ? "-" : Section.getYearSection(section), 12),
                    request.getRoomCode(), request.getRequestedDate(),
                    UI.fitText(formatTime(request.getStartTime()) + " - " + formatTime(request.getEndTime()), 20),
                    request.getStatus(), request.getReason()), true);
        }

        UI.footer(true);

        int choice = -1;
        do {
            UI.select("Select Request Number", false);
            String input = in.nextLine().trim();
            if (input.equalsIgnoreCase("X")) return;

            try {
                choice = Integer.parseInt(input);
                if (choice < 1 || choice > collegeRequests.size()) {
                    UI.print("Choose a listed request number.");
                    choice = -1;
                }
            } catch (NumberFormatException e) {
                UI.print("Enter a valid request number.");
                choice = -1;
            }
        } while (choice == -1);

        Request selectedRequest = collegeRequests.get(choice - 1);
        if (!canReviewRequest(head, selectedRequest)) {
            UI.print("You cannot access requests outside your college.");
            return;
        }

        displayRequest(selectedRequest.getRequestID(), true);
    }

    // VIEW REQUESTS BY FACULTY ID
    public static void viewMyRequests(String facultyID) {
        Faculty faculty = Faculty.findFaculty(facultyID);
        if (faculty == null) {
            UI.print("Faculty not found.");
            return;
        }

        ArrayList<Request> facultyRequests = getRequestsByFaculty(faculty.getUserID());
        UI.header("MY ROOM REQUESTS", true);
        UI.liner("  FACULTY: " + faculty.getUserName()
                + " | TOTAL REQUESTS: " + facultyRequests.size(), true);

        if (facultyRequests.isEmpty()) {
            UI.liner("  No room requests found. Your submitted requests will appear here.", true);
        } else {
            String format = " %-10s %-10s %-10s %-12s %-10s %-24s %-12s %-20s %-11s %-52s";
            UI.separator(true);
            UI.liner(String.format(format, " REQUEST", "OFFERING", "COURSE", "SECTION", "ROOM",
                    "BUILDING", "DATE", "TIME", "STATUS", "REASON"), true);
            UI.separator(true);

            for (Request request : facultyRequests) {
                if (request == null) continue;
                CourseOffering offering = CourseOffering.findOffering(request.getOfferingID());
                Course course = offering == null ? null : Course.findCourse(offering.getCourseCode());
                Section section = offering == null ? null : Section.findSection(offering.getSectionID());
                Room room = Room.findRoom(request.getRoomCode());
                Building building = room == null ? null : Building.findBuilding(room.getBuildingCode());
                UI.liner(String.format(format, request.getRequestID(),
                        offering == null ? "-" : offering.getOfferingID(),
                        course == null ? "-" : course.getCourseCode(),
                        UI.fitText(section == null ? "-" : Section.getYearSection(section), 12),
                        request.getRoomCode(), UI.fitText(building == null ? "-" : building.getBuildingName(), 24),
                        request.getRequestedDate(),
                        UI.fitText(formatTime(request.getStartTime()) + " - " + formatTime(request.getEndTime()), 20),
                        request.getStatus(), request.getReason()), true);
            }
        }

        UI.footer(true);
        UI.returnButton();
    }
}