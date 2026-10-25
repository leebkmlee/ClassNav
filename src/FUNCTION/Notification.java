
package FUNCTION;

import java.util.ArrayList;
import java.util.Objects;

import DATA.Building;
import DATA.Room;
import UI.*;

import static UI.UI.in;

public class Notification {

    // FIELDS
    private static int nextNotificationID = 1;

    private final String notificationID, senderID, recipientID, courseCode, roomCode, buildingName, day, startTime,
                         endTime, message;

    public static ArrayList<Notification> notifications = new ArrayList<>();
    public static ArrayList<Notification> archiveNotifications = new ArrayList<>();

    // CONSTRUCTOR
    public Notification(String senderID, String recipientID, String courseCode, String roomCode,
                        String buildingName, String day, String startTime, String endTime, String message) {
        this.notificationID = String.format("N%03d", nextNotificationID++);
        this.senderID = senderID.trim();
        this.recipientID = recipientID.trim();
        this.courseCode = courseCode.trim();
        this.roomCode = roomCode.trim();
        this.buildingName = buildingName.trim();
        this.day = day.trim();
        this.startTime = startTime.trim();
        this.endTime = endTime.trim();
        this.message = message.trim();
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

    // FIND METHOD
    public static Notification findNotification(String notificationID) {
        for (Notification n : notifications) {
            if (n != null && notificationID.equals(n.notificationID)) return n;
        }
        return null;
    }

    // DISPLAY METHODS
    public static void displayNotification(String notificationID) {
        Notification n = findNotification(notificationID);

        if (n == null) {
            UI.print("Notification not found");
            return;
        }

        String text = n.getMessage();
        if (text.length() > 30) text = text.substring(0, 30) + "...";

        UI.header("BUILDING DETAILS");
        UI.liner(String.format("  NOTIFICAITON ID : %s", n.getNotificationID()));
        UI.liner(String.format("  SENDER          : %s", n.getSenderID()));
        UI.liner(String.format("  RECIPIENT       : %s", n.getRecipientID()));
        UI.liner(String.format("  MESSAGE         : %s", text));
        UI.footer();
    }
    public static void displayNotifications() {

        UI.header("NOTIFICATIONS");

        if (notifications.isEmpty()) {
            UI.liner("  No notifications found.");
        } else {
            for (Notification n : notifications) {
                UI.liner(String.format("  %-12s %-12s %-12s %-12s",
                        n.notificationID,
                        n.senderID,
                        n.recipientID,
                        n.message));
            }
        }

        UI.footer();
    }
    public static void displayArchiveNotifications() {
        UI.header("ARCHIVED NOTIFICATIONS");

        if (archiveNotifications.isEmpty()) {
            UI.liner("  No archived notifications available.");
            UI.footer();
            return;
        }

        UI.liner(String.format("  %-5s %-9s %-9s %-9s %-10s %-12s",
                "ID", "SENDER", "RECIPIENT", "COURSE", "ROOM", "MESSAGE"));

        UI.separator();

        for (Notification n : archiveNotifications) {
            UI.liner(String.format("  %-5d %-9s %-9s %-9s %-10s %-12s",
                    n.notificationID, n.senderID, n.recipientID, n.courseCode, n.roomCode, n.message));
        }

        UI.footer();
    }
    public static void archiveNotifications() {
        String input; String choice;
        UI.header("ARCHIVE NOTIFICATIONS");

        do {
            System.out.print("  Enter Notification Code > ");
            input = in.nextLine().trim();
            if (input.isEmpty()) UI.print("Invalid Input");
            else if (findNotification(input) != null) UI.print("Notification does not exist");
        } while (input.isEmpty() || findNotification(input) != null);

        Notification n = findNotification(input);

        if (n == null) {
            UI.print("Notification not found");
            return;
        }

        displayNotification(n.getNotificationID());

        do {
            System.out.print("  Confirm Notification Deletion? (Y/N) > ");
            choice = in.nextLine().trim().toUpperCase();

            if (!choice.matches("[YN]")) {
                UI.print("Invalid Input");
            }
        } while (!choice.matches("[YN]"));

        if (choice.equals("Y")) {
            archiveNotifications.add(n);
            notifications.remove(n);
            UI.print("Notification successfully archived");
        } else {
            UI.print("Archiving cancelled");
        }

    }
}