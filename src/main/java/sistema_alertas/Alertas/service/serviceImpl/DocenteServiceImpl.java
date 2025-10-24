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

    // Crear usuario asociado
    Usuario usuario = new Usuario();
    usuario.setCedula(cedula);
    usuario.setNombres(docente.getNombres() + " " + docente.getApellidos());
    usuario.setCorreo(docente.getCorreo());
    usuario.setRol(0); // 0 = docente
    usuario.setPassword(passwordEncoder.encode(cedula)); // Contraseña encriptada igual a la cédula

    // Guardar usuario
    Usuario creado = usuarioRepository.save(usuario);

    // Asociar el usuario al docente
    docente.setUsuario(creado);

    // Guardar docente
    return repository.save(docente);
}

    @Override
    public Docente actualizar(Integer id, Docente datos) {
        Docente actual = obtenerPorId(id);
        if (actual == null)
            return null;

        Docente actualizado = new Docente(
                datos.getTipoDoc(),
                datos.getNroDoc(),
                datos.getNombres(),
                datos.getApellidos(),
                datos.getCorreo());

        actualizado.setId(id);
        return repository.save(actualizado);
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