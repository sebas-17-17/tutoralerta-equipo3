
package modelo;

import java.time.LocalDate;
import java.util.List;


public record Sesion(
    LocalDate fecha, 
    List<String> cedulasPresentes, 
    List<String> cedulasAusentes
) {}
