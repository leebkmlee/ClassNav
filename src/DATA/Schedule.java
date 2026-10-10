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
        schedules.add(new Schedule("SCH001", "M", "7:00AM", "8:30AM", "0001", "PL101"));
        schedules.add(new Schedule("SCH002", "M", "9:00AM", "10:30AM", "0003", "PL101"));
        schedules.add(new Schedule("SCH003", "M", "7:00AM", "8:30AM", "0005", "ACAD 1"));
        schedules.add(new Schedule("SCH004", "M", "9:00AM", "10:30AM", "0008", "ACAD 1"));
        schedules.add(new Schedule("SCH005", "T", "8:00AM", "9:30AM", "0002", "SDL 1"));
        schedules.add(new Schedule("SCH006", "T", "10:00AM", "11:30AM", "0006", "SDL 1"));
        schedules.add(new Schedule("SCH007", "T", "1:00PM", "2:30PM", "0004", "ICT301"));
        schedules.add(new Schedule("SCH008", "T", "3:00PM", "4:30PM", "0007", "ICT301"));
        schedules.add(new Schedule("SCH009", "W", "7:00AM", "8:30AM", "0009", "PL102"));
        schedules.add(new Schedule("SCH010", "W", "9:00AM", "10:30AM", "0010", "PL102"));
        schedules.add(new Schedule("SCH011", "W", "1:00PM", "2:30PM", "0011", "NH201"));
        schedules.add(new Schedule("SCH012", "W", "3:00PM", "4:30PM", "0012", "NH201"));
        schedules.add(new Schedule("SCH013", "TH", "8:00AM", "9:30AM", "0001", "PL201"));
        schedules.add(new Schedule("SCH014", "TH", "10:00AM", "11:30AM", "0004", "PL201"));
        schedules.add(new Schedule("SCH015", "F", "1:00PM", "2:30PM", "0002", "ICT302"));
        schedules.add(new Schedule("SCH016", "F", "3:00PM", "4:30PM", "0006", "ICT302"));
    }

    public String getScheduleID(){
        return scheduleID;
    }

    public static Schedule findSchedule(String scheduleID) {
        for (Schedule s : schedules) if (scheduleID.equals(s.getScheduleID())) return s;
        return null;
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
