package sistema_alertas.Alertas.service.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sistema_alertas.Alertas.model.ObservacionSeguimiento;
import sistema_alertas.Alertas.repository.ObservacionSeguimientoRepository;
import sistema_alertas.Alertas.service.ObservacionSeguimientoService;

@Service
public class ObservacionSeguimientoServiceImpl implements ObservacionSeguimientoService {

    @Autowired
    private ObservacionSeguimientoRepository repository;

    @Override
    public List<ObservacionSeguimiento> obtenerPorSeguimiento(Integer seguimientoId) {

        return repository.findBySeguimientoIdOrderByFechaAsc(seguimientoId);
    }

    @Override
    public ObservacionSeguimiento guardar(ObservacionSeguimiento observacion) {
        return repository.save(observacion);
    }

    

    @Override
    public ObservacionSeguimiento actualizar(Integer id, ObservacionSeguimiento datos) {
        Optional<ObservacionSeguimiento> opt = repository.findById(id);
        if (opt.isEmpty())
            return null;

        ObservacionSeguimiento actual = opt.get();
        actual.setTexto(datos.getTexto());
        actual.setFecha(datos.getFecha());
        actual.setSeguimiento(datos.getSeguimiento());

        return repository.save(actual);
    }

    @Override
    public boolean eliminar(Integer id) {
        if (!repository.existsById(id))
            return false;
        repository.deleteById(id);
        return true;
    }

    @Override
public List<ObservacionSeguimiento> obtenerObservacionesPorEstudiante(Integer estudianteId) {
    return repository.findBySeguimientoConsultaEstudianteIdOrderByFechaAsc(estudianteId);
}

}
