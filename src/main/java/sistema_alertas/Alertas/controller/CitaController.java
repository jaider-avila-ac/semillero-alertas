package sistema_alertas.Alertas.controller;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import sistema_alertas.Alertas.model.Cita;
import sistema_alertas.Alertas.model.Consulta;
import sistema_alertas.Alertas.model.ObservacionSeguimiento;
import sistema_alertas.Alertas.model.Psicorientador;
import sistema_alertas.Alertas.model.Seguimiento;
import sistema_alertas.Alertas.model.SeguimientoCita;
import sistema_alertas.Alertas.model.enums.CitaEstado;
import sistema_alertas.Alertas.model.enums.ConsEstado;
import sistema_alertas.Alertas.service.CitaService;
import sistema_alertas.Alertas.service.ConsultaService;
import sistema_alertas.Alertas.service.ObservacionSeguimientoService;
import sistema_alertas.Alertas.service.SeguimientoCitaService;
import sistema_alertas.Alertas.service.SeguimientoService;

@RestController
@RequestMapping(value = "/api/citas", produces = "application/json")

public class CitaController {

    @Autowired
    private SimpMessagingTemplate webSocket;

    @Autowired
    private ConsultaService consultaService;

    @Autowired
    private SeguimientoService seguimientoService;

    @Autowired
    private SeguimientoCitaService seguimientoCitaService;

    @Autowired
    private CitaService citaService;

    @Autowired
    private ObservacionSeguimientoService observacionSeguimientoService;

    @GetMapping
    public List<Cita> obtenerTodas() {
        return citaService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cita> obtenerPorId(@PathVariable Integer id) {
        Cita cita = citaService.obtenerPorId(id);
        return cita != null ? ResponseEntity.ok(cita) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Cita> guardar(@RequestBody Cita cita) {
        return ResponseEntity.ok(citaService.guardar(cita));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cita> actualizar(@PathVariable Integer id, @RequestBody Cita datos) {
        Cita actualizada = citaService.actualizar(id, datos);
        return actualizada != null ? ResponseEntity.ok(actualizada) : ResponseEntity.notFound().build();
    }

    @GetMapping("/buscar")
    public List<Cita> buscarPorCriterio(
            @RequestParam(required = false) Integer estudiante,
            @RequestParam(required = false) Integer psicorientador,
            @RequestParam(required = false) Date fecha) {

        if (estudiante != null)
            return citaService.buscarPorEstudiante(estudiante);
        if (psicorientador != null)
            return citaService.buscarPorPsicorientador(psicorientador);
        if (fecha != null)
            return citaService.buscarPorFecha(fecha);

        return citaService.obtenerTodas();
    }

    @GetMapping("/total")
    public long totalCitas() {
        return citaService.obtenerTodas().size();
    }

  @PostMapping("/agendar")
public ResponseEntity<?> agendarCitaParaConsultasPendientes(
        @RequestParam Integer consultaId,
        @RequestParam Integer psicorientadorId,
        @RequestParam Date fecha) {

    Consulta consultaOrigen = consultaService.obtenerPorId(consultaId);
    if (consultaOrigen == null) {
        return ResponseEntity.badRequest().body("Consulta no encontrada");
    }

    Integer estudianteId = consultaOrigen.getEstudiante().getId();

    // Verificar si ya existe una cita pendiente
    List<Cita> citasActivas = citaService.buscarPorEstudiante(estudianteId).stream()
            .filter(c -> c.getEstado() == CitaEstado.pendiente)
            .toList();

    if (!citasActivas.isEmpty()) {
        return ResponseEntity.badRequest().body("Este estudiante ya tiene una cita pendiente");
    }

    // Buscar todas las consultas pendientes, en progreso o sin estado
    List<Consulta> consultasRelacionadas = consultaService.buscarPorEstudiante(estudianteId).stream()
            .filter(c -> c.getEstado() == null 
                      || c.getEstado() == ConsEstado.pendiente 
                      || c.getEstado() == ConsEstado.en_progreso)
            .toList();

    if (consultasRelacionadas.isEmpty()) {
        return ResponseEntity.badRequest().body("No hay consultas activas (pendientes o en progreso) para este estudiante");
    }

    // Crear la cita
    Cita cita = new Cita();
    cita.setFecha(fecha);
    cita.setEstudiante(consultaOrigen.getEstudiante());

    Psicorientador psic = new Psicorientador();
    psic.setId(psicorientadorId);
    cita.setPsicorientador(psic);

    Cita citaGuardada = citaService.guardar(cita);
    webSocket.convertAndSend("/tema/citas", citaGuardada);

    for (Consulta c : consultasRelacionadas) {
        Optional<Seguimiento> existente = seguimientoService.obtenerPorConsulta(c.getId());

        if (existente.isEmpty()) {
            Seguimiento s = new Seguimiento();
            s.setConsulta(c);
            s.setFechaInicio(fecha);
            s.setPsicorientador(psic);
            Seguimiento seguimientoGuardado = seguimientoService.guardar(s);

            // Relación seguimiento - cita
            seguimientoCitaService.guardarRelacion(citaGuardada.getId(), seguimientoGuardado.getId());

            // Observación automática
            ObservacionSeguimiento obs = new ObservacionSeguimiento();
            obs.setSeguimiento(seguimientoGuardado);
            obs.setFecha(fecha);
            obs.setTexto("Seguimiento iniciado automáticamente al agendar cita");
            observacionSeguimientoService.guardar(obs);
        } else {
            seguimientoCitaService.guardarRelacion(citaGuardada.getId(), existente.get().getId());
        }

        // Siempre actualizar el estado de la consulta a en_cita
        c.setEstado(ConsEstado.en_cita);
        consultaService.guardar(c);
    }

    return ResponseEntity.ok("Cita creada y asociada a las consultas pendientes o en progreso");
}


    @GetMapping("/estudiante/{id}")
    public ResponseEntity<List<Cita>> obtenerCitaPorEstudiante(@PathVariable Integer id) {
        List<Cita> citas = citaService.buscarPorEstudiante(id);
        return ResponseEntity.ok(citas);
    }

    @PutMapping("/cancelar/{id}")
    public ResponseEntity<?> cancelarCita(@PathVariable Integer id) {
        // 1. Buscar la cita
        Cita cita = citaService.obtenerPorId(id);
        if (cita == null) {
            return ResponseEntity.notFound().build();
        }

        // 2. Verificar si ya está cancelada
        if (cita.getEstado() == CitaEstado.cancelada) {
            return ResponseEntity.badRequest().body("La cita ya está cancelada.");
        }

        // 3. Cambiar estado de la cita a cancelada
        cita.setEstado(CitaEstado.cancelada);
        citaService.guardar(cita);

        // 4. Obtener relaciones seguimiento - cita
        List<SeguimientoCita> relaciones = seguimientoCitaService.obtenerPorCitaId(id);

        for (SeguimientoCita relacion : relaciones) {
            Integer seguimientoId = relacion.getSeguimientoId();

            seguimientoService.obtenerPorId(seguimientoId).ifPresent(seguimiento -> {
                Consulta consulta = seguimiento.getConsulta();
                if (consulta != null && consulta.getEstado() == ConsEstado.en_cita) {

                    // 5. Cambiar estado de la consulta a pendiente
                    consulta.setEstado(ConsEstado.pendiente);
                    consultaService.guardar(consulta);

                    // 6. Crear observación automática
                    ObservacionSeguimiento obs = new ObservacionSeguimiento();
                    obs.setSeguimiento(seguimiento);
                    obs.setFecha(new java.sql.Date(System.currentTimeMillis()));
                    obs.setTexto("La cita fue cancelada. Seguimiento pendiente de reprogramación.");
                    observacionSeguimientoService.guardar(obs);
                }
            });
        }

        return ResponseEntity.ok("Cita cancelada y consultas actualizadas correctamente.");
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<?> actualizarEstadoCita(@PathVariable Integer id, @RequestBody String nuevoEstado) {
        try {
            Cita cita = citaService.obtenerPorId(id);
            if (cita == null) {
                return ResponseEntity.notFound().build();
            }

            cita.setEstado(CitaEstado.valueOf(nuevoEstado.replace("\"", "")));
            citaService.guardar(cita);

            return ResponseEntity.ok("Estado actualizado");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Estado inválido: " + nuevoEstado);
        }
    }

}
