package sistema_alertas.Alertas.service;

import sistema_alertas.Alertas.model.SeguimientoCita;

import java.util.List;

public interface SeguimientoCitaService {
    List<SeguimientoCita> obtenerPorCitaId(Integer citaId);
    List<SeguimientoCita> obtenerPorSeguimientoId(Integer seguimientoId);


    void guardarRelacion(Integer citaId, Integer seguimientoId);
}
