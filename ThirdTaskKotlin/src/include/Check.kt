package include

val ABC = hashMapOf<String, Int>()
val ABClist = mutableListOf<String>()
var n = 0
val vars = listOf(
        listOf(false, false, false, false, false),
        listOf(true, false, false, false, false),
        listOf(false, true, false, false, false),
        listOf(true, true, false, false, false),
        listOf(false, false, true, false, false),
        listOf(true, false, true, false, false),
        listOf(false, true, true, false, false),
        listOf(true, true, true, false, false),
        listOf(false, false, false, true, false),
        listOf(true, false, false, true, false),
        listOf(false, true, false, true, false),
        listOf(true, true, false, true, false),
        listOf(false, false, true, true, false),
        listOf(true, false, true, true, false),
        listOf(false, true, true, true, false),
        listOf(true, true, true, true, false),
        listOf(false, false, false, false, true),
        listOf(true, false, false, false, true),
        listOf(false, true, false, false, true),
        listOf(true, true, false, false, true),
        listOf(false, false, true, false, true),
        listOf(true, false, true, false, true),
        listOf(false, true, true, false, true),
        listOf(true, true, true, false, true),
        listOf(false, false, false, true, true),
        listOf(true, false, false, true, true),
        listOf(false, true, false, true, true),
        listOf(true, true, false, true, true),
        listOf(false, false, true, true, true),
        listOf(true, false, true, true, true),
        listOf(false, true, true, true, true),
        listOf(true, true, true, true, true))

fun set(string: String) {
    if (!ABC.containsKey(string)) {
        ABC[string] = n++
        ABClist.add(string)
    }
}

fun makeOperation(expr: Result, index: Int): Boolean {
    when (expr.expression) {
        "->" -> {
            return !makeOperation(expr.left!!, index) || makeOperation(expr.right!!, index)
        }
        "&" -> {
            return makeOperation(expr.left!!, index) && makeOperation(expr.right!!, index)
        }
        "|" -> {
            return makeOperation(expr.left!!, index) || makeOperation(expr.right!!, index)
        }
        "!" -> {
            return !makeOperation(expr.left!!, index)
        }
        else -> {
            return vars[index][ABC[expr.expression]!!]
        }
    }
}

fun isTrue(expr: Result): Boolean {
    for (i in 0 until (1 shl n)) {
        if (!makeOperation(expr, i)) {
            print("Высказывание ложно при ")
            for (abc in ABC) {
                print(abc.key + "=")
                if (vars[i][abc.value]) {
                    print("И")
                } else {
                    print("Л")
                }
                if (n != 1) {
                    print(", ")
                }
                n--
            }
            return false
        }
    }
    return true
}

fun isEquals(left: Result?, right: Result?): Boolean {
    if (left == null && right == null) {
        return true
    }
    if (left?.expression == right?.expression) {
        return isEquals(left?.left, right?.left) && isEquals(left?.right, right?.right)
    }
    return false
}