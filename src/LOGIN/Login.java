package LOGIN;
import DATA.RetrieveUser;
import USER.User;

import java.util.Scanner;
public class Login {

    static Scanner in = new Scanner(System.in);

    public static void loginPassword() {
        boolean authenticated = false; int tries = 2; boolean print = true;
        String headerTitle = "PORTAL LOG-IN";

        int innerWidth = 46;

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
                        System.out.println("\n┌" + "─".repeat(innerWidth) + "┐");
                        System.out.println("│    LOGIN SUCCESSFUL! Welcome to ClassNav.    │");
                        System.out.println("└" + "─".repeat(innerWidth) + "┘");
                    }
                    else {
                        if (tries != 0) {
                            System.out.println("\n┌" + "─".repeat(innerWidth) + "┐");
                            System.out.println("│    Incorrect Email or Password. Try again.   │");
                            String result = (tries == 2) ? "│             " + tries + " attempts remaining.            │" : "│             " + tries + " attempt remaining.             │";
                            System.out.println(result);
                            System.out.println("└" + "─".repeat(innerWidth) + "┘");
                        }
                        tries--;
                    }
                }
            }
            else {
                System.out.println("\n┌" + "─".repeat(innerWidth) + "┐");
                System.out.println("│          ID Does not exist. Try again.       │");
                System.out.println("└" + "─".repeat(innerWidth) + "┘");
            }
        }

        if (tries == -1) {
            System.out.println("\n┌" + "─".repeat(innerWidth) + "┐");
            System.out.println("│               Ran out of Attempts            │");
            System.out.println("└" + "─".repeat(innerWidth) + "┘");
            System.out.println("Exiting system...");
            System.exit(0);
        }


    }
}
