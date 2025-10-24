package sistema_alertas.Alertas.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "observaciones_seguimiento")
@Getter
@Setter
public class ObservacionSeguimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OBS_ID")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "SEGU_ID", referencedColumnName = "SEGU_ID")
    private Seguimiento seguimiento;

    @Column(name = "OBS_FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    @Column(name = "OBS_TEXTO", columnDefinition = "TEXT")
    private String texto;

    public ObservacionSeguimiento() {
    }

    public ObservacionSeguimiento(Seguimiento seguimiento, Date fecha, String texto) {
        this.seguimiento = seguimiento;
        this.fecha = fecha;
        this.texto = texto;
    }
}
