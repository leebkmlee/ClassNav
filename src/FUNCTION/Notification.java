
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

    public int getNotificationID() {
        return notificationID;
    }
    public static Notification findNotification(int notificationID) {
        for (Notification n : notifications) {
            if (n != null && n.notificationID == notificationID) return n;
        }
        return null;
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