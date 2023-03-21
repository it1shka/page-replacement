abstract class Algorithm(protected val memory: Memory) {

    // private fields
    protected abstract val name: String
    private var hits = 0
    private var faults = 0
    private val efficiencyRate get() = hits.toFloat() / (hits + faults).toFloat()

    // for logging
    val header get() = "${Colors.YELLOW}$name: ${Colors.RESET}"
    val statistics get(): String {
        val hitsString = "Hits: $hits"
        val faultsString = "Faults: $faults"
        return "${Colors.YELLOW}$hitsString, $faultsString${Colors.RESET}"
    }
    val efficiency get() = "${Colors.PURPLE}%.2f".format(efficiencyRate * 100).plus("%${Colors.RESET}")

    // for dumping to output
    private val buffer = StringBuilder()
    val output get() = buffer.toString()

    private fun dumpReplace(oldPage: Int?, newPage: Int) {
        val message = if (oldPage == null) "Page $newPage was inserted"
        else "Page $oldPage was replaced with Page $newPage"
        buffer.append("$message. ${Colors.RED}+1 fault${Colors.RESET}\n")
    }

    private fun dumpHit(page: Int) {
        val message = "Page $page was hit! ${Colors.GREEN}+1 hit${Colors.RESET}"
        buffer.append("$message\n")
    }

    private fun dumpMemory() = buffer.append("${memory.getDump()}\n")

    // core functionality
    open fun processPage(page: Int) {
        if (memory.contains(page)) {
            dumpHit(page)
            hits++
        } else {
            val old = replace(page)
            dumpReplace(old, page)
            dumpMemory()
            faults++
        }
    }

    protected abstract fun replace(page: Int): Int?

}