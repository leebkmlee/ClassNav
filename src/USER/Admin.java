package USER;

public class Admin extends User{
    private String role;
    public Admin (String userID, String userName, String address, int postalCode, String birthDate, char gender, String role) {
        super(userID, userName, address, postalCode, birthDate, gender);
        this.role = role;
    }
}
