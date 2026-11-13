package lectura;

import modelo.Estudiante;
import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Loky
 */
public class LectorEstudiantesCsv implements FuenteDatos<Estudiante> {

    private final Path rutaArchivo;

    public LectorEstudiantesCsv(Path rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    @Override
    public List<Estudiante> leer() {
        List<Estudiante> estudiantes = new ArrayList<>();

        if (!Files.exists(rutaArchivo)) {
            System.out.println("Aviso: No se encontró el archivo " + rutaArchivo);
            return estudiantes;
        }

        try (BufferedReader br = Files.newBufferedReader(rutaArchivo)) {
            String linea = br.readLine();

            while ((linea = br.readLine()) != null) {
                if (linea.isBlank()) {
                    continue;
                }

                String separador = linea.contains(";") ? ";" : ",";
                String[] partes = linea.split(separador, -1);

                if (partes.length >= 4) {
                    String cedula = partes[0].trim();
                    String nombres = partes[1].trim();
                    String apellidos = partes[2].trim();
                    String correo = partes[3].trim();

                    estudiantes.add(new Estudiante(cedula, nombres, apellidos, correo));
                }
            }
        } catch (Exception e) {
            System.out.println("Error al leer estudiantes.csv: " + e.getMessage());
        }

        return estudiantes;
    }
}
