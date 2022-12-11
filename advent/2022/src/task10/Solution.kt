package task10;

import java.io.File
import kotlin.math.abs


fun readInput(name: String) = File("src/task10", "$name")
        .readLines().map { if (it.equals("noop")) listOf(0) else listOf(0, it.split(' ')[1].toInt()) }.flatten()


fun part1(input: List<Int>): Int {
    var x = 1;
    var cycleCounter = 20;
    var ans = 0
    for ((idx, v) in input.withIndex()) {
        if (idx + 1 == cycleCounter) {
            ans += cycleCounter * x
            cycleCounter += 40
        }
        x += v
    }
    return ans
}

fun part2(input: List<Int>) {
    var x = 1;
    for ((idx, v) in input.withIndex()) {
        val i = idx % 40
        if (abs(x-i) < 2) print("#")
        else print(' ')
        if (i == 39) println()
        x += v
    }
}

fun main() {
    val input = readInput("test.in")
    println(part1(input))
    part2(input)
}