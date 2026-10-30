package USER;
import DATA.*;
import FUNCTION.*;
import UI.*;
import static UI.UI.*;
import java.time.*;
import java.time.format.*;

public class DepartmentHead extends User {

    // FIELDS
    private static int nextDepartmentHeadID = 1;
    private String collegeCode;

    // CONSTRUCTOR
    public DepartmentHead(String lastName, String firstName, String middleInitial,
                          String emailAddress, String password, String address,
                          int postalCode, String birthDate, char sex, String collegeCode) {
        super(String.format("DH%04d", nextDepartmentHeadID++), lastName, firstName,
                middleInitial, emailAddress, password, address, postalCode, birthDate, sex);
        this.collegeCode = collegeCode;
    }

    @Override
    public void menu() {
        String select;

        while (true) {
            header("CLASSNAV | DEPARTMENT HEAD", false);
            liner("  [S] Search By ID", false);
            separator(false);
            liner("  OVERVIEW AND ACCOUNT", false);
            liner("  [1] View System Summary", false);
            liner("  [2] View My Profile", false);
            liner("  [3] Edit My Profile", false);
            separator(false);
            liner("  REQUESTS AND APPROVALS", false);
            liner("  [4] View Faculty Requests", false);
            liner("  [5] Review Pending Requests", false);
            separator(false);
            liner("  RECORD MANAGEMENT", false);
            liner("  [6] Academic Management", false);
            liner("  [7] Buildings and Rooms", false);
            liner("  [8] Schedules and Exceptions", false);
            liner("  [9] Notifications", false);
            liner("  [A] Archived Records", false);
            separator(false);
            liner("  [X] Log Out", false);
            footer(false);

            System.out.print("  Select > ");
            select = in.nextLine().trim().toUpperCase();

            switch (select) {
                case "1": displaySystemSummary(); break;
                case "2": User.viewProfile(getUserID(), true);break;
                case "3": editUser(getUserID()); break;
                case "4": Request.viewRequest(getUserID()); break;
                case "5": Request.viewRequests(getUserID()); break;
                case "6": academicManageMenu(); break;
                case "7": facilitiesManageMenu(); break;
                case "8": scheduleManageMenu(); break;
                case "9": notificationMenu(getUserID()); break;
                case "A": archiveMenu(); break;
                case "S": User.searchID(); break;
                case "X": print("You have been logged out."); return;
                default: print("Invalid Choice");
            }
        }
    }
    public static void displayArchivedUsers() {
        header("ARCHIVED USERS", true);
        if (archiveStudent.isEmpty()) {
            liner("  No archived users found.", true);
        } else {
            liner(String.format("  %-14s %-42s %-99s %-20s",
                    "USER ID", "USER NAME", "EMAIL ADDRESS", "USER TYPE"), true);
            separator(true);
            int count = 0;
            for (User u : archiveStudent) {
                if (u == null) continue;
                String userType = u instanceof Student ? "Student"
                        : u instanceof Faculty ? "Faculty"
                        : u instanceof DepartmentHead ? "Department Head" : "User";
                liner(String.format("  %-14s %-42s %-99s %-20s",
                        u.getUserID(), fitText(u.getLastName() + ", " + u.getFirstName(), 42),
                        fitText(u.getEmail(), 99), userType), true);
                count++;
            }
            separator(true);
            liner("  TOTAL ARCHIVED USERS: " + count, true);
        }
        footer(true);
        returnButton();
    }
    public static void displayUsers() {
        header("USER LIST", true);
        if (users.isEmpty()) liner("  No users found.", true);
        else {
            String format = "  %-12s %-42s %-20s %-22s %-78s";
            liner(String.format(format, "USER ID", "FULL NAME", "ROLE", "PROGRAM / COLLEGE", "EMAIL ADDRESS"), true);
            separator(true);
            int count = 0;
            for (User u : users) {
                if (u == null) continue;
                String userType = "User", special = "N/A";
                if (u instanceof Student student) {
                    userType = "Student";
                    special = student.getProgramCode() + " / " + student.getEnrollmentStatus();
                } else if (u instanceof Faculty faculty) {
                    userType = "Faculty";
                    special = faculty.getCollegeCode() + " / " + faculty.getEmploymentStatus();
                } else if (u instanceof DepartmentHead head) {
                    userType = "Department Head";
                    special = head.getCollegeCode();
                }
                liner(String.format(format, u.getUserID(), UI.fitText(u.getUserName(), 42), userType,
                        UI.fitText(special, 22), UI.fitText(u.getEmail(), 78)), true);
                liner(String.format(format, "", UI.fitText("Address: " + u.getAddress(), 42),
                        UI.fitText("Birthdate: " + u.getBirthDate(), 20),
                        "Sex: " + u.getSex(), ""), true);
                count++;
                separator(true);
            }
            liner("  TOTAL USERS: " + count, true);
        }
        footer(true);
        returnButton();
    }

    private static void academicManageMenu() {
        String choice;
        do {
            header("ACADEMIC MANAGEMENT", false);
            liner("  [1] Manage Users", false);
            liner("  [2] Manage Colleges", false);
            liner("  [3] Manage Programs", false);
            liner("  [4] Manage Courses", false);
            liner("  [5] Manage Course Offerings", false);
            liner("  [6] Manage Sections", false);
            liner("  [7] Manage Curricula", false);
            liner("  [8] Manage Course Registrations", false);
            footer(false);
            UI.select("Select", false);
            choice = in.nextLine().trim().toUpperCase();
            switch (choice) {
                case "1": manageUsers(); break;
                case "2": manageColleges(); break;
                case "3": managePrograms(); break;
                case "4": manageCourses(); break;
                case "5": manageCourseOfferings(); break;
                case "6": manageSections(); break;
                case "7": manageCurricula(); break;
                case "8": manageCourseRegistrations(); break;
                case "X": break;
                default: print("Invalid Choice");
            }
        } while (!choice.equalsIgnoreCase("X"));
    }
    private static void facilitiesManageMenu() {
        String choice;
        do {
            header("BUILDINGS AND ROOMS", false);
            liner("  [1] Manage Buildings", false);
            liner("  [2] Manage Rooms", false);
            footer(false);
            UI.select("Select", false);
            choice = in.nextLine().trim().toUpperCase();
            switch (choice) {
                case "1": manageBuildings(); break;
                case "2": manageRooms(); break;
                case "X": break;
                default: print("Invalid Choice");
            }
        } while (!choice.equalsIgnoreCase("X"));
    }
    private void scheduleManageMenu() {
        String choice;
        do {
            header("SCHEDULES AND EXCEPTIONS", false);
            liner("  [1] Manage Schedules", false);
            liner("  [2] Manage Cancelled Schedules", false);
            liner("  [3] Manage Make-up Schedules", false);
            footer(false);
            UI.select("Select", false);
            choice = in.nextLine().trim().toUpperCase();
            switch (choice) {
                case "1": manageSchedules(); break;
                case "2": manageCancelledSchedules(); break;
                case "3": manageMakeUpSchedules(); break;
                case "X": break;
                default: print("Invalid Choice");
            }
        } while (!choice.equalsIgnoreCase("X"));
    }
    private static void notificationMenu(String departmentHeadID) {
        String choice;
        do {
            header("NOTIFICATIONS", false);
            liner("  [1] View My Notifications", false);
            footer(false);
            UI.select("Select", false);
            choice = in.nextLine().trim().toUpperCase();
            switch (choice) {
                case "1": {
                    User current = User.findUser(departmentHeadID);
                    if (current != null) current.viewMyNotifications();
                    break;
                }
                case "X": break;
                default: print("Invalid Choice");
            }
        } while (!choice.equalsIgnoreCase("X"));
    }
    public static void archiveUser() {
        String userID, choice;

        print("ARCHIVE EXISTING USER");

        do {
            UI.select("Enter User ID", false);
            userID = in.nextLine().trim();

            if (userID.equalsIgnoreCase("X")) return;
            else if (userID.isEmpty()) UI.print("Enter User ID");
            else if (findUser(userID) == null) print("User does not exist");
        } while (findUser(userID) == null);

        User u = User.findUser(userID);

        if (u == null) {
            print("User not found");
            return;
        }

        for (CourseRegistration registration : CourseRegistration.registrations) {
            if (registration != null && registration.getStudentID() != null
                    && registration.getStudentID().equalsIgnoreCase(userID)) {
                print("Archive the student's active course registrations first.");
                return;
            }
        }
        for (CourseOffering offering : CourseOffering.offerings) {
            if (offering != null && offering.getFacultyID() != null
                    && offering.getFacultyID().equalsIgnoreCase(userID)) {
                print("Reassign or archive the faculty member's active course offerings first.");
                return;
            }
        }
        for (Request request : Request.requests) {
            if (request != null && request.getFacultyID() != null
                    && request.getFacultyID().equalsIgnoreCase(userID)) {
                print("This user is referenced by request history. Keep the account to preserve that history.");
                return;
            }
        }
        for (MakeUpSchedule makeUp : MakeUpSchedule.makeUpSchedules) {
            if (makeUp != null && makeUp.getFacultyID() != null
                    && makeUp.getFacultyID().equalsIgnoreCase(userID)) {
                print("This user is referenced by make-up schedule history. Keep the account to preserve that history.");
                return;
            }
        }
        for (Notification notification : Notification.notifications) {
            if (notification != null && ((notification.getSenderID() != null
                    && notification.getSenderID().equalsIgnoreCase(userID))
                    || (notification.getRecipientID() != null
                    && notification.getRecipientID().equalsIgnoreCase(userID)))) {
                print("This user is referenced by notification history. Keep the account to preserve that history.");
                return;
            }
        }
        for (Notification notification : Notification.archiveNotifications) {
            if (notification != null && ((notification.getSenderID() != null
                    && notification.getSenderID().equalsIgnoreCase(userID))
                    || (notification.getRecipientID() != null
                    && notification.getRecipientID().equalsIgnoreCase(userID)))) {
                print("This user is referenced by archived notification history. Keep the account to preserve that history.");
                return;
            }
        }

        User.viewProfile(userID, false);

        do {
            UI.select("Confirm User Deletion? (Y/N)", false);
            choice = in.nextLine().trim().toUpperCase();
            if (choice.equals("X")) return;

            if (!choice.matches("[YN]"))
                print("Invalid Input");
        } while (!choice.matches("[YN]"));

        if (choice.equals("Y")) {
            archiveStudent.add(u);
            users.remove(u);
            print("User successfully deleted");
        } else {
            print("User deletion cancelled");
        }
    }
    private static void archiveMenu() {
        String choice;
        do {
            header("ARCHIVED RECORDS", false);
            liner("  [1] Archived Users", false);
            liner("  [2] Academic Record Archives", false);
            liner("  [3] Building and Room Archives", false);
            liner("  [4] Schedule and Exception Archives", false);
            footer(false);
            UI.select("Select", false);
            choice = in.nextLine().trim().toUpperCase();
            switch (choice) {
                case "1": DepartmentHead.displayArchivedUsers(); break;
                case "2": academicArchiveMenu(); break;
                case "3": facilitiesArchiveMenu(); break;
                case "4": scheduleArchiveMenu(); break;
                case "X": break;
                default: print("Invalid Choice");
            }
        } while (!choice.equalsIgnoreCase("X"));
    }
    private static void academicArchiveMenu() {
        String choice;
        do {
            header("ACADEMIC ARCHIVES", false);
            liner("  [1] Colleges", false);
            liner("  [2] Programs", false);
            liner("  [3] Courses", false);
            liner("  [4] Course Offerings", false);
            liner("  [5] Sections", false);
            liner("  [6] Curricula", false);
            liner("  [7] Course Registrations", false);
            footer(false);
            UI.select("Select", false);
            choice = in.nextLine().trim().toUpperCase();
            switch (choice) {
                case "1": College.displayArchivedColleges(); break;
                case "2": Program.displayArchivePrograms(); break;
                case "3": Course.displayArchiveCourses(); break;
                case "4": CourseOffering.displayArchivedOfferings(); break;
                case "5": Section.displayArchiveSections(); break;
                case "6": Curriculum.displayArchiveCurricula(); break;
                case "7": CourseRegistration.displayArchiveRegistrations(); break;
                case "X": break;
                default: print("Invalid Choice");
            }
        } while (!choice.equalsIgnoreCase("X"));
    }
    private static void facilitiesArchiveMenu() {
        String choice;
        do {
            header("FACILITY ARCHIVES", false);
            liner("  [1] Buildings", false);
            liner("  [2] Rooms", false);
            liner("  [3] Restore Building and Its Rooms", false);
            liner("  [4] Restore Individual Room", false);
            footer(false);
            UI.select("Select", false);
            choice = in.nextLine().trim().toUpperCase();
            switch (choice) {
                case "1": Building.displayArchiveBuildings(); break;
                case "2": Room.displayArchivedRooms(); break;
                case "3": Building.restoreBuilding(); break;
                case "4": Room.restoreRoom(); break;
                case "X": break;
                default: print("Invalid Choice");
            }
        } while (!choice.equalsIgnoreCase("X"));
    }
    private static void scheduleArchiveMenu() {
        String choice;
        do {
            header("SCHEDULE ARCHIVES", false);
            liner("  [1] Schedules", false);
            liner("  [2] Cancelled Schedules", false);
            liner("  [3] Make-up Schedules", false);
            footer(false);
            UI.select("Select", false);
            choice = in.nextLine().trim().toUpperCase();
            switch (choice) {
                case "1": Schedule.displayArchivedSchedules(); break;
                case "2": CancelledSchedule.displayArchivedCancellations(); break;
                case "3": MakeUpSchedule.displayArchiveMakeUpSchedules(); break;
                case "X": break;
                default: print("Invalid Choice");
            }
        } while (!choice.equalsIgnoreCase("X"));
    }


    // SETTER METHOD
    public void setCollegeCode(String collegeCode) {
        if (College.findCollege(collegeCode) == null) {
            UI.print("Invalid college code.");
            return;
        }
        this.collegeCode = collegeCode.trim();
    }

    // GETTER METHOD
    public String getCollegeCode() {
        return collegeCode;
    }

    // FIND METHOD
    public static DepartmentHead findDepartmentHead(String departmentHeadID) {
        if (departmentHeadID == null || departmentHeadID.trim().isEmpty()) return null;
        for (User user : User.users) {
            if (user instanceof DepartmentHead
                    && user.getUserID().equalsIgnoreCase(departmentHeadID.trim())) {
                return (DepartmentHead) user;
            }
        }

        return null;
    }

    // MANAGE METHODS
    private static int getManageChoice(String manageText) {
        String input;
        while (true) {
            manageUI(manageText);
            UI.select("Select", false);
            input = in.nextLine().trim();
            if (input.equalsIgnoreCase("X")) return 0;
            try {
                int choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= 4) return choice;
            } catch (NumberFormatException e) {
                // Continue prompting until a listed option or X is selected.
            }
            print("Invalid Choice");
        }
    }
    public static void manageUsers() {
        while (true) {
            switch (getManageChoice("Users")) {
                case 1:
                    createUser();
                    break;
                case 2:
                    editUser();
                    break;
                case 3:
                    archiveUser();
                    break;
                case 4:
                    displayUsers();
                    break;
                case 0:
                    return;
            }
        }
    }
    private static void manageBuildings() {
        while (true) {
            switch (getManageChoice("Buildings")) {
                case 1:
                    Building.createBuilding();
                    break;
                case 2:
                    Building.editBuilding();
                    break;
                case 3:
                    Building.archiveBuilding();
                    break;
                case 4:
                    Building.displayBuildings();
                    break;
                case 0:
                    return;
            }
        }
    }
    private void manageCancelledSchedules() {
        String choice;
        while (true) {
            header("MANAGE CANCELLED SCHEDULES", false);
            liner("  [1] Create Cancellation", false);
            liner("  [2] Archive Expired Cancellations", false);
            liner("  [3] View Cancellations", false);
            footer(false);
            UI.select("Select", false);
            choice = in.nextLine().trim().toUpperCase();
            switch (choice) {
                case "1": CancelledSchedule.createCancelledSchedule(this); break;
                case "2": CancelledSchedule.archiveCancelledSchedule(); break;
                case "3": CancelledSchedule.displayCancellations(); break;
                case "X": return;
                default: print("Invalid Choice");
            }
        }
    }
    private static void manageColleges() {
        while (true) {
            switch (getManageChoice("Colleges")) {
                case 1:
                    College.createCollege();
                    break;
                case 2:
                    College.editCollege();
                    break;
                case 3:
                    College.archiveCollege();
                    break;
                case 4:
                    College.displayColleges();
                    break;
                case 0:
                    return;
            }
        }
    }
    private static void manageCourses() {
        while (true) {
            switch (getManageChoice("Courses")) {
                case 1:
                    Course.createCourse();
                    break;
                case 2:
                    Course.editCourse();
                    break;
                case 3:
                    Course.archiveCourse();
                    break;
                case 4:
                    Course.displayCourses();
                    break;
                case 0:
                    return;
            }
        }
    }
    private static void manageCourseOfferings() {
        while (true) {
            switch (getManageChoice("Course Offerings")) {
                case 1:
                    CourseOffering.createCourseOffering();
                    break;
                case 2:
                    CourseOffering.editCourseOffering();
                    break;
                case 3:
                    CourseOffering.archiveCourseOffering();
                    break;
                case 4:
                    CourseOffering.displayOfferings();
                    break;
                case 0:
                    return;
            }
        }
    }
    private static void manageCourseRegistrations() {
        while (true) {
            switch (getManageChoice("Course Registrations")) {
                case 1:
                    CourseRegistration.createRegistration();
                    break;
                case 2:
                    CourseRegistration.editRegistration();
                    break;
                case 3:
                    CourseRegistration.archiveRegistration();
                    break;
                case 4:
                    CourseRegistration.displayRegistrations();
                    break;
                case 0:
                    return;
            }
        }
    }
    private static void manageCurricula() {
        while (true) {
            switch (getManageChoice("Curricula")) {
                case 1:
                    Curriculum.createCurriculum();
                    break;
                case 2:
                    Curriculum.editCurriculum();
                    break;
                case 3:
                    Curriculum.archiveCurriculum();
                    break;
                case 4:
                    Curriculum.displayCurricula();
                    break;
                case 0:
                    return;
            }
        }
    }
    private static void manageMakeUpSchedules() {
        String choice;
        while (true) {
            header("MANAGE MAKE-UP SCHEDULES", false);
            liner("  [1] Create Make-up Schedule", false);
            liner("  [2] Archive Make-up Schedule", false);
            liner("  [3] View Make-up Schedules", false);
            footer(false);
            UI.select("Select", false);
            choice = in.nextLine().trim().toUpperCase();
            switch (choice) {
                case "1": MakeUpSchedule.createMakeUpSchedule(); break;
                case "2": MakeUpSchedule.archiveMakeUpSchedule(); break;
                case "3": MakeUpSchedule.displayMakeUpSchedules(); break;
                case "X": return;
                default: print("Invalid Choice");
            }
        }
    }
    private static void managePrograms() {
        while (true) {
            switch (getManageChoice("Programs")) {
                case 1:
                    Program.createProgram();
                    break;
                case 2:
                    Program.editProgram();
                    break;
                case 3:
                    Program.archiveProgram();
                    break;
                case 4:
                    Program.displayPrograms();
                    break;
                case 0:
                    return;
            }
        }
    }
    private static void manageRooms() {
        while (true) {
            switch (getManageChoice("Rooms")) {
                case 1:
                    Room.createRoom();
                    break;
                case 2:
                    Room.editRoom();
                    break;
                case 3:
                    Room.archiveRoom();
                    break;
                case 4:
                    Room.displayRooms();
                    break;
                case 0:
                    return;
            }
        }
    }
    private static void manageSchedules() {
        while (true) {
            switch (getManageChoice("Schedules")) {
                case 1:
                    Schedule.createSchedule();
                    break;
                case 2:
                    Schedule.editSchedule();
                    break;
                case 3:
                    Schedule.archiveSchedule();
                    break;
                case 4:
                    Schedule.displaySchedules();
                    break;
                case 0:
                    return;
            }
        }
    }
    private static void manageSections() {
        while (true) {
            switch (getManageChoice("Sections")) {
                case 1:
                    Section.createSection();
                    break;
                case 2:
                    Section.editSection();
                    break;
                case 3:
                    Section.archiveSection();
                    break;
                case 4:
                    Section.displaySections();
                    break;
                case 0:
                    return;
            }
        }
    }

    // EDIT USERS
    private static void editUser() {
        String userID, lastName, firstName, middleInitial, emailAddress, password, address, birthDate, choice, userName;
        String enrollmentStatus = "", programCode = "", collegeCode = ""; String employmentStatus = "";
        int postalCode; char sex; boolean valid, submit = false;

        print("EDIT EXISTING USER");

        do {
            select("Enter User ID", false);
            userID = in.nextLine().trim().toUpperCase();

            if (userID.equalsIgnoreCase("X")) return;
            else if (userID.isEmpty()) print("User ID is required");
            else if (User.findUser(userID) == null) print("User does not exist");
        } while (User.findUser(userID) == null);

        User u = User.findUser(userID);

        if (u == null) {
            print("User not found");
            return;
        }

        userID = u.getUserID();
        lastName = u.getLastName();
        firstName = u.getFirstName();
        middleInitial = u.getMiddleInitial();
        emailAddress = u.getEmail();
        password = u.getPassword();
        address = u.getAddress();
        postalCode = u.getPostalCode();
        birthDate = u.getBirthDate();
        sex = u.getSex();

        switch (u) {
            case Student s -> {
                enrollmentStatus = s.getEnrollmentStatus();
                programCode = s.getProgramCode();
            }
            case Faculty f -> {
                collegeCode = f.getCollegeCode();
                employmentStatus = f.getEmploymentStatus();
            }
            case DepartmentHead dh -> collegeCode = dh.getCollegeCode();
            default -> {
                print("Invalid User Type");
                return;
            }
        }

        while (!submit) {
            if (u instanceof Student) {
                userManage("EDIT", userID, lastName, firstName, middleInitial,
                        emailAddress, password, address, postalCode, birthDate,
                        sex, enrollmentStatus, programCode, true);

                do {
                    select("Select", false);
                    choice = in.nextLine().trim().toUpperCase();
                    if (choice.equalsIgnoreCase("X")) return;
                    else if (!choice.matches("([1-9]|10|X|/)") || choice.isEmpty()) print("Invalid Input");
                } while (!choice.matches("([1-9]|10|X|/)"));

            }
            else if (u instanceof Faculty) {
                userManage("EDIT", userID, lastName, firstName, middleInitial,
                        emailAddress, password, address, postalCode, birthDate,
                        sex, collegeCode, employmentStatus, true);

                do {
                    UI.select("Select", false);
                    choice = in.nextLine().trim().toUpperCase();
                    if (choice.equalsIgnoreCase("X")) return;
                    else if (!choice.matches("([1-9]|10|X|/)") || choice.isEmpty()) UI.print("Invalid Input");
                } while (!choice.matches("([1-9]|10|X|/)"));
            }
            else {
                userManage("EDIT", userID, lastName, firstName, middleInitial,
                        emailAddress, password, address, postalCode, birthDate,
                        sex, collegeCode, true);

                do {
                    select("Select", false);
                    choice = in.nextLine().trim().toUpperCase();
                    if (choice.equalsIgnoreCase("X")) return;
                    else if (!choice.matches("([1-9]|X|/)") || choice.isEmpty()) print("Invalid Input");
                } while (!choice.matches("([1-9]|X|/)"));
            }

            switch (choice) {
                case "1":
                    do {
                        select("Enter Last Name", true);
                        lastName = in.nextLine().trim();
                        if (lastName.equalsIgnoreCase("X")) {
                            lastName = u.getLastName();
                            break;
                        }
                        else if (lastName.isEmpty()) print("Last Name is required");
                        else if (!lastName.matches("[A-Za-z][A-Za-z .'-]*")) UI.print("Enter a valid name.");
                    } while (!lastName.matches("[A-Za-z][A-Za-z .'-]*"));
                    break;

                case "2":
                    do {
                        select("Enter First Name", true);
                        firstName = in.nextLine().trim();
                        if (firstName.equalsIgnoreCase("X")) {
                            firstName = u.getFirstName();
                            break;
                        }
                        else if (firstName.isEmpty()) print("First Name is required");
                        else if (!firstName.matches("[A-Za-z][A-Za-z .'-]*")) UI.print("Enter a valid name.");
                    } while (!firstName.matches("[A-Za-z][A-Za-z .'-]*"));
                    break;

                case "3":
                    do {
                        System.out.print("   Enter Middle Initial > ");
                        middleInitial = in.nextLine().trim();

                        if (middleInitial.equalsIgnoreCase("X")) {
                            middleInitial = u.getMiddleInitial();
                            break;
                        }
                        if (middleInitial.endsWith("."))
                            middleInitial = middleInitial.substring(0, middleInitial.length() - 1);

                        if (!middleInitial.isEmpty() && !middleInitial.matches("[a-zA-Z]"))
                            UI.print("Enter one letter or leave the middle initial blank.");
                    } while (!middleInitial.isEmpty() && !middleInitial.matches("[a-zA-Z]"));
                    break;

                case "4":
                    valid = false;

                    do {
                        select("Enter Password", true);
                        password = in.nextLine();

                        if (password.equalsIgnoreCase("X")) {
                            password = u.getPassword();
                            break;
                        }
                        else if (password.trim().isEmpty())
                            print("Password is required");
                        else if (password.length() < 8)
                            print("Password must be at least 8 characters");
                        else if (!password.matches(".*[A-Z].*"))
                            print("Password must contain an uppercase letter");
                        else if (!password.matches(".*[a-z].*"))
                            print("Password must contain a lowercase letter");
                        else if (!password.matches(".*[0-9].*"))
                            print("Password must contain a number");
                        else if (!password.matches(".*[^a-zA-Z0-9].*"))
                            print("Password must contain a special character");
                        else if (password.contains(" "))
                            print("Password cannot contain spaces");
                        else
                            valid = true;
                    } while (!valid);
                    break;

                case "5":
                    String street, barangay, city, province;

                    do {
                        select(" Enter Street", true);
                        street = in.nextLine().trim();
                        if (street.equalsIgnoreCase("X")) return;
                        else if (street.isEmpty()) print("Street is required");
                        else if (!street.matches("^[a-zA-Z]+$")) UI.print("Special characters are not allowed");
                    } while (street.isEmpty() || !street.matches("^[a-zA-Z]+$"));

                    do {
                        select(" Enter Barangay", true);
                        barangay = in.nextLine().trim();
                        if (barangay.equalsIgnoreCase("X")) return;
                        else if (barangay.isEmpty()) print("Barangay is required");
                        else if (!barangay.matches("^[a-zA-Z]+$")) UI.print("Special characters are not allowed");
                    } while (barangay.isEmpty() || !barangay.matches("^[a-zA-Z]+$"));

                    do {
                        select(" Enter City", true);
                        city = in.nextLine().trim();
                        if (city.equalsIgnoreCase("X")) return;
                        else if (city.isEmpty()) print("City is required");
                        else if (!city.matches("^[a-zA-Z]+$")) UI.print("Special characters are not allowed");
                    } while (city.isEmpty() || !city.matches("^[a-zA-Z]+$"));

                    do {
                        select(" Enter Province", true);
                        province = in.nextLine().trim();
                        if (province.equalsIgnoreCase("X")) return;
                        else if (province.isEmpty()) print("Province is required");
                        else if (!province.matches("^[a-zA-Z]+$")) UI.print("Special characters are not allowed");
                    } while (province.isEmpty() || !province.matches("^[a-zA-Z]+$"));

                    address = street + ", " + barangay + ", " + city + ", " + province;
                    break;

                case "6":
                    String postalInput;
                    do {
                        select(" Enter Postal Code", true);
                        postalInput = in.nextLine().trim();

                        if (postalInput.equalsIgnoreCase("X")) {
                            postalCode = u.getPostalCode();
                            break;
                        }
                        else if (!postalInput.matches("[0-9]{4}")) print("Postal code must be 4 digits");
                        else postalCode = Integer.parseInt(postalInput);
                    } while (!postalInput.matches("[0-9]{4}"));
                    break;

                case "7":
                    do {
                        select(" Enter Birthdate (MM/DD/YYYY)", true);
                        birthDate = in.nextLine().trim();

                        if (birthDate.equalsIgnoreCase("X")) {
                            birthDate = u.getBirthDate();
                            break;
                        }
                        else if (!isValidBirthDate(birthDate)) print("Invalid birthdate");
                    } while (!isValidBirthDate(birthDate));
                    break;

                case "8":
                    String sexInput;

                    do {
                        select(" Enter Sex (M/F)", true);
                        sexInput = in.nextLine().trim().toUpperCase();

                        if (sexInput.equalsIgnoreCase("X")) {
                            sexInput = String.valueOf(u.getSex());
                            break;
                        }
                        else if (sexInput.isEmpty()) print("Sex is required");
                        else if (!sexInput.matches("[MF]")) print("Invalid Input");
                    } while (!sexInput.matches("[MF]"));

                    sex = sexInput.charAt(0);
                    break;

                case "9":
                    if (u instanceof Student) {
                        do {
                            select("Enrollment Status (R/IR/LOA)", true);
                            enrollmentStatus = in.nextLine().trim().toUpperCase();

                            if (enrollmentStatus.equalsIgnoreCase("X")) {
                                enrollmentStatus = ((Student) u).getEnrollmentStatus();
                                break;
                            }
                            else if (enrollmentStatus.isEmpty()) print("Enrollment Status is required");
                            else if (!enrollmentStatus.matches("^[a-zA-Z]+$"))
                                UI.print("Special characters are not allowed");
                            else if (!enrollmentStatus.matches("R|IR|LOA")) print("Invalid Input");
                        } while (!enrollmentStatus.matches("R|IR|LOA") || enrollmentStatus.isEmpty() ||
                                !enrollmentStatus.matches("^[a-zA-Z]+$"));

                    } else {
                        do {
                            select("College Code", true);
                            collegeCode = in.nextLine().trim().toUpperCase();

                            if (collegeCode.equalsIgnoreCase("X")) {
                                if (u instanceof Faculty) collegeCode = ((Faculty) u).getCollegeCode();
                                else collegeCode = ((DepartmentHead) u).getCollegeCode();
                                break;
                            }
                            else if (collegeCode.isEmpty()) UI.print("College Code is required");
                            else if (College.findCollege(collegeCode) == null) print("College Code does not exist");
                            else if (!collegeCode.matches("^[a-zA-Z]+$")) UI.print("Special characters are not allowed");
                        } while (College.findCollege(collegeCode) == null || !collegeCode.matches("^[a-zA-Z]+$"));
                    }
                    break;

                case "10":
                    if (u instanceof Student student) {
                        do {
                            select("Program Code", true);
                            programCode = in.nextLine().trim().toUpperCase();
                            if (programCode.equalsIgnoreCase("X")) {
                                programCode = student.getProgramCode();
                                break;
                            }
                            else if (programCode.isEmpty()) UI.print("Program Code is required.");
                            else if (Program.findProgram(programCode) == null)
                                UI.print("Program Code does not exist.");
                        } while (Program.findProgram(programCode) == null);
                    } else if (u instanceof Faculty faculty) {
                        do {
                            select("Employment Status", true);
                            employmentStatus = in.nextLine().trim().toUpperCase();
                            if (employmentStatus.equalsIgnoreCase("X")) {
                                employmentStatus = faculty.getEmploymentStatus();
                                break;
                            }
                            else if (!employmentStatus.matches("ACTIVE|INACTIVE"))
                                UI.print("Enter ACTIVE or INACTIVE.");
                        } while (!employmentStatus.matches("ACTIVE|INACTIVE"));
                    }
                    break;

                case "/":
                    userName = lastName + ", " + firstName + (middleInitial.isEmpty() ? "" : " " + middleInitial + ".");

                    if (isDuplicateName(userName) && !userName.equalsIgnoreCase(u.getUserName())) {
                        print("Name already exists");
                        break;
                    }

                    if (middleInitial != null && !middleInitial.isEmpty()
                            && !middleInitial.matches("[A-Za-z]")) {
                        print("Middle initial must be one letter or blank.");
                        break;
                    }
                    if (!isValidBirthDate(birthDate) || postalCode < 1000 || postalCode > 9999
                            || (sex != 'M' && sex != 'F')) {
                        print("One or more profile fields are invalid. No changes were saved.");
                        break;
                    }
                    if (u instanceof Student && (Program.findProgram(programCode) == null
                            || !enrollmentStatus.matches("R|IR|LOA"))) {
                        print("Invalid student program or enrollment status. No changes were saved.");
                        break;
                    }
                    if ((u instanceof Faculty || u instanceof DepartmentHead)
                            && College.findCollege(collegeCode) == null) {
                        print("Invalid college code. No changes were saved.");
                        break;
                    }
                    if (u instanceof Faculty && !employmentStatus.matches("ACTIVE|INACTIVE")) {
                        print("Invalid employment status. No changes were saved.");
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

                    if (u instanceof Student s) {
                        s.setEnrollmentStatus(enrollmentStatus);
                        s.setProgramCode(programCode);
                    } else if (u instanceof Faculty f) {
                        f.setCollegeCode(collegeCode);
                        f.setEmploymentStatus(employmentStatus);
                    } else {
                        DepartmentHead dh = (DepartmentHead) u;
                        dh.setCollegeCode(collegeCode);
                    }

                    print("User successfully edited");
                    User.viewProfile(u.getUserID(), true);
                    submit = true;
                    break;

                case "X":
                    print("User edit cancelled");
                    submit = true;
                    break;
            }
        }
    }

    // DISPLAYS STATISTICS
    private static void displaySystemSummary() {
        int students = 0, facultyCount = 0, activeFaculty = 0, inactiveFaculty = 0, departmentHeads = 0;
        int pending = 0, approved = 0, rejected = 0; int regular = 0; int irregular = 0; int loa = 0;

        for (User user : User.users) {
            if (user instanceof Student) {
                students++;
                String status = ((Student) user).getEnrollmentStatus();
                if (status != null && status.equalsIgnoreCase("R")) regular++;
                else if (status != null && status.equalsIgnoreCase("IR")) irregular++;
                else loa++;
            } else if (user instanceof Faculty) {
                facultyCount++;
                String status = ((Faculty) user).getEmploymentStatus();
                if (status != null && status.equalsIgnoreCase("ACTIVE")) activeFaculty++;
                else inactiveFaculty++;
            } else if (user instanceof DepartmentHead) {
                departmentHeads++;
            }
        }

        for (Request request : Request.requests) {
            if (request == null || request.getStatus() == null) continue;
            if (request.getStatus().equalsIgnoreCase("PENDING")) pending++;
            else if (request.getStatus().equalsIgnoreCase("APPROVED")) approved++;
            else if (request.getStatus().equalsIgnoreCase("REJECTED")) rejected++;
        }

        header("SYSTEM SUMMARY", false);
        liner(String.format("  %-29s : %d", "Students", students), false);
        liner(String.format("  %-29s : %d", "  Regular Students", regular), false);
        liner(String.format("  %-29s : %d", "  Irregular Students", irregular), false);
        liner(String.format("  %-29s : %d", "  Students on LOA", loa), false);
        liner(String.format("  %-29s : %d", "Faculty", facultyCount), false);
        liner(String.format("  %-29s : %d", "  Active Faculty", activeFaculty), false);
        liner(String.format("  %-29s : %d", "  Inactive Faculty", inactiveFaculty), false);
        liner(String.format("  %-29s : %d", "Department Heads", departmentHeads), false);
        separator(false);
        liner(String.format("  %-29s : %d", "Buildings", Building.buildings.size()), false);
        liner(String.format("  %-29s : %d", "Rooms", Room.rooms.size()), false);
        liner(String.format("  %-29s : %d", "Colleges", College.colleges.size()), false);
        liner(String.format("  %-29s : %d", "Programs", Program.programs.size()), false);
        liner(String.format("  %-29s : %d", "Courses", Course.courses.size()), false);
        liner(String.format("  %-29s : %d", "Course Offerings", CourseOffering.offerings.size()), false);
        liner(String.format("  %-29s : %d", "Sections", Section.sections.size()), false);
        liner(String.format("  %-29s : %d", "Course Registrations", CourseRegistration.registrations.size()), false);
        liner(String.format("  %-29s : %d", "Schedules", Schedule.schedules.size()), false);
        separator(false);
        liner(String.format("  %-29s : %d", "Pending Requests", pending), false);
        liner(String.format("  %-29s : %d", "Approved Requests", approved), false);
        liner(String.format("  %-29s : %d", "Rejected Requests", rejected), false);
        UI.footer(false);
        UI.returnButton();
    }

    private static void createUser() {
        int complete = 0, postalDigit = 0;
        boolean submit = false, valid;
        char gen = '\0';

        String user = "[1] Full Name:", email = "[2] Email Address:";
        String password = "[3] Password:", address = "[4] Address:";
        String postalCode = "[5] Postal Code:", birthDate = "[6] Birthdate:";
        String sex = "[7] Sex:";

        String userName = "", lastName = "", firstName = "", middleInitial = "";
        String emailAddress = "", passKey = "";
        String street = "", barangay = "", city = "", province = "", location = "", birthDay = "";

        while (!submit) {
            printSignUp("CREATE: USER", user, email, password, address, postalCode, birthDate, sex);

            UI.select("Select", false);
            String choice = in.nextLine().trim().toUpperCase();

            switch (choice) {
                case "1":
                    valid = false;
                    System.out.println("   Enter Full Name (Last Name/First Name/Middle Initial)");

                    do {
                        do {
                            System.out.print("    Last Name > ");
                            lastName = in.nextLine().trim();

                            if (!lastName.matches("[A-Za-z][A-Za-z .'-]*"))
                                print("Enter a valid name using letters, spaces, apostrophes, or hyphens.");
                        } while (!lastName.matches("[A-Za-z][A-Za-z .'-]*"));

                        do {
                            System.out.print("    First Name > ");
                            firstName = in.nextLine().trim();

                            if (!firstName.matches("[A-Za-z][A-Za-z .'-]*"))
                                print("Enter a valid name using letters, spaces, apostrophes, or hyphens.");
                        } while (!firstName.matches("[A-Za-z][A-Za-z .'-]*"));

                        do {
                            System.out.print("    Middle Initial (one letter or blank) > ");
                            middleInitial = in.nextLine().trim().toUpperCase();
                            if (middleInitial.endsWith("."))
                                middleInitial = middleInitial.substring(0, middleInitial.length() - 1);
                            if (!middleInitial.isEmpty() && !middleInitial.matches("[A-Z]"))
                                print("Enter one letter or leave the middle initial blank.");
                        } while (!middleInitial.isEmpty() && !middleInitial.matches("[A-Z]"));

                        userName = lastName + ", " + firstName
                                + (middleInitial.isEmpty() ? "" : " " + middleInitial + ".");

                        if (User.isDuplicateName(userName)) {
                            print("Name already exists");
                            firstName = "";
                            middleInitial = "";
                            lastName = "";
                        } else {
                            if (user.equals("[1] Full Name:")) complete++;
                            user = "[1] Full Name: " + userName;
                            valid = true;
                        }
                    } while (!valid);
                    break;

                case "2":
                    valid = false;

                    do {
                        System.out.print("   Enter Email Address > ");
                        emailAddress = in.nextLine().trim();

                        if (emailAddress.isEmpty()) {
                            print("Invalid Input");
                        } else if (!emailAddress.matches(
                                "(S[0-9]{4}|F[0-9]{4}|DH[0-9]{4})@bulsu\\.edu\\.ph")) {
                            print("Use the appropriate BulSU account format: S0001, F0001, or DH0001");
                        } else if (User.isDuplicateEmail(emailAddress)) {
                            print("Email already exists");
                        } else {
                            valid = true;
                        }
                    } while (!valid);

                    if (email.equals("[2] Email Address:")) complete++;
                    email = "[2] Email Address: " + emailAddress;
                    break;

                case "3":
                    valid = false;

                    do {
                        System.out.print("   Enter Password > ");
                        passKey = in.nextLine();

                        if (passKey.trim().isEmpty())
                            print("Invalid Input");
                        else if (passKey.length() < 8)
                            print("Password must be at least 8 characters");
                        else if (!passKey.matches(".*[A-Z].*"))
                            print("Password must contain an uppercase letter");
                        else if (!passKey.matches(".*[a-z].*"))
                            print("Password must contain a lowercase letter");
                        else if (!passKey.matches(".*[0-9].*"))
                            print("Password must contain a number");
                        else if (!passKey.matches(".*[^a-zA-Z0-9].*"))
                            print("Password must contain a special character");
                        else if (passKey.matches(".*\\s+.*"))
                            print("Password cannot contain whitespace");
                        else
                            valid = true;
                    } while (!valid);

                    if (password.equals("[3] Password:")) complete++;
                    password = "[3] Password: " + "*".repeat(passKey.length());
                    break;

                case "4":
                    System.out.println("   Enter Address (Street/Barangay/City/Province)");

                    do {
                        System.out.print("    Enter Street > ");
                        street = in.nextLine().trim();
                        if (street.isEmpty()) print("Invalid Input");
                    } while (street.isEmpty());

                    do {
                        System.out.print("    Enter Barangay > ");
                        barangay = in.nextLine().trim();
                        if (barangay.isEmpty()) print("Invalid Input");
                    } while (barangay.isEmpty());

                    do {
                        System.out.print("    Enter City > ");
                        city = in.nextLine().trim();
                        if (city.isEmpty()) print("Invalid Input");
                    } while (city.isEmpty());

                    do {
                        System.out.print("    Enter Province > ");
                        province = in.nextLine().trim();
                        if (province.isEmpty()) print("Invalid Input");
                    } while (province.isEmpty());

                    location = street + ", " + barangay + ", " + city + ", " + province;

                    if (address.equals("[4] Address:")) complete++;
                    address = "[4] Address: " + location;
                    break;

                case "5":
                    valid = false;

                    do {
                        System.out.print("   Enter Postal Code > ");
                        String postalInput = in.nextLine().trim();

                        if (!postalInput.matches("[0-9]{4}")) {
                            print("Postal code must contain exactly 4 digits");
                        } else {
                            postalDigit = Integer.parseInt(postalInput);
                            valid = true;
                        }
                    } while (!valid);

                    if (postalCode.equals("[5] Postal Code:")) complete++;
                    postalCode = "[5] Postal Code: " + String.format("%04d", postalDigit);
                    break;

                case "6":
                    valid = false;

                    DateTimeFormatter formatter = DateTimeFormatter
                            .ofPattern("MM/dd/uuuu")
                            .withResolverStyle(ResolverStyle.STRICT);

                    do {
                        System.out.print("   Enter Birthdate (MM/DD/YYYY) > ");
                        birthDay = in.nextLine().trim();

                        try {
                            LocalDate date = LocalDate.parse(birthDay, formatter);

                            if (date.getYear() < 1900) {
                                print("Birthdate year must be 1900 or later");
                            } else if (date.isAfter(LocalDate.now())) {
                                print("Birthdate cannot be in the future");
                            } else {
                                valid = true;
                            }
                        } catch (DateTimeParseException e) {
                            print("Invalid birthdate. Use a valid MM/DD/YYYY date");
                        }
                    } while (!valid);

                    if (birthDate.equals("[6] Birthdate:")) complete++;
                    birthDate = "[6] Birthdate: " + birthDay;
                    break;

                case "7":
                    String sexInput;

                    do {
                        System.out.print("   Enter Sex (M/F) > ");
                        sexInput = in.nextLine().trim().toUpperCase();

                        if (!sexInput.matches("[MF]"))
                            print("Invalid Input");
                    } while (!sexInput.matches("[MF]"));

                    gen = sexInput.charAt(0);

                    if (sex.equals("[7] Sex:")) complete++;
                    sex = "[7] Sex: " + gen;
                    break;

                case "/":
                    if (complete != 7) {
                        print("Incomplete Details!");
                        break;
                    }

                    String accountType = emailAddress.substring(0, emailAddress.indexOf('@'));

                    try {
                        if (accountType.startsWith("DH")) {
                            String collegeCode;

                            do {
                                System.out.print("   College Code > ");
                                collegeCode = in.nextLine().trim().toUpperCase();

                                if (College.findCollege(collegeCode) == null)
                                    print("College code does not exist");
                            } while (College.findCollege(collegeCode) == null);

                            User.users.add(new DepartmentHead(
                                    lastName, firstName, middleInitial,
                                    emailAddress, passKey, location,
                                    postalDigit, birthDay, gen, collegeCode));

                        } else if (accountType.startsWith("F")) {
                            String collegeCode, employmentStatus;

                            do {
                                System.out.print("   College Code > ");
                                collegeCode = in.nextLine().trim().toUpperCase();

                                if (College.findCollege(collegeCode) == null)
                                    print("College code does not exist");
                            } while (College.findCollege(collegeCode) == null);

                            do {
                                System.out.print("   Employment Status > ");
                                employmentStatus = in.nextLine().trim().toUpperCase();

                                if (!employmentStatus.matches("ACTIVE|INACTIVE"))
                                    print("Enter ACTIVE or INACTIVE.");
                            } while (!employmentStatus.matches("ACTIVE|INACTIVE"));

                            User.users.add(new Faculty(
                                    lastName, firstName, middleInitial,
                                    emailAddress, passKey, location,
                                    postalDigit, birthDay, gen,
                                    collegeCode, employmentStatus));

                        } else if (accountType.startsWith("S")) {
                            String enrollmentStatus, programCode;

                            do {
                                System.out.print("   Enrollment Status (R, IR) > ");
                                enrollmentStatus = in.nextLine().trim().toUpperCase();

                                if (!enrollmentStatus.matches("R|IR"))
                                    print("Enter R or IR");
                            } while (!enrollmentStatus.matches("R|IR"));

                            do {
                                System.out.print("   Program Code > ");
                                programCode = in.nextLine().trim().toUpperCase();

                                if (Program.findProgram(programCode) == null)
                                    print("Program Code does not exist");
                            } while (Program.findProgram(programCode) == null);

                            User.users.add(new Student(
                                    lastName, firstName, middleInitial,
                                    emailAddress, passKey, location,
                                    postalDigit, birthDay, gen,
                                    enrollmentStatus, programCode));
                        }

                        print("SIGN UP SUCCESSFUL! Welcome to ClassNav.");
                        submit = true;
                    } catch (IllegalArgumentException e) {
                        print("Unable to create account: " + e.getMessage());
                    }
                    break;

                case "X":
                    submit = true;
                    break;

                default:
                    print("Invalid Choice");
                    break;
            }
        }
    }

    private static void printSignUp(String text, String user, String email,
                                    String password, String address, String postalCode,
                                    String birthDate, String sex) {

        header(text, false);
        liner(" " + user, false);
        liner(" " + email, false);
        liner(" " + password, false);
        liner(" " + address, false);
        liner(" " + postalCode, false);
        liner(" " + birthDate, false);
        liner(" " + sex, false);
        option();
        footer(false);
    }
}