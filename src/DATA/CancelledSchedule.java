
package DATA;

import UI.*;
import static UI.UI.in;

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

    public static void createCancelledSchedule() {
        String scheduleID = "", affectedDate = "", exceptionType = "";
        String reason = "", replacementRoomCode = "", status = "PENDING";
        String choice = "";
        boolean submit = false;

        while (!submit) {
            cancelledScheduleManage("CREATE", "AUTO-GENERATED",
                    scheduleID, affectedDate, exceptionType, reason,
                    replacementRoomCode, status);

            do {
                System.out.print("  Select > ");
                choice = in.nextLine().trim().toUpperCase();

                if (!choice.matches("[1-6X]") && !choice.equals("/"))
                    UI.print("Invalid Choice");

            } while (!choice.matches("[1-6X]") && !choice.equals("/"));

            switch (choice) {
                case "1":
                    do {
                        System.out.print("   Enter Schedule ID > ");
                        scheduleID = in.nextLine().trim().toUpperCase();

                        if (scheduleID.isEmpty()
                                || Schedule.findSchedule(scheduleID) == null)
                            UI.print("Schedule does not exist");

                    } while (scheduleID.isEmpty()
                            || Schedule.findSchedule(scheduleID) == null);
                    break;

                case "2":
                    do {
                        System.out.print("   Enter Affected Date (MM/DD/YYYY) > ");
                        affectedDate = in.nextLine().trim();

                        try {
                            java.time.LocalDate.parse(
                                    affectedDate,
                                    java.time.format.DateTimeFormatter
                                            .ofPattern("MM/dd/uuuu")
                                            .withResolverStyle(
                                                    java.time.format.ResolverStyle.STRICT
                                            )
                            );
                        } catch (java.time.format.DateTimeParseException e) {
                            UI.print("Invalid Date. Use MM/DD/YYYY format.");
                            affectedDate = "";
                        }

                    } while (affectedDate.isEmpty());
                    break;

                case "3":
                    do {
                        System.out.print(
                                "   Enter Exception Type (CANCELLATION/ROOM CHANGE) > "
                        );
                        exceptionType = in.nextLine().trim().toUpperCase();

                        if (!exceptionType.equals("CANCELLATION")
                                && !exceptionType.equals("ROOM CHANGE"))
                            UI.print("Invalid Exception Type");

                    } while (!exceptionType.equals("CANCELLATION")
                            && !exceptionType.equals("ROOM CHANGE"));

                    if (exceptionType.equals("CANCELLATION"))
                        replacementRoomCode = "";
                    break;

                case "4":
                    do {
                        System.out.print("   Enter Reason > ");
                        reason = in.nextLine().trim();

                        if (reason.isEmpty())
                            UI.print("Reason is required");

                    } while (reason.isEmpty());
                    break;

                case "5":
                    if (!exceptionType.equals("ROOM CHANGE")) {
                        UI.print("Replacement room is only required for ROOM CHANGE");
                        break;
                    }

                    do {
                        System.out.print("   Enter Replacement Room Code > ");
                        replacementRoomCode = in.nextLine().trim().toUpperCase();

                        if (replacementRoomCode.isEmpty()
                                || Room.findRoom(replacementRoomCode) == null)
                            UI.print("Room does not exist");

                    } while (replacementRoomCode.isEmpty()
                            || Room.findRoom(replacementRoomCode) == null);
                    break;

                case "6":
                    status = "PENDING";
                    UI.print("Status set to PENDING");
                    break;

                case "/":
                    if (scheduleID.isEmpty() || affectedDate.isEmpty()
                            || exceptionType.isEmpty() || reason.isEmpty()) {
                        UI.print("Details are incomplete");
                        break;
                    }

                    if (exceptionType.equals("ROOM CHANGE")
                            && replacementRoomCode.isEmpty()) {
                        UI.print("Replacement room is required");
                        break;
                    }

                    try {
                        boolean added = CancelledSchedule.addException(
                                scheduleID,
                                affectedDate,
                                exceptionType,
                                reason,
                                replacementRoomCode.isEmpty()
                                        ? null : replacementRoomCode,
                                status
                        );

                        if (added) {
                            UI.print("Schedule exception successfully created");
                            CancelledSchedule.displayExceptions();
                            submit = true;
                        } else {
                            UI.print("An exception already exists for this schedule and date");
                        }

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

    public static void editCancelledSchedule() {
        String input = "", choice = "", status;
        boolean submit = false;

        UI.header("EDIT SCHEDULE EXCEPTION");

        do {
            System.out.print("  Enter Exception ID > ");
            input = in.nextLine().trim().toUpperCase();

            if (input.isEmpty()
                    || !CancelledSchedule.existExceptionID(input))
                UI.print("Exception does not exist");

        } while (input.isEmpty()
                || !CancelledSchedule.existExceptionID(input));

        CancelledSchedule e = CancelledSchedule.findException(input);

        if (e == null) {
            UI.print("Exception not found");
            return;
        }

        status = e.getStatus();

        while (!submit) {
            cancelledScheduleManage("EDIT", e.getExceptionID(),
                    e.getScheduleID(), e.getAffectedDate(),
                    e.getExceptionType(), e.getReason(),
                    e.getReplacementRoomCode() == null
                            ? "N/A" : e.getReplacementRoomCode(),
                    status);

            do {
                System.out.print("  Select > ");
                choice = in.nextLine().trim().toUpperCase();

                if (!choice.matches("[1X]") && !choice.equals("/"))
                    UI.print("Invalid Choice");

            } while (!choice.matches("[1X]") && !choice.equals("/"));

            switch (choice) {
                case "1":
                    do {
                        System.out.print(
                                "   Enter Status (PENDING/APPROVED/REJECTED) > "
                        );
                        status = in.nextLine().trim().toUpperCase();

                        if (!status.equals("PENDING")
                                && !status.equals("APPROVED")
                                && !status.equals("REJECTED"))
                            UI.print("Invalid Status");

                    } while (!status.equals("PENDING")
                            && !status.equals("APPROVED")
                            && !status.equals("REJECTED"));
                    break;

                case "/":
                    if (CancelledSchedule.updateStatus(
                            e.getExceptionID(), status)) {
                        UI.print("Schedule exception successfully edited");
                        CancelledSchedule.displayException(e.getExceptionID());
                        submit = true;
                    } else {
                        UI.print("Unable to update schedule exception");
                    }
                    break;

                case "X":
                    submit = true;
                    break;
            }
        }
    }

    public static void deleteCancelledSchedule() {
        String input, choice;

        UI.print("DELETE SCHEDULE EXCEPTION");

        do {
            System.out.print("  Enter Exception ID > ");
            input = in.nextLine().trim().toUpperCase();

            if (input.isEmpty()
                    || !CancelledSchedule.existExceptionID(input))
                UI.print("Exception does not exist");

        } while (input.isEmpty()
                || !CancelledSchedule.existExceptionID(input));

        CancelledSchedule e = CancelledSchedule.findException(input);

        if (e == null) {
            UI.print("Exception not found");
            return;
        }

        CancelledSchedule.displayException(e.getExceptionID());

        do {
            System.out.print("  Confirm Deletion? (Y/N) > ");
            choice = in.nextLine().trim().toUpperCase();

            if (!choice.matches("[YN]"))
                UI.print("Invalid Input");

        } while (!choice.matches("[YN]"));

        if (choice.equals("Y")) {
            CancelledSchedule.exceptions.remove(e);
            UI.print("Schedule exception successfully deleted");
        }
    }

    private static void cancelledScheduleManage(String text, String exceptionID, String scheduleID,
                                                String affectedDate, String exceptionType, String reason,
                                                String replacementRoomCode, String status) {

        UI.header(text.toUpperCase() + ": SCHEDULE EXCEPTION");

        UI.liner(String.format(" [1] SCHEDULE ID        : %s", scheduleID));
        UI.liner(String.format(" [2] AFFECTED DATE      : %s", affectedDate));
        UI.liner(String.format(" [3] EXCEPTION TYPE     : %s", exceptionType));
        UI.liner(String.format(" [4] REASON             : %s", reason));
        UI.liner(String.format(" [5] REPLACEMENT ROOM   : %s", replacementRoomCode));
        UI.liner(String.format(" [6] STATUS             : %s", status));
        UI.liner(String.format("     EXCEPTION ID       : %s", exceptionID));
        UI.liner("");

        UI.option();
        UI.footer();
    }
}