package recoope.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
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
    @GetMapping("/{id}")
    public ResponseEntity<RespostaApi> pegarPorId(@PathVariable Long id) {
        return _cooperativaServices.pegarPorId(id).get();
    }

    @Operation(summary = "Buscar cooperativas.")
    @GetMapping("buscar/{nomeCooperativa}")
    public ResponseEntity<RespostaApi> buscar(@PathVariable String nomeCooperativa) {
        return _cooperativaServices.buscar(nomeCooperativa).get();
    }

    @Operation(summary = "Pegar leilões pertencentes a uma cooperativa.")
    @GetMapping("{id}/leiloes/")
    public ResponseEntity<RespostaApi> leiloes(@PathVariable Long id) {
        return _cooperativaServices.leiloes(id).get();
    }

    @Operation(summary = "Pegar leilões pertencentes a uma cooperativa, e filtrar por tipo de produto.")
    @GetMapping("{id}/leiloes/{material}")
    public ResponseEntity<RespostaApi> leiloesPorMaterial(
            @PathVariable Long id,
            @PathVariable String material)
    {
        return _cooperativaServices.leiloesPorMaterial(id, material).get();
    }
}
