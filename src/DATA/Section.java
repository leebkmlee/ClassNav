package DATA;

import UI.*;
import USER.User;
import java.util.ArrayList;

import static DATA.Enrollment.enrollements;

public class Section {
    static int innerWidth = 70;

    private String sectionID;
    private int yearLevel;
    private char block;
    private int group;
    private String academicYear;
    private String roomCode; // foreign key
    private String courseCode; // foreign key
    private String professorID; // foreign key

    public Section(String sectionID, int yearLevel,char block, int group,String academicYear,
                   String roomCode, String courseCode, String professorID) {
        this.sectionID = sectionID;
        this.yearLevel = yearLevel;
        this.block = block;
        this.group = group;
        this.academicYear = academicYear;
        this.roomCode = roomCode;
        this.courseCode = courseCode;
        this.professorID = professorID;
    }

    public static ArrayList<Section> sections = new ArrayList<Section>();
    static{
        sections.add(new Section("0001",2,'B',1, "2025-2026", "PL101", "CC 106", "F002"));
        sections.add(new Section("0002",3,'A',0, "2024-2025", "ACAD 3", "ETH 101", "F001"));
        sections.add(new Section("0003",2,'B',2, "2025-2026", "PL101", "CC 106", "F002"));
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

    public static String getYearSection(Section s) {
        Course c = Course.findCourse(s.courseCode);
        if (c == null) {
            UI.print("Course not found");
            return null;
        }
        Program p = Program.findProgram(Course.getProgramCode(c));
        if (p == null) {
            UI.print("Program not found");
            return null;
        }
        String sectionText;
        if (s.group != 0) sectionText = s.yearLevel + "" + s.block + "-G" + s.group;
        else sectionText = s.yearLevel + "" + s.block;
        return p.getProgramID() + " " + sectionText;
    }

    public static String getAcademicYear(Section s) {
        return s.academicYear;
    }

    public static String getRoomCode(Section s) {
        return s.roomCode;
    }

    public static String getCourseCode(Section s) {
        return s.courseCode;
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
        for (Enrollment e : enrollements) {
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
