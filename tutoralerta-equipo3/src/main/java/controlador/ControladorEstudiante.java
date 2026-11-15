
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
    
    public List<Estudiante> ordenPorApellido() {
        List<Estudiante> ordenados = new ArrayList<>(listaEstudiantes);
        for (int posActual = 0; posActual < ordenados.size() - 1; posActual++) {
            for (int posSiguiente = posActual + 1; posSiguiente < ordenados.size(); posSiguiente++) {
                Estudiante actual = ordenados.get(posActual);
                Estudiante siguiente = ordenados.get(posSiguiente);
                if (actual.apellidos().compareToIgnoreCase(siguiente.apellidos()) > 0) {
                    ordenados.set(posActual, siguiente);
                    ordenados.set(posSiguiente, actual);
                }
            }
        }
        return ordenados;
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
    public boolean editarEstudiante(String cedula, String nuevosNombres, String nuevosApellidos, String nuevoCorreo) {
        if (cedula == null){
            return false;
        }
        for (int i = 0; i < listaEstudiantes.size(); i++){
            Estudiante actual = listaEstudiantes.get(i);
            if (actual.cedula().equals(cedula)){
                Estudiante actualizado =new Estudiante(actual.cedula(), nuevosNombres, nuevosApellidos, nuevoCorreo);
                listaEstudiantes.set(i, actualizado);
                return true;
            }
        }
        return false;
    }
}
