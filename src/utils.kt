import kotlin.math.max
import kotlin.random.Random

object Colors {
    const val RESET = "\u001B[0m"
    const val BLACK = "\u001B[30m"
    const val RED = "\u001B[31m"
    const val GREEN = "\u001B[32m"
    const val YELLOW = "\u001B[33m"
    const val BLUE = "\u001B[34m"
    const val PURPLE = "\u001B[35m"
    const val CYAN = "\u001B[36m"
    const val WHITE = "\u001B[37m"
}

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

private fun generateRandomReferenceString(size: Int): List<Int> {
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

private fun generateFullyRandomReferenceString(size: Int): List<Int> =
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
            if (page <= 0) {
                println("Page \"$part\" is not a natural number. Please, try again: ")
                continue@outer
            }
            output.add(page)
        }
        return output
    }
}

fun prompt(message: String?): Boolean {
    while (true) {
        print("${ message ?: "Select" } [Y/n] ")
        when (readln().trim().lowercase()) {
            "", "y", "yes", "true" -> return true
            "n", "no", "false" -> return false
        }
    }
}

fun chooseFrom(message: String?, options: Array<String>): String {
    println(message ?: "Please, choose one of the options: ")
    options.forEachIndexed { index, option ->
        println("${Colors.YELLOW}${index + 1})${Colors.RESET} $option")
    }
    while (true) {
        val choice = getPositiveInteger("Provide the number: ") - 1
        if (choice !in options.indices) {
            println("$choice is not in options. ")
            continue
        }
        return options[choice]
    }
}
