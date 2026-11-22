
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
    
    private static final double NOTA_MIN = 0.0;
    private static final double NOTA_MAX = 20.0;
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
    
    public double validarCalificacion(Object valorCelda, String nombreEstudiante) {
    if (valorCelda == null || valorCelda.toString().trim().isEmpty()) {
        throw new IllegalArgumentException(
            "La calificación de " + nombreEstudiante + " no puede estar vacía."
        );
    }

    String texto = valorCelda.toString().trim().replace(',', '.');

    // Validar que sea número positivo con máximo 2 decimales (sin letras ni negativos)
    if (!texto.matches("\\d+(\\.\\d{1,2})?")) {
        throw new IllegalArgumentException(
            "Calificación inválida para " + nombreEstudiante + ": '" + texto + "'.\n" +
            "Ingrese un número positivo con hasta dos decimales (ej. 8.50)."
        );
    }

    double nota = Double.parseDouble(texto);
    if (nota < NOTA_MIN || nota > NOTA_MAX) {
        throw new IllegalArgumentException(
            "Calificación fuera de rango para " + nombreEstudiante + ": " + nota + ".\n" +
            "Debe estar entre " + NOTA_MIN + " y " + NOTA_MAX + "."
        );
    }

    return nota;
    }
    
    public List<Nota> validarYConstruirNotas(String nombreEvaluacion, String textoFecha, List<Object[]> filasTabla) {
    String evalLimpia = validarNombreEvaluacion(nombreEvaluacion);
    validarFecha(textoFecha);

    if (filasTabla == null || filasTabla.isEmpty()) {
        throw new IllegalArgumentException("Primero cargue la lista de estudiantes.");
    }

    List<Nota> notasValidas = new ArrayList<>();
    for (Object[] fila : filasTabla) {
        String cedula = String.valueOf(fila[0]).trim();
        String estudiante = String.valueOf(fila[1]).trim();
        double calificacion = validarCalificacion(fila[2], estudiante);
        notasValidas.add(new Nota(cedula, evalLimpia, calificacion));
    }
    return notasValidas;
    }
    
    
}