import javax.swing.*;
import java.util.*;

public class Wisielec {
    private static final List<String> words = Arrays.asList("MADRYT", "BARCELONA", "MEDIOLAN", "MONACHIUM", "AMSTERDAM",
            "WENECJA", "PARYŻ"); //lista do przechowywania dostępnych haseł.
    private static final String[] gallows = {
            " _______\n" +
                    " |     |\n" +
                    " |\n" +
                    " |\n" +
                    " |\n" +
                    " |\n" +
                    "_|_",

            " _______\n" +
                    " |     |\n" +
                    " |     O\n" +
                    " |\n" +
                    " |\n" +
                    " |\n" +
                    "_|_",

            " _______\n" +
                    " |     |\n" +
                    " |     O\n" +
                    " |     |\n" +
                    " |\n" +
                    " |\n" +
                    "_|_",

            " _______\n" +
                    " |     |\n" +
                    " |     O\n" +
                    " |    /|\n" +
                    " |\n" +
                    " |\n" +
                    "_|_",

            " _______\n" +
                    " |     |\n" +
                    " |     O\n" +
                    " |    /|\\\n" +
                    " |\n" +
                    " |\n" +
                    "_|_",

            " _______\n" +
                    " |     |\n" +
                    " |     O\n" +
                    " |    /|\\\n" +
                    " |    /\n" +
                    " |\n" +
                    "_|_",

            " _______\n" +
                    " |     |\n" +
                    " |     O\n" +
                    " |    /|\\\n" +
                    " |    / \\\n" +
                    " |\n" +
                    "_|_"
    };
    private static final int maxAttempts = 7; // stała określająca maksymalną liczbę prób

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        greetPlayer();

        String password = selectWord(scanner);
        Set<Character> usedLetters = new HashSet<>();
        StringBuilder hiddenPassword = createHiddenPassword(password);

        int faults = playGame(scanner, usedLetters, password, hiddenPassword);
        displayResults(faults, password);
    }

    private static void greetPlayer() {
        System.out.println("Czołem! Zapraszam do gry w Wisielca.");
    }

    private static String selectWord(Scanner scanner) {
        int choice;
        do {
            System.out.println("Wybierz liczbę od 1 do " + words.size() + ", aby wylosować hasło do odgadnięcia: ");
            choice = scanner.nextInt();
        } while (choice < 1 || choice > words.size());
        return words.get(choice - 1);
    }

    private static StringBuilder createHiddenPassword(String password) {
        StringBuilder hiddenPassword = new StringBuilder();
        for (int i = 0; i < password.length(); i++) {
            hiddenPassword.append("_ ");//Zastąpienie litery znakami "_".
        }
        return hiddenPassword;
    }

    private static int playGame(Scanner scanner, Set<Character> usedLetters, String password, StringBuilder hiddenPassword) {
        int faults = 0;
        while (faults < maxAttempts && hiddenPassword.indexOf("_") != -1) {
            System.out.println("Aktualny stan: " + hiddenPassword);
            char letter = getInputLetter(scanner, usedLetters);

            if (password.indexOf(letter) == -1) {
                faults++;
                printGallows(faults);
            } else {
                updateHiddenPassword(password, hiddenPassword, letter);
            }
        }
        return faults;
    }

    private static char getInputLetter(Scanner scanner, Set<Character> usedLetters) {
        char letter;
        while (true) {
            System.out.println("Podaj literę: ");
            letter = scanner.next().toUpperCase().charAt(0);
            if (isValidLetter(letter) && !isLetterUsed(usedLetters, letter)) {
                usedLetters.add(letter);
                return letter;
            }
        }
    }


    private static boolean isValidLetter(char letter) {
        if (!Character.isLetter(letter)) {
            System.out.println("Wprowadzono znak zamiast liter! Spróbój ponownie.");
            return false;
        }
        return true;
    }

    private static boolean isLetterUsed(Set<Character> usedLetters, char letter) {
        if (usedLetters.contains(letter)) {
            System.out.println("Ta litera została już użyta!");
            return true;
        }
        return false;
    }

    private static void updateHiddenPassword(String password, StringBuilder hiddenPassword, char letter) {
        for (int i = 0; i < password.length(); i++) {
            if (password.charAt(i) == letter) hiddenPassword.setCharAt(i * 2, letter);
        }
    }

    private static void displayResults(int faults, String password) {
        if (faults == maxAttempts)
            System.out.println("Przegrałeś, wykorzystałeś wszystkie próby. Prawidłowe hasło to: " + password);
        else System.out.println("Brawo! Odgadłeś hasło: " + password);
    }

    private static void printGallows(int faults) {
        System.out.println(gallows[faults - 1]);
    }
}
