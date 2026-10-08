package DATA;

import UI.*;

import java.util.ArrayList;

public class Course {
    int innerWidth = 70;
    private String courseCode;
    private String courseDescription;
    private int creditUnits;
    private String programCode; // foreign key

    public Course(String courseCode, String courseDescription, int creditUnits, String programCode) {
        this.courseCode = courseCode;
        this.courseDescription = courseDescription;
        this.creditUnits = creditUnits;
        this.programCode = programCode;
    }


    public void displayCourse(){
        UI.header("COURSE DETAILS");
        System.out.printf("│%-" + innerWidth + "s│%n", " Course Code: " + courseCode);
        System.out.printf("│%-" + innerWidth + "s│%n", " Course Description: " + courseDescription);
        System.out.printf("│%-" + innerWidth + "s│%n", " Credit Units: " + creditUnits);
        UI.footer();
    }

    public  String getCourse() {
        return courseCode;
    }


    public static String getCourseCode(Course course) {
        for (Course c: courses){
            if (course.equals(c.getCourse())){
                return c.courseCode;}
        }
        return null;
    }

    public static String getCourseDescription(Course course) {
        for (Course c: courses){
            if (course.equals(c.courseDescription)) return c.courseDescription;}
        return null;
    }

    public static int getCreditUnits(Course course) {
        for (Course c : courses){
            if(course.equals(c.creditUnits)){ return  c.creditUnits;}
        }
        return 0;
    }

    public static ArrayList<Course> courses = new ArrayList<>();
    static {
        courses.add(new Course("PE 12", "PathFit 3", 2, "PE"));
        courses.add(new Course("RLW 101", "Rizal Life and Works", 3, "CAL"));
        courses.add(new Course("ETH 101", "Ethics", 3, "CAL"));
        courses.add(new Course("STS 101", "Science, Technology, and Society", 3, "CS"));
        courses.add(new Course("IT 204", "Networking", 3, "CICT"));
        courses.add(new Course("CC 106", "Information Management", 3, "CICT"));
        courses.add(new Course("IT 203", "Object-Oriented Programming", 3, "CICT"));
        courses.add(new Course("IT 205", "Quantitative Methods", 3, "CICT"));
        courses.add(new Course("CC 105", "Data Structure Algorithm", 3, "CICT"));
    }

    //getSections()
    public Course getSections(String courseCode){
        for (Course c : courses){
            if (c.courseCode.equals(courseCode)){
                return c;
            }
        }
        return null;
    }
}