// ANSI colors

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

// functions for console logging

fun dumpStats(hits: Int, faults: Int) {
    val hitsString = "${Colors.GREEN}Hits: $hits${Colors.RESET}"
    val faultsString = "${Colors.RED}Faults: $faults${Colors.RESET}"
    println("$hitsString, $faultsString")
}

fun dumpHeader(algorithm: String) {
    println("${Colors.YELLOW}$algorithm${Colors.RESET}")
}

fun dumpHit(page: Int) {
    println("Page $page was hit! ${Colors.GREEN}+1 hit${Colors.RESET}")
}

fun dumpReplace(oldPage: Int?, newPage: Int) {
    val message = if (oldPage == null) "Page $newPage was inserted. "
        else "Page $oldPage was replaced with Page $newPage. "
    println("$message. ${Colors.RED}+1 fault${Colors.RESET}")
}

// memory dump

private fun memLineFormat(strings: List<String>) =
    strings.map { "* $it " }.reduce { a, b -> a + b } + "*"

private fun memPageFormat(page: Int?) =
    if (page == null) " "
    else "${Colors.CYAN}$page${Colors.RESET}"

fun dumpMemory(memory: Array<Int?>) {
    val sizes = memory.map { it?.toString()?.length ?: 1 }
    val totalNumberSize = sizes.sum()
    val totalWidth = 4 + totalNumberSize + (memory.size - 1) * 3

    val upperLine = "*".repeat(totalWidth)
    val middleLine = memLineFormat(sizes.map { " ".repeat(it) })
    val mainLine = memLineFormat(memory.map(::memPageFormat))

    val lines = arrayOf (upperLine, middleLine, mainLine, middleLine, upperLine)
    lines.forEach(::println)
}