package sistema_alertas.Alertas.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import sistema_alertas.Alertas.model.Docente;
import sistema_alertas.Alertas.model.Usuario;
import sistema_alertas.Alertas.repository.DocenteRepository;
import sistema_alertas.Alertas.repository.UsuarioRepository;
import sistema_alertas.Alertas.service.DocenteService;

@Service
public class DocenteServiceImpl implements DocenteService {

@Autowired
private UsuarioRepository usuarioRepository;

@Autowired
private PasswordEncoder passwordEncoder;


    @Autowired
    private DocenteRepository repository;

    @Override
    public List<Docente> obtenerTodos() {
        return repository.findAll();
    }

    @Override
    public Docente obtenerPorId(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Docente> buscarPorNombre(String nombre) {
        return repository.findByNombresContainingIgnoreCase(nombre);
    }

    @Override
    public List<Docente> buscarPorDocumento(String documento) {
        return repository.findByNroDocContaining(documento);
    }

   @Override
public Docente guardar(Docente docente) {
    String cedula = docente.getNroDoc();

    // Validar si ya existe un usuario con esa cedula
    if (usuarioRepository.existsByCedula(cedula)) {
        throw new RuntimeException("Ya existe un usuario con esa cédula");
    }

    // Guardar docente primero para obtener su ID generado
    Docente guardado = repository.save(docente);

    // Crear usuario con persona_id ya conocido
    Usuario usuario = new Usuario();
    usuario.setCedula(cedula);
    usuario.setNombres(guardado.getNombres() + " " + guardado.getApellidos());
    usuario.setCorreo(guardado.getCorreo());
    usuario.setRol(0);
    usuario.setPassword(passwordEncoder.encode(cedula));
    usuario.setPersonaId(guardado.getId());

    Usuario creado = usuarioRepository.save(usuario);

    // Vincular usuario al docente
    guardado.setUsuario(creado);
    return repository.save(guardado);
}

    @Override
    public Docente actualizar(Integer id, Docente datos) {
        Docente actual = obtenerPorId(id);
        if (actual == null)
            return null;

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
        Docente docente = obtenerPorId(id);
        if (docente != null) {
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