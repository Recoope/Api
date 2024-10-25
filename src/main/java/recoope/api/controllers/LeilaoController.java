package recoope.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recoope.api.domain.RespostaApi;
import recoope.api.services.LeilaoService;

import java.util.Date;
import java.util.List;

@Tag(name = "Leilao")
@RestController
@RequestMapping("/leilao")
@CrossOrigin(origins = "*")
public class LeilaoController {
    private final LeilaoService leilaoServices;

    public LeilaoController(LeilaoService leilaoServices) {
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
    public ResponseEntity<RespostaApi> todos(
        @RequestParam(value = "materiais", required = false) List<String> materiais,
        @RequestParam(value = "ate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date ate,
        @RequestParam(value = "pesoMin", required = false) Double pesoMin,
        @RequestParam(value = "pesoMax", required = false) Double pesoMax) {
        return leilaoServices.todos(materiais, ate, pesoMin, pesoMax).get();
    }

    @Operation(summary = "Pegar leilões participados, podendo pegar pelo dia de sua data de vencimento.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Leilões encontrados com sucesso."),
            @ApiResponse(responseCode = "404", description = "Nenhum leilão encontrado.")
    })
    @GetMapping("/participados/{cnpj}")
    public ResponseEntity<RespostaApi> pegarLeiloesParticipados(@PathVariable String cnpj,
            @RequestParam(value = "fim", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fim)
    {
        return leilaoServices.pegarParticipados(cnpj, fim).get();
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
