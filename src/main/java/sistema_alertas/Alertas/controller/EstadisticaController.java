
package sistema_alertas.Alertas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sistema_alertas.Alertas.dto.EstadisticasDTO;
import sistema_alertas.Alertas.service.EstadisticaService;

@RestController
@RequestMapping(value = "/api/estadisticas", produces = "application/json")


public class EstadisticaController {

    @Autowired
    private EstadisticaService estadisticaService;

    @GetMapping
    public ResponseEntity<EstadisticasDTO> obtenerEstadisticas() {
        EstadisticasDTO dto = estadisticaService.obtenerEstadisticas();
        return ResponseEntity.ok(dto);
    }
}