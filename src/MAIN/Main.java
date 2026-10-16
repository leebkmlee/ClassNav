package MAIN;
import DATA.*;
import UI.*;
import USER.*;
class Main {
    public static void main(String[] args) {
        Faculty.displaySchedules("F001");

        Faculty.viewNotifications("F001");
//        Admin.manageSection();
        User.start();
    }
}