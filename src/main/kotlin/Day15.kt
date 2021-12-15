import org.jgrapht.alg.shortestpath.DijkstraShortestPath
import org.jgrapht.graph.DefaultWeightedEdge
import org.jgrapht.graph.SimpleDirectedWeightedGraph


object Day15 {

    private fun parseInput(fileName: String): Triple<Map<Pair<Int, Int>, Int>, Int, Int> {
        val inputMap: List<String> = Utils.readInputToList(fileName)

        val all = mutableMapOf<Pair<Int, Int>, Int>()
        inputMap.forEachIndexed { iRow, row ->
            row.toCharArray().map { it.toString().toInt() }
                .forEachIndexed { iCol, col ->
                    all.put(Pair(iRow, iCol), col)
                }
        }

        return Triple(all, inputMap.size, inputMap[0].length)
    }

    fun getPossibleVertices(cell: Pair<Int, Int>, xMax: Int, yMax: Int): Set<Pair<Int, Int>> {
        val neigh = mutableSetOf<Pair<Int, Int>>()
        if (cell.first > 0) {
            neigh.add(Pair(cell.first - 1, cell.second))
        }
        if (cell.second > 0) {
            neigh.add(Pair(cell.first, cell.second - 1))
        }
        if (cell.first < xMax - 1) {
            neigh.add(Pair(cell.first + 1, cell.second))
        }
        if (cell.second < yMax - 1) {
            neigh.add(Pair(cell.first, cell.second + 1))
        }
        return neigh
    }

    private fun calculatePath(grid: Map<Pair<Int, Int>, Int>, xMax: Int, yMax: Int): Int {
        val graph = SimpleDirectedWeightedGraph<Pair<Int, Int>, DefaultWeightedEdge>(DefaultWeightedEdge::class.java)
        grid.forEach {
            graph.addVertex(it.key)
        }
        grid.forEach {
            getPossibleVertices(it.key, xMax, yMax).forEach { n ->
                graph.setEdgeWeight(graph.addEdge(it.key, n), grid[n]!!.toDouble())
            }
        }

        val dijkstraAlg = DijkstraShortestPath(graph)
        val iPaths = dijkstraAlg.getPaths(Pair(0, 0))
        val path = iPaths.getPath(Pair(xMax - 1, yMax - 1))

        return path.weight.toInt()
    }

    private fun partOne(filename: String): Int {
        val (grid, xMax, yMax) = parseInput(filename)
        return calculatePath(grid, xMax, yMax)
    }

    private fun inflateInput(grid: Map<Pair<Int, Int>, Int>, xMax: Int, yMax: Int): Triple<Map<Pair<Int, Int>, Int>, Int, Int> {

        val mGrid = grid.toMutableMap()
        for (x in 0..4) {
            for (y in 0..4) {
                if (x > 0 || y > 0) {
                    grid.forEach {
                        var newValue = it.value + x + y
                        if (newValue > 9) {
                            newValue -= 9
                        }
                        mGrid.put(Pair(it.key.first + (x * xMax), it.key.second + (y * yMax)), newValue)
                    }
                }
            }
        }
        return Triple(mGrid, xMax * 5, yMax * 5)
    }

    private fun partTwo(filename: String): Int {
        val (grid, xMax, yMax) = parseInput(filename)
        val (grid2, xMax2, yMax2) = inflateInput(grid, xMax, yMax)
        return calculatePath(grid2, xMax2, yMax2)
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println("Part 1 test: " + partOne("src/main/resources/input15-test.txt"))
        println("Part 2 test: " + partTwo("src/main/resources/input15-test.txt"))
        println("Part 1: " + partOne("src/main/resources/input15.txt"))
        println("Part 2: " + partTwo("src/main/resources/input15.txt"))
    }
}