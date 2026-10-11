package DATA;

import java.util.ArrayList;

public class Notification {
    private static int notificationID = 0;
    private String senderID;
    private String recepientID;
    private String roomCode;
    private String buildingName;
    private String day;
    private String startTime;
    private String endTime;
    private String message;
    public static ArrayList<Notification> notifications = new ArrayList<>();

    public Notification(String senderID, String recepientID, String roomCode, String buildingName,
                        String day, String startTime, String endTime, String message) { // message kay prof
        notificationID++;
        this.senderID = senderID;
        this.recepientID = recepientID;
        this.roomCode = roomCode;
        this.buildingName = buildingName;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.message = message;
    }


}
