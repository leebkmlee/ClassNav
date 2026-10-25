package DATA;

import UI.*;
import USER.*;

import static UI.UI.in;

import java.time.LocalDate;
import java.util.ArrayList;

public class CourseRegistration {
    // FIELDS
    private static int nextRegistrationID = 1;

    private final String registrationID, registrationDate;
    private String studentID, offeringID, status;

    public static ArrayList<CourseRegistration> registrations = new ArrayList<>();
    public static ArrayList<CourseRegistration> archiveRegistrations = new ArrayList<>();

    // CONSTRUCTOR
    public CourseRegistration(String studentID, String offeringID, String status) {
        this.registrationID = String.format("CR%04d", nextRegistrationID++);
        this.studentID = studentID;
        this.offeringID = offeringID;
        this.registrationDate = LocalDate.now().toString();
        this.status = status;
    }

    // DUMMY DATA
    static {
        registrations.add(new CourseRegistration("S0001", "COF001", "Enrolled"));
        registrations.add(new CourseRegistration("S0001", "COF002", "Enrolled"));
        registrations.add(new CourseRegistration("S0001", "COF003", "Enrolled"));
        registrations.add(new CourseRegistration("S0004", "COF004", "Enrolled"));
        registrations.add(new CourseRegistration("S0005", "COF005", "Enrolled"));
        registrations.add(new CourseRegistration("S0006", "COF006", "Enrolled"));
        registrations.add(new CourseRegistration("S0007", "COF007", "Enrolled"));
        registrations.add(new CourseRegistration("S0008", "COF008", "Enrolled"));
        registrations.add(new CourseRegistration("S0009", "COF009", "Enrolled"));
        registrations.add(new CourseRegistration("S0010", "COF010", "Enrolled"));
        registrations.add(new CourseRegistration("S0011", "COF011", "Enrolled"));
        registrations.add(new CourseRegistration("S0012", "COF012", "Enrolled"));
        registrations.add(new CourseRegistration("S0013", "COF013", "Enrolled"));
        registrations.add(new CourseRegistration("S0014", "COF014", "Enrolled"));
        registrations.add(new CourseRegistration("S0015", "COF015", "Enrolled"));
        registrations.add(new CourseRegistration("S0002", "COF001", "Enrolled"));
        registrations.add(new CourseRegistration("S0003", "COF002", "Enrolled"));
        registrations.add(new CourseRegistration("S0004", "COF003", "Enrolled"));
        registrations.add(new CourseRegistration("S0005", "COF004", "Enrolled"));
        registrations.add(new CourseRegistration("S0006", "COF005", "Enrolled"));
    }

    // COURSE REGISTRATION GETTER METHODS
    public String getRegistrationID() {
        return registrationID;
    }
    public String getStudentID() {
        return studentID;
    }
    public String getOfferingID() {
        return offeringID;
    }
    public String getRegistrationDate() {
        return registrationDate;
    }
    public String getStatus() {
        return status;
    }

    // FIND REGISTRATION
    public static CourseRegistration findRegistration(String registrationID) {
        if (registrationID == null || registrationID.trim().isEmpty())
            return null;

        for (CourseRegistration r : registrations) {
            if (registrationID.trim().equalsIgnoreCase(r.getRegistrationID()))
                return r;
        }

        return null;
    }

    // RETRIEVE REGISTRATIONS BY STUDENT
    public static ArrayList<CourseRegistration> getRegistrationsByStudent(String studentID) {
        ArrayList<CourseRegistration> result = new ArrayList<>();

        if (studentID == null || studentID.trim().isEmpty())
            return result;

        for (CourseRegistration r : registrations) {
            if (studentID.trim().equalsIgnoreCase(r.getStudentID())
                    && r.getStatus().equalsIgnoreCase("Enrolled"))
                result.add(r);
        }

        return result;
    }

    // RETRIEVE REGISTRATIONS BY OFFERING
    public static ArrayList<CourseRegistration> getRegistrationsByOffering(String offeringID) {
        ArrayList<CourseRegistration> result = new ArrayList<>();

        if (offeringID == null || offeringID.trim().isEmpty())
            return result;

        for (CourseRegistration r : registrations) {
            if (offeringID.trim().equalsIgnoreCase(r.getOfferingID())
                    && r.getStatus().equalsIgnoreCase("Enrolled"))
                result.add(r);
        }

        return result;
    }

    // DISPLAY ONE REGISTRATION
    public static void displayRegistration(String registrationID) {

        CourseRegistration r = findRegistration(registrationID);

        if (r == null) {
            UI.print("Registration not found.");
            return;
        }

        UI.header("COURSE REGISTRATION DETAILS");
        UI.liner(String.format("  REGISTRATION ID    : %s", r.getRegistrationID()));
        UI.liner(String.format("  STUDENT ID         : %s", r.getStudentID()));
        UI.liner(String.format("  OFFERING ID        : %s", r.getOfferingID()));
        UI.liner(String.format("  REGISTRATION DATE  : %s", r.getRegistrationDate()));
        UI.liner(String.format("  STATUS             : %s", r.getStatus()));
        UI.footer();
    }

    // DISPLAY ALL REGISTRATIONS
    public static void displayRegistrations() {

        UI.header("COURSE REGISTRATIONS");

        if (registrations.isEmpty()) {
            UI.liner("  No course registrations found.");
        } else {
            for (int i = 0; i < registrations.size(); i++) {
                CourseRegistration r = registrations.get(i);

                UI.liner(String.format("  REGISTRATION ID    : %s", r.getRegistrationID()));
                UI.liner(String.format("  STUDENT ID         : %s", r.getStudentID()));
                UI.liner(String.format("  OFFERING ID        : %s", r.getOfferingID()));
                UI.liner(String.format("  REGISTRATION DATE  : %s", r.getRegistrationDate()));
                UI.liner(String.format("  STATUS             : %s", r.getStatus()));

                if (i < registrations.size() - 1)
                    UI.separator();
            }
        }

        UI.footer();
    }

    public static void displayArchiveRegistrations() {

        UI.header("ARCHIVE COURSE REGISTRATIONS");

        if (archiveRegistrations.isEmpty()) {
            UI.liner("  No course registrations found.");
        } else {
            for (int i = 0; i < archiveRegistrations.size(); i++) {
                CourseRegistration r = archiveRegistrations.get(i);

                UI.liner(String.format("  REGISTRATION ID    : %s", r.getRegistrationID()));
                UI.liner(String.format("  STUDENT ID         : %s", r.getStudentID()));
                UI.liner(String.format("  OFFERING ID        : %s", r.getOfferingID()));
                UI.liner(String.format("  REGISTRATION DATE  : %s", r.getRegistrationDate()));
                UI.liner(String.format("  STATUS             : %s", r.getStatus()));

                if (i < archiveRegistrations.size() - 1)
                    UI.separator();
            }
        }

        UI.footer();
    }

    // MANAGE METHODS
    public static void createRegistration() {

        String studentID = "", offeringID = "", status = "", choice;
        boolean submit = false;

        while (!submit) {
            registrationManage("CREATE", "", studentID, offeringID, status);

            do {
                System.out.print("  Select > ");
                choice = in.nextLine().trim().toUpperCase();

                if (!choice.matches("[13X/]"))
                    UI.print("Invalid Choice");

            } while (!choice.matches("[13X/]"));

            switch (choice) {
                case "1":
                    do {
                        System.out.print("   Enter Student ID > ");
                        studentID = in.nextLine().trim();

                        if (Student.findStudent(studentID) == null) {
                            UI.print("Student does not exist.");
                            studentID = "";
                        }
                        else if (studentID.trim().isEmpty()) UI.print("Invalid Input");
                    } while (Student.findStudent(studentID) == null || studentID.trim().isEmpty());
                    break;

                case "2":
                    do {
                        System.out.print("   Enter Offering ID > ");
                        offeringID = in.nextLine().trim();

                        if (CourseOffering.findOffering(offeringID) == null) {
                            UI.print("Course offering does not exist.");
                            offeringID = "";
                        }
                        else if (offeringID.trim().isEmpty()) UI.print("Invalid Input");
                    } while(CourseOffering.findOffering(offeringID) == null || offeringID.trim().isEmpty());
                    break;

                case "3":
                    do {
                        System.out.print("   Enter Status > ");
                        status = in.nextLine().trim();

                        if (status.isEmpty()) UI.print("Invalid Input");
                        else if (!status.matches("ENROLLED|DROPPED")) UI.print("Invalid Status");
                    } while (status.isEmpty() || !status.matches("ENROLLED|DROPPED"));

                case "/":
                    boolean valid = true;
                    if (studentID.isEmpty() || offeringID.isEmpty() || status.isEmpty()) {
                        UI.print("Details are incomplete.");
                        break;
                    }

                    for (CourseRegistration r : registrations) {
                        if (studentID.trim().equalsIgnoreCase(r.getStudentID())
                                && offeringID.trim().equalsIgnoreCase(r.getOfferingID())
                                && r.getStatus().equalsIgnoreCase("Enrolled")) {
                            UI.print("Student is already registered in this offering.");
                            valid = false;
                        }
                    }

                    if (valid) {
                        registrations.add(new CourseRegistration(studentID, offeringID, status));
                        UI.print("Course registration successfully created.");
                        displayRegistration(registrations.getLast().getRegistrationID());
                        submit = true;
                    }
                    break;

                case "X":
                    submit = true;
                    break;
            }
        }
    }
    public static void editRegistration() {

        String input, choice;
        boolean submit = false;

        UI.print("EDIT EXISTING REGISTRATION");

        CourseRegistration r;

        do {
            System.out.print("  Enter Registration ID > ");
            input = in.nextLine().trim();

            r = findRegistration(input);

            if (r == null)
                UI.print("Registration does not exist.");

        } while (r == null);

        String studentID = r.getStudentID();
        String offeringID = r.getOfferingID();
        String status = r.getStatus();

        while (!submit) {
            registrationManage("EDIT", r.getRegistrationID(), studentID, offeringID, status);

            do {
                System.out.print("  Select > ");
                choice = in.nextLine().trim().toUpperCase();

                if (!choice.matches("[12X/]"))
                    UI.print("Invalid Choice");

            } while (!choice.matches("[12X/]"));

            switch (choice) {
                case "1":
                    System.out.print("   Enter Student ID > ");
                    input = in.nextLine().trim();

                    if (Student.findStudent(input) != null)
                        studentID = input;
                    else
                        UI.print("Student does not exist.");

                    break;

                case "2":
                    System.out.print("   Enter Offering ID > ");
                    input = in.nextLine().trim();

                    if (CourseOffering.findOffering(input) != null)
                        offeringID = input;
                    else
                        UI.print("Course offering does not exist.");

                    break;

                case "3":
                    do {
                        System.out.print("   Enter Status > ");
                        status = in.nextLine().trim();

                        if (status.isEmpty()) UI.print("Invalid Input");
                        else if (!status.matches("ENROLLED|DROPPED")) UI.print("Invalid Status");
                    } while (status.isEmpty() || !status.matches("ENROLLED|DROPPED"));
                break;

                case "/":
                    submit = true;
                    if (studentID == null || studentID.trim().isEmpty()
                            || offeringID == null || offeringID.trim().isEmpty()) {
                        UI.print("All registration fields are required.");
                    }

                    for (CourseRegistration existing : registrations) {
                        if (existing != r
                                && existing.getStudentID().equalsIgnoreCase(studentID)
                                && existing.getOfferingID().equalsIgnoreCase(offeringID)
                                && existing.getStatus().equalsIgnoreCase("Enrolled")) {
                            UI.print("Student is already registered in this offering.");
                            submit = false;
                        }
                    }

                    if (submit) {
                        r.studentID = studentID; r.offeringID = offeringID; r.status = status;
                        UI.print("Registration successfully edited.");
                        displayRegistration(r.getRegistrationID());
                    }
                    break;

                case "X":
                    submit = true;
                    break;
            }
        }
    }
    public static void archiveRegistration() {
        String input, choice;

        UI.print("DELETE EXISTING REGISTRATION");

        CourseRegistration r;

        do {
            System.out.print("  Enter Registration ID > ");
            input = in.nextLine().trim();

            r = findRegistration(input);

            if (r == null)
                UI.print("Registration does not exist.");

        } while (r == null);

        displayRegistration(r.getRegistrationID());

        do {
            System.out.print("  Confirm Registration Deletion? (Y/N) > ");
            choice = in.nextLine().trim().toUpperCase();

            if (!choice.matches("[YN]"))
                UI.print("Invalid Input");

        } while (!choice.matches("[YN]"));

        if (choice.equals("Y")){
            archiveRegistrations.add(r);
            registrations.remove(r);
            UI.print("Registration successfully archived");
        }
        else UI.print("Archiving cancelled");

    }
    private static void registrationManage(String text, String registrationID,
                                           String studentID, String offeringID, String status) {

        UI.header(text.toUpperCase() + ": COURSE REGISTRATION");
        UI.liner("     REGISTRATION ID : " + (registrationID.trim().isEmpty() ?
                String.format("CR%04d", registrations.size()) : registrationID));
        UI.liner(" [1] STUDENT ID      : " + studentID);
        UI.liner(" [2] OFFERING ID     : " + offeringID);
        UI.liner(" [3] STATUS          : " + status);
        UI.liner("");
        UI.option();
        UI.footer();
    }
}