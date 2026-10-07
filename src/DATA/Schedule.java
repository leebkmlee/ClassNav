package DATA;
import java.util.ArrayList;
import UI.*;

public class Schedule {
    private static final int innerWidth = 70;
    private String scheduleID;
    private  String day;
    private  String startTime;
    private  String endTime;
    private  String sectionID; // foreign key
    private  String roomCode; // foreign key

    public Schedule(String scheduleID, String day, String startTime, String endTime, String sectionID, String roomCode) {
        this.scheduleID = scheduleID;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.sectionID = sectionID;
        this.roomCode = roomCode;
    }

    public String getScheduleID(){
        return scheduleID;
    }

    public String getDayTime(){
        return  day + " " +startTime + " - " + endTime;
    }

    public static ArrayList<Schedule> schedules = new ArrayList<>();
    public Schedule() {
        schedules.add(new Schedule("101", "Monday", "7:00am", "10:00am", "2B", "Lab101"));
        schedules.add(new Schedule("102", "Tuesday", "1:00pm", "2:30pm", "2B", "CICT201"));
        schedules.add(new Schedule("103", "Wednesday", "5:00pm", "8:00pm", "2B", "NSTP103"));
    }

    public void displaySchedule(){
        UI.header("Schedules");
        System.out.printf("│  ROOM        : %-" + (innerWidth - 16) + "s│\n", roomCode);
        System.out.printf("│  SECTION     : %-" + (innerWidth - 16) + "s│\n", sectionID);
        System.out.printf("│  DAY         : %-" + (innerWidth - 16) + "s│\n", day);
        System.out.printf("│  TIME START  : %-" + (innerWidth - 16) + "s│\n", startTime);
        System.out.printf("│  END TIME    : %-" + (innerWidth - 16) + "s│\n", endTime);
        UI.footer();
    }
}
