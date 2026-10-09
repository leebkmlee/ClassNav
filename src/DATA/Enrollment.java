package DATA;

import java.util.ArrayList;
import java.util.Arrays;
import UI.*;

public class Enrollment {

    private String enrollmentID;
    private String sectionID; // foreign key
    private String studentNumber; // foreign key
    private final ArrayList<Course> enrolledCourses;

    Enrollment(String enrollmentID, String sectionID, String studentNumber, ArrayList<Course> enrolledCourses) {
        this.enrollmentID = enrollmentID;
        this.sectionID = sectionID;
        this.studentNumber = studentNumber;
        this.enrolledCourses = enrolledCourses;
    }

    public static ArrayList<Enrollment> enrollements = new ArrayList<>();

    static {
        enrollements.add(new Enrollment("0001", "0001", "S001", new ArrayList<>(Arrays.asList(
                        new Course("PE 12", "PathFit 3", 2, "PE"),
                        new Course("RLW 101", "Rizal Life and Works", 3, "CAL"),
                        new Course("ETH 101", "Ethics", 3, "CAL"),
                        new Course("STS 101", "Science, Technology, and Society", 3, "CS"),
                        new Course("IT 204", "Networking", 3, "CICT"),
                        new Course("CC 106", "Information Management", 3, "CICT"),
                        new Course("IT 203", "Object-Oriented Programming", 3, "CICT"),
                        new Course("IT 205", "Quantitative Methods", 3, "CICT"),
                        new Course("CC 105", "Data Structure Algorithm", 3, "CICT")))));

        enrollements.add(new Enrollment("0002", "0001", "S002", new ArrayList<>(Arrays.asList(
                        new Course("IT 204", "Networking", 3, "CICT"),
                        new Course("CC 106", "Information Management", 3, "CICT"),
                        new Course("IT 203", "Object-Oriented Programming", 3, "CICT"),
                        new Course("IT 205", "Quantitative Methods", 3, "CICT"),
                        new Course("CC 105", "Data Structure Algorithm", 3, "CICT")))));

        enrollements.add(new Enrollment("0003", "0002", "S002", new ArrayList<>(Arrays.asList(
                        new Course("PE 12", "PathFit 3", 2, "PE"),
                        new Course("RLW 101", "Rizal Life and Works", 3, "CAL"),
                        new Course("ETH 101", "Ethics", 3, "CAL")))));

        enrollements.add(new Enrollment("0004", "0002", "S003", new ArrayList<>(Arrays.asList(
                        new Course("CC 106", "Information Management", 3, "CICT"),
                        new Course("IT 203", "Object-Oriented Programming", 3, "CICT"),
                        new Course("IT 205", "Quantitative Methods", 3, "CICT"),
                        new Course("CC 105", "Data Structure Algorithm", 3, "CICT")))));
    }

    public String getEnrollmentID() {
        return enrollmentID;
    }

    public static Enrollment findEnrollment(String enrollmentID) {
        for (Enrollment e : enrollements) if (enrollmentID.equals(e.getEnrollmentID())) return e;
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

    public static ArrayList<Course> getEnrolledCourses(Enrollment e) {
        return e.enrolledCourses;
    }

    public static void displayEnrollment(String enrollmentID) {
        int innerWidth = 70;
        Enrollment e = findEnrollment(enrollmentID);
        if (e == null) {
            UI.print("Enrollment not found");
            return;
        }
        UI.header("ENROLLMENT INFORMATION");
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("  STUDENT ID     : %s", Enrollment.getStudentNumber(e)));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("  SECTION ID     : %s", Enrollment.getSectionID(e)));
        System.out.println("├" + "─".repeat(innerWidth) + "┤");
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("  Enrollment ID  : %s", Enrollment.getEnrollment(e)));
        System.out.printf("│%-" + innerWidth + "s│\n", "  Enrolled Courses:");
        for (Course c : Enrollment.getEnrolledCourses(e)) {
            String course = String.format("    %-10s %-40s %2d units",
                    Course.getCourseCode(c), Course.getCourseDescription(c), Course.getCreditUnits(c));
            System.out.printf("│%-" + innerWidth + "s│\n", course);
        }
        UI.footer();
    }
}