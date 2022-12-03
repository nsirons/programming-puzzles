package task03;

import java.io.File

fun readInput(name: String): List<String> = File("src/task03", "$name")
        .readLines()

fun part1(input: List<String>): Int {
    return input.map {
        getCharScore(it.subSequence(it.length / 2, it.length).toSet().intersect(it.substring(0,it.length/2).toSet()).first());
    }.sum()
}

fun part2(input: List<String>): Int {
    return (0..input.size-1 step 3).map {
        getCharScore(input[it].toSet().intersect(input[it+1].toSet()).intersect(input[it+2].toSet()).first())
    }.sum()
}

fun getCharScore(it: Char) = if (it - 'a' >= 0) it - 'a' + 1 else it - 'A' + 27

fun main() {
    val input = readInput("test.in").map{it.trim()}.mapNotNull{it.takeIf {!it.isBlank()}}

    println(part1(input))
    println(part2(input))
}