import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Collectors

object Utils {
    fun readInputToListOfIntegers(fileName: String): List<Int> {
        var result: List<Int> = ArrayList()
        try {
            Files.lines(Paths.get(fileName)).use { lines -> result = lines.map { s: String -> s.toInt() }.collect(Collectors.toList()) }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return result
    }

    fun readInputToList(fileName: String): List<String> {
        return File(fileName).useLines { it.toList() }.stream().collect(Collectors.toList())
    }

    fun readInputToBLocks(fileName: String): List<String> {
        return File(fileName).readText(Charsets.UTF_8).split("\n\n")
    }

    fun readInputToListOfLongsFromOneLine(fileName: String): List<Long> {
        return File(fileName).readText(Charsets.UTF_8).split(",").map { it.toLong() }
    }

    fun readInputToListOfIntsFromOneLine(fileName: String): List<Int> {
        return File(fileName).readText(Charsets.UTF_8).split(",").map { it.toInt() }
    }
}