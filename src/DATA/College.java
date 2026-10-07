package DATA;
import java.util.ArrayList;

public class College {

    public College(String collegeCode, String collegeDescription) {
        this.collegeCode = collegeCode;
        this.collegeDescription = collegeDescription;
    }

    ArrayList<College> colleges = new ArrayList<>();

    private String collegeCode;
    private String collegeDescription;


    College() {
        colleges.add(new College("CICT", "College of Information Communication Technology"));
        colleges.add(new College("COED", "College of Education"));
        colleges.add(new College("COE", "College of Engineering"));
    }

}
