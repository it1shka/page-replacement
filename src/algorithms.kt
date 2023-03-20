class FirstInFirstOut(memory: Memory): Algorithm(memory) {
    override val name = "First In First Out (FIFO)"
    private val queue = MutableList<Int?>(memory.size) { null }

    override fun replace(page: Int): Int? {
        val oldest = queue.removeAt(0)
        memory.replace(oldest, page)
        queue.add(page)
        return oldest
    }
}

class Optimal(memory: Memory): Algorithm(memory) {
    override val name = "Optimal (OPT)"
    private val periodSum = HashMap<Int, Int>()
    private val counter = HashMap<Int, Int>()
    private val lastRequest = HashMap<Int, Int>()
    private var index = 0

    override fun processPage(page: Int) {
        super.processPage(page)
        val prev = lastRequest[page]
        if (prev != null) {
            val delta = index - prev
            periodSum.merge(page, delta, Int::plus)
            counter.merge(page, 1, Int::plus)
        }
        lastRequest[page] = index
        index++
    }

    override fun replace(page: Int): Int? {
        val best = memory.maxBy {
            val count = counter[it]
            if (count == null || count == 0) Int.MAX_VALUE
            else periodSum[it]!! / count
        }
        memory.replace(best, page)
        return best
    }
}

class LeastRecentlyUsed(memory: Memory): Algorithm(memory) {
    override val name = "Least Recently Used (LRU)"
    private val lastRequest = HashMap<Int, Int>()
    private var index = 0

    override fun processPage(page: Int) {
        super.processPage(page)
        lastRequest[page] = index
        index++
    }

    override fun replace(page: Int): Int? {
        val best = memory.minBy { lastRequest[it] ?: Int.MIN_VALUE }
        memory.replace(best, page)
        return best
    }
}