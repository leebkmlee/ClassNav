
package DATA;

import UI.*;
import USER.User;
import USER.Student;

import java.util.ArrayList;

import static UI.UI.in;

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
        sections.add(new Section(1, 'A', 2, "2025-2026"));
        sections.add(new Section(1, 'B', 0, "2024-2025"));
        sections.add(new Section(1, 'C', 1, "2026-2027"));
        sections.add(new Section(1, 'D', 2, "2025-2026"));
        sections.add(new Section(2, 'A', 0, "2024-2025"));
        sections.add(new Section(2, 'B', 1, "2026-2027"));
        sections.add(new Section(2, 'C', 2, "2025-2026"));
        sections.add(new Section(2, 'D', 0, "2024-2025"));
        sections.add(new Section(3, 'A', 1, "2026-2027"));
        sections.add(new Section(3, 'B', 2, "2025-2026"));
        sections.add(new Section(3, 'C', 0, "2024-2025"));
        sections.add(new Section(3, 'D', 1, "2026-2027"));
        sections.add(new Section(4, 'A', 2, "2025-2026"));
        sections.add(new Section(4, 'B', 0, "2024-2025"));
        sections.add(new Section(4, 'C', 1, "2026-2027"));
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

        ArrayList<String> displayedStudents = new ArrayList<>();
        int count = 0;

        for (CourseOffering offering : CourseOffering.offerings) {

            if (offering == null
                    || offering.getSectionID() == null
                    || !offering.getSectionID().equalsIgnoreCase(s.getSectionID()))
                continue;

            for (CourseRegistration registration :
                    CourseRegistration.getRegistrationsByOffering(offering.getOfferingID())) {

                if (registration == null)
                    continue;

                String studentID = registration.getStudentID();

                if (studentID == null
                        || displayedStudents.contains(studentID.toUpperCase()))
                    continue;

                Student student = Student.findStudent(studentID);

                if (student != null) {
                    UI.liner(String.format(
                            "  %-12s %-25s %-30s",
                            student.getUserID(),
                            student.getUserName(),
                            student.getEmail()
                    ));

                    displayedStudents.add(studentID.toUpperCase());
                    count++;
                }
            }
        }

        if (count == 0)
            UI.liner("  No students registered in this section.");

        UI.liner("  TOTAL STUDENTS: " + count);
        UI.footer();
    }

    public static void createSection() {
        String academicYear = "", choice, input = "";
        int yearLevel = -1, group = -1;
        char block = 0;
        boolean submit = false;

        while (!submit) {
            sectionManage("CREATE",
                    String.format("SEC%03d", Section.getNextID()),
                    yearLevel, block, group, academicYear);

            do {
                System.out.print("  Select > ");
                choice = in.nextLine().trim();

                if (!choice.matches("[1-5X/]"))
                    UI.print("Invalid Choice");
            } while (!choice.matches("[1-5X/]"));

            switch (choice) {
                case "1":
                    do {
                        System.out.print("   Enter Year Level > ");
                        input = in.nextLine().trim();

                        try {
                            yearLevel = Integer.parseInt(input);
                            if (yearLevel < 1 || yearLevel > 4)
                                UI.print("Invalid Input");
                        } catch (NumberFormatException e) {
                            yearLevel = -1;
                            UI.print("Invalid Input");
                        }
                    } while (yearLevel < 1 || yearLevel > 4);
                    break;

                case "2":
                    do {
                        System.out.print("   Block > ");
                        input = in.nextLine().trim().toUpperCase();

                        if (input.length() != 1 ||
                                input.charAt(0) < 'A' || input.charAt(0) > 'J')
                            UI.print("Invalid Input");
                    } while (input.length() != 1 ||
                            input.charAt(0) < 'A' || input.charAt(0) > 'J');

                    block = input.charAt(0);
                    break;

                case "3":
                    do {
                        System.out.print("  Enter Group (0-2) > ");
                        input = in.nextLine().trim();

                        try {
                            group = Integer.parseInt(input);
                            if (group < 0 || group > 2)
                                UI.print("Invalid Input");
                        } catch (NumberFormatException e) {
                            group = -1;
                            UI.print("Invalid Input");
                        }
                    } while (group < 0 || group > 2);
                    break;

                case "4":
                    do {
                        System.out.print("  Enter Academic Year > ");
                        academicYear = in.nextLine().trim();

                        if (!academicYear.matches("\\d{4}-\\d{4}"))
                            UI.print("Invalid Format");
                    } while (!academicYear.matches("\\d{4}-\\d{4}"));
                    break;

                case "/":
                    if (yearLevel < 1 || yearLevel > 4 ||
                            block < 'A' || block > 'J' ||
                            group < 0 || group > 2 ||
                            !academicYear.matches("\\d{4}-\\d{4}")) {
                        UI.print("Details are incomplete");
                        break;
                    }

                    boolean gate = true;

                    for (Section existing : sections) {
                        if (existing != null
                                && getYearSection(existing).equals(
                                getYearSection(yearLevel, block, group))
                                && existing.getAcademicYear().equalsIgnoreCase(academicYear)) {
                            gate = false;
                            break;
                        }
                    }

                    if (gate) {
                        sections.add(new Section(yearLevel, block, group, academicYear));
                        UI.print("Section successfully created");
                        submit = true;
                    } else {
                        UI.print("Section already exists for this academic year");
                    }
                    break;

                case "X":
                    submit = true;
                    break;

                case "5":
                    UI.print("No additional section fields are available.");
                    break;
            }
        }
    }
    public static void editSection() {
        String sectionID = "", academicYear = "", choice, input = "";
        int yearLevel, group;
        char block;
        boolean submit = false;

        UI.print("EDIT EXISTING SECTION");

        do {
            System.out.print("  Enter Section ID > ");
            sectionID = in.nextLine().trim();

            if (!Section.existSectionID(sectionID))
                UI.print("Section does not exist");
        } while (!Section.existSectionID(sectionID));

        Section s = Section.findSection(sectionID);

        if (s == null) {
            UI.print("Section not found");
            return;
        }

        sectionID = Section.getSection(s);
        yearLevel = Section.getYearLevel(s);
        block = Section.getBlock(s);
        group = Section.getGroup(s);
        academicYear = Section.getAcademicYear(s);

        while (!submit) {
            sectionManage("EDIT", sectionID, yearLevel, block, group, academicYear);

            do {
                System.out.print("  Select > ");
                choice = in.nextLine().trim();

                if (!choice.matches("[1-5X/]"))
                    UI.print("Invalid Choice");
            } while (!choice.matches("[1-5X/]"));

            switch (choice) {
                case "1":
                    UI.print("Section IDs are generated automatically and cannot be edited.");
                    break;

                case "2":
                    do {
                        System.out.print("   Enter Year Level > ");
                        input = in.nextLine().trim();

                        try {
                            yearLevel = Integer.parseInt(input);
                            if (yearLevel < 1 || yearLevel > 4)
                                UI.print("Invalid Input");
                        } catch (NumberFormatException e) {
                            yearLevel = -1;
                            UI.print("Invalid Input");
                        }
                    } while (yearLevel < 1 || yearLevel > 4);
                    break;

                case "3":
                    do {
                        System.out.print("   Block > ");
                        input = in.nextLine().trim().toUpperCase();

                        if (input.length() != 1 ||
                                input.charAt(0) < 'A' || input.charAt(0) > 'J')
                            UI.print("Invalid Input");
                    } while (input.length() != 1 ||
                            input.charAt(0) < 'A' || input.charAt(0) > 'J');

                    block = input.charAt(0);
                    break;

                case "4":
                    do {
                        System.out.print("  Enter Group (0-2) > ");
                        input = in.nextLine().trim();

                        try {
                            group = Integer.parseInt(input);
                            if (group < 0 || group > 2)
                                UI.print("Invalid Input");
                        } catch (NumberFormatException e) {
                            group = -1;
                            UI.print("Invalid Input");
                        }
                    } while (group < 0 || group > 2);
                    break;

                case "5":
                    do {
                        System.out.print("  Enter Academic Year > ");
                        academicYear = in.nextLine().trim();

                        if (!academicYear.matches("\\d{4}-\\d{4}"))
                            UI.print("Invalid Format");
                    } while (!academicYear.matches("\\d{4}-\\d{4}"));
                    break;

                case "/":
                    if (yearLevel < 1 || yearLevel > 4 ||
                            block < 'A' || block > 'J' ||
                            group < 0 || group > 2 ||
                            !academicYear.matches("\\d{4}-\\d{4}")) {
                        UI.print("Details are incomplete");
                        break;
                    }

                    boolean gate = true;

                    for (Section existing : sections) {
                        if (existing != null && existing != s
                                && getYearSection(existing).equals(
                                getYearSection(yearLevel, block, group))
                                && existing.getAcademicYear().equalsIgnoreCase(academicYear)) {
                            gate = false;
                            break;
                        }
                    }

                    if (gate) {
                        s.setYearLevel(yearLevel);
                        s.setBlock(block);
                        s.setGroup(group);
                        s.setAcademicYear(academicYear);

                        UI.print("Section successfully updated");
                        submit = true;
                    } else {
                        UI.print("Section already exists for this academic year");
                    }
                    break;

                case "X":
                    submit = true;
                    break;
            }
        }
    }
    public static void deleteSection() {
        String choice, input;

        UI.print("DELETE EXISTING SECTION");

        do {
            System.out.print("  Enter Section ID > ");
            input = in.nextLine().trim();

            if (!Section.existSectionID(input))
                UI.print("Section does not exist");
        } while (!Section.existSectionID(input));

        Section s = Section.findSection(input);

        if (s == null) {
            UI.print("Section not found");
            return;
        }

        sectionManage("DELETE",
                Section.getSection(s),
                Section.getYearLevel(s),
                Section.getBlock(s),
                Section.getGroup(s),
                Section.getAcademicYear(s));

        do {
            System.out.print("  Confirm Section Deletion? (Y/N) > ");
            choice = in.nextLine().trim().toUpperCase();

            if (!choice.matches("[YN]"))
                UI.print("Invalid Input");
        } while (!choice.matches("[YN]"));

        if (choice.equals("Y")) {
            sections.remove(s);
            UI.print("Section successfully deleted");
        } else {
            UI.print("Section deletion cancelled");
        }
    }
    private static String getYearSection(int yearLevel, char block, int group) {
        if (group != 0)
            return yearLevel + "" + block + "-G" + group;
        return yearLevel + "" + block;
    }
    private static void sectionManage(String text, String sectionID,
                                      int yearLevel, char block, int group, String academicYear) {

        UI.header(text.toUpperCase() + ": SECTION INFORMATION");
        UI.liner(String.format(" [1] SECTION ID     : %s", sectionID));
        UI.liner(String.format(" [2] YEAR LEVEL     : %s", yearLevel));
        UI.liner(String.format(" [3] BLOCK          : %s", block));
        UI.liner(String.format(" [4] GROUP          : %s", group == 0 ? "N/A" : "G" + group));
        UI.liner(String.format(" [5] ACADEMIC YEAR  : %s", academicYear));
        UI.liner("");
        UI.option();
        UI.footer();
    }
}