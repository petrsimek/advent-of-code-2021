object Day13 {

    private fun foldIt(fold: Pair<String, Int>, pairs: Set<Pair<Int, Int>>): Set<Pair<Int, Int>> {
        val newSet = mutableSetOf<Pair<Int, Int>>()
        pairs.forEach {
            if (fold.first == "y") {
                if (it.second < fold.second) {
                    newSet.add(it)
                } else if (it.second > fold.second) {
                    newSet.add(Pair(it.first, fold.second - (it.second - fold.second)))
                }
            } else {
                if (it.first < fold.second) {
                    newSet.add(it)
                } else if (it.first > fold.second) {
                    newSet.add(Pair(fold.second - (it.first - fold.second), it.second))
                }
            }
        }
        return newSet
    }

    private fun printIt(pairs: Set<Pair<Int, Int>>) {
        val array = Array(pairs.maxOf { it.second } + 1) { Array(pairs.maxOf { it.first } + 1) { 0 } }
        pairs.forEach {
            array[it.second][it.first] = 1
        }
        for (x in 0 until array.size) {
            for (y in 0 until array[0].size) {
                if (array[x][y] == 1) print("#") else print(".")
            }
            println()
        }
    }

    private fun parseInput(fileName: String): Pair<Set<Pair<Int, Int>>, List<Pair<String, Int>>> {
        val inputList: List<String> = Utils.readInputToBLocks(fileName)
        val pairs = inputList.get(0).split("\n").map {
            Pair(it.split(",")[0].toInt(), it.split(",")[1].toInt())
        }.toSet()

        val folds = inputList.get(1).split("\n").map {
            val regexp = Regex("""fold along (\w+)\=(\d+)""")
            val (ch, d) = regexp.find(it)!!.destructured
            Pair(ch, d.toInt())
        }

        return Pair(pairs, folds)
    }

    private fun partOne(fileName: String): Int {
        val input = parseInput(fileName)
        return foldIt(input.second.get(0), input.first).count()
    }

    private fun partTwo(fileName: String) {
        val input = parseInput(fileName)
        var pairs = input.first
        input.second.forEach {
            pairs = foldIt(it, pairs)
        }
        printIt(pairs)
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println("Part 1 test: " + partOne("src/main/resources/input13-test.txt"))
        partTwo("src/main/resources/input13-test.txt")
        println("Part 1: " + partOne("src/main/resources/input13.txt"))
        partTwo("src/main/resources/input13.txt")
    }
}