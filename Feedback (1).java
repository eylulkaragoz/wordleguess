
    import java.util.Arrays;

    public class Feedback {
        public static final int BLACK = 0;
        public static final int YELLOW = 1;
        public static final int GREEN = 2;

        private int[] feedback;

        public Feedback(String userInput) {
            if (userInput.length() != WordleSolver.WORD_SIZE) {
                throw new IllegalArgumentException("Argument to construct Feedback object has incorrect length.");
            }

            this.feedback = new int[WordleSolver.WORD_SIZE];

            for (int i = 0; i < userInput.length(); i++) {
                char c = userInput.charAt(i);

                switch (c) {
                    case 'b', 'B' -> this.feedback[i] = BLACK;
                    case 'y', 'Y' -> this.feedback[i] = YELLOW;
                    case 'g', 'G' -> this.feedback[i] = GREEN;
                    default -> throw new IllegalArgumentException("Feedback contains bad letters.");
                }
            }
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Feedback) {
                Feedback other = (Feedback) obj;
                return Arrays.equals(this.feedback, other.feedback);
            } else {
                return false;
            }
        }

        @Override
        public String toString() {
            return Arrays.toString(this.feedback);
        }
    }









