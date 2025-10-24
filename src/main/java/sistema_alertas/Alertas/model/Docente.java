package sistema_alertas.Alertas.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "docentes")
public class Docente {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "DOCE_ID")
  private Integer id;

  @Column(name = "DOCE_TIPO_DOC")
  private String tipoDoc;

  @Column(name = "DOCE_NRO_DOC")
  private String nroDoc;

  @Column(name = "DOCE_NOMBRES")
  private String nombres;

  @Column(name = "DOCE_APELLIDOS")
  private String apellidos;

  @Column(name = "DOCE_CORREO")
  private String correo;

  @ManyToOne
  @JoinColumn(name = "USUA_ID")
  private Usuario usuario;

  @JsonIgnore
  @OneToMany(mappedBy = "docente")
  private List<Consulta> consultas;

  public Docente() {
  }

  public Docente(String tipoDoc, String nroDoc, String nombres, String apellidos, String correo) {
    this.tipoDoc = tipoDoc;
    this.nroDoc = nroDoc;
    this.nombres = nombres;
    this.apellidos = apellidos;
    this.correo = correo;
  }
}
