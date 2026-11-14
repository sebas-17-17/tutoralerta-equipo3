
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
    
    public boolean esCedulaValida(String cedula) {
        if (cedula == null) {
            return false;
        }
        return cedula.trim().matches("\\d{10}");
    }

    public boolean existeCedula(String cedula) {
        if (cedula == null) {
            return false;
        }
        String cedulaLimpia = cedula.trim();
        for (Estudiante e : listaEstudiantes) {
            if (e.cedula().equals(cedulaLimpia)) {
                return true;
            }
        }
        return false;
    }
}
