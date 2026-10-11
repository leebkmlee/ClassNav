package USER;
import DATA.Building;
import DATA.Course;
import DATA.Program;
import DATA.Room;
import LOGIN.Signup;
import DATA.Notification;
import DATA.Room;
import DATA.Section;
import LOGIN.Signup;
import UI.*;
import java.util.Scanner;

import static UI.UI.INNERWIDTH;

public class Admin extends User{

import DATA.College;

import java.util.ArrayList;

import static DATA.Notification.*;


public class Admin extends User {
    private String role;

    public Admin (String userID, String lastName, String firstName, String middleInitial, String emailAddress,
                  String password, String address, int postalCode, String birthDate, char sex, String role) {

    public Admin(String userID, String lastName, String firstName, String middleInitial, String emailAddress,
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

    static ArrayList<User> users = User.users;

    public static String AutoGeneratedStudent() {
        int max = 0;
        for (User u : users) {
            String userID = u.getUserID();
            if (userID.startsWith("S")) {
                int number = Integer.parseInt(userID.substring(1));

                if (number > max) {
                    max = number;
                }
            }
        }
        return String.format("S%03d", max + 1);
    }

    public static void adminStart(String userID) {
        UI.header("WELCOME ADMIN " + userID);
        System.out.printf("│%-" + innerWidth + "s│%n", " [1] View Request");
        System.out.printf("│%-" + innerWidth + "s│%n", " [2] Manage Colleges");
        System.out.printf("│%-" + innerWidth + "s│%n", " [3] Manage Sections");
        System.out.printf("│%-" + innerWidth + "s│%n", " [4] Manage Students");
        System.out.printf("│%-" + innerWidth + "s│%n", " [5] Manage Faculties");
        System.out.printf("│%-" + innerWidth + "s│%n", " [6] Backup Data");
        System.out.printf("│%-" + innerWidth + "s│%n", " [7] View Notifications");
        System.out.printf("│%-" + innerWidth + "s│%n", " ");
        System.out.printf("│%-" + innerWidth + "s│%n", " [0] Back");
        UI.footer();

        System.out.print("Select > ");
        int choice = in.nextInt();
        switch (choice) {
            case 0: // go back to login/signup choices break;
            case 1: viewRequest();break;
            case 7: viewNotifications(); break;
            case 2: manageColleges();break;
            case 3: manageSection();break;
            case 4: manageStudents();break;
            case 5: manageFaculties(); break;
            case 6: backUpData(); break;
            default: System.out.println("Invalid choice"); adminStart("A001"); break;
        }
    }
    // ------------------------------------------------------------//
    static ArrayList<String[]> requests = Faculty.requests;

    static void viewRequest() {
        ArrayList<Integer> ctr = new ArrayList<>();
        UI.header("ADMIN NOTIFICATION LIST");
        int notificationID = 1;
        for (String[] r : requests) {
            String print = String.format("   %-5d %-10s %s", notificationID, r[2], r[7]);
            System.out.printf("│%-" + innerWidth + "s│%n", print);
            ctr.add(notificationID);
            notificationID++;
        }
        UI.footer();
        System.out.print(" Select Notification ID to view: > ");
        int choice = in.nextInt();
        in.nextLine();

        if (choice >= 1 && choice <= requests.size()) {
            String[] selectedRequest = requests.get(choice - 1);
            UI.header(" Notification ID: " + choice);
            UI.liner(" Sender ID: " + selectedRequest[0]);
            UI.liner(" Recipient ID: " + selectedRequest[1]);
            UI.liner(" Room Code: " + selectedRequest[2]);
            UI.liner(" Building Name: " + selectedRequest[3]);
            UI.liner(" Day: " + selectedRequest[4]);
            UI.liner(" Start Time: " + selectedRequest[5]);
            UI.liner(" End Time: " + selectedRequest[6]);
            UI.liner(" Message: " + selectedRequest[7]);
            UI.liner(" ");
            UI.approveDecline();
        } else {
            System.out.println("Invalid Notification ID.");
        }
    }
    // ------------------------------------------------------------//
    static void manageColleges() {
        UI.manageUI("College");
        System.out.print("Select > ");
        int choice = in.nextInt();
        in.nextLine();

        switch (choice) {
            case 0: adminStart("A001"); break;
            case 1: createCollege();break;
            case 2: deleteCollege();break;
            case 3: updateCollege();break;
            case 4: viewAllColleges(); break;
            default: System.out.println("Invalid Choice"); manageColleges(); break;
        }
    }

    static ArrayList<College> colleges = College.colleges;
    // verifying before submitting
    // catch that when user input. big letter ang start
    // view all college
    // include in the manage__() auto generated userID();

    public static void createCollege() {
        String title = "CREATING NEW COLLEGE";
        UI.print(title);
        boolean isValid;
        String College_Description;
        String College_Name;

        do {
            System.out.print("  New College Description: ");;
            College_Description = in.nextLine().trim();

            if (College_Description.isBlank() ||
                    !College_Description.toUpperCase().matches("[a-zA-Z ]+")) {
                isValid = false;
                UI.print(" Description is empty or contains numeric or special characters.. Please try again.");
            } else isValid = true;

            for (College e : colleges) {
                if (College_Description.equalsIgnoreCase(College.getCollegeDescription(e))) {
                    UI.print(" College Description already exists. Please try again.");
                    isValid = false;
                    break;
                }
            }
        } while (!isValid);

        do {
            System.out.print("  New College Name: ");;
            College_Name = in.nextLine().toUpperCase().trim();

            if (College_Name.isBlank() || !College_Name.toUpperCase().matches("[A-Z]+")) {
                isValid = false;
                UI.print("College Name is empty or contains numeric or special characters..  Please try again.");
            } else isValid = true;

            for (College e : colleges) {
                if (College_Name.equalsIgnoreCase(e.getCollegeCode())) {
                    UI.print("College Description already exists. Please try again.");
                    isValid = false;
                    break;
                }
            }
        } while (!isValid);

        UI.header(title);
        System.out.printf("│%-" + innerWidth + "s│%n"," College Name: " + College_Name);
        System.out.printf("│%-" + innerWidth + "s│%n"," College Description: " + College_Description);
        System.out.printf("│%-" + innerWidth + "s│%n", " ");
        UI.submitExit();
        System.out.print("Press '/' to submit the changes > ");
        String choice = in.nextLine().toUpperCase().trim();
        switch (choice) {
            case "/": colleges.add(new College(College_Name, College_Description)); UI.print("NEW COLLEGE SUCCESSFULLY ADDED"); break;
            case "X": UI.print("Adding College was Cancelled.."); manageColleges(); break;
        }
        manageColleges();
    }
    public static void deleteCollege() {
        UI.print("DELETING EXISTING COLLEGE");

        String College_Name;
        boolean isValid;

        do {
            System.out.print("  Input Existing College Name(Abbreviation): ");
            College_Name = in.nextLine().trim().toUpperCase();

            if (College_Name.isEmpty() || !College_Name.toUpperCase().matches("[A-Z]+")) {
                UI.print(" Invalid input. Use letters only. Please try again.");
                isValid = false;
            }

            isValid = true;
            boolean found = false;

            for (int x = 0; x < colleges.size(); x++) {
                College e = colleges.get(x);

                if (College_Name.equals(e.getCollegeCode())) {
                    found = true;
                    UI.header("COLLEGE INFORMATION");
                    System.out.printf("│%-" + innerWidth + "s│%n"," College Name: " + e.getCollegeCode());
                    System.out.printf("│%-" + innerWidth + "s│%n"," College Description: " + College.getCollegeDescription(e));
                    System.out.printf("│%-" + innerWidth + "s│%n", " ");
                    UI.deleteExit();
                    System.out.print("Select > ");
                    String num = in.nextLine().toUpperCase().trim();

                    if (num.equals("/")) {
                        String deletedCollege = e.getCollegeCode();
                        colleges.remove(x);

                        UI.print("College " + deletedCollege + " successfully deleted.");
                    } else if (num.equals("X")) {
                        System.out.println("Deletion cancelled.");
                        manageColleges();
                    } else {
                        System.out.println("Invalid choice. Deletion cancelled.");
                        manageColleges();
                    }
                    break;
                }
            }
            if (!found) {
                System.out.println("College not found. Please try again.");
                isValid = false;
            }
        } while (!isValid);
    }
    public static void updateCollege() {
        UI.header("UPDATING NEW COLLEGE");
        String College_Description;
        String College_Name;

        System.out.printf("│%-" + innerWidth + "s│%n", " Detail to be updated");
        System.out.printf("│%-" + innerWidth + "s│%n", " [1] College Description");
        System.out.printf("│%-" + innerWidth + "s│%n", " [2] College Name");
        UI.footer();

        System.out.print("Select > ");
        int choice = in.nextInt();
        in.nextLine();
        switch (choice) {
            case 1:
                System.out.print("Input Existing College Name: ");
                College_Name = in.nextLine().toUpperCase().trim();
                boolean found = false;

                String newCollegeDescription;
                for (College c : colleges) {
                    if (College_Name.equals(c.getCollegeCode())) {
                        found = true;
                        UI.header("COLLEGE INFORMATION");
                        System.out.printf("│%-" + innerWidth + "s│%n","College Name: " + c.getCollegeCode());
                        System.out.printf("│%-" + innerWidth + "s│%n","College Description: " + College.getCollegeDescription(c));
                        UI.footer();
                        do {
                            System.out.print("New College Description: ");
                            newCollegeDescription = in.nextLine().trim();

                            if (newCollegeDescription.isBlank() || !newCollegeDescription.matches("[a-zA-Z ]+")) {
                                UI.print("Invalid input. Use letters only. Please try again.");
                                found = false;
                            }

                        } while (newCollegeDescription.isBlank() || !newCollegeDescription.matches("[a-zA-Z ]+"));
                        c.setCollegeDescription(newCollegeDescription);

                        break;
                    }

                }
                if (!found) {
                    System.out.println("College not found.");
                }
                break;

            case 2:
                System.out.print("Input Existing College Description: ");
                College_Description = in.nextLine().trim().toUpperCase().trim();
                found = false;

                String newCollegeName;

                for (College c : colleges) {
                    if (College_Description.equalsIgnoreCase(College.getCollegeDescription(c))) {
                        found = true;
                        UI.header("COLLEGE INFORMATION");
                        System.out.printf("│%-" + innerWidth + "s│%n","College Name: " + c.getCollegeCode());
                        System.out.printf("│%-" + innerWidth + "s│%n","College Description: " + College.getCollegeDescription(c));
                        UI.footer();
                        do {
                            System.out.print("New College Name: ");
                            newCollegeName = in.nextLine().toUpperCase().trim();

                            if (newCollegeName.isBlank() || !newCollegeName.matches("[A-Z]+")) {
                                System.out.println("Invalid input. Use uppercase letters only. Please try again.");
                            }
                        } while (newCollegeName.isBlank() || !newCollegeName.matches("[A-Z]+"));

                        c.setCollegeCode(newCollegeName);
                        UI.print("College " + c.getCollegeCode() + " successfully updated.");
                        break;
                    }
                }
                if (!found) {
                    System.out.println("College not found");
                }
                break;

            default:
                System.out.println("Invalid choice. Please try again.");
                break;

        }
    }
    public static void viewAllColleges(){
        College.displayCollege();
    }
    // ------------------------------------------------------------//
    static void manageSection() {
        UI.manageUI("Section");

        System.out.print("Select > ");
        int choice = in.nextInt();
        in.nextLine();

        switch (choice) {
            case 1: createSection();break;
            case 2: updateSection();break;
            case 3: deleteSection();break;
            case 4: viewAllSection(); break;
            default: System.out.println("invalid choice"); break;
        }
    }

    static ArrayList<Section> sections = Section.sections;
    static void createSection(){
        UI.print("ADD NEW SECTION");

        int maxSecID = 0;
        for (Section s: sections){
            if (Integer.parseInt(s.getSectionID()) > maxSecID){
                maxSecID = Integer.parseInt(s.getSectionID());
            }
        }

        maxSecID++;
        String sectionID = String.format("%04d", maxSecID);
        System.out.println(" SECTION ID: " + sectionID);

        System.out.print(" Year Level(1-4): " );
        int yearLevel = in.nextInt();
        in.nextLine();

        char block = 'A';
        boolean found = false;

        for (Section s: sections) {
            if (yearLevel == s.getyearLevel()) {
                found = true;
                break;
            }
        }


        if (found) {
            System.out.print(" Section under this year: ");

            for (Section s: sections) {
                if (yearLevel == s.getyearLevel()) {
                    System.out.print(s.getBlock() + " ");
                }
            }

            boolean isFound = false;
            do {
                isFound = true;
                System.out.print(" \n Block(A-D): ");
               String input = in.nextLine().trim().toUpperCase();

               if (input.length() != 1 || input.charAt(0) < 'A'
                        || input.charAt(0) > 'D') {
                    System.out.println("Invalid block. Enter A, B, C, D only.");
                    isFound = false;
                    continue;
                }
                block = input.charAt(0);
                for (Section s : sections) {
                    if (yearLevel == s.getyearLevel() && block == s.getBlock()) {
                        System.out.println("Block already exists for this year level.");
                        isFound = false;
                        break;
                    }
                }
            }while (!isFound);

        } else { System.out.println("No sections found fo that year level"); return;}

        int group;
        do {
            System.out.print(" Enter [0-2] Group: ");
            group = in.nextInt();


            if (group < 0 || group > 2) {
                System.out.println("Invalid group. Enter 0, 1, or 2 only.");
            }
        } while (group < 0 || group > 2);

        in.nextLine();

        String academicYear;
        do {
            System.out.print(" Academic Year (e.g., 2026-2027): ");
            academicYear = in.nextLine().trim();

            if (!academicYear.matches("\\d{4}-\\d{4}")) {
                System.out.println("Invalid format. Use YYYY-YYYY (e.g., 2026-2027).");
            } else {
                int startYear = Integer.parseInt(academicYear.substring(0, 4));
                int endYear = Integer.parseInt(academicYear.substring(5, 9));

                if (endYear != startYear + 1) {
                    System.out.println("Invalid academic year. Same year input");
                    academicYear = "";
                }
            }

        } while (academicYear.isEmpty() || !academicYear.matches("\\d{4}-\\d{4}"));

        String profID;

        boolean isFound = false;
        do {
            System.out.print("Professor ID: ");
            profID = in.nextLine().trim();

            for (User u : users) {
                if (u.getUserID().equalsIgnoreCase(profID)) {
                    System.out.println("Professor is now assigned");
                    isFound = true;
                    break;
                }
            }
            if (!isFound) {
                System.out.println("Professor ID does not exist. Try again.");
            }
        } while (!isFound);

        UI.header("NEW SECTION DETAILS");
        System.out.printf("│%-" + innerWidth + "s│\n", "Section ID: " + sectionID);
        System.out.printf("│%-" + innerWidth + "s│\n", "Year Level: " + yearLevel);
        System.out.printf("│%-" + innerWidth + "s│\n", "Block: " + block);
        System.out.printf("│%-" + innerWidth + "s│\n", "Group: " + group);
        System.out.printf("│%-" + innerWidth + "s│\n", "Academic Year: " + academicYear);
        System.out.printf("│%-" + innerWidth + "s│\n", "Registered Professor ID: " + profID);
        UI.footer();

        sections.add(new Section(sectionID,yearLevel,block,group,academicYear,profID));
        System.out.println("New Section Successfully Added");
    }
    static void updateSection() {
        Section selectedSection = null;
        Section copy =null;
        boolean isFound = false;
        do {
            isFound = false;
            UI.print("UPDATE EXISTING SECTION DETAILS");
            System.out.print("  Enter Existing Section ID: ");
            String sectionID = in.nextLine().trim();

            for (Section section : sections) {
                if (sectionID.equalsIgnoreCase(section.getSectionID())) {
                    selectedSection = section;
                    copy = section;
                    isFound = true;
                    break;
                }
            }
            if (!isFound) {
                System.out.println("Section ID does not exist. Try again.");
            }
        } while (!isFound);


        do {
            UI.header("UPDATE SECTION DETAILS");
            UI.liner(" [1] Section ID: " + selectedSection.getSectionID());
            if (selectedSection.getgroup() == 0) {
                UI.liner(" [2] Year Level/Block/Group: " + selectedSection.getyearLevel()+
                        selectedSection.getBlock());
            } else UI.liner(" [2] Year Level/Block/Group: " + selectedSection.getyearLevel()+
                    selectedSection.getBlock() +" - G" + selectedSection.getgroup() );
            UI.liner(" [3] Academic Year: " + Section.getAcademicYear(selectedSection));
            UI.liner(" [4] Registered Professor ID: " + Section.getProfessorID(selectedSection));
            UI.liner(" ");
            UI.submitExit();
            System.out.print("Select > ");
            String choice = in.nextLine().trim();

            switch (choice) {
                case "/": UI.print("Updated successfully"); manageSection(); break;
                case "X":
                    System.out.println("Updating Section was cancelled. Reverting changes");
                    for (int c = 0; c < sections.size(); c++) {
                        if (sections.get(c).getSectionID().equalsIgnoreCase(selectedSection.getSectionID())) {
                            sections.set(c, copy);
                            break;
                        }
                    }
                    manageSection();
                    break;

                case "1":
                    String newSectionID;

                    do {
                        isFound = true;
                        System.out.print("Enter New Section ID >  ");
                        newSectionID = in.nextLine().trim();

                        if (!newSectionID.matches("\\d{4}")) {
                            System.out.println("Invalid input. Please enter exactly 4 digits (e.g., 0003).");
                            isFound = false;
                        } else for (Section section : sections) {
                            if (section.getSectionID().equals(newSectionID)) {
                                System.out.println("Section already exists");
                                isFound = false;
                                break;
                            }
                        }
                    } while (!isFound);
                    selectedSection.setSectionID(newSectionID);
                    UI.print("Section ID successfully updated");
                break;
                case "2":
                    System.out.print("Enter New Year Level(1-4) > ");
                    int newyearlevel = in.nextInt(); in.nextLine();

                    System.out.print("Enter New Block(A-D) > ");
                    char newblock = in.nextLine().trim().toUpperCase().charAt(0);

                    System.out.print("Enter New Group(0-2) > ");
                    int newgroup = in.nextInt(); in.nextLine();

                    if (newyearlevel < 1 || newyearlevel > 4 ||
                            newblock < 'A' || newblock > 'D' ||
                            newgroup < 0 || newgroup > 2) {

                        System.out.println("Invalid input. Please try again.");
                    } else {
                        boolean isDuplicate = false;

                        for (Section s : sections) {
                            if (s != selectedSection && newyearlevel == s.getyearLevel() &&
                                    newblock == s.getBlock() && newgroup == s.getgroup()) {

                                System.out.println("Year Level/Block/Group already exists.");
                                isDuplicate = true;
                                break;
                            }
                        }
                        if (!isDuplicate) {
                            selectedSection.setyearLevel(newyearlevel);
                            selectedSection.setBlock(newblock);
                            selectedSection.setGroup(newgroup);

                            System.out.println("Section details updated successfully");
                        }
                    }
                break;

                case "3":
                    boolean found = false;
                    String newacademicYear;
                    do {
                        System.out.print("Enter New Academic Year (ex: 2025-2026) > ");
                        newacademicYear = in.nextLine().trim();

                        if (!newacademicYear.matches("\\d{4}-\\d{4}")) {
                            System.out.println("Invalid format. Use YYYY-YYYY (e.g., 2026-2027).");
                        } else {
                            int startYear = Integer.parseInt(newacademicYear.substring(0, 4));
                            int endYear = Integer.parseInt(newacademicYear.substring(5, 9));

                            if (endYear != startYear + 1) {
                                System.out.println("Invalid academic year. Same year input");
                            } else found = true;
                        }
                    }while (!found);
                    selectedSection.setAcademicYear(newacademicYear);
                    System.out.println("Academic Year updated successfully");
                break;

                case "4":
                    String newprofessorID;
                    found = false;
                    do {
                        System.out.print("Register to New Professor ID (ex. F001) >  ");
                        newprofessorID = in.nextLine().trim();
                        for (User u : users) {
                            if(u.getUserID().startsWith("F") && u.getUserID().equalsIgnoreCase(newprofessorID)) {
                                selectedSection.setProfessorID(u.getUserID());
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            System.out.println("Invalid or Non-Existing professorID. Please try again");
                        }
                    } while (!found);
                break;
            }
        } while (true);
    }
    static void deleteSection(){
        UI.print("DELETE EXISTING SECTION");
        String sectionID;
        do {
            System.out.print("Enter valid Section ID: ");
            sectionID = in.nextLine().trim().toUpperCase();

            if (sectionID.isBlank()){
                System.out.println("Invalid Section ID. Enter valid Section ID.");
            }

            for (Section s: sections) {
                if (s.getSectionID().equalsIgnoreCase(sectionID)) {
                    UI.header("SECTION DETAILS");
                    UI.liner("Section ID: " + sectionID);
                    UI.liner("Year Level: " + s.getyearLevel());
                    UI.liner("Block: " + s.getBlock());
                    UI.liner("Group: " + s.getgroup());
                    UI.liner("Academic Year: " + Section.getAcademicYear(s));
                    UI.liner("Registered Professor ID: " + Section.getProfessorID(s));
                    UI.footer();
                    UI.deleteExit();
                    System.out.print("Select > ");
                    String choice = in.nextLine().trim();
                    switch (choice) {
                        case "/":
                            String copy = sectionID;
                            sections.remove(s);
                            UI.print("Section " + copy + " has been successfully deleted.");
                            manageSection();
                            break;
                        case "X":
                            System.out.println("Deletion cancelled.");
                            manageSection();
                            break;
                        default:
                            System.out.println("Invalid choice. Try again."); manageSection(); break;
                    }
                    break;
                }
            }
        } while (sectionID.isBlank());
    }
    static void viewAllSection(){
        DATA.Section.displaySection();

        int choice;
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
            System.out.print("Select > ");
            choice = in.nextInt();

            if  (choice != 0) {
                System.out.println("Invalid choice. Try again.");
            }
        }while (choice != 0);
        manageSection();
    }
    // ------------------------------------------------------------//
    static void manageStudents() {
        UI.manageUI("Students");
        System.out.print("Select > ");
        int choice = in.nextInt();
        in.nextLine();

        switch (choice) {
            case 0: adminStart("A001"); break;
            case 1: createStudent();break;
            case 2: deleteStudent();break;
            case 3: updateStudent();break;
            case 4: viewAllStudents(); break;

            default: System.out.println("invalid choice");break;
        }
    }

    static void createStudent() {Signup.signUp("Add New Student");}
    static void deleteStudent() {
        UI.header("DELETE EXISTING STUDENT PROFILE");
        String userID;
        do {
            System.out.println("Enter User ID: ");
            userID = in.nextLine();

            if (userID.isBlank() || !userID.startsWith("S")) {
                System.out.println("Invalid input. Please try again.");
            }
        } while (!userID.isBlank());

        for (User user : users) {
            if (user.getUserID().equals(userID) || !userID.startsWith("S")) {
                System.out.printf("│%-" + innerWidth + "s│\n","USER INFORMATION");
                System.out.printf("│%-" + innerWidth + "s│\n","User ID: " + user.getUserID());
                System.out.printf("│%-" + innerWidth + "s│\n","User Name: " + user.getUserName());
                System.out.printf("│%-" + innerWidth + "s│\n","Email: " + user.getEmail());
                System.out.printf("│%-" + innerWidth + "s│\n","Password: " + user.getPassword());
                System.out.printf("│%-" + innerWidth + "s│\n","Address : " + user.getAddress());
                System.out.printf("│%-" + innerWidth + "s│\n","Postal Code: " + user.getPostalCode());
                System.out.printf("│%-" + innerWidth + "s│\n","Birth Date: " + user.getBirthDate());
                System.out.printf("│%-" + innerWidth + "s│\n","Sex: " + user.getSex());

                System.out.println("Sure to delete the User's Information?");
                System.out.println("[/] Delete \n[X] Cancel");
                String choice = in.nextLine();

                switch (choice) {
                    case "/":
                        users.remove(user);
                        System.out.println("User successfully deleted.");
                        break;
                    case "X": System.out.println("Deleting User is cancelled");break;
                    default: System.out.println("invalid choice"); deleteStudent(); break;
                }
            }
        }
    }
    static void updateStudent() {
        UI.print("UPDATE EXISTING STUDENT PROFILE");
        String userID;
        User selectedUser = null;
        do {
            System.out.print("  Enter User ID: ");
            userID = in.nextLine().trim().toUpperCase();

            if (userID.isBlank()) {
                System.out.println("Invalid input. Please try again.");
            }
            for (User user : users) {
                if (user.getUserID().equalsIgnoreCase(userID)
                        && user.getUserID().startsWith("S")) {
                    selectedUser = user;
                    break;
                }
            }
            if (selectedUser == null) {
                System.out.println("Student ID not found. Please try again.");
            }
        } while (selectedUser == null);
        updateProfile(userID);
    }
    static void viewAllStudents(){
        UI.header("VIEW ALL STUDENT PROFILE");
        for (User user : users) {
            if(user.getUserID().startsWith("S")){
                System.out.printf("│%-" + innerWidth + "s│\n"," User ID: " + user.getUserID());
                System.out.printf("│%-" + innerWidth + "s│\n"," User Name: " + user.getUserName());
                System.out.printf("│%-" + innerWidth + "s│\n"," Email: " + user.getEmail());
                System.out.printf("│%-" + innerWidth + "s│\n"," Password: " + user.getPassword());
                System.out.printf("│%-" + innerWidth + "s│\n"," Address : " + user.getAddress());
                System.out.printf("│%-" + innerWidth + "s│\n"," Postal Code: " + user.getPostalCode());
                System.out.printf("│%-" + innerWidth + "s│\n"," Birth Date: " + user.getBirthDate());
                System.out.printf("│%-" + innerWidth + "s│\n"," Sex: " + user.getSex());
                System.out.printf("│%-" + innerWidth + "s│\n"," ");
            }
            break;
        }
        System.out.printf("│%-" + innerWidth + "s│\n"," [0] Back ");
        UI.footer();
        int choice = in.nextInt();
        switch (choice) {case 0: manageStudents(); break;
            default: System.out.println("invalid choice");break;
        }
    }
    // ------------------------------------------------------------//
    static void manageFaculties(){
        UI.manageUI("Faculties");
        System.out.print("Select > ");
        int choice = in.nextInt();
        in.nextLine();

        switch (choice) {
            case 1: createFaculty();break;
            case 2: deleteFaculty();break;
            case 3: updateFaculty();break;
            case 4: viewAllFaculty(); break;
            default: System.out.println("invalid choice");break;
        }
    }

    static void createFaculty(){
        Signup.signUp("Add New Faculty");
    }
    static void deleteFaculty(){
        UI.header("DELETE EXISTING FACULTY PROFILE");
        String userID;
        do {
            System.out.println("Enter User ID: ");
            userID = in.nextLine();

            if (userID.isBlank() || !userID.startsWith("F")) {
                System.out.println("Invalid input. Please try again.");
            }
        } while (!userID.isBlank() ||  !userID.startsWith("F"));

        for (User user : users) {
            if (user.getUserID().equals(userID)) {
                System.out.printf("│%-" + innerWidth + "s│\n","USER INFORMATION");
                System.out.printf("│%-" + innerWidth + "s│\n","User ID: " + user.getUserID());
                System.out.printf("│%-" + innerWidth + "s│\n","User Name: " + user.getUserName());
                System.out.printf("│%-" + innerWidth + "s│\n","Email: " + user.getEmail());
                System.out.printf("│%-" + innerWidth + "s│\n","Password: " + user.getPassword());
                System.out.printf("│%-" + innerWidth + "s│\n","Address : " + user.getAddress());
                System.out.printf("│%-" + innerWidth + "s│\n","Postal Code: " + user.getPostalCode());
                System.out.printf("│%-" + innerWidth + "s│\n","Birth Date: " + user.getBirthDate());
                System.out.printf("│%-" + innerWidth + "s│\n","Sex: " + user.getSex());

                System.out.println("Sure to delete this Faculty's Information?");
                System.out.println("[/] Delete \n[X] Cancel");
                String choice = in.nextLine();

                switch (choice) {
                    case "/":
                        users.remove(user);
                        System.out.println("Faculty successfully deleted.");
                        break;
                    case "X": System.out.println("Deleting Faculty is cancelled");break;
                    default: System.out.println("Invalid choice"); deleteStudent(); break;
                }
            }
        }
    }
    static void updateFaculty(){
        UI.print("UPDATE EXISTING FACULTY PROFILE");
        String userID;
        User selectedUser = null;
        do {
            System.out.print("  Enter User ID: ");
            userID = in.nextLine().trim().toUpperCase();

            if (userID.isBlank()) {
                System.out.println("Invalid input. Please try again.");
            }
            for (User user : users) {
                if (user.getUserID().equalsIgnoreCase(userID)
                        && user.getUserID().startsWith("F")) {
                    selectedUser = user;
                    break;
                }
            }
            if (selectedUser == null) {
                System.out.println("Faculty ID not found. Please try again.");
            }
        } while (selectedUser == null);
        updateProfile(userID);
    }
    static void viewAllFaculty(){
        UI.header("VIEW ALL FACULTY PROFILE");
        for (User user : users) {
            if(user.getUserID().startsWith("F")){
                System.out.printf("│%-" + innerWidth + "s│\n","User ID: " + user.getUserID());
                System.out.printf("│%-" + innerWidth + "s│\n","User Name: " + user.getUserName());
                System.out.printf("│%-" + innerWidth + "s│\n","Email: " + user.getEmail());
                System.out.printf("│%-" + innerWidth + "s│\n","Password: " + user.getPassword());
                System.out.printf("│%-" + innerWidth + "s│\n","Address : " + user.getAddress());
                System.out.printf("│%-" + innerWidth + "s│\n","Postal Code: " + user.getPostalCode());
                System.out.printf("│%-" + innerWidth + "s│\n","Birth Date: " + user.getBirthDate());
                System.out.printf("│%-" + innerWidth + "s│\n","Sex: " + user.getSex());
                System.out.printf("│%-" + innerWidth + "s│\n"," ");
            }
            break;
        }
        System.out.printf("│%-" + innerWidth + "s│\n"," [0] Back ");
        int choice = in.nextInt();
        switch (choice) {case 0: manageStudents(); break;
            default: System.out.println("invalid choice");break;
        }
        UI.footer();
    }

    // ------------------------------------------------------------//
    static void viewNotifications(){
        ArrayList<Notification> notifications = Notification.notifications;
        UI.print("VIEW NOTIFICATIONS");

        for (Notification n : notifications) {
            UI.header("NOTIFICATION #" + getNotificationID());
            UI.liner("Sender ID: " + n.getSenderID());
            UI.liner("Recipient ID: " + n.getRecipientID());
            UI.liner("Room Code: " + n.getRoomCode());
            UI.liner("Building Name: " + n.getBuildingName());
            UI.liner("Day: " + n.getDay());
            UI.liner("Time: " + n.getStartTime() + " -  " + n.getEndTime());
            UI.liner("Day: " + n.getMessage());
            UI.footer();
        }

    }

    // ------------------------------------------------------------//
    static void backUpData(){
        //BackUp.backup;
    }
}