package sistema_alertas.Alertas.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sistema_alertas.Alertas.model.Estudiante;
import sistema_alertas.Alertas.model.Seguimiento;
import sistema_alertas.Alertas.model.SeguimientoCita;
import sistema_alertas.Alertas.repository.SeguimientoCitaRepository;
import sistema_alertas.Alertas.repository.SeguimientoRepository;
import sistema_alertas.Alertas.service.SeguimientoService;

import java.util.List;
import java.util.Optional;

@Service
public class SeguimientoServiceImpl implements SeguimientoService {

    @Autowired
    private SeguimientoRepository seguimientoRepository;

    @Autowired
    private SeguimientoCitaRepository seguimientoCitaRepository;

    @Override
    public Optional<Seguimiento> obtenerPorConsulta(Integer consultaId) {
        return Optional.ofNullable(seguimientoRepository.findByConsultaId(consultaId));
    }

    @Override
    public Seguimiento guardar(Seguimiento seguimiento) {
        return seguimientoRepository.save(seguimiento);
    }

    @Override
    public Seguimiento obtenerUltimoSeguimiento(Integer consultaId) {
        return seguimientoRepository.findByConsultaId(consultaId);
    }

    @Override
    public long contarPorConsulta(Integer consultaId) {
        return seguimientoRepository.countByConsultaId(consultaId);
    }

    @Override
    public void asociarASeguimientoYCita(Integer seguimientoId, Integer citaId) {
        SeguimientoCita relacion = new SeguimientoCita();
        relacion.setSeguimientoId(seguimientoId);
        relacion.setCitaId(citaId);
        seguimientoCitaRepository.save(relacion);
    }

    @Override
    public Optional<Seguimiento> obtenerPorId(Integer id) {
        return seguimientoRepository.findById(id);
    }

    @Override
    public List<Estudiante> obtenerEstudiantesConSeguimientos() {
        return seguimientoRepository.obtenerEstudiantesConSeguimientos();
    }

    @Override
public List<Seguimiento> obtenerPorEstudiante(Integer estudianteId) {
    return seguimientoRepository.findByConsultaEstudianteId(estudianteId);
}
}
