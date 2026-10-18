package DATA;

import java.util.ArrayList;
import java.util.Arrays;
import UI.*;
public class Enrollment {
    private static int nextEnrollmentID = 1;
    private String enrollmentID;
    private String sectionID; // foreign key
    private String studentNumber; // foreign key

    Enrollment(String sectionID, String studentNumber) {
        this.enrollmentID = String.format("E%04d", nextEnrollmentID++);
        this.sectionID = sectionID;
        this.studentNumber = studentNumber;
    }

    public static ArrayList<Enrollment> enrollments = new ArrayList<>();

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
    public static Enrollment findEnrollment(String enrollmentID) {
        for (Enrollment e : enrollments) if (enrollmentID.equals(e.getEnrollmentID())) return e;
        return null;
    }
    public static Enrollment findEnrollmentByID(String userID) {
        for (Enrollment e : enrollments) if (Enrollment.getStudentNumber(e).equals(userID)) return e;
        return null;
    }
    public static Enrollment findEnrollmentBySec(String sectionID) {
        for (Enrollment e : enrollments) if (sectionID.equals(e.sectionID)) return e;
        return null;
    }
    public static String getEnrollment(Enrollment e) {
        return e.enrollmentID;
    }
    public static String getStudentNumber(Enrollment e) {
        return e.studentNumber;
    }
    public static String getSectionID(Enrollment e) {
        return e.sectionID;
    }

    // ENROLLMENT METHOD
    public static void displayEnrollment(String enrollmentID) {
        int innerWidth = 70;
        Enrollment e = findEnrollment(enrollmentID);
        if (e == null) {
            UI.print("Enrollment not found");
            return;
        }
        UI.header("ENROLLMENT INFORMATION");
        UI.liner(String.format("  ENROLLMENT ID  : %s", Enrollment.getEnrollment(e)));
        UI.separator();
        UI.liner(String.format("  STUDENT ID     : %s", Enrollment.getStudentNumber(e)));
        UI.liner(String.format("  SECTION ID     : %s", Enrollment.getSectionID(e)));
        UI.footer();
    }
}