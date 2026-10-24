package MAIN;
import DATA.*;
import USER.*;

class Main {
    public static void main(String[] args) {
        User.initializeUsers();
        DepartmentHead dh = DepartmentHead.findDepartmentHead("DH0001");
        assert dh != null;
        DepartmentHead.viewProfile(dh.getUserID());
        System.out.println("College Code: " + dh.getCollegeCode());
        CourseRegistration.displayRegistration("CR0001");

    }
}