
package DATA;

import java.util.ArrayList;
import UI.*;
import static UI.UI.in;

public class College {
    // FIELDS
    private String collegeCode, collegeDescription;

    public static ArrayList<College> colleges = new ArrayList<>();
    public static ArrayList<College> archivedColleges = new ArrayList<>();

    // CONSTRUCTOR
    public College(String collegeCode, String collegeDescription) {
        this.collegeCode = collegeCode.trim().toUpperCase();
        this.collegeDescription = collegeDescription.trim();
    }

    // DUMMY DATA
    static {
        colleges.add(new College("CAFA", "College of Architecture and Fine Arts"));
        colleges.add(new College("CAL", "College of Arts and Letters"));
        colleges.add(new College("CBEA", "College of Business Education and Accountancy"));
        colleges.add(new College("CCJE", "College of Criminal Justice Education"));
        colleges.add(new College("CHTM", "College of Hospitality and Tourism Management"));
        colleges.add(new College("CICT", "College of Information and Communication Technology"));
        colleges.add(new College("CIT", "College of Industrial Technology"));
        colleges.add(new College("COL", "College of Law"));
        colleges.add(new College("CON", "College of Nursing"));
        colleges.add(new College("COE", "College of Engineering"));
        colleges.add(new College("CPTED", "College of Professional Teacher Education"));
        colleges.add(new College("CS", "College of Science"));
        colleges.add(new College("CSER", "College of Sports, Exercise and Recreation"));
        colleges.add(new College("CSSP", "College of Social Sciences and Philosophy"));
    }

    // GETTER METHODS
    public String getCollegeCode() {
        return collegeCode;
    }
    public String getCollegeDescription() {
        return collegeDescription;
    }

    // COLLEGE SETTER METHODS
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
    public void setCollegeDescription(String collegeDescription) {

        if (collegeDescription == null || collegeDescription.trim().isEmpty())
            throw new IllegalArgumentException("College description cannot be empty.");

        for (College c : colleges) {
            if (c != this && collegeDescription.trim().equalsIgnoreCase(c.getCollegeDescription())) {
                throw new IllegalArgumentException("College description already exists.");
            }
        }

        this.collegeDescription = collegeDescription.trim();
    }

    // FIND METHODS
    public static College findCollege(String collegeCode) {
        if (collegeCode == null || collegeCode.trim().isEmpty())
            return null;

        for (College c : colleges) {
            if (collegeCode.trim().equalsIgnoreCase(c.getCollegeCode()))
                return c;
        }

        return null;
    }
    public static College findCollegeDesc(String collegeDescription) {
        if (collegeDescription == null || collegeDescription.trim().isEmpty())
            return null;

        for (College c : colleges) {
            if (collegeDescription.trim().equalsIgnoreCase(c.getCollegeDescription()))
                return c;
        }

        return null;
    }

    // DISPLAY METHODS
    public static void displayCollege(String collegeCode) {

        College c = findCollege(collegeCode);

        if (c == null) {
            UI.print("College not found");
            return;
        }

        String collegeDescription = c.getCollegeDescription();

        UI.header("COLLEGE INFORMATION");
        UI.liner(String.format("  COLLEGE CODE            : %s", c.getCollegeCode()));
        String collegeDesc = collegeDescription;
        if (collegeDesc.length() > 35) collegeDesc = collegeDesc.substring(0, 35) + " ...";

        UI.liner(String.format("  COLLEGE DESCRIPTION     : %s", collegeDesc));


        UI.footer();
    }
    public static void displayColleges() {
        UI.header("COLLEGES");

        if (colleges.isEmpty()) {
            UI.liner("  No colleges found.");
        } else {
            for (College c : colleges) {

                if (c == null)
                    continue;

                String college = String.format("  %-10s %-40s",
                        c.getCollegeCode(), c.getCollegeDescription());

                UI.liner(college);
            }
        }

        UI.footer();
    }
    public static void displayArchivedColleges() {
        UI.header("ARCHIVED COLLEGES");

        if (archivedColleges.isEmpty()) {
            UI.liner("  No archived colleges found.");
        } else {
            for (College c : archivedColleges) {

                if (c == null)
                    continue;

                String college = String.format("  %-10s %-40s",
                        c.getCollegeCode(), c.getCollegeDescription());

                UI.liner(college);
            }
        }

        UI.footer();
    }

    // MANAGE METHODS
    public static void createCollege() {
        String collegeDescription = "", collegeCode = "", choice;
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
                        else if (findCollege(collegeCode) != null)
                            UI.print("College Code already exists");
                    } while (collegeCode.isEmpty() || findCollege(collegeCode) != null);
                    break;

                case "2":
                    do {
                        System.out.print("   Enter College Description > ");
                        collegeDescription = in.nextLine().trim();

                        if (collegeDescription.isEmpty())
                            UI.print("Invalid Input");
                        else if (College.findCollegeDesc(collegeDescription) != null)
                            UI.print("College Description already exists");
                    } while (collegeDescription.isEmpty() || College.findCollegeDesc(collegeDescription) != null);
                    break;

                case "/":
                    if (collegeCode.isEmpty() || collegeDescription.isEmpty()) {
                        UI.print("Details are incomplete");
                    } else {
                        College.colleges.add(new College(collegeCode, collegeDescription));
                        UI.print("College successfully created");
                        College.displayCollege(collegeCode);
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
        String input, choice, collegeDescription, collegeCode;
        boolean submit = false;

        UI.header("EDIT EXISTING COLLEGE");

        do {
            System.out.print("  Enter College Code > ");
            input = in.nextLine().trim().toUpperCase();

            if (input.isEmpty() || findCollege(input) == null)
                UI.print("College does not exist");
        } while (input.isEmpty() || findCollege(input) == null);

        College c = College.findCollege(input);

        if (c == null) {
            UI.print("College not found");
            return;
        }

        collegeCode = c.getCollegeCode();
        collegeDescription = c.getCollegeDescription();

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
                        else if (findCollege(collegeCode) != null &&
                                !collegeCode.equalsIgnoreCase(c.getCollegeCode()))
                            UI.print("College Code already exists");
                    } while (collegeCode.isEmpty() ||
                            (findCollege(collegeCode) != null &&
                                    !collegeCode.equalsIgnoreCase(c.getCollegeCode())));
                    break;

                case "2":
                    do {
                        System.out.print("   Enter College Description > ");
                        collegeDescription = in.nextLine().trim();

                        if (collegeDescription.isEmpty())
                            UI.print("Invalid Input");
                        else if (College.findCollegeDesc(collegeDescription) != null &&
                                !collegeDescription.equalsIgnoreCase(c.getCollegeDescription()))
                            UI.print("College Description already exists");
                    } while (collegeDescription.isEmpty() ||
                            (College.findCollegeDesc(collegeDescription) != null &&
                                    !collegeDescription.equalsIgnoreCase(c.getCollegeDescription())));
                    break;

                case "/":
                    c.setCollegeCode(collegeCode);
                    c.setCollegeDescription(collegeDescription);

                    UI.print("College successfully edited");
                    College.displayCollege(c.getCollegeCode());
                    submit = true;
                    break;

                case "X":
                    submit = true;
                    break;
            }
        }
    }
    public static void archiveCollege() {
        String input, choice;

        UI.print("ARCHIVE EXISTING COLLEGE");

        do {
            System.out.print("  Enter College Code > ");
            input = in.nextLine().trim().toUpperCase();

            if (input.isEmpty() || findCollege(input) == null)
                UI.print("College does not exist");
        } while (input.isEmpty() || findCollege(input) == null);

        College c = College.findCollege(input);

        if (c == null) {
            UI.print("College not found");
            return;
        }

        displayCollege(c.getCollegeCode());

        do {
            System.out.print("  Confirm College Deletion? (Y/N) > ");
            choice = in.nextLine().trim().toUpperCase();

            if (!choice.matches("[YN]"))
                UI.print("Invalid Input");
        } while (!choice.matches("[YN]"));

        if (choice.equals("Y")) {
            archivedColleges.add(c);
            colleges.remove(c);
            UI.print("College successfully archived");
        }
        else UI.print("Archiving cancelled");
    }
    public static void collegeManage(String text, String collegeCode, String collegeDescription) {
        String collegeDesc = collegeDescription;
        if (collegeDesc.length() > 35) collegeDesc = collegeDesc.substring(0, 35) + " ...";
        UI.header(text.toUpperCase() + ": COLLEGE INFORMATION");
        UI.liner(" [1] COLLEGE CODE         : " + collegeCode);
        UI.liner(" [2] COLLEGE DESCRIPTION  : " + collegeDesc);
        UI.liner("");
        UI.option();
        UI.footer();
    }
}