package include;

public class Checker {
    private Result A = null;
    private Result B = null;
    private Result C = null;

    private boolean isEquals(Result left, Result right) {
        if (left == null && right == null) {
            return true;
        }
        if (left.expression.equals(right.expression)) {
            return isEquals(left.left, right.left) && isEquals(left.right, right.right);
        }
        return false;
    }

    public boolean checker(Result axiom, Result expression) {
        switch (axiom.expression) {
            case "A":
                if (A == null) {
                    A = expression;
                    return true;
                } else {
                    return isEquals(A, expression);
                }
            case "B":
                if (B == null) {
                    B = expression;
                    return true;
                } else {
                    return isEquals(B, expression);
                }
            case "C":
                if (C == null) {
                    C = expression;
                    return true;
                } else {
                    return isEquals(C, expression);
                }
            case "!":
                if (expression.expression.equals("!")) {
                    return checker(axiom.left, expression.left);
                } else {
                    return false;
                }
            default:
                break;
        }
        if (axiom.expression.equals(expression.expression)) {
            return checker(axiom.left, expression.left) && checker(axiom.right, expression.right);
        } else {
            return false;
        }
    }
}
