package sistema_alertas.Alertas.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cedula;

    private String nombres;

    private String correo;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private int rol;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "institucion_id", referencedColumnName = "id")
    private Institucion institucion;

    @Column(name = "persona_id")
    private Integer personaId;  
}
