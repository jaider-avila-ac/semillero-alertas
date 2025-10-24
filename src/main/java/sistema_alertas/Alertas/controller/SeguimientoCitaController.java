package sistema_alertas.Alertas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sistema_alertas.Alertas.model.SeguimientoCita;
import sistema_alertas.Alertas.service.SeguimientoCitaService;

import java.util.List;
@RestController
@RequestMapping(value = "/api/seguimientos-citas", produces = "application/json")


public class SeguimientoCitaController {

    @Autowired
    private SeguimientoCitaService seguimientoCitaService;

    @GetMapping("/cita/{citaId}")
    public ResponseEntity<List<SeguimientoCita>> obtenerPorCita(@PathVariable Integer citaId) {
        List<SeguimientoCita> relaciones = seguimientoCitaService.obtenerPorCitaId(citaId);
        return ResponseEntity.ok(relaciones);
    }

    @GetMapping("/seguimiento/{seguimientoId}")
    public ResponseEntity<List<SeguimientoCita>> obtenerPorSeguimiento(@PathVariable Integer seguimientoId) {
        List<SeguimientoCita> relaciones = seguimientoCitaService.obtenerPorSeguimientoId(seguimientoId);
        return ResponseEntity.ok(relaciones);
    }
}
