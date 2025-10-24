package sistema_alertas.Alertas.controller;

import java.util.List;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sistema_alertas.Alertas.model.Sugerencia;
import sistema_alertas.Alertas.service.SugerenciaService;

@RestController
@RequestMapping(value = "/api/sugerencias", produces = "application/json")


public class SugerenciaController {

    private final SugerenciaService sugerenciaService;

    public SugerenciaController(SugerenciaService sugerenciaService) {
        this.sugerenciaService = sugerenciaService;
    }

    @GetMapping
    public List<Sugerencia> listar() {
        return sugerenciaService.obtenerTodas();
    }

    @PostMapping
    public Sugerencia crear(@RequestBody Sugerencia sugerencia) {
        return sugerenciaService.crear(sugerencia);
    }

    
}