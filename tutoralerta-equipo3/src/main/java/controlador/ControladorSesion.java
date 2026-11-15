package controlador;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import modelo.Estudiante;

public class ControladorSesion {

    private final ControladorEstudiante controladorEstudiante;
    private static final String CARPETA_ASISTENCIAS = "asistencias"; // Se creará dentro de dist/ al lado del jar

    public ControladorSesion() {
        this(new ControladorEstudiante());
    }

    public ControladorSesion(ControladorEstudiante controladorEstudiante) {
        this.controladorEstudiante = controladorEstudiante;
        File directorio = new File(CARPETA_ASISTENCIAS);
        if (!directorio.exists()) {
            directorio.mkdir();
        }
    }

    public LocalDate validarFecha(String textoFecha) {
        if (textoFecha == null || textoFecha.trim().isEmpty()) {
            throw new IllegalArgumentException("Por favor ingrese una fecha (AAAA-MM-DD).");
        }
        try {
            return LocalDate.parse(textoFecha.trim());
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de fecha inválido. Use AAAA-MM-DD");
        }
    }

  
    //Carga la lista: Si ya existe un archivo CSV para esa fecha, lo lee
    //Si no existe, carga a todos los estudiantes como presentes por defecto 
    
    public List<Object[]> cargarListaPresentes(String textoFecha) {
        LocalDate fechaSeleccionada = validarFecha(textoFecha);
        File archivoDia = new File(CARPETA_ASISTENCIAS + "/asistencia_" + fechaSeleccionada + ".csv");

        List<Object[]> filas = new ArrayList<>();

        // CASO 1: Ya existe el archivo de asistencia para este día
        if (archivoDia.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(archivoDia))) {
                String linea;
                boolean esPrimeraLinea = true;
                while ((linea = br.readLine()) != null) {
                    if (esPrimeraLinea) {
                        esPrimeraLinea = false;
                        continue;
                    }
                    String[] partes = linea.split(";");
                    if (partes.length >= 5) {
                        filas.add(new Object[]{
                            partes[0], 
                            partes[1], 
                            Boolean.parseBoolean(partes[2]), 
                            Boolean.parseBoolean(partes[3]), 
                            partes[4]  
                        });
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException("Error al leer el archivo de asistencia de la fecha.", e);
            }
            return filas;
        }

        // CASO 2: No existe el archivo, carga la lista base 
        List<Estudiante> estudiantes = controladorEstudiante.ordenPorApellido();
        for (Estudiante est : estudiantes) {
            filas.add(new Object[]{
                est.cedula(),
                est.apellidos() + " " + est.nombres(),
                true,  
                false,  
                ""     
            });
        }
        return filas;
    }


    //Guarda la asistencia actual en un archivo CSV específico para la fecha
    public void guardarAsistencia(String textoFecha, List<Object[]> datosTabla) {
        LocalDate fechaSeleccionada = validarFecha(textoFecha);
        File archivoDia = new File(CARPETA_ASISTENCIAS + "/asistencia_" + fechaSeleccionada + ".csv");

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivoDia))) {
            bw.write("cedula;estudiante;presente;ausente;observacion");
            bw.newLine();

            for (Object[] fila : datosTabla) {
                String cedula = String.valueOf(fila[0]);
                String estudiante = String.valueOf(fila[1]);
                boolean presente = (Boolean) fila[2];
                boolean ausente = (Boolean) fila[3];
                Object obsObj = fila[4];
                String observacion = (obsObj != null) ? String.valueOf(obsObj) : "";

                bw.write(cedula + ";" + estudiante + ";" + presente + ";" + ausente + ";" + observacion);
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar el archivo CSV de asistencia.", e);
        }
    }
}