
package USER;

import DATA.*;
import FUNCTION.Notification;
import FUNCTION.Request;
import static UI.UI.*;
import UI.*;

import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Faculty extends User {
    // FACULTY FIELDS
    private static int nextFacultyID = 1;

    private String collegeCode, employmentStatus;

    // FACULTY CONSTRUCTOR
    public Faculty(String lastName, String firstName, String middleInitial, String emailAddress,
                   String password, String address, int postalCode, String birthDate, char gender,
                   String collegeCode, String employmentStatus) {

        super(String.format("F%04d", nextFacultyID++), lastName, firstName, middleInitial,
                emailAddress, password, address, postalCode, birthDate, gender);
        this.collegeCode = collegeCode;
        this.employmentStatus = employmentStatus;
    }

    @Override
    public void menu() {
        String select;

        while (true) {
            UI.header("CLASSNAV | FACULTY");
            UI.liner("  TEACHING INFORMATION");
            UI.liner("  [1] View My Profile");
            UI.liner("  [2] Edit My Profile");
            UI.liner("  [3] View My Teaching Schedule");
            UI.separator();
            UI.liner("  ROOMS AND SCHEDULING");
            UI.liner("  [4] View Available Rooms");
            UI.liner("  [5] Locate a Room");
            UI.liner("  [6] Submit a Room Request");
            UI.separator();
            UI.liner("  REQUESTS AND COMMUNICATION");
            UI.liner("  [7] View My Requests and Status");
            UI.liner("  [8] View My Notifications");
            UI.separator();
            UI.liner("  [X] Log Out");
            UI.footer();

            System.out.print("  Select > ");
            select = in.nextLine().trim().toUpperCase();

            switch (select) {
                case "1":
                    User.viewProfile(getUserID());
                    break;
                case "2":
                    User.editUser(getUserID());
                    break;
                case "3":
                    Faculty.displaySchedules(getUserID());
                    break;
                case "4":
                    User.viewAvailableRooms();
                    break;
                case "5":
                    User.locateRoom();
                    break;
                case "6":
                    Request.submitRequest(getUserID());
                    break;
                case "7":
                    Request.viewMyRequests(getUserID());
                    break;
                case "8":
                    viewMyNotifications();
                    break;
                case "X":
                    UI.print("Exiting...");
                    return;
                default:
                    UI.print("Invalid Choice");
            }
        }
    }

    // SETTER METHODS
    public void setCollegeCode(String collegeCode) {
        College college = College.findCollege(collegeCode);
        if (college == null) {
            UI.print("Invalid college code.");
            return;
        }
        this.collegeCode = collegeCode;
    }
    public void setEmploymentStatus(String employmentStatus) {
        if (employmentStatus == null || employmentStatus.trim().isEmpty()) {
            UI.print("Employment status cannot be empty.");
            return;
        }
        this.employmentStatus = employmentStatus;
    }

    // GETTER METHODS
    public static String getCollegeCode(User u) {
        if (u == null)
            return null;

        Faculty f = findFaculty(u.getUserID());
        return f == null ? null : f.collegeCode;
    }
    public static String getEmploymentStatus(User u) {
        if (u == null)
            return null;

        Faculty f = findFaculty(u.getUserID());
        return f == null ? null : f.employmentStatus;
    }

    // FIND METHODS
    public static Faculty findFaculty(String facultyID) {
        if (facultyID == null || facultyID.trim().isEmpty())
            return null;

        for (User u : users) {
            if (u instanceof Faculty
                    && facultyID.trim().equalsIgnoreCase(u.getUserID())) {
                return (Faculty) u;
            }
        }

        return null;
    }

    // DISPLAY FACULTY SCHEDULES
    public static void displaySchedules(String facultyID) {
        Faculty f = findFaculty(facultyID);

        if (f == null) {
            UI.print("Faculty not found.");
            return;
        }

        UI.header("FACULTY SCHEDULE");
        UI.liner(String.format("  FACULTY ID    : %s", f.getUserID()));
        UI.liner(String.format("  FACULTY NAME  : %s", f.getUserName()));
        UI.liner(String.format("  EMAIL ADDRESS : %s", f.getEmail()));
        UI.liner(String.format("  EMPLOYMENT    : %s", f.employmentStatus));

        College college = College.findCollege(f.collegeCode);

        UI.liner(String.format("  COLLEGE       : %s",
                college == null ? "College not found"
                        : college.getCollegeDescription()));

        boolean found = false;

        for (CourseOffering offering : CourseOffering.getOfferingsByFaculty(f.getUserID())) {

            if (offering == null) continue;

            Section section = Section.findSection(offering.getSectionID());
            Course course = Course.findCourse(offering.getCourseCode());

            for (Schedule schedule : Schedule.getSchedulesByOffering(offering.getOfferingID())) {

                if (schedule == null)
                    continue;

                found = true;

                UI.separator();

                UI.liner(String.format("  SCHEDULE ID   : %s", schedule.getScheduleID()));
                UI.liner(String.format("  COURSE CODE   : %s", course == null ?
                        "Course not found" : course.getCourseCode()));
                UI.liner(String.format("  SECTION       : %s", section == null ?
                        "Section not found" : Section.getYearSection(section)));
                UI.liner(String.format("  ACADEMIC YEAR : %s", offering.getAcademicYear()));
                UI.liner(String.format("  TERM          : %d", offering.getTerm()));
                UI.liner(String.format("  DAY           : %s", schedule.getDay()));
                UI.liner(String.format("  TIME          : %s - %s", schedule.getStartTime(), schedule.getEndTime()));
                UI.liner(String.format("  EFFECTIVE     : %s - %s", schedule.getEffectiveFrom(),
                        schedule.getEffectiveTo()));

                String roomCode = schedule.getRoomCode();
                Room room = Room.findRoom(roomCode);

                UI.liner(String.format("  ROOM CODE     : %s", roomCode));

                if (room != null) {
                    Building building = Building.findBuilding(room.getBuildingCode());
                    UI.liner(String.format("  ROOM          : %s", room.getRoomDescription()));
                    UI.liner(String.format("  ROOM TYPE     : %s", room.getRoomType()));
                    UI.liner(String.format("  CAPACITY      : %d", room.getCapacity()));
                    UI.liner(String.format("  FLOOR         : %d", room.getFloorNumber()));
                    UI.liner(String.format("  BUILDING      : %s", building == null ? "Building not found"
                            : building.getBuildingName()));
                } else {
                    UI.liner("  ROOM          : Room not found");
                }
            }
        }

        if (!found) {
            UI.liner("  No schedules found.");
        }

        UI.footer();
    }

    // DISPLAY NOTIFICATIONS
    public static void viewNotifications(String facultyID) {
        Faculty faculty = Faculty.findFaculty(facultyID);

        if (faculty == null) {
            UI.print("Faculty not found");
            return;
        }

        ArrayList<Notification> facultyNotifications =
                Notification.getNotificationsByRecipient(faculty.getUserID());

        UI.header("FACULTY NOTIFICATIONS");
        UI.liner("  FACULTY ID   : " + faculty.getUserID());
        UI.liner("  FACULTY NAME : " + faculty.getUserName());
        UI.separator();

        if (facultyNotifications.isEmpty()) {
            UI.liner("  No notifications found.");
            UI.footer();
            return;
        }

        UI.liner(String.format("  %-5s %-8s %-22s %-20s",
                "NO.", "ID", "SENDER", "MESSAGE"));
        UI.separator();

        for (int i = 0; i < facultyNotifications.size(); i++) {
            Notification n = facultyNotifications.get(i);

            Student student = Student.findStudent(
                    n.getSenderID());

            String senderName = student == null
                    ? n.getSenderID()
                    : student.getUserName();

            String message = n.getMessage();

            if (message == null)
                message = "";

            if (message.length() > 17)
                message = message.substring(0, 17) + "...";

            UI.liner(String.format("  [%-3d] %-8s %-22s %-20s",
                    i + 1,
                    String.format("%04d", n.getNotificationID()),
                    senderName,
                    message));
        }

        UI.liner("");
        UI.liner("  [0] Back");
        UI.footer();

        String choice;
        int selected = -1;

        do {
            System.out.print("  Select > ");
            choice = in.nextLine().trim();

            try {
                selected = Integer.parseInt(choice);
                if (selected < 0 || selected > facultyNotifications.size()) {
                    UI.print("Invalid selection. Choose from 0 to "
                            + facultyNotifications.size());
                    selected = -1;
                }
            } catch (NumberFormatException e) {
                UI.print("Invalid input. Numbers only.");
                selected = -1;
            }
        } while (selected == -1);

        if (selected == 0) return;

        Notification n = facultyNotifications.get(selected - 1);
        Student student = Student.findStudent(n.getSenderID());
        UI.header("NOTIFICATION DETAILS");
        UI.liner("  NOTIFICATION ID : " + n.getNotificationID());
        UI.liner("  FROM            : " + (student == null ? n.getSenderID() : student.getUserName()));
        UI.liner("  SENDER ID       : " + n.getSenderID());
        UI.liner("  TO              : " + n.getRecipientID());
        UI.separator();
        UI.liner("  COURSE CODE     : " + n.getCourseCode());
        UI.liner("  ROOM CODE       : " + n.getRoomCode());
        UI.liner("  BUILDING NAME   : " + n.getBuildingName());
        UI.liner("  DAY             : " + n.getDay());
        UI.liner("  TIME            : " + n.getStartTime() + " - " + n.getEndTime());
        UI.separator();
        UI.liner("  MESSAGE         : " + n.getMessage());
        UI.footer();
    }
}
