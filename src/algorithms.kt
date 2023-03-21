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

class RandomReplace(memory: Memory): Algorithm(memory) {
    override val name = "Random (RAND)"

    override fun replace(page: Int): Int? {
        val random = memory.random()
        memory.replace(random, page)
        return random
    }
}

class LeastFrequentlyUsed(memory: Memory): Algorithm(memory) {
    override val name = "Least Frequently Used (LFU)"
    private val counter = HashMap<Int?, Int>()

    override fun processPage(page: Int) {
        super.processPage(page)
        counter.merge(page, 1, Int::plus)
    }

    override fun replace(page: Int): Int? {
        val best = memory.minBy { counter[it] ?: 0 }
        memory.replace(best, page)
        counter[best] = 0
        return best
    }
}

class ApproxLeastRecentlyUsed(memory: Memory): Algorithm(memory) {
    override val name = "Approx Least Recently Used (ALRU)"
    private val used = HashSet<Int>()

    override fun processPage(page: Int) {
        super.processPage(page)
        used.add(page)
    }

    override fun replace(page: Int): Int? {
        val best = memory.find { !used.contains(it) }
        used.clear()
        memory.replace(best, page)
        return best
    }
}