package USER;
import LOGIN.*;
import UI.*;
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
    private char sex;

    public User(String userID, String lastName, String firstName, String middleInitial, String emailAddress,
                String password, String address, int postalCode, String birthDate, char sex) {

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
        this.sex = sex;
    }

    public String getUserID() {return userID;}
    public String getEmail() {return emailAddress;}
    public String getPassword() {return password;}
    public String getUserName() {return userName;}
    public String getAddress() {return address;}
    public int getPostalCode() {return postalCode;}
    public String getBirthDate() {return birthDate;}
    public char getSex() {return sex;}

    static Scanner in = new Scanner(System.in);

    static final int innerWidth = 70;

    public static void start() {

        String select;
        boolean valid;

        do {
            UI.startPrint("ClassNav");
            select = in.nextLine();
            if (select.equals("X") || select.equals("1") || select.equals("2")) valid = true;
            else {
                UI.print("Invalid Choice");
                valid = false;
            }
        } while (!valid);

        if (select.equals("X")) {
            System.out.println("   Exiting...");
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

//    public void displayInfo() {
//        System.out.println("\n┌" + "─".repeat(innerWidth) + "┐");
//        String title = "ACCOUNT PROFILE";
//        int left = (innerWidth - title.length()) / 2;
//        int right = innerWidth - title.length() - left;
//        System.out.println("│" + " ".repeat(left) + title + " ".repeat(right) + "│");
//        System.out.println("├" + "─".repeat(innerWidth) + "┤");
//        System.out.printf("│  ID NUMBER   : %-" + (innerWidth - 16) + "s│\n", userID);
//        System.out.printf("│  FULL NAME   : %-" + (innerWidth - 16) + "s│\n", userName);
//        System.out.println("├" + "─".repeat(innerWidth) + "┤");
//        System.out.printf("│  Gender      : %-" + (innerWidth - 16) + "s│\n", sex);
//        System.out.printf("│  Birthdate   : %-" + (innerWidth - 16) + "s│\n", birthDate);
//        System.out.printf("│  Address     : %-" + (innerWidth - 16) + "s│\n", address);
//        System.out.println("└" + "─".repeat(innerWidth) + "┘\n");
//    }
}