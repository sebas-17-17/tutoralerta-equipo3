
package controlador;
import lectura.FuenteDatos;
import modelo.Estudiante;
import java.util.ArrayList;
import java.util.List;

public class ControladorEstudiante {

    private final List<Estudiante> listaEstudiantes;

    public ControladorEstudiante() {
        this.listaEstudiantes = new ArrayList<>();
    }

    public void cargarDatosIniciales(FuenteDatos<Estudiante> fuenteEstudiantes) {
        listaEstudiantes.clear();
        listaEstudiantes.addAll(fuenteEstudiantes.leer());
    }

    public List<Estudiante> getListaEstudiantes() {
        return listaEstudiantes;
    }

    public void agregarEstudiante(Estudiante estudiante) {
        listaEstudiantes.add(estudiante);
    }
}
