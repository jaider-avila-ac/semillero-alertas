package sistema_alertas.Alertas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;


import sistema_alertas.Alertas.model.Docente;


import sistema_alertas.Alertas.service.DocenteService;

@RestController
@RequestMapping(value = "/api/docentes", produces = "application/json")


public class DocenteController {

    @Autowired
    private DocenteService docenteService;

    @GetMapping
    public List<Docente> obtenerTodos() {
        return docenteService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Docente> obtenerPorId(@PathVariable Integer id) {
        Docente docente = docenteService.obtenerPorId(id);
        if (docente != null) {
            return ResponseEntity.ok(docente);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/buscar")
    public List<Docente> buscarPorNombreODocumento(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String documento) {
        if (nombre != null)
            return docenteService.buscarPorNombre(nombre);
        if (documento != null)
            return docenteService.buscarPorDocumento(documento);
        return docenteService.obtenerTodos();
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Docente datos) {
        try {
            datos.setUsuario(null); // ⚠️ Usuario no se asigna automáticamente

            Docente guardado = docenteService.guardar(datos);
            return ResponseEntity.ok(guardado);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error al guardar docente: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Docente> actualizar(@PathVariable Integer id, @RequestBody Docente datos) {
        Docente actualizado = docenteService.actualizar(id, datos);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        if (docenteService.eliminar(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/total")
    public long totalDocentes() {
        return docenteService.contar();
    }

}
