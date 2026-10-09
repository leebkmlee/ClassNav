package DATA;

import java.util.ArrayList;
import UI.*;
import static DATA.Room.rooms;

public class Building {
    private String buildingCode;
    private String buildingName;

    public Building(String buildingCode, String buildingName) {
        this.buildingCode = buildingCode;
        this.buildingName = buildingName;
    }

    public static ArrayList<Building> buildings = new ArrayList<>();

    static {
        buildings.add(new Building("BLD01", "Pimentel Hall"));
        buildings.add(new Building("BLD02", "NSTP Building"));
        buildings.add(new Building("BLD03", "Federizo Hall"));
        buildings.add(new Building("BLD04", "Natividad Hall"));
    }

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

    public static void displayBuilding(String buildingCode) {
        final int innerWidth = 70;
        Building bldg = findBuilding(buildingCode);
        if (bldg == null) {
            UI.print("Building not found");
            return;
        }
        UI.header("BUILDING DETAILS");
        System.out.printf("│  BUILDING CODE   : %-" + (innerWidth - 20) + "s│\n", Building.getBuilding(bldg));
        System.out.printf("│  BUILDING NAME   : %-" + (innerWidth - 20) + "s│\n", Building.getBuildingName(bldg));
        UI.footer();
    }

    public static void displayRooms(String buildingCode) {
        final int innerWidth = 70;
        Building b = findBuilding(buildingCode);
        if (b == null) {
            UI.print("Building not found");
            return;
        }
        UI.header("ROOM LIST");
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("  BUILDING CODE     : %s", Building.getBuilding(b)));
        System.out.printf("│%-" + innerWidth + "s│\n", "  Rooms under " + Building.getBuildingName(b) + ":");
        for (Room r : rooms) {
            if (Room.getBuilding(r).equals(b.getBuildingCode())) {
                int floor = Room.getFloorNumber(r);
                String floorText;
                if (floor == 1) floorText = "1st Floor";
                else if (floor == 2) floorText = "2nd Floor";
                else if (floor == 3) floorText = "3rd Floor";
                else floorText = floor + "th Floor";
                String room = String.format("    %-10s %-40s %s", Room.getRoom(r), Room.getRoomDescription(r), floorText);
                System.out.printf("│%-" + innerWidth + "s│\n", room);
            }
        }
        UI.footer();
    }
}