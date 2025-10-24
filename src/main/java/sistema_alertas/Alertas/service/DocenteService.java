package sistema_alertas.Alertas.service;

import java.util.List;
import sistema_alertas.Alertas.model.Docente;

public interface DocenteService {
    List<Docente> obtenerTodos();

    Docente obtenerPorId(Integer id);

    List<Docente> buscarPorNombre(String nombre);

    List<Docente> buscarPorDocumento(String documento);

    Docente guardar(Docente docente);

    Docente actualizar(Integer id, Docente datos);

    boolean eliminar(Integer id);

    long contar();

    
}
