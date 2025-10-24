package sistema_alertas.Alertas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import sistema_alertas.Alertas.model.SeguimientoCita;

public interface SeguimientoCitaRepository extends JpaRepository<SeguimientoCita, Integer> {

    List<SeguimientoCita> findByCitaId(Integer citaId);

    List<SeguimientoCita> findBySeguimientoId(Integer seguimientoId);
}