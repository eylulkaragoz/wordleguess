import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        WordList wordList = new WordList();
        wordList.loadWords("/Users/eylulkaragoz/Downloads/words.txt");
        boolean finished = false;
        Scanner scanner = new Scanner(System.in);
        int guessCount = 0;
        final int MAX_GUESSES = 6;

        while (!finished) {
            if (wordList.getWords().isEmpty()) {
                System.out.println("Due to false feedback, no possible words remain. Exiting.");
                break;
            }

            if (guessCount >= MAX_GUESSES) {
                System.out.println("The game is over! No chances left!");
                break;
            }

            String guess;
            if (guessCount == 0) {
                guess = "salet";
            } else {
                guess = wordList.findBestWord();
            }

            if (guess == null) {
                System.out.println("No valid guesses left!");
                break;
            }

            System.out.println("My guess is: " + guess);
            System.out.println("Enter feedback:");

            String feedback;
            boolean isValid;

            do {
                feedback = scanner.nextLine();
                isValid = isValidFeedback(feedback);

                if (!isValid) {
                    System.out.println("Invalid feedback. Try again.");
                }
            } while (!isValid);

            Feedback actualFeedback = new Feedback(feedback);
            wordList.reduce(new Word(guess), actualFeedback);

            System.out.println("Words remaining: " + wordList.getWords().size());

            guessCount++;

            if (feedback.equalsIgnoreCase("ggggg")) {
                finished = true;
                System.out.println("Correct! The word is: " + guess);
                System.out.println("Congrats! found in " + guessCount + " guesses.");
            }
        }

        scanner.close();
    }

    public static boolean isValidFeedback(String input) {
        if (input.length() != WordleSolver.WORD_SIZE) {
            return false;
        }

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            switch (c) {
                case 'b', 'B', 'y', 'Y', 'g', 'G':
                    break;
                default:
                    return false;
            }
        }

        return true;
    }
}
