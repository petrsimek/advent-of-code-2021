import java.lang.Integer.max

object Day5 {

    private val inputList: List<String> = Utils.readInputToList("src/main/resources/input5.txt")
    private val inputListTest: List<String> = Utils.readInputToList("src/main/resources/input5-test.txt");

    class Command {
        lateinit var start: Pair<Int, Int>
        lateinit var end: Pair<Int, Int>
        override fun toString(): String {
            return "Command(start=$start, end=$end)"
        }
    }

    private fun buildInputMap(inputList: List<String>): List<Command> {
        val regexp = Regex("""(\d+)\,(\d+) \-\> (\d+)\,(\d+)""")
        return inputList.map {
            val (sX, sY, eX, eY) = regexp.find(it)!!.destructured
            val currComm = Command()
            currComm.start = Pair(sX.toInt(), sY.toInt())
            currComm.end = Pair(eX.toInt(), eY.toInt())
            currComm
        }
    }

    private fun getGrid(inputMap: List<Command>): Array<Array<Int>> {
        val maximum = max(inputMap.maxOf {
            it.start.second
        }, inputMap.maxOf {
            it.end.second
        })
        return Array(maximum + 1) { Array(maximum + 1) { 0 } }
    }

    private fun handleHorizontalsAndVerticals(grid: Array<Array<Int>>, inputMap: List<Command>): Array<Array<Int>> {
        inputMap
            .filter {
                it.start.first == it.end.first || it.start.second == it.end.second
            }
            .forEach {
                val startI = if (it.start.first < it.end.first) it.start.first else it.end.first
                val endI = if (it.start.first < it.end.first) it.end.first else it.start.first
                val startJ = if (it.start.second < it.end.second) it.start.second else it.end.second
                val endJ = if (it.start.second < it.end.second) it.end.second else it.start.second

                for (i in startI..endI) {
                    for (j in startJ..endJ) {
                        grid[j][i]++
                    }
                }
            }
        return grid
    }

    private fun handleDiagonals(grid: Array<Array<Int>>, inputMap: List<Command>): Array<Array<Int>> {
        inputMap
            .filter {
                it.start.first != it.end.first && it.start.second != it.end.second

            }
            .forEach {
                val startI = if (it.start.first < it.end.first) it.start.first else it.end.first
                val endI = if (it.start.first < it.end.first) it.end.first else it.start.first
                val startJ = if (it.start.first < it.end.first) it.start.second else it.end.second
                val endJ = if (it.start.first < it.end.first) it.end.second else it.start.second

                var x = startI
                var y = startJ
                for (i in startI..endI) {
                    grid[y][x]++
                    if (startI < endI) x++ else x--
                    if (startJ < endJ) y++ else y--
                }
            }
        return grid
    }

    private fun partOne(inputMap: List<Command>): Int {
        var grid = getGrid(inputMap)
        grid = handleHorizontalsAndVerticals(grid, inputMap)
        return grid.asList().sumBy { it.sumBy { it2 -> if (it2 > 1) 1 else 0 } }
    }

    private fun partTwo(inputMap: List<Command>): Int {
        var grid = getGrid(inputMap)
        grid = handleHorizontalsAndVerticals(grid, inputMap)
        grid = handleDiagonals(grid, inputMap)
        return grid.asList().sumBy { it.sumBy { it2 -> if (it2 > 1) 1 else 0 } }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println("Part 1 test: " + partOne(buildInputMap(inputListTest)))
        println("Part 2 test: " + partTwo(buildInputMap(inputListTest)))
        println("Part 1: " + partOne(buildInputMap(inputList)))
        println("Part 2: " + partTwo(buildInputMap(inputList)))
    }
}