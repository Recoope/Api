package recoope.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recoope.api.domain.RespostaApi;
import recoope.api.services.CooperativaServices;


@Tag(name = "Cooperativa")
@RestController
@RequestMapping("/cooperativa")
public class CooperativaController {
    private final CooperativaServices _cooperativaServices;

    public CooperativaController(CooperativaServices cooperativaServices) {
        _cooperativaServices = cooperativaServices;
    }

    @Operation(summary = "Pegar cooperativa pelo ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cooperativa encontrada com sucesso."),
            @ApiResponse(responseCode = "404", description = "Cooperativa não encontrada.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<RespostaApi> pegarPorId(@PathVariable String id) {
        return _cooperativaServices.pegarPorId(id).get();
    }

    @Operation(summary = "Buscar cooperativas.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cooperativas encontradas com sucesso."),
            @ApiResponse(responseCode = "404", description = "Nenhuma cooperativa encontrada.")
    })
    @GetMapping("/buscar/{nomeCooperativa}")
    public ResponseEntity<RespostaApi> buscar(@PathVariable String nomeCooperativa) {
        return _cooperativaServices.buscar(nomeCooperativa).get();
    }

    @Operation(summary = "Pegar leilões pertencentes a uma cooperativa.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Leilões de cooperativas encontradas com sucesso."),
            @ApiResponse(responseCode = "400", description = "Cooperativa não existe."),
            @ApiResponse(responseCode = "404", description = "Nenhum leilão encontrada.")
    })
    @GetMapping("/{id}/leiloes/")
    public ResponseEntity<RespostaApi> leiloes(@PathVariable String id) {
        return _cooperativaServices.leiloes(id).get();
    }

    @Operation(summary = "Pegar leilões pertencentes a uma cooperativa, e filtrar por tipo de produto.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Leilões de cooperativas encontradas com sucesso."),
            @ApiResponse(responseCode = "400", description = "Cooperativa não existe."),
            @ApiResponse(responseCode = "404", description = "Nenhum leilão encontrada.")
    })
        @GetMapping("/{id}/leiloes/{material}")
    public ResponseEntity<RespostaApi> leiloesPorMaterial(
            @PathVariable String id,
            @PathVariable String material) {
        return _cooperativaServices.leiloesPorMaterial(id, material).get();
    }
}
