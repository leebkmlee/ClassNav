package DATA;

import UI.*;
import static UI.UI.in;
import java.util.ArrayList;

public class Curriculum {

    // FIELDS
    private static int nextCurriculumID = 1;
    private final String curriculumID;
    private final String programCode, courseCode;
    private String curriculumYear;
    private int curriculumTerm;

    public static ArrayList<Curriculum> curricula = new ArrayList<>();
    public static ArrayList<Curriculum> archiveCurricula = new ArrayList<>();

    // CONSTRUCTOR
    public Curriculum(String programCode, String courseCode,
                      String curriculumYear, int curriculumTerm) {

        this.curriculumID = String.format("CUR%04d", nextCurriculumID++);
        this.programCode = programCode;
        this.courseCode = courseCode;
        this.curriculumYear = curriculumYear.trim();
        this.curriculumTerm = curriculumTerm;
    }

    // DUMMY DATA
    static {
        curricula.add(new Curriculum("BSIT", "IT101", "2026", 1));
        curricula.add(new Curriculum("BSIT", "IT102", "2026", 2));
        curricula.add(new Curriculum("BSIT", "IT103", "2025", 1));
        curricula.add(new Curriculum("BSIT", "IT104", "2025", 2));
        curricula.add(new Curriculum("BSIT", "IT105", "2024", 1));
        curricula.add(new Curriculum("BSIS", "IT101", "2026", 1));
        curricula.add(new Curriculum("BSIS", "IS101", "2025", 1));
        curricula.add(new Curriculum("BSIS", "IT101", "2026", 2));
        curricula.add(new Curriculum("BLIS", "BL101", "2026", 1));
        curricula.add(new Curriculum("BLIS", "IT101", "2026", 2));
        curricula.add(new Curriculum("BSCrim", "CR101", "2026", 1));
        curricula.add(new Curriculum("BSCrim", "CR102", "2025", 1));
        curricula.add(new Curriculum("BSTM", "TM101", "2026", 1));
        curricula.add(new Curriculum("BSHM", "HM101", "2026", 1));
        curricula.add(new Curriculum("BSN", "NS101", "2026", 1));
        curricula.add(new Curriculum("BSN", "NS102", "2025", 1));
        curricula.add(new Curriculum("BSCE", "CE101", "2026", 1));
        curricula.add(new Curriculum("BSElecEng", "CE102", "2025", 1));
        curricula.add(new Curriculum("BSBio", "BIO101", "2026", 1));
        curricula.add(new Curriculum("BSPsych", "PS101", "2026", 1));
        curricula.add(new Curriculum("BSIT", "GE101", "2026", 1));
        curricula.add(new Curriculum("BSIT", "GE102", "2026", 1));
        curricula.add(new Curriculum("BSIT", "GE103", "2026", 2));
        curricula.add(new Curriculum("BSIT", "GE104", "2026", 2));
        curricula.add(new Curriculum("BSIT", "PE101", "2026", 1));
        curricula.add(new Curriculum("BLIS", "GE101", "2026", 1));
        curricula.add(new Curriculum("BLIS", "GE102", "2026", 1));
        curricula.add(new Curriculum("BLIS", "GE105", "2026", 2));
        curricula.add(new Curriculum("BLIS", "PE101", "2026", 1));
        curricula.add(new Curriculum("BSIS", "GE101", "2026", 1));
        curricula.add(new Curriculum("BSIS", "GE103", "2026", 1));
        curricula.add(new Curriculum("BSIS", "GE106", "2026", 2));
        curricula.add(new Curriculum("BSIS", "PE101", "2026", 1));
        curricula.add(new Curriculum("BSArch", "GE102", "2026", 1));
        curricula.add(new Curriculum("BSArch", "GE107", "2026", 2));
        curricula.add(new Curriculum("BSArch", "PE101", "2026", 1));
        curricula.add(new Curriculum("BSBroad", "GE103", "2026", 1));
        curricula.add(new Curriculum("BSBroad", "GE108", "2026", 2));
        curricula.add(new Curriculum("BSBroad", "GE109", "2026", 2));
        curricula.add(new Curriculum("BSBroad", "PE101", "2026", 1));
    }

    // GETTER METHODS
    public String getCurriculumID() {
        return curriculumID;
    }
    public String getProgramCode() {
        return programCode;
    }
    public String getCourseCode() {
        return courseCode;
    }
    public String getCurriculumYear() {
        return curriculumYear;
    }
    public int getCurriculumTerm() {
        return curriculumTerm;
    }

    // FIND USING PRIMARY KEY
    public static Curriculum findCurriculum(String curriculumID) {
        if (curriculumID == null || curriculumID.trim().isEmpty())
            return null;

        curriculumID = curriculumID.trim();

        for (Curriculum c : curricula) {
            if (c != null && c.getCurriculumID() != null
                    && curriculumID.equalsIgnoreCase(c.getCurriculumID()))
                return c;
        }

        return null;
    }

    // FIND USING PROGRAM AND COURSE
    public static Curriculum findCurriculumByProgramCourse(String programCode, String courseCode) {
        if (programCode == null || courseCode == null)
            return null;

        programCode = programCode.trim();
        courseCode = courseCode.trim();

        for (Curriculum c : curricula) {
            if (c != null && c.getProgramCode() != null && c.getCourseCode() != null
                    && programCode.equalsIgnoreCase(c.getProgramCode())
                    && courseCode.equalsIgnoreCase(c.getCourseCode()))
                return c;
        }

        return null;
    }

    // RETRIEVE COURSES THROUGH PROGRAM -> CURRICULUM -> COURSE
    public static ArrayList<Course> getCoursesByProgram(String programCode) {

        ArrayList<Course> result = new ArrayList<>();

        if (programCode == null || programCode.isBlank())
            return result;

        programCode = programCode.trim();

        if (Program.findProgram(programCode) == null)
            return result;

        for (Curriculum c : curricula) {
            if (c != null && c.getProgramCode() != null
                    && programCode.equalsIgnoreCase(c.getProgramCode())) {
                Course course = Course.findCourse(c.getCourseCode());

                if (course != null && !result.contains(course))
                    result.add(course);
            }
        }

        return result;
    }

    // RETRIEVE PROGRAMS THROUGH COURSE -> CURRICULUM -> PROGRAM
    public static ArrayList<Program> getProgramsByCourse(String courseCode) {

        ArrayList<Program> result = new ArrayList<>();

        if (courseCode == null || courseCode.isBlank())
            return result;

        courseCode = courseCode.trim();

        if (Course.findCourse(courseCode) == null)
            return result;

        for (Curriculum c : curricula) {
            if (c != null && c.getCourseCode() != null
                    && courseCode.equalsIgnoreCase(c.getCourseCode())) {
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
        UI.header("PROGRAM CURRICULUM", true);
        UI.liner("  PROGRAM CODE: " + programCode + " | PROGRAM NAME: " + program.getProgramDescription(), true);
        UI.liner(String.format("  %-14s %-120s %-16s %-12s %-12s",
                "COURSE CODE", "COURSE DESCRIPTION", "YEAR LEVEL", "TERM", "CREDIT UNITS"), true);
        UI.separator(true);
        int count = 0;
        for (Course c : courses) {
            Curriculum entry = findCurriculumByProgramCourse(programCode, c.getCourseCode());
            if (entry == null) continue;
            UI.liner(String.format("  %-14s %-120s %-16s %-12s %-12s",
                    c.getCourseCode(), UI.fitText(c.getCourseDescription(), 120),
                    entry.getCurriculumYear(), entry.getCurriculumTerm(), c.getCreditUnits()), true);
            count++;
        }
        if (count == 0) UI.liner("  No courses found in this curriculum.", true);
        UI.separator(true);
        UI.liner("  TOTAL COURSES: " + count, true);
        UI.footer(true);
        UI.returnButton();
    }

    // DISPLAY METHODS
    public static void displayCurriculum(String curriculumID,boolean print) {
        Curriculum c = findCurriculum(curriculumID);
        if (c == null) {
            UI.print("Curriculum does not exist");
            return;
        }

        UI.header("CURRICULUM", false);
        UI.liner(String.format("  CURRICULUM ID  : %s", c.getCurriculumID()), false);
        UI.liner(String.format("  PROGRAM CODE   : %s", c.getProgramCode()), false);
        UI.liner(String.format("  COURSE CODE    : %s", c.getCourseCode()), false);
        UI.liner(String.format("  YEAR LEVEL     : %s", c.getCurriculumYear()), false);
        UI.liner(String.format("  TERM           : %d", c.getCurriculumTerm()), false);
        UI.footer(false);
        if(print) UI.returnButton();
    }
    public static void displayCurricula() {
        UI.header("CURRICULUM LIST", true);
        if (curricula.isEmpty()) UI.liner("  No curriculum entries found.", true);
        else {
            String format = "  %-16s %-14s %-14s %-12s %-8s %-42s %-66s";
            UI.liner(String.format(format, "CURRICULUM ID", "PROGRAM CODE", "COURSE CODE", "YEAR LEVEL",
                    "TERM", "PROGRAM NAME", "COURSE DESCRIPTION"), true);
            UI.separator(true);
            int count = 0;
            for (Curriculum curriculum : curricula) {
                if (curriculum == null) continue;
                Program program = Program.findProgram(curriculum.getProgramCode());
                Course course = Course.findCourse(curriculum.getCourseCode());
                UI.liner(String.format(format, curriculum.getCurriculumID(), curriculum.getProgramCode(),
                        curriculum.getCourseCode(), curriculum.getCurriculumYear(), curriculum.getCurriculumTerm(),
                        UI.fitText(program == null ? "Program unavailable" : program.getProgramDescription(), 42),
                        UI.fitText(course == null ? "Course unavailable" : course.getCourseDescription(), 66)), true);
                count++;
            }
            UI.separator(true);
            UI.liner("  TOTAL CURRICULUM RECORDS: " + count, true);
        }
        UI.footer(true);
        UI.returnButton();
    }
    public static void displayArchiveCurricula() {
        UI.header("ARCHIVED CURRICULUM LIST", true);
        if (archiveCurricula.isEmpty()) UI.liner("  No archived curriculum entries found.", true);
        else {
            String format = "  %-16s %-14s %-14s %-12s %-8s %-42s %-66s";
            UI.liner(String.format(format, "CURRICULUM ID", "PROGRAM CODE", "COURSE CODE", "YEAR LEVEL",
                    "TERM", "PROGRAM NAME", "COURSE DESCRIPTION"), true);
            UI.separator(true);
            int count = 0;
            for (Curriculum curriculum : archiveCurricula) {
                if (curriculum == null) continue;
                Program program = Program.findProgram(curriculum.getProgramCode());
                Course course = Course.findCourse(curriculum.getCourseCode());
                UI.liner(String.format(format, curriculum.getCurriculumID(), curriculum.getProgramCode(),
                        curriculum.getCourseCode(), curriculum.getCurriculumYear(), curriculum.getCurriculumTerm(),
                        UI.fitText(program == null ? "Program archived" : program.getProgramDescription(), 42),
                        UI.fitText(course == null ? "Course archived" : course.getCourseDescription(), 66)), true);
                count++;
            }
            UI.separator(true);
            UI.liner("  TOTAL ARCHIVED CURRICULUM RECORDS: " + count, true);
        }
        UI.footer(true);
        UI.returnButton();
    }

    // MANAGE CURRICULUM
    public static void createCurriculum() {
        String programCode = "", courseCode = "", curriculumYearLevel = "", choice;
        int curriculumTerm = -1; boolean submit = false;

        while (!submit) {
            curriculumManage("CREATE", "", programCode, courseCode, curriculumYearLevel, curriculumTerm);

            do {
                UI.select("Select", false);
                choice = in.nextLine().trim();
                if (!choice.matches("[1-4X]") && !choice.equals("/")) UI.print("Invalid Choice");
            } while (!choice.matches("[1-4X]") && !choice.equals("/"));

            switch (choice) {
                case "1":
                    do {
                        UI.select("Enter Program Code", true);
                        programCode = in.nextLine().trim();
                        if (programCode.equalsIgnoreCase("X")) {
                            programCode = "";
                            break;
                        }
                        else if (programCode.isEmpty()) UI.print("Invalid Input");
                        else if (Program.findProgram(programCode) == null) UI.print("Program does not exist");
                    } while (programCode.isEmpty() || Program.findProgram(programCode) == null);
                    break;

                case "2":
                    do {
                        UI.select("Enter Course Code", true);
                        courseCode = in.nextLine().trim();
                        if (courseCode.equalsIgnoreCase("X")) {
                            courseCode = "";
                            break;
                        }
                        else if (courseCode.isEmpty()) UI.print("Invalid Input");
                        else if (Course.findCourse(courseCode) == null) UI.print("Course does not exist");
                        else if (findCurriculumByProgramCourse(programCode, courseCode) != null)
                            UI.print("Course is already included in this program");
                    } while (courseCode.isEmpty() || Course.findCourse(courseCode) == null ||
                            findCurriculumByProgramCourse(programCode, courseCode) != null);
                    break;

                case "3":
                    do {
                        UI.select("Enter Year Level", true);
                        curriculumYearLevel = in.nextLine().trim();
                        if (curriculumYearLevel.equalsIgnoreCase("X")) {
                            curriculumYearLevel = "";
                            break;
                        }
                        else if (curriculumYearLevel.isEmpty()) UI.print("Invalid Input");
                    } while (curriculumYearLevel.isEmpty());
                    break;

                case "4":
                    do {
                        UI.select("Enter Curriculum Term (1-2)", true);
                        String input = in.nextLine().trim();
                        if (input.equalsIgnoreCase("X")) {
                            input = "";
                            break;
                        }
                        else if (input.isEmpty()) UI.print("Curriculum Term is required");
                        else {
                            try {
                                curriculumTerm = Integer.parseInt(input);
                                if (!(curriculumTerm >= 1 && curriculumTerm <= 3)) UI.print("Invalid Curriculum Term");
                            } catch (NumberFormatException e) {
                                UI.print("Invalid Curriculum Term");
                                curriculumTerm = -1;
                            }
                        }
                    } while (!(curriculumTerm >= 1 && curriculumTerm <= 2));
                    break;

                case "/":
                    if (programCode.isEmpty() || courseCode.isEmpty() || curriculumYearLevel.isEmpty() ||
                            curriculumTerm == -1) {
                        UI.print("Details are incomplete");
                    }
                    else if (findCurriculumByProgramCourse(programCode, courseCode) != null) UI.print("Course is already included in this program");
                    else {
                        curricula.add(new Curriculum(programCode, courseCode, curriculumYearLevel, curriculumTerm));
                        UI.print("Curriculum entry successfully created");
                        submit = true;
                    }
                    break;

                case "X":
                    submit = true;
                    break;
            }
        }
    }
    public static void editCurriculum() {
        String input, choice, curriculumYearLevel;
        int curriculumTerm;
        boolean submit = false;

        UI.print("EDIT EXISTING CURRICULUM ENTRY");

        do {
            UI.select("Enter Curriculum ID", false);
            input = in.nextLine().trim();
            if (input.equalsIgnoreCase("X")) return;
            else if (input.isEmpty()) UI.print("Curriculum ID is required");
            else if (findCurriculum(input) == null) UI.print("Curriculum entry does not exist");
        } while (input.isEmpty() || findCurriculum(input) == null);

        Curriculum c = findCurriculum(input);
        curriculumYearLevel = c.getCurriculumYear();
        curriculumTerm = c.getCurriculumTerm();

        while (!submit) {
            curriculumManage("EDIT", c.getCurriculumID(), c.getProgramCode(), c.getCourseCode(),
                    curriculumYearLevel, curriculumTerm);

            do {
                UI.select("Select", false);
                choice = in.nextLine().trim();
                if (choice.matches("[1-2]")) UI.print("Program Code, and Course Code cannot be edited");
                else if (!choice.matches("[3-4X]") && !choice.equals("/")) UI.print("Invalid Input");
            } while (!choice.matches("[3-4X]") && !choice.equals("/"));

            switch (choice) {
                case "3":
                    do {
                        UI.select("Enter Year Level", true);
                        curriculumYearLevel = in.nextLine().trim();
                        if (curriculumYearLevel.equalsIgnoreCase("X")) {
                            curriculumYearLevel = c.curriculumYear;
                            break;
                        }
                        else if (curriculumYearLevel.isEmpty()) UI.print("Invalid Input");
                    } while (curriculumYearLevel.isEmpty());
                    break;

                case "4":
                    do {
                        UI.select("Enter Curriculum Term (1-2)", true);
                        String inp = in.nextLine().trim();
                        if (inp.equalsIgnoreCase("X")) {
                            curriculumTerm = c.curriculumTerm;
                            break;
                        }
                        else if (inp.isEmpty()) UI.print("Curriculum Term is required");
                        else {
                            try {
                                curriculumTerm = Integer.parseInt(inp);
                                if (!(curriculumTerm >= 1 && curriculumTerm <= 2))
                                    UI.print("Invalid Curriculum Term");
                            } catch (NumberFormatException e) {
                                UI.print("Invalid Curriculum Term");
                                curriculumTerm = -1;
                            }
                        }
                    } while (!(curriculumTerm >= 1 && curriculumTerm <= 2));
                    break;

                case "/":
                    c.curriculumYear = curriculumYearLevel;
                    c.curriculumTerm = curriculumTerm;
                    UI.print("Curriculum entry successfully edited");
                    submit = true;
                    break;

                case "X":
                    submit = true;
                    break;
            }
        }
    }

    public static void archiveCurriculum() {
        String input, choice;

        UI.print("ARCHIVE CURRICULUM ENTRY");

        do {
            UI.select("Enter Curriculum ID", false);
            input = in.nextLine().trim();
            if (input.equalsIgnoreCase("X")) return;
            else if (input.isEmpty()) UI.print("Curriculum ID is required");
            else if (findCurriculum(input) == null) UI.print("Curriculum entry does not exist");
        } while (input.isEmpty() || findCurriculum(input) == null);

        Curriculum c = findCurriculum(input);
        displayCurriculum(c.getCurriculumID(),false);

        do {
            UI.select("Confirm Curriculum Deletion? (Y/N)", false);
            choice = in.nextLine().trim().toUpperCase();
            if (choice.equals("X")) return;
            if (!choice.matches("[YN]")) UI.print("Invalid Input");
        } while (!choice.matches("[YN]"));

        if (choice.equals("Y")) {
            archiveCurricula.add(c);
            curricula.remove(c);
            UI.print("Curriculum entry successfully deleted");
        }
        else UI.print("Archiving cancelled");
    }
    private static void curriculumManage(String text, String curriculumID, String programCode,
                                         String courseCode, String curriculumYearLevel, int curriculumTerm) {

        UI.header(text.toUpperCase() + ": CURRICULUM INFORMATION", false);
        UI.liner(String.format("     CURRICULUM ID  : %s", curriculumID.isEmpty() ?
                String.format("CUR%04d", curricula.size() + 1) : curriculumID), false);
        UI.liner(String.format(" [1] PROGRAM CODE   : %s", programCode), false);
        UI.liner(String.format(" [2] COURSE CODE    : %s", courseCode), false);
        UI.liner(String.format(" [3] YEAR LEVEL     : %s", curriculumYearLevel), false);
        UI.liner(String.format(" [4] TERM           : %s", curriculumTerm == -1 ? "" : curriculumTerm), false);
        UI.liner("", false);
        UI.option();
        UI.footer(false);
    }
}