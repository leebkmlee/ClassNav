
package DATA;

import UI.UI;
import java.util.ArrayList;
import static DATA.Schedule.schedules;

public class Room {

    private String roomCode; // PK
    private String roomDescription;
    private int floorNumber;
    private String roomType;
    private int capacity;
    private String buildingCode; // FK

    public Room(String roomCode, String roomDescription, int floorNumber,
                String roomType, int capacity, String buildingCode) {

        if (roomCode == null || roomCode.trim().isEmpty())
            throw new IllegalArgumentException("Room code cannot be empty.");

        if (roomDescription == null || roomDescription.trim().isEmpty())
            throw new IllegalArgumentException("Room description cannot be empty.");

        if (floorNumber < 1)
            throw new IllegalArgumentException("Floor number must be at least 1.");

        if (roomType == null || roomType.trim().isEmpty())
            throw new IllegalArgumentException("Room type cannot be empty.");

        if (capacity < 1)
            throw new IllegalArgumentException("Room capacity must be at least 1.");

        if (buildingCode == null || buildingCode.trim().isEmpty()
                || Building.findBuilding(buildingCode.trim()) == null)
            throw new IllegalArgumentException("Invalid building code.");

        roomCode = roomCode.trim();

        for (Room r : rooms) {
            if (roomCode.equalsIgnoreCase(r.getRoomCode()))
                throw new IllegalArgumentException("Room code already exists.");
        }

        this.roomCode = roomCode;
        this.roomDescription = roomDescription.trim();
        this.floorNumber = floorNumber;
        this.roomType = roomType.trim();
        this.capacity = capacity;
        this.buildingCode = buildingCode.trim();
    }

    // COMPATIBILITY CONSTRUCTOR
    public Room(String roomCode, String roomDescription,
                int floorNumber, String buildingCode) {
        this(roomCode, roomDescription, floorNumber,
                "Classroom", 40, buildingCode);
    }

    public static ArrayList<Room> rooms = new ArrayList<>();

    static {
        rooms.add(new Room("PL101", "Programming Laboratory 1", 1, "Computer Laboratory", 40, "BLD01"));
        rooms.add(new Room("PL102", "Programming Laboratory 2", 1, "Computer Laboratory", 40, "BLD01"));
        rooms.add(new Room("PL201", "Computer Laboratory 3", 2, "Computer Laboratory", 40, "BLD01"));
        rooms.add(new Room("SDL 1", "Smart Development Lab 1", 2, "Development Laboratory", 35, "BLD02"));
        rooms.add(new Room("SDL 2", "Smart Development Lab 2", 2, "Development Laboratory", 35, "BLD02"));
        rooms.add(new Room("ACAD 1", "Academic Room 1", 1, "Classroom", 45, "BLD03"));
        rooms.add(new Room("ACAD 2", "Academic Room 2", 1, "Classroom", 45, "BLD03"));
        rooms.add(new Room("ACAD 3", "Academic Room 3", 1, "Classroom", 45, "BLD03"));
        rooms.add(new Room("NH201", "Lecture Room 201", 2, "Lecture Room", 50, "BLD04"));
        rooms.add(new Room("NH202", "Lecture Room 202", 2, "Lecture Room", 50, "BLD04"));
        rooms.add(new Room("ICT301", "ICT Lecture Room 301", 3, "Lecture Room", 50, "BLD05"));
        rooms.add(new Room("ICT302", "ICT Lecture Room 302", 3, "Lecture Room", 50, "BLD05"));
    }

    // ROOM SETTER METHODS
    public void setRoomCode(String roomCode) {

        if (roomCode == null || roomCode.trim().isEmpty())
            throw new IllegalArgumentException("Room code cannot be empty.");

        roomCode = roomCode.trim();

        for (Room r : rooms) {
            if (r != this && roomCode.equalsIgnoreCase(r.getRoomCode()))
                throw new IllegalArgumentException("Room code already exists.");
        }

        this.roomCode = roomCode;
    }

    public void setRoomDescription(String roomDescription) {
        if (roomDescription == null || roomDescription.trim().isEmpty())
            throw new IllegalArgumentException("Room description cannot be empty.");

        this.roomDescription = roomDescription.trim();
    }

    public void setFloorNumber(int floorNumber) {
        if (floorNumber < 1)
            throw new IllegalArgumentException("Floor number must be at least 1.");

        this.floorNumber = floorNumber;
    }

    public void setRoomType(String roomType) {
        if (roomType == null || roomType.trim().isEmpty())
            throw new IllegalArgumentException("Room type cannot be empty.");

        this.roomType = roomType.trim();
    }

    public void setCapacity(int capacity) {
        if (capacity < 1)
            throw new IllegalArgumentException("Room capacity must be at least 1.");

        this.capacity = capacity;
    }

    public void setBuildingCode(String buildingCode) {

        if (buildingCode == null || buildingCode.trim().isEmpty()
                || Building.findBuilding(buildingCode.trim()) == null)
            throw new IllegalArgumentException("Invalid building code.");

        this.buildingCode = buildingCode.trim();
    }

    // ROOM GETTER METHODS
    public String getRoomCode() {
        return roomCode;
    }

    public String getRoomDescription() {
        return roomDescription;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getBuildingCode() {
        return buildingCode;
    }

    // FIND ROOM BY PRIMARY KEY
    public static Room findRoom(String roomCode) {

        if (roomCode == null || roomCode.trim().isEmpty())
            return null;

        roomCode = roomCode.trim();

        for (Room r : rooms) {
            if (roomCode.equalsIgnoreCase(r.getRoomCode()))
                return r;
        }

        return null;
    }

    // STATIC GETTER METHODS
    public static String getRoom(Room r) {
        return r == null ? null : r.roomCode;
    }

    public static String getRoomDescription(Room r) {
        return r == null ? null : r.roomDescription;
    }

    public static int getFloorNumber(Room r) {
        return r == null ? 0 : r.floorNumber;
    }

    public static String getRoomType(Room r) {
        return r == null ? null : r.roomType;
    }

    public static int getCapacity(Room r) {
        return r == null ? 0 : r.capacity;
    }

    public static String getBuildingCode(Room r) {
        return r == null ? null : r.buildingCode;
    }

    public static boolean existRoomCode(String roomCode) {
        return findRoom(roomCode) != null;
    }

    public static boolean existRoomDesc(String roomDescription) {

        if (roomDescription == null || roomDescription.trim().isEmpty())
            return false;

        roomDescription = roomDescription.trim();

        for (Room r : rooms) {
            if (roomDescription.equalsIgnoreCase(r.roomDescription))
                return true;
        }

        return false;
    }

    // CONVERTS A 12-HOUR TIME TO MINUTES AFTER MIDNIGHT
    public static int convertToMinutes(String time) {

        if (time == null || time.trim().isEmpty())
            throw new IllegalArgumentException("Time cannot be empty.");

        time = time.trim().toUpperCase().replace(" ", "");

        if (!time.matches("\\d{1,2}:\\d{2}(AM|PM)"))
            throw new IllegalArgumentException(
                    "Invalid time format. Use a format such as 8:30 AM.");

        String period = time.substring(time.length() - 2);
        String[] parts = time.substring(0, time.length() - 2).split(":");

        int hour;
        int minute;

        try {
            hour = Integer.parseInt(parts[0]);
            minute = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid time.");
        }

        if (hour < 1 || hour > 12 || minute < 0 || minute > 59)
            throw new IllegalArgumentException("Invalid time.");

        if (period.equals("AM")) {
            if (hour == 12)
                hour = 0;
        } else {
            if (hour != 12)
                hour += 12;
        }

        return hour * 60 + minute;
    }

    private static String getFloorText(int floor) {

        if (floor == 1) return "1st Floor";
        if (floor == 2) return "2nd Floor";
        if (floor == 3) return "3rd Floor";

        return floor + "th Floor";
    }

    // DISPLAY ROOM INFORMATION
    public static void displayRoom(String roomCode) {

        Room r = findRoom(roomCode);

        if (r == null) {
            UI.print("Room not found.");
            return;
        }

        Building b = Building.findBuilding(r.getBuildingCode());

        String buildingName = (b == null)
                ? "Building not found"
                : Building.getBuildingName(b);

        UI.header("ROOM INFORMATION");
        UI.liner(String.format("  ROOM CODE         : %s", r.getRoomCode()));
        UI.liner(String.format("  ROOM DESCRIPTION  : %s", r.getRoomDescription()));
        UI.liner(String.format("  ROOM TYPE         : %s", r.getRoomType()));
        UI.liner(String.format("  CAPACITY          : %d", r.getCapacity()));
        UI.liner(String.format("  FLOOR NUMBER      : %s", getFloorText(r.getFloorNumber())));
        UI.liner(String.format("  BUILDING CODE     : %s", r.getBuildingCode()));
        UI.liner(String.format("  BUILDING NAME     : %s", buildingName));
        UI.footer();
    }

    // DISPLAY ALL ROOMS
    public static void displayRooms() {

        UI.header("ROOM LIST");
        UI.liner(String.format("  %-10s %-32s %-24s %-8s %-12s %-12s",
                "ROOM CODE", "DESCRIPTION", "ROOM TYPE",
                "CAPACITY", "FLOOR", "BUILDING"));

        for (Room r : rooms) {
            UI.liner(String.format("  %-10s %-32s %-24s %-8d %-12s %-12s",
                    r.getRoomCode(), r.getRoomDescription(), r.getRoomType(),
                    r.getCapacity(), getFloorText(r.getFloorNumber()),
                    r.getBuildingCode()));
        }

        UI.footer();
    }

    // DISPLAY ROOMS BY BUILDING
    public static void displayRooms(String buildingCode) {

        Building b = Building.findBuilding(buildingCode);

        if (b == null) {
            UI.print("Building not found.");
            return;
        }

        UI.header("ROOM LIST");
        UI.liner(String.format("  BUILDING CODE : %s", Building.getBuilding(b)));
        UI.liner(String.format("  BUILDING NAME : %s", Building.getBuildingName(b)));

        boolean found = false;

        for (Room r : rooms) {
            if (r.getBuildingCode().equalsIgnoreCase(buildingCode.trim())) {
                found = true;

                UI.liner(String.format("  %-10s %-32s %-24s %-8d %-12s",
                        r.getRoomCode(), r.getRoomDescription(), r.getRoomType(),
                        r.getCapacity(), getFloorText(r.getFloorNumber())));
            }
        }

        if (!found)
            UI.liner("  No rooms registered under this building.");

        UI.footer();
    }

    // CHECKS FOR OVERLAPPING RECURRING SCHEDULES
    public static boolean verifyAvailability(String roomCode, String day,
                                             String startTime, String endTime) {

        if (findRoom(roomCode) == null
                || day == null || day.trim().isEmpty())
            return false;

        int requestedStart;
        int requestedEnd;

        try {
            requestedStart = convertToMinutes(startTime);
            requestedEnd = convertToMinutes(endTime);
        } catch (IllegalArgumentException e) {
            return false;
        }

        if (requestedStart >= requestedEnd)
            return false;

        roomCode = roomCode.trim();
        day = day.trim();

        for (Schedule s : schedules) {

            if (s == null)
                continue;

            String scheduledRoom = Schedule.getRoomCode(s);
            String scheduledDay = Schedule.getDay(s);
            String scheduledStart = Schedule.getStartTime(s);
            String scheduledEnd = Schedule.getEndTime(s);

            if (scheduledRoom == null || scheduledDay == null
                    || scheduledStart == null || scheduledEnd == null)
                continue;

            if (scheduledRoom.trim().equalsIgnoreCase(roomCode)
                    && scheduledDay.trim().equalsIgnoreCase(day)) {

                int existingStart;
                int existingEnd;

                try {
                    existingStart = convertToMinutes(scheduledStart);
                    existingEnd = convertToMinutes(scheduledEnd);
                } catch (IllegalArgumentException e) {
                    continue;
                }

                if (requestedStart < existingEnd
                        && requestedEnd > existingStart)
                    return false;
            }
        }

        return true;
    }
}