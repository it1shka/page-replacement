class Optimal(memorySize: Int): Algorithm(memorySize) {
    override val name = "Optimal (OPT)"

    override fun replace(page: Int) {
        if (replaceWith(null, page)) return
        val best = memory.minBy(fun(item): Int {
            val index = memory.indexOf(item)
            val rest = memory.slice(index+1 until memory.size)
            val nextIndex = rest.indexOf(item)
            return (if (nextIndex == -1) -1 else nextIndex - index)
        })
        replaceWith(best, page)
    }
}