class LeastRecentlyUsed(memorySize: Int): Algorithm(memorySize) {
    override val name = "Least Recently Used (LRU)"
    private val used = HashMap<Int, Int>()

    override fun replace(page: Int) {
        val lru = memory
            .filter { it != page }
            .minBy { used.getOrDefault(it, 0) }
        used.merge(page, 1, Int::plus)
        replaceWith(lru, page)
    }
}