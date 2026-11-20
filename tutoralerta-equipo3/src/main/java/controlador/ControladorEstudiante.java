
package controlador;
import java.io.File;
import java.io.PrintWriter;
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
        guardarEnCsv();
    }
    
    //Método para actualizar los datos de un estudiante existente (excepto la cédula)
    public boolean actualizarEstudiante(String cedula, String nuevoNombre, String nuevoApellido, String nuevoCorreo) {
        for (int i = 0; i < listaEstudiantes.size(); i++) {
            Estudiante e = listaEstudiantes.get(i);
            if (e.cedula().equals(cedula)) {
                // Reemplaza con los nuevos datos manteniendo la cédula intacta
                listaEstudiantes.set(i, new Estudiante(cedula, nuevoNombre, nuevoApellido, nuevoCorreo));
                guardarEnCsv(); // Guarda los cambios en el archivo físico
                return true;
            }
        }
        return false;
    }

    public void guardarEnCsv() {
        String rutaArchivo = new File("estudiantes.csv").exists()
        ? "estudiantes.csv"
        : "datos/recursos/estudiantes.csv";
        try (PrintWriter pw = new PrintWriter(new File(rutaArchivo))) {
            pw.println("cedula;nombres;apellidos;correo");
            for (Estudiante e : listaEstudiantes) {
                pw.println(e.cedula() + "," + e.nombres() + "," + e.apellidos() + "," + e.correo());
            }
        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(null, "Error al guardar en el archivo CSV: " + ex.getMessage());
        }
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
