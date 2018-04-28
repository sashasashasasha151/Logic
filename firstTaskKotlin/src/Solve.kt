import include.Checker
import java.util.*

import include.Parser
import include.Result
import java.io.File
import java.util.stream.Collectors
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.collections.HashMap


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
                sb.append("!(").append(makeResult(current_expression.left!!)).append(")")
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

//        val path = Paths.get("input.txt")
        val path = Paths.get("src/in.txt")
        val list = Files
                .lines(path)
                .filter { !it.isEmpty() }
                .collect(Collectors.toList());

        val inputString = list[0].split("|-")

        var assIndex = 1
        val assumptions: HashMap<String, Int> = hashMapOf()

        if (inputString[0] != "") {
            inputString[0].split(",")
                    .forEach { assumptions[makeResult(Parser(it).parse())] = assIndex++ }
        }
        var i = 1
        val printed = mutableListOf<Result>()
        val hashed = hashMapOf<String, Int>()
        val hashedH = hashMapOf<String, MutableList<Int>>()
        val answers = mutableListOf<String>()

        for (h in 1 until list.size) {
            var s = list[h]
            val newAssumption = Parser(s).parse()
            s = makeResult(newAssumption)
            var isAssum = false


            if (assumptions.containsKey(s)) {
                answers.add("(${i++}) $s (Предп. ${assumptions[s]})")
                isAssum = true
            }


            if (isAssum) {
                printed.add(newAssumption)
                hashed[s] = i - 1
                if (newAssumption.expression == "->") {
                    val ss = makeResult(newAssumption.right)
                    if (hashedH.containsKey(ss)) {
                        hashedH[ss]!!.add(i - 1)
                    } else {
                        hashedH[ss] = mutableListOf(i - 1)
                    }
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
                hashed[s] = i - 1
                if (newAssumption.expression == "->") {
                    val ss = makeResult(newAssumption.right)
                    if (hashedH.containsKey(ss)) {
                        hashedH[ss]!!.add(i - 1)
                    } else {
                        hashedH[ss] = mutableListOf(i - 1)
                    }
                }
                continue
            }

            if (hashedH.containsKey(s)) {
                for (l in hashedH[s]!!) {
                    val dell = makeResult(printed[l - 1].left)
                    if (hashed.containsKey(dell)) {
                        answers.add("(${i++}) $s (M.P. ${l}, ${hashed[dell]})")
                        isAssum = true
                        break
                    }
                }
            }

            if (isAssum) {
                printed.add(newAssumption)
                hashed[s] = i - 1
                if (newAssumption.expression == "->") {
                    val ss = makeResult(newAssumption.right)
                    if (hashedH.containsKey(ss)) {
                        hashedH[ss]!!.add(i - 1)
                    } else {
                        hashedH[ss] = mutableListOf(i - 1)
                    }
                }
                continue
            }

            answers.add("(${i++}) $s (Не доказано)")

            printed.add(newAssumption)
            hashed[s] = i - 1
            if (newAssumption.expression == "->") {
                val ss = makeResult(newAssumption.right)
                if (hashedH.containsKey(ss)) {
                    hashedH[ss]!!.add(i - 1)
                } else {
                    hashedH[ss] = mutableListOf(i - 1)
                }
            }
        }

//        for (t in 0 until answers.size) {
//            println(answers[t]);
//        }
        Files.newBufferedWriter(Paths.get("output.txt")).use { bw ->
            for (t in 0 until answers.size) {
                bw.append(answers[t])
                bw.append("\n")
            }
        }
    }
}