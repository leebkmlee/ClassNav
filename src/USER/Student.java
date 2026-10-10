package USER;

public class Student extends User{

    private String enrollmentStatus;

    public Student(String userID, String lastName, String firstName, String middleInitial, String emailAddress,
                   String password, String address, int postalCode, String birthDate, char gender, String enrollmentStatus) {
        super(userID, lastName, firstName, middleInitial, emailAddress, password, address, postalCode, birthDate, gender);
        this.enrollmentStatus = enrollmentStatus;
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
