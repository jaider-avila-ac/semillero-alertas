package sistema_alertas.Alertas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sistema_alertas.Alertas.model.ObservacionSeguimiento;

public interface ObservacionSeguimientoRepository extends JpaRepository<ObservacionSeguimiento, Integer> {

    List<ObservacionSeguimiento> findBySeguimientoIdOrderByFechaAsc(Integer seguimientoId);

  
List<ObservacionSeguimiento> findBySeguimientoConsultaEstudianteIdOrderByFechaAsc(Integer estudianteId);




}