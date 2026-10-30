package DATA;

import java.util.ArrayList;
import UI.*;
import static DATA.Room.rooms;
import static UI.UI.in;

public class Building {
    // FIELDS
    private static int nextBuildingCode = 1;

    private final String buildingCode;
    private String buildingName;

    public static ArrayList<Building> buildings = new ArrayList<>();
    public static ArrayList<Building> archiveBuildings = new ArrayList<>();
    private static ArrayList<Room> buildingArchivedRooms = new ArrayList<>();

    // CONSTRUCTOR
    public Building(String buildingName) {
        this.buildingCode = String.format("BLD%02d", nextBuildingCode++);
        this.buildingName = buildingName.trim();
    }

    // DUMMY DATA
    static {
        buildings.add(new Building("Pimentel Hall"));
        buildings.add(new Building("Roxas Hall"));
        buildings.add(new Building("Alvarado Hall"));
        buildings.add(new Building("SLRC"));
        buildings.add(new Building("CBA Building"));
        buildings.add(new Building("Natividad Hall"));
        buildings.add(new Building("CHTM Building"));
        buildings.add(new Building("Federizo"));
        buildings.add(new Building("NSTP Building"));
        buildings.add(new Building("Mendoza Hall"));
        buildings.add(new Building("Carpio Hall"));
        buildings.add(new Building("Flores Hall"));
        buildings.add(new Building("Valencia Hall"));
        buildings.add(new Building("E-Library"));
        buildings.add(new Building("College of Law Building"));
    }

    // GETTERS METHOD
    public String getBuildingCode() {
        return buildingCode;
    }
    public String getBuildingName() {
        return buildingName;
    }

    // SETTERS METHOD
    public void setBuildingName(String buildingName) {

        if (buildingName == null || buildingName.trim().isEmpty()) {
            UI.print("Building name is required.");
            return;
        }

        Building existing = findBuildingByName(buildingName);

        if (existing != null && existing != this) {
            UI.print("Building name already exists.");
            return;
        }

        this.buildingName = buildingName.trim();
    }

    // FIND METHODS
    public static Building findBuilding(String buildingCode) {

        if (buildingCode == null)
            return null;

        for (Building b : buildings) {
            if (b != null && b.getBuildingCode() != null
                    && buildingCode.trim().equalsIgnoreCase(b.getBuildingCode()))
                return b;
        }

        return null;
    }
    public static Building findBuildingByName(String buildingName) {

        if (buildingName == null)
            return null;

        for (Building b : buildings) {
            if (b != null && b.getBuildingName() != null
                    && buildingName.trim().equalsIgnoreCase(b.getBuildingName()))
                return b;
        }

        return null;
    }

    // DISPLAY METHODS
    public static void displayBuildings() {
        UI.header("BUILDINGS", true);
        if (buildings.isEmpty()) UI.liner("  No buildings found.", true);
        else {
            UI.liner(String.format("  %-18s %-159s", "BUILDING CODE", "BUILDING NAME"), true);
            UI.separator(true);
            int count = 0;
            for (Building b : buildings) {
                if (b == null) continue;
                UI.liner(String.format("  %-18s %-159s", b.getBuildingCode(),
                        UI.fitText(b.getBuildingName(), 159)), true);
                count++;
            }
            UI.separator(true);
            UI.liner("  TOTAL BUILDINGS: " + count, true);
        }
        UI.footer(true);
        UI.returnButton();
    }
    public static void displayArchiveBuildings() {
        UI.header("ARCHIVED BUILDINGS", true);
        if (archiveBuildings.isEmpty()) UI.liner("  No archived buildings found.", true);
        else {
            UI.liner(String.format("  %-18s %-159s", "BUILDING CODE", "BUILDING NAME"), true);
            UI.separator(true);
            int count = 0;
            for (Building b : archiveBuildings) {
                if (b == null) continue;
                UI.liner(String.format("  %-18s %-159s", b.getBuildingCode(),
                        UI.fitText(b.getBuildingName(), 159)), true);
                count++;
            }
            UI.separator(true);
            UI.liner("  TOTAL ARCHIVED BUILDINGS: " + count, true);
        }
        UI.footer(true);
        UI.returnButton();
    }
    public static void displayBuilding(String buildingCode, boolean print) {

        Building b = findBuilding(buildingCode);

        if (b == null) {
            UI.print("Building not found");
            return;
        }

        UI.header("BUILDING DETAILS", false);
        UI.liner(String.format("  BUILDING CODE  : %s", b.getBuildingCode()), false);
        UI.liner(String.format("  BUILDING NAME  : %s", b.getBuildingName()), false);
        UI.footer(false);
        if (print) UI.returnButton();
    }
    public static void displayRooms(String buildingCode) {
        Building b = findBuilding(buildingCode);
        if (b == null) {
            UI.print("Building not found");
            return;
        }

        UI.header("ROOM LIST", true);
        UI.liner("  BUILDING CODE: " + b.getBuildingCode()
                + " | BUILDING: " + b.getBuildingName(), true);
        UI.liner(String.format("  %-12s %-102s %-15s %-15s %-12s %-17s",
                "ROOM CODE", "ROOM DESCRIPTION", "FLOOR", "ROOM TYPE", "CAPACITY", "COLLEGE"), true);
        UI.separator(true);

        int count = 0;
        for (Room r : rooms) {
            if (r == null || !b.getBuildingCode().equalsIgnoreCase(r.getBuildingCode())) continue;
            UI.liner(String.format("  %-12s %-102s %-15s %-15s %-12s %-17s",
                    r.getRoomCode(), UI.fitText(r.getRoomDescription(), 102),
                    UI.fitText(Room.getFloorText(r.getFloorNumber()), 15),
                    UI.fitText(r.getRoomType(), 15), r.getCapacity(), r.getCollegeCode()), true);
            count++;
        }
        UI.separator(true);
        UI.liner("  TOTAL ROOMS: " + count, true);
        UI.footer(true);
        UI.returnButton();
    }

    // MANAGE METHODS
    public static void createBuilding() {
        String buildingName = "";
        String choice;
        boolean submit = false;

        while (!submit) {
            buildingManage("CREATE", String.format("BLD%02d", buildings.size() + 1), buildingName);

            do {
                UI.select("Select",false);
                choice = in.nextLine().trim().toUpperCase();

                if (!choice.matches("[1X]") && !choice.equals("/"))
                    UI.print("Invalid Choice");

            } while (!choice.matches("[1X]") && !choice.equals("/"));

            switch (choice) {
                case "1":
                    do {
                        UI.select("Enter Building Name", true);
                        buildingName = in.nextLine().trim();
                        if (buildingName.equalsIgnoreCase("X")) {
                            buildingName = "";
                            break;
                        }
                        else if (buildingName.trim().isEmpty())
                            UI.print("Building name is required");
                        else if (Building.findBuildingByName(buildingName) != null)
                            UI.print("Building name already exists");

                    } while (buildingName.isEmpty()
                            || Building.findBuildingByName(buildingName) != null);
                    break;

                case "/":
                    if (buildingName == null || buildingName.isEmpty()) UI.print("Building name is required.");
                    else {
                        buildings.add(new Building(buildingName));
                        UI.print("Building successfully created");

                        Building b = Building.findBuildingByName(buildingName);
                        displayBuilding(b.getBuildingCode(), true);
                        submit = true;
                    }
                    break;
                case "X":
                    submit = true;
                    break;
            }
        }
    }
    public static void editBuilding() {
        String input;
        String buildingName;
        String choice;
        boolean submit = false;

        UI.print("EDIT EXISTING BUILDING");

        do {
            UI.select("Enter Building Code", false);
            input = in.nextLine().trim();
            if (input.equalsIgnoreCase("X")) return;
            else if (input.isEmpty()) UI.print("Building Code is required");
            else if (Building.findBuilding(input) == null) UI.print("Building does not exist");
        } while (Building.findBuilding(input) == null);

        Building b = Building.findBuilding(input);
        buildingName = b.getBuildingName();

        while (!submit) {
            buildingManage("EDIT", b.getBuildingCode(), buildingName);

            do {
                UI.select("Select",false);
                choice = in.nextLine().trim().toUpperCase();

                if (!choice.matches("[1X]") && !choice.equals("/"))
                    UI.print("Invalid Choice");

            } while (!choice.matches("[1X]") && !choice.equals("/"));

            switch (choice) {
                case "1":
                    do {
                        UI.select("Enter Building Name", true);
                        buildingName = in.nextLine().trim();
                        if (buildingName.equalsIgnoreCase("X")) {
                            buildingName = b.getBuildingName();
                            break;
                        }
                        else if (buildingName.isEmpty()) UI.print("Building name is required");
                        else if (Building.findBuildingByName(buildingName) != null && Building.findBuildingByName(buildingName) != b)
                            UI.print("Building name already exists");

                    } while (buildingName.isEmpty()
                            || (Building.findBuildingByName(buildingName) != null
                            && Building.findBuildingByName(buildingName) != b));
                    break;
                case "/":
                    b.setBuildingName(buildingName);

                    UI.print("Building successfully edited");
                    Building.displayBuilding(b.getBuildingCode(), true);

                    submit = true;
                    break;
                case "X":
                    submit = true;
                    break;
            }
        }
    }
    public static void archiveBuilding() {
        String input;
        String choice;

        UI.print("ARCHIVE EXISTING BUILDING");

        do {
            UI.select("Enter Building Code",false);
            input = in.nextLine().trim();
            if (input.equalsIgnoreCase("X")) return;
            else if (input.isEmpty()) UI.print("Building Code is required");
            else if (Building.findBuilding(input) == null) UI.print("Building does not exist");
        } while (Building.findBuilding(input) == null);

        Building b = Building.findBuilding(input);
        for (Room room : Room.rooms) {
            if (room != null && room.getBuildingCode().equalsIgnoreCase(b.getBuildingCode())
                    && Room.hasUpcomingUse(room.getRoomCode())) {
                UI.print("Building cannot be archived while one of its rooms has an active schedule,");
                UI.print("an approved future make-up class, or a pending future room request.");
                return;
            }
        }

        displayBuilding(b.getBuildingCode(), false);

        do {
            UI.select("Confirm Building Archive? (Y/N)",true);
            choice = in.nextLine().trim().toUpperCase();
            if (choice.equals("X")) return;

            if (!choice.matches("[YN]"))
                UI.print("Invalid Input");

        } while (!choice.matches("[YN]"));

        if (choice.equals("Y")) {
            for (Room room : new ArrayList<>(Room.rooms)) {
                if (room != null && room.getBuildingCode().equalsIgnoreCase(b.getBuildingCode())) {
                    Room.rooms.remove(room);
                    if (!Room.archiveRooms.contains(room)) Room.archiveRooms.add(room);
                    if (!buildingArchivedRooms.contains(room)) buildingArchivedRooms.add(room);
                }
            }
            archiveBuildings.add(b);
            buildings.remove(b);
            UI.print("Building and its rooms successfully archived.");
        } else UI.print("Archiving cancelled");
    }

    public static void restoreBuilding() {
        String input, choice;
        UI.print("RESTORE ARCHIVED BUILDING");

        do {
            UI.select("Enter Building Code", false);
            input = in.nextLine().trim();
            if (input.equalsIgnoreCase("X")) return;
            if (input.isEmpty()) UI.print("Building code is required.");
            else if (findArchivedBuilding(input) == null) UI.print("Archived building does not exist.");
        } while (input.isEmpty() || findArchivedBuilding(input) == null);

        Building building = findArchivedBuilding(input);
        if (building == null) return;

        do {
            UI.select("Confirm Building Restoration? (Y/N)", false);
            choice = in.nextLine().trim().toUpperCase();
            if (choice.equals("X")) return;
            if (!choice.matches("[YN]")) UI.print("Enter Y or N.");
        } while (!choice.matches("[YN]"));

        if (choice.equals("Y")) {
            archiveBuildings.remove(building);
            buildings.add(building);
            for (Room room : new ArrayList<>(buildingArchivedRooms)) {
                if (room != null && room.getBuildingCode().equalsIgnoreCase(building.getBuildingCode())) {
                    Room.archiveRooms.remove(room);
                    if (!Room.rooms.contains(room)) Room.rooms.add(room);
                    buildingArchivedRooms.remove(room);
                }
            }
            UI.print("Building and its archived rooms successfully restored.");
        } else UI.print("Building restoration cancelled.");
    }

    private static Building findArchivedBuilding(String buildingCode) {
        if (buildingCode == null || buildingCode.trim().isEmpty()) return null;
        for (Building building : archiveBuildings) {
            if (building != null && building.getBuildingCode().equalsIgnoreCase(buildingCode.trim())) return building;
        }
        return null;
    }

    private static void buildingManage(String text, String buildingCode, String buildingName) {
        UI.header(text.toUpperCase() + ": BUILDING INFORMATION", false);
        UI.liner(String.format("      BUILDING CODE : %s", buildingCode), false);
        UI.liner(String.format("  [1] BUILDING NAME : %s", buildingName), false);
        UI.liner("", false);
        UI.option();
        UI.footer(false);
    }
}