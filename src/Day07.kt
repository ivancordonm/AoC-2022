import java.util.*

fun main() {

    fun List<String>.path() = fold("") { acc, elem -> "$acc/$elem" }

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
                        val idx = stack.toList().path()
                        sizes[idx] = 0
                    }
                }

                "ls" -> continue
                else -> {
                    if (splitCommand[0] != "dir") {
                        val sz = splitCommand[0].toInt()
                        for (i in 1..stack.size) {
                            val idx = stack.take(i).path()
                            sizes[idx] = sizes[idx]!! + sz
                        }
                    }
                }
            }
        }

        return sizes.toMap()
    }


    fun part1(input: List<String>): Int {

        return dirSizes(input).filter { it.value <= 100000 }.map { it.value }.sum()
    }



    fun part2(input: List<String>): Int {

        val sizes = dirSizes(input)
        val rescue = 30000000 - 70000000 + sizes["//"]!!

        return (sizes.toList().filter { it.second >= rescue }.minByOrNull { it.second }!!.second)
    }

    val testInput = readInput("Day07_test")
    val input = readInput("Day07")

    println(part1(testInput))
    check(part1(testInput) == 95437)
    println(part1(input))

    println(part2(testInput))
    check(part2(testInput) == 24933642)
    println(part2(input))
}
