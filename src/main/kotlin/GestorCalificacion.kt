import java.io.FileNotFoundException
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.notExists

class GestorCalificacion(private val filePath: Path) {
    init {
        checkFile()
    }

    private fun checkFile() {
        if (filePath.notExists()) throw FileNotFoundException("Archivo no encontrado $filePath")
    }

    /*Una función que reciba el fichero de calificaciones y devuelva una lista de diccionarios, donde cada diccionario contiene
     la información de los exámenes y la asistencia de un alumno. La lista tiene que estar ordenada por apellidos.*/
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
                map[headers[i]] = row[i].replace(",", ".")
            }
            data.add(map)
        }
        return data.drop(1)
    }

    /*
    Una función que reciba una lista de diccionarios como la que devuelve la función anterior y añada a cada diccionario un nuevo
    par con la nota final del curso. El peso de cada parcial de teoría en la nota final es de un 30% mientras que el peso del
    examen de prácticas es de un 40%.
    * */
    fun addFinalTestScores(data: List<Map<String, String>>): List<Map<String, String>> {
        return data.map { student ->
            val parcial1 = student["Parcial1"]?.toDoubleOrNull()?: 0.0
            val parcial2 = student["Parcial2"]?.toDoubleOrNull()?: 0.0
            val practicas = student["Practicas"]?.toDoubleOrNull()?: 0.0
            val notaFinal = parcial1.times(0.3) + parcial2.times(0.3) + practicas.times(0.4)
            student + ("Ordinario1" to notaFinal.toString())
        }
    }

    /*
    Una función que reciba una lista de diccionarios como la que devuelve la función anterior y devuelva dos listas, una con los
    alumnos aprobados y otra con los alumnos suspensos. Para aprobar el curso, la asistencia tiene que ser mayor o igual que el
    75%, la nota de los exámenes parciales y de prácticas mayor o igual que 4 y la nota final mayor o igual que 5.
    */
    fun passOrFail(data: List<Map<String, String>>):Pair<List<String>, List<String>>{
        val failed = mutableListOf("Alumnos suspensos: ")
        val passed = mutableListOf("Alumnos aprobados: ")
        data.forEach { student ->
            val asistencia = (student["Asistencia"]?.replace("%", "")?.toDoubleOrNull()?: 0.0) >=75
            val parcial1 = (student["Parcial1"]?.toDoubleOrNull()?: 0.0) >=4
            val parcial2 = (student["Parcial2"]?.toDoubleOrNull()?: 0.0) >=4
            val practicas = (student["Practicas"]?.toDoubleOrNull()?: 0.0) >=4
            val ordinario1 = (student["Ordinario1"]?.toDoubleOrNull()?: 0.0) >= 5
            val nombre = student["Apellidos"] + ", "+ student["Nombre"]
            if (asistencia && parcial1 && parcial2 && practicas && ordinario1){
                passed.add(nombre)
            }else{
                failed.add(nombre)
            }
        }
        return Pair(passed, failed)
    }

}