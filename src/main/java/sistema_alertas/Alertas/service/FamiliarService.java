package sistema_alertas.Alertas.service;

import java.util.List;

import sistema_alertas.Alertas.model.Familiar;

public interface FamiliarService {
    List<Familiar> obtenerPorEstudiante(Integer estudianteId);
    Familiar guardar(Familiar familiar);
    Familiar actualizar(Integer id, Familiar datos);
    boolean eliminar(Integer id);
}
