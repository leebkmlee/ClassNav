package LOGIN;

import UI.*;
import USER.User;

import java.util.Scanner;

public class Login {

    static Scanner in = new Scanner(System.in);

    static final int innerWidth = 70;

    public static void loginPassword() {
        boolean authenticated = false; int tries = 2;

        UI.print("PORTAL LOG-IN");

        while (!authenticated && tries != -1) {

            System.out.print("  Enter ID            : ");
            String userID = in.nextLine();

            if (User.getUser(userID) != null) {
                while (!authenticated && tries != -1) {
                    System.out.print("  Enter Email Address : ");
                    String emailAddress = in.nextLine();

                    System.out.print("  Enter Password      : ");
                    String password = in.nextLine();

                    if (User.verifyEmail(userID, emailAddress) && User.verifyPassword(userID, password)) {
                        authenticated = true;
                        UI.print("LOGIN SUCCESSFUL! Welcome to ClassNav.");
                    }
                    else {
                        if (tries != 0) {
                            String message = "Incorrect Email or Password. Try again.";
                            String attempt;
                            if (tries == 2) attempt = tries + " attempts remaining.";
                            else attempt = tries + " attempt remaining.";
                            UI.print(message, attempt);
                        }
                        tries--;
                    }
                }

            }
            else UI.print("ID does not exist. Try again.");
        }

        if (tries == -1) {
            UI.print("Ran out of attempts.");
            System.out.println("Exiting system...");
            System.exit(0);
        }
    }
}