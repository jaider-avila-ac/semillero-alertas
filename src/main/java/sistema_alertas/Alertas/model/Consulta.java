package sistema_alertas.Alertas.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import sistema_alertas.Alertas.model.enums.ConsEstado;
import sistema_alertas.Alertas.model.enums.MetodoValidacion;
import sistema_alertas.Alertas.model.enums.NivelAlerta;

import java.util.Date;

@Entity
@Table(name = "consultas")
@Getter
@Setter
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CONS_ID")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ESTU_ID", referencedColumnName = "ESTU_ID")
    private Estudiante estudiante;

    @ManyToOne
    @JoinColumn(name = "DOCE_ID", referencedColumnName = "DOCE_ID")
    private Docente docente;

    @Column(name = "CONS_FECHA", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    @Column(name = "CONS_MOTIVO")
    private String motivo;

    @Column(name = "CONS_DESCARGOS")
    private String descargos;

    @Enumerated(EnumType.STRING)
    @Column(name = "CONS_NIVEL_ALERTA")
    private NivelAlerta nivel;

    @Enumerated(EnumType.STRING)
    @Column(name = "CONS_ESTADO")
    private ConsEstado estado = ConsEstado.pendiente;

    @Column(name = "ESTU_PRESENCIAL")
    private Boolean presenciaEstudiante = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "ESTU_VALIDACION")
    private MetodoValidacion metodoValidacion = MetodoValidacion.NINGUNO;

    public Consulta() {}

    public Consulta(Estudiante estudiante, Docente docente, String motivo,
                    String descargos, NivelAlerta nivel, ConsEstado estado,
                    Boolean presenciaEstudiante, MetodoValidacion metodoValidacion) {
        this.estudiante = estudiante;
        this.docente = docente;
        this.motivo = motivo;
        this.descargos = descargos;
        this.nivel = nivel;
        this.estado = estado;
        this.presenciaEstudiante = presenciaEstudiante;
        this.metodoValidacion = metodoValidacion;
    }
}
