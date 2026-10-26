
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
        this.scheduleID = scheduleID;
        this.affectedDate = affectedDate.trim();
        this.reason = reason.trim();
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
            if (c.getCancellationID().equalsIgnoreCase(cancellationID.trim())) {
                return c;
            }
        }

        return null;
    }

    // DISPLAY METHODS
    public static void displayCancellation(String cancellationID) {
        CancelledSchedule c = findException(cancellationID);

        if (c == null) {
            System.out.println("Exception not found.");
            return;
        }

        String reason = c.getReason();
        if (reason.length() > 42) reason = reason.substring(0, 42) + "...";

        UI.header("CANCELLATION SCHEDULE DETAILS");
        UI.liner(String.format("  CANCELLATION ID  : %s", c.getCancellationID()));
        UI.liner(String.format("  SCHEDULE ID      : %s", c.getScheduleID()));
        UI.liner(String.format("  AFFECTED DATE    : %s", c.getAffectedDate()));
        UI.liner(String.format("  REASON           : %s", reason));
        if (c.getCancelledByUserID() != null)
            UI.liner(String.format("  CANCELLED BY     : %s", c.getCancelledByUserID()));
        UI.footer();
        UI.returnButton();
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
        UI.returnButton();
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
        UI.returnButton();
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
                && !"Active".equalsIgnoreCase(Faculty.getEmploymentStatus(u))) {
            UI.print("Inactive faculty cannot cancel a schedule.");
            return;
        }

        ArrayList<Schedule> availableSchedules = new ArrayList<>();
        if (u instanceof Faculty) {
            Faculty faculty = (Faculty) u;
            for (CourseOffering offering : CourseOffering.getOfferingsByFaculty(faculty.getUserID())) {
                if (offering == null)
                    continue;
                availableSchedules.addAll(Schedule.getSchedulesByOffering(offering.getOfferingID()));
            }
        } else {
            availableSchedules.addAll(Schedule.schedules);
        }

        if (availableSchedules.isEmpty()) {
            UI.header("CANCEL SCHEDULE OCCURRENCE");
            UI.liner("  No active schedules are available.");
            UI.footer();
            return;
        }

        UI.header("CANCEL SCHEDULE OCCURRENCE");
        UI.liner("  USER ID   : " + u.getUserID());
        UI.liner("  USER NAME : " + u.getUserName());
        UI.liner(u instanceof Faculty
                ? "  Select a schedule from your own teaching load."
                : "  Select the schedule occurrence to cancel.");
        UI.separator();

        for (Schedule schedule : availableSchedules) {
            if (schedule == null)
                continue;

            CourseOffering offering = CourseOffering.findOffering(schedule.getOfferingID());
            Course course = offering == null ? null : Course.findCourse(offering.getCourseCode());
            Section section = offering == null ? null : Section.findSection(offering.getSectionID());

            UI.liner(String.format("  %-10s | %-8s | %-12s", schedule.getScheduleID(),
                    course == null ? "Course" : course.getCourseCode(),
                    section == null ? "Section" : Section.getYearSection(section)));
            UI.liner(String.format("             %s | %s - %s", schedule.getDay(),
                    schedule.getStartTime(), schedule.getEndTime()));
            UI.liner(String.format("             ROOM: %s | ACTIVE: %s - %s", schedule.getRoomCode(),
                    schedule.getEffectiveFrom(), schedule.getEffectiveTo()));
            UI.separator();
        }
        UI.liner("  [X] Cancel operation");
        UI.footer();

        Schedule selected = null;
        while (selected == null) {
            System.out.print("  Enter Schedule ID > ");
            String scheduleID = in.nextLine().trim();
            if (scheduleID.equalsIgnoreCase("X"))
                return;

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
        DateTimeFormatter storedFormatter = DateTimeFormatter.ofPattern("M/d/uuuu")
                .withResolverStyle(ResolverStyle.STRICT);
        LocalDate affectedDate = null;
        String dateText = "";

        while (affectedDate == null) {
            System.out.print("  Enter Affected Date (MM/DD/YYYY) > ");
            dateText = in.nextLine().trim();
            if (dateText.equalsIgnoreCase("X"))
                return;

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
                    if (scheduledDay.length() > 3)
                        scheduledDay = scheduledDay.substring(0, 3);

                    if (!scheduledDay.equals(actualDay)) {
                        UI.print("The date does not match the schedule's day (" + selected.getDay() + ").");
                        affectedDate = null;
                    } else if (affectedDate.equals(LocalDate.now())
                            && Room.convertToMinutes(selected.getStartTime()) >= 0
                            && LocalTime.now().getHour() * 60 + LocalTime.now().getMinute()
                            >= Room.convertToMinutes(selected.getStartTime())) {
                        UI.print("This class has already started or passed today.");
                        affectedDate = null;
                    }
                }

                if (affectedDate != null) {
                    dateText = affectedDate.format(formatter);
                    for (CancelledSchedule existing : cancellations) {
                        if (existing != null
                                && existing.getScheduleID().equalsIgnoreCase(selected.getScheduleID())
                                && LocalDate.parse(existing.getAffectedDate(), storedFormatter).equals(affectedDate)) {
                            UI.print("This schedule occurrence has already been cancelled.");
                            affectedDate = null;
                            break;
                        }
                    }
                    if (affectedDate != null) {
                        for (CancelledSchedule existing : archivedCancellations) {
                            if (existing != null
                                    && existing.getScheduleID().equalsIgnoreCase(selected.getScheduleID())
                                    && LocalDate.parse(existing.getAffectedDate(), storedFormatter).equals(affectedDate)) {
                                UI.print("A cancellation record already exists for this schedule and date.");
                                affectedDate = null;
                                break;
                            }
                        }
                    }
                }
            } catch (DateTimeParseException e) {
                UI.print("Invalid date or schedule effective date. Use MM/DD/YYYY.");
                affectedDate = null;
            }
        }

        String reason = "";
        while (reason.isEmpty()) {
            System.out.print("  Enter Cancellation Reason > ");
            reason = in.nextLine().trim();
            if (reason.equalsIgnoreCase("X"))
                return;
            if (reason.isEmpty())
                UI.print("Reason is required.");
        }

        CancelledSchedule cancellation = new CancelledSchedule(
                selected.getScheduleID(), dateText, reason, u.getUserID());
        cancellations.add(cancellation);

        UI.header("SCHEDULE CANCELLATION CONFIRMED");
        UI.liner("  CANCELLATION ID : " + cancellation.getCancellationID());
        UI.liner("  SCHEDULE ID     : " + cancellation.getScheduleID());
        UI.liner("  AFFECTED DATE   : " + cancellation.getAffectedDate());
        UI.liner("  CANCELLED BY    : " + cancellation.getCancelledByUserID());
        UI.liner("  REASON          : " + cancellation.getReason());
        UI.footer();
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

        if (gate) UI.print("Cancellation Schedule successfully archived");
        UI.returnButton();
    }

}