import kotlin.math.max
import kotlin.random.Random

fun getPositiveInteger(prompt: String?): Int {
    print(prompt ?: "Please, enter an integer: ")
    while (true) {
        val input = readln()
        val value = input.toIntOrNull()
        if (value == null) {
            print("Not an integer. Please, try again: ")
            continue
        }
        if (value <= 0) {
            print("Integer should be bigger than zero. Please, try again: ")
            continue
        }
        return value
    }
}

fun generateRandomReferenceString(size: Int): List<Int> {
    var current = 0
    return List(size + 1) {
        current += when (Random.nextInt(3)) {
            1 -> 1
            2 -> -1
            else -> 0
        }
        current = max(current, 1)
        current
    }
}

fun generateFullyRandomReferenceString(size: Int): List<Int> =
    List(size) { Random.nextInt(10) }

fun getPageReferenceString(): List<Int> {
    println("Please, enter page reference string: ")
    outer@ while (true) {
        val input = readln().trim()
        if (input.startsWith('~') || input.startsWith('@')) {
            val size = input.slice(1 until input.length).toIntOrNull()
            if (size == null) {
                println("\"$input\" does not contain an integer. Please, try again: ")
                continue@outer
            }
            val random = when(input[0]) {
                '~' -> generateRandomReferenceString(size)
                else -> generateFullyRandomReferenceString(size)
            }
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
            if (page < 0) {
                println("Page \"$part\" is not a natural number. Please, try again: ")
                continue@outer
            }
            output.add(page)
        }
        return output
    }
}

fun printAlgorithmStats(algorithms: Array<Algorithm>) {
    println()
    algorithms.forEach {
        val efficiency = "%.2f".format(it.success * 100) + "%"
        println("${Colors.BLUE}${it.name}:${Colors.RESET} $efficiency")
    }
}

fun main() {
    val slotsAmount = getPositiveInteger("Please, enter slots amount: ")
    val referenceString = getPageReferenceString()
    // here I will be running algorithms
    val algorithms = arrayOf(
        FirstInFirstOut(slotsAmount),
        Optimal(slotsAmount),
        LeastRecentlyUsed(slotsAmount)
    )
    algorithms.forEach {
        println()
        it.run(referenceString)
    }
    printAlgorithmStats(algorithms)
}