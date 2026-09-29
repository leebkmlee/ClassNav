package LOGIN;

import java.util.Scanner;

public class Signup {
//    private String userID; private String userName;
//    private String emailAddress; private String password;
//    private String address; private int postalCode;
//    private String birthDate; private char gender;

    static Scanner in = new Scanner(System.in);

    static String user = "│ [1] Username:                                │";
    static String email = "│ [2] Email Address:                           │";
    static String password = "│ [3] Password:                                │";
    static String address = "│ [4] Address:                                 │";
    static String postalCode = "│ [5] Postal Code:                             │";
    static String birthDate = "│ [6] Birthdate:                               │";
    static String gender = "│ [7] Gender:                                  │";

    public static void signUp() {
        int complete = 0;

        while (complete != 7) {
            System.out.println("\n┌──────────────────────────────────────────────┐");
            System.out.println("│                PORTAL SIGN UP                │");
            System.out.println("├──────────────────────────────────────────────┤");
            printUser();
            printEmail();
            printPassword();
            printAddress();
            printPostal();
            printBDate();
            printGender();
            System.out.println("│ [X] Exit:                                    │");
            System.out.println("└──────────────────────────────────────────────┘\n");

            System.out.print("Select > ");
            String choice = in.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("  Enter Username\t > ");
                    String userName = in.nextLine();
                    complete++;
                    printUser(userName);
                    break;
                case "2":
                    System.out.print("  Enter Email Address\t > ");
                    String emailAddress = in.nextLine();
                    complete++;
                    printEmail(emailAddress);
                    break;
                case "3":
                    System.out.print("  Enter Password\t > ");
                    String passKey = in.nextLine();
                    complete++;
                    printPassword(passKey);
                    break;
                case "4":
                    System.out.print("  Enter Address\t > ");
                    String location = in.nextLine();
                    complete++;
                    printAddress(location);
                    break;
                case "5":
                    System.out.print("  Enter Postal Code\t > ");
                    String postalDigit = in.nextLine();
                    complete++;
                    printPostal(postalDigit);
                    break;
                case "6":
                    System.out.print("  Enter Birthdate\t > ");
                    String BDate = in.nextLine();
                    complete++;
                    printBDate(BDate);
                    break;
                case "7":
                    System.out.print("  Enter Gender\t > ");
                    String gen = in.nextLine();
                    printGender(gen);
                    break;
            }
        }
    }

    static void printUser() {
        System.out.println(user);
    }

    static void printUser(String userName) {
        user = "│ [1] Username: " + userName + " │";
    }

    static void printEmail() {
        System.out.println(email);
    }

    static void printEmail(String emailAddress) {
        email = "│ [2] Email Address: " + emailAddress + " │";
    }

    static void printPassword() {
        System.out.println(password);
    }

    static void printPassword(String passKey) {
        email = "│ [3] Password: " + passKey + " │";
    }

    static void printAddress() {
        System.out.println(address);
    }

    static void printAddress(String location) {
        address = "│ [4] Address: " + location + " │";
    }

    static void printPostal () {
        System.out.println(postalCode);
    }

    static void printPostal (String postalDigit) {
        postalCode = "│ [5] Postal Code: " + postalDigit + " │";
    }

    static void printBDate() {
        System.out.println(birthDate);
    }

    static void printBDate(String BDate) {
        birthDate = "│ [6] Birthdate: " + BDate + " │";
    }

    static void printGender() {
        System.out.println(gender);
    }

    static  void printGender(String gen) {
        gender = "│ [7] Gender: " + gen + " │";
    }


}
