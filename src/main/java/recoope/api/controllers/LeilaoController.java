package recoope.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recoope.api.domain.RespostaApi;
import recoope.api.services.LeilaoServices;

import java.util.Date;

@Tag(name = "Leilao")
@RestController
@RequestMapping("/leilao")
public class LeilaoController {
    private final LeilaoServices leilaoServices;

    public LeilaoController(LeilaoServices leilaoServices) {
        this.leilaoServices = leilaoServices;
    }

    @Operation(summary = "Pegar leilão pelo ID.")
    @GetMapping("/{id}")
    public ResponseEntity<RespostaApi> pegarPorId(@PathVariable Long id) {
        return leilaoServices.pegarPorId(id).get();
    }

    @Operation(summary = "Pegar todos os leilões.")
    @GetMapping
    public ResponseEntity<RespostaApi> todos() {
        return leilaoServices.todos().get();
    }

    @Operation(summary = "Pegar todos os leilões, filtrando por tipo de produto.")
    @GetMapping("/material/{material}")
    public ResponseEntity<RespostaApi> pegarPorMaterial(@PathVariable String material) {
        return leilaoServices.pegarPorMaterial(material).get();
    }

    @Operation(summary = "Pegar leilões pela sua data de fim.")
    @GetMapping("/fim")
    public ResponseEntity<RespostaApi> pegarPorDataDeFim(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date data) {
        return leilaoServices.pegarPorDataFim(data).get();
    }
}
