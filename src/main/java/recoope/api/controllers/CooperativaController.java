package recoope.api.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recoope.api.domain.ApiResponse;
import recoope.api.domain.entities.Cooperativa;
import recoope.api.services.CooperativaServices;


@Tag(name = "Cooperativa")
@RestController
@RequestMapping("/cooperativa")
public class CooperativaController {
    private final CooperativaServices _cooperativaServices;

    public CooperativaController(CooperativaServices cooperativaServices) {
        _cooperativaServices = cooperativaServices;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> pegarPorId(@PathVariable Long id) {
        return _cooperativaServices.pegarPorId(id).get();
    }

    @GetMapping("buscar/{nomeCooperativa}")
    public ResponseEntity<ApiResponse> buscar(@PathVariable String nomeCooperativa) {
        return _cooperativaServices.buscar(nomeCooperativa).get();
    }

    @GetMapping("{id}/leiloes/")
    public ResponseEntity<ApiResponse> leiloes(@PathVariable Long id) {
        return _cooperativaServices.leiloes(id).get();
    }

    @GetMapping("{id}/leiloes/{material}")
    public ResponseEntity<ApiResponse> leiloesPorMaterial(
            @PathVariable Long id,
            @PathVariable String material)
    {
        return _cooperativaServices.leiloesPorMaterial(id, material).get();
    }
}
