
package DATA;

import UI.*;
import USER.User;
import USER.Faculty;
import USER.DepartmentHead;

import static UI.UI.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.*;

public class CancelledSchedule {
    // FIELDS
    private static int nextCancellationID = 1;

    private final String cancellationID, scheduleID, affectedDate, reason, cancelledByUserID;

    public static ArrayList<CancelledSchedule> cancellations = new ArrayList<>();
    public static ArrayList<CancelledSchedule> archivedCancellations = new ArrayList<>();

    // CONSTRUCTOR
    public CancelledSchedule(String scheduleID, String affectedDate, String reason) {
        this(scheduleID, affectedDate, reason, null);
    }
    public CancelledSchedule(String scheduleID, String affectedDate, String reason,
                             String cancelledByUserID) {
        this.cancellationID = String.format("CAN%04d", nextCancellationID++);
        this.scheduleID = scheduleID == null ? "" : scheduleID.trim();
        this.affectedDate = affectedDate == null ? "" : affectedDate.trim();
        this.reason = reason == null ? "" : reason.trim();
        if (this.scheduleID.isEmpty() || this.affectedDate.isEmpty() || this.reason.isEmpty())
            UI.print("Cancellation record contains incomplete details.");
        this.cancelledByUserID = cancelledByUserID == null || cancelledByUserID.trim().isEmpty()
                ? null : cancelledByUserID.trim().toUpperCase();
    }

    static {
        cancellations.add(new CancelledSchedule("SCH0002", "10/27/2026",
                "Original room is reserved for university maintenance."));
        cancellations.add(new CancelledSchedule("SCH0004", "10/29/2026",
                "Faculty member is unavailable for the scheduled class."));
        cancellations.add(new CancelledSchedule("SCH0006", "11/02/2026",
                "Original room is unavailable due to equipment repairs."));
        cancellations.add(new CancelledSchedule("SCH0009", "11/05/2026",
                "Class suspended due to an academic department activity."));
        cancellations.add(new CancelledSchedule("SCH0012", "11/10/2026",
                "Room reassigned to accommodate a larger class."));
        cancellations.add(new CancelledSchedule("SCH0015", "11/13/2026",
                "Instructor requested cancellation due to an emergency."));
        cancellations.add(new CancelledSchedule("SCH0018", "11/18/2026",
                "Science laboratory is temporarily unavailable."));
    }

    // GETTER METHODS
    public String getCancellationID() {return cancellationID;}
    public String getScheduleID() {
        return scheduleID;
    }
    public String getAffectedDate() {
        return affectedDate;
    }
    public String getReason() {
        return reason;
    }
    public String getCancelledByUserID() {
        return cancelledByUserID;
    }

    // FIND METHODS
    public static CancelledSchedule findException(String cancellationID) {

        if (cancellationID == null || cancellationID.trim().isEmpty())
            return null;

        for (CancelledSchedule c : cancellations) {
            if (c != null && c.getCancellationID() != null
                    && c.getCancellationID().equalsIgnoreCase(cancellationID.trim())) {
                return c;
            }
        }

        return null;
    }

    // DISPLAY METHODS
    public static void displayCancellation(String cancellationID) {
        CancelledSchedule c = findException(cancellationID);

        if (c == null) {
            UI.print("Exception not found.");
            return;
        }

        String reason = c.getReason() == null || c.getReason().isBlank() ? "-" : c.getReason();

        UI.header("CANCELLATION SCHEDULE DETAILS", false);
        UI.liner(String.format("  CANCELLATION ID  : %s", c.getCancellationID()), false);
        UI.liner(String.format("  SCHEDULE ID      : %s", c.getScheduleID()), false);
        UI.liner(String.format("  AFFECTED DATE    : %s", c.getAffectedDate()), false);
        UI.liner(String.format("  REASON           : %s", reason), false);
        if (c.getCancelledByUserID() != null)
            UI.liner(String.format("  CANCELLED BY     : %s", c.getCancelledByUserID()), false);
        UI.footer(false);
        UI.returnButton();
    }
    public static void displayCancellations() {
        UI.header("CANCELLED SCHEDULES", true);

        if (cancellations.isEmpty()) {
            UI.liner("  No schedule cancellations found.", true);
        } else {
            UI.liner(String.format("  %-14s %-14s %-16s %-131s",
                    "CANCELLATION", "SCHEDULE ID", "AFFECTED DATE", "REASON"), true);
            UI.separator(true);

            int count = 0;
            for (CancelledSchedule cancellation : cancellations) {
                if (cancellation == null) continue;
                UI.liner(String.format("  %-14s %-14s %-16s %-131s",
                        cancellation.getCancellationID(), cancellation.getScheduleID(),
                        cancellation.getAffectedDate(), cancellation.getReason()), true);
                count++;
            }
            UI.separator(true);
            UI.liner("  TOTAL CANCELLATIONS: " + count, true);
        }

        UI.footer(true);
        UI.returnButton();
    }
    public static void displayArchivedCancellations() {
        UI.header("ARCHIVED SCHEDULE CANCELLATIONS", true);

        if (archivedCancellations.isEmpty()) {
            UI.liner("  No archived schedule cancellations found.", true);
        } else {
            UI.liner(String.format("  %-14s %-14s %-16s %-131s",
                    "CANCELLATION", "SCHEDULE ID", "AFFECTED DATE", "REASON"), true);
            UI.separator(true);

            int count = 0;
            for (CancelledSchedule cancellation : archivedCancellations) {
                if (cancellation == null) continue;
                UI.liner(String.format("  %-14s %-14s %-16s %-131s",
                        cancellation.getCancellationID(), cancellation.getScheduleID(),
                        cancellation.getAffectedDate(), cancellation.getReason()), true);
                count++;
            }
            UI.separator(true);
            UI.liner("  TOTAL ARCHIVED CANCELLATIONS: " + count, true);
        }

        UI.footer(true);
        UI.returnButton();
    }
    public static void displayCancellationsBySchedule(String scheduleID) {
        if (scheduleID == null || scheduleID.trim().isEmpty()) {
            UI.print("Schedule ID is required.");
            return;
        }

        UI.header("CANCELLED SCHEDULES LIST", true);
        UI.liner("  SCHEDULE ID: " + scheduleID.trim(), true);
        UI.liner(String.format("  %-16s %-18s %-142s", "CANCELLATION ID", "AFFECTED DATE", "REASON"), true);
        UI.separator(true);
        int count = 0;
        for (CancelledSchedule c : cancellations) {
            if (c != null && c.getScheduleID() != null
                    && c.getScheduleID().equalsIgnoreCase(scheduleID.trim())) {
                UI.liner(String.format("  %-16s %-18s %-142s", c.getCancellationID(),
                        c.getAffectedDate(), UI.fitText(c.getReason(), 142)), true);
                count++;
            }
        }
        if (count == 0) UI.liner("  No cancelled schedules found.", true);
        UI.separator(true);
        UI.liner("  TOTAL CANCELLATIONS: " + count, true);
        UI.footer(true);
        UI.returnButton();
    }

    // Legacy entry point: actor identity is required for safe cancellation.
    public static void createCancelledSchedule() {
        UI.print("Cancellation requires the current Faculty or Department Head account.");
    }

    // Cancellation must be initiated by an authenticated Faculty or Department Head object.
    public static void createCancelledSchedule(User u) {
        if (u == null || User.findUser(u.getUserID()) != u
                || (!(u instanceof Faculty) && !(u instanceof DepartmentHead))) {
            UI.print("Authorized user not found.");
            return;
        }
        if (u instanceof Faculty
                && !"Active".equalsIgnoreCase(((Faculty) u).getEmploymentStatus())) {
            UI.print("Inactive faculty cannot cancel a schedule.");
            return;
        }

        ArrayList<Schedule> availableSchedules = new ArrayList<>();
        if (u instanceof Faculty) {
            for (CourseOffering offering : CourseOffering.getOfferingsByFaculty(u.getUserID())) {
                if (offering != null)
                    availableSchedules.addAll(Schedule.getSchedulesByOffering(offering.getOfferingID()));
            }
        } else {
            availableSchedules.addAll(Schedule.schedules);
        }

        if (availableSchedules.isEmpty()) {
            UI.print("No active schedules are available to cancel.");
            return;
        }

        UI.header("CANCEL SCHEDULE OCCURRENCE", true);
        UI.liner("  USER ID: " + u.getUserID() + " | USER NAME: " + u.getUserName(), true);
        UI.liner(u instanceof Faculty
                ? "  Select a schedule from your own teaching load."
                : "  Select the schedule occurrence to cancel.", true);
        UI.separator(true);
        String format = "  %-12s %-12s %-12s %-20s %-12s %-20s %-12s %-71s";
        UI.liner(String.format(format, "SCHEDULE", "OFFERING", "COURSE", "SECTION", "DAY",
                "TIME", "ROOM", "EFFECTIVE PERIOD"), true);
        UI.separator(true);
        for (Schedule schedule : availableSchedules) {
            if (schedule == null) continue;
            CourseOffering offering = CourseOffering.findOffering(schedule.getOfferingID());
            Course course = offering == null ? null : Course.findCourse(offering.getCourseCode());
            Section section = offering == null ? null : Section.findSection(offering.getSectionID());
            UI.liner(String.format(format, schedule.getScheduleID(), schedule.getOfferingID(),
                    course == null ? "-" : course.getCourseCode(),
                    UI.fitText(section == null ? "-" : Section.getYearSection(section), 20),
                    schedule.getDay(), UI.fitText(schedule.getStartTime() + " - " + schedule.getEndTime(), 20),
                    schedule.getRoomCode(), UI.fitText(schedule.getEffectiveFrom() + " - "
                            + schedule.getEffectiveTo(), 71)), true);
        }
        UI.footer(true);

        Schedule selected = null;
        while (selected == null) {
            UI.select("Enter Schedule ID", false);
            String scheduleID = in.nextLine().trim();
            if (scheduleID.equalsIgnoreCase("X")) return;
            for (Schedule schedule : availableSchedules) {
                if (schedule != null && schedule.getScheduleID().equalsIgnoreCase(scheduleID)) {
                    selected = schedule;
                    break;
                }
            }
            if (selected == null)
                UI.print(u instanceof Faculty
                        ? "Schedule not found in your teaching load. Choose one of the listed IDs."
                        : "Schedule not found. Choose one of the listed IDs.");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/uuuu")
                .withResolverStyle(ResolverStyle.STRICT);
        LocalDate affectedDate = null;
        String dateText = "";
        while (affectedDate == null) {
            UI.select("Enter Affected Date (MM/dd/yyyy)", false);
            dateText = in.nextLine().trim();
            if (dateText.equalsIgnoreCase("X")) return;
            try {
                affectedDate = LocalDate.parse(dateText, formatter);
                LocalDate effectiveFrom = LocalDate.parse(selected.getEffectiveFrom(), formatter);
                LocalDate effectiveTo = LocalDate.parse(selected.getEffectiveTo(), formatter);
                if (affectedDate.isBefore(LocalDate.now())) {
                    UI.print("A past schedule occurrence cannot be cancelled.");
                    affectedDate = null;
                } else if (affectedDate.isBefore(effectiveFrom) || affectedDate.isAfter(effectiveTo)) {
                    UI.print("The date must fall within the schedule's effective period.");
                    affectedDate = null;
                } else {
                    String scheduledDay = selected.getDay() == null ? ""
                            : selected.getDay().trim().toLowerCase(Locale.ENGLISH);
                    String actualDay = affectedDate.getDayOfWeek()
                            .getDisplayName(java.time.format.TextStyle.SHORT, Locale.ENGLISH)
                            .toLowerCase(Locale.ENGLISH);
                    if (scheduledDay.length() > 3) scheduledDay = scheduledDay.substring(0, 3);
                    if (!scheduledDay.equals(actualDay)) {
                        UI.print("The date does not match the schedule's day (" + selected.getDay() + ").");
                        affectedDate = null;
                    } else if (affectedDate.equals(LocalDate.now())
                            && LocalTime.now().getHour() * 60 + LocalTime.now().getMinute()
                            >= Room.convertToMinutes(selected.getStartTime())) {
                        UI.print("This class has already started or passed today.");
                        affectedDate = null;
                    }
                }
                if (affectedDate != null) {
                    dateText = affectedDate.format(formatter);
                    for (CancelledSchedule existing : cancellations) {
                        if (existing != null && existing.getScheduleID().equalsIgnoreCase(selected.getScheduleID())
                                && UI.parseDate(existing.getAffectedDate()) != null
                                && UI.parseDate(existing.getAffectedDate()).equals(affectedDate)) {
                            UI.print("This schedule occurrence has already been cancelled.");
                            affectedDate = null;
                            break;
                        }
                    }
                    if (affectedDate != null) {
                        for (CancelledSchedule existing : archivedCancellations) {
                            if (existing != null && existing.getScheduleID().equalsIgnoreCase(selected.getScheduleID())
                                    && UI.parseDate(existing.getAffectedDate()) != null
                                    && UI.parseDate(existing.getAffectedDate()).equals(affectedDate)) {
                                UI.print("A cancellation record already exists for this schedule and date.");
                                affectedDate = null;
                                break;
                            }
                        }
                    }
                }
            } catch (DateTimeParseException e) {
                UI.print("Invalid date or schedule effective date. Use MM/dd/yyyy.");
                affectedDate = null;
            }
        }

        String reason = "";
        while (reason.isEmpty()) {
            UI.select("Enter Cancellation Reason", false);
            reason = in.nextLine().trim();
            if (reason.equalsIgnoreCase("X")) return;
            if (reason.isEmpty()) UI.print("Reason is required.");
        }

        CancelledSchedule cancellation = new CancelledSchedule(
                selected.getScheduleID(), dateText, reason, u.getUserID());
        cancellations.add(cancellation);
        UI.header("SCHEDULE CANCELLATION CONFIRMED", false);
        UI.liner("  CANCELLATION ID : " + cancellation.getCancellationID(), false);
        UI.liner("  SCHEDULE ID     : " + cancellation.getScheduleID(), false);
        UI.liner("  AFFECTED DATE   : " + cancellation.getAffectedDate(), false);
        UI.liner("  CANCELLED BY    : " + cancellation.getCancelledByUserID(), false);
        UI.liner("  REASON          : " + cancellation.getReason(), false);
        UI.footer(false);
    }

    public static void archiveCancelledSchedule() {
        ArrayList<CancelledSchedule> moved = new ArrayList<>();
        for (CancelledSchedule cancellation : new ArrayList<>(cancellations)) {
            LocalDate affectedDate = UI.parseDate(cancellation.getAffectedDate());
            if (affectedDate != null && LocalDate.now().isAfter(affectedDate)) {
                archivedCancellations.add(cancellation);
                cancellations.remove(cancellation);
                moved.add(cancellation);
            }
        }

        UI.header("ARCHIVE CANCELLED SCHEDULES", true);
        if (moved.isEmpty()) {
            UI.liner("  No available cancellation schedules can be archived.", true);
        } else {
            String format = "  %-14s %-14s %-16s %-108s %-22s";
            UI.liner(String.format(format, "CANCELLATION", "SCHEDULE", "AFFECTED DATE", "REASON", "CANCELLED BY"), true);
            UI.separator(true);
            for (CancelledSchedule cancellation : moved) {
                UI.liner(String.format(format, cancellation.getCancellationID(), cancellation.getScheduleID(),
                        cancellation.getAffectedDate(), cancellation.getReason(),
                        cancellation.getCancelledByUserID() == null ? "-" : cancellation.getCancelledByUserID()), true);
            }
            UI.separator(true);
            UI.liner("  TOTAL ARCHIVED: " + moved.size(), true);
        }
        UI.footer(true);
        if (!moved.isEmpty()) UI.print("Cancellation schedules successfully archived.");
        UI.returnButton();
    }
}