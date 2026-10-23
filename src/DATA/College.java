
package DATA;

import java.util.ArrayList;
import UI.*;
import static UI.UI.in;

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

    public static void createCollege() {
        String collegeDescription = "", collegeCode = "", choice = "";
        boolean submit = false;

        while (!submit) {
            collegeManage("CREATE", collegeCode, collegeDescription);

            do {
                System.out.print("  Select > ");
                choice = in.nextLine().trim().toUpperCase();
                if (!choice.matches("[12X]") && !choice.equals("/"))
                    UI.print("Invalid Choice");
            } while (!choice.matches("[12X]") && !choice.equals("/"));

            switch (choice) {
                case "1":
                    do {
                        System.out.print("   Enter College Code > ");
                        collegeCode = in.nextLine().trim().toUpperCase();

                        if (collegeCode.isEmpty())
                            UI.print("Invalid Input");
                        else if (College.existCollegeCode(collegeCode))
                            UI.print("College Code already exists");
                    } while (collegeCode.isEmpty() || College.existCollegeCode(collegeCode));
                    break;

                case "2":
                    do {
                        System.out.print("   Enter College Description > ");
                        collegeDescription = in.nextLine().trim();

                        if (collegeDescription.isEmpty())
                            UI.print("Invalid Input");
                        else if (College.existCollegeDesc(collegeDescription))
                            UI.print("College Description already exists");
                    } while (collegeDescription.isEmpty() || College.existCollegeDesc(collegeDescription));
                    break;

                case "/":
                    if (collegeCode.isEmpty() || collegeDescription.isEmpty()) {
                        UI.print("Details are incomplete");
                    } else {
                        College.colleges.add(new College(collegeCode, collegeDescription));
                        UI.print("College successfully created");
                        College.displayColleges(collegeCode);
                        submit = true;
                    }
                    break;

                case "X":
                    submit = true;
                    break;
            }
        }
    }

    public static void editCollege() {
        String input = "", choice = "", collegeDescription, collegeCode;
        boolean submit = false;

        UI.header("EDIT EXISTING COLLEGE");

        do {
            System.out.print("  Enter College Code > ");
            input = in.nextLine().trim().toUpperCase();

            if (input.isEmpty() || !College.existCollegeCode(input))
                UI.print("College does not exist");
        } while (input.isEmpty() || !College.existCollegeCode(input));

        College c = College.findCollege(input);

        if (c == null) {
            UI.print("College not found");
            return;
        }

        collegeCode = College.getCollege(c);
        collegeDescription = College.getCollegeDescription(c);

        while (!submit) {
            collegeManage("EDIT", collegeCode, collegeDescription);

            do {
                System.out.print("  Select > ");
                choice = in.nextLine().trim().toUpperCase();

                if (!choice.matches("[12X]") && !choice.equals("/"))
                    UI.print("Invalid Input");
            } while (!choice.matches("[12X]") && !choice.equals("/"));

            switch (choice) {
                case "1":
                    do {
                        System.out.print("   Enter College Code > ");
                        collegeCode = in.nextLine().trim().toUpperCase();

                        if (collegeCode.isEmpty())
                            UI.print("Invalid Input");
                        else if (College.existCollegeCode(collegeCode) &&
                                !collegeCode.equalsIgnoreCase(College.getCollege(c)))
                            UI.print("College Code already exists");
                    } while (collegeCode.isEmpty() ||
                            (College.existCollegeCode(collegeCode) &&
                                    !collegeCode.equalsIgnoreCase(College.getCollege(c))));
                    break;

                case "2":
                    do {
                        System.out.print("   Enter College Description > ");
                        collegeDescription = in.nextLine().trim();

                        if (collegeDescription.isEmpty())
                            UI.print("Invalid Input");
                        else if (College.existCollegeDesc(collegeDescription) &&
                                !collegeDescription.equalsIgnoreCase(College.getCollegeDescription(c)))
                            UI.print("College Description already exists");
                    } while (collegeDescription.isEmpty() ||
                            (College.existCollegeDesc(collegeDescription) &&
                                    !collegeDescription.equalsIgnoreCase(College.getCollegeDescription(c))));
                    break;

                case "/":
                    c.setCollegeCode(collegeCode);
                    c.setCollegeDescription(collegeDescription);

                    UI.print("College successfully edited");
                    College.displayColleges(College.getCollege(c));
                    submit = true;
                    break;

                case "X":
                    submit = true;
                    break;
            }
        }
    }

    public static void deleteCollege() {
        String input, choice;

        UI.print("DELETE EXISTING COLLEGE");

        do {
            System.out.print("  Enter College Code > ");
            input = in.nextLine().trim().toUpperCase();

            if (input.isEmpty() || !College.existCollegeCode(input))
                UI.print("College does not exist");
        } while (input.isEmpty() || !College.existCollegeCode(input));

        College c = College.findCollege(input);

        if (c == null) {
            UI.print("College not found");
            return;
        }

        College.displayColleges(College.getCollege(c));

        do {
            System.out.print("  Confirm College Deletion? (Y/N) > ");
            choice = in.nextLine().trim().toUpperCase();

            if (!choice.matches("[YN]"))
                UI.print("Invalid Input");
        } while (!choice.matches("[YN]"));

        if (choice.equals("Y")) {
            College.colleges.remove(c);
            UI.print("College successfully deleted");
        }
    }

    private static void collegeManage(String text, String collegeCode, String collegeDescription) {
        UI.header(text.toUpperCase() + ": COLLEGE INFORMATION");
        UI.liner(String.format(" [1] COLLEGE CODE         : %s", collegeCode));
        UI.liner(String.format(" [2] COLLEGE DESCRIPTION  : %s", collegeDescription));
        UI.liner("");
        UI.option();
        UI.footer();
    }
}