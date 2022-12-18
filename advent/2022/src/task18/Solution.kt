package task18;

import java.io.File

val MOVES = listOf(listOf(1,0,0), listOf(-1,0,0), listOf(0,1,0), listOf(0,-1,0), listOf(0,0,1), listOf(0,0,-1))
const val minDim = 0
const val maxDim = 20
val visited = mutableSetOf<List<Int>>()


fun readInput(name: String) = File("src/task18", "$name").readLines().map{it.split(',').map{it.toInt()}}

fun solution(grid: MutableMap<List<Int>, Boolean>): Int {
    var ans = 0;
    for (x in minDim..maxDim) {
        for (y in minDim..maxDim) {
            for (z in minDim..maxDim){
                if (grid.getOrDefault(listOf(x,y,z), false)) {
                    var sides = 6;
                    for ((dx,dy,dz) in MOVES) {
                        if (grid.getOrDefault(listOf(x+dx, y+dy, z+dz), false)) {
                            sides--
                        }
                    }
                    ans += sides;
                }
            }
        }
    }
    return ans
}

fun dfs(x: Int,y: Int, z: Int, grid: MutableMap<List<Int>, Boolean>): Boolean {
    visited.add(listOf(x,y,z))
    if (x < minDim || y < minDim || z < minDim || x > maxDim || y > maxDim || z > maxDim) {
        return false
    }
    for ((dx,dy,dz) in MOVES) {
        if (!visited.contains(listOf(x+dx, y+dy, z+dz)) && (!grid.containsKey(listOf(x+dx, y+dy, z+dz)) || !grid.getOrDefault(listOf(x+dx, y+dy, z+dz),true))) {
            if (!dfs(x+dx, y+dy, z+dz, grid)) return false
        }
    }
    return true
}

fun main() {
    val input = readInput("test.in")
    val grid = mutableMapOf<List<Int>, Boolean>()
    for ((x,y,z) in input) {
        grid[listOf(x,y,z)] = true;
    }
    println(solution(grid))

    for (x in minDim..maxDim) {
        for (y in minDim..maxDim) {
            for (z in minDim..maxDim) {
                if (!grid.containsKey(listOf(x,y,z))) {
                    val resp = dfs(x,y,z, grid)
                    for (coords in visited) {
                        grid[coords] = resp;
                    }
                    visited.clear()
                }
            }
        }
    }
    println(solution(grid))
}


