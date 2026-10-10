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

    public static void footer() {
        System.out.println("└" + "─".repeat(INNERWIDTH) + "┘");
    }

    public static void separator() {
        System.out.println("├" + "─".repeat(INNERWIDTH) + "┤");
    }

    public static void manageUI(String manageText) {
        UI.header("MANAGE " + manageText.toUpperCase());
        System.out.printf("│%-" + INNERWIDTH + "s│%n", " [1] Create New " + manageText);
        System.out.printf("│%-" + INNERWIDTH + "s│%n", " [2] Update Existing " + manageText);
        System.out.printf("│%-" + INNERWIDTH + "s│%n", " [3] Delete Existing " + manageText);
        System.out.printf("│%-" + INNERWIDTH + "s│%n", " [4] View All " + manageText);
        System.out.printf("│%-" + INNERWIDTH + "s│%n", " ");
        System.out.printf("│%-" + INNERWIDTH + "s│%n", " [0] Back");
        UI.footer();
    }

    public static void option() {
        String options = "[/] Submit     [X] Return";
        int left = (INNERWIDTH - options.length()) / 2;
        int right = INNERWIDTH - options.length() - left;
        System.out.println("│" + " ".repeat(left) + options + " ".repeat(right) + "│");
    }
}
