import java.util.Scanner;

public class User {
    public static Scanner in = new Scanner(System.in);

    public static void displayUser(){
        System.out.println("========================");
        System.out.println("WELCOME TO CLASS NAV");
        System.out.println("User: " +
                "\t [1] Student " +
                "\t [2] Faculty " +
                "\t [3]Admin " +
                "\t [4] Exit");
        System.out.println("========================");
    }

    public static void loginPassword(String userType, String correctID, String correctPassword){
        System.out.print("Enter " + userType + " password: ");
        String userID = in.nextLine();

        System.out.print("Enter " + correctID + " password: ");
        String password = in.nextLine();

        while(!userID.equals(correctID) || !password.equals(correctPassword)){
            System.out.print("Incorrect Username or Password, please try again: ");

            System.out.print("Enter " + userType + " password: ");
            userID = in.nextLine();

            System.out.print("Enter " + correctID + " password: ");
            password = in.nextLine();
        }

        System.out.println("LOGIN SUCCESSFUL!");
    }
    public static void main(String [] args){
        int num;
        do {
            displayUser();

            num = in.nextInt();
            in.nextLine();

            switch (num) {
                case 1:
                    loginPassword("Student", "S001", "Student123");
                    break;

                case 2:
                    loginPassword("Faculty", "F001", "Faculty123");
                    break;

                case 3:
                    loginPassword("Admin", "A001", "Admin123");
                    break;

                case 4:
                    System.out.println("Exiting Class Nav...");
                    break;

                default:
                    System.out.println("Invalid Input");
                    break;

            }
        } while (num != 4);

        System.exit(0);
    }
}
