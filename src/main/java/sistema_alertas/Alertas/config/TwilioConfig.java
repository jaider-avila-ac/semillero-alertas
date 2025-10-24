package sistema_alertas.Alertas.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwilioConfig {

    @Value("${twilio.sid}")
    private String sidCuenta;

    @Value("${twilio.token}")
    private String tokenAutorizacion;

    @Value("${twilio.numero}")
    private String numeroTwilio;

    public String getSidCuenta() {
        return sidCuenta;
    }

    public String getTokenAutorizacion() {
        return tokenAutorizacion;
    }

    public String getNumeroTwilio() {
        return numeroTwilio;
    }
}
