package DATA;

public class Section {
    private String sectionID;
    private int yearLevel;
    private int group;
    private String roomCode; // foreign key
    private String courseCode; // foreign key
    private String professorID; // foreign key

    Section(String sectionID, int yearLevel, int group, String roomCode, String courseCode, String professorID) {
        this.sectionID = sectionID;
        this.yearLevel = yearLevel;
        this.group = group;
        this.roomCode = roomCode;
        this.courseCode = courseCode;
        this.professorID = professorID;
    }
}
