package USER;
import DATA.*;
import FUNCTION.*;
import UI.*;
import static UI.UI.*;

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
        boolean valid;

        do {
            UI.liner(" [3] View Faculty Requests");
            UI.liner(" [4] Review Requests");
            UI.liner(" [5] Manage Users");
            UI.liner(" [6] Manage Programs");
            UI.liner(" [7] Manage Sections");
            UI.liner(" [8] Manage Rooms");
            UI.liner(" [9] Manage Schedules");
            UI.liner(" [10] Manage Colleges");
            UI.liner(" [11] Manage Courses");
            UI.liner(" [12] Manage Course Offerings");
            UI.liner(" [13] Manage Curricula");
            UI.liner(" [14] Manage Course Registrations");
            UI.liner(" [15] Manage Buildings");
            UI.liner(" [16] Manage Cancelled Schedules");
            UI.liner(" [17] Manage Make-Up Schedules");
            UI.liner("");
            UI.liner(" [X] Log out");
            UI.footer();

            System.out.print("  Select > ");
            select = in.nextLine().trim().toUpperCase();

            valid = select.matches("([1-9]|1[0-7]|X)");

            if (!valid)
                UI.print("Invalid Choice");

        } while (!valid);

        switch (select) {
            case "1":
                User.viewProfile(getUserID());
                menu();
                break;

            case "2":
                User.editUser(getUserID());
                menu();
                break;

            case "3":
                Request.viewRequest(getUserID());
                menu();
                break;

            case "4":
                Request.viewRequests(getUserID());
                menu();
                break;

            case "5":
                manageUsers();
                menu();
                break;

            case "6":
                managePrograms();
                menu();
                break;

            case "7":
                manageSections();
                menu();
                break;

            case "8":
                manageRooms();
                menu();
                break;

            case "9":
                manageSchedules();
                menu();
                break;

            case "10":
                manageColleges();
                menu();
                break;

            case "11":
                manageCourses();
                menu();
                break;

            case "12":
                manageCourseOfferings();
                menu();
                break;

            case "13":
                manageCurricula();
                menu();
                break;

            case "14":
                manageCourseRegistrations();
                menu();
                break;

            case "15":
                manageBuildings();
                menu();
                break;

            case "16":
                manageCancelledSchedules();
                menu();
                break;

            case "17":
                manageMakeUpSchedules();
                menu();
                break;

            case "X":
                System.out.println("  Exiting ...");
                System.exit(0);
                break;
        }
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
                editUser();
                break;
            case 3:
                User.archiveUser();
                break;
            case 4:
                User.displayUsers();
                break;
            case 0:
                // menu
        }
    }
    private static void manageBuildings() {
        int choice = -1;
        String input;

        do {
            UI.manageUI("Buildings");

            System.out.print("  Select > ");
            input = in.nextLine().trim();

            try {
                choice = Integer.parseInt(input);

                if (!(choice >= 1 && choice <= 4) && choice != 0)
                    UI.print("Invalid Input");

            } catch (NumberFormatException e) {
                UI.print("Invalid Input");
                choice = -1;
            }

            if (choice == 1)
                Building.createBuilding();
            else if (choice == 2)
                Building.editBuilding();
            else if (choice == 3)
                Building.archiveBuilding();
            else if (choice == 4)
                Building.displayBuildings();

        } while (choice != 0);
    }
    private static void manageCancelledSchedules() {
        int choice = -1;
        String input = "";

        UI.manageUI("Cancelled Schedules");

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
                CancelledSchedule.createCancelledSchedule();
                break;
            case 2:
//                CancelledSchedule.editCancelledSchedule();
                break;
            case 3:
                CancelledSchedule.archiveCancelledSchedule();
                break;
            case 4:
                CancelledSchedule.displayCancellations();
                break;
            case 0:
                // Return to previous menu
                break;
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
                // Return to previous menu
                break;
        }
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
                // Return to previous menu
                break;
        }
    }
    private static void manageCourseOfferings() {
        int choice = -1; String input = "";
        UI.manageUI("Course Offering");

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
                // menu
                break;
        }
    }
    private static void manageCourseRegistrations() {

        int choice = -1;
        String input;

        do {
            UI.manageUI("Course Registration");
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

            if (choice == -1)
                continue;

            switch (choice) {
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

        } while (true);
    }
    private static void manageCurricula() {
        int choice = -1; String input = "";
        UI.manageUI("Curriculum");

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
                // menu
                break;
        }
    }
    private static void manageMakeUpSchedules() {
        int choice = -1;
        String input = "";

        UI.manageUI("Room Requests");

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
                MakeUpSchedule.createMakeUpSchedule();
                break;
            case 2:
                // wala na
                break;
            case 3:
                MakeUpSchedule.archiveMakeUpSchedule();
                break;
            case 4:
                MakeUpSchedule.displayMakeUpSchedules();
                break;
            case 0:
                // Return to previous menu
                break;
        }
    }
    private static void managePrograms() {
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
    private static void manageRooms() {
        int choice = -1;
        String input;

        do {
            UI.manageUI("Rooms");
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
    private static void manageSchedules() {
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
    private static void manageSections() {
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
            case 1: Section.createSection(); break;
            case 2: Section.editSection(); break;
            case 3: Section.archiveSection(); break;
            case 4: Section.displaySections(); break;
            case 0:
                break;
        }
    }

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

                    } else if (u instanceof Faculty || u instanceof DepartmentHead) {
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
}