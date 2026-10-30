package UI;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Locale;
import java.util.Scanner;

public class UI {

    // PRINT METHODS
    public static void print(String input) {
        if (input == null)
            input = "null";

        System.out.println("\n┌" + "─".repeat(INNERWIDTH) + "┐");

        if (input.length() <= INNERWIDTH) {
            int left = (INNERWIDTH - input.length()) / 2;
            int right = INNERWIDTH - input.length() - left;

            System.out.println("│" + " ".repeat(left) + input
                    + " ".repeat(right) + "│");
        } else {
            for (int i = 0; i < input.length(); i += INNERWIDTH) {
                String line = input.substring(i,
                        Math.min(i + INNERWIDTH, input.length()));

                System.out.printf("│%-" + INNERWIDTH + "s│%n", line);
            }
        }

        System.out.println("└" + "─".repeat(INNERWIDTH) + "┘");
    }
    public static void print(String input1, String input2) {
        if (input1 == null) input1 = "null";
        if (input2 == null) input2 = "null";

        if (input1.length() > INNERWIDTH || input2.length() > INNERWIDTH) {
            print(input1 + " | " + input2);
            return;
        }

        int left = (INNERWIDTH - input1.length()) / 2;
        int right = INNERWIDTH - input1.length() - left;
        int attemptLeft = (INNERWIDTH - input2.length()) / 2;
        int attemptRight = INNERWIDTH - input2.length() - attemptLeft;
        System.out.println("\n┌" + "─".repeat(INNERWIDTH) + "┐");
        System.out.println("│" + " ".repeat(left) + input1 + " ".repeat(right) + "│");
        System.out.println("│" + " ".repeat(attemptLeft) + input2 + " ".repeat(attemptRight) + "│");
        System.out.println("└" + "─".repeat(INNERWIDTH) + "┘");
    }
    public static void startPrint(String title) {
        System.out.println("\n┌" + "─".repeat(INNERWIDTH) + "┐");
        title = fitText(title, INNERWIDTH);
        int left = (INNERWIDTH - title.length()) / 2;
        int right = INNERWIDTH - title.length() - left;
        System.out.println("│" + " ".repeat(left) + title + " ".repeat(right) + "│");
        System.out.println("├" + "─".repeat(INNERWIDTH) + "┤");
        System.out.printf("│%-" + INNERWIDTH + "s│%n", " [1] Log in");
        System.out.printf("│%-" + INNERWIDTH + "s│%n", " [X] Exit");
        System.out.println("└" + "─".repeat(INNERWIDTH) + "┘");
        System.out.print("  Select > ");
    }
    public static void header (String title, boolean isExtended) {
        int width = isExtended ? TABLEWIDTH : INNERWIDTH;
        title = fitText(title, width);

        System.out.println("\n┌" + "─".repeat(width) + "┐");
        int left = (width - title.length()) / 2;
        int right = width - title.length() - left;
        System.out.println("│" + " ".repeat(left) + title + " ".repeat(right) + "│");
        System.out.println("├" + "─".repeat(width) + "┤");
    }
    public static void footer (boolean isExtended) {
        int width = isExtended ? TABLEWIDTH : INNERWIDTH;
        System.out.println("└" + "─".repeat(width) + "┘");
    }
    public static void separator(boolean isExtended) {
        int width = isExtended ? TABLEWIDTH : INNERWIDTH;
        System.out.println("├" + "─".repeat(width) + "┤");
    }
    public static void manageUI(String manageText) {
        UI.header("MANAGE " + manageText.toUpperCase(), false);
        System.out.printf("│%-" + INNERWIDTH + "s│%n", " [1] Create New " + manageText);
        System.out.printf("│%-" + INNERWIDTH + "s│%n", " [2] Update Existing " + manageText);
        System.out.printf("│%-" + INNERWIDTH + "s│%n", " [3] Delete Existing " + manageText);
        System.out.printf("│%-" + INNERWIDTH + "s│%n", " [4] View All " + manageText);
        System.out.printf("│%-" + INNERWIDTH + "s│%n", " ");
        System.out.printf("│%-" + INNERWIDTH + "s│%n", " [X] Back");
        UI.footer(false);
    }
    public static void option() {
        String options = "[/] Submit     [X] Return";
        int left = (INNERWIDTH - options.length()) / 2;
        int right = INNERWIDTH - options.length() - left;
        System.out.println("│" + " ".repeat(left) + options + " ".repeat(right) + "│");
    }
    public static void liner (String text, boolean isExtended){
        int width = isExtended ? TABLEWIDTH : INNERWIDTH;
        if (text == null) text = "null";

        String[] lines = text.split("\\R", -1);
        for (String line : lines) {
            if (line.isEmpty()) {
                System.out.printf("│%-" + width + "s│%n", "");
                continue;
            }
            while (line.length() > width) {
                int end = width;
                int space = line.lastIndexOf(' ', end);
                if (space > 0) end = space;
                System.out.printf("│%-" + width + "s│%n", line.substring(0, end));
                line = line.substring(end).stripLeading();
            }
            System.out.printf("│%-" + width + "s│%n", line);
        }
    }
    public static void returnButton() {
        System.out.print(" ⏎ Press ENTER to return ...");
        in.nextLine();
    }
    public static void select(String text, boolean indent) {
        if (!indent) System.out.print("  [X] Return | " + text + " > ");
        else System.out.print("   [X] Return | " + text + " > ");
    }

    // VALIDATION HELPERS
    public static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("MM/dd/uuuu").withResolverStyle(ResolverStyle.STRICT);
    public static Scanner in = new Scanner (System.in);
    public static final int INNERWIDTH = 70;
    public static final int TABLEWIDTH = 180;
    public static final String[] DAYS = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    public static String normalizeDay(String day) {
        if (day == null) return null;

        for (String validDay : DAYS) {
            if (validDay.equalsIgnoreCase(day.trim()))
                return validDay;
        }

        return null;
    }
    public static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");
    public static final DateTimeFormatter DISPLAY_TIME_FORMAT = DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH);
    public static String convertTime(String time) {
        if (time == null || time.trim().isEmpty()) {
            UI.print("Time is required.");
            return "";
        }

        String value = time.trim().toUpperCase(Locale.ENGLISH);
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("[h:mm a][hh:mm a]",
                Locale.ENGLISH);

        try {
            LocalTime parsedTime;
            if (value.matches("\\d{2}:\\d{2}"))
                parsedTime = LocalTime.parse(value, TIME_FORMAT);
            else
                parsedTime = LocalTime.parse(value, inputFormat);

            return parsedTime.format(TIME_FORMAT);
        } catch (DateTimeParseException e) {
            UI.print("Invalid time. Use hh:mm AM/PM, such as 02:30 PM.");
            return "";
        }
    }
    public static String formatTime(String time) {

        if (time == null || time.trim().isEmpty())
            return "";

        try {
            return LocalTime.parse(time.trim(), TIME_FORMAT)
                    .format(DISPLAY_TIME_FORMAT);

        } catch (DateTimeParseException e) {
            try {
                return LocalTime.parse(time.trim(), DISPLAY_TIME_FORMAT)
                        .format(DISPLAY_TIME_FORMAT);
            } catch (DateTimeParseException ex) {
                return time;
            }
        }
    }
    public static LocalDate parseDate(String date) {

        if (date == null || date.trim().isEmpty())
            return null;

        try {
            return LocalDate.parse(date.trim(), DATE_FORMAT);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
    public static String fitText(String text, int width) {
        if (text == null || width <= 0) {
            return "";
        }

        if (text.length() > width) {
            if (width <= 3) {
                return text.substring(0, width);
            }

            return text.substring(0, width - 3) + "...";
        }

        return text;
    }
}