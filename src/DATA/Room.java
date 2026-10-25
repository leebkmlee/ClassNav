package DATA;

import UI.UI;

import java.time.LocalTime;
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

    private String roomCode, roomDescription, buildingCode, roomType, collegeCode;
    private int floorNumber, capacity;

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("MM/dd/uuuu")
                    .withResolverStyle(ResolverStyle.STRICT);

    public static ArrayList<Room> rooms = new ArrayList<>();
    public static ArrayList<Room> archiveRooms = new ArrayList<>();

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
    public static Room findRoomDesc(String roomDescription) {
        if (roomDescription == null) {
            return null;
        }

        for (Room room : rooms) {
            if (room.getRoomCode().equalsIgnoreCase(roomDescription.trim())) {
                return room;
            }
        }

        return null;
    }

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
    private static boolean overlaps(String start1, String end1, String start2, String end2) {
        int s1 = convertToMinutes(start1);
        int e1 = convertToMinutes(end1);
        int s2 = convertToMinutes(start2);
        int e2 = convertToMinutes(end2);

        if (s1 < 0 || e1 < 0 || s2 < 0 || e2 < 0) return false;
        return s1 < e2 && s2 < e1;
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
    private static boolean hasApprovedException(String scheduleID, String requestedDate, String exceptionType) {
        if (scheduleID == null || requestedDate == null || exceptionType == null) return false;

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
    public static boolean verifyAvailability(String roomCode, String requestedDate, String startTime, String endTime) {
        if (findRoom(roomCode) == null) return false;

        LocalDate date = parseDate(requestedDate);

        if (date == null) return false;

        int requestedStart = convertToMinutes(startTime);
        int requestedEnd = convertToMinutes(endTime);

        if (requestedStart < 0 || requestedEnd < 0 || requestedStart >= requestedEnd) return false;

        String fullDay = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);

        String shortDay = date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);

        String normalizedDate = date.format(DATE_FORMAT);

        for (Schedule schedule : schedules) {
            if (schedule.getRoomCode() == null || !schedule.getRoomCode().equalsIgnoreCase(roomCode.trim())) continue;
            if (!sameDay(schedule.getDay(), fullDay) && !sameDay(schedule.getDay(), shortDay)) continue;
            if (!isScheduleActiveOnDate(schedule, date)) continue;
            boolean cancelled = hasApprovedException(schedule.getScheduleID(), normalizedDate, "CANCELLATION");
            boolean roomChanged = hasApprovedException(schedule.getScheduleID(), normalizedDate, "ROOM CHANGE");
            if (cancelled || roomChanged) continue;
            if (overlaps(startTime, endTime, schedule.getStartTime(), schedule.getEndTime())) return false;
        }

        for (CancelledSchedule exception : CancelledSchedule.cancellations) {
            Schedule original = Schedule.findSchedule(exception.getScheduleID());
            if (!isScheduleActiveOnDate(original, date) || !sameDay(original.getDay(), fullDay)) continue;
            if (overlaps(startTime, endTime, original.getStartTime(), original.getEndTime())) return false;
        }

        for (MakeUpSchedule makeup : MakeUpSchedule.makeUpSchedules) {
            if (makeup.getStatus() == null
                    || !makeup.getStatus().equalsIgnoreCase("Approved")
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
    private static String fitText(String text, int width) {
        if (text == null || width <= 0) {
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

    public static void displayRoom(String roomCode) {
        Room room = findRoom(roomCode);

        UI.header("ROOM DETAILS");

        if (room == null) {
            UI.liner("  Room not found.");
        } else {
            Building building = Building.findBuilding(room.getBuildingCode());
            College college = College.findCollege(room.getCollegeCode());

            UI.liner(String.format("  ROOM CODE      : %s", room.getRoomCode()));
            UI.liner(String.format("  DESCRIPTION    : %s", fitText(room.getRoomDescription(), 50)));
            UI.liner(String.format("  ROOM TYPE      : %s", fitText(room.getRoomType(), 50)));
            UI.liner(String.format("  CAPACITY       : %s", room.getCapacity() + " seats"));
            UI.liner(String.format("  FLOOR          : %s", getFloorText(room.getFloorNumber())));
            UI.liner(String.format("  BUILDING CODE  : %s", room.getBuildingCode()));
            UI.liner(String.format("  COLLEGE CODE   : %s", room.getCollegeCode()));
            UI.liner(String.format("  BUILDING       : %s", fitText(building.getBuildingName(), 50)));
            UI.liner(String.format("  COLLEGE        : %s", fitText(college.getCollegeDescription(), 50)));
        }
        UI.footer();
    }
    public static void displayRooms() {
        UI.header("ROOM LIST");

        if (rooms.isEmpty()) {
            UI.liner("  No rooms found.");
            UI.footer();
            return;
        }

        ArrayList<String> displayedBuildings = new ArrayList<>();
        boolean firstBuilding = true;

        for (Room room : rooms) {
            if (room == null) continue;

            String currentBuildingCode = room.getBuildingCode();

            if (currentBuildingCode == null
                    || displayedBuildings.contains(currentBuildingCode.toUpperCase(Locale.ENGLISH))) {
                continue;
            }

            displayedBuildings.add(currentBuildingCode.toUpperCase(Locale.ENGLISH));

            if (!firstBuilding) UI.separator();

            firstBuilding = false;

            Building building = Building.findBuilding(currentBuildingCode);
            String buildingName = building != null
                    ? building.getBuildingName()
                    : "Unknown Building";

            UI.liner("  " + fitText(currentBuildingCode + " - " + buildingName, 64));

            for (Room buildingRoom : rooms) {
                if (buildingRoom == null
                        || buildingRoom.getBuildingCode() == null
                        || !buildingRoom.getBuildingCode().equalsIgnoreCase(currentBuildingCode)) {
                    continue;
                }

                UI.separator();

                UI.liner(String.format(
                        "  %-9s %-20s %-25s %-10s",
                        fitText(buildingRoom.getRoomCode(), 9),
                        fitText(buildingRoom.getRoomDescription(), 20),
                        fitText(buildingRoom.getRoomType(), 20),
                        buildingRoom.getCollegeCode()));

                UI.liner(String.format(
                        "  %-9s %-20s %-12s",
                        "",
                        buildingRoom.getCapacity() + " seats",
                        getFloorText(buildingRoom.getFloorNumber())));
            }
        }
        UI.footer();
    }
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

        UI.liner("  " + fitText(building.getBuildingCode() + " - " + building.getBuildingName(), 64));

        boolean found = false;

        for (Room buildingRoom : rooms) {
            if (buildingRoom == null
                    || buildingRoom.getBuildingCode() == null
                    || !buildingRoom.getBuildingCode().equalsIgnoreCase(building.getBuildingCode())) {
                continue;
            }

            UI.separator();

            UI.liner(String.format(
                    "  %-9s %-20s %-25s %-10s",
                    fitText(buildingRoom.getRoomCode(), 9),
                    fitText(buildingRoom.getRoomDescription(), 20),
                    fitText(buildingRoom.getRoomType(), 20),
                    buildingRoom.getCollegeCode()));

            UI.liner(String.format(
                    "  %-9s %-20s %-12s",
                    "",
                    buildingRoom.getCapacity() + " seats",
                    getFloorText(buildingRoom.getFloorNumber())));

            found = true;
        }

        if (!found) {
            UI.separator();
            UI.liner("  No rooms found for this building.");
        }

        UI.footer();
    }
    public static void displayArchivedRooms() {
        UI.header("ARCHIVED ROOMS");

        if (archiveRooms.isEmpty()) {
            UI.liner("  No rooms found.");
            UI.footer();
            return;
        }

        ArrayList<String> displayedBuildings = new ArrayList<>();
        boolean firstBuilding = true;

        for (Room room : archiveRooms) {
            if (room == null) continue;

            String currentBuildingCode = room.getBuildingCode();

            if (currentBuildingCode == null
                    || displayedBuildings.contains(currentBuildingCode.toUpperCase(Locale.ENGLISH))) {
                continue;
            }

            displayedBuildings.add(currentBuildingCode.toUpperCase(Locale.ENGLISH));

            if (!firstBuilding) UI.separator();

            firstBuilding = false;

            Building building = Building.findBuilding(currentBuildingCode);
            String buildingName = building != null
                    ? building.getBuildingName()
                    : "Unknown Building";

            UI.liner("  " + fitText(currentBuildingCode + " - " + buildingName, 64));

            for (Room buildingRoom : archiveRooms) {
                if (buildingRoom == null
                        || buildingRoom.getBuildingCode() == null
                        || !buildingRoom.getBuildingCode().equalsIgnoreCase(currentBuildingCode)) {
                    continue;
                }

                UI.separator();

                UI.liner(String.format(
                        "  %-9s %-20s %-25s %-10s",
                        fitText(buildingRoom.getRoomCode(), 9),
                        fitText(buildingRoom.getRoomDescription(), 20),
                        fitText(buildingRoom.getRoomType(), 20),
                        buildingRoom.getCollegeCode()));

                UI.liner(String.format(
                        "  %-9s %-20s %-12s",
                        "",
                        buildingRoom.getCapacity() + " seats",
                        getFloorText(buildingRoom.getFloorNumber())));
            }
        }

        UI.footer();
    }

    public static void createRoom() {
        String roomCode = "", roomDescription = "", roomType = "", buildingCode = "", collegeCode = "", choice;
        int floorNumber = 0, capacity = 0; boolean submit = false;

        while (!submit) {
            roomManage("CREATE", roomCode, roomDescription, roomType,
                    floorNumber, capacity, buildingCode, collegeCode);

            do {
                System.out.print("  Select > ");
                choice = in.nextLine().trim().toUpperCase();

                if (!choice.matches("[1-7X]") && !choice.equals("/")) {
                    UI.print("Invalid Choice");
                }
            } while (!choice.matches("[1-7X]") && !choice.equals("/"));

            switch (choice) {
                case "1":
                    do {
                        System.out.print("   Enter Room Code > ");
                        roomCode = in.nextLine().trim();

                        if (roomCode.isEmpty()) {
                            UI.print("Room code is required");
                        } else if (findRoom(roomCode) != null) {
                            UI.print("Room code already exists");
                        }
                    } while (roomCode.isEmpty() || findRoom(roomCode) != null);
                    break;

                case "2":
                    do {
                        System.out.print("   Enter Room Description > ");
                        roomDescription = in.nextLine().trim();

                        if (roomDescription.isEmpty()) {
                            UI.print("Room description is required");
                        } else if (findRoomDesc(roomDescription) != null) {
                            UI.print("Room description already exists");
                        }
                    } while (roomDescription.isEmpty() || findRoomDesc(roomDescription) != null);
                    break;

                case "3":
                    do {
                        System.out.print("   Enter Room Type > ");
                        roomType = in.nextLine().trim();

                        if (roomType.isEmpty()) {
                            UI.print("Room type is required");
                        }
                    } while (roomType.isEmpty());
                    break;

                case "4":
                    boolean validFloor = false;

                    while (!validFloor) {
                        System.out.print("   Enter Floor Number > ");
                        String input = in.nextLine().trim();

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
                    break;

                case "5":
                    boolean validCapacity = false;

                    while (!validCapacity) {
                        System.out.print("   Enter Capacity > ");
                        String input = in.nextLine().trim();

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
                    break;

                case "6":
                    do {
                        System.out.print("   Enter Building Code > ");
                        buildingCode = in.nextLine().trim().toUpperCase();

                        if (buildingCode.isEmpty()) {
                            UI.print("Building code is required");
                        } else if (Building.findBuilding(buildingCode) == null) {
                            UI.print("Building code does not exist");
                        }
                    } while (buildingCode.isEmpty()
                            || Building.findBuilding(buildingCode) == null);
                    break;

                case "7":
                    do {
                        System.out.print("   Enter College Code > ");
                        collegeCode = in.nextLine().trim().toUpperCase();

                        if (collegeCode.isEmpty()) {
                            UI.print("College code is required");
                        } else if (College.findCollege(collegeCode) == null) {
                            UI.print("College code does not exist");
                        }
                    } while (collegeCode.isEmpty()
                            || College.findCollege(collegeCode) == null);
                    break;

                case "/":
                    if (roomCode.isEmpty() || roomDescription.isEmpty() || roomType.isEmpty() || floorNumber < 1 ||
                    capacity < 1 || buildingCode.isEmpty() || collegeCode.isEmpty()) UI.print("Details are incomplete");
                    else {
                        rooms.add(new Room(roomCode, roomDescription, floorNumber, roomType, capacity,
                                buildingCode, collegeCode));
                        UI.print("Room successfully created");
                        Room.displayRoom(roomCode);
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

        Room r;

        do {
            System.out.print("  Enter Room Code > ");
            input = in.nextLine().trim();

            r = Room.findRoom(input);

            if (r == null) UI.print("Room does not exist");
        } while (r == null);

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
                System.out.print("  Select > ");
                choice = in.nextLine().trim().toUpperCase();

                if (!choice.matches("[1-7X]") && !choice.equals("/")) {
                    UI.print("Invalid Choice");
                }
            } while (!choice.matches("[1-7X]") && !choice.equals("/"));

            switch (choice) {
                case "1":
                    do {
                        System.out.print("   Enter Room Code > ");
                        roomCode = in.nextLine().trim();
                        if (roomCode.isEmpty()) UI.print("Room code is required");
                        else if (findRoom(roomCode) != null) UI.print("Room code already exists");
                    } while (roomCode.isEmpty() || findRoom(roomCode) != null);
                    break;

                case "2":
                    do {
                        System.out.print("   Enter Room Description > ");
                        roomDescription = in.nextLine().trim();
                        if (roomDescription.isEmpty()) UI.print("Room description is required");
                        else if (findRoomDesc(roomDescription) != null) UI.print("Room description already exists");

                    } while (roomDescription.isEmpty() || findRoomDesc(roomDescription) != null);
                    break;

                case "3":
                    do {
                        System.out.print("   Enter Room Type > ");
                        roomType = in.nextLine().trim();
                        if (roomType.isEmpty()) UI.print("Room type is required");
                    } while (roomType.isEmpty());
                    break;

                case "4":
                    boolean validFloor = false;
                    while (!validFloor) {
                        System.out.print("   Enter Floor Number > ");
                        input = in.nextLine().trim();
                        try {
                            floorNumber = Integer.parseInt(input);
                            if (floorNumber < 1) UI.print("Floor number must be at least 1");
                            else validFloor = true;
                        } catch (NumberFormatException e) {
                            UI.print("Invalid floor number. Numbers only");
                        }
                    }
                    break;

                case "5":
                    boolean validCapacity = false;
                    while (!validCapacity) {
                        System.out.print("   Enter Capacity > ");
                        input = in.nextLine().trim();
                        try {
                            capacity = Integer.parseInt(input);
                            if (capacity < 1) UI.print("Capacity must be at least 1");
                            else validCapacity = true;
                        } catch (NumberFormatException e) {
                            UI.print("Invalid capacity. Numbers only");
                        }
                    }
                    break;

                case "6":
                    do {
                        System.out.print("   Enter Building Code > ");
                        buildingCode = in.nextLine().trim().toUpperCase();
                        if (buildingCode.isEmpty()) {
                            UI.print("Building code is required");
                        } else if (Building.findBuilding(buildingCode) == null) {
                            UI.print("Building code does not exist");
                        }
                    } while (buildingCode.isEmpty()
                            || Building.findBuilding(buildingCode) == null);
                    break;

                case "7":
                    do {
                        System.out.print("   Enter College Code > ");
                        collegeCode = in.nextLine().trim().toUpperCase();

                        if (collegeCode.isEmpty()) {
                            UI.print("College code is required");
                        } else if (College.findCollege(collegeCode) == null) {
                            UI.print("College code does not exist");
                        }
                    } while (collegeCode.isEmpty()
                            || College.findCollege(collegeCode) == null);
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
    public static void archiveRoom() {
        String input, choice;

        UI.print("ARCHIVE EXISTING ROOM");

        do {
            System.out.print("  Enter Room Code > ");
            input = in.nextLine().trim();

            if (input.isEmpty() || findRoom(input) == null) {
                UI.print("Room does not exist");
            }
        } while (input.isEmpty() || findRoom(input) == null);

        Room room = findRoom(input);

        if (room == null) {
            UI.print("Room not found");
            return;
        }

        displayRoom(room.getRoomCode());

        do {
            System.out.print("  Confirm Room Deletion? (Y/N) > ");
            choice = in.nextLine().trim().toUpperCase();

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
    private static void roomManage(String text, String roomCode, String roomDescription, String roomType,
                                   int floorNumber, int capacity, String buildingCode, String collegeCode) {
        UI.header(text.toUpperCase() + ": ROOM INFORMATION");
        UI.liner(String.format(" [1] ROOM CODE        : " + roomCode));
        UI.liner(String.format(" [2] ROOM DESCRIPTION : " + roomDescription));
        UI.liner(String.format(" [3] ROOM TYPE        : " + roomType));
        UI.liner(String.format(" [4] FLOOR NUMBER     : %s", floorNumber == 0 ? "" : floorNumber));
        UI.liner(String.format(" [5] CAPACITY         : %s", capacity == 0 ? "": capacity));
        UI.liner(String.format(" [6] BUILDING CODE    : " + buildingCode));
        UI.liner(String.format(" [7] COLLEGE CODE     : " + collegeCode));
        UI.liner("");
        UI.option();
        UI.footer();
    }
}