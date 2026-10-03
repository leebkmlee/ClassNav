package USER;

import LOGIN.Login;
import LOGIN.Signup;

import java.util.Scanner;

public class User {

    private String userID;
    private String userName;
    private String lastName, firstName, middleInitial;
    private String emailAddress;
    private String password;
    private String address;
    private int postalCode;
    private String birthDate;
    private char gender;

    public User(String userID, String lastName, String firstName, String middleInitial, String emailAddress,
                String password, String address, int postalCode, String birthDate, char gender) {

        this.userID = userID;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleInitial = middleInitial;
        if (!middleInitial.isEmpty()) middleInitial += ".";
        userName = lastName + ", " + firstName + " " + middleInitial;
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

    public String getUserName() {
        return userName;
    }

    static Scanner in = new Scanner(System.in);

    static final int innerWidth = 70;

    public static void start() {

        String select;
        boolean valid;

        do {
            System.out.println("\n┌" + "─".repeat(innerWidth) + "┐");
            String title = "ClassNav";
            int left = (innerWidth - title.length()) / 2;
            int right = innerWidth - title.length() - left;
            System.out.println("│" + " ".repeat(left) + title + " ".repeat(right) + "│");
            System.out.println("├" + "─".repeat(innerWidth) + "┤");
            System.out.printf("│%-" + innerWidth + "s│%n", " [1] Log in");
            System.out.printf("│%-" + innerWidth + "s│%n", " [2] Sign up");
            System.out.printf("│%-" + innerWidth + "s│%n", " [X] Exit");
            System.out.println("└" + "─".repeat(innerWidth) + "┘");
            System.out.print("  Select > ");
            select = in.nextLine();
            if (select.equals("X") || select.equals("1") || select.equals("2")) valid = true;
            else {
                String message = "INVALID CHOICE";
                left = (innerWidth - message.length()) / 2;
                right = innerWidth - message.length() - left;
                System.out.println("\n┌" + "─".repeat(innerWidth) + "┐");
                System.out.println("│" + " ".repeat(left) + message + " ".repeat(right) + "│");
                System.out.println("└" + "─".repeat(innerWidth) + "┘");
                valid = false;
            }
        } while (!valid);
        if (select.equals("X")) {
            System.out.println("  Exiting...");
            System.out.println("─".repeat(innerWidth));
            System.exit(0);
        }

        switch (select) {
            case "1":
                Login.loginPassword();
                break;
            case "2":
                Signup.signUp();
                break;
        }
    }

    public void displayInfo() {
        System.out.println("\n┌" + "─".repeat(innerWidth) + "┐");
        String title = "ACCOUNT PROFILE";
        int left = (innerWidth - title.length()) / 2;
        int right = innerWidth - title.length() - left;
        System.out.println("│" + " ".repeat(left) + title + " ".repeat(right) + "│");
        System.out.println("├" + "─".repeat(innerWidth) + "┤");
        System.out.printf("│  ID NUMBER   : %-" + (innerWidth - 16) + "s│\n", userID);
        System.out.printf("│  FULL NAME   : %-" + (innerWidth - 16) + "s│\n", userName);
        System.out.println("├" + "─".repeat(innerWidth) + "┤");
        System.out.printf("│  Gender      : %-" + (innerWidth - 16) + "s│\n", gender);
        System.out.printf("│  Birthdate   : %-" + (innerWidth - 16) + "s│\n", birthDate);
        System.out.printf("│  Address     : %-" + (innerWidth - 16) + "s│\n", address);
        System.out.println("└" + "─".repeat(innerWidth) + "┘\n");
    }
}