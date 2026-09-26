import java.util.Scanner;

class User {
    Scanner in = new Scanner(System.in);
    void start() {
        int num;
        do {
            displayUser();

            num = in.nextInt();
            in.nextLine();

            switch (num) {
                case 1:
                    Login.loginPassword("Student", "S001", "pw1");
                    break;

                case 2:
                    Login.loginPassword("Faculty", "F001", "pw2");
                    break;

                case 3:
                    Login.loginPassword("Admin", "A001", "pw3");
                    break;

                case 4:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (num != 4);
    }

    void displayUser() {
        System.out.println("=========================");
        System.out.println("|  WELCOME TO CLASS NAV |");
        System.out.println("| [1] Student           |");
        System.out.println("| [2] Faculty           |");
        System.out.println("| [3] Admin             |");
        System.out.println("| [4] Exit              |");
        System.out.println("=========================");
        System.out.print("Select user: ");
    }
}

class Login {

    static Scanner in = new Scanner(System.in);

    static void loginPassword(
            String userType,
            String correctID,
            String correctPassword) {

        System.out.print("Enter " + userType + " ID: ");
        String userID = in.nextLine();

        System.out.print("Enter " + userType + " password: ");
        String password = in.nextLine();

        while (!userID.equals(correctID)
                || !password.equals(correctPassword)) {

            System.out.println("Incorrect ID or Password.");

            System.out.print("Enter " + userType + " ID: ");
            userID = in.nextLine();

            System.out.print("Enter " + userType + " password: ");
            password = in.nextLine();
        }

        System.out.println("LOGIN SUCCESSFUL!");
    }
}

class Mainn {
    public static void main(String[] args) {
        User user = new User();
        user.start();
    }
}