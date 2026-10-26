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

        if (courseCode == null || courseCode.trim().isEmpty())
            throw new IllegalArgumentException("Course code cannot be empty.");

        for (Course c : courses) {
            if (c != this &&
                    courseCode.trim().equalsIgnoreCase(c.getCourseCode())) {
                throw new IllegalArgumentException("Course code already exists.");
            }
        }

        this.courseCode = courseCode.trim();
    }
    public void setCourseDescription(String courseDescription) {

        if (courseDescription == null || courseDescription.trim().isEmpty())
            throw new IllegalArgumentException("Course description cannot be empty.");

        for (Course c : courses) {
            if (c != this &&
                    courseDescription.trim().equalsIgnoreCase(c.getCourseDescription())) {
                throw new IllegalArgumentException("Course description already exists.");
            }
        }

        this.courseDescription = courseDescription.trim();
    }
    public void setCreditUnits(int creditUnits) {

        if (creditUnits <= 0)
            throw new IllegalArgumentException("Credit units must be greater than zero.");

        this.creditUnits = creditUnits;
    }
    public void setCourseType(String courseType) {

        if (courseType == null || courseType.trim().isEmpty())
            throw new IllegalArgumentException("Course type cannot be empty.");

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
            if (courseCode.trim().equalsIgnoreCase(c.getCourseCode()))
                return c;
        }

        return null;
    }
    public static Course findCourseDesc(String courseDescription) {
        if (courseDescription == null || courseDescription.trim().isEmpty())
            return null;

        for (Course c : courses) {
            if (courseDescription.trim().equalsIgnoreCase(c.courseDescription))
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

        UI.header("COURSE DETAILS");
        UI.liner(String.format("  COURSE CODE         : %s", c.getCourseCode()));
        UI.liner(String.format("  COURSE DESCRIPTION  : %s", c.getCourseDescription()));
        UI.liner(String.format("  CREDIT UNITS        : %d", c.getCreditUnits()));
        UI.liner(String.format("  COURSE TYPE         : %s", c.getCourseType()));
        UI.footer();
        if (print) UI.returnButton();
    }
    public static void displayCourses() {

        UI.header("COURSE LIST");

        if (courses.isEmpty()) {
            UI.liner("  No courses found.");
        } else {
            for (Course c : courses) {
                String text = c.getCourseDescription();
                if (text.length() > 40) text = text.substring(0, 40) + "...";
                String course = String.format("  %-10s %-45s %-10s",
                        c.getCourseCode(), text, c.getCreditUnits() + " units");
                UI.liner(course);
            }
        }

        UI.footer();
        UI.returnButton();
    }
    public static void displayArchiveCourses() {

        UI.header("ARCHIVED COURSES");

        if (archiveCourses.isEmpty()) {
            UI.liner("  No courses found.");
        } else {
            for (Course c : archiveCourses) {
                String text = c.getCourseDescription();
                if (text.length() > 40) text = text.substring(0, 40) + "...";
                String course = String.format("  %-10s %-45s %-10s",
                        c.getCourseCode(), text, c.getCreditUnits() + " units");
                UI.liner(course);
            }
        }

        UI.footer();
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

            if (o.getCourseCode().equalsIgnoreCase(c.getCourseCode())
                    && o.getFacultyID().equalsIgnoreCase(facultyID.trim())) {
                matchingOfferings.add(o);
            }
        }

        UI.header("COURSE SECTIONS");

        UI.liner(String.format("  COURSE CODE : %s", c.getCourseCode()));
        UI.liner(String.format("  COURSE      : %s", c.getCourseDescription()));
        UI.liner(String.format("  FACULTY ID  : %s", facultyID.trim()));
        UI.separator();

        if (matchingOfferings.isEmpty()) {

            UI.liner("  No sections found for this course and faculty.");

        } else {

            for (int i = 0; i < matchingOfferings.size(); i++) {

                CourseOffering o = matchingOfferings.get(i);
                Section s = Section.findSection(o.getSectionID());

                UI.liner(String.format("  OFFERING ID  : %s", o.getOfferingID()));
                UI.liner(String.format("  SECTION ID   : %s", o.getSectionID()));

                UI.liner(String.format("  SECTION      : %s",
                        s == null ? "Section not found"
                                : Section.getYearSection(s)));

                UI.liner(String.format("  ACADEMIC YEAR: %s", o.getAcademicYear()));
                UI.liner(String.format("  TERM         : %d", o.getTerm()));

                if (i < matchingOfferings.size() - 1)
                    UI.separator();
            }
        }

        UI.footer();
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
                            input = in.nextLine().trim();
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

        displayCourse(c.getCourseCode(), false);

        do {
            System.out.print("  Confirm Course Archive? (Y/N) > ");
            choice = in.nextLine().trim().toUpperCase();

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
        UI.header(text.toUpperCase() + ": COURSE INFORMATION");
        UI.liner(String.format(" [1] COURSE CODE         : %s", courseCode));
        UI.liner(String.format(" [2] COURSE DESCRIPTION  : %s", courseDescription));
        UI.liner(String.format(" [3] CREDIT UNITS        : %s", creditUnits == -1 ? "" : creditUnits));
        UI.liner(String.format(" [4] COURSE TYPE         : %s", courseType));
        UI.liner("");
        UI.option();
        UI.footer();
    }
}