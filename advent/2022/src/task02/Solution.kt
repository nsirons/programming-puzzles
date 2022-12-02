package task02;

import java.io.File

fun readInput(name: String): List<String> = File("src/task02", "$name")
        .readLines()

fun main() {
    fun part1(input: List<List<Int>>): Int {
        return input.map {
            (opp, me) -> me+1 + 3*(me-opp+1).mod(3)
        }.sum();
    }

    fun part2(input: List<List<Int>>): Int {
        return input.map {
            (opp, me) -> (opp-1+me).mod(3)+1 + 3*me
        }.sum();
    }

    val input = readInput("test.in").mapNotNull {
        if (!it.isBlank()) listOf(it[0] - 'A', it[2] - 'X') else null
    };

    println(part1(input))
    println(part2(input))
}