package sistema_alertas.Alertas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import sistema_alertas.Alertas.model.Familiar;

public interface FamiliarRepository extends JpaRepository<Familiar, Integer> {
    List<Familiar> findByEstudiante_Id(Integer id);
}