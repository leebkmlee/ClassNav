
package FUNCTION;

import java.util.ArrayList;

public class Notification {

    private static int nextNotificationID = 1;

    private int notificationID;
    private String senderID;
    private String recipientID;
    private String courseCode;
    private String roomCode;
    private String buildingName;
    private String day;
    private String startTime;
    private String endTime;
    private String message;

    public Notification(String senderID, String recipientID, String courseCode, String roomCode,
                        String buildingName, String day, String startTime, String endTime, String message) {

        if (senderID == null || senderID.isBlank()
                || recipientID == null || recipientID.isBlank()
                || courseCode == null || courseCode.isBlank()
                || roomCode == null || roomCode.isBlank()
                || buildingName == null || buildingName.isBlank()
                || day == null || day.isBlank()
                || startTime == null || startTime.isBlank()
                || endTime == null || endTime.isBlank()
                || message == null || message.isBlank()) {
            throw new IllegalArgumentException("Notification fields cannot be empty.");
        }

        this.notificationID = nextNotificationID++;
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

    public static ArrayList<Notification> notifications = new ArrayList<>();
    static {
        notifications.add(new Notification("S001", "F001", "CC 106", "R101",
                "Pimentel Hall", "M", "08:00 AM", "10:00 AM",
                "Room is available"));

        notifications.add(new Notification("S002", "F001", "IT 206", "R202",
                "Rizal Building", "T", "10:00 AM", "12:00 PM",
                "Room is occupied"));

        notifications.add(new Notification("S003", "F002", "CC 103", "R303",
                "CIT Building", "W", "01:00 PM", "03:00 PM",
                "Room is available"));

        notifications.add(new Notification("S004", "F001", "IT 206", "R102",
                "Pimentel Hall", "Th", "08:00 AM", "11:00 AM",
                "Room is occupied"));

        notifications.add(new Notification("S005", "F002", "CC 106", "R201",
                "Rizal Building", "F", "02:00 PM", "04:00 PM",
                "Hello po maam available po yung room"));
    }

    public int getNotificationID() {
        return notificationID;
    }

    public static Notification findNotification(int notificationID) {
        for (Notification n : notifications) {
            if (n != null && n.notificationID == notificationID) return n;
        }
        return null;
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

    public static int getNotification(Notification n) {
        return n == null ? -1 : n.notificationID;
    }

    public static String getSenderID(Notification n) {
        return n == null ? null : n.senderID;
    }

    public static String getRecipientID(Notification n) {
        return n == null ? null : n.recipientID;
    }

    public static String getCourseCode(Notification n) {
        return n == null ? null : n.courseCode;
    }

    public static String getRoomCode(Notification n) {
        return n == null ? null : n.roomCode;
    }

    public static String getBuildingName(Notification n) {
        return n == null ? null : n.buildingName;
    }

    public static String getDay(Notification n) {
        return n == null ? null : n.day;
    }

    public static String getStartTime(Notification n) {
        return n == null ? null : n.startTime;
    }

    public static String getEndTime(Notification n) {
        return n == null ? null : n.endTime;
    }

    public static String getMessage(Notification n) {
        return n == null ? null : n.message;
    }
}