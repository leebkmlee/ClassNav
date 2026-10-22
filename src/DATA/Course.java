package DATA;

import UI.*;
import USER.*;

import java.util.ArrayList;

public class Course {

    private static int nextCourseID = 1;

    private String courseID; // PK
    private String courseCode;
    private String courseDescription;
    private int creditUnits;
    private String courseType; // Course classification

    public static ArrayList<Course> courses = new ArrayList<>();

    public Course(String courseCode, String courseDescription, int creditUnits,
                  String courseType) {

        if (courseCode == null || courseCode.trim().isEmpty())
            throw new IllegalArgumentException("Course code cannot be empty.");

        if (courseDescription == null || courseDescription.trim().isEmpty())
            throw new IllegalArgumentException("Course description cannot be empty.");

        if (creditUnits <= 0)
            throw new IllegalArgumentException("Credit units must be greater than zero.");

        if (courseType == null || courseType.trim().isEmpty())
            throw new IllegalArgumentException("Course type cannot be empty.");

        if (existCourseCode(courseCode))
            throw new IllegalArgumentException("Course code already exists.");

        if (existCourseDesc(courseDescription))
            throw new IllegalArgumentException("Course description already exists.");

        this.courseID = String.format("C%03d", nextCourseID++);
        this.courseCode = courseCode.trim();
        this.courseDescription = courseDescription.trim();
        this.creditUnits = creditUnits;
        this.courseType = courseType.trim().toUpperCase();
    }

    static {
        courses.add(new Course("PE 12", "PathFit 3", 2, "GEN ED"));
        courses.add(new Course("RLW 101", "Rizal Life and Works", 3, "GEN ED"));
        courses.add(new Course("ETH 101", "Ethics", 3, "GEN ED"));
        courses.add(new Course("STS 101", "Science, Technology, and Society", 3, "GEN ED"));
        courses.add(new Course("IT 204", "Networking", 3, "MAJOR"));
        courses.add(new Course("CC 106", "Information Management", 3, "MAJOR"));
        courses.add(new Course("IT 203", "Object-Oriented Programming", 3, "MAJOR"));
        courses.add(new Course("IT 205", "Quantitative Methods", 3, "MAJOR"));
        courses.add(new Course("CC 105", "Data Structure Algorithm", 3, "MAJOR"));
        courses.add(new Course("CC 104", "Computer Programming 2", 3, "MAJOR"));
        courses.add(new Course("IT 206", "Web Systems and Technologies", 3, "MAJOR"));
        courses.add(new Course("IT 207", "Platform Technologies", 3, "MAJOR"));
        courses.add(new Course("GE 101", "General Education Elective", 3, "GEN ED"));
        courses.add(new Course("IT 301", "Systems Analysis and Design", 3, "MAJOR"));
        courses.add(new Course("IT 302", "Information Assurance and Security", 3, "MAJOR"));
        courses.add(new Course("IT 303", "Application Development", 3, "MAJOR"));
    }

    // COURSE SETTER METHODS

    public void setCourseCode(String courseCode) {

        if (courseCode == null || courseCode.trim().isEmpty())
            throw new IllegalArgumentException("Course code cannot be empty.");

        for (Course c : courses) {
            if (c != this &&
                    courseCode.trim().equalsIgnoreCase(c.getCourseCode())) {
                throw new IllegalArgumentException("Course code already exists.");
            }
        }

        this.courseCode = courseCode.trim();
    }

    public void setCourseDescription(String courseDescription) {

        if (courseDescription == null || courseDescription.trim().isEmpty())
            throw new IllegalArgumentException("Course description cannot be empty.");

        for (Course c : courses) {
            if (c != this &&
                    courseDescription.trim().equalsIgnoreCase(c.getCourseDescription())) {
                throw new IllegalArgumentException("Course description already exists.");
            }
        }

        this.courseDescription = courseDescription.trim();
    }

    public void setCreditUnits(int creditUnits) {

        if (creditUnits <= 0)
            throw new IllegalArgumentException("Credit units must be greater than zero.");

        this.creditUnits = creditUnits;
    }

    public void setCourseType(String courseType) {

        if (courseType == null || courseType.trim().isEmpty())
            throw new IllegalArgumentException("Course type cannot be empty.");

        this.courseType = courseType.trim().toUpperCase();
    }

    // COURSE GETTER METHODS

    public String getCourseID() {
        return courseID;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public int getCreditUnits() {
        return creditUnits;
    }

    public String getCourseType() {
        return courseType;
    }

    // FIND USING THE PRIMARY KEY

    public static Course findCourseByID(String courseID) {

        if (courseID == null || courseID.trim().isEmpty())
            return null;

        for (Course c : courses) {
            if (courseID.trim().equalsIgnoreCase(c.getCourseID()))
                return c;
        }

        return null;
    }

    // FIND USING THE OFFICIAL COURSE CODE

    public static Course findCourse(String courseCode) {

        if (courseCode == null || courseCode.trim().isEmpty())
            return null;

        for (Course c : courses) {
            if (courseCode.trim().equalsIgnoreCase(c.getCourseCode()))
                return c;
        }

        return null;
    }

    // EXISTING STATIC GETTERS

    public static String getCourse(Course c) {
        return c == null ? null : c.getCourseID();
    }

    public static String getCourseCode(Course c) {
        return c == null ? null : c.getCourseCode();
    }

    public static String getCourseDescription(Course c) {
        return c == null ? null : c.getCourseDescription();
    }

    public static int getCreditUnits(Course c) {
        return c == null ? 0 : c.getCreditUnits();
    }

    public static String getCourseType(Course c) {
        return c == null ? null : c.getCourseType();
    }

    public static boolean existCourseID(String courseID) {
        return findCourseByID(courseID) != null;
    }

    public static boolean existCourseCode(String courseCode) {
        return findCourse(courseCode) != null;
    }

    public static boolean existCourseDesc(String courseDescription) {

        if (courseDescription == null || courseDescription.trim().isEmpty())
            return false;

        for (Course c : courses) {
            if (courseDescription.trim().equalsIgnoreCase(c.getCourseDescription()))
                return true;
        }

        return false;
    }

    // COURSE METHODS

    public static void displayCourse(String courseCode) {

        Course c = findCourse(courseCode);

        if (c == null) {
            UI.print("Course not found");
            return;
        }

        UI.header("COURSE DETAILS");
        UI.liner(String.format("  COURSE ID           : %s", c.getCourseID()));
        UI.liner(String.format("  COURSE CODE         : %s", c.getCourseCode()));
        UI.liner(String.format("  COURSE DESCRIPTION  : %s", c.getCourseDescription()));
        UI.liner(String.format("  CREDIT UNITS        : %d", c.getCreditUnits()));
        UI.liner(String.format("  COURSE TYPE         : %s", c.getCourseType()));
        UI.footer();
    }

    public static void displayCourses() {

        UI.header("COURSE LIST");

        if (courses.isEmpty()) {
            UI.liner("  No courses found.");
        } else {
            for (Course c : courses) {
                String course = String.format("  %-10s %-37s %-12s",
                        c.getCourseCode(),
                        c.getCourseDescription(),
                        c.getCreditUnits() + " units");

                UI.liner(course);
            }
        }

        UI.footer();
    }

    // CHECK FACULTY COURSE ASSIGNMENT

    public static boolean courseLoad(String facultyID, String courseCode) {

        if (facultyID == null || facultyID.trim().isEmpty()
                || courseCode == null || courseCode.trim().isEmpty())
            return false;

        Course c = findCourse(courseCode);

        if (c == null || Faculty.findFaculty(facultyID.trim()) == null)
            return false;

        for (CourseOffering o : CourseOffering.offerings) {

            if (o.getCourseID().equalsIgnoreCase(c.getCourseID())
                    && o.getFacultyID().equalsIgnoreCase(facultyID.trim())) {
                return true;
            }
        }

        return false;
    }

    // DISPLAY SECTIONS ASSIGNED TO A COURSE AND FACULTY

    public static void displaySections(String courseCode, String facultyID) {

        Course c = findCourse(courseCode);

        if (c == null) {
            UI.print("Course not found");
            return;
        }

        if (facultyID == null || facultyID.trim().isEmpty()
                || Faculty.findFaculty(facultyID.trim()) == null) {
            UI.print("Faculty not found");
            return;
        }

        ArrayList<CourseOffering> matchingOfferings = new ArrayList<>();

        for (CourseOffering o : CourseOffering.offerings) {

            if (o.getCourseID().equalsIgnoreCase(c.getCourseID())
                    && o.getFacultyID().equalsIgnoreCase(facultyID.trim())) {
                matchingOfferings.add(o);
            }
        }

        UI.header("COURSE SECTIONS");

        UI.liner(String.format("  COURSE CODE : %s", c.getCourseCode()));
        UI.liner(String.format("  COURSE      : %s", c.getCourseDescription()));
        UI.liner(String.format("  FACULTY ID  : %s", facultyID.trim()));
        UI.separator();

        if (matchingOfferings.isEmpty()) {

            UI.liner("  No sections found for this course and faculty.");

        } else {

            for (int i = 0; i < matchingOfferings.size(); i++) {

                CourseOffering o = matchingOfferings.get(i);
                Section s = Section.findSection(o.getSectionID());

                UI.liner(String.format("  OFFERING ID  : %s", o.getOfferingID()));
                UI.liner(String.format("  SECTION ID   : %s", o.getSectionID()));

                UI.liner(String.format("  SECTION      : %s",
                        s == null ? "Section not found"
                                : Section.getYearSection(s)));

                UI.liner(String.format("  ACADEMIC YEAR: %s", o.getAcademicYear()));
                UI.liner(String.format("  TERM         : %d", o.getTerm()));

                if (i < matchingOfferings.size() - 1)
                    UI.separator();
            }
        }

        UI.footer();
    }
}