
package DATA;

import USER.*;
import java.util.ArrayList;

public class MakeUpSchedule {

    // ROOM REQUEST FIELDS
    private static int nextRequestID = 1;
    public static ArrayList<MakeUpSchedule> requests = new ArrayList<>();

    private String requestID;
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

        if (facultyID == null || facultyID.trim().isEmpty()) {
            throw new IllegalArgumentException("Faculty ID is required.");
        }

        facultyID = facultyID.trim();

        if (Faculty.findFaculty(facultyID) == null) {
            throw new IllegalArgumentException("Invalid faculty ID.");
        }

        if (roomCode != null) {
            roomCode = roomCode.trim();

            if (!roomCode.isEmpty() && Room.findRoom(roomCode) == null) {
                throw new IllegalArgumentException("Invalid room code.");
            }
        }

        if (requestType == null || requestType.trim().isEmpty()) {
            throw new IllegalArgumentException("Request type is required.");
        }

        requestType = requestType.trim();

        if (requestType.equalsIgnoreCase("Reservation")) {
            requestType = "Reservation";
        } else if (requestType.equalsIgnoreCase("Room Change")) {
            requestType = "Room Change";
        } else if (requestType.equalsIgnoreCase("Make-up Class")) {
            requestType = "Make-up Class";
        } else {
            throw new IllegalArgumentException("Invalid request type.");
        }

        if (requestedDate == null || requestedDate.trim().isEmpty()
                || startTime == null || startTime.trim().isEmpty()
                || endTime == null || endTime.trim().isEmpty()
                || reason == null || reason.trim().isEmpty()) {
            throw new IllegalArgumentException("Request details cannot be empty.");
        }

        requestedDate = requestedDate.trim();
        startTime = startTime.trim();
        endTime = endTime.trim();
        reason = reason.trim();

        int start, end;

        try {
            start = Room.convertToMinutes(startTime);
            end = Room.convertToMinutes(endTime);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid time format.");
        }

        if (start >= end) {
            throw new IllegalArgumentException(
                    "Start time must be before end time.");
        }

        this.requestID = String.format("RR%04d", nextRequestID++);
        this.facultyID = facultyID;
        this.roomCode = roomCode == null || roomCode.isEmpty()
                ? null : roomCode;
        this.requestType = requestType;
        this.requestedDate = requestedDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.reason = reason;
        this.status = "Pending";
        this.reviewedBy = null;
        this.reviewedAt = null;

        requests.add(this);
    }

    // ROOM REQUEST GETTER METHODS
    public String getRequestID() {
        return requestID;
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
            if (requestID.equalsIgnoreCase(request.requestID)) {
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
}