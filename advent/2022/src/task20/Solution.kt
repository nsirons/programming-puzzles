package task20;

import java.io.File


fun readInput(name: String) = File("src/task20", "$name").readLines().mapIndexed { idx, it -> listOf(it.toLong(), idx.toLong()) }


fun simulation(input: List<List<Long>>): List<List<Long>> {
    val ans = ArrayDeque<List<Long>>(input.size)
    for (elem in input) ans.addLast(elem)

    var cnt = 0L
    while (cnt < input.size) {
        val elem = ans.removeFirst()
        if (elem[1] != cnt) {
            ans.addLast(elem)
            continue
        }
        ans.add((elem[0].mod(input.size - 1)), elem)
        cnt++
    }
    return ans.toList()
}


fun getAnswer(arr: List<List<Long>>): Long {
    val idx = arr.map { it[0] }.indexOf(0L)
    return (1000..3000 step 1000).map { arr[(idx + it).mod(arr.size)][0] }.sum()
}


fun solution(input: List<List<Long>>, n: Int, mult: Long): Long {
    var finalList = input.map { listOf(it[0] * mult, it[1]) }
    for (i in 0 until n) {
        finalList = simulation(finalList)
    }
    return getAnswer(finalList)
}


fun main() {
    val input = readInput("test.in")
    println(solution(input, 1, 1L))
    println(solution(input, 10, 811589153L))
}
