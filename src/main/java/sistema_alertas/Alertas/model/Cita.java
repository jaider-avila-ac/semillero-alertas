package sistema_alertas.Alertas.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import sistema_alertas.Alertas.model.enums.CitaEstado;

@Entity
@Getter
@Setter
@Table(name = "citas")
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "FECHA")
    private java.sql.Date fecha;

    @ManyToOne
    @JoinColumn(name = "ESTU_ID")
    private Estudiante estudiante;

    @ManyToOne
    @JoinColumn(name = "PSIC_ID")
    private Psicorientador psicorientador;

    @Enumerated(EnumType.STRING)
    @Column(name = "CITA_ESTADO")
    private CitaEstado estado = CitaEstado.pendiente;

    public Cita() {
    }

    public Cita(java.sql.Date fecha, Estudiante estudiante, Psicorientador psicorientador) {
        this.fecha = fecha;
        this.estudiante = estudiante;
        this.psicorientador = psicorientador;
        this.estado = CitaEstado.pendiente;
    }
}
