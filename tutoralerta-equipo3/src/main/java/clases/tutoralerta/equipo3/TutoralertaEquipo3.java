package clases.tutoralerta.equipo3;
import controlador.ControladorEstudiante;
import java.nio.file.Paths;
import lectura.LectorEstudiantesCsv;
import lectura.FuenteDatos;
import modelo.Estudiante;
import vista.vistaMenuPrincipal;

public class TutoralertaEquipo3 {

    public static void main(String[] args) {
        FuenteDatos<Estudiante> lectorEstudiantes = new LectorEstudiantesCsv(Paths.get("datos/recursos/estudiantes.csv"));

        ControladorEstudiante controladorEstudiante = new ControladorEstudiante();
        controladorEstudiante.cargarDatosIniciales(lectorEstudiantes);

        System.out.println("Estudiantes cargados al iniciar: " + controladorEstudiante.getListaEstudiantes().size());
    }
}
