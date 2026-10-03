package USER;

public class Admin extends User{
    private String role;
    public Admin (String userID, String lastName, String firstName, String middleInitial, String emailAddress,
                  String password, String address, int postalCode, String birthDate, char sex, String role) {
        super(userID, lastName, firstName, middleInitial, emailAddress, password, address, postalCode, birthDate, sex);
        this.role = role;
    }
}
