import java.io.File

fun readInput(name: String) = File("src/task01", "$name")
        .readText()

fun main() {
    fun part1(input: List<Int>): Int {
        return input.max();
    }

    fun part2(input: List<Int>): Int {
        return input.sortedDescending().take(3).sum();
    }

    val input = readInput("test.in").split("\n\n").map {
        it.split('\n').mapNotNull {
            it.toIntOrNull()
        }.sum()
    };
    println(part1(input))
    println(part2(input))
}