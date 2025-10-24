package sistema_alertas.Alertas.service.serviceImpl;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sistema_alertas.Alertas.dto.EstadisticasDTO;
import sistema_alertas.Alertas.model.Consulta;
import sistema_alertas.Alertas.model.Seguimiento;
import sistema_alertas.Alertas.model.enums.ConsEstado;
import sistema_alertas.Alertas.repository.ConsultaRepository;
import sistema_alertas.Alertas.repository.SeguimientoRepository;
import sistema_alertas.Alertas.service.EstadisticaService;

@Service
public class EstadisticaServiceImpl implements EstadisticaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private SeguimientoRepository seguimientoRepository;

    private int contarPendientes(List<Consulta> consultas) {
        int contador = 0;
        for (int i = 0; i < consultas.size(); i++) {
            Consulta c = consultas.get(i);
            if (c.getEstado() != null && c.getEstado() == ConsEstado.pendiente) {
                contador++;
            }
        }
        return contador;
    }

    private int contarCompletadas(List<Consulta> consultas) {
        int contador = 0;
        for (int i = 0; i < consultas.size(); i++) {
            Consulta c = consultas.get(i);
            if (c.getEstado() != null && c.getEstado() == ConsEstado.completado) {
                contador++;
            }
        }
        return contador;
    }

    private Map<String, Integer> contarPorNivel(List<Consulta> consultas) {
        Map<String, Integer> niveles = new HashMap<String, Integer>();
        niveles.put("critico", 0);
        niveles.put("alto", 0);
        niveles.put("moderado", 0);
        niveles.put("leve", 0);

        for (int i = 0; i < consultas.size(); i++) {
            Consulta c = consultas.get(i);
            if (c.getNivel() != null) {
                String nivel = c.getNivel().name();
                int actual = niveles.get(nivel);
                niveles.put(nivel, actual + 1);
            }
        }
        return niveles;
    }

    private int contarSinSeguimiento(List<Consulta> consultas, List<Seguimiento> seguimientos) {
        Set<Integer> consultasConSeguimiento = new HashSet<Integer>();

        for (int i = 0; i < seguimientos.size(); i++) {
            Seguimiento s = seguimientos.get(i);
            if (s.getConsulta() != null) {
                consultasConSeguimiento.add(s.getConsulta().getId());
            }
        }

        int contador = 0;
        for (int i = 0; i < consultas.size(); i++) {
            Consulta c = consultas.get(i);
            if (!consultasConSeguimiento.contains(c.getId())) {
                contador++;
            }
        }

        return contador;
    }

    private Map<String, Integer> contarConsultasPorMes(List<Consulta> consultas) {
        String[] meses = {
            "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
            "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
        };
        Map<String, Integer> consultasPorMes = new LinkedHashMap<String, Integer>();
        for (int i = 0; i < meses.length; i++) {
            consultasPorMes.put(meses[i], 0);
        }

        for (int i = 0; i < consultas.size(); i++) {
            Consulta c = consultas.get(i);
            if (c.getFecha() != null) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(c.getFecha());
                int mesIndex = cal.get(Calendar.MONTH);
                String nombreMes = meses[mesIndex];
                int actual = consultasPorMes.get(nombreMes);
                consultasPorMes.put(nombreMes, actual + 1);
            }
        }
        return consultasPorMes;
    }

    @Override
    public EstadisticasDTO obtenerEstadisticas() {
        List<Consulta> todasConsultas = consultaRepository.findAll();
        List<Seguimiento> todosSeguimientos = seguimientoRepository.findAll();

        EstadisticasDTO dto = new EstadisticasDTO();

        int totalAlertas = todasConsultas.size();
        int pendientes = contarPendientes(todasConsultas);
        int completadas = contarCompletadas(todasConsultas);
        Map<String, Integer> niveles = contarPorNivel(todasConsultas);
        int sinSeguimiento = contarSinSeguimiento(todasConsultas, todosSeguimientos);
        Map<String, Integer> consultasPorMes = contarConsultasPorMes(todasConsultas);

        Set<Integer> idsEstudiantes = new HashSet<Integer>();
        for (int i = 0; i < todasConsultas.size(); i++) {
            Consulta c = todasConsultas.get(i);
            if (c.getEstudiante() != null) {
                idsEstudiantes.add(c.getEstudiante().getId());
            }
        }

        dto.setTotalAlertas(totalAlertas);
        dto.setEstudiantesConAlertas(idsEstudiantes.size());
        dto.setAlertasPendientes(pendientes);
        dto.setAlertasCompletadas(completadas);
        dto.setAlertasCritico(niveles.get("critico"));
        dto.setAlertasAlto(niveles.get("alto"));
        dto.setAlertasModerado(niveles.get("moderado"));
        dto.setAlertasLeve(niveles.get("leve"));
        dto.setAlertasSinSeguimiento(sinSeguimiento);
        dto.setPromedioPorMes(totalAlertas / 12.0);
        if (idsEstudiantes.size() == 0) {
            dto.setPromedioPorEstudiante(0.0);
        } else {
            dto.setPromedioPorEstudiante(totalAlertas / (double) idsEstudiantes.size());
        }
        dto.setConsultasPorMes(consultasPorMes);

        return dto;
    }
}
