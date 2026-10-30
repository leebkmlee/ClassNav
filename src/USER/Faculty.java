
package USER;

import DATA.*;
import FUNCTION.Notification;
import FUNCTION.Request;
import static UI.UI.*;
import UI.*;

import java.util.ArrayList;

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
            UI.header("CLASSNAV | FACULTY", false);
            UI.liner("  TEACHING INFORMATION", false);
            UI.liner("  [1] View My Profile", false);
            UI.liner("  [2] Edit My Profile", false);
            UI.liner("  [3] View My Teaching Schedule", false);
            UI.separator(false);
            UI.liner("  ROOMS AND SCHEDULING", false);
            UI.liner("  [4] View Available Rooms", false);
            UI.liner("  [5] Locate a Room", false);
            UI.liner("  [6] Submit a Room Request", false);
            UI.liner("  [7] Cancel a Scheduled Class in My Load", false);
            UI.separator(false);
            UI.liner("  REQUESTS AND COMMUNICATION", false);
            UI.liner("  [8] View My Requests and Status", false);
            UI.liner("  [9] View My Notifications", false);
            UI.liner("  [10] Notify Students in My Class", false);
            UI.separator(false);
            UI.liner("  [X] Log Out", false);
            UI.footer(false);

            System.out.print("  Select > ");
            select = in.nextLine().trim().toUpperCase();

            switch (select) {
                case "1": User.viewProfile(getUserID(),true); break;
                case "2": editUser(getUserID()); break;
                case "3": Faculty.displaySchedules(getUserID()); break;
                case "4": User.viewAvailableRooms(); break;
                case "5": User.locateRoom(); break;
                case "6": Request.submitRequest(getUserID()); break;
                case "7": CancelledSchedule.createCancelledSchedule(findFaculty(getUserID())); break;
                case "8": Request.viewMyRequests(getUserID()); break;
                case "9": viewMyNotifications(); break;
                case "10": notifyStudents(getUserID()); break;
                case "X": UI.print("You have been logged out."); return;
                default: UI.print("Invalid Choice");
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
    public String getCollegeCode() {
        return collegeCode;
    }
    public String getEmploymentStatus() {
        return employmentStatus;
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

        ArrayList<Schedule> facultySchedules = new ArrayList<>();
        for (CourseOffering offering : CourseOffering.getOfferingsByFaculty(f.getUserID())) {
            if (offering == null) continue;
            facultySchedules.addAll(Schedule.getSchedulesByOffering(offering.getOfferingID()));
        }

        facultySchedules.sort((a, b) -> {
            int day = Integer.compare(dayOrder(a.getDay()), dayOrder(b.getDay()));
            if (day != 0) return day;
            return Integer.compare(Room.convertToMinutes(a.getStartTime()),
                    Room.convertToMinutes(b.getStartTime()));
        });

        UI.header("FACULTY TEACHING SCHEDULE", true);
        UI.liner("  FACULTY ID: " + f.getUserID() + " | NAME: " + f.getUserName()
                + " | COLLEGE: " + f.getCollegeCode(), true);
        if (facultySchedules.isEmpty()) {
            UI.liner("  No schedules found for this faculty member.", true);
        } else {
            String format = "%-10s %-10s %-10s %-12s %-10s %-19s %-22s %-10s %-24s %-44s";
            UI.liner(String.format(format, "SCHEDULE", "OFFERING", "COURSE", "SECTION", "DAY",
                    "TIME", "EFFECTIVE", "ROOM", "BUILDING", "ROOM DESCRIPTION"), true);
            UI.separator(true);
            int count = 0;
            for (Schedule schedule : facultySchedules) {
                if (schedule == null) continue;
                CourseOffering offering = CourseOffering.findOffering(schedule.getOfferingID());
                Course course = offering == null ? null : Course.findCourse(offering.getCourseCode());
                Section section = offering == null ? null : Section.findSection(offering.getSectionID());
                Room room = Room.findRoom(schedule.getRoomCode());
                Building building = room == null ? null : Building.findBuilding(room.getBuildingCode());
                UI.liner(String.format(format,
                        schedule.getScheduleID(), schedule.getOfferingID(),
                        course == null ? "-" : course.getCourseCode(),
                        section == null ? "-" : UI.fitText(Section.getYearSection(section), 12),
                        schedule.getDay(), UI.fitText(UI.formatTime(schedule.getStartTime())
                                + " - " + UI.formatTime(schedule.getEndTime()), 19),
                        UI.fitText(schedule.getEffectiveFrom() + " - " + schedule.getEffectiveTo(), 22),
                        schedule.getRoomCode(),
                        UI.fitText(building == null ? "-" : building.getBuildingName(), 24),
                        (room == null ? "Room unavailable" : room.getRoomDescription())), true);
                count++;
            }
            UI.separator(true);
            UI.liner("  TOTAL SCHEDULES: " + count, true);
        }
        UI.footer(true);
        UI.returnButton();
    }

    private static int dayOrder(String day) {
        if (day == null) return 8;
        for (int i = 0; i < UI.DAYS.length; i++)
            if (UI.DAYS[i].equalsIgnoreCase(day.trim())) return i + 1;
        return 8;
    }

    // DISPLAY NOTIFICATIONS
    public static void viewNotifications(String facultyID) {
        Faculty faculty = findFaculty(facultyID);
        if (faculty == null) {
            UI.print("Faculty not found.");
            return;
        }
        faculty.viewMyNotifications();
    }

    // NOTIFY STUDENTS ENROLLED IN A FACULTY MEMBER'S COURSE OFFERING
    public static void notifyStudents(String facultyID) {
        Faculty faculty = findFaculty(facultyID);
        if (faculty == null) {
            UI.print("Faculty not found.");
            return;
        }
        if (faculty.getEmploymentStatus() == null
                || !faculty.getEmploymentStatus().equalsIgnoreCase("ACTIVE")) {
            UI.print("Only active faculty members may send class announcements.");
            return;
        }

        ArrayList<CourseOffering> assignedOfferings = CourseOffering.getOfferingsByFaculty(faculty.getUserID());
        if (assignedOfferings.isEmpty()) {
            UI.print("No course offerings are assigned to this faculty member.");
            return;
        }

        UI.header("MY COURSE OFFERINGS", true);
        UI.liner(String.format("  %-14s %-14s %-22s %-18s %-14s %-10s %-80s",
                "OFFERING ID", "COURSE", "SECTION", "ACADEMIC YEAR", "FACULTY ID", "TERM", "COURSE DESCRIPTION"), true);
        UI.separator(true);
        for (CourseOffering offering : assignedOfferings) {
            if (offering == null) continue;
            Course course = Course.findCourse(offering.getCourseCode());
            Section section = Section.findSection(offering.getSectionID());
            UI.liner(String.format("  %-14s %-14s %-22s %-18s %-14s %-10d %-80s",
                    offering.getOfferingID(), offering.getCourseCode(),
                    UI.fitText(section == null ? "-" : Section.getYearSection(section), 22),
                    offering.getAcademicYear(), offering.getFacultyID(), offering.getTerm(),
                    (course == null ? "Course unavailable" : course.getCourseDescription())), true);
        }
        UI.footer(true);

        String offeringID;
        CourseOffering selectedOffering;
        do {
            UI.select("Enter Offering ID", false);
            offeringID = in.nextLine().trim();
            if (offeringID.equalsIgnoreCase("X")) return;
            selectedOffering = CourseOffering.findOffering(offeringID);
            if (selectedOffering == null || selectedOffering.getFacultyID() == null
                    || !selectedOffering.getFacultyID().equalsIgnoreCase(faculty.getUserID()))
                UI.print("Choose an offering assigned to your account.");
        } while (selectedOffering == null || selectedOffering.getFacultyID() == null
                || !selectedOffering.getFacultyID().equalsIgnoreCase(faculty.getUserID()));

        ArrayList<CourseRegistration> registrations = CourseRegistration.getRegistrationsByOffering(selectedOffering.getOfferingID());
        if (registrations.isEmpty()) {
            UI.print("No enrolled students are associated with this offering.");
            return;
        }

        String mode;
        do {
            UI.header("NOTIFICATION RECIPIENT", false);
            UI.liner("  [1] Notify One Enrolled Student", false);
            UI.liner("  [2] Notify All Enrolled Students in This Section", false);
            UI.footer(false);
            UI.select("Select Recipient", false);
            mode = in.nextLine().trim().toUpperCase();
            if (!mode.matches("[12X]")) UI.print("Invalid choice.");
        } while (!mode.matches("[12X]"));
        if (mode.equals("X")) return;

        ArrayList<Student> recipients = new ArrayList<>();
        if (mode.equals("1")) {
            String studentID;
            Student student;
            boolean enrolled;
            do {
                UI.select("Enter Student ID", false);
                studentID = in.nextLine().trim();
                if (studentID.equalsIgnoreCase("X")) return;
                student = Student.findStudent(studentID);
                enrolled = false;
                for (CourseRegistration registration : registrations) {
                    if (registration != null && registration.getStudentID() != null
                            && registration.getStudentID().equalsIgnoreCase(studentID)) {
                        enrolled = true;
                        break;
                    }
                }
                if (student == null || !enrolled) UI.print("Student is not enrolled in this course offering.");
            } while (student == null || !enrolled);
            recipients.add(student);
        } else {
            ArrayList<String> added = new ArrayList<>();
            for (CourseRegistration registration : registrations) {
                if (registration == null || registration.getStudentID() == null
                        || added.contains(registration.getStudentID().toUpperCase())) continue;
                Student student = Student.findStudent(registration.getStudentID());
                if (student != null) {
                    recipients.add(student);
                    added.add(student.getUserID().toUpperCase());
                }
            }
        }

        String message;
        do {
            UI.select("Enter Message", false);
            message = in.nextLine().trim();
            if (message.equalsIgnoreCase("X")) return;
            if (message.isEmpty()) UI.print("Message cannot be blank.");
        } while (message.isEmpty());

        int sent = 0;
        for (Student recipient : recipients) {
            if (recipient != null && Notification.sendNotification(faculty.getUserID(), recipient.getUserID(),
                    selectedOffering.getCourseCode(), "", "", "", "", "", message,
                    selectedOffering.getOfferingID(), null, null)) sent++;
        }
        UI.print(sent + " notification(s) sent successfully.");
    }
}