package MAIN;
import DATA.*;
import USER.*;

class Main {
    public static void main(String[] args) {
        User.initializeUsers();
        Building.displayBuildings();
        Building.displayBuilding("BLD01",true);
        Building.displayRooms("BLD01");
        Building.displayArchiveBuildings();
        Building.createBuilding();
        Building.editBuilding();
        Building.archiveBuilding();

    }
}

// S0001  S0001@bulsu.edu.ph   Student001!
// F0001  F0001@bulsu.edu.ph   Faculty001!
// DH0001 DH0001@bulsu.edu.ph  Head001!