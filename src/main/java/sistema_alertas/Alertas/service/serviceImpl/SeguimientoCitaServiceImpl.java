package sistema_alertas.Alertas.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sistema_alertas.Alertas.model.SeguimientoCita;
import sistema_alertas.Alertas.repository.SeguimientoCitaRepository;
import sistema_alertas.Alertas.service.SeguimientoCitaService;

import java.util.List;

@Service
public class SeguimientoCitaServiceImpl implements SeguimientoCitaService {

    @Autowired
    private SeguimientoCitaRepository seguimientoCitaRepository;

    @Override
    public List<SeguimientoCita> obtenerPorCitaId(Integer citaId) {
        return seguimientoCitaRepository.findByCitaId(citaId);
    }

    @Override
    public List<SeguimientoCita> obtenerPorSeguimientoId(Integer seguimientoId) {
        return seguimientoCitaRepository.findBySeguimientoId(seguimientoId);
    }


    @Override
    public void guardarRelacion(Integer citaId, Integer seguimientoId) {
        SeguimientoCita relacion = new SeguimientoCita();
        relacion.setCitaId(citaId);
        relacion.setSeguimientoId(seguimientoId);
        seguimientoCitaRepository.save(relacion);
    }
}
