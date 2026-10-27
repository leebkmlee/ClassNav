
package DATA;

import UI.*;
import USER.*;

import java.util.ArrayList;

import static UI.UI.in;

public class CourseOffering {

    // FIELDS
    private static int nextOfferingID = 1;

    private final String offeringID;
    private String sectionID, courseCode, facultyID, academicYear; // FOREIGN KEY
    private int term;

    public static ArrayList<CourseOffering> offerings = new ArrayList<>();
    public static ArrayList<CourseOffering> archivedOfferings = new ArrayList<>();

    // CONSTRUCTOR
    public CourseOffering(String sectionID, String courseCode, String facultyID,
                          String academicYear, int term) {
        this.offeringID = String.format("COF%03d", nextOfferingID++);
        this.sectionID = sectionID;
        this.courseCode = courseCode;
        this.facultyID = facultyID;
        this.academicYear = academicYear;
        this.term = term;
    }

    // DUMMY DATA
    static {
        offerings.add(new CourseOffering("SEC001", "IT101", "F0001", "2025-2026", 1));
        offerings.add(new CourseOffering("SEC001", "GE101", "F0002", "2025-2026", 1));
        offerings.add(new CourseOffering("SEC001", "PE101", "F0003", "2025-2026", 1));
        offerings.add(new CourseOffering("SEC002", "IT102", "F0002", "2024-2025", 2));
        offerings.add(new CourseOffering("SEC002", "GE103", "F0007", "2024-2025", 2));
        offerings.add(new CourseOffering("SEC003", "IT103", "F0001", "2026-2027", 1));
        offerings.add(new CourseOffering("SEC003", "GE101", "F0002", "2026-2027", 1));
        offerings.add(new CourseOffering("SEC004", "IT104", "F0003", "2025-2026", 2));
        offerings.add(new CourseOffering("SEC004", "GE104", "F0004", "2025-2026", 2));
        offerings.add(new CourseOffering("SEC005", "IS102", "F0007", "2024-2025", 1));
        offerings.add(new CourseOffering("SEC005", "GE102", "F0002", "2024-2025", 1));
        offerings.add(new CourseOffering("SEC006", "IS101", "F0001", "2026-2027", 1));
        offerings.add(new CourseOffering("SEC006", "GE106", "F0002", "2026-2027", 2));
        offerings.add(new CourseOffering("SEC007", "BL101", "F0003", "2025-2026", 1));
        offerings.add(new CourseOffering("SEC008", "CR101", "F0004", "2024-2025", 1));
        offerings.add(new CourseOffering("SEC009", "TM101", "F0007", "2026-2027", 1));
        offerings.add(new CourseOffering("SEC010", "HM101", "F0007", "2025-2026", 1));
        offerings.add(new CourseOffering("SEC011", "NS101", "F0010", "2024-2025", 1));
        offerings.add(new CourseOffering("SEC012", "CE101", "F0011", "2026-2027", 1));
        offerings.add(new CourseOffering("SEC013", "BIO101", "F0013", "2025-2026", 1));
        offerings.add(new CourseOffering("SEC014", "IT105", "F0005", "2026-2027", 1));
        offerings.add(new CourseOffering("SEC015", "GE105", "F0006", "2026-2027", 1));
    }

    // GETTER METHODS
    public String getOfferingID() {
        return offeringID;
    }
    public String getSectionID() {
        return sectionID;
    }
    public String getCourseCode() {
        return courseCode;
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

    // FIND METHOD
    public static CourseOffering findOffering(String offeringID) {

        if (offeringID == null || offeringID.trim().isEmpty())
            return null;

        for (CourseOffering o : offerings) {
            if (offeringID.trim().equalsIgnoreCase(o.getOfferingID()))
                return o;
        }

        return null;
    }

    // RETRIEVE OFFERINGS BY FACULTY
    public static ArrayList<CourseOffering> getOfferingsByFaculty(String facultyID) {
        ArrayList<CourseOffering> result = new ArrayList<>();
        if (facultyID == null || facultyID.trim().isEmpty()) return result;
        facultyID = facultyID.trim();
        if (Faculty.findFaculty(facultyID) == null) return result;
        for (CourseOffering o : offerings) if (facultyID.equalsIgnoreCase(o.getFacultyID())) result.add(o);
        return result;
    }

    // DISPLAY METHODS
    public static void displayOffering(String offeringID, boolean print) {

        CourseOffering o = findOffering(offeringID);

        if (o == null) {
            UI.print("Course offering not found.");
            return;
        }

        UI.header("COURSE OFFERING DETAILS");
        UI.liner(String.format("  OFFERING ID    : %s", o.getOfferingID()));
        UI.liner(String.format("  SECTION ID     : %s", o.getSectionID()));
        UI.liner(String.format("  COURSE CODE    : %s", o.getCourseCode()));
        UI.liner(String.format("  FACULTY ID     : %s", o.getFacultyID()));
        UI.liner(String.format("  ACADEMIC YEAR  : %s", o.getAcademicYear()));
        UI.liner(String.format("  TERM           : %d", o.getTerm()));
        UI.footer();
        if (print) UI.returnButton();
    }
    public static void displayOfferings() {
        UI.header("COURSE OFFERINGS");
        if (offerings.isEmpty()) UI.liner("  No Course Offerings found.");
        else {
            for (int i = 0; i < offerings.size(); i++) {
                CourseOffering o = offerings.get(i);

                Section section = Section.findSection(o.getSectionID());
                Course course = Course.findCourse(o.getCourseCode());
                Faculty faculty = Faculty.findFaculty(o.getFacultyID());

                UI.liner(String.format("  OFFERING ID    : %s", o.getOfferingID()));
                UI.liner(String.format("  SECTION        : %s", Section.getYearSection(section)));
                UI.liner(String.format("  COURSE         : %s", course.getCourseCode()));
                UI.liner(String.format("  FACULTY ID     : %s", o.getFacultyID()));
                UI.liner(String.format("  FACULTY        : %s", faculty.getUserName()));
                UI.liner(String.format("  ACADEMIC YEAR  : %s", o.getAcademicYear()));
                UI.liner(String.format("  TERM           : %d", o.getTerm()));

                if (i < offerings.size() - 1) UI.separator();
            }
        }

        UI.footer();
        UI.returnButton();
    }
    public static void displayArchivedOfferings() {

        UI.header("ARCHIVED COURSE OFFERINGS");
        if (archivedOfferings.isEmpty()) UI.liner("  No Archived Course Offerings found.");
        else {
            for (int i = 0; i < archivedOfferings.size(); i++) {
                CourseOffering o = archivedOfferings.get(i);

                Section section = Section.findSection(o.getSectionID());
                Course course = Course.findCourse(o.getCourseCode());
                Faculty faculty = Faculty.findFaculty(o.getFacultyID());

                UI.liner(String.format("  OFFERING ID    : %s", o.getOfferingID()));
                UI.liner(String.format("  SECTION        : %s", Section.getYearSection(section)));
                UI.liner(String.format("  COURSE         : %s", course.getCourseCode()));
                UI.liner(String.format("  FACULTY ID     : %s", o.getFacultyID()));
                UI.liner(String.format("  FACULTY        : %s", faculty.getUserName()));
                UI.liner(String.format("  ACADEMIC YEAR  : %s", o.getAcademicYear()));
                UI.liner(String.format("  TERM           : %d", o.getTerm()));

                if (i < archivedOfferings.size() - 1) UI.separator();
            }
        }
        UI.footer();
        UI.returnButton();
    }

    // MANAGE METHODS
    public static void createCourseOffering() {
        String sectionID = "", courseCode = "", facultyID = "",academicYear = "", choice;
        int term = -1; boolean submit = false;

        while (!submit) {
            courseOfferingManage("CREATE", "", sectionID, courseCode, facultyID, academicYear, term);

            do {
                UI.select("Select",false);
                choice = in.nextLine().trim();
                if(choice.equalsIgnoreCase("X")){
                    return;
                }

                if (!choice.matches("[1-5X]") && !choice.equals("/")) UI.print("Invalid Choice");
            } while (!choice.matches("[1-5X]") && !choice.equals("/"));

            switch (choice) {
                case "1":
                    do {
                        UI.select("Enter Section ID",true);
                        sectionID = in.nextLine().trim();
                        if (sectionID.equalsIgnoreCase("X")) {
                            sectionID = "";
                            break;
                        }
                        else if (sectionID.trim().isEmpty()){
                            UI.print("Section ID is required.");
                            return;
                        }
                        else if (Section.findSection(sectionID) == null) UI.print("Section does not exist");
                    } while (sectionID.isEmpty() || Section.findSection(sectionID) == null);
                    break;

                case "2":
                    do {
                        UI.select("Enter Course Code",true);
                        courseCode = in.nextLine().trim();
                        if (courseCode.equalsIgnoreCase("X")) {
                            courseCode = "";
                            break;
                        }
                        else if (courseCode.trim().isEmpty()){
                            UI.print("Course Code is required.");
                            return;
                        }
                        else if (Course.findCourse(courseCode) == null) UI.print("Course does not exist");
                    } while (courseCode.isEmpty() || Course.findCourse(courseCode) == null);
                    break;

                case "3":
                    do {
                        UI.select("Enter Faculty ID",true);
                        facultyID = in.nextLine().trim();
                        if (facultyID.equalsIgnoreCase("X")) {
                            facultyID = "";
                            break;
                        }
                        else if (facultyID.trim().isEmpty()){
                            UI.print("Faculty ID is required.");
                            return;
                        }
                        else if (Faculty.findFaculty(facultyID) == null) UI.print("Faculty does not exist");
                    } while (facultyID.isEmpty() || Faculty.findFaculty(facultyID) == null);
                    break;

                case "4":
                    int startYear; int endYear;
                    do {
                        UI.select("Enter Academic Year",true);
                        academicYear = in.nextLine().trim();
                        if (academicYear.equalsIgnoreCase("X")) {
                            academicYear = "";
                            break;
                        }
                        else if (academicYear.trim().isEmpty()){
                            UI.print("Academic year is required.");
                            return;
                        }
                        else if (!academicYear.trim().matches("\\d{4}-\\d{4}"))
                            UI.print("Academic year must use the format YYYY-YYYY.");

                        String[] years = academicYear.trim().split("-");

                        startYear = Integer.parseInt(years[0]);
                        endYear = Integer.parseInt(years[1]);

                        if (endYear != startYear + 1) UI.print("Academic year must contain consecutive years.");
                    } while (!academicYear.matches("\\d{4}-\\d{4}") || endYear != startYear + 1);
                    break;

                case "5":
                    do {
                        System.out.print("   Enter Term (1-2) > ");
                        try {
                            String input = in.nextLine().trim();
                            term = Integer.parseInt(input);
                            if (!(term >= 1 && term <= 2)) UI.print("Invalid Term");
                        } catch (NumberFormatException e) {
                            UI.print("Invalid Term");
                            term = -1;
                        }
                    } while (!(term >= 0 && term <= 2));
                    break;

                case "/":
                    boolean existing = false;
                    if (sectionID.isEmpty() || courseCode.isEmpty() || facultyID.isEmpty() || academicYear.isEmpty()
                            || term == -1) {
                        UI.print("Details are incomplete");
                    }
                    else {
                        for (CourseOffering o : offerings) {
                            if (sectionID.equalsIgnoreCase(o.getSectionID())
                                    && courseCode.equalsIgnoreCase(o.getCourseCode())
                                    && academicYear.equalsIgnoreCase(o.getAcademicYear())
                                    && term == o.getTerm()) {
                                UI.print("Course Offering has an existing duplicate copy");
                                existing = true;
                            }
                        }

                        if (!existing) {
                            UI.print("Course Offering successfully created");
                            offerings.add(new CourseOffering(sectionID, courseCode, facultyID, academicYear, term));
                            CourseOffering o = offerings.getLast();
                            displayOffering(o.getOfferingID(), true);
                            submit = true;
                        }
                    }
                    break;

                case "X":
                    submit = true;
                    break;
            }
        }
    }
    public static void editCourseOffering() {
        String input, choice, sectionID, courseCode, facultyID, academicYear;
        int term; boolean submit = false;

        UI.print("EDIT EXISTING COURSE OFFERING");

        do {
            UI.select("Enter Offering ID",false);
            input = in.nextLine().trim();
            if (input.equalsIgnoreCase("X")) return;
            else if (input.isEmpty()) UI.print("Offering ID is required");
            else if (findOffering(input) == null) UI.print("Course offering does not exist");
        } while (input.isEmpty() || findOffering(input) == null);

        CourseOffering o = findOffering(input);
        if (o == null) {
            UI.print("Course offering not found");
            return;
        }

        sectionID = o.getSectionID();
        courseCode = o.getCourseCode();
        facultyID = o.getFacultyID();
        academicYear = o.getAcademicYear();
        term = o.getTerm();

        while (!submit) {
            courseOfferingManage("EDIT", o.getOfferingID(), sectionID, courseCode, facultyID, academicYear, term);

            do {
                UI.select("Select",false);
                choice = in.nextLine().trim();

                if (!choice.matches("[1-5X]") && !choice.equals("/")) UI.print("Invalid Choice");
            } while (!choice.matches("[1-5X]") && !choice.equals("/"));

            switch (choice) {
                case "1":
                    do {
                        UI.select("Enter Section ID",true);
                        sectionID = in.nextLine().trim();
                        if (sectionID.equalsIgnoreCase("X")) {
                            sectionID = o.getSectionID();
                            break;
                        }
                        else if (sectionID.trim().isEmpty()){
                            UI.print("Section ID is required.");
                            return;
                        }
                        else if (Section.findSection(sectionID) == null) UI.print("Section does not exist");
                    } while (sectionID.isEmpty() || Section.findSection(sectionID) == null);
                    break;

                case "2":
                    do {
                        UI.select("Enter Course Code",true);
                        courseCode = in.nextLine().trim();
                        if (courseCode.equalsIgnoreCase("X")) {
                            courseCode = o.getCourseCode();
                            break;
                        }
                        else if (courseCode.trim().isEmpty()){
                            UI.print("Course Code is required.");
                            return;
                        }
                        else if (Course.findCourse(courseCode) == null) UI.print("Course does not exist");
                    } while (courseCode.isEmpty() || Course.findCourse(courseCode) == null);
                    break;

                case "3":
                    do {
                        UI.select("Enter Faculty ID",true);
                        facultyID = in.nextLine().trim();
                        if (facultyID.equalsIgnoreCase("X")) {
                            facultyID = o.getFacultyID();
                            break;
                        }
                        else if (facultyID.trim().isEmpty()){
                            UI.print("Faculty ID is required.");
                            return;
                        }
                        else if (Faculty.findFaculty(facultyID) == null) UI.print("Faculty does not exist");
                    } while (facultyID.isEmpty() || Faculty.findFaculty(facultyID) == null);
                    break;

                case "4":
                    int startYear; int endYear;
                    do {
                        UI.select("Enter Academic Year",true);
                        academicYear = in.nextLine().trim();
                        if (academicYear.equalsIgnoreCase("X")) {
                            academicYear = o.getAcademicYear();
                            break;
                        }
                        else if (academicYear.trim().isEmpty()){
                            UI.print("Academic year is required.");
                            return;
                        }
                        else if (!academicYear.trim().matches("\\d{4}-\\d{4}"))
                            UI.print("Academic year must use the format YYYY-YYYY.");

                        String[] years = academicYear.trim().split("-");

                        startYear = Integer.parseInt(years[0]);
                        endYear = Integer.parseInt(years[1]);

                        if (endYear != startYear + 1) UI.print("Academic year must contain consecutive years.");
                    } while (!academicYear.matches("\\d{4}-\\d{4}") || endYear != startYear + 1);
                    break;

                case "5":
                    do {
                        UI.select("Enter Term (1-2)", true);
                        input = in.nextLine().trim();
                        if (input.equalsIgnoreCase("X")) {
                            term = o.getTerm();
                            break;
                        }
                        else if (input.trim().isEmpty()){
                            UI.print("Term is required.");
                            return;
                        }
                        try {
                            term = Integer.parseInt(input);
                            if (!(term >= 1 && term <= 3))
                                UI.print("Invalid Term");
                        } catch (NumberFormatException e) {
                            UI.print("Invalid Term");
                            term = -1;
                        }
                    } while (!(term >= 1 && term <= 2));
                    break;

                case "/":
                    boolean duplicate = false;

                    for (CourseOffering existing : offerings) {
                        if (existing != o
                                && sectionID.equalsIgnoreCase(existing.getSectionID())
                                && courseCode.equalsIgnoreCase(existing.getCourseCode())
                                && academicYear.equalsIgnoreCase(existing.getAcademicYear())
                                && term == existing.getTerm()) {
                            duplicate = true;
                            break;
                        }
                    }

                    if (duplicate) {
                        UI.print("This course is already offered to this section for that academic year and term.");
                    } else {
                        o.sectionID = sectionID;
                        o.courseCode = courseCode;
                        o.facultyID = facultyID;
                        o.academicYear = academicYear;
                        o.term = term;

                        UI.print("Course offering successfully edited");
                        displayOffering(o.getOfferingID(), true);
                        submit = true;
                    }
                    break;

                case "X":
                    submit = true;
                    break;
            }
        }
    }
    public static void archiveCourseOffering() {
        String input; String choice;

        UI.print("DELETE EXISTING COURSE OFFERING");

        do {
            UI.select("Enter Offering ID",false);
            input = in.nextLine().trim();
            if (input.equalsIgnoreCase("X")) return;
            else if (input.isEmpty()) UI.print("Offering ID is required");

            if (input.isEmpty())
                UI.print("Invalid Input");
            else if (findOffering(input) == null)
                UI.print("Course offering does not exist");
        } while (input.isEmpty() || findOffering(input) == null);

        CourseOffering o = findOffering(input);
        if (o == null) {
            UI.print("Course offering not found");
            return;
        }

        displayOffering(o.getOfferingID(), false);

        do {
            System.out.print("  Confirm Course Offering Deletion? (Y/N) > ");
            choice = in.nextLine().trim().toUpperCase();
            if (!choice.matches("[YN]"))
                UI.print("Invalid Input");
        } while (!choice.matches("[YN]"));

        if (choice.equals("Y")) {
            archivedOfferings.add(o);
            offerings.remove(o);
            UI.print("Course Offering successfully archived");
        }
        else UI.print("Archiving cancelled");
    }
    private static void courseOfferingManage(String text, String offeringID, String sectionID, String courseCode,
                                             String facultyID, String academicYear, int term) {

        UI.header(text.toUpperCase() + ": COURSE OFFERING INFORMATION");
        UI.liner(String.format("     OFFERING ID    : %s", offeringID.trim().isEmpty() ?
                String.format("COF%03d", offerings.size() + 1) : offeringID));
        UI.liner(String.format(" [1] SECTION ID     : %s", sectionID));
        UI.liner(String.format(" [2] COURSE CODE    : %s", courseCode));
        UI.liner(String.format(" [3] FACULTY ID     : %s", facultyID));
        UI.liner(String.format(" [4] ACADEMIC YEAR  : %s", academicYear));
        UI.liner(String.format(" [5] TERM           : %s", term == -1 ? "" : term));
        UI.liner("");
        UI.option();
        UI.footer();
    }
}