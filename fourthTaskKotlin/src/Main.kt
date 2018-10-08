import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Collectors
import java.util.stream.IntStream
import java.util.stream.Stream

var n = 0
var G = mutableListOf<MutableList<Boolean>>()
var rG = mutableListOf<MutableList<Boolean>>()
var sup = mutableListOf<MutableList<Int>>()
var inf = mutableListOf<MutableList<Int>>()
var used = mutableListOf<Boolean>()

fun dfs(k: Int) {
    used[k] = true
    IntStream.range(0, n).filter { G[k][it] && !used[it] }.forEach { dfs(it) }
}

fun getSup(l: Int, r: Int): Int {
    val conc = Stream.iterate(0, { it + 1 }).limit(n.toLong()).map { rG[l][it] && rG[r][it] }.collect(Collectors.toList())
    if (conc.stream().allMatch { !it }) return -1

    var kk = -1

    if (IntStream.range(0, n).filter { conc[it] }.anyMatch {
                if (!IntStream.range(0, n).filter { j: Int -> it != j }.anyMatch { j: Int -> G[it][j] && conc[j] }) {
                    if (kk == -1) {
                        kk = it
                    } else {
                        return@anyMatch true
                    }
                }
                return@anyMatch false
            }) {
        return -1
    }
    return kk
}

fun getInf(l: Int, r: Int): Int {
    val conc = Stream.iterate(0, { it + 1 }).limit(n.toLong()).map { G[l][it] && G[r][it] }.collect(Collectors.toList())
    if (conc.stream().allMatch { !it }) {
        return -1
    }

    var kk = -1

    if (IntStream.range(0, n).filter { conc[it] }.anyMatch {
                if (!IntStream.range(0, n).filter { j: Int -> it != j }.anyMatch { j: Int -> rG[it][j] && conc[j] }) {
                    if (kk == -1) {
                        kk = it
                    } else {
                        return@anyMatch true
                    }
                }
                return@anyMatch false
            }) {
        return -1
    }
    return kk
}

fun getImpl(a: Int, b: Int): Int {
    val conc = Stream.iterate(0, { it + 1 }).limit(n.toLong()).map { G[b][inf[a][it]] }.collect(Collectors.toList())

    if (conc.stream().allMatch { !it }) {
        return -1
    }

    var kk = -1

    if (IntStream.range(0, n).filter { conc[it] }.anyMatch {
                if (!IntStream.range(0, n).filter { j: Int -> it != j }.anyMatch { j: Int -> rG[it][j] && conc[j] }) {
                    if (kk == -1) {
                        kk = it
                    } else {
                        return@anyMatch true
                    }
                }
                return@anyMatch false
            }) {
        return -1
    }
    return kk
}

fun main(args: Array<String>) {
//    val path = Paths.get("input.txt")
    val path = Paths.get("src/in.txt")

    val list = Files.lines(path).filter { !it.isEmpty() }.map { it -> it.split(" ").stream().filter { !it.isEmpty() }.map { it.toInt() }.collect(Collectors.toList()) }.collect(Collectors.toList())

    n = list[0][0]

    G = Stream.generate { Stream.generate { false }.limit(n.toLong()).collect(Collectors.toList()) }.limit(n.toLong()).collect(Collectors.toList())
    rG = Stream.generate { Stream.generate { false }.limit(n.toLong()).collect(Collectors.toList()) }.limit(n.toLong()).collect(Collectors.toList())
    sup = Stream.generate { Stream.generate { -1 }.limit(n.toLong()).collect(Collectors.toList()) }.limit(n.toLong()).collect(Collectors.toList())
    inf = Stream.generate { Stream.generate { -1 }.limit(n.toLong()).collect(Collectors.toList()) }.limit(n.toLong()).collect(Collectors.toList())

    list.indices.toList().stream().skip(1).forEach {
        list[it].stream().forEach { i: Int ->
            G[i - 1][it - 1] = true
            rG[it - 1][i - 1] = true
        }
    }

    IntStream.range(0, n).forEach { it ->
        used = Stream.generate { false }.limit(n.toLong()).collect(Collectors.toList())
        dfs(it)
        IntStream.range(0, n).filter { used[it] }.forEach { i: Int ->
            G[it][i] = true
            rG[i][it] = true
        }
    }

    if (IntStream.range(0, n).anyMatch {
                IntStream.range(0, n).anyMatch second@{ j: Int ->
                    sup[it][j] = getSup(it, j)
                    if (sup[it][j] == -1) {
                        print("Операция '+' не определена: ${it + 1}+${j + 1}")
                        return@second true
                    }
                    return@second false
                }
            }) {
        return
    }

    if (IntStream.range(0, n).anyMatch {
                IntStream.range(0, n).anyMatch second@{ j: Int ->
                    inf[it][j] = getInf(it, j)
                    if (inf[it][j] == -1) {
                        print("Операция '*' не определена: ${it + 1}*${j + 1}")
                        return@second true
                    }
                    return@second false
                }
            }) {
        return
    }

    if (IntStream.range(0, n).anyMatch { a: Int ->
                IntStream.range(0, n).anyMatch { b: Int ->
                    IntStream.range(0, n).anyMatch third@{ c: Int ->
                        if (inf[a][sup[b][c]] != sup[inf[a][b]][inf[a][c]]) {
                            print("Нарушается дистрибутивность: ${a + 1}*(${b + 1}+${c + 1})")
                            return@third true
                        }
                        return@third false
                    }
                }
            }) {
        return
    }

    val min = IntStream.range(0, n).filter { IntStream.range(0, n).filter { j: Int -> it != j }.allMatch { j: Int -> !G[it][j] } }.findFirst().asInt

    val max = getImpl(0, 0)

    val rA = Stream.iterate(0, { it + 1 }).limit(n.toLong()).map { getImpl(it, min) }.collect(Collectors.toList())

    if (IntStream.range(0, n).anyMatch {
                if (sup[it][rA[it]] != max) {
                    print("Не булева алгебра: ${it + 1}+~${it + 1}")
                    return@anyMatch true
                }
                return@anyMatch false
            }) {
        return
    }

    print("Булева алгебра")
}