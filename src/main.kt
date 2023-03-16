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

fun getPageReferenceString(): List<Int> {
    println("Please, enter page reference string: ")
    outer@ while (true) {
        val input = readln()
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

fun main() {
    val slotsAmount = getPositiveInteger("Please, enter slots amount: ")
    val referenceString = getPageReferenceString()
    // here i will be running algorithms
    val algorithms = arrayOf(
        FirstInFirstOut(slotsAmount),
    )
    for (algorithm in algorithms) {
        algorithm.run(referenceString)
    }
}