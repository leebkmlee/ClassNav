package MAIN;
import DATA.*;
import UI.*;
import USER.*;
class Main {
    public static void main(String[] args) {
        User.initializeUsers();
        Faculty.displaySchedules("F0001");

        Faculty.viewNotifications("F0001");
//        Admin.manageSection();
        User.start();
    }
}