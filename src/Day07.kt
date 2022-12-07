import java.util.*

fun main() {

    fun dirSizes(commands: List<String>): Map<String, Int> {

        val sizes = mutableMapOf<String, Int>()
        val stack = Stack<String>()

        for (command in commands) {
            val splitCommand = command.split(" ")
            when (splitCommand[1]) {
                "cd" -> {
                    if (splitCommand[2] == "..") stack.pop()
                    else {
                        stack.push(splitCommand[2])
                        sizes[splitCommand[2]] = 0
                    }
                }
                "ls" -> continue
                else -> {
                    if (splitCommand[0] != "dir") {
                        val sz = splitCommand[0].toInt()
                        for (dir in stack) sizes[dir] = sizes[dir]!! + sz
                    }
                }
            }
        }

//        println(sizes)
        return sizes.toMap()
    }


    fun part1(input: List<String>): Int {
        println(dirSizes(input).filter { it.value <= 100000 })

        return dirSizes(input).filter { it.value <= 100000 }.map { it.value }.sum()
    }


    fun part2(input: List<String>): Int {

        return 0
    }


    val testInput = readInput("Day07_test")
    val input = readInput("Day07")


    println(part1(testInput))
//    check(part1(testInput) == 95437)
    println(part1(input))

//    println(part2(testInput))
//    check(part2(testInput) == 95437)
//    println(part2(input))
}
