package USER;

import DATA.College;
import DATA.Program;
import FUNCTION.*;
import UI.*;
import static UI.UI.in;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;

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
    public static ArrayList<User> users = new ArrayList<>();

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

        switch (select) {
            case "1":
                String user = Login.logIn();
                if (!user.equals("Ran out of attempts.")) {
                    User u = User.getUser(user);
                    if (u == null) {
                        UI.print("User not found");
                        return;
                    }
                    u.menu();
                }
                User.start();
                break;
            case "2":
                Signup.signUp("SIGN UP");
                User.start();
                break;
            case "X":
                System.out.println("   Exiting...");
                break;
        }
    }

    public void menu() {
        UI.header("USER MENU");
        UI.liner(" [1] View Profile");
        UI.liner(" [2] Update Profile");
    }

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
                "S0001@bulsu.edu.ph", "Student001!", "CSJDM, Bulacan", 3023,
                "06/03/2007", 'M', "R", "BSIT"));
        users.add(new Student("Rural", "Elijah", "R",
                "S0002@bulsu.edu.ph", "Student002!", "Malolos, Bulacan", 3000,
                "04/20/2006", 'M', "R", "BSIT"));
        users.add(new Student("Caparas", "Kyle", "A",
                "S0003@bulsu.edu.ph", "Student003!", "Meycauayan, Bulacan", 3020,
                "08/14/2005", 'M', "R", "BSIT"));
        users.add(new Student("Banot", "Josh", "R",
                "S0004@bulsu.edu.ph", "Student004!", "Bocaue, Bulacan", 3018,
                "11/09/2006", 'M', "IR", "BSIT"));
        users.add(new Student("Reyes", "Andrea", "M",
                "S0005@bulsu.edu.ph", "Student005!", "Guiguinto, Bulacan", 3015,
                "01/12/2005", 'F', "R", "BSIT"));
        users.add(new Student("Santos", "Miguel", "L",
                "S0006@bulsu.edu.ph", "Student006!", "Plaridel, Bulacan", 3004,
                "03/22/2004", 'M', "R", "BSIT"));
        users.add(new Student("Cruz", "Bianca", "T",
                "S0007@bulsu.edu.ph", "Student007!", "Baliwag, Bulacan", 3006,
                "07/30/2006", 'F', "R", "BSIT"));
        users.add(new Student("Garcia", "Nathan", "J",
                "S0008@bulsu.edu.ph", "Student008!", "Paombong, Bulacan", 3001,
                "09/18/2005", 'M', "R", "BSIT"));
        users.add(new Student("Mendoza", "Sofia", "C",
                "S0009@bulsu.edu.ph", "Student009!", "Calumpit, Bulacan", 3003,
                "12/05/2004", 'F', "IR", "BSIT"));
        users.add(new Student("Flores", "Daniel", "B",
                "S0010@bulsu.edu.ph", "Student010!", "Hagonoy, Bulacan", 3002,
                "02/17/2005", 'M', "R", "BSIT"));
        users.add(new Student("Torres", "Nicole", "D",
                "S0011@bulsu.edu.ph", "Student011!", "Malolos, Bulacan", 3000,
                "05/11/2006", 'F', "R", "BSIT"));
        users.add(new Student("Villanueva", "Gabriel", "S",
                "S0012@bulsu.edu.ph", "Student012!", "San Rafael, Bulacan", 3008,
                "10/23/2004", 'M', "R", "BSIT"));
        users.add(new Student("Aquino", "Patricia", "E",
                "S0013@bulsu.edu.ph", "Student013!", "Angat, Bulacan", 3012,
                "06/19/2005", 'F', "R", "BSIT"));
        users.add(new Student("Castillo", "Adrian", "F",
                "S0014@bulsu.edu.ph", "Student014!", "Bustos, Bulacan", 3007,
                "03/08/2006", 'M', "R", "BSIT"));
        users.add(new Student("Domingo", "Jasmine", "K",
                "S0015@bulsu.edu.ph", "Student015!", "Pulilan, Bulacan", 3005,
                "08/27/2004", 'F', "R", "BSIT"));
        users.add(new Student("Dela Cruz", "Marco", "R",
                "S0016@bulsu.edu.ph", "Student016!", "Marilao, Bulacan", 3019,
                "01/16/2005", 'M', "IR", "BSIT"));
        users.add(new Student("Navarro", "Angela", "V",
                "S0017@bulsu.edu.ph", "Student017!", "Obando, Bulacan", 3021,
                "04/02/2006", 'F', "R", "BSIT"));
        users.add(new Student("Pascual", "Enzo", "M",
                "S0018@bulsu.edu.ph", "Student018!", "Balagtas, Bulacan", 3016,
                "07/07/2005", 'M', "R", "BSIT"));

        users.add(new Faculty("Kim", "Elijah", "",
                "F0001@bulsu.edu.ph", "Faculty001!", "Malolos, Bulacan", 3000,
                "04/20/1988", 'M', "CICT", "ACTIVE"));
        users.add(new Faculty("Ortega", "Maria", "L",
                "F0002@bulsu.edu.ph", "Faculty002!", "Guiguinto, Bulacan", 3015,
                "02/14/1985", 'F', "CICT", "ACTIVE"));
        users.add(new Faculty("Dizon", "Paolo", "R",
                "F0003@bulsu.edu.ph", "Faculty003!", "Bocaue, Bulacan", 3018,
                "09/03/1982", 'M', "CICT", "ACTIVE"));
        users.add(new Faculty("Santiago", "Leah", "M",
                "F0004@bulsu.edu.ph", "Faculty004!", "Meycauayan, Bulacan", 3020,
                "12/19/1987", 'F', "CICT", "ACTIVE"));
        users.add(new Faculty("Bautista", "Ramon", "C",
                "F0005@bulsu.edu.ph", "Faculty005!", "Plaridel, Bulacan", 3004,
                "05/25/1980", 'M', "CICT", "ACTIVE"));
        users.add(new Faculty("Lopez", "Catherine", "A",
                "F0006@bulsu.edu.ph", "Faculty006!", "Baliwag, Bulacan", 3006,
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

    static final int innerWidth = 70;


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
            LocalDate parsed = LocalDate.parse(
                    date, DateTimeFormatter
                            .ofPattern("MM/dd/uuuu")
                            .withResolverStyle(
                                    ResolverStyle.STRICT));

            return !parsed.isAfter(LocalDate.now());
        } catch (DateTimeParseException e) {
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

    public static void editUser() {
        String userID, lastName, firstName, middleInitial, emailAddress;
        String password, address, birthDate, choice, userName;
        String enrollmentStatus = "", programCode = "", collegeCode = "";
        int postalCode;
        char sex;
        boolean valid, submit = false;

        UI.print("EDIT EXISTING USER");

        do {
            System.out.print("  Enter User ID > ");
            userID = in.nextLine().trim().toUpperCase();

            if (!User.existUserID(userID))
                UI.print("User does not exist");
        } while (!User.existUserID(userID));

        User u = User.getUser(userID);

        if (u == null) {
            UI.print("User not found");
            return;
        }

        userID = u.getUserID();
        lastName = u.getLastName();
        firstName = u.getFirstName();
        middleInitial = u.getMiddleInitial();
        emailAddress = u.getEmailAddress();
        password = u.getPassword();
        address = u.getAddress();
        postalCode = u.getPostalCode();
        birthDate = u.getBirthDate();
        sex = u.getSex();

        if (u instanceof Student) {
            Student s = (Student) u;
            enrollmentStatus = s.getEnrollmentStatus();
            programCode = s.getProgramCode();
        } else if (u instanceof Faculty) {
            Faculty f = (Faculty) u;
            collegeCode = Faculty.getCollegeCode(f);
        } else if (u instanceof DepartmentHead) {
            DepartmentHead dh = (DepartmentHead) u;
            collegeCode = dh.getCollegeCode();
        } else {
            UI.print("Invalid User Type");
            return;
        }

        while (!submit) {
            if (u instanceof Student) {
                userManage("EDIT", userID, lastName, firstName, middleInitial,
                        emailAddress, password, address, postalCode, birthDate,
                        sex, enrollmentStatus, programCode);
            } else if (u instanceof Faculty || u instanceof DepartmentHead) {
                userManage("EDIT", userID, lastName, firstName, middleInitial,
                        emailAddress, password, address, postalCode, birthDate,
                        sex, collegeCode);
            }

            do {
                System.out.print("  Select > ");
                choice = in.nextLine().trim().toUpperCase();

                if (!choice.matches("([1-9]|10|X|/)"))
                    UI.print("Invalid Input");
            } while (!choice.matches("([1-9]|10|X|/)"));

            switch (choice) {
                case "1":
                    do {
                        System.out.print("   Enter Last Name > ");
                        lastName = in.nextLine().trim();
                        if (lastName.isEmpty())
                            UI.print("Invalid Input");
                    } while (lastName.isEmpty());
                    break;

                case "2":
                    do {
                        System.out.print("   Enter First Name > ");
                        firstName = in.nextLine().trim();
                        if (firstName.isEmpty())
                            UI.print("Invalid Input");
                    } while (firstName.isEmpty());
                    break;

                case "3":
                    do {
                        System.out.print("   Enter Middle Initial > ");
                        middleInitial = in.nextLine().trim();

                        if (middleInitial.endsWith("."))
                            middleInitial = middleInitial.substring(
                                    0, middleInitial.length() - 1);

                        if (!middleInitial.isEmpty()
                                && !middleInitial.matches("[A-Za-z]"))
                            UI.print("Enter one letter only");
                    } while (!middleInitial.isEmpty()
                            && !middleInitial.matches("[A-Za-z]"));
                    break;

                case "4":
                    valid = false;

                    do {
                        System.out.print("   Enter Email Address > ");
                        emailAddress = in.nextLine().trim();

                        if (emailAddress.isEmpty()) {
                            UI.print("Invalid Input");
                        } else if (!emailAddress.endsWith("@bulsu.edu.ph")) {
                            UI.print("Invalid Email Domain");
                        } else if (!emailAddress.matches(
                                "(S|F)[0-9]{4}@bulsu\\.edu\\.ph")
                                && !emailAddress.matches(
                                "DH[0-9]{4}@bulsu\\.edu\\.ph")) {
                            UI.print("Invalid User");
                        } else if (User.checkDuplicateEmail(emailAddress)
                                && !emailAddress.equalsIgnoreCase(
                                u.getEmailAddress())) {
                            UI.print("Email already exists");
                        } else {
                            valid = true;
                        }
                    } while (!valid);
                    break;

                case "5":
                    valid = false;

                    do {
                        System.out.print("   Enter Password > ");
                        password = in.nextLine();

                        if (password.trim().isEmpty())
                            UI.print("Invalid Input");
                        else if (password.length() < 8)
                            UI.print("Password must be at least 8 characters");
                        else if (!password.matches(".*[A-Z].*"))
                            UI.print("Password must contain an uppercase letter");
                        else if (!password.matches(".*[a-z].*"))
                            UI.print("Password must contain a lowercase letter");
                        else if (!password.matches(".*[0-9].*"))
                            UI.print("Password must contain a number");
                        else if (!password.matches(".*[^a-zA-Z0-9].*"))
                            UI.print("Password must contain a special character");
                        else if (password.contains(" "))
                            UI.print("Password cannot contain spaces");
                        else
                            valid = true;
                    } while (!valid);
                    break;

                case "6":
                    String street, barangay, city, province;

                    do {
                        System.out.print("    Enter Street > ");
                        street = in.nextLine().trim();
                        if (street.isEmpty())
                            UI.print("Invalid Input");
                    } while (street.isEmpty());

                    do {
                        System.out.print("    Enter Barangay > ");
                        barangay = in.nextLine().trim();
                        if (barangay.isEmpty())
                            UI.print("Invalid Input");
                    } while (barangay.isEmpty());

                    do {
                        System.out.print("    Enter City > ");
                        city = in.nextLine().trim();
                        if (city.isEmpty())
                            UI.print("Invalid Input");
                    } while (city.isEmpty());

                    do {
                        System.out.print("    Enter Province > ");
                        province = in.nextLine().trim();
                        if (province.isEmpty())
                            UI.print("Invalid Input");
                    } while (province.isEmpty());

                    address = street + ", " + barangay + ", " + city + ", " + province;
                    break;

                case "7":
                    valid = false;

                    do {
                        System.out.print("   Enter Postal Code > ");
                        String postalInput = in.nextLine().trim();

                        if (!postalInput.matches("[0-9]{4}")) {
                            UI.print("Postal code must be 4 digits");
                        } else {
                            postalCode = Integer.parseInt(postalInput);
                            valid = true;
                        }
                    } while (!valid);
                    break;

                case "8":
                    valid = false;

                    do {
                        System.out.print("   Enter Birthdate (MM/DD/YYYY) > ");
                        birthDate = in.nextLine().trim();

                        if (!isValidBirthDate(birthDate))
                            UI.print("Invalid birthdate");
                        else
                            valid = true;
                    } while (!valid);
                    break;

                case "9":
                    String sexInput;

                    do {
                        System.out.print("   Enter Sex (M/F) > ");
                        sexInput = in.nextLine().trim().toUpperCase();

                        if (!sexInput.matches("[MF]"))
                            UI.print("Invalid Input");
                    } while (!sexInput.matches("[MF]"));

                    sex = sexInput.charAt(0);
                    break;

                case "10":
                    if (u instanceof Student) {
                        do {
                            System.out.print("   Enrollment Status (R, IR) > ");
                            enrollmentStatus = in.nextLine().trim().toUpperCase();

                            if (!enrollmentStatus.matches("R|IR"))
                                UI.print("Invalid Input");
                        } while (!enrollmentStatus.matches("R|IR"));

                        do {
                            System.out.print("   Program Code > ");
                            programCode = in.nextLine().trim().toUpperCase();

                            if (!Program.existProgramCode(programCode))
                                UI.print("Program Code does not exist");
                        } while (!Program.existProgramCode(programCode));

                    } else if (u instanceof Faculty || u instanceof DepartmentHead) {
                        do {
                            System.out.print("   College Code > ");
                            collegeCode = in.nextLine().trim().toUpperCase();

                            if (!College.existCollegeCode(collegeCode))
                                UI.print("College Code does not exist");
                        } while (!College.existCollegeCode(collegeCode));
                    }
                    break;

                case "/":
                    userName = lastName + ", " + firstName
                            + (middleInitial.isEmpty() ? "" : " " + middleInitial + ".");

                    if (checkDuplicateName(userName)
                            && !userName.equalsIgnoreCase(u.getUserName())) {
                        UI.print("Name already exists");
                        break;
                    }

                    u.setLastName(lastName);
                    u.setFirstName(firstName);
                    u.setMiddleInitial(middleInitial);
                    u.setEmailAddress(emailAddress);
                    u.setPassword(password);
                    u.setAddress(address);
                    u.setPostalCode(postalCode);
                    u.setBirthDate(birthDate);
                    u.setSex(sex);

                    try {
                        if (u instanceof Student) {
                            Student s = (Student) u;
                            s.setEnrollmentStatus(enrollmentStatus);
                            s.setProgramCode(programCode);
                        } else if (u instanceof Faculty) {
                            Faculty f = (Faculty) u;
                            f.setCollegeCode(collegeCode);
                        } else if (u instanceof DepartmentHead) {
                            DepartmentHead dh = (DepartmentHead) u;
                            dh.setCollegeCode(collegeCode);
                        }
                    } catch (IllegalArgumentException e) {
                        UI.print(e.getMessage());
                        break;
                    }

                    UI.print("User successfully edited");
                    User.viewProfile(u.getUserID());
                    submit = true;
                    break;

                case "X":
                    UI.print("User edit cancelled");
                    submit = true;
                    break;
            }
        }
    }

    public static void editUser(String userID) {
        String lastName, firstName, middleInitial, emailAddress;
        String password, address, birthDate, choice, userName;
        String enrollmentStatus = "", programCode = "", collegeCode = "";
        int postalCode;
        char sex;
        boolean valid, submit = false;

        User u = User.getUser(userID);

        if (u == null) {
            UI.print("User not found");
            return;
        }

        userID = u.getUserID();
        lastName = u.getLastName();
        firstName = u.getFirstName();
        middleInitial = u.getMiddleInitial();
        emailAddress = u.getEmailAddress();
        password = u.getPassword();
        address = u.getAddress();
        postalCode = u.getPostalCode();
        birthDate = u.getBirthDate();
        sex = u.getSex();

        if (u instanceof Student) {
            Student s = (Student) u;
            enrollmentStatus = s.getEnrollmentStatus();
            programCode = s.getProgramCode();
        } else if (u instanceof Faculty) {
            Faculty f = (Faculty) u;
            collegeCode = Faculty.getCollegeCode(f);
        } else if (u instanceof DepartmentHead) {
            DepartmentHead dh = (DepartmentHead) u;
            collegeCode = dh.getCollegeCode();
        } else {
            UI.print("Invalid User Type");
            return;
        }

        while (!submit) {
            if (u instanceof Student) {
                userManage("EDIT", userID, lastName, firstName, middleInitial,
                        emailAddress, password, address, postalCode, birthDate,
                        sex, enrollmentStatus, programCode);
            } else if (u instanceof Faculty || u instanceof DepartmentHead) {
                userManage("EDIT", userID, lastName, firstName, middleInitial,
                        emailAddress, password, address, postalCode, birthDate,
                        sex, collegeCode);
            }

            do {
                System.out.print("  Select > ");
                choice = in.nextLine().trim().toUpperCase();

                if (!choice.matches("([1-9]|10|X|/)"))
                    UI.print("Invalid Input");
            } while (!choice.matches("([1-9]|10|X|/)"));

            switch (choice) {
                case "1":
                    do {
                        System.out.print("   Enter Last Name > ");
                        lastName = in.nextLine().trim();
                        if (lastName.isEmpty())
                            UI.print("Invalid Input");
                    } while (lastName.isEmpty());
                    break;

                case "2":
                    do {
                        System.out.print("   Enter First Name > ");
                        firstName = in.nextLine().trim();
                        if (firstName.isEmpty())
                            UI.print("Invalid Input");
                    } while (firstName.isEmpty());
                    break;

                case "3":
                    do {
                        System.out.print("   Enter Middle Initial > ");
                        middleInitial = in.nextLine().trim();

                        if (middleInitial.endsWith("."))
                            middleInitial = middleInitial.substring(
                                    0, middleInitial.length() - 1);

                        if (!middleInitial.isEmpty()
                                && !middleInitial.matches("[A-Za-z]"))
                            UI.print("Enter one letter only");
                    } while (!middleInitial.isEmpty()
                            && !middleInitial.matches("[A-Za-z]"));
                    break;

                case "4":
                    valid = false;

                    do {
                        System.out.print("   Enter Email Address > ");
                        emailAddress = in.nextLine().trim();

                        if (emailAddress.isEmpty()) {
                            UI.print("Invalid Input");
                        } else if (!emailAddress.endsWith("@bulsu.edu.ph")) {
                            UI.print("Invalid Email Domain");
                        } else if (!emailAddress.matches(
                                "(S|F)[0-9]{4}@bulsu\\.edu\\.ph")
                                && !emailAddress.matches(
                                "DH[0-9]{4}@bulsu\\.edu\\.ph")) {
                            UI.print("Invalid User");
                        } else if (User.checkDuplicateEmail(emailAddress)
                                && !emailAddress.equalsIgnoreCase(
                                u.getEmailAddress())) {
                            UI.print("Email already exists");
                        } else {
                            valid = true;
                        }
                    } while (!valid);
                    break;

                case "5":
                    valid = false;

                    do {
                        System.out.print("   Enter Password > ");
                        password = in.nextLine();

                        if (password.trim().isEmpty())
                            UI.print("Invalid Input");
                        else if (password.length() < 8)
                            UI.print("Password must be at least 8 characters");
                        else if (!password.matches(".*[A-Z].*"))
                            UI.print("Password must contain an uppercase letter");
                        else if (!password.matches(".*[a-z].*"))
                            UI.print("Password must contain a lowercase letter");
                        else if (!password.matches(".*[0-9].*"))
                            UI.print("Password must contain a number");
                        else if (!password.matches(".*[^a-zA-Z0-9].*"))
                            UI.print("Password must contain a special character");
                        else if (password.contains(" "))
                            UI.print("Password cannot contain spaces");
                        else
                            valid = true;
                    } while (!valid);
                    break;

                case "6":
                    String street, barangay, city, province;

                    do {
                        System.out.print("    Enter Street > ");
                        street = in.nextLine().trim();
                        if (street.isEmpty())
                            UI.print("Invalid Input");
                    } while (street.isEmpty());

                    do {
                        System.out.print("    Enter Barangay > ");
                        barangay = in.nextLine().trim();
                        if (barangay.isEmpty())
                            UI.print("Invalid Input");
                    } while (barangay.isEmpty());

                    do {
                        System.out.print("    Enter City > ");
                        city = in.nextLine().trim();
                        if (city.isEmpty())
                            UI.print("Invalid Input");
                    } while (city.isEmpty());

                    do {
                        System.out.print("    Enter Province > ");
                        province = in.nextLine().trim();
                        if (province.isEmpty())
                            UI.print("Invalid Input");
                    } while (province.isEmpty());

                    address = street + ", " + barangay + ", " + city + ", " + province;
                    break;

                case "7":
                    valid = false;

                    do {
                        System.out.print("   Enter Postal Code > ");
                        String postalInput = in.nextLine().trim();

                        if (!postalInput.matches("[0-9]{4}")) {
                            UI.print("Postal code must be 4 digits");
                        } else {
                            postalCode = Integer.parseInt(postalInput);
                            valid = true;
                        }
                    } while (!valid);
                    break;

                case "8":
                    valid = false;

                    do {
                        System.out.print("   Enter Birthdate (MM/DD/YYYY) > ");
                        birthDate = in.nextLine().trim();

                        if (!isValidBirthDate(birthDate))
                            UI.print("Invalid birthdate");
                        else
                            valid = true;
                    } while (!valid);
                    break;

                case "9":
                    String sexInput;

                    do {
                        System.out.print("   Enter Sex (M/F) > ");
                        sexInput = in.nextLine().trim().toUpperCase();

                        if (!sexInput.matches("[MF]"))
                            UI.print("Invalid Input");
                    } while (!sexInput.matches("[MF]"));

                    sex = sexInput.charAt(0);
                    break;

                case "10":
                    if (u instanceof Student) {
                        do {
                            System.out.print("   Enrollment Status (R, IR) > ");
                            enrollmentStatus = in.nextLine().trim().toUpperCase();

                            if (!enrollmentStatus.matches("R|IR"))
                                UI.print("Invalid Input");
                        } while (!enrollmentStatus.matches("R|IR"));

                        do {
                            System.out.print("   Program Code > ");
                            programCode = in.nextLine().trim().toUpperCase();

                            if (!Program.existProgramCode(programCode))
                                UI.print("Program Code does not exist");
                        } while (!Program.existProgramCode(programCode));

                    } else if (u instanceof Faculty || u instanceof DepartmentHead) {
                        do {
                            System.out.print("   College Code > ");
                            collegeCode = in.nextLine().trim().toUpperCase();

                            if (!College.existCollegeCode(collegeCode))
                                UI.print("College Code does not exist");
                        } while (!College.existCollegeCode(collegeCode));
                    }
                    break;

                case "/":
                    userName = lastName + ", " + firstName
                            + (middleInitial.isEmpty() ? "" : " " + middleInitial + ".");

                    if (checkDuplicateName(userName)
                            && !userName.equalsIgnoreCase(u.getUserName())) {
                        UI.print("Name already exists");
                        break;
                    }

                    u.setLastName(lastName);
                    u.setFirstName(firstName);
                    u.setMiddleInitial(middleInitial);
                    u.setEmailAddress(emailAddress);
                    u.setPassword(password);
                    u.setAddress(address);
                    u.setPostalCode(postalCode);
                    u.setBirthDate(birthDate);
                    u.setSex(sex);

                    try {
                        if (u instanceof Student) {
                            Student s = (Student) u;
                            s.setEnrollmentStatus(enrollmentStatus);
                            s.setProgramCode(programCode);
                        } else if (u instanceof Faculty) {
                            Faculty f = (Faculty) u;
                            f.setCollegeCode(collegeCode);
                        } else if (u instanceof DepartmentHead) {
                            DepartmentHead dh = (DepartmentHead) u;
                            dh.setCollegeCode(collegeCode);
                        }
                    } catch (IllegalArgumentException e) {
                        UI.print(e.getMessage());
                        break;
                    }

                    UI.print("User successfully edited");
                    User.viewProfile(u.getUserID());
                    submit = true;
                    break;

                case "X":
                    UI.print("User edit cancelled");
                    submit = true;
                    break;
            }
        }
    }

    public static void deleteUser() {
        String userID, choice;

        UI.print("DELETE EXISTING USER");

        do {
            System.out.print("  Enter User ID > ");
            userID = in.nextLine().trim();

            if (!User.existUserID(userID))
                UI.print("User does not exist");
        } while (!User.existUserID(userID));

        User u = User.getUser(userID);

        if (u == null) {
            UI.print("User not found");
            return;
        }

        User.viewProfile(userID);

        do {
            System.out.print("  Confirm User Deletion? (Y/N) > ");
            choice = in.nextLine().trim().toUpperCase();

            if (!choice.matches("[YN]"))
                UI.print("Invalid Input");
        } while (!choice.matches("[YN]"));

        if (choice.equals("Y")) {
            User.users.remove(u);
            UI.print("User successfully deleted");
        } else {
            UI.print("User deletion cancelled");
        }
    }

    public static void userManage(String text, String userID, String lastName,
                                  String firstName, String middleInitial, String emailAddress,
                                  String password, String address, int postalCode, String birthDate,
                                  char sex, String input1, String input2) {

        User u = User.getUser(userID);

        if (u == null) {
            UI.print("User not found");
            return;
        }

        UI.header(text.toUpperCase() + ": USER INFORMATION");
        UI.liner(String.format("  USER ID                : %s", userID));
        UI.separator();
        UI.liner(String.format(" [1] LAST NAME           : %s", lastName));
        UI.liner(String.format(" [2] FIRST NAME          : %s", firstName));
        UI.liner(String.format(" [3] MIDDLE INITIAL      : %s", middleInitial));
        UI.liner(String.format(" [4] EMAIL ADDRESS       : %s", emailAddress));
        UI.liner(String.format(" [5] PASSWORD            : %s", password));
        UI.liner(String.format(" [6] ADDRESS             : %s", address));
        UI.liner(String.format(" [7] POSTAL CODE         : %s", postalCode));
        UI.liner(String.format(" [8] BIRTHDATE           : %s", birthDate));
        UI.liner(String.format(" [9] SEX                 : %s", sex));
        UI.liner(String.format(" [10] ENROLLMENT STATUS  : %s", input1));
        UI.liner(String.format(" [11] PROGRAM CODE       : %s", input2));

        UI.liner("");
        UI.option();
        UI.footer();
    }

    public static void userManage(String text, String userID, String lastName,
                                  String firstName, String middleInitial, String emailAddress,
                                  String password, String address, int postalCode, String birthDate,
                                  char sex, String collegeCode) {

        User u = User.getUser(userID);

        if (u == null) {
            UI.print("User not found");
            return;
        }

        UI.header(text.toUpperCase() + ": USER INFORMATION");
        UI.liner(String.format("  USER ID            : %s", userID));
        UI.separator();
        UI.liner(String.format(" [1] LAST NAME       : %s", lastName));
        UI.liner(String.format(" [2] FIRST NAME      : %s", firstName));
        UI.liner(String.format(" [3] MIDDLE INITIAL  : %s", middleInitial));
        UI.liner(String.format(" [4] EMAIL ADDRESS   : %s", emailAddress));
        UI.liner(String.format(" [5] PASSWORD        : %s", password));
        UI.liner(String.format(" [6] ADDRESS         : %s", address));
        UI.liner(String.format(" [7] POSTAL CODE     : %s", postalCode));
        UI.liner(String.format(" [8] BIRTHDATE       : %s", birthDate));
        UI.liner(String.format(" [9] SEX             : %s", sex));
        UI.liner(String.format(" [10] COLLEGE CODE   : %s", collegeCode));
        UI.liner("");
        UI.option();
        UI.footer();
    }

    public static boolean isValidBirthDate(String birthDate) {
        try {
            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern("MM/dd/uuuu")
                            .withResolverStyle(
                                    ResolverStyle.STRICT);

            LocalDate date =
                    LocalDate.parse(birthDate, formatter);

            return !date.isAfter(LocalDate.now());
        } catch (DateTimeException e) {
            return false;
        }
    }
}