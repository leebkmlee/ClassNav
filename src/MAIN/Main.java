package MAIN;
import DATA.*;
import USER.*;

class Main {
    public static void main(String[] args) {
        User.initializeUsers();

        Curriculum.editCurriculum();

        Building.createBuilding();
        Building.editBuilding();
        Building.archiveBuilding();
        CancelledSchedule.createCancelledSchedule();
        CancelledSchedule.archiveCancelledSchedule();
        College.createCollege();
        College.editCollege();
        College.archiveCollege();



    }
}

// S0001  S0001@bulsu.edu.ph   Student001!
// F0001  F0001@bulsu.edu.ph   Faculty001!
// DH0001 DH0001@bulsu.edu.ph  Head001!