package DATA;
import UI.UI;

import java.util.ArrayList;

import static DATA.Schedule.schedules;

public class Room {
    private static final int innerWidth = 70;
    private String roomCode;
    private String roomDescription;
    private int floorNumber;
    private String buildingCode; // foreign key


    public Room(String roomCode, String roomDescription, int floorNumber, String buildingCode) {
        this.roomCode = roomCode;
        this.roomDescription = roomDescription;
        this.floorNumber = floorNumber;
        this.buildingCode = buildingCode;
    }

    public static ArrayList<Room> rooms = new ArrayList<>();

    static {
        rooms.add(new Room("PL101", "Programming Laboratory 1", 1, "BLD01"));
        rooms.add(new Room("PL102", "Programming Laboratory 2", 1, "BLD01"));
        rooms.add(new Room("PL201", "Computer Laboratory 3", 2, "BLD01"));
        rooms.add(new Room("SDL 1", "Smart Development Lab 1", 2, "BLD02"));
        rooms.add(new Room("SDL 2", "Smart Development Lab 2", 2, "BLD02"));
        rooms.add(new Room("ACAD 1", "Academic Room 1", 1, "BLD03"));
        rooms.add(new Room("ACAD 2", "Academic Room 2", 1, "BLD03"));
        rooms.add(new Room("ACAD 3", "Academic Room 3", 1, "BLD03"));
        rooms.add(new Room("NH201", "Lecture Room 201", 2, "BLD04"));
        rooms.add(new Room("NH202", "Lecture Room 202", 2, "BLD04"));
        rooms.add(new Room("ICT301", "ICT Lecture Room 301", 3, "BLD05"));
        rooms.add(new Room("ICT302", "ICT Lecture Room 302", 3, "BLD05"));
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public void setRoomDescription(String roomDescription) {
        this.roomDescription = roomDescription;
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public void setBuildingCode(String buildingCode) {
        this.buildingCode = buildingCode;
    }

    public static Room findRoom(String roomCode) {
        for (Room r : rooms) if (roomCode.equals(r.getRoomCode())) return r;
        return null;
    }

    public static String getRoom(Room r) {
        return r.roomCode;
    }

    public static String getRoomDescription(Room r) {
        return r.roomDescription;
    }

    public static int getFloorNumber(Room r) {
        return r.floorNumber;
    }

    public static String getBuildingCode(Room r) {
        return r.buildingCode;
    }

    public static boolean existRoomCode(String roomCode) {
        for (Room r : rooms) if (roomCode.equals(r.roomCode)) return true;
        return false;
    }

    public static boolean existRoomDesc(String roomDescription) {
        for (Room r : rooms) if (roomDescription.equals(r.roomDescription)) return true;
        return false;
    }

    public static int convertToMinutes(String time) {
        time = time.toUpperCase().replace(" ", "");

        String period = time.substring(time.length() - 2);
        String[] parts = time.substring(0, time.length() - 2).split(":");

        int hour = Integer.parseInt(parts[0]);
        int minute = Integer.parseInt(parts[1]);

        if (period.equals("AM")) {
            if (hour == 12) {
                hour = 0;
            }
        } else if (period.equals("PM")) {
            if (hour != 12) {
                hour += 12;
            }
        }
        return hour * 60 + minute;
    }

    public static void displayRoom(String roomCode){
        Room r = findRoom(roomCode);
        if (r == null) {
            UI.print("Room not found");
            return;
        }
        UI.header("ROOM INFORMATION");
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("  ROOM CODE         : %s", Room.getRoom(r)));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("  ROOM DESCRIPTION  : %s", Room.getRoomDescription(r)));
        int floor = Room.getFloorNumber(r);
        String floorText;
        if (floor == 1) floorText = "1st Floor";
        else if (floor == 2) floorText = "2nd Floor";
        else if (floor == 3) floorText = "3rd Floor";
        else floorText = floor + "th Floor";
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("  FLOOR NUMBER      : %s", floorText));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("  BUILDING CODE     : %s", Room.getBuildingCode(r)));
        UI.footer();
    }

    public static void displayRooms() {
        UI.header("ROOM LIST");
        for (Room r : rooms) {
            int floor = Room.getFloorNumber(r);
            String floorText;
            if (floor == 1) floorText = "1st Floor";
            else if (floor == 2) floorText = "2nd Floor";
            else if (floor == 3) floorText = "3rd Floor";
            else floorText = floor + "th Floor";
            String room = String.format( "  %-10s %-32s %-15s %-4s", Room.getRoom(r), Room.getRoomDescription(r),
                    floorText, Room.getBuildingCode(r));
            System.out.printf("│%-" + innerWidth + "s│\n", room);
        }
        UI.footer();
    }

    public static void checkAvailability(String roomCode, String day, String startTime, String endTime) {
        int scheduledStart = convertToMinutes(startTime);
        int scheduledEnd = convertToMinutes(endTime);
        UI.header("ROOM AVAILABILITY");
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("  ROOM CODE     : %s", roomCode));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("  ROOM REQUEST  : %s", day + " " + startTime + "-" + endTime));
        for (Schedule s : schedules) {
            if (Schedule.getRoomCode(s).equals(roomCode) && Schedule.getDay(s).equalsIgnoreCase(day)) {

                int existingStart = convertToMinutes(Schedule.getStartTime(s));
                int existingEnd = convertToMinutes(Schedule.getEndTime(s));

                if (scheduledStart < existingEnd && scheduledEnd > existingStart) {
                    System.out.printf("│%-" + innerWidth + "s│\n", "  ROOM IS OCCUPIED");
                    UI.footer();
                    return;
                }
            }
        }
        System.out.printf("│%-" + innerWidth + "s│\n", "  ROOM IS AVAILABLE ");
        UI.footer();
    }

    public static boolean verifyAvailability(String roomCode, String day, String startTime, String endTime) {
        int scheduledStart = convertToMinutes(startTime);
        int scheduledEnd = convertToMinutes(endTime);
        for (Schedule s : schedules) {
            if (Schedule.getRoomCode(s).equals(roomCode) && Schedule.getDay(s).equalsIgnoreCase(day)) {

                int existingStart = convertToMinutes(Schedule.getStartTime(s));
                int existingEnd = convertToMinutes(Schedule.getEndTime(s));

                if (scheduledStart < existingEnd && scheduledEnd > existingStart) {
                    return false;
                }
            }
        }
        return true;
    }
}


