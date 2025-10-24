package sistema_alertas.Alertas.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import sistema_alertas.Alertas.dto.TextoPromt;
import sistema_alertas.Alertas.service.IAService;


@RestController
@RequestMapping("/api/ia")
public class IAController {

   

    @Autowired
    private IAService iaService;

    @PostMapping("/corregir")
    public ResponseEntity<String> corregir(@RequestBody TextoPromt entrada) {
        return ResponseEntity.ok(iaService.corregirTexto(entrada.getTexto()));
    }

    

}