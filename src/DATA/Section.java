package DATA;

import UI.*;

import java.util.ArrayList;

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

    public void displaySection() {
        UI.header("SECTION DETAILS");
        System.out.printf("│%-" + innerWidth + "s│%n"," Section ID: " + sectionID);
        System.out.printf("│%-" + innerWidth + "s│%n"," Year Level: " + yearLevel);
        System.out.printf("│%-" + innerWidth + "s│%n"," Block: " + block);
        System.out.printf("│%-" + innerWidth + "s│%n"," Group: " + group);
        System.out.printf("│%-" + innerWidth + "s│%n"," Academic Year: " + academicYear);
        UI.footer();
    }

    public String getSectionID() {
        return sectionID;
    }

    public String getSection(){
        if (group != 0){
            return yearLevel + group + " - " + block;
        }
        return String.valueOf(yearLevel + group);
    }

    public static ArrayList<Section> section = new ArrayList<Section>();
    static{
        section.add(new Section("0001",2,'B',2, "2025-2026", "Lab101", "CC 106", "F002"));
        section.add(new Section("0002",3,'A',0, "2024-2025", "NSTP103", "ETH 101", "F001"));
    }

    // not done yet 07/10/2026 10:36PM
    public void getStudents(){

    }

}
