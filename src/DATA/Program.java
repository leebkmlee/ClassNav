
package DATA;

import java.util.ArrayList;
import UI.*;
import static UI.UI.in;

public class Program {

    private String programCode; // primary key
    private String programDescription;
    private String collegeCode; // foreign key

    public static ArrayList<Program> programs = new ArrayList<>();

    public Program(String programCode, String programDescription, String collegeCode) {

        if (programCode == null || programCode.trim().isEmpty())
            throw new IllegalArgumentException("Program code cannot be empty.");

        if (programDescription == null || programDescription.trim().isEmpty())
            throw new IllegalArgumentException("Program description cannot be empty.");

        if (collegeCode == null || collegeCode.trim().isEmpty())
            throw new IllegalArgumentException("College code cannot be empty.");

        programCode = programCode.trim();
        programDescription = programDescription.trim();
        collegeCode = collegeCode.trim();

        if (College.findCollege(collegeCode) == null)
            throw new IllegalArgumentException("College does not exist.");

        for (Program p : programs) {
            if (programCode.equalsIgnoreCase(p.getProgramID()))
                throw new IllegalArgumentException("Program code already exists.");
        }

        this.programCode = programCode;
        this.programDescription = programDescription;
        this.collegeCode = collegeCode;
    }

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

    // PROGRAM GETTER METHODS
    public String getProgramID() {
        return programCode;
    }

    public String getProgramDescription() {
        return programDescription;
    }

    public String getCollegeCode() {
        return collegeCode;
    }

    // FIND PROGRAM BY PRIMARY KEY
    public static Program findProgram(String programCode) {

        if (programCode == null || programCode.trim().isEmpty())
            return null;

        programCode = programCode.trim();

        for (Program p : programs) {
            if (programCode.equalsIgnoreCase(p.getProgramID()))
                return p;
        }

        return null;
    }

    // STATIC GETTER METHODS
    public static String getProgram(Program p) {
        return p == null ? null : p.getProgramID();
    }

    public static String getProgramDescription(Program p) {
        return p == null ? null : p.getProgramDescription();
    }

    public static String getCollegeCode(Program p) {
        return p == null ? null : p.getCollegeCode();
    }

    public static boolean existProgramCode(String programCode) {
        return findProgram(programCode) != null;
    }

    // PROGRAM METHODS
    public static void displayProgram(String programCode) {

        Program p = findProgram(programCode);

        if (p == null) {
            UI.print("Program not found");
            return;
        }

        College c = College.findCollege(p.getCollegeCode());

        UI.header("PROGRAM INFORMATION");
        UI.liner(String.format("  PROGRAM CODE         : %s", p.getProgramID()));

        String description = p.getProgramDescription();

        if (description.length() <= 40) {
            UI.liner(String.format("  PROGRAM DESCRIPTION  : %s", description));
        } else {
            UI.liner("  PROGRAM DESCRIPTION  :");
            UI.liner("  " + description);
        }

        UI.liner(String.format("  COLLEGE CODE         : %s", p.getCollegeCode()));

        if (c != null)
            UI.liner(String.format("  COLLEGE DESCRIPTION  : %s",
                    c.getCollegeDescription()));

        UI.footer();
    }

    public static void displayPrograms() {

        UI.header("PROGRAMS");

        if (programs.isEmpty()) {
            UI.liner("  No programs found.");
        } else {
            for (int i = 0; i < programs.size(); i++) {
                Program p = programs.get(i);

                UI.liner(String.format(
                        "  %-15s %-45s",
                        p.getProgramID(),
                        p.getProgramDescription()
                ));

                if (i < programs.size() - 1)
                    UI.separator();
            }
        }

        UI.footer();
    }

    public static void createProgram() {
        String programCode = "", programDescription = "", collegeCode = "";
        String choice;
        boolean submit = false;

        while (!submit) {
            programManage("CREATE", programCode, programDescription, collegeCode);

            do {
                System.out.print("  Select > ");
                choice = in.nextLine().trim().toUpperCase();

                if (!choice.matches("[1-3X/]"))
                    UI.print("Invalid Choice");
            } while (!choice.matches("[1-3X/]"));

            switch (choice) {
                case "1":
                    do {
                        System.out.print("   Enter Program Code > ");
                        programCode = in.nextLine().trim();

                        if (programCode.isEmpty())
                            UI.print("Program Code is required");
                        else if (Program.existProgramCode(programCode))
                            UI.print("Program Code already exists");

                    } while (programCode.isEmpty()
                            || Program.existProgramCode(programCode));
                    break;

                case "2":
                    do {
                        System.out.print("   Enter Program Description > ");
                        programDescription = in.nextLine().trim();

                        if (programDescription.isEmpty())
                            UI.print("Program Description is required");
                        else if (programDescriptionExists(programDescription, null))
                            UI.print("Program Description already exists");

                    } while (programDescription.isEmpty()
                            || programDescriptionExists(programDescription, null));
                    break;

                case "3":
                    do {
                        System.out.print("   Enter College Code > ");
                        collegeCode = in.nextLine().trim();

                        if (collegeCode.isEmpty())
                            UI.print("College Code is required");
                        else if (College.existCollegeCode(collegeCode))
                            UI.print("College Code already exist");
                    } while (collegeCode.isEmpty()
                            || College.findCollege(collegeCode) != null);
                    break;

                case "/":
                    if (programCode.isEmpty() || programDescription.isEmpty()
                            || collegeCode.isEmpty()) {
                        UI.print("Details are incomplete");
                        break;
                    }

                    try {
                        Program p = new Program(
                                programCode, programDescription, collegeCode);

                        Program.programs.add(p);
                        UI.print("Program successfully created");
                        Program.displayProgram(p.getProgramID());
                        submit = true;

                    } catch (IllegalArgumentException e) {
                        UI.print(e.getMessage());
                    }
                    break;

                case "X":
                    submit = true;
                    break;
            }
        }
    }

    public static void editProgram() {
        String input, choice;
        boolean submit = false;

        UI.print("EDIT EXISTING PROGRAM");

        do {
            System.out.print("  Enter Program Code > ");
            input = in.nextLine().trim();

            if (!Program.existProgramCode(input))
                UI.print("Program does not exist");

        } while (!Program.existProgramCode(input));

        Program p = Program.findProgram(input);

        if (p == null) {
            UI.print("Program not found");
            return;
        }

        String programCode = p.getProgramID();
        String programDescription = p.getProgramDescription();
        String collegeCode = p.getCollegeCode();

        while (!submit) {
            programManage("EDIT", programCode, programDescription, collegeCode);

            do {
                System.out.print("  Select > ");
                choice = in.nextLine().trim().toUpperCase();

                if (!choice.matches("[1-3X/]"))
                    UI.print("Invalid Choice");

            } while (!choice.matches("[1-3X/]"));

            switch (choice) {
                case "1":
                    do {
                        System.out.print("   Enter Program Code > ");
                        input = in.nextLine().trim();

                        if (input.isEmpty())
                            UI.print("Program Code is required");
                        else if (Program.existProgramCode(input)
                                && !input.equalsIgnoreCase(p.getProgramID()))
                            UI.print("Program Code already exists");

                    } while (input.isEmpty()
                            || (Program.existProgramCode(input)
                            && !input.equalsIgnoreCase(p.getProgramID())));

                    programCode = input;
                    break;

                case "2":
                    do {
                        System.out.print("   Enter Program Description > ");
                        input = in.nextLine().trim();

                        if (input.isEmpty())
                            UI.print("Program Description is required");
                        else if (programDescriptionExists(input, p))
                            UI.print("Program Description already exists");

                    } while (input.isEmpty()
                            || programDescriptionExists(input, p));

                    programDescription = input;
                    break;

                case "3":
                    do {
                        System.out.print("   Enter College Code > ");
                        input = in.nextLine().trim();

                        if (input.isEmpty())
                            UI.print("College Code is required");
                        else if (College.findCollege(collegeCode) != null)
                            UI.print("College Code does not exist");

                    } while (input.isEmpty()
                            || College.findCollege(collegeCode) != null);

                    collegeCode = input;
                    break;

                case "/":
                    if (programCode.isEmpty() || programDescription.isEmpty()
                            || collegeCode.isEmpty()) {
                        UI.print("Details are incomplete");
                        break;
                    }

                    if (Program.existProgramCode(programCode)
                            && !programCode.equalsIgnoreCase(p.getProgramID())) {
                        UI.print("Program Code already exists");
                        break;
                    }

                    if (programDescriptionExists(programDescription, p)) {
                        UI.print("Program Description already exists");
                        break;
                    }

                    if (College.findCollege(collegeCode) != null) {
                        UI.print("College Code does not exist");
                        break;
                    }

                    try {
                        Program updated = new Program(
                                programCode, programDescription, collegeCode);

                        int index = Program.programs.indexOf(p);

                        if (index != -1) {
                            Program.programs.set(index, updated);
                            UI.print("Program successfully edited");
                            Program.displayProgram(updated.getProgramID());
                            submit = true;
                        } else {
                            UI.print("Program could not be updated");
                        }

                    } catch (IllegalArgumentException e) {
                        UI.print(e.getMessage());
                    }
                    break;

                case "X":
                    submit = true;
                    break;
            }
        }
    }

    private static boolean programDescriptionExists(
            String description, Program excludedProgram) {

        if (description == null || description.trim().isEmpty())
            return false;

        for (Program p : Program.programs) {
            if (p != excludedProgram
                    && description.trim().equalsIgnoreCase(
                    p.getProgramDescription())) {
                return true;
            }
        }

        return false;
    }

    public static void deleteProgram() {
        String input, choice;

        UI.print("DELETE EXISTING PROGRAM");

        do {
            System.out.print("  Enter Program Code > ");
            input = in.nextLine().trim();

            if (!Program.existProgramCode(input))
                UI.print("Program does not exist");

        } while (!Program.existProgramCode(input));

        Program p = Program.findProgram(input);

        if (p == null) {
            UI.print("Program not found");
            return;
        }

        programManage("DELETE", p.getProgramID(),
                p.getProgramDescription(), p.getCollegeCode());

        do {
            System.out.print("  Confirm Program Deletion? (Y/N) > ");
            choice = in.nextLine().trim().toUpperCase();

            if (!choice.matches("[YN]"))
                UI.print("Invalid Input");

        } while (!choice.matches("[YN]"));

        if (choice.equals("Y")) {
            Program.programs.remove(p);
            UI.print("Program successfully deleted");
        }
    }

    private static void programManage(String text, String programCode,
                                      String programDescription, String collegeCode) {

        UI.header(text.toUpperCase() + ": PROGRAM INFORMATION");
        UI.liner(" [1] PROGRAM CODE        : " + programCode);
        UI.liner(" [2] PROGRAM DESCRIPTION : " + programDescription);
        UI.liner(" [3] COLLEGE CODE        : " + collegeCode);
        UI.liner("");
        UI.option();
        UI.footer();
    }
}
