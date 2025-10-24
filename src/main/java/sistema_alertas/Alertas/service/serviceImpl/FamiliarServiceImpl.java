package sistema_alertas.Alertas.service.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sistema_alertas.Alertas.model.Familiar;
import sistema_alertas.Alertas.repository.FamiliarRepository;
import sistema_alertas.Alertas.service.FamiliarService;

@Service
public class FamiliarServiceImpl implements FamiliarService {

    @Autowired
    private FamiliarRepository repository;

    @Override
    public List<Familiar> obtenerPorEstudiante(Integer estudianteId) {
        return repository.findByEstudiante_Id(estudianteId);
    }

    @Override
    public Familiar guardar(Familiar familiar) {
        return repository.save(familiar);
    }

    @Override
    public Familiar actualizar(Integer id, Familiar datos) {
        Optional<Familiar> fam = repository.findById(id);
        if (fam.isPresent()) {
            Familiar f = fam.get();
            f.setNombres(datos.getNombres());
            f.setApellidos(datos.getApellidos());
            f.setParentesco(datos.getParentesco());
            f.setFechaNacimiento(datos.getFechaNacimiento());
            f.setEscolaridad(datos.getEscolaridad());
            f.setTelefono(datos.getTelefono());
            f.setHorario(datos.getHorario());
            return repository.save(f);
        }
        return null;
    }

    @Override
    public boolean eliminar(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}