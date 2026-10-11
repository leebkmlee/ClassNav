package USER;

import DATA.*;
import UI.*;

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
}
