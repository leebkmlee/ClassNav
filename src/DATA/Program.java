
package DATA;

import java.util.ArrayList;
import UI.*;

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
        programs.add(new Program("BSIS", "Bachelor of Science in Information Systems", "CICT"));
        programs.add(new Program("BSCpE", "Bachelor of Science in Computer Engineering", "COE"));
        programs.add(new Program("BSIE", "Bachelor of Science in Industrial Engineering", "COE"));
        programs.add(new Program("BSED", "Bachelor of Secondary Education", "COED"));
        programs.add(new Program("BSPSY", "Bachelor of Science in Psychology", "CAS"));
        programs.add(new Program("BSBA", "Bachelor of Science in Business Administration", "CBA"));
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
                    College.getCollegeDescription(c)));

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
}
