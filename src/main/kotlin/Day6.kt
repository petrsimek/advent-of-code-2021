object Day6 {

    private val inputList: List<Long> = Utils.readInputToListOfLongsFromOneLine("src/main/resources/input6.txt")
    private val inputListTest: List<Long> = Utils.readInputToListOfLongsFromOneLine("src/main/resources/input6-test.txt");

    private fun simulateAfterDay2(inputMap: List<Long>, forDays: Int): Long {
        var counts = LongArray(9) { 0 }
        inputMap.forEach {
            counts[it.toInt()]++
        }

        val counts2 = counts.copyOf()

        for (day in 1..forDays) {
            for (i in counts.indices) {
                if (counts[i] > 0) {
                    if (i == 0) {
                        counts2[6] += counts[0]
                        counts2[8] += counts[0]
                        counts2[0] -= counts[0]
                    } else {
                        counts2[i - 1] += counts[i]
                        counts2[i] -= counts[i]
                    }
                }
            }

            counts = counts2.copyOf()
        }

        return counts.asList().sum()
    }


    private fun partOne(inputMap: List<Long>): Long {
        return simulateAfterDay2(inputMap, 80)
    }

    private fun partTwo(inputMap: List<Long>): Long {
        return simulateAfterDay2(inputMap, 256)
    }


    @JvmStatic
    fun main(args: Array<String>) {
        println("Part 1 test: " + partOne(inputListTest))
        println("Part 2 test: " + partTwo(inputListTest))
        println("Part 1: " + partOne(inputList))
        println("Part 2: " + partTwo(inputList))
    }
}