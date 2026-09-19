import java.util.Scanner;
import Console.*;
public class App {
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        boolean permissionGranted = false;
        
        do {
            System.out.println("Welcome to ClassNav!\n");
            System.out.print("[User Type]: ");
            String userType = in.nextLine();
            System.out.print("[Password]: ");
            String password = in.nextLine();

            switch (userType) {
                case "Student":
                    if (password.equals("student123")) {
                        System.out.println("\nWelcome, Student!");
                        permissionGranted = true;
                    } else {
                        System.out.println("\nIncorrect password for Student.");
                    }
                    break;
                case "Faculty":
                    if (password.equals("faculty123")) {
                        System.out.println("\nWelcome, Faculty!");
                        permissionGranted = true;
                    } else {
                        System.out.println("\nIncorrect password for Faculty.");
                    }
                    break;
                case "Admin":
                    if (password.equals("admin123")) {
                        System.out.println("\nWelcome, Admin!");
                        permissionGranted = true;
                    } else {
                        System.out.println("\nIncorrect password for Admin.");
                    }
                    break;
                default:
                    System.out.println("\nInvalid user type.");
            }
        } while (!permissionGranted);
    }
}