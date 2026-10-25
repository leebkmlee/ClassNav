package MAIN;
import USER.*;

class Main {
    public static void main(String[] args) {
        User.initializeUsers();
        User u = User.findUser("DH0001");
        u.menu();
    }
}

// S0001  S0001@bulsu.edu.ph   Student001!
// F0001  F0001@bulsu.edu.ph   Faculty001!
// DH0001 DH0001@bulsu.edu.ph  Head001!