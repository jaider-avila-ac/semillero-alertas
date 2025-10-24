package sistema_alertas.Alertas.model;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sistema_alertas.Alertas.config.TwilioConfig;

@Service
public class ServicioSms {

    @Autowired
    private TwilioConfig twilioConfig;

    public String enviarMensaje(String numeroDestino, String textoMensaje) {
        System.out.println("Accediendo al metodo enviarMensaje()");

        try {
            Twilio.init(twilioConfig.getSidCuenta(), twilioConfig.getTokenAutorizacion());

            if (!numeroDestino.startsWith("+")) {
                numeroDestino = "+57" + numeroDestino;
            }

            Message mensaje = Message.creator(
                    new PhoneNumber(numeroDestino),
                    new PhoneNumber(twilioConfig.getNumeroTwilio()),
                    textoMensaje
            ).create();

            String sid = mensaje.getSid();
            System.out.println("Mensaje enviado correctamente. SID: " + sid);
            return "Mensaje enviado correctamente. SID: " + sid;

        } catch (Exception e) {
            System.out.println("Error al enviar el mensaje: " + e.getMessage());
            return "Error al enviar mensaje: " + e.getMessage();
        }
    }
}
