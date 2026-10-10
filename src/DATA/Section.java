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
    private int yearLevel;
    private char block;
    private int group;
    private String academicYear;
    private String professorID; // foreign key

    public Section(String sectionID, int yearLevel,char block, int group,String academicYear, String professorID) {
        this.sectionID = sectionID;
        this.yearLevel = yearLevel;
        this.block = block;
        this.group = group;
        this.academicYear = academicYear;
        this.professorID = professorID;
    }

    public static ArrayList<Section> sections = new ArrayList<Section>();
    static{
        Section.sections.add(new Section("0001", 2, 'B', 1, "2025-2026", "F001"));
        Section.sections.add(new Section("0002", 4, 'A', 2, "2025-2026", "F002"));
        Section.sections.add(new Section("0003", 2, 'B', 2, "2025-2026", "F003"));
        Section.sections.add(new Section("0004", 3, 'A', 1, "2025-2026", "F004"));
        Section.sections.add(new Section("0005", 1, 'C', 1, "2025-2026", "F005"));
        Section.sections.add(new Section("0006", 4, 'A', 1, "2025-2026", "F006"));
        Section.sections.add(new Section("0007", 3, 'B', 2, "2025-2026", "F001"));
        Section.sections.add(new Section("0008", 1, 'A', 0, "2025-2026", "F002"));
        Section.sections.add(new Section("0009", 2, 'C', 1, "2025-2026", "F003"));
        Section.sections.add(new Section("0010", 3, 'C', 2, "2025-2026", "F004"));
        Section.sections.add(new Section("0011", 4, 'B', 1, "2025-2026", "F005"));
        Section.sections.add(new Section("0012", 1, 'B', 0, "2025-2026", "F006"));
    }

    public String getSectionID() {
        return sectionID;
    }

    public static Section findSection(String sectionID) {
        for (Section s : sections) if (sectionID.equals(s.getSectionID())) return s;
        return null;
    }

    public static String getSection(Section s){
        return s.sectionID;
    }

    public static String getFacultyID(Section s) {
        return s.professorID;
    }

    public static String getYearSection(Section s) {
        Enrollment e = findEnrollmentByID(s.sectionID);
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

    public static String getAcademicYear(Section s) {
        return s.academicYear;
    }

    public static String getProfessorID(Section s) {
        return s.professorID;
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
