import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Collectors
import java.util.stream.Stream

var n = 0
var G = mutableListOf<MutableList<Boolean>>()
var _G = mutableListOf<MutableList<Boolean>>()
var sup = mutableListOf<MutableList<Int>>()
var inf = mutableListOf<MutableList<Int>>()
var impl = mutableListOf<MutableList<Int>>()
var used = mutableListOf<Boolean>()

fun dfs(k: Int) {
    used[k] = true
    for (i in 0 until n) {
        if (G[k][i] && !used[i]) {
            dfs(i)
        }
    }
}

fun get_sup(l: Int, r: Int): Int {
    val conc = mutableListOf<Boolean>()
    for (i in 0 until n) {
        conc.add(_G[l][i] && _G[r][i])
    }
    var k = true
    for (i in 0 until n) {
        if (conc[i]) {
            k = false
            break
        }
    }

    if (k) {
        return -1
    }

    var kk = -1
    for (i in 0 until n) {
        if (conc[i]) {
            var check = false
            for (j in 0 until n) {
                if (i == j) {
                    continue
                }
                if (G[i][j] && conc[j]) {
                    check = true
                    break
                }
            }
            if (!check) {
                if (kk == -1) {
                    kk = i
                } else {
                    return -1
                }
            }
        }
    }

    return kk
}

fun get_inf(l: Int, r: Int): Int {
    val conc = mutableListOf<Boolean>()
    for (i in 0 until n) {
        conc.add(G[l][i] && G[r][i])
    }
    var k = true
    for (i in 0 until n) {
        if (conc[i]) {
            k = false
            break
        }
    }

    if (k) {
        return -1
    }

    var kk = -1
    for (i in 0 until n) {
        if (conc[i]) {
            var check = false
            for (j in 0 until n) {
                if (i == j) {
                    continue
                }
                if (_G[i][j] && conc[j]) {
                    check = true
                    break
                }
            }
            if (!check) {
                if (kk == -1) {
                    kk = i
                } else {
                    return -1
                }
            }
        }
    }

    return kk
}

fun get_impl(a: Int, b: Int): Int {
    val conc = mutableListOf<Boolean>()
    for (c in 0 until n) {
        if (G[b][inf[a][c]]) {
            conc.add(true)
        } else {
            conc.add(false)
        }
    }

    var k = true
    for (i in 0 until n) {
        if (conc[i]) {
            k = false
            break
        }
    }

    if (k) {
        return -1
    }

    var kk = -1
    for (i in 0 until n) {
        if (conc[i]) {
            var check = false
            for (j in 0 until n) {
                if (i == j) {
                    continue
                }
                if (_G[i][j] && conc[j]) {
                    check = true
                    break
                }
            }
            if (!check) {
                if (kk == -1) {
                    kk = i
                } else {
                    return -1
                }
            }
        }
    }

    return kk
}


fun main(args: Array<String>) {
    val path = Paths.get("input.txt")
//    val path = Paths.get("src/in.txt")

    val list = Files
            .lines(path)
            .filter { !it.isEmpty() }
            .map {
                it.split(" ")
                        .stream()
                        .filter { !it.isEmpty() }
                        .map { it.toInt() }
                        .collect(Collectors.toList())
            }
            .collect(Collectors.toList())

    n = list[0][0]

    G = Stream.generate { Stream.generate { false }.limit(n.toLong()).collect(Collectors.toList()) }.limit(n.toLong()).collect(Collectors.toList())
    _G = Stream.generate { Stream.generate { false }.limit(n.toLong()).collect(Collectors.toList()) }.limit(n.toLong()).collect(Collectors.toList())
    sup = Stream.generate { Stream.generate { -1 }.limit(n.toLong()).collect(Collectors.toList()) }.limit(n.toLong()).collect(Collectors.toList())
    inf = Stream.generate { Stream.generate { -1 }.limit(n.toLong()).collect(Collectors.toList()) }.limit(n.toLong()).collect(Collectors.toList())
    impl = Stream.generate { Stream.generate { -1 }.limit(n.toLong()).collect(Collectors.toList()) }.limit(n.toLong()).collect(Collectors.toList())

    for (i in 1 until list.size) {
        for (j in 0 until list[i].size) {
            G[list[i][j] - 1][i - 1] = true
            _G[i - 1][list[i][j] - 1] = true
        }
    }

    for (i in 0 until n) {
        used = Stream.generate { false }.limit(n.toLong()).collect(Collectors.toList())
        dfs(i)
        for (j in 0 until n) {
            if (used[j]) {
                G[i][j] = true
                _G[j][i] = true
            }
        }
    }

    // a*(b+c)=a*b+a*c
    for (i in 0 until n) {
        for (j in 0 until n) {
            if (sup[i][j] == -1) {
                sup[i][j] = get_sup(i, j)
                if (sup[i][j] == -1) {
                    print("Операция '+' не определена: ${i + 1}+${j + 1}")
                    return
                }
                sup[j][i] = sup[i][j]
            }
        }
    }

    for (i in 0 until n) {
        for (j in 0 until n) {
            if (inf[i][j] == -1) {
                inf[i][j] = get_inf(i, j)
                if (inf[i][j] == -1) {
                    print("Операция '*' не определена: ${i + 1}*${j + 1}")
                    return
                }
                inf[j][i] = inf[i][j]
            }
        }
    }

    for (a in 0 until n) {
        for (b in 0 until n) {
            for (c in 0 until n) {
                if (inf[a][sup[b][c]] != sup[inf[a][b]][inf[a][c]]) {
                    print("Нарушается дистрибутивность: ${a + 1}*(${b + 1}+${c + 1})")
                    return
                }
            }
        }
    }

    //a->b
    for (a in 0 until n) {
        for (b in 0 until n) {
            impl[a][b] = get_impl(a, b)
            if (impl[a][b] == -1) {
                print("Операция '->' не определена: ${a + 1}->${b + 1}")
                return
            }
        }
    }

    //a+~b
    var min = -1
    for (i in 0 until n) {
        var q = false
        for (j in 0 until n) {
            if (i == j) {
                continue
            }
            if (G[i][j]) {
                q = true
                break
            }
        }
        if (!q) {
            min = i
            break
        }
    }

    val max = get_impl(0, 0)

    val _a = mutableListOf<Int>()
    for (i in 0 until n) {
        _a.add(get_impl(i, min))
    }

    for (i in 0 until n) {
        if (sup[i][_a[i]] != max) {
            print("Не булева алгебра: ${i + 1}+~${i + 1}")
            return
        }
    }

    print("Булева алгебра")

    return
}