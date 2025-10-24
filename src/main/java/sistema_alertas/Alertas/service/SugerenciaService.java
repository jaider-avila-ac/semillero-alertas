package sistema_alertas.Alertas.service;

import java.util.List;

import sistema_alertas.Alertas.model.Sugerencia;

public interface SugerenciaService {
    List<Sugerencia> obtenerTodas();
    Sugerencia crear(Sugerencia sugerencia);
    void eliminar(Long id);
}