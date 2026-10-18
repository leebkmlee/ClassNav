package DATA;

import UI.*;

import java.util.ArrayList;

import static DATA.Section.sections;

public class Course {
    private static int nextCourseID = 1;
    private String courseID;
    private String courseCode;
    private String courseDescription;
    private int creditUnits;
    private String courseType;
    private String programCode; // foreign key
    private String facultyID; // foreign key

    public Course(String courseCode, String courseDescription, int creditUnits, String courseType,
                  String programCode, String facultyID) {
        this.courseID = String.format("C%03d", nextCourseID++);
        this.courseCode = courseCode;
        this.courseDescription = courseDescription;
        this.creditUnits = creditUnits;
        this.courseType = courseType;
        this.programCode = programCode;
        this.facultyID = facultyID;
    }

    public static ArrayList<Course> courses = new ArrayList<>();
    static {
        courses.add(new Course("PE 12", "PathFit 3", 2,
                "GEN ED", "BSIT", "F001"));
        courses.add(new Course("RLW 101", "Rizal Life and Works", 3,
                "GEN ED", "BSIT", "F002"));
        courses.add(new Course("ETH 101", "Ethics", 3,
                "GEN ED", "BSIT", "F003"));
        courses.add(new Course("STS 101", "Science, Technology, and Society", 3,
                "GEN ED", "BSIT", "F004"));
        courses.add(new Course("IT 204", "Networking", 3,
                "MAJOR", "BSIT", "F005"));
        courses.add(new Course("CC 106", "Information Management", 3,
                "MAJOR", "BSIT", "F006"));
        courses.add(new Course("IT 203", "Object-Oriented Programming", 3,
                "MAJOR", "BSIT", "F001"));
        courses.add(new Course("IT 205", "Quantitative Methods", 3,
                "MAJOR", "BSIT", "F002"));
        courses.add(new Course("CC 105", "Data Structure Algorithm", 3,
                "MAJOR", "BSIT", "F003"));
        courses.add(new Course("CC 104", "Computer Programming 2", 3,
                "MAJOR", "BSIT", "F004"));
        courses.add(new Course("IT 206", "Web Systems and Technologies", 3,
                "MAJOR", "BSIT", "F005"));
        courses.add(new Course("IT 207", "Platform Technologies", 3,
                "MAJOR", "BSIT", "F006"));
        courses.add(new Course("GE 101", "General Education Elective", 3,
                "GEN ED", "BSIT", "F001"));
        courses.add(new Course("IT 301", "Systems Analysis and Design", 3,
                "MAJOR", "BSIT", "F002"));
        courses.add(new Course("IT 302", "Information Assurance and Security", 3,
                "MAJOR", "BSIT", "F003"));
        courses.add(new Course("IT 303", "Application Development", 3,
                "MAJOR", "BSIT", "F004"));
    }

    // COURSE SETTER METHOD
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }
    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }
    public void setCreditUnits(int creditUnits) {
        this.creditUnits = creditUnits;
    }
    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }
    public void setProgramCode(String programCode) {
        this.programCode = programCode;
    }
    public void setFacultyID(String facultyID) {
        this.facultyID = facultyID;
    }

    // COURSE GETTER METHOD
    public String getCourseID() {
        return courseID;
    }
    public String getCourseCode() {
        return courseCode;
    }
    public static Course findCourse (String courseCode) {
        for (Course c : courses) if (courseCode.equals(c.getCourseCode())) return c;
        return null;
    }
    public static String getCourse(Course c) {
        return c.courseID;
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
    public static String getCourseType(Course c) {
        return c.courseType;
    }
    public static String getProgramCode(Course c) {
        return c.programCode;
    }
    public static String getFacultyID(Course c) {
        return c.facultyID;
    }
    public static boolean existCourseID(String courseID) {
        for (Course c : courses) if (courseID.equals(c.courseID)) return true;
        return false;
    }
    public static boolean existCourseCode(String courseCode) {
        for (Course c : courses) if (courseCode.equals(c.courseCode)) return true;
        return false;
    }
    public static boolean existCourseDesc(String courseDescription) {
        for (Course c : courses) if (courseDescription.equals(c.courseDescription)) return true;
        return false;
    }
    public static boolean courseLoad(String facultyID, String courseCode) {
        for (Course c : courses) if (Course.getFacultyID(c).equals(facultyID) && Course.getCourseCode(c).equals(courseCode))
            return true;
        return false;
    }

    // COURSE METHODS
    public static void displayCourse(String courseCode){
        Course c = findCourse(courseCode);
        if (c == null) {
            UI.print("Course not found");
            return;
        }
        UI.header("COURSE DETAILS");
        UI.liner(String.format("  COURSE CODE         : %s", Course.getCourseCode(c)));
        UI.liner(String.format("  COURSE DESCRIPTION  : %s", Course.getCourseDescription(c)));
        UI.liner(String.format("  CREDIT UNITS        : %s", Course.getCreditUnits(c)));
        UI.liner(String.format("  COURSE TYPE         : %s", Course.getCourseType(c)));
        UI.liner(String.format("  PROGRAM CODE        : %s", Course.getProgramCode(c)));
        UI.liner(String.format("  FACULTY ID          : %s", Course.getFacultyID(c)));
        UI.footer();
    }
    public static void displayCourses() {
        UI.header("COURSE LIST");
        ArrayList<String> listCourses = new ArrayList<>();
        for (Course c : courses) {
            if (!listCourses.contains(Course.getCourseCode(c))) {
                listCourses.add(Course.getCourseCode(c));
                String room = String.format( "  %-10s %-37s %-12s %-5s", Course.getCourseCode(c),
                        Course.getCourseDescription(c), Course.getCreditUnits(c) + " units", Course.getProgramCode(c));
                UI.liner(room);
            }
        }
        UI.footer();
    }
    public static void displaySections(String courseCode, String facultyID){
        Course c = findCourse(courseCode);
        if (c == null) {
            UI.print("Course not found");
            return;
        }
        UI.header("SECTION LIST");
        UI.liner(String.format("  COURSE CODE     : %s", Course.getCourseCode(c)));
        UI.liner("  Sections under " + Course.getCourseDescription(c) + ":");

        for (Course co : courses) {
            if (Course.getCourseCode(co).equals(courseCode) && Course.getFacultyID(c).equals(facultyID)) {
                for (Section s : sections) {
                    if (Course.getCourse(co).equals(Section.getCourseID(s))) {
                        String section = String.format("    %-15s %-35s %-12s",
                                Section.getSection(s), Section.getYearSection(s), "AY " + Section.getAcademicYear(s));
                        UI.liner(section);
                    }
                }
            }
        }
        UI.footer();
    }
}