package USER;
import DATA.Building;
import DATA.Class;
import DATA.Room;
import UI.*;

import java.util.Scanner;
import DATA.*;

import static DATA.Building.buildings;
import static DATA.Class.classes;
import static DATA.College.colleges;
import static DATA.Course.courses;
import static DATA.Enrollment.enrollments;
import static DATA.Program.programs;
import static DATA.Room.rooms;
import static DATA.Schedule.schedules;
import static DATA.Section.sections;
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
                        rooms.add(new Room(roomCode, roomDescription, floorNumber, buildingCode));
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
            rooms.remove(r);
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
                        courses.add(new Course(courseCode, courseDescription, creditUnits, programCode));
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
            courses.remove(c);
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
                if (!(choice >= 1 && choice <= 3) && choice != 0) UI.print("Invalid Input");
            } catch (NumberFormatException e) {
                UI.print("Invalid Input");
                choice = -1;
            }
        } while (!(choice >= 1 && choice <= 4) && choice != 0);

        switch (choice) {
            case 1:
                createUser();
                break;
            case 2:
                editUser();
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

    private static void createUser() {
        String userID = ""; String lastName = ""; String firstName = ""; String middleInitial = ""; String emailAddress = "";
                String password = ""; String address = ""; int postalCode = -1; String birthDate= ""; char sex= '\0'; String userType = "";
                String choice = ""; boolean submit = false; String enrollmentStatus = ""; String programCode = ""; String contactNumber = "";
                String collegeCode = ""; String role = ""; String userName = "";

        do {
            UI.header("CREATE NEW USER");
            System.out.printf("│%-" + INNERWIDTH + "s│%n", " [1] Student");
            System.out.printf("│%-" + INNERWIDTH + "s│%n", " [2] Faculty");
            System.out.printf("│%-" + INNERWIDTH + "s│%n", " [3] Admin");
            System.out.printf("│%-" + INNERWIDTH + "s│%n", " [X] Cancel");
            UI.footer();

            System.out.print("  Select > ");
            userType = in.nextLine().trim().toUpperCase();

            if (!userType.matches("[123X]")) {
                UI.print("Invalid Choice");
            }

        } while (!userType.matches("[123X]"));

        if (userType.equals("X")) {
            manageUsers();
            return;
        }
        boolean check = false;
        switch (userType) {
            case "1":
                do {
                    System.out.print("   Enter UserID > ");
                    userID = in.nextLine();
                    for (User u : users) {
                        if (userID.equals(u.getUserID())) {
                            UI.print("User ID already exists.");
                        }
                        else{
                            check = true;
                        }
                    }
                }while (userID.isBlank() || !check);
                do {
                    System.out.print("   Enter Last Name > ");
                    lastName = in.nextLine();
                    System.out.print("   Enter First Name > ");
                    firstName = in.nextLine();
                System.out.print("   No Middle Initial = Enter ");
                System.out.print("   Enter Middle Initial > ");
                middleInitial = in.nextLine();
                userName = lastName + ", " + firstName + " " + middleInitial;
                }while (firstName.isBlank() || lastName.isBlank() || checkDuplicateName(userName));
                do {
                    System.out.print("   Enter Email Address > ");
                    emailAddress = in.nextLine();
                }while (emailAddress.isBlank());
                do {
                    System.out.print("   Enter Password > ");
                    password = in.nextLine();
                }while (password.isBlank());
                do {
                    System.out.print("   Enter Address > ");
                    address = in.nextLine();
                }while (address.isBlank());
                do {
                    System.out.print("   Enter Postal Code > ");
                    postalCode = in.nextInt();
                }while (postalCode == -1);
                do {
                    System.out.print("   Enter BirthDate (MM/DD/YY) > ");
                    birthDate = in.nextLine();
                }while (birthDate.isBlank());
                do {
                    System.out.print("   Enter Sex > ");
                    sex = in.nextLine().charAt(0);
                }while (sex == '\0');
                do {
                    System.out.print("   Enter Enrollment Status (R/IR) > ");
                    enrollmentStatus = in.nextLine();
                }while (enrollmentStatus.isBlank());
                do {
                    System.out.print("   Enter Program Code > ");
                    programCode = in.nextLine();
                }while (programCode.isBlank());
                User.users.add(new Student(
                        userID, lastName, firstName, middleInitial,
                        emailAddress, password, address, postalCode,
                        birthDate, sex, enrollmentStatus, programCode
                ));
                UI.print("Student User created successfully.");
                break;

            case "2":
                do {
                    System.out.print("   Enter UserID > ");
                    userID = in.nextLine();
                    for (User u : users) {
                        if (userID.equals(u.getUserID())) {
                            UI.print("User ID already exists.");
                        }
                        else{
                            check = true;
                        }
                    }
                }while (userID.isBlank() || !check);
                do {
                    System.out.print("   Enter Last Name > ");
                    lastName = in.nextLine();
                    System.out.print("   Enter First Name > ");
                    firstName = in.nextLine();
                    System.out.print("   No Middle Initial = Enter ");
                    System.out.print("   Enter Middle Initial > ");
                    middleInitial = in.nextLine();
                    userName = lastName + ", " + firstName + " " + middleInitial;
                }while (firstName.isBlank() || lastName.isBlank() || checkDuplicateName(userName));
                do {
                    System.out.print("   Enter Email Address > ");
                    emailAddress = in.nextLine();
                }while (emailAddress.isBlank());
                do {
                    System.out.print("   Enter Password > ");
                    password = in.nextLine();
                }while (password.isBlank());
                do {
                    System.out.print("   Enter Address > ");
                    address = in.nextLine();
                }while (address.isBlank());
                do {
                    System.out.print("   Enter Postal Code > ");
                    postalCode = in.nextInt();
                }while (postalCode == -1);
                do {
                    System.out.print("   Enter BirthDate (MM/DD/YY) > ");
                    birthDate = in.nextLine();
                }while (birthDate.isBlank());
                do {
                    System.out.print("   Enter Sex > ");
                    sex = in.nextLine().charAt(0);
                }while (sex == '\0');
                do {
                    System.out.print("   Enter Contact Number > ");
                    contactNumber = in.nextLine();
                }while (contactNumber.isBlank());
                do {
                    System.out.print("   Enter College Code > ");
                    collegeCode = in.nextLine();
                }while (collegeCode.isBlank());
                User.users.add(new Faculty(
                        userID, lastName, firstName, middleInitial,
                        emailAddress, password, address, postalCode,
                        birthDate, sex, contactNumber, collegeCode
                ));
                UI.print("Faculty User created successfully.");
                break;
            case "3":
                do {
                    System.out.print("   Enter UserID > ");
                    userID = in.nextLine();
                    for (User u : users) {
                        if (userID.equals(u.getUserID())) {
                            UI.print("User ID already exists.");
                        }
                        else{
                            check = true;
                        }
                    }
                }while (userID.isBlank() || !check);
                do {
                    System.out.print("   Enter Last Name > ");
                    lastName = in.nextLine();
                    System.out.print("   Enter First Name > ");
                    firstName = in.nextLine();
                    System.out.print("   No Middle Initial = Enter ");
                    System.out.print("   Enter Middle Initial > ");
                    middleInitial = in.nextLine();
                    userName = lastName + ", " + firstName + " " + middleInitial;
                }while (firstName.isBlank() || lastName.isBlank() || checkDuplicateName(userName));
                do {
                    System.out.print("   Enter Email Address > ");
                    emailAddress = in.nextLine();
                }while (emailAddress.isBlank());
                do {
                    System.out.print("   Enter Password > ");
                    password = in.nextLine();
                }while (password.isBlank());
                do {
                    System.out.print("   Enter Address > ");
                    address = in.nextLine();
                }while (address.isBlank());
                do {
                    System.out.print("   Enter Postal Code > ");
                    postalCode = in.nextInt();
                }while (postalCode == -1);
                do {
                    System.out.print("   Enter BirthDate (MM/DD/YY) > ");
                    birthDate = in.nextLine();
                }while (birthDate.isBlank());
                do {
                    System.out.print("   Enter Sex > ");
                    sex = in.nextLine().charAt(0);
                }while (sex == '\0');
                do {
                    System.out.print("   Enter Role > ");
                    role = in.nextLine();
                }while (role.isEmpty());
                User.users.add(new Admin(
                        userID, lastName, firstName, middleInitial,
                        emailAddress, password, address, postalCode,
                        birthDate, sex, role
                ));
                UI.print("Admin User created successfully.");
                break;
            case "X":
                manageUsers();
                submit = true;
                break;

        }

    }
    private static void editUser() {
        String userID = ""; String lastName = ""; String firstName = ""; String middleInitial = ""; String emailAddress = "";
        String password = ""; String address = ""; int postalCode = -1; String birthDate= ""; char sex= '\0';
        String choice = ""; boolean submit = false; String enrollmentStatus = ""; String programCode = ""; String contactNumber = "";
        String collegeCode = ""; String role = "";

        UI.print("EDIT EXISTING USER");
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

        while (!submit) {
            userManage("EDIT", userID, lastName, firstName, middleInitial, emailAddress, password, address, postalCode, birthDate, sex);

            do {
                System.out.print("  Select > ");
                choice = in.nextLine();
                if (!choice.matches("[1-12X]") && !choice.equals("/")) UI.print("Invalid Input");
            } while (!choice.matches("[1-12X]") && !choice.equals("/"));

            switch(choice) {
                case "1":
                    boolean exists = false;
                    do {
                        System.out.print("   Enter User ID > ");
                        userID = in.nextLine();
                        for (User x : users) {
                            if (userID.equals(x.getUserID())) {
                                exists = true;
                                UI.print("User ID already exists");
                                break;
                            }
                        }
                    } while(exists);
                    break;
                case "2":
                    do {
                        System.out.print("   Enter Last Name > ");
                        lastName = in.nextLine();
                    } while(lastName.isBlank());
                    break;
                case "3":
                    do {
                        System.out.print("   Enter First Name > ");
                        firstName = in.nextLine();
                    }while(firstName.isBlank());
                case "4":
                        System.out.print("   Enter Middle Initial (Press Enter if None) > ");
                        middleInitial = in.nextLine();
                    break;
                case "5":
                    do {
                        System.out.print("   Enter Email Address > ");
                        emailAddress = in.nextLine();
                    }while(emailAddress.isBlank());
                case "6":
                    do {
                        System.out.print("   Enter Password > ");
                        password = in.nextLine();
                    }while(password.isBlank());
                case "7":
                    do {
                        System.out.print("   Enter Address > ");
                        address = in.nextLine();
                    }while(address.isBlank());
                case "8":
                    do {
                        System.out.print("   Enter Postal Code > ");
                        postalCode = in.nextInt();
                    }while(postalCode == -1);
                case "9":
                    do {
                        System.out.print("   Enter Birthdate (MM/DD/YY) > ");
                        birthDate = in.nextLine();
                    }while(birthDate.isBlank());
                case "10":
                    do {
                        System.out.print("   Enter Sex (M/F) > ");
                        sex = in.nextLine().charAt(0);
                    }while(sex == '\0');
                case "11":
                    if (userID.charAt(0) == 'S') {
                        Student s = (Student) u;
                        enrollmentStatus = s.getEnrollmentStatus();
                        programCode = s.getProgramCode();

                    } else if (userID.charAt(0) == 'F') {
                        Faculty f = (Faculty) u;
                        contactNumber = f.getContactNumber();
                        collegeCode = f.getCollegeCode();

                    } else if (userID.charAt(0) == 'A') {
                        Admin a = (Admin) u;
                        role = a.getRole();
                    }
                    if (userID.charAt(0) == 'S') {
                        do {
                            System.out.print("   Enter Enrollment Status > ");
                            enrollmentStatus = in.nextLine();
                        } while (enrollmentStatus.isBlank());

                    } else if (userID.charAt(0) == 'F') {
                        do {
                            System.out.print("   Enter Contact Number > ");
                            contactNumber = in.nextLine();
                        } while (contactNumber.isBlank());

                    } else if (userID.charAt(0) == 'A') {
                        do {
                            System.out.print("   Enter Role > ");
                            role = in.nextLine();
                        } while (role.isBlank());
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
        }

    }

    private void setRole(String role) { this.role = role;
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

        userManage("DELETE",userID, lastName, firstName, middleInitial, emailAddress, password, address, postalCode, birthDate, sex);
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

    public static void userManage(String text, String userID, String lastName, String firstName, String middleInitial, String emailAddress,
                                  String password, String address, int postalCode, String birthDate, char sex){
        User u = User.getUser(userID);
        if (u == null) {
            System.out.println("User not found");
            return;
        }
        String enrollmentStatus;
        String programCode; String contactNumber; String collegeCode; String role;
        UI.header(text.toUpperCase() + ": USER INFORMATION");
        System.out.printf("│%-" + innerWidth + "s│\n", String.format(" [1] USER ID           : %s", userID));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format(" [2] LAST NAME         : %s", lastName));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format(" [3] FIRSTNAME         : %s", firstName));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format(" [4] MIDDLE INITIAL    : %s", middleInitial));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format(" [5] EMAIL ADDRESS     : %s", emailAddress));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format(" [6] PASSWORD          : %s", password));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format(" [7] ADDRESS           : %s", address));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format(" [8] POSTAL CODE       : %s", postalCode));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format(" [9] BIRTHDATE         : %s", birthDate));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format(" [10] SEX              : %s", sex));
        if (userID.charAt(0) == 'S'){
            Student s = (Student) u;
            enrollmentStatus = s.getEnrollmentStatus();
            programCode = s.getProgramCode();
            System.out.printf("│%-" + innerWidth + "s│\n", String.format(" [11] ENROLLMENT STATUS: %s", enrollmentStatus));
            System.out.printf("│%-" + innerWidth + "s│\n", String.format(" [12] PROGRAM CODE     : %s", programCode));
        } else if (userID.charAt(0) == 'F') {
            Faculty f = (Faculty) u;
            contactNumber = f.getContactNumber();
            collegeCode = f.getCollegeCode();
            System.out.printf("│%-" + innerWidth + "s│\n", String.format(" [11] CONTACT NUMBER   : %s", contactNumber));
            System.out.printf("│%-" + innerWidth + "s│\n", String.format(" [12] COLLEGE CODE     : %s", collegeCode));
        } else if (userID.charAt(0) == 'A') {
            Admin a = (Admin) u;
            role = a.getRole();
            System.out.printf("│%-" + innerWidth + "s│\n", String.format(" [11] ROLE             : %s", role));
    }
        System.out.printf("│%-" + innerWidth + "s│\n", "");
        UI.option();
        UI.footer();
    }

    public String getRole() { return role;
    }

    public void manageBuildings(){
        int choice = -1; String input = "";

        UI.manageUI("Buildings");
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
                createBuilding();
                break;
            case 2:
                editBuilding();
                break;
            case 3:
                deleteBuilding();
                break;
            case 4:
                Building.displayBuildings();
                break;
            case 0:
                // menu
        }
    }

    public void createBuilding(){
        String buildingCode = ""; String buildingName = "";  String choice = ""; boolean submit = false;

        while (!submit) {
            UI.header("CREATE NEW ROOM");
            System.out.printf("│%-" + INNERWIDTH + "s│%n", " [1] Building Code         : " + buildingCode);
            System.out.printf("│%-" + INNERWIDTH + "s│%n", " [2] Building Name  : " + buildingName);
            UI.option();
            UI.footer();

            do {
                System.out.print("  Select > ");
                choice = in.nextLine();
                if (!choice.matches("[1-2X]") && !choice.equals("/")) UI.print("Invalid Choice");
            } while (!choice.matches("[1-2X]") && !choice.equals("/"));

            switch (choice) {
                case "1":
                    do {
                        System.out.print("   Enter Building Code > ");
                        buildingCode = in.nextLine();
                        if (Building.existBuildingCode(buildingCode)) UI.print("Building Code already exists");
                    } while(Building.existBuildingCode(buildingCode));
                    break;
                case "2":
                    do {
                        System.out.print("   Enter Building Name > ");
                        buildingName = in.nextLine();
                        if (Building.existBuildingName(buildingName)) UI.print("Building Name already exists");
                    } while(Building.existBuildingName(buildingName));
                    break;
                case "X":
                    manageBuildings();
                    submit = true;
                    break;
            }
        }
    }

    public void editBuilding(){
        String buildingCode = ""; String buildingName = "";
        boolean submit = false; String input; String choice;

        UI.print("EDIT EXISTING BUILDING");
        do {
            System.out.print("  Enter Building Code > ");
            input = in.nextLine();
            if (!Building.existBuildingName(input)) UI.print("Building does not exist");
        } while(!Building.existBuildingName(input));

        Building b = Building.findBuilding(input);
        if (b == null) {
            UI.print("Building not found");
            return;
        }
        buildingCode = Building.getBuilding(b);
        buildingName = Building.getBuildingName(b);

        while (!submit) {
            buildingManage("EDIT", buildingCode, buildingName);

            do {
                System.out.print("  Select > ");
                choice = in.nextLine();
                if (!choice.matches("[1-2X]") && !choice.equals("/")) UI.print("Invalid Input");
            } while (!choice.matches("[1-2X]") && !choice.equals("/"));

            switch(choice) {
                case "1":
                    do {
                        System.out.print("   Enter Building Code > ");
                        buildingCode = in.nextLine();
                        if (Building.existBuildingCode(buildingCode) && !buildingCode.equalsIgnoreCase(b.getBuildingCode()))
                            UI.print("Room Code already exists");
                    } while(Building.existBuildingCode(buildingCode) && !buildingCode.equalsIgnoreCase(b.getBuildingCode()));
                    break;
                case "2":
                    do {
                        System.out.print("   Enter Building Name > ");
                        buildingName = in.nextLine();
                        if (Building.existBuildingName(buildingName)  && !buildingName.equalsIgnoreCase(Building.getBuildingName(b)))
                            UI.print("Room Description already exists");
                    } while(Building.existBuildingName(buildingName)  && !buildingName.equalsIgnoreCase(Building.getBuildingName(b)));
                    break;
                case "/":
                    UI.print("Room successfully edited");
                    b.setBuildingCode(buildingCode); b.setBuildingName(buildingName);
                    Building.displayBuilding(b.getBuildingCode());
                    submit = true;
                    break;
                case "X":
                    submit = true;
                    break;
            }
        }
    }

    public void deleteBuilding(){
        String input; String choice; String buildingCode; String buildingName;

        UI.print("DELETE EXISTING BUILDING");

        do {
            System.out.print("  Enter Building Code > ");
            input = in.nextLine();
            if (!Building.existBuildingCode(input)) UI.print("Room does not exist");
        } while(!Building.existBuildingCode(input));

        Building b = Building.findBuilding(input);
        if (b == null) {
            UI.print("Building not found");
            return;
        }

        buildingCode = Building.getBuilding(b);
        buildingName = Building.getBuildingName(b);

        buildingManage("DELETE", buildingCode, buildingName);
        do {
            System.out.print("  Confirm Building Deletion? (Y/N) > ");
            choice = in.nextLine().toUpperCase();
            if (!choice.matches("[YN]")) UI.print("Invalid Input");
        } while(!choice.matches("[YN]"));

        if (choice.equals("Y")) {
            buildings.remove(b);
            UI.print("Building successfully deleted");
        }
        else manageBuildings();
    }

    private static void buildingManage(String text, String buildingCode, String buildingName) {
        UI.header(text.toUpperCase() + ": BUILDING INFORMATION");
        System.out.printf("│%-" + innerWidth + "s│\n", String.format(" [1] BUILDING CODE     : %s", buildingCode));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format(" [2] BUILDING NAME     : %s", buildingName));
        System.out.printf("│%-" + innerWidth + "s│\n", "");
        UI.option();
        UI.footer();
    }

    public void viewStatistics(){
        int totalUser = 0, totalStudent = 0, totalFaculty = 0, totalAdmin = 0, totalBuilding = 0, totalRoom = 0,
                totalCourse= 0, totalProgram = 0, totalClass = 0, totalCollege = 0, totalEnrollee = 0, totalSchedule = 0,
                totalSection = 0;
        for (Building b : buildings) {
            totalBuilding++;
        }
        for (User u : users) {
            totalUser++;
            if (u.getUserID().charAt(0) == 'S') {
                totalStudent++;
            } else if (u.getUserID().charAt(0) == 'F') {
                totalFaculty++;
            } else if (u.getUserID().charAt(0) == 'A') {
                totalAdmin++;
            }
        }
        for (Room r : rooms) {
            totalRoom++;
        }
        for (Course c : courses){
            totalCourse++;
        }
        for (Program p :programs){
            totalProgram++;
        }
        for (Class c : classes){
            totalClass++;
        }
        for (College c : colleges){
            totalCollege++;
        }
        for (Enrollment e : enrollments){
            totalEnrollee++;
        }
        for (Schedule s : schedules){
            totalSchedule++;
        }
        for (Section s : sections){
            totalSection++;
        }
        UI.header("STATISTICS");
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("  USER          : %s", totalUser));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("    STUDENT     : %s", totalStudent));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("    FACULTY     : %s", totalFaculty));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("    ADMIN       : %s", totalAdmin));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("  PROGRAM       : %s", totalProgram));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("  COURSE        : %s", totalCourse));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("  COLLEGE       : %s", totalCollege));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("  BUILDING      : %s", totalBuilding));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("  ROOM          : %s", totalRoom));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("  ENROLLEE      : %s", totalEnrollee));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("  CLASS         : %s", totalClass));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("  SECTION       : %s", totalSection));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("  SCHEDULE      : %s", totalSchedule));
        UI.footer();
    }

}
