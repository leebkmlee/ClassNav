package MAIN;
import DATA.*;
import FUNCTION.Notification;
import FUNCTION.Request;
import USER.*;

class Main {
    public static void main(String[] args) {
        User.initializeUsers();
        Student.viewAvailableRooms();

        //Notification.displayNotifications();
        //Notification.archiveNotifications();
        //Notification.displayArchiveNotifications();
        //Request.submitRequest("F0001");
    }
}