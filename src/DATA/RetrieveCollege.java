package DATA;
import COLLEGE.College;
import java.util.ArrayList;

public class RetrieveCollege {
    ArrayList<College> colleges = new ArrayList<>();

    RetrieveCollege() {
        colleges.add(new College("CICT", "College of Information Communication Technology"));
        colleges.add(new College("COED", "College of Education"));
        colleges.add(new College("COE", "College of Engineering"));
    }

}
