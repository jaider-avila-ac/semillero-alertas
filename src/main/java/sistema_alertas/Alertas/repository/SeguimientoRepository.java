package sistema_alertas.Alertas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sistema_alertas.Alertas.model.Estudiante;
import sistema_alertas.Alertas.model.Seguimiento;

public interface SeguimientoRepository extends JpaRepository<Seguimiento, Integer> {

    // Retorna el único seguimiento para una consulta (1 a 1)
    Seguimiento findByConsultaId(Integer consultaId);

    // Cuenta seguimientos por consulta (normalmente 0 o 1)
    long countByConsultaId(Integer consultaId);

    @Query("SELECT DISTINCT s.consulta.estudiante FROM Seguimiento s")
    List<Estudiante> obtenerEstudiantesConSeguimientos();

    
List<Seguimiento> findByConsultaEstudianteId(Integer estudianteId);

    
}
