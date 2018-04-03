package include

class Checker() {
    var A: Result? = null
    var B: Result? = null
    var C: Result? = null

    private fun isEquals(left: Result?, right: Result?): Boolean {
        if (left == null && right == null) {
            return true
        }
        if(left?.expression == right?.expression) {
            return isEquals(left?.left, right?.left) && isEquals(left?.right, right?.right)
        }
        return false
    }

    fun checker(axiom: Result?, expression: Result?): Boolean {
        when (axiom?.expression) {
            "A" -> {
                if (A == null) {
                    A = expression
                    return true
                } else {
                    return isEquals(A, expression)
                }
            }
            "B" -> {
                if (B == null) {
                    B = expression
                    return true
                } else {
                    return isEquals(B, expression)
                }
            }
            "C" -> {
                if (C == null) {
                    C = expression
                    return true
                } else {
                    return isEquals(C, expression)
                }
            }
            "!" -> {
                if(expression?.expression == "!") {
                    return checker(axiom.left, expression.left)
                } else {
                    return false
                }
            }
            expression?.expression -> {
                return checker(axiom?.left, expression?.left) && checker(axiom?.right, expression?.right)
            }
            else -> {
                return false
            }
        }
    }
}