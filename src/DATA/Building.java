package DATA;

import java.util.ArrayList;
import UI.*;
import static DATA.Room.rooms;

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

    // FIND METHODS

    // Find by primary key: BUILDING.buildingCode
    public static Building findBuilding(String buildingCode) {

        if (buildingCode == null)
            return null;

        for (Building b : buildings) {
            if (buildingCode.equalsIgnoreCase(b.getBuildingCode()))
                return b;
        }

        return null;
    }

    // Find by name; useful for preventing duplicate building names
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

    // DISPLAY ROOMS BY BUILDING PRIMARY KEY

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

            // ROOM.buildingCode (FK) -> BUILDING.buildingCode (PK)
            if (b.getBuildingCode().equalsIgnoreCase(Room.getBuildingCode(r))) {

                int floor = Room.getFloorNumber(r);
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
                        Room.getRoom(r),
                        Room.getRoomDescription(r),
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
}