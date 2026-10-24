package DATA;

import java.util.ArrayList;
import UI.*;
import static DATA.Room.rooms;
import static UI.UI.in;

public class Building {

    private static int nextBuildingCode = 1;

    private final String buildingCode;
    private String buildingName;
    public static ArrayList<Building> buildings = new ArrayList<>();
    public static ArrayList<Building> archiveBuildings = new ArrayList<>();

    public Building(String buildingName) {
        this.buildingCode = String.format("BLD%02d", nextBuildingCode++);
        this.buildingName = buildingName.trim();
    }

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
    // GETTERS
    public String getBuildingCode() {
        return buildingCode;
    }
    public String getBuildingName() {
        return buildingName;
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

    // DISPLAY METHODS
    public static void displayBuildings() {

        UI.header("BUILDINGS");

        if (buildings.isEmpty()) {
            UI.liner("  No buildings found.");
        } else {
            for (Building b : buildings) {
                UI.liner(String.format(
                        "  %-15s %-30s",
                        b.getBuildingCode(),
                        b.getBuildingName()
                ));
            }
        }

        UI.footer();

    }
    public static void displayArchiveBuildings() {

        UI.header("ARCHIVED BUILDINGS");

        if (archiveBuildings.isEmpty()) {
            UI.liner("  No archived buildings found.");
        } else {
            for (Building b : archiveBuildings) {
                UI.liner(String.format(
                        "  %-15s %-30s",
                        b.getBuildingCode(),
                        b.getBuildingName()
                ));
            }
        }

        UI.footer();

    }
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
                        "  %-15s %-35s %s",
                        r.getRoomCode(),
                        r.getRoomDescription(),
                        floorText
                ));

                count++;
            }
        }

        if (count == 0)
            UI.liner("  No rooms registered under this building.");
        else {
            UI.liner("");
            UI.liner("  TOTAL ROOMS: " + count);
        }
        UI.footer();
    }

    // MANAGE METHODS
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
                    buildings.add(new Building(buildingName));
                    UI.print("Building successfully created");

                    Building b = Building.findBuildingByName(buildingName);
                    Building.displayBuilding(b.getBuildingCode());
                    submit = true;
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

                        if (buildingName.isEmpty()) UI.print("Building name is required");
                        else if (Building.findBuildingByName(buildingName) != null && Building.findBuildingByName(buildingName) != b)
                                UI.print("Building name already exists");

                    } while (buildingName.isEmpty()
                            || (Building.findBuildingByName(buildingName) != null
                            && Building.findBuildingByName(buildingName) != b));
                    break;
                case "/":
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
    public static void archiveBuilding() {
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
            System.out.print("  Confirm Building Archive? (Y/N) > ");
            choice = in.nextLine().trim().toUpperCase();

            if (!choice.matches("[YN]"))
                UI.print("Invalid Input");

        } while (!choice.matches("[YN]"));

        if (choice.equals("Y")) {
            archiveBuildings.add(b);
            buildings.remove(b);
            UI.print("Building successfully archived");
        }
        else UI.print("Archive building cancelled");
    }
    private static void buildingManage(String text, String buildingCode, String buildingName) {

        UI.header(text.toUpperCase() + ": BUILDING INFORMATION");
        UI.liner(String.format("      BUILDING CODE : %s", buildingCode));
        UI.liner(String.format("  [1] BUILDING NAME : %s", buildingName));
        UI.liner("");

        UI.option();
        UI.footer();
    }
}