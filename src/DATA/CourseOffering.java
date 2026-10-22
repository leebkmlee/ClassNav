
package DATA;

import UI.*;
import USER.*;
import DATA.*;

import java.util.ArrayList;

public class CourseOffering {

    private static int nextOfferingID = 1;

    private String offeringID;
    private String sectionID; // FK -> Section.sectionID
    private String courseID;  // FK -> Course.courseID
    private String facultyID; // FK -> Faculty.facultyID
    private String academicYear;
    private int term;

    public static ArrayList<CourseOffering> offerings = new ArrayList<>();

    // COURSE OFFERING CONSTRUCTOR

    public CourseOffering(String sectionID, String courseID, String facultyID,
                          String academicYear, int term) {

        if (sectionID == null || sectionID.trim().isEmpty())
            throw new IllegalArgumentException("Section ID is required.");

        if (courseID == null || courseID.trim().isEmpty())
            throw new IllegalArgumentException("Course ID is required.");

        if (facultyID == null || facultyID.trim().isEmpty())
            throw new IllegalArgumentException("Faculty ID is required.");

        if (academicYear == null || academicYear.trim().isEmpty())
            throw new IllegalArgumentException("Academic year is required.");

        sectionID = sectionID.trim();
        courseID = courseID.trim();
        facultyID = facultyID.trim();
        academicYear = academicYear.trim();

        if (Section.findSection(sectionID) == null)
            throw new IllegalArgumentException("Section not found.");

        if (Course.findCourseByID(courseID) == null)
            throw new IllegalArgumentException("Course not found.");

        if (Faculty.findFaculty(facultyID) == null)
            throw new IllegalArgumentException("Faculty not found.");

        if (term < 1 || term > 3)
            throw new IllegalArgumentException("Term must be 1, 2, or 3.");

        this.offeringID = String.format("COF%03d", nextOfferingID++);
        this.sectionID = sectionID;
        this.courseID = courseID;
        this.facultyID = facultyID;
        this.academicYear = academicYear;
        this.term = term;
    }

    // COURSE OFFERING GETTER METHODS

    public String getOfferingID() {
        return offeringID;
    }

    public String getSectionID() {
        return sectionID;
    }

    public String getCourseID() {
        return courseID;
    }

    public String getFacultyID() {
        return facultyID;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public int getTerm() {
        return term;
    }

    // STATIC GETTER METHODS

    public static String getOffering(CourseOffering o) {
        return o == null ? null : o.getOfferingID();
    }

    public static String getSectionID(CourseOffering o) {
        return o == null ? null : o.getSectionID();
    }

    public static String getCourseID(CourseOffering o) {
        return o == null ? null : o.getCourseID();
    }

    public static String getFacultyID(CourseOffering o) {
        return o == null ? null : o.getFacultyID();
    }

    public static String getAcademicYear(CourseOffering o) {
        return o == null ? null : o.getAcademicYear();
    }

    public static int getTerm(CourseOffering o) {
        return o == null ? 0 : o.getTerm();
    }

    // FIND METHODS

    public static CourseOffering findOffering(String offeringID) {

        if (offeringID == null || offeringID.trim().isEmpty())
            return null;

        for (CourseOffering o : offerings) {
            if (offeringID.trim().equalsIgnoreCase(o.getOfferingID()))
                return o;
        }

        return null;
    }

    public static boolean existOffering(String offeringID) {
        return findOffering(offeringID) != null;
    }

    // ADD A COURSE OFFERING

    public static boolean addOffering(String sectionID, String courseID,
                                      String facultyID, String academicYear,
                                      int term) {

        if (sectionID == null || sectionID.trim().isEmpty()
                || courseID == null || courseID.trim().isEmpty()
                || facultyID == null || facultyID.trim().isEmpty()
                || academicYear == null || academicYear.trim().isEmpty()) {

            UI.print("All course offering fields are required.");
            return false;
        }

        sectionID = sectionID.trim();
        courseID = courseID.trim();
        facultyID = facultyID.trim();
        academicYear = academicYear.trim();

        if (Section.findSection(sectionID) == null) {
            UI.print("Section not found.");
            return false;
        }

        if (Course.findCourseByID(courseID) == null) {
            UI.print("Course not found.");
            return false;
        }

        if (Faculty.findFaculty(facultyID) == null) {
            UI.print("Faculty not found.");
            return false;
        }

        if (term < 1 || term > 3) {
            UI.print("Term must be 1, 2, or 3.");
            return false;
        }

        // Prevent duplicate course offerings for the same section,
        // academic year, and term, regardless of assigned faculty.

        for (CourseOffering o : offerings) {

            if (sectionID.equalsIgnoreCase(o.getSectionID())
                    && courseID.equalsIgnoreCase(o.getCourseID())
                    && academicYear.equalsIgnoreCase(o.getAcademicYear())
                    && term == o.getTerm()) {

                UI.print("This course is already offered to this section for that academic year and term.");
                return false;
            }
        }

        offerings.add(new CourseOffering(sectionID, courseID, facultyID,
                academicYear, term));

        return true;
    }

    // RETRIEVE OFFERINGS BY SECTION

    public static ArrayList<CourseOffering> getOfferingsBySection(String sectionID) {

        ArrayList<CourseOffering> result = new ArrayList<>();

        if (sectionID == null || sectionID.trim().isEmpty())
            return result;

        sectionID = sectionID.trim();

        if (Section.findSection(sectionID) == null)
            return result;

        for (CourseOffering o : offerings) {
            if (sectionID.equalsIgnoreCase(o.getSectionID()))
                result.add(o);
        }

        return result;
    }

    // RETRIEVE OFFERINGS BY COURSE

    public static ArrayList<CourseOffering> getOfferingsByCourse(String courseID) {

        ArrayList<CourseOffering> result = new ArrayList<>();

        if (courseID == null || courseID.trim().isEmpty())
            return result;

        courseID = courseID.trim();

        if (Course.findCourseByID(courseID) == null)
            return result;

        for (CourseOffering o : offerings) {
            if (courseID.equalsIgnoreCase(o.getCourseID()))
                result.add(o);
        }

        return result;
    }

    // RETRIEVE OFFERINGS BY FACULTY

    public static ArrayList<CourseOffering> getOfferingsByFaculty(String facultyID) {

        ArrayList<CourseOffering> result = new ArrayList<>();

        if (facultyID == null || facultyID.trim().isEmpty())
            return result;

        facultyID = facultyID.trim();

        if (Faculty.findFaculty(facultyID) == null)
            return result;

        for (CourseOffering o : offerings) {
            if (facultyID.equalsIgnoreCase(o.getFacultyID()))
                result.add(o);
        }

        return result;
    }

    // DISPLAY ALL COURSE OFFERINGS

    public static void displayOfferings() {

        UI.header("COURSE OFFERINGS");

        if (offerings.isEmpty()) {

            UI.liner("  No course offerings found.");

        } else {

            for (int i = 0; i < offerings.size(); i++) {

                CourseOffering o = offerings.get(i);

                Section section = Section.findSection(o.getSectionID());
                Course course = Course.findCourseByID(o.getCourseID());
                Faculty faculty = Faculty.findFaculty(o.getFacultyID());

                UI.liner(String.format("  OFFERING ID  : %s",
                        o.getOfferingID()));

                UI.liner(String.format("  SECTION      : %s",
                        section == null ? "Section not found"
                                : Section.getYearSection(section)));

                UI.liner(String.format("  COURSE       : %s",
                        course == null ? "Course not found"
                                : course.getCourseCode()));

                UI.liner(String.format("  FACULTY ID   : %s",
                        o.getFacultyID()));

                UI.liner(String.format("  FACULTY      : %s",
                        faculty == null ? "Faculty not found"
                                : faculty.getUserName()));

                UI.liner(String.format("  ACADEMIC YEAR: %s",
                        o.getAcademicYear()));

                UI.liner(String.format("  TERM         : %d",
                        o.getTerm()));

                if (i < offerings.size() - 1)
                    UI.separator();
            }
        }

        UI.footer();
    }

    // DISPLAY ONE COURSE OFFERING

    public static void displayOffering(String offeringID) {

        CourseOffering o = findOffering(offeringID);

        if (o == null) {
            UI.print("Course offering not found.");
            return;
        }

        Section section = Section.findSection(o.getSectionID());
        Course course = Course.findCourseByID(o.getCourseID());
        Faculty faculty = Faculty.findFaculty(o.getFacultyID());

        UI.header("COURSE OFFERING DETAILS");

        UI.liner(String.format("  OFFERING ID   : %s",
                o.getOfferingID()));

        UI.liner(String.format("  SECTION ID    : %s",
                o.getSectionID()));

        UI.liner(String.format("  SECTION       : %s",
                section == null ? "Section not found"
                        : Section.getYearSection(section)));

        UI.liner(String.format("  COURSE ID     : %s",
                o.getCourseID()));

        UI.liner(String.format("  COURSE        : %s",
                course == null ? "Course not found"
                        : course.getCourseCode()));

        UI.liner(String.format("  FACULTY ID    : %s",
                o.getFacultyID()));

        UI.liner(String.format("  FACULTY       : %s",
                faculty == null ? "Faculty not found"
                        : faculty.getUserName()));

        UI.liner(String.format("  ACADEMIC YEAR : %s",
                o.getAcademicYear()));

        UI.liner(String.format("  TERM          : %d",
                o.getTerm()));

        UI.footer();
    }
}