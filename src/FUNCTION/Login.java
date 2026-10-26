package FUNCTION;

import UI.*;
import USER.*;
import static UI.UI.in;

public class Login {
    public static String logIn() {
        int maxAttempts = 3;

        UI.print("PORTAL LOG-IN");

        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            UI.select("Enter ID",false);
            String userID = in.nextLine().trim();
            String input = userID;
            if (userID.equalsIgnoreCase("X")) return "Back";
            else if (userID.isEmpty())UI.print("User ID is required.");

            User user = User.findUser(userID);

            if (user == null) {
                UI.print("ID does not exist. Try again.");
            } else {
                System.out.print("  Enter Email Address > ");
                String emailAddress = in.nextLine().trim();

                System.out.print("  Enter Password > ");
                String password = in.nextLine();

                if (User.isValidEmail(userID, emailAddress) && User.isValidPassword(userID, password)) {
                    if (user instanceof Faculty) {
                        String status = Faculty.getEmploymentStatus(user);

                        if (!"ACTIVE".equalsIgnoreCase(status == null ? "" : status.trim())) {
                            UI.print("Faculty account is inactive");
                            return "Ran out of attempts.";
                        }
                    }

                    UI.print("LOGIN SUCCESSFUL! Welcome to ClassNav.");
                    return userID;
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
        return "Ran out of attempts.";
    }
}