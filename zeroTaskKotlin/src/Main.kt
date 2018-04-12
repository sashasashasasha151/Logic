import include.Parser
import include.Result

import java.util.Scanner

object Main {

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

    @JvmStatic
    fun main(args: Array<String>) {
        val `in` = Scanner(System.`in`)
        val str = `in`.nextLine()
        val parser = Parser(str)
        makeResult(parser.parse())
    }
}
