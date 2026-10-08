package DATA;
import UI.UI;

import java.util.ArrayList;
import static DATA.Schedule.schedules;

public class Room {
    private static final int innerWidth = 70;
    private String roomCode;
    private String roomDescription;
    private int floorNumber;
    private String buildingCode;


    public Room(String roomCode, String roomDescription, int floorNumber, String buildingCode) {
        this.roomCode = roomCode;
        this.roomDescription = roomDescription;
        this.floorNumber = floorNumber;
        this.buildingCode = buildingCode;
    }

    public static ArrayList<Room> rooms = new ArrayList<>();

    static {
        rooms.add(new Room("Lab101", "Programming Laboratory 1", 1, "BLD01"));
        rooms.add(new Room("CICT201", "Smart Development Lab", 2, "BLD02"));
        rooms.add(new Room("NSTP103", "Acad Room 3", 1, "BLD03"));
    }

    public String getRoomCode() {
        return roomCode;
    }

    public String getRoomDescription() {
        return roomDescription;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public String getBuildingCode() {
        return buildingCode;
    }

    public static int convertToMinutes(String time) {
        time = time.toLowerCase().replace(" ", "");

        String period = time.substring(time.length() - 2);
        String[] parts = time.substring(0, time.length() - 2).split(":");

        int hour = Integer.parseInt(parts[0]);
        int minute = Integer.parseInt(parts[1]);

        if (period.equals("am")) {
            if (hour == 12) {
                hour = 0;
            }
        } else if (period.equals("pm")) {
            if (hour != 12) {
                hour += 12;
            }
        }
        return hour * 60 + minute;
    }

    public String checkAvailability() {

        String sampleRoomCode = "CICT201";
        String sampleDay = "tuesday";
        String sampleStartTime = "1:00pm";
        String sampleEndTime = "2:30pm";
        int sampleStartTime1 = convertToMinutes(sampleStartTime);
        int sampleEndTime1 = convertToMinutes(sampleEndTime);
        boolean isAvail = true;
        String availability = "Room is Available";
        int temp;
        for (Schedule s : schedules) {
            if (s.getRoomCode().equals(sampleRoomCode) && s.getDay().equals(sampleDay) && sampleStartTime1 >= (temp = convertToMinutes(s.getStartTime())) &&
                    sampleEndTime1 <= (temp = convertToMinutes(s.getEndTime()))) {
                isAvail = false;
            }
        }
        if (!isAvail) {
            availability = "Room Occupied.";
        }
        return availability;
    }

    public void displayRoom(){
        UI.header("Room Details");
        System.out.printf("│  BUILDING CODE   : %-" + (innerWidth - 16) + "s│\n", buildingCode);
        System.out.printf("│  FLOOR NO.       : %-" + (innerWidth - 16) + "s│\n", floorNumber);
        System.out.printf("│  ROOM CODE       : %-" + (innerWidth - 16) + "s│\n", roomCode);
        System.out.printf("│  ROOM NAME       : %-" + (innerWidth - 16) + "s│\n", roomDescription);
        UI.footer();
    }
}


