object Day10 {

    private val inputList: List<String> = Utils.readInputToList("src/main/resources/input10.txt")
    private val inputListTest: List<String> = Utils.readInputToList("src/main/resources/input10-test.txt")

    private val pairs = mapOf('(' to ')', '[' to ']', '{' to '}', '<' to '>')
    private val points = mapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)
    private val points2 = mapOf(')' to 1, ']' to 2, '}' to 3, '>' to 4)

    private fun getInvalidCharacterPoints(row: String): Int {
        val buffer = mutableListOf<Char>()
        row.toCharArray().forEach { ch ->
            run {
                if (pairs.containsKey(ch)) {
                    buffer.add(ch)
                } else {
                    if (buffer.size > 0 && pairs[buffer.last()] == ch) {
                        buffer.removeLast()
                    } else {
                        return points[ch]!!
                    }
                }
            }
        }
        return 0
    }

    private fun getBuffer(row: String): List<Char> {
        val buffer = mutableListOf<Char>()
        row.toCharArray().forEach { ch ->
            run {
                if (pairs.containsKey(ch)) {
                    buffer.add(ch)
                } else {
                    if (buffer.size > 0 && pairs[buffer.last()] == ch) {
                        buffer.removeLast()
                    } else {
                        return emptyList()
                    }
                }
            }
        }
        return buffer
    }

    private fun partOne(inputMap: List<String>): Int {
        return inputMap.sumOf {
            getInvalidCharacterPoints(it)
        }
    }

    private fun partTwo(inputMap: List<String>): Long {
        val scores = inputMap.map { row ->
            getBuffer(row)
                .reversed()
                .map { pairs[it] }
                .fold(0L) { sum, it ->
                    sum * 5 + points2[it]!!
                }
        }.filter { it > 0 }.sorted()

        return scores[scores.size / 2]
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println("Part 1 test: " + partOne(inputListTest))
        println("Part 2 test: " + partTwo(inputListTest))
        println("Part 1: " + partOne(inputList))
        //339477
        println("Part 2: " + partTwo(inputList))
        //3049320156
    }
}