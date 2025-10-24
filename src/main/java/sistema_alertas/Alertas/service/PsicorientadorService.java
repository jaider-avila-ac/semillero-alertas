package sistema_alertas.Alertas.service;

import java.util.List;
import sistema_alertas.Alertas.model.Psicorientador;

public interface PsicorientadorService {
    List<Psicorientador> obtenerTodos();
    Psicorientador obtenerPorId(Integer id);
    List<Psicorientador> buscarPorNombre(String nombre);
    List<Psicorientador> buscarPorDocumento(String documento);
    Psicorientador guardar(Psicorientador psicorientador);
    Psicorientador actualizar(Integer id, Psicorientador datos);
    boolean eliminar(Integer id);
    long contar();
}