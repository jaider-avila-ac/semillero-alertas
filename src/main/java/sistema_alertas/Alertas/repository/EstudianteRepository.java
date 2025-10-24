package sistema_alertas.Alertas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sistema_alertas.Alertas.model.Estudiante;

public interface EstudianteRepository extends JpaRepository<Estudiante, Integer> {
    List<Estudiante> findByNombresContainingIgnoreCase(String nombres);

    List<Estudiante> findByNroDocContaining(String nroDoc);

    Optional<Estudiante> findByNroDoc(String nroDoc);

    // obtener el número de telefono (SMS)
    @Query("SELECT e.sms FROM Estudiante e WHERE e.id = :id")
    String obtenerSmsPorId(@Param("id") Integer id);

    @Query("SELECT CONCAT(e.nombres, ' ', e.apellidos) FROM Estudiante e WHERE e.id = :id")
    String obtenerNombreCompletoPorId(@Param("id") Integer id);

    List<Estudiante> findByUsuarioIsNull();

}
