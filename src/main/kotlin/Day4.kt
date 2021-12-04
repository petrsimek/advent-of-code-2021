object Day4 {

    private val inputList: List<String> = Utils.readInputToBLocks("src/main/resources/input4.txt")
    private val inputListTest: List<String> = Utils.readInputToBLocks("src/main/resources/input4-test.txt");

    fun analyzeBingo(bingo: MutableList<MutableList<Pair<Int, Boolean>>>, numbers: List<Int>): Pair<Int, Int> {
        var mBingo = bingo
        var lastIdx = 0
        run lit@{
            numbers.forEachIndexed { idx, bNum ->
                lastIdx = idx

                mBingo = mBingo.map {
                    it.map { it2 -> if (it2.first == bNum) it2.copy(second = true) else it2 }.toMutableList()
                }.toMutableList()

                if (!mBingo.filter { it.sumBy { it2 -> if (it2.second) 1 else 0 } == 5 }.isEmpty()) return@lit
            }
        }
        val calculateUnchecked = mBingo.take(5).sumOf {
            it.filter { it2 -> !it2.second }.sumOf { it2 -> it2.first }
        }
        return Pair(lastIdx, calculateUnchecked * numbers[lastIdx])
    }

    fun calculate(blocks: List<String>): MutableList<Pair<Int, Int>> {
        val numbers = blocks[0].split(",").map { it.toInt() }
        val result: MutableList<Pair<Int, Int>> = mutableListOf()
        blocks.forEachIndexed { idxB, bingo ->
            if (idxB > 0) {
                val fiveRows = bingo.split("\n").mapIndexed { idx, row ->
                    row.chunked(3).map {
                        Pair(it.trim().toInt(), false)
                    }.toMutableList()
                }.toMutableList()

                val wins: MutableList<MutableList<Pair<Int, Boolean>>> = mutableListOf()
                fiveRows.forEach {
                    wins.add(it)
                }
                for (i in 0 until fiveRows.size) {
                    wins.add(fiveRows.map { it.get(i) }.toMutableList())
                }
                result.add(analyzeBingo(wins.toMutableList(), numbers))
            }
        }
        return result
    }

    private fun partTwo(inputList: List<String>): Int {
        return calculate(inputList).sortedByDescending { it.first }[0].second
    }

    private fun partOne(inputList: List<String>): Int {
        return calculate(inputList).sortedBy { it.first }[0].second
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println("Part 1 test: " + partOne(inputListTest))
        println("Part 2 test: " + partTwo(inputListTest))
        println("Part 1: " + partOne(inputList))
        println("Part 2: " + partTwo(inputList))
    }
}