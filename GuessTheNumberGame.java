import java.util.Random;
import java.util.Scanner;

public class GuessTheNumberGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        int min = 1;
        int max = 100;
        int maxAttempts = 10;
        int rounds = 3;
        int totalScore = 0;

        System.out.println("Welcome to the Guess the Number Game!");

        for (int round = 1; round <= rounds; round++) {
            System.out.println("Round " + round + " of " + rounds);
            int targetNumber = random.nextInt(max - min + 1) + min;
            boolean guessedCorrectly = false;

            for (int attempt = 1; attempt <= maxAttempts; attempt++) {
                System.out.println("Attempt " + attempt + " of " + maxAttempts);
                System.out.print("Enter your guess: ");
                int userGuess = scanner.nextInt();

                if (userGuess == targetNumber) {
                    System.out.println("Congratulations! You guessed the number.");
                    totalScore += (maxAttempts - attempt + 1) * 10; // Points based on the number of attempts left
                    guessedCorrectly = true;
                    break;
                } else if (userGuess < targetNumber) {
                    System.out.println("The number is higher.");
                } else {
                    System.out.println("The number is lower.");
                }
            }

            if (!guessedCorrectly) {
                System.out.println("Sorry! You've used all your attempts. The number was " + targetNumber);
            }
            System.out.println("Your score for this round: " + totalScore);
            System.out.println();
        }

        System.out.println("Game Over! Your total score is: " + totalScore);
        scanner.close();
    }
}
