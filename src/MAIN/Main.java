package MAIN;
import DATA.*;
import UI.*;
import USER.*;

import java.util.Objects;

class Main {
    public static void main(String[] args) {
        User.initializeUsers();
        User.start();
    }
}