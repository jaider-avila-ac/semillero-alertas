package sistema_alertas.Alertas.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CambioContrasenaDTO {
    private String cedula;
    private String contrasenaActual;
    private String contrasenaNueva;

}