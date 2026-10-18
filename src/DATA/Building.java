package DATA;

import java.util.ArrayList;
import UI.*;
import static DATA.Room.rooms;

public class Building {
    private static int nextBuildingCode = 1;
    private String buildingCode;
    private String buildingName;

    public Building(String buildingName) {
        this.buildingCode = String.format("BLD%02d", nextBuildingCode++);
        this.buildingName = buildingName;
    }

    public static ArrayList<Building> buildings = new ArrayList<>();

    static {
        buildings.add(new Building("Pimentel Hall"));
        buildings.add(new Building("NSTP Building"));
        buildings.add(new Building("Federizo Hall"));
        buildings.add(new Building("Natividad Hall"));
        buildings.add(new Building("Roxas Hall"));
    }

    // BUILDING GETTER METHODS
    public String getBuildingCode() {
        return buildingCode;
    }
    public static Building findBuilding(String buildingCode) {
        for (Building b : buildings) if (buildingCode.equals(b.getBuildingCode())) return b;
        return null;
    }
    public static String getBuilding(Building b) {
        return b.buildingCode;
    }
    public static String getBuildingName(Building b) {
        return b.buildingName;
    }
    public static boolean verifyBuilding(String buildingCode) {
        for (Building b : buildings) if (buildingCode.equals(b.getBuildingCode())) return true;
        return false;
    }

    // BUILDING METHODS
    public static void displayBuilding(String buildingCode) {
        Building b = findBuilding(buildingCode);
        if (b == null) {
            UI.print("Building not found");
            return;
        }
        UI.header("BUILDING DETAILS");
        UI.liner(String.format("  BUILDING CODE  : %s", Building.getBuilding(b)));
        UI.liner(String.format("  BUILDING NAME  : %s", Building.getBuildingName(b)));
        UI.footer();
    }
    public static void displayRooms(String buildingCode) {
        Building b = findBuilding(buildingCode);
        if (b == null) {
            UI.print("Building not found");
            return;
        }
        UI.header("ROOM LIST");
        UI.liner(String.format("  BUILDING CODE     : %s", Building.getBuilding(b)));
        UI.liner("  Rooms under " + Building.getBuildingName(b) + ":");
        for (Room r : rooms) {
            if (Room.getBuildingCode(r).equals(b.getBuildingCode())) {
                int floor = Room.getFloorNumber(r);
                String floorText;
                if (floor == 1) floorText = "1st Floor";
                else if (floor == 2) floorText = "2nd Floor";
                else if (floor == 3) floorText = "3rd Floor";
                else floorText = floor + "th Floor";
                String room = String.format("    %-10s %-40s %s", Room.getRoom(r), Room.getRoomDescription(r), floorText);
                UI.liner(room);
            }
        }
        UI.footer();
    }
}