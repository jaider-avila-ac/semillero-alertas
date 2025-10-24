package sistema_alertas.Alertas.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "seguimientos")
@Getter
@Setter
public class Seguimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEGU_ID")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "CONS_ID", referencedColumnName = "CONS_ID")
    private Consulta consulta;

    @ManyToOne
    @JoinColumn(name = "PSIC_ID")
    private Psicorientador psicorientador;

    @Column(name = "SEGU_FECHA_INICIO")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;

    @Column(name = "SEGU_FECHA_FIN")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;

    @Column(name = "resumen_general", columnDefinition = "text")
    private String resumenGeneral;

    @Column(name = "segu_total_obs")
    private Integer totalObservaciones;

    public Seguimiento() {}

    public Seguimiento(Consulta consulta, Psicorientador psicorientador, Date fechaInicio, Date fechaFin) {
        this.consulta = consulta;
        this.psicorientador = psicorientador;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }
}
