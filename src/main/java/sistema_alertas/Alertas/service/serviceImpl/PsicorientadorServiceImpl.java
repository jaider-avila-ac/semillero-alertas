package sistema_alertas.Alertas.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import sistema_alertas.Alertas.model.Psicorientador;
import sistema_alertas.Alertas.model.Usuario;
import sistema_alertas.Alertas.repository.PsicorientadorRepository;
import sistema_alertas.Alertas.repository.UsuarioRepository;
import sistema_alertas.Alertas.service.PsicorientadorService;

@Service
public class PsicorientadorServiceImpl implements PsicorientadorService {

    @Autowired
    private PsicorientadorRepository repository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<Psicorientador> obtenerTodos() {
        return repository.findAll();
    }

    @Override
    public Psicorientador obtenerPorId(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Psicorientador> buscarPorNombre(String nombre) {
        return repository.findByNombresContainingIgnoreCase(nombre);
    }

    @Override
    public List<Psicorientador> buscarPorDocumento(String documento) {
        return repository.findByNroDocContaining(documento);
    }

    @Override
    public Psicorientador guardar(Psicorientador psic) {
        // Verificar si ya existe un usuario con esa cédula
        String cedula = psic.getNroDoc();
        if (usuarioRepository.existsByCedula(cedula)) {
            // Si ya existe, verificar si está asignado al psicorientador
            Usuario existente = usuarioRepository.findByCedula(cedula).orElse(null);
            if (existente != null && psic.getUsuario() == null) {
                // Solo lo asignamos si no está asociado aún
                psic.setUsuario(existente);
            }
        } else {
            // Guardar psicorientador primero para obtener su ID generado
            Psicorientador guardado = repository.save(psic);

            Usuario nuevo = new Usuario();
            nuevo.setCedula(cedula);
            nuevo.setNombres(guardado.getNombres() + " " + guardado.getApellidos());
            nuevo.setCorreo(guardado.getCorreo());
            nuevo.setRol(2);
            nuevo.setPassword(passwordEncoder.encode(cedula));
            nuevo.setPersonaId(guardado.getId());

            Usuario creado = usuarioRepository.save(nuevo);
            guardado.setUsuario(creado);
            return repository.save(guardado);
        }

        return repository.save(psic);
    }

    @Override
    public Psicorientador actualizar(Integer id, Psicorientador datos) {
        Psicorientador actual = obtenerPorId(id);
        if (actual == null) return null;

        actual.setTipoDoc(datos.getTipoDoc());
        actual.setNroDoc(datos.getNroDoc());
        actual.setNombres(datos.getNombres());
        actual.setApellidos(datos.getApellidos());
        actual.setCorreo(datos.getCorreo());

        // Sincronizar usuario vinculado si existe
        if (actual.getUsuario() != null) {
            Usuario u = actual.getUsuario();
            u.setCedula(datos.getNroDoc());
            u.setNombres(datos.getNombres() + " " + datos.getApellidos());
            u.setCorreo(datos.getCorreo());
            usuarioRepository.save(u);
        }

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
    public long contar() {
        return repository.count();
    }
}
