package DATA;

public class Course {
    private String courseCode;
    private String courseDescription;
    private int creditUnits;
    private String programCode; // foreign key

    Course(String courseCode, String courseDescription, int creditUnits, String programCode) {
        this.courseCode = courseCode;
        this.courseDescription = courseDescription;
        this.creditUnits = creditUnits;
        this.programCode = programCode;
    }
}
