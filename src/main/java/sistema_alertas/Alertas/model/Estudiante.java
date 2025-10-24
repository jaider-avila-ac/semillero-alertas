package sistema_alertas.Alertas.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "estudiantes")
@Getter
@Setter
public class Estudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ESTU_ID")
    private Integer id;

    @Column(name = "ESTU_TIPO_DOC")
    private String tipoDoc;

    @Column(name = "ESTU_NRO_DOC")
    private String nroDoc;

    @Column(name = "ESTU_NOMBRES")
    private String nombres;

    @Column(name = "ESTU_APELLIDOS")
    private String apellidos;

    @Column(name = "ESTU_GENERO")
    private String genero;

    @Column(name = "ESTU_FECHA_NAC")
    private String fechaNac;

    @Column(name = "ESTU_DIREC")
    private String direccion;

    @Column(name = "ESTU_BARRIO")
    private String barrio;

    @Column(name = "ESTU_ESTRATO")
    private String estrato;

    @Column(name = "ESTU_SISBEN")
    private String sisben;

    @Column(name = "ESTU_EPS")
    private String eps;

    @Column(name = "ESTU_RH")
    private String rh;

    @Column(name = "ESTU_ACUDIENTE")
    private String acudiente;

    @Column(name = "ESTU_TEL")
    private String tel;

    @Column(name = "ESTU_SMS")
    private String sms;

    @Column(name = "ESTU_CORREO")
    private String correo;

    @Column(name = "ESTU_CURSO")
    private String curso;

    @Column(name = "ESTU_ECIVIL")
    private String estadoCivil;

    @Column(name = "ESTU_TIEMPO")
    private String tiempo;

    @Column(name = "ESTU_NRO_HNOS")
    private String nroHnos;

    @Column(name = "ESTU_TIPO_VIV")
    private String tipoVivienda;

    @Column(name = "ESTU_IMAGEN")
    private String imagen;

    @Column(name = "ESTU_HUELLA_HASH", unique = true)
    private String huellaHash;

    @JsonIgnore
    @OneToMany(mappedBy = "estudiante")
    private List<Consulta> consultas;

    @ManyToOne
    @JoinColumn(name = "USUA_ID")
    private Usuario usuario;

    public Estudiante() {
    }

    public Estudiante(
            String tipoDoc, String nroDoc, String nombres, String apellidos, String genero,
            String fechaNac, String direccion, String barrio, String estrato, String sisben,
            String eps, String rh, String acudiente, String tel, String sms, String correo,
            String curso, String estadoCivil, String tiempo, String nroHnos, String tipoVivienda,
            String imagen, String huellaHash) {
        this.tipoDoc = tipoDoc;
        this.nroDoc = nroDoc;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.genero = genero;
        this.fechaNac = fechaNac;
        this.direccion = direccion;
        this.barrio = barrio;
        this.estrato = estrato;
        this.sisben = sisben;
        this.eps = eps;
        this.rh = rh;
        this.acudiente = acudiente;
        this.tel = tel;
        this.sms = sms;
        this.correo = correo;
        this.curso = curso;
        this.estadoCivil = estadoCivil;
        this.tiempo = tiempo;
        this.nroHnos = nroHnos;
        this.tipoVivienda = tipoVivienda;
        this.imagen = imagen;
        this.huellaHash = huellaHash;
    }
}
