package se.funkabo.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import se.funkabo.utils.PostFixOperation;

/**
 * ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
 * ┃                            Exercise 1                        ┃
 * ┃      title: Postfix Calculator                               ┃
 * ┃    version: 1.0                                              ┃
 * ┃     author: Federico Sanders <federico.sanders@hotmail.com>  ┃
 * ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛
 */

public class Calculator {

    private static String INFORMATION = "src/main/resources/information";
    private static String BANNER = "src/main/resources/banner";
    private static String MENU = "src/main/resources/menu";
    private static String INPUT_PROMPT = "  \tINPUT:~$ ";
    private static String CLEAR_SCREEN = "\033\143";
    private static boolean isRunning = true;

    private Calculator() {}

    public static Calculator instance;

    public static synchronized Calculator init() {
        return instance == null
                ? instance = new Calculator()
                : instance;
    }


    public static void run() throws IOException {
       do { menu();
       } while (isRunning);
    }

    private static void menu() throws IOException {
        do { readFile(BANNER);
            readFile(MENU);
            System.out.print(INPUT_PROMPT);
            String[] choice = readOption();
            switch (choice[0]){
                case "1": calculation();
                    break;
                case "2": information();
                    break;
                case "3": exit();
                    break;
            }
        } while (isRunning);

    }

    private static String[] readOption() throws IOException {
        String[] choice = readFromConsole();
        return isOptionValid(choice[0]) ? choice : null;
    }

    private static String[] readFromConsole() throws IOException {
        String readLine;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            while ((readLine = reader.readLine()) != null) break;
        List<String> input = new ArrayList<>();
        CharSequence sequence = readLine.trim();
        Matcher matcher = Pattern.compile("(([0-9]+)([0-9.]+)?|([-+*/()^%]))").matcher(sequence);
            while (matcher.find()) input.add(matcher.group(1));
        return input.stream().toArray(String[]::new);
    }

    private static void readFile(String filepath) throws IOException {
        Files.lines(Paths.get(filepath)).forEach(System.out::println);
    }

    private static boolean isNull(String input){
        return input.length() == 0;
    }

    private static boolean isOptionValid(String input) {
        return !isNull(input) && input.length() == 1
                && input.matches("1")
                || input.matches("2")
                || input.matches("3");
    }

    private static void calculation() throws IOException {
        System.out.print(CLEAR_SCREEN);
        System.out.print("\n\n");
        readFile(BANNER);
        System.out.print(INPUT_PROMPT);
        String[] choice = readFromConsole();
        PostFixOperation.run(choice);
        isRunning = false;
    }

    private static void information() throws IOException {
        boolean showInformation = true;
        do { System.out.print(CLEAR_SCREEN);
             readFile(INFORMATION);
             System.out.print(INPUT_PROMPT);
             String[] choice = readOption();
             if(choice[0].equals("3"))
                 showInformation = false;
        } while (showInformation);
        System.out.print(CLEAR_SCREEN);
        System.out.print("\n\n");
        run();
    }

    private static void exit() throws IOException {
        isRunning = false;
    }
}
