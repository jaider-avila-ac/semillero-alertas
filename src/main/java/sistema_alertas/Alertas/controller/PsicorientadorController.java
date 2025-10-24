package sistema_alertas.Alertas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import sistema_alertas.Alertas.model.Psicorientador;
import sistema_alertas.Alertas.service.PsicorientadorService;

@RestController
@RequestMapping(value = "/api/psicorientadores", produces = "application/json")

public class PsicorientadorController {

    @Autowired
    private PsicorientadorService service;

    @GetMapping
    public List<Psicorientador> obtenerTodos() {
        return service.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Psicorientador> obtenerPorId(@PathVariable Integer id) {
        Psicorientador psic = service.obtenerPorId(id);
        if (psic != null) {
            return ResponseEntity.ok(psic);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/buscar")
    public List<Psicorientador> buscar(
        @RequestParam(required = false) String nombre,
        @RequestParam(required = false) String documento
    ) {
        if (nombre != null) {
            return service.buscarPorNombre(nombre);
        } else {
            if (documento != null) {
                return service.buscarPorDocumento(documento);
            } else {
                return service.obtenerTodos();
            }
        }
    }

    @PostMapping
    public ResponseEntity<Psicorientador> guardar(@RequestBody Psicorientador datos) {
        Psicorientador nuevo = service.guardar(datos);
        return ResponseEntity.ok(nuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Psicorientador> actualizar(@PathVariable Integer id, @RequestBody Psicorientador datos) {
        Psicorientador actualizado = service.actualizar(id, datos);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        boolean eliminado = service.eliminar(id);
        if (eliminado == true) {
            return ResponseEntity.ok("Psicorientador eliminado.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/total")
    public long total() {
        return service.contar();
    }


    
}
