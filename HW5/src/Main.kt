import include.KripkeV
import include.Parser
import include.Result
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import java.util.stream.Collectors
import java.util.stream.Stream
import kotlin.math.pow

var G = mutableListOf<MutableList<Int>>()
var kripkeVertexes = mutableListOf<KripkeV>()
var used = mutableListOf<Boolean>()
var n = -1
var base = HashSet<Int>()
var topology = HashSet<Int>()
var frcdVars = HashSet<String>()
var notAKripke = false

fun fUsed() {
    for (i in 0 until n) {
        used[i] = false
    }
}

fun isReallyKripke(variable: String, k: Int): Boolean {
    used[k] = true

    for (i in 0 until n) {
        if (G[k][i] == 1 && !used[i] && !isKripkeGood(variable, i)) {
            notAKripke = true
            return false
        }
    }
    if (kripkeVertexes[k].forced.contains(variable)) {
        return true
    }
    return false
}

fun checkVars(variable: String, k: Int) {
    used[k] = true

    if (kripkeVertexes[k].forced.contains(variable)) {
        isReallyKripke(variable, k)
        if (notAKripke) {
            return
        }
    }
    for (i in 0 until n) {
        if (G[k][i] == 1 && !used[i]) {
            checkVars(variable, i)
        }
    }
}

fun setNegate(variable: String, k: Int): Boolean {
    used[k] = true
    if (kripkeVertexes[k].forced.contains(variable)) {
        return false
    }
    for (i in 0 until n) {
        if (G[k][i] == 1 && !used[i]) {
            if (setNegate(variable, i)) {
                continue
            } else {
                return false
            }
        }
    }
    kripkeVertexes[k].forced.add("!($variable)")
    return true
}

fun setImpl(left: String, right: String, k: Int): Boolean {
    used[k] = true

    for (i in 0 until n) {
        if (G[k][i] == 1 && !used[i] && !setImpl(left, right, i)) {
            return false
        }
    }

    if (!kripkeVertexes[k].forced.contains(left) || kripkeVertexes[k].forced.contains(right)) {
        kripkeVertexes[k].forced.add("($left->$right)")
    } else {
        return false
    }

    return true
}

fun setAnd(left: String, right: String, k: Int) {
    used[k] = true
    if (kripkeVertexes[k].forced.contains(left) && kripkeVertexes[k].forced.contains(right)) {
        kripkeVertexes[k].forced.add("($left&$right)")
    }
    for (i in 0 until n) {
        if (G[k][i] == 1 && !used[i]) {
            setAnd(left, right, i)
        }
    }
}

fun setOr(left: String, right: String, k: Int) {
    used[k] = true
    if (kripkeVertexes[k].forced.contains(left) || kripkeVertexes[k].forced.contains(right)) {
        kripkeVertexes[k].forced.add("($left|$right)")
    }
    for (i in 0 until n) {
        if (G[k][i] == 1 && !used[i]) {
            setOr(left, right, i)
        }
    }
}

fun isKripkeGood(formula: String, k: Int): Boolean {
    used[k] = true
    if (!kripkeVertexes[k].forced.contains(formula)) {
        return false
    }
    for (i in 0 until n) {
        if (G[k][i] == 1 && !used[i] && !isKripkeGood(formula, i)) {
            return false
        }
    }
    return true
}

fun buildBase(k: Int): Int {
    used[k] = true
    var top = 1 shl k
    for (i in 0 until n) {
        if (G[k][i] == 1 && !used[i]) {
            top = buildBase(i) or top
        }
    }
    base.add(top)
    return top
}

fun makeResult(current_expression: Result?): String {
    val sb = StringBuilder()
    when (current_expression?.number) {
        2 -> {
            val expr1 = makeResult(current_expression.left!!)
            val expr2 = makeResult(current_expression.right!!)
            fUsed()
            for (i in 0 until n) {
                if (!used[i]) {
                    when (current_expression.expression) {
                        "->" -> {
                            setImpl(expr1, expr2, i)
                        }
                        "&" -> {
                            setAnd(expr1, expr2, i)
                        }
                        "|" -> {
                            setOr(expr1, expr2, i)
                        }
                        else -> {
                            return ""
                        }
                    }
                }
            }
            sb.append("(")
                    .append(expr1)
                    .append(current_expression.expression)
                    .append(expr2)
                    .append(")")
        }
        1 -> {
            val expr = makeResult(current_expression.left!!)
            fUsed()
            for (i in 0 until n) {
                if (!used[i]) {
                    setNegate(expr, i)
                }
            }
            sb.append("!(").append(expr).append(")")
        }
        0 -> {
            fUsed()
            for (i in 0 until n) {
                if (!used[i]) {
                    checkVars(current_expression.expression, i)
                }
                if (notAKripke) {
                    return ""
                }
            }
            frcdVars.add(current_expression.expression)
            return current_expression.expression
        }
        else -> {
            return ""
        }
    }
    return sb.toString()
}

fun getVarNumber(variable: String, k: Int): Int {
    used[k] = true
    var top: Int
    if (kripkeVertexes[k].forced.contains(variable)) {
        top = (1 shl k)
    } else {
        return 0
    }
    for (i in 0 until n) {
        if (G[k][i] == 1 && !used[i]) {
            top = getVarNumber(variable, i) or top
        }
    }
    return top
}

fun getForcedVars(expr: Result) {
    if (expr.number == 0) {
        frcdVars.add(expr.expression)
    } else {
        if (expr.number == 1) {
            getForcedVars(expr.left!!)
        } else {
            getForcedVars(expr.left!!)
            getForcedVars(expr.right!!)
        }
    }
}

fun main(args: Array<String>) {
    var path = Paths.get("input.txt")
//    var path = Paths.get("src/in.txt")
    var list = Files.lines(path).filter { !it.isEmpty() }.collect(Collectors.toList())

    val parsedFormula = Parser(list[0]).parse()

    path = Paths.get("src/include/tests.txt")
    list = Files.lines(path).filter { !it.isEmpty() }.collect(Collectors.toList())

    G = Stream.generate { Stream.generate { -1 }.limit(5).collect(Collectors.toList()) }.limit(5).collect(Collectors.toList())
    used = Stream.generate { false }.limit(5).collect(Collectors.toList())

    var l = 0
    while (l < list.size) {
        for (g in 0..4) {
            used[g] = false
            for (gg in 0..4) {
                G[g][gg] = -1
            }
        }
        kripkeVertexes.clear()
        frcdVars.clear()
        n = 0

        var currentElementNumber = 0
        while (l < list.size && list[l] != "!") {
            n++
            val kripkeWorld = KripkeV()

            val world = list[l].split('*')

            kripkeWorld.space = world[0].length

            if (world[0].isEmpty()) {
                currentElementNumber++
                kripkeVertexes.add(kripkeWorld)
            } else {
                if (kripkeWorld.space!! == kripkeVertexes[currentElementNumber - 1].space!! + 1) {
                    kripkeWorld.parent = currentElementNumber - 1
                    G[currentElementNumber - 1][currentElementNumber] = 1
                    currentElementNumber++
                    kripkeVertexes.add(kripkeWorld)
                } else {
                    var curV = kripkeVertexes[currentElementNumber - 1].parent
                    while (kripkeWorld.space!! <= kripkeVertexes[curV!!].space!!) {
                        curV = kripkeVertexes[curV].parent
                    }
                    kripkeWorld.parent = curV
                    G[curV][currentElementNumber] = 1
                    currentElementNumber++
                    kripkeVertexes.add(kripkeWorld)
                }
            }
            l++
        }
        l++

        getForcedVars(parsedFormula)

        val VARS = frcdVars.asSequence().toList()
        for (i0 in 0 until 2.toDouble().pow(n).toInt()) {
            var arr0 = i0.toString(2)
            while (arr0.length != n) {
                arr0 = '0' + arr0
            }

            for (i1 in 0 until 2.toDouble().pow(n).toInt()) {
                var arr1 = i1.toString(2)
                while (arr1.length != n) {
                    arr1 = '0' + arr1
                }

                for (i2 in 0 until 2.toDouble().pow(n).toInt()) {
                    var arr2 = i2.toString(2)
                    while (arr2.length != n) {
                        arr2 = '0' + arr2
                    }

                    for (kr in kripkeVertexes) {
                        kr.forced.clear()
                    }
                    for (j0 in 0 until arr0.length) {
                        if (arr0[j0] == '1') {
                            kripkeVertexes[j0].forced.add(VARS[0])
                        } else {
                            kripkeVertexes[j0].forced.remove(VARS[0])
                        }
                    }
                    if (VARS.size > 1) {
                        for (j1 in 0 until arr1.length) {
                            if (arr1[j1] == '1') {
                                kripkeVertexes[j1].forced.add(VARS[1])
                            } else {
                                kripkeVertexes[j1].forced.remove(VARS[1])
                            }
                        }
                    }
                    if (VARS.size > 2) {
                        for (j2 in 0 until arr2.length) {
                            if (arr2[j2] == '1') {
                                kripkeVertexes[j2].forced.add(VARS[2])
                            } else {
                                kripkeVertexes[j2].forced.remove(VARS[2])
                            }
                        }
                    }


                    notAKripke = false
                    val buildedFormula = makeResult(parsedFormula)

                    if (notAKripke) {
                        continue
                    }

                    fUsed()
                    var isGood = false
                    for (i in 0 until n) {
                        if (!used[i] && !isKripkeGood(buildedFormula, i)) {
                            isGood = true
                            break
                        }
                    }

                    if (!isGood) {
                        continue
                    }

                    fUsed()
                    base.add(0)
                    for (i in 0 until n) {
                        if (!used[i]) {
                            buildBase(i)
                        }
                    }

                    val sortedBase = base.asSequence().toList().asSequence().sorted().toList()

                    for (i in 0 until 2.toDouble().pow(sortedBase.size).toInt()) {
                        val arr = i.toString(2)
                        var t = 0
                        for (j in 0 until arr.length) {
                            if (arr[j] == '1') {
                                t = t or sortedBase[j]
                            }
                        }
                        topology.add(t)
                    }

                    val sortedGraphVertexes = topology.asSequence().toList().asSequence().sorted().toList()
                    val geitG = Stream.generate { mutableListOf<Int>() }.limit((sortedGraphVertexes.size).toLong()).collect(Collectors.toList())

                    for (i in 0 until sortedGraphVertexes.size) {
                        for (j in i until sortedGraphVertexes.size) {
                            if ((sortedGraphVertexes[j] or sortedGraphVertexes[i]) == sortedGraphVertexes[j]) {
                                geitG[i].add(j)
                            }
                        }
                    }

                    println(sortedGraphVertexes.size)
                    for (i in 0 until sortedGraphVertexes.size) {
                        for (j in 0 until geitG[i].size - 1) {
                            print(geitG[i][j] + 1)
                            print(" ")
                        }
                        print(geitG[i][geitG[i].size - 1] + 1)
                        println()
                    }


                    for (j in 0 until VARS.size) {
                        var numb = 0
                        fUsed()
                        for (i in 0 until n) {
                            if (!used[i]) {
                                numb = numb or getVarNumber(VARS[j], i)
                            }
                        }
                        if (j == 0) {
                            print(VARS[j] + "=")
                        } else {
                            print("," + VARS[j] + "=")
                        }
                        for (i in 0 until sortedGraphVertexes.size) {
                            if (sortedGraphVertexes[i] == numb) {
                                print(i + 1)
                                break
                            }
                        }
                    }
                    return
                }
            }
        }
    }
    print("Формула общезначима")
}