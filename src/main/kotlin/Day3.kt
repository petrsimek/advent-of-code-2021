import java.lang.Integer.parseInt

object Day3 {

    private val inputList: List<String> = Utils.readInputToList("src/main/resources/input3.txt");
    private val inputListTest: List<String> = Utils.readInputToList("src/main/resources/input3-test.txt");

    private fun getRate(inputList: List<String>, higher: Boolean): CharArray {
        val rate = CharArray(inputList[0].length)
        for (i in 0 until inputList[0].length) {
            var sum0 = 0
            var sum1 = 0
            inputList.forEach {
                run {
                    if (it[i] == '0') {
                        sum0++
                    } else {
                        sum1++
                    }
                }
            }
            if (higher) {
                if (sum1 >= sum0) rate[i] = '1' else rate[i] = '0'
            } else {
                if (sum0 > sum1) rate[i] = '1' else rate[i] = '0'
            }
        }
        return rate;
    }

    private fun filterByBitAtPos(inputList: MutableList<String>, pos: Int, bitChar: Char): MutableList<String> {
        return inputList.filter {
            it.get(pos) == bitChar
        }.toMutableList()
    }

    private fun calculateRating(inputList: List<String>, higher: Boolean): Int {
        var filteredList = inputList.toMutableList()
        loop@ for (i in 0 until inputList[0].length) {
            val startRate = getRate(filteredList, higher)
            filteredList = filterByBitAtPos(filteredList, i, startRate[i])
            if (filteredList.size == 1) break@loop
        }
        return parseInt(filteredList[0], 2)
    }

    private fun partTwo(inputList: List<String>): Int {
        val oxy = calculateRating(inputList, true)
        val co2 = calculateRating(inputList, false)
        return oxy * co2
    }

    private fun partOne(inputList: List<String>): Int {
        val gama = getRate(inputList, true)
        val eps = getRate(inputList, false)
        return parseInt(gama.joinToString(""), 2) * parseInt(eps.joinToString(""), 2)
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println("Part 1 test: " + partOne(inputListTest))
        println("Part 2 test: " + partTwo(inputListTest))
        println("Part 1: " + partOne(inputList))
        println("Part 2: " + partTwo(inputList))
    }
}