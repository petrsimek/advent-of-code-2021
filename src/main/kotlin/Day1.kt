object Day1 {

    private fun partOne(inputList: List<Int>): Int {
        var sum = 0
        for (i in inputList.indices) {
            if (i > 0 && inputList[i] > inputList[i - 1]) sum++
        }
        return sum
    }

    private fun partTwo(inputList: List<Int>): Int {
        val windows: MutableList<Int> = mutableListOf()
        for (i in 0 until inputList.size - 2) {
            windows.add(inputList[i] + inputList[i + 1] + inputList[i + 2])
        }
        return partOne(windows)
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val inputList = Utils.readInputToListOfIntegers("src/main/resources/input1.txt")
        println("Part 1: " + partOne(inputList))
        println("Part 2: " + partTwo(inputList))
    }
}