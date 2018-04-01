import include.Checker
import java.util.*

import include.Parser
import include.Result
import java.io.File
import java.util.stream.Collectors

object Main {
    private val axioms = mutableListOf<Result>()

    private fun isEquals(left: Result?, right: Result?): Boolean {
        if (left == null || right == null) {
            return true
        } else {
            if (left.expression == right.expression) {
                return isEquals(left.left, right.left) && isEquals(left.right, right.right)
            }
        }
        return false
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val input = Scanner(File("src/include/axioms.txt"))
        for (i in 0..9) {
            axioms.add(Parser(input.nextLine()).parse())
        }
//        for (i in axioms) {
//            makeResult(i)
//            println()
//        }
        //val `in` = Scanner(System.`in`)
        val `in` = Scanner(File("src/input.txt"))
        val inputString = `in`.nextLine().split("|-")
        val terminate = Parser(inputString[1]).parse()
        val assumptions = if (inputString[0] == "") mutableListOf<Result>() else
            inputString[0].split(",").stream().map(fun(p) = Parser(p).parse()).collect(Collectors.toList<Result>())
        var i = 1
//        for (assumption in assumptions) {
//            print("($i) " )
//            makeResult(assumption)
//            println(" (Предп. ${i++})")
//        }
        val printed = mutableListOf<Result>()
        while (true) {
            val s = `in`.nextLine()
            val newAssumption = Parser(s).parse()
            var isAssum = false

            for (j in 0 until assumptions.size) {
                if (isEquals(newAssumption, assumptions[j])) {
                    println("(${i++}) $s (Предп. ${j + 1})")
                    isAssum = true
                    break
                }
            }

            if (isAssum) {
                if(isEquals(newAssumption,terminate)) {
                    break
                }
                printed.add(newAssumption)
                continue
            }

            for (j in 0..9) {
                val checker = Checker()
                if (checker.checker(axioms[j], newAssumption)) {
                    println("(${i++}) $s (Сх. акс. ${j + 1})")
                    isAssum = true
                    break
                }
            }

            if (isAssum) {
                if(isEquals(newAssumption,terminate)) {
                    break
                }
                printed.add(newAssumption)
                continue
            }

            for (j in printed.size-1 downTo 0) {
                if(printed[j].expression == "->" && isEquals(newAssumption,printed[j].right)) {
                    for(k in printed.size-1 downTo 0) {
                        if(isEquals(printed[k],printed[j].left)) {
                            println("(${i++}) $s (M.P. ${j+1}, ${k+1})")
                            isAssum = true
                            break
                        }
                    }
                    if(isAssum) {
                        break
                    }
                }
            }

            if (isAssum) {
                if(isEquals(newAssumption,terminate)) {
                    break
                }
                printed.add(newAssumption)
                continue
            }

            println("(${i++}) $s (Не доказано)")
            if(isEquals(newAssumption,terminate)) {
                break
            }
            printed.add(newAssumption)
        }
    }
}