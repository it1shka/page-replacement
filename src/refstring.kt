import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

private fun fullRandom(size: Int, bound: Int) =
    List(size) { Random.nextInt(bound - 1) + 1 }

private fun drunkardWalk(size: Int, bound: Int): List<Int> {
    var current = bound / 2
    return List(size) {
        val prev = current
        when (Random.nextInt(3)) {
            0 -> current -= 1
            1 -> current += 1
        }
        current = max(1, min(bound - 1, current))
        prev
    }
}

private fun cycled(size: Int, bound: Int): List<Int> {
    val probabilities = Array(bound - 1) { Random.nextInt(100) }
    val totalProbability = probabilities.sum()
    val normalizedProbabilities = probabilities.map { it.toFloat() / totalProbability.toFloat() }

    return List(size, fun(_): Int {
        var choice = Random.nextFloat()
        for (i in normalizedProbabilities.indices) {
            choice -= normalizedProbabilities[i]
            if (choice <= 0f) {
                return i + 1
            }
        }
        return bound - 1
    })
}

val modes = arrayOf("Full", "Drunkard Walk", "Cycled")
private fun getRandomPageReferenceString(size: Int): List<Int> {
    val upperBound = getPositiveInteger("Upper bound of randomization (exclusive): ")
    val algorithm = when(chooseFrom("Choose randomization mode: ", modes)) {
        "Full" -> ::fullRandom
        "Drunkard Walk" -> ::drunkardWalk
        "Cycled" -> ::cycled
        else -> throw RuntimeException("This should never happen")
    }
    return algorithm(size, upperBound)
}

fun getPageReferenceString(): List<Int> {
    println("Please, enter page reference string: ")
    outer@ while (true) {
        val input = readln().trim()
        if (input.startsWith('~')) {
            val size = input.slice(1 until input.length).toIntOrNull()
            if (size == null) {
                println("Incorrect format for randomization: expected \"~[size]\", found \"$input\". ")
                print("Try again: ")
                continue@outer
            }
            val random = getRandomPageReferenceString(size)
            println("Reference string: ")
            println("${Colors.GREEN}${random.joinToString()}${Colors.RESET}")
            return random
        }
        val parts = input
            .split(',')
            .map { it.trim() }
            .filter { it.isNotEmpty() }
        val output = mutableListOf<Int>()
        for (part in parts) {
            val page = part.toIntOrNull()
            if (page == null) {
                println("Page \"$part\" is not an integer. Please, try again: ")
                continue@outer
            }
            if (page <= 0) {
                println("Page \"$part\" is not a natural number. Please, try again: ")
                continue@outer
            }
            output.add(page)
        }
        return output
    }
}
