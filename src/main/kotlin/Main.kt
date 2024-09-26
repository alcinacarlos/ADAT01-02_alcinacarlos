import java.io.FileNotFoundException
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.notExists

// Lista[0]["Nombre"] = "JOSE ANTONIO"

fun main() {
    val ruta = Path.of("src", "main", "resources", "calificaciones.csv")
    val gestorCalificacion = GestorCalificacion(ruta)
}

