package include;

public class Result {
    public String expression = null;
    public Result left = null;
    public Result right = null;
    public int number = 0;

    public Result(String expression) {
        this.expression = expression;
    }

    public Result(String expression, Result a) {
        this.expression = expression;
        left = a;
        number = 1;
    }

    public Result(String expression, Result a, Result b) {
        this.expression = expression;
        left = a;
        right = b;
        number = 2;
    }
}