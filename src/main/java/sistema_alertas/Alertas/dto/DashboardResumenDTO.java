package sistema_alertas.Alertas.dto;


import lombok.Data;

@Data
public class DashboardResumenDTO {
    private int totalEstudiantes;
    private int totalConsultas;
    private int totalCitas;
    private int totalDocentes;
    private int totalPsicos;
    private int totalSeguimientos;

    private int alertasDelDocente;

    private int alertasDelEstudiante;
    private int citasPendientesEstudiante;
    private int consultasCompletadasEstudiante;

    private int alertasPendientesPsico;
    private int alertasCompletadasPsico;
    private int citasAsignadasPsico;
}