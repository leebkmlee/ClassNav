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
        User.users.add(new Student("S001", "Sabulao", "Josh", "P",
                "S001@bulsu.edu.ph", "Student001!", "CSJDM, Bulacan", 3023,
                "06/03/2007", 'M', "R", "BSIT"));
        User.users.add(new Student("S002", "Rural", "Elijah", "R",
                "S002@bulsu.edu.ph", "Student002!", "Malolos, Bulacan", 3000,
                "04/20/2006", 'M', "R", "BSIT"));
        User.users.add(new Student("S003", "Caparas", "Kyle", "A",
                "S003@bulsu.edu.ph", "Student003!", "Meycauayan, Bulacan", 3020,
                "08/14/2005", 'M', "R", "BSIT"));
        User.users.add(new Student("S004", "Banot", "Josh", "R",
                "S004@bulsu.edu.ph", "Student004!", "Bocaue, Bulacan", 3018,
                "11/09/2006", 'M', "IR", "BSIT"));
        User.users.add(new Student("S005", "Reyes", "Andrea", "M",
                "S005@bulsu.edu.ph", "Student005!", "Guiguinto, Bulacan", 3015,
                "01/12/2005", 'F', "R", "BSIT"));
        User.users.add(new Student("S006", "Santos", "Miguel", "L",
                "S006@bulsu.edu.ph", "Student006!", "Plaridel, Bulacan", 3004,
                "03/22/2004", 'M', "R", "BSIT"));
        User.users.add(new Student("S007", "Cruz", "Bianca", "T",
                "S007@bulsu.edu.ph", "Student007!", "Baliwag, Bulacan", 3006,
                "07/30/2006", 'F', "R", "BSIT"));
        User.users.add(new Student("S008", "Garcia", "Nathan", "J",
                "S008@bulsu.edu.ph", "Student008!", "Paombong, Bulacan", 3001,
                "09/18/2005", 'M', "R", "BSIT"));
        User.users.add(new Student("S009", "Mendoza", "Sofia", "C",
                "S009@bulsu.edu.ph", "Student009!", "Calumpit, Bulacan", 3003,
                "12/05/2004", 'F', "IR", "BSIT"));
        User.users.add(new Student("S010", "Flores", "Daniel", "B",
                "S010@bulsu.edu.ph", "Student010!", "Hagonoy, Bulacan", 3002,
                "02/17/2005", 'M', "R", "BSIT"));
        User.users.add(new Student("S011", "Torres", "Nicole", "D",
                "S011@bulsu.edu.ph", "Student011!", "Malolos, Bulacan", 3000,
                "05/11/2006", 'F', "R", "BSIT"));
        User.users.add(new Student("S012", "Villanueva", "Gabriel", "S",
                "S012@bulsu.edu.ph", "Student012!", "San Rafael, Bulacan", 3008,
                "10/23/2004", 'M', "R", "BSIT"));
        User.users.add(new Student("S013", "Aquino", "Patricia", "E",
                "S013@bulsu.edu.ph", "Student013!", "Angat, Bulacan", 3012,
                "06/19/2005", 'F', "R", "BSIT"));
        User.users.add(new Student("S014", "Castillo", "Adrian", "F",
                "S014@bulsu.edu.ph", "Student014!", "Bustos, Bulacan", 3007,
                "03/08/2006", 'M', "R", "BSIT"));
        User.users.add(new Student("S015", "Domingo", "Jasmine", "K",
                "S015@bulsu.edu.ph", "Student015!", "Pulilan, Bulacan", 3005,
                "08/27/2004", 'F', "R", "BSIT"));
        User.users.add(new Student("S016", "Dela Cruz", "Marco", "R",
                "S016@bulsu.edu.ph", "Student016!", "Marilao, Bulacan", 3019,
                "01/16/2005", 'M', "IR", "BSIT"));
        User.users.add(new Student("S017", "Navarro", "Angela", "V",
                "S017@bulsu.edu.ph", "Student017!", "Obando, Bulacan", 3021,
                "04/02/2006", 'F', "R", "BSIT"));
        User.users.add(new Student("S018", "Pascual", "Enzo", "M",
                "S018@bulsu.edu.ph", "Student018!", "Balagtas, Bulacan", 3016,
                "07/07/2005", 'M', "R", "BSIT"));
        User.users.add(new Faculty("F001", "Kim", "Elijah", "",
                "F001@bulsu.edu.ph", "Faculty001!", "Malolos, Bulacan", 3000,
                "04/20/1988", 'M', "09293268930", "CICT"));
        User.users.add(new Faculty("F002", "Ortega", "Maria", "L",
                "F002@bulsu.edu.ph", "Faculty002!", "Guiguinto, Bulacan", 3015,
                "02/14/1985", 'F', "09171234567", "CICT"));
        User.users.add(new Faculty("F003", "Dizon", "Paolo", "R",
                "F003@bulsu.edu.ph", "Faculty003!", "Bocaue, Bulacan", 3018,
                "09/03/1982", 'M', "09281234567", "CICT"));
        User.users.add(new Faculty("F004", "Santiago", "Leah", "M",
                "F004@bulsu.edu.ph", "Faculty004!", "Meycauayan, Bulacan", 3020,
                "12/19/1987", 'F', "09391234567", "CICT"));
        User.users.add(new Faculty("F005", "Bautista", "Ramon", "C",
                "F005@bulsu.edu.ph", "Faculty005!", "Plaridel, Bulacan", 3004,
                "05/25/1980", 'M', "09451234567", "CICT"));
        User.users.add(new Faculty("F006", "Lopez", "Catherine", "A",
                "F006@bulsu.edu.ph", "Faculty006!", "Baliwag, Bulacan", 3006,
                "11/30/1984", 'F', "09561234567", "CICT"));
        User.users.add(new Admin("A001", "Maangas", "Andrei", "P",
                "A001@bulsu.edu.ph", "Admin001!", "Guiguinto, Bulacan", 3015,
                "09/17/1987", 'M', "System Administrator"));
        User.users.add(new Admin("A002", "Mercado", "Rina", "S",
                "A002@bulsu.edu.ph", "Admin002!", "Malolos, Bulacan", 3000,
                "03/11/1990", 'F', "ICTO Personnel"));
        User.users.add(new Admin("A003", "Manalo", "Victor", "D",
                "A003@bulsu.edu.ph", "Admin003!", "Bustos, Bulacan", 3007,
                "07/29/1986", 'M', "Room Scheduler"));
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

    public static void viewProfile(String userID) {
        User u = getUser(userID);
        if (u == null) {
            UI.print("User not found");
            return;
        }
        UI.header("USER PROFILE");
        System.out.printf("│%-" + innerWidth + "s│\n", "  ACCOUNT INFORMATION");
        UI.separator();
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("  USER ID        : %s", u.getUserID()));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("  FULL NAME      : %s", u.getUserName()));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("  EMAIL ADDRESS  : %s", u.getEmail()));
        UI.separator();
        System.out.printf("│%-" + innerWidth + "s│\n", "  PERSONAL INFORMATION");
        UI.separator();
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("  HOME ADDRESS   : %s", u.getAddress()));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("  POSTAL CODE    : %s", u.getPostalCode()));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("  BIRTHDATE      : %s", u.getBirthDate()));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("  SEX            : %s", u.getSex()));
        UI.footer();
    }

    public static void displayUsers() {
        UI.header("USER LIST");
        for (User u : users) {
            String userType = "";
            String additionalInfo = "";
            if (u.getUserID().charAt(0) == 'S') {
                Student s = (Student) u;
                userType = "Student";
                additionalInfo = s.getEnrollmentStatus();
            } else if (u.getUserID().charAt(0) == 'F') {
                Faculty f = (Faculty) u;
                userType = "Faculty";
                additionalInfo = f.getCollegeCode();
            } else if (u.getUserID().charAt(0) == 'A') {
                Admin a = (Admin) u;
                userType = "Admin";
                additionalInfo = a.getRole();
            }
            String user = String.format(
                    "  %-10s %-20s %-25s %-12s %-15s",
                    u.getUserID(),
                    u.getLastName() + ", " + u.getFirstName(),
                    u.getEmailAddress(),
                    userType,
                    additionalInfo
            );
            System.out.printf("│%-" + innerWidth + "s│\n", user);
        }
        UI.footer();
    }

    public void setLastName(String lastName) { this.lastName = lastName;
    }
    public void setFirstName(String firstName) { this.firstName = firstName;
    }
    public void setMiddleInitial(String middleInitial) { this.middleInitial = middleInitial;
    }
    public void setEmailAddress(String emailAddress) { this.emailAddress = emailAddress;
    }
    public void setPassword(String password) { this.password = password;
    }
    public void setAddress(String address) { this.address = address;
    }
    public void setPostalCode(int postalCode) { this.postalCode = postalCode;
    }
    public void setBirthDate(String birthDate) { this.birthDate = birthDate;
    }
    public void setSex(char sex) { this.sex = sex;
    }

    public String getLastName() { return lastName;
    }

    public String getFirstName() { return firstName;
    }

    public String getMiddleInitial() { return middleInitial;
    }

    public String getEmailAddress() { return emailAddress;
    }

    public void setUserID(String userID) { this.userID = userID;
    }
}