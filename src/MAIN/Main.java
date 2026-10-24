package MAIN;
import DATA.*;
import USER.*;

class Main {
    public static void main(String[] args) {
        User.initializeUsers();
        DepartmentHead dh = DepartmentHead.findDepartmentHead("DH0001");
        dh.menu();
        College.archiveCollege();
        CourseRegistration.createRegistration();

    }
}