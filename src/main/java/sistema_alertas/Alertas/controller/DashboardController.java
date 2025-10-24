package sistema_alertas.Alertas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import sistema_alertas.Alertas.dto.DashboardResumenDTO;
import sistema_alertas.Alertas.model.Cita;
import sistema_alertas.Alertas.model.Consulta;
import sistema_alertas.Alertas.model.enums.CitaEstado;
import sistema_alertas.Alertas.model.enums.ConsEstado;
import sistema_alertas.Alertas.service.CitaService;
import sistema_alertas.Alertas.service.ConsultaService;
import sistema_alertas.Alertas.service.DocenteService;
import sistema_alertas.Alertas.service.EstudianteService;
import sistema_alertas.Alertas.service.PsicorientadorService;
import sistema_alertas.Alertas.service.SeguimientoService;

@RestController
@RequestMapping("/api/dashboard")

public class DashboardController {

    @Autowired private EstudianteService estudianteService;
    @Autowired private DocenteService docenteService;
    @Autowired private PsicorientadorService psicoService;
    @Autowired private CitaService citaService;
    @Autowired private ConsultaService consultaService;
    @Autowired private SeguimientoService seguimientoService;

    @GetMapping("/resumen/{rol}/{id}")
    public ResponseEntity<DashboardResumenDTO> obtenerResumen(
            @PathVariable int rol,
            @PathVariable int id
    ) {
        DashboardResumenDTO dto = new DashboardResumenDTO();

        // Datos globales (siempre los enviamos)
        dto.setTotalEstudiantes((int) estudianteService.contar());
        dto.setTotalConsultas((int) consultaService.contar());
        dto.setTotalCitas(citaService.obtenerTodas().size());
        dto.setTotalDocentes((int) docenteService.contar());
        dto.setTotalPsicos((int) psicoService.contar());
        dto.setTotalSeguimientos(seguimientoService.obtenerEstudiantesConSeguimientos().size());


        switch (rol) {
            case 0: // docente
                List<Consulta> consultasDocente = consultaService.obtenerTodas().stream()
                        .filter(c -> c.getDocente() != null && c.getDocente().getId().equals(id))
                        .toList();
                dto.setAlertasDelDocente(consultasDocente.size());
                break;

            case 1: // estudiante
                List<Consulta> consultasEst = consultaService.buscarPorEstudiante(id);
                dto.setAlertasDelEstudiante(consultasEst.size());
                dto.setConsultasCompletadasEstudiante((int) consultasEst.stream()
                        .filter(c -> c.getEstado() != null && c.getEstado() == ConsEstado.completado)
                        .count());

                List<Cita> citasEst = citaService.buscarPorEstudiante(id);
                dto.setCitasPendientesEstudiante((int) citasEst.stream()
                        .filter(c -> c.getEstado() != null && c.getEstado() == CitaEstado.pendiente)
                        .count());
                break;

            case 2: // psicorientador
                List<Consulta> todas = consultaService.obtenerTodas();
                dto.setAlertasPendientesPsico((int) todas.stream()
                        .filter(c -> c.getEstado() != null && c.getEstado() == ConsEstado.pendiente)
                        .count());
                dto.setAlertasCompletadasPsico((int) todas.stream()
                        .filter(c -> c.getEstado() != null && c.getEstado() == ConsEstado.completado)
                        .count());

                List<Cita> citasPsico = citaService.buscarPorPsicorientador(id);
                dto.setCitasAsignadasPsico(citasPsico.size());
                break;

            case 3: // administrador
                // puse los datos arriba creo que como pues yatine todo 
                break;

            default:
                return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(dto);
    }
}
