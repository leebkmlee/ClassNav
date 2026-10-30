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

    public static ArrayList<Section> sections = new ArrayList<>();
    public static ArrayList<Section> archiveSections = new ArrayList<>();

    // CONSTRUCTOR
    public Section(int yearLevel, char block, int group) {

        this.sectionID = String.format("SEC%03d", nextSectionID++);
        this.yearLevel = yearLevel;
        this.block = Character.toUpperCase(block);
        this.group = group;
    }

    // DUMMY DATA
    static {
        sections.add(new Section(1, 'A', 2));
        sections.add(new Section(1, 'B', 0));
        sections.add(new Section(1, 'C', 1));
        sections.add(new Section(1, 'D', 2));
        sections.add(new Section(2, 'A', 0));
        sections.add(new Section(2, 'B', 1));
        sections.add(new Section(2, 'C', 2));
        sections.add(new Section(2, 'D', 0));
        sections.add(new Section(3, 'A', 1));
        sections.add(new Section(3, 'B', 2));
        sections.add(new Section(3, 'C', 0));
        sections.add(new Section(3, 'D', 1));
        sections.add(new Section(4, 'A', 2));
        sections.add(new Section(4, 'B', 0));
        sections.add(new Section(4, 'C', 1));
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
        UI.header("SECTIONS", true);
        if (sections.isEmpty()) UI.liner("  No sections found.", true);
        else {
            String format = "  %-14s %-22s %-12s %-10s %-10s %-20s %-59s %-24s";
            UI.liner(String.format(format, "SECTION ID", "YEAR / SECTION", "YEAR LEVEL", "BLOCK", "GROUP",
                    "OFFERINGS", "COURSE CODES", "STUDENTS"), true);
            UI.separator(true);
            int count = 0;
            for (Section section : sections) {
                if (section == null) continue;
                ArrayList<String> offeringIDs = new ArrayList<>(), courseCodes = new ArrayList<>(), studentIDs = new ArrayList<>();
                for (CourseOffering offering : CourseOffering.offerings) {
                    if (offering == null || offering.getSectionID() == null
                            || !offering.getSectionID().equalsIgnoreCase(section.getSectionID())) continue;
                    offeringIDs.add(offering.getOfferingID());
                    if (offering.getCourseCode() != null && !courseCodes.contains(offering.getCourseCode()))
                        courseCodes.add(offering.getCourseCode());
                    for (CourseRegistration registration : CourseRegistration.getRegistrationsByOffering(offering.getOfferingID())) {
                        if (registration != null && registration.getStudentID() != null
                                && !studentIDs.contains(registration.getStudentID())) studentIDs.add(registration.getStudentID());
                    }
                }
                String offeringText = String.join(", ", offeringIDs);
                String courseText = String.join(", ", courseCodes);
                UI.liner(String.format(format, section.getSectionID(), UI.fitText(getYearSection(section), 22),
                        section.getYearLevel(), section.getBlock(), section.getGroup() == 0 ? "N/A" : "G" + section.getGroup(),
                        UI.fitText(offeringText.isEmpty() ? "-" : offeringText, 20),
                        UI.fitText(courseText.isEmpty() ? "-" : courseText, 59),
                        studentIDs.size()), true);
                count++;
            }
            UI.separator(true);
            UI.liner("  TOTAL SECTIONS: " + count, true);
        }
        UI.footer(true);
        UI.returnButton();
    }
    public static void displaySection(String sectionID) {

        Section s = findSection(sectionID);

        if (s == null) {
            UI.print("Section not found");
            return;
        }

        UI.header("SECTION DETAILS", false);
        UI.liner(String.format("  SECTION ID        : %s", s.getSectionID()), false);
        UI.liner(String.format("  YEAR LEVEL        : %d", s.getYearLevel()), false);
        UI.liner(String.format("  BLOCK             : %c", s.getBlock()), false);
        UI.liner(String.format("  GROUP             : %s",
                s.getGroup() == 0 ? "N/A" : "G" + s.getGroup()), false);
        UI.liner(String.format("  YEAR AND SECTION  : %s", getYearSection(s)), false);
        UI.footer(false);
        UI.returnButton();
    }
    public static void displayStudents(String sectionID) {
        Section s = findSection(sectionID);
        if (s == null) {
            UI.print("Section not found");
            return;
        }

        UI.header("STUDENT LIST", true);
        UI.liner("  SECTION ID: " + s.getSectionID() + " | SECTION: " + getYearSection(s), true);
        UI.liner(String.format("  %-14s %-42s %-82s %-18s %-18s",
                "STUDENT ID", "STUDENT NAME", "EMAIL ADDRESS", "PROGRAM", "STATUS"), true);
        UI.separator(true);

        ArrayList<String> displayedStudents = new ArrayList<>();
        int count = 0;
        for (CourseOffering offering : CourseOffering.offerings) {
            if (offering == null || offering.getSectionID() == null
                    || !offering.getSectionID().equalsIgnoreCase(s.getSectionID())) continue;
            for (CourseRegistration registration : CourseRegistration.getRegistrationsByOffering(offering.getOfferingID())) {
                if (registration == null || registration.getStudentID() == null
                        || displayedStudents.contains(registration.getStudentID().toUpperCase())) continue;
                Student student = Student.findStudent(registration.getStudentID());
                if (student == null) continue;
                UI.liner(String.format("  %-14s %-42s %-82s %-18s %-18s",
                        student.getUserID(), UI.fitText(student.getUserName(), 42),
                        UI.fitText(student.getEmail(), 82), student.getProgramCode(),
                        student.getEnrollmentStatus()), true);
                displayedStudents.add(registration.getStudentID().toUpperCase());
                count++;
            }
        }
        if (count == 0) UI.liner("  No students registered in this section.", true);
        UI.separator(true);
        UI.liner("  TOTAL STUDENTS: " + count, true);
        UI.footer(true);
        UI.returnButton();
    }
    public static void displayArchiveSections() {
        UI.header("ARCHIVED SECTIONS", true);
        if (archiveSections.isEmpty()) UI.liner("  No archived sections found.", true);
        else {
            String format = "  %-14s %-22s %-12s %-10s %-10s %-20s %-59s %-24s";
            UI.liner(String.format(format, "SECTION ID", "YEAR / SECTION", "YEAR LEVEL", "BLOCK", "GROUP",
                    "OFFERINGS", "COURSE CODES", "STUDENTS"), true);
            UI.separator(true);
            int count = 0;
            for (Section section : archiveSections) {
                if (section == null) continue;
                ArrayList<String> offeringIDs = new ArrayList<>(), courseCodes = new ArrayList<>(), studentIDs = new ArrayList<>();
                for (CourseOffering offering : CourseOffering.offerings) {
                    if (offering == null || offering.getSectionID() == null
                            || !offering.getSectionID().equalsIgnoreCase(section.getSectionID())) continue;
                    offeringIDs.add(offering.getOfferingID());
                    if (offering.getCourseCode() != null && !courseCodes.contains(offering.getCourseCode()))
                        courseCodes.add(offering.getCourseCode());
                    for (CourseRegistration registration : CourseRegistration.getRegistrationsByOffering(offering.getOfferingID())) {
                        if (registration != null && registration.getStudentID() != null
                                && !studentIDs.contains(registration.getStudentID())) studentIDs.add(registration.getStudentID());
                    }
                }
                UI.liner(String.format(format, section.getSectionID(), UI.fitText(getYearSection(section), 22),
                        section.getYearLevel(), section.getBlock(), section.getGroup() == 0 ? "N/A" : "G" + section.getGroup(),
                        UI.fitText(String.join(", ", offeringIDs), 20),
                        UI.fitText(String.join(", ", courseCodes), 59), studentIDs.size()), true);
                count++;
            }
            UI.separator(true);
            UI.liner("  TOTAL ARCHIVED SECTIONS: " + count, true);
        }
        UI.footer(true);
        UI.returnButton();
    }

    // MANAGE METHODS
    public static void createSection() {
        String choice, input;
        int yearLevel = -1, group = -1;
        char block = 0;
        boolean submit = false;

        while (!submit) {
            sectionManage("CREATE", String.format("SEC%03d", nextSectionID), yearLevel, block, group);

            do {
                UI.select("Select", false);
                choice = in.nextLine().trim().toUpperCase();
                if (!choice.matches("[1-3X/]")) UI.print("Invalid Choice");
            } while (!choice.matches("[1-3X/]"));

            switch (choice) {
                case "1":
                    do {
                        UI.select("Enter Year Level (1-4)", true);
                        input = in.nextLine().trim();
                        if (input.equalsIgnoreCase("X")) break;
                        try {
                            yearLevel = Integer.parseInt(input);
                            if (yearLevel < 1 || yearLevel > 4) UI.print("Year level must be from 1 to 4.");
                        } catch (NumberFormatException e) {
                            yearLevel = -1;
                            UI.print("Enter a valid whole number.");
                        }
                    } while (yearLevel < 1 || yearLevel > 4);
                    break;

                case "2":
                    do {
                        UI.select("Enter Block (A-J)", true);
                        input = in.nextLine().trim().toUpperCase();
                        if (input.equalsIgnoreCase("X")) break;
                        if (!input.matches("[A-J]")) UI.print("Block must be one letter from A to J.");
                    } while (!input.matches("[A-J]") && !input.equalsIgnoreCase("X"));
                    if (input.matches("[A-J]")) block = input.charAt(0);
                    break;

                case "3":
                    do {
                        UI.select("Enter Group (0-2)", true);
                        input = in.nextLine().trim();
                        if (input.equalsIgnoreCase("X")) break;
                        try {
                            group = Integer.parseInt(input);
                            if (group < 0 || group > 2) UI.print("Group must be from 0 to 2.");
                        } catch (NumberFormatException e) {
                            group = -1;
                            UI.print("Enter a valid whole number.");
                        }
                    } while (group < 0 || group > 2);
                    break;

                case "/":
                    if (yearLevel < 1 || yearLevel > 4 || block < 'A' || block > 'J' || group < 0 || group > 2) {
                        UI.print("Complete all section details before submitting.");
                        break;
                    }

                    String yearSection = group == 0 ? yearLevel + "" + block : yearLevel + "" + block + "-G" + group;
                    boolean duplicate = false;
                    for (Section existing : sections) {
                        if (existing != null && getYearSection(existing).equalsIgnoreCase(yearSection)) {
                            duplicate = true;
                            break;
                        }
                    }

                    if (duplicate) {
                        UI.print("This section already exists.");
                    } else {
                        Section section = new Section(yearLevel, block, group);
                        sections.add(section);
                        UI.print("Section successfully created: " + section.getSectionID());
                        submit = true;
                    }
                    break;

                case "X":
                    submit = true;
                    UI.print("Section creation cancelled.");
                    break;
            }
        }
    }
    public static void editSection() {
        String sectionID = "", choice, input = "";
        int yearLevel, group; char block;
        boolean submit = false;

        UI.print("EDIT EXISTING SECTION");

        do {
            UI.select("Enter Section ID", false);
            sectionID = in.nextLine().trim();
            if (sectionID.equalsIgnoreCase("X"))return;
            else if (sectionID.isEmpty())UI.print("Section ID is required");
            else if (findSection(sectionID) == null) UI.print("Section does not exist");
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

        while (!submit) {
            sectionManage("EDIT", sectionID, yearLevel, block, group);

            do {
                UI.select("Select", false);
                choice = in.nextLine().trim();

                if (!choice.matches("[1-3X/]")) UI.print("Invalid Choice");
            } while (!choice.matches("[1-3X/]"));

            switch (choice) {
                case "1":
                    do {
                        UI.select("Enter Year Level", true);
                        input = in.nextLine().trim();
                        if (input.equalsIgnoreCase("X")) {
                            yearLevel = s.getYearLevel();
                            break;
                        }

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
                        UI.select("Enter Block (A-J)", true);
                        input = in.nextLine().trim().toUpperCase();
                        if (input.equalsIgnoreCase("X")) break;
                        if (!input.matches("[A-J]")) UI.print("Block must be one letter from A to J.");
                    } while (!input.matches("[A-J]") && !input.equalsIgnoreCase("X"));
                    if (input.matches("[A-J]")) block = input.charAt(0);
                    break;

                case "3":
                    do {
                        UI.select("Enter Group (0-2)", true);
                        input = in.nextLine().trim();
                        if (input.equalsIgnoreCase("X")) {
                            group = s.getGroup();
                            break;
                        }
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

                case "/":
                    if (yearLevel < 1 || yearLevel > 4 ||
                            block < 'A' || block > 'J' ||
                            group < 0 || group > 2) {
                        UI.print("Details are incomplete");
                        break;
                    }

                    boolean gate = true;

                    for (Section existing : sections) {
                        String yearSection;
                        if (group != 0) yearSection = yearLevel + "" + block + "-G" + group;
                        else yearSection = yearLevel + "" + block;

                        if (existing != null && existing != s && getYearSection(existing).equals(yearSection)) {
                            gate = false;
                            break;
                        }
                    }

                    if (gate) {
                        s.yearLevel = yearLevel;
                        s.block = block;
                        s.group = group;

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

        UI.print("ARCHIVE EXISTING SECTION");

        do {
            UI.select("Enter Section ID",false);
            input = in.nextLine().trim();
            if (input.equalsIgnoreCase("X")) return;
            else if (input.isEmpty()) UI.print("Section ID is required");

            if (findSection(input) == null)
                UI.print("Section does not exist");
        } while (findSection(input) == null);

        Section s = Section.findSection(input);

        if (s == null) {
            UI.print("Section not found");
            return;
        }

        for (CourseOffering offering : CourseOffering.offerings) {
            if (offering != null && offering.getSectionID() != null
                    && offering.getSectionID().equalsIgnoreCase(s.getSectionID())) {
                UI.print("Archive the section's course offerings first.");
                return;
            }
        }

        sectionManage("ARCHIVE", s.getSectionID(), s.getYearLevel(), s.getBlock(), s.getGroup());

        do {
            UI.select("Confirm Section Archive? (Y/N)", false);
            choice = in.nextLine().trim().toUpperCase();
            if (choice.equals("X")) return;

            if (!choice.matches("[YN]"))
                UI.print("Invalid Input");
        } while (!choice.matches("[YN]"));

        if (choice.equals("Y")) {
            archiveSections.add(s);
            sections.remove(s);
            UI.print("Section successfully archive");
        } else {
            UI.print("Section deletion cancelled");
        }
    }
    private static void sectionManage(String text, String sectionID, int yearLevel, char block, int group) {

        UI.header(text.toUpperCase() + ": SECTION INFORMATION", false);
        UI.liner(String.format("     SECTION ID     : %s", sectionID), false);
        UI.liner(String.format(" [1] YEAR LEVEL     : %s", yearLevel == -1 ? "" : yearLevel), false);
        UI.liner(String.format(" [2] BLOCK          : %s", block == '\0' ? "" : block), false);
        UI.liner(String.format(" [3] GROUP          : %s", group == -1 ? "" : (group == 0 ? "N/A" : "G" + group)), false);
        UI.liner("", false);
        UI.option();
        UI.footer(false);
    }

}