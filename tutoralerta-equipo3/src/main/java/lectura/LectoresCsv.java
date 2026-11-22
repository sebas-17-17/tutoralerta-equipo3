
package lectura;

import modelo.Estudiante;
import modelo.Nota;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

// LECTOR DE NOTAS
public class LectoresCsv {

    public static class LectorNotasCsv implements FuenteDatos<Nota> {

        private final Path rutaArchivo;

        public LectorNotasCsv(Path rutaArchivo) {
            this.rutaArchivo = rutaArchivo;
        }

        @Override
        public List<Nota> leer() {

            List<Nota> notas = new ArrayList<>();

            if (!Files.exists(rutaArchivo)) {
                System.out.println(
                    "Aviso: No se encontró " + rutaArchivo
                );
                return notas;
            }

            try {
                List<String> lineas = Files.readAllLines(rutaArchivo);

                for (int i = 0; i < lineas.size(); i++) {

                    String linea = lineas.get(i).trim();

                    if (linea.isEmpty()) {
                        continue;
                    }

                    if (i == 0 &&
                        linea.toLowerCase().startsWith("cedula")) {
                        continue;
                    }

                    String separador = linea.contains(";") ? ";" : ",";
                    String[] partes = linea.split(separador, -1);

                    if (partes.length >= 3) {
                        try {
                            String cedula = partes[0].trim();
                            String evaluacion = partes[1].trim();

                            double calificacion = Double.parseDouble(
                                partes[2].trim()
                            );

                            notas.add(new Nota(
                                cedula,
                                evaluacion,
                                calificacion
                            ));

                        } catch (NumberFormatException e) {
                            System.out.println(
                                "Registro inválido en línea " + (i + 1)
                            );
                        }
                    }
                }

            } catch (Exception e) {
                System.out.println(
                    "Error al leer notas.csv: " + e.getMessage()
                );
            }

            return notas;
        }
    }

    // LECTOR DE ESTUDIANTES
    public static class LectorEstudiantesCsv
            implements FuenteDatos<Estudiante> {

        private final Path rutaArchivo;

        public LectorEstudiantesCsv(Path rutaArchivo) {
            this.rutaArchivo = rutaArchivo;
        }

        @Override
        public List<Estudiante> leer() {

            List<Estudiante> estudiantes = new ArrayList<>();

            if (!Files.exists(rutaArchivo)) {
                System.out.println(
                    "Aviso: No se encontró " + rutaArchivo
                );
                return estudiantes;
            }

            try {
                List<String> lineas = Files.readAllLines(rutaArchivo);

                for (int i = 0; i < lineas.size(); i++) {

                    String linea = lineas.get(i).trim();

                    if (linea.isEmpty()) {
                        continue;
                    }

                    if (i == 0 &&
                        linea.toLowerCase().startsWith("cedula")) {
                        continue;
                    }

                    String separador = linea.contains(";") ? ";" : ",";
                    String[] partes = linea.split(separador, -1);

                    if (partes.length >= 4) {

                        String cedula = partes[0].trim();
                        String nombres = partes[1].trim();
                        String apellidos = partes[2].trim();
                        String correo = partes[3].trim();

                        estudiantes.add(new Estudiante(
                            cedula,
                            nombres,
                            apellidos,
                            correo
                        ));
                    }
                }

            } catch (Exception e) {
                System.out.println(
                    "Error al leer estudiantes.csv: " + e.getMessage()
                );
            }

            return estudiantes;
        }
    }
}

