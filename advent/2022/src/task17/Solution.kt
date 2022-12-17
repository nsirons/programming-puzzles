package task17

import java.io.File

fun readInput(name: String) = File("src/task17", "$name").readLines().get(0).map { if (it == '>') 1 else -1 }

const val WIDTH = 7

val tetris = mutableMapOf<List<Int>, Boolean>();
val blockList = listOf(
        listOf(listOf(0, 0), listOf(1, 0), listOf(2, 0), listOf(3, 0)),
        listOf(listOf(1, 0), listOf(0, 1), listOf(1, 1), listOf(2, 1), listOf(1, 2)),
        listOf(listOf(0, 0), listOf(1, 0), listOf(2, 0), listOf(2, 1), listOf(2, 2)),
        listOf(listOf(0, 0), listOf(0, 1), listOf(0, 2), listOf(0, 3)),
        listOf(listOf(0, 0), listOf(1, 0), listOf(0, 1), listOf(1, 1)),
)

fun isBlockMovable(block: List<List<Int>>, x: Int, y: Int, dx: Int, dy: Int): Boolean {
    for (rock in block) {
        var newX = rock[0] + x + dx;
        var newY = rock[1] + y + dy;
        if (newX <= 0 || newX > WIDTH || tetris.getOrDefault(listOf(newX, newY), false)) {
            return false
        }
    }
    return true;
}

fun solution(input: List<Int>, roundNumber: Long): Long {
    // initialize
    tetris.clear()
    for (x in 0..WIDTH) {
        tetris[listOf(x, 0)] = true
    }

    var height = 0
    var j = 0;

    var stateMemo = mutableMapOf<List<Int>, Int>()
    var heightList = mutableListOf<Int>()

    for (i in 0 until minOf(Int.MAX_VALUE.toLong(), roundNumber).toInt()) {
        var x = 3
        var y = height + 4
        var block = blockList[(i % blockList.size)]

        // 15 is an arbitary guess, could be bigger number for your input
        val floorState: List<Int> = (1..WIDTH).map { (0 until 15).map { itt -> if (tetris.getOrDefault(listOf(it, height - itt), false)) 1 else 0 } }.flatten()
        val state = floorState + listOf(i % 5, j % input.size)

        while (true) {
            // stream move
            val dx = input[j % input.size];
            if (isBlockMovable(block, x, y, dx, 0)) {
                x += dx
            }
            j++

            // move down
            if (isBlockMovable(block, x, y, 0, -1)) {
                y += -1
            } else {
                for (rock in block) {
                    val newX = rock[0] + x
                    val newY = rock[1] + y
                    tetris[listOf(newX, newY)] = true
                    height = maxOf(height, newY)
                }
                break
            }
        }

        heightList.add(height)

        if (state in stateMemo) {
            val iPrev = stateMemo.get(state)!!
            val heightDiff = (heightList[i] - heightList[iPrev]).toLong()
            val cyclesNumber = (roundNumber - (i + 1)) / (i - iPrev)
            val rem = ((roundNumber - (i + 1)) % (i - iPrev)).toInt()
            return height.toLong() + cyclesNumber * heightDiff + heightList[iPrev + rem] - heightList[iPrev]
        }

        stateMemo[state] = i
    }
    return height.toLong()
}

fun main() {
    var input = readInput("test.in")
    println(solution(input, 2022))
    println(solution(input, 1000000000000))
}