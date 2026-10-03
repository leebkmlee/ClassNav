package LOGIN;
import DATA.RetrieveUser;
import UI.*;
import java.util.Scanner;

public class Login {

    static Scanner in = new Scanner(System.in);

    static final int innerWidth = 70;

    public static void loginPassword() {
        boolean authenticated = false; int tries = 2;

        UI.print("PORTAL LOG-IN", innerWidth);
        String headerTitle = "PORTAL LOG-IN";

//        System.out.println("\n╔" + "═".repeat(innerWidth) + "╗");
//        System.out.println("║" + centeredTitle + "║");
//        System.out.println("╚" + "═".repeat(innerWidth) + "╝");

        while (!authenticated && tries != -1) {

            System.out.print("  Enter ID            : ");
            String userID = in.nextLine();
            System.out.println(userID);

            if (RetrieveUser.getUser(userID) != null) {
                while (!authenticated && tries != -1) {
                    System.out.print("  Enter Email Address : ");
                    String emailAddress = in.nextLine();

                    System.out.print("  Enter Password      : ");
                    String password = in.nextLine();

                    if (RetrieveUser.verifyEmail(userID, emailAddress) && RetrieveUser.verifyPassword(userID, password)) {
                        authenticated = true;
                        UI.print("LOGIN SUCCESSFUL! Welcome to ClassNav.", innerWidth);
                    }
                    else {
                        if (tries != 0) {
                            String message = "Incorrect Email or Password. Try again.";
                            int left = (innerWidth - message.length()) / 2;
                            int right = innerWidth - message.length() - left;
                            String attempt;

                            if (tries == 2) attempt = tries + " attempts remaining.";
                            else attempt = tries + " attempt remaining.";

                            int attemptLeft = (innerWidth - attempt.length()) / 2;
                            int attemptRight = innerWidth - attempt.length() - attemptLeft;
                            System.out.println("\n┌" + "─".repeat(innerWidth) + "┐");
                            System.out.println("│" + " ".repeat(left) + message + " ".repeat(right) + "│");
                            System.out.println("│" + " ".repeat(attemptLeft) + attempt + " ".repeat(attemptRight) + "│");
                            System.out.println("└" + "─".repeat(innerWidth) + "┘");
                        }
                        tries--;
                    }
                }

            }
            else UI.print("ID does not exist. Try again.", innerWidth);
        }

        if (tries == -1) {
            UI.print("Ran out of attempts.", innerWidth);
            System.out.println("Exiting system...");
            System.exit(0);
        }
    }
}