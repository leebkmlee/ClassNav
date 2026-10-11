package USER;
import DATA.Building;
import DATA.Course;
import DATA.Program;
import DATA.Room;
import LOGIN.Signup;
import UI.*;
import java.util.Scanner;

import static UI.UI.INNERWIDTH;

public class Admin extends User{

    private String role;

    public Admin (String userID, String lastName, String firstName, String middleInitial, String emailAddress,
                  String password, String address, int postalCode, String birthDate, char sex, String role) {
        super(userID, lastName, firstName, middleInitial, emailAddress, password, address, postalCode, birthDate, sex);
        this.role = role;
    }

    static Scanner in = new Scanner(System.in);

    public static void manageRooms() {
        int choice = -1; String input = "";
        UI.manageUI("Room");
        do {
            System.out.print("  Select > ");
            try {
                input = in.nextLine().trim();
                if (input.isEmpty()) {
                    UI.print("Invalid Input");
                    continue;
                }
                choice = Integer.parseInt(input);
                if (!(choice >= 1 && choice <= 3) && choice != 0) UI.print("Invalid Input");
            } catch (NumberFormatException e) {
                UI.print("Invalid Input");
                choice = -1;
            }
        } while (!(choice >= 1 && choice <= 4) && choice != 0);

        switch (choice) {
            case 1:
                createRoom();
                break;
            case 2:
                editRoom();
                break;
            case 3:
                deleteRoom();
                break;
            case 4:
                Room.displayRooms();
                break;
            case 0:
                // menu
        }
    }

    private static void createRoom() {
        String roomCode = ""; String roomDescription = ""; String buildingCode = ""; String choice = "";
        int floorNumber = -1; boolean submit = false;

        while (!submit) {
            UI.header("CREATE NEW ROOM");
            System.out.printf("│%-" + INNERWIDTH + "s│%n", " [1] Room Code         : " + roomCode);
            System.out.printf("│%-" + INNERWIDTH + "s│%n", " [2] Room Description  : " + roomDescription);
            if (floorNumber == -1) System.out.printf("│%-" + INNERWIDTH + "s│%n", " [3] Floor Number      : ");
            else if (floorNumber == 1) System.out.printf("│%-" + INNERWIDTH + "s│%n", " [3] Floor Number      : " +
                    floorNumber + "st Floor");
            else if (floorNumber == 2) System.out.printf("│%-" + INNERWIDTH + "s│%n", " [3] Floor Number      : " +
                    floorNumber + "nd Floor");
            else if (floorNumber == 3) System.out.printf("│%-" + INNERWIDTH + "s│%n", " [3] Floor Number      : " +
                    floorNumber + "rd Floor");
            else System.out.printf("│%-" + INNERWIDTH + "s│%n", " [3] Floor Number      : " + floorNumber + "th Floor");
            System.out.printf("│%-" + INNERWIDTH + "s│%n", " [4] Building Code     : " + buildingCode);
            System.out.printf("│%-" + INNERWIDTH + "s│%n", " ");
            UI.option();
            UI.footer();

            do {
                System.out.print("  Select > ");
                choice = in.nextLine();
                if (!choice.matches("[1-4X]") && !choice.equals("/")) UI.print("Invalid Choice");
            } while (!choice.matches("[1-4X]") && !choice.equals("/"));

            switch (choice) {
                case "1":
                    do {
                        System.out.print("   Enter Room Code > ");
                        roomCode = in.nextLine();
                        if (Room.existRoomCode(roomCode)) UI.print("Room Code already exists");
                    } while(Room.existRoomCode(roomCode));
                    break;
                case "2":
                    do {
                        System.out.print("   Enter Room Description > ");
                        roomDescription = in.nextLine();
                        if (Room.existRoomDesc(roomDescription)) UI.print("Room Description already exists");
                    } while(Room.existRoomDesc(roomDescription));
                    break;
                case "3":
                    do {
                        System.out.print("   Enter Floor Number > ");
                        try {
                            String input = in.nextLine();
                            floorNumber = Integer.parseInt(input);
                            if (!(floorNumber >= 1 && floorNumber <= 4)) UI.print("Invalid Floor Number");
                        } catch (NumberFormatException e) {
                            UI.print("Invalid Floor Number");
                            floorNumber = -1;
                        }
                    } while (!(floorNumber >= 1 && floorNumber <= 4));
                    break;
                case "4":
                    do {
                        System.out.print("   Enter Building Code > ");
                        buildingCode = in.nextLine();
                        if(!Building.verifyBuilding(buildingCode)) UI.print("Building Code does not exist");
                    } while(!Building.verifyBuilding(buildingCode));
                    break;
                case "/":
                    if (roomCode.isEmpty() || roomDescription.isEmpty() || floorNumber == -1 || buildingCode.isEmpty())
                        UI.print("Details are incomplete");
                    else {
                        Room.rooms.add(new Room(roomCode, roomDescription, floorNumber, buildingCode));
                        UI.print("Room successfully created");
                        roomManage("ADD", roomCode, roomDescription, floorNumber, buildingCode);
                        submit = true;
                    }
                    break;
                case "X":
                    manageRooms();
                    submit = true;
                    break;
            }
        }

    }

    private static void editRoom() {
        String roomCode = ""; String roomDescription = ""; String buildingCode = "";
        int floorNumber = -1; boolean submit = false; String input; String choice;

        UI.print("EDIT EXISTING ROOM");
        do {
            System.out.print("  Enter Room Code > ");
            input = in.nextLine();
            if (!Room.existRoomCode(input)) UI.print("Room does not exist");
        } while(!Room.existRoomCode(input));

        Room r = Room.findRoom(input);
        if (r == null) {
            UI.print("Room not found");
            return;
        }
        roomCode = Room.getRoom(r);
        roomDescription = Room.getRoomDescription(r);
        floorNumber = Room.getFloorNumber(r);
        buildingCode = Room.getBuildingCode(r);

        while (!submit) {
            roomManage("EDIT", roomCode, roomDescription, floorNumber, buildingCode);

            do {
                System.out.print("  Select > ");
                choice = in.nextLine();
                if (!choice.matches("[1-4X]") && !choice.equals("/")) UI.print("Invalid Input");
            } while (!choice.matches("[1-4X]") && !choice.equals("/"));

            switch(choice) {
                case "1":
                    do {
                        System.out.print("   Enter Room Code > ");
                        roomCode = in.nextLine();
                        if (Room.existRoomCode(roomCode) && !roomCode.equalsIgnoreCase(r.getRoomCode()))
                            UI.print("Room Code already exists");
                    } while(Room.existRoomCode(roomCode) && !roomCode.equalsIgnoreCase(r.getRoomCode()));
                    break;
                case "2":
                    do {
                        System.out.print("   Enter Room Description > ");
                        roomDescription = in.nextLine();
                        if (Room.existRoomDesc(roomDescription)  && !roomDescription.equalsIgnoreCase(Room.getRoomDescription(r)))
                            UI.print("Room Description already exists");
                    } while(Room.existRoomDesc(roomDescription) && !roomDescription.equalsIgnoreCase(Room.getRoomDescription(r)));
                    break;
                case "3":
                    do {
                        System.out.print("   Enter Floor Number > ");
                        try {
                            input = in.nextLine();
                            floorNumber = Integer.parseInt(input);
                            if (!(floorNumber >= 1 && floorNumber <= 4)) UI.print("Invalid Floor Number");
                        } catch (NumberFormatException e) {
                            UI.print("Invalid Floor Number");
                            floorNumber = -1;
                        }
                    } while (!(floorNumber >= 1 && floorNumber <= 4));
                    break;
                case "4":
                    do {
                        System.out.print("   Enter Building Code > ");
                        buildingCode = in.nextLine();
                        if(!Building.verifyBuilding(buildingCode)) UI.print("Building Code does not exist");
                    } while(!Building.verifyBuilding(buildingCode));
                    break;
                case "/":
                    UI.print("Room successfully edited");
                    r.setRoomCode(roomCode); r.setRoomDescription(roomDescription);
                    r.setFloorNumber(floorNumber); r.setBuildingCode(buildingCode);
                    Room.displayRoom(r.getRoomCode());
                    submit = true;
                    break;
                case "X":
                    submit = true;
                    break;
            }
        }
    }

    private static void deleteRoom() {
        String input; String choice; String roomCode; String roomDescription; int floorNumber; String buildingCode;

        UI.print("DELETE EXISTING ROOM");

        do {
            System.out.print("  Enter Room Code > ");
            input = in.nextLine();
            if (!Room.existRoomCode(input)) UI.print("Room does not exist");
        } while(!Room.existRoomCode(input));

        Room r = Room.findRoom(input);
        if (r == null) {
            UI.print("Room not found");
            return;
        }

        roomCode = Room.getRoom(r);
        roomDescription = Room.getRoomDescription(r);
        floorNumber = Room.getFloorNumber(r);
        buildingCode = Room.getBuildingCode(r);

        roomManage("DELETE", roomCode, roomDescription, floorNumber, buildingCode);
        do {
            System.out.print("  Confirm Room Deletion? (Y/N) > ");
            choice = in.nextLine().toUpperCase();
            if (!choice.matches("[YN]")) UI.print("Invalid Input");
        } while(!choice.matches("[YN]"));

        if (choice.equals("Y")) {
            Room.rooms.remove(r);
            UI.print("Room successfully deleted");
        }
        else manageRooms();
    }

    private static void roomManage(String text, String roomCode, String roomDescription, int floorNumber, String buildingCode) {
        UI.header(text.toUpperCase() + ": ROOM INFORMATION");
        System.out.printf("│%-" + innerWidth + "s│\n", String.format(" [1] ROOM CODE         : %s", roomCode));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format(" [2] ROOM DESCRIPTION  : %s", roomDescription));
        String floorText;
        if (floorNumber == 1) floorText = "1st Floor";
        else if (floorNumber == 2) floorText = "2nd Floor";
        else if (floorNumber == 3) floorText = "3rd Floor";
        else floorText = floorNumber + "th Floor";
        System.out.printf("│%-" + innerWidth + "s│\n", String.format(" [3] FLOOR NUMBER      : %s", floorText));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format(" [4] BUILDING CODE     : %s", buildingCode));
        System.out.printf("│%-" + innerWidth + "s│\n", "");
        UI.option();
        UI.footer();
    }

    public static void manageCourses() {
        int choice = -1; String input = "";
        UI.manageUI("Course");
        do {
            System.out.print("  Select > ");
            try {
                input = in.nextLine().trim();
                if (input.isEmpty()) {
                    UI.print("Invalid Input");
                    continue;
                }
                choice = Integer.parseInt(input);
                if (!(choice >= 1 && choice <= 4) && choice != 0) UI.print("Invalid Input");
            } catch (NumberFormatException e) {
                UI.print("Invalid Input");
                choice = -1;
            }
        } while (!(choice >= 1 && choice <= 4) && choice != 0);

        switch (choice) {
            case 1:
                createCourse();
                break;
            case 2:
                editCourse();
                break;
            case 3:
                deleteCourse();
                break;
            case 4:
                Course.displayCourses();
                break;
            case 0:
                // menu
        }
    }

    private static void createCourse() {
        String courseCode = ""; String courseDescription = ""; String programCode = ""; String choice = "";
        int creditUnits = -1; boolean submit = false;

        while (!submit) {
            courseManage("CREATE", courseCode, courseDescription, creditUnits, programCode);

            do {
                System.out.print("  Select > ");
                choice = in.nextLine();
                if (!choice.matches("[1-4X]") && !choice.equals("/")) UI.print("Invalid Choice");
            } while (!choice.matches("[1-4X]") && !choice.equals("/"));

            switch (choice) {
                case "1":
                    do {
                        System.out.print("   Enter Course Code > ");
                        courseCode = in.nextLine();
                        if (Course.existCourseCode(courseCode)) UI.print("Course Code already exists");
                    } while(Course.existCourseCode(courseCode));
                    break;
                case "2":
                    do {
                        System.out.print("   Enter Course Description > ");
                        courseDescription = in.nextLine();
                        if (Course.existCourseDesc(courseDescription)) UI.print("Course Description already exists");
                    } while(Course.existCourseDesc(courseDescription));
                    break;
                case "3":
                    do {
                        System.out.print("   Enter Credit Units > ");
                        try {
                            String input = in.nextLine();
                            creditUnits = Integer.parseInt(input);
                            if (creditUnits != 2 && creditUnits != 3) UI.print("Invalid Credit Units");
                        } catch (NumberFormatException e) {
                            UI.print("Invalid Credit Units");
                            creditUnits = -1;
                        }
                    } while (creditUnits != 2 && creditUnits != 3);
                    break;
                case "4":
                    do {
                        System.out.print("   Enter Program Code > ");
                        programCode = in.nextLine();
                        if(!Program.verifyProgramCode(programCode)) UI.print("Program Code does not exist");
                    } while(!Program.verifyProgramCode(programCode));
                    break;
                case "/":
                    if (courseCode.isEmpty() || courseDescription.isEmpty() || creditUnits == -1 || programCode.isEmpty())
                        UI.print("Details are incomplete");
                    else {
                        Course.courses.add(new Course(courseCode, courseDescription, creditUnits, programCode));
                        UI.print("Course successfully created");
                        roomManage("ADD", courseCode, courseDescription, creditUnits, programCode);
                        submit = true;
                    }
                    break;
                case "X":
                    manageCourses();
                    submit = true;
                    break;
            }
        }
    }

    private static void editCourse() {
        String courseCode = ""; String courseDescription = ""; String programCode = ""; String choice = "";
        int creditUnits = -1; boolean submit = false; String input;

        UI.print("EDIT EXISTING COURSE");
        do {
            System.out.print("  Enter Course Code > ");
            input = in.nextLine();
            if (!Course.existCourseCode(input)) UI.print("Course does not exist");
        } while(!Course.existCourseCode(input));

        Course c = Course.findCourse(input);
        if (c == null) {
            UI.print("Room not found");
            return;
        }
        courseCode = Course.getCourseCode(c);
        courseDescription = Course.getCourseDescription(c);
        creditUnits = Course.getCreditUnits(c);
        programCode = Course.getProgramCode(c);

        while (!submit) {
            courseManage("EDIT", courseCode, courseDescription, creditUnits, programCode);

            do {
                System.out.print("  Select > ");
                choice = in.nextLine();
                if (!choice.matches("[1-4X]") && !choice.equals("/")) UI.print("Invalid Input");
            } while (!choice.matches("[1-4X]") && !choice.equals("/"));

            switch(choice) {
                case "1":
                    do {
                        System.out.print("   Enter Course Code > ");
                        courseCode = in.nextLine();
                        if (Course.existCourseCode(courseCode)) UI.print("Course Code already exists");
                    } while(Course.existCourseCode(courseCode));
                    break;
                case "2":
                    do {
                        System.out.print("   Enter Course Description > ");
                        courseDescription = in.nextLine();
                        if (Course.existCourseDesc(courseDescription)) UI.print("Course Description already exists");
                    } while(Course.existCourseDesc(courseDescription));
                    break;
                case "3":
                    do {
                        System.out.print("   Enter Credit Units > ");
                        try {
                            input = in.nextLine();
                            creditUnits = Integer.parseInt(input);
                            if (creditUnits != 2 && creditUnits != 3) UI.print("Invalid Credit Units");
                        } catch (NumberFormatException e) {
                            UI.print("Invalid Credit Units");
                            creditUnits = -1;
                        }
                    } while (creditUnits != 2 && creditUnits != 3);
                    break;
                case "4":
                    do {
                        System.out.print("   Enter Program Code > ");
                        programCode = in.nextLine();
                        if(!Program.verifyProgramCode(programCode)) UI.print("Program Code does not exist");
                    } while(!Program.verifyProgramCode(programCode));
                    break;
                case "/":
                    UI.print("Room successfully edited");
                    c.setCourseCode(courseCode); c.setCourseDescription(courseDescription);
                    c.setCreditUnits(creditUnits); c.setProgramCode(programCode);
                    Course.displayCourse(c.getCourseCode());
                    submit = true;
                    break;
                case "X":
                    submit = true;
                    break;
            }
        }
    }

    private static void deleteCourse() {
        String input; String choice;

        UI.print("DELETE EXISTING COURSE");

        do {
            System.out.print("  Enter Course Code > ");
            input = in.nextLine();
            if (!Course.existCourseCode(input)) UI.print("Course does not exist");
        } while(!Course.existCourseCode(input));

        Course c = Course.findCourse(input);
        if (c == null) {
            UI.print("Room not found");
            return;
        }

        Course.displayCourse(c.getCourseCode());

        do {
            System.out.print("  Confirm Room Deletion? (Y/N) > ");
            choice = in.nextLine().toUpperCase();
            if (!choice.matches("[YN]")) UI.print("Invalid Input");
        } while(!choice.matches("[YN]"));

        if (choice.equals("Y")) {
            Course.courses.remove(c);
            UI.print("Course successfully deleted");
        }
        else manageCourses();
    }

    private static void courseManage(String text, String courseCode, String courseDescription,
                                     int creditUnits, String programCode) {
        UI.header(text.toUpperCase() + ": ROOM INFORMATION");
        System.out.printf("│%-" + innerWidth + "s│\n", String.format(" [1] COURSE CODE         : %s", courseCode));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format(" [2] COURSE DESCRIPTION  : %s", courseDescription));
        String unitText;
        if (creditUnits == -1) unitText = "";
        else if (creditUnits == 1) unitText = "1st Floor";
        else if (creditUnits == 2) unitText = "2nd Floor";
        else if (creditUnits == 3) unitText = "3rd Floor";
        else unitText = creditUnits + "th Floor";
        System.out.printf("│%-" + innerWidth + "s│\n", String.format(" [3] CREDIT UNITS        : %s", unitText));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format(" [4] PROGRAM CODE        : %s", programCode));
        System.out.printf("│%-" + innerWidth + "s│\n", "");
        UI.option();
        UI.footer();
    }

    public static void manageUsers() {
        int choice = -1; String input = "";

        UI.manageUI("Users");

        do {
            System.out.print("  Select > ");
            try {
                input = in.nextLine().trim();
                if (input.isEmpty()) {
                    UI.print("Invalid Input");
                    continue;
                }
                choice = Integer.parseInt(input);
                if (!(choice >= 1 && choice <= 4) && choice != 0) UI.print("Invalid Input");
            } catch (NumberFormatException e) {
                UI.print("Invalid Input");
                choice = -1;
            }
        } while (!(choice >= 1 && choice <= 4) && choice != 0);

        switch (choice) {
            case 1:
                Signup.signUp("CREATE NEW USER");
                break;
            case 2:
                editUser(true);
                break;
            case 3:
                deleteUser();
                break;
            case 4:
                User.displayUsers();
                break;
            case 0:
                // menu
        }
    }

    private static void editUser(boolean permit) {
        String userID; String lastName; String firstName; String middleInitial; String emailAddress;
        String password; String address; int postalCode; String birthDate; char sex; boolean valid;
        String choice; boolean submit = false; String enrollmentStatus = ""; String programCode = "";
        String contactNumber = ""; String collegeCode = ""; String role = ""; String userName;

        UI.print("EDIT EXISTING USER");
        do {
            System.out.print("  Enter User ID > ");
            userID = in.nextLine();
            if (!User.existUserID(userID)) UI.print("User does not exist");
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

        if (userID.charAt(0) == 'S') {
            Student s = (Student) u;
            enrollmentStatus = s.getEnrollmentStatus();
            programCode = s.getProgramCode();
        }
        else if (userID.charAt(0) == 'F') {
            Faculty f = (Faculty) u;
            contactNumber = f.getContactNumber();
            collegeCode = f.getCollegeCode();
        }
        else if (userID.charAt(0) == 'A') {
            Admin a = (Admin) u;
            role = a.getRole();
        }

        while (!submit) {
            if (userID.charAt(0) == 'S') userManage("EDIT", userID, lastName, firstName, middleInitial,
                    emailAddress, password, address, postalCode, birthDate, sex, enrollmentStatus, programCode);
            else if (userID.charAt(0) == 'A') userManage("EDIT", userID, lastName, firstName, middleInitial,
                    emailAddress, password, address, postalCode, birthDate, sex, contactNumber, collegeCode);
            else userManage("EDIT", userID, lastName, firstName, middleInitial, emailAddress, password,
                        address, postalCode, birthDate, sex, role);

            do {
                System.out.print("  Select > ");
                choice = in.nextLine();
                if (!choice.matches("[1-12X]") && !choice.equals("/")) UI.print("Invalid Input");
            } while (!choice.matches("[1-12X]") && !choice.equals("/"));

            switch(choice) {
                case "1":
                    if (permit) {
                        do {
                            System.out.print("   Enter User ID > ");
                            userID = in.nextLine();
                            if (!User.existUserID(userID)) User.existUserID("User does not exist");
                        } while(!User.existUserID(userID));
                    }
                    else UI.print("Edit access denied");
                    break;
                case "2":
                    do {
                        System.out.print("   Enter Last Name > ");
                        lastName = in.nextLine();
                        if (lastName.trim().isEmpty()) UI.print("Invalid Input");
                    } while(lastName.trim().isEmpty());
                    break;
                case "3":
                    do {
                        System.out.print("   Enter First Name > ");
                        firstName = in.nextLine();
                        if (firstName.trim().isEmpty()) UI.print("Invalid Input");
                    } while(firstName.trim().isEmpty());
                    break;
                case "4":
                        System.out.print("   Enter Middle Initial > ");
                        middleInitial = in.nextLine();
                        if (middleInitial.endsWith(".")) middleInitial = middleInitial.replace(".", "");
                    break;
                case "5":
                    valid = false;
                    do {
                        System.out.print("   Enter Email Address > ");
                        emailAddress = in.nextLine();
                        if (emailAddress.trim().isEmpty()) UI.print("Invalid Input");
                        else if (!emailAddress.endsWith("@bulsu.edu.ph")) UI.print("Invalid Email Domain");
                        else if (!emailAddress.matches("[SFA][0-9]{3}@bulsu\\.edu\\.ph"))
                            UI.print("Invalid User");
                        else if (User.checkDuplicateEmail(emailAddress)) UI.print("Email already exists");
                        else valid = true;
                    } while (!valid);
                    break;
                case "6":
                    valid = false;
                    do {
                        System.out.print("   Enter Password > ");
                        password = in.nextLine();
                        if (password.trim().isEmpty()) UI.print("Invalid Input");
                        else if (password.length() < 8) UI.print("Password must be at least 8 characters");
                        else if (!password.matches(".*[A-Z].*"))
                            UI.print("Password must contain an uppercase letter");
                        else if (!password.matches(".*[a-z].*"))
                            UI.print("Password must contain an lowercase letter");
                        else if (!password.matches(".*[0-9].*"))
                            UI.print("Password must contain a number");
                        else if (!password.matches(".*[^a-zA-Z0-9].*"))
                            UI.print("Password must contain a special character");
                        else if (password.contains(" ")) UI.print("Password cannot contain spaces");
                        else valid = true;
                    } while (!valid);
                    break;
                case "7":
                    String street; String barangay; String city; String province;
                    do {
                        System.out.print("    Enter Street > ");
                        street = in.nextLine();
                        if (street.trim().isEmpty()) UI.print("Invalid Input");
                    } while (street.trim().isEmpty());

                    do {
                        System.out.print("    Enter Barangay > ");
                        barangay = in.nextLine();
                        if (barangay.trim().isEmpty()) UI.print("Invalid Input");
                    } while (barangay.trim().isEmpty());

                    do {
                        System.out.print("    Enter City > ");
                        city = in.nextLine();
                        if (city.trim().isEmpty()) UI.print("Invalid Input");
                    } while (city.trim().isEmpty());

                    do {
                        System.out.print("    Enter Province > ");
                        province = in.nextLine();
                        if (province.trim().isEmpty()) UI.print("Invalid Input");
                    } while (province.trim().isEmpty());

                    address = street + ", " + barangay + ", " + city + ", " + province;
                    break;
                case "8":
                    valid = false;
                    do {
                        System.out.print("   Enter Postal Code > ");
                        try {
                            postalCode = in.nextInt();
                            in.nextLine();
                            if (postalCode < 1000 || postalCode > 9999) UI.print("Postal code must be 4 digits");
                            else valid = true;
                        }
                        catch (Exception e) {
                            in.nextLine();
                            UI.print("Invalid Input");
                        }
                    } while (!valid);
                case "9":
                    do {
                        System.out.print("   Enter Birthdate (MM/DD/YYYY) > ");
                        birthDate = in.nextLine();
                        if (!birthDate.matches("(0[1-9]|1[0-2])/(0[1-9]|[12][0-9]|3[01])/\\d{4}"))
                            UI.print("Invalid birthdate format");
                    } while (!birthDate.matches("(0[1-9]|1[0-2])/(0[1-9]|[12][0-9]|3[01])/\\d{4}"));
                case "10":
                    do {
                        System.out.print("   Enter Sex (M/F) > ");
                        sex = in.nextLine().toUpperCase().charAt(0);
                        if  (sex != 'M' && sex != 'F') UI.print("Invalid Input");
                    } while (sex != 'M' && sex != 'F');
                case "11":
                    if (userID.charAt(0) == 'S') {
                        do {
                            System.out.print("   Enrollment Status (R, IR) > ");
                            enrollmentStatus = in.nextLine().toUpperCase();
                            if (!enrollmentStatus.matches("R|IR")) UI.print("Invalid Input");
                        } while (!enrollmentStatus.matches("R|IR"));
                        do {
                            System.out.println("   Program Code > ");
                            programCode = in.nextLine();
                            if (!(programCode.length() >= 4 && programCode.length() <= 6)) UI.print("Invalid Input");
                        } while (!(programCode.length() >= 4 && programCode.length() <= 6));
                    }
                    else if (userID.charAt(0) == 'F') {
                        do {
                            System.out.print("   Contact Number > ");
                            contactNumber = in.nextLine();
                            if (contactNumber.matches("[0-9]{11}")) UI.print("Invalid Input");
                        } while (contactNumber.length() != 11);
                        do {
                            System.out.println("   College Code > ");
                            collegeCode = in.nextLine();
                            if (!(collegeCode.length() >= 4 && collegeCode.length() <= 6)) UI.print("Invalid Input");
                        } while (!(collegeCode.length() >= 4 && collegeCode.length() <= 6));
                    }
                    else if (userID.charAt(0) == 'A') {
                        do {
                            System.out.print("   Role > ");
                            role = in.nextLine();
                            if (role.trim().isEmpty()) UI.print("Invalid Input");
                        } while (role.trim().isEmpty());
                    }
                    break;

                case "12":
                    if (userID.charAt(0) == 'S') {
                        do {
                            System.out.print("   Enter Program Code > ");
                            programCode = in.nextLine();
                        } while (programCode.isBlank());

                    } else if (userID.charAt(0) == 'F') {
                        do {
                            System.out.print("   Enter College Code > ");
                            collegeCode = in.nextLine();
                        } while (collegeCode.isBlank());
                    }
                    break;
                case "/":
                    UI.print("User successfully edited");
                    u.setUserID(userID);
                    u.setLastName(lastName);
                    u.setFirstName(firstName);
                    u.setMiddleInitial(middleInitial);
                    u.setEmailAddress(emailAddress);
                    u.setPassword(password);
                    u.setAddress(address);
                    u.setPostalCode(postalCode);
                    u.setBirthDate(birthDate);
                    u.setSex(sex);

                    if (userID.charAt(0) == 'S') {
                        Student s = (Student) u;
                        s.setEnrollmentStatus(enrollmentStatus);
                        s.setProgramCode(programCode);

                    } else if (userID.charAt(0) == 'F') {
                        Faculty f = (Faculty) u;
                        f.setContactNumber(contactNumber);
                        f.setCollegeCode(collegeCode);

                    } else if (userID.charAt(0) == 'A') {
                        Admin a = (Admin) u;
                        a.setRole(role);
                    }

                    User.viewProfile(u.getUserID());

                    submit = true;
                    break;
                case "X":
                    submit = true;
                    break;
            }

            userName = lastName + ", " + firstName + " " + middleInitial + ".";
            if (checkDuplicateName(userName) && !userName.equals(u.getUserName())) {
                UI.print("Name already exist");
                lastName = u.getLastName();
                firstName = u.getFirstName();
                middleInitial = u.getMiddleInitial();
            }
        }

    }

    public static void deleteUser(){
        String userID = ""; String lastName = ""; String firstName = ""; String middleInitial = ""; String emailAddress = "";
        String password = ""; String address = ""; int postalCode = -1; String birthDate= ""; char sex= '\0';
        String choice = ""; boolean submit = false; String enrollmentStatus = ""; String programCode = ""; String contactNumber = "";
        String collegeCode = ""; String role = "";

        UI.print("DELETE EXISTING ROOM");
        boolean found = true;
        do {
            System.out.print("  Enter User ID > ");
            userID = in.nextLine();
            for (User u : users) if (!userID.equals(u.getUserID())) {
                UI.print("User does not exist");
                found = false;
            }
        } while (!found);
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

        if (userID.charAt(0) == 'S') {
            Student s = (Student) u;
            enrollmentStatus = s.getEnrollmentStatus();
            programCode = s.getProgramCode();
        }
        else if (userID.charAt(0) == 'F') {
            Faculty f = (Faculty) u;
            contactNumber = f.getContactNumber();
            collegeCode = f.getCollegeCode();
        }
        else if (userID.charAt(0) == 'A') {
            Admin a = (Admin) u;
            role = a.getRole();
        }

        if (userID.charAt(0) == 'S') userManage("EDIT", userID, lastName, firstName, middleInitial,
                emailAddress, password, address, postalCode, birthDate, sex, enrollmentStatus, programCode);
        else if (userID.charAt(0) == 'A') userManage("EDIT", userID, lastName, firstName, middleInitial,
                emailAddress, password, address, postalCode, birthDate, sex, contactNumber, collegeCode);
        else userManage("EDIT", userID, lastName, firstName, middleInitial, emailAddress, password,
                    address, postalCode, birthDate, sex, role);

        do {
            System.out.print("  Confirm Room Deletion? (Y/N) > ");
            choice = in.nextLine().toUpperCase();
            if (!choice.matches("[YN]")) UI.print("Invalid Input");
        } while(!choice.matches("[YN]"));

        if (choice.equals("Y")) {
            User.users.remove(u);
            UI.print("User successfully deleted");
        }
        else manageUsers();
    }

    public static void userManage(String text, String userID, String lastName, String firstName, String middleInitial,
                                  String emailAddress, String password, String address, int postalCode, String birthDate,
                                  char sex, String role){
        User u = User.getUser(userID);
        if (u == null) {
            System.out.println("User not found");
            return;
        }
        UI.header(text.toUpperCase() + ": USER INFORMATION");
        System.out.printf("│%-" + innerWidth + "s│\n", String.format(" [1]  USER ID          : %s", userID));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format(" [2]  LAST NAME        : %s", lastName));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format(" [3]  FIRSTNAME        : %s", firstName));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format(" [4]  MIDDLE INITIAL   : %s", middleInitial));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format(" [5]  EMAIL ADDRESS    : %s", emailAddress));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format(" [6]  PASSWORD         : %s", password));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format(" [7]  ADDRESS          : %s", address));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format(" [8]  POSTAL CODE      : %s", postalCode));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format(" [9]  BIRTHDATE        : %s", birthDate));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format(" [10] SEX              : %s", sex));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format(" [11] ROLE             : %s", role));
        System.out.printf("│%-" + innerWidth + "s│\n", "");
        UI.option();
        UI.footer();
    }

    public static void userManage(String text, String userID, String lastName, String firstName, String middleInitial,
                                  String emailAddress, String password, String address, int postalCode, String birthDate,
                                  char sex, String input1, String input2){
        User u = User.getUser(userID);
        if (u == null) {
            System.out.println("User not found");
            return;
        }
        UI.header(text.toUpperCase() + ": USER INFORMATION");
        System.out.printf("│%-" + innerWidth + "s│\n", String.format(" [1]  USER ID          : %s", userID));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format(" [2]  LAST NAME        : %s", lastName));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format(" [3]  FIRSTNAME        : %s", firstName));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format(" [4]  MIDDLE INITIAL   : %s", middleInitial));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format(" [5]  EMAIL ADDRESS    : %s", emailAddress));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format(" [6]  PASSWORD         : %s", password));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format(" [7]  ADDRESS          : %s", address));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format(" [8]  POSTAL CODE      : %s", postalCode));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format(" [9]  BIRTHDATE        : %s", birthDate));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format(" [10] SEX              : %s", sex));
        if (userID.charAt(0) == 'S'){
            System.out.printf("│%-" + innerWidth + "s│\n", String.format(" [11] ENROLLMENT STATUS: %s", input1));
            System.out.printf("│%-" + innerWidth + "s│\n", String.format(" [12] PROGRAM CODE     : %s", input2));
        }
        else {
            System.out.printf("│%-" + innerWidth + "s│\n", String.format(" [11] CONTACT NUMBER   : %s", input1));
            System.out.printf("│%-" + innerWidth + "s│\n", String.format(" [12] COLLEGE CODE     : %s", input2));
        }
    }

    public String getRole() {
        return role;
    }

    private void setRole(String role) {
        this.role = role;
    }
}
