import include.Parser

import java.util.Scanner

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val `in` = Scanner(System.`in`)
        val str = `in`.nextLine()
        val parser = Parser(str)
        parser.parse()
    }
}
