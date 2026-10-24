
package DATA;

import UI.*;

import static UI.UI.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.*;

public class CancelledSchedule {

    private static int nextCancellationID = 1;

    private final String cancellationID, scheduleID, affectedDate, reason;

    public static ArrayList<CancelledSchedule> cancellations = new ArrayList<>();
    public static ArrayList<CancelledSchedule> archivedCancellations = new ArrayList<>();

    public CancelledSchedule(String scheduleID, String affectedDate, String reason) {
        this.cancellationID = String.format("CAN%04d", nextCancellationID++);
        this.scheduleID = scheduleID;
        this.affectedDate = affectedDate.trim();
        this.reason = reason.trim();
    }

    static {
        cancellations.add(new CancelledSchedule("SCH0002", "10/27/2026",
                "Original room is reserved for university maintenance."));
        cancellations.add(new CancelledSchedule("SCH0004", "10/29/2026",
                "Faculty member is unavailable for the scheduled class."));
        cancellations.add(new CancelledSchedule("SCH0006", "11/2/2026",
                "Original room is unavailable due to equipment repairs."));
        cancellations.add(new CancelledSchedule("SCH0009", "11/5/2026",
                "Class suspended due to an academic department activity."));
        cancellations.add(new CancelledSchedule("SCH0012", "11/10/2026",
                "Room reassigned to accommodate a larger class."));
        cancellations.add(new CancelledSchedule("SCH0015", "11/13/2026",
                "Instructor requested cancellation due to an emergency."));
        cancellations.add(new CancelledSchedule("SCH0018", "11/18/2026",
                "Science laboratory is temporarily unavailable."));
    }

    // GETTERS
    public String getCancellationID() {
        return cancellationID;
    }
    public String getScheduleID() {
        return scheduleID;
    }
    public String getAffectedDate() {
        return affectedDate;
    }
    public String getReason() {
        return reason;
    }

    // FIND EXCEPTION
    public static CancelledSchedule findException(String cancellationID) {

        if (cancellationID == null || cancellationID.trim().isEmpty())
            return null;

        for (CancelledSchedule c : cancellations) {
            if (c.getCancellationID().equalsIgnoreCase(cancellationID.trim())) {
                return c;
            }
        }

        return null;
    }

    // DISPLAY ALL EXCEPTIONS
    public static void displayCancellation(String cancellationID) {
        CancelledSchedule c = findException(cancellationID);

        if (c == null) {
            System.out.println("Exception not found.");
            return;
        }

        String reason = c.getReason();
        if (reason.length() > 42) reason = reason.substring(0, 42) + " ...";

        UI.header("CANCELLATION SCHEDULE DETAILS");
        UI.liner(String.format("  CANCELLATION ID  : %s", c.getCancellationID()));
        UI.liner(String.format("  SCHEDULE ID      : %s", c.getScheduleID()));
        UI.liner(String.format("  AFFECTED DATE    : %s", c.getAffectedDate()));
        UI.liner(String.format("  REASON           : %s", reason));
        UI.footer();
    }
    public static void displayCancellations() {
        UI.header("CANCELLED SCHEDULE LIST");

        if (cancellations.isEmpty()) {
            UI.liner(" No schedule cancellations found.");
            UI.footer();
            return;
        }

        for (CancelledSchedule c : cancellations) {
            UI.liner(String.format("  %-12s %-12s %s", c.getCancellationID(), c.getScheduleID(), c.getAffectedDate()));
        }

        UI.footer();
    }
    public static void displayArchivedCancellations() {
        UI.header("ARCHIVED CANCELLED SCHEDULE LIST");

        if (archivedCancellations.isEmpty()) {
            UI.liner(" No archived schedule cancellations found.");
        } else {
            for (CancelledSchedule c : archivedCancellations) {
                UI.liner(String.format("  %-12s %-12s %s", c.getCancellationID(), c.getScheduleID(), c.getAffectedDate()));
            }
        }

        UI.footer();
    }
    public static void displayCancellationsBySchedule(String scheduleID) {
        boolean gate = false;

        UI.header("CANCELLED SCHEDULES LIST");
        UI.liner("  Schedule ID: " + scheduleID);
        UI.separator();

        for (CancelledSchedule c : cancellations) {
            if (c.getScheduleID().equalsIgnoreCase(scheduleID.trim())) {
                UI.liner(String.format("  %-12s %s", c.getCancellationID(), c.getAffectedDate()));
                gate = true;
            }
        }

        if (!gate) UI.liner("  No cancelled schedules found");
        UI.footer();
    }

    // MANAGE METHODS
    public static void createCancelledSchedule() {
        String scheduleID = "", affectedDate = "";
        String reason = ""; String choice;
        boolean submit = false;

        while (!submit) {
            cancelledScheduleManage(scheduleID, affectedDate, reason);

            do {
                System.out.print("  Select > ");
                choice = in.nextLine().trim().toUpperCase();

                if (!choice.matches("[1-3X]") && !choice.equals("/"))
                    UI.print("Invalid Choice");

            } while (!choice.matches("[1-3X]") && !choice.equals("/"));

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
                    if (scheduleID.trim().isEmpty()) UI.print(" Enter a Schedule ID first");
                    else {
                        do {
                            System.out.print("   Enter Affected Date (MM/DD/YYYY) > ");
                            affectedDate = in.nextLine().trim();

                            try {
                                java.time.LocalDate date = java.time.LocalDate.parse(affectedDate,
                                        java.time.format.DateTimeFormatter.ofPattern("MM/dd/uuuu").withResolverStyle(
                                                java.time.format.ResolverStyle.STRICT));

                                Schedule schedule = Schedule.findSchedule(scheduleID);
                                if (schedule == null || !date.getDayOfWeek().name()
                                        .equalsIgnoreCase(schedule.getDay())) {

                                    UI.print("Invalid Date. The date must match the selected schedule's day.");
                                    affectedDate = "";
                                }

                            } catch (java.time.format.DateTimeParseException e) {
                                UI.print("Invalid Date. Use MM/DD/YYYY format.");
                                affectedDate = "";
                            }

                        } while (affectedDate.isEmpty());
                    }
                    break;
                case "3":
                    do {
                        System.out.print("   Enter Reason > ");
                        reason = in.nextLine().trim();

                        if (reason.isEmpty())
                            UI.print("Reason is required");

                    } while (reason.isEmpty());
                    break;

                case "/":
                    if (scheduleID.isEmpty() || affectedDate.isEmpty() || reason.isEmpty()) {
                        UI.print("Details are incomplete");
                        break;
                    }

                    for (CancelledSchedule e : cancellations) {
                        if (e.getScheduleID().equalsIgnoreCase(scheduleID.trim()) &&
                                e.getAffectedDate().equalsIgnoreCase(affectedDate.trim())) {
                            UI.print("Exception already exists");
                            return;
                        }
                    }

                    UI.print("Schedule Exception successfully created");
                    CancelledSchedule e = new CancelledSchedule(scheduleID, affectedDate, reason);
                    cancellations.add(e);
                    submit = true;
                    break;

                case "X":
                    submit = true;
                    break;
            }
        }
    }
    public static void archiveCancelledSchedule() {
        boolean gate = false;

        UI.header("ARCHIVE CANCELLED SCHEDULES");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/uuuu").withResolverStyle(ResolverStyle.STRICT);

        for (CancelledSchedule c :  new ArrayList<>(cancellations)) {
            LocalDate affectedDate = LocalDate.parse(c.getAffectedDate(), formatter);

            if (LocalDate.now().isAfter(affectedDate)) {
                archivedCancellations.add(c);
                cancellations.remove(c);
                UI.liner(String.format("  %-12s %-11s %s", c.getCancellationID(), c.getScheduleID(), c.getAffectedDate()));
                gate = true;
            }
        }

        if (!gate) UI.liner("  No available cancellation schedules can be archived.");
        UI.footer();

        if (gate) UI.print("Successfully archived");
    }
    private static void cancelledScheduleManage(String scheduleID, String affectedDate, String reason) {
        String text = reason;
        if (reason.length() > 35) text = reason.substring(0, 35) + " ...";

        UI.header("CREATE: SCHEDULE EXCEPTION");
        UI.liner(String.format("     EXCEPTION ID       : %s", "AUTO-GENERATED"));
        UI.liner(String.format(" [1] SCHEDULE ID        : %s", scheduleID));
        UI.liner(String.format(" [2] AFFECTED DATE      : %s", affectedDate));
        UI.liner(String.format(" [3] REASON             : %s", text));
        UI.liner("");
        UI.option();
        UI.footer();
    }
}