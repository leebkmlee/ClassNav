package LOGIN;
import DATA.RetrieveUser;
import USER.User;
import java.util.Scanner;

public class Login {

    static Scanner in = new Scanner(System.in);

    static final int innerWidth = 70;

    public static void loginPassword() {
        boolean authenticated = false; int tries = 2;
        String headerTitle = "PORTAL LOG-IN";
        int totalPadding = innerWidth - headerTitle.length();
        int padLeft = totalPadding / 2;
        int padRight = totalPadding - padLeft;
        String centeredTitle = " ".repeat(padLeft) + headerTitle + " ".repeat(padRight);

        System.out.println("\n╔" + "═".repeat(innerWidth) + "╗");
        System.out.println("║" + centeredTitle + "║");
        System.out.println("╚" + "═".repeat(innerWidth) + "╝");

        while (!authenticated && tries != -1) {

            System.out.print("  Enter ID            : ");
            String userID = in.nextLine();

            RetrieveUser data = new RetrieveUser();
            User user = data.getUser(userID);

            if (user != null) {
                while (!authenticated && tries != -1) {
                    System.out.print("  Enter Email Address : ");
                    String emailAddress = in.nextLine();

                    System.out.print("  Enter Password      : ");
                    String password = in.nextLine();

                    if (data.verifyEmail(userID, emailAddress) && data.verifyPassword(userID, password)) {
                        authenticated = true;
                        String message = "LOGIN SUCCESSFUL! Welcome to ClassNav.";
                        int left = (innerWidth - message.length()) / 2;
                        int right = innerWidth - message.length() - left;
                        System.out.println("\n┌" + "─".repeat(innerWidth) + "┐");
                        System.out.println("│" + " ".repeat(left) + message + " ".repeat(right) + "│");
                        System.out.println("└" + "─".repeat(innerWidth) + "┘");
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

            } else {
                String message = "ID Does not exist. Try again.";
                int left = (innerWidth - message.length()) / 2;
                int right = innerWidth - message.length() - left;
                System.out.println("\n┌" + "─".repeat(innerWidth) + "┐");
                System.out.println("│" + " ".repeat(left) + message + " ".repeat(right) + "│");
                System.out.println("└" + "─".repeat(innerWidth) + "┘");
            }
        }

        if (tries == -1) {
            String message = "Ran out of Attempts";
            int left = (innerWidth - message.length()) / 2;
            int right = innerWidth - message.length() - left;
            System.out.println("\n┌" + "─".repeat(innerWidth) + "┐");
            System.out.println("│" + " ".repeat(left) + message + " ".repeat(right) + "│");
            System.out.println("└" + "─".repeat(innerWidth) + "┘");
            System.out.println("Exiting system...");
            System.exit(0);
        }
    }
}