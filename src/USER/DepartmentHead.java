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
            UI.header("CLASSNAV | DEPARTMENT HEAD");
            UI.liner("  OVERVIEW AND ACCOUNT");
            UI.liner("  [1] View System Summary");
            UI.liner("  [2] View My Profile");
            UI.liner("  [3] Edit My Profile");
            UI.separator();
            UI.liner("  REQUESTS AND APPROVALS");
            UI.liner("  [4] View Faculty Requests");
            UI.liner("  [5] Review Pending Requests");
            UI.separator();
            UI.liner("  RECORD MANAGEMENT");
            UI.liner("  [6] Academic Management");
            UI.liner("  [7] Buildings and Rooms");
            UI.liner("  [8] Schedules and Exceptions");
            UI.liner("  [9] Notifications");
            UI.liner("  [A] Archived Records");
            UI.separator();
            UI.liner("  [X] Log Out");
            UI.footer();

            System.out.print("  Select > ");
            select = in.nextLine().trim().toUpperCase();

            switch (select) {
                case "1":
                    displaySystemSummary();
                    break;
                case "2":
                    User.viewProfile(getUserID());
                    break;
                case "3":
                    User.editUser(getUserID());
                    break;
                case "4":
                    Request.viewRequest(getUserID());
                    break;
                case "5":
                    Request.viewRequests(getUserID());
                    break;
                case "6":
                    academicManageMenu();
                    break;
                case "7":
                    facilitiesManageMenu();
                    break;
                case "8":
                    scheduleManageMenu();
                    break;
                case "9":
                    notificationMenu();
                    break;
                case "A":
                    archiveMenu();
                    break;
                case "X":
                    UI.print("Exiting...");
                    return;
                default:
                    UI.print("Invalid Choice");
            }
        }
    }

    private static void academicManageMenu() {
        String choice;
        do {
            UI.header("ACADEMIC MANAGEMENT");
            UI.liner("  [1] Manage Users");
            UI.liner("  [2] Manage Colleges");
            UI.liner("  [3] Manage Programs");
            UI.liner("  [4] Manage Courses");
            UI.liner("  [5] Manage Course Offerings");
            UI.liner("  [6] Manage Sections");
            UI.liner("  [7] Manage Curricula");
            UI.liner("  [8] Manage Course Registrations");
            UI.liner("  [0] Back");
            UI.footer();
            System.out.print("  Select > ");
            choice = in.nextLine().trim();

            switch (choice) {
                case "1": manageUsers(); break;
                case "2": manageColleges(); break;
                case "3": managePrograms(); break;
                case "4": manageCourses(); break;
                case "5": manageCourseOfferings(); break;
                case "6": manageSections(); break;
                case "7": manageCurricula(); break;
                case "8": manageCourseRegistrations(); break;
                case "0": break;
                default: UI.print("Invalid Choice");
            }
        } while (!choice.equals("0"));
    }
    private static void facilitiesManageMenu() {
        String choice;
        do {
            UI.header("BUILDINGS AND ROOMS");
            UI.liner("  [1] Manage Buildings");
            UI.liner("  [2] Manage Rooms");
            UI.liner("  [0] Back");
            UI.footer();
            System.out.print("  Select > ");
            choice = in.nextLine().trim();

            switch (choice) {
                case "1": manageBuildings(); break;
                case "2": manageRooms(); break;
                case "0": break;
                default: UI.print("Invalid Choice");
            }
        } while (!choice.equals("0"));
    }
    private void scheduleManageMenu() {
        String choice;
        do {
            UI.header("SCHEDULES AND EXCEPTIONS");
            UI.liner("  [1] Manage Schedules");
            UI.liner("  [2] Manage Cancelled Schedules");
            UI.liner("  [3] Manage Make-up Schedules");
            UI.liner("  [0] Back");
            UI.footer();
            System.out.print("  Select > ");
            choice = in.nextLine().trim();

            switch (choice) {
                case "1": manageSchedules(); break;
                case "2": manageCancelledSchedules(); break;
                case "3": manageMakeUpSchedules(); break;
                case "0": break;
                default: UI.print("Invalid Choice");
            }
        } while (!choice.equals("0"));
    }
    private static void notificationMenu() {
        String choice;
        do {
            UI.header("NOTIFICATIONS");
            UI.liner("  [1] View All Notifications");
            UI.liner("  [2] Archive a Notification");
            UI.liner("  [3] View Archived Notifications");
            UI.liner("  [0] Back");
            UI.footer();
            System.out.print("  Select > ");
            choice = in.nextLine().trim();

            switch (choice) {
                case "1": Notification.displayNotifications(); break;
                case "2": Notification.archiveNotifications(); break;
                case "3": Notification.displayArchiveNotifications(); break;
                case "0": break;
                default: UI.print("Invalid Choice");
            }
        } while (!choice.equals("0"));
    }
    private static void archiveMenu() {
        String choice;
        do {
            UI.header("ARCHIVED RECORDS");
            UI.liner("  [1] Archived Users");
            UI.liner("  [2] Academic Record Archives");
            UI.liner("  [3] Building and Room Archives");
            UI.liner("  [4] Schedule and Exception Archives");
            UI.liner("  [5] Archived Notifications");
            UI.liner("  [0] Back");
            UI.footer();
            System.out.print("  Select > ");
            choice = in.nextLine().trim();

            switch (choice) {
                case "1": User.displayArchivedUsers(); break;
                case "2": academicArchiveMenu(); break;
                case "3": facilitiesArchiveMenu(); break;
                case "4": scheduleArchiveMenu(); break;
                case "5": Notification.displayArchiveNotifications(); break;
                case "0": break;
                default: UI.print("Invalid Choice");
            }
        } while (!choice.equals("0"));
    }
    private static void academicArchiveMenu() {
        String choice;
        do {
            UI.header("ACADEMIC ARCHIVES");
            UI.liner("  [1] Colleges");
            UI.liner("  [2] Programs");
            UI.liner("  [3] Courses");
            UI.liner("  [4] Course Offerings");
            UI.liner("  [5] Sections");
            UI.liner("  [6] Curricula");
            UI.liner("  [7] Course Registrations");
            UI.liner("  [0] Back");
            UI.footer();
            System.out.print("  Select > ");
            choice = in.nextLine().trim();

            switch (choice) {
                case "1": College.displayArchivedColleges(); break;
                case "2": Program.displayArchivePrograms(); break;
                case "3": Course.displayArchiveCourses(); break;
                case "4": CourseOffering.displayArchivedOfferings(); break;
                case "5": Section.displayArchiveSections(); break;
                case "6": Curriculum.displayArchiveCurricula(); break;
                case "7": CourseRegistration.displayArchiveRegistrations(); break;
                case "0": break;
                default: UI.print("Invalid Choice");
            }
        } while (!choice.equals("0"));
    }
    private static void facilitiesArchiveMenu() {
        String choice;
        do {
            UI.header("FACILITY ARCHIVES");
            UI.liner("  [1] Buildings");
            UI.liner("  [2] Rooms");
            UI.liner("  [0] Back");
            UI.footer();
            System.out.print("  Select > ");
            choice = in.nextLine().trim();

            switch (choice) {
                case "1": Building.displayArchiveBuildings(); break;
                case "2": Room.displayArchivedRooms(); break;
                case "0": break;
                default: UI.print("Invalid Choice");
            }
        } while (!choice.equals("0"));
    }
    private static void scheduleArchiveMenu() {
        String choice;
        do {
            UI.header("SCHEDULE ARCHIVES");
            UI.liner("  [1] Schedules");
            UI.liner("  [2] Cancelled Schedules");
            UI.liner("  [3] Make-up Schedules");
            UI.liner("  [0] Back");
            UI.footer();
            System.out.print("  Select > ");
            choice = in.nextLine().trim();

            switch (choice) {
                case "1": Schedule.displayArchivedSchedules(); break;
                case "2": CancelledSchedule.displayArchivedCancellations(); break;
                case "3": MakeUpSchedule.displayArchiveMakeUpSchedules(); break;
                case "0": break;
                default: UI.print("Invalid Choice");
            }
        } while (!choice.equals("0"));
    }


    // SETTER METHOD
    public void setCollegeCode(String collegeCode) {
        this.collegeCode = collegeCode;
    }

    // GETTER METHOD
    public String getCollegeCode() {
        return collegeCode;
    }

    // FIND METHOD
    public static DepartmentHead findDepartmentHead(String departmentHeadID) {
        for (User user : User.users) {
            if (user instanceof DepartmentHead
                    && user.getUserID().equals(departmentHeadID)) {
                return (DepartmentHead) user;
            }
        }

        return null;
    }

    // MANAGE METHODS
    private static int getManageChoice(String manageText) {
        int choice = -1;
        String input;

        do {
            UI.manageUI(manageText);
            System.out.print("  Select > ");
            input = in.nextLine().trim();

            try {
                choice = Integer.parseInt(input);
                if (choice < 0 || choice > 4) {
                    UI.print("Invalid Choice");
                    choice = -1;
                }
            } catch (NumberFormatException e) {
                UI.print("Invalid Choice");
                choice = -1;
            }
        } while (choice == -1);

        return choice;
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
                    User.archiveUser();
                    break;
                case 4:
                    User.displayUsers();
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
            UI.header("MANAGE CANCELLED SCHEDULES");
            UI.liner("  [1] Create Cancellation");
            UI.liner("  [2] Archive Expired Cancellations");
            UI.liner("  [3] View Cancellations");
            UI.liner("  [0] Back");
            UI.footer();
            System.out.print("  Select > ");
            choice = in.nextLine().trim();

            switch (choice) {
                case "1":
                    CancelledSchedule.createCancelledSchedule(this);
                    break;
                case "2":
                    CancelledSchedule.archiveCancelledSchedule();
                    break;
                case "3":
                    CancelledSchedule.displayCancellations();
                    break;
                case "0":
                    return;
                default:
                    UI.print("Invalid Choice");
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
            UI.header("MANAGE MAKE-UP SCHEDULES");
            UI.liner("  [1] Create Make-up Schedule");
            UI.liner("  [2] Archive Make-up Schedule");
            UI.liner("  [3] View Make-up Schedules");
            UI.liner("  [0] Back");
            UI.footer();
            System.out.print("  Select > ");
            choice = in.nextLine().trim();

            switch (choice) {
                case "1":
                    MakeUpSchedule.createMakeUpSchedule();
                    break;
                case "2":
                    MakeUpSchedule.archiveMakeUpSchedule();
                    break;
                case "3":
                    MakeUpSchedule.displayMakeUpSchedules();
                    break;
                case "0":
                    return;
                default:
                    UI.print("Invalid Choice");
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

            if (User.findUser(userID) == null)
                UI.print("User does not exist");
        } while (User.findUser(userID) == null);

        User u = User.findUser(userID);

        if (u == null) {
            UI.print("User not found");
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
            } else {
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
                        } else if (User.isDuplicateEmail(emailAddress)
                                && !emailAddress.equalsIgnoreCase(
                                u.getEmail())) {
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

                        if (!User.isValidBirthDate(birthDate))
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
                            if (Program.findProgram(programCode) == null)
                                UI.print("Program Code does not exist");
                        } while (Program.findProgram(programCode) == null);

                    } else {
                        do {
                            System.out.print("   College Code > ");
                            collegeCode = in.nextLine().trim().toUpperCase();

                            if (College.findCollege(collegeCode) == null)
                                UI.print("College Code does not exist");
                        } while (College.findCollege(collegeCode) == null);
                    }
                    break;

                case "/":
                    userName = lastName + ", " + firstName
                            + (middleInitial.isEmpty() ? "" : " " + middleInitial + ".");

                    if (isDuplicateName(userName)
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
                        } else {
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

    // DISPLAYS STATISTICS
    private static void displaySystemSummary() {
        int students = 0, facultyCount = 0, activeFaculty = 0, inactiveFaculty = 0, departmentHeads = 0;
        int pending = 0, approved = 0, rejected = 0;

        for (User user : User.users) {
            if (user instanceof Student) {
                students++;
            } else if (user instanceof Faculty) {
                facultyCount++;
                String status = Faculty.getEmploymentStatus(user);
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

        UI.header("SYSTEM SUMMARY");
        UI.liner(String.format("  %-29s : %d", "Students", students));
        UI.liner(String.format("  %-29s : %d", "Faculty", facultyCount));
        UI.liner(String.format("  %-29s : %d", "  Active Faculty", activeFaculty));
        UI.liner(String.format("  %-29s : %d", "  Inactive Faculty", inactiveFaculty));
        UI.liner(String.format("  %-29s : %d", "Department Heads", departmentHeads));
        UI.separator();
        UI.liner(String.format("  %-29s : %d", "Buildings", Building.buildings.size()));
        UI.liner(String.format("  %-29s : %d", "Rooms", Room.rooms.size()));
        UI.liner(String.format("  %-29s : %d", "Colleges", College.colleges.size()));
        UI.liner(String.format("  %-29s : %d", "Programs", Program.programs.size()));
        UI.liner(String.format("  %-29s : %d", "Courses", Course.courses.size()));
        UI.liner(String.format("  %-29s : %d", "Course Offerings", CourseOffering.offerings.size()));
        UI.liner(String.format("  %-29s : %d", "Sections", Section.sections.size()));
        UI.liner(String.format("  %-29s : %d", "Course Registrations", CourseRegistration.registrations.size()));
        UI.liner(String.format("  %-29s : %d", "Schedules", Schedule.schedules.size()));
        UI.separator();
        UI.liner(String.format("  %-29s : %d", "Pending Requests", pending));
        UI.liner(String.format("  %-29s : %d", "Approved Requests", approved));
        UI.liner(String.format("  %-29s : %d", "Rejected Requests", rejected));
        UI.footer();
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

            System.out.print("  Select > ");
            String choice = in.nextLine().trim().toUpperCase();

            switch (choice) {
                case "1":
                    valid = false;
                    System.out.println("   Enter Full Name (Last Name/First Name/Middle Initial)");

                    do {
                        do {
                            System.out.print("    Last Name > ");
                            lastName = in.nextLine().trim();

                            if (lastName.isEmpty())
                                UI.print("Invalid Input");
                        } while (lastName.isEmpty());

                        do {
                            System.out.print("    First Name > ");
                            firstName = in.nextLine().trim();

                            if (firstName.isEmpty())
                                UI.print("Invalid Input");
                        } while (firstName.isEmpty());

                        System.out.print("    Middle Initial > ");
                        middleInitial = in.nextLine().trim().toUpperCase();

                        if (middleInitial.endsWith("."))
                            middleInitial = middleInitial.substring(0, middleInitial.length() - 1);

                        userName = lastName + ", " + firstName
                                + (middleInitial.isEmpty() ? "" : " " + middleInitial + ".");

                        if (User.isDuplicateName(userName)) {
                            UI.print("Name already exists");
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
                            UI.print("Invalid Input");
                        } else if (!emailAddress.matches(
                                "(S[0-9]{4}|F[0-9]{4}|DH[0-9]{4})@bulsu\\.edu\\.ph")) {
                            UI.print("Use the appropriate BulSU account format: S0001, F0001, or DH0001");
                        } else if (User.isDuplicateEmail(emailAddress)) {
                            UI.print("Email already exists");
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
                            UI.print("Invalid Input");
                        else if (passKey.length() < 8)
                            UI.print("Password must be at least 8 characters");
                        else if (!passKey.matches(".*[A-Z].*"))
                            UI.print("Password must contain an uppercase letter");
                        else if (!passKey.matches(".*[a-z].*"))
                            UI.print("Password must contain a lowercase letter");
                        else if (!passKey.matches(".*[0-9].*"))
                            UI.print("Password must contain a number");
                        else if (!passKey.matches(".*[^a-zA-Z0-9].*"))
                            UI.print("Password must contain a special character");
                        else if (passKey.matches(".*\\s+.*"))
                            UI.print("Password cannot contain whitespace");
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
                        if (street.isEmpty()) UI.print("Invalid Input");
                    } while (street.isEmpty());

                    do {
                        System.out.print("    Enter Barangay > ");
                        barangay = in.nextLine().trim();
                        if (barangay.isEmpty()) UI.print("Invalid Input");
                    } while (barangay.isEmpty());

                    do {
                        System.out.print("    Enter City > ");
                        city = in.nextLine().trim();
                        if (city.isEmpty()) UI.print("Invalid Input");
                    } while (city.isEmpty());

                    do {
                        System.out.print("    Enter Province > ");
                        province = in.nextLine().trim();
                        if (province.isEmpty()) UI.print("Invalid Input");
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
                            UI.print("Postal code must contain exactly 4 digits");
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

                            if (date.isAfter(LocalDate.now())) {
                                UI.print("Birthdate cannot be in the future");
                            } else {
                                valid = true;
                            }
                        } catch (DateTimeParseException e) {
                            UI.print("Invalid birthdate. Use a valid MM/DD/YYYY date");
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
                            UI.print("Invalid Input");
                    } while (!sexInput.matches("[MF]"));

                    gen = sexInput.charAt(0);

                    if (sex.equals("[7] Sex:")) complete++;
                    sex = "[7] Sex: " + gen;
                    break;

                case "/":
                    if (complete != 7) {
                        UI.print("Incomplete Details!");
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
                                    UI.print("College code does not exist");
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
                                    UI.print("College code does not exist");
                            } while (College.findCollege(collegeCode) == null);

                            do {
                                System.out.print("   Employment Status > ");
                                employmentStatus = in.nextLine().trim().toUpperCase();

                                if (!employmentStatus.matches("[A-Z ]+"))
                                    UI.print("Invalid employment status");
                            } while (!employmentStatus.matches("[A-Z ]+"));

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
                                    UI.print("Enter R or IR");
                            } while (!enrollmentStatus.matches("R|IR"));

                            do {
                                System.out.print("   Program Code > ");
                                programCode = in.nextLine().trim().toUpperCase();

                                if (Program.findProgram(programCode) == null)
                                    UI.print("Program Code does not exist");
                            } while (Program.findProgram(programCode) == null);

                            User.users.add(new Student(
                                    lastName, firstName, middleInitial,
                                    emailAddress, passKey, location,
                                    postalDigit, birthDay, gen,
                                    enrollmentStatus, programCode));
                        }

                        UI.print("SIGN UP SUCCESSFUL! Welcome to ClassNav.");
                        submit = true;
                    } catch (IllegalArgumentException e) {
                        UI.print("Unable to create account: " + e.getMessage());
                    }
                    break;

                case "X":
                    submit = true;
                    break;

                default:
                    UI.print("Invalid Choice");
                    break;
            }
        }
    }

    private static void printSignUp(String text, String user, String email,
                                    String password, String address, String postalCode,
                                    String birthDate, String sex) {

        UI.header(text);
        UI.liner(" " + user);
        UI.liner(" " + email);
        UI.liner(" " + password);
        UI.liner(" " + address);
        UI.liner(" " + postalCode);
        UI.liner(" " + birthDate);
        UI.liner(" " + sex);
        UI.option();
        UI.footer();
    }
}