package sistema_alertas.Alertas.model;



import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "seguimientos_citas")
public class SeguimientoCita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEGCI_ID")
    private Integer id;

    @Column(name = "CITA_ID")
    private Integer citaId;

    @Column(name = "SEGU_ID")
    private Integer seguimientoId;

    public SeguimientoCita() {}

    public SeguimientoCita(Integer citaId, Integer seguimientoId) {
        this.citaId = citaId;
        this.seguimientoId = seguimientoId;
    }
}