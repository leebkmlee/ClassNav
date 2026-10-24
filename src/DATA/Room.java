
package DATA;

import UI.UI;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.time.format.TextStyle;
import java.util.Locale;

import static DATA.Schedule.schedules;
import static UI.UI.in;

public class Room {

    private String roomCode, roomDescription, buildingCode, roomType;
    private int floorNumber, capacity;

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("MM/dd/uuuu")
                    .withResolverStyle(ResolverStyle.STRICT);

    public static ArrayList<Room> rooms = new ArrayList<>();

    static {
        rooms.add(new Room("RM001", "SDL 3", 3, "Computer Laboratory", 30, "BLD01"));
        rooms.add(new Room("RM002", "Prog Lab 2", 3, "Computer Laboratory", 30, "BLD01"));
        rooms.add(new Room("RM003", "CON 1", 2, "Computer Laboratory", 30, "BLD01"));
        rooms.add(new Room("RM004", "ACAD 6", 1, "Classroom", 50, "BLD09"));
        rooms.add(new Room("RM005", "ACAD 3", 1, "Classroom", 50, "BLD09"));
        rooms.add(new Room("RM006", "AAP10", 1, "Classroom", 50, "BLD10"));
        rooms.add(new Room("RM007", "AAP15", 2, "Classroom", 50, "BLD10"));
        rooms.add(new Room("RM008", "Science Lab 3", 1, "Science Laboratory", 20, "BLD02"));
        rooms.add(new Room("RM009", "Science Lab 2", 1, "Science Laboratory", 20, "BLD02"));
        rooms.add(new Room("RM010", "Science Lab 1", 1, "Science Laboratory", 20, "BLD02"));
        rooms.add(new Room("RM011", "Recreation Room 1", 2, "Exercise Room", 30, "BLD13"));
        rooms.add(new Room("RM012", "Recreation Room 2", 2, "Exercise Room", 30, "BLD13"));
        rooms.add(new Room("RM013", "Bio Lab 2", 3, "Biology Room", 20, "BLD08"));
        rooms.add(new Room("RM014", "Bio Lab 3", 3, "Biology Room", 20, "BLD08"));
        rooms.add(new Room("RM015", "ACAD 2", 1, "ClassRoom", 50, "BLD09"));
    }

    public Room(String roomCode, String roomDescription, int floorNumber,
                String roomType, int capacity, String buildingCode) {

        if (roomCode == null || roomCode.isBlank()
                || roomDescription == null || roomDescription.isBlank()
                || roomType == null || roomType.isBlank()
                || floorNumber < 1 || capacity < 1
                || buildingCode == null || buildingCode.isBlank()) {
            throw new IllegalArgumentException("Invalid room information.");
        }

        if (Building.findBuilding(buildingCode) == null) {
            throw new IllegalArgumentException("Building does not exist.");
        }

        if (existRoomCode(roomCode)) {
            throw new IllegalArgumentException("Room code already exists.");
        }

        if (existRoomDesc(roomDescription)) {
            throw new IllegalArgumentException("Room description already exists.");
        }

        this.roomCode = roomCode;
        this.roomDescription = roomDescription;
        this.floorNumber = floorNumber;
        this.roomType = roomType;
        this.capacity = capacity;
        this.buildingCode = buildingCode;
    }

    public Room(String roomCode, String roomDescription, int floorNumber, String buildingCode) {
        this(roomCode, roomDescription, floorNumber, "Classroom", 40, buildingCode);
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

    public String getRoomType() {
        return roomType;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getBuildingCode() {
        return buildingCode;
    }

    public void setRoomCode(String roomCode) {
        if (roomCode == null || roomCode.isBlank()) {
            throw new IllegalArgumentException("Room code cannot be empty.");
        }

        if (!this.roomCode.equalsIgnoreCase(roomCode) && existRoomCode(roomCode)) {
            throw new IllegalArgumentException("Room code already exists.");
        }

        this.roomCode = roomCode;
    }

    public void setRoomDescription(String roomDescription) {
        if (roomDescription == null || roomDescription.isBlank()) {
            throw new IllegalArgumentException("Room description cannot be empty.");
        }

        if (!this.roomDescription.equalsIgnoreCase(roomDescription)
                && existRoomDesc(roomDescription)) {
            throw new IllegalArgumentException("Room description already exists.");
        }

        this.roomDescription = roomDescription;
    }

    public void setFloorNumber(int floorNumber) {
        if (floorNumber < 1) {
            throw new IllegalArgumentException("Invalid floor number.");
        }

        this.floorNumber = floorNumber;
    }

    public void setRoomType(String roomType) {
        if (roomType == null || roomType.isBlank()) {
            throw new IllegalArgumentException("Room type cannot be empty.");
        }

        this.roomType = roomType;
    }

    public void setCapacity(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("Capacity must be greater than zero.");
        }

        this.capacity = capacity;
    }

    public void setBuildingCode(String buildingCode) {
        if (buildingCode == null || buildingCode.isBlank()
                || Building.findBuilding(buildingCode) == null) {
            throw new IllegalArgumentException("Building does not exist.");
        }

        this.buildingCode = buildingCode;
    }

    public static Room findRoom(String roomCode) {
        if (roomCode == null) {
            return null;
        }

        for (Room room : rooms) {
            if (room.getRoomCode().equalsIgnoreCase(roomCode.trim())) {
                return room;
            }
        }

        return null;
    }

    public static boolean existRoomCode(String roomCode) {
        return findRoom(roomCode) != null;
    }

    public static boolean existRoomDesc(String roomDescription) {
        if (roomDescription == null) {
            return false;
        }

        for (Room room : rooms) {
            if (room.getRoomDescription().equalsIgnoreCase(roomDescription.trim())) {
                return true;
            }
        }

        return false;
    }

    public static int convertToMinutes(String time) {
        if (time == null || time.isBlank()) {
            return -1;
        }

        time = time.toUpperCase().replace(" ", "");

        if (!time.matches("\\d{1,2}:\\d{2}(AM|PM)")) {
            return -1;
        }

        try {
            String period = time.substring(time.length() - 2);
            String[] parts = time.substring(0, time.length() - 2).split(":");

            int hour = Integer.parseInt(parts[0]);
            int minute = Integer.parseInt(parts[1]);

            if (hour < 1 || hour > 12 || minute < 0 || minute > 59) {
                return -1;
            }

            if (period.equals("AM")) {
                if (hour == 12) {
                    hour = 0;
                }
            } else if (hour != 12) {
                hour += 12;
            }

            return hour * 60 + minute;

        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static boolean overlaps(String start1, String end1,
                                    String start2, String end2) {

        int s1 = convertToMinutes(start1);
        int e1 = convertToMinutes(end1);
        int s2 = convertToMinutes(start2);
        int e2 = convertToMinutes(end2);

        if (s1 < 0 || e1 < 0 || s2 < 0 || e2 < 0) {
            return false;
        }

        return s1 < e2 && s2 < e1;
    }

    private static LocalDate parseDate(String date) {
        if (date == null || date.isBlank()) {
            return null;
        }

        try {
            return LocalDate.parse(date.trim(), DATE_FORMAT);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    private static boolean sameDate(String date1, String date2) {
        LocalDate first = parseDate(date1);
        LocalDate second = parseDate(date2);

        return first != null && first.equals(second);
    }

    private static boolean sameDay(String day1, String day2) {
        if (day1 == null || day2 == null) {
            return false;
        }

        String first = day1.trim().toLowerCase(Locale.ENGLISH);
        String second = day2.trim().toLowerCase(Locale.ENGLISH);

        return first.equals(second)
                || first.startsWith(second)
                || second.startsWith(first);
    }

    private static boolean isApproved(String status) {
        return status != null && status.equalsIgnoreCase("Approved");
    }

    private static boolean hasApprovedException(String scheduleID,
                                                String requestedDate,
                                                String exceptionType) {

        if (scheduleID == null || requestedDate == null || exceptionType == null) {
            return false;
        }

        for (CancelledSchedule exception : CancelledSchedule.cancellations) {
            if (exception.getScheduleID() != null
                    && exception.getAffectedDate() != null
                    && exception.getScheduleID().equalsIgnoreCase(scheduleID)
                    && sameDate(exception.getAffectedDate(), requestedDate)) {
                return true;
            }
        }

        return false;
    }

    private static boolean isScheduleActiveOnDate(Schedule schedule, LocalDate date) {
        if (schedule == null || date == null) {
            return false;
        }

        LocalDate effectiveFrom = parseDate(schedule.getEffectiveFrom());
        LocalDate effectiveTo = parseDate(schedule.getEffectiveTo());

        if (effectiveFrom == null || effectiveTo == null) {
            return false;
        }

        return !date.isBefore(effectiveFrom) && !date.isAfter(effectiveTo);
    }

    // Checks room availability for a specific date.
    public static boolean verifyAvailability(String roomCode, String requestedDate,
                                             String startTime, String endTime) {

        if (findRoom(roomCode) == null) {
            return false;
        }

        LocalDate date = parseDate(requestedDate);

        if (date == null) {
            return false;
        }

        int requestedStart = convertToMinutes(startTime);
        int requestedEnd = convertToMinutes(endTime);

        if (requestedStart < 0 || requestedEnd < 0 || requestedStart >= requestedEnd) {
            return false;
        }

        String fullDay = date.getDayOfWeek()
                .getDisplayName(TextStyle.FULL, Locale.ENGLISH);

        String shortDay = date.getDayOfWeek()
                .getDisplayName(TextStyle.SHORT, Locale.ENGLISH);

        String normalizedDate = date.format(DATE_FORMAT);

        // Check regular schedules using the room on the requested date.
        for (Schedule schedule : schedules) {

            if (schedule.getRoomCode() == null
                    || !schedule.getRoomCode().equalsIgnoreCase(roomCode.trim())) {
                continue;
            }

            if (!sameDay(schedule.getDay(), fullDay)
                    && !sameDay(schedule.getDay(), shortDay)) {
                continue;
            }

            if (!isScheduleActiveOnDate(schedule, date)) {
                continue;
            }

            boolean cancelled = hasApprovedException(
                    schedule.getScheduleID(), normalizedDate, "CANCELLATION"
            );

            boolean roomChanged = hasApprovedException(
                    schedule.getScheduleID(), normalizedDate, "ROOM CHANGE"
            );

            // An approved exception releases the original room for that date.
            if (cancelled || roomChanged) {
                continue;
            }

            if (overlaps(startTime, endTime,
                    schedule.getStartTime(), schedule.getEndTime())) {
                return false;
            }
        }

        // Check approved room-change cancellations occupying the replacement room.
        for (CancelledSchedule exception : CancelledSchedule.cancellations) {
            Schedule original = Schedule.findSchedule(exception.getScheduleID());

            if (!isScheduleActiveOnDate(original, date)
                    || !sameDay(original.getDay(), fullDay)) {
                continue;
            }

            if (overlaps(startTime, endTime,
                    original.getStartTime(), original.getEndTime())) {
                return false;
            }
        }

        // Check approved make-up classes and reservations.
        for (MakeUpSchedule makeup : MakeUpSchedule.requests) {

            if (!isApproved(makeup.getStatus())
                    || makeup.getRoomCode() == null
                    || !makeup.getRoomCode().equalsIgnoreCase(roomCode.trim())
                    || !sameDate(makeup.getRequestedDate(), normalizedDate)) {
                continue;
            }

            if (overlaps(startTime, endTime,
                    makeup.getStartTime(), makeup.getEndTime())) {
                return false;
            }
        }

        return true;
    }

    // Checks recurring availability by weekday.
    public static boolean verifyAvailabilityByDay(String roomCode, String day,
                                                  String startTime, String endTime) {

        if (findRoom(roomCode) == null) {
            return false;
        }

        int requestedStart = convertToMinutes(startTime);
        int requestedEnd = convertToMinutes(endTime);

        if (requestedStart < 0 || requestedEnd < 0 || requestedStart >= requestedEnd) {
            return false;
        }

        for (Schedule schedule : schedules) {

            if (schedule.getRoomCode() == null
                    || !schedule.getRoomCode().equalsIgnoreCase(roomCode.trim())
                    || !sameDay(schedule.getDay(), day)) {
                continue;
            }

            if (overlaps(startTime, endTime,
                    schedule.getStartTime(), schedule.getEndTime())) {
                return false;
            }
        }

        return true;
    }

    public static String getFloorText(int floorNumber) {
        switch (floorNumber) {
            case 1:
                return "1st Floor";
            case 2:
                return "2nd Floor";
            case 3:
                return "3rd Floor";
            default:
                return floorNumber + "th Floor";
        }
    }

    // Displays a single room.
    public static void displayRoom(String roomCode) {

        Room room = findRoom(roomCode);

        UI.header("ROOM DETAILS");

        if (room == null) {
            UI.liner("  Room not found.");
        } else {
            UI.liner(String.format("  %-20s %-35s", "ROOM CODE:",
                    fitText(room.getRoomCode(), 35)));
            UI.separator();
            UI.liner(String.format("  %-20s %-35s", "DESCRIPTION:",
                    fitText(room.getRoomDescription(), 35)));
            UI.separator();
            UI.liner(String.format("  %-20s %-35s", "ROOM TYPE:",
                    fitText(room.getRoomType(), 35)));
            UI.separator();
            UI.liner(String.format("  %-20s %-35s", "CAPACITY:",
                    room.getCapacity() + " seats"));
            UI.separator();
            UI.liner(String.format("  %-20s %-35s", "FLOOR:",
                    getFloorText(room.getFloorNumber())));
            UI.separator();
            UI.liner(String.format("  %-20s %-35s", "BUILDING CODE:",
                    room.getBuildingCode()));

            Building building = Building.findBuilding(room.getBuildingCode());

            if (building != null) {
                UI.separator();
                UI.liner(String.format("  %-20s %-35s", "BUILDING:",
                        fitText(building.getBuildingName(), 35)));
            }
        }

        UI.footer();
    }

    // Displays all rooms grouped by building.
    public static void displayRooms() {

        UI.header("ROOM LIST");

        if (rooms.isEmpty()) {
            UI.liner("  No rooms found.");
            UI.footer();
            return;
        }

        ArrayList<String> displayedBuildings = new ArrayList<>();

        for (Room room : rooms) {

            String currentBuildingCode = room.getBuildingCode();

            if (currentBuildingCode == null
                    || displayedBuildings.contains(currentBuildingCode.toUpperCase(Locale.ENGLISH))) {
                continue;
            }

            displayedBuildings.add(currentBuildingCode.toUpperCase(Locale.ENGLISH));

            Building building = Building.findBuilding(currentBuildingCode);
            String buildingName = building != null
                    ? building.getBuildingName()
                    : "Unknown Building";

            UI.liner("  " + fitText(currentBuildingCode + " - " + buildingName, 64));
            UI.separator();

            for (Room buildingRoom : rooms) {

                if (buildingRoom.getBuildingCode() != null
                        && buildingRoom.getBuildingCode().equalsIgnoreCase(currentBuildingCode)) {

                    UI.liner(String.format(
                            "  %-9s %-40s %-12s",
                            fitText(buildingRoom.getRoomCode(), 9),
                            fitText(buildingRoom.getRoomDescription(), 30),
                            fitText(buildingRoom.getRoomType(), 12)
                    ));

                    UI.liner(String.format(
                            "  %-9s %-40s %-12s",
                            "",
                            buildingRoom.getCapacity() + " seats",
                            getFloorText(buildingRoom.getFloorNumber())
                    ));

                    UI.separator();
                }
            }
        }

        UI.footer();
    }

    // Displays rooms belonging to a specific building.
    public static void displayRooms(String buildingCode) {

        UI.header("ROOM LIST");

        if (buildingCode == null || buildingCode.isBlank()) {
            UI.liner("  Invalid building code.");
            UI.footer();
            return;
        }

        buildingCode = buildingCode.trim();

        Building building = Building.findBuilding(buildingCode);

        if (building == null) {
            UI.liner("  Building not found.");
            UI.footer();
            return;
        }

        UI.liner("  " + fitText(
                building.getBuildingCode() + " - " + building.getBuildingName(), 64));
        UI.separator();

        boolean found = false;

        for (Room room : rooms) {

            if (room.getBuildingCode() != null
                    && room.getBuildingCode().equalsIgnoreCase(buildingCode)) {

                UI.liner("    " + fitText(room.getRoomCode(), 10) + "  "
                        + fitText(room.getRoomDescription(), 42));

                UI.liner("    " + fitText(room.getRoomType(), 18)
                        + "  " + room.getCapacity() + " seats"
                        + "  " + getFloorText(room.getFloorNumber()));

                UI.separator();
                found = true;
            }
        }

        if (!found) {
            UI.liner("  No rooms found for this building.");
        }

        UI.footer();
    }

    // Limits displayed text to a fixed width.
    private static String fitText(String text, int width) {
        if (text == null) {
            return "";
        }

        if (width <= 0) {
            return "";
        }

        if (text.length() > width) {
            if (width <= 3) {
                return text.substring(0, width);
            }

            return text.substring(0, width - 3) + "...";
        }

        return text;
    }

    public static void createRoom() {
        UI.header("CREATE ROOM");

        try {
            System.out.print("Enter room code: ");
            String roomCode = in.nextLine();

            System.out.print("Enter room description: ");
            String roomDescription = in.nextLine();

            System.out.print("Enter floor number: ");
            int floorNumber = Integer.parseInt(in.nextLine());

            System.out.print("Enter room type: ");
            String roomType = in.nextLine();

            System.out.print("Enter capacity: ");
            int capacity = Integer.parseInt(in.nextLine());

            System.out.print("Enter building code: ");
            String buildingCode = in.nextLine();

            Room room = new Room(roomCode, roomDescription, floorNumber,
                    roomType, capacity, buildingCode);

            rooms.add(room);
            UI.liner("  Room created successfully.");

        } catch (IllegalArgumentException e) {
            UI.liner("  Unable to create room: " + e.getMessage());
        }

        UI.footer();
    }

    public static void editRoom() {
        UI.header("EDIT ROOM");

        System.out.print("Enter room code: ");
        String roomCode = in.nextLine();

        Room room = findRoom(roomCode);

        if (room == null) {
            UI.liner("  Room not found.");
            UI.footer();
            return;
        }

        try {
            System.out.print("Enter new room description (or press Enter to keep current): ");
            String description = in.nextLine();

            if (!description.isBlank()) {
                room.setRoomDescription(description);
            }

            System.out.print("Enter new floor number (or press Enter to keep current): ");
            String floor = in.nextLine();

            if (!floor.isBlank()) {
                room.setFloorNumber(Integer.parseInt(floor));
            }

            System.out.print("Enter new room type (or press Enter to keep current): ");
            String type = in.nextLine();

            if (!type.isBlank()) {
                room.setRoomType(type);
            }

            System.out.print("Enter new capacity (or press Enter to keep current): ");
            String capacity = in.nextLine();

            if (!capacity.isBlank()) {
                room.setCapacity(Integer.parseInt(capacity));
            }

            System.out.print("Enter new building code (or press Enter to keep current): ");
            String building = in.nextLine();

            if (!building.isBlank()) {
                room.setBuildingCode(building);
            }

            UI.liner("  Room updated successfully.");

        } catch (IllegalArgumentException e) {
            UI.liner("  Unable to edit room: " + e.getMessage());
        }

        UI.footer();
    }

    public static void deleteRoom() {
        UI.header("DELETE ROOM");

        System.out.print("Enter room code: ");
        String roomCode = in.nextLine();

        Room room = findRoom(roomCode);

        if (room == null) {
            UI.liner("  Room not found.");
            UI.footer();
            return;
        }

        rooms.remove(room);
        UI.liner("  Room deleted successfully.");

        UI.footer();
    }

    private static void roomManage(String text, String roomCode,
                                   String roomDescription, String roomType,
                                   int floorNumber, int capacity, String buildingCode) {

        UI.header(text.toUpperCase() + ": ROOM INFORMATION");
        UI.liner(" [1] ROOM CODE        : " + roomCode);
        UI.liner(" [2] ROOM DESCRIPTION : " + roomDescription);
        UI.liner(" [3] ROOM TYPE        : " + roomType);
        UI.liner(" [4] FLOOR NUMBER     : " + floorNumber);
        UI.liner(" [5] CAPACITY         : " + capacity);
        UI.liner(" [6] BUILDING CODE    : " + buildingCode);
        UI.liner("");
        UI.option();
        UI.footer();
    }
}