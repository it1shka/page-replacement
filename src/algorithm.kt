abstract class Algorithm(memorySize: Int) {
    abstract val name: String
    protected val memory = Array<Int?>(memorySize) { null }
    private var hits = 0
    private  var faults = 0
    val success get() = hits.toFloat() / (hits + faults).toFloat()

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