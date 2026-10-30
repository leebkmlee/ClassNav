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

        registrations.add(new CourseRegistration("S0007","COF021","Enrolled"));
        registrations.add(new CourseRegistration("S0008","COF022","Enrolled"));
        registrations.add(new CourseRegistration("S0009","COF023","Enrolled"));
        registrations.add(new CourseRegistration("S0010","COF024","Enrolled"));
        registrations.add(new CourseRegistration("S0011","COF025","Enrolled"));
        registrations.add(new CourseRegistration("S0012","COF026","Enrolled"));
        registrations.add(new CourseRegistration("S0013","COF027","Enrolled"));
        registrations.add(new CourseRegistration("S0014","COF028","Enrolled"));
        registrations.add(new CourseRegistration("S0015","COF029","Enrolled"));
        registrations.add(new CourseRegistration("S0001","COF030","Enrolled"));
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
            if (r != null && r.getRegistrationID() != null
                    && registrationID.trim().equalsIgnoreCase(r.getRegistrationID()))
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
            if (r != null && r.getStudentID() != null && r.getStatus() != null
                    && studentID.trim().equalsIgnoreCase(r.getStudentID())
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
            if (r != null && r.getOfferingID() != null && r.getStatus() != null
                    && offeringID.trim().equalsIgnoreCase(r.getOfferingID())
                    && r.getStatus().equalsIgnoreCase("Enrolled"))
                result.add(r);
        }

        return result;
    }

    // DISPLAY METHODS
    public static void displayRegistration(String registrationID, boolean print) {

        CourseRegistration r = findRegistration(registrationID);

        if (r == null) {
            UI.print("Registration not found.");
            return;
        }

        UI.header("COURSE REGISTRATION DETAILS", false);
        UI.liner(String.format("  REGISTRATION ID    : %s", r.getRegistrationID()), false);
        UI.liner(String.format("  STUDENT ID         : %s", r.getStudentID()), false);
        UI.liner(String.format("  OFFERING ID        : %s", r.getOfferingID()), false);
        UI.liner(String.format("  REGISTRATION DATE  : %s", r.getRegistrationDate()), false);
        UI.liner(String.format("  STATUS             : %s", r.getStatus()), false);
        UI.footer(false);
        if (print) UI.returnButton();
    }
    public static void displayRegistrations() {
        UI.header("COURSE REGISTRATIONS", true);
        if (registrations.isEmpty()) UI.liner("  No course registrations found.", true);
        else {
            String format = "  %-18s %-14s %-38s %-16s %-14s %-20s %-22s %-16s %-12s";
            UI.liner(String.format(format, "REGISTRATION ID", "STUDENT ID", "STUDENT NAME", "OFFERING ID",
                    "COURSE", "SECTION", "FACULTY", "DATE", "STATUS"), true);
            UI.separator(true);
            int count = 0;
            for (CourseRegistration registration : registrations) {
                if (registration == null) continue;
                Student student = Student.findStudent(registration.getStudentID());
                CourseOffering offering = CourseOffering.findOffering(registration.getOfferingID());
                Course course = offering == null ? null : Course.findCourse(offering.getCourseCode());
                Section section = offering == null ? null : Section.findSection(offering.getSectionID());
                Faculty faculty = offering == null ? null : Faculty.findFaculty(offering.getFacultyID());
                UI.liner(String.format(format, registration.getRegistrationID(), registration.getStudentID(),
                        UI.fitText(student == null ? "Student unavailable" : student.getUserName(), 38),
                        registration.getOfferingID(), course == null ? "-" : course.getCourseCode(),
                        UI.fitText(section == null ? "-" : Section.getYearSection(section), 20),
                        UI.fitText(faculty == null ? "-" : faculty.getUserName(), 22),
                        registration.getRegistrationDate(), registration.getStatus()), true);
                count++;
            }
            UI.separator(true);
            UI.liner("  TOTAL REGISTRATIONS: " + count, true);
        }
        UI.footer(true);
        UI.returnButton();
    }
    public static void displayArchiveRegistrations() {
        UI.header("ARCHIVED COURSE REGISTRATIONS", true);
        if (archiveRegistrations.isEmpty()) UI.liner("  No archived course registrations found.", true);
        else {
            String format = "  %-18s %-14s %-38s %-16s %-14s %-20s %-22s %-16s %-12s";
            UI.liner(String.format(format, "REGISTRATION ID", "STUDENT ID", "STUDENT NAME", "OFFERING ID",
                    "COURSE", "SECTION", "FACULTY", "DATE", "STATUS"), true);
            UI.separator(true);
            int count = 0;
            for (CourseRegistration registration : archiveRegistrations) {
                if (registration == null) continue;
                Student student = Student.findStudent(registration.getStudentID());
                CourseOffering offering = CourseOffering.findOffering(registration.getOfferingID());
                Course course = offering == null ? null : Course.findCourse(offering.getCourseCode());
                Section section = offering == null ? null : Section.findSection(offering.getSectionID());
                Faculty faculty = offering == null ? null : Faculty.findFaculty(offering.getFacultyID());
                UI.liner(String.format(format, registration.getRegistrationID(), registration.getStudentID(),
                        UI.fitText(student == null ? "Student archived" : student.getUserName(), 38),
                        registration.getOfferingID(), course == null ? "-" : course.getCourseCode(),
                        UI.fitText(section == null ? "-" : Section.getYearSection(section), 20),
                        UI.fitText(faculty == null ? "-" : faculty.getUserName(), 22),
                        registration.getRegistrationDate(), registration.getStatus()), true);
                count++;
            }
            UI.separator(true);
            UI.liner("  TOTAL ARCHIVED REGISTRATIONS: " + count, true);
        }
        UI.footer(true);
        UI.returnButton();
    }

    // MANAGE METHODS
    public static void createRegistration() {
        String studentID = "";
        String offeringID = "";
        String status = "";
        String choice;
        boolean submit = false;

        while (!submit) {
            registrationManage("CREATE", "", studentID, offeringID, status);

            do {
                UI.select("Select", false);
                choice = in.nextLine().trim().toUpperCase();
                if (!choice.matches("[123X/]")) UI.print("Invalid Choice");
            } while (!choice.matches("[123X/]"));

            switch (choice) {
                case "1":
                    do {
                        UI.select("Enter Student ID", true);
                        studentID = in.nextLine().trim();
                        if (studentID.equalsIgnoreCase("X")) {
                            studentID = "";
                            break;
                        }
                        else if (studentID.isEmpty()) UI.print("Student ID is required");
                        else if (Student.findStudent(studentID) == null) {
                            UI.print("Student does not exist.");
                            studentID = "";
                        }
                    } while (studentID.isEmpty() || Student.findStudent(studentID) == null);
                    break;

                case "2":
                    do {
                        UI.select("Enter Offering ID", true);
                        offeringID = in.nextLine().trim();
                        if (offeringID.equalsIgnoreCase("X")) {
                            offeringID = "";
                            break;
                        }
                        else if (offeringID.isEmpty()) UI.print("Offering ID is required");
                        else if (CourseOffering.findOffering(offeringID) == null) {
                            UI.print("Course Offering does not exist.");
                            offeringID = "";
                        }
                    } while (offeringID.isEmpty() || CourseOffering.findOffering(offeringID) == null);
                    break;

                case "3":
                    do {
                        UI.select("Enter Status", true);
                        status = in.nextLine().trim().toUpperCase();
                        if (status.equalsIgnoreCase("X")) {
                            status = "";
                            break;
                        }
                        else if (status.trim().isEmpty()) UI.print("Status is required");
                        else if (!status.matches("ENROLLED|DROPPED")) UI.print("Invalid Status");
                    } while (!status.matches("ENROLLED|DROPPED"));
                    break;

                case "/":
                    if (studentID.isEmpty() || offeringID.isEmpty() || status.isEmpty()) {
                        UI.print("Details are incomplete.");
                        break;
                    }

                    boolean valid = true;

                    for (CourseRegistration registration : registrations) {
                        if (registration != null
                                && studentID.equalsIgnoreCase(registration.getStudentID())
                                && offeringID.equalsIgnoreCase(registration.getOfferingID())
                                && "ENROLLED".equalsIgnoreCase(registration.getStatus())) {
                            UI.print("Student is already registered in this offering.");
                            valid = false;
                            break;
                        }
                    }

                    if (valid) {
                        CourseRegistration registration =
                                new CourseRegistration(studentID, offeringID, status);
                        registrations.add(registration);
                        UI.print("Course registration successfully created.");
                        displayRegistration(registration.getRegistrationID(), true);
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

        do {
            UI.select("Enter Registration ID", false);
            input = in.nextLine().trim();

            if (input.equalsIgnoreCase("X")) return;
            else if (input.isEmpty()) UI.print("Registration ID is required");
            else if (findRegistration(input) == null) UI.print("Registration does not exist");
        } while (input.isEmpty() || findRegistration(input) == null);

        CourseRegistration r = findRegistration(input);

        if (r == null) {
            UI.print("Registration does not exist");
            return;
        }

        String studentID = r.getStudentID();
        String offeringID = r.getOfferingID();
        String status = r.getStatus();

        while (!submit) {
            registrationManage("EDIT", r.getRegistrationID(), studentID, offeringID, status);

            do {
                UI.select("Select", false);
                choice = in.nextLine().trim().toUpperCase();
                if (!choice.matches("[123X/]")) UI.print("Invalid Choice");
            } while (!choice.matches("[123X/]"));

            switch (choice) {
                case "1":
                    do {
                        UI.select("Enter Student ID", true);
                        studentID = in.nextLine().trim();
                        if (studentID.equalsIgnoreCase("X")) {
                            studentID = r.studentID;
                            break;
                        }
                        else if (studentID.isEmpty()) UI.print("Student ID is required");
                        else if (Student.findStudent(studentID) == null) {
                            UI.print("Student does not exist.");
                            studentID = "";
                        }
                    } while (studentID.isEmpty() || Student.findStudent(studentID) == null);
                    break;

                case "2":
                    do {
                        UI.select("Enter Offering ID", true);
                        offeringID = in.nextLine().trim();
                        if (offeringID.equalsIgnoreCase("X")) {
                            offeringID = r.offeringID;
                            break;
                        }
                        else if (offeringID.isEmpty()) UI.print("Offering ID is required");
                        else if (CourseOffering.findOffering(offeringID) == null) {
                            UI.print("Course offering does not exist.");
                            offeringID = "";
                        }
                    } while (offeringID.isEmpty() || CourseOffering.findOffering(offeringID) == null);
                    break;

                case "3":
                    do {
                        UI.select("Enter Status", true);
                        status = in.nextLine().trim().toUpperCase();
                        if (status.equalsIgnoreCase("X")) {
                            status = r.status;
                            break;
                        }
                        else if (status.isEmpty()) UI.print("Status is required");
                        if (!status.matches("ENROLLED|DROPPED")) UI.print("Invalid Status");
                    } while (!status.matches("ENROLLED|DROPPED"));
                    break;

                case "/":
                    submit = true;
                    for (CourseRegistration existing : registrations) {
                        if (existing != null && existing != r
                                && existing.getStudentID() != null && existing.getOfferingID() != null
                                && existing.getStatus() != null
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
                        displayRegistration(r.getRegistrationID(), true);
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

        do {
            UI.select("Enter Registration ID", false);
            input = in.nextLine().trim();

            if (input.equalsIgnoreCase("X")) return;
            else if (input.isEmpty()) UI.print("Registration ID is required");
            else if (findRegistration(input) == null) UI.print("Registration does not exist.");
        } while (input.isEmpty() || findRegistration(input) == null);

        CourseRegistration r = findRegistration(input);

        if (r == null) {
            UI.print("Registration not found");
            return;
        }

        displayRegistration(r.getRegistrationID(), false);

        do {
            UI.select("Confirm Registration Deletion? (Y/N)", false);
            choice = in.nextLine().trim().toUpperCase();
            if (choice.equals("X")) return;

            if (!choice.matches("[YN]")) UI.print("Invalid Input");

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

        UI.header(text.toUpperCase() + ": COURSE REGISTRATION", false);
        UI.liner("     REGISTRATION ID : " + (registrationID.trim().isEmpty() ?
                String.format("CR%04d", registrations.size() + 1) : registrationID), false);
        UI.liner(" [1] STUDENT ID      : " + studentID, false);
        UI.liner(" [2] OFFERING ID     : " + offeringID, false);
        UI.liner(" [3] STATUS          : " + status, false);
        UI.liner("", false);
        UI.option();
        UI.footer(false);
    }
}