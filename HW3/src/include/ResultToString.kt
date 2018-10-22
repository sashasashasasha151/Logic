package include

fun resultToString(current_expression: Result): String {
    return when (current_expression.number) {
        2 -> {
            "(" + resultToString(current_expression.left!!) + current_expression.expression + resultToString(current_expression.right!!) + ")"
        }
        1 -> {
            "!(" + resultToString(current_expression.left!!) + ")"
        }
        else -> {
            current_expression.expression
        }
    }
}