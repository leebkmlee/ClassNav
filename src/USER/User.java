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
    public static ArrayList<User> archiveStudent = new ArrayList<>();

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
                    //if (u instanceof Student && (Student) u )
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

        users.add(new Student("Alvarez", "Ariana", "A",
                "S0001@bulsu.edu.ph", "Student001!", "Street one, Balibago, Angeles City, Pampanga", 2000,
                "01/01/2003", 'M', "R", "BSIT"));
        users.add(new Student("Banot", "Josh Roden", "B",
                "S0002@bulsu.edu.ph", "Student002!", "Street two, Dolores, San Fernando, Pampanga", 2001,
                "02/02/2004", 'M', "IR", "BSIS"));
        users.add(new Student("Caparas", "Andrei Kyle", "B",
                "S0003@bulsu.edu.ph", "Student003!", "Street three, Sampaloc, Apalit, Pampanga", 2002,
                "03/03/2005", 'M', "LOA", "BLIS"));
        users.add(new Student("David", "Daphne", "D",
                "S0004@bulsu.edu.ph", "Student004!", "Street four, Dau, Mabalacat, Pampanga", 2003,
                "04/04/2006", 'F', "R", "BSCrim"));
        users.add(new Student("Espino", "Emmanuel", "E",
                "S0005@bulsu.edu.ph", "Student005!", "Street five, Poblacion, Baliwag, Bulacan", 2004,
                "05/05/2007", 'M', "IR", "BSTM"));
        users.add(new Student("Francisco", "Faith", "F",
                "S0006@bulsu.edu.ph", "Student006!", "Street six, Sto. Rosario, Malolos, Bulacan", 2005,
                "06/06/2003", 'F', "LOA", "BSHM"));
        users.add(new Student("Gonzales", "Gian", "G",
                "S0007@bulsu.edu.ph", "Student007!", "Street seven, Tabang, Guiguinto, Bulacan", 2006,
                "07/07/2004", 'M', "R", "BSN"));
        users.add(new Student("Hernandez", "Hannah", "H",
                "S0008@bulsu.edu.ph", "Student008!", "Street eight, Malhacan, Meycauyan, Bulacan", 2007,
                "08/08/2005", 'F', "IR", "BSIT"));
        users.add(new Student("Ignacio", "Ivan", "I",
                "S0009@bulsu.edu.ph", "Student009!", "Street nine, Turo, Bocaue, Bulacan", 2008,
                "09/09/2006", 'M', "LOA", "BSCE"));
        users.add(new Student("Jimenez", "Julia", "J",
                "S0010@bulsu.edu.ph", "Student010!", "Street ten, Poblacion, Plaridel, Bulacan", 2009,
                "10/10/2007", 'F', "R", "BSIT"));
        users.add(new Student("Luna", "Kenzo", "K",
                "S0011@bulsu.edu.ph", "Student011!", "Street eleven, Sta. Cruz, Pulilan, Bulacan", 2010,
                "11/11/2003", 'M', "IR", "BSIT"));
        users.add(new Student("Morales", "Lara", "L",
                "S0012@bulsu.edu.ph", "Student012!", "Street twelve, Loma de Gato, Marilao, Bulacan", 2011,
                "12/12/2004", 'F', "LOA", "BSElecEng"));
        users.add(new Student("Nicolas", "Mateo", "M",
                "S0013@bulsu.edu.ph", "Student013!", "Street thirteen, Binwangan, Obando, Bulacan", 2012,
                "01/13/2005", 'M', "R", "BSPsych"));
        users.add(new Student("Rural", "Elijah Rikiel", "P",
                "S0014@bulsu.edu.ph", "Student014!", "Street fourteen, Calizon, Calumpit, Bulacan", 2013,
                "02/14/2006", 'F', "IR", "BSBio"));
        users.add(new Student("Sabulao", "Josh Beckhamlee", "P",
                "S0015@bulsu.edu.ph", "Student015!", "Street fifteen, Poblacion, Bustos, Bulacan", 2014,
                "03/15/2007", 'M', "LOA", "BSIT"));


        users.add(new Faculty("Betong", "Maam", "A",
                "F0001@bulsu.edu.ph", "Faculty001!", "McArthur Highway, Bulihan, Malolos, Bulacan", 3000,
                "01/01/2000", 'F', "CICT", "ACTIVE"));
        users.add(new Faculty("Lanie", "Maam", "B",
                "F0002@bulsu.edu.ph", "Faculty002!", "Calumpit–Apalit Road, Poblacion, Calumpit, Bulacan", 3017,
                "02/17/2000", 'F', "CICT", "ACTIVE"));
        users.add(new Faculty("Ivy", "Maam", "C",
                "F0003@bulsu.edu.ph", "Faculty003!", "Plaridel Bypass Road, Lumang Bayan, Plaridel, Bulacan", 3070,
                "04/24/2000", 'F', "CICT", "ACTIVE"));
        users.add(new Faculty("Bautista", "Ramon", "E",
                "F0004@bulsu.edu.ph", "Faculty004!", "MacArthur Highway, Turo, Bocaue, Bulacan", 3004,
                "05/05/1982", 'M', "CICT", "ACTIVE"));
        users.add(new Faculty("Santiago", "Leah", "D",
                "F0005@bulsu.edu.ph", "Faculty005!", "McArthur Highway, Malhacan, Meycauayan, Bulacan", 3003,
                "04/04/1981", 'F', "CICT", "INACTIVE"));
        users.add(new Faculty("Clemente", "Sir", "E",
                "F0006@bulsu.edu.ph", "Faculty006!", "Paseo del Congreso Street, San Agustin, Malolos, Bulacan", 3000,
                "05/12/2004", 'M', "CSER", "ACTIVE"));
        users.add(new Faculty("Ortega", "Maria", "B",
                "F0007@bulsu.edu.ph", "Faculty007!", "Cagayan Valley Road, Poblacion, Malolos, Bulacan", 3001,
                "02/02/1979", 'F', "CICT", "ACTIVE"));
        users.add(new Faculty("Dizon", "Paolo", "C",
                "F0008@bulsu.edu.ph", "Faculty008!", "MacArthur Highway, Tabang, Guiguinto, Bulacan", 3002,
                "03/03/1980", 'M', "CICT", "INACTIVE"));
        users.add(new Faculty("Lopez", "Catherine", "F",
                "F0009@bulsu.edu.ph", "Faculty009!", "Plaridel–Baliwag Road, Poblacion, Plaridel, Bulacan", 3005,
                "06/06/1983", 'F', "CICT", "INACTIVE"));
        users.add(new Faculty("Alcantara", "Noel", "G",
                "F0010@bulsu.edu.ph", "Faculty010!", "Pulilan Regional Road, Poblacion, Pulilan, Bulacan", 3006,
                "07/07/1984", 'M', "COE", "ACTIVE"));
        users.add(new Faculty("Barrera", "Therese", "H",
                "F0011@bulsu.edu.ph", "Faculty011!", "MacArthur Highway, Loma de Gato, Marilao, Bulacan", 3007,
                "08/08/1985", 'F', "COE", "ACTIVE"));
        users.add(new Faculty("Cabrera", "Marco", "I",
                "F0012@bulsu.edu.ph", "Faculty012!", "Obando–Paco Road, Paliwas, Obando, Bulacan", 3008,
                "09/09/1986", 'M', "CON", "INACTIVE"));
        users.add(new Faculty("Delos Santos", "Isabel", "J",
                "F0013@bulsu.edu.ph", "Faculty013!", "Calumpit–Apalit Road, Poblacion, Calumpit, Bulacan", 3009,
                "10/10/1987", 'F', "CAFA", "ACTIVE"));
        users.add(new Faculty("Estrada", "Rafael", "K",
                "F0014@bulsu.edu.ph", "Faculty014!", "Bustos–Pandi Road, Poblacion, Bustos, Bulacan", 3010,
                "11/11/1988", 'M', "CBEA", "ACTIVE"));
        users.add(new Faculty("Fernandez", "Joy", "L",
                "F0015@bulsu.edu.ph", "Faculty015!", "MacArthur Highway, Balibago, Angeles City, Pampanga", 3011,
                "12/12/1989", 'F', "CICT", "INACTIVE"));

        users.add(new DepartmentHead("Maangas", "Andrei", "A",
                "DH0001@bulsu.edu.ph", "Head001!", "McArthur Highway, Malhacan, Meycauayan, Bulacan", 3000,
                "01/01/1970", 'M', "CAFA"));
        users.add(new DepartmentHead("Mercado", "Rina", "",
                "DH0002@bulsu.edu.ph", "Head002!", "Plaridel Bypass Road, Turo, Bocaue, Bulacan", 3001,
                "02/02/1971", 'F', "CAL"));
        users.add(new DepartmentHead("Manalo", "Victor", "C",
                "DH0003@bulsu.edu.ph", "Head003!", "Malhacan Road, Poblacion, Plaridel, Bulacan", 3002,
                "03/03/1972", 'M', "CBEA"));
        users.add(new DepartmentHead("Aquino", "Paula", "D",
                "DH0004@bulsu.edu.ph", "Head004!", "Crossing Street, Sta. Cruz, Pulilan, Bulacan", 3003,
                "04/04/1973", 'F', "CCJE"));
        users.add(new DepartmentHead("Bautista", "Renato", "E",
                "DH0005@bulsu.edu.ph", "Head005!", "Provincial Road, Loma de Gato, Marilao, Bulacan", 3004,
                "05/05/1974", 'M', "CHTM"));
        users.add(new DepartmentHead("Cortez", "Celina", "F",
                "DH0006@bulsu.edu.ph", "Head006!", "Capitol View Park, Binwangan, Obando, Bulacan", 3005,
                "06/06/1975", 'F', "CICT"));
        users.add(new DepartmentHead("Dominguez", "Dante", "G",
                "DH0007@bulsu.edu.ph", "Head007!", "M. Villarica Road, Calizon, Calumpit, Bulacan", 3006,
                "07/07/1976", 'M', "CIT"));
        users.add(new DepartmentHead("Enriquez", "Erika", "",
                "DH0008@bulsu.edu.ph", "Head008!", "Gov. Fortunato Halili Avenue, Poblacion, Bustos, Bulacan", 3007,
                "08/08/1977", 'F', "CLaw"));
        users.add(new DepartmentHead("Flores", "Felix", "I",
                "DH0009@bulsu.edu.ph", "Head009!", "Igulot Road, Balibago, Angeles City, Pampanga", 3008,
                "09/09/1978", 'M', "CON"));
        users.add(new DepartmentHead("Gomez", "Grace", "J",
                "DH0010@bulsu.edu.ph", "Head010!", "San Jose Road, Dolores, San Fernando, Pampanga", 3009,
                "10/10/1979", 'F', "COE"));
        users.add(new DepartmentHead("Hidalgo", "Hector", "K",
                "DH0011@bulsu.edu.ph", "Head011!", "Bagbaguin Road, Sampaloc, Apalit, Pampanga", 3010,
                "11/11/1980", 'M', "CPTED"));
        users.add(new DepartmentHead("Jimenez", "Irene", "L",
                "DH0012@bulsu.edu.ph", "Head012!", "Pandayan Road, Dau, Mabalacat, Pampanga", 3011,
                "12/12/1981", 'F', "CS"));
        users.add(new DepartmentHead("Lazaro", "Javier", "M",
                "DH0013@bulsu.edu.ph", "Head013!", "Perez Street, Poblacion, Baliwag, Bulacan", 3012,
                "01/13/1982", 'M', "CSER"));
        users.add(new DepartmentHead("Miranda", "Karen", "N",
                "DH0014@bulsu.edu.ph", "Head014!", "Rizal Street, Sto. Rosario, Malolos, Bulacan", 3013,
                "02/14/1983", 'F', "CSSP"));
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
            String address = u.getAddress();
            if (address.length() > 35) address = address.substring(0, 35) + " ...";

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

    public static void displayArchivedUsers(){
        UI.header("ARCHIVED USER ");

        for (User u : archiveStudent) {
            String userType = "";

            if (u instanceof Student) {
                userType = "Student";
            } else if (u instanceof Faculty) {
                userType = "Faculty";
            } else if (u instanceof DepartmentHead) {
                userType = "Department Head";
            }

            String user = String.format(
                    "  %-10s %-20s %-25s %-5s",
                    u.getUserID(),
                    u.getLastName() + ", " + u.getFirstName(),
                    u.getEmailAddress(),
                    userType
            );

            UI.liner(user);
        }
        UI.footer();
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

                            if (Program.findProgram(programCode) != null)
                                UI.print("Program Code does not exist");
                        } while (Program.findProgram(programCode) != null);

                    } else if (u instanceof Faculty || u instanceof DepartmentHead) {
                        do {
                            System.out.print("   College Code > ");
                            collegeCode = in.nextLine().trim().toUpperCase();

                            if (College.findCollege(collegeCode) == null)
                                UI.print("College Code does not exist");
                        } while (College.findCollege(collegeCode) == null);
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

                            if (Program.findProgram(programCode) != null)
                                UI.print("Program Code does not exist");
                        } while (Program.findProgram(programCode) != null);

                    } else if (u instanceof Faculty || u instanceof DepartmentHead) {
                        do {
                            System.out.print("   College Code > ");
                            collegeCode = in.nextLine().trim().toUpperCase();

                            if (College.findCollege(collegeCode) == null)
                                UI.print("College Code does not exist");
                        } while (College.findCollege(collegeCode) == null);
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
    public static void archiveUser() {
        String userID, choice;

        UI.print("ARCHIVE EXISTING USER");

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
            archiveStudent.add(u);
            users.remove(u);
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
}