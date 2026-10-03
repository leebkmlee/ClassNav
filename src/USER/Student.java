package USER;

public class Student extends User{

    private String enrollmentStatus;

    public Student(String userID, String lastName, String firstName, String middleInitial, String emailAddress,
                   String password, String address, int postalCode, String birthDate, char gender, String enrollmentStatus) {
        super(userID, lastName, firstName, middleInitial, emailAddress, password, address, postalCode, birthDate, gender);
        this.enrollmentStatus = enrollmentStatus;
    }
    // displayInfo()

}
