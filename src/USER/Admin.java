package USER;
import DATA.*;
import FUNCTION.*;
import UI.*;

import static DATA.College.colleges;
import static DATA.Section.sections;
import static UI.UI.INNERWIDTH;
import java.util.ArrayList;

public class Admin extends User {

    // ADMIN FIELDS
    private static int nextAdminID = 1;
    private String role;

    // ADMIN CONSTRUCTOR
    public Admin(String lastName, String firstName, String middleInitial, String emailAddress,
                 String password, String address, int postalCode, String birthDate, char sex, String role) {
        super(String.format("A%04d", nextAdminID++), lastName, firstName, middleInitial, emailAddress, password,
                address, postalCode, birthDate, sex);
        this.role = role;
    }

    // ADMIN SETTER METHOD
    private void setRole(String role) {
        this.role = role;
    }

    // ADMIN GETTER METHOD
    public String getRole() {
        return role;
    }

    // ADMIN METHODS
    private static void manageRooms() {
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
            UI.liner(String.format(" [1] Room Code         : %s", roomCode));
            UI.liner(String.format(" [2] Room Description  : %s", roomDescription));

            if (floorNumber == -1) UI.liner(String.format(" [3] Floor Number      : %s", floorNumber));
            else if (floorNumber == 1) UI.liner(String.format(" [3] Floor Number      : %s", floorNumber + "st Floor"));
            else if (floorNumber == 2) UI.liner(String.format(" [3] Floor Number      : %s", floorNumber + "nd Floor"));
            else if (floorNumber == 3) UI.liner(String.format(" [3] Floor Number      : %s", floorNumber + "rd Floor"));
            else UI.liner(String.format(" [3] Floor Number      : %s", floorNumber + "th Floor"));
            UI.liner(String.format(" [4] Building Code     : %s", buildingCode));
            UI.liner("");
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
                        if (roomCode.trim().isEmpty()) UI.print("Invalid Input");
                        if (Room.existRoomCode(roomCode)) UI.print("Room Code already exists");
                    } while(roomCode.trim().isEmpty() || Room.existRoomCode(roomCode));
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
                        if (roomCode.trim().isEmpty()) UI.print("Invalid Input");
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
    private static void roomManage(String text, String roomCode, String roomDescription, int floorNumber,
                                   String buildingCode) {
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

    private static void manageCourses() {
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
        String courseCode = "", courseDescription = "", courseType = "", programCode = "", facultyID = "", choice = "";
        int creditUnits = -1; boolean submit = false;

        while (!submit) {
            courseManage("CREATE", courseCode, courseDescription, creditUnits, courseType, programCode, facultyID);

            do {
                System.out.print("  Select > ");
                choice = in.nextLine();
                if (!choice.matches("[1-6X]") && !choice.equals("/")) UI.print("Invalid Choice");
            } while (!choice.matches("[1-6X]") && !choice.equals("/"));

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
                        System.out.print("   Enter Course Type (GEN ED/MAJOR) > ");
                        courseType = in.nextLine();
                        if(!courseType.matches("GEN ED|MAJOR")) UI.print("Invalid Input");
                    } while(!courseType.matches("GEN ED|MAJOR"));
                    break;
                case "5":
                    do {
                        System.out.print("   Enter Program Code > ");
                        programCode = in.nextLine();
                        if(!Program.existProgramCode(programCode)) UI.print("Program Code does not exist");
                    } while(!Program.existProgramCode(programCode));
                    break;
                case "6":
                    do {
                        System.out.print("   Enter Faculty ID > ");
                        facultyID = in.nextLine();
                        if(!Faculty.existUserID(facultyID)) UI.print("Faculty ID does not exist");
                    } while(!Faculty.existUserID(facultyID));
                    break;
                case "/":
                    if (courseCode.isEmpty() || courseDescription.isEmpty() || creditUnits == -1 ||
                            courseType.isEmpty() || programCode.isEmpty() || facultyID.isEmpty())
                        UI.print("Details are incomplete");
                    else {
                        Course.courses.add(new Course(courseCode, courseDescription, creditUnits, courseType, programCode, facultyID));
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
        String courseCode = "", courseDescription = "", courseType = "", programCode = "", facultyID = "", choice = "";
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
        facultyID = Course.getFacultyID(c);

        while (!submit) {
            courseManage("EDIT", courseCode, courseDescription, creditUnits, courseType, programCode, facultyID);

            do {
                System.out.print("  Select > ");
                choice = in.nextLine();
                if (!choice.matches("[1-6X]") && !choice.equals("/")) UI.print("Invalid Input");
            } while (!choice.matches("[1-6X]") && !choice.equals("/"));

            switch(choice) {
                case "1":
                    do {
                        System.out.print("   Enter Course Code > ");
                        courseCode = in.nextLine();
                        if (Course.existCourseCode(courseCode) && !Course.getCourseCode(c).equals(courseCode))
                            UI.print("Course Code already exists");
                    } while(Course.existCourseCode(courseCode) && !Course.getCourseCode(c).equals(courseCode));
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
                        System.out.print("   Enter Course Type (GEN ED/MAJOR) > ");
                        courseType = in.nextLine();
                        if(!courseType.matches("GEN ED|MAJOR")) UI.print("Invalid Input");
                    } while(!courseType.matches("GEN ED|MAJOR"));
                    break;
                case "5":
                    do {
                        System.out.print("   Enter Program Code > ");
                        programCode = in.nextLine();
                        if(!Program.existProgramCode(programCode)) UI.print("Program Code does not exist");
                    } while(!Program.existProgramCode(programCode));
                    break;
                case "6":
                    do {
                        System.out.print("   Enter Faculty ID > ");
                        facultyID = in.nextLine();
                        if(!Faculty.existUserID(facultyID)) UI.print("Faculty ID does not exist");
                    } while(!Faculty.existUserID(facultyID));
                    break;
                case "/":
                    UI.print("Room successfully edited");
                    c.setCourseCode(courseCode); c.setCourseDescription(courseDescription);
                    c.setCreditUnits(creditUnits); c.setCourseType(courseType);
                    c.setProgramCode(programCode); c.setFacultyID(facultyID);
                    Course.displayCourse(Course.getCourseCode(c));
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
                                     int creditUnits, String courseType, String programCode, String facultyID) {
        UI.header(text.toUpperCase() + ": ROOM INFORMATION");
        UI.liner(String.format(" [1] COURSE CODE         : %s", courseCode));
        UI.liner(String.format(" [2] COURSE DESCRIPTION  : %s", courseDescription));
        String unitText;
        if (creditUnits == -1) unitText = "";
        else if (creditUnits == 1) unitText = "1st Floor";
        else if (creditUnits == 2) unitText = "2nd Floor";
        else if (creditUnits == 3) unitText = "3rd Floor";
        else unitText = creditUnits + "th Floor";
        UI.liner(String.format(" [3] CREDIT UNITS        : %s", unitText));
        UI.liner(String.format(" [4] COURSE TYPE         : %s", programCode));
        UI.liner(String.format(" [5] PROGRAM CODE        : %s", programCode));
        UI.liner(String.format(" [6] FACULTY ID          : %s", facultyID));
        UI.liner("");
        UI.option();
    }

//    static ArrayList<User> users = User.users;

//    public static String AutoGeneratedStudent() {
//        int max = 0;
//        for (User u : users) {
//            String userID = u.getUserID();
//            if (userID.startsWith("S")) {
//                int number = Integer.parseInt(userID.substring(1));
//
//                if (number > max) {
//                    max = number;
//                }
//            }
//        }
//        return String.format("S%03d", max + 1);
//    }

//    public static void adminStart(String userID) {
//        UI.header("WELCOME ADMIN " + userID);
//        System.out.printf("│%-" + innerWidth + "s│%n", " [1] View Request");
//        System.out.printf("│%-" + innerWidth + "s│%n", " [2] Manage Colleges");
//        System.out.printf("│%-" + innerWidth + "s│%n", " [3] Manage Sections");
//        System.out.printf("│%-" + innerWidth + "s│%n", " [4] Manage Students");
//        System.out.printf("│%-" + innerWidth + "s│%n", " [5] Manage Faculties");
//        System.out.printf("│%-" + innerWidth + "s│%n", " [6] Backup Data");
//        System.out.printf("│%-" + innerWidth + "s│%n", " [7] View Notifications");
//        System.out.printf("│%-" + innerWidth + "s│%n", " ");
//        System.out.printf("│%-" + innerWidth + "s│%n", " [0] Back");
//        UI.footer();
//
//        System.out.print("Select > ");
//        int choice = in.nextInt();
//        switch (choice) {
//            case 0: // go back to login/signup choices break;
//            case 1: viewRequest();break;
//            case 7: viewNotifications(); break;
//            case 2: manageColleges();break;
//            case 3: manageSection();break;
//            case 4: manageStudents();break;
//            case 5: manageFaculties(); break;
//            case 6: backUpData(); break;
//            default: System.out.println("Invalid choice"); adminStart("A001"); break;
//        }
//    }
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

    private static void manageColleges() {
        int choice = -1; String input = "";
        UI.manageUI("College");

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
            case 1: createCollege(); break;
            case 2: editCollege(); break;
            case 3: deleteCollege(); break;
            case 4: College.displayColleges(); break;
            case 0:
                // menu
        }
    }
    public static void createCollege() {
        String collegeDescription = ""; String collegeCode = ""; String choice = "";
        boolean submit = false;

        while (!submit) {
            collegeManage("CREATE", collegeCode, collegeDescription);

            do {
                System.out.print("  Select > ");
                choice = in.nextLine().trim();
                if (!choice.matches("[12X]") && !choice.equals("/")) UI.print("Invalid Choice");
            } while (!choice.matches("[12X]") && !choice.equals("/"));

            switch (choice) {
                case "1":
                    do {
                        System.out.print("   Enter College Code > ");
                        collegeCode = in.nextLine().toUpperCase().trim();
                        if (collegeCode.trim().isEmpty()) UI.print("Invalid Input");
                        if (College.existCollegeCode(collegeCode)) UI.print("College code already exists");
                    } while (collegeCode.trim().isEmpty() || College.existCollegeCode(collegeCode));
                    break;
                case "2":
                    do {
                        System.out.print("   Enter College Description > ");
                        collegeDescription = in.nextLine().trim();
                        if (collegeDescription.trim().isEmpty()) UI.print("Invalid Input");
                        if (College.existCollegeDesc(collegeDescription)) UI.print("College Description already exists");
                    } while (collegeDescription.trim().isEmpty() || College.existCollegeDesc(collegeDescription));
                    break;
                case "/":
                    if (collegeCode.isEmpty() || collegeDescription.isEmpty())
                        UI.print("Details are incomplete");
                    else {
                        colleges.add(new College(collegeCode, collegeDescription));
                        UI.print("College successfully created");
                        collegeManage("ADD", collegeCode, collegeDescription);
                        submit = true;
                    }
                    break;
                case "X":
                    submit = true;
                    break;
            }
        }
    }
    public static void editCollege() {
        String input = ""; String choice = ""; String collegeDescription = "";
        String collegeCode = ""; boolean submit = false;

        UI.header("EDIT EXISTING COLLEGE");

        do {
            System.out.print("  Enter College Code > ");
            input = in.nextLine();
            if (!College.existCollegeCode(input)) UI.print("College does not exist");
        } while(!College.existCollegeCode(input));

        College c = College.findCollege(input);
        if (c == null) {
            UI.print("College not found");
            return;
        }
        collegeCode = College.getCollege(c);
        collegeDescription = College.getCollegeDescription(c);

        while (!submit) {
            collegeManage("EDIT", collegeCode, collegeDescription);

            do {
                System.out.print("  Select > ");
                choice = in.nextLine();
                if (!choice.matches("[12X]") && !choice.equals("/")) UI.print("Invalid Input");
            } while (!choice.matches("[12X]") && !choice.equals("/"));

            switch (choice) {
                case "1":
                    do {
                        System.out.print("  Enter College Code > ");
                        collegeCode = in.nextLine();
                        if (collegeCode.trim().isEmpty()) UI.print("Invalid Input");
                        if (College.existCollegeCode(input) && !collegeCode.equalsIgnoreCase(College.getCollege(c)))
                            UI.print("College Code already exists");
                    } while (collegeCode.trim().isEmpty() && College.existCollegeCode(input)
                            && !collegeCode.equalsIgnoreCase(College.getCollege(c)));
                    break;
                case "2":
                    do {
                        System.out.print("  Input College Description > ");
                        collegeDescription = in.nextLine().trim();
                        if (collegeDescription.trim().isEmpty()) UI.print("Invalid Input");
                        if (College.existCollegeDesc(input) && !collegeDescription.equals(College.getCollegeDescription(c)))
                            UI.print("College Description already exists");
                    } while (collegeDescription.trim().isEmpty() && College.existCollegeDesc(input) &&
                            !collegeDescription.equals(College.getCollegeDescription(c)));
                    break;
                case "/":
                    UI.print("College successfully edited");
                    c.setCollegeCode(collegeCode); c.setCollegeDescription(collegeDescription);
                    College.displayColleges(c.getCollegeCode());
                    submit = true;
                    break;
                case "X":
                    submit = true;
                    break;
            }
        }
    }
    public static void deleteCollege() {
        String input; String choice; String collegeCode; String collegeDescription;

        UI.print("DELETING EXISTING COLLEGE");

        do {
            System.out.print("  Enter College Code > ");
            input = in.nextLine();
            if (!College.existCollegeCode(input)) UI.print("College does not exist");
        } while(!College.existCollegeCode(input));

        College c = College.findCollege(input);
        if (c == null) {
            UI.print("College not found");
            return;
        }

        collegeCode = College.getCollege(c);
        collegeDescription = College.getCollegeDescription(c);

        collegeManage("DELETE", collegeCode, collegeDescription);

        do {
            System.out.print("  Confirm College Deletion? (Y/N) > ");
            choice = in.nextLine().toUpperCase();
            if (!choice.matches("[YN]")) UI.print("Invalid Input");
        } while(!choice.matches("[YN]"));

        if (choice.equals("/")) {
            colleges.remove(c);
            UI.print("College successfully deleted");
        }
        else manageColleges();
    }
    private static void collegeManage(String text, String collegeCode, String collegeDescription) {
        UI.header(text.toUpperCase() + ": COLLEGE INFORMATION");
        System.out.printf("│%-" + innerWidth + "s│\n", String.format(" [1] COLLEGE CODE         : %s", collegeCode));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format(" [2] COLLEGE DESCRIPTION  : %s", collegeDescription));
        System.out.printf("│%-" + innerWidth + "s│\n", "");
        UI.option();
        UI.footer();
    }

    public static void manageSections() {
        int choice = -1; String input = "";
        UI.manageUI("Section");

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
            case 1: createSection(); break;
            case 2: editSection(); break;
            case 3: deleteSection(); break;
            case 4: Section.displaySections(); break;
            case 0:
        }
    }
    public static void createSection() {
        String sectionID = ""; String academicYear = ""; String courseID = ""; String choice; String input = "";
        int yearLevel = -1; int group = -1; char block = 0; boolean submit = false;

        while (!submit) {
            sectionManage("CREATE", yearLevel, block, group, academicYear, courseID);

            do {
                System.out.print("  Select > ");
                choice = in.nextLine().trim();
                if (!choice.matches("[1-5X]") && !choice.equals("/")) UI.print("Invalid Choice");
            } while (!choice.matches("[1-5X]") && !choice.equals("/"));

            switch (choice) {
                case "1":
                    do {
                        System.out.print("   Enter Year Level > ");
                        input = in.nextLine();

                        try {
                            yearLevel = Integer.parseInt(input);
                            if (yearLevel < 1 || yearLevel > 4) UI.print("Invalid Input");
                        } catch (NumberFormatException e) {
                            UI.print("Invalid Input");
                        }
                    } while (yearLevel < 1 || yearLevel > 4);
                    break;
                case "2":
                    do {
                        System.out.print("   Block > ");
                        input = in.nextLine().trim().toUpperCase();
                        if (input.length() != 1 || !(input.charAt(0) >= 'A' && input.charAt(0) <= 'J')) UI.print("Invalid Input");
                        block = input.charAt(0);
                    } while (input.length() != 1 || !(input.charAt(0) >= 'A' && input.charAt(0) <= 'J'));
                    break;
                case "3":
                    do {
                        System.out.print("  Enter Group > ");
                        input = in.nextLine().trim();

                        try {
                            group = Integer.parseInt(input);
                            if (group < 0 || group > 2) UI.print("Invalid Input");
                        } catch (NumberFormatException e) {
                            UI.print("Invalid Input");
                        }
                    } while (group < 0 || group > 2);
                    break;
                case "4":
                    do {
                        System.out.print("  Enter Academic Year > ");
                        academicYear = in.nextLine();
                        if (academicYear.trim().isEmpty()) UI.print("Invalid Input");
                        if (!academicYear.matches("\\d{4}-\\d{4}")) UI.print("Invalid Format");
                    } while (academicYear.isEmpty() || !academicYear.matches("\\d{4}-\\d{4}"));
                    break;
                case "5":
                    do {
                        System.out.print("  Enter Course ID > ");
                        courseID = in.nextLine();
                        if (courseID.trim().isEmpty()) UI.print("Invalid Input");
                        if (!Course.existCourseID(courseID)) UI.print("Course ID does not exist");
                    } while (courseID.trim().isEmpty() || !Course.existCourseID(courseID));
                    break;
                case "/":
                    if ((yearLevel < 1 || yearLevel > 4) || (group < 0 || group > 2) ||
                            !(input.charAt(0) >= 'A' && input.charAt(0) <= 'J') || academicYear.isEmpty() ||
                            courseID.trim().isEmpty())
                        UI.print("Details are incomplete");
                    else {
                        boolean gate = true;
                        for (Section s : sections) {
                            String section;
                            if (group != 0) section = yearLevel + "" + block + "-G" + group;
                            else section = yearLevel + "" + block;
                            if (section.equals(Section.getYearSection(s))) {
                                gate = false;
                                break;
                            }
                        }
                        if (gate) {
                            sections.add(new Section(yearLevel, block, group, academicYear, courseID));
                            UI.print("Section successfully created");
                            sectionManage("CREATE", yearLevel, block, group, academicYear, courseID);
                            submit = true;
                        }
                        else UI.print("Section already exists");
                    }
                    break;
                case "X":
                    submit = true;
                    break;
            }
        }
    }
    static void editSection() {
        String sectionID = ""; String academicYear = ""; String courseID = ""; String choice; String input = "";
        int yearLevel = -1; int group = -1; char block = 0; boolean submit = false;

        UI.print("EDIT EXISTING SECTION");

        do {
            System.out.print("  Enter Section ID > ");
            input = in.nextLine();
            if (!Section.existSectionID(input)) UI.print("Section does not exist");
        } while(!Section.existSectionID(input));

        Section s = Section.findSection(input);
        if (s == null) {
            UI.print("Section not found");
            return;
        }
        sectionID = Section.getSection(s);
        yearLevel = Section.getYearLevel(s);
        block = Section.getBlock(s);
        group = Section.getGroup(s);
        academicYear = Section.getAcademicYear(s);
        courseID = Section.getCourseID(s);


        while (!submit) {
            sectionManage("EDIT", yearLevel, block, group, academicYear, courseID);

            do {
                System.out.print("  Select > ");
                choice = in.nextLine();
                if (!choice.matches("[17X]") && !choice.equals("/")) UI.print("Invalid Input");
            } while (!choice.matches("[17X]") && !choice.equals("/"));

            switch (choice) {
                case "1":
                    do {
                        System.out.print("  Enter Section ID > ");
                        sectionID = in.nextLine();
                        if (sectionID.trim().isEmpty()) UI.print("Invalid Input");
                        if (Section.existSectionID(sectionID) && sectionID.equalsIgnoreCase(Section.getSection(s)))
                            UI.print("Section ID already exists");
                    } while(sectionID.trim().isEmpty() && Section.existSectionID(sectionID) && sectionID.equalsIgnoreCase(Section.getSection(s)));
                    break;
                case "2":
                    do {
                        System.out.print("   Enter Year Level > ");
                        input = in.nextLine();

                        try {
                            yearLevel = Integer.parseInt(input);
                            if (yearLevel < 1 || yearLevel > 4) UI.print("Invalid Input");
                        } catch (NumberFormatException e) {
                            UI.print("Invalid Input");
                        }
                    } while (yearLevel < 1 || yearLevel > 4);
                    break;
                case "3":
                    do {
                        System.out.print("   Block > ");
                        input = in.nextLine().trim().toUpperCase();
                        if (input.length() != 1 || !(input.charAt(0) >= 'A' && input.charAt(0) <= 'J')) UI.print("Invalid Input");
                        block = input.charAt(0);
                    } while (input.length() != 1 || !(input.charAt(0) >= 'A' && input.charAt(0) <= 'J'));
                    break;
                case "4":
                    do {
                        System.out.print("  Enter Group > ");
                        input = in.nextLine().trim();

                        try {
                            group = Integer.parseInt(input);
                            if (group < 0 || group > 2) UI.print("Invalid Input");
                        } catch (NumberFormatException e) {
                            UI.print("Invalid Input");
                        }
                    } while (group < 0 || group > 2);
                    break;
                case "5":
                    do {
                        System.out.print("  Enter Academic Year > ");
                        academicYear = in.nextLine();
                        if (academicYear.trim().isEmpty()) UI.print("Invalid Input");
                        if (!academicYear.matches("\\d{4}-\\d{4}")) UI.print("Invalid Format");
                    } while (academicYear.isEmpty() || !academicYear.matches("\\d{4}-\\d{4}"));
                    break;
                case "6":
                    do {
                        System.out.print("  Enter Course ID > ");
                        courseID = in.nextLine();
                        if (courseID.trim().isEmpty()) UI.print("Invalid Input");
                        if (!Course.existCourseID(courseID)) UI.print("Course ID does not exist");
                    } while (courseID.trim().isEmpty() || !Course.existCourseID(courseID));
                    break;
                case "/":
                    boolean gate = true;
                    for (Section se : sections) {
                        String section;
                        if (group != 0) section = yearLevel + "" + block + "-G" + group;
                        else section = yearLevel + "" + block;
                        if (section.equals(Section.getYearSection(se))) {
                            gate = false;
                            break;
                        }
                    }

                    if (gate) {
                        UI.print("Section successfully updated");
                        s.setSectionID(sectionID); s.setYearLevel(yearLevel); s.setBlock(block); s.setGroup(group);
                        s.setAcademicYear(academicYear); s.setCourseID(courseID);
                        sectionManage("EDIT", yearLevel, block, group, academicYear, courseID);
                        submit = true;
                    }
                    else UI.print("Section already exists");
                    break;
                case "X":
                    submit = true;
                    break;
            }
        }
    }
    static void deleteSection() {
        String sectionID = ""; String academicYear = ""; String courseID = ""; String choice; String input = "";
        int yearLevel = -1; int group = -1; char block = 0; boolean submit = false;

        UI.print("DELETE EXISTING SECTION");

        do {
            System.out.print("  Enter Section ID > ");
            input = in.nextLine();
            if (!Section.existSectionID(input)) UI.print("Section does not exist");
        } while(!Section.existSectionID(input));

        Section s = Section.findSection(input);
        if (s == null) {
            UI.print("Section not found");
            return;
        }

        yearLevel = Section.getYearLevel(s);
        block = Section.getBlock(s);
        group = Section.getGroup(s);
        academicYear = Section.getAcademicYear(s);
        courseID = Section.getCourseID(s);

        sectionManage("DELETE", yearLevel, block, group, academicYear, courseID);

        do {
            System.out.print("  Confirm Section Deletion? (Y/N) > ");
            choice = in.nextLine().toUpperCase();
            if (!choice.matches("[YN]")) UI.print("Invalid Input");
        } while(!choice.matches("[YN]"));

        if (choice.equals("/")) {
            sections.remove(s);
            UI.print("Section successfully deleted");
        }
        else manageSections();
    }
    private static void sectionManage(String text, int yearLevel, char block, int group, String academicYear, String courseID) {
        UI.header(text.toUpperCase() + ": SECTION INFORMATION");
        System.out.printf("│%-" + innerWidth + "s│\n", String.format(" [1] SECTION ID     : %s", sections.getLast().getSectionID()));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format(" [2] YEAR LEVEL     : %s", yearLevel));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format(" [3] BLOCK          : %s", block));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format(" [4] GROUP          : %s", group));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format(" [5] ACADEMIC YEAR  : %s", academicYear));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format(" [6] COURSE ID      : %s", courseID));
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
        String contactNumber = ""; String collegeCode = ""; String supervisorID = ""; String role = ""; String userName;

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
            enrollmentStatus = Student.getEnrollmentStatus(u);
        }
        else if (userID.charAt(0) == 'F') {
            Faculty f = (Faculty) u;
            contactNumber = Faculty.getContactNumber(f);
            collegeCode = Faculty.getCollegeCode(f);
            supervisorID = Faculty.getSupervisorID(f);
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
        String collegeCode = ""; String role = ""; String supervisorID = "";

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
            enrollmentStatus = Student.getEnrollmentStatus(u);
        }
        else if (userID.charAt(0) == 'F') {
            Faculty f = (Faculty) u;
            contactNumber = Faculty.getContactNumber(f);
            collegeCode = Faculty.getCollegeCode(f);
            supervisorID = Faculty.getSupervisorID(f);
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
    // ------------------------------------------------------------//
//    private static void manageStudents() {
//        UI.manageUI("Students");
//        System.out.print("Select > ");
//        int choice = in.nextInt();
//        in.nextLine();
//
//        switch (choice) {
//            case 1: createStudent();break;
//            case 2: deleteStudent();break;
//            case 3: updateStudent();break;
//            case 4: viewAllStudents(); break;
//
//            default: System.out.println("invalid choice");break;
//        }
//    }
//    static void createStudent() {Signup.signUp("Add New Student");}
//    static void deleteStudent() {
//        UI.header("DELETE EXISTING STUDENT PROFILE");
//        String userID;
//        do {
//            System.out.println("Enter User ID: ");
//            userID = in.nextLine();
//
//            if (userID.isBlank() || !userID.startsWith("S")) {
//                System.out.println("Invalid input. Please try again.");
//            }
//        } while (!userID.isBlank());
//
//        for (User user : users) {
//            if (user.getUserID().equals(userID) || !userID.startsWith("S")) {
//                System.out.printf("│%-" + innerWidth + "s│\n","USER INFORMATION");
//                System.out.printf("│%-" + innerWidth + "s│\n","User ID: " + user.getUserID());
//                System.out.printf("│%-" + innerWidth + "s│\n","User Name: " + user.getUserName());
//                System.out.printf("│%-" + innerWidth + "s│\n","Email: " + user.getEmail());
//                System.out.printf("│%-" + innerWidth + "s│\n","Password: " + user.getPassword());
//                System.out.printf("│%-" + innerWidth + "s│\n","Address : " + user.getAddress());
//                System.out.printf("│%-" + innerWidth + "s│\n","Postal Code: " + user.getPostalCode());
//                System.out.printf("│%-" + innerWidth + "s│\n","Birth Date: " + user.getBirthDate());
//                System.out.printf("│%-" + innerWidth + "s│\n","Sex: " + user.getSex());
//
//                System.out.println("Sure to delete the User's Information?");
//                System.out.println("[/] Delete \n[X] Cancel");
//                String choice = in.nextLine();
//
//                switch (choice) {
//                    case "/":
//                        users.remove(user);
//                        System.out.println("User successfully deleted.");
//                        break;
//                    case "X": System.out.println("Deleting User is cancelled");break;
//                    default: System.out.println("invalid choice"); deleteStudent(); break;
//                }
//            }
//        }
//    }
//    static void updateStudent() {
//        UI.print("UPDATE EXISTING STUDENT PROFILE");
//        String userID;
//        User selectedUser = null;
//        do {
//            System.out.print("  Enter User ID: ");
//            userID = in.nextLine().trim().toUpperCase();
//
//            if (userID.isBlank()) {
//                System.out.println("Invalid input. Please try again.");
//            }
//            for (User user : users) {
//                if (user.getUserID().equalsIgnoreCase(userID)
//                        && user.getUserID().startsWith("S")) {
//                    selectedUser = user;
//                    break;
//                }
//            }
//            if (selectedUser == null) {
//                System.out.println("Student ID not found. Please try again.");
//            }
//        } while (selectedUser == null);
//        updateProfile(userID);
//    }
//    static void viewAllStudents(){
//        UI.header("VIEW ALL STUDENT PROFILE");
//        for (User user : users) {
//            if(user.getUserID().startsWith("S")){
//                System.out.printf("│%-" + innerWidth + "s│\n"," User ID: " + user.getUserID());
//                System.out.printf("│%-" + innerWidth + "s│\n"," User Name: " + user.getUserName());
//                System.out.printf("│%-" + innerWidth + "s│\n"," Email: " + user.getEmail());
//                System.out.printf("│%-" + innerWidth + "s│\n"," Password: " + user.getPassword());
//                System.out.printf("│%-" + innerWidth + "s│\n"," Address : " + user.getAddress());
//                System.out.printf("│%-" + innerWidth + "s│\n"," Postal Code: " + user.getPostalCode());
//                System.out.printf("│%-" + innerWidth + "s│\n"," Birth Date: " + user.getBirthDate());
//                System.out.printf("│%-" + innerWidth + "s│\n"," Sex: " + user.getSex());
//                System.out.printf("│%-" + innerWidth + "s│\n"," ");
//            }
//            break;
//        }
//        System.out.printf("│%-" + innerWidth + "s│\n"," [0] Back ");
//        UI.footer();
//        int choice = in.nextInt();
//        switch (choice) {case 0: manageStudents(); break;
//            default: System.out.println("invalid choice");break;
//        }
//    }
//    // ------------------------------------------------------------//
//    static void manageFaculties(){
//        UI.manageUI("Faculties");
//        System.out.print("Select > ");
//        int choice = in.nextInt();
//        in.nextLine();
//
//        switch (choice) {
//            case 1: createFaculty();break;
//            case 2: deleteFaculty();break;
//            case 3: updateFaculty();break;
//            case 4: viewAllFaculty(); break;
//            default: System.out.println("invalid choice");break;
//        }
//    }
//
//    static void createFaculty(){
//        Signup.signUp("Add New Faculty");
//    }
//    static void deleteFaculty(){
//        UI.header("DELETE EXISTING FACULTY PROFILE");
//        String userID;
//        do {
//            System.out.println("Enter User ID: ");
//            userID = in.nextLine();
//
//            if (userID.isBlank() || !userID.startsWith("F")) {
//                System.out.println("Invalid input. Please try again.");
//            }
//        } while (!userID.isBlank() ||  !userID.startsWith("F"));
//
//        for (User user : users) {
//            if (user.getUserID().equals(userID)) {
//                System.out.printf("│%-" + innerWidth + "s│\n","USER INFORMATION");
//                System.out.printf("│%-" + innerWidth + "s│\n","User ID: " + user.getUserID());
//                System.out.printf("│%-" + innerWidth + "s│\n","User Name: " + user.getUserName());
//                System.out.printf("│%-" + innerWidth + "s│\n","Email: " + user.getEmail());
//                System.out.printf("│%-" + innerWidth + "s│\n","Password: " + user.getPassword());
//                System.out.printf("│%-" + innerWidth + "s│\n","Address : " + user.getAddress());
//                System.out.printf("│%-" + innerWidth + "s│\n","Postal Code: " + user.getPostalCode());
//                System.out.printf("│%-" + innerWidth + "s│\n","Birth Date: " + user.getBirthDate());
//                System.out.printf("│%-" + innerWidth + "s│\n","Sex: " + user.getSex());
//
//                System.out.println("Sure to delete this Faculty's Information?");
//                System.out.println("[/] Delete \n[X] Cancel");
//                String choice = in.nextLine();
//
//                switch (choice) {
//                    case "/":
//                        users.remove(user);
//                        System.out.println("Faculty successfully deleted.");
//                        break;
//                    case "X": System.out.println("Deleting Faculty is cancelled");break;
//                    default: System.out.println("Invalid choice"); deleteStudent(); break;
//                }
//            }
//        }
//    }
//    static void updateFaculty(){
//        UI.print("UPDATE EXISTING FACULTY PROFILE");
//        String userID;
//        User selectedUser = null;
//        do {
//            System.out.print("  Enter User ID: ");
//            userID = in.nextLine().trim().toUpperCase();
//
//            if (userID.isBlank()) {
//                System.out.println("Invalid input. Please try again.");
//            }
//            for (User user : users) {
//                if (user.getUserID().equalsIgnoreCase(userID)
//                        && user.getUserID().startsWith("F")) {
//                    selectedUser = user;
//                    break;
//                }
//            }
//            if (selectedUser == null) {
//                System.out.println("Faculty ID not found. Please try again.");
//            }
//        } while (selectedUser == null);
//        updateProfile(userID);
//    }
//    static void viewAllFaculty(){
//        UI.header("VIEW ALL FACULTY PROFILE");
//        for (User user : users) {
//            if(user.getUserID().startsWith("F")){
//                System.out.printf("│%-" + innerWidth + "s│\n","User ID: " + user.getUserID());
//                System.out.printf("│%-" + innerWidth + "s│\n","User Name: " + user.getUserName());
//                System.out.printf("│%-" + innerWidth + "s│\n","Email: " + user.getEmail());
//                System.out.printf("│%-" + innerWidth + "s│\n","Password: " + user.getPassword());
//                System.out.printf("│%-" + innerWidth + "s│\n","Address : " + user.getAddress());
//                System.out.printf("│%-" + innerWidth + "s│\n","Postal Code: " + user.getPostalCode());
//                System.out.printf("│%-" + innerWidth + "s│\n","Birth Date: " + user.getBirthDate());
//                System.out.printf("│%-" + innerWidth + "s│\n","Sex: " + user.getSex());
//                System.out.printf("│%-" + innerWidth + "s│\n"," ");
//            }
//            break;
//        }
//        System.out.printf("│%-" + innerWidth + "s│\n"," [0] Back ");
//        int choice = in.nextInt();
//        switch (choice) {case 0: manageStudents(); break;
//            default: System.out.println("invalid choice");break;
//        }
//        UI.footer();
//    }

    // ------------------------------------------------------------//
//    public static void viewNotifications(){
//        UI.print("VIEW NOTIFICATIONS");
//
//        for (Notification n : notifications) {
//            UI.header("NOTIFICATION #" + getNotificationID());
//            UI.liner("Sender ID: " + n.getSenderID());
//            UI.liner("Recipient ID: " + n.getRecipientID());
//            UI.liner("Room Code: " + n.getRoomCode());
//            UI.liner("Building Name: " + n.getBuildingName());
//            UI.liner("Day: " + n.getDay());
//            UI.liner("Time: " + n.getStartTime() + " -  " + n.getEndTime());
//            UI.liner("Day: " + n.getMessage());
//            UI.footer();
//        }
//
//    }

    // ------------------------------------------------------------//
    static void backUpData(){
        //BackUp.backup;
    }
}