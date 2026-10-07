package DATA;

import java.util.ArrayList;
import java.util.Arrays;

public class Enrollement {
    private String enrollmentID;
    private String sectionID; // foreign key
    private String studentNumber; // foreign key
    private final ArrayList<Course> enrolledCourses;

    Enrollement(String enrollmentID, String sectionID, String studentNumber, ArrayList<Course> enrolledCourses) {
        this.enrollmentID = enrollmentID;
        this.sectionID = sectionID;
        this.studentNumber = studentNumber;
        this.enrolledCourses = enrolledCourses;
    }

    private static ArrayList<Enrollement> enrollements = new ArrayList<>();
    static {
        enrollements.add(new Enrollement("0001", "0001", "2025004151", new ArrayList<>(Arrays.asList(
                new Course("PE 12", "PathFit 3", 2, "PE"),
                new Course("RLW 101", "Rizal Life and Works", 3, "CAL"),
                new Course("ETH 101", "Ethics", 3, "CAL"),
                new Course("STS 101", "Science, Technology, and Society", 3, "CS"),
                new Course("IT 204", "Networking", 3, "CICT"),
                new Course("CC 106", "Information Management", 3, "CICT"),
                new Course("IT 203", "Object-Oriented Programming", 3, "CICT"),
                new Course("IT 205", "Quantitative Methods", 3, "CICT"),
                new Course("CC 105", "Data Structure Algorithm", 3, "CICT")
        ))));
        enrollements.add(new Enrollement("0002", "0001", "20254155", new ArrayList<>(Arrays.asList(
                new Course("IT 204", "Networking", 3, "CICT"),
                new Course("CC 106", "Information Management", 3, "CICT"),
                new Course("IT 203", "Object-Oriented Programming", 3, "CICT"),
                new Course("IT 205", "Quantitative Methods", 3, "CICT"),
                new Course("CC 105", "Data Structure Algorithm", 3, "CICT")
        ))));
        enrollements.add(new Enrollement("0003", "0002", "2025004156", new ArrayList<>(Arrays.asList(
                new Course("CC 106", "Information Management", 3, "CICT"),
                new Course("IT 203", "Object-Oriented Programming", 3, "CICT"),
                new Course("IT 205", "Quantitative Methods", 3, "CICT"),
                new Course("CC 105", "Data Structure Algorithm", 3, "CICT")
        ))));
    }

    public String getEnrollmentID() {return enrollmentID;}

    public ArrayList<Course> getEnrolledCourses() {return enrolledCourses;}

    public void displayEnrollment() {
        int innerWidth = 70;
        System.out.println("\n┌" + "─".repeat(innerWidth) + "┐");
        String title = "ENROLLMENT INFORMATION";
        int left = (innerWidth - title.length()) / 2;
        int right = innerWidth - title.length() - left;
        System.out.println("│" + " ".repeat(left) + title + " ".repeat(right) + "│");
        System.out.println("├" + "─".repeat(innerWidth) + "┤");
        System.out.printf("│  STUDENT ID   : %-" + (innerWidth - 16) + "s│\n", studentNumber);
        System.out.printf("│  SECTION ID   : %-" + (innerWidth - 16) + "s│\n", sectionID);
        System.out.println("├" + "─".repeat(innerWidth) + "┤");
        System.out.printf("│  Enrollment ID      : %-" + (innerWidth - 16) + "s│\n", enrollmentID);
        for (Course c : enrolledCourses) {
            System.out.printf("│%-" + (innerWidth - 16) + "s│\n", c);
        }
        System.out.println("└" + "─".repeat(innerWidth) + "┘\n");
    }
}
