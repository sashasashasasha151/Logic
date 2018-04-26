import include.Checker
import java.util.*

import include.Parser
import include.Result
import java.io.File
import java.util.stream.Collectors
import java.nio.file.Files
import java.nio.file.Paths



class Solve {
    private val axioms = mutableListOf<Result>()

    private fun isEquals(left: Result?, right: Result?): Boolean {
        if (left == null && right == null) {
            return true
        }
        if (left?.expression == right?.expression) {
            return isEquals(left?.left, right?.left) && isEquals(left?.right, right?.right)
        }
        return false
    }

    private fun makeResult(current_expression: Result?): String {
        val sb = StringBuilder()
        when (current_expression?.number) {
            2 -> {
                sb.append("(")
                        .append(makeResult(current_expression.left!!))
                        .append(current_expression.expression)
                        .append(makeResult(current_expression.right!!))
                        .append(")")
            }
            1 -> {
                sb.append("!").append(makeResult(current_expression.left!!)).append(")")
            }
            0 -> {
                return current_expression.expression
            }
            else -> {
                return ""
            }
        }
        return sb.toString()
    }

    fun main() {
        val input = Scanner(File("src/include/axioms.txt"))
        for (i in 0..9) {
            axioms.add(Parser(input.nextLine()).parse())
        }

        val path = Paths.get("input.txt")
        //val path = Paths.get("src/in.txt")
        val list = Files
                .lines(path)
                .filter(fun(s) = !s.isEmpty())
                .collect(Collectors.toList());

        val inputString = list[0].split("|-")
        val assumptions = if (inputString[0] == "") mutableListOf<Result>() else
            inputString[0].split(",")
                    .stream()
                    .map(fun(p) = Parser(p).parse())
                    .collect(Collectors.toList<Result>())
        var i = 1
        val printed = mutableListOf<Result>()
        val hashed = hashMapOf<String, Int>()
        val hashedH = hashMapOf<String, Int>()
        val answers = mutableListOf<String>()
        for (h in 1 until list.size) {
            val s = list[h]
            val newAssumption = Parser(s).parse()
            var isAssum = false

            for (j in 0 until assumptions.size) {
                if (isEquals(newAssumption, assumptions[j])) {
                    answers.add("(${i++}) $s (Предп. ${j + 1})")
                    isAssum = true
                    break
                }
            }

            if (isAssum) {
                printed.add(newAssumption)
                hashed[makeResult(newAssumption)] = i - 1
                if (newAssumption.expression == "->") {
                    hashedH[makeResult(newAssumption.right)] = i - 1
                }
                continue
            }

            for (j in 0..9) {
                val checker = Checker()
                if (checker.checker(axioms[j], newAssumption)) {
                    answers.add("(${i++}) $s (Сх. акс. ${j + 1})")
                    isAssum = true
                    break
                }
            }

            if (isAssum) {
                printed.add(newAssumption)
                hashed[makeResult(newAssumption)] = i - 1
                if (newAssumption.expression == "->") {
                    hashedH[makeResult(newAssumption.right)] = i - 1
                }
                continue
            }

            val del = makeResult(newAssumption)
            if (hashedH.containsKey(del)) {
                val dell = makeResult(printed[hashedH[del]!! - 1].left)
                if (hashed.containsKey(dell)) {
                    answers.add("(${i++}) $s (M.P. ${hashedH[del]}, ${hashed[dell]})")
                    isAssum = true
                }
            }

            if (isAssum) {
                printed.add(newAssumption)
                hashed[makeResult(newAssumption)] = i - 1
                if (newAssumption.expression == "->") {
                    hashedH[makeResult(newAssumption.right)] = i - 1
                }
                continue
            }

            answers.add("(${i++}) $s (Не доказано)")
            printed.add(newAssumption)
            hashed[makeResult(newAssumption)] = i - 1
            if (newAssumption.expression == "->") {
                hashedH[makeResult(newAssumption.right)] = i - 1
            }
        }

        for (t in 0 until answers.size) {
            println(answers[t]);
        }

//        Files.newBufferedWriter(Paths.get("output.txt")).use { bw ->
//            for (t in 0 until answers.size) {
//                bw.append(answers[t])
//                bw.append("\n")
//            }
//        }
    }
}