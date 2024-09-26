import java.io.FileNotFoundException
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.notExists

class GestorCalificacion(val filePath: Path) {
    init {
        checkOrCreateFile()
    }
    private fun checkOrCreateFile(){

    }
    fun csvToListMaps(path: Path): List<Map<String, String>> {
        if (path.notExists()) throw FileNotFoundException("No existe el fichero $path")
        val br = Files.newBufferedReader(path)
        val rows = mutableListOf<List<String>>()
        br.use { bufferedReader ->
            bufferedReader.forEachLine { line ->
                val row = line.split(";")
                rows.add(row)
            }
        }
        val data = mutableListOf<Map<String, String>>()
        val headers = rows.first()

        for (row in rows) {
            val map = emptyMap<String, String>().toMutableMap()
            for (i in headers.indices) {
                map[headers[i]] = row[i]
            }
            data.add(map)
        }
        return data.drop(1)
    }
    fun addFinalTestScores(data: List<Map<String, String>>) :List<Map<String, String>>{
        data.forEach {
            val parcial1 = it["Parcial1"]
        }
    }
}