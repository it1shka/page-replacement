class FirstInFirstOut(memorySize: Int): Algorithm(memorySize) {
    override val name = "First In First Out (FIFO)"
    private var replaceIndex = 0

    override fun replace(page: Int) {
        replaceIndex %= memory.size
        val old = memory[replaceIndex]
        memory[replaceIndex] = page
        replaceIndex++
        dumpReplace(old, page)
    }
}