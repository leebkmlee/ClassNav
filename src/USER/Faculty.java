
package USER;

import DATA.*;
import FUNCTION.Request;
import UI.*;

import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class Faculty extends User {

    // FACULTY FIELDS
    private static int nextFacultyID = 1;
    private String collegeCode;
    private String employmentStatus;

    // Legacy temporary storage; replace with MakeUpSchedule records later.
    public static ArrayList<String[]> requests = new ArrayList<>();

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("MM/dd/uuuu")
                    .withResolverStyle(ResolverStyle.STRICT);

    // FACULTY CONSTRUCTOR
    public Faculty(String lastName, String firstName, String middleInitial, String emailAddress,
                   String password, String address, int postalCode, String birthDate, char gender,
                   String collegeCode, String employmentStatus) {

        super(String.format("F%04d", nextFacultyID++), lastName, firstName, middleInitial,
                emailAddress, password, address, postalCode, birthDate, gender);

        College college = College.findCollege(collegeCode);

        if (college == null) {
            throw new IllegalArgumentException("Invalid college code.");
        }

        if (employmentStatus == null || employmentStatus.trim().isEmpty()) {
            throw new IllegalArgumentException("Employment status cannot be empty.");
        }

        this.collegeCode = college.getCollegeCode();
        this.employmentStatus = employmentStatus.trim();
    }

    @Override
    public void menu() {
        String select;
        boolean valid;

        do {
            super.menu();
            UI.liner(" [3] View Schedule");
            UI.liner(" [4] Locate Room");
            UI.liner(" [5] Request Make-Up Class");
            UI.liner("");
            UI.liner(" [X] Log out");
            UI.footer();

            System.out.print("  Select > ");
            select = in.nextLine().trim().toUpperCase();

            valid = select.matches("[1-6X]");

            if (!valid)
                UI.print("Invalid Choice");

        } while (!valid);

        switch (select) {
            case "1":
                User.viewProfile(getUserID());
                menu();
                break;

            case "2":
                User.updateProfile(getUserID());
                menu();
                break;

            case "3":
                Faculty.displaySchedules(getUserID());
                menu();
                break;

            case "4":
                Faculty.locateRoom();
                menu();
                break;

            case "5":
                Request.submitRequest(getUserID());
                menu();
                break;

            case "X":
                User.start();
                break;
        }
    }

    // FACULTY SETTER METHODS
    public void setCollegeCode(String collegeCode) {
        College college = College.findCollege(collegeCode);

        if (college == null) {
            throw new IllegalArgumentException("Invalid college code.");
        }

        this.collegeCode = college.getCollegeCode();
    }

    public void setEmploymentStatus(String employmentStatus) {
        if (employmentStatus == null || employmentStatus.trim().isEmpty()) {
            throw new IllegalArgumentException("Employment status cannot be empty.");
        }

        this.employmentStatus = employmentStatus.trim();
    }

    // FACULTY GETTER METHODS
    public static Faculty findFaculty(String facultyID) {
        if (facultyID == null || facultyID.trim().isEmpty())
            return null;

        for (User u : users) {
            if (u instanceof Faculty
                    && facultyID.trim().equalsIgnoreCase(u.getUserID())) {
                return (Faculty) u;
            }
        }

        return null;
    }

    public static String getCollegeCode(User u) {
        if (u == null)
            return null;

        Faculty f = findFaculty(u.getUserID());
        return f == null ? null : f.collegeCode;
    }

    public static String getEmploymentStatus(User u) {
        if (u == null)
            return null;

        Faculty f = findFaculty(u.getUserID());
        return f == null ? null : f.employmentStatus;
    }

    // DISPLAY FACULTY SCHEDULES
    public static void displaySchedules(String facultyID) {
        Faculty f = findFaculty(facultyID);

        if (f == null) {
            UI.print("Faculty not found.");
            return;
        }

        UI.header("FACULTY SCHEDULE");
        UI.liner(String.format("  FACULTY ID    : %s", f.getUserID()));
        UI.liner(String.format("  FACULTY NAME  : %s", f.getUserName()));
        UI.liner(String.format("  EMAIL ADDRESS : %s", f.getEmail()));
        UI.liner(String.format("  EMPLOYMENT    : %s", f.employmentStatus));

        College college = College.findCollege(f.collegeCode);

        UI.liner(String.format("  COLLEGE       : %s",
                college == null ? "College not found"
                        : College.getCollegeDescription(college)));

        UI.separator();

        boolean found = false;

        for (CourseOffering offering : CourseOffering.getOfferingsByFaculty(f.getUserID())) {

            if (offering == null)
                continue;

            Section section = Section.findSection(offering.getSectionID());
            Course course = Course.findCourseByID(offering.getCourseID());

            for (Schedule schedule :
                    Schedule.getSchedulesByOffering(offering.getOfferingID())) {

                if (schedule == null)
                    continue;

                found = true;

                UI.liner(String.format("  SCHEDULE ID   : %s",
                        schedule.getScheduleID()));

                UI.liner(String.format("  COURSE CODE   : %s",
                        course == null ? "Course not found"
                                : course.getCourseCode()));

                UI.liner(String.format("  SECTION       : %s",
                        section == null ? "Section not found"
                                : Section.getYearSection(section)));

                UI.liner(String.format("  ACADEMIC YEAR : %s",
                        offering.getAcademicYear()));

                UI.liner(String.format("  TERM          : %d",
                        offering.getTerm()));

                UI.liner(String.format("  DAY           : %s",
                        schedule.getDay()));

                UI.liner(String.format("  TIME          : %s - %s",
                        schedule.getStartTime(), schedule.getEndTime()));

                UI.liner(String.format("  EFFECTIVE     : %s - %s",
                        schedule.getEffectiveFrom(), schedule.getEffectiveTo()));

                String roomCode = schedule.getRoomCode();
                Room room = Room.findRoom(roomCode);

                UI.liner(String.format("  ROOM CODE     : %s", roomCode));

                if (room != null) {
                    Building building = Building.findBuilding(room.getBuildingCode());

                    UI.liner(String.format("  ROOM          : %s",
                            room.getRoomDescription()));

                    UI.liner(String.format("  ROOM TYPE     : %s",
                            room.getRoomType()));

                    UI.liner(String.format("  CAPACITY      : %d",
                            room.getCapacity()));

                    UI.liner(String.format("  FLOOR         : %d",
                            room.getFloorNumber()));

                    UI.liner(String.format("  BUILDING      : %s",
                            building == null ? "Building not found"
                                    : Building.getBuildingName(building)));
                } else {
                    UI.liner("  ROOM          : Room not found");
                }

                UI.separator();
            }
        }

        if (!found) {
            UI.liner("  No schedules found.");
        }

        UI.footer();
    }

    // LOCATE ROOM
    public static void locateRoom() {

        String roomCode;

        System.out.print("  Enter Room Code > ");
        roomCode = in.nextLine().trim();

        Room room = Room.findRoom(roomCode);

        if (room == null) {
            UI.print("Room not found.");
            return;
        }

        Building building = Building.findBuilding(room.getBuildingCode());

        if (building == null) {
            UI.print("Building not found.");
            return;
        }

        UI.header("ROOM INFORMATION");
        UI.liner(String.format("  ROOM CODE        : %s", room.getRoomCode()));
        UI.liner(String.format("  ROOM DESCRIPTION : %s", room.getRoomDescription()));
        UI.liner(String.format("  ROOM TYPE        : %s", room.getRoomType()));
        UI.liner(String.format("  CAPACITY         : %d", room.getCapacity()));
        UI.liner(String.format("  BUILDING NAME    : %s",
                Building.getBuildingName(building)));
        UI.liner(String.format("  FLOOR NUMBER     : %d", room.getFloorNumber()));
        UI.footer();
    }

    // CHECK WHETHER A SCHEDULE IS ACTIVE TODAY
    private static boolean isScheduleActiveToday(Schedule schedule) {
        if (schedule == null
                || schedule.getEffectiveFrom() == null
                || schedule.getEffectiveTo() == null) {
            return false;
        }

        try {
            LocalDate effectiveFrom =
                    LocalDate.parse(schedule.getEffectiveFrom().trim(), DATE_FORMAT);

            LocalDate effectiveTo =
                    LocalDate.parse(schedule.getEffectiveTo().trim(), DATE_FORMAT);

            LocalDate today = LocalDate.now();

            return !today.isBefore(effectiveFrom)
                    && !today.isAfter(effectiveTo);

        } catch (DateTimeParseException e) {
            // Invalid date ranges must not be treated as active schedules.
            return false;
        }
    }

    // VIEW AVAILABLE ROOMS
    public static void viewAvailableRooms(String day, String startTime, String endTime) {

        if (day == null || day.trim().isEmpty()
                || startTime == null || startTime.trim().isEmpty()
                || endTime == null || endTime.trim().isEmpty()) {
            UI.print("Invalid schedule details.");
            return;
        }

        String requestedDay = day.trim();

        boolean validDay = false;

        String[] days = {
                "Monday", "Tuesday", "Wednesday", "Thursday",
                "Friday", "Saturday", "Sunday"
        };

        for (String valid : days) {
            if (valid.equalsIgnoreCase(requestedDay)) {
                requestedDay = valid;
                validDay = true;
                break;
            }
        }

        if (!validDay) {
            UI.print("Invalid day. Use Monday through Sunday.");
            return;
        }

        int requestedStart;
        int requestedEnd;

        try {
            requestedStart = Room.convertToMinutes(startTime);
            requestedEnd = Room.convertToMinutes(endTime);
        } catch (IllegalArgumentException e) {
            UI.print("Invalid time format.");
            return;
        }

        if (requestedStart >= requestedEnd) {
            UI.print("Start time must be earlier than end time.");
            return;
        }

        UI.header("AVAILABLE ROOMS");
        UI.liner(String.format("  DAY        : %s", requestedDay));
        UI.liner(String.format("  START TIME : %s", startTime));
        UI.liner(String.format("  END TIME   : %s", endTime));
        UI.liner(String.format("  CHECK DATE : %s", LocalDate.now()));
        UI.separator();

        boolean found = false;

        for (Room room : Room.rooms) {
            if (room == null)
                continue;

            boolean available = true;

            for (Schedule schedule : Schedule.schedules) {
                if (schedule == null)
                    continue;

                // Ignore schedules that are not active today.
                if (!isScheduleActiveToday(schedule))
                    continue;

                String scheduledRoom = schedule.getRoomCode();
                String scheduledDay = schedule.getDay();

                if (scheduledRoom == null || scheduledDay == null)
                    continue;

                // Only check schedules assigned to this room on the requested day.
                if (!room.getRoomCode().equalsIgnoreCase(scheduledRoom.trim())
                        || !requestedDay.equalsIgnoreCase(scheduledDay.trim())) {
                    continue;
                }

                int existingStart;
                int existingEnd;

                try {
                    existingStart = Room.convertToMinutes(schedule.getStartTime());
                    existingEnd = Room.convertToMinutes(schedule.getEndTime());
                } catch (IllegalArgumentException e) {
                    // Conservatively treat an invalid active schedule as a conflict.
                    available = false;
                    break;
                }

                // Check whether the requested time overlaps an active schedule.
                if (requestedStart < existingEnd && requestedEnd > existingStart) {
                    available = false;
                    break;
                }
            }

            if (!available)
                continue;

            found = true;

            Building building = Building.findBuilding(room.getBuildingCode());

            UI.liner(String.format("  ROOM CODE   : %s", room.getRoomCode()));
            UI.liner(String.format("  DESCRIPTION : %s", room.getRoomDescription()));
            UI.liner(String.format("  ROOM TYPE   : %s", room.getRoomType()));
            UI.liner(String.format("  CAPACITY    : %d", room.getCapacity()));
            UI.liner(String.format("  FLOOR       : %d", room.getFloorNumber()));
            UI.liner(String.format("  BUILDING    : %s",
                    building == null ? "Building not found"
                            : Building.getBuildingName(building)));
            UI.separator();
        }

        if (!found) {
            UI.liner("  No available rooms found.");
        }

        UI.footer();
    }
}