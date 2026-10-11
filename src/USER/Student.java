package USER;

import DATA.Building;
import DATA.Course;
import DATA.Enrollment;
import UI.UI;

import static DATA.Enrollment.enrollements;
import static DATA.Enrollment.getStudentNumber;

public class Student extends User{

    private String enrollmentStatus;

    public Student(String userID, String lastName, String firstName, String middleInitial, String emailAddress,
                   String password, String address, int postalCode, String birthDate, char gender, String enrollmentStatus) {
        super(userID, lastName, firstName, middleInitial, emailAddress, password, address, postalCode, birthDate, gender);
        this.enrollmentStatus = enrollmentStatus;
    }


    void viewEnrollmentInformation() {
        String currentUserID = getUserID();
        final int innerWidth = 70;

        UI.header("ROOM LIST");

        for (User data : users) {
            if (data.getUserID().equals(currentUserID)) {
                System.out.printf("│%-" + innerWidth + "s│\n", String.format("  STUDENT NAME     : %s", data.getUserName()));
                System.out.printf("│%-" + innerWidth + "s│\n", String.format("  STUDENT ID       : %s", data.getUserID()));
            }
        }

        for (Enrollment e : enrollements) {
            if (getStudentNumber(e).equals(currentUserID)) {
                System.out.println("├" + "─".repeat(innerWidth) + "┤");
                System.out.printf("│%-" + innerWidth + "s│\n", String.format("  SECTION          : %s", Enrollment.getSectionID(e)));
                System.out.printf("│%-" + innerWidth + "s│\n", String.format("  ENROLLMENT ID    : %s", Enrollment.getEnrollment(e)));
                System.out.printf("│%-" + innerWidth + "s│\n", "  Enrolled Courses:");

                for (Course c : Enrollment.getEnrolledCourses(e)) {
                    String course = String.format("    %-10s %-40s %2d units",
                            Course.getCourseCode(c), Course.getCourseDescription(c), Course.getCreditUnits(c));
                    System.out.printf("│%-" + innerWidth + "s│\n", course);
                }
            }
        }
        System.out.println("└" + "─".repeat(innerWidth) + "┘");
    }
    // displayInfo()
//    private void displayInfo() {
//        System.out.println("\n┌" + "─".repeat(innerWidth) + "┐");
//        String title = "ACCOUNT PROFILE";
//        int left = (innerWidth - title.length()) / 2;
//        int right = innerWidth - title.length() - left;
//        System.out.println("│" + " ".repeat(left) + title + " ".repeat(right) + "│");
//        System.out.println("├" + "─".repeat(innerWidth) + "┤");
//        System.out.println("└" + "─".repeat(innerWidth) + "┘\n");
//    }
}
