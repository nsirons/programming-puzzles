package task03;

import java.io.File

fun readInput(name: String): List<String> = File("src/task03", "$name")
        .readLines()

fun part1(input: List<String>): Int {
    return input.map {
        val a = it.subSequence(0, it.length / 2);
        val b = it.subSequence(it.length / 2, it.length).toSet();
        a.toSet().mapNotNull{it.takeIf{b.contains(it)}}.map{getCharScore(it) }.sum()
    }.sum()
}

fun part2(input: List<String>): Int {
    return (0..input.size-1 step 3).map {
        val a = input[it]; val b = input[it+1].toSet(); val c = input[it+2].toSet();
        a.toSet().mapNotNull{it.takeIf{b.contains(it) and c.contains(it)}}.map{ getCharScore(it) }.sum()
    }.sum()
}

fun getCharScore(it: Char) = if (it - 'a' >= 0) it - 'a' + 1 else it - 'A' + 27

fun main() {
    val input = readInput("test.in").map{it.trim()}.mapNotNull{it.takeIf {!it.isBlank()}}

    println(part1(input))
    println(part2(input))
}