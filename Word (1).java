import java.util.Arrays;

public class Word {
    private String letters;
    public static final char GREEN = 'G';
    public static final char YELLOW = 'Y';
    public static final char BLACK = 'B';

    public Word(String letters) {
        this.letters = letters;
    }

    public Feedback generateFeedbackWithActualWord(Word actualWord) {
        char[] feedback = new char[WordleSolver.WORD_SIZE];

        for (int i = 0; i < feedback.length; i++) {
            feedback[i] = 'B';
        }

        char[] actualLetters = actualWord.letters.toCharArray();

        for (int i = 0; i < this.letters.length(); i++) {
            if (this.letters.charAt(i) == actualLetters[i]) {
                feedback[i] = 'G';
                actualLetters[i] = ' ';
            }
        }

        for (int i = 0; i < this.letters.length(); i++) {
            char c = this.letters.charAt(i);

            for (int j = 0; j < actualLetters.length; j++) {
                if (c == actualLetters[j]) {
                    feedback[i] = 'Y';
                    actualLetters[j] = ' ';
                }
            }
        }
        return new Feedback(new String(feedback));
    }

}

