package USER;
import DATA.*;
import UI.*;

import java.util.ArrayList;
import java.util.Scanner;

import DATA.*;
import UI.*;

import static DATA.Room.convertToMinutes;

public class Faculty extends User{
    private String contactNumber;
    private String collegeCode;
    public Faculty (String userID, String lastName, String firstName, String middleInitial, String emailAddress,
                    String password, String address, int postalCode, String birthDate, char gender, String contactNumber,
                    String collegeCode) {
        super(userID, lastName, firstName, middleInitial, emailAddress, password, address, postalCode, birthDate, gender);
        this.contactNumber = contactNumber;
        this.collegeCode = collegeCode;
    }

    public static void locateRoom(String roomCode) {
        Room r = Room.findRoom(roomCode);
        if (r == null) {
            UI.print("Room not found");
            return;
        }
        Building b = Building.findBuilding(Room.getBuildingCode(r));
        if (b == null) {
            UI.print("Building not found");
            return;
        }
        UI.header("ROOM INFORMATION");
        System.out.printf("│%-" + innerWidth + "s│\n", "  ROOM DETAILS");
        UI.separator();
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("  ROOM CODE         : %s", Room.getRoom(r)));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("  ROOM DESCRIPTION  : %s", Room.getRoomDescription(r)));
        UI.separator();
        System.out.printf("│%-" + innerWidth + "s│\n", "  ROOM LOCATION");
        UI.separator();
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("  BUILDING NAME     : %s", Building.getBuildingName(b)));
        int floor = Room.getFloorNumber(r);
        String floorText;
        if (floor == 1) floorText = "1st Floor";
        else if (floor == 2) floorText = "2nd Floor";
        else if (floor == 3) floorText = "3rd Floor";
        else floorText = floor + "th Floor";
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("  FLOOR NUMBER      : %s", floorText));
        UI.footer();
    }

    Scanner in = new Scanner(System.in);

    public static ArrayList<String[]> requests = new ArrayList<>();
    static {
    }

    public static void viewAvailableRooms( String day, String startTime, String endTime){
        int scheduledStart = convertToMinutes(startTime);
        int scheduledEnd = convertToMinutes(endTime);
        UI.header("AVAILABLE ROOMS");
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("  DAY          : %s", day));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("  START TIME   : %s", startTime));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("  END TIME     : %s", endTime));
        UI.separator();
        boolean found = false;

        for (Room r : Room.rooms) {
            String roomCode = Room.getRoom(r);
            boolean available = true;

            for (Schedule s : Schedule.schedules) {
                if (roomCode.equals(Schedule.getRoomCode(s)) && day.equalsIgnoreCase(Schedule.getDay(s))) {
                    int existingStart = convertToMinutes(Schedule.getStartTime(s));
                    int existingEnd = convertToMinutes(Schedule.getEndTime(s));
                    if (scheduledStart < existingEnd && scheduledEnd > existingStart) {
                        available = false;
                        break;
                    }
                }
            }
            if (available) {
                found = true;
                System.out.printf("│%-" + innerWidth + "s│%n", String.format("  ROOM CODE    : %s", roomCode));
            }
        }
        if (!found) {
            System.out.printf("│%-" + innerWidth + "s│%n", "  NO AVAILABLE ROOMS FOUND");
        }
        UI.footer();
    }

    public void requestRoom(){
        String buildingCode, roomCode, day, startTime, endTime;
        UI.header("REQUEST ROOM");
        UI.footer();
        System.out.print("Building Code: ");
        buildingCode = in.nextLine();
        System.out.print("Room Code: ");
        roomCode = in.nextLine();
        System.out.print("Day: ");
        day = in.nextLine();
        System.out.print("Start Time: ");
        startTime = in.nextLine();
        System.out.print("End Time: ");
        endTime = in.nextLine();

        UI.header("REQUEST ROOM");
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("  Building Code : %s", buildingCode));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("  Room Code     : %s", roomCode));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("  Day           : %s", day));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("  Start Time    : %s", startTime));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("  End Time      : %s", endTime));
        UI.footer();
        System.out.print("  Request (Y/N) > ");
        char choice = in.next().toUpperCase().charAt(0);
        if (choice == 'Y'){
            requests.add(new String[]{
                    buildingCode, roomCode, day, startTime, endTime
            });
        }
        else{
            requestRoom();
        }
    }

    public String getContactNumber() { return contactNumber;
    }

    public String getCollegeCode() { return collegeCode;
    }

    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber;
    }

    public void setCollegeCode(String collegeCode) { this.collegeCode = collegeCode;
    }
}
