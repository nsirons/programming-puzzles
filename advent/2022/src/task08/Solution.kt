package task08

import java.io.File


fun readInput(name: String): List<List<Int>> = File("src/task08", "$name")
        .readLines().map { it.strip().map { it - '0' } }

fun getMaxGrid(input: List<List<Int>>, h: Int, w: Int, dir: Boolean, transpose: Boolean): Array<Array<Int>> {
    var grid = Array(h) { Array(w) { 0 } }
    for (i in 0 until h) {
        var curMax = -1;
        for (j in if (dir) IntRange(0, w - 1) else IntRange(0, w - 1).reversed()) {
            if (!transpose) grid[i][j] = curMax else grid[j][i] = curMax;
            curMax = maxOf(curMax, if (!transpose) input[i][j] else input[j][i])
        }
    }
    return grid
}

fun part1(input: List<List<Int>>): Int {
    val h = input.size
    val w = input[0].size
    var ans = 0
    val left = getMaxGrid(input, h, w, dir = true, transpose = false)
    val right = getMaxGrid(input, h, w, dir = false, transpose = false)
    val top = getMaxGrid(input, w, h, dir = true, transpose = true)
    val bot = getMaxGrid(input, w, h, dir = false, transpose = true)

    for (i in 0 until h) {
        for (j in 0 until w) {
            if ((input[i][j] > left[i][j]) || (input[i][j] > right[i][j]) || (input[i][j] > top[i][j]) || (input[i][j] > bot[i][j])) {
                ans += 1
            }
        }
    }
    return ans
}

fun part2(input: List<List<Int>>): Int {
    val h = input.size
    val w = input[0].size
    var ans = 0
    for (i in 0 until h) {
        for (j in 0 until w) {
            var score = 1;
            for ((dx, dy) in listOf(listOf(0, 1), listOf(1, 0), listOf(0, -1), listOf(-1, 0))) {
                var x = i + dx
                var y = j + dy
                var curScore = 0
                while (x >= 0 && y >= 0 && x < h && y < w) {
                    curScore += 1
                    if (input[x][y] >= input[i][j]) break;
                    x += dx
                    y += dy
                }
                score *= curScore
            }
            ans = maxOf(ans, score)
        }
    }
    return ans;
}

fun main() {
    val input = readInput("test.in")
    println(part1(input))
    println(part2(input))
}
