import java.util.*

fun main() {

    data class Element(val n: Int?, val p: List<Element>?)

    fun tree(stringTree: String): List<Element> {
        val stack = Stack<MutableList<Element>>()
        val root = mutableListOf<Element>()
        var node = root

        for (s in stringTree) {
            when (s) {
                '[' -> {
                    val newNode = mutableListOf<Element>()
                    node.add(Element(null, newNode))
                    stack.push(node)
                    node = newNode
                }

                ']' -> node = stack.pop()
                ',' -> {}
                else -> node.add(Element(s.digitToInt(), null))
            }
        }

        return root
    }

//    fun List<Element>.isLower(other: List<Element>) : Boolean {
//
//        val thisStack: Stack<Element> = Stack()
//        val otherStack: Stack<Element> = Stack()
//
//        this.forEach{thisStack.push(it)}
//        other.forEach{otherStack.push(it)}
//
//        while (thisStack.isNotEmpty() && otherStack.isNotEmpty()) {
//            val thisNode = thisStack.pop()
//            val otherNode = otherStack.pop()
//            if(thisNode.n != null && otherNode.n != null) if(thisNode.n > otherNode.n) return false
//            else if(thisNode.n != null && otherNode.n == null) {
//                if(otherNode.p.count{})
//            }
//
//            if (thisNode.p != null && thisNode.p.count{it.n == null} == 0) {
//
//            }
//            else if(thisNode.p != null && otherNode.p != null) {
//                thisNode.p.forEach { thisStack.push(it) }
//                otherNode.p.forEach { otherStack.push(it) }
//            } else if(thisNode.)
//        }
//
//        return true
//    }

    fun inOrder(root: List<Element>): List<List<Int>> {
        val ordered = mutableListOf<List<Int>>()
        val stack: Stack<Element> = Stack()
        root.forEach{stack.push(it)}
        while (stack.isNotEmpty()) {
            val node = stack.pop()
            if(node.p.isNullOrEmpty()) ordered.add(if(node.n == null) listOf() else listOf(node.n))
            else {
                if(node.p.count { it.p == null } == node.p.size) ordered.add(node.p.map { it.n!! } )
                else(node.p.forEach { stack.push(it) })
            }
        }

        return ordered.reversed()
    }

    fun List<Element>.emptyDepth(): Int {
        var d = 1
        var node = this.first()
        while(node.p != null && node.p!!.isNotEmpty()) {
            d++
            node = node.p?.first()!!
        }

        return d

    }

    fun List<List<Int>>.isLower(other: List<List<Int>>): Boolean{
        for(idx in 0..listOf(this.lastIndex, other.lastIndex).min()) {
            val elem1 = this[idx]
            val elem2 = other[idx]
            for (i in 0..listOf(elem1.lastIndex, elem2.lastIndex).min()) {
                if(elem1[i] > elem2[i]) return false
                else if (elem1[i] < elem2[i]) return true
            }
            if(elem1.size > elem2.size) return false
        }
        if(this.size > other.size) return false
        return true
    }



    fun part1(input: List<String>): Int {

        val right = mutableListOf<Int>()

        for ((index,g) in input.withIndex()) {
            val pairs = g.split("\n").map { it.trim().drop(1).dropLast(1) }

            println("${pairs[0]} - ${pairs[1]}")

            val tree1 = tree(pairs[0])
            val tree2 = tree(pairs[1])

            val in1 = inOrder(tree1)
            val in2 = inOrder(tree2)

            println(in1)
            println(in2)

            if(in1.isEmpty() && in2.isNotEmpty()) right.add(index+1)
            else if(in1.first().isEmpty() && in2.first().isEmpty()) {
                if (tree1.emptyDepth() < tree2.emptyDepth()) {
                    right.add(index + 1)
                }
            }
            else if(in1.isLower(in2)) right.add(index+1)
//            println(right)

            if(index -1 in right) println("true") else println("false")
            println()
            println()
            println()



        }

        return right.sum()

    }


    fun part2(input: List<String>): Int {
        TODO()
    }


    val testInput = readInputByGroups("Day13_test")
    val input = readInputByGroups("Day13")


//    check(part1(testInput) == 13)
//    println(part1(testInput))
    println(part1(input))

//    println(part2(testInput))
//    check(part2(testInput) == 100)
//    println(part2(input))

}
