
package FUNCTION;

import USER.*;
import DATA.*;
import UI.*;

import static UI.UI.in;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.time.format.DateTimeParseException;


public class Signup {

    static String user = "[1] Full Name:";
    static String email = "[2] Email Address:";
    static String password = "[3] Password:";
    static String address = "[4] Address:";
    static String postalCode = "[5] Postal Code:";
    static String birthDate = "[6] Birthdate:";
    static String sex = "[7] Sex:";

    public static void signUp(String text) {

        int complete = 0;
        boolean submit = false, valid;
        String userName = "", lastName = "", firstName = "", middleInitial = "";
        String emailAddress = "", passKey = "";
        String street = "", barangay = "", city = "", province = "", location = "";
        String birthDay = "";
        int postalDigit = 0;
        char gen = '\0';

        while (!submit) {
            UI.header(text);

            printUser();
            printEmail();
            printPassword();
            printAddress();
            printPostal();
            printBDate();
            printSex();

            UI.option();
            UI.footer();

            System.out.print("  Select > ");
            String choice = in.nextLine().trim().toUpperCase();

            switch (choice) {
                case "1":
                    valid = false;
                    System.out.println("   Enter Full Name (Last Name/First Name/Middle Initial)");

                    do {
                        do {
                            System.out.print("    Last Name > ");
                            lastName = in.nextLine().trim();

                            if (lastName.isEmpty())
                                UI.print("Invalid Input");
                        } while (lastName.isEmpty());

                        do {
                            System.out.print("    First Name > ");
                            firstName = in.nextLine().trim();

                            if (firstName.isEmpty())
                                UI.print("Invalid Input");
                        } while (firstName.isEmpty());

                        System.out.print("    Middle Initial > ");
                        middleInitial = in.nextLine().trim().toUpperCase();

                        if (middleInitial.endsWith("."))
                            middleInitial = middleInitial.substring(0, middleInitial.length() - 1);

                        userName = lastName + ", " + firstName
                                + (middleInitial.isEmpty() ? "" : " " + middleInitial + ".");

                        if (User.checkDuplicateName(userName)) {
                            UI.print("Name already exists");
                            firstName = ""; middleInitial = ""; lastName = "";
                        } else {
                            if (user.equals("[1] Full Name:")) complete++;
                            user = "[1] Full Name: " + userName;
                            valid = true;
                        }
                    } while (!valid);
                    break;

                case "2":
                    valid = false;

                    do {
                        System.out.print("   Enter Email Address > ");
                        emailAddress = in.nextLine().trim();

                        if (emailAddress.isEmpty()) {
                            UI.print("Invalid Input");
                        } else if (!emailAddress.matches(
                                "(S[0-9]{4}|F[0-9]{4}|DH[0-9]{4})@bulsu\\.edu\\.ph")) {
                            UI.print("Use the appropriate BulSU account format: S0001, F0001, or DH0001");
                        } else if (User.checkDuplicateEmail(emailAddress)) {
                            UI.print("Email already exists");
                        } else {
                            valid = true;
                        }
                    } while (!valid);

                    if (email.equals("[2] Email Address:")) complete++;
                    email = "[2] Email Address: " + emailAddress;
                    break;

                case "3":
                    valid = false;

                    do {
                        System.out.print("   Enter Password > ");
                        passKey = in.nextLine();

                        if (passKey.trim().isEmpty())
                            UI.print("Invalid Input");
                        else if (passKey.length() < 8)
                            UI.print("Password must be at least 8 characters");
                        else if (!passKey.matches(".*[A-Z].*"))
                            UI.print("Password must contain an uppercase letter");
                        else if (!passKey.matches(".*[a-z].*"))
                            UI.print("Password must contain a lowercase letter");
                        else if (!passKey.matches(".*[0-9].*"))
                            UI.print("Password must contain a number");
                        else if (!passKey.matches(".*[^a-zA-Z0-9].*"))
                            UI.print("Password must contain a special character");
                        else if (passKey.matches(".*\\s+.*"))
                            UI.print("Password cannot contain whitespace");
                        else
                            valid = true;

                    } while (!valid);

                    if (password.equals("[3] Password:")) complete++;
                    password = "[3] Password: " + "*".repeat(passKey.length());
                    break;

                case "4":
                    System.out.println("   Enter Address (Street/Barangay/City/Province)");

                    do {
                        System.out.print("    Enter Street > ");
                        street = in.nextLine().trim();
                        if (street.isEmpty()) UI.print("Invalid Input");
                    } while (street.isEmpty());

                    do {
                        System.out.print("    Enter Barangay > ");
                        barangay = in.nextLine().trim();
                        if (barangay.isEmpty()) UI.print("Invalid Input");
                    } while (barangay.isEmpty());

                    do {
                        System.out.print("    Enter City > ");
                        city = in.nextLine().trim();
                        if (city.isEmpty()) UI.print("Invalid Input");
                    } while (city.isEmpty());

                    do {
                        System.out.print("    Enter Province > ");
                        province = in.nextLine().trim();
                        if (province.isEmpty()) UI.print("Invalid Input");
                    } while (province.isEmpty());

                    location = street + ", " + barangay + ", " + city + ", " + province;

                    if (address.equals("[4] Address:")) complete++;
                    address = "[4] Address: " + location;
                    break;

                case "5":
                    valid = false;

                    do {
                        System.out.print("   Enter Postal Code > ");
                        String postalInput = in.nextLine().trim();

                        if (!postalInput.matches("[0-9]{4}")) {
                            UI.print("Postal code must contain exactly 4 digits");
                        } else {
                            postalDigit = Integer.parseInt(postalInput);
                            valid = true;
                        }
                    } while (!valid);

                    if (postalCode.equals("[5] Postal Code:")) complete++;
                    postalCode = "[5] Postal Code: " + String.format("%04d", postalDigit);
                    break;

                case "6":
                    valid = false;
                    DateTimeFormatter formatter = DateTimeFormatter
                            .ofPattern("MM/dd/uuuu")
                            .withResolverStyle(ResolverStyle.STRICT);

                    do {
                        System.out.print("   Enter Birthdate (MM/DD/YYYY) > ");
                        birthDay = in.nextLine().trim();

                        try {
                            LocalDate date = LocalDate.parse(birthDay, formatter);

                            if (date.isAfter(LocalDate.now())) {
                                UI.print("Birthdate cannot be in the future");
                            } else {
                                valid = true;
                            }
                        } catch (DateTimeParseException e) {
                            UI.print("Invalid birthdate. Use a valid MM/DD/YYYY date");
                        }
                    } while (!valid);

                    if (birthDate.equals("[6] Birthdate:")) complete++;
                    birthDate = "[6] Birthdate: " + birthDay;
                    break;

                case "7":
                    String sexInput;

                    do {
                        System.out.print("   Enter Sex (M/F) > ");
                        sexInput = in.nextLine().trim().toUpperCase();

                        if (!sexInput.matches("[MF]"))
                            UI.print("Invalid Input");
                    } while (!sexInput.matches("[MF]"));

                    gen = sexInput.charAt(0);

                    if (sex.equals("[7] Sex:")) complete++;
                    sex = "[7] Sex: " + gen;
                    break;

                case "/":
                    if (complete != 7) {
                        UI.print("Incomplete Details!");
                        break;
                    }

                    String accountType = emailAddress.substring(0, emailAddress.indexOf('@'));

                    try {
                        if (accountType.startsWith("DH")) {
                            String collegeCode;

                            do {
                                System.out.print("   College Code > ");
                                collegeCode = in.nextLine().trim().toUpperCase();

                                if (College.findCollege(collegeCode) == null)
                                    UI.print("College code does not exist");
                            } while (College.findCollege(collegeCode) == null);

                            User.users.add(new DepartmentHead(
                                    lastName, firstName, middleInitial,
                                    emailAddress, passKey, location,
                                    postalDigit, birthDay, gen, collegeCode));

                        } else if (accountType.startsWith("F")) {
                            String collegeCode;
                            String employmentStatus;

                            do {
                                System.out.print("   College Code > ");
                                collegeCode = in.nextLine().trim().toUpperCase();

                                if (College.findCollege(collegeCode) == null)
                                    UI.print("College code does not exist");
                            } while (College.findCollege(collegeCode) == null);

                            do {
                                System.out.print("   Employment Status > ");
                                employmentStatus = in.nextLine().trim().toUpperCase();

                                if (!employmentStatus.matches("[A-Z ]+"))
                                    UI.print("Invalid employment status");
                            } while (!employmentStatus.matches("[A-Z ]+"));

                            User.users.add(new Faculty(
                                    lastName, firstName, middleInitial,
                                    emailAddress, passKey, location,
                                    postalDigit, birthDay, gen,
                                    collegeCode, employmentStatus));

                        } else if (accountType.startsWith("S")) {
                            String enrollmentStatus, programCode;

                            do {
                                System.out.print("   Enrollment Status (R, IR) > ");
                                enrollmentStatus = in.nextLine().trim().toUpperCase();

                                if (!enrollmentStatus.matches("R|IR"))
                                    UI.print("Enter R or IR");
                            } while (!enrollmentStatus.matches("R|IR"));

                            do {
                                System.out.print("   Program Code > ");
                                programCode = in.nextLine().trim().toUpperCase();

                                if (Program.findProgram(programCode) != null)
                                    UI.print("Program code does not exist");
                            } while (Program.findProgram(programCode) != null);

                            User.users.add(new Student(
                                    lastName, firstName, middleInitial,
                                    emailAddress, passKey, location,
                                    postalDigit, birthDay, gen,
                                    enrollmentStatus, programCode));
                        }

                        UI.print("SIGN UP SUCCESSFUL! Welcome to ClassNav.");
                        resetFields();
                        submit = true;
                    } catch (IllegalArgumentException e) {
                        UI.print("Unable to create account: " + e.getMessage());
                    }
                    break;

                case "X":
                    resetFields();
                    submit = true;
                    break;

                default:
                    UI.print("Invalid Choice");
                    break;
            }
        }
    }

    public static void resetFields() {
        user = "[1] Full Name:";
        email = "[2] Email Address:";
        password = "[3] Password:";
        address = "[4] Address:";
        postalCode = "[5] Postal Code:";
        birthDate = "[6] Birthdate:";
        sex = "[7] Sex:";
    }

    static void printUser() {
        UI.liner(" " + user);
    }
    static void printEmail() {
        UI.liner(" " + email);
    }
    static void printPassword() {
        UI.liner(" " + password);
    }
    static void printAddress() {
        UI.liner(" " + address);
    }
    static void printPostal() {
        UI.liner(" " + postalCode);
    }
    static void printBDate() {
        UI.liner(" " + birthDate);
    }
    static void printSex() {
        UI.liner(" " + sex);
    }
}