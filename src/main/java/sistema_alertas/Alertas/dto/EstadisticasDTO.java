package sistema_alertas.Alertas.dto;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadisticasDTO {
    private long totalAlertas;
    private long estudiantesConAlertas;
    private long alertasPendientes;
    private long alertasCompletadas;
    private long alertasCritico;
    private long alertasAlto;
    private long alertasModerado;
    private long alertasLeve;
    private long alertasSinSeguimiento;
    private double promedioPorMes;
    private double promedioPorEstudiante;
    private Map<String, Integer> consultasPorMes;



}
