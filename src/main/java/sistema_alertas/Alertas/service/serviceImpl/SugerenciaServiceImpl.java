package sistema_alertas.Alertas.service.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import sistema_alertas.Alertas.model.Sugerencia;
import sistema_alertas.Alertas.repository.SugerenciaRepository;
import sistema_alertas.Alertas.service.SugerenciaService;

@Service
public class SugerenciaServiceImpl implements SugerenciaService {

    private final SugerenciaRepository sugerenciaRepository;

    public SugerenciaServiceImpl(SugerenciaRepository sugerenciaRepository) {
        this.sugerenciaRepository = sugerenciaRepository;
    }

    @Override
    public List<Sugerencia> obtenerTodas() {
        return sugerenciaRepository.findAll();
    }

    @Override
    public Sugerencia crear(Sugerencia sugerencia) {
        return sugerenciaRepository.save(sugerencia);
    }

    @Override
    public void eliminar(Long id) {
        sugerenciaRepository.deleteById(id);
    }
}