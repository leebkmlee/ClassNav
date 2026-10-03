package USER;

public class Faculty extends User{
    private String contactNumber;
    public Faculty (String userID, String lastName, String firstName, String middleInitial, String emailAddress,
                    String password, String address, int postalCode, String birthDate, char gender, String contactNumber) {
        super(userID, lastName, firstName, middleInitial, emailAddress, password, address, postalCode, birthDate, gender);
        this.contactNumber = contactNumber;
    }
}
