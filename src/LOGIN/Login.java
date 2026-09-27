package LOGIN;
import java.util.Scanner;
public class Login {

    static Scanner in = new Scanner(System.in);

    public static void loginPassword(String userType, String correctID, String correctPassword) {
        boolean authenticated = false;
        String headerTitle = "PORTAL LOG-IN: " + userType.toUpperCase();

        int innerWidth = 46;

        int totalPadding = innerWidth - headerTitle.length();
        int padLeft = totalPadding / 2;
        int padRight = totalPadding - padLeft;

        String centeredTitle = " ".repeat(padLeft) + headerTitle + " ".repeat(padRight);

        System.out.println("\n╔" + "═".repeat(innerWidth) + "╗");
        System.out.println("║" + centeredTitle + "║");
        System.out.println("╚" + "═".repeat(innerWidth) + "╝");

        while (!authenticated) {
            System.out.printf("  Enter %-8s ID  : ", userType);
            String userID = in.nextLine().trim();

            System.out.printf("  Enter %-8s Password: ", userType);
            String password = in.nextLine().trim();

            if (userID.equals(correctID) && password.equals(correctPassword)) {
                authenticated = true;
                System.out.println("\n┌" + "─".repeat(innerWidth) + "┐");
                System.out.println("│  ✔ LOGIN SUCCESSFUL! Welcome to ClassNav.    │");
                System.out.println("└" + "─".repeat(innerWidth) + "┘");
            } else {
                System.out.println("\n┌" + "─".repeat(innerWidth) + "┐");
                System.out.println("│  ✘ Incorrect ID or Password. Try again.      │");
                System.out.println("└" + "─".repeat(innerWidth) + "┘");
            }
        }
    }
}
