package MAIN;
import DATA.setUser;
import USER.User;
class Main {
    public static void main(String[] args) {
        setUser data = new setUser();
        User user = data.getUser("F001");
        user.start();
        user.displayInfo();
    }
}