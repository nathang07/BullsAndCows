package bullscows;
import java.util.Scanner;
import java.util.Random;

public class Main {
    private static boolean contains(char[] array, char c) {
        for (char ch : array) {
            if (ch == c) {
                return true;
            }
        }
        return false;
    }
    private static final Random RANDOM = new Random();
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // ask the player for the length of the secret code
        System.out.println("Input the length of the secret code: ");
        int length = 0;
        if (scanner.hasNextInt()) {
            length = scanner.nextInt();
        } else {
            String error = scanner.nextLine();
            System.out.println("Error: \"" + error + "\" isn't a valid number.");
            return;
        }
        if (length > 36) {
            System.out.println("Error: length must be 36 or less");
            return;
        }
        if (length == 0) {
            System.out.println("Error: secret code can not have a length of 0");
            return;
        }

        // ask the player for the range of possible characters in the code
        System.out.println("Input the number of possible symbols in the code: "); // 0123456789abcdefghijklmnopqrstuvwxyz
        int range = scanner.nextInt();
        if (range > 36) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            return;
        }

        if (length > range) {
            System.out.println("Error: it's not possible to generate a code with a length of " + length + " with " + range + " unique symbols.");
            return;
        }

        // generate the secret code
        char[] symbols = new char[range]; // __________
        for (int i = 0; i < range; i++) {
            if (i == 10) {
                break;
            }
            symbols[i] = (char)('0' + i);
        }
        for (int i = 10; i < range; i++) {
            symbols[i] = (char)('a' + i - 10);

        }
        char[] code = new char[length];
        for (int i = 0; i < length; i++) {
            char c = symbols[RANDOM.nextInt(symbols.length)];
            if (!contains(code, c)) {
                code[i] = c;
            } else {
                // If the character is already in the code, decrease the counter so that we generate the same
                // number of characters.
                i--;
            }
        }

        // print the secret code and the allowed characters
       System.out.print("The secret is prepared: ");
        for (int i = 0; i < length; i++) {
            System.out.print("*");
        }
        int x = range;
        if (x > 10) {
            System.out.print(" (0-9, a-");
            char letter = (char) ('a' + x - 11);
            System.out.println(letter + ")");
        } else {
            System.out.println(" (0-" + (x-1) + ")");
        }
        // play the game
        int turn = 1;
        System.out.println("Okay, let's start a game!\nTurn " + turn + ":");
        while (true) {
            // prompt the player to guess the code
            String guess = scanner.next();
            if (guess.equals("quit")) {
                break;
            }

            // grade the guess in bulls and cows
            int bulls = 0;
            int cows = 0;
            for (int i = 0; i < length; i++) {
                if (guess.charAt(i) == code[i]) {
                    bulls++;
                } else {
                    for (int j = 0; j < length; j++) {
                        if (guess.charAt(i) == code[j]) {
                            cows++;
                            break;
                        }
                    }
                }
            }

            // print the number of bulls and cows
            System.out.println(bulls + " bulls, " + cows + " cows");
            turn++;
            System.out.println("Turn " + turn + ":");

            // check if the secret code has been guessed
            if (bulls == length)
            {
                System.out.println("Congratulations! You guessed the secret code.");
                break;
            }
        }
    }
}
