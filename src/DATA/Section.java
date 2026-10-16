package DATA;

import UI.*;
import USER.Student;
import USER.User;
import java.util.ArrayList;

import static DATA.Enrollment.enrollments;
import static DATA.Enrollment.findEnrollmentByID;

public class Section {
    static int innerWidth = 70;

    private String sectionID;
    private  int yearLevel;
    private  char block;
    private int group;
    private String academicYear;
    private String professorID; // foreign key
    private String programCode; // foreign key

    public Section(String sectionID, int yearLevel, char block, int group,String academicYear, String professorID, String programCode) {
        this.sectionID = sectionID;
        this.yearLevel = yearLevel;
        this.block = block;
        this.group = group;
        this.academicYear = academicYear;
        this.professorID = professorID;
        this.programCode = programCode;
    }

    public static ArrayList<Section> sections = new ArrayList<Section>();

    static {
        sections.add(new Section("0001", 2, 'B', 1, "2024-2025", "F001", "BSIT"));
        sections.add(new Section("0002", 4, 'A', 2, "2022-2023", "F002", "BSIS"));
        sections.add(new Section("0003", 2, 'B', 2, "2024-2025", "F003", "BSIT"));
        sections.add(new Section("0004", 3, 'A', 1, "2023-2024", "F004", "BSIE"));
        sections.add(new Section("0005", 1, 'C', 1, "2025-2026", "F005", "BSED"));
        sections.add(new Section("0006", 4, 'A', 1, "2022-2023", "F006", "BSIS"));
        sections.add(new Section("0007", 3, 'B', 2, "2023-2024", "F001", "BSIT"));
        sections.add(new Section("0008", 1, 'A', 0, "2025-2026", "F002", "BSIT"));
        sections.add(new Section("0009", 2, 'C', 1, "2024-2025", "F003", "BSPSY"));
        sections.add(new Section("0010", 3, 'C', 2, "2023-2024", "F004", "BSCpE"));
        sections.add(new Section("0011", 4, 'B', 1, "2022-2023", "F005", "BSED"));
        sections.add(new Section("0012", 1, 'B', 0, "2025-2026", "F006", "BSBA"));
    }

    //setters
    public void setSectionID(String newSectionID) {
        this.sectionID = newSectionID;
    }
    public void setYearLevel(int yearLevel){
        this.yearLevel = yearLevel;
    }
    public void setBlock(char block) {
        this.block = block;
    }
    public void setGroup(int group) {
        this.group = group;
    }
    public void setAcademicYear(String academicYear){
        this.academicYear = academicYear;
    }
    public void setProfessorID(String professorID){
        this.professorID = professorID;
    }
    public void setProgramCode(String programCode) {
        this.programCode = programCode;
    }

    //getters
    public String getSectionID() {
        return sectionID;
    }
    public static String getSection(Section s){
        return s.sectionID;
    }
    public static String getYearSection(Section s) {
        Enrollment e = Enrollment.findEnrollmentBySec(s.sectionID);
        if (e == null) {
            UI.print("Enrollment not found");
            return null;
        }
        Student st = Student.findStudent(Enrollment.getStudentNumber(e)); // fix later
        if (st == null) {
            UI.print("Student not found");
            return null;
        }
        String sectionText;
        if (s.group != 0) sectionText = s.yearLevel + "" + s.block + "-G" + s.group;
        else sectionText = s.yearLevel + "" + s.block;
        return Student.getProgramCode(st) + " " + sectionText;
    }
    public static int getYearLevel(Section s) {
        return s.yearLevel;
    }
    public static int getGroup(Section s) {
        return s.group;
    }
    public static char getBlock(Section s) {
        return s.block;
    }
    public static String getAcademicYear(Section s) {
        return s.academicYear;
    }
    public static String getFacultyID(Section s) {
        return s.professorID;
    }
    public static String getProgramCode(Section s) {
        return s.programCode;
    }

    //methods
    public static Section findSection(String sectionID) {
        for (Section s : sections) if (sectionID.equals(s.getSectionID())) return s;
        return null;
    }
    public static boolean existSectionID(String sectionID) {
        for (Section s : sections) if (sectionID.equalsIgnoreCase(s.getSectionID())) return true;
        return false;
    }

    public static void displaySections() {
        UI.header("SECTIONS");
        for (int i = 0; i < sections.size(); i++) {
            Section s = sections.get(i);
            User u = User.getUser(Section.getFacultyID(s));
            Program p = Program.findProgram(Section.getProgramCode(s));

            if (u == null) {
                UI.print("Faculty not found");
                return;
            }
            if (p == null) {
                UI.print("Program not found");
                return;
            }

            String section = String.format("  %-10s %-15s %-15s %-25s", Section.getSection(s),
                    Section.getYearSection(s), Section.getAcademicYear(s), u.getUserName());
            String details = String.format("  " + Program.getProgramDescription(p));

            System.out.printf("│%-" + innerWidth + "s│\n", section);
            System.out.printf("│%-" + innerWidth + "s│\n", details);

            if (i < sections.size() - 1) UI.separator();
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
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("  SECTION ID        : %s", Section.getSection(s)));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("  YEAR AND SECTION  : %s", Section.getYearSection(s)));
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("  ACADEMIC YEAR     : %s", Section.getAcademicYear(s)));
        UI.footer();
    }
    public static void displayStudents(String sectionID){
        Section s = findSection(sectionID);
        if (s == null) {
            UI.print("Section not found");
            return;
        }
        UI.header("STUDENT LIST");
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("  SECTION ID     : %s", Section.getSection(s)));
        System.out.printf("│%-" + innerWidth + "s│\n", "  Students under " + Section.getYearSection(s) + ":");
        for (Enrollment e : enrollments) {
            if (Enrollment.getSectionID(e).equals(s.getSectionID())) {
                User student = User.getUser(Enrollment.getStudentNumber(e));
                if (student != null) {
                    String enrollment = String.format("  %-12s  %-25s  %-26s",
                            Enrollment.getStudentNumber(e), student.getUserName(), student.getEmail());
                    System.out.printf("│%-" + innerWidth + "s│\n", enrollment);
                }
            }
        }
        UI.footer();
    }

}