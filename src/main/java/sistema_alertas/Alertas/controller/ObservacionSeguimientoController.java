package sistema_alertas.Alertas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import sistema_alertas.Alertas.model.ObservacionSeguimiento;
import sistema_alertas.Alertas.service.ObservacionSeguimientoService;

@RestController
@RequestMapping(value = "/api/observaciones", produces = "application/json")

public class ObservacionSeguimientoController {

    @Autowired
    private ObservacionSeguimientoService service;

    @GetMapping("/seguimiento/{seguimientoId}")
    public List<ObservacionSeguimiento> obtenerPorSeguimiento(@PathVariable Integer seguimientoId) {
        return service.obtenerPorSeguimiento(seguimientoId);
    }

    @PostMapping
    public ResponseEntity<ObservacionSeguimiento> guardar(@RequestBody ObservacionSeguimiento observacion) {
        return ResponseEntity.ok(service.guardar(observacion));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ObservacionSeguimiento> actualizar(@PathVariable Integer id,
            @RequestBody ObservacionSeguimiento datos) {
        ObservacionSeguimiento actualizada = service.actualizar(id, datos);
        if (actualizada == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        if (service.eliminar(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
