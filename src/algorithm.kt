abstract class Algorithm(memorySize: Int) {
    protected val memory = Array<Int?>(memorySize) { null }
    protected abstract val name: String

    protected abstract fun replace(page: Int)
    fun run(referenceString: List<Int>) {
        dumpHeader("$name: ")
        var (hits, faults) = listOf(0, 0)
        for (page in referenceString) {
            if (memory.contains(page)) {
                hits++
                dumpHit(page)
            } else {
                replace(page)
                dumpMemory(memory)
            }
        }
        dumpStats(hits, faults)
    }
}