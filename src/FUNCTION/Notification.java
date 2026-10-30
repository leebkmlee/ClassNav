
package FUNCTION;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import USER.User;
import DATA.Room;
import DATA.Building;
import DATA.Section;
import DATA.Course;
import DATA.CourseOffering;
import DATA.CourseRegistration;
import USER.Faculty;
import USER.Student;
import USER.DepartmentHead;

import UI.*;

import static UI.UI.in;

public class Notification {

    // FIELDS
    private static int nextNotificationID = 1;

    private final String notificationID, senderID, recipientID, courseCode, roomCode, buildingName, day, startTime,
            endTime, message, createdAt, offeringID, requestID, courseID, sectionID, sectionName,
            roomDescription, buildingCode, meetingDate;

    public static ArrayList<Notification> notifications = new ArrayList<>();
    public static ArrayList<Notification> archiveNotifications = new ArrayList<>();

    // CONSTRUCTOR
    public Notification(String senderID, String recipientID, String courseCode, String roomCode,
                        String buildingName, String day, String startTime, String endTime, String message) {
        this(senderID, recipientID, courseCode, roomCode, buildingName, day, startTime, endTime,
                message, null, null, null);
    }

    public Notification(String senderID, String recipientID, String courseCode, String roomCode,
                        String buildingName, String day, String startTime, String endTime, String message,
                        String offeringID, String requestID, String meetingDate) {
        this.notificationID = String.format("NOT%03d", nextNotificationID++);
        this.senderID = safeText(senderID);
        this.recipientID = safeText(recipientID);
        String linkedOfferingID = safeText(offeringID);
        if (linkedOfferingID.isEmpty())
            linkedOfferingID = resolveOfferingID(this.senderID, this.recipientID, courseCode);
        this.offeringID = linkedOfferingID;
        this.requestID = safeText(requestID);
        CourseOffering offering = this.offeringID.isEmpty() ? null : CourseOffering.findOffering(this.offeringID);
        Course course = Course.findCourse(offering == null ? courseCode : offering.getCourseCode());
        Section section = offering == null ? null : Section.findSection(offering.getSectionID());
        Room room = Room.findRoom(roomCode);
        Building building = room == null ? null : Building.findBuilding(room.getBuildingCode());

        this.courseCode = safeText(course == null ? courseCode : course.getCourseCode());
        this.courseID = this.courseCode;
        this.sectionID = safeText(offering == null ? "" : offering.getSectionID());
        this.sectionName = section == null ? "" : Section.getYearSection(section);
        this.roomCode = safeText(room == null ? roomCode : room.getRoomCode());
        this.roomDescription = safeText(room == null ? "" : room.getRoomDescription());
        this.buildingCode = safeText(room == null ? "" : room.getBuildingCode());
        this.buildingName = safeText(building == null ? buildingName : building.getBuildingName());
        this.meetingDate = safeText(meetingDate);
        String actualDay = safeText(day);
        if (actualDay.isEmpty() && !this.meetingDate.isEmpty()) {
            LocalDate date = UI.parseDate(this.meetingDate);
            if (date != null) actualDay = date.getDayOfWeek().getDisplayName(
                    java.time.format.TextStyle.FULL, Locale.ENGLISH);
        }
        this.day = actualDay;
        this.startTime = safeText(startTime);
        this.endTime = safeText(endTime);
        this.message = safeText(message);
        this.createdAt = LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("MM/dd/uuuu hh:mm a", Locale.ENGLISH));
    }

    // DUMMY DATA
    static {
        notifications.add(new Notification("F0001", "S0001", "IT101", "RM001", "Pimentel Hall",
                "Monday", "8:00 AM", "9:30 AM", "Room assigned"));
        notifications.add(new Notification("F0002", "S0002", "GE101", "RM002", "Pimentel Hall",
                "Tuesday", "10:00 AM", "11:30 AM", "Room changed"));
        notifications.add(new Notification("F0003", "S0003", "PE101", "RM011", "Valencia Hall",
                "Wednesday", "12:00 PM", "1:30 PM", "Class schedule updated"));
        notifications.add(new Notification("F0004", "S0004", "IT102", "RM004", "NSTP Building",
                "Thursday", "2:00 PM", "3:30 PM", "Make-up class approved"));
        notifications.add(new Notification("F0005", "S0005", "GE103", "RM005", "NSTP Building",
                "Friday", "4:00 PM", "5:30 PM", "Class cancelled"));
        notifications.add(new Notification("F0006", "S0006", "IT103", "RM002", "Pimentel Hall",
                "Monday", "9:45 AM", "11:15 AM", "Room assigned"));
        notifications.add(new Notification("F0001", "S0007", "GE101", "RM004", "NSTP Building",
                "Tuesday", "1:00 PM", "2:30 PM", "Room changed"));
        notifications.add(new Notification("F0002", "S0008", "IT104", "RM001", "Pimentel Hall",
                "Wednesday", "8:00 AM", "9:30 AM", "Class schedule updated"));
        notifications.add(new Notification("F0003", "S0009", "GE104", "RM005", "NSTP Building",
                "Thursday", "10:00 AM", "11:30 AM", "Make-up class approved"));
        notifications.add(new Notification("F0004", "S0010", "IS102", "RM003", "Pimentel Hall",
                "Friday", "8:00 AM", "9:30 AM", "Class cancelled"));
        notifications.add(new Notification("F0005", "S0011", "GE102", "RM004", "NSTP Building",
                "Monday", "1:00 PM", "2:30 PM", "Room assigned"));
        notifications.add(new Notification("F0006", "S0012", "IS101", "RM002", "Pimentel Hall",
                "Tuesday", "8:00 AM", "9:30 AM", "Room changed"));
        notifications.add(new Notification("F0001", "S0013", "BL101", "RM005", "NSTP Building",
                "Wednesday", "2:00 PM", "3:30 PM", "Class schedule updated"));
        notifications.add(new Notification("F0002", "S0014", "CR101", "RM004", "NSTP Building",
                "Thursday", "8:00 AM", "9:30 AM", "Make-up class approved"));
        notifications.add(new Notification("F0003", "S0015", "TM101", "RM013", "Federizo",
                "Friday", "1:00 PM", "3:00 PM", "Class cancelled"));

        notifications.add(new Notification("F0005","S0007","IT105","RM006","Pimentel Hall","Monday","8:00 AM","9:30 AM","New class schedule assigned"));
        notifications.add(new Notification("F0006","S0008","GE105","RM007","Pimentel Hall","Monday","9:45 AM","11:15 AM","Class schedule updated"));
        notifications.add(new Notification("F0008","S0009","IS103","RM008","Valencia Hall","Tuesday","8:00 AM","9:30 AM","Room assigned"));
        notifications.add(new Notification("F0009","S0010","CE102","RM009","Valencia Hall","Tuesday","9:45 AM","11:15 AM","Class schedule updated"));
        notifications.add(new Notification("F0012","S0011","BIO102","RM010","NSTP Building","Wednesday","8:00 AM","9:30 AM","Room changed"));
        notifications.add(new Notification("F0014","S0012","HM102","RM012","NSTP Building","Wednesday","9:45 AM","11:15 AM","New class schedule assigned"));
        notifications.add(new Notification("F0015","S0013","NS102","RM013","Federizo","Thursday","8:00 AM","9:30 AM","Room assigned"));
        notifications.add(new Notification("F0012","S0014","TM102","RM014","Federizo","Thursday","9:45 AM","11:15 AM","Class schedule updated"));
        notifications.add(new Notification("F0013","S0015","CR102","RM006","Pimentel Hall","Friday","8:00 AM","9:30 AM","Room changed"));
        notifications.add(new Notification("F0014","S0001","BL102","RM007","Pimentel Hall","Friday","9:45 AM","11:15 AM","New class schedule assigned"));
    }

    private static String safeText(String value) {
        return value == null ? "" : value.trim();
    }

    private static String resolveOfferingID(String senderID, String recipientID, String courseCode) {
        String studentID, facultyID;
        if (senderID != null && senderID.toUpperCase(Locale.ENGLISH).startsWith("F")) {
            facultyID = senderID;
            studentID = recipientID;
        } else if (recipientID != null && recipientID.toUpperCase(Locale.ENGLISH).startsWith("F")) {
            studentID = senderID;
            facultyID = recipientID;
        } else return "";

        if (studentID == null || studentID.isBlank() || facultyID == null || facultyID.isBlank()) return "";
        String matchedOfferingID = "";
        for (CourseRegistration registration : CourseRegistration.getRegistrationsByStudent(studentID)) {
            if (registration == null) continue;
            CourseOffering offering = CourseOffering.findOffering(registration.getOfferingID());
            if (offering == null || offering.getFacultyID() == null
                    || !offering.getFacultyID().equalsIgnoreCase(facultyID)
                    || courseCode == null || !offering.getCourseCode().equalsIgnoreCase(courseCode.trim())) continue;
            if (!matchedOfferingID.isEmpty() && !matchedOfferingID.equalsIgnoreCase(offering.getOfferingID()))
                return "";
            matchedOfferingID = offering.getOfferingID();
        }
        return matchedOfferingID;
    }

    public static boolean sendNotification(String senderID, String recipientID, String courseCode,
                                           String roomCode, String buildingName, String day,
                                           String startTime, String endTime, String message) {
        return sendNotification(senderID, recipientID, courseCode, roomCode, buildingName, day,
                startTime, endTime, message, null, null, null);
    }

    public static boolean sendNotification(String senderID, String recipientID, String courseCode,
                                           String roomCode, String buildingName, String day,
                                           String startTime, String endTime, String message,
                                           String offeringID, String requestID, String meetingDate) {
        User sender = User.findUser(senderID);
        User recipient = User.findUser(recipientID);

        if (sender == null) {
            UI.print("Notification was not sent: sender does not exist.");
            return false;
        }
        if (recipient == null) {
            UI.print("Notification was not sent: recipient does not exist.");
            return false;
        }

        boolean authorized = false;
        if (sender instanceof Student student && recipient instanceof Faculty faculty) {
            for (CourseRegistration registration : CourseRegistration.getRegistrationsByStudent(student.getUserID())) {
                CourseOffering offering = registration == null ? null
                        : CourseOffering.findOffering(registration.getOfferingID());
                if (offering != null && offering.getFacultyID() != null
                        && offering.getFacultyID().equalsIgnoreCase(faculty.getUserID())
                        && (offeringID == null || offeringID.isBlank()
                        || offering.getOfferingID().equalsIgnoreCase(offeringID.trim()))
                        && (courseCode == null || courseCode.trim().isEmpty()
                        || offering.getCourseCode().equalsIgnoreCase(courseCode.trim()))) {
                    authorized = true;
                    break;
                }
            }
        } else if (sender instanceof Faculty faculty && recipient instanceof Student student) {
            for (CourseRegistration registration : CourseRegistration.getRegistrationsByStudent(student.getUserID())) {
                CourseOffering offering = registration == null ? null
                        : CourseOffering.findOffering(registration.getOfferingID());
                if (offering != null && offering.getFacultyID() != null
                        && offering.getFacultyID().equalsIgnoreCase(faculty.getUserID())
                        && (offeringID == null || offeringID.isBlank()
                        || offering.getOfferingID().equalsIgnoreCase(offeringID.trim()))
                        && (courseCode == null || courseCode.trim().isEmpty()
                        || offering.getCourseCode().equalsIgnoreCase(courseCode.trim()))) {
                    authorized = true;
                    break;
                }
            }
        } else if (sender instanceof Faculty faculty && recipient instanceof DepartmentHead head) {
            Room targetRoom = Room.findRoom(roomCode);
            String targetCollege = targetRoom == null ? faculty.getCollegeCode() : targetRoom.getCollegeCode();
            authorized = targetCollege != null && head.getCollegeCode() != null
                    && targetCollege.equalsIgnoreCase(head.getCollegeCode());
        } else if (sender instanceof DepartmentHead head && recipient instanceof Faculty faculty) {
            Room targetRoom = Room.findRoom(roomCode);
            authorized = head.getCollegeCode() != null && faculty.getCollegeCode() != null
                    && head.getCollegeCode().equalsIgnoreCase(faculty.getCollegeCode());
            if (!authorized && targetRoom != null && targetRoom.getCollegeCode() != null
                    && head.getCollegeCode() != null)
                authorized = head.getCollegeCode().equalsIgnoreCase(targetRoom.getCollegeCode());
        } else if (sender instanceof DepartmentHead head && recipient instanceof Student student) {
            CourseOffering offering = offeringID == null || offeringID.isBlank()
                    ? null : CourseOffering.findOffering(offeringID);
            boolean enrolled = false;
            for (CourseRegistration registration : CourseRegistration.getRegistrationsByStudent(student.getUserID())) {
                if (registration != null && offering != null
                        && registration.getOfferingID().equalsIgnoreCase(offering.getOfferingID())) {
                    enrolled = true;
                    break;
                }
            }
            Room targetRoom = Room.findRoom(roomCode);
            authorized = enrolled && offering != null
                    && head.getCollegeCode() != null && targetRoom != null
                    && targetRoom.getCollegeCode() != null
                    && head.getCollegeCode().equalsIgnoreCase(targetRoom.getCollegeCode())
                    && (courseCode == null || courseCode.isBlank()
                    || offering.getCourseCode().equalsIgnoreCase(courseCode.trim()));
        }

        if (!authorized) {
            UI.print("Notification was not sent: sender and recipient are not related to this action.");
            return false;
        }
        if (message == null || message.trim().isEmpty()) {
            UI.print("Notification message cannot be blank.");
            return false;
        }
        if (roomCode != null && !roomCode.trim().isEmpty() && Room.findRoom(roomCode) == null) {
            UI.print("Notification was not sent: room does not exist or is archived.");
            return false;
        }
        if (courseCode != null && !courseCode.trim().isEmpty() && Course.findCourse(courseCode) == null) {
            UI.print("Notification was not sent: course does not exist.");
            return false;
        }
        boolean hasTime = (startTime != null && !startTime.trim().isEmpty())
                || (endTime != null && !endTime.trim().isEmpty());
        if (hasTime) {
            String convertedStart = UI.convertTime(startTime);
            String convertedEnd = UI.convertTime(endTime);
            if (convertedStart.isEmpty() || convertedEnd.isEmpty()) return false;
            try {
                if (!LocalTime.parse(convertedStart, UI.TIME_FORMAT)
                        .isBefore(LocalTime.parse(convertedEnd, UI.TIME_FORMAT))) {
                    UI.print("Notification time range is invalid.");
                    return false;
                }
            } catch (DateTimeParseException e) {
                UI.print("Notification time range is invalid.");
                return false;
            }
        }

        CourseOffering offering = offeringID == null || offeringID.isBlank()
                ? null : CourseOffering.findOffering(offeringID);
        if (!safeText(offeringID).isEmpty() && (offering == null
                || (sender instanceof Faculty && (offering.getFacultyID() == null
                || !offering.getFacultyID().equalsIgnoreCase(sender.getUserID())))
                || (sender instanceof DepartmentHead && recipient instanceof Faculty
                && (offering.getFacultyID() == null
                || !offering.getFacultyID().equalsIgnoreCase(recipient.getUserID())))
                || (recipient instanceof Student && CourseRegistration.getRegistrationsByStudent(recipient.getUserID())
                .stream().noneMatch(r -> r != null && r.getOfferingID().equalsIgnoreCase(offering.getOfferingID()))))) {
            UI.print("Notification was not sent: course offering is not related to the sender and recipient.");
            return false;
        }
        notifications.add(new Notification(sender.getUserID(), recipient.getUserID(), courseCode,
                roomCode, buildingName, day, startTime, endTime, message,
                offeringID, requestID, meetingDate));
        return true;
    }

    // GETTER METHODS
    public String getNotificationID() {
        return notificationID;
    }
    public String getSenderID() {
        return this.senderID;
    }
    public String getRecipientID() {
        return this.recipientID;
    }
    public String getCourseCode() {
        return this.courseCode;
    }
    public String getCourseID() { return this.courseID; }
    public String getOfferingID() { return this.offeringID; }
    public String getRequestID() { return this.requestID; }
    public String getSectionID() { return this.sectionID; }
    public String getSectionName() { return this.sectionName; }
    public String getRoomDescription() { return this.roomDescription; }
    public String getBuildingCode() { return this.buildingCode; }
    public String getMeetingDate() { return this.meetingDate; }
    public String getRoomCode() {
        return this.roomCode;
    }
    public String getBuildingName() {
        return this.buildingName;
    }
    public String getDay() {
        return this.day;
    }
    public String getStartTime() {
        return this.startTime;
    }
    public String getEndTime() {
        return this.endTime;
    }
    public String getMessage() {
        return this.message;
    }
    public String getCreatedAt() {
        return this.createdAt;
    }
    public static ArrayList<Notification> getNotificationsByRecipient(String recipientID) {
        ArrayList<Notification> received = new ArrayList<>();

        if (recipientID == null || recipientID.isBlank()) return received;

        for (Notification n : notifications) {
            if (n != null && recipientID.trim().equalsIgnoreCase(n.recipientID)) {
                received.add(n);
            }
        }
        return received;
    }

    public static ArrayList<Notification> getArchivedNotificationsByRecipient(String recipientID) {
        ArrayList<Notification> received = new ArrayList<>();
        if (recipientID == null || recipientID.isBlank()) return received;
        for (Notification n : archiveNotifications) {
            if (n != null && recipientID.trim().equalsIgnoreCase(n.recipientID)) received.add(n);
        }
        return received;
    }

    public static boolean archiveNotification(String recipientID, String notificationID) {
        if (recipientID == null || recipientID.isBlank() || notificationID == null || notificationID.isBlank()) {
            UI.print("Recipient and notification IDs are required.");
            return false;
        }
        Notification selected = null;
        for (Notification n : notifications) {
            if (n != null && n.notificationID.equalsIgnoreCase(notificationID.trim())) {
                selected = n;
                break;
            }
        }
        if (selected == null) {
            UI.print("Active notification not found.");
            return false;
        }
        if (!selected.recipientID.equalsIgnoreCase(recipientID.trim())) {
            UI.print("You can only archive notifications sent to your account.");
            return false;
        }
        notifications.remove(selected);
        if (!archiveNotifications.contains(selected)) archiveNotifications.add(selected);
        return true;
    }

    // FIND METHOD
    public static Notification findNotification(String notificationID) {
        if (notificationID == null || notificationID.trim().isEmpty()) return null;
        for (Notification n : notifications) {
            if (n != null && notificationID.trim().equalsIgnoreCase(n.notificationID)) return n;
        }
        return null;
    }

    private static String displayValue(String value) {
        return value == null || value.trim().isEmpty() ? "-" : value.trim();
    }

    // DISPLAY METHODS
    public static void displayNotification(String notificationID, boolean print) {
        Notification n = findNotification(notificationID);

        if (n == null) {
            UI.print("Notification not found");
            return;
        }

        String text = n.getMessage() == null || n.getMessage().isBlank() ? "-"
                : n.getMessage().replace('\n', ' ').replace('\r', ' ').trim();
        UI.header("NOTIFICATION DETAILS", true);
        UI.liner(String.format("  %-18s : %s", "NOTIFICATION ID", n.getNotificationID()), true);
        UI.liner(String.format("  %-18s : %s", "SENDER", n.getSenderID()), true);
        UI.liner(String.format("  %-18s : %s", "RECIPIENT", n.getRecipientID()), true);
        UI.liner(String.format("  %-18s : %s", "CREATED AT", n.getCreatedAt()), true);
        UI.liner(String.format("  %-18s : %s", "REQUEST ID", displayValue(n.getRequestID())), true);
        UI.liner(String.format("  %-18s : %s", "OFFERING ID", displayValue(n.getOfferingID())), true);
        UI.liner(String.format("  %-18s : %s", "COURSE ID / CODE", displayValue(n.getCourseID())), true);
        UI.liner(String.format("  %-18s : %s", "SECTION ID", displayValue(n.getSectionID())), true);
        UI.liner(String.format("  %-18s : %s", "SECTION", displayValue(n.getSectionName())), true);
        UI.liner(String.format("  %-18s : %s", "ROOM CODE", displayValue(n.getRoomCode())), true);
        UI.liner(String.format("  %-18s : %s", "ROOM", displayValue(n.getRoomDescription())), true);
        UI.liner(String.format("  %-18s : %s", "BUILDING CODE", displayValue(n.getBuildingCode())), true);
        UI.liner(String.format("  %-18s : %s", "BUILDING", displayValue(n.getBuildingName())), true);
        UI.liner(String.format("  %-18s : %s", "MEETING DATE", displayValue(n.getMeetingDate())), true);
        UI.liner(String.format("  %-18s : %s", "DAY / TIME", displayValue(n.getDay()) + " | "
                + displayValue(UI.formatTime(n.getStartTime())) + " - "
                + displayValue(UI.formatTime(n.getEndTime()))), true);
        UI.separator(true);
        UI.liner("  MESSAGE:", true);
        while (!text.isEmpty()) {
            int end = Math.min(text.length(), 150);
            if (end < text.length() && text.lastIndexOf(' ', end) > 0) end = text.lastIndexOf(' ', end);
            UI.liner("  " + text.substring(0, end).trim(), true);
            text = text.substring(end).trim();
        }
        UI.footer(true);
        if(print) UI.returnButton();
    }
    public static void displayNotifications() {
        UI.header("NOTIFICATIONS", true);

        if (notifications.isEmpty()) {
            UI.liner("  No notifications found.", true);
        } else {
            UI.liner(String.format("  %-10s %-12s %-12s %-20s %-12s %-10s %-96s",
                    "ID", "SENDER", "RECIPIENT", "DATE / TIME", "COURSE", "ROOM", "MESSAGE"), true);
            UI.separator(true);

            int count = 0;
            for (Notification notification : notifications) {
                if (notification == null) continue;
                UI.liner(String.format("  %-10s %-12s %-12s %-20s %-12s %-10s %-96s",
                        notification.getNotificationID(), notification.getSenderID(),
                        notification.getRecipientID(), UI.fitText(notification.getCreatedAt(), 20),
                        UI.fitText(notification.getCourseCode(), 12),
                        UI.fitText(notification.getRoomCode(), 10),
                        notification.getMessage()), true);
                count++;
            }
            UI.separator(true);
            UI.liner("  TOTAL NOTIFICATIONS: " + count, true);
        }

        UI.footer(true);
        UI.returnButton();
    }
    public static void displayArchiveNotifications() {
        UI.header("ARCHIVED NOTIFICATIONS", true);

        if (archiveNotifications.isEmpty()) {
            UI.liner("  No archived notifications found.", true);
        } else {
            UI.liner(String.format("  %-10s %-12s %-12s %-20s %-12s %-10s %-96s",
                    "ID", "SENDER", "RECIPIENT", "DATE / TIME", "COURSE", "ROOM", "MESSAGE"), true);
            UI.separator(true);

            int count = 0;
            for (Notification notification : archiveNotifications) {
                if (notification == null) continue;
                UI.liner(String.format("  %-10s %-12s %-12s %-20s %-12s %-10s %-96s",
                        notification.getNotificationID(), notification.getSenderID(),
                        notification.getRecipientID(), UI.fitText(notification.getCreatedAt(), 20),
                        UI.fitText(notification.getCourseCode(), 12),
                        UI.fitText(notification.getRoomCode(), 10),
                        notification.getMessage()), true);
                count++;
            }
            UI.separator(true);
            UI.liner("  TOTAL ARCHIVED NOTIFICATIONS: " + count, true);
        }

        UI.footer(true);
        UI.returnButton();
    }
    public static void archiveNotifications() {
        UI.print("Archiving requires the currently logged-in recipient account.");
    }

    public static void archiveNotifications(String recipientID) {
        ArrayList<Notification> received = getNotificationsByRecipient(recipientID);
        if (received.isEmpty()) {
            UI.print("You have no active notifications to archive.");
            return;
        }
        UI.header("ARCHIVE MY NOTIFICATION", true);
        UI.liner(String.format("  %-5s %-12s %-14s %-18s %-14s %-110s",
                "NO.", "ID", "FROM", "DATE", "COURSE", "MESSAGE"), true);
        UI.separator(true);
        for (int i = 0; i < received.size(); i++) {
            Notification n = received.get(i);
            UI.liner(String.format("  %-5d %-12s %-14s %-18s %-14s %-110s",
                    i + 1, n.getNotificationID(), n.getSenderID(), n.getCreatedAt(),
                    UI.fitText(n.getCourseCode(), 14), UI.fitText(n.getMessage(), 107)), true);
        }
        UI.footer(true);
        String input;
        Notification selected = null;
        do {
            UI.select("Enter Notification ID", false);
            input = in.nextLine().trim();
            if (input.equalsIgnoreCase("X")) return;
            for (Notification n : received) {
                if (n.getNotificationID().equalsIgnoreCase(input)) selected = n;
            }
            if (selected == null) UI.print("Choose a notification from your own active list.");
        } while (selected == null);
        displayNotification(selected.getNotificationID(), false);
        String choice;
        do {
            UI.select("Confirm Archive (Y/N)", false);
            choice = in.nextLine().trim().toUpperCase(Locale.ENGLISH);
            if (choice.equals("X")) return;
            if (!choice.matches("[YN]")) UI.print("Enter Y or N.");
        } while (!choice.matches("[YN]"));
        if (choice.equals("Y") && archiveNotification(recipientID, selected.getNotificationID()))
            UI.print("Notification archived successfully.");
        else if (choice.equals("N")) UI.print("Archiving cancelled.");
    }
}