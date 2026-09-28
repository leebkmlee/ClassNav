package DATA;
import PROGRAM.Program;
import java.util.ArrayList;

public class RetrieveProgram {
    ArrayList<Program> programs = new ArrayList<>();

    public RetrieveProgram() {
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
