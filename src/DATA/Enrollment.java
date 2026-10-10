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

    public static ArrayList<Enrollment> enrollments = new ArrayList<>();

    static {
        enrollments.add(new Enrollment(
                "E0001", "0001", "S001",
                new ArrayList<>(Arrays.asList(
                        Course.findCourse("PE 12"),
                        Course.findCourse("CC 105"),
                        Course.findCourse("IT 203"),
                        Course.findCourse("CC 106")
                ))
        ));

        enrollments.add(new Enrollment(
                "E0002", "0002", "S001",
                new ArrayList<>(Arrays.asList(
                        Course.findCourse("IT 301"),
                        Course.findCourse("IT 302")
                ))
        ));

        enrollments.add(new Enrollment(
                "E0003", "0001", "S002",
                new ArrayList<>(Arrays.asList(
                        Course.findCourse("RLW 101"),
                        Course.findCourse("ETH 101"),
                        Course.findCourse("CC 105"),
                        Course.findCourse("IT 203")
                ))
        ));

        enrollments.add(new Enrollment(
                "E0004", "0003", "S002",
                new ArrayList<>(Arrays.asList(
                        Course.findCourse("PE 12"),
                        Course.findCourse("IT 204"),
                        Course.findCourse("IT 205")
                ))
        )); 

        enrollments.add(new Enrollment(
                "E0005", "0001", "S003",
                new ArrayList<>(Arrays.asList(
                        Course.findCourse("CC 105"),
                        Course.findCourse("CC 106")
                ))
        ));

        enrollments.add(new Enrollment(
                "E0006", "0004", "S003",
                new ArrayList<>(Arrays.asList(
                        Course.findCourse("IT 204"),
                        Course.findCourse("IT 206"),
                        Course.findCourse("CC 106")
                ))
        ));

        enrollments.add(new Enrollment(
                "E0007", "0005", "S004",
                new ArrayList<>(Arrays.asList(
                        Course.findCourse("PE 12"),
                        Course.findCourse("RLW 101"),
                        Course.findCourse("CC 104")
                ))
        ));

        enrollments.add(new Enrollment(
                "E0008", "0001", "S004",
                new ArrayList<>(Arrays.asList(
                        Course.findCourse("CC 105"),
                        Course.findCourse("IT 203")
                ))
        ));

        enrollments.add(new Enrollment(
                "E0009", "0005", "S005",
                new ArrayList<>(Arrays.asList(
                        Course.findCourse("ETH 101"),
                        Course.findCourse("STS 101"),
                        Course.findCourse("CC 104")
                ))
        ));

        enrollments.add(new Enrollment(
                "E0010", "0008", "S005",
                new ArrayList<>(Arrays.asList(
                        Course.findCourse("PE 12"),
                        Course.findCourse("RLW 101")
                ))
        ));

        enrollments.add(new Enrollment(
                "E0011", "0006", "S006",
                new ArrayList<>(Arrays.asList(
                        Course.findCourse("IT 301"),
                        Course.findCourse("IT 303")
                ))
        ));

        enrollments.add(new Enrollment(
                "E0012", "0011", "S006",
                new ArrayList<>(Arrays.asList(
                        Course.findCourse("IT 302"),
                        Course.findCourse("GE 101")
                ))
        ));

        enrollments.add(new Enrollment(
                "E0013", "0003", "S007",
                new ArrayList<>(Arrays.asList(
                        Course.findCourse("PE 12"),
                        Course.findCourse("RLW 101"),
                        Course.findCourse("CC 105")
                ))
        ));

        enrollments.add(new Enrollment(
                "E0014", "0009", "S007",
                new ArrayList<>(Arrays.asList(
                        Course.findCourse("IT 203"),
                        Course.findCourse("IT 205")
                ))
        ));

        enrollments.add(new Enrollment(
                "E0015", "0004", "S008",
                new ArrayList<>(Arrays.asList(
                        Course.findCourse("IT 204"),
                        Course.findCourse("IT 207")
                ))
        ));

        enrollments.add(new Enrollment(
                "E0016", "0007", "S008",
                new ArrayList<>(Arrays.asList(
                        Course.findCourse("CC 106"),
                        Course.findCourse("IT 206")
                ))
        ));

        enrollments.add(new Enrollment(
                "E0017", "0008", "S009",
                new ArrayList<>(Arrays.asList(
                        Course.findCourse("PE 12"),
                        Course.findCourse("ETH 101")
                ))
        ));

        enrollments.add(new Enrollment(
                "E0018", "0012", "S009",
                new ArrayList<>(Arrays.asList(
                        Course.findCourse("RLW 101"),
                        Course.findCourse("CC 104")
                ))
        ));

        enrollments.add(new Enrollment(
                "E0019", "0007", "S010",
                new ArrayList<>(Arrays.asList(
                        Course.findCourse("IT 204"),
                        Course.findCourse("IT 205"),
                        Course.findCourse("IT 207")
                ))
        ));

        enrollments.add(new Enrollment(
                "E0020", "0010", "S010",
                new ArrayList<>(Arrays.asList(
                        Course.findCourse("CC 106"),
                        Course.findCourse("IT 206")
                ))
        ));

        enrollments.add(new Enrollment(
                "E0021", "0009", "S011",
                new ArrayList<>(Arrays.asList(
                        Course.findCourse("PE 12"),
                        Course.findCourse("CC 105"),
                        Course.findCourse("STS 101")
                ))
        ));

        enrollments.add(new Enrollment(
                "E0022", "0001", "S011",
                new ArrayList<>(Arrays.asList(
                        Course.findCourse("IT 203"),
                        Course.findCourse("CC 106")
                ))
        ));

        enrollments.add(new Enrollment(
                "E0023", "0002", "S012",
                new ArrayList<>(Arrays.asList(
                        Course.findCourse("IT 301"),
                        Course.findCourse("IT 303"),
                        Course.findCourse("IT 206")
                ))
        ));

        enrollments.add(new Enrollment(
                "E0024", "0011", "S012",
                new ArrayList<>(Arrays.asList(
                        Course.findCourse("IT 302"),
                        Course.findCourse("GE 101")
                ))
        ));

        enrollments.add(new Enrollment(
                "E0025", "0003", "S013",
                new ArrayList<>(Arrays.asList(
                        Course.findCourse("PE 12"),
                        Course.findCourse("IT 204"),
                        Course.findCourse("IT 205")
                ))
        ));

        enrollments.add(new Enrollment(
                "E0026", "0004", "S013",
                new ArrayList<>(Arrays.asList(
                        Course.findCourse("STS 101"),
                        Course.findCourse("CC 106")
                ))
        ));

        enrollments.add(new Enrollment(
                "E0027", "0012", "S014",
                new ArrayList<>(Arrays.asList(
                        Course.findCourse("PE 12"),
                        Course.findCourse("CC 104")
                ))
        ));

        enrollments.add(new Enrollment(
                "E0028", "0005", "S014",
                new ArrayList<>(Arrays.asList(
                        Course.findCourse("RLW 101"),
                        Course.findCourse("ETH 101")
                ))
        ));

        enrollments.add(new Enrollment(
                "E0029", "0010", "S015",
                new ArrayList<>(Arrays.asList(
                        Course.findCourse("IT 204"),
                        Course.findCourse("IT 207"),
                        Course.findCourse("IT 205")
                ))
        ));

        enrollments.add(new Enrollment(
                "E0030", "0007", "S015",
                new ArrayList<>(Arrays.asList(
                        Course.findCourse("CC 106"),
                        Course.findCourse("IT 206")
                ))
        ));

        enrollments.add(new Enrollment(
                "E0031", "0006", "S016",
                new ArrayList<>(Arrays.asList(
                        Course.findCourse("IT 301"),
                        Course.findCourse("IT 302")
                ))
        ));

        enrollments.add(new Enrollment(
                "E0032", "0002", "S016",
                new ArrayList<>(Arrays.asList(
                        Course.findCourse("IT 303"),
                        Course.findCourse("IT 206")
                ))
        ));

        enrollments.add(new Enrollment(
                "E0033", "0008", "S017",
                new ArrayList<>(Arrays.asList(
                        Course.findCourse("PE 12"),
                        Course.findCourse("RLW 101"),
                        Course.findCourse("CC 104")
                ))
        ));

        enrollments.add(new Enrollment(
                "E0034", "0012", "S017",
                new ArrayList<>(Arrays.asList(
                        Course.findCourse("ETH 101")
                ))
        ));

        enrollments.add(new Enrollment(
                "E0035", "0009", "S018",
                new ArrayList<>(Arrays.asList(
                        Course.findCourse("PE 12"),
                        Course.findCourse("CC 105"),
                        Course.findCourse("IT 205")
                ))
        ));

        enrollments.add(new Enrollment(
                "E0036", "0003", "S018",
                new ArrayList<>(Arrays.asList(
                        Course.findCourse("RLW 101"),
                        Course.findCourse("IT 204")
                ))
        ));
    }

    public String getEnrollmentID() {
        return enrollmentID;
    }

    public static Enrollment findEnrollment(String enrollmentID) {
        for (Enrollment e : enrollments) if (enrollmentID.equals(e.getEnrollmentID())) return e;
        return null;
    }

    public static Enrollment findEnrollmentByID(String userID, String courseCode) {
        for (Enrollment e : enrollments) {
            if (!getStudentNumber(e).equals(userID)) continue;
            for (Course c : getEnrolledCourses(e)) if (Course.getCourseCode(c).equals(courseCode)) return e;
        }
        return null;
    }

    public static Enrollment findEnrollmentByID(String sectionID) {
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