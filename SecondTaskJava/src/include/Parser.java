package include;

import java.util.List;

public class Parser {
    private int index = 0;
    private String expression;

    public Parser(String expression) {
        this.expression = expression;
    }

    private Result parsing() {
        return binaryParsing(0);
    }

    private Result binaryParsing(int currentPriority) {
        Result left = expressionsParsing();

        while (true) {
            String op = charParsing();
            int priority = getPriority(op);
            if (priority == 1) {
                if (priority < currentPriority) {
                    index -= op.length();
                    return left;
                }
            } else {
                if (priority <= currentPriority) {
                    index -= op.length();
                    return left;
                }
            }

            Result right = binaryParsing(priority);
            left = new Result(op, left, right);
        }
    }

    private Result expressionsParsing() {
        String currentChar = charParsing();

        if (currentChar.equals("(")) {
            Result result = parsing();
            index++;
            return result;
        }

        if (currentChar.charAt(0) >= 65 && currentChar.charAt(0) <= 90){
            return new Result(currentChar);
        } else {
            return new Result(currentChar, expressionsParsing());
        }
    }

    private String charParsing() {
        if (index == expression.length()) {
            return "";
        }

        if (expression.charAt(index) >= 65 && expression.charAt(index) <= 90) {
            StringBuilder number = new StringBuilder();
            while (index < expression.length() && (expression.charAt(index) >= 65 && expression.charAt(index) <= 90 || expression.charAt(index) >= 48 && expression.charAt(index) <= 57)) {
                number.append(expression.charAt(index++));
            }
            return number.toString();
        }

        String[] operations = {"(", ")", "->", "&", "|", "!"};
        for (String operation : operations) {
            if (operation.charAt(0) == expression.charAt(index)) {
                index += operation.length();
                return operation;
            }
        }
        return "";
    }

    private int getPriority(String operation) {
        if (operation.equals("->")) {
            return 1;
        }
        if (operation.equals("|")) {
            return 2;
        }
        if (operation.equals("&")) {
            return 3;
        }
        if (operation.equals("!")) {
            return 4;
        }
        return 0;
    }

    public Result parse() {
        index = 0;
        return parsing();
    }
}
