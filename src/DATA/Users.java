package DATA;
import USER.*;
import java.util.ArrayList;
public class Users {
    public static ArrayList<User> users = new ArrayList<>();
    static {
        users.add(new Student("S001", "Sabulao", "Josh", "P",
                "S001@bulsu.edu.ph", "2025004151",
                "CSJDM, Bulacan", 3023, "06/03/07", 'M', "Enrolled"));

        users.add(new Faculty("F001", "Kim", "Elijah", "",
                "F001@bulsu.edu.ph", "elijahmasikip",
                "Malolos, Bulacan", 3000, "04/20/07", 'M',
                "09293268930"));

        users.add(new Admin("A001", "Maangas", "Andrei", "P",
                "A001@bulsu.edu.ph", "4ndr31b4tumb4k4l",
                "Guiguinto, Bulacan", 6767, "09/17/07", 'M',
                "Computer Programmer I"));
    }

    public static User getUser(String userID) {
        for (User u : users) if (userID.equals(u.getUserID())) return u;
        return null;
    }

    public static boolean verifyEmail(String userID, String emailAddress) {
        for (User u : users) if (userID.equals(u.getUserID()) && emailAddress.equals(u.getEmail())) return true;
        return false;
    }

    public static boolean verifyPassword(String userID, String password) {
        for (User u : users) if (userID.equals(u.getUserID()) && password.equals(u.getPassword())) return true;
        return false;
    }

    public static boolean checkDuplicateName(String userName) {
        for (User u : users) if (userName.equals(u.getUserName())) return true;
        return false;
    }

    public static boolean checkDuplicateEmail(String emailAddress) {
        for (User u : users) if (emailAddress.equals(u.getEmail())) return true;
        return false;
    }
}
