package sistema_alertas.Alertas.service;

import java.util.List;

import sistema_alertas.Alertas.model.Antecedente;

public interface AntecedenteService {
   List<Antecedente> obtenerTodosPorEstudiante(Integer estudianteId);

    Antecedente guardar(Antecedente antecedente);
    Antecedente actualizar(Integer estudianteId, Antecedente datos);
}
