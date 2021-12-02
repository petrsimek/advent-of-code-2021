import java.io.File

object Day2 {

    private val inputList: List<String> = File("src/main/resources/input2.txt").readText(Charsets.UTF_8).split("\n");
    private val inputMap: List<Command> = buildInputMap()

    class Command(var direction: String, var value: Int) {
        override fun toString(): String {
            return "Instr(direction='$direction', valu=$value)"
        }
    }

    private fun buildInputMap(): List<Command> {
        return inputList.map {
            val (instr, value) = Regex("""(\w+) ([\+\-\d]+)""").find(it)!!.destructured
            Command(instr, value.toInt())
        }
    }

    private fun partOne(inputMap: List<Command>): Int {
        var h = 0
        var d = 0
        inputMap.forEach {
            run {
                if (it.direction == "up") {
                    d -= it.value
                }
                if (it.direction == "down") {
                    d += it.value
                }
                if (it.direction == "forward") {
                    h += it.value
                }
            }
        }

        return h * d
    }

    private fun partTwo(inputMap: List<Command>): Int {
        var h = 0
        var d = 0
        var a = 0
        inputMap.forEach {
            run {
                if (it.direction == "up") {
                    a -= it.value
                }
                if (it.direction == "down") {
                    a += it.value
                }
                if (it.direction == "forward") {
                    h += it.value
                    d += (a * it.value)
                }
            }
        }

        return h * d
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println("Part 1: " + partOne(inputMap))
        println("Part 2: " + partTwo(inputMap))
    }
}