package sistema_alertas.Alertas.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import sistema_alertas.Alertas.model.Consulta;

@Service
public class WebSocketService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void enviarNuevaConsulta(Consulta consulta) {
        Integer estudianteId = consulta.getEstudiante().getId();
        messagingTemplate.convertAndSend("/topic/consultas/" + estudianteId, consulta);
    }
}
