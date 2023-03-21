fun main() {
    val slotsAmount = getPositiveInteger("Please, enter slots amount: ")
    val referenceString = getPageReferenceString()
    val memory = Memory(slotsAmount) { null }

    // here I will be running algorithms
    val briefResults = StringBuilder()
    val choices = arrayOf("FIFO", "OPT", "LRU", "Exit")

    while (true) {
        val choice = chooseFrom("Choose an algorithm you wanna test: ", choices)
        if (choice == "Exit") break
        val algorithm = when (choice) {
            "FIFO" -> FirstInFirstOut(memory)
            "OPT" -> Optimal(memory)
            "LRU" -> LeastRecentlyUsed(memory)
            "RAND" -> RandomReplace(memory)
            else -> throw RuntimeException("This should never happen")
        }
        runAlgorithm(algorithm, memory, referenceString)
        briefResults.append("$choice: ${algorithm.efficiency}\n")
    }

    println()
    println("${Colors.BLUE}Brief Results: ${Colors.RESET}")
    println(briefResults.toString())
}

private fun runAlgorithm(algorithm: Algorithm, memory: Memory, refString: List<Int>) {
    memory.clear()
    println(algorithm.header)
    refString.forEach(algorithm::processPage)

    if (prompt("Show algorithm output?")) {
        println(algorithm.output)
    }
    if (prompt("Show algorithm stats?")) {
        println(algorithm.statistics)
        println(algorithm.efficiency)
    }
    println()
}