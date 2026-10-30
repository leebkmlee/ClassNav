
package DATA;

import java.util.ArrayList;
import UI.*;
import USER.*;
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

        if (collegeCode == null || collegeCode.trim().isEmpty()) {
            UI.print("College code cannot be empty.");
            return;
        }

        for (College c : colleges) {
            if (c != this &&
                    collegeCode.trim().equalsIgnoreCase(c.getCollegeCode())) {
                UI.print("College code already exists.");
                return;
            }
        }

        this.collegeCode = collegeCode.trim().toUpperCase();
    }
    public void setCollegeDescription(String collegeDescription) {

        if (collegeDescription == null || collegeDescription.trim().isEmpty()) {
            UI.print("College description cannot be empty.");
            return;
        }

        for (College c : colleges) {
            if (c != this && collegeDescription.trim().equalsIgnoreCase(c.getCollegeDescription())) {
                UI.print("College description already exists.");
                return;
            }
        }

        this.collegeDescription = collegeDescription.trim();
    }

    // FIND METHODS
    public static College findCollege(String collegeCode) {
        if (collegeCode == null || collegeCode.trim().isEmpty())
            return null;

        for (College c : colleges) {
            if (c != null && c.getCollegeCode() != null
                    && collegeCode.trim().equalsIgnoreCase(c.getCollegeCode()))
                return c;
        }

        return null;
    }
    public static College findCollegeDesc(String collegeDescription) {
        if (collegeDescription == null || collegeDescription.trim().isEmpty())
            return null;

        for (College c : colleges) {
            if (c != null && c.getCollegeDescription() != null
                    && collegeDescription.trim().equalsIgnoreCase(c.getCollegeDescription()))
                return c;
        }

        return null;
    }

    // DISPLAY METHODS
    public static void displayCollege(String collegeCode, boolean print) {

        College c = findCollege(collegeCode);

        if (c == null) {
            UI.print("College not found");
            return;
        }

        String collegeDescription = c.getCollegeDescription();

        UI.header("COLLEGE INFORMATION", false);
        UI.liner(String.format("  COLLEGE CODE            : %s", c.getCollegeCode()), false);
        String collegeDesc = collegeDescription;
        if (collegeDesc.length() > 35) collegeDesc = collegeDesc.substring(0, 35) + "...";

        UI.liner(String.format("  COLLEGE DESCRIPTION     : %s", collegeDesc), false);

        UI.footer(false);
        if (print) UI.returnButton();
    }
    public static void displayColleges() {
        UI.header("COLLEGES", true);
        if (colleges.isEmpty()) UI.liner("  No colleges found.", true);
        else {
            UI.liner(String.format("  %-12s %-165s", "COLLEGE CODE", "COLLEGE DESCRIPTION"), true);
            UI.separator(true);
            int count = 0;
            for (College c : colleges) {
                if (c == null) continue;
                UI.liner(String.format("  %-12s %-165s", c.getCollegeCode(),
                        UI.fitText(c.getCollegeDescription(), 165)), true);
                count++;
            }
            UI.separator(true);
            UI.liner("  TOTAL COLLEGES: " + count, true);
        }
        UI.footer(true);
        UI.returnButton();
    }
    public static void displayArchivedColleges() {
        UI.header("ARCHIVED COLLEGES", true);
        if (archivedColleges.isEmpty()) {
            UI.liner("  No archived colleges found.", true);
        } else {
            UI.liner(String.format("  %-18s %-159s", "COLLEGE CODE", "COLLEGE DESCRIPTION"), true);
            UI.separator(true);
            int count = 0;
            for (College c : archivedColleges) {
                if (c == null) continue;
                UI.liner(String.format("  %-18s %-159s", c.getCollegeCode(),
                        UI.fitText(c.getCollegeDescription(), 159)), true);
                count++;
            }
            UI.separator(true);
            UI.liner("  TOTAL ARCHIVED COLLEGES: " + count, true);
        }
        UI.footer(true);
        UI.returnButton();
    }

    // MANAGE METHODS
    public static void createCollege() {
        String collegeDescription = "", collegeCode = "", choice;
        boolean submit = false;

        while (!submit) {
            collegeManage("CREATE", collegeCode, collegeDescription);

            do {
                UI.select("Select",false);
                choice = in.nextLine().trim().toUpperCase();
                if (!choice.matches("[12X]") && !choice.equals("/"))
                    UI.print("Invalid Choice");
            } while (!choice.matches("[12X]") && !choice.equals("/"));

            switch (choice) {
                case "1":
                    do {
                        UI.select("Enter College Code",true);
                        collegeCode = in.nextLine().trim().toUpperCase();
                        if (collegeCode.equalsIgnoreCase("X")) {
                            collegeCode = "";
                            break;
                        }
                        else if (collegeCode.trim().isEmpty())
                            UI.print("College Code is required");

                        else if (findCollege(collegeCode) != null)
                            UI.print("College Code already exists");
                    } while (collegeCode.isEmpty() || findCollege(collegeCode) != null);
                    break;

                case "2":
                    do {
                        UI.select("Enter College Description",true);
                        collegeDescription = in.nextLine().trim().toUpperCase();
                        if (collegeDescription.equalsIgnoreCase("X")) {
                            collegeDescription = "";
                            break;
                        }
                        else if (collegeDescription.trim().isEmpty())
                            UI.print("College Description is required");
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
                        displayCollege(collegeCode, true);
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

        UI.print("EDIT EXISTING COLLEGE");

        do {
            UI.select("Enter College Code",false);
            input = in.nextLine().trim().toUpperCase();
            if (input.equalsIgnoreCase("X")) return;
            else if (input.isEmpty()) UI.print("College Code is required");
            else if (findCollege(input) == null)
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
                UI.select("Select",false);
                choice = in.nextLine().trim().toUpperCase();

                if (!choice.matches("[12X]") && !choice.equals("/"))
                    UI.print("Invalid Input");
            } while (!choice.matches("[12X]") && !choice.equals("/"));

            switch (choice) {
                case "1":
                    do {
                        UI.select("Enter College Code",true);
                        collegeCode = in.nextLine().trim().toUpperCase();
                        if (collegeCode.equalsIgnoreCase("X")) {
                            collegeCode = c.getCollegeCode();
                            break;
                        }
                        else if (collegeCode.trim().isEmpty())
                            UI.print("College Code is required");
                        else if (findCollege(collegeCode) != null &&
                                !collegeCode.equalsIgnoreCase(c.getCollegeCode()))
                            UI.print("College Code already exists");
                    } while (collegeCode.isEmpty() ||
                            (findCollege(collegeCode) != null &&
                                    !collegeCode.equalsIgnoreCase(c.getCollegeCode())));
                    break;

                case "2":
                    do {
                        UI.select("Enter College Description",true);
                        collegeDescription = in.nextLine().trim().toUpperCase();
                        if (collegeDescription.equalsIgnoreCase("X")) {
                            collegeDescription = c.getCollegeDescription();
                            break;
                        }
                        else if (collegeDescription.trim().isEmpty())
                            UI.print("College Description is required");
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
                    College.displayCollege(c.getCollegeCode(), true);
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
            UI.select("Enter College Code",false);
            input = in.nextLine().trim().toUpperCase();
            if (input.equalsIgnoreCase("X")) return;
            else if (input.isEmpty()) UI.print("College Code is required");
            else if (findCollege(input) == null) UI.print("College does not exist");
        } while (input.isEmpty() || findCollege(input) == null);

        College c = College.findCollege(input);

        if (c == null) {
            UI.print("College not found");
            return;
        }

        for (Program program : Program.programs) {
            if (program != null && program.getCollegeCode() != null
                    && program.getCollegeCode().equalsIgnoreCase(c.getCollegeCode())) {
                UI.print("Archive or reassign the college's active programs first.");
                return;
            }
        }
        for (Room room : Room.rooms) {
            if (room != null && room.getCollegeCode() != null
                    && room.getCollegeCode().equalsIgnoreCase(c.getCollegeCode())) {
                UI.print("Archive or reassign the college's active rooms first.");
                return;
            }
        }
        for (User user : User.users) {
            if (user instanceof Faculty faculty && faculty.getCollegeCode() != null
                    && faculty.getCollegeCode().equalsIgnoreCase(c.getCollegeCode())) {
                UI.print("Reassign or archive the college's faculty first.");
                return;
            }
            if (user instanceof DepartmentHead head && head.getCollegeCode() != null
                    && head.getCollegeCode().equalsIgnoreCase(c.getCollegeCode())) {
                UI.print("Reassign or archive the college's Department Heads first.");
                return;
            }
        }

        displayCollege(c.getCollegeCode(), false);

        do {
            UI.select("Confirm College Archive? (Y/N)",true);
            choice = in.nextLine().trim().toUpperCase();
            if (choice.equalsIgnoreCase("X")) {
                choice = "";
                break;
            }
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
        if (collegeDesc.length() > 35) collegeDesc = collegeDesc.substring(0, 35) + "...";
        UI.header(text.toUpperCase() + ": COLLEGE INFORMATION", false);
        UI.liner(" [1] COLLEGE CODE         : " + collegeCode, false);
        UI.liner(" [2] COLLEGE DESCRIPTION  : " + collegeDesc, false);
        UI.liner("", false);
        UI.option();
        UI.footer(false);
    }
}