package task04;

import java.io.File

fun readInput(name: String): List<String> = File("src/task04", "$name")
        .readLines()

fun part1(input: List<List<List<Int>>>): Int {
    return input.map {
        if ((it[0][0] <= it[1][0] && it[0][1] >= it[1][1]) || (it[1][0] <= it[0][0] && it[1][1] >= it[0][1])) 1 else 0
    }.sum()
}

fun part2(input: List<List<List<Int>>>): Int {
    return input.map { if (it[0][0] > it[1][1] || it[1][0] > it[0][1]) 0 else 1 }.sum()
}

fun main() {
    val input = readInput("test.in").map { it.split(",").map { it.split("-").map { it.toInt() } } }
    println(part1(input))
    println(part2(input))
}
