package org.presentation;

public class Style {
    static final String RESET = "\u001B[0m";
    static final String GREEN = "\u001B[32m";
    static final String RED = "\u001B[31m";
     static final String CYAN = "\u001B[36m";
     static final String YELLOW = "\u001B[33m";
     static final String GRAY = "\u001B[90m";
     static final String BOLD = "\u001B[1m";

     static void header(String title) {
         System.out.println();
        System.out.println(CYAN + "╔══════════════════════════════════════════════╗");
        System.out.printf("║  %-44s║%n", title);
        System.out.println("╚══════════════════════════════════════════════╝" + RESET);
        System.out.println();
    }
    static  void logo(){
        System.out.println(CYAN + """ 
                 ▗▄▄▖ ▗▄▖ ▗▖  ▗▖▗▄▄▄▖▗▖  ▗▖ ▗▄▄▖ ▗▄▄▖     ▗▄▖ ▗▖  ▗▖ ▗▄▖ ▗▖ ▗▖  ▗▖▗▄▄▖▗▄▄▄▖▗▄▄▖\s
                ▐▌   ▐▌ ▐▌▐▌  ▐▌  █  ▐▛▚▖▐▌▐▌   ▐▌       ▐▌ ▐▌▐▛▚▖▐▌▐▌ ▐▌▐▌  ▝▚▞▘▐▌   ▐▌   ▐▌ ▐▌
                 ▝▀▚▖▐▛▀▜▌▐▌  ▐▌  █  ▐▌ ▝▜▌▐▌▝▜▌ ▝▀▚▖    ▐▛▀▜▌▐▌ ▝▜▌▐▛▀▜▌▐▌   ▐▌  ▝▀▚▖▐▛▀▀▘▐▛▀▚▖
                ▗▄▄▞▘▐▌ ▐▌ ▝▚▞▘ ▗▄█▄▖▐▌  ▐▌▝▚▄▞▘▗▄▄▞▘    ▐▌ ▐▌▐▌  ▐▌▐▌ ▐▌▐▙▄▄▖▐▌ ▗▄▄▞▘▐▙▄▄▖▐▌ ▐▌
                """ + RESET);

    }

     static void success(String message) {
        System.out.println(GREEN + "  ✔ " + message + RESET);
    }

     static void error(String message) {
        System.out.println(RED + "  ✖ " + message + RESET);
    }

     static void info(String message) {
        System.out.println(CYAN + "  ➜ " + message + RESET);
    }

     static void warning(String message) {
        System.out.println(YELLOW + "  ⚠ " + message + RESET);
    }

     static void menuItem(String key, String description) {
        System.out.println("  " + YELLOW + "[" + key + "]" + RESET
                + " " + description);
    }


     static void separator() {
        System.out.println(GRAY + "  ────────────────────────────────────────────" + RESET);
    }




}
