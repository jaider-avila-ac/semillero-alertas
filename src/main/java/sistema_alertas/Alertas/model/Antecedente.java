package sistema_alertas.Alertas.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
@Table(name = "antecedentes")
@Getter
@Setter
public class Antecedente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ANTE_ID")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ANTE_ESTU_ID", nullable = false)
    @JsonIgnore // esto evita que se devuelva el estudiante completo
    private Estudiante estudiante;

    @ManyToOne
    @JoinColumn(name = "CATE_ID", nullable = false)
    private CategoriaAntecedente categoria;

    @Column(name = "ANTE_DETALLE", nullable = false)
    private String detalle;

    @Column(name = "ANTE_FECHA_REGISTRO")
    private LocalDateTime fechaRegistro = LocalDateTime.now();
}