package USER;

import FUNCTION.*;
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

    public User(String userID, String lastName, String firstName, String middleInitial,
                String emailAddress, String password, String address, int postalCode,
                String birthDate, char sex) {

        this.userID = userID;
        this.lastName = lastName == null ? "" : lastName.trim();
        this.firstName = firstName == null ? "" : firstName.trim();
        this.middleInitial = middleInitial == null ? "" : middleInitial.trim();
        this.emailAddress = emailAddress;
        this.password = password;
        this.address = address;
        this.postalCode = postalCode;
        this.birthDate = birthDate;
        this.sex = Character.toUpperCase(sex);

        updateUserName();
    }

    public static ArrayList<User> users = new ArrayList<>();

    private void updateUserName() {
        if (middleInitial.isEmpty())
            userName = lastName + ", " + firstName;
        else
            userName = lastName + ", " + firstName + " " + middleInitial + ".";
    }

    public static void initializeUsers() {
        if (!users.isEmpty())
            return;

        users.add(new Student("Sabulao", "Josh", "P",
                "S001@bulsu.edu.ph", "Student001!", "CSJDM, Bulacan", 3023,
                "06/03/2007", 'M', "R", "BSIT"));
        users.add(new Student("Rural", "Elijah", "R",
                "S002@bulsu.edu.ph", "Student002!", "Malolos, Bulacan", 3000,
                "04/20/2006", 'M', "R", "BSIT"));
        users.add(new Student("Caparas", "Kyle", "A",
                "S003@bulsu.edu.ph", "Student003!", "Meycauayan, Bulacan", 3020,
                "08/14/2005", 'M', "R", "BSIT"));
        users.add(new Student("Banot", "Josh", "R",
                "S004@bulsu.edu.ph", "Student004!", "Bocaue, Bulacan", 3018,
                "11/09/2006", 'M', "IR", "BSIT"));
        users.add(new Student("Reyes", "Andrea", "M",
                "S005@bulsu.edu.ph", "Student005!", "Guiguinto, Bulacan", 3015,
                "01/12/2005", 'F', "R", "BSIT"));
        users.add(new Student("Santos", "Miguel", "L",
                "S006@bulsu.edu.ph", "Student006!", "Plaridel, Bulacan", 3004,
                "03/22/2004", 'M', "R", "BSIT"));
        users.add(new Student("Cruz", "Bianca", "T",
                "S007@bulsu.edu.ph", "Student007!", "Baliwag, Bulacan", 3006,
                "07/30/2006", 'F', "R", "BSIT"));
        users.add(new Student("Garcia", "Nathan", "J",
                "S008@bulsu.edu.ph", "Student008!", "Paombong, Bulacan", 3001,
                "09/18/2005", 'M', "R", "BSIT"));
        users.add(new Student("Mendoza", "Sofia", "C",
                "S009@bulsu.edu.ph", "Student009!", "Calumpit, Bulacan", 3003,
                "12/05/2004", 'F', "IR", "BSIT"));
        users.add(new Student("Flores", "Daniel", "B",
                "S010@bulsu.edu.ph", "Student010!", "Hagonoy, Bulacan", 3002,
                "02/17/2005", 'M', "R", "BSIT"));
        users.add(new Student("Torres", "Nicole", "D",
                "S011@bulsu.edu.ph", "Student011!", "Malolos, Bulacan", 3000,
                "05/11/2006", 'F', "R", "BSIT"));
        users.add(new Student("Villanueva", "Gabriel", "S",
                "S012@bulsu.edu.ph", "Student012!", "San Rafael, Bulacan", 3008,
                "10/23/2004", 'M', "R", "BSIT"));
        users.add(new Student("Aquino", "Patricia", "E",
                "S013@bulsu.edu.ph", "Student013!", "Angat, Bulacan", 3012,
                "06/19/2005", 'F', "R", "BSIT"));
        users.add(new Student("Castillo", "Adrian", "F",
                "S014@bulsu.edu.ph", "Student014!", "Bustos, Bulacan", 3007,
                "03/08/2006", 'M', "R", "BSIT"));
        users.add(new Student("Domingo", "Jasmine", "K",
                "S015@bulsu.edu.ph", "Student015!", "Pulilan, Bulacan", 3005,
                "08/27/2004", 'F', "R", "BSIT"));
        users.add(new Student("Dela Cruz", "Marco", "R",
                "S016@bulsu.edu.ph", "Student016!", "Marilao, Bulacan", 3019,
                "01/16/2005", 'M', "IR", "BSIT"));
        users.add(new Student("Navarro", "Angela", "V",
                "S017@bulsu.edu.ph", "Student017!", "Obando, Bulacan", 3021,
                "04/02/2006", 'F', "R", "BSIT"));
        users.add(new Student("Pascual", "Enzo", "M",
                "S018@bulsu.edu.ph", "Student018!", "Balagtas, Bulacan", 3016,
                "07/07/2005", 'M', "R", "BSIT"));

        users.add(new Faculty("Kim", "Elijah", "",
                "F001@bulsu.edu.ph", "Faculty001!", "Malolos, Bulacan", 3000,
                "04/20/1988", 'M', "CICT", "ACTIVE"));
        users.add(new Faculty("Ortega", "Maria", "L",
                "F002@bulsu.edu.ph", "Faculty002!", "Guiguinto, Bulacan", 3015,
                "02/14/1985", 'F', "CICT", "ACTIVE"));
        users.add(new Faculty("Dizon", "Paolo", "R",
                "F003@bulsu.edu.ph", "Faculty003!", "Bocaue, Bulacan", 3018,
                "09/03/1982", 'M', "CICT", "ACTIVE"));
        users.add(new Faculty("Santiago", "Leah", "M",
                "F004@bulsu.edu.ph", "Faculty004!", "Meycauayan, Bulacan", 3020,
                "12/19/1987", 'F', "CICT", "ACTIVE"));
        users.add(new Faculty("Bautista", "Ramon", "C",
                "F005@bulsu.edu.ph", "Faculty005!", "Plaridel, Bulacan", 3004,
                "05/25/1980", 'M', "CICT", "ACTIVE"));
        users.add(new Faculty("Lopez", "Catherine", "A",
                "F006@bulsu.edu.ph", "Faculty006!", "Baliwag, Bulacan", 3006,
                "11/30/1984", 'F', "CICT", "ACTIVE"));

        users.add(new DepartmentHead("Maangas", "Andrei", "P",
                "DH0001@bulsu.edu.ph", "Head001!", "Guiguinto, Bulacan", 3015,
                "09/17/1987", 'M', "CICT"));

        users.add(new DepartmentHead("Mercado", "Rina", "S",
                "DH0002@bulsu.edu.ph", "Head002!", "Malolos, Bulacan", 3000,
                "03/11/1990", 'F', "CICT"));

        users.add(new DepartmentHead("Manalo", "Victor", "D",
                "DH0003@bulsu.edu.ph", "Head003!", "Bustos, Bulacan", 3007,
                "07/29/1986", 'M', "COE"));
    }

    public static User getUser(String userID) {
        if (userID == null) return null;

        for (User u : users) {
            if (userID.trim().equalsIgnoreCase(u.getUserID()))
                return u;
        }

        return null;
    }

    public static boolean verifyEmail(String userID, String emailAddress) {
        User u = getUser(userID);

        return u != null && emailAddress != null
                && emailAddress.trim().equalsIgnoreCase(u.getEmail());
    }

    public static boolean verifyPassword(String userID, String password) {
        User u = getUser(userID);

        return u != null && password != null
                && password.equals(u.getPassword());
    }

    public static boolean checkDuplicateName(String userName) {
        if (userName == null) return false;

        for (User u : users) {
            if (userName.trim().equalsIgnoreCase(u.getUserName()))
                return true;
        }

        return false;
    }

    public static boolean checkDuplicateEmail(String emailAddress) {
        if (emailAddress == null) return false;

        for (User u : users) {
            if (emailAddress.trim().equalsIgnoreCase(u.getEmail()))
                return true;
        }

        return false;
    }

    public String getUserID() { return userID; }
    public String getEmail() { return emailAddress; }
    public String getPassword() { return password; }
    public String getUserName() { return userName; }
    public String getAddress() { return address; }
    public int getPostalCode() { return postalCode; }
    public String getBirthDate() { return birthDate; }
    public char getSex() { return sex; }

    static Scanner in = new Scanner(System.in);

    static final int innerWidth = 70;

    public static void start() {
        String select;
        boolean valid;

        do {
            UI.startPrint("ClassNav");
            select = in.nextLine().trim().toUpperCase();

            if (select.equals("X") || select.equals("1") || select.equals("2"))
                valid = true;
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
                Signup.signUp("SIGN UP");
                break;
        }
    }

    public static void viewProfile(String userID) {
        User u = getUser(userID);

        if (u == null) {
            UI.print("User not found");
            return;
        }

        UI.header("USER PROFILE");
        UI.liner("  ACCOUNT INFORMATION");
        UI.separator();
        UI.liner(String.format("  USER ID        : %s", u.getUserID()));
        UI.liner(String.format("  FULL NAME      : %s", u.getUserName()));
        UI.liner(String.format("  EMAIL ADDRESS  : %s", u.getEmail()));
        UI.separator();
        UI.liner("  PERSONAL INFORMATION");
        UI.separator();
        UI.liner(String.format("  HOME ADDRESS   : %s", u.getAddress()));
        UI.liner(String.format("  POSTAL CODE    : %s", u.getPostalCode()));
        UI.liner(String.format("  BIRTHDATE      : %s", u.getBirthDate()));
        UI.liner(String.format("  SEX            : %s", u.getSex()));
        UI.footer();
    }

    public static void updateProfile(String userID) {
        User u = getUser(userID);

        if (u == null) {
            UI.print("User not found");
            return;
        }

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

            select = readInt("  Select > ");

            switch (select) {
                case 1:
                    updateName(u);
                    break;

                case 2:
                    updateEmail(u);
                    break;

                case 3:
                    updatePassword(u);
                    break;

                case 4:
                    System.out.print("New Address: ");
                    String address = in.nextLine().trim();

                    if (address.isEmpty())
                        UI.print("Address cannot be blank");
                    else {
                        u.setAddress(address);
                        UI.print("Address updated successfully!");
                    }
                    break;

                case 5:
                    System.out.print("New Postal Code: ");
                    String postalInput = in.nextLine().trim();

                    try {
                        int postalCode = Integer.parseInt(postalInput);

                        if (postalCode <= 0)
                            UI.print("Postal code must be positive");
                        else {
                            u.setPostalCode(postalCode);
                            UI.print("Postal Code updated successfully!");
                        }
                    } catch (NumberFormatException e) {
                        UI.print("Invalid postal code");
                    }
                    break;

                case 6:
                    System.out.print("New Birthdate (MM/dd/yyyy): ");
                    String birthDate = in.nextLine().trim();

                    if (!isValidDate(birthDate))
                        UI.print("Invalid birthdate. Use MM/dd/yyyy.");
                    else {
                        u.setBirthDate(birthDate);
                        UI.print("Birthdate updated successfully!");
                    }
                    break;

                case 7:
                    System.out.print("New Sex (M/F): ");
                    String sexInput = in.nextLine().trim().toUpperCase();

                    if (sexInput.equals("M") || sexInput.equals("F")) {
                        u.setSex(sexInput.charAt(0));
                        UI.print("Sex updated successfully!");
                    } else {
                        UI.print("Invalid sex. Enter M or F.");
                    }
                    break;

                case 0:
                    break;

                default:
                    UI.print("Invalid input. Please try again.");
                    break;
            }
        } while (select != 0);
    }

    private static int readInt(String prompt) {
        System.out.print(prompt);
        String input = in.nextLine().trim();

        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void updateName(User u) {
        int select;

        do {
            UI.header("UPDATE NAME");
            System.out.printf("│%-" + innerWidth + "s│%n",
                    String.format("  [1] First Name      : %s", u.firstName));
            System.out.printf("│%-" + innerWidth + "s│%n",
                    String.format("  [2] Middle Initial  : %s", u.middleInitial));
            System.out.printf("│%-" + innerWidth + "s│%n",
                    String.format("  [3] Last Name       : %s", u.lastName));
            System.out.printf("│%-" + innerWidth + "s│%n", "  [0] Back");
            UI.footer();

            select = readInt("  Select > ");

            switch (select) {
                case 1:
                    System.out.print("New First Name: ");
                    String firstName = in.nextLine().trim();

                    if (firstName.isEmpty())
                        UI.print("First name cannot be blank");
                    else {
                        u.setFirstName(firstName);
                        UI.print("First name updated successfully!");
                    }
                    break;

                case 2:
                    System.out.print("New Middle Initial (Enter to remove): ");
                    String middleInitial = in.nextLine().trim();

                    if (middleInitial.length() > 1) {
                        UI.print("Enter only one character");
                    } else {
                        u.setMiddleInitial(middleInitial);
                        UI.print("Middle Initial updated successfully!");
                    }
                    break;

                case 3:
                    System.out.print("New Last Name: ");
                    String lastName = in.nextLine().trim();

                    if (lastName.isEmpty())
                        UI.print("Last name cannot be blank");
                    else {
                        u.setLastName(lastName);
                        UI.print("Last name updated successfully!");
                    }
                    break;

                case 0:
                    break;

                default:
                    UI.print("Invalid input. Please try again.");
                    break;
            }

            u.updateUserName();

        } while (select != 0);
    }

    private static void updateEmail(User u) {
        System.out.print("New Email Address: ");
        String emailAddress = in.nextLine().trim();

        if (emailAddress.isEmpty() || !emailAddress.contains("@")) {
            UI.print("Invalid email address");
            return;
        }

        for (User other : users) {
            if (other != u && emailAddress.equalsIgnoreCase(other.getEmail())) {
                UI.print("Email address is already registered");
                return;
            }
        }

        u.setEmailAddress(emailAddress);
        UI.print("Email Address updated successfully!");
    }

    private static void updatePassword(User u) {
        System.out.print("New Password: ");
        String password = in.nextLine();

        if (password.isBlank()) {
            UI.print("Password cannot be blank");
            return;
        }

        u.setPassword(password);
        UI.print("Password updated successfully!");
    }

    private static boolean isValidDate(String date) {
        if (date == null || !date.matches("\\d{2}/\\d{2}/\\d{4}"))
            return false;

        try {
            java.time.LocalDate parsed = java.time.LocalDate.parse(
                    date, java.time.format.DateTimeFormatter
                            .ofPattern("MM/dd/uuuu")
                            .withResolverStyle(
                                    java.time.format.ResolverStyle.STRICT));

            return !parsed.isAfter(java.time.LocalDate.now());
        } catch (java.time.format.DateTimeParseException e) {
            return false;
        }
    }

    public static void displayUsers() {
        UI.header("USER LIST");

        for (User u : users) {
            String userType = "";
            String additionalInfo = "";

            if (u instanceof Student) {
                Student s = (Student) u;
                userType = "Student";
                additionalInfo = Student.getEnrollmentStatus(s);
            } else if (u instanceof Faculty) {
                Faculty f = (Faculty) u;
                userType = "Faculty";
                additionalInfo = Faculty.getCollegeCode(f);
            } else if (u instanceof DepartmentHead) {
                DepartmentHead d = (DepartmentHead) u;
                userType = "Department Head";
                additionalInfo = d.getCollegeCode();
            }

            String user = String.format(
                    "  %-10s %-20s %-25s %-18s %-15s",
                    u.getUserID(),
                    u.getLastName() + ", " + u.getFirstName(),
                    u.getEmailAddress(),
                    userType,
                    additionalInfo
            );

            UI.liner(user);
        }

        UI.footer();
    }

    public static boolean existUserID(String userID) {
        if (userID == null) return false;

        for (User u : users) {
            if (userID.trim().equalsIgnoreCase(u.getUserID()))
                return true;
        }

        return false;
    }

    public void setLastName(String lastName) {
        if (lastName == null || lastName.isBlank())
            throw new IllegalArgumentException("Last name cannot be blank");

        this.lastName = lastName.trim();
        updateUserName();
    }

    public void setFirstName(String firstName) {
        if (firstName == null || firstName.isBlank())
            throw new IllegalArgumentException("First name cannot be blank");

        this.firstName = firstName.trim();
        updateUserName();
    }

    public void setMiddleInitial(String middleInitial) {
        if (middleInitial == null) middleInitial = "";

        middleInitial = middleInitial.trim();

        if (middleInitial.length() > 1)
            throw new IllegalArgumentException("Middle initial must be one character");

        this.middleInitial = middleInitial;
        updateUserName();
    }

    public void setEmailAddress(String emailAddress) {
        if (emailAddress == null || emailAddress.isBlank())
            throw new IllegalArgumentException("Email cannot be blank");

        this.emailAddress = emailAddress.trim();
    }

    public void setPassword(String password) {
        if (password == null || password.isBlank())
            throw new IllegalArgumentException("Password cannot be blank");

        this.password = password;
    }

    public void setAddress(String address) {
        if (address == null || address.isBlank())
            throw new IllegalArgumentException("Address cannot be blank");

        this.address = address.trim();
    }

    public void setPostalCode(int postalCode) {
        if (postalCode <= 0)
            throw new IllegalArgumentException("Postal code must be positive");

        this.postalCode = postalCode;
    }

    public void setBirthDate(String birthDate) {
        if (!isValidDate(birthDate))
            throw new IllegalArgumentException("Invalid birthdate");

        this.birthDate = birthDate;
    }

    public void setSex(char sex) {
        sex = Character.toUpperCase(sex);

        if (sex != 'M' && sex != 'F')
            throw new IllegalArgumentException("Sex must be M or F");

        this.sex = sex;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleInitial() {
        return middleInitial;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setUserID(String userID) {
        if (userID == null || userID.isBlank())
            throw new IllegalArgumentException("User ID cannot be blank");

        this.userID = userID.trim();
    }
}