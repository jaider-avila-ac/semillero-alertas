package sistema_alertas.Alertas.service;



public interface IAService {
    String corregirTexto(String texto);
    String sugerirObservacion(String idea);
    String detectarNivel(String motivo);
    String observacionGeneral(java.util.List<String> observaciones);
   

}