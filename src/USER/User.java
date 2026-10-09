package USER;
import LOGIN.*;
import UI.*;

import java.util.ArrayList;
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

    public static ArrayList<User> users = new ArrayList<>();
    static {
        users.add(new Student("S001", "Sabulao", "Josh", "P",
                "S001@bulsu.edu.ph", "2025004151",
                "CSJDM, Bulacan", 3023, "06/03/07", 'M', "Enrolled"));

        users.add(new Faculty("F001", "Kim", "Elijah", "",
                "S002@bulsu.edu.ph", "elijahmasikip",
                "Malolos, Bulacan", 3000, "04/20/07", 'M',
                "09293268930"));

        users.add(new Admin("A001", "Maangas", "Andrei", "P",
                "A001@bulsu.edu.ph", "4ndr31b4tumb4k4l",
                "Guiguinto, Bulacan", 6767, "09/17/07", 'M',
                "Computer Programmer I"));
    }

    public static User getUser(String userID) {
        for (User u : users) if (userID.equals(u.getUserID())) return u;
        return null;
    }

    public static boolean verifyEmail(String userID, String emailAddress) {
        for (User u : users) if (userID.equals(u.getUserID()) && emailAddress.equals(u.getEmail())) return true;
        return false;
    }

    public static boolean verifyPassword(String userID, String password) {
        for (User u : users) if (userID.equals(u.getUserID()) && password.equals(u.getPassword())) return true;
        return false;
    }

    public static boolean checkDuplicateName(String userName) {
        for (User u : users) if (userName.equals(u.getUserName())) return true;
        return false;
    }

    public static boolean checkDuplicateEmail(String emailAddress) {
        for (User u : users) if (emailAddress.equals(u.getEmail())) return true;
        return false;
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
}