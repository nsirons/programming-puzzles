package task14;

import java.io.File


fun readInput(name: String) = File("src/task14", "$name").readLines().map { it.strip().split(" -> ").map { it.split(",").map { it.toInt() } } }

val moves = listOf(listOf(1,0), listOf(1,-1), listOf(1,1))

fun part1(grid: Array<IntArray>, start: List<Int>): Int {
    var ans = 0;
    while (true) {
        var x = start[1];
        var y = start[0];
        val pair = simulation(y, x, grid)
        x = pair.first
        y = pair.second

        if (y==grid.size-1) return ans;
        grid[y][x] = 1
        ans++;
    }
}

private fun simulation(y: Int, x: Int, grid: Array<IntArray>): Pair<Int, Int> {
    var y1 = y
    var x1 = x
    var moving = true;
    while (moving) {
        moving = false
        for ((dy, dx) in moves) {
            if (y1 + dy >= 0 && x1 + dx >= 0 && y1 + dy < grid.size && x1 + dx < grid[0].size && grid[y1 + dy][x1 + dx] == 0) {
                y1 += dy
                x1 += dx
                moving = true;
                break;
            }
        }
    }
    return Pair(x1, y1)
}

fun part2(grid: Array<IntArray>, start: List<Int>): Int {
    var ans = 0;
    for (x in 0 until grid[0].size) {
        grid[grid.size-1][x] = 1;
    }

    while (true) {
        var x = start[1];
        var y = start[0];
        val pair = simulation(y, x, grid)
        x = pair.first
        y = pair.second
        grid[y][x] = 1
        ans++;
        if (y==start[0] && x==start[1]) return ans;
    }
}

fun createGrid(input: List<List<List<Int>>>, xsize: Int, ysize: Int): Array<IntArray>  {
    var grid = Array(ysize) { IntArray(xsize){0} }

    for (line in input) {
        var xPrev = -1;
        var yPrev = -1;
        for ((x,y) in line) {
            if (y == yPrev) {
                for (xx in minOf(x,xPrev)..maxOf(x,xPrev)) {
                    grid[y][xx] = 1;
                }
            }
            else if (x == xPrev) {
                for (yy in minOf(y, yPrev)..maxOf(y, yPrev)) {
                    grid[yy][x] = 1;
                }
            }
            xPrev = x;
            yPrev = y;
        }
    }
    return grid
}
fun main() {
    val input = readInput("test.in");
    var maxY = 0
    for (line in input) {
        for ((_, y) in line) {
            maxY = maxOf(maxY, y)
        }
    }

    println(part1(createGrid(input, 1000, maxY+3), listOf(0, 500)))
    println(part2(createGrid(input, 1000, maxY + 3), listOf(0, 500)))
}