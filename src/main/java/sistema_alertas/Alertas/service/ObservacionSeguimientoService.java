package sistema_alertas.Alertas.service;

import java.util.List;
import sistema_alertas.Alertas.model.ObservacionSeguimiento;

public interface ObservacionSeguimientoService {

    List<ObservacionSeguimiento> obtenerPorSeguimiento(Integer seguimientoId);

    ObservacionSeguimiento guardar(ObservacionSeguimiento observacion);

    ObservacionSeguimiento actualizar(Integer id, ObservacionSeguimiento datos);

    boolean eliminar(Integer id);


 List<ObservacionSeguimiento> obtenerObservacionesPorEstudiante(Integer estudianteId);

    
}
