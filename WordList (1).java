import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class WordList {

    private ArrayList<String> words;

    public WordList() {
        this.words = new ArrayList<>();
    }

    public void loadWords(String path) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                this.words.add(line.trim().toLowerCase());
            }
        }
    }

    public ArrayList<String> getWords() {
        return this.words;
    }

    public void reduce(Word guess, Feedback feedback) {
        ArrayList<String> newWords = new ArrayList<>();

        for (String wordString : this.words) {
            Word word = new Word(wordString);
            Feedback feedbackForWord = guess.generateFeedbackWithActualWord(word);

            if (feedbackForWord.equals(feedback)) {
                newWords.add(wordString);
            }
        }

        this.words = newWords;
    }

    public String findBestWord() {
        if (this.words.isEmpty()) { // No valid guesses left, return null, game over
            return null;
        }

        Map<Character, Integer> totalFrequency = new HashMap<>();// Total frequency of each character in the word list
        Map<Character, int[]> positionFrequency = new HashMap<>();// Frequency of each character in each position
        final int WORD_SIZE = WordleSolver.WORD_SIZE;// Word size is 5

        for (String word : this.words) {// Iterate over the words in the list
            for (int i = 0; i < WORD_SIZE; i++) {// Iterate over the characters in the word
                char c = word.charAt(i);// Get the character at the current position
                totalFrequency.put(c, totalFrequency.getOrDefault(c, 0) + 1);// Increment the total frequency of the character, if it doesn't exist, set it to 1
                positionFrequency.putIfAbsent(c, new int[WORD_SIZE]);// Initialize the position frequency array for the character, if it doesn't exist yet
                positionFrequency.get(c)[i]++;// Increment the frequency of the character at the current position
            }
        }

        String bestWord = null;// Initialize the best word to null
        double maxScore = -1;// Initialize the maximum score to -1

        for (String word : this.words) {// Iterate over the words in the list
            double score = 0;// Initialize the score to 0
            Set<Character> uniqueChars = new HashSet<>();// Set to store unique characters in the word

            for (int i = 0; i < WORD_SIZE; i++) {// Iterate over the characters in the word
                char c = word.charAt(i);// Get the character at the current position
                score += positionFrequency.get(c)[i];// Add the frequency of the character at the current position to the score
                score += totalFrequency.get(c);// Add the total frequency of the character to the score
                if (uniqueChars.add(c)) {// If the character is unique, add to the score because we don't want repeated characters too much in the word
                    score += totalFrequency.get(c) * 8; // Prioritize unique characters,used 8 to give more weight to unique characters but we can use another value too.
                } else {
                    score -= 1;// Penalize repeated characters because it creates disadvantage in getting information about the word
                }
            }

            if (score > maxScore) {// If the current score is greater than the maximum score
                maxScore = score;// Update the maximum score
                bestWord = word;// Update the best word
            }
        }

        return bestWord;// Return the best word
    }
}
