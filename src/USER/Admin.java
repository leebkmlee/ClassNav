package USER;
import DATA.Building;
import DATA.Course;
import DATA.Program;
import DATA.Room;
import UI.*;
import java.util.Scanner;
import DATA.Schedule;
import UI.UI;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import static DATA.Program.*;
import static DATA.Schedule.*;
import static USER.User.in;

    public class Admin extends User {
        Scanner sc = new Scanner(System.in);


        private String role;

        public Admin(String userID, String lastName, String firstName, String middleInitial, String emailAddress,
                     String password, String address, int postalCode, String birthDate, char sex, String role) {
            super(userID, lastName, firstName, middleInitial, emailAddress, password, address, postalCode, birthDate, sex);
            this.role = role;
        }

        static Scanner in = new Scanner(System.in);

        public static void manageRooms() {
            int choice = -1;
            String input = "";
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
            String roomCode = "";
            String roomDescription = "";
            String buildingCode = "";
            String choice = "";
            int floorNumber = -1;
            boolean submit = false;

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
                        } while (Room.existRoomCode(roomCode));
                        break;
                    case "2":
                        do {
                            System.out.print("   Enter Room Description > ");
                            roomDescription = in.nextLine();
                            if (Room.existRoomDesc(roomDescription)) UI.print("Room Description already exists");
                        } while (Room.existRoomDesc(roomDescription));
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
                            if (!Building.verifyBuilding(buildingCode)) UI.print("Building Code does not exist");
                        } while (!Building.verifyBuilding(buildingCode));
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
            String roomCode = "";
            String roomDescription = "";
            String buildingCode = "";
            int floorNumber = -1;
            boolean submit = false;
            String input;
            String choice;

            UI.print("EDIT EXISTING ROOM");
            do {
                System.out.print("  Enter Room Code > ");
                input = in.nextLine();
                if (!Room.existRoomCode(input)) UI.print("Room does not exist");
            } while (!Room.existRoomCode(input));

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

                switch (choice) {
                    case "1":
                        do {
                            System.out.print("   Enter Room Code > ");
                            roomCode = in.nextLine();
                            if (Room.existRoomCode(roomCode) && !roomCode.equalsIgnoreCase(r.getRoomCode()))
                                UI.print("Room Code already exists");
                        } while (Room.existRoomCode(roomCode) && !roomCode.equalsIgnoreCase(r.getRoomCode()));
                        break;
                    case "2":
                        do {
                            System.out.print("   Enter Room Description > ");
                            roomDescription = in.nextLine();
                            if (Room.existRoomDesc(roomDescription) && !roomDescription.equalsIgnoreCase(Room.getRoomDescription(r)))
                                UI.print("Room Description already exists");
                        } while (Room.existRoomDesc(roomDescription) && !roomDescription.equalsIgnoreCase(Room.getRoomDescription(r)));
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
                            if (!Building.verifyBuilding(buildingCode)) UI.print("Building Code does not exist");
                        } while (!Building.verifyBuilding(buildingCode));
                        break;
                    case "/":
                        UI.print("Room successfully edited");
                        r.setRoomCode(roomCode);
                        r.setRoomDescription(roomDescription);
                        r.setFloorNumber(floorNumber);
                        r.setBuildingCode(buildingCode);
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
            String input;
            String choice;
            String roomCode;
            String roomDescription;
            int floorNumber;
            String buildingCode;

            UI.print("DELETE EXISTING ROOM");

            do {
                System.out.print("  Enter Room Code > ");
                input = in.nextLine();
                if (!Room.existRoomCode(input)) UI.print("Room does not exist");
            } while (!Room.existRoomCode(input));

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
            } while (!choice.matches("[YN]"));

            if (choice.equals("Y")) {
                Room.rooms.remove(r);
                UI.print("Room successfully deleted");
            } else manageRooms();
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
            int choice = -1;
            String input = "";
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
            String courseCode = "";
            String courseDescription = "";
            String programCode = "";
            String choice = "";
            int creditUnits = -1;
            boolean submit = false;

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
                        } while (Course.existCourseCode(courseCode));
                        break;
                    case "2":
                        do {
                            System.out.print("   Enter Course Description > ");
                            courseDescription = in.nextLine();
                            if (Course.existCourseDesc(courseDescription))
                                UI.print("Course Description already exists");
                        } while (Course.existCourseDesc(courseDescription));
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
                            if (!Program.verifyProgramCode(programCode)) UI.print("Program Code does not exist");
                        } while (!Program.verifyProgramCode(programCode));
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
            String courseCode = "";
            String courseDescription = "";
            String programCode = "";
            String choice = "";
            int creditUnits = -1;
            boolean submit = false;
            String input;

            UI.print("EDIT EXISTING COURSE");
            do {
                System.out.print("  Enter Course Code > ");
                input = in.nextLine();
                if (!Course.existCourseCode(input)) UI.print("Course does not exist");
            } while (!Course.existCourseCode(input));

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

                switch (choice) {
                    case "1":
                        do {
                            System.out.print("   Enter Course Code > ");
                            courseCode = in.nextLine();
                            if (Course.existCourseCode(courseCode)) UI.print("Course Code already exists");
                        } while (Course.existCourseCode(courseCode));
                        break;
                    case "2":
                        do {
                            System.out.print("   Enter Course Description > ");
                            courseDescription = in.nextLine();
                            if (Course.existCourseDesc(courseDescription))
                                UI.print("Course Description already exists");
                        } while (Course.existCourseDesc(courseDescription));
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
                            if (!Program.verifyProgramCode(programCode)) UI.print("Program Code does not exist");
                        } while (!Program.verifyProgramCode(programCode));
                        break;
                    case "/":
                        UI.print("Room successfully edited");
                        c.setCourseCode(courseCode);
                        c.setCourseDescription(courseDescription);
                        c.setCreditUnits(creditUnits);
                        c.setProgramCode(programCode);
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
            String input;
            String choice;

            UI.print("DELETE EXISTING COURSE");

            do {
                System.out.print("  Enter Course Code > ");
                input = in.nextLine();
                if (!Course.existCourseCode(input)) UI.print("Course does not exist");
            } while (!Course.existCourseCode(input));

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
            } while (!choice.matches("[YN]"));

            if (choice.equals("Y")) {
                Course.courses.remove(c);
                UI.print("Course successfully deleted");
            } else manageCourses();
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


        void manageSchedule() {
            final int innerWidth = 70;
            UI.header("MANAGE SCHEDULE");
            System.out.printf("│%-" + innerWidth + "s│\n", " [1] ADD SCHEDULE");
            System.out.printf("│%-" + innerWidth + "s│\n", " [2] UPDATE SCHEDULE");
            System.out.printf("│%-" + innerWidth + "s│\n", " [3] DELETE SCHEDULE");
            UI.footer();
            System.out.println(" Select > ");

            int choice = in.nextInt();
            in.nextLine();
            System.out.println("└" + "─".repeat(innerWidth) + "┘");

            switch (choice) {
                case 1:
                    addSchedule();
                    break;
                case 2:
                    updateSchedule();
                    break;
                case 3:
                    deleteSchedule();
                    break;
                default:
                    System.out.println("INVALID CHOICE");
            }
        }

        void addSchedule() {
            UI.header("ADD A SCHEDULE");

            boolean exists;
            String scheduleID, sectionID, day, startTime, endTime, roomCode;
            do {
                scheduleID = UI.inserted(in, "ENTER SCHEDULE ID: ");
                exists = false;
                for (Schedule schedule : schedules) {
                    if (schedule.getScheduleID().equalsIgnoreCase(scheduleID)) {
                        exists = true;
                        System.out.printf("│%-" + UI.INNERWIDTH + "s│\n", "  [!] SCHEDULE ALREADY EXISTS! ENTER AGAIN.");
                        break;
                    }
                }
            } while (exists);

            do {
                sectionID = UI.inserted(in, "ENTER SECTION ID: ");
                exists = false;
                if (sectionID.isEmpty()) {
                    exists = true;
                    System.out.printf("│%-" + UI.INNERWIDTH + "s│\n", "  [!] SECTION ID MUST NOT BE EMPTY!");
                }
            } while (exists);
            do {
                day = UI.inserted(in, "ENTER DAY (M, T, W, TH, F, S): ");
                if (!day.matches("(?i)M|T|W|TH|F|S")) {
                    System.out.printf("│%-" + UI.INNERWIDTH + "s│\n", "  [!] INVALID DAY! USE (M, T, W, TH, F, S).");
                    exists = true;
                } else {
                    exists = false;
                }
            } while (exists);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mma", Locale.ENGLISH);

            do {
                System.out.printf("│%-" + UI.INNERWIDTH + "s│\n", "  FORMAT OF TIME SHOULD BE (0:00AM) IN 12-HOUR SYSTEM");
                startTime = UI.inserted(in, "ENTER START TIME: ");
                endTime = UI.inserted(in, "ENTER END TIME: ");
                exists = false;

                try {
                    LocalTime newStart = LocalTime.parse(startTime.toUpperCase(), formatter);
                    LocalTime newEnd = LocalTime.parse(endTime.toUpperCase(), formatter);

                    if (!newStart.isBefore(newEnd)) {
                        System.out.printf("│%-" + UI.INNERWIDTH + "s│\n", "  [!] START TIME MUST BE BEFORE END TIME!");
                        exists = true;
                        continue;
                    }

                    for (Schedule schedule : schedules) {
                        if (getDay(schedule).equalsIgnoreCase(day) && getSectionID(schedule).equalsIgnoreCase(sectionID)) {
                            LocalTime existingStart = LocalTime.parse(getStartTime(schedule).toUpperCase(), formatter);
                            LocalTime existingEnd = LocalTime.parse(getEndTime(schedule).toUpperCase(), formatter);

                            if (newStart.isBefore(existingEnd) && newEnd.isAfter(existingStart)) {
                                exists = true;
                                System.out.printf("│%-" + UI.INNERWIDTH + "s│\n", "  [!] SCHEDULE OVERLAPS WITH AN EXISTING CLASS!");
                                break;
                            }
                        }
                    }
                } catch (DateTimeParseException e) {
                    System.out.printf("│%-" + UI.INNERWIDTH + "s│\n", "  [!] INVALID TIME FORMAT! USE 5:00PM.");
                    exists = true;
                }
            } while (exists);
            do {
                roomCode = UI.inserted(in, "ENTER ROOM CODE: ");
                exists = false;

                for (Schedule schedule : schedules) {
                    if (getRoomCode(schedule).equalsIgnoreCase(roomCode) &&
                            getDay(schedule).equalsIgnoreCase(day) &&
                            getStartTime(schedule).equalsIgnoreCase(startTime) &&
                            getEndTime(schedule).equalsIgnoreCase(endTime)) {

                        exists = true;
                        System.out.printf("│%-" + UI.INNERWIDTH + "s│\n", "  [!] OVERLAPPING SCHEDULE IN ROOM!");
                        break;
                    }
                }
            } while (exists);

            schedules.add(new Schedule(scheduleID, day, startTime, endTime, sectionID, roomCode));
            UI.separator();
            System.out.printf("│%-" + UI.INNERWIDTH + "s│\n", "  SCHEDULE ADDED SUCCESSFULLY!");
            UI.footer();
        }

        void updateSchedule() {
            UI.header("UPDATE SCHEDULE");

            String targetID = UI.inserted(in, "ENTER SCHEDULE ID TO UPDATE: ");

            while (targetID.isEmpty()) {
                targetID = UI.inserted(in, "ENTER SCHEDULE ID TO UPDATE: ");
            }

            int targetIndex = -1;

            // Find the schedule to update
            for (int i = 0; i < schedules.size(); i++) {
                if (schedules.get(i).getScheduleID().equalsIgnoreCase(targetID)) {
                    targetIndex = i;
                    break;
                }
            }

            if (targetIndex == -1) {
                UI.separator();
                System.out.printf("│%-" + UI.INNERWIDTH + "s│\n", "  [!] SCHEDULE ID NOT FOUND!");
                UI.footer();
                return;
            }

            boolean exists;
            String newDay, newStartTime, newEndTime, newSection, newRoom;

            do {
                newSection = UI.inserted(in, "ENTER NEW SECTION ID: ");
                exists = false;
                if (newSection.isEmpty()) {
                    exists = true;
                    System.out.printf("│%-" + UI.INNERWIDTH + "s│\n", "  [!] SECTION ID MUST NOT BE EMPTY! ENTER AGAIN.");
                }
            } while (exists);
            do {
                newDay = UI.inserted(in, "ENTER NEW DAY (M, T, W, TH, F, S): ");
                if (!newDay.equalsIgnoreCase("M") &&
                        !newDay.equalsIgnoreCase("T") &&
                        !newDay.equalsIgnoreCase("W") &&
                        !newDay.equalsIgnoreCase("TH") &&
                        !newDay.equalsIgnoreCase("F") &&
                        !newDay.equalsIgnoreCase("S")) {

                    System.out.printf("│%-" + UI.INNERWIDTH + "s│\n", "  [!] INVALID DAY! PLEASE ENTER (M, T, W, TH, F, S).");
                    exists = true;
                } else {
                    exists = false;
                }
            } while (exists);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mma", Locale.ENGLISH);

            do {
                System.out.printf("│%-" + UI.INNERWIDTH + "s│\n", "  FORMAT OF TIME SHOULD BE (0:00AM) IN 12-HOUR SYSTEM");
                newStartTime = UI.inserted(in, "ENTER NEW START TIME: ");
                newEndTime = UI.inserted(in, "ENTER NEW END TIME: ");

                exists = false;

                try {
                    LocalTime newStart = LocalTime.parse(newStartTime.toUpperCase(), formatter);
                    LocalTime newEnd = LocalTime.parse(newEndTime.toUpperCase(), formatter);

                    if (!newStart.isBefore(newEnd)) {
                        System.out.printf("│%-" + UI.INNERWIDTH + "s│\n", "  [!] START TIME MUST BE BEFORE END TIME!");
                        exists = true;
                        continue;
                    }

                    for (int i = 0; i < schedules.size(); i++) {

                        if (i == targetIndex) continue;

                        Schedule schedule = schedules.get(i);
                        if (getDay(schedule).equalsIgnoreCase(newDay) && getSectionID(schedule).equalsIgnoreCase(newSection)) {
                            LocalTime existingStart = LocalTime.parse(getStartTime(schedule).toUpperCase(), formatter);
                            LocalTime existingEnd = LocalTime.parse(getEndTime(schedule).toUpperCase(), formatter);

                            if (newStart.isBefore(existingEnd) && newEnd.isAfter(existingStart)) {
                                exists = true;
                                System.out.printf("│%-" + UI.INNERWIDTH + "s│\n", "  [!] SCHEDULE OVERLAPS WITH AN EXISTING CLASS!");
                                break;
                            }
                        }
                    }
                } catch (DateTimeParseException e) {
                    System.out.printf("│%-" + UI.INNERWIDTH + "s│\n", "  [!] INVALID TIME FORMAT! USE FORMAT LIKE 5:00PM.");
                    exists = true;
                }

            } while (exists);
            do {
                newRoom = UI.inserted(in, "ENTER NEW ROOM CODE: ");
                exists = false;

                for (int i = 0; i < schedules.size(); i++) {

                    if (i == targetIndex) continue;

                    Schedule schedule = schedules.get(i);
                    if (getRoomCode(schedule).equalsIgnoreCase(newRoom) &&
                            getDay(schedule).equalsIgnoreCase(newDay) &&
                            getStartTime(schedule).equalsIgnoreCase(newStartTime) &&
                            getEndTime(schedule).equalsIgnoreCase(newEndTime)) {

                        exists = true;
                        System.out.printf("│%-" + UI.INNERWIDTH + "s│\n", "  [!] OVERLAPPING SCHEDULE IN THE ROOM! ENTER AGAIN.");
                        break;
                    }
                }
            } while (exists);

            Schedule updatedSchedule = new Schedule(targetID, newDay, newStartTime, newEndTime, newSection, newRoom);
            schedules.set(targetIndex, updatedSchedule);

            UI.separator();
            System.out.printf("│%-" + UI.INNERWIDTH + "s│\n", "  SCHEDULE UPDATED SUCCESSFULLY!");
            UI.footer();
        }

        void deleteSchedule() {
            UI.header("DELETE SCHEDULE");

            String targetID = UI.inserted(in, "ENTER SCHEDULE ID TO DELETE: ");

            Schedule scheduleToRemove = null;

            for (Schedule schedule : schedules) {
                if (schedule.getScheduleID().equalsIgnoreCase(targetID)) {
                    scheduleToRemove = schedule;
                    break;
                }
            }

            UI.separator();
            if (scheduleToRemove != null) {
                schedules.remove(scheduleToRemove);
                System.out.printf("│%-" + UI.INNERWIDTH + "s│\n", "  SCHEDULE DELETED SUCCESSFULLY!");
            } else {
                System.out.printf("│%-" + UI.INNERWIDTH + "s│\n", "  [!] SCHEDULE ID NOT FOUND.");
            }
            UI.footer();
        }

        void manageProgram() {
            final int innerWidth = 70;
            UI.header("MANAGE PROGRAM");
            System.out.printf("│%-" + innerWidth + "s│\n", " [1] ADD PROGRAM");
            System.out.printf("│%-" + innerWidth + "s│\n", " [2] UPDATE PROGRAM");
            System.out.printf("│%-" + innerWidth + "s│\n", " [3] DELETE PROGRAM");
            UI.footer();
            System.out.println(" Select > ");

            int choice = in.nextInt();
            in.nextLine();
            System.out.println("└" + "─".repeat(innerWidth) + "┘");

            switch (choice) {
                case 1:
                    addProgram();
                    break;
                case 2:
                    updateProgram();
                    break;
                case 3:
                    deleteProgram();
                    break;
                default:
                    System.out.println("INVALID CHOICE");
            }
        }

        void updateProgram() {
            UI.header("UPDATE PROGRAM");

            String targetID = UI.inserted(in, "ENTER PROGRAM ID TO UPDATE: ");

            for (int i = 0; i < programs.size(); i++) {
                Program oldProgram = programs.get(i);

                if (oldProgram.getProgramID().equalsIgnoreCase(targetID)) {

                    String newProgramDescription = UI.inserted(in, "ENTER NEW PROGRAM DESCRPTION: ");
                    String newCollegeCode = UI.inserted(in, "ENTER NEW COLLEGE CODE: ");
                    Program updateProgram = new Program(targetID, newProgramDescription, newCollegeCode);
                    programs.set(i, updateProgram);

                    UI.separator();
                    System.out.printf("│%-" + UI.INNERWIDTH + "s│\n", "  SCHEDULE UPDATED SUCCESSFULLY!");
                    UI.footer();
                    return;
                }
            }
        }


        void deleteProgram() {
            UI.header("DELETE PROGRAM");

            String targetID = UI.inserted(in, "ENTER PROGRAM CODE TO DELETE: ");

            Program programToRemove = null;

            for (Program program : programs) {
                if (program.getProgramID().equalsIgnoreCase(targetID)) {
                    programToRemove = program;
                    break;
                }
            }

            UI.separator();
            if (programToRemove != null) {
                programs.remove(programToRemove);
                System.out.printf("│%-" + UI.INNERWIDTH + "s│\n", "  PROGRAM DELETED SUCCESSFULLY!");
            } else {
                System.out.printf("│%-" + UI.INNERWIDTH + "s│\n", "  [!] PROGRAM ID NOT FOUND.");
            }
            UI.footer();
        }

        void addProgram() {
            UI.header("ADD A PROGRAM  ");

            boolean exists;
            String programCode, programDescription, collegeCode;
            do {
                programCode = UI.inserted(in, "ENTER PROGRAM CODE: ");
                exists = false;
                for (Program program : programs) {
                    if (program.getProgramID().equalsIgnoreCase(programCode)) {
                        exists = true;
                        System.out.printf("│%-" + UI.INNERWIDTH + "s│\n", "  [!] PROGRAM CODE ALREADY EXISTS! ENTER AGAIN.");
                        break;
                    }
                }
            } while (exists);
            do {
                programDescription = UI.inserted(in, "ENTER PROGRAM DESCRIPTION: ");
                exists = false;
                for (Program program : programs) {
                    if (getProgramDescription(program).equalsIgnoreCase(programDescription)) {
                        exists = true;
                        System.out.printf("│%-" + UI.INNERWIDTH + "s│\n", "  [!] PROGRAM DESCRIPTION ALREADY EXISTS! ENTER AGAIN.");
                        break;
                    }
                }
            } while (exists);
            do {
                collegeCode = UI.inserted(in, "ENTER COLLEGE CODE: ");
                exists = false;
                for (Program program : programs) {
                    if (getCollegeCode(program).equalsIgnoreCase(collegeCode)) {
                        exists = true;
                        System.out.printf("│%-" + UI.INNERWIDTH + "s│\n", "  [!] SCHEDULE ALREADY EXISTS! ENTER AGAIN.");
                        break;
                    }
                }
            } while (exists);
            programs.add(new Program(programCode, programDescription, collegeCode));
        }
    }




