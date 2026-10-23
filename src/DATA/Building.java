package DATA;

import java.util.ArrayList;
import UI.*;
import static DATA.Room.rooms;
import static UI.UI.in;

public class Building {

    private static int nextBuildingCode = 1;

    private String buildingCode;
    private String buildingName;

    public Building(String buildingName) {

        if (buildingName == null || buildingName.trim().isEmpty())
            throw new IllegalArgumentException("Building name is required");

        if (findBuildingByName(buildingName) != null)
            throw new IllegalArgumentException("Building name already exists");

        this.buildingCode = String.format("BLD%02d", nextBuildingCode++);
        this.buildingName = buildingName.trim();
    }

    public static ArrayList<Building> buildings = new ArrayList<>();

    static {
        buildings.add(new Building("Pimentel Hall"));
        buildings.add(new Building("NSTP Building"));
        buildings.add(new Building("Federizo Hall"));
        buildings.add(new Building("Natividad Hall"));
        buildings.add(new Building("Roxas Hall"));
    }

    // GETTERS

    public String getBuildingCode() {
        return buildingCode;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public static String getBuilding(Building b) {
        return b.getBuildingCode();
    }

    public static String getBuildingName(Building b) {
        return b.getBuildingName();
    }

    public static int getNextID() {
        return nextBuildingCode;
    }

    // SETTERS

    public void setBuildingName(String buildingName) {

        if (buildingName == null || buildingName.trim().isEmpty())
            throw new IllegalArgumentException("Building name is required");

        Building existing = findBuildingByName(buildingName);

        if (existing != null && existing != this)
            throw new IllegalArgumentException("Building name already exists");

        this.buildingName = buildingName.trim();
    }

    // FIND METHODS

    public static Building findBuilding(String buildingCode) {

        if (buildingCode == null)
            return null;

        for (Building b : buildings) {
            if (buildingCode.equalsIgnoreCase(b.getBuildingCode()))
                return b;
        }

        return null;
    }

    public static Building findBuildingByName(String buildingName) {

        if (buildingName == null)
            return null;

        for (Building b : buildings) {
            if (buildingName.trim().equalsIgnoreCase(b.getBuildingName()))
                return b;
        }

        return null;
    }

    public static boolean verifyBuilding(String buildingCode) {
        return findBuilding(buildingCode) != null;
    }

    // ADD BUILDING

    public static boolean addBuilding(String buildingName) {

        if (buildingName == null || buildingName.trim().isEmpty()) {
            UI.print("Building name is required");
            return false;
        }

        if (findBuildingByName(buildingName) != null) {
            UI.print("Building name already exists");
            return false;
        }

        buildings.add(new Building(buildingName));
        return true;
    }

    // DISPLAY ALL BUILDINGS

    public static void displayBuildings() {

        UI.header("BUILDINGS");

        if (buildings.isEmpty()) {
            UI.liner("  No buildings found.");
        } else {
            for (int i = 0; i < buildings.size(); i++) {
                Building b = buildings.get(i);

                UI.liner(String.format(
                        "  %-15s %-30s",
                        b.getBuildingCode(),
                        b.getBuildingName()
                ));

                if (i < buildings.size() - 1)
                    UI.separator();
            }
        }

        UI.footer();
    }

    // DISPLAY ONE BUILDING

    public static void displayBuilding(String buildingCode) {

        Building b = findBuilding(buildingCode);

        if (b == null) {
            UI.print("Building not found");
            return;
        }

        UI.header("BUILDING DETAILS");
        UI.liner(String.format("  BUILDING CODE  : %s", b.getBuildingCode()));
        UI.liner(String.format("  BUILDING NAME  : %s", b.getBuildingName()));
        UI.footer();
    }

    // DISPLAY ROOMS BY BUILDING CODE

    public static void displayRooms(String buildingCode) {

        Building b = findBuilding(buildingCode);

        if (b == null) {
            UI.print("Building not found");
            return;
        }

        UI.header("ROOM LIST");
        UI.liner(String.format("  BUILDING CODE  : %s", b.getBuildingCode()));
        UI.liner("  Rooms under " + b.getBuildingName() + ":");
        UI.separator();

        int count = 0;

        for (Room r : rooms) {

            if (b.getBuildingCode().equalsIgnoreCase(r.getBuildingCode())) {

                int floor = r.getFloorNumber();
                String floorText;

                if (floor < 1) {
                    floorText = "Invalid Floor";
                } else if (floor % 100 >= 11 && floor % 100 <= 13) {
                    floorText = floor + "th Floor";
                } else if (floor % 10 == 1) {
                    floorText = floor + "st Floor";
                } else if (floor % 10 == 2) {
                    floorText = floor + "nd Floor";
                } else if (floor % 10 == 3) {
                    floorText = floor + "rd Floor";
                } else {
                    floorText = floor + "th Floor";
                }

                UI.liner(String.format(
                        "  %-10s %-35s %s",
                        r.getRoomCode(),
                        r.getRoomDescription(),
                        floorText
                ));

                count++;
            }
        }

        if (count == 0)
            UI.liner("  No rooms registered under this building.");

        UI.liner("  TOTAL ROOMS: " + count);
        UI.footer();
    }

    public static void createBuilding() {
        String buildingName = "";
        String choice;
        boolean submit = false;

        while (!submit) {
            buildingManage("CREATE", "AUTO-GENERATED", buildingName);

            do {
                System.out.print("  Select > ");
                choice = in.nextLine().trim().toUpperCase();

                if (!choice.matches("[1X]") && !choice.equals("/"))
                    UI.print("Invalid Choice");

            } while (!choice.matches("[1X]") && !choice.equals("/"));

            switch (choice) {
                case "1":
                    do {
                        System.out.print("   Enter Building Name > ");
                        buildingName = in.nextLine().trim();

                        if (buildingName.trim().isEmpty())
                            UI.print("Building name is required");
                        else if (Building.findBuildingByName(buildingName) != null)
                            UI.print("Building name already exists");

                    } while (buildingName.isEmpty()
                            || Building.findBuildingByName(buildingName) != null);
                    break;

                case "/":
                    if (buildingName.isEmpty()) {
                        UI.print("Building name is required");
                        break;
                    }

                    if (Building.addBuilding(buildingName)) {
                        UI.print("Building successfully created");

                        Building b = Building.findBuildingByName(buildingName);
                        Building.displayBuilding(b.getBuildingCode());

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
            System.out.print("  Enter Building Code > ");
            input = in.nextLine().trim();

            if (Building.findBuilding(input) == null)
                UI.print("Building does not exist");

        } while (Building.findBuilding(input) == null);

        Building b = Building.findBuilding(input);
        buildingName = b.getBuildingName();

        while (!submit) {
            buildingManage("EDIT", b.getBuildingCode(), buildingName);

            do {
                System.out.print("  Select > ");
                choice = in.nextLine().trim().toUpperCase();

                if (!choice.matches("[1X]") && !choice.equals("/"))
                    UI.print("Invalid Choice");

            } while (!choice.matches("[1X]") && !choice.equals("/"));

            switch (choice) {
                case "1":
                    do {
                        System.out.print("   Enter Building Name > ");
                        buildingName = in.nextLine().trim();

                        if (buildingName.isEmpty()) {
                            UI.print("Building name is required");
                        } else {
                            Building existing =
                                    Building.findBuildingByName(buildingName);

                            if (existing != null && existing != b)
                                UI.print("Building name already exists");
                        }

                    } while (buildingName.isEmpty()
                            || (Building.findBuildingByName(buildingName) != null
                            && Building.findBuildingByName(buildingName) != b));
                    break;

                case "/":
                    if (buildingName.isEmpty()) {
                        UI.print("Building name is required");
                        break;
                    }

                    Building existing = Building.findBuildingByName(buildingName);

                    if (existing != null && existing != b) {
                        UI.print("Building name already exists");
                        break;
                    }

                    b.setBuildingName(buildingName);

                    UI.print("Building successfully edited");
                    Building.displayBuilding(b.getBuildingCode());

                    submit = true;
                    break;

                case "X":
                    submit = true;
                    break;
            }
        }
    }

    public static void deleteBuilding() {
        String input;
        String choice;

        UI.print("DELETE EXISTING BUILDING");

        do {
            System.out.print("  Enter Building Code > ");
            input = in.nextLine().trim();

            if (Building.findBuilding(input) == null)
                UI.print("Building does not exist");

        } while (Building.findBuilding(input) == null);

        Building b = Building.findBuilding(input);

        Building.displayBuilding(b.getBuildingCode());

        do {
            System.out.print("  Confirm Building Deletion? (Y/N) > ");
            choice = in.nextLine().trim().toUpperCase();

            if (!choice.matches("[YN]"))
                UI.print("Invalid Input");

        } while (!choice.matches("[YN]"));

        if (choice.equals("Y")) {
            Building.buildings.remove(b);
            UI.print("Building successfully deleted");
        }
    }

    private static void buildingManage(
            String text, String buildingCode, String buildingName) {

        UI.header(text.toUpperCase() + ": BUILDING INFORMATION");

        UI.liner(String.format("  [1] BUILDING NAME : %s", buildingName));
        UI.liner(String.format("      BUILDING CODE : %s", buildingCode));
        UI.liner("");

        UI.option();
        UI.footer();
    }
}