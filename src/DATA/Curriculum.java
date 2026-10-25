package DATA;

import UI.*;
import static UI.UI.in;
import java.util.ArrayList;

public class Curriculum {

    private final String programCode, courseCode;
    private String curriculumYearLevel;
    private int curriculumTerm;

    public static ArrayList<Curriculum> curricula = new ArrayList<>();
    public static ArrayList<Curriculum> archiveCurricula = new ArrayList<>();

    public Curriculum(String programCode, String courseCode,
                      String curriculumYearLevel, int curriculumTerm) {

        if (programCode == null || programCode.isBlank())
            throw new IllegalArgumentException("Program code cannot be empty");

        if (courseCode == null || courseCode.isBlank())
            throw new IllegalArgumentException("Course Code cannot be empty");

        programCode = programCode.trim();
        courseCode = courseCode.trim();

        if (Program.findProgram(programCode) == null)
            throw new IllegalArgumentException("Program not found");

        if (Course.findCourse(courseCode) == null)
            throw new IllegalArgumentException("Course not found");

        if (curriculumYearLevel == null || curriculumYearLevel.isBlank())
            throw new IllegalArgumentException("Year level cannot be empty");

        if (curriculumTerm < 1 || curriculumTerm > 3)
            throw new IllegalArgumentException("Invalid curriculum term");

        this.programCode = programCode;
        this.courseCode = courseCode;
        this.curriculumYearLevel = curriculumYearLevel.trim();
        this.curriculumTerm = curriculumTerm;
    }

    static {
        curricula.add(new Curriculum("BSIT", "IT101", "1", 1));
        curricula.add(new Curriculum("BSIT", "IT102", "1", 2));
        curricula.add(new Curriculum("BSIT", "IT103", "2", 1));
        curricula.add(new Curriculum("BSIT", "IT104", "2", 2));
        curricula.add(new Curriculum("BSIT", "IT105", "3", 1));

        curricula.add(new Curriculum("BSIS", "IS102", "1", 1));
        curricula.add(new Curriculum("BSIS", "IS101", "2", 1));
        curricula.add(new Curriculum("BSIS", "IT101", "1", 2));

        curricula.add(new Curriculum("BLIS", "BL101", "1", 1));
        curricula.add(new Curriculum("BLIS", "IT101", "1", 2));

        curricula.add(new Curriculum("BSCrim", "CR101", "1", 1));
        curricula.add(new Curriculum("BSCrim", "CR102", "2", 1));

        curricula.add(new Curriculum("BSTM", "TM101", "1", 1));
        curricula.add(new Curriculum("BSHM", "HM101", "1", 1));

        curricula.add(new Curriculum("BSN", "NS101", "1", 1));
        curricula.add(new Curriculum("BSN", "NS102", "2", 1));

        curricula.add(new Curriculum("BSCE", "CE101", "1", 1));
        curricula.add(new Curriculum("BSElecEng", "CE102", "2", 1));

        curricula.add(new Curriculum("BSBio", "BIO101", "1", 1));
        curricula.add(new Curriculum("BSPsych", "PS101", "1", 1));

        curricula.add(new Curriculum("BSIT", "GE101", "1", 1));
        curricula.add(new Curriculum("BSIT", "GE102", "1", 1));
        curricula.add(new Curriculum("BSIT", "GE103", "1", 2));
        curricula.add(new Curriculum("BSIT", "GE104", "1", 2));
        curricula.add(new Curriculum("BSIT", "PE101", "1", 1));

        curricula.add(new Curriculum("BLIS", "GE101", "1", 1));
        curricula.add(new Curriculum("BLIS", "GE102", "1", 1));
        curricula.add(new Curriculum("BLIS", "GE105", "1", 2));
        curricula.add(new Curriculum("BLIS", "PE101", "1", 1));

        curricula.add(new Curriculum("BSIS", "GE101", "1", 1));
        curricula.add(new Curriculum("BSIS", "GE103", "1", 1));
        curricula.add(new Curriculum("BSIS", "GE106", "1", 2));
        curricula.add(new Curriculum("BSIS", "PE101", "1", 1));

        curricula.add(new Curriculum("BSArch", "GE102", "1", 1));
        curricula.add(new Curriculum("BSArch", "GE107", "1", 2));
        curricula.add(new Curriculum("BSArch", "PE101", "1", 1));

        curricula.add(new Curriculum("BSBroad", "GE103", "1", 1));
        curricula.add(new Curriculum("BSBroad", "GE108", "1", 2));
        curricula.add(new Curriculum("BSBroad", "GE109", "1", 2));
        curricula.add(new Curriculum("BSBroad", "PE101", "1", 1));
    }

    // INSTANCE GETTER METHODS
    public String getProgramCode() {
        return programCode;
    }
    public String getCourseCode() {
        return courseCode;
    }
    public String getCurriculumYearLevel() {
        return curriculumYearLevel;
    }
    public int getCurriculumTerm() {
        return curriculumTerm;
    }

    // FIND USING COMPOSITE PRIMARY KEY
    public static Curriculum findCurriculum(String programCode, String courseCode) {

        if (programCode == null || courseCode == null)
            return null;

        programCode = programCode.trim();
        courseCode = courseCode.trim();

        for (Curriculum c : curricula) {
            if (programCode.equalsIgnoreCase(c.getProgramCode())
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
            if (programCode.equalsIgnoreCase(c.getProgramCode())) {
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
            if (courseCode.equalsIgnoreCase(c.getCourseCode())) {
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
        UI.liner("  PROGRAM CODE  : " + programCode);
        UI.liner("  PROGRAM NAME  : " + program.getProgramDescription());
        UI.separator();

        if (courses.isEmpty()) {
            UI.liner("  No courses found in this curriculum.");
        } else {
            for (Course c : courses) {
                Curriculum entry = findCurriculum(programCode, c.getCourseCode());

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

    // DISPLAY METHODS
    public static void displayCurriculum(String programCode, String courseCode) {
        Curriculum c = findCurriculum(programCode, courseCode);
        if (c == null) {
            UI.print("Curriculum does not exist");
            return;
        }

        UI.header("CURRICULUM");
        UI.liner(String.format("  PROGRAM CODE  : %s", c.getProgramCode()));
        UI.liner(String.format("  COURSE CODE   : %s", c.getCourseCode()));
        UI.liner(String.format("  YEAR LEVEL    : %s", c.getCurriculumYearLevel()));
        UI.liner(String.format("  TERM          : %d", c.getCurriculumTerm()));
        UI.separator();
    }
    public static void displayCurricula() {
        UI.header("ALL CURRICULUM ENTRIES");

        if (curricula.isEmpty()) {
            UI.liner("  No curriculum entries found.");
        } else {
            for (Curriculum c : curricula) {
                UI.liner(String.format("  PROGRAM CODE  : %s", c.getProgramCode()));
                UI.liner(String.format("  COURSE CODE   : %s", c.getCourseCode()));
                UI.liner(String.format("  YEAR LEVEL    : %s", c.getCurriculumYearLevel()));
                UI.liner(String.format("  TERM          : %d", c.getCurriculumTerm()));
                UI.separator();
            }
        }

        UI.footer();
    }
    public static void displayArchiveCurricula() {

        UI.header("ARCHIVED CURRICULA");

        if (archiveCurricula.isEmpty()) {
            UI.liner("  No archived buildings found.");
        } else {
            for (Curriculum c : archiveCurricula) {
                UI.liner(String.format(
                        "  %-15s %-20s %-10s %10s",
                        c.programCode,
                        c.courseCode,
                        c.curriculumYearLevel,
                        c.curriculumTerm
                ));
            }
        }

        UI.footer();

    }

    // MANAGE CURRICULUM
    public static void createCurriculum() {
        String programCode = ""; String courseCode = "";
        String curriculumYearLevel = ""; String choice;
        int curriculumTerm = -1; boolean submit = false;

        while (!submit) {
            curriculumManage("CREATE", programCode, courseCode,
                    curriculumYearLevel, curriculumTerm);

            do {
                System.out.print("  Select > ");
                choice = in.nextLine().trim();
                if (!choice.matches("[1-4X]") && !choice.equals("/"))
                    UI.print("Invalid Choice");
            } while (!choice.matches("[1-4X]") && !choice.equals("/"));

            switch (choice) {
                case "1":
                    do {
                        System.out.print("   Enter Program Code > ");
                        programCode = in.nextLine().trim();
                        if (programCode.isEmpty())
                            UI.print("Invalid Input");
                        else if (Program.findProgram(programCode) == null)
                            UI.print("Program does not exist");
                    } while (programCode.isEmpty()
                            || Program.findProgram(programCode) == null);
                    break;

                case "2":
                    do {
                        System.out.print("   Enter Course Code > ");
                        courseCode = in.nextLine().trim();
                        if (courseCode.isEmpty())
                            UI.print("Invalid Input");
                        else if (Course.findCourse(courseCode) == null)
                            UI.print("Course does not exist");
                        else if (findCurriculum(programCode, courseCode) != null)
                            UI.print("Course is already included in this program");
                    } while (courseCode.isEmpty()
                            || Course.findCourse(courseCode) == null
                            || findCurriculum(programCode, courseCode) != null);
                    break;

                case "3":
                    do {
                        System.out.print("   Enter Year Level > ");
                        curriculumYearLevel = in.nextLine().trim();
                        if (curriculumYearLevel.isEmpty())
                            UI.print("Invalid Input");
                    } while (curriculumYearLevel.isEmpty());
                    break;

                case "4":
                    do {
                        System.out.print("   Enter Curriculum Term (1-2) > ");
                        try {
                            String input = in.nextLine().trim();
                            curriculumTerm = Integer.parseInt(input);
                            if (!(curriculumTerm >= 1 && curriculumTerm <= 3))
                                UI.print("Invalid Curriculum Term");
                        } catch (NumberFormatException e) {
                            UI.print("Invalid Curriculum Term");
                            curriculumTerm = -1;
                        }
                    } while (!(curriculumTerm >= 1 && curriculumTerm <= 2));
                    break;

                case "/":
                    if (programCode.isEmpty() || courseCode.isEmpty()
                            || curriculumYearLevel.isEmpty() || curriculumTerm == -1) {
                        UI.print("Details are incomplete");
                    }
                    else if (findCurriculum(programCode, courseCode) == null) UI.print("Curriculum does not exist");
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
        String input; String choice;
        String programCode; String courseCode;
        String curriculumYearLevel; int curriculumTerm;
        boolean submit = false;

        UI.print("EDIT EXISTING CURRICULUM ENTRY");

        do {
            System.out.print("  Enter Program Code > ");
            input = in.nextLine().trim();
            if (input.isEmpty())
                UI.print("Invalid Input");
            else if (Program.findProgram(input) == null)
                UI.print("Program does not exist");
        } while (input.isEmpty() || Program.findProgram(input) == null);

        programCode = input;

        do {
            System.out.print("  Enter Course Code > ");
            input = in.nextLine().trim();
            if (input.isEmpty())
                UI.print("Invalid Input");
            else if (Course.findCourse(input) == null)
                UI.print("Course does not exist");
            else if (findCurriculum(programCode, input) == null)
                UI.print("Curriculum entry does not exist");
        } while (input.isEmpty() || Course.findCourse(input) == null
                || findCurriculum(programCode, input) == null);

        courseCode = input;

        Curriculum c = findCurriculum(programCode, courseCode);
        if (c == null) {
            UI.print("Curriculum entry not found");
            return;
        }

        curriculumYearLevel = c.getCurriculumYearLevel();
        curriculumTerm = c.getCurriculumTerm();

        while (!submit) {
            curriculumManage("EDIT", programCode, courseCode,
                    curriculumYearLevel, curriculumTerm);

            do {
                System.out.print("  Select > ");
                choice = in.nextLine().trim();
                if (choice.matches("[1-2]")) UI.print("Curriculum fields cannot be edited");
                else if (!choice.matches("[3-4X]") && !choice.equals("/"))
                    UI.print("Invalid Input");
            } while (!choice.matches("[3-4X]") && !choice.equals("/") && choice.matches("[1-2]"));

            switch (choice) {
                case "3":
                    do {
                        System.out.print("   Enter Year Level > ");
                        curriculumYearLevel = in.nextLine().trim();
                        if (curriculumYearLevel.isEmpty())
                            UI.print("Invalid Input");
                    } while (curriculumYearLevel.isEmpty());
                    break;

                case "4":
                    do {
                        System.out.print("   Enter Curriculum Term (1-2) > ");
                        try {
                            input = in.nextLine().trim();
                            curriculumTerm = Integer.parseInt(input);
                            if (!(curriculumTerm >= 1 && curriculumTerm <= 2))
                                UI.print("Invalid Curriculum Term");
                        } catch (NumberFormatException e) {
                            UI.print("Invalid Curriculum Term");
                            curriculumTerm = -1;
                        }
                    } while (!(curriculumTerm >= 1 && curriculumTerm <= 2));
                    break;

                case "/":
                    if (curriculumYearLevel.isEmpty() || curriculumTerm == -1) {
                        UI.print("Details are incomplete");
                    } else {
                        c.curriculumYearLevel = curriculumYearLevel;
                        c.curriculumTerm = curriculumTerm;
                        UI.print("Curriculum entry successfully edited");
                        submit = true;
                    }
                    break;

                case "X":
                    submit = true;
                    break;
            }
        }
    }
    public static void archiveCurriculum() {
        String input; String choice; String programCode; String courseCode;

        UI.print("DELETE CURRICULUM ENTRY");

        do {
            System.out.print("  Enter Program Code > ");
            input = in.nextLine().trim();
            if (input.isEmpty())
                UI.print("Invalid Input");
            else if (Program.findProgram(input) == null)
                UI.print("Program does not exist");
        } while (input.isEmpty() || Program.findProgram(input) == null);

        programCode = input;

        do {
            System.out.print("  Enter Course Code > ");
            input = in.nextLine().trim();
            if (input.isEmpty())
                UI.print("Invalid Input");
            else if (Course.findCourse(input) == null)
                UI.print("Course does not exist");
            else if (findCurriculum(programCode, input) == null)
                UI.print("Curriculum entry does not exist");
        } while (input.isEmpty() || Course.findCourse(input) == null
                || findCurriculum(programCode, input) == null);

        courseCode = input;

        Curriculum c = findCurriculum(programCode, courseCode);
        if (c == null) {
            UI.print("Curriculum entry not found");
            return;
        }

        displayCurriculum(c.getProgramCode(), c.getCourseCode());

        do {
            System.out.print("  Confirm Curriculum Deletion? (Y/N) > ");
            choice = in.nextLine().trim().toUpperCase();
            if (!choice.matches("[YN]"))
                UI.print("Invalid Input");
        } while (!choice.matches("[YN]"));

        if (choice.equals("Y")) {
            archiveCurricula.add(c);
            curricula.remove(c);
            UI.print("Curriculum entry successfully deleted");
        }
    }

    // CURRICULUM FORM
    private static void curriculumManage(String text, String programCode,
                                         String courseCode, String curriculumYearLevel, int curriculumTerm) {

        UI.header(text.toUpperCase() + ": CURRICULUM INFORMATION");
        UI.liner(String.format(" [1] PROGRAM CODE : %s", programCode));
        UI.liner(String.format(" [2] COURSE CODE  : %s", courseCode));
        UI.liner(String.format(" [3] YEAR LEVEL   : %s", curriculumYearLevel));
        UI.liner(String.format(" [4] TERM         : %s", curriculumTerm == -1 ? "" : curriculumTerm));
        UI.liner("");
        UI.option();
        UI.footer();
    }


}