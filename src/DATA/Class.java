package DATA;

import java.util.ArrayList;

public class Class {
    private String classID;
    private String sectionID;
    private String courseCode;

    Class (String classID, String sectionID, String courseCode) {
        this.classID = classID;
        this.sectionID = sectionID;
        this.courseCode = courseCode;
    }

    static public ArrayList<Class> classes = new ArrayList<>();
    static {
        classes.add(new Class("0001", "0001", "PE 12"));
        classes.add(new Class("0002", "0001", "RLW 101"));
        classes.add(new Class("0003", "0001", "ETH 101"));
        classes.add(new Class("0004", "0001", "STS 101"));
        classes.add(new Class("0005", "0001", "CC 105"));
        classes.add(new Class("0006", "0001", "IT 203"));
        classes.add(new Class("0007", "0001", "CC 106"));

        classes.add(new Class("0008", "0002", "IT 301"));
        classes.add(new Class("0009", "0002", "IT 302"));
        classes.add(new Class("0010", "0002", "IT 303"));
        classes.add(new Class("0011", "0002", "IT 206"));

        classes.add(new Class("0012", "0003", "PE 12"));
        classes.add(new Class("0013", "0003", "RLW 101"));
        classes.add(new Class("0014", "0003", "CC 105"));
        classes.add(new Class("0015", "0003", "IT 204"));
        classes.add(new Class("0016", "0003", "IT 205"));
        classes.add(new Class("0017", "0003", "CC 106"));

        classes.add(new Class("0018", "0004", "STS 101"));
        classes.add(new Class("0019", "0004", "IT 204"));
        classes.add(new Class("0020", "0004", "IT 206"));
        classes.add(new Class("0021", "0004", "IT 207"));
        classes.add(new Class("0022", "0004", "CC 106"));

        classes.add(new Class("0023", "0005", "PE 12"));
        classes.add(new Class("0024", "0005", "RLW 101"));
        classes.add(new Class("0025", "0005", "ETH 101"));
        classes.add(new Class("0026", "0005", "STS 101"));
        classes.add(new Class("0027", "0005", "CC 104"));

        classes.add(new Class("0028", "0006", "IT 301"));
        classes.add(new Class("0029", "0006", "IT 302"));
        classes.add(new Class("0030", "0006", "IT 303"));
        classes.add(new Class("0031", "0006", "GE 101"));

        classes.add(new Class("0032", "0007", "IT 204"));
        classes.add(new Class("0033", "0007", "CC 106"));
        classes.add(new Class("0034", "0007", "IT 206"));
        classes.add(new Class("0035", "0007", "IT 207"));
        classes.add(new Class("0036", "0007", "IT 205"));

        classes.add(new Class("0037", "0008", "PE 12"));
        classes.add(new Class("0038", "0008", "RLW 101"));
        classes.add(new Class("0039", "0008", "ETH 101"));
        classes.add(new Class("0040", "0008", "CC 104"));

        classes.add(new Class("0041", "0009", "PE 12"));
        classes.add(new Class("0042", "0009", "CC 105"));
        classes.add(new Class("0043", "0009", "IT 203"));
        classes.add(new Class("0044", "0009", "IT 205"));
        classes.add(new Class("0045", "0009", "STS 101"));

        classes.add(new Class("0046", "0010", "IT 204"));
        classes.add(new Class("0047", "0010", "CC 106"));
        classes.add(new Class("0048", "0010", "IT 206"));
        classes.add(new Class("0049", "0010", "IT 207"));
        classes.add(new Class("0050", "0010", "IT 205"));

        classes.add(new Class("0051", "0011", "IT 301"));
        classes.add(new Class("0052", "0011", "IT 302"));
        classes.add(new Class("0053", "0011", "IT 303"));
        classes.add(new Class("0054", "0011", "GE 101"));

        classes.add(new Class("0055", "0012", "PE 12"));
        classes.add(new Class("0056", "0012", "RLW 101"));
        classes.add(new Class("0057", "0012", "ETH 101"));
        classes.add(new Class("0058", "0012", "CC 104"));
    }

    public String getClassID() {
        return classID;
    }

    public static Class findClass(String classID) {
        for (Class c : classes) if (classID.equals(c.getClassID())) return c;
        return null;
    }

    public static Class findClassByID(String sectionID) {
        for (Class c : classes) if (sectionID.equals(Class.getSectionID(c))) return c;
        return null;
    }

    public static String getSectionID(Class c) {
        return c.sectionID;
    }

    public static String getCourseCode(Class c) {
        return c.courseCode;
    }


}
