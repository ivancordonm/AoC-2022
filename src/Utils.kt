import java.io.File
import java.math.BigInteger
import java.security.MessageDigest
import kotlin.math.abs
import kotlin.math.min


sealed class Item : Comparable<Item> {
    data class Values(
        val values: MutableList<Item>
    ) : Item() {

        override fun compareTo(other: Item): Int {
            when (other) {
                is Values -> {
                    for (i in 0..min(values.lastIndex, other.values.lastIndex)) {
                        val result = values[i].compareTo(other.values[i])
                        if (result != 0) return result
                    }
                    return values.lastIndex - other.values.lastIndex
                }

                is Value -> return compareTo(Values(mutableListOf(other)))
            }
        }
    }

    data class Value(
        val value: Int
    ) : Item() {

        override fun compareTo(other: Item): Int = when (other) {
            is Value -> this.value - other.value
            is Values -> Values(mutableListOf(this)).compareTo(other)
        }
    }
}
/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt")
    .readLines()

fun readInputByGroups(name: String) = File("src", "$name.txt")
    .readText().split("\r\n\r\n|\n\n".toRegex())

fun readInputToInt(name: String) = File("src", "$name.txt")
    .readLines().map { it.toInt() }

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')


fun Pair<Int, Int>.manhattan(other: Pair<Int, Int>) = abs(first - other.first) + abs(second - other.second)

operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>) = Pair(first + other.first, second + other.second)
