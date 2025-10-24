package sistema_alertas.Alertas.service;

import java.sql.Date;
import java.util.List;
import sistema_alertas.Alertas.model.Cita;

public interface CitaService {

    List<Cita> obtenerTodas();

    Cita obtenerPorId(Integer id);

    Cita guardar(Cita cita);

    Cita actualizar(Integer id, Cita datos);

    boolean eliminar(Integer id);

    List<Cita> buscarPorEstudiante(Integer estuId);

    List<Cita> buscarPorPsicorientador(Integer psicId);

    List<Cita> buscarPorFecha(Date fecha);
}