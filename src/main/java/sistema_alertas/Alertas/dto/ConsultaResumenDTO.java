package sistema_alertas.Alertas.dto;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsultaResumenDTO {
    private Integer id;
    private Integer estudianteId;
    private Integer docenteId; 
    private String nombreEstudiante;
    private String curso;
    private String imagen;
    private String motivo;
    private Date fecha;
    private String estado;
    private String nivel;

    public ConsultaResumenDTO() {}

    public ConsultaResumenDTO(
        Integer id,
        Integer estudianteId,
        Integer docenteId, 
        String nombreEstudiante,
        String curso,
        String imagen,
        String motivo,
        Date fecha,
        String estado,
        String nivel
    ) {
        this.id = id;
        this.estudianteId = estudianteId;
        this.docenteId = docenteId; 
        this.nombreEstudiante = nombreEstudiante;
        this.curso = curso;
        this.imagen = imagen;
        this.motivo = motivo;
        this.fecha = fecha;
        this.estado = estado;
        this.nivel = nivel;
    }
}
