package DATA;
import java.util.ArrayList;
import UI.*;

public class College {
    private String collegeCode;
    private String collegeDescription;

    public College(String collegeCode, String collegeDescription) {
        this.collegeCode = collegeCode;
        this.collegeDescription = collegeDescription;
    }

    public static ArrayList<College> colleges = new ArrayList<>();
    static {
        colleges.add(new College("CICT", "College of Information and Communication Technology"));
        colleges.add(new College("COED", "College of Education"));
        colleges.add(new College("COE", "College of Engineering"));
        colleges.add(new College("CAS", "College of Arts and Sciences"));
        colleges.add(new College("CBA", "College of Business Administration"));
    }

    // COLLEGE GETTER METHODS
    public String getCollegeCode() {
        return collegeCode;
    }
    public static College findCollege(String collegeCode) {
        for (College c : colleges) if (collegeCode.equals(c.getCollegeCode())) return c;
        return null;
    }
    public static String getCollege(College c) {
        return c.collegeCode;
    }
    public static String getCollegeDescription(College c) {
        return c.collegeDescription;
    }
    public static boolean existCollegeCode(String collegeCode) {
        for (College c : colleges) if (collegeCode.equals(c.getCollegeCode())) return  true;
        return false;
    }
    public static boolean existCollegeDesc(String collegeDescription) {
        for (College c : colleges) if (collegeDescription.equals(College.getCollegeDescription(c))) return true;
        return false;
    }

    // COLLEGE SETTER METHODS
    public void setCollegeDescription(String collegeDescription) {
        this.collegeDescription = collegeDescription;
    }
    public void setCollegeCode(String collegeCode) {
       this.collegeCode = collegeCode;
    }

    // COLLEGE METHODS
    public static void displayColleges(){
        UI.header("COLLEGES");
        for  (College c : colleges) {
            String college = String.format( "  %-10s %-40s", College.getCollege(c), College.getCollegeDescription(c));
            UI.liner(college);
        }
        UI.footer();
    }
    public static void displayColleges(String collegeCode) {
        College c = findCollege(collegeCode);
        if (c == null) {
            UI.print("College not found");
            return;
        }
        String collegeDescription = College.getCollegeDescription(c);
        UI.header("COLLEGE INFORMATION");
        UI.liner(String.format("  COLLEGE CODE            : %s", College.getCollege(c)));
        if (collegeDescription.length() <= 40) UI.liner(String.format("  COLLEGE DESCRIPTION     : %s", collegeDescription));
        else {
            UI.liner("  COLLEGE DESCRIPTION     :");
            UI.liner("  " + collegeDescription);
        }
        UI.footer();
    }
}
