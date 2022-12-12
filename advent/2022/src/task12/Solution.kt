package task12

import java.io.File

fun readInput(name: String) = File("src/task12", "$name").readLines().map { it.strip().toMutableList() }

var visited = mutableMapOf<List<Int>, Int>();
var input = listOf<MutableList<Char>>();
var w = 0;
var h = 0;
var startx = 0;
var starty = 0;

fun part1(curx: Int, cury: Int, steps: Int): Int {
    if (curx == startx && cury == starty) return steps
    var ans = Int.MAX_VALUE;
    for ((dx, dy) in listOf(listOf(1, 0), listOf(-1, 0), listOf(0, 1), listOf(0, -1))) {
        val x = curx + dx
        val y = cury + dy
        if (x >= 0 && y >= 0 && x < h && y < w && visited.getOrDefault(listOf(x, y), Int.MAX_VALUE) > steps) {
            if (input[curx][cury] - input[x][y] <= 1) {
                visited[listOf(x, y)] = steps
                ans = minOf(ans, part1(x, y, steps + 1))
            }
        }
    }
    return ans
}

fun part2(startList: List<List<Int>>): Int {
    var ans = Int.MAX_VALUE;
    for (pos in startList) {
        ans = minOf(ans, visited.getOrDefault(pos, Int.MAX_VALUE - 1) + 1)
    }
    return ans
}

fun main() {
    input = readInput("test.in");
    h = input.size;
    w = input[0].size
    var endx = 0
    var endy = 0
    var startListPart2 = mutableListOf<List<Int>>()

    for (i in 0 until h) {
        for (j in 0 until w) {
            if (input[i][j] == 'S') {
                startListPart2.add(listOf(i, j))
                input[i][j] = 'a'
                startx = i
                starty = j
            } else if (input[i][j] == 'E') {
                input[i][j] = 'z'
                endx = i
                endy = j
            } else if (input[i][j] == 'a') {
                startListPart2.add(listOf(i, j))
            }
        }
    }
    println(part1(endx, endy, 0))
    println(part2(startListPart2))
}