package LOGIN;
import DATA.RetrieveUser;
import USER.Admin;
import USER.Faculty;
import USER.Student;
import USER.User;
import UI.*;
import java.util.Scanner;

public class Signup {

    static final int innerWidth = 70;

    static Scanner in = new Scanner(System.in);

    static String user = "[1] Full Name:";
    static String email = "[2] Email Address:";
    static String password = "[3] Password:";
    static String address = "[4] Address:";
    static String postalCode = "[5] Postal Code:";
    static String birthDate = "[6] Birthdate:";
    static String sex = "[7] Sex:";

    static String userName, lastName, firstName, middleInitial;
    static String emailAddress;
    static String passKey;
    static String street, barangay, city, province, location;
    static int postalDigit;
    static String birthDay;
    static char gen;

    public static void signUp() {
        int complete = 0; boolean submit = false; boolean valid = false;

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
            printSex();

            System.out.println("│" + " ".repeat(innerWidth) + "│");
            String options = "[/] Submit     [X] Return";
            left = (innerWidth - options.length()) / 2;
            right = innerWidth - options.length() - left;
            System.out.println("│" + " ".repeat(left) + options + " ".repeat(right) + "│");
            System.out.println("└" + "─".repeat(innerWidth) + "┘");
            System.out.print("  Select > ");
            String choice = in.nextLine();

            switch (choice) {
                case "1":
                    System.out.println("   Enter Full Name (Last Name/First Name/Middle Initial)");

                    do {
                        do {
                            System.out.print("    Last Name > ");
                            lastName = in.nextLine();
                            if (lastName.trim().isEmpty()) UI.print("Invalid Input", innerWidth);
                        } while (lastName.trim().isEmpty());

                        do {
                            System.out.print("    First Name > ");
                            firstName = in.nextLine();
                            if (firstName.trim().isEmpty()) UI.print("Invalid Input", innerWidth);
                        } while (firstName.trim().isEmpty());

                        System.out.print("    Middle Initial > ");
                        middleInitial = in.nextLine().toUpperCase();

                        if (middleInitial.endsWith(".")) middleInitial = middleInitial.replace(".", "");
                        userName = lastName + ", " + firstName + " " + middleInitial + ".";
                        if (RetrieveUser.checkDuplicateName(userName)) UI.print("Name already exists", innerWidth);
                    } while (RetrieveUser.checkDuplicateName(userName));

                    if (user.equals("[1] Full Name:")) complete++;

                    printUser(userName);
                    break;

                case "2":
                    valid = false;

                    do {
                        System.out.print("   Enter Email Address > ");
                        emailAddress = in.nextLine();
                        if (emailAddress.trim().isEmpty()) UI.print("Invalid Input", innerWidth);
                        else if (!emailAddress.endsWith("@bulsu.edu.ph")) UI.print("Invalid Email Domain", innerWidth);
                        else if (!emailAddress.matches("[SFA][0-9]{3}@bulsu\\.edu\\.ph"))
                            UI.print("Invalid User", innerWidth);
                        else if (RetrieveUser.checkDuplicateEmail(emailAddress)) UI.print("Email already exists", innerWidth);
                        else valid = true;
                    } while (!valid);

                    if (email.equals("[2] Email Address:")) complete++;
                    printEmail(emailAddress);
                    break;

                case "3":
                    valid = false;
                    do {
                        System.out.print("   Enter Password > ");
                        passKey = in.nextLine();
                        if (passKey.trim().isEmpty()) UI.print("Invalid Input", innerWidth);
                        else if (passKey.length() < 8) UI.print("Password must be at least 8 characters", innerWidth);
                        else if (!passKey.matches(".*[A-Z].*"))
                            UI.print("Password must contain an uppercase letter", innerWidth);
                        else if (!passKey.matches(".*[a-z].*"))
                            UI.print("Password must contain an lowercase letter", innerWidth);
                        else if (!passKey.matches(".*[0-9].*"))
                            UI.print("Password must contain a number", innerWidth);
                        else if (!passKey.matches(".*[^a-zA-Z0-9].*"))
                            UI.print("Password must contain a special character", innerWidth);
                        else if (passKey.contains(" ")) UI.print("Password cannot contain spaces", innerWidth);
                        else valid = true;
                    } while (!valid);
                    if (password.equals("[3] Password:")) complete++;
                    printPassword(passKey);
                    break;

                case "4":
                    System.out.println("   Enter Address (Street/Barangay/City/Province)");

                    do {
                        System.out.print("    Enter Street > ");
                        street = in.nextLine();
                        if (street.trim().isEmpty()) UI.print("Invalid Input", innerWidth);
                    } while (street.trim().isEmpty());

                    do {
                        System.out.print("    Enter Barangay > ");
                        barangay = in.nextLine();
                        if (barangay.trim().isEmpty()) UI.print("Invalid Input", innerWidth);
                    } while (barangay.trim().isEmpty());

                    do {
                        System.out.print("    Enter City > ");
                        city = in.nextLine();
                        if (city.trim().isEmpty()) UI.print("Invalid Input", innerWidth);
                    } while (city.trim().isEmpty());

                    do {
                        System.out.print("    Enter Province > ");
                        province = in.nextLine();
                        if (province.trim().isEmpty()) UI.print("Invalid Input", innerWidth);
                    } while (province.trim().isEmpty());

                    location = street + ", " + barangay + ", " + city + ", " + province;
                    System.out.println(location);

                    if (address.equals("[4] Address:")) complete++;
                    printAddress(location);
                    break;

                case "5":
                    valid = false;
                    do {
                        System.out.print("   Enter Postal Code > ");

                        try {
                            postalDigit = in.nextInt();
                            in.nextLine();
                            if (postalDigit < 1000 || postalDigit > 9999)
                                UI.print("Postal code must be 4 digits", innerWidth);
                            else valid = true;
                        }
                        catch (Exception e) {
                            in.nextLine();
                            UI.print("Invalid Input", innerWidth);
                        }

                    } while (!valid);

                    if (postalCode.equals("[5] Postal Code:")) complete++;
                    printPostal(postalDigit);
                    break;

                case "6":
                    do {
                        System.out.print("   Enter Birthdate (MM/DD/YYYY) > ");
                        birthDay = in.nextLine();
                        if (!birthDay.matches("(0[1-9]|1[0-2])/(0[1-9]|[12][0-9]|3[01])/\\d{4}"))
                            UI.print("Invalid birthdate format", innerWidth);
                    } while (!birthDay.matches("(0[1-9]|1[0-2])/(0[1-9]|[12][0-9]|3[01])/\\d{4}"));

                    if (birthDate.equals("[6] Birthdate:")) complete++;
                    printBDate(birthDay);
                    break;

                case "7":
                    do {
                        System.out.print("   Enter Sex (M/F) > ");
                        gen = in.nextLine().toUpperCase().charAt(0);
                        if  (gen != 'M' && gen != 'F') UI.print("Invalid Input", innerWidth);
                    } while (gen != 'M' && gen != 'F');

                    if (sex.equals("[7] Sex:")) complete++;
                    printSex(gen);
                    break;

                case "/":
                    if (complete == 7) {
                        submit = true;

                        switch (emailAddress.charAt(0)) {
                            case 'A':
                                System.out.print("   Role > ");
                                String role = in.nextLine();
                                RetrieveUser.users.add(new Admin(emailAddress.substring(0, 4), lastName, firstName, middleInitial,
                                        emailAddress, passKey, location, postalDigit, birthDay, gen, role));
                                break;
                            case 'F':
                                String contactNumber;
                                do {
                                    System.out.print("   Contact Number > ");
                                    contactNumber = in.nextLine();
                                    if (contactNumber.matches("[0-9]{11}"))
                                        UI.print("Invalid Input", innerWidth);
                                } while (contactNumber.length() != 11);
                                RetrieveUser.users.add(new Faculty(emailAddress.substring(0, 4), lastName, firstName, middleInitial,
                                        emailAddress, passKey, location, postalDigit, birthDay, gen, contactNumber));
                                break;
                            case 'S':
                                String enrollmentStatus;
                                do {
                                    System.out.print("   Enrollment Status (R, IR) > ");
                                    enrollmentStatus = in.nextLine().toUpperCase();
                                    if (!enrollmentStatus.matches("R|IR")) UI.print("Invalid Input", innerWidth);
                                } while (!enrollmentStatus.matches("R|IR"));
                                RetrieveUser.users.add(new Student(emailAddress.substring(0, 4), lastName, firstName, middleInitial,
                                        emailAddress, passKey, location, postalDigit, birthDay, gen, enrollmentStatus));
                                break;
                        }
                        UI.print("SIGN UP SUCCESSFUL! Welcome to ClassNav.", innerWidth);
                    }
                    else UI.print("Incomplete Details!", innerWidth);
                    break;

                case "X":
                    user = "[1] Full Name:";
                    email = "[2] Email Address:";
                    password = "[3] Password:";
                    address = "[4] Address:";
                    postalCode = "[5] Postal Code:";
                    birthDate = "[6] Birthdate:";
                    sex = "[7] Sex:";
                    complete = 0;
                    User.start();
                    break;
                default:
                    UI.print("Invalid Choice", innerWidth);
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
    static void printSex() {
        System.out.printf("│ %-" + (innerWidth - 2) + "s │%n", sex);
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
    static void printPostal(int postalDigit) {
        postalCode = "[5] Postal Code: " + postalDigit;
    }
    static void printBDate(String BDate) {
        birthDate = "[6] Birthdate: " + BDate;
    }
    static void printSex(char gen) {
        sex = "[7] Sex: " + gen;
    }
}
