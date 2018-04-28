package include

import p

class ProofMaker(index: Int) {
    private val list = mutableListOf<Result>()
    private val assume = mutableListOf<Result>()
    var index: Int = 0

    init {
        this.index = index
        for (abc in ABC) {
            if (vars[index][abc.value]) {
                assume.add(Result(abc.key))
            } else {
                assume.add(Result("!", Result(abc.key)))
            }
        }
    }

    fun make(expr: Result): List<Result> {
        makeProof(expr)
        return list
    }

    private fun makeProof(expr: Result) {
        for (a in assume) {
            if (isEquals(a, expr)) {
                list.add(expr)
                return
            }
        }

        if (expr.expression == "!" && expr.left!!.expression == "!") {
            makeProof(expr.left!!.left!!)
            addProofs(p.nnA, expr.left!!.left!!, null)
        }

        when (expr.expression) {
            "->" -> {
                if (eval(expr.left!!) && eval(expr.right!!)) {
                    makeProof(expr.left!!)
                    makeProof(expr.right!!)
                    addProofs(p.iAB, expr.left, expr.right)
                    return
                }

                if (eval(expr.left!!) && !eval(expr.right!!)) {
                    makeProof(expr.left!!)
                    makeProof(Result("!", expr.right!!))
                    addProofs(p.iAnB, expr.left!!, expr.right!!)
                    return
                }

                if (!eval(expr.left!!) && eval(expr.right!!)) {
                    makeProof(Result("!", expr.left!!))
                    makeProof(expr.right!!)
                    addProofs(p.inAB, expr.left!!, expr.right!!)
                    return
                }

                if (!eval(expr.left!!) && !eval(expr.right!!)) {
                    makeProof(Result("!", expr.left!!))
                    makeProof(Result("!", expr.right!!))
                    addProofs(p.inAnB, expr.left!!, expr.right!!)
                    return
                }
            }
            "&" -> {
                if (eval(expr.left!!) && eval(expr.right!!)) {
                    makeProof(expr.left!!)
                    makeProof(expr.right!!)
                    addProofs(p.uAB, expr.left, expr.right)
                    return
                }

                if (eval(expr.left!!) && !eval(expr.right!!)) {
                    makeProof(expr.left!!)
                    makeProof(Result("!", expr.right!!))
                    addProofs(p.uAnB, expr.left!!, expr.right!!)
                    return
                }

                if (!eval(expr.left!!) && eval(expr.right!!)) {
                    makeProof(Result("!", expr.left!!))
                    makeProof(expr.right!!)
                    addProofs(p.unAB, expr.left!!, expr.right!!)
                    return
                }

                if (!eval(expr.left!!) && !eval(expr.right!!)) {
                    makeProof(Result("!", expr.left!!))
                    makeProof(Result("!", expr.right!!))
                    addProofs(p.unAnB, expr.left!!, expr.right!!)
                    return
                }
            }
            "|" -> {
                if (eval(expr.left!!) && eval(expr.right!!)) {
                    makeProof(expr.left!!)
                    makeProof(expr.right!!)
                    addProofs(p.pAB, expr.left, expr.right)
                    return
                }

                if (eval(expr.left!!) && !eval(expr.right!!)) {
                    makeProof(expr.left!!)
                    makeProof(Result("!", expr.right!!))
                    addProofs(p.pAnB, expr.left!!, expr.right!!)
                    return
                }

                if (!eval(expr.left!!) && eval(expr.right!!)) {
                    makeProof(Result("!", expr.left!!))
                    makeProof(expr.right!!)
                    addProofs(p.pnAB, expr.left!!, expr.right!!)
                    return
                }

                if (!eval(expr.left!!) && !eval(expr.right!!)) {
                    makeProof(Result("!", expr.left!!))
                    makeProof(Result("!", expr.right!!))
                    addProofs(p.pnAnB, expr.left!!, expr.right!!)
                    return
                }
            }
            "!" -> {
                when (expr.left!!.expression) {
                    "->" -> {
                        makeProof(expr.left!!.left!!)
                        makeProof(Result("!", expr.left!!.right!!))
                        addProofs(p.iAnB, expr.left!!.left!!, expr.left!!.right!!)
                        return
                    }
                    "|" -> {
                        makeProof(Result("!", expr.left!!.left!!))
                        makeProof(Result("!", expr.left!!.right!!))
                        addProofs(p.pnAnB, expr.left!!.left!!, expr.left!!.right!!)
                        return
                    }
                    "&" -> {
                        if (eval(expr.left!!.left!!) && !eval(expr.left!!.right!!)) {
                            makeProof(expr.left!!.left!!)
                            makeProof(Result("!", expr.left!!.right!!))
                            addProofs(p.uAnB, expr.left!!.left!!, expr.left!!.right!!)
                            return
                        }

                        if (!eval(expr.left!!.left!!) && eval(expr.left!!.right!!)) {
                            makeProof(Result("!", expr.left!!.left!!))
                            makeProof(expr.left!!.right!!)
                            addProofs(p.unAB, expr.left!!.left!!, expr.left!!.right!!)
                            return
                        }

                        if (!eval(expr.left!!.left!!) && !eval(expr.left!!.right!!)) {
                            makeProof(Result("!", expr.left!!.left!!))
                            makeProof(Result("!", expr.left!!.right!!))
                            addProofs(p.unAnB, expr.left!!.left!!, expr.left!!.right!!)
                            return
                        }
                    }
                }
            }
        }
    }

    private fun eval(expr: Result): Boolean {
        return makeOperation(expr, index)
    }

    private var prf: Result? = null
    private var aa: Result? = null
    private var bb: Result? = null

    private fun addProofs(proof: List<Result>, a: Result?, b: Result?) {
        for (pr in proof) {
            if (pr.number == 0) {
                if (pr.expression == "A") {
                    list.add(a!!)
                } else {
                    list.add(b!!)
                }
                continue
            }
            prf = pr.copy()
            aa = a?.copy()
            bb = b?.copy()
            addProof(prf!!, aa, bb)
            list.add(prf!!)
        }
    }

    private fun addProof(pr: Result, a: Result?, b: Result?) {
        if (pr.number == 1) {
            if (pr.left!!.expression == "A") {
                pr.left = a
            } else if (pr.left!!.expression == "B") {
                pr.left = b
            } else {
                addProof(pr.left!!, a, b)
            }
        } else {
            if (pr.left!!.expression == "A") {
                pr.left = a
            } else if (pr.left!!.expression == "B") {
                pr.left = b
            } else {
                addProof(pr.left!!, a, b)
            }

            if (pr.right!!.expression == "A") {
                pr.right = a
            } else if (pr.right!!.expression == "B") {
                pr.right = b
            } else {
                addProof(pr.right!!, a, b)
            }
        }
    }
}