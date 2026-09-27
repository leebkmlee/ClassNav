package DATA;
import USER.*;
import java.util.ArrayList;
public class setUser {
    ArrayList<User> users = new ArrayList<>();
    public setUser() {
        users.add(new Student("S001", "Josh Sabulao", "CSJDM, Bulacan", 3023, "06/03/07", 'M'));
        users.add(new Faculty("F001", "Elijah Kim", "Malolos, Bulacan", 3000, "04/20/07", 'M'));
        users.add(new Admin("A001", "Andrei Maangas", "Guguinto, Bulacan", 6767, "09/17/07", 'M'));
    }

    public User getUser(String userID) {
        for (User u : users) {
            if (userID.equals(u.getUserID())) {
                return u;
            }
        }
        return null;
    }
}
