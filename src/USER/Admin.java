package USER;
import DATA.Building;
import DATA.Course;
import DATA.Program;
import DATA.Room;
import UI.*;
import java.util.Scanner;

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
                if (!(choice >= 1 && choice <= 4) && choice != 0) UI.print("Invalid Input");
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
            roomManage("CREATE", roomCode, roomDescription, floorNumber, buildingCode);

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

        Room.displayRoom(r.getRoomCode());

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
        if (floorNumber == -1) floorText = "";
        else if (floorNumber == 1) floorText = "1st Floor";
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
}
