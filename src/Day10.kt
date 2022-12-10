fun main() {

    fun part1(input: List<String>): Int {

        var x = 1
        var cycle = 1
        var total = 0
        for(line in input) {
            val command = line.trim().split(" ")
            if(command.first() == "addx") {
                for(i in 1..2) {
                    if (i == 2) x += command.last().toInt()
                    cycle++
                    if(cycle in listOf(20, 60, 100, 140,180, 220)) total += cycle * x
                }
            } else {
                cycle++
                if(cycle in listOf(20, 60, 100, 140,180, 220)) total += cycle * x
            }

        }
        return total
    }

    fun printSprite(cycle: Int, x: Int) {
        val position = cycle.mod(40) - 1
        if(cycle.mod(40) == 0) println()
        if(position in x-1..x+1) print("# ") else print(". ")
    }


    fun part2(input: List<String>){

        var x = 1
        var cycle = 1
        for(line in input) {
            val command = line.trim().split(" ")
            if(command.first() == "addx") {
                for(i in 1..2) {
                    printSprite(cycle, x)
                    if (i == 2) x += command.last().toInt()
                    cycle++
                }
            } else {
                printSprite(cycle, x)
                cycle++
            }
        }
    }


    val testInput = readInput("Day10_test")
    val input = readInput("Day10")

    println(part1(testInput))
    check(part1(testInput) == 13140)
    println(part1(input))

    part2(testInput)
    println()
    part2(input)

}
