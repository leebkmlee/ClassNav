package DATA;

import UI.*;
import USER.*;

import static UI.UI.in;

import java.time.LocalDate;
import java.util.ArrayList;

public class CourseRegistration {

    private static int nextRegistrationID = 1;

    private String registrationID;
    private String studentID;
    private String offeringID;
    private String registrationDate;
    private String status;

    public static ArrayList<CourseRegistration> registrations = new ArrayList<>();

    // COURSE REGISTRATION CONSTRUCTOR

    public CourseRegistration(String studentID, String offeringID) {

        if (studentID == null || studentID.trim().isEmpty())
            throw new IllegalArgumentException("Student ID is required.");

        if (offeringID == null || offeringID.trim().isEmpty())
            throw new IllegalArgumentException("Offering ID is required.");

        studentID = studentID.trim();
        offeringID = offeringID.trim();

        if (Student.findStudent(studentID) == null)
            throw new IllegalArgumentException("Student not found.");

        if (CourseOffering.findOffering(offeringID) == null)
            throw new IllegalArgumentException("Course offering not found.");

        if (isRegistered(studentID, offeringID))
            throw new IllegalArgumentException("Student is already registered in this offering.");

        this.registrationID = String.format("CR%04d", nextRegistrationID++);
        this.studentID = studentID;
        this.offeringID = offeringID;
        this.registrationDate = LocalDate.now().toString();
        this.status = "Enrolled";

        registrations.add(this);
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

    // STATIC GETTER METHODS

    public static String getRegistration(CourseRegistration r) {
        return r == null ? null : r.getRegistrationID();
    }

    public static String getStudentID(CourseRegistration r) {
        return r == null ? null : r.getStudentID();
    }

    public static String getOfferingID(CourseRegistration r) {
        return r == null ? null : r.getOfferingID();
    }

    public static String getRegistrationDate(CourseRegistration r) {
        return r == null ? null : r.getRegistrationDate();
    }

    public static String getStatus(CourseRegistration r) {
        return r == null ? null : r.getStatus();
    }

    // UPDATE REGISTRATION STATUS

    public boolean setStatus(String status) {

        if (status == null ||
                !(status.equalsIgnoreCase("Enrolled")
                        || status.equalsIgnoreCase("Dropped"))) {
            UI.print("Invalid registration status.");
            return false;
        }

        if (this.status.equalsIgnoreCase(status)) {
            UI.print("Registration already has this status.");
            return false;
        }

        this.status = status.substring(0, 1).toUpperCase()
                + status.substring(1).toLowerCase();

        return true;
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

    // CHECK EXISTING ACTIVE REGISTRATION

    public static boolean isRegistered(String studentID, String offeringID) {

        if (studentID == null || offeringID == null)
            return false;

        for (CourseRegistration r : registrations) {
            if (studentID.trim().equalsIgnoreCase(r.getStudentID())
                    && offeringID.trim().equalsIgnoreCase(r.getOfferingID())
                    && r.getStatus().equalsIgnoreCase("Enrolled"))
                return true;
        }

        return false;
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

    // ADD REGISTRATION

    public static boolean addRegistration(String studentID, String offeringID) {

        try {
            new CourseRegistration(studentID, offeringID);
            UI.print("Course registration successfully created.");
            return true;

        } catch (IllegalArgumentException e) {
            UI.print(e.getMessage());
            return false;
        }
    }

    // EDIT REGISTRATION

    public static boolean editRegistration(String registrationID,
                                           String studentID, String offeringID) {

        CourseRegistration r = findRegistration(registrationID);

        if (r == null) {
            UI.print("Registration not found.");
            return false;
        }

        if (studentID == null || studentID.trim().isEmpty()
                || offeringID == null || offeringID.trim().isEmpty()) {
            UI.print("All registration fields are required.");
            return false;
        }

        studentID = studentID.trim();
        offeringID = offeringID.trim();

        if (Student.findStudent(studentID) == null) {
            UI.print("Student not found.");
            return false;
        }

        if (CourseOffering.findOffering(offeringID) == null) {
            UI.print("Course offering not found.");
            return false;
        }

        for (CourseRegistration existing : registrations) {
            if (existing != r
                    && existing.getStudentID().equalsIgnoreCase(studentID)
                    && existing.getOfferingID().equalsIgnoreCase(offeringID)
                    && existing.getStatus().equalsIgnoreCase("Enrolled")) {
                UI.print("Student is already registered in this offering.");
                return false;
            }
        }

        r.studentID = studentID;
        r.offeringID = offeringID;
        r.status = "Enrolled";

        return true;
    }

    // DELETE REGISTRATION

    public static boolean deleteRegistration(String registrationID) {

        CourseRegistration r = findRegistration(registrationID);

        if (r == null) {
            UI.print("Registration not found.");
            return false;
        }

        registrations.remove(r);
        UI.print("Course registration successfully deleted.");

        return true;
    }

    // DISPLAY ALL REGISTRATIONS

    public static void displayRegistrations() {

        UI.header("COURSE REGISTRATIONS");

        if (registrations.isEmpty()) {
            UI.liner("  No course registrations found.");
        } else {
            for (int i = 0; i < registrations.size(); i++) {
                CourseRegistration r = registrations.get(i);

                UI.liner(String.format("  REGISTRATION ID : %s", r.getRegistrationID()));
                UI.liner(String.format("  STUDENT ID      : %s", r.getStudentID()));
                UI.liner(String.format("  OFFERING ID     : %s", r.getOfferingID()));
                UI.liner(String.format("  REGISTRATION DATE: %s", r.getRegistrationDate()));
                UI.liner(String.format("  STATUS          : %s", r.getStatus()));

                if (i < registrations.size() - 1)
                    UI.separator();
            }
        }

        UI.footer();
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

    // MANAGE COURSE REGISTRATIONS

    // CREATE REGISTRATION MENU

    public static void createRegistration() {

        String studentID = "", offeringID = "", choice;
        boolean submit = false;

        while (!submit) {
            registrationManage("CREATE", "NEW", studentID, offeringID);

            do {
                System.out.print("  Select > ");
                choice = in.nextLine().trim().toUpperCase();

                if (!choice.matches("[12X/]"))
                    UI.print("Invalid Choice");

            } while (!choice.matches("[12X/]"));

            switch (choice) {
                case "1":
                    System.out.print("   Enter Student ID > ");
                    studentID = in.nextLine().trim();

                    if (Student.findStudent(studentID) == null) {
                        UI.print("Student does not exist.");
                        studentID = "";
                    }
                    break;

                case "2":
                    CourseOffering.displayOfferings();

                    System.out.print("   Enter Offering ID > ");
                    offeringID = in.nextLine().trim();

                    if (!CourseOffering.existOffering(offeringID)) {
                        UI.print("Course offering does not exist.");
                        offeringID = "";
                    }
                    break;

                case "/":
                    if (studentID.isEmpty() || offeringID.isEmpty()) {
                        UI.print("Details are incomplete.");
                        break;
                    }

                    if (addRegistration(studentID, offeringID))
                        submit = true;
                    break;

                case "X":
                    submit = true;
                    break;
            }
        }
    }

    // EDIT REGISTRATION MENU

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

        while (!submit) {
            registrationManage("EDIT", r.getRegistrationID(), studentID, offeringID);

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
                    CourseOffering.displayOfferings();

                    System.out.print("   Enter Offering ID > ");
                    input = in.nextLine().trim();

                    if (CourseOffering.existOffering(input))
                        offeringID = input;
                    else
                        UI.print("Course offering does not exist.");

                    break;

                case "/":
                    if (editRegistration(r.getRegistrationID(), studentID, offeringID)) {
                        UI.print("Registration successfully edited.");
                        submit = true;
                    }
                    break;

                case "X":
                    submit = true;
                    break;
            }
        }
    }

    // DELETE REGISTRATION MENU

    public static void deleteRegistration() {

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

        if (choice.equals("Y"))
            deleteRegistration(r.getRegistrationID());
    }

    // REGISTRATION FORM DISPLAY

    private static void registrationManage(String text, String registrationID,
                                           String studentID, String offeringID) {

        UI.header(text.toUpperCase() + ": COURSE REGISTRATION");
        UI.liner(" REGISTRATION ID : " + registrationID);
        UI.liner(" [1] STUDENT ID   : " + studentID);
        UI.liner(" [2] OFFERING ID  : " + offeringID);
        UI.liner("");
        UI.option();
        UI.footer();
    }
}