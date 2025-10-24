package sistema_alertas.Alertas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.client.RestClientAutoConfiguration;

@SpringBootApplication(exclude = { RestClientAutoConfiguration.class })
public class AlertasSoftwareApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlertasSoftwareApplication.class, args);
	}
}
