package USER;
import DATA.*;
import FUNCTION.Notification;
import UI.*;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import static DATA.Course.courses;
import static DATA.Enrollment.enrollments;
import static DATA.Room.*;
import static DATA.Schedule.schedules;
import static DATA.Section.sections;

import static FUNCTION.Notification.*;

public class Faculty extends User {
    static Scanner in = new Scanner(System.in);

    // FACULTY FIELDS
    private static int nextFacultyID = 1;
    private String contactNumber;
    private String collegeCode;
    private String supervisorID;
    public static ArrayList<String[]> requests = new ArrayList<>();

    // FACULTY CONSTRUCTOR
    public Faculty(String lastName, String firstName, String middleInitial, String emailAddress,
                   String password, String address, int postalCode, String birthDate, char gender, String contactNumber,
                   String collegeCode, String supervisorID) {
        super(String.format("F%04d", nextFacultyID++), lastName, firstName, middleInitial, emailAddress, password,
                address, postalCode, birthDate, gender);
        this.contactNumber = contactNumber;
        this.collegeCode = collegeCode;
        this.supervisorID = supervisorID;
    }

    // add notificationID
//    static {
//        requests.add(new String[]{"S001", "F001", "R101", "PM1", "Monday", "08:00 AM", "10:00 AM",
//                "Request for Room R101 in PM1 has been submitted."});
//        requests.add(new String[]{"S002", "F002", "R202", "R1",
//                "Tuesday", "10:00 AM", "12:00 PM", "Request for Room R202 in R1 has been submitted."});
//        requests.add(new String[]{"S003", "F003", "R303", "CB2",
//                "Wednesday", "01:00 PM", "03:00 PM", "Request for Room R303 in CB2 has been submitted."});
//        requests.add(new String[]{"S004", "F001", "R102", "PM1",
//                "Thursday", "08:00 AM", "11:00 AM", "Request for Room R102 in PM1 has been submitted."});
//        requests.add(new String[]{"S005", "F002", "R201", "R1",
//                "Friday", "02:00 PM", "04:00 PM", "Request for Room R201 in R1 has been submitted."});
//    }

    // FACULTY SETTER METHODS
        public void setContactNumber(String contactNumber) {
            this.contactNumber = contactNumber;
        }
        public void setCollegeCode(String collegeCode) {
            this.collegeCode = collegeCode;
        }
        public void setSupervisorID(String supervisorID) {
            this.supervisorID = supervisorID;
        }

    // FACULTY GETTER METHODS
    public static Faculty findFaculty(String userID) {
        for (User u : users) if (userID.equals(u.getUserID()) && u.getClass() == Faculty.class) return (Faculty) u;
        return null;
    }
    public static String getContactNumber(User u) {
        Faculty f = findFaculty(u.getUserID());
        if (f == null) return null;
        return f.contactNumber;
    }
    public static String getCollegeCode(User u) {
        Faculty f = findFaculty(u.getUserID());
        if (f == null) return null;
        return f.collegeCode;
    }
    public static String getSupervisorID(User u) {
        Faculty f = findFaculty(u.getUserID());
        if (f == null) return null;
        return f.supervisorID;
    }

    // FACULTY MENU METHODS
    public static void requestRoom(String facultyID) {
        String buildingCode, roomCode, day, startTime, endTime, courseCode = "";
        Faculty f = Faculty.findFaculty(facultyID);
        if (f == null) {
            UI.print("Faculty not found");
            return;
        }
        Room r = null;

        UI.print("REQUEST ROOM");

        do {
            System.out.print("  Course Code > ");
            courseCode = in.nextLine().trim();
            if (courseCode.trim().isEmpty()) UI.print("Invalid Input");
            if (!Course.courseLoad(facultyID, courseCode))
                UI.print("Course is not in your teaching load.");
        } while (!Course.courseLoad(facultyID, courseCode));

        do {
            System.out.print("  Building Code > ");
            buildingCode = in.nextLine();
            if (!Building.verifyBuilding(buildingCode)) UI.print("Building does not exist");
        } while (!Building.verifyBuilding(buildingCode));

        do {
            System.out.print("  Room Code > ");
            roomCode = in.nextLine();
            if (!Room.existRoomCode(roomCode)) UI.print("Room does not exist");
            else {
                r = findRoom(roomCode);
                if (r == null) return;
                if (!Room.getBuildingCode(r).equals(buildingCode)) UI.print("Room does not exist in " + buildingCode);
            }
            if (r == null) return;
        } while (!getBuildingCode(r).equals(buildingCode));

        do {
            System.out.print("  Day > ");
            day = in.nextLine();
            if (!day.matches("M|T|W|Th|F|S|Su")) UI.print("Invalid Input");
        } while (!day.matches("M|T|W|Th|F|S|Su"));

        do {
            System.out.print("  Start Time > ");
            startTime = in.nextLine();
            if (!startTime.matches("(?:0?[1-9]|1[0-2]):[0-5][0-9]\\s?(?i:AM|PM)")) UI.print("Invalid Input");
        } while (!startTime.matches("(?:0?[1-9]|1[0-2]):[0-5][0-9]\\s?(?i:AM|PM)"));

        do {
            do {
                System.out.print("  End Time > ");
                endTime = in.nextLine();
                if (!endTime.matches("(?:0?[1-9]|1[0-2]):[0-5][0-9]\\s?(?i:AM|PM)")) UI.print("Invalid Input");
            } while (!endTime.matches("(?:0?[1-9]|1[0-2]):[0-5][0-9]\\s?(?i:AM|PM)"));
            if (convertToMinutes(endTime) <= convertToMinutes(startTime))
                UI.print("End time must be later than start time.");
        } while (convertToMinutes(endTime) <= convertToMinutes(startTime));


        for (Section s : sections) {
            Course c = Course.findCourse(Section.getCourseID(s));

            if (c != null && Course.getFacultyID(c).equals(facultyID)) {
                for (Schedule sc : schedules) {
                    if (Schedule.getSectionID(sc).equals(s.getSectionID())) {
                        boolean sameCourse = Course.getCourseCode(c).equalsIgnoreCase(courseCode);


                    }
                }
            }
        }

        for (Section s : sections) {
            Course c = Course.findCourse(Section.getCourseID(s));

            if (c != null && Course.getFacultyID(c).equals(facultyID)) {
                for (Schedule sc : schedules) {
                    if (Schedule.getSectionID(sc).equals(s.getSectionID())) {

                        if (day.equalsIgnoreCase(Schedule.getDay(sc))) {
                            int existingStart = convertToMinutes(Schedule.getStartTime(sc));
                            int existingEnd = convertToMinutes(Schedule.getEndTime(sc));

                            int requestedStart = convertToMinutes(startTime);
                            int requestedEnd = convertToMinutes(endTime);

                            if (requestedStart < existingEnd && requestedEnd > existingStart) {
                                UI.print("Faculty has an existing schedule during this time.");
                                return;
                            }
                        }
                    }
                }
            }
        }

        if (Room.verifyAvailability(roomCode, day, startTime, endTime)) {
            UI.header("REQUEST ROOM");
            UI.liner(String.format("  FACULTY ID    : %s", f.getUserID()));
            UI.separator();
            UI.liner(String.format("  BUILDING CODE : %s", buildingCode));
            UI.liner(String.format("  ROOM CODE     : %s", roomCode));
            UI.liner(String.format("  DAY           : %s", day));
            UI.liner(String.format("  START TIME    : %s", startTime));
            UI.liner(String.format("  END TIME      : %s", endTime));
            UI.footer();

            System.out.print("  Request (Y/N) > ");
            char choice = in.next().toUpperCase().charAt(0);

            if (choice == 'Y') {
                requests.add(new String[]{
                        buildingCode, roomCode, day, startTime, endTime
                });
                UI.print("Room request submitted successfully.");
            } else {
                requestRoom(facultyID);
            }
        } else {
            UI.print("Room is occupied.");
        }
    }


    public static void displaySchedules(String facultyID) {
        Faculty f = Faculty.findFaculty(facultyID);
        if (f == null) {
            UI.print("Faculty not found");
            return;
        }

        UI.header("FACULTY SCHEDULE");
        UI.liner(String.format("  FACULTY ID    : %s", facultyID));
        UI.separator();

        boolean found = false;

        for (Section s : sections) {
            Course c = Course.findCourse(Section.getCourseID(s));

            if (c != null && Course.getFacultyID(c).equals(facultyID)) {
                String sectionID = Section.getSection(s);

                for (Schedule sc : schedules) {
                    if (sectionID.equals(Schedule.getSectionID(sc))) {
                        found = true;

                        UI.liner(String.format("  SCHEDULE ID   : %s", Schedule.getSchedule(sc)));
                        UI.liner(String.format("  COURSE CODE   : %s", Course.getCourseCode(c)));
                        UI.liner(String.format("  DAY           : %s", Schedule.getDay(sc)));
                        UI.liner(String.format("  TIME          : %s - %s",
                                Schedule.getStartTime(sc), Schedule.getEndTime(sc)));
                        UI.liner(String.format("  SECTION ID    : %s", sectionID));

                        String roomCode = Schedule.getRoomCode(sc);
                        UI.liner(String.format("  ROOM CODE     : %s", roomCode));

                        Room r = findRoom(roomCode);

                        if (r != null) {
                            String buildingCode = getBuildingCode(r);
                            Building b = Building.findBuilding(buildingCode);
                            if (b != null) {
                                UI.liner(String.format("  BUILDING NAME : %s",
                                        Building.getBuildingName(b)));
                            } else {
                                UI.liner("  BUILDING NAME : Building not found");
                            }
                        } else {
                            UI.liner("  BUILDING NAME : Room not found");
                        }
                        UI.separator();
                    }
                }
            }
        }
        if (!found) UI.liner("  No schedules found.");
        UI.footer();
    }
    public static void locateRoom(String roomCode) {
        Room r = findRoom(roomCode);
        if (r == null) {
            UI.print("Room not found");
            return;
        }
        Building b = Building.findBuilding(getBuildingCode(r));
        if (b == null) {
            UI.print("Building not found");
            return;
        }
        UI.header("ROOM INFORMATION");
        System.out.printf("│%-" + innerWidth + "s│\n", "  ROOM DETAILS");
        UI.separator();
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("  ROOM CODE         : %s", getRoom(r)));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("  ROOM DESCRIPTION  : %s", getRoomDescription(r)));
        UI.separator();
        System.out.printf("│%-" + innerWidth + "s│\n", "  ROOM LOCATION");
        UI.separator();
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("  BUILDING NAME     : %s", Building.getBuildingName(b)));
        int floor = getFloorNumber(r);
        String floorText;
        if (floor == 1) floorText = "1st Floor";
        else if (floor == 2) floorText = "2nd Floor";
        else if (floor == 3) floorText = "3rd Floor";
        else floorText = floor + "th Floor";
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("  FLOOR NUMBER      : %s", floorText));
        UI.footer();
    }
    public static void viewAvailableRooms(String day, String startTime, String endTime) {
        int scheduledStart = convertToMinutes(startTime);
        int scheduledEnd = convertToMinutes(endTime);

        UI.header("AVAILABLE ROOMS");
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("  DAY          : %s", day));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("  START TIME   : %s", startTime));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("  END TIME     : %s", endTime));
        UI.separator();
        boolean found = false;

        for (Room r : rooms) {
            String roomCode = getRoom(r);
            boolean available = true;

            for (Schedule s : schedules) {
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
                int floor = getFloorNumber(r);
                String floorText;
                if (floor == 1) floorText = "1st Floor";
                else if (floor == 2) floorText = "2nd Floor";
                else if (floor == 3) floorText = "3rd Floor";
                else floorText = floor + "th Floor";
                String room = String.format("  %-10s %-32s %-15s %-4s", getRoom(r), getRoomDescription(r),
                        floorText, getBuildingCode(r));
                System.out.printf("│%-" + innerWidth + "s│\n", room);
            }
        }
        if (!found) {
            System.out.printf("│%-" + innerWidth + "s│%n", "  NO AVAILABLE ROOMS FOUND");
        }
        UI.footer();
    }
    public static void viewNotifications(String facultyID) {
        String choice;
        int valid = 0, value = -1, find = 1;
        User u = findFaculty(facultyID);

        if (u == null) {
            UI.print("Faculty not found");
            return;
        }

        UI.header("CHECK NOTIFICATION");
        UI.liner("  FACULTY ID    : " + facultyID);
        UI.liner("  FACULTY NAME  : " + u.getUserName());
        UI.separator();

        for (Notification n : notifications) {
            if (facultyID.equals(getRecipientID(n))) {
                String message = getMessage(n);

                if (message.length() > 25) {
                    message = message.substring(0, 20) + "...";
                }

                User us = getUser(Notification.getSenderID(n));
                if (us == null) {
                    UI.print("Sender not found");
                    return;
                }

                if (valid == 0) UI.liner(String.format("  %-6s %-9s %-25s %-24s", "NO.", "ID", "FROM", "MESSAGE"));

                String text = String.format("  %-10s %-25s %-20s",
                        String.format("%04d", Notification.getNotification(n)),
                        us.getUserName(), message);

                UI.liner("  [" + (valid + 1) + "] " + text);
                valid++;
            }
        }

        if (valid == 0) {
            UI.liner("  No notifications");
            UI.footer();
            return;
        }

        UI.liner("");
        UI.liner("  [0] Back");
        UI.footer();

        do {
            System.out.print("  Select > ");
            choice = in.nextLine().trim();

            try {
                value = Integer.parseInt(choice);
                if (!(value >= 0 && value <= valid)) {
                    UI.print("Invalid Input");
                }
            } catch (NumberFormatException e) {
                UI.print("Invalid Input");
                value = -1;
            }
        } while (!(value >= 0 && value <= valid));

        if (value != 0) {

            for (Notification n : notifications) {
                if (facultyID.equals(getRecipientID(n))) {
                    if (value == find++) {
                        User us = getUser(Notification.getSenderID(n));

                        if (us == null) {
                            UI.print("Sender not found");
                            return;
                        }

                        String sec = "";

                        for (Section s : sections) {
                            Course c = Course.findCourse(Section.getCourseID(s));

                            if (c != null && Course.getFacultyID(c).equals(facultyID)) {
                                for (Enrollment e : enrollments) {
                                    if (s.getSectionID().equals(Enrollment.getSectionID(e))
                                            && us.getUserID().equals(Enrollment.getStudentNumber(e))) {
                                        sec = Section.getYearSection(s);
                                        break;
                                    }
                                }

                                if (!sec.trim().isEmpty()) break;
                            }
                        }

                        UI.header("NOTIFICATION MAIL");
                        UI.liner("  NOTIFICATION ID  : " +
                                String.format("%04d", getNotification(n)));
                        UI.liner("  FROM             : " + us.getUserName());
                        UI.liner("  TO               : " + Notification.getRecipientID(n));
                        UI.separator();
                        UI.liner("  SECTION          : " + sec);
                        UI.liner("  ROOM CODE        : " + Notification.getRoomCode(n));
                        UI.liner("  BUILDING NAME    : " + Notification.getBuildingName(n));
                        UI.liner("  DAY              : " + Notification.getDay(n));
                        UI.liner("  TIME             : " + Notification.getStartTime(n)
                                + " - " + Notification.getEndTime(n));
                        UI.separator();
                        UI.liner("  MESSAGE          : " + Notification.getMessage(n));
                        UI.footer();
                        return;
                    }
                }
            }
        }
    }
}
