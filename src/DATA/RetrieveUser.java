package DATA;
import USER.*;
import java.util.ArrayList;
public class RetrieveUser {
    ArrayList<User> users = new ArrayList<>();
    public RetrieveUser() {
        users.add(new Student("S001", "Josh Sabulao", "S001@bulsu.edu.ph", "2025004151", "CSJDM, Bulacan", 3023, "06/03/07", 'M'));
        users.add(new Faculty("F001", "Elijah Kim", "F001@bulsu.edu.ph", "elijahmasikip", "Malolos, Bulacan", 3000, "04/20/07", 'M', "09293268930"));
        users.add(new Admin("A001", "Andrei Maangas", "A001@bulsu.edu.ph", "4ndr31b4tumb4k4l", "Guguinto, Bulacan", 6767, "09/17/07", 'M', "Computer Programmer I"));
    }

    public User getUser(String userID) {
        for (User u : users) {
            if (userID.equals(u.getUserID())) return u;

        }
        return null;
    }

    public boolean verifyEmail(String userID, String emailAddress) {
        for (User u : users) {
            if (userID.equals(u.getUserID())) {
                if (emailAddress.equals(u.getEmail())) return true;
            }
        }
        return false;
    }

    public boolean verifyPassword(String userID, String password) {
        for (User u : users) {
            if (userID.equals(u.getUserID())) {
                if (password.equals(u.getPassword())) return true;
            }
        }
        return false;
    }
}
