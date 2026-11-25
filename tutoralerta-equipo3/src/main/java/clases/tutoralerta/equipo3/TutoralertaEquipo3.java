package clases.tutoralerta.equipo3;

import controlador.ControladorEstudiante;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.SwingUtilities;
import lectura.FuenteDatos;
import lectura.LectoresCsv.LectorEstudiantesCsv;
import modelo.Estudiante;
import vista.vistaMenuPrincipal;

public class TutoralertaEquipo3 {

    public static void main(String[] args) {
        Path rutaCsv = Paths.get("estudiantes.csv");

        FuenteDatos<Estudiante> lectorEstudiantes = new LectorEstudiantesCsv(rutaCsv);

        ControladorEstudiante controladorEstudiante = new ControladorEstudiante();
        controladorEstudiante.cargarDatosIniciales(lectorEstudiantes);

        System.out.println("Estudiantes cargados al iniciar: " + controladorEstudiante.getListaEstudiantes().size());

        SwingUtilities.invokeLater(() -> {
            new vistaMenuPrincipal(controladorEstudiante).setVisible(true);
        });
    }
}