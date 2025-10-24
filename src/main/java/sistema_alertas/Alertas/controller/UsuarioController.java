package sistema_alertas.Alertas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import sistema_alertas.Alertas.model.Usuario;
import sistema_alertas.Alertas.dto.CambioContrasenaDTO;
import sistema_alertas.Alertas.dto.LoginDTO;
import sistema_alertas.Alertas.model.Docente;
import sistema_alertas.Alertas.model.Psicorientador;
import sistema_alertas.Alertas.model.Estudiante;
import sistema_alertas.Alertas.repository.*;
import sistema_alertas.Alertas.util.JwtUtil;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/usuarios", produces = "application/json")

public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private DocenteRepository docenteRepository;

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private PsicorientadorRepository psicorientadorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable Long id) {
        return usuarioRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/generar")
    public ResponseEntity<?> generarUsuario(
            @RequestParam String cedula,
            @RequestParam int rol) {

        if (usuarioRepository.existsByCedula(cedula)) {
            return ResponseEntity.badRequest().body("El usuario ya existe.");
        }

        Usuario usuario = new Usuario();
        usuario.setCedula(cedula);
        usuario.setRol(rol);
        usuario.setPassword(passwordEncoder.encode(cedula));

        switch (rol) {
            case 0: { // Docente
                Optional<Docente> dOpt = docenteRepository.findByNroDoc(cedula);
                if (dOpt.isEmpty())
                    return ResponseEntity.badRequest().body("Docente no encontrado.");
                Docente d = dOpt.get();
                usuario.setNombres(d.getNombres() + " " + d.getApellidos());
                usuario.setCorreo(d.getCorreo());
                usuario.setPersonaId(d.getId()); // <- Aquí asignas el DOCE_ID
                break;
            }
            case 1: { // Estudiante
                Optional<Estudiante> eOpt = estudianteRepository.findByNroDoc(cedula);
                if (eOpt.isEmpty())
                    return ResponseEntity.badRequest().body("Estudiante no encontrado.");
                Estudiante e = eOpt.get();
                usuario.setNombres(e.getNombres() + " " + e.getApellidos());
                usuario.setCorreo(e.getCorreo());
                usuario.setPersonaId(e.getId()); // <- Aquí asignas el ESTU_ID
                break;
            }
            case 2: { // Psicorientador
                Optional<Psicorientador> pOpt = psicorientadorRepository.findByNroDoc(cedula);
                if (pOpt.isEmpty())
                    return ResponseEntity.badRequest().body("Psicorientador no encontrado.");
                Psicorientador p = pOpt.get();
                usuario.setNombres(p.getNombres() + " " + p.getApellidos());
                usuario.setCorreo(p.getCorreo());
                usuario.setPersonaId(p.getId()); // <- Aquí asignas el PSICO_ID
                break;
            }

            case 3: { // Administrador
                usuario.setNombres("Administrador");
                usuario.setCorreo(cedula + "@admin.com");
                // Aquí se asigna el ID al mismo personaId
                Usuario creado = usuarioRepository.save(usuario);
                creado.setPersonaId(creado.getId().intValue());
                usuarioRepository.save(creado);
                return ResponseEntity.ok(creado);
            }
            default:
                return ResponseEntity.badRequest().body("Rol inválido.");
        }

  
        Usuario creado = usuarioRepository.save(usuario);

        switch (rol) {
            case 0: {
                Docente d = docenteRepository.findByNroDoc(cedula).get();
                d.setUsuario(creado);
                docenteRepository.save(d);
                break;
            }
            case 1: {
                Estudiante e = estudianteRepository.findByNroDoc(cedula).get();
                e.setUsuario(creado);
                estudianteRepository.save(e);
                break;
            }
            case 2: {
                Psicorientador p = psicorientadorRepository.findByNroDoc(cedula).get();
                p.setUsuario(creado);
                psicorientadorRepository.save(p);
                break;
            }
        }

        return ResponseEntity.ok(creado);
    }

    @PostMapping("/generar-masivo")
    public ResponseEntity<?> generarUsuariosMasivamente(@RequestParam int rol) {
        int creados = 0;

        switch (rol) {
            case 0:
                for (Docente d : docenteRepository.findAll()) {
                    if (d.getUsuario() == null && !usuarioRepository.existsByCedula(d.getNroDoc())) {
                        Usuario u = new Usuario();
                        u.setCedula(d.getNroDoc());
                        u.setNombres(d.getNombres() + " " + d.getApellidos());
                        u.setCorreo(d.getCorreo());
                        u.setPassword(passwordEncoder.encode(d.getNroDoc()));
                        u.setRol(0);
                        u.setPersonaId(d.getId());
                        Usuario creado = usuarioRepository.save(u);
                        d.setUsuario(creado);
                        docenteRepository.save(d);
                        creados++;
                    }
                }
                break;

            case 1:
                for (Estudiante e : estudianteRepository.findAll()) {
                    if (e.getUsuario() == null && !usuarioRepository.existsByCedula(e.getNroDoc())) {
                        Usuario u = new Usuario();
                        u.setCedula(e.getNroDoc());
                        u.setNombres(e.getNombres() + " " + e.getApellidos());
                        u.setCorreo(e.getCorreo());
                        u.setPassword(passwordEncoder.encode(e.getNroDoc()));
                        u.setRol(1);
                        u.setPersonaId(e.getId());
                        Usuario creado = usuarioRepository.save(u);
                        e.setUsuario(creado);
                        estudianteRepository.save(e);
                        creados++;
                    }
                }
                break;

            case 2: // Psicorientadores
                for (Psicorientador p : psicorientadorRepository.findAll()) {
                    if (p.getUsuario() == null && !usuarioRepository.existsByCedula(p.getNroDoc())) {
                        Usuario u = new Usuario();
                        u.setCedula(p.getNroDoc());
                        u.setNombres(p.getNombres() + " " + p.getApellidos());
                        u.setCorreo(p.getCorreo());
                        u.setPassword(passwordEncoder.encode(p.getNroDoc()));
                        u.setRol(2);
                        u.setPersonaId(p.getId());
                        Usuario creado = usuarioRepository.save(u);
                        p.setUsuario(creado);
                        psicorientadorRepository.save(p);
                        creados++;
                    }
                }
                break;

            default:
                return ResponseEntity.badRequest().body("Rol inválido.");
        }

        return ResponseEntity.ok("Usuarios creados: " + creados);
    }

    @PutMapping("/cambiar-contrasena")
    public ResponseEntity<?> cambiarContrasena(@RequestBody CambioContrasenaDTO dto) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByCedula(dto.getCedula());

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Usuario no encontrado");
        }

        Usuario usuario = usuarioOpt.get();

        if (!passwordEncoder.matches(dto.getContrasenaActual(), usuario.getPassword())) {
            return ResponseEntity.status(403).body("Contraseña actual incorrecta");
        }

        usuario.setPassword(passwordEncoder.encode(dto.getContrasenaNueva()));
        usuarioRepository.save(usuario);

        return ResponseEntity.ok("Contraseña actualizada exitosamente");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO dto) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByCedula(dto.getCedula());

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }

        Usuario usuario = usuarioOpt.get();

        if (!passwordEncoder.matches(dto.getPassword(), usuario.getPassword())) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }

        String token = jwtUtil.generarToken(usuario.getCedula(), usuario.getRol(), usuario.getId());

        return ResponseEntity.ok(
                Map.of(
                        "token", token,
                        "id", usuario.getId(),
                        "rol", usuario.getRol(),
                        "personaId", usuario.getPersonaId(),
                        "nombres", usuario.getNombres(),
                        "correo", usuario.getCorreo()));
    }

}
