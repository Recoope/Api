package recoope.api.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import recoope.api.domain.ApiResponse;
import recoope.api.domain.dtos.LeilaoPorCooperativa;
import recoope.api.domain.entities.Cooperativa;
import recoope.api.services.CooperativaServices;

import java.util.List;

@Tag(name = "Cooperativa")
@RestController
@RequestMapping("/cooperativa")
public class CooperativaController {
    private final CooperativaServices _cooperativaServices;

    public CooperativaController(CooperativaServices cooperativaServices) {
        _cooperativaServices = cooperativaServices;
    }

    @GetMapping("/{id}")
    public ApiResponse<Cooperativa> pegarPorId(@PathVariable Long id) {
        return _cooperativaServices.pegarPorId(id);
    }

    @GetMapping("buscar/{nomeCooperativa}")
    public ApiResponse<List<Cooperativa>> buscar(@PathVariable String nome) {
        return _cooperativaServices.buscar(nome);
    }

    @GetMapping("{id}/leiloes/")
    public ApiResponse<List<LeilaoPorCooperativa>> leiloes(@PathVariable Long id) {
        return _cooperativaServices.leiloes(id);
    }

    @GetMapping("{id}/leiloes/{material}")
    public ApiResponse<List<LeilaoPorCooperativa>> leiloesPorMaterial(
            @PathVariable Long id,
            @PathVariable String material)
    {
        return _cooperativaServices.leiloesPorMaterial(id, material);
    }
}
