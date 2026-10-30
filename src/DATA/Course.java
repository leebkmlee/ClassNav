package DATA;

import UI.*;
import USER.*;
import static UI.UI.in;

import java.util.ArrayList;

public class Course {

    // FIELDS
    private String courseCode, courseDescription, courseType;
    private int creditUnits;

    public static ArrayList<Course> courses = new ArrayList<>();
    public static ArrayList<Course> archiveCourses = new ArrayList<>();

    // CONSTRUCTOR
    public Course(String courseCode, String courseDescription, int creditUnits,
                  String courseType) {
        this.courseCode = courseCode.trim();
        this.courseDescription = courseDescription.trim();
        this.creditUnits = creditUnits;
        this.courseType = courseType.trim().toUpperCase();
    }

    // DUMMY DATA
    static {
        courses.add(new Course("GE101", "Purposive Communication", 3, "GEN ED"));
        courses.add(new Course("GE102", "Mathematics in the Modern World", 3, "GEN ED"));
        courses.add(new Course("GE103", "Understanding the Self", 3, "GEN ED"));
        courses.add(new Course("GE104", "Readings in Philippine History", 3, "GEN ED"));
        courses.add(new Course("GE105", "The Contemporary World", 3, "GEN ED"));
        courses.add(new Course("GE106", "Ethics", 3, "GEN ED"));
        courses.add(new Course("GE107", "Science, Technology, and Society", 3, "GEN ED"));
        courses.add(new Course("GE108", "Art Appreciation", 3, "GEN ED"));
        courses.add(new Course("GE109", "Filipino sa Piling Larang", 3, "GEN ED"));
        courses.add(new Course("PE101", "Physical Education 1", 2, "GEN ED"));
        courses.add(new Course("IT101", "Introduction to Computing", 3, "MAJOR"));
        courses.add(new Course("IT102", "Computer Programming 1", 3, "MAJOR"));
        courses.add(new Course("IT103", "Object-Oriented Programming", 3, "MAJOR"));
        courses.add(new Course("IT104", "Data Structures and Algorithms", 3, "MAJOR"));
        courses.add(new Course("IT105", "Database Management Systems", 3, "MAJOR"));

        courses.add(new Course("IS101", "Systems Analysis and Design", 3, "MAJOR"));
        courses.add(new Course("IS102", "Information Systems Fundamentals", 3, "MAJOR"));
        courses.add(new Course("BL101", "Information Resources and Services", 3, "MAJOR"));
        courses.add(new Course("CR101", "Introduction to Criminology", 3, "MAJOR"));
        courses.add(new Course("CR102", "Philippine Criminal Justice System", 3, "MAJOR"));
        courses.add(new Course("TM101", "Principles of Tourism", 3, "MAJOR"));
        courses.add(new Course("HM101", "Fundamentals of Hospitality Management", 3, "MAJOR"));
        courses.add(new Course("NS101", "Anatomy and Physiology", 4, "MAJOR"));
        courses.add(new Course("NS102", "Fundamentals of Nursing", 3, "MAJOR"));
        courses.add(new Course("CE101", "Engineering Drawing", 3, "MAJOR"));
        courses.add(new Course("CE102", "Engineering Mechanics", 3, "MAJOR"));
        courses.add(new Course("ED101", "The Teaching Profession", 3, "MAJOR"));
        courses.add(new Course("PS101", "Introduction to Psychology", 3, "MAJOR"));
        courses.add(new Course("BIO101", "General Biology", 4, "MAJOR"));
        courses.add(new Course("CH101", "General Chemistry", 4, "MAJOR"));
    }

    // SETTER METHODS
    public void setCourseCode(String courseCode) {

        if (courseCode == null || courseCode.trim().isEmpty()) {
            UI.print("Course code cannot be empty.");
            return;
        }

        for (Course c : courses) {
            if (c != this &&
                    courseCode.trim().equalsIgnoreCase(c.getCourseCode())) {
                UI.print("Course code already exists.");
                return;
            }
        }

        this.courseCode = courseCode.trim();
    }
    public void setCourseDescription(String courseDescription) {

        if (courseDescription == null || courseDescription.trim().isEmpty()) {
            UI.print("Course description cannot be empty.");
            return;
        }

        for (Course c : courses) {
            if (c != this &&
                    courseDescription.trim().equalsIgnoreCase(c.getCourseDescription())) {
                UI.print("Course description already exists.");
                return;
            }
        }

        this.courseDescription = courseDescription.trim();
    }
    public void setCreditUnits(int creditUnits) {

        if (creditUnits != 2 && creditUnits != 3) {
            UI.print("Credit units must be 2 or 3.");
            return;
        }

        this.creditUnits = creditUnits;
    }
    public void setCourseType(String courseType) {

        if (courseType == null || (!courseType.trim().equalsIgnoreCase("GEN ED")
                && !courseType.trim().equalsIgnoreCase("MAJOR"))) {
            UI.print("Course type must be GEN ED or MAJOR.");
            return;
        }

        this.courseType = courseType.trim().toUpperCase();
    }

    // GETTER METHODS
    public String getCourseCode() {
        return courseCode;
    }
    public String getCourseDescription() {
        return courseDescription;
    }
    public int getCreditUnits() {
        return creditUnits;
    }
    public String getCourseType() {
        return courseType;
    }

    // FIND METHODS
    public static Course findCourse(String courseCode) {
        if (courseCode == null || courseCode.trim().isEmpty())
            return null;

        for (Course c : courses) {
            if (c != null && c.getCourseCode() != null
                    && courseCode.trim().equalsIgnoreCase(c.getCourseCode()))
                return c;
        }

        return null;
    }
    public static Course findCourseDesc(String courseDescription) {
        if (courseDescription == null || courseDescription.trim().isEmpty())
            return null;

        for (Course c : courses) {
            if (c != null && c.courseDescription != null
                    && courseDescription.trim().equalsIgnoreCase(c.courseDescription))
                return c;
        }

        return null;
    }

    // DISPLAY METHODS
    public static void displayCourse(String courseCode, boolean print) {

        Course c = findCourse(courseCode);

        if (c == null) {
            UI.print("Course not found");
            return;
        }

        UI.header("COURSE DETAILS", false);
        UI.liner(String.format("  COURSE CODE         : %s", c.getCourseCode()), false);
        UI.liner(String.format("  COURSE DESCRIPTION  : %s", c.getCourseDescription()), false);
        UI.liner(String.format("  CREDIT UNITS        : %d", c.getCreditUnits()), false);
        UI.liner(String.format("  COURSE TYPE         : %s", c.getCourseType()), false);
        UI.footer(false);
        if (print) UI.returnButton();
    }
    public static void displayCourses() {
        UI.header("COURSES", true);
        if (courses.isEmpty()) UI.liner("  No courses found.", true);
        else {
            UI.liner(String.format("  %-12s %-133s %-15s %-15s",
                    "COURSE CODE", "COURSE DESCRIPTION", "CREDIT UNITS", "COURSE TYPE"), true);
            UI.separator(true);
            int count = 0;
            for (Course c : courses) {
                if (c == null) continue;
                UI.liner(String.format("  %-12s %-133s %-15s %-15s",
                        c.getCourseCode(), UI.fitText(c.getCourseDescription(), 133),
                        c.getCreditUnits(), UI.fitText(c.getCourseType(), 15)), true);
                count++;
            }
            UI.separator(true);
            UI.liner("  TOTAL COURSES: " + count, true);
        }
        UI.footer(true);
        UI.returnButton();
    }
    public static void displayArchiveCourses() {
        UI.header("ARCHIVED COURSES", true);
        if (archiveCourses.isEmpty()) {
            UI.liner("  No archived courses found.", true);
        } else {
            UI.liner(String.format("  %-14s %-126s %-15s %-20s",
                    "COURSE CODE", "COURSE DESCRIPTION", "CREDIT UNITS", "COURSE TYPE"), true);
            UI.separator(true);
            int count = 0;
            for (Course c : archiveCourses) {
                if (c == null) continue;
                UI.liner(String.format("  %-14s %-126s %-15s %-20s", c.getCourseCode(),
                        UI.fitText(c.getCourseDescription(), 126), c.getCreditUnits(),
                        UI.fitText(c.getCourseType(), 20)), true);
                count++;
            }
            UI.separator(true);
            UI.liner("  TOTAL ARCHIVED COURSES: " + count, true);
        }
        UI.footer(true);
        UI.returnButton();
    }

    // CHECK FACULTY COURSE ASSIGNMENT
    public static boolean courseLoad(String facultyID, String courseCode) {

        if (facultyID == null || facultyID.trim().isEmpty()
                || courseCode == null || courseCode.trim().isEmpty())
            return false;

        Course c = findCourse(courseCode);

        if (c == null || Faculty.findFaculty(facultyID.trim()) == null)
            return false;

        for (CourseOffering o : CourseOffering.offerings) {

            if (o.getCourseCode().equalsIgnoreCase(c.getCourseCode())
                    && o.getFacultyID().equalsIgnoreCase(facultyID.trim())) {
                return true;
            }
        }

        return false;
    }

    // DISPLAY SECTIONS ASSIGNED TO A COURSE AND FACULTY
    public static void displaySections(String courseCode, String facultyID) {
        Course c = findCourse(courseCode);
        if (c == null) {
            UI.print("Course not found");
            return;
        }
        if (facultyID == null || facultyID.trim().isEmpty()
                || Faculty.findFaculty(facultyID.trim()) == null) {
            UI.print("Faculty not found");
            return;
        }

        ArrayList<CourseOffering> matchingOfferings = new ArrayList<>();
        for (CourseOffering o : CourseOffering.offerings) {
            if (o != null && o.getCourseCode() != null && o.getFacultyID() != null
                    && o.getCourseCode().equalsIgnoreCase(c.getCourseCode())
                    && o.getFacultyID().equalsIgnoreCase(facultyID.trim())) matchingOfferings.add(o);
        }

        UI.header("COURSE SECTIONS", true);
        UI.liner("  COURSE CODE: " + c.getCourseCode() + " | COURSE: " + c.getCourseDescription()
                + " | FACULTY ID: " + facultyID.trim(), true);
        UI.liner(String.format("  %-14s %-14s %-22s %-18s %-12s %-93s",
                "OFFERING ID", "SECTION ID", "SECTION", "ACADEMIC YEAR", "TERM", "COURSE DESCRIPTION"), true);
        UI.separator(true);

        if (matchingOfferings.isEmpty()) {
            UI.liner("  No sections found for this course and faculty.", true);
        } else {
            for (CourseOffering o : matchingOfferings) {
                Section section = Section.findSection(o.getSectionID());
                UI.liner(String.format("  %-14s %-14s %-22s %-18s %-12s %-93s",
                        o.getOfferingID(), o.getSectionID(),
                        UI.fitText(section == null ? "Section unavailable" : Section.getYearSection(section), 22),
                        o.getAcademicYear(), o.getTerm(), UI.fitText(c.getCourseDescription(), 93)), true);
            }
        }
        UI.separator(true);
        UI.liner("  TOTAL COURSE OFFERINGS: " + matchingOfferings.size(), true);
        UI.footer(true);
        UI.returnButton();
    }

    // MANAGE METHODS
    public static void createCourse() {
        String courseCode = "", courseDescription = "", courseType = "", choice;
        int creditUnits = -1; boolean submit = false;

        while (!submit) {
            courseManage("CREATE", courseCode, courseDescription, creditUnits, courseType);

            do {
                UI.select("Select",false);
                choice = in.nextLine().trim().toUpperCase();
                if (!choice.matches("[1-4X]") && !choice.equals("/"))
                    UI.print("Invalid Choice");
            } while (!choice.matches("[1-4X]") && !choice.equals("/"));

            switch (choice) {
                case "1":
                    do {
                        UI.select("Enter Course Code", true);
                        courseCode = in.nextLine().trim();
                        if (courseCode.equalsIgnoreCase("X")) {
                            courseCode = "";
                            break;
                        }
                        else if (courseCode.trim().isEmpty())
                            UI.print("Course Code is required");
                        else if (Course.findCourse(courseCode) != null)
                            UI.print("Course Code already exists");
                    } while (courseCode.isEmpty() || Course.findCourse(courseCode) != null);
                    break;

                case "2":
                    do {
                        UI.select("Enter Course Description",true);
                        courseDescription = in.nextLine().trim();
                        if (courseDescription.equalsIgnoreCase("X")) {
                            courseDescription = "";
                            break;
                        }
                        else if (courseDescription.trim().isEmpty())
                            UI.print("Course Description is required");
                        else if (Course.findCourseDesc(courseDescription) != null)
                            UI.print("Course Description already exists");
                    } while (courseDescription.isEmpty() || Course.findCourseDesc(courseDescription) != null);
                    break;

                case "3":
                    do {
                        UI.select("Enter Credit Units",true);
                        String input = in.nextLine().trim();
                        if (input.equalsIgnoreCase("X")) {
                            break;
                        }
                        try {
                            creditUnits = Integer.parseInt(input);
                            if (creditUnits != 2 && creditUnits != 3)
                                UI.print("Invalid Credit Units");
                        } catch (NumberFormatException e) {
                            UI.print("Invalid Credit Units");
                            creditUnits = -1;
                        }
                    } while (creditUnits != 2 && creditUnits != 3);
                    break;

                case "4":
                    do {
                        UI.select("Enter Course Type (GEN ED/MAJOR)",true);
                        courseType = in.nextLine().trim().toUpperCase();
                        if (courseType.equalsIgnoreCase("X")) {
                            courseType = "";
                            break;
                        }
                        else if (courseDescription.trim().isEmpty())
                            UI.print("Course Type is required");
                        else if (!courseType.matches("GEN ED|MAJOR"))
                            UI.print("Invalid Input");
                    } while (!courseType.matches("GEN ED|MAJOR"));
                    break;

                case "/":
                    if (courseCode.isEmpty() || courseDescription.isEmpty() ||
                            creditUnits == -1 || courseType.isEmpty()) {
                        UI.print("Details are incomplete");
                    } else {
                        Course.courses.add(new Course(courseCode, courseDescription, creditUnits, courseType));
                        UI.print("Course successfully created");
                        displayCourse(courseCode, true);
                        submit = true;
                    }
                    break;

                case "X":
                    submit = true;
                    break;
            }
        }
    }
    public static void editCourse() {
        String courseCode, courseDescription, courseType, choice, input;
        int creditUnits; boolean submit = false;

        UI.print("EDIT EXISTING COURSE");

        do {
            UI.select("Enter Course Code",false);
            input = in.nextLine().trim();
            if (input.equalsIgnoreCase("X")) return;
            else if (input.trim().isEmpty()) UI.print("Course Code is required");
            else if (findCourse(input) == null) UI.print("Course code does not exist");
        } while (input.isEmpty() || findCourse(input) == null);

        Course c = Course.findCourse(input);

        if (c == null) {
            UI.print("Course not found");
            return;
        }

        courseCode = c.getCourseCode();
        courseDescription = c.getCourseDescription();
        creditUnits = c.getCreditUnits();
        courseType = c.getCourseType();

        while (!submit) {
            courseManage("EDIT", courseCode, courseDescription, creditUnits, courseType);

            do {
                UI.select("Select", false);
                choice = in.nextLine().trim().toUpperCase();

                if (!choice.matches("[1-4X]") && !choice.equals("/")) UI.print("Invalid Input");
            } while (!choice.matches("[1-4X]") && !choice.equals("/"));

            switch (choice) {
                case "1":
                    do {
                        UI.select("Enter Course Code", true);
                        courseCode = in.nextLine().trim();
                        if (courseCode.equalsIgnoreCase("X")) {
                            courseCode = c.courseCode;
                            break;
                        }
                        else if (courseCode.trim().isEmpty()) UI.print("Course Code is required");
                        else if (Course.findCourse(courseCode) != null && !c.getCourseCode().equalsIgnoreCase(courseCode))
                            UI.print("Course Code already exists");
                    } while (courseCode.isEmpty() ||
                            (Course.findCourse(courseCode) != null && !c.getCourseCode().equalsIgnoreCase(courseCode)));
                    break;

                case "2":
                    do {
                        UI.select("Enter Course Description",true);
                        courseDescription = in.nextLine().trim();
                        if (courseDescription.equalsIgnoreCase("X")) {
                            courseDescription = c.courseDescription;
                            break;
                        }
                        else if (courseDescription.trim().isEmpty()) UI.print("Course Description is required");
                        else if (Course.findCourseDesc(courseDescription) != null &&
                                !c.getCourseDescription().equalsIgnoreCase(courseDescription))
                            UI.print("Course Description already exists");
                    } while (courseDescription.isEmpty() ||
                            (Course.findCourseDesc(courseDescription) != null &&
                                    !c.getCourseDescription().equalsIgnoreCase(courseDescription)));
                    break;

                case "3":
                    do {
                        UI.select("Enter Credit Units",true);
                        input = in.nextLine().trim();
                        if (input.equalsIgnoreCase("X")) {
                            creditUnits = c.getCreditUnits();
                            break;
                        }
                        try {
                            creditUnits = Integer.parseInt(input);

                            if (creditUnits != 2 && creditUnits != 3) UI.print("Invalid Credit Units");
                        } catch (NumberFormatException e) {
                            UI.print("Invalid Credit Units");
                            creditUnits = -1;
                        }
                    } while (creditUnits != 2 && creditUnits != 3);
                    break;

                case "4":
                    do {
                        UI.select("Enter Course Type (GEN ED/MAJOR)",true);
                        courseType = in.nextLine().trim().toUpperCase();
                        if (courseType.equalsIgnoreCase("X")) {
                            courseType = c.getCourseType();
                            break;
                        }
                        else if (courseType.trim().isEmpty())
                            UI.print("Course Type is required");
                        else if (!courseType.matches("GEN ED|MAJOR"))
                            UI.print("Invalid Input");
                    } while (!courseType.matches("GEN ED|MAJOR"));
                    break;

                case "/":
                    c.setCourseCode(courseCode);
                    c.setCourseDescription(courseDescription);
                    c.setCreditUnits(creditUnits);
                    c.setCourseType(courseType);

                    UI.print("Course successfully edited");
                    displayCourse(c.getCourseCode(), true);
                    submit = true;
                    break;

                case "X":
                    submit = true;
                    break;
            }
        }
    }
    public static void archiveCourse() {
        String input, choice;

        UI.print("ARCHIVE EXISTING COURSE");

        do {
            UI.select("Enter Course Code",false);
            input = in.nextLine().trim();
            if (input.equalsIgnoreCase("X")) return;
            else if (input.isEmpty()) UI.print("Course Code is required");
            else if (findCourse(input) == null) UI.print("Course does not exist");
        } while (input.isEmpty() || findCourse(input) == null);

        Course c = Course.findCourse(input);

        if (c == null) {
            UI.print("Course not found");
            return;
        }

        for (CourseOffering offering : CourseOffering.offerings) {
            if (offering != null && offering.getCourseCode() != null
                    && offering.getCourseCode().equalsIgnoreCase(c.getCourseCode())) {
                UI.print("Archive or reassign the active course offerings first.");
                return;
            }
        }
        for (Curriculum curriculum : Curriculum.curricula) {
            if (curriculum != null && curriculum.getCourseCode() != null
                    && curriculum.getCourseCode().equalsIgnoreCase(c.getCourseCode())) {
                UI.print("Archive the active curriculum records for this course first.");
                return;
            }
        }

        displayCourse(c.getCourseCode(), false);

        do {
            UI.select("Confirm Course Archive? (Y/N)", false);
            choice = in.nextLine().trim().toUpperCase();
            if (choice.equals("X")) return;

            if (!choice.matches("[YN]"))
                UI.print("Invalid Input");
        } while (!choice.matches("[YN]"));

        if (choice.equals("Y")) {
            archiveCourses.add(c);
            Course.courses.remove(c);
            UI.print("Course successfully archived");
        }
        else UI.print("Archiving cancelled");
    }
    private static void courseManage(String text, String courseCode, String courseDescription,
                                     int creditUnits, String courseType) {
        UI.header(text.toUpperCase() + ": COURSE INFORMATION", false);
        UI.liner(String.format(" [1] COURSE CODE         : %s", courseCode), false);
        UI.liner(String.format(" [2] COURSE DESCRIPTION  : %s", courseDescription), false);
        UI.liner(String.format(" [3] CREDIT UNITS        : %s", creditUnits == -1 ? "" : creditUnits), false);
        UI.liner(String.format(" [4] COURSE TYPE         : %s", courseType), false);
        UI.liner("", false);
        UI.option();
        UI.footer(false);
    }
}