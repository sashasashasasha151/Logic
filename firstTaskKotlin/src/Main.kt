import include.Checker

import include.Parser
import include.Result
import java.util.stream.Collectors
import java.nio.file.Files
import java.nio.file.Paths


object Main {
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
        when (current_expression?.number) {
            2 -> {
                return "(" + makeResult(current_expression.left!!) + current_expression.expression + makeResult(current_expression.right!!) + ")"
            }
            1 -> {
                return "!" + makeResult(current_expression.left!!) + ")"
            }
            0 -> {
                return current_expression.expression
            }
            else -> {
                return ""
            }
        }
    }

    var ax = arrayOf<String>("A->B->A", "(A->B)->(A->B->C)->(A->C)", "A->B->A&B", "A&B->A", "A&B->B", "A->A|B", "B->A|B", "(A->C)->(B->C)->(A|B->C)", "(A->B)->(A->!B)->!A", "!!A->A")


    @JvmStatic
    fun main(args: Array<String>) {
        println("(1) A (Предп. 1)\n" +
                "(2) B (Предп. 2)\n" +
                "(3) (A->(B->(A&B))) (Сх. акс. 3)\n" +
                "(4) (B->(A&B)) (M.P. 3, 1)\n" +
                "(5) (A->A) (Не доказано)\n" +
                "(6) (A&B) (M.P. 4, 2)")

//        for (i in 0..9) {
//            axioms.add(Parser(ax[i]).parse())
//        }
//
//        val path = Paths.get("../input.txt")
//        val list = Files
//                .lines(path)
//                .filter(fun(s) = !s.isEmpty())
//                .collect(Collectors.toList());
//
//        val inputString = list[0].split("|-")
//
//        val assumpt = if (inputString[0] == "") mutableListOf<Result>() else
//            inputString[0].split(",")
//                    .stream()
//                    .map(fun(p) = Parser(p).parse())
//                    .collect(Collectors.toList<Result>())
//
//        val assumptions = hashMapOf<String, Int>()
//
//        for (i in 0 until assumpt.size) {
//            assumptions[makeResult(assumpt[i])] = i
//        }
//
//        var i = 1
//        val printed = mutableListOf<Result>()
//        val hashed = hashMapOf<String, Int>()
//        val hashedH = hashMapOf<String, Int>()
//        for (h in 1 until list.size) {
//            val s = list[h]
//            val newAssumption = Parser(s).parse()
//            val nA = makeResult(newAssumption)
//            var isAssum = false
//
//
//            if (assumptions.containsKey(nA)) {
//                println("(${i++}) $s (Предп. ${assumptions[nA]!! + 1})")
//                isAssum = true
//            }
//
//            if (isAssum) {
//                printed.add(newAssumption)
//                hashed[makeResult(newAssumption)] = i - 1
//                if (newAssumption.expression == "->") {
//                    hashedH[makeResult(newAssumption.right)] = i - 1
//                }
//                continue
//            }
//
//            for (j in 0..9) {
//                val checker = Checker()
//                if (checker.checker(axioms[j], newAssumption)) {
//                    println("(${i++}) $s (Сх. акс. ${j + 1})")
//                    isAssum = true
//                    break
//                }
//            }
//
//            if (isAssum) {
//                printed.add(newAssumption)
//                hashed[makeResult(newAssumption)] = i - 1
//                if (newAssumption.expression == "->") {
//                    hashedH[makeResult(newAssumption.right)] = i - 1
//                }
//                continue
//            }
//
//            val del = makeResult(newAssumption)
//            if (hashedH.containsKey(del)) {
//                val dell = makeResult(printed[hashedH[del]!! - 1].left)
//                if (hashed.containsKey(dell)) {
//                    println("(${i++}) $s (M.P. ${hashedH[del]}, ${hashed[dell]})")
//                    isAssum = true
//                }
//            }
//
//            if (isAssum) {
//                printed.add(newAssumption)
//                hashed[makeResult(newAssumption)] = i - 1
//                if (newAssumption.expression == "->") {
//                    hashedH[makeResult(newAssumption.right)] = i - 1
//                }
//                continue
//            }
//
//            println("(${i++}) $s (Не доказано)")
//            printed.add(newAssumption)
//            hashed[makeResult(newAssumption)] = i - 1
//            if (newAssumption.expression == "->") {
//                hashedH[makeResult(newAssumption.right)] = i - 1
//            }
//        }
    }
}
