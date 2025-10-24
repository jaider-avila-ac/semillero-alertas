package sistema_alertas.Alertas.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import sistema_alertas.Alertas.model.Estudiante;


import sistema_alertas.Alertas.service.EstudianteService;

import org.springframework.web.multipart.MultipartFile;



@RestController
@RequestMapping(value = "/api/estudiantes", produces = "application/json")

public class EstudianteController {



    @Autowired
    private EstudianteService estudianteService;

    @GetMapping
    public List<Estudiante> obtenerTodos() {
        return estudianteService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estudiante> obtenerPorId(@PathVariable Integer id) {
        Estudiante estudiante = estudianteService.obtenerPorId(id);
        if (estudiante == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(estudiante);
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Estudiante>> buscarEstudiante(@RequestParam String valor) {
        valor = valor.trim();
        List<Estudiante> resultado = estudianteService.buscarPorNombre(valor);
        return ResponseEntity.ok(resultado);
    }

   @PostMapping
public ResponseEntity<?> guardar(@RequestBody Estudiante datos) {
    try {
        datos.setUsuario(null);
        Estudiante guardado = estudianteService.guardar(datos);
        return ResponseEntity.ok(guardado);
    } catch (Exception e) {
        return ResponseEntity.status(500).body("Error al guardar estudiante: " + e.getMessage());
    }
}


    @PutMapping("/{id}")
    public ResponseEntity<Estudiante> actualizar(@PathVariable Integer id, @RequestBody Estudiante datos) {
        Estudiante actualizado = estudianteService.actualizar(id, datos);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(actualizado);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        boolean eliminado = estudianteService.eliminar(id);
        if (eliminado == true) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/total")
    public long totalEstudiantes() {
        return estudianteService.contar();
    }

    @PostMapping("/{id}/imagen")
    public ResponseEntity<String> subirImagen(@PathVariable Integer id,
            @RequestParam("archivo") MultipartFile archivo) {
        String nombre = estudianteService.subirImagen(id, archivo);
        if (nombre != null) {
            return ResponseEntity.ok("Imagen actualizada.");
        } else {
            return ResponseEntity.badRequest().body("Error al subir imagen.");
        }
    }

    @DeleteMapping("/{id}/imagen")
    public ResponseEntity<String> eliminarImagen(@PathVariable Integer id) {
        boolean eliminado = estudianteService.eliminarImagen(id);
        if (eliminado == true) {
            return ResponseEntity.ok("Imagen eliminada.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/imagen")
    public ResponseEntity<byte[]> obtenerImagen(@PathVariable Integer id) {
        byte[] imagen = estudianteService.obtenerImagen(id);
        if (imagen == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imagen);
        }
    }

    @GetMapping("/sin-usuario")
    public ResponseEntity<List<Estudiante>> listarSinUsuario() {
        List<Estudiante> sinUsuario = estudianteService.obtenerSinUsuario();
        return ResponseEntity.ok(sinUsuario);
    }

}
