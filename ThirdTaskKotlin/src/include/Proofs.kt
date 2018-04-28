package include

import java.io.File
import java.util.stream.Collectors
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*


class Proofs() {
    var pAB = listOf<Result>()
    var pnAB = listOf<Result>()
    var pAnB = listOf<Result>()
    var pnAnB = listOf<Result>()
    var uAB = listOf<Result>()
    var unAB = listOf<Result>()
    var uAnB = listOf<Result>()
    var unAnB = listOf<Result>()
    var iAB = listOf<Result>()
    var inAB = listOf<Result>()
    var iAnB = listOf<Result>()
    var inAnB = listOf<Result>()
    var nnA = listOf<Result>()
    var axioms = mutableListOf<Result>()
    var ded = listOf<Result>()
    var apna = listOf<Result>()

    init {
        val input = Scanner(File("src/include/proofsTXT/axioms.txt"))
        for (i in 0..9) {
            axioms.add(Parser(input.nextLine()).parse())
        }
        //A|-!!A
        var path = Paths.get("src/include/proofsTXT/nnA.txt")
        nnA = Files
                .lines(path)
                .filter(fun(s) = !s.isEmpty())
                .map(fun(s) = Parser(s).parse())
                .collect(Collectors.toList())

        //A,B|-A|B
        path = Paths.get("src/include/proofsTXT/pAB.txt")
        pAB = Files
                .lines(path)
                .filter(fun(s) = !s.isEmpty())
                .map(fun(s) = Parser(s).parse())
                .collect(Collectors.toList())

        path = Paths.get("src/include/proofsTXT/pnAB.txt")
        pnAB = Files
                .lines(path)
                .filter(fun(s) = !s.isEmpty())
                .map(fun(s) = Parser(s).parse())
                .collect(Collectors.toList())

        path = Paths.get("src/include/proofsTXT/pAnB.txt")
        pAnB = Files
                .lines(path)
                .filter(fun(s) = !s.isEmpty())
                .map(fun(s) = Parser(s).parse())
                .collect(Collectors.toList())

        path = Paths.get("src/include/proofsTXT/pnAnB.txt")
        pnAnB = Files
                .lines(path)
                .filter(fun(s) = !s.isEmpty())
                .map(fun(s) = Parser(s).parse())
                .collect(Collectors.toList())

        //A,B|-A&B
        path = Paths.get("src/include/proofsTXT/uAB.txt")
        uAB = Files
                .lines(path)
                .filter(fun(s) = !s.isEmpty())
                .map(fun(s) = Parser(s).parse())
                .collect(Collectors.toList())

        path = Paths.get("src/include/proofsTXT/unAB.txt")
        unAB = Files
                .lines(path)
                .filter(fun(s) = !s.isEmpty())
                .map(fun(s) = Parser(s).parse())
                .collect(Collectors.toList())

        path = Paths.get("src/include/proofsTXT/uAnB.txt")
        uAnB = Files
                .lines(path)
                .filter(fun(s) = !s.isEmpty())
                .map(fun(s) = Parser(s).parse())
                .collect(Collectors.toList())

        path = Paths.get("src/include/proofsTXT/unAnB.txt")
        unAnB = Files
                .lines(path)
                .filter(fun(s) = !s.isEmpty())
                .map(fun(s) = Parser(s).parse())
                .collect(Collectors.toList())

        //A,B|-A->B
        path = Paths.get("src/include/proofsTXT/iAB.txt")
        iAB = Files
                .lines(path)
                .filter(fun(s) = !s.isEmpty())
                .map(fun(s) = Parser(s).parse())
                .collect(Collectors.toList())

        path = Paths.get("src/include/proofsTXT/inAB.txt")
        inAB = Files
                .lines(path)
                .filter(fun(s) = !s.isEmpty())
                .map(fun(s) = Parser(s).parse())
                .collect(Collectors.toList())

        path = Paths.get("src/include/proofsTXT/iAnB.txt")
        iAnB = Files
                .lines(path)
                .filter(fun(s) = !s.isEmpty())
                .map(fun(s) = Parser(s).parse())
                .collect(Collectors.toList())

        path = Paths.get("src/include/proofsTXT/inAnB.txt")
        inAnB = Files
                .lines(path)
                .filter(fun(s) = !s.isEmpty())
                .map(fun(s) = Parser(s).parse())
                .collect(Collectors.toList())

        path = Paths.get("src/include/proofsTXT/ded.txt")
        ded = Files
                .lines(path)
                .filter(fun(s) = !s.isEmpty())
                .map(fun(s) = Parser(s).parse())
                .collect(Collectors.toList())

        path = Paths.get("src/include/proofsTXT/apna.txt")
        apna = Files
                .lines(path)
                .filter(fun(s) = !s.isEmpty())
                .map(fun(s) = Parser(s).parse())
                .collect(Collectors.toList())
    }
}