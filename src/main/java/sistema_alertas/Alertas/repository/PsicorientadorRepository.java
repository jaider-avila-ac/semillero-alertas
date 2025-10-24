package sistema_alertas.Alertas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import sistema_alertas.Alertas.model.Psicorientador;

public interface PsicorientadorRepository extends JpaRepository<Psicorientador, Integer> {
    List<Psicorientador> findByNombresContainingIgnoreCase(String nombre);
    List<Psicorientador> findByNroDocContaining(String nroDoc);
    Optional<Psicorientador> findByNroDoc(String nroDoc);
}
