package include;

public class Parser {

    private String expression;
    private int index = 0;

    private void make_result(Result current_expression) {
        switch (current_expression.number) {
            case 2: {
                System.out.print("(" + current_expression.expression + ",");
                make_result(current_expression.left);
                System.out.print(",");
                make_result(current_expression.right);
                System.out.print(")");
                break;
            }
            case 1: {
                System.out.print("(!");
                make_result(current_expression.left);
                System.out.print(")");
                break;
            }
            case 0: {
                System.out.print(current_expression.expression);
                break;
            }
            default:
                break;
        }
    }

    private Result parsing() {
        return binary_parsing(0);
    }

    private Result binary_parsing(int current_priority) {
        Result left = expressions_parsing();

        while (true) {
            String op = char_parsing();
            int priority = get_priority(op);
            if (priority == 1) {
                if (priority < current_priority) {
                    index -= op.length();
                    return left;
                }
            } else {
                if (priority <= current_priority) {
                    index -= op.length();
                    return left;
                }
            }

            Result right = binary_parsing(priority);
            left = new Result(op, left, right);
        }
    }

    private Result expressions_parsing() {
        String currentChar = char_parsing();

        if (currentChar.equals("(")) {
            Result  result = parsing();
            index++;
            return result;
        }

        if (currentChar.charAt(0) >= 65 && currentChar.charAt(0) <= 90) {
            return new Result(currentChar);
        }

        return new Result(currentChar, expressions_parsing());
    }

    private String char_parsing() {

        if (index == expression.length()) {
            return "";
        }

        if (expression.charAt(index) >= 65 && expression.charAt(index) <= 90) {
            StringBuilder number = new StringBuilder();
            while (index < expression.length() && ((expression.charAt(index) >= 65 && expression.charAt(index) <= 90) ||
                    (expression.charAt(index) >= 48 && expression.charAt(index) <= 57))) {
                number.append(expression.charAt(index++));
            }
            return number.toString();
        }

        String[] operations = {"(", ")", "->", "&", "|", "!"};
        for (String operation :operations){
            if (operation.charAt(0) == expression.charAt(index)) {
                index += operation.length();
                return operation;
            }
        }
        return "";
    }

    private int get_priority(String operation) {
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

    public Parser(String expression) {
        this.expression = expression;
    }

    ;

    public void parse() {
        index = 0;
        make_result(parsing());
    }
}
