package DATA;
import java.util.ArrayList;
import UI.*;

public class Schedule {
    private static int nextScheduleID = 1;
    private String scheduleID;
    private String day;
    private String startTime;
    private String endTime;
    private String effectiveFrom;
    private String effectTo;
    private String sectionID; // foreign key
    private String roomCode; // foreign key

    public Schedule(String day, String startTime, String endTime, String effectiveFrom, String effectTo,
                    String sectionID, String roomCode) {
        this.scheduleID = String.format("SCH%04d", nextScheduleID++);
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.effectiveFrom = effectiveFrom;
        this.effectTo = effectTo;
        this.sectionID = sectionID;
        this.roomCode = roomCode;
    }

    public static ArrayList<Schedule> schedules = new ArrayList<>();

    static {
        schedules.add(new Schedule("M", "7:00AM", "8:30AM", "08/22/2026",
                "12/13/2026", "0001", "PL101"));
        schedules.add(new Schedule("M", "9:00AM", "10:30AM", "08/22/2026",
                "12/13/2026", "0003", "PL101"));
        schedules.add(new Schedule("M", "7:00AM", "8:30AM", "08/22/2026",
                "12/13/2026", "0005", "ACAD 1"));
        schedules.add(new Schedule("M", "9:00AM", "10:30AM", "08/22/2026",
                "12/13/2026", "0008", "ACAD 1"));
        schedules.add(new Schedule("T", "8:00AM", "9:30AM", "08/22/2026",
                "12/13/2026", "0002", "SDL 1"));
        schedules.add(new Schedule("T", "10:00AM", "11:30AM", "08/22/2026",
                "12/13/2026", "0006", "SDL 1"));
        schedules.add(new Schedule("T", "1:00PM", "2:30PM", "08/22/2026",
                "12/13/2026", "0004", "ICT301"));
        schedules.add(new Schedule("T", "3:00PM", "4:30PM", "08/22/2026",
                "12/13/2026", "0007", "ICT301"));
        schedules.add(new Schedule("W", "7:00AM", "8:30AM", "08/22/2026",
                "12/13/2026", "0009", "PL102"));
        schedules.add(new Schedule("W", "9:00AM", "10:30AM", "08/22/2026",
                "12/13/2026", "0010", "PL102"));
        schedules.add(new Schedule("W", "1:00PM", "2:30PM", "08/22/2026",
                "12/13/2026", "0011", "NH201"));
        schedules.add(new Schedule("W", "3:00PM", "4:30PM", "08/22/2026",
                "12/13/2026", "0012", "NH201"));
        schedules.add(new Schedule("TH", "8:00AM", "9:30AM", "08/22/2026",
                "12/13/2026", "0001", "PL201"));
        schedules.add(new Schedule("TH", "10:00AM", "11:30AM", "08/22/2026",
                "12/13/2026", "0004", "PL201"));
        schedules.add(new Schedule("F", "1:00PM", "2:30PM", "08/22/2026",
                "12/13/2026", "0002", "ICT302"));
        schedules.add(new Schedule("F", "3:00PM", "4:30PM", "08/22/2026",
                "12/13/2026", "0006", "ICT302"));
    }

    // SCHEDULE GETTER
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
    public static String getEffectiveFrom(Schedule s) {
        return s.effectiveFrom;
    }
    public static String getEffectiveTo(Schedule s) {
        return s.effectTo;
    }
    public static String getSectionID(Schedule s) {
        return s.sectionID;
    }
    public static String getRoomCode(Schedule s){
        return s.roomCode;
    }
}
