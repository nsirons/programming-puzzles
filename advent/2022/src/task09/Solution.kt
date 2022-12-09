package task09;

import java.io.File
import kotlin.math.sign

fun readInput(name: String) = File("src/task09", "$name")
        .readLines().map { it.split(' ') }

fun dist(a: List<Int>, b: List<Int>): Int {
    return (a[0] - b[0]) * (a[0] - b[0]) + (a[1] - b[1]) * (a[1] - b[1])
}

fun main() {
    fun solution(input: List<List<String>>, tailNumber: Int): Int {
        val rope = MutableList(tailNumber + 1) { _ -> mutableListOf(0, 0) };
        val charToMove: Map<String, List<Int>> = mapOf(
                "R" to listOf(1, 0),
                "L" to listOf(-1, 0),
                "U" to listOf(0, 1),
                "D" to listOf(0, -1)
        )
        val visited: MutableSet<List<Int>> = mutableSetOf();
        for (line in input) {
            val move = charToMove[line[0]]!!;
            for (c in 0 until line[1].toInt()) {
                for ((i, tail) in rope.withIndex()) {
                    if (i == 0) {
                        rope[i][0] += move[0];
                        rope[i][1] += move[1];
                        continue;
                    }
                    val head = rope[i - 1];
                    if (dist(head, tail) > 2) {
                        rope[i][0] += (head[0] - tail[0]).sign
                        rope[i][1] += (head[1] - tail[1]).sign
                    }
                }
                visited.add(rope.last().toList())
            }
        }
        return visited.size;
    }

    val input = readInput("test.in")
    println(solution(input, 1))
    println(solution(input, 9))
}