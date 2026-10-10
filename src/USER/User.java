package USER;
import DATA.Schedule;
import LOGIN.*;
import UI.*;
import java.util.*;

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

    String getUserID() {return userID;}
    public String getEmail() {return emailAddress;}
    public String getPassword() {return password;}
    public String getUserName() {return userName;}
    public String getAddress() {return address;}
    public int getPostalCode() {return postalCode;}
    public String getBirthDate() {return birthDate;}
    public char getSex() {return sex;}

    static Scanner in = new Scanner(System.in);

    static final int innerWidth = 70;

    public static void updateProfile(String userID){
        int select;
        do {
            UI.header("UPDATE PROFILE");
            System.out.printf("│%-" + innerWidth + "s│%n", " [1] Name");
            System.out.printf("│%-" + innerWidth + "s│%n", " [2] Email Address");
            System.out.printf("│%-" + innerWidth + "s│%n", " [3] Password");
            System.out.printf("│%-" + innerWidth + "s│%n", " [4] Address");
            System.out.printf("│%-" + innerWidth + "s│%n", " [5] Postal Code");
            System.out.printf("│%-" + innerWidth + "s│%n", " [6] Birthday");
            System.out.printf("│%-" + innerWidth + "s│%n", " [7] Sex");
            System.out.printf("│%-" + innerWidth + "s│%n", " [0] Back");
            UI.footer();
            System.out.print("  Select > ");
            if (in.hasNextInt()) {
                select = in.nextInt();
            } else {
                System.out.println("Invalid input. Please enter a number.");
                in.nextLine();
                select = -1;
            }

            switch (select) {
                case 1:
                    for (User u : users) {
                        if (userID.equals(u.getUserID())) {
                            do {
                                UI.header("UPDATE NAME");
                                System.out.printf("│%-" + innerWidth + "s│\n", String.format("  [1] First Name      : %s", u.firstName));
                                System.out.printf("│%-" + innerWidth + "s│\n", String.format("  [2] Middle Initial  : %s", u.middleInitial));
                                System.out.printf("│%-" + innerWidth + "s│\n", String.format("  [3] Last Name       : %s", u.lastName));
                                System.out.printf("│%-" + innerWidth + "s│%n", "  [0] Back");
                                UI.footer();
                                System.out.print("  Select > ");
                                if (in.hasNextInt()) {
                                    select = in.nextInt();
                                } else {
                                    System.out.println("Invalid input. Please enter a number.");
                                    in.nextLine();
                                    select = -1;
                                }
                                switch (select) {
                                    case 1:
                                        System.out.print("New First Name: ");
                                        in.nextLine();
                                        u.firstName = in.nextLine();
                                        if (!u.middleInitial.isEmpty()) {
                                            u.userName = u.lastName + ", " + u.firstName + " " + u.middleInitial + ".";
                                        } else {
                                            u.userName = u.lastName + ", " + u.firstName;
                                        }
                                        System.out.println("First name updated successfully!");
                                        break;
                                    case 2:
                                        System.out.println("Press Enter to Remove Middle Initial");
                                        System.out.print("New Middle Initial: ");
                                        in.nextLine();
                                        u.middleInitial = in.nextLine();
                                        if (!u.middleInitial.isEmpty()) {
                                            u.userName = u.lastName + ", " + u.firstName + " " + u.middleInitial + ".";
                                        } else {
                                            u.userName = u.lastName + ", " + u.firstName;
                                        }
                                        System.out.println("Middle Initial updated successfully!");
                                        break;
                                    case 3:
                                        System.out.print("New Last Name: ");
                                        in.nextLine();
                                        u.lastName = in.nextLine();
                                        if (!u.middleInitial.isEmpty()) {
                                            u.userName = u.lastName + ", " + u.firstName + " " + u.middleInitial + ".";
                                        } else {
                                            u.userName = u.lastName + ", " + u.firstName;
                                        }
                                        System.out.println("Last name updated successfully!");
                                        System.out.println(u.getUserName());
                                        break;
                                    case 0:
                                        updateProfile(userID);
                                        break;
                                    default:
                                        System.out.println("Invalid input. Please Try again.");
                                        break;
                                }
                            } while (select != 0);
                        }
                    }
                    break;
                case 2:
                    for (User u : users) {
                        if (userID.equals(u.getUserID())) {
                            do {
                                UI.header("UPDATE EMAIL ADDRESS");
                                System.out.printf("│%-" + innerWidth + "s│\n", String.format("  [1] Email Address : %s", u.emailAddress));
                                System.out.printf("│%-" + innerWidth + "s│%n", "  [0] Back");
                                UI.footer();
                                System.out.print("  Select > ");
                                if (in.hasNextInt()) {
                                    select = in.nextInt();
                                } else {
                                    System.out.println("Invalid input. Please enter a number.");
                                    in.nextLine();
                                    select = -1;
                                }
                                switch (select) {
                                    case 1:
                                        System.out.print("New Email Address: ");
                                        in.nextLine();
                                        u.emailAddress = in.nextLine();
                                        System.out.println("Email Address updated successfully!");
                                        break;
                                    case 0:
                                        updateProfile(userID);
                                        break;
                                    default:
                                        System.out.println("Invalid input. Please Try again.");
                                        break;
                                }
                            } while (select != 0);
                        }
                    }
                    break;
                case 3:
                    for (User u : users) {
                        if (userID.equals(u.getUserID())) {
                            do {
                                UI.header("UPDATE PASSWORD");
                                System.out.printf("│%-" + innerWidth + "s│\n", String.format("  [1] Password : %s", u.password));
                                System.out.printf("│%-" + innerWidth + "s│%n", "  [0] Back");
                                UI.footer();
                                System.out.print("  Select > ");
                                if (in.hasNextInt()) {
                                    select = in.nextInt();
                                } else {
                                    System.out.println("Invalid input. Please enter a number.");
                                    in.nextLine();
                                    select = -1;
                                }
                                switch (select) {
                                    case 1:
                                        System.out.print("New Password: ");
                                        in.nextLine();
                                        u.password = in.nextLine();
                                        System.out.println("Password updated successfully!");
                                        break;
                                    case 0:
                                        updateProfile(userID);
                                        break;
                                    default:
                                        System.out.println("Invalid input. Please Try again.");
                                        break;
                                }
                            } while (select != 0);
                        }
                    }
                    break;
                case 4:
                    for (User u : users) {
                        if (userID.equals(u.getUserID())) {
                            do {
                                UI.header("UPDATE ADDRESS");
                                System.out.printf("│%-" + innerWidth + "s│\n", String.format("  [1] Address : %s", u.address));
                                System.out.printf("│%-" + innerWidth + "s│%n", "  [0] Back");
                                UI.footer();
                                System.out.print("  Select > ");
                                if (in.hasNextInt()) {
                                    select = in.nextInt();
                                } else {
                                    System.out.println("Invalid input. Please enter a number.");
                                    in.nextLine();
                                    select = -1;
                                }
                                switch (select) {
                                    case 1:
                                        System.out.print("New Address: ");
                                        in.nextLine();
                                        u.address = in.nextLine();
                                        System.out.println("New Address updated successfully!");
                                        break;
                                    case 0:
                                        updateProfile(userID);
                                        break;
                                    default:
                                        System.out.println("Invalid input. Please Try again.");
                                        break;
                                }
                            } while (select != 0);
                        }
                    }
                    break;
                case 5:
                    for (User u : users) {
                        if (userID.equals(u.getUserID())) {
                            do {
                                UI.header("UPDATE POSTAL CODE");
                                System.out.printf("│%-" + innerWidth + "s│\n", String.format("  [1] Postal Code : %s", u.postalCode));
                                System.out.printf("│%-" + innerWidth + "s│%n", "  [0] Back");
                                UI.footer();
                                System.out.print("  Select > ");
                                if (in.hasNextInt()) {
                                    select = in.nextInt();
                                } else {
                                    System.out.println("Invalid input. Please enter a number.");
                                    in.nextLine();
                                    select = -1;
                                }
                                switch (select) {
                                    case 1:
                                        System.out.print("New Postal Code: ");
                                        in.nextLine();
                                        u.postalCode = in.nextInt();
                                        System.out.println("Postal Code updated successfully!");
                                        break;
                                    case 0:
                                        updateProfile(userID);
                                        break;
                                    default:
                                        System.out.println("Invalid input. Please Try again.");
                                        break;
                                }
                            } while (select != 0);
                        }
                    }
                    break;
                case 6:
                    for (User u : users) {
                        if (userID.equals(u.getUserID())) {
                            do {
                                UI.header("UPDATE BIRTHDAY");
                                System.out.printf("│%-" + innerWidth + "s│\n", String.format("  [1] Birthdate : %s", u.birthDate));
                                System.out.printf("│%-" + innerWidth + "s│%n", "  [0] Back");
                                UI.footer();
                                System.out.print("  Select > ");
                                if (in.hasNextInt()) {
                                    select = in.nextInt();
                                } else {
                                    System.out.println("Invalid input. Please enter a number.");
                                    in.nextLine();
                                    select = -1;
                                }
                                switch (select) {
                                    case 1:
                                        System.out.print("New Birthdate: ");
                                        in.nextLine();
                                        u.birthDate = in.nextLine();
                                        System.out.println("Birthdate updated successfully!");
                                        break;
                                    case 0:
                                        updateProfile(userID);
                                        break;
                                    default:
                                        System.out.println("Invalid input. Please Try again.");
                                        break;
                                }
                            } while (select != 0);
                        }
                    }
                    break;
                case 7:
                    for (User u : users) {
                        if (userID.equals(u.getUserID())) {
                            do {
                                UI.header("UPDATE SEX");
                                System.out.printf("│%-" + innerWidth + "s│\n", String.format("  [1] Sex : %s", u.sex));
                                System.out.printf("│%-" + innerWidth + "s│%n", "  [0] Back");
                                UI.footer();
                                System.out.print("  Select > ");
                                if (in.hasNextInt()) {
                                    select = in.nextInt();
                                } else {
                                    System.out.println("Invalid input. Please enter a number.");
                                    in.nextLine();
                                    select = -1;
                                }
                                switch (select) {
                                    case 1:
                                        System.out.print("New Sex (M/F): ");
                                        in.nextLine();
                                        u.sex = in.next().charAt(0);
                                        System.out.println("Sex updated successfully!");
                                        break;
                                    case 0:
                                        updateProfile(userID);
                                        break;
                                    default:
                                        System.out.println("Invalid input. Please Try again.");
                                        break;
                                }
                            } while (select != 0);
                        }
                    }
                    break;
                case 0:
                    //viewProfile();
                    break;
                default:
                    System.out.println("Invalid input. Please Try again.");
                    updateProfile(userID);
                    break;
            }
        }while (select != 0);
    }

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