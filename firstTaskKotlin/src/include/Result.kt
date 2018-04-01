package include

class Result {
    var expression: String
    var left: Result? = null
    var right: Result? = null
    var number = 0

    constructor(expression: String) {
        this.expression = expression
    }

    constructor(expression: String, a: Result) {
        this.expression = expression
        left = a
        number = 1
    }

    constructor(expression: String, a: Result, b: Result) {
        this.expression = expression
        left = a
        right = b
        number = 2
    }
}