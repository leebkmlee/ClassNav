package DATA;

import UI.*;

import java.util.ArrayList;

import static DATA.Section.sections;

public class Course {
    static int innerWidth = 70;
    private String courseCode;
    private String courseDescription;
    private int creditUnits;
    private String programCode; // foreign key

    public Course(String courseCode, String courseDescription, int creditUnits, String programCode) {
        this.courseCode = courseCode;
        this.courseDescription = courseDescription;
        this.creditUnits = creditUnits;
        this.programCode = programCode;
    }

    public static ArrayList<Course> courses = new ArrayList<>();
    static {
        courses.add(new Course("PE 12", "PathFit 3", 2, "BSIT"));
        courses.add(new Course("RLW 101", "Rizal Life and Works", 3, "BSIT"));
        courses.add(new Course("ETH 101", "Ethics", 3, "BSIT"));
        courses.add(new Course("STS 101", "Science, Technology, and Society", 3, "BSIT"));
        courses.add(new Course("IT 204", "Networking", 3, "BSIT"));
        courses.add(new Course("CC 106", "Information Management", 3, "BSIT"));
        courses.add(new Course("IT 203", "Object-Oriented Programming", 3, "BSIT"));
        courses.add(new Course("IT 205", "Quantitative Methods", 3, "BSIT"));
        courses.add(new Course("CC 105", "Data Structure Algorithm", 3, "BSIT"));
    }

    public String getCourseCode() {
        return courseCode;
    }

    public static Course findCourse(String courseCode) {
        for (Course c : courses) if (courseCode.equals(c.getCourseCode())) return c;
        return null;
    }

    public static String getCourseCode(Course c) {
        return c.courseCode;
    }

    public static String getCourseDescription(Course c) {
        return c.courseDescription;
    }

    public static int getCreditUnits(Course c) {
        return c.creditUnits;
    }

    public static String getProgramCode(Course c) {
        return c.programCode;
    }

    public static void displayCourse(String courseCode){
        Course c = findCourse(courseCode);
        if (c == null) {
            UI.print("Course not found");
            return;
        }
        UI.header("COURSE DETAILS");
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("  COURSE CODE         : %s", Course.getCourseCode(c)));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("  COURSE DESCRIPTION  : %s", Course.getCourseDescription(c)));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("  CREDIT UNITS        : %s", Course.getCreditUnits(c)));
        UI.footer();
    }

    public static void displaySections(String courseCode){
        Course c = findCourse(courseCode);
        if (c == null) {
            UI.print("Course not found");
            return;
        }
        UI.header("SECTION LIST");
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("  COURSE CODE     : %s", Course.getCourseCode(c)));
        System.out.printf("│%-" + innerWidth + "s│\n", "  Sections under " + Course.getCourseDescription(c) + ":");
        for (Section s : sections) {
            if (Section.getCourseCode(s).equals(c.getCourseCode())) {
                String section = String.format("    %-15s %-35s %-12s",
                        Section.getSection(s), Section.getYearSection(s), "AY " + Section.getAcademicYear(s));
                System.out.printf("│%-" + innerWidth + "s│\n", section);
            }
        }
        UI.footer();
    }
}