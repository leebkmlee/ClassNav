package USER;
import DATA.*;
import UI.*;

import java.util.ArrayList;
import java.util.Scanner;

import DATA.*;
import UI.*;

import static DATA.Notification.*;

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
        Building b = Building.findBuilding(Room.getBuilding(r));
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

        public static ArrayList<User> users = new ArrayList<>();
    public static ArrayList<String[]> requests = new ArrayList<>();

    // add notificationID
    static {
        requests.add(new String[]{"S001", "F001", "R101", "PM1", "Monday", "08:00 AM", "10:00 AM",
                "Request for Room R101 in PM1 has been submitted."});
        requests.add(new String[]{"S002", "F002", "R202", "R1",
                "Tuesday", "10:00 AM", "12:00 PM", "Request for Room R202 in R1 has been submitted."});
        requests.add(new String[]{"S003", "F003", "R303", "CB2",
                "Wednesday", "01:00 PM", "03:00 PM", "Request for Room R303 in CB2 has been submitted."});
        requests.add(new String[]{"S004", "F001", "R102", "PM1",
                "Thursday", "08:00 AM", "11:00 AM", "Request for Room R102 in PM1 has been submitted."});
        requests.add(new String[]{"S005", "F002", "R201", "R1",
                "Friday", "02:00 PM", "04:00 PM", "Request for Room R201 in R1 has been submitted."});
    }

    public void requestRoom() {
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
        if (choice == 'Y') {
            requests.add(new String[]{
                    buildingCode, roomCode, day, startTime, endTime
            });
        } else {
            requestRoom();
        }
    }

    public void viewNotification(String facultyID){
        ArrayList<Notification> notifications = Notification.notifications;
        for (User u : users) {
            if(facultyID.equals(u.getUserID())){
                for (Notification n : notifications){
                    UI.header("CHECK NOTIFICATION");
                    UI.liner("Notification ID: " + getNotificationID());
                    UI.liner("From: " + n.getSenderID());
                    UI.liner("To: " + n.getRecipientID());
                    UI.liner("Room Code: " +  n.getRoomCode());
                    UI.liner("Building Name: " + n.getBuildingName());
                    UI.liner("Day: " + n.getDay());
                    UI.liner("Time: " +  n.getStartTime() + " -  " +   n.getEndTime());
                    UI.liner("Message: " + n.getMessage());
                    UI.footer();
                    UI.liner(" ");
                    break;
                }
                int choice;
                boolean found = false;
                do {

                    System.out.print("Select Student Request to approve > ");
                    choice = in.nextInt(); in.nextLine();
                    if(choice == getNotificationID()) {
                        for (Notification n : notifications) {

                            requests.add(new String[]{});break;
                        }
                        found = true;

                    } else System.out.println("Invalid choice");
                }while (!found);




                System.out.print(" [0] Back");
            } else  {
                System.out.println("Faculty not found");
                break;
            }
        }
    }
}
