package UI;

public class UI {

    public static final int INNERWIDTH = 70;

    public static void print(String input) {
        System.out.println("\n┌" + "─".repeat(INNERWIDTH) + "┐");
        int left = (INNERWIDTH - input.length()) / 2;
        int right = INNERWIDTH - input.length() - left;
        System.out.println("│" + " ".repeat(left) + input + " ".repeat(right) + "│");
        System.out.println("└" + "─".repeat(INNERWIDTH) + "┘");
    }

    public static void print(String input1, String input2) {
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
        int left = (INNERWIDTH - title.length()) / 2;
        int right = INNERWIDTH - title.length() - left;
        System.out.println("│" + " ".repeat(left) + title + " ".repeat(right) + "│");
        System.out.println("├" + "─".repeat(INNERWIDTH) + "┤");
        System.out.printf("│%-" + INNERWIDTH + "s│%n", " [1] Log in");
        System.out.printf("│%-" + INNERWIDTH + "s│%n", " [2] Sign up");
        System.out.printf("│%-" + INNERWIDTH + "s│%n", " [X] Exit");
        System.out.println("└" + "─".repeat(INNERWIDTH) + "┘");
        System.out.print("  Select > ");
    }

    public static void header (String title) {
        System.out.println("\n┌" + "─".repeat(INNERWIDTH) + "┐");
        int left = (INNERWIDTH - title.length()) / 2;
        int right = INNERWIDTH - title.length() - left;
        System.out.println("│" + " ".repeat(left) + title + " ".repeat(right) + "│");
        System.out.println("├" + "─".repeat(INNERWIDTH) + "┤");
    }

    public static void footer () {
        System.out.println("└" + "─".repeat(INNERWIDTH) + "┘");
    }

    public static void separator() {
        System.out.println("│" + "─".repeat(INNERWIDTH) + "│");
    }

    public static void boxLine(String message) {
        int width = INNERWIDTH;
        while (message.length() > width) {
            int breakAt = message.lastIndexOf(' ', width);

            // If there is no space, break at the width
            if (breakAt <= 0) {
                breakAt = width;
            }
            System.out.printf("│%-" + width + "s│%n",
                    message.substring(0, breakAt));

            message = message.substring(breakAt).trim();
        }
        System.out.printf("│%-" + width + "s│%n", message);
    }

    public static void submitExit(){
        String options = "[/] Submit     [X] Return";
        int left = (INNERWIDTH - options.length()) / 2;
        int right = INNERWIDTH - options.length() - left;
        System.out.println("│" + " ".repeat(left) + options + " ".repeat(right) + "│");
        System.out.println("└" + "─".repeat(INNERWIDTH) + "┘");
    }

    public static void deleteExit(){
        String options = "[/] Delete     [X] Return";
        int left = (INNERWIDTH - options.length()) / 2;
        int right = INNERWIDTH - options.length() - left;
        System.out.println("│" + " ".repeat(left) + options + " ".repeat(right) + "│");
        System.out.println("└" + "─".repeat(INNERWIDTH) + "┘");
    }
}
