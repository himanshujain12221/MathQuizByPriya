package quiz;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class MathQuiz {
    private static final List<Character> operators = Arrays.asList('+', '-', '*', '/');
    private static final int numberOfQuestions = 5;
    private static final int timeLimitPerQuestion = 10; 
    private static final Random random = new Random();

    public static void startQuiz() {
        int score = 0;
        Scanner scanner = new Scanner(System.in);

        System.out.println("***************************************************");
    	System.out.println("Welcome to the Math Quiz! Let's begin!\n");
    	System.out.println("***************************************************\n");
    	System.out.println("You have " + timeLimitPerQuestion + " seconds for each question. All the best!\n");
    	
        for (int i = 0; i < numberOfQuestions; i++) {
            MathQuestions question = generateQuestion();
            System.out.println("***************************************************\n");
            System.out.println("Question " + (i + 1) + ": " + question);

            int answer = getUserAnswerWithTimeout(scanner, timeLimitPerQuestion);

            if (answer != -1) {
                if (answer == question.getAnswer()) {
                    System.out.println("\nCorrect! You've earned 10 points. \n");
                    score += 10;
                } else {
                    System.out.println("Incorrect. The correct answer was " + question.getAnswer()
                            + ". You've lost 5 points.\n");
                    score -= 5;
                }
            } else {
                System.out.println("Time's up for this question! You've lost 5 points. \n");
                score -= 5;
            }
        }

        System.out.println("***************************************************\n");
        System.out.println("                   Game Over!                     ");
        System.out.println("");
        System.out.println("           Your final score is: " + score );
        System.out.println("");
        System.out.println("         Thank you for playing the Math Quiz Game!      ");
        System.out.println(" ");
        System.out.println("***************************************************\n");

    }

    private static int getUserAnswerWithTimeout(Scanner scanner, int timeLimit) {
        Thread userInputThread = new Thread(() -> {
            synchronized (scanner) {
                try {
                    if (scanner.hasNextInt()) {
                        return;
                    } else {
                        scanner.next();
                        System.out.println("Invalid");
                    }
                } catch (InputMismatchException e) {
                    scanner.next();
                    System.out.println("** Invalid input. ****");
                }
            }
        });

        userInputThread.start();

        try {
            userInputThread.join(TimeUnit.SECONDS.toMillis(timeLimit));
            if (userInputThread.isAlive()) {
                userInputThread.interrupt();
                return -1;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return scanner.nextInt();
    }

    private static MathQuestions generateQuestion() {
        int number1 = random.nextInt(10) + 1;
        int number2 = random.nextInt(10) + 1;
        char operator = operators.get(random.nextInt(operators.size()));
        return new MathQuestions(number1, number2, operator);
    }

}
