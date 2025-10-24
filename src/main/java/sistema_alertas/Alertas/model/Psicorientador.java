package sistema_alertas.Alertas.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "psicorientadores")
public class Psicorientador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PSIC_ID")
    private Integer id;

    @Column(name = "PSIC_TIPO_DOC")
    private String tipoDoc;

    @Column(name = "PSIC_NRO_DOC")
    private String nroDoc;

    @Column(name = "PSIC_NOMBRES")
    private String nombres;

    @Column(name = "PSIC_APELLIDOS")
    private String apellidos;


        @Column(name = "PSIC_CORREO")
    private String correo;
    
    @ManyToOne
    @JoinColumn(name = "USUA_ID")
    private Usuario usuario;

    @JsonIgnore
    @OneToMany(mappedBy = "psicorientador")
    private List<Seguimiento> seguimientos;

    @JsonIgnore
    @OneToMany(mappedBy = "psicorientador")
    private List<Cita> citas;



    public Psicorientador() {
    }

    public Psicorientador(String tipoDoc, String nroDoc, String nombres, String apellidos, String correo) {
        this.tipoDoc = tipoDoc;
        this.nroDoc = nroDoc;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
    }
}
