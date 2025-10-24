package sistema_alertas.Alertas.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import sistema_alertas.Alertas.model.Cita;

public interface CitaRepository extends JpaRepository<Cita, Integer> {

    List<Cita> findByEstudianteId(Integer estuId);

    List<Cita> findByPsicorientadorId(Integer psicId);

    List<Cita> findByFecha(Date fecha);
    
}