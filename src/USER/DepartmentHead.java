package USER;
import DATA.*;
import FUNCTION.*;
import UI.*;

import static DATA.College.colleges;
import static DATA.Schedule.schedules;
import static DATA.Section.sections;
import static UI.UI.INNERWIDTH;
import java.util.ArrayList;

public class DepartmentHead extends User {

    // DEPARTMENT HEAD FIELDS
    private static int nextDepartmentHeadID = 1;
    private String collegeCode;

    // DEPARTMENT HEAD CONSTRUCTOR
    public DepartmentHead(String lastName, String firstName, String middleInitial,
                          String emailAddress, String password, String address,
                          int postalCode, String birthDate, char sex, String collegeCode) {
        super(String.format("DH%04d", nextDepartmentHeadID++), lastName, firstName,
                middleInitial, emailAddress, password, address, postalCode, birthDate, sex);
        this.collegeCode = collegeCode;
    }

    // DEPARTMENT HEAD SETTER METHOD
    public void setCollegeCode(String collegeCode) {
        this.collegeCode = collegeCode;
    }

    // DEPARTMENT HEAD GETTER METHOD
    public String getCollegeCode() {
        return collegeCode;
    }

    public static DepartmentHead findDepartmentHead(String departmentHeadID) {
        for (User user : User.users) {
            if (user instanceof DepartmentHead
                    && user.getUserID().equals(departmentHeadID)) {
                return (DepartmentHead) user;
            }
        }

        return null;
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
                break;
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
                choice = in.nextLine().trim();
                if (!choice.matches("[1-4X]") && !choice.equals("/")) UI.print("Invalid Choice");
            } while (!choice.matches("[1-4X]") && !choice.equals("/"));

            switch (choice) {
                case "1":
                    do {
                        System.out.print("   Enter Room Code > ");
                        roomCode = in.nextLine().trim();
                        if (roomCode.isEmpty()) UI.print("Invalid Input");
                        else if (Room.existRoomCode(roomCode)) UI.print("Room Code already exists");
                    } while (roomCode.isEmpty() || Room.existRoomCode(roomCode));
                    break;

                case "2":
                    do {
                        System.out.print("   Enter Room Description > ");
                        roomDescription = in.nextLine().trim();
                        if (roomDescription.isEmpty()) UI.print("Invalid Input");
                        else if (Room.existRoomDesc(roomDescription)) UI.print("Room Description already exists");
                    } while (roomDescription.isEmpty() || Room.existRoomDesc(roomDescription));
                    break;

                case "3":
                    do {
                        System.out.print("   Enter Floor Number > ");
                        try {
                            String input = in.nextLine().trim();
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
                        buildingCode = in.nextLine().trim();
                        if (buildingCode.isEmpty()) UI.print("Invalid Input");
                        else if (!Building.verifyBuilding(buildingCode)) UI.print("Building Code does not exist");
                    } while (buildingCode.isEmpty() || !Building.verifyBuilding(buildingCode));
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
                    submit = true;
                    manageRooms();
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
            input = in.nextLine().trim();
            if (input.isEmpty()) UI.print("Invalid Input");
            else if (!Room.existRoomCode(input)) UI.print("Room does not exist");
        } while (input.isEmpty() || !Room.existRoomCode(input));

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
                choice = in.nextLine().trim();
                if (!choice.matches("[1-4X]") && !choice.equals("/")) UI.print("Invalid Input");
            } while (!choice.matches("[1-4X]") && !choice.equals("/"));

            switch (choice) {
                case "1":
                    do {
                        System.out.print("   Enter Room Code > ");
                        roomCode = in.nextLine().trim();
                        if (roomCode.isEmpty()) UI.print("Invalid Input");
                        else if (Room.existRoomCode(roomCode) && !roomCode.equalsIgnoreCase(r.getRoomCode()))
                            UI.print("Room Code already exists");
                    } while (roomCode.isEmpty()
                            || (Room.existRoomCode(roomCode) && !roomCode.equalsIgnoreCase(r.getRoomCode())));
                    break;

                case "2":
                    do {
                        System.out.print("   Enter Room Description > ");
                        roomDescription = in.nextLine().trim();
                        if (roomDescription.isEmpty()) UI.print("Invalid Input");
                        else if (Room.existRoomDesc(roomDescription)
                                && !roomDescription.equalsIgnoreCase(Room.getRoomDescription(r)))
                            UI.print("Room Description already exists");
                    } while (roomDescription.isEmpty()
                            || (Room.existRoomDesc(roomDescription)
                            && !roomDescription.equalsIgnoreCase(Room.getRoomDescription(r))));
                    break;

                case "3":
                    do {
                        System.out.print("   Enter Floor Number > ");
                        try {
                            input = in.nextLine().trim();
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
                        buildingCode = in.nextLine().trim();
                        if (buildingCode.isEmpty()) UI.print("Invalid Input");
                        else if (!Building.verifyBuilding(buildingCode)) UI.print("Building Code does not exist");
                    } while (buildingCode.isEmpty() || !Building.verifyBuilding(buildingCode));
                    break;

                case "/":
                    r.setRoomCode(roomCode);
                    r.setRoomDescription(roomDescription);
                    r.setFloorNumber(floorNumber);
                    r.setBuildingCode(buildingCode);
                    UI.print("Room successfully edited");
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
        String input; String choice; String roomCode; String roomDescription;
        int floorNumber; String buildingCode;

        UI.print("DELETE EXISTING ROOM");

        do {
            System.out.print("  Enter Room Code > ");
            input = in.nextLine().trim();
            if (input.isEmpty()) UI.print("Invalid Input");
            else if (!Room.existRoomCode(input)) UI.print("Room does not exist");
        } while (input.isEmpty() || !Room.existRoomCode(input));

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
            choice = in.nextLine().trim().toUpperCase();
            if (!choice.matches("[YN]")) UI.print("Invalid Input");
        } while (!choice.matches("[YN]"));

        if (choice.equals("Y")) {
            Room.rooms.remove(r);
            UI.print("Room successfully deleted");
        }
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
                if (!(choice >= 1 && choice <= 4) && choice != 0)
                    UI.print("Invalid Input");
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
                // Return to previous menu
                break;
        }
    }

    private static void createCourse() {
        String courseCode = "", courseDescription = "", courseType = "", choice = "";
        int creditUnits = -1; boolean submit = false;

        while (!submit) {
            courseManage("CREATE", courseCode, courseDescription, creditUnits, courseType);

            do {
                System.out.print("  Select > ");
                choice = in.nextLine().trim().toUpperCase();
                if (!choice.matches("[1-4X]") && !choice.equals("/"))
                    UI.print("Invalid Choice");
            } while (!choice.matches("[1-4X]") && !choice.equals("/"));

            switch (choice) {
                case "1":
                    do {
                        System.out.print("   Enter Course Code > ");
                        courseCode = in.nextLine().trim();

                        if (courseCode.isEmpty())
                            UI.print("Course Code cannot be empty");
                        else if (Course.existCourseCode(courseCode))
                            UI.print("Course Code already exists");
                    } while (courseCode.isEmpty() || Course.existCourseCode(courseCode));
                    break;

                case "2":
                    do {
                        System.out.print("   Enter Course Description > ");
                        courseDescription = in.nextLine().trim();

                        if (courseDescription.isEmpty())
                            UI.print("Course Description cannot be empty");
                        else if (Course.existCourseDesc(courseDescription))
                            UI.print("Course Description already exists");
                    } while (courseDescription.isEmpty() || Course.existCourseDesc(courseDescription));
                    break;

                case "3":
                    do {
                        System.out.print("   Enter Credit Units > ");
                        try {
                            String input = in.nextLine().trim();
                            creditUnits = Integer.parseInt(input);
                            if (creditUnits != 2 && creditUnits != 3)
                                UI.print("Invalid Credit Units");
                        } catch (NumberFormatException e) {
                            UI.print("Invalid Credit Units");
                            creditUnits = -1;
                        }
                    } while (creditUnits != 2 && creditUnits != 3);
                    break;

                case "4":
                    do {
                        System.out.print("   Enter Course Type (GEN ED/MAJOR) > ");
                        courseType = in.nextLine().trim().toUpperCase();

                        if (!courseType.matches("GEN ED|MAJOR"))
                            UI.print("Invalid Input");
                    } while (!courseType.matches("GEN ED|MAJOR"));
                    break;

                case "/":
                    if (courseCode.isEmpty() || courseDescription.isEmpty() ||
                            creditUnits == -1 || courseType.isEmpty()) {
                        UI.print("Details are incomplete");
                    } else {
                        Course.courses.add(new Course(courseCode, courseDescription, creditUnits, courseType));
                        UI.print("Course successfully created");
                        Course.displayCourse(courseCode);
                        submit = true;
                    }
                    break;

                case "X":
                    submit = true;
                    break;
            }
        }
    }

    private static void editCourse() {
        String courseCode, courseDescription, courseType, choice = "", input;
        int creditUnits; boolean submit = false;

        UI.print("EDIT EXISTING COURSE");

        do {
            System.out.print("  Enter Course Code > ");
            input = in.nextLine().trim();

            if (input.isEmpty() || !Course.existCourseCode(input))
                UI.print("Course does not exist");
        } while (input.isEmpty() || !Course.existCourseCode(input));

        Course c = Course.findCourse(input);

        if (c == null) {
            UI.print("Course not found");
            return;
        }

        courseCode = Course.getCourseCode(c);
        courseDescription = Course.getCourseDescription(c);
        creditUnits = Course.getCreditUnits(c);
        courseType = Course.getCourseType(c);

        while (!submit) {
            courseManage("EDIT", courseCode, courseDescription, creditUnits, courseType);

            do {
                System.out.print("  Select > ");
                choice = in.nextLine().trim().toUpperCase();

                if (!choice.matches("[1-4X]") && !choice.equals("/"))
                    UI.print("Invalid Input");
            } while (!choice.matches("[1-4X]") && !choice.equals("/"));

            switch (choice) {
                case "1":
                    do {
                        System.out.print("   Enter Course Code > ");
                        courseCode = in.nextLine().trim();

                        if (courseCode.isEmpty())
                            UI.print("Course Code cannot be empty");
                        else if (Course.existCourseCode(courseCode) &&
                                !Course.getCourseCode(c).equalsIgnoreCase(courseCode))
                            UI.print("Course Code already exists");
                    } while (courseCode.isEmpty() ||
                            (Course.existCourseCode(courseCode) &&
                                    !Course.getCourseCode(c).equalsIgnoreCase(courseCode)));
                    break;

                case "2":
                    do {
                        System.out.print("   Enter Course Description > ");
                        courseDescription = in.nextLine().trim();

                        if (courseDescription.isEmpty())
                            UI.print("Course Description cannot be empty");
                        else if (Course.existCourseDesc(courseDescription) &&
                                !Course.getCourseDescription(c).equalsIgnoreCase(courseDescription))
                            UI.print("Course Description already exists");
                    } while (courseDescription.isEmpty() ||
                            (Course.existCourseDesc(courseDescription) &&
                                    !Course.getCourseDescription(c).equalsIgnoreCase(courseDescription)));
                    break;

                case "3":
                    do {
                        System.out.print("   Enter Credit Units > ");
                        try {
                            input = in.nextLine().trim();
                            creditUnits = Integer.parseInt(input);

                            if (creditUnits != 2 && creditUnits != 3)
                                UI.print("Invalid Credit Units");
                        } catch (NumberFormatException e) {
                            UI.print("Invalid Credit Units");
                            creditUnits = -1;
                        }
                    } while (creditUnits != 2 && creditUnits != 3);
                    break;

                case "4":
                    do {
                        System.out.print("   Enter Course Type (GEN ED/MAJOR) > ");
                        courseType = in.nextLine().trim().toUpperCase();

                        if (!courseType.matches("GEN ED|MAJOR"))
                            UI.print("Invalid Input");
                    } while (!courseType.matches("GEN ED|MAJOR"));
                    break;

                case "/":
                    c.setCourseCode(courseCode);
                    c.setCourseDescription(courseDescription);
                    c.setCreditUnits(creditUnits);
                    c.setCourseType(courseType);

                    UI.print("Course successfully edited");
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
        String input, choice;

        UI.print("DELETE EXISTING COURSE");

        do {
            System.out.print("  Enter Course Code > ");
            input = in.nextLine().trim();

            if (input.isEmpty() || !Course.existCourseCode(input))
                UI.print("Course does not exist");
        } while (input.isEmpty() || !Course.existCourseCode(input));

        Course c = Course.findCourse(input);

        if (c == null) {
            UI.print("Course not found");
            return;
        }

        Course.displayCourse(c.getCourseCode());

        do {
            System.out.print("  Confirm Course Deletion? (Y/N) > ");
            choice = in.nextLine().trim().toUpperCase();

            if (!choice.matches("[YN]"))
                UI.print("Invalid Input");
        } while (!choice.matches("[YN]"));

        if (choice.equals("Y")) {
            Course.courses.remove(c);
            UI.print("Course successfully deleted");
        }
    }

    private static void courseManage(String text, String courseCode, String courseDescription,
                                     int creditUnits, String courseType) {
        UI.header(text.toUpperCase() + ": COURSE INFORMATION");
        UI.liner(String.format(" [1] COURSE CODE         : %s", courseCode));
        UI.liner(String.format(" [2] COURSE DESCRIPTION  : %s", courseDescription));
        UI.liner(String.format(" [3] CREDIT UNITS        : %s", creditUnits == -1 ? "" : creditUnits));
        UI.liner(String.format(" [4] COURSE TYPE         : %s", courseType));
        UI.liner("");
        UI.option();
    }

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
                if (!(choice >= 1 && choice <= 4) && choice != 0)
                    UI.print("Invalid Input");
            } catch (NumberFormatException e) {
                UI.print("Invalid Input");
                choice = -1;
            }
        } while (!(choice >= 1 && choice <= 4) && choice != 0);

        switch (choice) {
            case 1:
                createCollege();
                break;
            case 2:
                editCollege();
                break;
            case 3:
                deleteCollege();
                break;
            case 4:
                College.displayColleges();
                break;
            case 0:
                // Return to previous menu
                break;
        }
    }

    public static void createCollege() {
        String collegeDescription = "", collegeCode = "", choice = "";
        boolean submit = false;

        while (!submit) {
            collegeManage("CREATE", collegeCode, collegeDescription);

            do {
                System.out.print("  Select > ");
                choice = in.nextLine().trim().toUpperCase();
                if (!choice.matches("[12X]") && !choice.equals("/"))
                    UI.print("Invalid Choice");
            } while (!choice.matches("[12X]") && !choice.equals("/"));

            switch (choice) {
                case "1":
                    do {
                        System.out.print("   Enter College Code > ");
                        collegeCode = in.nextLine().trim().toUpperCase();

                        if (collegeCode.isEmpty())
                            UI.print("Invalid Input");
                        else if (College.existCollegeCode(collegeCode))
                            UI.print("College Code already exists");
                    } while (collegeCode.isEmpty() || College.existCollegeCode(collegeCode));
                    break;

                case "2":
                    do {
                        System.out.print("   Enter College Description > ");
                        collegeDescription = in.nextLine().trim();

                        if (collegeDescription.isEmpty())
                            UI.print("Invalid Input");
                        else if (College.existCollegeDesc(collegeDescription))
                            UI.print("College Description already exists");
                    } while (collegeDescription.isEmpty() || College.existCollegeDesc(collegeDescription));
                    break;

                case "/":
                    if (collegeCode.isEmpty() || collegeDescription.isEmpty()) {
                        UI.print("Details are incomplete");
                    } else {
                        College.colleges.add(new College(collegeCode, collegeDescription));
                        UI.print("College successfully created");
                        College.displayColleges(collegeCode);
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
        String input = "", choice = "", collegeDescription, collegeCode;
        boolean submit = false;

        UI.header("EDIT EXISTING COLLEGE");

        do {
            System.out.print("  Enter College Code > ");
            input = in.nextLine().trim().toUpperCase();

            if (input.isEmpty() || !College.existCollegeCode(input))
                UI.print("College does not exist");
        } while (input.isEmpty() || !College.existCollegeCode(input));

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
                choice = in.nextLine().trim().toUpperCase();

                if (!choice.matches("[12X]") && !choice.equals("/"))
                    UI.print("Invalid Input");
            } while (!choice.matches("[12X]") && !choice.equals("/"));

            switch (choice) {
                case "1":
                    do {
                        System.out.print("   Enter College Code > ");
                        collegeCode = in.nextLine().trim().toUpperCase();

                        if (collegeCode.isEmpty())
                            UI.print("Invalid Input");
                        else if (College.existCollegeCode(collegeCode) &&
                                !collegeCode.equalsIgnoreCase(College.getCollege(c)))
                            UI.print("College Code already exists");
                    } while (collegeCode.isEmpty() ||
                            (College.existCollegeCode(collegeCode) &&
                                    !collegeCode.equalsIgnoreCase(College.getCollege(c))));
                    break;

                case "2":
                    do {
                        System.out.print("   Enter College Description > ");
                        collegeDescription = in.nextLine().trim();

                        if (collegeDescription.isEmpty())
                            UI.print("Invalid Input");
                        else if (College.existCollegeDesc(collegeDescription) &&
                                !collegeDescription.equalsIgnoreCase(College.getCollegeDescription(c)))
                            UI.print("College Description already exists");
                    } while (collegeDescription.isEmpty() ||
                            (College.existCollegeDesc(collegeDescription) &&
                                    !collegeDescription.equalsIgnoreCase(College.getCollegeDescription(c))));
                    break;

                case "/":
                    c.setCollegeCode(collegeCode);
                    c.setCollegeDescription(collegeDescription);

                    UI.print("College successfully edited");
                    College.displayColleges(College.getCollege(c));
                    submit = true;
                    break;

                case "X":
                    submit = true;
                    break;
            }
        }
    }

    public static void deleteCollege() {
        String input, choice;

        UI.print("DELETE EXISTING COLLEGE");

        do {
            System.out.print("  Enter College Code > ");
            input = in.nextLine().trim().toUpperCase();

            if (input.isEmpty() || !College.existCollegeCode(input))
                UI.print("College does not exist");
        } while (input.isEmpty() || !College.existCollegeCode(input));

        College c = College.findCollege(input);

        if (c == null) {
            UI.print("College not found");
            return;
        }

        College.displayColleges(College.getCollege(c));

        do {
            System.out.print("  Confirm College Deletion? (Y/N) > ");
            choice = in.nextLine().trim().toUpperCase();

            if (!choice.matches("[YN]"))
                UI.print("Invalid Input");
        } while (!choice.matches("[YN]"));

        if (choice.equals("Y")) {
            College.colleges.remove(c);
            UI.print("College successfully deleted");
        }
    }

    private static void collegeManage(String text, String collegeCode, String collegeDescription) {
        UI.header(text.toUpperCase() + ": COLLEGE INFORMATION");
        UI.liner(String.format(" [1] COLLEGE CODE         : %s", collegeCode));
        UI.liner(String.format(" [2] COLLEGE DESCRIPTION  : %s", collegeDescription));
        UI.liner("");
        UI.option();
        UI.footer();
    }

    public static void manageSections() {
        int choice = -1;
        String input = "";

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

                if (!(choice >= 1 && choice <= 4) && choice != 0)
                    UI.print("Invalid Input");
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
                break;
        }
    }

    public static void createSection() {
        String academicYear = "", choice, input = "";
        int yearLevel = -1, group = -1;
        char block = 0;
        boolean submit = false;

        while (!submit) {
            sectionManage("CREATE",
                    String.format("SEC%03d", Section.getNextID()),
                    yearLevel, block, group, academicYear);

            do {
                System.out.print("  Select > ");
                choice = in.nextLine().trim();

                if (!choice.matches("[1-5X/]"))
                    UI.print("Invalid Choice");
            } while (!choice.matches("[1-5X/]"));

            switch (choice) {
                case "1":
                    do {
                        System.out.print("   Enter Year Level > ");
                        input = in.nextLine().trim();

                        try {
                            yearLevel = Integer.parseInt(input);
                            if (yearLevel < 1 || yearLevel > 4)
                                UI.print("Invalid Input");
                        } catch (NumberFormatException e) {
                            yearLevel = -1;
                            UI.print("Invalid Input");
                        }
                    } while (yearLevel < 1 || yearLevel > 4);
                    break;

                case "2":
                    do {
                        System.out.print("   Block > ");
                        input = in.nextLine().trim().toUpperCase();

                        if (input.length() != 1 ||
                                input.charAt(0) < 'A' || input.charAt(0) > 'J')
                            UI.print("Invalid Input");
                    } while (input.length() != 1 ||
                            input.charAt(0) < 'A' || input.charAt(0) > 'J');

                    block = input.charAt(0);
                    break;

                case "3":
                    do {
                        System.out.print("  Enter Group (0-2) > ");
                        input = in.nextLine().trim();

                        try {
                            group = Integer.parseInt(input);
                            if (group < 0 || group > 2)
                                UI.print("Invalid Input");
                        } catch (NumberFormatException e) {
                            group = -1;
                            UI.print("Invalid Input");
                        }
                    } while (group < 0 || group > 2);
                    break;

                case "4":
                    do {
                        System.out.print("  Enter Academic Year > ");
                        academicYear = in.nextLine().trim();

                        if (!academicYear.matches("\\d{4}-\\d{4}"))
                            UI.print("Invalid Format");
                    } while (!academicYear.matches("\\d{4}-\\d{4}"));
                    break;

                case "/":
                    if (yearLevel < 1 || yearLevel > 4 ||
                            block < 'A' || block > 'J' ||
                            group < 0 || group > 2 ||
                            !academicYear.matches("\\d{4}-\\d{4}")) {
                        UI.print("Details are incomplete");
                        break;
                    }

                    boolean gate = true;

                    for (Section s : Section.sections) {
                        if (Section.getYearSection(s).equals(
                                getYearSection(yearLevel, block, group)) &&
                                s.getAcademicYear().equalsIgnoreCase(academicYear)) {
                            gate = false;
                            break;
                        }
                    }

                    if (gate) {
                        Section.sections.add(
                                new Section(yearLevel, block, group, academicYear));

                        UI.print("Section successfully created");
                        submit = true;
                    } else {
                        UI.print("Section already exists for this academic year");
                    }
                    break;

                case "X":
                    submit = true;
                    break;

                case "5":
                    UI.print("No additional section fields are available.");
                    break;
            }
        }
    }

    public static void editSection() {
        String sectionID = "", academicYear = "", choice, input = "";
        int yearLevel, group;
        char block;
        boolean submit = false;

        UI.print("EDIT EXISTING SECTION");

        do {
            System.out.print("  Enter Section ID > ");
            sectionID = in.nextLine().trim();

            if (!Section.existSectionID(sectionID))
                UI.print("Section does not exist");
        } while (!Section.existSectionID(sectionID));

        Section s = Section.findSection(sectionID);

        if (s == null) {
            UI.print("Section not found");
            return;
        }

        sectionID = Section.getSection(s);
        yearLevel = Section.getYearLevel(s);
        block = Section.getBlock(s);
        group = Section.getGroup(s);
        academicYear = Section.getAcademicYear(s);

        while (!submit) {
            sectionManage("EDIT", sectionID, yearLevel, block, group, academicYear);

            do {
                System.out.print("  Select > ");
                choice = in.nextLine().trim();

                if (!choice.matches("[1-5X/]"))
                    UI.print("Invalid Choice");
            } while (!choice.matches("[1-5X/]"));

            switch (choice) {
                case "1":
                    UI.print("Section IDs are generated automatically and cannot be edited.");
                    break;

                case "2":
                    do {
                        System.out.print("   Enter Year Level > ");
                        input = in.nextLine().trim();

                        try {
                            yearLevel = Integer.parseInt(input);
                            if (yearLevel < 1 || yearLevel > 4)
                                UI.print("Invalid Input");
                        } catch (NumberFormatException e) {
                            yearLevel = -1;
                            UI.print("Invalid Input");
                        }
                    } while (yearLevel < 1 || yearLevel > 4);
                    break;

                case "3":
                    do {
                        System.out.print("   Block > ");
                        input = in.nextLine().trim().toUpperCase();

                        if (input.length() != 1 ||
                                input.charAt(0) < 'A' || input.charAt(0) > 'J')
                            UI.print("Invalid Input");
                    } while (input.length() != 1 ||
                            input.charAt(0) < 'A' || input.charAt(0) > 'J');

                    block = input.charAt(0);
                    break;

                case "4":
                    do {
                        System.out.print("  Enter Group (0-2) > ");
                        input = in.nextLine().trim();

                        try {
                            group = Integer.parseInt(input);
                            if (group < 0 || group > 2)
                                UI.print("Invalid Input");
                        } catch (NumberFormatException e) {
                            group = -1;
                            UI.print("Invalid Input");
                        }
                    } while (group < 0 || group > 2);
                    break;

                case "5":
                    do {
                        System.out.print("  Enter Academic Year > ");
                        academicYear = in.nextLine().trim();

                        if (!academicYear.matches("\\d{4}-\\d{4}"))
                            UI.print("Invalid Format");
                    } while (!academicYear.matches("\\d{4}-\\d{4}"));
                    break;

                case "/":
                    if (yearLevel < 1 || yearLevel > 4 ||
                            block < 'A' || block > 'J' ||
                            group < 0 || group > 2 ||
                            !academicYear.matches("\\d{4}-\\d{4}")) {
                        UI.print("Details are incomplete");
                        break;
                    }

                    boolean gate = true;

                    for (Section se : Section.sections) {
                        if (se != s &&
                                Section.getYearSection(se).equals(
                                        getYearSection(yearLevel, block, group)) &&
                                se.getAcademicYear().equalsIgnoreCase(academicYear)) {
                            gate = false;
                            break;
                        }
                    }

                    if (gate) {
                        s.setYearLevel(yearLevel);
                        s.setBlock(block);
                        s.setGroup(group);
                        s.setAcademicYear(academicYear);

                        UI.print("Section successfully updated");
                        submit = true;
                    } else {
                        UI.print("Section already exists for this academic year");
                    }
                    break;

                case "X":
                    submit = true;
                    break;
            }
        }
    }

    public static void deleteSection() {
        String choice, input;

        UI.print("DELETE EXISTING SECTION");

        do {
            System.out.print("  Enter Section ID > ");
            input = in.nextLine().trim();

            if (!Section.existSectionID(input))
                UI.print("Section does not exist");
        } while (!Section.existSectionID(input));

        Section s = Section.findSection(input);

        if (s == null) {
            UI.print("Section not found");
            return;
        }

        sectionManage("DELETE",
                Section.getSection(s),
                Section.getYearLevel(s),
                Section.getBlock(s),
                Section.getGroup(s),
                Section.getAcademicYear(s));

        do {
            System.out.print("  Confirm Section Deletion? (Y/N) > ");
            choice = in.nextLine().trim().toUpperCase();

            if (!choice.matches("[YN]"))
                UI.print("Invalid Input");
        } while (!choice.matches("[YN]"));

        if (choice.equals("Y")) {
            Section.sections.remove(s);
            UI.print("Section successfully deleted");
        } else {
            UI.print("Section deletion cancelled");
        }
    }

    private static String getYearSection(int yearLevel, char block, int group) {
        if (group != 0)
            return yearLevel + "" + block + "-G" + group;
        return yearLevel + "" + block;
    }

    private static void sectionManage(String text, String sectionID,
                                      int yearLevel, char block, int group, String academicYear) {

        UI.header(text.toUpperCase() + ": SECTION INFORMATION");
        System.out.printf("│%-" + innerWidth + "s│\n",
                String.format(" [1] SECTION ID     : %s", sectionID));
        System.out.printf("│%-" + innerWidth + "s│\n",
                String.format(" [2] YEAR LEVEL     : %s", yearLevel));
        System.out.printf("│%-" + innerWidth + "s│\n",
                String.format(" [3] BLOCK          : %s", block));
        System.out.printf("│%-" + innerWidth + "s│\n",
                String.format(" [4] GROUP          : %s",
                        group == 0 ? "N/A" : "G" + group));
        System.out.printf("│%-" + innerWidth + "s│\n",
                String.format(" [5] ACADEMIC YEAR  : %s", academicYear));
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
        System.out.printf("│%-" + innerWidth + "s│%n",
                String.format(" [1]  USER ID          : %s", userID));
        System.out.printf("│%-" + innerWidth + "s│%n",
                String.format(" [2]  LAST NAME        : %s", lastName));
        System.out.printf("│%-" + innerWidth + "s│%n",
                String.format(" [3]  FIRST NAME       : %s", firstName));
        System.out.printf("│%-" + innerWidth + "s│%n",
                String.format(" [4]  MIDDLE INITIAL   : %s", middleInitial));
        System.out.printf("│%-" + innerWidth + "s│%n",
                String.format(" [5]  EMAIL ADDRESS    : %s", emailAddress));
        System.out.printf("│%-" + innerWidth + "s│%n",
                String.format(" [6]  PASSWORD         : %s", password));
        System.out.printf("│%-" + innerWidth + "s│%n",
                String.format(" [7]  ADDRESS          : %s", address));
        System.out.printf("│%-" + innerWidth + "s│%n",
                String.format(" [8]  POSTAL CODE      : %s", postalCode));
        System.out.printf("│%-" + innerWidth + "s│%n",
                String.format(" [9]  BIRTHDATE        : %s", birthDate));
        System.out.printf("│%-" + innerWidth + "s│%n",
                String.format(" [10] SEX              : %s", sex));

        if (u instanceof Student) {
            System.out.printf("│%-" + innerWidth + "s│%n",
                    String.format(" [11] ENROLLMENT STATUS: %s", input1));
            System.out.printf("│%-" + innerWidth + "s│%n",
                    String.format(" [12] PROGRAM CODE     : %s", input2));
        } else if (u instanceof Faculty) {
            System.out.printf("│%-" + innerWidth + "s│%n",
                    String.format(" [11] COLLEGE CODE     : %s", input2));
        } else if (u instanceof DepartmentHead) {
            System.out.printf("│%-" + innerWidth + "s│%n",
                    String.format(" [11] COLLEGE CODE     : %s", input2));
        }

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
        System.out.printf("│%-" + innerWidth + "s│%n",
                String.format(" [1]  USER ID          : %s", userID));
        System.out.printf("│%-" + innerWidth + "s│%n",
                String.format(" [2]  LAST NAME        : %s", lastName));
        System.out.printf("│%-" + innerWidth + "s│%n",
                String.format(" [3]  FIRST NAME       : %s", firstName));
        System.out.printf("│%-" + innerWidth + "s│%n",
                String.format(" [4]  MIDDLE INITIAL   : %s", middleInitial));
        System.out.printf("│%-" + innerWidth + "s│%n",
                String.format(" [5]  EMAIL ADDRESS    : %s", emailAddress));
        System.out.printf("│%-" + innerWidth + "s│%n",
                String.format(" [6]  PASSWORD         : %s", password));
        System.out.printf("│%-" + innerWidth + "s│%n",
                String.format(" [7]  ADDRESS          : %s", address));
        System.out.printf("│%-" + innerWidth + "s│%n",
                String.format(" [8]  POSTAL CODE      : %s", postalCode));
        System.out.printf("│%-" + innerWidth + "s│%n",
                String.format(" [9]  BIRTHDATE        : %s", birthDate));
        System.out.printf("│%-" + innerWidth + "s│%n",
                String.format(" [10] SEX              : %s", sex));
        System.out.printf("│%-" + innerWidth + "s│%n",
                String.format(" [11] COLLEGE CODE     : %s", collegeCode));

        UI.liner("");
        UI.option();
        UI.footer();
    }

    private static boolean isValidBirthDate(String birthDate) {
        try {
            java.time.format.DateTimeFormatter formatter =
                    java.time.format.DateTimeFormatter.ofPattern("MM/dd/uuuu")
                            .withResolverStyle(
                                    java.time.format.ResolverStyle.STRICT);

            java.time.LocalDate date =
                    java.time.LocalDate.parse(birthDate, formatter);

            return !date.isAfter(java.time.LocalDate.now());
        } catch (java.time.DateTimeException e) {
            return false;
        }
    }


    public static void manageSchedules() {
        int choice = -1;
        String input;

        do {
            UI.manageUI("Schedule");
            System.out.print("  Select > ");

            input = in.nextLine().trim();

            try {
                choice = Integer.parseInt(input);

                if (choice < 0 || choice > 4) {
                    UI.print("Invalid Input");
                    choice = -1;
                }
            } catch (NumberFormatException e) {
                UI.print("Invalid Input");
                choice = -1;
            }
        } while (choice == -1);

        switch (choice) {
            case 1:
                createSchedule();
                break;
            case 2:
                editSchedule();
                break;
            case 3:
                deleteSchedule();
                break;
            case 4:
                Schedule.displaySchedules();
                break;
            case 0:
                return;
        }
    }


    public static void createSchedule() {
        String offeringID = "", day = "", startTime = "", endTime = "";
        String effectiveFrom = "", effectiveTo = "", roomCode = "";
        String choice;
        boolean submit = false;

        while (!submit) {
            scheduleManage("EDIT", String.format("SCH%04d", (schedules.size() + 1)), offeringID, day,
                    startTime, endTime, effectiveFrom, effectiveTo, roomCode);

            do {
                System.out.print("  Select > ");
                choice = in.nextLine().trim().toUpperCase();

                if (!choice.matches("[1-7X/]"))
                    UI.print("Invalid Choice");
            } while (!choice.matches("[1-7X/]"));

            switch (choice) {
                case "1":
                    CourseOffering.displayOfferings();
                    System.out.print("   Enter Offering ID > ");
                    offeringID = in.nextLine().trim();
                    if (!CourseOffering.existOffering(offeringID)) {
                        UI.print("Course offering does not exist");
                        offeringID = "";
                    }
                    break;

                case "2":
                    System.out.print("   Enter Day > ");
                    day = in.nextLine().trim();
                    break;

                case "3":
                    System.out.print("   Enter Start Time (e.g. 8:30 AM) > ");
                    startTime = in.nextLine().trim();
                    break;

                case "4":
                    System.out.print("   Enter End Time (e.g. 10:00 AM) > ");
                    endTime = in.nextLine().trim();
                    break;

                case "5":
                    System.out.print("   Enter Effective From (MM/dd/yyyy) > ");
                    effectiveFrom = in.nextLine().trim();
                    break;

                case "6":
                    System.out.print("   Enter Effective To (MM/dd/yyyy) > ");
                    effectiveTo = in.nextLine().trim();
                    break;

                case "7":
                    System.out.print("   Enter Room Code > ");
                    roomCode = in.nextLine().trim();

                    if (Room.findRoom(roomCode) == null) {
                        UI.print("Room does not exist");
                        roomCode = "";
                    }
                    break;

                case "/":
                    if (offeringID.isEmpty() || day.isEmpty()
                            || startTime.isEmpty() || endTime.isEmpty()
                            || effectiveFrom.isEmpty() || effectiveTo.isEmpty()
                            || roomCode.isEmpty()) {
                        UI.print("Details are incomplete");
                        break;
                    }

                    try {
                        Schedule s = new Schedule(offeringID, day, startTime, endTime,
                                effectiveFrom, effectiveTo, roomCode);

                        schedules.add(s);
                        UI.print("Schedule successfully created");
                        Schedule.displaySchedule(s.getScheduleID());
                        submit = true;

                    } catch (IllegalArgumentException e) {
                        UI.print(e.getMessage());
                    }
                    break;

                case "X":
                    submit = true;
                    break;
            }
        }
    }


    private static void editSchedule() {
        String input, choice;
        boolean submit = false;

        UI.print("EDIT EXISTING SCHEDULE");

        Schedule s;

        do {
            System.out.print("  Enter Schedule ID > ");
            input = in.nextLine().trim();

            s = Schedule.findSchedule(input);

            if (s == null)
                UI.print("Schedule does not exist");
        } while (s == null);

        String offeringID = s.getOfferingID();
        String day = s.getDay();
        String startTime = s.getStartTime();
        String endTime = s.getEndTime();
        String effectiveFrom = s.getEffectiveFrom();
        String effectiveTo = s.getEffectiveTo();
        String roomCode = s.getRoomCode();

        while (!submit) {
            scheduleManage("EDIT", s.getScheduleID(), offeringID, day,
                    startTime, endTime, effectiveFrom, effectiveTo, roomCode);

            do {
                System.out.print("  Select > ");
                choice = in.nextLine().trim().toUpperCase();

                if (!choice.matches("[1-7X/]"))
                    UI.print("Invalid Choice");
            } while (!choice.matches("[1-7X/]"));

            switch (choice) {
                case "1":
                    CourseOffering.displayOfferings();
                    System.out.print("   Enter Offering ID > ");
                    input = in.nextLine().trim();

                    if (CourseOffering.existOffering(input))
                        offeringID = input;
                    else
                        UI.print("Course offering does not exist");
                    break;

                case "2":
                    System.out.print("   Enter Day > ");
                    day = in.nextLine().trim();
                    break;

                case "3":
                    System.out.print("   Enter Start Time > ");
                    startTime = in.nextLine().trim();
                    break;

                case "4":
                    System.out.print("   Enter End Time > ");
                    endTime = in.nextLine().trim();
                    break;

                case "5":
                    System.out.print("   Enter Effective From (MM/dd/yyyy) > ");
                    effectiveFrom = in.nextLine().trim();
                    break;

                case "6":
                    System.out.print("   Enter Effective To (MM/dd/yyyy) > ");
                    effectiveTo = in.nextLine().trim();
                    break;

                case "7":
                    System.out.print("   Enter Room Code > ");
                    input = in.nextLine().trim();

                    if (Room.findRoom(input) != null)
                        roomCode = input;
                    else
                        UI.print("Room does not exist");
                    break;

                case "/":
                    if (s.updateSchedule(offeringID, day, startTime, endTime,
                            effectiveFrom, effectiveTo, roomCode)) {
                        UI.print("Schedule successfully edited");
                        Schedule.displaySchedule(s.getScheduleID());
                        submit = true;
                    }
                    break;

                case "X":
                    submit = true;
                    break;
            }
        }
    }


    private static void deleteSchedule() {
        String input, choice;

        UI.print("DELETE EXISTING SCHEDULE");

        Schedule s;

        do {
            System.out.print("  Enter Schedule ID > ");
            input = in.nextLine().trim();

            s = Schedule.findSchedule(input);

            if (s == null)
                UI.print("Schedule does not exist");
        } while (s == null);

        scheduleManage("DELETE", s.getScheduleID(), s.getOfferingID(),
                s.getDay(), s.getStartTime(), s.getEndTime(),
                s.getEffectiveFrom(), s.getEffectiveTo(), s.getRoomCode());

        do {
            System.out.print("  Confirm Schedule Deletion? (Y/N) > ");
            choice = in.nextLine().trim().toUpperCase();

            if (!choice.matches("[YN]"))
                UI.print("Invalid Input");
        } while (!choice.matches("[YN]"));

        if (choice.equals("Y")) {
            schedules.remove(s);
            UI.print("Schedule successfully deleted");
        }
    }


    private static void scheduleManage(String text, String scheduleID,
                                       String offeringID, String day, String startTime, String endTime,
                                       String effectiveFrom, String effectiveTo, String roomCode) {

        UI.header(text.toUpperCase() + ": SCHEDULE INFORMATION");
        UI.liner(" [1] OFFERING ID        : " + offeringID);
        UI.liner(" [2] DAY                : " + day);
        UI.liner(" [3] START TIME         : " + startTime);
        UI.liner(" [4] END TIME           : " + endTime);
        UI.liner(" [5] EFFECTIVE FROM     : " + effectiveFrom);
        UI.liner(" [6] EFFECTIVE TO       : " + effectiveTo);
        UI.liner(" [7] ROOM CODE          : " + roomCode);
        UI.liner("");
        UI.option();
        UI.footer();
    }


    public static void managePrograms() {
        int choice = -1;
        String input;

        do {
            UI.manageUI("Programs");
            System.out.print("  Select > ");

            input = in.nextLine().trim();

            try {
                choice = Integer.parseInt(input);

                if (choice < 0 || choice > 4) {
                    UI.print("Invalid Input");
                    choice = -1;
                }
            } catch (NumberFormatException e) {
                UI.print("Invalid Input");
                choice = -1;
            }
        } while (choice == -1);

        switch (choice) {
            case 1:
                createProgram();
                break;
            case 2:
                editProgram();
                break;
            case 3:
                deleteProgram();
                break;
            case 4:
                Program.displayPrograms();
                break;
            case 0:
                return;
        }
    }

    public static void createProgram() {
        String programCode = "", programDescription = "", collegeCode = "";
        String choice;
        boolean submit = false;

        while (!submit) {
            programManage("CREATE", programCode, programDescription, collegeCode);

            do {
                System.out.print("  Select > ");
                choice = in.nextLine().trim().toUpperCase();

                if (!choice.matches("[1-3X/]"))
                    UI.print("Invalid Choice");
            } while (!choice.matches("[1-3X/]"));

            switch (choice) {
                case "1":
                    do {
                        System.out.print("   Enter Program Code > ");
                        programCode = in.nextLine().trim();

                        if (programCode.isEmpty())
                            UI.print("Program Code is required");
                        else if (Program.existProgramCode(programCode))
                            UI.print("Program Code already exists");

                    } while (programCode.isEmpty()
                            || Program.existProgramCode(programCode));
                    break;

                case "2":
                    do {
                        System.out.print("   Enter Program Description > ");
                        programDescription = in.nextLine().trim();

                        if (programDescription.isEmpty())
                            UI.print("Program Description is required");
                        else if (programDescriptionExists(programDescription, null))
                            UI.print("Program Description already exists");

                    } while (programDescription.isEmpty()
                            || programDescriptionExists(programDescription, null));
                    break;

                case "3":
                    do {
                        System.out.print("   Enter College Code > ");
                        collegeCode = in.nextLine().trim();

                        if (collegeCode.isEmpty())
                            UI.print("College Code is required");
                        else if (College.findCollege(collegeCode) != null)
                            UI.print("College Code does not exist");

                    } while (collegeCode.isEmpty()
                            || College.findCollege(collegeCode) != null);
                    break;

                case "/":
                    if (programCode.isEmpty() || programDescription.isEmpty()
                            || collegeCode.isEmpty()) {
                        UI.print("Details are incomplete");
                        break;
                    }

                    try {
                        Program p = new Program(
                                programCode, programDescription, collegeCode);

                        Program.programs.add(p);
                        UI.print("Program successfully created");
                        Program.displayProgram(p.getProgramID());
                        submit = true;

                    } catch (IllegalArgumentException e) {
                        UI.print(e.getMessage());
                    }
                    break;

                case "X":
                    submit = true;
                    break;
            }
        }
    }

    private static void editProgram() {
        String input, choice;
        boolean submit = false;

        UI.print("EDIT EXISTING PROGRAM");

        do {
            System.out.print("  Enter Program Code > ");
            input = in.nextLine().trim();

            if (!Program.existProgramCode(input))
                UI.print("Program does not exist");

        } while (!Program.existProgramCode(input));

        Program p = Program.findProgram(input);

        if (p == null) {
            UI.print("Program not found");
            return;
        }

        String programCode = p.getProgramID();
        String programDescription = p.getProgramDescription();
        String collegeCode = p.getCollegeCode();

        while (!submit) {
            programManage("EDIT", programCode, programDescription, collegeCode);

            do {
                System.out.print("  Select > ");
                choice = in.nextLine().trim().toUpperCase();

                if (!choice.matches("[1-3X/]"))
                    UI.print("Invalid Choice");

            } while (!choice.matches("[1-3X/]"));

            switch (choice) {
                case "1":
                    do {
                        System.out.print("   Enter Program Code > ");
                        input = in.nextLine().trim();

                        if (input.isEmpty())
                            UI.print("Program Code is required");
                        else if (Program.existProgramCode(input)
                                && !input.equalsIgnoreCase(p.getProgramID()))
                            UI.print("Program Code already exists");

                    } while (input.isEmpty()
                            || (Program.existProgramCode(input)
                            && !input.equalsIgnoreCase(p.getProgramID())));

                    programCode = input;
                    break;

                case "2":
                    do {
                        System.out.print("   Enter Program Description > ");
                        input = in.nextLine().trim();

                        if (input.isEmpty())
                            UI.print("Program Description is required");
                        else if (programDescriptionExists(input, p))
                            UI.print("Program Description already exists");

                    } while (input.isEmpty()
                            || programDescriptionExists(input, p));

                    programDescription = input;
                    break;

                case "3":
                    do {
                        System.out.print("   Enter College Code > ");
                        input = in.nextLine().trim();

                        if (input.isEmpty())
                            UI.print("College Code is required");
                        else if (College.findCollege(collegeCode) != null)
                            UI.print("College Code does not exist");

                    } while (input.isEmpty()
                            || College.findCollege(collegeCode) != null);

                    collegeCode = input;
                    break;

                case "/":
                    if (programCode.isEmpty() || programDescription.isEmpty()
                            || collegeCode.isEmpty()) {
                        UI.print("Details are incomplete");
                        break;
                    }

                    if (Program.existProgramCode(programCode)
                            && !programCode.equalsIgnoreCase(p.getProgramID())) {
                        UI.print("Program Code already exists");
                        break;
                    }

                    if (programDescriptionExists(programDescription, p)) {
                        UI.print("Program Description already exists");
                        break;
                    }

                    if (College.findCollege(collegeCode) != null) {
                        UI.print("College Code does not exist");
                        break;
                    }

                    try {
                        Program updated = new Program(
                                programCode, programDescription, collegeCode);

                        int index = Program.programs.indexOf(p);

                        if (index != -1) {
                            Program.programs.set(index, updated);
                            UI.print("Program successfully edited");
                            Program.displayProgram(updated.getProgramID());
                            submit = true;
                        } else {
                            UI.print("Program could not be updated");
                        }

                    } catch (IllegalArgumentException e) {
                        UI.print(e.getMessage());
                    }
                    break;

                case "X":
                    submit = true;
                    break;
            }
        }
    }

    private static boolean programDescriptionExists(
            String description, Program excludedProgram) {

        if (description == null || description.trim().isEmpty())
            return false;

        for (Program p : Program.programs) {
            if (p != excludedProgram
                    && description.trim().equalsIgnoreCase(
                    p.getProgramDescription())) {
                return true;
            }
        }

        return false;
    }

    private static void deleteProgram() {
        String input, choice;

        UI.print("DELETE EXISTING PROGRAM");

        do {
            System.out.print("  Enter Program Code > ");
            input = in.nextLine().trim();

            if (!Program.existProgramCode(input))
                UI.print("Program does not exist");

        } while (!Program.existProgramCode(input));

        Program p = Program.findProgram(input);

        if (p == null) {
            UI.print("Program not found");
            return;
        }

        programManage("DELETE", p.getProgramID(),
                p.getProgramDescription(), p.getCollegeCode());

        do {
            System.out.print("  Confirm Program Deletion? (Y/N) > ");
            choice = in.nextLine().trim().toUpperCase();

            if (!choice.matches("[YN]"))
                UI.print("Invalid Input");

        } while (!choice.matches("[YN]"));

        if (choice.equals("Y")) {
            Program.programs.remove(p);
            UI.print("Program successfully deleted");
        }
    }

    private static void programManage(String text, String programCode,
                                      String programDescription, String collegeCode) {

        UI.header(text.toUpperCase() + ": PROGRAM INFORMATION");
        UI.liner(" [1] PROGRAM CODE        : " + programCode);
        UI.liner(" [2] PROGRAM DESCRIPTION : " + programDescription);
        UI.liner(" [3] COLLEGE CODE        : " + collegeCode);
        UI.liner("");
        UI.option();
        UI.footer();
    }


    // ------------------------------------------------------------//
    static void backUpData(){
        //BackUp.backup;
    }
}