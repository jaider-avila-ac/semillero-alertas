package sistema_alertas.Alertas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import sistema_alertas.Alertas.model.Consulta;
import sistema_alertas.Alertas.model.Estudiante;
import sistema_alertas.Alertas.model.enums.ConsEstado;

public interface ConsultaRepository extends JpaRepository<Consulta, Integer> {


    List<Consulta> findByEstudiante(Estudiante estudiante);

 
    List<Consulta> findByMotivoContainingIgnoreCase(String motivo);


    List<Consulta> findByEstado(ConsEstado estado);


    List<Consulta> findByEstudianteAndEstado(Estudiante estudiante, ConsEstado estado);
}
