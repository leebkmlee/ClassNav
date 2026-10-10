package USER;
import UI.*;
import java.util.Scanner;
public class Admin extends User{
    private String role;
    public Admin (String userID, String lastName, String firstName, String middleInitial, String emailAddress,
                  String password, String address, int postalCode, String birthDate, char sex, String role) {
        super(userID, lastName, firstName, middleInitial, emailAddress, password, address, postalCode, birthDate, sex);
        this.role = role;
    }
    static Scanner in = new Scanner(System.in);
    public static void manageRooms() {
        int choice; boolean valid = false;
        UI.header("MANAGE ROOMS");
        System.out.printf("│%-" + innerWidth + "s│\n", "  [1] Add Room");
        System.out.printf("│%-" + innerWidth + "s│\n", "  [2] Edit Room");
        System.out.printf("│%-" + innerWidth + "s│\n", "  [3] Delete Room");
        UI.footer();
        do {
            System.out.print("  Select > ");
            try {
                choice = in.nextInt();
                in.nextLine();
                if (!(choice >= 1 && choice <= 3)) UI.print("Invalid Input");
                else valid = true;
            }
            catch (Exception e) {
                in.nextLine();
                UI.print("Invalid Input");
            }
        } while (!valid);
    }
}
