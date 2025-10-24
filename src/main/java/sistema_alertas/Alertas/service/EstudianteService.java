package sistema_alertas.Alertas.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import sistema_alertas.Alertas.model.Estudiante;

public interface EstudianteService {
    List<Estudiante> obtenerTodos();
    Estudiante obtenerPorId(Integer id);
    List<Estudiante> buscarPorNombre(String nombre);
    List<Estudiante> buscarPorDocumento(String documento);
    Estudiante guardar(Estudiante estudiante);
    Estudiante actualizar(Integer id, Estudiante estudiante);
    boolean eliminar(Integer id);
    long contar();
    public String subirImagen(Integer id, MultipartFile archivo);
    public boolean eliminarImagen(Integer id);
    public byte[] obtenerImagen(Integer id);
    List<Estudiante> obtenerSinUsuario();

}