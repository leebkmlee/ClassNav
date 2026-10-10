package USER;
import DATA.*;
import UI.*;

import static DATA.Room.convertToMinutes;

public class Student extends User{

    private String enrollmentStatus;
    private String programCode; // foreign key

    public Student(String userID, String lastName, String firstName, String middleInitial, String emailAddress,
                   String password, String address, int postalCode, String birthDate, char gender, String enrollmentStatus,
                   String programCode) {
        super(userID, lastName, firstName, middleInitial, emailAddress, password, address, postalCode, birthDate, gender);
        this.enrollmentStatus = enrollmentStatus;
        this.programCode = programCode;
    }

    public static void locateRoom(String roomCode) {
        Room r = Room.findRoom(roomCode);
        if (r == null) {
            UI.print("Room not found");
            return;
        }
        Building b = Building.findBuilding(Room.getBuildingCode(r));
        if (b == null) {
            UI.print("Building not found");
            return;
        }
        UI.header("ROOM INFORMATION");
        System.out.printf("│%-" + innerWidth + "s│\n", "  ROOM DETAILS");
        UI.separator();
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("  ROOM CODE         : %s", Room.getRoom(r)));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("  ROOM DESCRIPTION  : %s", Room.getRoomDescription(r)));
        UI.separator();
        System.out.printf("│%-" + innerWidth + "s│\n", "  ROOM LOCATION");
        UI.separator();
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("  BUILDING NAME     : %s", Building.getBuildingName(b)));
        int floor = Room.getFloorNumber(r);
        String floorText;
        if (floor == 1) floorText = "1st Floor";
        else if (floor == 2) floorText = "2nd Floor";
        else if (floor == 3) floorText = "3rd Floor";
        else floorText = floor + "th Floor";
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("  FLOOR NUMBER      : %s", floorText));
        UI.footer();
    }

    public static Student findStudent(String userID) {
        for (User u : users) if (userID.equals(u.getUserID()) && u.getClass() == Student.class) return (Student) u;
        return null;
    }

    public static String getProgramCode(Student s) {
        return s.programCode;
    }

    public String getEnrollmentStatus(){ return enrollmentStatus; }

    public String getProgramCode(){ return programCode; }

    public static void displaySchedule(String userID) {
        Student st = Student.findStudent(userID);
        if (st == null) {
            UI.print("Student not found");
            return;
        }
        UI.header("STUDENT SCHEDULE");
        System.out.printf("│%-" + innerWidth + "s│%n", String.format("  STUDENT ID : %s", userID));UI.separator();
        boolean found = false;
        for (Enrollment e : Enrollment.enrollments) {
            if (userID.equals(Enrollment.getStudentNumber(e))) {
                String sectionID = Enrollment.getSectionID(e);
                for (Schedule s : Schedule.schedules) {
                    if (sectionID.equals(Schedule.getSectionID(s))) {
                        found = true;
                        System.out.printf("│%-" + innerWidth + "s│%n", String.format("  SCHEDULE ID   : %s", Schedule.getSchedule(s)));
                        System.out.printf("│%-" + innerWidth + "s│%n", String.format("  DAY           : %s", Schedule.getDay(s)));
                        System.out.printf("│%-" + innerWidth + "s│%n", String.format("  TIME          : %s - %s", Schedule.getStartTime(s), Schedule.getEndTime(s)));
                        System.out.printf("│%-" + innerWidth + "s│%n", String.format("  SECTION ID    : %s", sectionID));
                        String roomCode = Schedule.getRoomCode(s);
                        System.out.printf("│%-" + innerWidth + "s│%n", String.format("  ROOM CODE     : %s", roomCode));
                        Room r = Room.findRoom(roomCode);
                        if (r != null) {
                            String buildingCode = Room.getBuildingCode(r);
                            Building b = Building.findBuilding(buildingCode);
                            if (b != null) {
                                System.out.printf("│%-" + innerWidth + "s│%n", String.format("  BUILDING NAME : %s", Building.getBuildingName(b)));
                            } else {
                                System.out.printf("│%-" + innerWidth + "s│%n", "  BUILDING NAME : Building not found");
                            }
                        } else {
                            System.out.printf("│%-" + innerWidth + "s│%n", "  BUILDING NAME : Room not found");
                        }
                        UI.separator();
                    }
                }
            }
        }
        if (!found) {
            System.out.printf("│%-" + innerWidth + "s│%n",
                    "  No schedules found.");
        }
        UI.footer();
    }

    public static void viewAvailableRooms( String day, String startTime, String endTime){
        int scheduledStart = convertToMinutes(startTime);
        int scheduledEnd = convertToMinutes(endTime);
        UI.header("AVAILABLE ROOMS");
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("  DAY          : %s", day));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("  START TIME   : %s", startTime));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("  END TIME     : %s", endTime));
        UI.separator();
        boolean found = false;

        for (Room r : Room.rooms) {
            String roomCode = Room.getRoom(r);
            boolean available = true;

            for (Schedule s : Schedule.schedules) {
                if (roomCode.equals(Schedule.getRoomCode(s)) && day.equalsIgnoreCase(Schedule.getDay(s))) {
                    int existingStart = convertToMinutes(Schedule.getStartTime(s));
                    int existingEnd = convertToMinutes(Schedule.getEndTime(s));
                    if (scheduledStart < existingEnd && scheduledEnd > existingStart) {
                        available = false;
                        break;
                    }
                }
            }
            if (available) {
                found = true;
                System.out.printf("│%-" + innerWidth + "s│%n", String.format("  ROOM CODE    : %s", roomCode));
            }
        }
        if (!found) {
            System.out.printf("│%-" + innerWidth + "s│%n", "  NO AVAILABLE ROOMS FOUND");
        }
        UI.footer();
    }

    public static void informFaculty(String studentID, String courseCode, String buildingName,
                                     String roomCode, String day, String startTime, String endTime) {
        Enrollment e = Enrollment.findEnrollmentByID(studentID, courseCode);
        if (e == null) {
            UI.print("Enrollment does not exist");
            return;
        }
        Section s = Section.findSection(Enrollment.getSectionID(e));
        if (s == null) {
            UI.print("Section not found");
            return;
        }
        UI.header("INFORM FACULTY");
        boolean available = Room.verifyAvailability(roomCode, day, startTime, endTime);
        String choice; String message;
        if (available) {
            System.out.printf("│%-" + innerWidth + "s│\n", "  Room is available");
            message = "Room is available";
        }
        else {
            System.out.printf("│%-" + innerWidth + "s│\n", "  Room is occupied");
            message = "Room is occupied";
        }
        System.out.printf("│%-" + innerWidth + "s│\n", "  Inform faculty? ");
        String options = "[/] Submit     [X] Return";
        System.out.println("│" + " ".repeat((innerWidth - options.length()) / 2) +
                options + " ".repeat(innerWidth - options.length() - (innerWidth - options.length()) / 2) + "│");
        System.out.println("└" + "─".repeat(innerWidth) + "┘");
        do {
            System.out.print("  Select > ");
            choice = in.nextLine().toUpperCase();
            if (!choice.equals("/") && !choice.equals("X")) UI.print("Invalid choice");
        } while(!choice.equals("/") && !choice.equals("X"));
        switch (choice) {
            case "/":
                Notification.notifications.add(new Notification(studentID, Section.getProfessorID(s), roomCode, buildingName,
                        day, startTime, endTime, message));
                UI.print("Successfully informed faculty");
                break;
            case "X":
                // balik menu
                break;
        }
    }

    public void setEnrollmentStatus(String enrollmentStatus) { this.enrollmentStatus = enrollmentStatus;
    }

    public void setProgramCode(String programCode) { this.programCode = programCode;
    }
}
