package sistema_alertas.Alertas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import sistema_alertas.Alertas.model.Consulta;
import sistema_alertas.Alertas.model.Estudiante;
import sistema_alertas.Alertas.model.ObservacionSeguimiento;
import sistema_alertas.Alertas.model.Seguimiento;
import sistema_alertas.Alertas.service.IAService;
import sistema_alertas.Alertas.service.ObservacionSeguimientoService;
import sistema_alertas.Alertas.service.SeguimientoService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/seguimientos", produces = "application/json")
public class SeguimientoController {

    @Autowired
    private SeguimientoService seguimientoService;

    @Autowired
    private ObservacionSeguimientoService observacionService;

    @Autowired
    private IAService iaService;

    @GetMapping("/consulta/{consultaId}")
    public ResponseEntity<Seguimiento> obtenerPorConsulta(@PathVariable Integer consultaId) {
        Optional<Seguimiento> resultado = seguimientoService.obtenerPorConsulta(consultaId);
        return resultado.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Seguimiento> guardar(@RequestBody Seguimiento seguimiento) {
        Seguimiento nuevo = seguimientoService.guardar(seguimiento);
        return ResponseEntity.ok(nuevo);
    }

    @GetMapping("/estudiantes-con-seguimientos")
    public ResponseEntity<List<Estudiante>> estudiantesConSeguimientos() {
        return ResponseEntity.ok(seguimientoService.obtenerEstudiantesConSeguimientos());
    }

    @PutMapping("/{seguimientoId}/resumen-manual")
    public ResponseEntity<String> guardarResumenManual(
            @PathVariable Integer seguimientoId,
            @RequestBody Map<String, String> body) {

        Optional<Seguimiento> optional = seguimientoService.obtenerPorId(seguimientoId);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        String resumen = body.get("resumen");
        Seguimiento seguimiento = optional.get();
        seguimiento.setResumenGeneral(resumen);
        seguimientoService.guardar(seguimiento);

        return ResponseEntity.ok("Resumen general actualizado correctamente.");
    }

    @PutMapping("/{seguimientoId}/generar-resumen")
public ResponseEntity<String> generarResumen(@PathVariable Integer seguimientoId) {
    Optional<Seguimiento> optional = seguimientoService.obtenerPorId(seguimientoId);
    if (optional.isEmpty()) {
        return ResponseEntity.notFound().build();
    }

    Seguimiento seguimientoActual = optional.get();
    Integer estudianteId = seguimientoActual.getConsulta().getEstudiante().getId();

    // Obtener todas las observaciones válidas del estudiante
    List<String> promptsActuales = generarPromptsDelEstudiante(estudianteId);
    int totalObservacionesActuales = contarObservacionesValidas(estudianteId);

    if (promptsActuales.isEmpty() || totalObservacionesActuales == 0) {
        return ResponseEntity.ok("NO_OBSERVACIONES");
    }

    Integer totalAnterior = seguimientoActual.getTotalObservaciones();
    if (totalAnterior != null && totalObservacionesActuales <= totalAnterior) {
        return ResponseEntity.ok("YA_GENERADO_PARA_ESTAS_OBSERVACIONES");
    }

    // Generar nuevo resumen
    String nuevoResumen = iaService.observacionGeneral(promptsActuales);

    // Si el nuevo resumen es igual al anterior, evitar duplicar
    String resumenActual = seguimientoActual.getResumenGeneral();
    if (resumenEsIgual(nuevoResumen, resumenActual)) {
        return ResponseEntity.ok("RESUMEN_YA_GENERADO");
    }

    // Actualizar resumen y contador de observaciones
    seguimientoActual.setResumenGeneral(nuevoResumen);
    seguimientoActual.setTotalObservaciones(totalObservacionesActuales);
    seguimientoService.guardar(seguimientoActual);

    return ResponseEntity.ok(nuevoResumen);
}


    private List<String> generarPromptsDelEstudiante(Integer estudianteId) {
        List<Seguimiento> seguimientos = seguimientoService.obtenerPorEstudiante(estudianteId);
        List<String> prompts = new ArrayList<>();

        for (Seguimiento seguimiento : seguimientos) {
            Consulta consulta = seguimiento.getConsulta();
            String motivo = consulta.getMotivo();
            String estado = consulta.getEstado() != null ? consulta.getEstado().name() : "sin_estado";

            List<ObservacionSeguimiento> observaciones = observacionService
                    .obtenerPorSeguimiento(seguimiento.getId())
                    .stream()
                    .filter(o -> o.getTexto() != null && !o.getTexto().isBlank())
                    .filter(o -> !o.getTexto().toLowerCase().contains("automáticamente"))
                    .toList();

            if (observaciones.isEmpty())
                continue;

            String observacionesTexto = observaciones.stream()
                    .map(ObservacionSeguimiento::getTexto)
                    .distinct()
                    .collect(Collectors.joining(" | "));

            String entrada = "Motivo: " + motivo + " | Estado: " + estado + " | Obs: " + observacionesTexto;
            prompts.add(entrada);
        }

        return prompts;
    }

    private boolean resumenEsIgual(String nuevoResumen, String resumenExistente) {
        if (resumenExistente == null || resumenExistente.isBlank())
            return false;
        return normalizarTexto(nuevoResumen).equals(normalizarTexto(resumenExistente));
    }

    private String normalizarTexto(String texto) {
        return texto.trim().replaceAll("\\s+", " ").toLowerCase();
    }


    private int contarObservacionesValidas(Integer estudianteId) {
    List<Seguimiento> seguimientos = seguimientoService.obtenerPorEstudiante(estudianteId);
    int total = 0;

    for (Seguimiento seguimiento : seguimientos) {
        long count = observacionService.obtenerPorSeguimiento(seguimiento.getId())
                .stream()
                .filter(o -> o.getTexto() != null && !o.getTexto().isBlank())
                .filter(o -> !o.getTexto().toLowerCase().contains("automáticamente"))
                .count();
        total += count;
    }

    return total;
}


}
