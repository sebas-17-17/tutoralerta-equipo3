
package controlador;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import modelo.Estudiante;
import lectura.LectoresCsv.LectorNotasCsv;
import modelo.Nota;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ControladorNota {
    private final ControladorEstudiante controladorEstudiante;

    public ControladorNota(ControladorEstudiante controladorEstudiante) {
        this.controladorEstudiante = controladorEstudiante;
    }

    public String validarNombreEvaluacion(String nombreEvaluacion) {
        if (nombreEvaluacion == null || nombreEvaluacion.trim().isEmpty()) {
            throw new IllegalArgumentException("Escriba el nombre de la evaluación para continuar.");
        }
        return nombreEvaluacion.trim();
    }

    public LocalDate validarFecha(String textoFecha) {
        if (textoFecha == null || textoFecha.trim().isEmpty()) {
            throw new IllegalArgumentException("Por favor ingrese una fecha (AAAA-MM-DD).");
        }
        try {
            return LocalDate.parse(textoFecha.trim());
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de fecha inválido. Use AAAA-MM-DD.");
        }
    }

    

    public List<Object[]> obtenerListaEstudiantesNotas(
            String nombreEvaluacion, String textoFecha) {

        validarNombreEvaluacion(nombreEvaluacion);
        validarFecha(textoFecha);

        List<Object[]> filas = new ArrayList<>();

        // Leer las notas registradas
        LectorNotasCsv lector = new LectorNotasCsv(
                Paths.get("notas.csv")
        );

        List<Nota> notasGuardadas = lector.leer();

        // Buscar las calificaciones de esta evaluación
        Map<String, String> calificaciones = new HashMap<>();

        for (Nota nota : notasGuardadas) {

            if (nota.nombreEvaluacion().equalsIgnoreCase(
                    nombreEvaluacion.trim())) {

                calificaciones.put(
                        nota.cedulaEstudiante(),
                        String.valueOf(nota.calificacion())
                );
            }
        }

        // Obtener estudiantes registrados
        List<Estudiante> estudiantes =
                controladorEstudiante.ordenPorApellido();

        // Construir las filas de la tabla
        for (Estudiante est : estudiantes) {

            String calificacion = calificaciones.getOrDefault(
                    est.cedula(), ""
            );

            filas.add(new Object[]{
                est.cedula(),
                est.apellidos() + " " + est.nombres(),
                calificacion
            });
        }

        return filas;
    }
}