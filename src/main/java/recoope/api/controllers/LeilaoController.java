package recoope.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@CrossOrigin(origins = "*")
public class LeilaoController {
    private final LeilaoServices leilaoServices;

    public LeilaoController(LeilaoServices leilaoServices) {
        this.leilaoServices = leilaoServices;
    }

    @Operation(summary = "Pegar leilão pelo ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Leilão encontrado com sucesso."),
        @ApiResponse(responseCode = "404", description = "Leilão não encontrado.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<RespostaApi> pegarPorId(@PathVariable Long id) {
        return leilaoServices.pegarPorId(id).get();
    }

    @Operation(summary = "Pegar todos os leilões ativados.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Leilões encontrados com sucesso."),
        @ApiResponse(responseCode = "404", description = "Nenhum leilão encontrado.")
    })
    @GetMapping
    public ResponseEntity<RespostaApi> todos() {
        return leilaoServices.todos().get();
    }

    @Operation(summary = "Pegar todos os leilões, filtrando por tipo de produto.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Leilões encontrados com sucesso."),
        @ApiResponse(responseCode = "404", description = "Nenhum leilão encontrado.")
    })
    @GetMapping("/material/{material}")
    public ResponseEntity<RespostaApi> pegarPorMaterial(@PathVariable String material) {
        return leilaoServices.pegarPorMaterial(material).get();
    }

    @Operation(summary = "Pegar leilões em que a empresa participa, podendo passar a data de fim")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Leilões encontrados com sucesso."),
            @ApiResponse(responseCode = "404", description = "Nenhum leilão encontrado.")
    })
    @GetMapping("/fim")
    public ResponseEntity<RespostaApi> pegarParticipacoes(String cnpj, @RequestParam(value = "date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date dataFim) {
        return leilaoServices.pegarPorDataFim(cnpj, dataFim).get();
    }

    @Operation(summary = "Pega todos as datas em que leilões participados vencem, em um mes especifico.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Leilões encontrados com sucesso."),
            @ApiResponse(responseCode = "404", description = "Nenhum leilão encontrado.")
    })
    @GetMapping("/vencimentos/{cnpj}")
    public ResponseEntity<RespostaApi> vencimentos(@PathVariable String cnpj) {
        return leilaoServices.vencimentos(cnpj).get();
    }
}
