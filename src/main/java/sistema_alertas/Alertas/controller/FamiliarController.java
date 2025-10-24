package sistema_alertas.Alertas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import sistema_alertas.Alertas.model.Familiar;
import sistema_alertas.Alertas.service.FamiliarService;

@RestController
@RequestMapping(value = "/api/familiares", produces = "application/json")

public class FamiliarController {

    @Autowired
    private FamiliarService service;

    @GetMapping("/estudiante/{id}")
    public ResponseEntity<?> obtenerPorEstudiante(@PathVariable Integer id) {
        try {
            List<Familiar> lista = service.obtenerPorEstudiante(id);
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error al obtener familiares: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Familiar> crear(@RequestBody Familiar familiar) {
        Familiar guardado = service.guardar(familiar);
        return ResponseEntity.ok(guardado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Familiar> actualizar(@PathVariable Integer id, @RequestBody Familiar datos) {
        Familiar actualizado = service.actualizar(id, datos);
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
            return ResponseEntity.ok("Familiar eliminado");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
