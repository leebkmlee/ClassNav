package DATA;
import java.util.ArrayList;
import UI.*;

import static DATA.Program.programs;

public class College {
    private String collegeCode;
    private String collegeDescription;

    public College(String collegeCode, String collegeDescription) {
        this.collegeCode = collegeCode;
        this.collegeDescription = collegeDescription;
    }

    static ArrayList<College> colleges = new ArrayList<>();
    static {
        College.colleges.add(new College("CICT", "College of Information and Communication Technology"));
        College.colleges.add(new College("COED", "College of Education"));
        College.colleges.add(new College("COE", "College of Engineering"));
        College.colleges.add(new College("CAS", "College of Arts and Sciences"));
        College.colleges.add(new College("CBA", "College of Business Administration"));
    }

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

    public static void displayCollege(String collegeCode) {
        int innerWidth = 70;
        College c = findCollege(collegeCode);
        if (c == null) {
            UI.print("College not found");
            return;
        }
        String collegeDescription = College.getCollegeDescription(c);
        UI.header("COLLEGE INFORMATION");
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("  COLLEGE CODE            : %s", College.getCollege(c)));
        if (collegeDescription.length() <= 40)
            System.out.printf("│%-" + innerWidth + "s│\n", String.format("  COLLEGE DESCRIPTION     : %s", collegeDescription));
        else {
            System.out.printf("│%-" + innerWidth + "s│\n", "  COLLEGE DESCRIPTION     :");
            System.out.printf("│%-" + innerWidth + "s│\n", "  " + collegeDescription);
        }
        UI.footer();
    }

    public static void displayPrograms(String collegeCode) {
        int innerWidth = 70;
        College c = findCollege(collegeCode);
        if (c == null) {
            UI.print("College not found");
            return;
        }
        UI.header("PROGRAM LIST");
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("  COLLEGE CODE     : %s", College.getCollege(c)));
        System.out.printf("│%-" + innerWidth + "s│\n", "  Programs under " + College.getCollegeDescription(c) + ":");
        for (Program p : programs) {
            if (Program.getCollegeCode(p).equals(c.getCollegeCode())) {
                String program = String.format("    %-10s %-40s",
                        Program.getProgram(p), Program.getProgramDescription(p));
                System.out.printf("│%-" + innerWidth + "s│\n", program);
            }
        }
        UI.footer();
    }
}
