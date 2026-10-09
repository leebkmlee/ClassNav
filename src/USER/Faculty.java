package USER;
import DATA.*;
import UI.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Faculty extends User{
    private String contactNumber;
    public Faculty (String userID, String lastName, String firstName, String middleInitial, String emailAddress,
                    String password, String address, int postalCode, String birthDate, char gender, String contactNumber) {
        super(userID, lastName, firstName, middleInitial, emailAddress, password, address, postalCode, birthDate, gender);
        this.contactNumber = contactNumber;
    }

    Scanner in = new Scanner(System.in);

    public static ArrayList<String[]> requests = new ArrayList<>();
    static {
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
}
