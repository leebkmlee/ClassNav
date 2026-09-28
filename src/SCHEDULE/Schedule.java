package SCHEDULE;

public class Schedule {
    private String scheduleID;
    private String day;
    private String startTime;
    private String endTime;
    private String sectionID; // foreign key
    private String roomCode; // foreign key

    Schedule(String scheduleID, String day, String startTime, String endTime, String sectionID, String roomCode) {
        this.scheduleID = scheduleID;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.sectionID = sectionID;
        this.roomCode = roomCode;
    }
}
