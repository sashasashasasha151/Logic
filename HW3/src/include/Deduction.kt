package include

import p

fun aTP(pr: Result, a: Result?) {
    if (pr.number == 1) {
        if (pr.left!!.expression == "A") {
            pr.left = a
        } else {
            aTP(pr.left!!, a)
        }
    } else {
        if (pr.left!!.expression == "A") {
            pr.left = a
        } else {
            aTP(pr.left!!, a)
        }

        if (pr.right!!.expression == "A") {
            pr.right = a
        } else {
            aTP(pr.right!!, a)
        }
    }
}

fun addToProof(proof: List<Result>, a: Result): List<Result> {
    val list = mutableListOf<Result>()

    var prf: Result?
    var aa: Result?

    for (pr in proof) {
        if (pr.number == 0) {
            list.add(a)
            continue
        }

        prf = pr.copy()
        aa = a.copy()
        aTP(prf, aa)
        list.add(prf)
    }
    return list
}

fun deduction(proof: List<Result>, index: Int, indexV: Int): List<Result> {
    val assum = mutableListOf<Result>()
    for (j in indexV + 1 until n) {
        if (vars[index][j]) {
            assum.add(Result(ABClist[j]))
        } else {
            assum.add(Result("!", Result(ABClist[j])))
        }
    }

    val a: Result

    if (vars[index][indexV]) {
        a = Result(ABClist[indexV])
    } else {
        a = Result("!", Result(ABClist[indexV]))
    }

    val list = mutableListOf<Result>()

    var i = 1
    val printed = mutableListOf<Result>()
    val hashed = hashMapOf<String, Int>()
    val hashedH = hashMapOf<String, MutableList<Int>>()

    for (newAssumption in proof) {
        val s = resultToString(newAssumption)
        var isAssum = false

        for (ass in assum) {
            if (isEquals(newAssumption, ass)) {
                isAssum = true
                i++
                break
            }
        }

        if (isAssum) {
            list.add(newAssumption)
            list.add(Result("->", newAssumption, Result("->", a, newAssumption)))
            list.add(Result("->", a, newAssumption))
            printed.add(newAssumption)
            hashed[s] = i - 1
            if (newAssumption.expression == "->") {
                val ss = resultToString(newAssumption.right!!)
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
            if (checker.checker(p.axioms[j], newAssumption)) {
                i++
                isAssum = true
                break
            }
        }

        if (isAssum) {
            list.add(newAssumption)
            list.add(Result("->", newAssumption, Result("->", a, newAssumption)))
            list.add(Result("->", a, newAssumption))
            printed.add(newAssumption)
            hashed[s] = i - 1
            if (newAssumption.expression == "->") {
                val ss = resultToString(newAssumption.right!!)
                if (hashedH.containsKey(ss)) {
                    hashedH[ss]!!.add(i - 1)
                } else {
                    hashedH[ss] = mutableListOf(i - 1)
                }
            }
            continue
        }

        if (isEquals(newAssumption, a)) {
            i++
            list.addAll(addToProof(p.ded, a.copy()))
            list.add(Result("->", a, newAssumption))

            printed.add(newAssumption)
            hashed[s] = i - 1
            if (newAssumption.expression == "->") {
                val ss = resultToString(newAssumption.right!!)
                if (hashedH.containsKey(ss)) {
                    hashedH[ss]!!.add(i - 1)
                } else {
                    hashedH[ss] = mutableListOf(i - 1)
                }
            }
            continue
        }

        var lft = 0

        if (hashedH.containsKey(s)) {
            for (l in hashedH[s]!!) {
                val dell = resultToString(printed[l - 1].left!!)
                if (hashed.containsKey(dell)) {
                    lft = hashed[dell]!! - 1
                    i++
                    isAssum = true
                    break
                }
            }
        }


        if (isAssum) {
            list.add(Result(
                    "->",
                    Result(
                            "->",
                            a,
                            printed[lft]),
                    Result("->",
                            Result(
                                    "->",
                                    a,
                                    Result(
                                            "->",
                                            printed[lft],
                                            newAssumption
                                    )
                            ),
                            Result(
                                    "->",
                                    a,
                                    newAssumption)
                    )))

            list.add(Result("->",
                    Result(
                            "->",
                            a,
                            Result(
                                    "->",
                                    printed[lft],
                                    newAssumption
                            )
                    ),
                    Result(
                            "->",
                            a,
                            newAssumption)
            ))
            list.add(Result("->", a, newAssumption))
            printed.add(newAssumption)
            hashed[s] = i - 1
            if (newAssumption.expression == "->") {
                val ss = resultToString(newAssumption.right!!)
                if (hashedH.containsKey(ss)) {
                    hashedH[ss]!!.add(i - 1)
                } else {
                    hashedH[ss] = mutableListOf(i - 1)
                }
            }
            continue
        } else {
            while (true) {
                i++;
            }
        }
    }
    return list
}