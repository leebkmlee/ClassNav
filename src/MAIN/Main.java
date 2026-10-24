package MAIN;
import DATA.CancelledSchedule;
import USER.*;

class Main {
    public static void main(String[] args) {
        User.initializeUsers();
        CancelledSchedule.displayCancellations();
        User.start();
    }
}