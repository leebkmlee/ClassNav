package DATA;

import java.util.ArrayList;

public class Notification {
    private static int notificationID = 1;
    private String senderID;
    private String recipientID;
    private String roomCode;
    private String buildingName;
    private String day;
    private String startTime;
    private String endTime;
    private String message;

    public static ArrayList<Notification> notifications = new ArrayList<>();

    // stud to prof
    public Notification(String senderID, String recepientID, String roomCode, String buildingName,
                        String day, String startTime, String endTime, String message) { // message kay prof
        notificationID++;
        this.senderID = senderID;
        this.recipientID = recepientID;
        this.roomCode = roomCode;
        this.buildingName = buildingName;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.message = message;
    }

    public static void setNotificationID(){notificationID++;}

    public static int getNotificationID() {return notificationID;}
    public String getSenderID() {return senderID;}
    public String getRecipientID() {return recipientID;}
    public String getRoomCode() {return roomCode;}
    public String getBuildingName() {return buildingName;}
    public String getDay() {return day;}
    public String getStartTime() {return startTime;}
    public String getEndTime() {return endTime;}
    public String getMessage() {return message;}
}
