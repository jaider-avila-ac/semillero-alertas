package sistema_alertas.Alertas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sistema_alertas.Alertas.model.Antecedente;

import java.util.List;

public interface AntecedenteRepository extends JpaRepository<Antecedente, Integer> {
   List<Antecedente> findByEstudianteIdOrderByCategoriaIdAscFechaRegistroAsc(Integer estudianteId);


}
