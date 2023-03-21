object Colors {
    const val RESET = "\u001B[0m"
    const val BLACK = "\u001B[30m"
    const val RED = "\u001B[31m"
    const val GREEN = "\u001B[32m"
    const val YELLOW = "\u001B[33m"
    const val BLUE = "\u001B[34m"
    const val PURPLE = "\u001B[35m"
    const val CYAN = "\u001B[36m"
    const val WHITE = "\u001B[37m"
}

fun getPositiveInteger(prompt: String?): Int {
    print(prompt ?: "Please, enter an integer: ")
    while (true) {
        val input = readln()
        val value = input.toIntOrNull()
        if (value == null) {
            print("Not an integer. Please, try again: ")
            continue
        }
        if (value <= 0) {
            print("Integer should be bigger than zero. Please, try again: ")
            continue
        }
        return value
    }
}

fun prompt(message: String?): Boolean {
    while (true) {
        print("${ message ?: "Select" } [Y/n] ")
        when (readln().trim().lowercase()) {
            "", "y", "yes", "true" -> return true
            "n", "no", "false" -> return false
        }
    }
}

fun chooseFrom(message: String?, options: Array<String>): String {
    println(message ?: "Please, choose one of the options: ")
    options.forEachIndexed { index, option ->
        println("${Colors.YELLOW}${index + 1})${Colors.RESET} $option")
    }
    while (true) {
        val choice = getPositiveInteger("Provide the number: ") - 1
        if (choice !in options.indices) {
            println("$choice is not in options. ")
            continue
        }
        return options[choice]
    }
}
