package include

import p

fun merge(lst1: List<Result>, lst2: List<Result>, a: Result): List<Result> {
    val list = mutableListOf<Result>()
    list.addAll(lst1)
    list.addAll(lst2)
    list.addAll(addToProof(p.apna, a.copy()))
    list.add(Result(
            "->",
            Result(
                    "->",
                    a,
                    lst1[lst1.size - 1].right!!
            ),
            Result(
                    "->",
                    Result(
                            "->",
                            Result("!", a),
                            lst1[lst1.size - 1].right!!
                    ),
                    Result(
                            "->",
                            Result(
                                    "|",
                                    a,
                                    Result("!", a)
                            ),
                            lst1[lst1.size - 1].right!!
                    )
            )
    ))
    list.add(Result(
            "->",
            Result(
                    "->",
                    Result("!", a),
                    lst1[lst1.size - 1].right!!
            ),
            Result(
                    "->",
                    Result(
                            "|",
                            a,
                            Result("!", a)
                    ),
                    lst1[lst1.size - 1].right!!
            )
    ))
    list.add(Result(
            "->",
            Result(
                    "|",
                    a,
                    Result("!", a)
            ),
            lst1[lst1.size - 1].right!!
    ))
    list.add(lst1[lst1.size - 1].right!!)
    return list
}