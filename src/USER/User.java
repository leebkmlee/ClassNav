package USER;
import LOGIN.Login;
import java.util.Scanner;

public class User {
    private String userID; private String userName;
    private String emailAddress; private String password;
    private String address; private int postalCode;
    private String birthDate; private char gender;

    public User(String userID, String userName, String emailAddress, String password, String address, int postalCode, String birthDate, char gender) {
        this.userID = userID;
        this.userName = userName;
        this.emailAddress = emailAddress;
        this.password = password;
        this.address = address;
        this.postalCode = postalCode;
        this.birthDate = birthDate;
        this.gender = gender;
    }

    public String getUserID() {
        return userID;
    }

    public String getEmail() {
        return emailAddress;
    }

    public String getPassword() {
        return password;
    }

    static Scanner in = new Scanner(System.in);

    public static void start() {
        String select; boolean valid;

        do {
            System.out.println("╔══════════════════════════════════════════════╗");
            System.out.println("║                   ClassNav                   ║");
            System.out.println("╠══════════════════════════════════════════════╣");
            System.out.println("║ [1] Log in                                   ║");
            System.out.println("║ [2] Sign up                                  ║");
            System.out.println("║ [X] Exit                                     ║");
            System.out.println("╚══════════════════════════════════════════════╝");
            System.out.print(" Select > ");
            select = in.nextLine();
            if (select.equals("X") || select.equals("1") || select.equals("2")) valid = true;
            else {
                int innerWidth = 46;
                System.out.println("┌" + "─".repeat(innerWidth) + "┐");
                System.out.println("│               ✘ INVALID CHOICE               │");
                System.out.println("└" + "─".repeat(innerWidth) + "┘");
                valid = false;
            }
        } while (!valid);
        if (select.equals("X")) {
            System.out.println(" Exiting...");
            System.out.println("─────────────────────────────────────────────");
            System.exit(0);
        }

        switch (select) {
            case "1":
                Login.loginPassword();
                break;
            case "2":

        }
    }

//    public void start() {
//        int num;
//        num = selectUser();
//        switch (num) {
//            case 1:
//                Login.loginPassword("Student", "S001", "Stud123");
//                break;
//            case 2:
//                Login.loginPassword("Faculty", "F001", "Fac456");
//                break;
//            case 3:
//                Login.loginPassword("Admin", "A001", "Admin789");
//                break;
//            default:
//                System.out.println("Invalid choice!");
//        }
//
//    }

    int selectUser() {
        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║                   ClassNav                   ║");
        System.out.println("╠══════════════════════════════════════════════╣");
        char select; boolean valid;
        do {
            System.out.println("║ [1] Student                                  ║");
            System.out.println("║ [2] Faculty                                  ║");
            System.out.println("║ [3] Admin                                    ║");
            System.out.println("║ [X] Exit                                     ║");
            System.out.println("╚══════════════════════════════════════════════╝");
            System.out.print(" Select Role > ");
            select = in.next().charAt(0);
            if (select == 'X' || (select >= '1' && select <= '3')) valid = true;
            else {
                System.out.println(" INVALID CHOICE!");
                valid = false;
            }
        } while (!valid);
        if (select == 'X') {
            System.out.println(" Exiting...");
            System.out.println("─────────────────────────────────────────────");
            System.exit(0);
        }
        return select - '0';
    }

    public void displayInfo() {
        System.out.println("\n┌──────────────────────────────────────────────┐");
        System.out.println("│  ACCOUNT PROFILE                             │");
        System.out.println("├──────────────────────────────────────────────┤");
        System.out.printf("│  ID NUMBER   : %-29s │\n", userID);
        System.out.printf("│  FULL NAME   : %-29s │\n", userName);
        System.out.println("├──────────────────────────────────────────────┤");
        System.out.printf("│  Gender      : %-29s │\n", gender);
        System.out.printf("│  Birthdate   : %-29s │\n", birthDate);
        System.out.printf("│  Address     : %-29s │\n", address);
        System.out.println("└──────────────────────────────────────────────┘\n");
    }
    // showMenu()
    // setter and getter
    // displayInfo()
    // viewSchedule()
}
