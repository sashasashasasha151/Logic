package include

class Parser(private val expression: String) {
    private var index = 0

    private fun makeResult(current_expression: Result) {
        when (current_expression.number) {
            2 -> {
                print("(" + current_expression.expression + ",")
                makeResult(current_expression.left!!)
                print(",")
                makeResult(current_expression.right!!)
                print(")")
            }
            1 -> {
                print("(!")
                makeResult(current_expression.left!!)
                print(")")
            }
            0 -> {
                print(current_expression.expression)
            }
            else -> {
            }
        }
    }

    private fun parsing(): Result {
        return binaryParsing(0)
    }

    private fun binaryParsing(current_priority: Int): Result {
        var left = expressionsParsing()

        while (true) {
            val op = charParsing()
            val priority = getPriority(op)
            if (priority == 1) {
                if (priority < current_priority) {
                    index -= op.length
                    return left
                }
            } else {
                if (priority <= current_priority) {
                    index -= op.length
                    return left
                }
            }

            val right = binaryParsing(priority)
            left = Result(op, left, right)
        }
    }

    private fun expressionsParsing(): Result {
        val currentChar = charParsing()

        if (currentChar == "(") {
            val result = parsing()
            index++
            return result
        }

        return if (currentChar[0].toInt() in 65..90) {
            Result(currentChar)
        } else Result(currentChar, expressionsParsing())

    }

    private fun charParsing(): String {

        if (index == expression.length) {
            return ""
        }

        if (expression[index].toInt() in 65..90) {
            val number = StringBuilder()
            while (index < expression.length && (expression[index].toInt() in 65..90 || expression[index].toInt() in 48..57)) {
                number.append(expression[index++])
            }
            return number.toString()
        }

        val operations = arrayOf("(", ")", "->", "&", "|", "!")
        for (operation in operations) {
            if (operation[0] == expression[index]) {
                index += operation.length
                return operation
            }
        }
        return ""
    }

    private fun getPriority(operation: String): Int {
        if (operation == "->") {
            return 1
        }
        if (operation == "|") {
            return 2
        }
        if (operation == "&") {
            return 3
        }
        return if (operation == "!") {
            4
        } else 0
    }

    fun parse() {
        index = 0
        makeResult(parsing())
    }
}
