package USER;
import DATA.*;
import FUNCTION.*;
import UI.*;

import static UI.UI.in;

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

    @Override
    public void menu() {
        String select;
        boolean valid;

        do {
            super.menu();
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
                MakeUpSchedule.manageMakeUpSchedules();
                menu();
                break;

            case "X":
                User.start();
                break;
        }
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
                User.editUser();
                break;
            case 3:
                User.deleteUser();
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
                Course.deleteCourse();
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
                    CourseRegistration.deleteRegistration();
                    break;

                case 4:
                    CourseRegistration.displayRegistrations();
                    break;

                case 0:
                    return;
            }

        } while (true);
    }
    public static void manageCurricula() {
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
                Curriculum.deleteCurriculum();
                break;
            case 4:
                Curriculum.displayCurriculums();
                break;
            case 0:
                // menu
                break;
        }
    }
    public static void manageMakeUpSchedules() {
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
                MakeUpSchedule.editMakeUpSchedule();
                break;
            case 3:
                MakeUpSchedule.deleteMakeUpSchedule();
                break;
            case 4:
                MakeUpSchedule.displayMakeUpSchedules();
                break;
            case 0:
                // Return to previous menu
                break;
        }
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
    public static void manageRooms() {
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
                Room.displayRooms();
                break;
            case 3:
                Room.editRoom();
                break;
            case 4:
                Room.deleteRoom();
                break;
            case 0:
                return;
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
                Schedule.createSchedule();
                break;
            case 2:
                Schedule.editSchedule();
                break;
            case 3:
                Schedule.deleteSchedule();
                break;
            case 4:
                Schedule.displaySchedules();
                break;
            case 0:
                return;
        }
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
            case 1: Section.createSection(); break;
            case 2: Section.editSection(); break;
            case 3: Section.archiveSection(); break;
            case 4: Section.displaySections(); break;
            case 0:
                break;
        }
    }

    // ------------------------------------------------------------//
    static void backUpData(){
        //BackUp.backup;
    }
}