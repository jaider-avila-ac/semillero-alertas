package sistema_alertas.Alertas.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sistema_alertas.Alertas.model.Antecedente;
import sistema_alertas.Alertas.repository.AntecedenteRepository;
import sistema_alertas.Alertas.service.AntecedenteService;

import java.util.List;
import java.util.Optional;

@Service
public class AntecedenteServiceImpl implements AntecedenteService {

    @Autowired
    private AntecedenteRepository repository;

    @Override
    public List<Antecedente> obtenerTodosPorEstudiante(Integer estudianteId) {
        return repository.findByEstudianteIdOrderByCategoriaIdAscFechaRegistroAsc(estudianteId);
    }

    @Override
    public Antecedente guardar(Antecedente antecedente) {
        return repository.save(antecedente);
    }

    @Override
    public Antecedente actualizar(Integer id, Antecedente nuevosDatos) {
        Optional<Antecedente> existente = repository.findById(id);
        if (existente.isPresent()) {
            Antecedente a = existente.get();
            a.setDetalle(nuevosDatos.getDetalle());
            a.setCategoria(nuevosDatos.getCategoria());
            return repository.save(a);
        }
        return null;
    }
}
