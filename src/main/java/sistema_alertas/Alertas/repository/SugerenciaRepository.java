package sistema_alertas.Alertas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sistema_alertas.Alertas.model.Sugerencia;

public interface SugerenciaRepository extends JpaRepository<Sugerencia, Long> {
}