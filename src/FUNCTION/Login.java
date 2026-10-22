
package FUNCTION;

import UI.*;
import USER.User;

import java.util.Scanner;

public class Login {

    static Scanner in = new Scanner(System.in);

    public static void loginPassword() {

        int maxAttempts = 3;

        UI.print("PORTAL LOG-IN");

        for (int attempt = 1; attempt <= maxAttempts; attempt++) {

            System.out.print("  Enter ID            : ");
            String userID = in.nextLine().trim();

            if (User.getUser(userID) == null) {
                UI.print("ID does not exist. Try again.");
            } else {

                System.out.print("  Enter Email Address : ");
                String emailAddress = in.nextLine().trim();

                System.out.print("  Enter Password      : ");
                String password = in.nextLine();

                if (User.verifyEmail(userID, emailAddress)
                        && User.verifyPassword(userID, password)) {

                    UI.print("LOGIN SUCCESSFUL! Welcome to ClassNav.");
                    return;
                }

                UI.print("Incorrect Email or Password.");
            }

            int remaining = maxAttempts - attempt;

            if (remaining > 0) {
                String message = remaining == 1
                        ? "1 attempt remaining."
                        : remaining + " attempts remaining.";

                UI.print("Try again.", message);
            }
        }

        UI.print("Ran out of attempts.");
        System.out.println("Login unsuccessful.");
    }
}