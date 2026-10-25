package DATA;

import UI.*;
import USER.Student;
import java.util.ArrayList;
import static UI.UI.in;

public class Section {

    // FIELDS
    private static int nextSectionID = 1;

    private final String sectionID;
    private int yearLevel, group;
    private char block;
    private String academicYear;

    public static ArrayList<Section> sections = new ArrayList<>();
    public static ArrayList<Section> archiveSections = new ArrayList<>();

    // CONSTRUCTOR
    public Section(int yearLevel, char block, int group, String academicYear) {

        this.sectionID = String.format("SEC%03d", nextSectionID++);
        this.yearLevel = yearLevel;
        this.block = Character.toUpperCase(block);
        this.group = group;
        this.academicYear = academicYear.trim();
    }

    // DUMMY DATA
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

    // GETTER METHODS
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

    // DISPLAY METHODS
    public static void displaySections() {

        UI.header("SECTIONS");

        if (sections.isEmpty()) UI.liner("  No sections found.");
        else {
            for (Section s : sections) {
                if (s == null) continue;
                UI.liner(String.format("  %-12s %-15s %-15s",
                        s.getSectionID(), getYearSection(s), s.getAcademicYear()));
            }
        }

        UI.footer();
    }
    public static void displaySection(String sectionID) {

        Section s = findSection(sectionID);

        if (s == null) {
            UI.print("Section not found");
            return;
        }

        UI.header("SECTION DETAILS");
        UI.liner(String.format("  SECTION ID        : %s", s.getSectionID()));
        UI.liner(String.format("  YEAR LEVEL        : %d", s.getYearLevel()));
        UI.liner(String.format("  BLOCK             : %c", s.getBlock()));
        UI.liner(String.format("  GROUP             : %s",
                s.getGroup() == 0 ? "N/A" : "G" + s.getGroup()));
        UI.liner(String.format("  YEAR AND SECTION  : %s", getYearSection(s)));
        UI.liner(String.format("  ACADEMIC YEAR     : %s", s.getAcademicYear()));
        UI.footer();
    }
    public static void displayStudents(String sectionID) {

        Section s = findSection(sectionID);

        if (s == null) {
            UI.print("Section not found");
            return;
        }

        UI.header("STUDENT LIST");
        UI.liner(String.format("  SECTION ID     : %s", s.getSectionID()));
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
                            "  %-12s %-25s %-25s",
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
        else {
            UI.liner("");
            UI.liner("  TOTAL STUDENTS: " + count);
        }
        UI.footer();
    }
    public static void displayArchiveSections() {

        UI.header("ARCHIVED SECTIONS");

        if (archiveSections.isEmpty()) UI.liner("  No sections found.");
        else {
            for (Section s : archiveSections) {
                if (s == null) continue;
                UI.liner(String.format("  %-12s %-15s %-15s",
                        s.getSectionID(), getYearSection(s), s.getAcademicYear()));
            }
        }

        UI.footer();
    }

    // MANAGE METHODS
    public static void createSection() {
        String academicYear = "", choice, input = "";
        int yearLevel = -1, group = -1;
        char block = 0;
        boolean submit = false;

        while (!submit) {
            sectionManage("CREATE", String.format("SEC%03d", sections.size()), yearLevel, block, group, academicYear);

            do {
                System.out.print("  Select > ");
                choice = in.nextLine().trim();

                if (!choice.matches("[1-4X/]"))
                    UI.print("Invalid Choice");
            } while (!choice.matches("[1-4X/]"));

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

                        if (input.length() != 1 || input.charAt(0) < 'A' || input.charAt(0) > 'J' || (!Character.isLetter(block)))
                            UI.print("Invalid Input");
                    } while (input.length() != 1 || input.charAt(0) < 'A' || input.charAt(0) > 'J' || !Character.isLetter(block));

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
                    int startYear;int endYear;
                    do {
                        System.out.print("   Enter Academic Year > ");
                        academicYear = in.nextLine().trim();

                        if (academicYear.trim().isEmpty()){
                            UI.print("Academic year is required.");
                            return;
                        }

                        if (!academicYear.trim().matches("\\d{4}-\\d{4}")) {
                            UI.print("Academic year must use the format YYYY-YYYY.");
                            return;
                        }

                        String[] years = academicYear.trim().split("-");

                        startYear = Integer.parseInt(years[0]);
                        endYear = Integer.parseInt(years[1]);

                        if (endYear != startYear + 1){
                            UI.print("Academic year must contain consecutive years.");
                            return;
                        }
                    } while (!academicYear.matches("\\d{4}-\\d{4}") || endYear != startYear + 1 || academicYear.trim().isEmpty());
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
                        String yearSection;
                        if (group != 0) yearSection = yearLevel + "" + block + "-G" + group;
                        else yearSection = yearLevel + "" + block;

                        if (existing != null
                                && getYearSection(existing).equals(yearSection)
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
            if (findSection(sectionID) == null) UI.print("Section does not exist");
        } while (findSection(sectionID) == null);

        Section s = Section.findSection(sectionID);

        if (s == null) {
            UI.print("Section not found");
            return;
        }

        sectionID = s.getSectionID();
        yearLevel = s.getYearLevel();
        block = s.getBlock();
        group = s.getGroup();
        academicYear = s.getAcademicYear();

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
                        System.out.print("   Enter Group (0-2) > ");
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
                        System.out.print("   Enter Academic Year > ");
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
                        String yearSection;
                        if (group != 0) yearSection = yearLevel + "" + block + "-G" + group;
                        else yearSection = yearLevel + "" + block;

                        if (existing != null && existing != s
                                && getYearSection(existing).equals(yearSection)
                                && existing.getAcademicYear().equalsIgnoreCase(academicYear)) {
                            gate = false;
                            break;
                        }
                    }

                    if (gate) {
                        s.yearLevel = yearLevel;
                        s.block = block;
                        s.group = group;
                        s.academicYear = academicYear;

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
    public static void archiveSection() {
        String choice, input;

        UI.print("DELETE EXISTING SECTION");

        do {
            System.out.print("  Enter Section ID > ");
            input = in.nextLine().trim();

            if (findSection(input) == null)
                UI.print("Section does not exist");
        } while (findSection(input) == null);

        Section s = Section.findSection(input);

        if (s == null) {
            UI.print("Section not found");
            return;
        }

        sectionManage("DELETE", s.getSectionID(), s.getYearLevel(), s.getBlock(), s.getGroup(), s.getAcademicYear());

        do {
            System.out.print("  Confirm Section Deletion? (Y/N) > ");
            choice = in.nextLine().trim().toUpperCase();

            if (!choice.matches("[YN]"))
                UI.print("Invalid Input");
        } while (!choice.matches("[YN]"));

        if (choice.equals("Y")) {
            archiveSections.add(s);
            sections.remove(s);
            UI.print("Section successfully deleted");
        } else {
            UI.print("Section deletion cancelled");
        }
    }
    private static void sectionManage(String text, String sectionID,
                                      int yearLevel, char block, int group, String academicYear) {

        UI.header(text.toUpperCase() + ": SECTION INFORMATION");
        UI.liner(String.format("     SECTION ID     : %s", sectionID));
        UI.liner(String.format(" [1] YEAR LEVEL     : %s", yearLevel == -1 ? "" : yearLevel));
        UI.liner(String.format(" [2] BLOCK          : %s", block == '\0' ? "" : block));
        UI.liner(String.format(" [3] GROUP          : %s", group == -1 ? "" : (group == 0 ? "N/A" : "G" + group)));
        UI.liner(String.format(" [4] ACADEMIC YEAR  : %s", academicYear));
        UI.liner("");
        UI.option();
        UI.footer();
    }

}