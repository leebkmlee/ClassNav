package MAIN;
import DATA.Building;
import USER.*;

class Main {
    public static void main(String[] args) {
        User.initializeUsers();
        User.findUser("S0001").menu();
        User.start();
    }
}

// S0001  S0001@bulsu.edu.ph   Student001!
// F0001  F0001@bulsu.edu.ph   Faculty001!
// DH0001 DH0001@bulsu.edu.ph  Head001! 9