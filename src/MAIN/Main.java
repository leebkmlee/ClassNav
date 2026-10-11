package MAIN;
import DATA.*;
import UI.*;
import USER.*;
class Main {
    public static void main(String[] args) {
        Building.displayRooms("BLD01");
        Faculty.requestRoom("F001");
        User.start();
    }
}