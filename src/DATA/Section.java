
package DATA;

import UI.*;
import USER.User;
import java.util.ArrayList;

import static DATA.Enrollment.enrollments;

public class Section {

    private static int nextSectionID = 1;

    private String sectionID;
    private int yearLevel;
    private char block;
    private int group;
    private String academicYear;

    public static ArrayList<Section> sections = new ArrayList<>();

    public Section(int yearLevel, char block, int group, String academicYear) {

        validateYearLevel(yearLevel);
        validateBlock(block);
        validateGroup(group);
        validateAcademicYear(academicYear);

        this.sectionID = String.format("SEC%03d", nextSectionID++);
        this.yearLevel = yearLevel;
        this.block = Character.toUpperCase(block);
        this.group = group;
        this.academicYear = academicYear.trim();
    }

    static {
        sections.add(new Section(2, 'B', 1, "2024-2025"));
        sections.add(new Section(4, 'A', 2, "2022-2023"));
        sections.add(new Section(2, 'B', 2, "2024-2025"));
        sections.add(new Section(3, 'A', 1, "2023-2024"));
        sections.add(new Section(1, 'C', 1, "2025-2026"));
        sections.add(new Section(4, 'A', 1, "2022-2023"));
        sections.add(new Section(3, 'B', 2, "2023-2024"));
        sections.add(new Section(1, 'A', 0, "2025-2026"));
        sections.add(new Section(2, 'C', 1, "2024-2025"));
        sections.add(new Section(3, 'C', 2, "2023-2024"));
        sections.add(new Section(4, 'B', 1, "2022-2023"));
        sections.add(new Section(1, 'B', 0, "2025-2026"));
    }

    // VALIDATION METHODS

    private static void validateYearLevel(int yearLevel) {
        if (yearLevel < 1 || yearLevel > 4)
            throw new IllegalArgumentException(
                    "Year level must be between 1 and 4.");
    }

    private static void validateBlock(char block) {
        if (!Character.isLetter(block))
            throw new IllegalArgumentException(
                    "Block must be a letter.");
    }

    private static void validateGroup(int group) {
        if (group < 0)
            throw new IllegalArgumentException(
                    "Group cannot be negative.");
    }

    private static void validateAcademicYear(String academicYear) {
        if (academicYear == null || academicYear.trim().isEmpty())
            throw new IllegalArgumentException(
                    "Academic year is required.");

        if (!academicYear.trim().matches("\\d{4}-\\d{4}"))
            throw new IllegalArgumentException(
                    "Academic year must use the format YYYY-YYYY.");

        String[] years = academicYear.trim().split("-");

        int startYear = Integer.parseInt(years[0]);
        int endYear = Integer.parseInt(years[1]);

        if (endYear != startYear + 1)
            throw new IllegalArgumentException(
                    "Academic year must contain consecutive years.");
    }

    // SETTERS

    public void setSectionID(String newSectionID) {

        if (newSectionID == null || newSectionID.trim().isEmpty())
            throw new IllegalArgumentException("Section ID is required.");

        newSectionID = newSectionID.trim();

        for (Section s : sections) {
            if (s != null && s != this
                    && newSectionID.equalsIgnoreCase(s.getSectionID())) {
                throw new IllegalArgumentException("Section ID already exists.");
            }
        }

        this.sectionID = newSectionID;
    }

    public void setYearLevel(int yearLevel) {
        validateYearLevel(yearLevel);
        this.yearLevel = yearLevel;
    }

    public void setBlock(char block) {
        validateBlock(block);
        this.block = Character.toUpperCase(block);
    }

    public void setGroup(int group) {
        validateGroup(group);
        this.group = group;
    }

    public void setAcademicYear(String academicYear) {
        validateAcademicYear(academicYear);
        this.academicYear = academicYear.trim();
    }

    // GETTERS

    public String getSectionID() {
        return sectionID;
    }

    public int getYearLevel() {
        return yearLevel;
    }

    public char getBlock() {
        return block;
    }

    public int getGroup() {
        return group;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public static int getNextID() {
        return nextSectionID;
    }

    public static String getSection(Section s) {
        return s == null ? null : s.getSectionID();
    }

    public static int getYearLevel(Section s) {
        return s == null ? 0 : s.getYearLevel();
    }

    public static char getBlock(Section s) {
        return s == null ? '\0' : s.getBlock();
    }

    public static int getGroup(Section s) {
        return s == null ? 0 : s.getGroup();
    }

    public static String getAcademicYear(Section s) {
        return s == null ? null : s.getAcademicYear();
    }

    public static String getYearSection(Section s) {

        if (s == null)
            return "Section not found";

        if (s.group != 0)
            return s.yearLevel + "" + s.block + "-G" + s.group;

        return s.yearLevel + "" + s.block;
    }

    // FIND METHODS

    public static Section findSection(String sectionID) {

        if (sectionID == null || sectionID.trim().isEmpty())
            return null;

        for (Section s : sections) {
            if (s != null
                    && sectionID.trim().equalsIgnoreCase(s.getSectionID()))
                return s;
        }

        return null;
    }

    public static boolean existSectionID(String sectionID) {
        return findSection(sectionID) != null;
    }

    // DISPLAY ALL SECTIONS

    public static void displaySections() {

        UI.header("SECTIONS");

        if (sections.isEmpty()) {
            UI.liner("  No sections found.");
        } else {
            int count = 0;

            for (Section s : sections) {

                if (s == null)
                    continue;

                if (count > 0)
                    UI.separator();

                UI.liner(String.format(
                        "  %-12s %-15s %-15s",
                        getSection(s),
                        getYearSection(s),
                        getAcademicYear(s)
                ));

                count++;
            }

            if (count == 0)
                UI.liner("  No sections found.");
        }

        UI.footer();
    }

    // DISPLAY ONE SECTION

    public static void displaySection(String sectionID) {

        Section s = findSection(sectionID);

        if (s == null) {
            UI.print("Section not found");
            return;
        }

        UI.header("SECTION DETAILS");
        UI.liner(String.format("  SECTION ID        : %s", getSection(s)));
        UI.liner(String.format("  YEAR LEVEL        : %d", getYearLevel(s)));
        UI.liner(String.format("  BLOCK             : %c", getBlock(s)));
        UI.liner(String.format("  GROUP             : %s",
                getGroup(s) == 0 ? "N/A" : "G" + getGroup(s)));
        UI.liner(String.format("  YEAR AND SECTION  : %s", getYearSection(s)));
        UI.liner(String.format("  ACADEMIC YEAR     : %s", getAcademicYear(s)));
        UI.footer();
    }

    // DISPLAY STUDENTS IN A SECTION

    public static void displayStudents(String sectionID) {

        Section s = findSection(sectionID);

        if (s == null) {
            UI.print("Section not found");
            return;
        }

        UI.header("STUDENT LIST");
        UI.liner(String.format("  SECTION ID     : %s", getSection(s)));
        UI.liner("  Students under " + getYearSection(s) + ":");
        UI.separator();

        int count = 0;

        for (Enrollment e : enrollments) {

            if (e == null)
                continue;

            String enrolledSectionID = Enrollment.getSectionID(e);
            String studentNumber = Enrollment.getStudentNumber(e);

            if (enrolledSectionID == null || studentNumber == null)
                continue;

            if (getSection(s).equalsIgnoreCase(enrolledSectionID)) {

                User student = User.getUser(studentNumber);

                if (student != null) {
                    UI.liner(String.format(
                            "  %-12s %-25s %-30s",
                            studentNumber,
                            student.getUserName(),
                            student.getEmail()
                    ));

                    count++;
                }
            }
        }

        if (count == 0)
            UI.liner("  No students enrolled in this section.");

        UI.liner("  TOTAL STUDENTS: " + count);
        UI.footer();
    }
}