package sistema_alertas.Alertas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sistema_alertas.Alertas.model.Docente;

@Repository
public interface DocenteRepository extends JpaRepository<Docente, Integer> {

    List<Docente> findByNombresContainingIgnoreCase(String nombre);

    List<Docente> findByNroDocContaining(String nroDoc);
    Optional<Docente> findByNroDoc(String nroDoc);
}