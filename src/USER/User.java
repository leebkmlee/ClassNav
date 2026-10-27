package USER;

import DATA.*;
import FUNCTION.*;
import UI.*;
import static UI.UI.*;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;

public abstract class User {

    private final String userID;
    private String userName, lastName, firstName, middleInitial, emailAddress, password, address, birthDate;
    private int postalCode; private char sex;
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

        setUserName();
    }

    public static void start() {
        String select;

        while (true) {
            UI.startPrint("ClassNav");
            select = in.nextLine().trim().toUpperCase();

            switch (select) {
                case "1":
                    String userID = Login.logIn();

                    if (!userID.equals("Ran out of attempts.")) {
                        User user = User.findUser(userID);
                        if (user != null) user.menu();
                        else UI.print("User not found");
                    }
                    break;
                    
                case "X":
                    System.out.println("   Exiting...");
                    return;

                default:
                    UI.print("Invalid Choice");
            }
        }
    }
    public abstract void menu();

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

    // GETTER METHODS
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
    public String getAddress() {
        return address;
    }
    public int getPostalCode() {
        return postalCode;
    }
    public String getBirthDate() {
        return birthDate;
    }
    public char getSex() {
        return sex;
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

    // SETTER METHODS
    public void setLastName(String lastName) {
        if (lastName == null || lastName.isBlank())
            throw new IllegalArgumentException("Last name cannot be blank");

        this.lastName = lastName.trim();
        setUserName();
    }
    public void setFirstName(String firstName) {
        if (firstName == null || firstName.isBlank())
            throw new IllegalArgumentException("First name cannot be blank");

        this.firstName = firstName.trim();
        setUserName();
    }
    public void setMiddleInitial(String middleInitial) {
        if (middleInitial == null) middleInitial = "";

        middleInitial = middleInitial.trim();

        if (middleInitial.length() > 1)
            throw new IllegalArgumentException("Middle initial must be one character");

        this.middleInitial = middleInitial;
        setUserName();
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
    private void setUserName() {
        if (middleInitial.isEmpty()) userName = lastName + ", " + firstName;
        else userName = lastName + ", " + firstName + " " + middleInitial + ".";
    }

    // FIND METHODS
    public static User findUser(String userID) {
        if (userID == null) return null;

        for (User u : users) {
            if (userID.trim().equalsIgnoreCase(u.getUserID()))
                return u;
        }

        return null;
    }

    // DISPLAY METHODS
    public static void viewProfile(String userID, boolean print) {
        User u = findUser(userID);

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
        if(print) UI.returnButton();
    }

    public static void locateRoom() {
        String roomCode;
        UI.print("Locate Room");
        do {
            UI.select("Room Code", false);
            roomCode = in.nextLine().trim();
            if (roomCode.equalsIgnoreCase("X")) {
                roomCode = "";
                break;
            }
            else if (roomCode.isEmpty()) UI.print("Room Code is required");
            else if (Room.findRoom(roomCode) == null) UI.print("Room does not exist");
        } while(roomCode.isEmpty() || Room.findRoom(roomCode) == null);

        Room r = Room.findRoom(roomCode);

        if (r == null) {
            UI.print("Room not found");
            return;
        }

        Building b = Building.findBuilding(r.getBuildingCode());

        if (b == null) {
            UI.print("Building not found");
            return;
        }

        UI.header("ROOM INFORMATION");
        UI.liner("  ROOM DETAILS");
        UI.separator();
        UI.liner(String.format("  ROOM CODE         : %s", r.getRoomCode()));
        UI.liner(String.format("  ROOM DESCRIPTION  : %s", r.getRoomDescription()));
        UI.separator();
        UI.liner("  ROOM LOCATION");
        UI.separator();
        UI.liner(String.format("  BUILDING NAME     : %s", b.getBuildingName()));
        UI.liner(String.format("  FLOOR NUMBER      : %s", Room.getFloorText(r.getFloorNumber())));
        UI.footer();
        UI.returnButton();
    }
    public static void viewAvailableRooms() {

        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        String currentDate = today.format(DATE_FORMAT);
        String currentTime = now.format(TIME_FORMAT);
        String checkEndTime = now.plusMinutes(1).format(TIME_FORMAT);

        ArrayList<Room> availableRooms = new ArrayList<>();

        for (Room room : Room.rooms) {
            if (room == null)
                continue;

            if (Room.verifyAvailability(
                    room.getRoomCode(),
                    currentDate,
                    currentTime,
                    checkEndTime)) {
                availableRooms.add(room);
            }
        }

        UI.header("ROOM AVAILABILITY");

        UI.liner("  DATE  : " + currentDate);
        UI.liner("  TIME  : " + currentTime);
        UI.separator();

        UI.liner("  AVAILABLE ROOMS : " + availableRooms.size());

        if (availableRooms.isEmpty()) {
            UI.liner("");
            UI.liner("       No rooms available right now.");
            UI.liner("       Please check again later.");
            UI.liner("");
        } else {
            int count = 1;

            for (Room room : availableRooms) {
                UI.separator();
                UI.liner(String.format("  [%02d] %s", count++, room.getRoomCode()) + " | " + room.getCollegeCode());
                UI.liner(String.format("       %-10s : %s", "ROOM DESCRIPTION ", fitText(room.getRoomDescription(), 52)));
                UI.liner(String.format("       %-10s : %s", "TYPE             ", fitText(room.getRoomType(), 45)));
                UI.liner(String.format("       %-10s : %d seats", "CAPACITY         ", room.getCapacity()));
                UI.liner(String.format("       %-10s : %s", "LOCATION         ", Room.getFloorText(room.getFloorNumber())));
            }
        }

        UI.footer();
        UI.returnButton();
    }

    // VALIDATION METHODS
    public static boolean isValidEmail(String userID, String emailAddress) {
        User u = findUser(userID);
        return u != null && emailAddress != null && emailAddress.trim().equalsIgnoreCase(u.getEmail());
    }
    public static boolean isValidPassword(String userID, String password) {
        User u = findUser(userID);

        return u != null && password != null
                && password.equals(u.getPassword());
    }
    public static boolean isDuplicateName(String userName) {
        if (userName == null) return false;

        for (User u : users) {
            if (userName.trim().equalsIgnoreCase(u.getUserName()))
                return true;
        }

        return false;
    }
    public static boolean isDuplicateEmail(String emailAddress) {
        if (emailAddress == null) return false;

        for (User u : users) {
            if (emailAddress.trim().equalsIgnoreCase(u.getEmail()))
                return true;
        }

        return false;
    }
    static boolean isValidBirthDate(String birthDate) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/uuuu").withResolverStyle(ResolverStyle.STRICT);
            LocalDate date = LocalDate.parse(birthDate, formatter);
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

    // EDIT USER
    public static void editUser(String userID) {
        String lastName, firstName, middleInitial, emailAddress, password, address, birthDate, choice, userName;
        String enrollmentStatus = "", programCode = "", collegeCode = "", employmentStatus = "";
        int postalCode; char sex; boolean valid, submit = false;

        User u = User.findUser(userID);

        if (u == null) {
            UI.print("User not found");
            return;
        }

        userID = u.getUserID();
        lastName = u.getLastName();
        firstName = u.getFirstName();
        middleInitial = u.getMiddleInitial();
        emailAddress = u.getEmail();
        password = u.getPassword();
        address = u.getAddress();
        postalCode = u.getPostalCode();
        birthDate = u.getBirthDate();
        sex = u.getSex();

        if (u instanceof Student s) {
            enrollmentStatus = s.getEnrollmentStatus();
            programCode = s.getProgramCode();
        }
        else if (u instanceof Faculty f) collegeCode = f.getCollegeCode();
        else if (u instanceof DepartmentHead dh) collegeCode = dh.getCollegeCode();
        else {
            UI.print("Invalid User Type");
            return;
        }

        while (!submit) {
            if (u instanceof Student) {
                userManage("EDIT", userID, lastName, firstName, middleInitial, emailAddress, password,
                        address, postalCode, birthDate, sex, enrollmentStatus, programCode, false);
            }
            else if (u instanceof Faculty) {
                userManage("EDIT", userID, lastName, firstName, middleInitial, emailAddress, password,
                        address, postalCode, birthDate, sex, collegeCode, employmentStatus, false);
            }
            else {
                userManage("EDIT", userID, lastName, firstName, middleInitial, emailAddress, password, address,
                        postalCode, birthDate, sex, collegeCode, false);
            }

            do {
                select("Select", false);
                choice = in.nextLine().trim().toUpperCase();
                if (choice.equalsIgnoreCase("X")) return;
                else if (!choice.matches("([1-8]|X|/)") || choice.isEmpty()) print("Invalid Input");
            } while (!choice.matches("([1-8]|X|/)"));

            switch (choice) {
                case "1":
                    do {
                        UI.select("Enter Last Name", true);
                        lastName = in.nextLine().trim();
                        if (lastName.equalsIgnoreCase("X")) {
                            lastName = u.getLastName();
                            break;
                        }
                        else if (lastName.isEmpty()) UI.print("Last Name is required");
                        else if (!lastName.matches("^[a-zA-Z]+$")) UI.print("Special characters are not allowed");
                    } while (lastName.isEmpty() || !lastName.matches("^[a-zA-Z]+$"));
                    break;

                case "2":
                    do {
                        UI.select("Enter First Name", true);
                        firstName = in.nextLine().trim();
                        if (firstName.equalsIgnoreCase("X")) {
                            firstName = u.getFirstName();
                            break;
                        }
                        else if (firstName.isEmpty()) UI.print("First Name is required");
                        else if (!firstName.matches("^[a-zA-Z]+$")) UI.print("Special characters are not allowed");
                    } while (firstName.isEmpty() || !firstName.matches("^[a-zA-Z]+$"));
                    break;

                case "3":
                    do {
                        UI.select("Enter Middle Initial", true);
                        middleInitial = in.nextLine().trim();

                        if (middleInitial.equalsIgnoreCase("X")) {
                            middleInitial = u.getMiddleInitial();
                            break;
                        }
                        if (middleInitial.endsWith("."))
                            middleInitial = middleInitial.replace(".", "");

                        if (middleInitial.length() > 2) UI.print("Enter up to two letters only");
                        else if (!middleInitial.matches("^[a-zA-Z]+$")) UI.print("Special characters are not allowed");
                    } while (middleInitial.length() > 2 || !middleInitial.matches("^[a-zA-Z]+$"));
                    break;

                case "4":
                    valid = false;

                    do {
                        UI.select("Enter Password", true);
                        password = in.nextLine();

                        if (password.equalsIgnoreCase("X")) {
                            password = u.getPassword();
                            break;
                        }
                        else if (password.trim().isEmpty())
                            UI.print("Password is required");
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

                case "5":
                    String street, barangay, city, province;

                    do {
                        UI.select(" Enter Street", true);
                        street = in.nextLine().trim();
                        if (street.equalsIgnoreCase("X")) return;
                        else if (street.isEmpty()) UI.print("Street is required");
                        else if (!street.matches("^[a-zA-Z]+$")) UI.print("Special characters are not allowed");
                    } while (street.isEmpty() || !street.matches("^[a-zA-Z]+$"));

                    do {
                        UI.select(" Enter Barangay", true);
                        barangay = in.nextLine().trim();
                        if (barangay.equalsIgnoreCase("X")) return;
                        else if (barangay.isEmpty()) UI.print("Barangay is required");
                        else if (!barangay.matches("^[a-zA-Z]+$")) UI.print("Special characters are not allowed");
                    } while (barangay.isEmpty() || !barangay.matches("^[a-zA-Z]+$"));

                    do {
                        UI.select(" Enter City", true);
                        city = in.nextLine().trim();
                        if (city.equalsIgnoreCase("X")) return;
                        else if (city.isEmpty()) UI.print("City is required");
                        else if (!city.matches("^[a-zA-Z]+$")) UI.print("Special characters are not allowed");
                    } while (city.isEmpty() || !city.matches("^[a-zA-Z]+$"));

                    do {
                        UI.select(" Enter Province", true);
                        province = in.nextLine().trim();
                        if (province.equalsIgnoreCase("X")) return;
                        else if (province.isEmpty()) UI.print("Province is required");
                        else if (!province.matches("^[a-zA-Z]+$")) UI.print("Special characters are not allowed");
                    } while (province.isEmpty() || !province.matches("^[a-zA-Z]+$"));

                    address = street + ", " + barangay + ", " + city + ", " + province;
                    break;

                case "6":
                    String postalInput;
                    do {
                        UI.select("Enter Postal Code", true);
                        postalInput = in.nextLine().trim();

                        if (postalInput.equalsIgnoreCase("X")) {
                            postalCode = u.getPostalCode();
                            break;
                        }
                        else if (!postalInput.matches("[0-9]{4}")) UI.print("Postal code must be 4 digits");
                        else postalCode = Integer.parseInt(postalInput);
                    } while (!postalInput.matches("[0-9]{4}"));
                    break;

                case "7":
                    do {
                        UI.select("Enter Birthdate (MM/DD/YYYY)", true);
                        birthDate = in.nextLine().trim();

                        if (birthDate.equalsIgnoreCase("X")) {
                            birthDate = u.getBirthDate();
                            break;
                        }
                        else if (!isValidBirthDate(birthDate)) UI.print("Invalid birthdate");
                    } while (!isValidBirthDate(birthDate));
                    break;

                case "8":
                    String sexInput;

                    do {
                        UI.select("Enter Sex (M/F)", true);
                        sexInput = in.nextLine().trim().toUpperCase();

                        if (sexInput.equalsIgnoreCase("X")) {
                            sexInput = String.valueOf(u.getSex());
                            break;
                        }
                        if (!sexInput.matches("[MF]")) UI.print("Invalid Input");
                    } while (!sexInput.matches("[MF]"));

                    sex = sexInput.charAt(0);
                    break;

                case "/":
                    userName = lastName + ", " + firstName
                            + (middleInitial.isEmpty() ? "" : " " + middleInitial + ".");

                    if (isDuplicateName(userName)
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
                    User.viewProfile(u.getUserID(), true);
                    submit = true;
                    break;

                case "X":
                    UI.print("User edit cancelled");
                    submit = true;
                    break;
            }
        }
    }

    public static void userManage(String text, String userID, String lastName,
                                  String firstName, String middleInitial, String emailAddress,
                                  String password, String address, int postalCode, String birthDate,
                                  char sex, String input1, String input2, boolean isDeptHead) {

        User u = User.findUser(userID);

        if (u == null) {
            UI.print("User not found");
            return;
        }

        String location = address;
        if (location.length() > 38) location = address.substring(0, 38) + "...";
        String hidePass = password.replaceAll(".", "*");

        UI.header(text.toUpperCase() + ": USER INFORMATION");
        UI.liner(String.format("  USER ID                : %s", userID));
        UI.separator();
        UI.liner(String.format(" [1] LAST NAME           : %s", lastName));
        UI.liner(String.format(" [2] FIRST NAME          : %s", firstName));
        UI.liner(String.format(" [3] MIDDLE INITIAL      : %s", middleInitial));
        UI.liner(String.format("     EMAIL ADDRESS       : %s", emailAddress));
        UI.liner(String.format(" [4] PASSWORD            : %s", hidePass));
        UI.liner(String.format(" [5] ADDRESS             : %s", location));
        UI.liner(String.format(" [6] POSTAL CODE         : %s", postalCode));
        UI.liner(String.format(" [7] BIRTHDATE           : %s", birthDate));
        UI.liner(String.format(" [8] SEX                 : %s", sex));
        if (userID.startsWith("S")) {
            if (isDeptHead) UI.liner(String.format(" [9] ENROLLMENT STATUS   : %s", input1));
            else UI.liner(String.format("     ENROLLMENT STATUS   : %s", input1));
            if (isDeptHead) UI.liner(String.format(" [10] PROGRAM CODE        : %s", input2));
            else UI.liner(String.format("     PROGRAM CODE        : %s", input2));
        }
        else {
            if (isDeptHead) UI.liner(String.format(" [9] COLLEGE CODE        : %s", input1));
            else UI.liner(String.format("     COLLEGE CODE        : %s", input1));
            if (isDeptHead) UI.liner(String.format(" [10] EMPLOYMENT STATUS  : %s", input2));
            else UI.liner(String.format("     EMPLOYMENT STATUS  : %s", input2));
        }
        UI.liner("");
        UI.option();
        UI.footer();
    }
    public static void userManage(String text, String userID, String lastName,
                                  String firstName, String middleInitial, String emailAddress,
                                  String password, String address, int postalCode, String birthDate,
                                  char sex, String collegeCode, boolean isDeptHead) {

        User u = User.findUser(userID);

        if (u == null) {
            UI.print("User not found");
            return;
        }

        String location = address;
        if (location.length() > 38) location = address.substring(0, 38) + "...";
        String hidePass = password.replaceAll(".", "*");

        UI.header(text.toUpperCase() + ": USER INFORMATION");
        UI.liner(String.format("  USER ID            : %s", userID));
        UI.separator();
        UI.liner(String.format(" [1] LAST NAME       : %s", lastName));
        UI.liner(String.format(" [2] FIRST NAME      : %s", firstName));
        UI.liner(String.format(" [3] MIDDLE INITIAL  : %s", middleInitial));
        UI.liner(String.format("     EMAIL ADDRESS   : %s", emailAddress));
        UI.liner(String.format(" [4] PASSWORD        : %s", hidePass));
        UI.liner(String.format(" [5] ADDRESS         : %s", location));
        UI.liner(String.format(" [6] POSTAL CODE     : %s", postalCode));
        UI.liner(String.format(" [7] BIRTHDATE       : %s", birthDate));
        UI.liner(String.format(" [8] SEX             : %s", sex));
        if (isDeptHead) UI.liner(String.format(" [9] COLLEGE CODE    : %s", collegeCode));
        else UI.liner(String.format("     COLLEGE CODE    : %s", collegeCode));
        UI.liner("");
        UI.option();
        UI.footer();
    }

    public void viewMyNotifications() {
        ArrayList<Notification> received =
                Notification.getNotificationsByRecipient(getUserID());

        UI.header("MY NOTIFICATIONS");
        UI.liner("  USER ID  : " + getUserID());
        UI.liner("  TOTAL    : " + received.size() + " notification(s)");

        if (received.isEmpty()) {
            UI.separator();
            UI.liner("");
            UI.liner("                        You're all caught up!");
            UI.liner("                       No notifications found.");
            UI.liner("");
        } else {
            int count = 1;
            int width = UI.INNERWIDTH - 15;

            for (Notification n : received) {
                UI.separator();
                UI.liner(String.format("  [%02d] NOTIFICATION #%s", count++, n.getNotificationID()));
                UI.liner(String.format("  %-10s : %.55s", "FROM", n.getSenderID() == null ? "-" : n.getSenderID()));
                UI.liner(String.format("  %-10s : %.55s", "ROOM", n.getRoomCode() == null ? "-" : n.getRoomCode()));
                UI.liner(String.format("  %-10s : %.55s", "COURSE", n.getCourseCode() == null ? "-" : n.getCourseCode()));
                UI.liner(String.format("  %-10s : %.55s", "BUILDING", n.getBuildingName() == null ? "-" : n.getBuildingName()));
                UI.liner(String.format("  %-10s : %.55s", "DAY / TIME",
                        (n.getDay() == null ? "-" : n.getDay()) + " | "
                                + (n.getStartTime() == null ? "-" : n.getStartTime()) + " - "
                                + (n.getEndTime() == null ? "-" : n.getEndTime())));

                UI.separator();
                UI.liner("  MESSAGE");

                String message = n.getMessage() == null ? "-" :
                        n.getMessage().replace('\n', ' ')
                                .replace('\r', ' ').trim();

                if (message.isEmpty())
                    message = "-";

                while (!message.isEmpty()) {
                    int end = Math.min(message.length(), width);
                    if (end < message.length()) {
                        int space = message.lastIndexOf(' ', end - 1);
                        if (space > 0)
                            end = space;
                    }

                    UI.liner("  " + message.substring(0, end).trim());
                    message = message.substring(end).trim();
                }
            }
        }

        UI.footer();
        UI.returnButton();
    }

    public static void searchID() {
        String inputID;

        UI.print("Search ID");

        do {
            UI.select("Enter ID", false);
            inputID = in.nextLine();

            if (inputID.equalsIgnoreCase("X")) return;
            else if (inputID.isEmpty()) UI.print("Invalid Input");
            else if (inputID.startsWith("BLD")) {
                if (Building.findBuilding(inputID) == null) UI.print("Building does not exist");
                else Building.displayBuilding(inputID, true);
            }
            else if (inputID.startsWith("CAN")) {
                if (CancelledSchedule.findException(inputID) == null) UI.print("Cancelled Schedule does not exist");
                else CancelledSchedule.displayCancellation(inputID);
            }
            else if (inputID.startsWith("COF")) {
                if (CourseOffering.findOffering(inputID) == null) UI.print("Course Offering does not exist");
                else CourseOffering.displayOffering(inputID, true);
            }
            else if (inputID.startsWith("CR")) {
                if (CourseRegistration.findRegistration(inputID) == null) UI.print("Course Registration does not exist");
                else CourseRegistration.displayRegistration(inputID, true);
            }
            else if (inputID.startsWith("CUR")) {
                if (Curriculum.findCurriculum(inputID) == null) UI.print("Curriculum does not exist");
                else Curriculum.displayCurriculum(inputID,true);
            }
            else if (inputID.startsWith("MS")) {
                if (MakeUpSchedule.findMakeUpID(inputID) == null) UI.print("Make Up Schedule does not exist");
                else MakeUpSchedule.displayMakeUpSchedule(inputID);
            }
            else if (inputID.startsWith("RM")) {
                if (Room.findRoom(inputID) == null) UI.print("Room does not exist");
                else Room.displayRoom(inputID,true);
            }
            else if (inputID.startsWith("SCH")) {
                if (Schedule.findSchedule(inputID) == null) UI.print("Schedule does not exist");
                else Schedule.displaySchedule(inputID);
            }
            else if (inputID.startsWith("SEC")) {
                if (Section.findSection(inputID) == null) UI.print("Section does not exist");
                else Section.displaySection(inputID);
            }
            else if (inputID.startsWith("NOT")) {
                if (Notification.findNotification(inputID) == null) UI.print("Notification does not exist");
                else Notification.displayNotification(inputID, true);
            }
            else if (inputID.startsWith("RR")) {
                if (Request.findRequest(inputID) == null) UI.print("Request does not exist");
                else Request.displayRequest(inputID,true);
            }
            else if (inputID.startsWith("DH")) {
                if (DepartmentHead.findDepartmentHead(inputID) == null) UI.print("Department Head does not exist");
                else DepartmentHead.viewProfile(inputID, true);
            }
            else if (inputID.matches("[A-Z]+\\d{3}")) {
                if (Course.findCourse(inputID) == null) UI.print("Course does not exist");
                else Course.displayCourse(inputID, true);
            }
            else if (inputID.startsWith("F")) {
                if (Faculty.findFaculty(inputID) == null) UI.print("Faculty does not exist");
                else Faculty.viewProfile(inputID, true);
            }
            else if (inputID.startsWith("S")) {
                if (Student.findStudent(inputID) == null) UI.print("Student does not exist");
                else Student.viewProfile(inputID, true);
            }
            else if ((inputID.startsWith("BS") || inputID.startsWith("B"))) {
                if (Program.findProgram(inputID) == null) UI.print("Program does not exist");
                else Program.displayProgram(inputID);
            }
            else if (inputID.startsWith("C")) {
                if (College.findCollege(inputID) == null) UI.print("College does not exist");
                else College.displayCollege(inputID, true);
            }
            else UI.print("ID unrecognized");
        } while (inputID.isEmpty());
    }
}