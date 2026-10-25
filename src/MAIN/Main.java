package MAIN;
import DATA.*;
import FUNCTION.Notification;
import FUNCTION.Request;
import USER.*;

class Main {
    public static void main(String[] args) {
        User.initializeUsers();
        CancelledSchedule c = CancelledSchedule.findException("CAN0001");
        System.out.println(c.getCancellationID());
        //Section.editSection();
    }
}