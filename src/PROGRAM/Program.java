package PROGRAM;

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
}
