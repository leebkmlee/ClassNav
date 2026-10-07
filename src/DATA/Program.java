package DATA;
import java.util.ArrayList;

public class Program {

    private String programCode;
    private String programDescription;
    private String collegeCode; // foreign key

    public Program(String programCode, String programDescription, String collegeCode) {
        this.programCode = programCode;
        this.programDescription = programDescription;
        this.collegeCode = collegeCode;
    }

    public void displayProgram() {
        System.out.println("Program Code: " + programCode);
        System.out.println("Program Description: " + programDescription);
        System.out.println("College Code: " + collegeCode);
    }

    public String getProgramID() {
        return programCode;
    }

    ArrayList<Program> programs = new ArrayList<>();

    public Program() {
        programs.add(new Program("BSIT", "Bachelor of Science in Information Technology", "CICT"));
        programs.add(new Program("BSED", "Bachelor of Secondary Education", "COED"));
        programs.add(new Program("BSIE", "Bachelor of Science in Industrial Engineering", "COE"));
    }

    public Program getProgram(String programCode) {
        for (Program p : programs) {
            if (programCode.equals(p.getProgramID())) {
                return p;
            }
        }
        return null;
    }
}
