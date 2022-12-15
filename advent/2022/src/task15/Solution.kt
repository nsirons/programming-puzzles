package task15;

import java.io.File
import kotlin.math.absoluteValue

fun readInput(name: String) = File("src/task15", "$name").readLines().map { it.strip().replace("Sensor at x=", "").replace(" y=", "").replace(": closest beacon is at x=", ",").split(',').map { it.toInt() } }

fun getSegments(input: List<List<Int>>, row: Int, isDimY: Boolean): List<List<Int>> {
    val segment = mutableListOf<List<Int>>()
    for ((sx,sy,bx,by) in input) {
        val maxDist = (sx-bx).absoluteValue + (sy-by).absoluteValue
        val sensorDim = if (isDimY) sy else sx
        val sensorDimOpp = if (isDimY) sx else sy
        val distToRow = (sensorDim-row).absoluteValue
        val d = maxDist - distToRow
        if (d >= 0) {
            segment.add(listOf(sensorDimOpp-d, sensorDimOpp+d+1))
        }
    }
    return segment.sortedBy { it[0] }
}

fun segmentHasEmptySpace(segment: List<List<Int>>): Boolean {
    var curS = Int.MIN_VALUE;
    var curE = Int.MIN_VALUE;
    for ((s,e) in segment) {
        if (e < 0) continue
        curS = maxOf(curS, maxOf(s,0))
        if ((curS > curE) && (curE != Int.MIN_VALUE)) {
            return true
        }
        curE = maxOf(curE, e)
    }
    return false
}


fun part1(input: List<List<Int>>, findY: Int): Int {
    val segment = getSegments(input, findY, true);
    var start = segment.first()[0]
    var end = segment.first()[1]
    var ans = 0
    for ((startSeg, endSeg) in segment) {
        if (startSeg > end) {
            ans += end-start-1;
            start = startSeg;
        }
        end = maxOf(end, endSeg)
    }
    ans += end-start - 1;
    return ans
}

fun part2(input: List<List<Int>>, limit: Int): Long {
    var ans: Long = 0;
    for (i in 0..limit ) {
        val segmentX = getSegments(input, i, true);
        val segmentY = getSegments(input, i, false);
        if (segmentHasEmptySpace(segmentX)) {
            println("X: " + i.toString())
        }
        if (segmentHasEmptySpace(segmentY)) {
            println("Y: " + i.toString())
        }
        ans += if (segmentHasEmptySpace(segmentY)) 4000000L * i.toLong() else 0
        ans += if (segmentHasEmptySpace(segmentX)) 1L * i.toLong() else 0
    }
    return ans
}

fun main() {
    val input = readInput("test.in")
    println(part1(input, 2000000))
    println(part2(input, 4000000))
}