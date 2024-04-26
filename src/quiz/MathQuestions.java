package quiz;

class MathQuestions {
    private final int number1;
    private final int number2;
    private final char operator;

    public MathQuestions(int number1, int number2, char operator) {
        this.number1 = number1;
        this.number2 = number2;
        this.operator = operator;
    }

    public int getAnswer() {
        switch (operator) {
            case '+':
                return number1 + number2;
            case '-':
                return number1 - number2;
            case '*':
                return number1 * number2;
            case '/':
                if (number2 == 0) {
                    throw new ArithmeticException("Division by zero is not allowed.");
                }
                return number1 / number2;
            default:
                throw new IllegalArgumentException("Invalid operator.");
        }
    }

    public String toString() {
        return "What is " + number1 + " " + operator + " " + number2 + "?";
    }
}
