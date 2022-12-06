package task05;

import java.io.File

fun readInput(name: String): List<String> = File("src/task05", "$name")
        .readLines()

fun solution(crates_lst: ArrayList<ArrayList<Char>>, moves: List<List<Int>>, isReverse: Boolean): String {
    for (move in moves) {
        var tmpList = (1..move[0]).map {
            crates_lst[move[1] - 1].removeLast();
        }
        for (crate in if (isReverse) tmpList.reversed() else tmpList) {
            crates_lst[move[2] - 1].add(crate);
        }
    }
    return crates_lst.map { it.last() }.joinToString("");
}

fun main() {
    val input = readInput("test.in");
    val crates = input.takeWhile { it[1] != '1' }.map { it.slice(1..it.length step 4) }

    val moves = input.mapNotNull { it.replace("move ", "").replace(" from ", ",").replace(" to ", ",").split(",").mapNotNull { it.toIntOrNull() } }.filter { it.size > 0 }  // if "move"
    val cratesList: ArrayList<ArrayList<Char>> = ArrayList();
    (1..crates.map { it.length }.max()).forEach() {
        cratesList.add(ArrayList())
    }

    for (line in crates.reversed()) {
        (line.indices).forEach() {
            if (!line[it].isWhitespace()) cratesList[it].add(line[it]) else null;
        }
    }

    println(solution(ArrayList(cratesList.map { ArrayList(it) }), moves, false))
    println(solution(ArrayList(cratesList.map { ArrayList(it) }), moves, true))

}