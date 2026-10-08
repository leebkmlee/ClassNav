package DATA;

import java.util.ArrayList;
import java.util.Arrays;

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

    private static ArrayList<Enrollment> enrollements = new ArrayList<>();
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
                new Course("CC 105", "Data Structure Algorithm", 3, "CICT")
        ))));
        enrollements.add(new Enrollment("0002", "0001", "S002", new ArrayList<>(Arrays.asList(
                new Course("IT 204", "Networking", 3, "CICT"),
                new Course("CC 106", "Information Management", 3, "CICT"),
                new Course("IT 203", "Object-Oriented Programming", 3, "CICT"),
                new Course("IT 205", "Quantitative Methods", 3, "CICT"),
                new Course("CC 105", "Data Structure Algorithm", 3, "CICT")
        ))));
        enrollements.add(new Enrollment("0003", "0002", "S002", new ArrayList<>(Arrays.asList(
                new Course("PE 12", "PathFit 3", 2, "PE"),
                new Course("RLW 101", "Rizal Life and Works", 3, "CAL"),
                new Course("ETH 101", "Ethics", 3, "CAL")
        ))));
        enrollements.add(new Enrollment("0004", "0002", "S003", new ArrayList<>(Arrays.asList(
                new Course("CC 106", "Information Management", 3, "CICT"),
                new Course("IT 203", "Object-Oriented Programming", 3, "CICT"),
                new Course("IT 205", "Quantitative Methods", 3, "CICT"),
                new Course("CC 105", "Data Structure Algorithm", 3, "CICT")
        ))));
    }

    public String getEnrollmentID() {return enrollmentID;}

    public static String getEnrollment(String enrollmentID) {
        for (Enrollment e : enrollements) if (enrollmentID.equals(e.getEnrollmentID())) return e.enrollmentID;
        return null;
    }

    public static String getStudentNumber(String enrollmentID) {
        for (Enrollment e : enrollements) if (enrollmentID.equals(e.getEnrollmentID())) return e.studentNumber;
        return null;
    }

    public static String getSectionID(String enrollmentID) {
        for (Enrollment e : enrollements) if (enrollmentID.equals(e.getEnrollmentID())) return e.sectionID;
        return null;
    }

    public static ArrayList<Course> getEnrolledCourses(String enrollmentID) {
        for (Enrollment e : enrollements) {
            if (enrollmentID.equals(e.getEnrollmentID())) {
                return e.enrolledCourses;
            }
        }
        return new ArrayList<>();
    }

    public static void displayEnrollment(String enrollmentID) {
        int innerWidth = 70;
        System.out.println("┌" + "─".repeat(innerWidth) + "┐");
        System.out.printf("│%-" + innerWidth + "s│\n", "                        ENROLLMENT INFORMATION");
        System.out.println("├" + "─".repeat(innerWidth) + "┤");
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("  STUDENT ID     : %s", getStudentNumber(enrollmentID)));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("  SECTION ID     : %s", getSectionID(enrollmentID)));
        System.out.println("├" + "─".repeat(innerWidth) + "┤");
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("  Enrollment ID  : %s", getEnrollment(enrollmentID)));
        System.out.printf("│%-" + innerWidth + "s│\n", "  Enrolled Courses:");
        for (Course c : getEnrolledCourses(enrollmentID)) {
            String course = String.format("    %-10s %-40s %2d units",
                    Course.getCourseCode(c), Course.getCourseDescription(c), Course.getCreditUnits(c));
            System.out.printf("│%-" + innerWidth + "s│\n", course);
        }
        System.out.println("└" + "─".repeat(innerWidth) + "┘");
    }
}
