package sistema_alertas.Alertas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sistema_alertas.Alertas.model.Antecedente;
import sistema_alertas.Alertas.service.AntecedenteService;

@RestController
@RequestMapping(value = "/api/antecedentes", produces = "application/json")

public class AntecedenteController {

    @Autowired
    private AntecedenteService service;

    @GetMapping("/estudiante/{id}")
    public ResponseEntity<List<Antecedente>> obtenerPorEstudiante(@PathVariable Integer id) {
        List<Antecedente> antecedentes = service.obtenerTodosPorEstudiante(id);
        if (antecedentes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(antecedentes);
    }

    @PostMapping
    public ResponseEntity<Antecedente> crear(@RequestBody Antecedente antecedente) {
        return ResponseEntity.ok(service.guardar(antecedente));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Antecedente> actualizar(@PathVariable Integer id, @RequestBody Antecedente datos) {
        Antecedente actualizado = service.actualizar(id, datos);
        return actualizado != null ? ResponseEntity.ok(actualizado) : ResponseEntity.notFound().build();
    }
}
