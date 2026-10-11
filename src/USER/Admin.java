package USER;

import DATA.Schedule;
import DATA.Program;
import UI.UI;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

import java.util.Scanner;

import static DATA.Program.*;
import static DATA.Schedule.*;


public class Admin extends User {
    Scanner sc = new Scanner(System.in);
    private String role;

    public Admin(String userID, String lastName, String firstName, String middleInitial, String emailAddress,
                 String password, String address, int postalCode, String birthDate, char sex, String role) {
        super(userID, lastName, firstName, middleInitial, emailAddress, password, address, postalCode, birthDate, sex);
        this.role = role;
    }

    void manageSchedule() {
        final int innerWidth = 70;
        UI.header("MANAGE SCHEDULE");
        System.out.printf("│%-" + innerWidth + "s│\n", " [1] ADD SCHEDULE");
        System.out.printf("│%-" + innerWidth + "s│\n", " [2] UPDATE SCHEDULE");
        System.out.printf("│%-" + innerWidth + "s│\n", " [3] DELETE SCHEDULE");
        UI.footer();
        System.out.println(" Select > ");

        int choice = sc.nextInt();
        sc.nextLine();
        System.out.println("└" + "─".repeat(innerWidth) + "┘");

        switch (choice) {
            case 1:
                addSchedule();
                break;
            case 2:
                updateSchedule();
                break;
            case 3:
                deleteSchedule();
                break;
            default:
                System.out.println("INVALID CHOICE");
        }
    }

    void addSchedule() {
        UI.header("ADD A SCHEDULE");

        boolean exists;
        String scheduleID, sectionID, day, startTime, endTime, roomCode;
        do {
            scheduleID = UI.inserted(sc, "ENTER SCHEDULE ID: ");
            exists = false;
            for (Schedule schedule : schedules) {
                if (schedule.getScheduleID().equalsIgnoreCase(scheduleID)) {
                    exists = true;
                    System.out.printf("│%-" + UI.INNERWIDTH + "s│\n", "  [!] SCHEDULE ALREADY EXISTS! ENTER AGAIN.");
                    break;
                }
            }
        } while (exists);

        do {
            sectionID = UI.inserted(sc, "ENTER SECTION ID: ");
            exists = false;
            if (sectionID.isEmpty()) {
                exists = true;
                System.out.printf("│%-" + UI.INNERWIDTH + "s│\n", "  [!] SECTION ID MUST NOT BE EMPTY!");
            }
        } while (exists);
        do {
            day = UI.inserted(sc, "ENTER DAY (M, T, W, TH, F, S): ");
            if (!day.matches("(?i)M|T|W|TH|F|S")) {
                System.out.printf("│%-" + UI.INNERWIDTH + "s│\n", "  [!] INVALID DAY! USE (M, T, W, TH, F, S).");
                exists = true;
            } else {
                exists = false;
            }
        } while (exists);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mma", Locale.ENGLISH);

        do {
            System.out.printf("│%-" + UI.INNERWIDTH + "s│\n", "  FORMAT OF TIME SHOULD BE (0:00AM) IN 12-HOUR SYSTEM");
            startTime = UI.inserted(sc, "ENTER START TIME: ");
            endTime = UI.inserted(sc, "ENTER END TIME: ");
            exists = false;

            try {
                LocalTime newStart = LocalTime.parse(startTime.toUpperCase(), formatter);
                LocalTime newEnd = LocalTime.parse(endTime.toUpperCase(), formatter);

                if (!newStart.isBefore(newEnd)) {
                    System.out.printf("│%-" + UI.INNERWIDTH + "s│\n", "  [!] START TIME MUST BE BEFORE END TIME!");
                    exists = true;
                    continue;
                }

                for (Schedule schedule : schedules) {
                    if (getDay(schedule).equalsIgnoreCase(day) && getSectionID(schedule).equalsIgnoreCase(sectionID)) {
                        LocalTime existingStart = LocalTime.parse(getStartTime(schedule).toUpperCase(), formatter);
                        LocalTime existingEnd = LocalTime.parse(getEndTime(schedule).toUpperCase(), formatter);

                        if (newStart.isBefore(existingEnd) && newEnd.isAfter(existingStart)) {
                            exists = true;
                            System.out.printf("│%-" + UI.INNERWIDTH + "s│\n", "  [!] SCHEDULE OVERLAPS WITH AN EXISTING CLASS!");
                            break;
                        }
                    }
                }
            } catch (DateTimeParseException e) {
                System.out.printf("│%-" + UI.INNERWIDTH + "s│\n", "  [!] INVALID TIME FORMAT! USE 5:00PM.");
                exists = true;
            }
        } while (exists);
        do {
            roomCode = UI.inserted(sc, "ENTER ROOM CODE: ");
            exists = false;

            for (Schedule schedule : schedules) {
                if (getRoomCode(schedule).equalsIgnoreCase(roomCode) &&
                        getDay(schedule).equalsIgnoreCase(day) &&
                        getStartTime(schedule).equalsIgnoreCase(startTime) &&
                        getEndTime(schedule).equalsIgnoreCase(endTime)) {

                    exists = true;
                    System.out.printf("│%-" + UI.INNERWIDTH + "s│\n", "  [!] OVERLAPPING SCHEDULE IN ROOM!");
                    break;
                }
            }
        } while (exists);

        schedules.add(new Schedule(scheduleID, day, startTime, endTime, sectionID, roomCode));
        UI.separator();
        System.out.printf("│%-" + UI.INNERWIDTH + "s│\n", "  SCHEDULE ADDED SUCCESSFULLY!");
        UI.footer();
    }

    void updateSchedule() {
        UI.header("UPDATE SCHEDULE");

        String targetID = UI.inserted(sc, "ENTER SCHEDULE ID TO UPDATE: ");

        while (targetID.isEmpty()) {
            targetID = UI.inserted(sc, "ENTER SCHEDULE ID TO UPDATE: ");
        }

        int targetIndex = -1;

        // Find the schedule to update
        for (int i = 0; i < schedules.size(); i++) {
            if (schedules.get(i).getScheduleID().equalsIgnoreCase(targetID)) {
                targetIndex = i;
                break;
            }
        }

        if (targetIndex == -1) {
            UI.separator();
            System.out.printf("│%-" + UI.INNERWIDTH + "s│\n", "  [!] SCHEDULE ID NOT FOUND!");
            UI.footer();
            return;
        }

        boolean exists;
        String newDay,newStartTime,newEndTime,newSection,newRoom;

        do {
            newSection = UI.inserted(sc, "ENTER NEW SECTION ID: ");
            exists = false;
            if (newSection.isEmpty()) {
                exists = true;
                System.out.printf("│%-" + UI.INNERWIDTH + "s│\n", "  [!] SECTION ID MUST NOT BE EMPTY! ENTER AGAIN.");
            }
        } while (exists);
        do {
            newDay = UI.inserted(sc, "ENTER NEW DAY (M, T, W, TH, F, S): ");
            if (!newDay.equalsIgnoreCase("M")  &&
                    !newDay.equalsIgnoreCase("T")  &&
                    !newDay.equalsIgnoreCase("W")  &&
                    !newDay.equalsIgnoreCase("TH") &&
                    !newDay.equalsIgnoreCase("F")  &&
                    !newDay.equalsIgnoreCase("S")) {

                System.out.printf("│%-" + UI.INNERWIDTH + "s│\n", "  [!] INVALID DAY! PLEASE ENTER (M, T, W, TH, F, S).");
                exists = true;
            } else {
                exists = false;
            }
        } while (exists);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mma", Locale.ENGLISH);

        do {
            System.out.printf("│%-" + UI.INNERWIDTH + "s│\n", "  FORMAT OF TIME SHOULD BE (0:00AM) IN 12-HOUR SYSTEM");
            newStartTime = UI.inserted(sc, "ENTER NEW START TIME: ");
            newEndTime = UI.inserted(sc, "ENTER NEW END TIME: ");

            exists = false;

            try {
                LocalTime newStart = LocalTime.parse(newStartTime.toUpperCase(), formatter);
                LocalTime newEnd = LocalTime.parse(newEndTime.toUpperCase(), formatter);

                if (!newStart.isBefore(newEnd)) {
                    System.out.printf("│%-" + UI.INNERWIDTH + "s│\n", "  [!] START TIME MUST BE BEFORE END TIME!");
                    exists = true;
                    continue;
                }

                for (int i = 0; i < schedules.size(); i++) {

                    if (i == targetIndex) continue;

                    Schedule schedule = schedules.get(i);
                    if (getDay(schedule).equalsIgnoreCase(newDay) && getSectionID(schedule).equalsIgnoreCase(newSection)) {

                        LocalTime existingStart = LocalTime.parse(getStartTime(schedule).toUpperCase(), formatter);
                        LocalTime existingEnd = LocalTime.parse(getEndTime(schedule).toUpperCase(), formatter);

                        if (newStart.isBefore(existingEnd) && newEnd.isAfter(existingStart)) {
                            exists = true;
                            System.out.printf("│%-" + UI.INNERWIDTH + "s│\n", "  [!] SCHEDULE OVERLAPS WITH AN EXISTING CLASS!");
                            break;
                        }
                    }
                }
            } catch (DateTimeParseException e) {
                System.out.printf("│%-" + UI.INNERWIDTH + "s│\n", "  [!] INVALID TIME FORMAT! USE FORMAT LIKE 5:00PM.");
                exists = true;
            }

        } while (exists);
        do {
            newRoom = UI.inserted(sc, "ENTER NEW ROOM CODE: ");
            exists = false;

            for (int i = 0; i < schedules.size(); i++) {

                if (i == targetIndex) continue;

                Schedule schedule = schedules.get(i);
                if (getRoomCode(schedule).equalsIgnoreCase(newRoom) &&
                        getDay(schedule).equalsIgnoreCase(newDay) &&
                        getStartTime(schedule).equalsIgnoreCase(newStartTime) &&
                        getEndTime(schedule).equalsIgnoreCase(newEndTime)) {

                    exists = true;
                    System.out.printf("│%-" + UI.INNERWIDTH + "s│\n", "  [!] OVERLAPPING SCHEDULE IN THE ROOM! ENTER AGAIN.");
                    break;
                }
            }
        } while (exists);

        Schedule updatedSchedule = new Schedule(targetID, newDay, newStartTime, newEndTime, newSection, newRoom);
        schedules.set(targetIndex, updatedSchedule);

        UI.separator();
        System.out.printf("│%-" + UI.INNERWIDTH + "s│\n", "  SCHEDULE UPDATED SUCCESSFULLY!");
        UI.footer();
    }

    void deleteSchedule() {
        UI.header("DELETE SCHEDULE");

        String targetID = UI.inserted(sc, "ENTER SCHEDULE ID TO DELETE: ");

        Schedule scheduleToRemove = null;

        for (Schedule schedule : schedules) {
            if (schedule.getScheduleID().equalsIgnoreCase(targetID)) {
                scheduleToRemove = schedule;
                break;
            }
        }

        UI.separator();
        if (scheduleToRemove != null) {
            schedules.remove(scheduleToRemove);
            System.out.printf("│%-" + UI.INNERWIDTH + "s│\n", "  SCHEDULE DELETED SUCCESSFULLY!");
        } else {
            System.out.printf("│%-" + UI.INNERWIDTH + "s│\n", "  [!] SCHEDULE ID NOT FOUND.");
        }
        UI.footer();
    }

    void manageProgram() {
        final int innerWidth = 70;
        UI.header("MANAGE PROGRAM");
        System.out.printf("│%-" + innerWidth + "s│\n", " [1] ADD PROGRAM");
        System.out.printf("│%-" + innerWidth + "s│\n", " [2] UPDATE PROGRAM");
        System.out.printf("│%-" + innerWidth + "s│\n", " [3] DELETE PROGRAM");
        UI.footer();
        System.out.println(" Select > ");

        int choice = sc.nextInt();
        sc.nextLine();
        System.out.println("└" + "─".repeat(innerWidth) + "┘");

        switch (choice) {
            case 1:
                addProgram();
                break;
            case 2:
                updateProgram();
                break;
            case 3:
                deleteProgram();
                break;
            default:
                System.out.println("INVALID CHOICE");
        }
    }

    void addProgram() {
        UI.header("ADD A PROGRAM  ");

        boolean exists;
        String programCode, programDescription, collegeCode;
        do {
            programCode = UI.inserted(sc, "ENTER PROGRAM CODE: ");
            exists = false;
            for (Program program : programs) {
                if (program.getProgramID().equalsIgnoreCase(programCode)) {
                    exists = true;
                    System.out.printf("│%-" + UI.INNERWIDTH + "s│\n", "  [!] PROGRAM CODE ALREADY EXISTS! ENTER AGAIN.");
                    break;
                }
            }
        } while (exists);
        do {
            programDescription = UI.inserted(sc, "ENTER PROGRAM DESCRIPTION: ");
            exists = false;
            for (Program program : programs) {
                if (getProgramDescription(program).equalsIgnoreCase(programDescription)) {
                    exists = true;
                    System.out.printf("│%-" + UI.INNERWIDTH + "s│\n", "  [!] PROGRAM DESCRIPTION ALREADY EXISTS! ENTER AGAIN.");
                    break;
                }
            }
        } while (exists);
        do {
            collegeCode = UI.inserted(sc, "ENTER COLLEGE CODE: ");
            exists = false;
            for (Program program : programs) {
                if (getCollegeCode(program).equalsIgnoreCase(collegeCode)) {
                    exists = true;
                    System.out.printf("│%-" + UI.INNERWIDTH + "s│\n", "  [!] SCHEDULE ALREADY EXISTS! ENTER AGAIN.");
                    break;
                }
            }
        } while (exists);
        programs.add(new Program(programCode, programDescription, collegeCode));
    }

    void updateProgram() {
        UI.header("UPDATE PROGRAM");

        String targetID = UI.inserted(sc, "ENTER PROGRAM ID TO UPDATE: ");

        for (int i = 0; i < programs.size(); i++) {
            Program oldProgram = programs.get(i);

            if (oldProgram.getProgramID().equalsIgnoreCase(targetID)) {

                String newProgramDescription = UI.inserted(sc, "ENTER NEW PROGRAM DESCRPTION: ");
                String newCollegeCode = UI.inserted(sc, "ENTER NEW COLLEGE CODE: ");
                Program updateProgram = new Program(targetID, newProgramDescription, newCollegeCode);
                programs.set(i, updateProgram);

                UI.separator();
                System.out.printf("│%-" + UI.INNERWIDTH + "s│\n", "  SCHEDULE UPDATED SUCCESSFULLY!");
                UI.footer();
                return;
            }
        }
    }


        void deleteProgram(){
            UI.header("DELETE PROGRAM");

            String targetID = UI.inserted(sc, "ENTER PROGRAM CODE TO DELETE: ");

            Program programToRemove = null;

            for (Program program : programs) {
                if (program.getProgramID().equalsIgnoreCase(targetID)) {
                    programToRemove = program;
                    break;
                }
            }

            UI.separator();
            if (programToRemove != null) {
                programs.remove(programToRemove);
                System.out.printf("│%-" + UI.INNERWIDTH + "s│\n", "  PROGRAM DELETED SUCCESSFULLY!");
            } else {
                System.out.printf("│%-" + UI.INNERWIDTH + "s│\n", "  [!] PROGRAM ID NOT FOUND.");
            }
            UI.footer();
        }
    }

