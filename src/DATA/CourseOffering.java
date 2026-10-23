
package DATA;

import UI.*;
import USER.*;
import DATA.*;

import java.util.ArrayList;

import static UI.UI.in;

public class CourseOffering {

    private static int nextOfferingID = 1;

    private String offeringID;
    private String sectionID; // FK -> Section.sectionID
    private String courseCode;  // FK -> Course.courseCode
    private String facultyID; // FK -> Faculty.facultyID
    private String academicYear;
    private int term;

    public static ArrayList<CourseOffering> offerings = new ArrayList<>();

    // COURSE OFFERING CONSTRUCTOR

    public CourseOffering(String sectionID, String courseCode, String facultyID,
                          String academicYear, int term) {

        if (sectionID == null || sectionID.trim().isEmpty())
            throw new IllegalArgumentException("Section ID is required.");

        if (courseCode == null || courseCode.trim().isEmpty())
            throw new IllegalArgumentException("Course Code is required.");

        if (facultyID == null || facultyID.trim().isEmpty())
            throw new IllegalArgumentException("Faculty ID is required.");

        if (academicYear == null || academicYear.trim().isEmpty())
            throw new IllegalArgumentException("Academic year is required.");

        sectionID = sectionID.trim();
        courseCode = courseCode.trim();
        facultyID = facultyID.trim();
        academicYear = academicYear.trim();

        if (Section.findSection(sectionID) == null)
            throw new IllegalArgumentException("Section not found.");

        if (Course.findCourse(courseCode) == null)
            throw new IllegalArgumentException("Course not found.");

        if (Faculty.findFaculty(facultyID) == null)
            throw new IllegalArgumentException("Faculty not found.");

        if (term < 1 || term > 3)
            throw new IllegalArgumentException("Term must be 1, 2, or 3.");

        this.offeringID = String.format("COF%03d", nextOfferingID++);
        this.sectionID = sectionID;
        this.courseCode = courseCode;
        this.facultyID = facultyID;
        this.academicYear = academicYear;
        this.term = term;
    }

    // COURSE OFFERING GETTER METHODS

    public String getOfferingID() {
        return offeringID;
    }

    public String getSectionID() {
        return sectionID;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getFacultyID() {
        return facultyID;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public int getTerm() {
        return term;
    }

    // STATIC GETTER METHODS

    public static String getOffering(CourseOffering o) {
        return o == null ? null : o.getOfferingID();
    }

    public static String getSectionID(CourseOffering o) {
        return o == null ? null : o.getSectionID();
    }

    public static String getCourseCode(CourseOffering o) {
        return o == null ? null : o.getCourseCode();
    }

    public static String getFacultyID(CourseOffering o) {
        return o == null ? null : o.getFacultyID();
    }

    public static String getAcademicYear(CourseOffering o) {
        return o == null ? null : o.getAcademicYear();
    }

    public static int getTerm(CourseOffering o) {
        return o == null ? 0 : o.getTerm();
    }

    // FIND METHODS

    public static CourseOffering findOffering(String offeringID) {

        if (offeringID == null || offeringID.trim().isEmpty())
            return null;

        for (CourseOffering o : offerings) {
            if (offeringID.trim().equalsIgnoreCase(o.getOfferingID()))
                return o;
        }

        return null;
    }

    public static boolean existOffering(String offeringID) {
        return findOffering(offeringID) != null;
    }

    // ADD A COURSE OFFERING

    public static boolean addOffering(String sectionID, String courseCode,
                                      String facultyID, String academicYear,
                                      int term) {

        if (sectionID == null || sectionID.trim().isEmpty()
                || courseCode == null || courseCode.trim().isEmpty()
                || facultyID == null || facultyID.trim().isEmpty()
                || academicYear == null || academicYear.trim().isEmpty()) {

            UI.print("All course offering fields are required.");
            return false;
        }

        sectionID = sectionID.trim();
        courseCode = courseCode.trim();
        facultyID = facultyID.trim();
        academicYear = academicYear.trim();

        if (Section.findSection(sectionID) == null) {
            UI.print("Section not found.");
            return false;
        }

        if (Course.findCourse(courseCode) == null) {
            UI.print("Course not found.");
            return false;
        }

        if (Faculty.findFaculty(facultyID) == null) {
            UI.print("Faculty not found.");
            return false;
        }

        if (term < 1 || term > 3) {
            UI.print("Term must be 1, 2, or 3.");
            return false;
        }

        // Prevent duplicate course offerings for the same section,
        // academic year, and term, regardless of assigned faculty.

        for (CourseOffering o : offerings) {

            if (sectionID.equalsIgnoreCase(o.getSectionID())
                    && courseCode.equalsIgnoreCase(o.getCourseCode())
                    && academicYear.equalsIgnoreCase(o.getAcademicYear())
                    && term == o.getTerm()) {

                UI.print("This course is already offered to this section for that academic year and term.");
                return false;
            }
        }

        offerings.add(new CourseOffering(sectionID, courseCode, facultyID,
                academicYear, term));

        return true;
    }

    // RETRIEVE OFFERINGS BY SECTION

    public static ArrayList<CourseOffering> getOfferingsBySection(String sectionID) {

        ArrayList<CourseOffering> result = new ArrayList<>();

        if (sectionID == null || sectionID.trim().isEmpty())
            return result;

        sectionID = sectionID.trim();

        if (Section.findSection(sectionID) == null)
            return result;

        for (CourseOffering o : offerings) {
            if (sectionID.equalsIgnoreCase(o.getSectionID()))
                result.add(o);
        }

        return result;
    }

    // RETRIEVE OFFERINGS BY COURSE

    public static ArrayList<CourseOffering> getOfferingsByCourse(String courseCode) {

        ArrayList<CourseOffering> result = new ArrayList<>();

        if (courseCode == null || courseCode.trim().isEmpty())
            return result;

        courseCode = courseCode.trim();

        if (Course.findCourse(courseCode) == null)
            return result;

        for (CourseOffering o : offerings) {
            if (courseCode.equalsIgnoreCase(o.getCourseCode()))
                result.add(o);
        }

        return result;
    }

    // RETRIEVE OFFERINGS BY FACULTY

    public static ArrayList<CourseOffering> getOfferingsByFaculty(String facultyID) {

        ArrayList<CourseOffering> result = new ArrayList<>();

        if (facultyID == null || facultyID.trim().isEmpty())
            return result;

        facultyID = facultyID.trim();

        if (Faculty.findFaculty(facultyID) == null)
            return result;

        for (CourseOffering o : offerings) {
            if (facultyID.equalsIgnoreCase(o.getFacultyID()))
                result.add(o);
        }

        return result;
    }

    // DISPLAY ALL COURSE OFFERINGS

    public static void displayOfferings() {

        UI.header("COURSE OFFERINGS");

        if (offerings.isEmpty()) {

            UI.liner("  No course offerings found.");

        } else {

            for (int i = 0; i < offerings.size(); i++) {

                CourseOffering o = offerings.get(i);

                Section section = Section.findSection(o.getSectionID());
                Course course = Course.findCourse(o.getCourseCode());
                Faculty faculty = Faculty.findFaculty(o.getFacultyID());

                UI.liner(String.format("  OFFERING ID  : %s",
                        o.getOfferingID()));

                UI.liner(String.format("  SECTION      : %s",
                        section == null ? "Section not found"
                                : Section.getYearSection(section)));

                UI.liner(String.format("  COURSE       : %s",
                        course == null ? "Course not found"
                                : course.getCourseCode()));

                UI.liner(String.format("  FACULTY ID   : %s",
                        o.getFacultyID()));

                UI.liner(String.format("  FACULTY      : %s",
                        faculty == null ? "Faculty not found"
                                : faculty.getUserName()));

                UI.liner(String.format("  ACADEMIC YEAR: %s",
                        o.getAcademicYear()));

                UI.liner(String.format("  TERM         : %d",
                        o.getTerm()));

                if (i < offerings.size() - 1)
                    UI.separator();
            }
        }

        UI.footer();
    }

    // DISPLAY ONE COURSE OFFERING

    public static void displayOffering(String offeringID) {

        CourseOffering o = findOffering(offeringID);

        if (o == null) {
            UI.print("Course offering not found.");
            return;
        }

        Section section = Section.findSection(o.getSectionID());
        Course course = Course.findCourse(o.getCourseCode());
        Faculty faculty = Faculty.findFaculty(o.getFacultyID());

        UI.header("COURSE OFFERING DETAILS");

        UI.liner(String.format("  OFFERING ID   : %s",
                o.getOfferingID()));

        UI.liner(String.format("  SECTION ID    : %s",
                o.getSectionID()));

        UI.liner(String.format("  SECTION       : %s",
                section == null ? "Section not found"
                        : Section.getYearSection(section)));

        UI.liner(String.format("  COURSE CODE     : %s",
                o.getCourseCode()));

        UI.liner(String.format("  COURSE        : %s",
                course == null ? "Course not found"
                        : course.getCourseCode()));

        UI.liner(String.format("  FACULTY ID    : %s",
                o.getFacultyID()));

        UI.liner(String.format("  FACULTY       : %s",
                faculty == null ? "Faculty not found"
                        : faculty.getUserName()));

        UI.liner(String.format("  ACADEMIC YEAR : %s",
                o.getAcademicYear()));

        UI.liner(String.format("  TERM          : %d",
                o.getTerm()));

        UI.footer();
    }

    // MANAGE COURSE OFFERING

    // CREATE COURSE OFFERING
    public static void createCourseOffering() {
        String sectionID = ""; String courseCode = ""; String facultyID = "";
        String academicYear = ""; String choice = "";
        int term = -1; boolean submit = false;

        while (!submit) {
            courseOfferingManage("CREATE", "", sectionID, courseCode,
                    facultyID, academicYear, term);

            do {
                System.out.print("  Select > ");
                choice = in.nextLine().trim();
                if (!choice.matches("[1-5X]") && !choice.equals("/"))
                    UI.print("Invalid Choice");
            } while (!choice.matches("[1-5X]") && !choice.equals("/"));

            switch (choice) {
                case "1":
                    do {
                        System.out.print("   Enter Section ID > ");
                        sectionID = in.nextLine().trim();
                        if (sectionID.isEmpty())
                            UI.print("Invalid Input");
                        else if (Section.findSection(sectionID) == null)
                            UI.print("Section does not exist");
                    } while (sectionID.isEmpty()
                            || Section.findSection(sectionID) == null);
                    break;

                case "2":
                    do {
                        System.out.print("   Enter Course Code > ");
                        courseCode = in.nextLine().trim();
                        if (courseCode.isEmpty())
                            UI.print("Invalid Input");
                        else if (Course.findCourse(courseCode) == null)
                            UI.print("Course does not exist");
                    } while (courseCode.isEmpty()
                            || Course.findCourse(courseCode) == null);
                    break;

                case "3":
                    do {
                        System.out.print("   Enter Faculty ID > ");
                        facultyID = in.nextLine().trim();
                        if (facultyID.isEmpty())
                            UI.print("Invalid Input");
                        else if (Faculty.findFaculty(facultyID) == null)
                            UI.print("Faculty does not exist");
                    } while (facultyID.isEmpty()
                            || Faculty.findFaculty(facultyID) == null);
                    break;

                case "4":
                    do {
                        System.out.print("   Enter Academic Year (e.g. 2026-2027) > ");
                        academicYear = in.nextLine().trim();
                        if (academicYear.isEmpty())
                            UI.print("Invalid Input");
                    } while (academicYear.isEmpty());
                    break;

                case "5":
                    do {
                        System.out.print("   Enter Term (1-3) > ");
                        try {
                            String input = in.nextLine().trim();
                            term = Integer.parseInt(input);
                            if (!(term >= 1 && term <= 3))
                                UI.print("Invalid Term");
                        } catch (NumberFormatException e) {
                            UI.print("Invalid Term");
                            term = -1;
                        }
                    } while (!(term >= 1 && term <= 3));
                    break;

                case "/":
                    if (sectionID.isEmpty() || courseCode.isEmpty()
                            || facultyID.isEmpty() || academicYear.isEmpty()
                            || term == -1) {
                        UI.print("Details are incomplete");
                    } else if (addOffering(sectionID, courseCode, facultyID,
                            academicYear, term)) {
                        CourseOffering o = offerings.get(offerings.size() - 1);
                        UI.print("Course offering successfully created");
                        displayOffering(o.getOfferingID());
                        submit = true;
                    }
                    break;

                case "X":
                    submit = true;
                    break;
            }
        }
    }

    // EDIT COURSE OFFERING
    public static void editCourseOffering() {
        String input; String choice;
        String sectionID; String courseCode; String facultyID;
        String academicYear; int term;
        boolean submit = false;

        UI.print("EDIT EXISTING COURSE OFFERING");

        do {
            System.out.print("  Enter Offering ID > ");
            input = in.nextLine().trim();
            if (input.isEmpty())
                UI.print("Invalid Input");
            else if (findOffering(input) == null)
                UI.print("Course offering does not exist");
        } while (input.isEmpty() || findOffering(input) == null);

        CourseOffering o = findOffering(input);
        if (o == null) {
            UI.print("Course offering not found");
            return;
        }

        sectionID = o.getSectionID();
        courseCode = o.getCourseCode();
        facultyID = o.getFacultyID();
        academicYear = o.getAcademicYear();
        term = o.getTerm();

        while (!submit) {
            courseOfferingManage("EDIT", o.getOfferingID(), sectionID,
                    courseCode, facultyID, academicYear, term);

            do {
                System.out.print("  Select > ");
                choice = in.nextLine().trim();
                if (!choice.matches("[1-5X]") && !choice.equals("/"))
                    UI.print("Invalid Choice");
            } while (!choice.matches("[1-5X]") && !choice.equals("/"));

            switch (choice) {
                case "1":
                    do {
                        System.out.print("   Enter Section ID > ");
                        sectionID = in.nextLine().trim();
                        if (sectionID.isEmpty())
                            UI.print("Invalid Input");
                        else if (Section.findSection(sectionID) == null)
                            UI.print("Section does not exist");
                    } while (sectionID.isEmpty()
                            || Section.findSection(sectionID) == null);
                    break;

                case "2":
                    do {
                        System.out.print("   Enter Course Code > ");
                        courseCode = in.nextLine().trim();
                        if (courseCode.isEmpty())
                            UI.print("Invalid Input");
                        else if (Course.findCourse(courseCode) == null)
                            UI.print("Course does not exist");
                    } while (courseCode.isEmpty()
                            || Course.findCourse(courseCode) == null);
                    break;

                case "3":
                    do {
                        System.out.print("   Enter Faculty ID > ");
                        facultyID = in.nextLine().trim();
                        if (facultyID.isEmpty())
                            UI.print("Invalid Input");
                        else if (Faculty.findFaculty(facultyID) == null)
                            UI.print("Faculty does not exist");
                    } while (facultyID.isEmpty()
                            || Faculty.findFaculty(facultyID) == null);
                    break;

                case "4":
                    do {
                        System.out.print("   Enter Academic Year > ");
                        academicYear = in.nextLine().trim();
                        if (academicYear.isEmpty())
                            UI.print("Invalid Input");
                    } while (academicYear.isEmpty());
                    break;

                case "5":
                    do {
                        System.out.print("   Enter Term (1-3) > ");
                        try {
                            input = in.nextLine().trim();
                            term = Integer.parseInt(input);
                            if (!(term >= 1 && term <= 3))
                                UI.print("Invalid Term");
                        } catch (NumberFormatException e) {
                            UI.print("Invalid Term");
                            term = -1;
                        }
                    } while (!(term >= 1 && term <= 3));
                    break;

                case "/":
                    if (sectionID.isEmpty() || courseCode.isEmpty()
                            || facultyID.isEmpty() || academicYear.isEmpty()
                            || term == -1) {
                        UI.print("Details are incomplete");
                    } else {
                        boolean duplicate = false;

                        for (CourseOffering existing : offerings) {
                            if (existing != o
                                    && sectionID.equalsIgnoreCase(existing.getSectionID())
                                    && courseCode.equalsIgnoreCase(existing.getCourseCode())
                                    && academicYear.equalsIgnoreCase(existing.getAcademicYear())
                                    && term == existing.getTerm()) {
                                duplicate = true;
                                break;
                            }
                        }

                        if (duplicate) {
                            UI.print("This course is already offered to this section for that academic year and term.");
                        } else {
                            o.sectionID = sectionID;
                            o.courseCode = courseCode;
                            o.facultyID = facultyID;
                            o.academicYear = academicYear;
                            o.term = term;

                            UI.print("Course offering successfully edited");
                            displayOffering(o.getOfferingID());
                            submit = true;
                        }
                    }
                    break;

                case "X":
                    submit = true;
                    break;
            }
        }
    }

    // DELETE COURSE OFFERING
    public static void deleteCourseOffering() {
        String input; String choice;

        UI.print("DELETE EXISTING COURSE OFFERING");

        do {
            System.out.print("  Enter Offering ID > ");
            input = in.nextLine().trim();
            if (input.isEmpty())
                UI.print("Invalid Input");
            else if (findOffering(input) == null)
                UI.print("Course offering does not exist");
        } while (input.isEmpty() || findOffering(input) == null);

        CourseOffering o = findOffering(input);
        if (o == null) {
            UI.print("Course offering not found");
            return;
        }

        displayOffering(o.getOfferingID());

        do {
            System.out.print("  Confirm Course Offering Deletion? (Y/N) > ");
            choice = in.nextLine().trim().toUpperCase();
            if (!choice.matches("[YN]"))
                UI.print("Invalid Input");
        } while (!choice.matches("[YN]"));

        if (choice.equals("Y")) {
            offerings.remove(o);
            UI.print("Course offering successfully deleted");
        }
    }

    // COURSE OFFERING FORM
    private static void courseOfferingManage(String text, String offeringID,
                                             String sectionID, String courseCode, String facultyID,
                                             String academicYear, int term) {

        UI.header(text.toUpperCase() + ": COURSE OFFERING INFORMATION");
        if (!offeringID.isEmpty())
            UI.liner(String.format(" OFFERING ID   : %s", offeringID));
        UI.liner(String.format(" [1] SECTION ID    : %s", sectionID));
        UI.liner(String.format(" [2] COURSE CODE     : %s", courseCode));
        UI.liner(String.format(" [3] FACULTY ID    : %s", facultyID));
        UI.liner(String.format(" [4] ACADEMIC YEAR : %s", academicYear));
        UI.liner(String.format(" [5] TERM          : %s", term == -1 ? "" : term));
        UI.liner("");
        UI.option();
        UI.footer();
    }
}