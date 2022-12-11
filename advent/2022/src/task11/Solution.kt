package task11;

import java.io.File

class Monkey(input: List<String>) {
    val itemsOriginal = input[1].replace("  Starting items: ", "").split(", ").map { it.toLong() }
    var items = ArrayDeque<Long>();
    val oper = input[2][23]
    val operVal = input[2].split(oper.toString() + " ")[1].toLongOrNull();
    val testDiv = input[3].split("by ")[1].toLong()
    val throwToMonkeyA = input[4].split("monkey ")[1].toInt();
    val throwToMonkeyB = input[5].split("monkey ")[1].toInt();

    init {
        reset()
    }

    fun throwItems(lmbd: (Long) -> Long): MutableList<List<Long>> {
        val ans: MutableList<List<Long>> = mutableListOf();
        while (!items.isEmpty()) {
            val old: Long = items.removeFirst();
            var new: Long = 0
            when (oper) {
                '+' -> new = if (operVal != null) old + operVal else old + old
                '*' -> new = if (operVal != null) old * operVal else old * old
                else -> throw java.lang.Exception();
            }
            new = lmbd(new)
            ans += listOf(if (new % testDiv == 0L) throwToMonkeyA.toLong() else throwToMonkeyB.toLong(), new)
        }
        return ans
    }

    fun reset() {
        items = ArrayDeque();
        items.addAll(itemsOriginal);
    }
}

fun readInput(name: String) = File("src/task11", "$name").readLines().chunked(7).map { Monkey(it) }

fun solution(input: List<Monkey>, roundNumber: Int, lmbd: (Long) -> Long): Long {
    val ans = List(input.size) { 0L }.toMutableList()
    for (i in 0 until roundNumber) {
        for ((idx, monkey) in input.withIndex()) {
            for ((toMonkey, newItem) in monkey.throwItems(lmbd)) {
                ans[idx]++
                input[toMonkey.toInt()].items.addLast(newItem.toLong())
            }
        }
    }
    return ans.sorted().takeLast(2).reduce { acc, i -> acc * i }
}


fun main() {
    val input = readInput("test.in")
    val mod = input.map { it.testDiv }.reduce { acc, i -> acc * i }
    println(solution(input, 20, { x -> x / 3 }))
    input.forEach { it.reset() }
    println(solution(input, 10000, { x -> x % mod }))
}
