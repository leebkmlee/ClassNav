package USER;

public class Faculty extends User{
    private String contactNumber;
    private String emailAddress;
    public Faculty (String userID, String userName, String address, int postalCode, String birthDate, char gender, String contactNumber, String emailAddress) {
        super(userID, userName, address, postalCode, birthDate, gender);
        this.contactNumber = contactNumber;
        this.emailAddress = emailAddress;
    }
}
