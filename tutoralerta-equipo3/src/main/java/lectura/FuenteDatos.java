
package lectura;
import java.util.List;

/**
 *
 * @author Loky
 */
public interface FuenteDatos<T> {
    List<T> leer();
}
