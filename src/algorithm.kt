abstract class Algorithm(memorySize: Int) {
    protected val memory = Array<Int?>(memorySize) { null }
    protected abstract val name: String

    protected abstract fun replace(page: Int)

    protected fun replaceWith(oldPage: Int?, newPage: Int): Boolean {
        val index = memory.indexOf(oldPage)
        if (index == -1) return false
        memory[index] = newPage
        dumpReplace(oldPage, newPage)
        return true
    }

    fun run(referenceString: List<Int>) {
        dumpHeader("$name: ")
        var (hits, faults) = listOf(0, 0)
        for (page in referenceString) {
            if (memory.contains(page)) {
                hits++
                dumpHit(page)
            } else {
                replace(page)
                faults++
                dumpMemory(memory)
            }
        }
        dumpStats(hits, faults)
    }
}