package sistema_alertas.Alertas.service.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sistema_alertas.Alertas.dto.ConsultaResumenDTO;
import sistema_alertas.Alertas.model.Consulta;
import sistema_alertas.Alertas.model.Docente;
import sistema_alertas.Alertas.model.Estudiante;
import sistema_alertas.Alertas.model.enums.ConsEstado;
import sistema_alertas.Alertas.repository.ConsultaRepository;
import sistema_alertas.Alertas.repository.EstudianteRepository;
import sistema_alertas.Alertas.service.ConsultaService;

@Service
public class ConsultaServiceImpl implements ConsultaService {

    @Autowired
    private ConsultaRepository repository;

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Override
    public List<Consulta> obtenerTodas() {
        return repository.findAll();
    }

    @Override
    public Consulta obtenerPorId(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Consulta guardar(Consulta consulta) {
        return repository.save(consulta);
    }

    @Override
    public Consulta actualizar(Integer id, Consulta datos) {
        Consulta actual = obtenerPorId(id);
        if (actual == null)
            return null;

        actual.setMotivo(datos.getMotivo());
        actual.setDescargos(datos.getDescargos());
        actual.setNivel(datos.getNivel());
        actual.setEstado(datos.getEstado());
        actual.setDocente(datos.getDocente());
        actual.setEstudiante(datos.getEstudiante());

        return repository.save(actual);
    }

    @Override
    public boolean eliminar(Integer id) {
        Consulta consulta = obtenerPorId(id);
        if (consulta != null) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Consulta> buscarPorMotivo(String motivo) {
        return repository.findByMotivoContainingIgnoreCase(motivo);
    }

    @Override
    public List<Consulta> buscarPorEstudiante(Integer estuId) {
        Estudiante estudiante = estudianteRepository.findById(estuId).orElse(null);
        if (estudiante == null)
            return List.of();
        return repository.findByEstudiante(estudiante);
    }

    @Override
    public long contar() {
        return repository.count();
    }

    @Override
    public long contarEstudiantesConConsultas() {
        return repository.findAll().stream()
                .map(c -> c.getEstudiante().getId())
                .collect(Collectors.toSet())
                .size();
    }

    @Override
    public List<ConsultaResumenDTO> obtenerConsultasSinSeguimiento() {
        List<Consulta> consultas = repository.findAll();
        List<ConsultaResumenDTO> resultado = new ArrayList<>();

        for (int i = 0; i < consultas.size(); i++) {
            Consulta c = consultas.get(i);
            ConsEstado estado = c.getEstado();

            if (estado == null || estado.equals(ConsEstado.pendiente)) {
                Estudiante estudiante = c.getEstudiante();
                Docente docente = c.getDocente();

                Integer docenteId = null;
                if (docente != null) {
                    docenteId = docente.getId();
                }

                String estadoTexto = "Sin estado";
                if (estado != null) {
                    estadoTexto = estado.name();
                }

                String nivelTexto = "Sin nivel";
                if (c.getNivel() != null) {
                    nivelTexto = c.getNivel().name();
                }

                String nombreCompleto = "";
                if (estudiante != null) {
                    nombreCompleto = estudiante.getNombres() + " " + estudiante.getApellidos();
                }

                String curso = null;
                if (estudiante != null) {
                    curso = estudiante.getCurso();
                }

                String imagen = null;
                if (estudiante != null) {
                    imagen = estudiante.getImagen();
                }

                Integer estudianteId = null;
                if (estudiante != null) {
                    estudianteId = estudiante.getId();
                }

                ConsultaResumenDTO dto = new ConsultaResumenDTO(
                        c.getId(),
                        estudianteId,
                        docenteId,
                        nombreCompleto,
                        curso,
                        imagen,
                        c.getMotivo(),
                        c.getFecha(),
                        estadoTexto,
                        nivelTexto);

                resultado.add(dto);
            }
        }

        return resultado;
    }

    public boolean actualizarEstado(Integer id, ConsEstado nuevoEstado) {
        Consulta consulta = obtenerPorId(id);
        if (consulta == null)
            return false;
        consulta.setEstado(nuevoEstado);
        repository.save(consulta);
        return true;
    }
}