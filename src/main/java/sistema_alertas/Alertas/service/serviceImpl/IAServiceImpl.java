package sistema_alertas.Alertas.service.serviceImpl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sistema_alertas.Alertas.config.OpenAIConfig;
import sistema_alertas.Alertas.service.IAService;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
public class IAServiceImpl implements IAService {

    private final OpenAIConfig config;
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();
    private final String endpoint = "https://api.openai.com/v1/chat/completions";

    @Autowired
    public IAServiceImpl(OpenAIConfig config) {
        this.config = config;
    }

    private String enviarPrompt(String promptUsuario) {
    try {
        if (config.getApiKey() == null || config.getApiKey().isBlank()) {
            System.err.println("❌ API Key de OpenAI no configurada.");
            return "❌ No se puede conectar a OpenAI: API Key no configurada.";
        }

        // 👉 Muestra el prompt que se enviará
        System.out.println("📤 Prompt enviado a OpenAI:\n" + promptUsuario);

        ObjectNode message = mapper.createObjectNode();
        message.put("role", "user");
        message.put("content", promptUsuario);

        ArrayNode messages = mapper.createArrayNode();
        messages.add(message);

        ObjectNode root = mapper.createObjectNode();
        root.put("model", "gpt-3.5-turbo");
        root.set("messages", messages);

        String json = mapper.writeValueAsString(root);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .header("Authorization", "Bearer " + config.getApiKey())
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            System.err.println("❌ Error HTTP al consultar OpenAI. Código: " + response.statusCode());
            System.err.println("🔎 Respuesta: " + response.body());
            return "❌ Error de conexión con OpenAI: Código " + response.statusCode();
        }

        JsonNode rootNode = mapper.readTree(response.body());
        JsonNode choices = rootNode.path("choices");

        if (!choices.isArray() || choices.isEmpty() || choices.get(0) == null) {
            System.err.println("❌ La respuesta de OpenAI no contiene 'choices' válidos. Respuesta: " + response.body());
            return "⚠️ La IA no devolvió una respuesta válida.";
        }

        JsonNode contentNode = choices.get(0).path("message").path("content");
        if (contentNode.isMissingNode() || contentNode.isNull()) {
            System.err.println("❌ 'content' no encontrado en la respuesta de OpenAI.");
            return "⚠️ No se pudo obtener el contenido generado por IA.";
        }

        return contentNode.asText().trim();

    } catch (Exception e) {
        System.err.println("❌ Excepción al llamar a OpenAI: " + e.getMessage());
        return "❌ Error al consultar OpenAI: " + e.getMessage();
    }
}


    @Override
    public String corregirTexto(String texto) {
        return enviarPrompt("Corrige ortografía y redacción sin cambiar el contenido ni la intención del texto:\n" + texto);
    }

    @Override
    public String sugerirObservacion(String idea) {
        return enviarPrompt("Redacta una observación profesional breve a partir de esta idea:\n" + idea);
    }

    @Override
    public String detectarNivel(String motivo) {
        return enviarPrompt("Responde solo con leve, moderado, alto o crítico. Motivo:\n" + motivo);
    }

   @Override
public String observacionGeneral(List<String> observaciones) {
    String joined = String.join("\n", observaciones);
    return enviarPrompt(
        "Actúa como un psicólogo escolar profesional y redacta una observación resumen clara y empática a partir de las siguientes observaciones. Usa un lenguaje técnico pero comprensible, con un máximo de 90 palabras:\n" + joined
    );
}
}
