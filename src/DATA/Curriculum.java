package DATA;

import UI.*;
import java.util.ArrayList;

public class Curriculum {

    private String programCode; // PK, FK -> Program.programCode
    private String courseID; // PK, FK -> Course.courseID
    private String curriculumYearLevel;
    private int curriculumTerm;

    public static ArrayList<Curriculum> curriculums = new ArrayList<>();

    public Curriculum(String programCode, String courseID,
                      String curriculumYearLevel, int curriculumTerm) {

        if (programCode == null || programCode.isBlank())
            throw new IllegalArgumentException("Program code cannot be empty");

        if (courseID == null || courseID.isBlank())
            throw new IllegalArgumentException("Course ID cannot be empty");

        programCode = programCode.trim();
        courseID = courseID.trim();

        if (Program.findProgram(programCode) == null)
            throw new IllegalArgumentException("Program not found");

        if (Course.findCourseByID(courseID) == null)
            throw new IllegalArgumentException("Course not found");

        if (curriculumYearLevel == null || curriculumYearLevel.isBlank())
            throw new IllegalArgumentException("Year level cannot be empty");

        if (curriculumTerm < 1 || curriculumTerm > 3)
            throw new IllegalArgumentException("Invalid curriculum term");

        this.programCode = programCode;
        this.courseID = courseID;
        this.curriculumYearLevel = curriculumYearLevel.trim();
        this.curriculumTerm = curriculumTerm;
    }

    // INSTANCE GETTER METHODS
    public String getProgramCode() {
        return programCode;
    }

    public String getCourseID() {
        return courseID;
    }

    public String getCurriculumYearLevel() {
        return curriculumYearLevel;
    }

    public int getCurriculumTerm() {
        return curriculumTerm;
    }

    // FIND USING COMPOSITE PRIMARY KEY
    public static Curriculum findCurriculum(String programCode, String courseID) {

        if (programCode == null || courseID == null)
            return null;

        programCode = programCode.trim();
        courseID = courseID.trim();

        for (Curriculum c : curriculums) {
            if (programCode.equalsIgnoreCase(c.getProgramCode())
                    && courseID.equalsIgnoreCase(c.getCourseID()))
                return c;
        }

        return null;
    }

    public static boolean existCurriculum(String programCode, String courseID) {
        return findCurriculum(programCode, courseID) != null;
    }

    // ADD CURRICULUM ENTRY
    public static boolean addCurriculum(String programCode, String courseID,
                                        String curriculumYearLevel, int curriculumTerm) {

        if (programCode == null || programCode.isBlank()
                || courseID == null || courseID.isBlank()
                || curriculumYearLevel == null || curriculumYearLevel.isBlank()) {

            UI.print("Please complete all curriculum fields");
            return false;
        }

        programCode = programCode.trim();
        courseID = courseID.trim();
        curriculumYearLevel = curriculumYearLevel.trim();

        if (Program.findProgram(programCode) == null) {
            UI.print("Program not found");
            return false;
        }

        if (Course.findCourseByID(courseID) == null) {
            UI.print("Course not found");
            return false;
        }

        if (curriculumTerm < 1 || curriculumTerm > 3) {
            UI.print("Invalid curriculum term");
            return false;
        }

        if (findCurriculum(programCode, courseID) != null) {
            UI.print("Course is already included in this program");
            return false;
        }

        curriculums.add(new Curriculum(programCode, courseID,
                curriculumYearLevel, curriculumTerm));

        return true;
    }

    // RETRIEVE COURSES THROUGH PROGRAM -> CURRICULUM -> COURSE
    public static ArrayList<Course> getCoursesByProgram(String programCode) {

        ArrayList<Course> result = new ArrayList<>();

        if (programCode == null || programCode.isBlank())
            return result;

        programCode = programCode.trim();

        if (Program.findProgram(programCode) == null)
            return result;

        for (Curriculum c : curriculums) {
            if (programCode.equalsIgnoreCase(c.getProgramCode())) {
                Course course = Course.findCourseByID(c.getCourseID());

                if (course != null && !result.contains(course))
                    result.add(course);
            }
        }

        return result;
    }

    // RETRIEVE PROGRAMS THROUGH COURSE -> CURRICULUM -> PROGRAM
    public static ArrayList<Program> getProgramsByCourse(String courseID) {

        ArrayList<Program> result = new ArrayList<>();

        if (courseID == null || courseID.isBlank())
            return result;

        courseID = courseID.trim();

        if (Course.findCourseByID(courseID) == null)
            return result;

        for (Curriculum c : curriculums) {
            if (courseID.equalsIgnoreCase(c.getCourseID())) {
                Program program = Program.findProgram(c.getProgramCode());

                if (program != null && !result.contains(program))
                    result.add(program);
            }
        }

        return result;
    }

    // DISPLAY COURSES INCLUDED IN A PROGRAM
    public static void displayCourses(String programCode) {

        if (programCode == null || programCode.isBlank()) {
            UI.print("Program code cannot be empty");
            return;
        }

        programCode = programCode.trim();

        Program program = Program.findProgram(programCode);

        if (program == null) {
            UI.print("Program not found");
            return;
        }

        ArrayList<Course> courses = getCoursesByProgram(programCode);

        UI.header("PROGRAM CURRICULUM");
        UI.liner("  PROGRAM CODE : " + programCode);
        UI.liner("  PROGRAM NAME : " + Program.getProgramDescription(program));
        UI.separator();

        if (courses.isEmpty()) {
            UI.liner("  No courses found in this curriculum.");
        } else {
            for (Course c : courses) {
                Curriculum entry = findCurriculum(programCode, c.getCourseID());

                if (entry == null)
                    continue;

                UI.liner(String.format("  %-12s %-35s",
                        c.getCourseCode(), c.getCourseDescription()));

                UI.liner(String.format("  YEAR LEVEL: %s  TERM: %d",
                        entry.getCurriculumYearLevel(),
                        entry.getCurriculumTerm()));

                UI.separator();
            }
        }

        UI.footer();
    }
}