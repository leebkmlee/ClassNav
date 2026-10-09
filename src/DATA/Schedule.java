package DATA;
import java.util.ArrayList;
import UI.*;

public class Schedule {
    private static final int innerWidth = 70;
    private String scheduleID;
    private String day;
    private String startTime;
    private String endTime;
    private String sectionID; // foreign key
    private String roomCode; // foreign key

    public Schedule(String scheduleID, String day, String startTime, String endTime, String sectionID, String roomCode) {
        this.scheduleID = scheduleID;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.sectionID = sectionID;
        this.roomCode = roomCode;
    }

    public static ArrayList<Schedule> schedules = new ArrayList<>();
    static {
        schedules.add(new Schedule("101", "M", "7:00AM", "10:00AM", "2B", "PL101"));
        schedules.add(new Schedule("102", "T", "1:00PM", "2:30PM", "2B", "SDL 1"));
        schedules.add(new Schedule("103", "W", "5:00PM", "8:00PM", "2B", "ACAD 3"));
    }

    public String getScheduleID(){
        return scheduleID;
    }

    public static Schedule findSchedule(String scheduleID) {
        for (Schedule s : schedules) if (scheduleID.equals(s.getScheduleID())) return s;
        return null;
    }

    public static void addSchedule(Schedule schedule) {
        schedules.add(schedule);
    }

    public static String getSchedule(Schedule s) {
        return s.scheduleID;
    }

    public static String getDay(Schedule s){
        return s.day;
    }

    public static String getStartTime(Schedule s){
        return s.startTime;
    }

    public static String getEndTime(Schedule s){
        return s.endTime;
    }

    public static String getSectionID(Schedule s) {
        return s.sectionID;
    }

    public static String getRoomCode(Schedule s){
        return s.roomCode;
    }

    public static void displaySchedule(String scheduleID){
        Schedule s = findSchedule(scheduleID);
        if (s == null) {
            UI.print("Schedule not found");
            return;
        }
        UI.header("SCHEDULE INFORMATION");
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("  SCHEDULE ID    : %s", Schedule.getSchedule(s)));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("  DATE AND TIME  : %s",
                Schedule.getDay(s) + " " + Schedule.getStartTime(s) + "-" + Schedule.getEndTime(s)));
        UI.footer();
    }
}
