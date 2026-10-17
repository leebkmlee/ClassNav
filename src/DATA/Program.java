package DATA;
import java.util.ArrayList;
import UI.*;

import static DATA.Course.courses;

public class Program {
    static int innerWidth = 70;
    private String programCode;
    private String programDescription;
    private String collegeCode; // foreign key

    public Program(String programCode, String programDescription, String collegeCode) {
        this.programCode = programCode;
        this.programDescription = programDescription;
        this.collegeCode = collegeCode;
    }

    public static ArrayList<Program> programs = new ArrayList<>();

    static {
        programs.add(new Program("BSIT", "Bachelor of Science in Information Technology", "CICT"));
        programs.add(new Program("BSIS", "Bachelor of Science in Information Systems", "CICT"));
        programs.add(new Program("BSCpE", "Bachelor of Science in Computer Engineering", "COE"));
        programs.add(new Program("BSIE", "Bachelor of Science in Industrial Engineering", "COE"));
        programs.add(new Program("BSED", "Bachelor of Secondary Education", "COED"));
        programs.add(new Program("BSPSY", "Bachelor of Science in Psychology", "CAS"));
        programs.add(new Program("BSBA", "Bachelor of Science in Business Administration", "CBA"));
    }

    public String getProgramID() {
        return programCode;
    }

    public static Program findProgram(String programCode) {
        for (Program p : programs) if (programCode.equals(p.getProgramID())) return p;
        return null;
    }

    public static String getProgram(Program p) {
        return p.programCode;
    }

    public static String getProgramDescription(Program p) {
        return p.programDescription;
    }

    public static String getCollegeCode(Program p) {
        return p.collegeCode;
    }

    public static boolean verifyProgramCode(String programCode) {
        for (Program p : programs) if (programCode.equals(p.programCode)) return true;
        return false;
    }

    public static boolean existProgramCode(String programCode) {
        for (Program p : programs) if (programCode.equals(p.programCode)) return true;
        return false;
    }

    public static boolean existProgramDesc(String programDescription) {
        for (Program p : programs) if (programDescription.equals(p.programDescription)) return true;
        return false;
    }

    public static void displayProgram(String programCode) {
        Program p = findProgram(programCode);
        if (p == null) {
            UI.print("Program not found");
            return;
        }
        String programDescription = Program.getProgramDescription(p);
        UI.header("PROGRAM INFORMATION");
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("  PROGRAM CODE         : %s", Program.getProgram(p)));
        if (programDescription.length() <= 40)
            System.out.printf("│%-" + innerWidth + "s│\n", String.format("  PROGRAM DESCRIPTION  : %s", Program.getProgramDescription(p)));
        else {
            System.out.printf("│%-" + innerWidth + "s│\n", "  PROGRAM DESCRIPTION  :");
            System.out.printf("│%-" + innerWidth + "s│\n", "  " + programDescription);
        }
        UI.footer();
    }

    public static void displayPrograms() {
        UI.header("PROGRAM LIST");
        for (Program p : programs) {
            String programs = String.format( "  %-10s %-32s %-15s %-4s", Program.getProgram(p), Program.getProgramDescription(p),
                    Program.getCollegeCode(p));
            System.out.printf("│%-" + innerWidth + "s│\n", programs);
        }
        UI.footer();
    }

    public static void displayCourses(String programCode) {
        Program p = findProgram(programCode);
        if (p == null) {
            UI.print("Program not found");
            return;
        }
        UI.header("COURSE LIST");
        System.out.printf("│%-" + innerWidth + "s│\n", String.format("  PROGRAM CODE     : %s", Program.getProgram(p)));
        System.out.printf("│%-" + innerWidth + "s│\n", "  Courses under " + Program.getProgramDescription(p) + ":");
        for (Course c : courses) {
            if(Course.getProgramCode(c).equals(p.getProgramID())) {
                String course = String.format("    %-15s %-40s %-8s",
                        Course.getCourseCode(c), Course.getCourseDescription(c), Course.getCreditUnits(c) + " units");
                System.out.printf("│%-" + innerWidth + "s│\n", course);
            }
        }
        UI.footer();
    }

    public void setProgramCode(String programCode) { this.programCode = programCode;
    }

    public void setProgramDescription(String programDescription) { this.programDescription = programDescription;
    }

    public void setCollegeCode(String collegeCode) { this.collegeCode = collegeCode;
    }
}
