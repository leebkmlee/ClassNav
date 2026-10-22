
package DATA;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.Locale;

public class CancelledSchedule {

    private static int nextExceptionID = 1;

    private String exceptionID, scheduleID, affectedDate, exceptionType,
            reason, replacementRoomCode, status;

    public static ArrayList<CancelledSchedule> exceptions = new ArrayList<>();

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("MM/dd/uuuu")
                    .withResolverStyle(ResolverStyle.STRICT);

    public CancelledSchedule(String scheduleID, String affectedDate, String exceptionType,
                             String reason, String replacementRoomCode, String status) {

        if (scheduleID == null || Schedule.findSchedule(scheduleID) == null) {
            throw new IllegalArgumentException("Schedule ID does not exist.");
        }

        if (affectedDate == null || affectedDate.trim().isEmpty()) {
            throw new IllegalArgumentException("Affected date cannot be empty.");
        }

        try {
            LocalDate.parse(affectedDate.trim(), DATE_FORMAT);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                    "Invalid affected date. Use MM/DD/YYYY format."
            );
        }

        if (exceptionType == null ||
                (!exceptionType.equalsIgnoreCase("CANCELLATION") &&
                        !exceptionType.equalsIgnoreCase("ROOM CHANGE"))) {
            throw new IllegalArgumentException(
                    "Exception type must be CANCELLATION or ROOM CHANGE."
            );
        }

        if (reason == null || reason.trim().isEmpty()) {
            throw new IllegalArgumentException("Reason cannot be empty.");
        }

        if (exceptionType.equalsIgnoreCase("ROOM CHANGE")) {

            if (replacementRoomCode == null ||
                    replacementRoomCode.trim().isEmpty()) {
                throw new IllegalArgumentException(
                        "Replacement room is required for a room change."
                );
            }

            if (Room.findRoom(replacementRoomCode) == null) {
                throw new IllegalArgumentException(
                        "Replacement room does not exist."
                );
            }

            this.replacementRoomCode = replacementRoomCode.trim();

        } else {
            this.replacementRoomCode = null;
        }

        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Status cannot be empty.");
        }

        if (!isValidStatus(status)) {
            throw new IllegalArgumentException(
                    "Status must be PENDING, APPROVED, or REJECTED."
            );
        }

        this.exceptionID = String.format("EXC%04d", nextExceptionID++);
        this.scheduleID = scheduleID;
        this.affectedDate = affectedDate.trim();
        this.exceptionType = exceptionType.toUpperCase(Locale.ROOT);
        this.reason = reason.trim();
        this.status = status.toUpperCase(Locale.ROOT);
    }

    // GETTERS

    public String getExceptionID() {
        return exceptionID;
    }

    public String getScheduleID() {
        return scheduleID;
    }

    public String getAffectedDate() {
        return affectedDate;
    }

    public String getExceptionType() {
        return exceptionType;
    }

    public String getReason() {
        return reason;
    }

    public String getReplacementRoomCode() {
        return replacementRoomCode;
    }

    public String getStatus() {
        return status;
    }

    // STATIC GETTERS

    public static String getExceptionID(CancelledSchedule e) {
        return e.getExceptionID();
    }

    public static String getScheduleID(CancelledSchedule e) {
        return e.getScheduleID();
    }

    public static String getAffectedDate(CancelledSchedule e) {
        return e.getAffectedDate();
    }

    public static String getExceptionType(CancelledSchedule e) {
        return e.getExceptionType();
    }

    public static String getReason(CancelledSchedule e) {
        return e.getReason();
    }

    public static String getReplacementRoomCode(CancelledSchedule e) {
        return e.getReplacementRoomCode();
    }

    public static String getStatus(CancelledSchedule e) {
        return e.getStatus();
    }

    // VALIDATION

    private static boolean isValidStatus(String status) {

        return status != null &&
                (status.equalsIgnoreCase("PENDING") ||
                        status.equalsIgnoreCase("APPROVED") ||
                        status.equalsIgnoreCase("REJECTED"));
    }

    // FIND EXCEPTION

    public static CancelledSchedule findException(String exceptionID) {

        if (exceptionID == null || exceptionID.trim().isEmpty())
            return null;

        for (CancelledSchedule e : exceptions) {
            if (e.getExceptionID().equalsIgnoreCase(exceptionID.trim())) {
                return e;
            }
        }

        return null;
    }

    public static boolean existExceptionID(String exceptionID) {
        return findException(exceptionID) != null;
    }

    // ADD EXCEPTION

    public static boolean addException(String scheduleID, String affectedDate,
                                       String exceptionType, String reason,
                                       String replacementRoomCode, String status) {

        if (scheduleID == null || affectedDate == null)
            return false;

        for (CancelledSchedule e : exceptions) {

            if (e.getScheduleID().equalsIgnoreCase(scheduleID.trim()) &&
                    e.getAffectedDate().equalsIgnoreCase(affectedDate.trim())) {
                return false;
            }
        }

        CancelledSchedule e = new CancelledSchedule(
                scheduleID, affectedDate, exceptionType, reason,
                replacementRoomCode, status
        );

        exceptions.add(e);
        return true;
    }

    // FIND EXCEPTIONS BY SCHEDULE

    public static ArrayList<CancelledSchedule> getExceptionsBySchedule(String scheduleID) {

        ArrayList<CancelledSchedule> result = new ArrayList<>();

        if (scheduleID == null ||
                scheduleID.trim().isEmpty() ||
                Schedule.findSchedule(scheduleID) == null) {
            return result;
        }

        for (CancelledSchedule e : exceptions) {
            if (e.getScheduleID().equalsIgnoreCase(scheduleID.trim())) {
                result.add(e);
            }
        }

        return result;
    }

    // UPDATE STATUS

    public static boolean updateStatus(String exceptionID, String status) {

        CancelledSchedule e = findException(exceptionID);

        if (e == null || !isValidStatus(status))
            return false;

        e.status = status.toUpperCase(Locale.ROOT);
        return true;
    }

    // DISPLAY ALL EXCEPTIONS

    public static void displayExceptions() {

        System.out.println(
                "EXCEPTION ID | SCHEDULE ID | DATE       | TYPE         | REPLACEMENT ROOM | STATUS"
        );
        System.out.println(
                "--------------------------------------------------------------------------------------"
        );

        if (exceptions.isEmpty()) {
            System.out.println("No schedule exceptions found.");
            return;
        }

        for (CancelledSchedule e : exceptions) {

            System.out.printf("%-12s | %-11s | %-10s | %-12s | %-16s | %s%n",
                    e.getExceptionID(),
                    e.getScheduleID(),
                    e.getAffectedDate(),
                    e.getExceptionType(),
                    e.getReplacementRoomCode() == null
                            ? "N/A"
                            : e.getReplacementRoomCode(),
                    e.getStatus()
            );
        }
    }

    // DISPLAY ONE EXCEPTION

    public static void displayException(String exceptionID) {

        CancelledSchedule e = findException(exceptionID);

        if (e == null) {
            System.out.println("Exception not found.");
            return;
        }

        Schedule schedule = Schedule.findSchedule(e.getScheduleID());

        System.out.println("EXCEPTION ID: " + e.getExceptionID());
        System.out.println("SCHEDULE ID: " + e.getScheduleID());
        System.out.println("AFFECTED DATE: " + e.getAffectedDate());
        System.out.println("EXCEPTION TYPE: " + e.getExceptionType());
        System.out.println("REASON: " + e.getReason());
        System.out.println("REPLACEMENT ROOM: " +
                (e.getReplacementRoomCode() == null
                        ? "N/A"
                        : e.getReplacementRoomCode()));
        System.out.println("STATUS: " + e.getStatus());

        if (schedule != null) {
            System.out.println("\n--- ORIGINAL SCHEDULE ---");
            Schedule.displaySchedule(e.getScheduleID());
        }
    }
}