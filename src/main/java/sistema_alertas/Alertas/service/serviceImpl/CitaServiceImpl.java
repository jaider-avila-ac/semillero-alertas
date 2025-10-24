package sistema_alertas.Alertas.service.serviceImpl;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sistema_alertas.Alertas.model.Cita;
import sistema_alertas.Alertas.repository.CitaRepository;
import sistema_alertas.Alertas.service.CitaService;

@Service
public class CitaServiceImpl implements CitaService {

    @Autowired
    private CitaRepository repository;

    @Override
    public List<Cita> obtenerTodas() {
        return repository.findAll();
    }

    @Override
    public Cita obtenerPorId(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Cita guardar(Cita cita) {
        return repository.save(cita);
    }

    @Override
    public Cita actualizar(Integer id, Cita datos) {
        Cita actual = obtenerPorId(id);
        if (actual == null) return null;

        actual.setFecha(datos.getFecha());
        actual.setEstudiante(datos.getEstudiante());
        actual.setPsicorientador(datos.getPsicorientador());
        actual.setEstado(datos.getEstado());

        return repository.save(actual);
    }

    @Override
    public boolean eliminar(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Cita> buscarPorEstudiante(Integer estuId) {
        return repository.findByEstudianteId(estuId);
    }

    @Override
    public List<Cita> buscarPorPsicorientador(Integer psicId) {
        return repository.findByPsicorientadorId(psicId);
    }

    @Override
    public List<Cita> buscarPorFecha(Date fecha) {
        return repository.findByFecha(fecha);
    }
}
