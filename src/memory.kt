typealias Memory = Array<Int?>

fun Memory.replace(old: Int?, new: Int): Boolean {
    val oldIndex = indexOf(old)
    if (oldIndex == -1) return false
    this[oldIndex] = new
    return true
}

fun Memory.push(new: Int) = replace(null, new)

fun Memory.clear() = indices.forEach { this[it] = null }

fun Memory.getDump(): String {
    val sizes = map { it?.toString()?.length ?: 1 }
    val totalNumberSize = sizes.sum()
    val totalWidth = 4 + totalNumberSize + (size - 1) * 3

    val upperLine = "*".repeat(totalWidth)
    val middleLine = formatMemoryLine(sizes.map { " ".repeat(it) })
    val mainLine = formatMemoryLine(map {
        when (it) {
            null -> " "
            else -> "${Colors.CYAN}$it${Colors.RESET}"
        }
    })

    val lines = arrayOf (upperLine, middleLine, mainLine, middleLine, upperLine)
    return lines.joinToString(separator = "\n")
}

private fun formatMemoryLine(strings: List<String>) =
    strings
        .map { "* $it " }
        .reduce { a, b -> a + b }
        .plus("*")