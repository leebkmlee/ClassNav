package USER;

public class Faculty extends User{
    private String contactNumber;
    public Faculty (String userID, String userName, String emailAddress, String password, String address, int postalCode, String birthDate, char gender, String contactNumber) {
        super(userID, userName, emailAddress, password, address, postalCode, birthDate, gender);
        this.contactNumber = contactNumber;
    }
}
