package task07

import java.io.File
import kotlin.math.min

class Node(dirName: String, root: Node?) {
    val dirName = dirName
    val root = root
    val nextNodes: MutableList<Node> = mutableListOf()
    val files: MutableMap<String, Int> = mutableMapOf()
    var totalSize = 0
}

fun readInput(name: String): List<String> = File("src/task07", "$name")
        .readLines().map { it.strip() }

val input = readInput("test.in")

fun dfsRead(i: Int, prevDir: Node) {
    var i = i
    var prevDir = prevDir

    while (i < input.size && input[i].equals("$ cd ..")) {
        prevDir = prevDir.root!!
        i++
    }
    if (i >= input.size) return
    // pick "$ cd <dirName>"
    var curDir = Node(input[i].substring(5), prevDir)
    prevDir.nextNodes.add(curDir)
    i += 2 // +1 for cd, +1 for $ls

    // read all files/dir under ls, until next command
    while (i < input.size && input[i][0] != '$') {
        // only files starts with digits
        if (input[i][0].isDigit()) {
            val fileLine = input[i].split(' ')
            curDir.files[fileLine[1]] = fileLine[0].toInt()
        }
        i++
    }
    dfsRead(i, curDir)
}

fun dfsDirMemory(curDir: Node): Int {
    var curMemorySize = curDir.files.values.sum()
    for (nextDir in curDir.nextNodes) {
        curMemorySize += dfsDirMemory(nextDir)
    }
    curDir.totalSize = curMemorySize
    return curMemorySize
}

fun part1(root: Node): Int {
    var ans = if (root.totalSize <= 100000) root.totalSize else 0

    for (nextNode in root.nextNodes) {
        ans += part1(nextNode)
    }
    return ans
}

fun part2(curNode: Node, threshold: Int): Int {
    var ans = if (curNode.totalSize >= threshold) curNode.totalSize else Int.MAX_VALUE
    for (nextNode in curNode.nextNodes) {
        ans = min(ans, part2(nextNode, threshold))
    }
    return ans
}

fun main() {
    val root = Node("rootOfAll", null)
    dfsRead(0, root)
    dfsDirMemory(root);
    println(part1(root))
    println(part2(root, root.totalSize - 40000000))
}