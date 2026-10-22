
package DATA;

import java.util.ArrayList;
import UI.*;

public class College {

    private String collegeCode; // primary key
    private String collegeDescription;

    public static ArrayList<College> colleges = new ArrayList<>();

    public College(String collegeCode, String collegeDescription) {

        if (collegeCode == null || collegeCode.trim().isEmpty())
            throw new IllegalArgumentException("College code cannot be empty.");

        if (collegeDescription == null || collegeDescription.trim().isEmpty())
            throw new IllegalArgumentException("College description cannot be empty.");

        if (existCollegeCode(collegeCode))
            throw new IllegalArgumentException("College code already exists.");

        if (existCollegeDesc(collegeDescription))
            throw new IllegalArgumentException("College description already exists.");

        this.collegeCode = collegeCode.trim().toUpperCase();
        this.collegeDescription = collegeDescription.trim();
    }

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

    public String getCollegeDescription() {
        return collegeDescription;
    }

    public static College findCollege(String collegeCode) {

        if (collegeCode == null || collegeCode.trim().isEmpty())
            return null;

        for (College c : colleges) {
            if (collegeCode.trim().equalsIgnoreCase(c.getCollegeCode()))
                return c;
        }

        return null;
    }

    public static String getCollege(College c) {
        return c == null ? null : c.getCollegeCode();
    }

    public static String getCollegeDescription(College c) {
        return c == null ? null : c.getCollegeDescription();
    }

    public static boolean existCollegeCode(String collegeCode) {
        return findCollege(collegeCode) != null;
    }

    public static boolean existCollegeDesc(String collegeDescription) {

        if (collegeDescription == null || collegeDescription.trim().isEmpty())
            return false;

        for (College c : colleges) {
            if (collegeDescription.trim().equalsIgnoreCase(c.getCollegeDescription()))
                return true;
        }

        return false;
    }

    // COLLEGE SETTER METHODS

    public void setCollegeDescription(String collegeDescription) {

        if (collegeDescription == null || collegeDescription.trim().isEmpty())
            throw new IllegalArgumentException("College description cannot be empty.");

        for (College c : colleges) {
            if (c != this &&
                    collegeDescription.trim().equalsIgnoreCase(c.getCollegeDescription())) {
                throw new IllegalArgumentException("College description already exists.");
            }
        }

        this.collegeDescription = collegeDescription.trim();
    }

    public void setCollegeCode(String collegeCode) {

        if (collegeCode == null || collegeCode.trim().isEmpty())
            throw new IllegalArgumentException("College code cannot be empty.");

        for (College c : colleges) {
            if (c != this &&
                    collegeCode.trim().equalsIgnoreCase(c.getCollegeCode())) {
                throw new IllegalArgumentException("College code already exists.");
            }
        }

        this.collegeCode = collegeCode.trim().toUpperCase();
    }

    // COLLEGE METHODS

    public static void displayColleges() {

        UI.header("COLLEGES");

        for (College c : colleges) {
            String college = String.format("  %-10s %-40s",
                    College.getCollege(c), College.getCollegeDescription(c));

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

        String collegeDescription = c.getCollegeDescription();

        UI.header("COLLEGE INFORMATION");
        UI.liner(String.format("  COLLEGE CODE            : %s", c.getCollegeCode()));

        if (collegeDescription.length() <= 40)
            UI.liner(String.format("  COLLEGE DESCRIPTION     : %s", collegeDescription));
        else {
            UI.liner("  COLLEGE DESCRIPTION     :");
            UI.liner("  " + collegeDescription);
        }

        UI.footer();
    }
}