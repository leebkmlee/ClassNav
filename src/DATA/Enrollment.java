
package DATA;

import java.util.ArrayList;
import USER.*;
import UI.*;

public class Enrollment {

    private static int nextEnrollmentID = 1;
    private String enrollmentID;
    private String sectionID; // FK -> Section.sectionID
    private String studentNumber; // FK -> Student.userID

    public static ArrayList<Enrollment> enrollments = new ArrayList<>();

    Enrollment(String sectionID, String studentNumber) {

        this.enrollmentID = String.format("E%04d", nextEnrollmentID++);
        this.sectionID = sectionID;
        this.studentNumber = studentNumber;
    }

    static {
        enrollments.add(new Enrollment("0001", "S0001"));
        enrollments.add(new Enrollment("0002", "S0001"));
        enrollments.add(new Enrollment("0001", "S0002"));
        enrollments.add(new Enrollment("0003", "S0002"));
        enrollments.add(new Enrollment("0001", "S0003"));
        enrollments.add(new Enrollment("0004", "S0003"));
        enrollments.add(new Enrollment("0005", "S0004"));
        enrollments.add(new Enrollment("0001", "S0004"));
        enrollments.add(new Enrollment("0005", "S0005"));
        enrollments.add(new Enrollment("0008", "S0005"));
        enrollments.add(new Enrollment("0006", "S0006"));
        enrollments.add(new Enrollment("0011", "S0006"));
        enrollments.add(new Enrollment("0003", "S0007"));
        enrollments.add(new Enrollment("0009", "S0007"));
        enrollments.add(new Enrollment("0004", "S0008"));
        enrollments.add(new Enrollment("0007", "S0008"));
        enrollments.add(new Enrollment("0008", "S0009"));
        enrollments.add(new Enrollment("0012", "S0009"));
        enrollments.add(new Enrollment("0007", "S0010"));
        enrollments.add(new Enrollment("0010", "S0010"));
        enrollments.add(new Enrollment("0009", "S0011"));
        enrollments.add(new Enrollment("0001", "S0011"));
        enrollments.add(new Enrollment("0002", "S0012"));
        enrollments.add(new Enrollment("0011", "S0012"));
        enrollments.add(new Enrollment("0003", "S0013"));
        enrollments.add(new Enrollment("0004", "S0013"));
        enrollments.add(new Enrollment("0012", "S0014"));
        enrollments.add(new Enrollment("0005", "S0014"));
        enrollments.add(new Enrollment("0010", "S0015"));
        enrollments.add(new Enrollment("0007", "S0015"));
        enrollments.add(new Enrollment("0006", "S0016"));
        enrollments.add(new Enrollment("0002", "S0016"));
        enrollments.add(new Enrollment("0008", "S0017"));
        enrollments.add(new Enrollment("0012", "S0017"));
        enrollments.add(new Enrollment("0009", "S0018"));
        enrollments.add(new Enrollment("0003", "S0018"));
        enrollments.add(new Enrollment("0007", "S0001"));
    }

    // ENROLLMENT GETTER METHOD
    public String getEnrollmentID() {
        return enrollmentID;
    }

    // FIND ENROLLMENT BY ENROLLMENT ID
    public static Enrollment findEnrollment(String enrollmentID) {

        if (enrollmentID == null || enrollmentID.isBlank())
            return null;

        enrollmentID = enrollmentID.trim();

        for (Enrollment e : enrollments) {
            if (enrollmentID.equalsIgnoreCase(e.getEnrollmentID()))
                return e;
        }

        return null;
    }

    // FIND FIRST ENROLLMENT BY STUDENT ID
    public static Enrollment findEnrollmentByID(String userID) {

        if (userID == null || userID.isBlank())
            return null;

        userID = userID.trim();

        for (Enrollment e : enrollments) {
            if (userID.equalsIgnoreCase(Enrollment.getStudentNumber(e)))
                return e;
        }

        return null;
    }

    // FIND FIRST ENROLLMENT BY SECTION ID
    public static Enrollment findEnrollmentBySec(String sectionID) {

        if (sectionID == null || sectionID.isBlank())
            return null;

        sectionID = sectionID.trim();

        for (Enrollment e : enrollments) {
            if (sectionID.equalsIgnoreCase(Enrollment.getSectionID(e)))
                return e;
        }

        return null;
    }

    // RETRIEVE ALL ENROLLMENTS BY STUDENT ID
    public static ArrayList<Enrollment> getEnrollmentsByStudent(String studentNumber) {

        ArrayList<Enrollment> result = new ArrayList<>();

        if (studentNumber == null || studentNumber.isBlank())
            return result;

        studentNumber = studentNumber.trim();

        for (Enrollment e : enrollments) {
            if (studentNumber.equalsIgnoreCase(Enrollment.getStudentNumber(e)))
                result.add(e);
        }

        return result;
    }

    // RETRIEVE ALL ENROLLMENTS BY SECTION ID
    public static ArrayList<Enrollment> getEnrollmentsBySection(String sectionID) {

        ArrayList<Enrollment> result = new ArrayList<>();

        if (sectionID == null || sectionID.isBlank())
            return result;

        sectionID = sectionID.trim();

        for (Enrollment e : enrollments) {
            if (sectionID.equalsIgnoreCase(Enrollment.getSectionID(e)))
                result.add(e);
        }

        return result;
    }

    // STATIC GETTER METHODS
    public static String getEnrollment(Enrollment e) {
        return e == null ? null : e.enrollmentID;
    }

    public static String getStudentNumber(Enrollment e) {
        return e == null ? null : e.studentNumber;
    }

    public static String getSectionID(Enrollment e) {
        return e == null ? null : e.sectionID;
    }

    // DISPLAY ENROLLMENT INFORMATION
    public static void displayEnrollment(String enrollmentID) {

        Enrollment e = Enrollment.findEnrollment(enrollmentID);

        if (e == null) {
            UI.print("Enrollment not found");
            return;
        }

        UI.header("ENROLLMENT INFORMATION");
        UI.liner(String.format("  ENROLLMENT ID  : %s", e.getEnrollmentID()));
        UI.separator();
        UI.liner(String.format("  STUDENT ID     : %s", Enrollment.getStudentNumber(e)));
        UI.liner(String.format("  SECTION ID     : %s", Enrollment.getSectionID(e)));
        UI.footer();
    }
}