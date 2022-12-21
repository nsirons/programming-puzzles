package task21;

import java.io.File


val graph = mutableMapOf<String, List<String>>()

fun readInput(name: String) = File("src/task21", "$name").readLines().map{ it.split(':')}

fun dfs(node: String): Long {
    val operation = graph[node]
    when (operation?.get(2)) {
        "None" -> return operation[0].toLong()
        "+" -> return dfs(operation[0]) + dfs(operation[1])
        "-" -> return dfs(operation[0]) - dfs(operation[1])
        "*" -> return dfs(operation[0]) * dfs(operation[1])
        "/" -> return dfs(operation[0]) / dfs(operation[1])
    }
    return 0
}


fun main() {
    for ((name, expr) in readInput("test.in")) {
        if (expr.strip().toIntOrNull() == null) {
            for (op in listOf("+", "-", "*" , "/")) {
                if (expr.contains(op)) {
                    val sp = expr.split(op).map { it.strip() }
                    graph[name] = listOf(sp[0], sp[1], op)
                    break
                }
            }
        }
        else {
            graph[name] = listOf(expr.strip(), "0", "None")
        }
    }

    println(dfs("root"))  // part1
    var l = 0L
    var r: Long = 10e14.toLong() // maybe needs to be changed
    val matchValue = dfs(graph["root"]!![1])
    while (l < r) {
        val mid = l + (r - l) / 2
        graph["humn"] = listOf(mid.toString(), "0", "None")
        val resp = dfs(graph["root"]!![0]) // 3759566892641
        if (resp == matchValue) {
            println("FOUND: $mid") // part2
        }
        // check where humn is used and which sign, if '-' then use >=, if '+' then use  <=
        if (resp <= matchValue) r = mid - 1
        else l = mid + 1
    }
}