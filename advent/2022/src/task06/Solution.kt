package task06;

import java.io.File
import kotlin.math.max

fun readInput(name: String): List<String> = File("src/task06", "$name")
        .readLines()

fun solution(input: String, marker_size: Int): Int {
    return input.mapIndexedNotNull{i,_ -> if (input.subSequence(max(0,i+1-marker_size), i+1).toSet().size == marker_size) i+1 else null}.first();
}

fun main() {
    val input = readInput("test.in");

    println(solution(input[0], 4))
    println(solution(input[0], 14))

}