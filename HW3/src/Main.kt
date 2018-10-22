import include.*
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import java.util.stream.Collector
import java.util.stream.Collectors

val p = Proofs()

fun main(args: Array<String>) {
//    val `in` = Scanner(System.`in`)
    val `in` = Scanner(File("src/in.txt"))
//    val `in` = Scanner(File("input.txt"))

    val string = `in`.nextLine()

    val inString = string.split("|=")

    var assumptions = mutableListOf<String>()

    if (inString[0] != "") {
        assumptions = inString[0].split(",").stream().map { "($it)" }.collect(Collectors.toList())
    }

    var inputString = ""
    for (a in assumptions) {
        inputString += a + "->"
    }
    inputString += inString[1]
    val pars = Parser(inputString)
    pars.is_set = true
    val inputTree = pars.parse()

    if (!isTrue(inputTree)) {
        return
    }

    val proofLists = mutableListOf<List<Result>>()
    for (i in 0 until (1 shl n)) {
        val proofMaker = ProofMaker(i)
        val tr = inputTree.copy()
        proofLists.add(proofMaker.make(tr))
    }

    val answer: List<Result>
    if (n == 5) {
        val proofLists1 = mutableListOf<List<Result>>()
        proofLists1.add(deduction(proofLists[0], 0, 0))
        proofLists1.add(deduction(proofLists[1], 1, 0))
        proofLists1.add(deduction(proofLists[2], 2, 0))
        proofLists1.add(deduction(proofLists[3], 3, 0))
        proofLists1.add(deduction(proofLists[4], 4, 0))
        proofLists1.add(deduction(proofLists[5], 5, 0))
        proofLists1.add(deduction(proofLists[6], 6, 0))
        proofLists1.add(deduction(proofLists[7], 7, 0))
        proofLists1.add(deduction(proofLists[8], 8, 0))
        proofLists1.add(deduction(proofLists[9], 9, 0))
        proofLists1.add(deduction(proofLists[10], 10, 0))
        proofLists1.add(deduction(proofLists[11], 11, 0))
        proofLists1.add(deduction(proofLists[12], 12, 0))
        proofLists1.add(deduction(proofLists[13], 13, 0))
        proofLists1.add(deduction(proofLists[14], 14, 0))
        proofLists1.add(deduction(proofLists[15], 15, 0))
        proofLists1.add(deduction(proofLists[16], 16, 0))
        proofLists1.add(deduction(proofLists[17], 17, 0))
        proofLists1.add(deduction(proofLists[18], 18, 0))
        proofLists1.add(deduction(proofLists[19], 19, 0))
        proofLists1.add(deduction(proofLists[20], 20, 0))
        proofLists1.add(deduction(proofLists[21], 21, 0))
        proofLists1.add(deduction(proofLists[22], 22, 0))
        proofLists1.add(deduction(proofLists[23], 23, 0))
        proofLists1.add(deduction(proofLists[24], 24, 0))
        proofLists1.add(deduction(proofLists[25], 25, 0))
        proofLists1.add(deduction(proofLists[26], 26, 0))
        proofLists1.add(deduction(proofLists[27], 27, 0))
        proofLists1.add(deduction(proofLists[28], 28, 0))
        proofLists1.add(deduction(proofLists[29], 29, 0))
        proofLists1.add(deduction(proofLists[30], 30, 0))
        proofLists1.add(deduction(proofLists[31], 31, 0))

        val proofLists2 = mutableListOf<List<Result>>()
        proofLists2.add(merge(proofLists1[0], proofLists1[1], Result(ABClist[0])))
        proofLists2.add(merge(proofLists1[2], proofLists1[3], Result(ABClist[0])))
        proofLists2.add(merge(proofLists1[4], proofLists1[5], Result(ABClist[0])))
        proofLists2.add(merge(proofLists1[6], proofLists1[7], Result(ABClist[0])))
        proofLists2.add(merge(proofLists1[8], proofLists1[9], Result(ABClist[0])))
        proofLists2.add(merge(proofLists1[10], proofLists1[11], Result(ABClist[0])))
        proofLists2.add(merge(proofLists1[12], proofLists1[13], Result(ABClist[0])))
        proofLists2.add(merge(proofLists1[14], proofLists1[15], Result(ABClist[0])))
        proofLists2.add(merge(proofLists1[16], proofLists1[17], Result(ABClist[0])))
        proofLists2.add(merge(proofLists1[18], proofLists1[19], Result(ABClist[0])))
        proofLists2.add(merge(proofLists1[20], proofLists1[21], Result(ABClist[0])))
        proofLists2.add(merge(proofLists1[22], proofLists1[23], Result(ABClist[0])))
        proofLists2.add(merge(proofLists1[24], proofLists1[25], Result(ABClist[0])))
        proofLists2.add(merge(proofLists1[26], proofLists1[27], Result(ABClist[0])))
        proofLists2.add(merge(proofLists1[28], proofLists1[29], Result(ABClist[0])))
        proofLists2.add(merge(proofLists1[30], proofLists1[31], Result(ABClist[0])))

        val proofLists3 = mutableListOf<List<Result>>()
        proofLists3.add(deduction(proofLists2[0], 0, 1))
        proofLists3.add(deduction(proofLists2[1], 2, 1))
        proofLists3.add(deduction(proofLists2[2], 4, 1))
        proofLists3.add(deduction(proofLists2[3], 6, 1))
        proofLists3.add(deduction(proofLists2[4], 8, 1))
        proofLists3.add(deduction(proofLists2[5], 10, 1))
        proofLists3.add(deduction(proofLists2[6], 12, 1))
        proofLists3.add(deduction(proofLists2[7], 14, 1))
        proofLists3.add(deduction(proofLists2[8], 16, 1))
        proofLists3.add(deduction(proofLists2[9], 18, 1))
        proofLists3.add(deduction(proofLists2[10], 20, 1))
        proofLists3.add(deduction(proofLists2[11], 22, 1))
        proofLists3.add(deduction(proofLists2[12], 24, 1))
        proofLists3.add(deduction(proofLists2[13], 26, 1))
        proofLists3.add(deduction(proofLists2[14], 28, 1))
        proofLists3.add(deduction(proofLists2[15], 30, 1))

        val proofLists4 = mutableListOf<List<Result>>()
        proofLists4.add(merge(proofLists3[0], proofLists3[1], Result(ABClist[1])))
        proofLists4.add(merge(proofLists3[2], proofLists3[3], Result(ABClist[1])))
        proofLists4.add(merge(proofLists3[4], proofLists3[5], Result(ABClist[1])))
        proofLists4.add(merge(proofLists3[6], proofLists3[7], Result(ABClist[1])))
        proofLists4.add(merge(proofLists3[8], proofLists3[9], Result(ABClist[1])))
        proofLists4.add(merge(proofLists3[10], proofLists3[11], Result(ABClist[1])))
        proofLists4.add(merge(proofLists3[12], proofLists3[13], Result(ABClist[1])))
        proofLists4.add(merge(proofLists3[14], proofLists3[15], Result(ABClist[1])))

        val proofLists5 = mutableListOf<List<Result>>()
        proofLists5.add(deduction(proofLists4[0], 0, 2))
        proofLists5.add(deduction(proofLists4[1], 4, 2))
        proofLists5.add(deduction(proofLists4[2], 8, 2))
        proofLists5.add(deduction(proofLists4[3], 12, 2))
        proofLists5.add(deduction(proofLists4[4], 16, 2))
        proofLists5.add(deduction(proofLists4[5], 20, 2))
        proofLists5.add(deduction(proofLists4[6], 24, 2))
        proofLists5.add(deduction(proofLists4[7], 28, 2))

        val proofLists6 = mutableListOf<List<Result>>()
        proofLists6.add(merge(proofLists5[0], proofLists5[1], Result(ABClist[2])))
        proofLists6.add(merge(proofLists5[2], proofLists5[3], Result(ABClist[2])))
        proofLists6.add(merge(proofLists5[4], proofLists5[5], Result(ABClist[2])))
        proofLists6.add(merge(proofLists5[6], proofLists5[7], Result(ABClist[2])))

        val proofLists7 = mutableListOf<List<Result>>()
        proofLists7.add(deduction(proofLists6[0], 0, 3))
        proofLists7.add(deduction(proofLists6[1], 8, 3))
        proofLists7.add(deduction(proofLists6[2], 16, 3))
        proofLists7.add(deduction(proofLists6[3], 24, 3))

        val proofLists8 = mutableListOf<List<Result>>()
        proofLists8.add(merge(proofLists7[0], proofLists7[1], Result(ABClist[3])))
        proofLists8.add(merge(proofLists7[2], proofLists7[3], Result(ABClist[3])))

        val proofLists9 = mutableListOf<List<Result>>()
        proofLists9.add(deduction(proofLists8[0], 0, 4))
        proofLists9.add(deduction(proofLists8[1], 16, 4))

        answer = merge(proofLists9[0], proofLists8[1], Result(ABClist[4]))

    } else if (n == 4) {
        val proofLists1 = mutableListOf<List<Result>>()
        proofLists1.add(deduction(proofLists[0], 0, 0))
        proofLists1.add(deduction(proofLists[1], 1, 0))
        proofLists1.add(deduction(proofLists[2], 2, 0))
        proofLists1.add(deduction(proofLists[3], 3, 0))
        proofLists1.add(deduction(proofLists[4], 4, 0))
        proofLists1.add(deduction(proofLists[5], 5, 0))
        proofLists1.add(deduction(proofLists[6], 6, 0))
        proofLists1.add(deduction(proofLists[7], 7, 0))
        proofLists1.add(deduction(proofLists[8], 8, 0))
        proofLists1.add(deduction(proofLists[9], 9, 0))
        proofLists1.add(deduction(proofLists[10], 10, 0))
        proofLists1.add(deduction(proofLists[11], 11, 0))
        proofLists1.add(deduction(proofLists[12], 12, 0))
        proofLists1.add(deduction(proofLists[13], 13, 0))
        proofLists1.add(deduction(proofLists[14], 14, 0))
        proofLists1.add(deduction(proofLists[15], 15, 0))

        val proofLists2 = mutableListOf<List<Result>>()
        proofLists2.add(merge(proofLists1[0], proofLists1[1], Result(ABClist[0])))
        proofLists2.add(merge(proofLists1[2], proofLists1[3], Result(ABClist[0])))
        proofLists2.add(merge(proofLists1[4], proofLists1[5], Result(ABClist[0])))
        proofLists2.add(merge(proofLists1[6], proofLists1[7], Result(ABClist[0])))
        proofLists2.add(merge(proofLists1[8], proofLists1[9], Result(ABClist[0])))
        proofLists2.add(merge(proofLists1[10], proofLists1[11], Result(ABClist[0])))
        proofLists2.add(merge(proofLists1[12], proofLists1[13], Result(ABClist[0])))
        proofLists2.add(merge(proofLists1[14], proofLists1[15], Result(ABClist[0])))

        val proofLists3 = mutableListOf<List<Result>>()
        proofLists3.add(deduction(proofLists2[0], 0, 1))
        proofLists3.add(deduction(proofLists2[1], 2, 1))
        proofLists3.add(deduction(proofLists2[2], 4, 1))
        proofLists3.add(deduction(proofLists2[3], 6, 1))
        proofLists3.add(deduction(proofLists2[4], 8, 1))
        proofLists3.add(deduction(proofLists2[5], 10, 1))
        proofLists3.add(deduction(proofLists2[6], 12, 1))
        proofLists3.add(deduction(proofLists2[7], 14, 1))

        val proofLists4 = mutableListOf<List<Result>>()
        proofLists4.add(merge(proofLists3[0], proofLists3[1], Result(ABClist[1])))
        proofLists4.add(merge(proofLists3[2], proofLists3[3], Result(ABClist[1])))
        proofLists4.add(merge(proofLists3[4], proofLists3[5], Result(ABClist[1])))
        proofLists4.add(merge(proofLists3[6], proofLists3[7], Result(ABClist[1])))

        val proofLists5 = mutableListOf<List<Result>>()
        proofLists5.add(deduction(proofLists4[0], 0, 2))
        proofLists5.add(deduction(proofLists4[1], 4, 2))
        proofLists5.add(deduction(proofLists4[2], 8, 2))
        proofLists5.add(deduction(proofLists4[3], 12, 2))

        val proofLists6 = mutableListOf<List<Result>>()
        proofLists6.add(merge(proofLists5[0], proofLists5[1], Result(ABClist[2])))
        proofLists6.add(merge(proofLists5[2], proofLists5[3], Result(ABClist[2])))

        val proofLists7 = mutableListOf<List<Result>>()
        proofLists7.add(deduction(proofLists6[0], 0, 3))
        proofLists7.add(deduction(proofLists6[1], 8, 3))

        answer = merge(proofLists7[0], proofLists7[1], Result(ABClist[3]))

    } else if (n == 3) {
        val proofLists1 = mutableListOf<List<Result>>()

        proofLists1.add(deduction(proofLists[0], 0, 0))
        proofLists1.add(deduction(proofLists[1], 1, 0))
        proofLists1.add(deduction(proofLists[2], 2, 0))
        proofLists1.add(deduction(proofLists[3], 3, 0))
        proofLists1.add(deduction(proofLists[4], 4, 0))
        proofLists1.add(deduction(proofLists[5], 5, 0))
        proofLists1.add(deduction(proofLists[6], 6, 0))
        proofLists1.add(deduction(proofLists[7], 7, 0))

        val proofLists2 = mutableListOf<List<Result>>()
        proofLists2.add(merge(proofLists1[0], proofLists1[1], Result(ABClist[0])))
        proofLists2.add(merge(proofLists1[2], proofLists1[3], Result(ABClist[0])))
        proofLists2.add(merge(proofLists1[4], proofLists1[5], Result(ABClist[0])))
        proofLists2.add(merge(proofLists1[6], proofLists1[7], Result(ABClist[0])))

        val proofLists3 = mutableListOf<List<Result>>()
        proofLists3.add(deduction(proofLists2[0], 0, 1))
        proofLists3.add(deduction(proofLists2[1], 2, 1))
        proofLists3.add(deduction(proofLists2[2], 4, 1))
        proofLists3.add(deduction(proofLists2[3], 6, 1))

        val proofLists4 = mutableListOf<List<Result>>()
        proofLists4.add(merge(proofLists3[0], proofLists3[1], Result(ABClist[1])))
        proofLists4.add(merge(proofLists3[2], proofLists3[3], Result(ABClist[1])))

        val proofLists5 = mutableListOf<List<Result>>()
        proofLists5.add(deduction(proofLists4[0], 0, 2))
        proofLists5.add(deduction(proofLists4[1], 4, 2))

        answer = merge(proofLists5[0], proofLists5[1], Result(ABClist[2]))
    } else if (n == 2) {
        val proofLists1 = mutableListOf<List<Result>>()
        proofLists1.add(deduction(proofLists[0], 0, 0))
        proofLists1.add(deduction(proofLists[1], 1, 0))
        proofLists1.add(deduction(proofLists[2], 2, 0))
        proofLists1.add(deduction(proofLists[3], 3, 0))

        val proofLists2 = mutableListOf<List<Result>>()
        proofLists2.add(merge(proofLists1[0], proofLists1[1], Result(ABClist[0])))
        proofLists2.add(merge(proofLists1[2], proofLists1[3], Result(ABClist[0])))

        val proofLists3 = mutableListOf<List<Result>>()
        proofLists3.add(deduction(proofLists2[0], 0, 1))
        proofLists3.add(deduction(proofLists2[1], 2, 1))

        answer = merge(proofLists3[0], proofLists3[1], Result(ABClist[1]))
    } else {
        val proofLists1 = mutableListOf<List<Result>>()
        proofLists1.add(deduction(proofLists[0], 0, 0))
        proofLists1.add(deduction(proofLists[1], 1, 0))

        answer = merge(proofLists1[0], proofLists1[1], Result(ABClist[0]))
    }

//    for (list in proofLists) {
//        for (l in list) {
//            println(resultToString(l))
//        }
//        println()
//    }

    val hhh = hashSetOf<String>()

    Files.newBufferedWriter(Paths.get("output.txt")).use { bw ->
        var c = 0
        while (string[c] != '=') {
            bw.append(string[c++])
        }
        bw.append('-')
        c++
        while (c != string.length) {
            bw.append(string[c++])
        }
        bw.append("\n")
        for (t in 0 until answer.size) {
            val str = resultToString(answer[t])
            if(hhh.contains(str)) {
                continue
            }
            bw.append(str)
            hhh.add(str)
            bw.append("\n")
        }
        var ans = answer[answer.size - 1]
        for (a in assumptions) {
            bw.append(a)
            bw.append("\n")
            bw.append(resultToString(ans.right!!))
            bw.append("\n")
            ans = ans.right!!
        }
    }

    return
}

/*
|=!A|A
|=!(!A&A)
|=A->B->A
|=(A->B)->(C->B)->(A|C->B)
|=((A|B)->(B|C)&(C|A))&((A&B)|(B&C)->(C&A))|((A->B)&(B->C)|(C->A))
 */