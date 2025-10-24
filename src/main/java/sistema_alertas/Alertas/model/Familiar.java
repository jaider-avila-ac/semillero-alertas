package sistema_alertas.Alertas.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "familiares")
@Getter
@Setter
public class Familiar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FAMI_ID") 
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ESTU_ID") 
    @JsonBackReference
    private Estudiante estudiante;

    @Column(name = "FAMI_NOMBRES")
    private String nombres;

    @Column(name = "FAMI_APELLIDOS")
    private String apellidos;

    @Column(name = "FAMI_PARENTESCO")
    private String parentesco;

    @Column(name = "FAMI_FECHA_NAC")
    private String fechaNacimiento;

    @Column(name = "FAMI_ESCOL")
    private String escolaridad;

    @Column(name = "FAMI_TEL")
    private String telefono;

    @Column(name = "FAMI_HORARIO")
    private String horario;
}
