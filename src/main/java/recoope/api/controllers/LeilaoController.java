package recoope.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import recoope.api.domain.RespostaApi;
import recoope.api.services.LeilaoServices;

@Tag(name = "Leilao")
@RestController
@RequestMapping("/leilao")
public class LeilaoController {
    private LeilaoServices leilaoServices;

    public LeilaoController(LeilaoServices leilaoServices) {
        this.leilaoServices = leilaoServices;
    }

    @Operation(summary = "Pegar leilão pelo ID.")
    @GetMapping("/{id}")
    public ResponseEntity<RespostaApi> pegarPorId(@PathVariable Long id) {
        return leilaoServices.pegarPorId(id).get();
    }

    @Operation(summary = "Pegar todos os leilões.")
    @GetMapping("/")
    public ResponseEntity<RespostaApi> todos() {
        return leilaoServices.todos().get();
    }

    @Operation(summary = "Pegar todos os leilões, filtrando por tipo de produto.")
    @GetMapping("material/{material}")
    public ResponseEntity<RespostaApi> pegarPorMaterial(@PathVariable String material) {
        return leilaoServices.pegarPorMaterial(material).get();
    }
}
