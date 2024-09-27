import java.nio.file.Path

fun main() {
    val ruta = Path.of("src", "main", "resources", "calificaciones.csv")
    val gestorCalificacion = GestorCalificacion(ruta)
    val csvList = gestorCalificacion.csvToListMaps(ruta)
    val finalScores = gestorCalificacion.addFinalTestScores(csvList)
    val passedAndFailed = gestorCalificacion.passOrFail(finalScores)
    passedAndFailed.first.forEach { println(it) }
    println("\n\n")
    passedAndFailed.second.forEach { println(it) }
}

