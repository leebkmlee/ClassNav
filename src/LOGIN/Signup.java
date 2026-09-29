package LOGIN;
import USER.User;
import java.util.Scanner;

public class Signup {

    static Scanner in = new Scanner(System.in);

    static final int innerWidth = 70;

    static String user = "[1] Full Name:";
    static String email = "[2] Email Address:";
    static String password = "[3] Password:";
    static String address = "[4] Address:";
    static String postalCode = "[5] Postal Code:";
    static String birthDate = "[6] Birthdate:";
    static String gender = "[7] Gender:";

    public static void signUp() {
        int complete = 0; boolean submit = false;

        while (!submit) {

            System.out.println("\n┌" + "─".repeat(innerWidth) + "┐");
            String title = "PORTAL SIGN UP";
            int left = (innerWidth - title.length()) / 2;
            int right = innerWidth - title.length() - left;
            System.out.println("│" + " ".repeat(left) + title + " ".repeat(right) + "│");

            System.out.println("├" + "─".repeat(innerWidth) + "┤");

            printUser();
            printEmail();
            printPassword();
            printAddress();
            printPostal();
            printBDate();
            printGender();

            System.out.println("│" + " ".repeat(innerWidth) + "│");
            String options = "[/] Submit     [X] Return";
            left = (innerWidth - options.length()) / 2;
            right = innerWidth - options.length() - left;
            System.out.println("│" + " ".repeat(left) + options + " ".repeat(right) + "│");
            System.out.println("└" + "─".repeat(innerWidth) + "┘");
            System.out.print("Select > ");
            String choice = in.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("  Enter Full Name (Last Name, First Name, M.I.)\n    > ");
                    String userName = in.nextLine();
                    complete++;
                    printUser(userName);
                    break;

                case "2":
                    System.out.print("  Enter Email Address\n    > ");
                    String emailAddress = in.nextLine();
                    complete++;
                    printEmail(emailAddress);
                    break;

                case "3":
                    System.out.print("  Enter Password\n    > ");
                    String passKey = in.nextLine();
                    complete++;
                    printPassword(passKey);
                    break;

                case "4":
                    System.out.print("  Enter Address (Street, Brgy, City, Province)\n    > ");
                    String location = in.nextLine();
                    complete++;
                    printAddress(location);
                    break;

                case "5":
                    System.out.print("  Enter Postal Code\n    > ");
                    String postalDigit = in.nextLine();
                    complete++;
                    printPostal(postalDigit);
                    break;

                case "6":
                    System.out.print("  Enter Birthdate (MM/DD/YYYY)\n    > ");
                    String BDate = in.nextLine();
                    complete++;
                    printBDate(BDate);
                    break;

                case "7":
                    System.out.print("  Enter Gender (M/F)\n    > ");
                    String gen = in.nextLine();
                    complete++;
                    printGender(gen);
                    break;

                case "/":
                    if (complete == 7) {
                        submit = true;

                        System.out.println("\n┌" + "─".repeat(innerWidth) + "┐");
                        String success = "SIGN UP SUCCESSFUL! Welcome to ClassNav.";
                        left = (innerWidth - success.length()) / 2;
                        right = innerWidth - success.length() - left;

                        System.out.println("│" + " ".repeat(left) + success + " ".repeat(right) + "│");

                        System.out.println("└" + "─".repeat(innerWidth) + "┘");
                    }
                    else {
                        System.out.println("\n┌" + "─".repeat(innerWidth) + "┐");
                        String incomplete = "Incomplete Details!";
                        left = (innerWidth - incomplete.length()) / 2;
                        right = innerWidth - incomplete.length() - left;
                        System.out.println("│" + " ".repeat(left) + incomplete + " ".repeat(right) + "│");
                        System.out.println("└" + "─".repeat(innerWidth) + "┘");
                    }
                    break;

                case "X":
                    user = "[1] Full Name:";
                    email = "[2] Email Address:";
                    password = "[3] Password:";
                    address = "[4] Address:";
                    postalCode = "[5] Postal Code:";
                    birthDate = "[6] Birthdate:";
                    gender = "[7] Gender:";
                    User.start();
                    break;
            }
        }
    }

    static void printUser() {
        System.out.printf("│ %-" + (innerWidth - 2) + "s │%n", user);
    }

    static void printEmail() {
        System.out.printf("│ %-" + (innerWidth - 2) + "s │%n", email);
    }

    static void printPassword() {
        System.out.printf("│ %-" + (innerWidth - 2) + "s │%n", password);
    }

    static void printAddress() {
        System.out.printf("│ %-" + (innerWidth - 2) + "s │%n", address);
    }

    static void printPostal() {
        System.out.printf("│ %-" + (innerWidth - 2) + "s │%n", postalCode);
    }

    static void printBDate() {
        System.out.printf("│ %-" + (innerWidth - 2) + "s │%n", birthDate);
    }

    static void printGender() {
        System.out.printf("│ %-" + (innerWidth - 2) + "s │%n", gender);
    }

    static void printUser(String userName) {
        user = "[1] Full Name: " + userName;
    }

    static void printEmail(String emailAddress) {
        email = "[2] Email Address: " + emailAddress;
    }

    static void printPassword(String passKey) {
        password = "[3] Password: " + passKey;
    }

    static void printAddress(String location) {
        address = "[4] Address: " + location;
    }

    static void printPostal(String postalDigit) {
        postalCode = "[5] Postal Code: " + postalDigit;
    }

    static void printBDate(String BDate) {
        birthDate = "[6] Birthdate: " + BDate;
    }

    static void printGender(String gen) {
        gender = "[7] Gender: " + gen;
    }
}
