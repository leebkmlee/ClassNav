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

    public Notification(String senderID, String recepientID, String courseCode, String roomCode, String buildingName,
                        String day, String startTime, String endTime, String message) { // message kay prof
        this.notificationID = nextNotificationID++;
        this.senderID = senderID;
        this.recipientID = recepientID;
        this.courseCode = courseCode;
        this.roomCode = roomCode;
        this.buildingName = buildingName;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.message = message;
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

    public static Notification findNotification (int notificationID) {
        for (Notification n : notifications) if (notificationID == n.notificationID) return n;
        return null;
    }
    public static int getNotification(Notification n) {
        return n.notificationID;
    }
    public static String getSenderID(Notification n) {return n.senderID;}
    public static String getRecipientID(Notification n) {return n.recipientID;}
    public static String getRoomCode(Notification n) {return n.roomCode;}
    public static String getBuildingName(Notification n) {return n.buildingName;}
    public static String getDay(Notification n) {return n.day;}
    public static String getStartTime(Notification n) {return n.startTime;}
    public static String getEndTime(Notification n) {return n.endTime;}
    public static String getMessage(Notification n) {return n.message;}
}
