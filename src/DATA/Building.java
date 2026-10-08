package DATA;

import java.util.ArrayList;
import java.util.Scanner;


public class Building {
    Scanner sc = new Scanner(System.in);
    private String buildingCode;
    private String buildingName;

    public Building(String buildingCode, String buildingName) {
        this.buildingCode = buildingCode;
        this.buildingName = buildingName;
    }


    public static ArrayList<Building> buildings = new ArrayList<>();

    public Building() {
        buildings.add(new Building("BLD01", "PIMENTEL HALL"));
        buildings.add(new Building("BLD02", "NSTP BUILDING"));
        buildings.add(new Building("BLD03", "FEDERIZO HALL"));
        buildings.add(new Building("BLD04", "NATIVIDAD HALL"));


    }

    public String getBuildingCode() {
        return buildingCode;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void displayBuilding() {
        final int innerWidth = 70;

        System.out.println("\n┌" + "─".repeat(innerWidth) + "┐");

        String title = "DISPLAY BUILDING";
        int left = (innerWidth - title.length()) / 2;
        int right = innerWidth - title.length() - left;

        System.out.println("│" + " ".repeat(left) + title + " ".repeat(right) + "│");
        System.out.println("├" + "─".repeat(innerWidth) + "┤");

        for (Building b : buildings) {
            System.out.printf("│  BUILDING CODE   : %-" + (innerWidth - 20) + "s│\n", b.getBuildingCode());
            System.out.printf("│  BUILDING NAME   : %-" + (innerWidth - 20) + "s│\n", b.getBuildingName());
            System.out.println("├" + "─".repeat(innerWidth) + "┤");
        }
        System.out.printf("│  ENTER BUILDING CODE   : %-" + (innerWidth - 20) + "s│\n");
        String choice = sc.nextLine().trim();
        System.out.println("└" + "─".repeat(innerWidth) + "┘\n");
        displayRoom(choice);
    }

    public void displayRoom(String choice) {
        Building ChosenBuilding = null;
        final int innerWidth = 70;

        for (Building b : buildings) {
            if (b.getBuildingCode().equalsIgnoreCase(choice)) {
                ChosenBuilding = b;
            }
            if (ChosenBuilding == null) {
                System.out.println("ROOM NOT FOUND OR INVALID CODE");
            }
        }

        System.out.println("\n┌" + "─".repeat(innerWidth) + "┐");
        String title = "ROOMS IN " + ChosenBuilding.getBuildingName();
        int left = (innerWidth - title.length()) / 2;
        int right = innerWidth - title.length() - left;
        System.out.println("│" + " ".repeat(left) + title + " ".repeat(right) + "│");
        System.out.println("├" + "─".repeat(innerWidth) + "┤");

        for (Room r : Room.rooms) {
            if (r.getBuildingCode().equalsIgnoreCase(choice)) {
                System.out.printf("│  ROOM CODE   : %-" + (innerWidth - 20) + "s│\n", r.getRoomCode());
                System.out.printf("│  ROOM NAME   : %-" + (innerWidth - 20) + "s│\n", r.getRoomName());
                System.out.println("├" + "─".repeat(innerWidth) + "┤");
            }
        }
        System.out.println("└" + "─".repeat(innerWidth) + "┘\n");
    }
}