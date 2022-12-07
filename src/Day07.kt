fun main() {

    fun dirSizes(commands: List<String>): Map<String, Int> {

        val sizes = mutableMapOf<String, Int>()

        var cwd = ""
        for (command in commands) {
            val splitCommand = command.split(" ")
            if (splitCommand[1] == "cd") {
                cwd = when (splitCommand[2]) {
                    ".." -> cwd.substringBeforeLast("/")
                    "/" -> "/"
                    else -> "$cwd/${splitCommand[2]}"
                }
            } else if ("\\d+".toRegex().matches(splitCommand[0])) {
                val sz = splitCommand[0].toInt()
                var dir = cwd
                while (true) {
                    sizes[dir] = sizes.getOrElse(dir) { 0 } + sz
                    if (dir == "/") break
                    dir = dir.substringBeforeLast("/")
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
        val rescue = 30000000 - 70000000 + sizes.getValue("/")

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
