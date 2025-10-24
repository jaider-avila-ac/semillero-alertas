package sistema_alertas.Alertas.service.serviceImpl;

import java.io.IOException;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;
import sistema_alertas.Alertas.model.Estudiante;
import sistema_alertas.Alertas.repository.EstudianteRepository;
import sistema_alertas.Alertas.service.EstudianteService;

import java.nio.file.*;

@Service
public class EstudianteServiceImpl implements EstudianteService {

    @Autowired
    private EstudianteRepository repository;

    @Override
    public List<Estudiante> obtenerTodos() {
        return repository.findAll(
                Sort.by(Sort.Direction.ASC, "nombres")
                        .and(Sort.by(Sort.Direction.ASC, "apellidos")));
    }

    @Override
    public Estudiante obtenerPorId(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Estudiante> buscarPorNombre(String nombre) {
        return repository.findByNombresContainingIgnoreCase(nombre);
    }

   @Override
public Estudiante guardar(Estudiante datos) {
    return repository.save(datos);
}

   @Override
public Estudiante actualizar(Integer id, Estudiante datos) {
    Estudiante actual = obtenerPorId(id);
    if (actual == null) return null;

    // Solo se actualizan los campos editables (no la imagen si ya existe)
    actual.setTipoDoc(datos.getTipoDoc());
    actual.setNroDoc(datos.getNroDoc());
    actual.setNombres(datos.getNombres());
    actual.setApellidos(datos.getApellidos());
    actual.setGenero(datos.getGenero());
    actual.setFechaNac(datos.getFechaNac());
    actual.setDireccion(datos.getDireccion());
    actual.setBarrio(datos.getBarrio());
    actual.setEstrato(datos.getEstrato());
    actual.setSisben(datos.getSisben());
    actual.setEps(datos.getEps());
    actual.setRh(datos.getRh());
    actual.setAcudiente(datos.getAcudiente());
    actual.setTel(datos.getTel());
    actual.setSms(datos.getSms());
    actual.setCorreo(datos.getCorreo());
    actual.setCurso(datos.getCurso());
    actual.setEstadoCivil(datos.getEstadoCivil());
    actual.setTiempo(datos.getTiempo());
    actual.setNroHnos(datos.getNroHnos());
    actual.setTipoVivienda(datos.getTipoVivienda());
    actual.setHuellaHash(datos.getHuellaHash());



    return repository.save(actual);
}

    @Override
    public List<Estudiante> buscarPorDocumento(String documento) {
        return repository.findByNroDocContaining(documento);
    }

    @Override
    public boolean eliminar(Integer id) {
        Estudiante estudiante = obtenerPorId(id);
        if (estudiante != null) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public long contar() {
        return repository.count();
    }

    private static final String RUTA_IMAGENES = "imagenes_estudiantes/";

    @Transactional
    @Override
    public String subirImagen(Integer id, MultipartFile archivo) {
        System.out.println("iniciando carga de imagen para estudiante id " + id);

        Estudiante estudiante = obtenerPorId(id);
        if (estudiante == null)
            return null;
        if (archivo == null || archivo.isEmpty())
            return null;

        String original = archivo.getOriginalFilename();
        if (original == null || !original.contains("."))
            return null;

        String extension = original.substring(original.lastIndexOf(".")).toLowerCase();
        if (!extension.equals(".jpg") && !extension.equals(".jpeg") && !extension.equals(".png")
                && !extension.equals(".gif")) {
            return null;
        }

        if (archivo.getSize() > 5 * 1024 * 1024)
            return null;

        // ← Guardamos el archivo con nombre fijo:
        String nombreNuevo = "estudiante_" + id + extension;

        Path carpeta = Paths.get(System.getProperty("user.dir"), RUTA_IMAGENES);
        Path destino = carpeta.resolve(nombreNuevo);

        try {
            Files.createDirectories(carpeta);

            // Eliminar imagen anterior si tiene otro nombre (por seguridad)
            if (estudiante.getImagen() != null && !estudiante.getImagen().equals(nombreNuevo)) {
                Files.deleteIfExists(carpeta.resolve(estudiante.getImagen()));
            }

            archivo.transferTo(destino.toFile());

            estudiante.setImagen(nombreNuevo);
            repository.save(estudiante);
            repository.flush();
            return nombreNuevo;

        } catch (IOException e) {
            System.out.println("Error al subir imagen: " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean eliminarImagen(Integer id) {
        Estudiante estudiante = obtenerPorId(id);
        if (estudiante == null || estudiante.getImagen() == null)
            return false;

        Path ruta = Paths.get(RUTA_IMAGENES + estudiante.getImagen());
        try {
            Files.deleteIfExists(ruta);
            estudiante.setImagen(null);
            repository.save(estudiante);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public byte[] obtenerImagen(Integer id) {
        Estudiante estudiante = obtenerPorId(id);
        if (estudiante == null || estudiante.getImagen() == null)
            return null;

        Path ruta = Paths.get(System.getProperty("user.dir"), RUTA_IMAGENES, estudiante.getImagen());

        try {
            return Files.readAllBytes(ruta);
        } catch (IOException e) {
            System.out.println("error al leer la imagen " + e.getMessage());
            return null;
        }
    }

    @Override
public List<Estudiante> obtenerSinUsuario() {
    return repository.findByUsuarioIsNull();
}

}
