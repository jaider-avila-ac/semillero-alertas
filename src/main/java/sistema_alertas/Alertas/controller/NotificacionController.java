package sistema_alertas.Alertas.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class NotificacionController {

  @MessageMapping("/notificar")
  @SendTo("/topic/alertas")
  public String enviarNotificacion(String mensaje) {
    return mensaje;
  }
}