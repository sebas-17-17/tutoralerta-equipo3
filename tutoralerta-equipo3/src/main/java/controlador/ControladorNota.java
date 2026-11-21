
package controlador;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import modelo.Estudiante;

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

    public List<Object[]> obtenerListaEstudiantesNotas(String nombreEvaluacion, String textoFecha) {
        validarNombreEvaluacion(nombreEvaluacion);
        validarFecha(textoFecha);

        List<Object[]> filas = new ArrayList<>();
        List<Estudiante> estudiantes = controladorEstudiante.ordenPorApellido();

        for (Estudiante est : estudiantes) {
            filas.add(new Object[]{
                est.cedula(),
                est.apellidos() + " " + est.nombres(),
                "" 
            });
        }
        return filas;
    }
}
