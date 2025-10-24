package sistema_alertas.Alertas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "categorias_antecedente")
@Getter
@Setter
public class CategoriaAntecedente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CATE_ID")
    private Integer id;

    @Column(name = "CATE_NOMBRE", nullable = false)
    private String nombre;
}
