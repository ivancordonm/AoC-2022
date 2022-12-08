fun main() {

    fun part1(input: List<String>): Int {

        val matrix = input.map { it.map { elem -> elem.digitToInt() } }
        val transpose = List(matrix.first().size) { index -> matrix.map { it[index] } }

        var total = input.size * 4 - 4

        for (i in 1..input.size - 2)
            for (j in 1..input.size - 2)
                if (transpose[j].take(i).max() < matrix[i][j] ||
                    transpose[j].takeLast(input.size - i - 1).max() < matrix[i][j] ||
                    matrix[i].take(j).max() < matrix[i][j] ||
                    matrix[i].takeLast(input.size - j - 1).max() < matrix[i][j])

                    total++
        return total
    }


    fun part2(input: List<String>): Int {

        fun countTreesLower(current: Int, trees: List<Int>): Int {
            var count = 0
            for(tree in trees) {
                count++
                if(current <= tree) break
            }
            return count
        }

        val matrix = input.map { it.map { elem -> elem.digitToInt() } }
        val transpose = List(matrix.first().size) { index -> matrix.map { it[index] } }

        var maxTrees = 0

        for (i in 1..input.size - 2)
            for (j in 1..input.size - 2) {
                var partialMaxTrees  = countTreesLower(matrix[i][j],transpose[j].take(i).reversed())
                partialMaxTrees *=  countTreesLower(matrix[i][j],transpose[j].takeLast(input.size - i - 1))
                partialMaxTrees *=  countTreesLower(matrix[i][j],matrix[i].take(j).reversed())
                partialMaxTrees *=  countTreesLower(matrix[i][j],matrix[i].takeLast(input.size - j - 1))
                if(partialMaxTrees > maxTrees) maxTrees = partialMaxTrees
            }
        return maxTrees
    }

    val testInput = readInput("Day08_test")
    val input = readInput("Day08")

//    part1(testInput)
    println(part1(testInput))
    check(part1(testInput) == 21)
    println(part1(input))

    println(part2(testInput))
    check(part2(testInput) == 8)
    println(part2(input))
}
