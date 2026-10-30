
package DATA;

import java.util.ArrayList;
import UI.*;
import USER.*;
import static UI.UI.in;

public class Program {

    // FIELDS
    private String programCode, programDescription, collegeCode;

    public static ArrayList<Program> programs = new ArrayList<>();
    public static ArrayList<Program> archivePrograms = new ArrayList<>();

    // CONSTRUCTOR
    public Program(String programCode, String programDescription, String collegeCode) {
        this.programCode = programCode;
        this.programDescription = programDescription;
        this.collegeCode = collegeCode;
    }

    // DUMMY DATA
    static {
        programs.add(new Program("BSIT", "Bachelor of Science in Information Technology", "CICT"));
        programs.add(new Program("BLIS", "Bachelor of Library and Information Science", "CICT"));
        programs.add(new Program("BSIS", "Bachelor of Science in Information Systems", "CICT"));
        programs.add(new Program("BSArch", "Bachelor of Science in Architecture", "CAFA"));
        programs.add(new Program("BSBroad", "Bachelor of Arts in Broadcasting", "CAL"));
        programs.add(new Program("BSA", "Bachelor of Science in Accountancy", "CBEA"));
        programs.add(new Program("BSEntrep", "Bachelor of Science in Entrepreneurship", "CBEA"));
        programs.add(new Program("BSCrim", "Bachelor of Science in Criminology", "CCJE"));
        programs.add(new Program("BSTM", "Bachelor of Science in Tourism Management", "CHTM"));
        programs.add(new Program("BSHM", "Bachelor of Science in Hospitality Management", "CHTM"));
        programs.add(new Program("BSN", "Bachelor of Science in Nursing", "CON"));
        programs.add(new Program("BSCE", "Bachelor of Science in Civil Engineering", "COE"));
        programs.add(new Program("BSElecEng", "Bachelor of Science in Electrical Engineering", "COE"));
        programs.add(new Program("BSBio", "Bachelor of Science in Biology", "CS"));
        programs.add(new Program("BSPsych", "Bachelor of Science in Psychology", "CSSP"));
    }

    // GETTER METHODS
    public String getProgramCode() {
        return programCode;
    }
    public String getProgramDescription() {
        return programDescription;
    }
    public String getCollegeCode() {
        return collegeCode;
    }

    // FIND METHODS
    public static Program findProgram(String programCode) {
        if (programCode == null || programCode.trim().isEmpty())
            return null;

        programCode = programCode.trim();

        for (Program p : programs) {
            if (p != null && p.getProgramCode() != null
                    && programCode.equalsIgnoreCase(p.getProgramCode()))
                return p;
        }

        return null;
    }
    public static Program findProgramDesc(String programDescription) {
        if (programDescription == null || programDescription.trim().isEmpty())
            return null;

        programDescription = programDescription.trim();

        for (Program p : programs) {
            if (p != null && p.getProgramDescription() != null
                    && programDescription.equalsIgnoreCase(p.getProgramDescription()))
                return p;
        }

        return null;
    }

    // DISPLAY METHODS
    public static void displayProgram(String programCode) {

        Program p = findProgram(programCode);

        if (p == null) {
            UI.print("Program not found");
            return;
        }

        College c = College.findCollege(p.getCollegeCode());

        if (c == null) {
            UI.print("College not found");
            return;
        }

        String progDesc = p.getProgramDescription();
        if (progDesc.length() > 40) progDesc = progDesc.substring(0, 40) + "...";
        String colDesc = c.getCollegeDescription();
        if (colDesc.length() > 40) colDesc = colDesc.substring(0, 40) + "...";

        UI.header("PROGRAM INFORMATION", false);
        UI.liner(String.format("  PROGRAM CODE         : %s", p.getProgramCode()), false);
        UI.liner(String.format("  PROGRAM DESCRIPTION  : %s", progDesc), false);
        UI.liner(String.format("  COLLEGE CODE         : %s", p.getCollegeCode()), false);
        UI.liner(String.format("  COLLEGE DESCRIPTION  : %s", colDesc), false);
        UI.footer(false);
        UI.returnButton();
    }
    public static void displayPrograms() {
        UI.header("PROGRAMS", true);
        if (programs.isEmpty()) UI.liner("  No programs found.", true);
        else {
            UI.liner(String.format("  %-14s %-148s %-14s", "PROGRAM CODE", "PROGRAM DESCRIPTION", "COLLEGE CODE"), true);
            UI.separator(true);
            int count = 0;
            for (Program p : programs) {
                if (p == null) continue;
                UI.liner(String.format("  %-14s %-148s %-14s", p.getProgramCode(),
                        UI.fitText(p.getProgramDescription(), 148), p.getCollegeCode()), true);
                count++;
            }
            UI.separator(true);
            UI.liner("  TOTAL PROGRAMS: " + count, true);
        }
        UI.footer(true);
        UI.returnButton();
    }
    public static void displayArchivePrograms() {
        UI.header("ARCHIVED PROGRAMS", true);
        if (archivePrograms.isEmpty()) UI.liner("  No archived programs found.", true);
        else {
            UI.liner(String.format("  %-14s %-148s %-14s", "PROGRAM CODE", "PROGRAM DESCRIPTION", "COLLEGE CODE"), true);
            UI.separator(true);
            int count = 0;
            for (Program p : archivePrograms) {
                if (p == null) continue;
                UI.liner(String.format("  %-14s %-148s %-14s", p.getProgramCode(),
                        UI.fitText(p.getProgramDescription(), 148), p.getCollegeCode()), true);
                count++;
            }
            UI.separator(true);
            UI.liner("  TOTAL ARCHIVED PROGRAMS: " + count, true);
        }
        UI.footer(true);
        UI.returnButton();
    }

    // MANAGE METHODS
    public static void createProgram() {
        String programCode = "", programDescription = "", collegeCode = "", choice;
        boolean submit = false;

        while (!submit) {
            programManage("CREATE", programCode, programDescription, collegeCode);

            do {
                UI.select("Select", false);
                choice = in.nextLine().trim().toUpperCase();

                if (choice.equalsIgnoreCase("X")) return;
                else if (!choice.matches("[1-3X/]")) UI.print("Invalid Choice");
            } while (!choice.matches("[1-3X/]"));

            switch (choice) {
                case "1":
                    do {
                        UI.select("Enter Program Code", true);
                        programCode = in.nextLine().trim();

                        if (programCode.equalsIgnoreCase("X")) {
                            programCode = "";
                            break;
                        }
                        else if (programCode.isEmpty()) UI.print("Program Code is required");
                        else if (Program.findProgram(programCode) != null) UI.print("Program Code already exists");
                    } while (programCode.isEmpty() || Program.findProgram(programCode) != null);
                    break;

                case "2":
                    do {
                        UI.select("Enter Program Description", true);
                        programDescription = in.nextLine().trim();

                        if (programDescription.equalsIgnoreCase("X")) {
                            programDescription = "";
                            break;
                        }
                        else if (programDescription.isEmpty()) UI.print("Program Description is required");
                        else if (findProgramDesc(programDescription) != null) UI.print("Program Description already exists");
                    } while (programDescription.isEmpty() || findProgramDesc(programDescription) != null);
                    break;

                case "3":
                    do {
                        UI.select("Enter College Code", true);
                        collegeCode = in.nextLine().trim();

                        if (collegeCode.equalsIgnoreCase("X")) {
                            collegeCode = "";
                            break;
                        }
                        else if (collegeCode.isEmpty()) UI.print("College Code is required");
                        else if (College.findCollege(collegeCode) ==  null) UI.print("College Code does not exist");
                    } while (collegeCode.isEmpty() || College.findCollege(collegeCode) ==  null);
                    break;

                case "/":
                    if (programCode.isEmpty() || programDescription.isEmpty() || collegeCode.isEmpty()) {
                        UI.print("Details are incomplete");
                        break;
                    }

                    Program.programs.add(new Program(programCode, programDescription, collegeCode));
                    UI.print("Program successfully created");
                    submit = true;
                    break;

                case "X":
                    submit = true;
                    break;
            }
        }
    }
    public static void editProgram() {
        String input, choice; boolean submit = false;

        UI.print("EDIT EXISTING PROGRAM");

        do {
            UI.select("Enter Program Code", false);
            input = in.nextLine().trim();

            if (input.equalsIgnoreCase("X")) return;
            else if (input.isEmpty()) UI.print("Program Code is required");
            else if (Program.findProgram(input) == null) UI.print("Program does not exist");
        } while (Program.findProgram(input) == null);

        Program p = Program.findProgram(input);

        if (p == null) {
            UI.print("Program not found");
            return;
        }

        String programCode = p.getProgramCode();
        String programDescription = p.getProgramDescription();
        String collegeCode = p.getCollegeCode();

        while (!submit) {
            programManage("EDIT", programCode, programDescription, collegeCode);

            do {
                UI.select("Select", false);
                choice = in.nextLine().trim().toUpperCase();
                if (!choice.matches("[1-3X/]")) UI.print("Invalid Choice");
            } while (!choice.matches("[1-3X/]"));

            switch (choice) {
                case "1":
                    do {
                        UI.select("Enter Program Code", true);
                        programCode = in.nextLine().trim();

                        if (programCode.equalsIgnoreCase("X")) {
                            programCode = p.getProgramCode();
                            break;
                        }
                        else if (programCode.isEmpty()) UI.print("Program Code is required");
                        else if (Program.findProgram(programCode) != null) UI.print("Program Code already exists");
                    } while (programCode.isEmpty() || Program.findProgram(programCode) != null);
                    break;

                case "2":
                    do {
                        UI.select("Enter Program Description", true);
                        programDescription = in.nextLine().trim();

                        if (programDescription.equalsIgnoreCase("X")) {
                            programDescription = p.programDescription;
                            break;
                        }
                        else if (programDescription.isEmpty()) UI.print("Program Description is required");
                        else if (findProgramDesc(programDescription) != null) UI.print("Program Description already exists");
                    } while (programDescription.isEmpty() || findProgramDesc(programDescription) != null);
                    break;

                case "3":
                    do {
                        UI.select("Enter College Code", true);
                        collegeCode = in.nextLine().trim();

                        if (collegeCode.equalsIgnoreCase("X")) {
                            collegeCode = p.collegeCode;
                            break;
                        }
                        else if (collegeCode.isEmpty()) UI.print("College Code is required");
                        else if (College.findCollege(collegeCode) ==  null) UI.print("College Code does not exist");
                    } while (collegeCode.isEmpty() || College.findCollege(collegeCode) ==  null);
                    break;

                case "/":
                    Program pr = findProgram(programCode);
                    pr.programCode = programCode; pr.programDescription = programDescription; pr.collegeCode = collegeCode;
                    UI.print("Program successfully edited");
                    submit = true;
                    break;

                case "X":
                    submit = true;
                    break;
            }
        }
    }
    public static void archiveProgram() {
        String input, choice;

        UI.print("ARCHIVE EXISTING PROGRAM");

        do {
            UI.select("Enter Program Code", false);
            input = in.nextLine().trim();
            if (input.equalsIgnoreCase("X")) return;
            else if (input.isEmpty()) UI.print("Program Code is required");
            else if (findProgram(input) == null) UI.print("Program does not exist");
        } while (input.isEmpty() || findProgram(input) == null);

        Program p = Program.findProgram(input);

        if (p == null) {
            UI.print("Program not found");
            return;
        }

        for (User user : User.users) {
            if (user instanceof Student student && student.getProgramCode() != null
                    && student.getProgramCode().equalsIgnoreCase(p.getProgramCode())) {
                UI.print("Reassign or archive the students using this program first.");
                return;
            }
        }
        for (Curriculum curriculum : Curriculum.curricula) {
            if (curriculum != null && curriculum.getProgramCode() != null
                    && curriculum.getProgramCode().equalsIgnoreCase(p.getProgramCode())) {
                UI.print("Archive the active curriculum records for this program first.");
                return;
            }
        }

        displayProgram(p.getProgramCode());

        do {
            UI.select("Confirm Program Deletion? (Y/N)", false);
            choice = in.nextLine().trim().toUpperCase();
            if (choice.equals("X")) return;

            if (!choice.matches("[YN]"))
                UI.print("Invalid Input");

        } while (!choice.matches("[YN]"));

        if (choice.equals("Y")) {
            archivePrograms.add(p);
            Program.programs.remove(p);
            UI.print("Program successfully deleted");
        }
        else UI.print("Archiving cancelled");
    }
    private static void programManage(String text, String programCode,
                                      String programDescription, String collegeCode) {
        String progDesc = programDescription;
        if (progDesc.length() > 35) progDesc = progDesc.substring(0, 35) + "...";
        UI.header(text.toUpperCase() + ": PROGRAM INFORMATION", false);
        UI.liner(" [1] PROGRAM CODE         : " + programCode, false);
        UI.liner(" [2] PROGRAM DESCRIPTION  : " + progDesc, false);
        UI.liner(" [3] COLLEGE CODE         : " + collegeCode, false);
        UI.liner("", false);
        UI.option();
        UI.footer(false);
    }

}