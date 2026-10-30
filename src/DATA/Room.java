package DATA;

import UI.UI;
import FUNCTION.Request;

import java.time.*;
import java.time.format.*;
import java.util.*;

import static DATA.Schedule.schedules;
import static UI.UI.*;

public class Room {

    // FIELDS
    private String roomCode, roomDescription, buildingCode, roomType, collegeCode;
    private int floorNumber, capacity;

    public static ArrayList<Room> rooms = new ArrayList<>();
    public static ArrayList<Room> archiveRooms = new ArrayList<>();

    // CONSTRUCTOR
    public Room(String roomCode, String roomDescription, int floorNumber,
                String roomType, int capacity, String buildingCode, String collegeCode) {
        this.roomCode = roomCode;
        this.roomDescription = roomDescription;
        this.floorNumber = floorNumber;
        this.roomType = roomType;
        this.capacity = capacity;
        this.buildingCode = buildingCode;
        this.collegeCode = collegeCode;
    }

    // DUMMY DATA
    static {
        rooms.add(new Room("RM001", "SDL 3", 3, "Computer Laboratory", 30, "BLD01", "CICT"));
        rooms.add(new Room("RM002", "Prog Lab 2", 3, "Computer Laboratory", 30, "BLD01", "CICT"));
        rooms.add(new Room("RM003", "CON 1", 2, "Computer Laboratory", 30, "BLD01", "CICT"));
        rooms.add(new Room("RM004", "ACAD 6", 1, "Classroom", 50, "BLD09", "CICT"));
        rooms.add(new Room("RM005", "ACAD 3", 1, "Classroom", 50, "BLD09", "CICT"));
        rooms.add(new Room("RM006", "AAP10", 1, "Classroom", 50, "BLD10", "CAFA"));
        rooms.add(new Room("RM007", "AAP15", 2, "Classroom", 50, "BLD10", "CAFA"));
        rooms.add(new Room("RM008", "Science Lab 3", 1, "Science Laboratory", 20, "BLD02", "CS"));
        rooms.add(new Room("RM009", "Science Lab 2", 1, "Science Laboratory", 20, "BLD02", "CS"));
        rooms.add(new Room("RM010", "Science Lab 1", 1, "Science Laboratory", 20, "BLD02", "CS"));
        rooms.add(new Room("RM011", "Recreation Room 1", 2, "Exercise Room", 30, "BLD13", "CPTED"));
        rooms.add(new Room("RM012", "Recreation Room 2", 2, "Exercise Room", 30, "BLD13", "CPTED"));
        rooms.add(new Room("RM013", "Bio Lab 2", 3, "Biology Room", 20, "BLD08", "CS"));
        rooms.add(new Room("RM014", "Bio Lab 3", 3, "Biology Room", 20, "BLD08", "CS"));
        rooms.add(new Room("RM015", "ACAD 2", 1, "Classroom", 50, "BLD09", "CICT"));
    }

    // GETTER METHODS
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
    public String getCollegeCode() {
        return collegeCode;
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

    // FIND METHODS
    public static Room findRoom(String roomCode) {
        if (roomCode == null) {
            return null;
        }

        for (Room room : rooms) {
            if (room != null && room.getRoomCode() != null
                    && room.getRoomCode().equalsIgnoreCase(roomCode.trim())) {
                return room;
            }
        }

        return null;
    }
    public static Room findRoomDesc(String roomDescription) {
        if (roomDescription == null || roomDescription.trim().isEmpty()) {
            return null;
        }

        for (Room room : rooms) {
            if (room != null
                    && room.getRoomDescription() != null
                    && room.getRoomDescription().trim()
                    .equalsIgnoreCase(roomDescription.trim())) {
                return room;
            }
        }

        return null;
    }

    // HELPER METHODS
    public static int convertToMinutes(String time) {
        if (time == null || time.trim().isEmpty()) return -1;

        time = time.trim().toUpperCase(Locale.ROOT);

        if (time.matches("\\d{1,2}:\\d{2}")) {
            try {
                LocalTime parsedTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("H:mm"));
                return parsedTime.getHour() * 60 + parsedTime.getMinute();
            } catch (DateTimeParseException e) {
                return -1;
            }
        }

        time = time.replace(" ", "");

        if (!time.matches("\\d{1,2}:\\d{2}(AM|PM)")) return -1;

        try {
            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern("[h:mma][hh:mma]", Locale.ENGLISH);

            LocalTime parsedTime = LocalTime.parse(time, formatter);

            return parsedTime.getHour() * 60 + parsedTime.getMinute();
        } catch (DateTimeParseException e) {
            return -1;
        }
    }
    private static LocalDate parseDate(String date) {
        if (date == null || date.isBlank()) return null;
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
        if (day1 == null || day2 == null) return false;
        String first = day1.trim().toLowerCase(Locale.ENGLISH);
        String second = day2.trim().toLowerCase(Locale.ENGLISH);
        return first.equals(second) || first.startsWith(second) || second.startsWith(first);
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
    public static boolean verifyAvailability(String roomCode, String requestedDate, String startTime, String endTime) {
        Room room = findRoom(roomCode);
        LocalDate date = parseDate(requestedDate);

        if (room == null || date == null) {
            return false;
        }

        int requestedStart = convertToMinutes(startTime);
        int requestedEnd = convertToMinutes(endTime);

        if (requestedStart < 0 || requestedEnd < 0
                || requestedStart >= requestedEnd) {
            return false;
        }

        String fullDay = date.getDayOfWeek()
                .getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        String shortDay = date.getDayOfWeek()
                .getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
        String normalizedDate = date.format(DATE_FORMAT);

        for (Schedule schedule : schedules) {
            if (schedule == null
                    || schedule.getRoomCode() == null
                    || !schedule.getRoomCode().equalsIgnoreCase(room.getRoomCode())
                    || (!sameDay(schedule.getDay(), fullDay)
                    && !sameDay(schedule.getDay(), shortDay))
                    || !isScheduleActiveOnDate(schedule, date)) {
                continue;
            }

            boolean cancelled = false;

            for (CancelledSchedule exception : CancelledSchedule.cancellations) {
                if (exception != null
                        && exception.getScheduleID() != null
                        && exception.getScheduleID()
                        .equalsIgnoreCase(schedule.getScheduleID())
                        && sameDate(exception.getAffectedDate(), normalizedDate)) {
                    cancelled = true;
                    break;
                }
            }

            if (cancelled) {
                continue;
            }

            int existingStart = convertToMinutes(schedule.getStartTime());
            int existingEnd = convertToMinutes(schedule.getEndTime());

            if (existingStart < 0 || existingEnd < 0
                    || existingStart >= existingEnd) {
                return false;
            }

            if (requestedStart < existingEnd && requestedEnd > existingStart) {
                return false;
            }
        }

        for (MakeUpSchedule makeup : MakeUpSchedule.makeUpSchedules) {
            if (makeup == null
                    || !"Approved".equalsIgnoreCase(makeup.getStatus())
                    || makeup.getRoomCode() == null
                    || !makeup.getRoomCode().equalsIgnoreCase(room.getRoomCode())
                    || !sameDate(makeup.getRequestedDate(), normalizedDate)) {
                continue;
            }

            int makeupStart = convertToMinutes(makeup.getStartTime());
            int makeupEnd = convertToMinutes(makeup.getEndTime());

            if (makeupStart < 0 || makeupEnd < 0 || makeupStart >= makeupEnd) {
                return false;
            }

            if (requestedStart < makeupEnd && requestedEnd > makeupStart) {
                return false;
            }
        }

        return true;
    }

    // DISPLAY METHODS
    public static void displayRoom(String roomCode,boolean print) {
        Room room = findRoom(roomCode);

        UI.header("ROOM DETAILS", false);

        if (room == null) {
            UI.liner("  Room not found.", false);
        } else {
            Building building = Building.findBuilding(room.getBuildingCode());
            College college = College.findCollege(room.getCollegeCode());

            UI.liner(String.format("  ROOM CODE      : %s", room.getRoomCode()), false);
            UI.liner(String.format("  DESCRIPTION    : %s", fitText(room.getRoomDescription(), 50)), false);
            UI.liner(String.format("  ROOM TYPE      : %s", fitText(room.getRoomType(), 50)), false);
            UI.liner(String.format("  CAPACITY       : %s", room.getCapacity() + " seats"), false);
            UI.liner(String.format("  FLOOR          : %s", getFloorText(room.getFloorNumber())), false);
            UI.liner(String.format("  BUILDING CODE  : %s", room.getBuildingCode()), false);
            UI.liner(String.format("  COLLEGE CODE   : %s", room.getCollegeCode()), false);
            UI.liner(String.format("  BUILDING       : %s",
                    fitText(building == null ? "Building unavailable" : building.getBuildingName(), 50)), false);
            UI.liner(String.format("  COLLEGE        : %s",
                    fitText(college == null ? "College unavailable" : college.getCollegeDescription(), 50)), false);
        }
        UI.footer(false);
        if(print) UI.returnButton();
    }
    public static void displayRooms() {
        UI.header("ROOMS", true);
        if (rooms.isEmpty()) UI.liner("  No rooms found.", true);
        else {
            String format = "  %-12s %-61s %-22s %-12s %-14s %-30s %-21s";
            UI.liner(String.format(format, "ROOM CODE", "DESCRIPTION", "ROOM TYPE", "CAPACITY",
                    "FLOOR", "BUILDING", "COLLEGE"), true);
            UI.separator(true);
            int count = 0;
            for (Room room : rooms) {
                if (room == null) continue;
                Building building = Building.findBuilding(room.getBuildingCode());
                UI.liner(String.format(format, room.getRoomCode(), UI.fitText(room.getRoomDescription(), 61),
                        UI.fitText(room.getRoomType(), 22), room.getCapacity() + " seats",
                        UI.fitText(getFloorText(room.getFloorNumber()), 14),
                        UI.fitText(building == null ? "Unavailable" : building.getBuildingName(), 30),
                        room.getCollegeCode()), true);
                count++;
            }
            UI.separator(true);
            UI.liner("  TOTAL ROOMS: " + count, true);
        }
        UI.footer(true);
        UI.returnButton();
    }
    public static void displayRooms(String buildingCode) {
        if (buildingCode == null || buildingCode.trim().isEmpty()) {
            UI.print("Building code is required.");
            return;
        }
        Building building = Building.findBuilding(buildingCode.trim());
        if (building == null) {
            UI.print("Building not found.");
            return;
        }
        UI.header("ROOMS BY BUILDING", true);
        UI.liner("  BUILDING: " + building.getBuildingCode() + " - " + building.getBuildingName(), true);
        String format = "  %-12s %-75s %-22s %-14s %-14s %-36s";
        UI.liner(String.format(format, "ROOM CODE", "DESCRIPTION", "ROOM TYPE", "CAPACITY", "FLOOR", "COLLEGE"), true);
        UI.separator(true);
        int count = 0;
        for (Room room : rooms) {
            if (room == null || room.getBuildingCode() == null
                    || !room.getBuildingCode().equalsIgnoreCase(building.getBuildingCode())) continue;
            UI.liner(String.format(format, room.getRoomCode(), UI.fitText(room.getRoomDescription(), 75),
                    UI.fitText(room.getRoomType(), 22), room.getCapacity() + " seats",
                    UI.fitText(getFloorText(room.getFloorNumber()), 14), room.getCollegeCode()), true);
            count++;
        }
        if (count == 0) UI.liner("  No rooms found for this building.", true);
        UI.separator(true);
        UI.liner("  TOTAL ROOMS: " + count, true);
        UI.footer(true);
        UI.returnButton();
    }
    public static void displayArchivedRooms() {
        UI.header("ARCHIVED ROOMS", true);
        if (archiveRooms.isEmpty()) UI.liner("  No archived rooms found.", true);
        else {
            String format = "  %-12s %-61s %-22s %-12s %-14s %-30s %-21s";
            UI.liner(String.format(format, "ROOM CODE", "DESCRIPTION", "ROOM TYPE", "CAPACITY",
                    "FLOOR", "BUILDING", "COLLEGE"), true);
            UI.separator(true);
            int count = 0;
            for (Room room : archiveRooms) {
                if (room == null) continue;
                Building building = Building.findBuilding(room.getBuildingCode());
                UI.liner(String.format(format, room.getRoomCode(), UI.fitText(room.getRoomDescription(), 61),
                        UI.fitText(room.getRoomType(), 22), room.getCapacity() + " seats",
                        UI.fitText(getFloorText(room.getFloorNumber()), 14),
                        UI.fitText(building == null ? "Archived building" : building.getBuildingName(), 30),
                        room.getCollegeCode()), true);
                count++;
            }
            UI.separator(true);
            UI.liner("  TOTAL ARCHIVED ROOMS: " + count, true);
        }
        UI.footer(true);
        UI.returnButton();
    }

    // MANAGE METHODS
    public static void createRoom() {
        String roomCode = "", roomDescription = "", roomType = "", buildingCode = "", collegeCode = "", choice;
        int floorNumber = 0, capacity = 0; boolean submit = false;

        while (!submit) {
            roomManage("CREATE", roomCode, roomDescription, roomType, floorNumber, capacity,
                    buildingCode, collegeCode);

            do {
                UI.select("Select", false);
                choice = in.nextLine().trim().toUpperCase();

                if (!choice.matches("[1-7X]") && !choice.equals("/")) UI.print("Invalid Choice");
            } while (!choice.matches("[1-7X]") && !choice.equals("/"));

            switch (choice) {
                case "1":
                    do {
                        UI.select("Enter Room Code", true);
                        roomCode = in.nextLine().trim();

                        if (roomCode.equalsIgnoreCase("X")) {
                            roomCode = "";
                            break;
                        }
                        else if (roomCode.isEmpty()) UI.print("Room code is required");
                        else if (findRoom(roomCode) != null) UI.print("Room code already exists");
                    } while (roomCode.isEmpty() || findRoom(roomCode) != null);
                    break;

                case "2":
                    do {
                        UI.select("Enter Room Description", true);
                        roomDescription = in.nextLine().trim();

                        if (roomDescription.equalsIgnoreCase("X")) {
                            roomDescription = "";
                            break;
                        }
                        else if (roomDescription.isEmpty()) UI.print("Room description is required");
                        else if (findRoomDesc(roomDescription) != null) UI.print("Room description already exists");
                    } while (roomDescription.isEmpty() || findRoomDesc(roomDescription) != null);
                    break;

                case "3":
                    do {
                        UI.select("Enter Room Type", true);
                        roomType = in.nextLine().trim();

                        if (roomType.equalsIgnoreCase("X")) {
                            roomType = "";
                            break;
                        }
                        else if (roomType.isEmpty()) UI.print("Room type is required");
                    } while (roomType.isEmpty());
                    break;

                case "4":
                    boolean validFloor = false;

                    while (!validFloor) {
                        UI.select("Enter Floor Number", true);
                        String input = in.nextLine().trim();

                        if (input.equalsIgnoreCase("X")) {
                            floorNumber = 0;
                            break;
                        }
                        else {
                            try {
                                floorNumber = Integer.parseInt(input);

                                if (floorNumber < 1) {
                                    UI.print("Floor number must be at least 1");
                                } else {
                                    validFloor = true;
                                }
                            } catch (NumberFormatException e) {
                                UI.print("Invalid floor number. Numbers only");
                            }
                        }
                    }
                    break;

                case "5":
                    boolean validCapacity = false;

                    while (!validCapacity) {
                        UI.select("Enter Capacity", true);
                        String input = in.nextLine().trim();

                        if (input.equalsIgnoreCase("X")) {
                            capacity = 0;
                            break;
                        }
                        else {
                            try {
                                capacity = Integer.parseInt(input);

                                if (capacity < 1) {
                                    UI.print("Capacity must be at least 1");
                                } else {
                                    validCapacity = true;
                                }
                            } catch (NumberFormatException e) {
                                UI.print("Invalid capacity. Numbers only");
                            }
                        }
                    }
                    break;

                case "6":
                    do {
                        UI.select("Enter Building Code", true);
                        buildingCode = in.nextLine().trim().toUpperCase();

                        if (buildingCode.equalsIgnoreCase("X")) {
                            buildingCode = "";
                            break;
                        }
                        else if (buildingCode.isEmpty()) UI.print("Building code is required");
                        else if (Building.findBuilding(buildingCode) == null)
                            UI.print("Building code does not exist");
                    } while (buildingCode.isEmpty() || Building.findBuilding(buildingCode) == null);
                    break;

                case "7":
                    do {
                        UI.select("Enter College Code", true);
                        collegeCode = in.nextLine().trim().toUpperCase();

                        if (collegeCode.equalsIgnoreCase("X")) {
                            collegeCode = "";
                            break;
                        }
                        else if (collegeCode.isEmpty()) UI.print("College code is required");
                        else if (College.findCollege(collegeCode) == null) UI.print("College code does not exist");
                    } while (collegeCode.isEmpty() || College.findCollege(collegeCode) == null);
                    break;

                case "/":
                    if (roomCode.isEmpty() || roomDescription.isEmpty() || roomType.isEmpty() || floorNumber < 1 ||
                            capacity < 1 || buildingCode.isEmpty() || collegeCode.isEmpty()) UI.print("Details are incomplete");
                    else {
                        rooms.add(new Room(roomCode, roomDescription, floorNumber, roomType, capacity,
                                buildingCode, collegeCode));
                        UI.print("Room successfully created");
                        Room.displayRoom(roomCode, true);
                        submit = true;
                    }
                    break;

                case "X":
                    submit = true;
                    break;
            }
        }
    }
    public static void editRoom() {
        String input, choice;
        boolean submit = false;

        UI.print("EDIT EXISTING ROOM");

        do {
            UI.select("Enter Room Code", false);
            input = in.nextLine().trim();

            if (input.equalsIgnoreCase("X")) return;
            else if (input.isEmpty()) UI.print("Room Code is required");
            else if (findRoom(input) == null) UI.print("Room does not exist");
        } while (input.isEmpty() || findRoom(input) == null);

        Room r = findRoom(input);

        String roomCode = r.getRoomCode();
        String roomDescription = r.getRoomDescription();
        String roomType = r.getRoomType();
        int floorNumber = r.getFloorNumber();
        int capacity = r.getCapacity();
        String buildingCode = r.getBuildingCode();
        String collegeCode = r.getCollegeCode();

        while (!submit) {
            roomManage("EDIT", roomCode, roomDescription, roomType, floorNumber, capacity, buildingCode, collegeCode);

            do {
                UI.select("Select", false);
                choice = in.nextLine().trim().toUpperCase();

                if (!choice.matches("[1-7X]") && !choice.equals("/")) UI.print("Invalid Choice");
            } while (!choice.matches("[1-7X]") && !choice.equals("/"));

            switch (choice) {
                case "1":
                    do {
                        UI.select("Enter Room Code", true);
                        roomCode = in.nextLine().trim();

                        if (roomCode.equalsIgnoreCase("X")) {
                            roomCode = r.getCollegeCode();
                            break;
                        }
                        else if (roomCode.isEmpty()) UI.print("Room code is required");
                        else if (findRoom(roomCode) != null) UI.print("Room code already exists");
                    } while (roomCode.isEmpty() || findRoom(roomCode) != null);
                    break;

                case "2":
                    do {
                        UI.select("Enter Room Description", true);
                        roomDescription = in.nextLine().trim();

                        if (roomDescription.equalsIgnoreCase("X")) {
                            roomDescription = r.getRoomDescription();
                            break;
                        }
                        else if (roomDescription.isEmpty()) UI.print("Room description is required");
                        else if (findRoomDesc(roomDescription) != null) UI.print("Room description already exists");
                    } while (roomDescription.isEmpty() || findRoomDesc(roomDescription) != null);
                    break;

                case "3":
                    do {
                        UI.select("Enter Room Type", true);
                        roomType = in.nextLine().trim();

                        if (roomType.equalsIgnoreCase("X")) {
                            roomType = r.getRoomType();
                            break;
                        }
                        else if (roomType.isEmpty()) UI.print("Room type is required");
                    } while (roomType.isEmpty());
                    break;

                case "4":
                    boolean validFloor = false;

                    while (!validFloor) {
                        UI.select("Enter Floor Number", true);
                        input = in.nextLine().trim();

                        if (input.equalsIgnoreCase("X")) {
                            floorNumber = r.getFloorNumber();
                            break;
                        }
                        else {
                            try {
                                floorNumber = Integer.parseInt(input);

                                if (floorNumber < 1) {
                                    UI.print("Floor number must be at least 1");
                                } else {
                                    validFloor = true;
                                }
                            } catch (NumberFormatException e) {
                                UI.print("Invalid floor number. Numbers only");
                            }
                        }
                    }
                    break;

                case "5":
                    boolean validCapacity = false;

                    while (!validCapacity) {
                        UI.select("Enter Capacity", true);
                        input = in.nextLine().trim();

                        if (input.equalsIgnoreCase("X")) {
                            capacity = r.getCapacity();
                            break;
                        }
                        else {
                            try {
                                capacity = Integer.parseInt(input);

                                if (capacity < 1) {
                                    UI.print("Capacity must be at least 1");
                                } else {
                                    validCapacity = true;
                                }
                            } catch (NumberFormatException e) {
                                UI.print("Invalid capacity. Numbers only");
                            }
                        }
                    }
                    break;

                case "6":
                    do {
                        UI.select("Enter Building Code", true);
                        buildingCode = in.nextLine().trim().toUpperCase();

                        if (buildingCode.equalsIgnoreCase("X")) {
                            buildingCode = r.getBuildingCode();
                            break;
                        }
                        else if (buildingCode.isEmpty()) UI.print("Building code is required");
                        else if (Building.findBuilding(buildingCode) == null)
                            UI.print("Building code does not exist");
                    } while (buildingCode.isEmpty() || Building.findBuilding(buildingCode) == null);
                    break;

                case "7":
                    do {
                        UI.select("Enter College Code", true);
                        collegeCode = in.nextLine().trim().toUpperCase();

                        if (collegeCode.equalsIgnoreCase("X")) {
                            collegeCode = r.getCollegeCode();
                            break;
                        }
                        else if (collegeCode.isEmpty()) UI.print("College code is required");
                        else if (College.findCollege(collegeCode) == null) UI.print("College code does not exist");
                    } while (collegeCode.isEmpty() || College.findCollege(collegeCode) == null);
                    break;

                case "/":
                    Room room = findRoom(roomCode);
                    room.roomDescription = roomDescription; room.floorNumber = floorNumber; room.roomType = roomType;
                    room.capacity = capacity; room.buildingCode = buildingCode; room.collegeCode = collegeCode;
                    UI.print("Room successfully created");
                    break;

                case "X":
                    submit = true;
                    break;
            }
        }
    }
    public static boolean hasUpcomingUse(String roomCode) {
        if (roomCode == null || roomCode.trim().isEmpty()) return false;
        LocalDate today = LocalDate.now();

        for (Schedule schedule : Schedule.getSchedulesByRoom(roomCode)) {
            if (schedule == null) continue;
            LocalDate endDate = parseDate(schedule.getEffectiveTo());
            if (endDate != null && !endDate.isBefore(today)) return true;
        }

        for (MakeUpSchedule request : MakeUpSchedule.getRequestsByStatus("Approved")) {
            if (request == null || request.getRoomCode() == null
                    || !roomCode.trim().equalsIgnoreCase(request.getRoomCode())) continue;
            LocalDate date = parseDate(request.getRequestedDate());
            if (date != null && !date.isBefore(today)) return true;
        }

        for (Request request : Request.requests) {
            if (request == null || request.getRoomCode() == null
                    || !roomCode.trim().equalsIgnoreCase(request.getRoomCode())
                    || !"Pending".equalsIgnoreCase(request.getStatus())) continue;
            LocalDate date = parseDate(request.getRequestedDate());
            if (date != null && !date.isBefore(today)) return true;
        }

        return false;
    }

    public static void archiveRoom() {
        String input, choice;

        UI.print("ARCHIVE EXISTING ROOM");

        do {
            UI.select("Enter Room Code", false);
            input = in.nextLine().trim();

            if (input.equalsIgnoreCase("X")) return;
            else if (input.isEmpty()) UI.print("Room Code is required");
            else if (findRoom(input) == null) UI.print("Room does not exist");
        } while (input.isEmpty() || findRoom(input) == null);

        Room room = findRoom(input);

        if (room == null) {
            UI.print("Room not found");
            return;
        }

        if (hasUpcomingUse(room.getRoomCode())) {
            UI.print("This room has an active schedule, approved make-up class, or pending future request.");
            UI.print("Resolve those records before archiving the room.");
            return;
        }

        displayRoom(room.getRoomCode(), false);

        do {
            UI.select("Confirm Room Deletion? (Y/N)", false);
            choice = in.nextLine().trim().toUpperCase();
            if (choice.equals("X")) return;

            if (!choice.matches("[YN]")) {
                UI.print("Invalid Input");
            }
        } while (!choice.matches("[YN]"));

        if (choice.equals("Y")) {
            archiveRooms.add(room);
            rooms.remove(room);
            UI.print("Room successfully archived");
        } else {
            UI.print("Archiving cancelled");
        }
    }
    public static void restoreRoom() {
        String input, choice;
        UI.print("RESTORE ARCHIVED ROOM");

        do {
            UI.select("Enter Room Code", false);
            input = in.nextLine().trim();
            if (input.equalsIgnoreCase("X")) return;
            if (input.isEmpty()) UI.print("Room code is required.");
            else if (findArchivedRoom(input) == null) UI.print("Archived room does not exist.");
        } while (input.isEmpty() || findArchivedRoom(input) == null);

        Room room = findArchivedRoom(input);
        if (room == null) return;
        if (Building.findBuilding(room.getBuildingCode()) == null) {
            UI.print("Restore the room's building before restoring this room.");
            return;
        }

        do {
            UI.select("Confirm Room Restoration? (Y/N)", false);
            choice = in.nextLine().trim().toUpperCase();
            if (choice.equals("X")) return;
            if (!choice.matches("[YN]")) UI.print("Enter Y or N.");
        } while (!choice.matches("[YN]"));

        if (choice.equals("Y")) {
            archiveRooms.remove(room);
            rooms.add(room);
            UI.print("Room successfully restored.");
        } else UI.print("Room restoration cancelled.");
    }

    private static Room findArchivedRoom(String roomCode) {
        if (roomCode == null || roomCode.trim().isEmpty()) return null;
        for (Room room : archiveRooms) {
            if (room != null && room.getRoomCode().equalsIgnoreCase(roomCode.trim())) return room;
        }
        return null;
    }

    private static void roomManage(String text, String roomCode, String roomDescription, String roomType,
                                   int floorNumber, int capacity, String buildingCode, String collegeCode) {
        UI.header(text.toUpperCase() + ": ROOM INFORMATION", false);
        UI.liner(String.format(" [1] ROOM CODE        : " + roomCode), false);
        UI.liner(String.format(" [2] ROOM DESCRIPTION : " + roomDescription), false);
        UI.liner(String.format(" [3] ROOM TYPE        : " + roomType), false);
        UI.liner(String.format(" [4] FLOOR NUMBER     : %s", floorNumber == 0 ? "" : floorNumber), false);
        UI.liner(String.format(" [5] CAPACITY         : %s", capacity == 0 ? "": capacity), false);
        UI.liner(String.format(" [6] BUILDING CODE    : " + buildingCode), false);
        UI.liner(String.format(" [7] COLLEGE CODE     : " + collegeCode), false);
        UI.liner("", false);
        UI.option();
        UI.footer(false);
    }
}