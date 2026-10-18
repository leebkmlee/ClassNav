package DATA;

import UI.*;
import USER.Student;
import USER.User;
import java.util.ArrayList;

import static DATA.Course.courses;
import static DATA.Enrollment.enrollments;

public class Section {
    private static int nextSectionID = 1;
    private String sectionID;
    private  int yearLevel;
    private  char block;
    private int group;
    private String academicYear;
    private String courseID; // foreign key

    public Section(int yearLevel, char block, int group,String academicYear, String courseID) {
        this.sectionID = String.format("SEC%03d", nextSectionID++);
        this.yearLevel = yearLevel;
        this.block = block;
        this.group = group;
        this.academicYear = academicYear;
        this.courseID = courseID;
    }

    public static ArrayList<Section> sections = new ArrayList<Section>();

    static {
        sections.add(new Section(2, 'B', 1, "2024-2025", "C001"));
        sections.add(new Section(4, 'A', 2, "2022-2023", "C002"));
        sections.add(new Section(2, 'B', 2, "2024-2025", "C003"));
        sections.add(new Section(3, 'A', 1, "2023-2024", "C004"));
        sections.add(new Section(1, 'C', 1, "2025-2026", "C005"));
        sections.add(new Section(4, 'A', 1, "2022-2023", "C006"));
        sections.add(new Section(3, 'B', 2, "2023-2024", "C007"));
        sections.add(new Section(1, 'A', 0, "2025-2026", "C008"));
        sections.add(new Section(2, 'C', 1, "2024-2025", "C009"));
        sections.add(new Section(3, 'C', 2, "2023-2024", "C010"));
        sections.add(new Section(4, 'B', 1, "2022-2023", "C011"));
        sections.add(new Section(1, 'B', 0, "2025-2026", "C012"));
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
    public void setCourseID(String courseCode){
        this.courseID = courseCode;
    }

    //getters
    public String getSectionID() {
        return sectionID;
    }
    public static String getSection(Section s){
        return s.sectionID;
    }
    public static String getYearSection(Section s) {
        Course c = Course.findCourse(Section.getCourseID(s));
        if (c == null) {
            UI.print("Course not found");
            return null;
        }
        String sectionText;
        if (s.group != 0) sectionText = s.yearLevel + "" + s.block + "-G" + s.group;
        else sectionText = s.yearLevel + "" + s.block;
        return Course.getProgramCode(c) + " " + sectionText;
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
    public static String getCourseID(Section s) {
        return s.courseID;
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
            Course c = Course.findCourse(Section.getCourseID(s));
            if (c == null) {
                UI.print("Course not found");
                return;
            }

            String section = String.format("  %-10s %-15s %-15s %-25s", Section.getSection(s),
                    Section.getYearSection(s), Section.getAcademicYear(s), Course.getFacultyID(c));
            String details = String.format("  " + Course.getProgramCode(c));
            UI.liner(section);
            UI.liner(details);

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
        UI.liner(String.format("  SECTION ID        : %s", Section.getSection(s)));
        UI.liner(String.format("  YEAR AND SECTION  : %s", Section.getYearSection(s)));
        UI.liner(String.format("  ACADEMIC YEAR     : %s", Section.getAcademicYear(s)));
        UI.liner(String.format("  COURSE ID         : %s", Section.getCourseID(s)));
        UI.footer();
    }
    public static void displayStudents(String sectionID){
        Section s = findSection(sectionID);
        if (s == null) {
            UI.print("Section not found");
            return;
        }
        UI.header("STUDENT LIST");
        UI.liner(String.format("  SECTION ID     : %s", Section.getSection(s)));
        UI.liner("  Students under " + Section.getYearSection(s) + ":");
        for (Enrollment e : enrollments) {
            if (Enrollment.getSectionID(e).equals(s.getSectionID())) {
                User student = User.getUser(Enrollment.getStudentNumber(e));
                if (student != null) {
                    String enrollment = String.format("  %-12s  %-25s  %-26s",
                            Enrollment.getStudentNumber(e), student.getUserName(), student.getEmail());
                    UI.liner(enrollment);
                }
            }
        }
        UI.footer();
    }

}