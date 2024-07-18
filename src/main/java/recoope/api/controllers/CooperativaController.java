package recoope.api.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recoope.api.domain.dtos.LeilaoPorCooperativa;
import recoope.api.domain.entities.Cooperativa;
import recoope.api.domain.inputs.Material;
import recoope.api.services.CooperativaServices;

import java.util.List;

@Tags(value = @Tag(name = "Cooperativa"))
@RestController
@RequestMapping("/cooperativa")
public class CooperativaController {
    private final CooperativaServices _cooperativaServices;

    public CooperativaController(CooperativaServices cooperativaServices) {
        _cooperativaServices = cooperativaServices;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cooperativa> pegarPorId(@PathVariable Long id) {
        Cooperativa result = _cooperativaServices.pegarPorId(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("buscar/{nomeCooperativa}")
    public ResponseEntity<List<Cooperativa>> buscar(@PathVariable String nomeCooperativa) {
        List<Cooperativa> result = _cooperativaServices.buscar(nomeCooperativa);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/leiloes/{idCooperativa}")
    public ResponseEntity<List<LeilaoPorCooperativa>> leiloes(@PathVariable Long idCooperativa) {
        List<LeilaoPorCooperativa> leiloes = _cooperativaServices.leiloes(idCooperativa);
        return ResponseEntity.ok(leiloes);
    }

    @PostMapping("/leilaoPorMaterial/{idCooperativa}")
    public ResponseEntity<List<LeilaoPorCooperativa>> leiloesPorMaterial(
            @PathVariable Long idCooperativa,
            @RequestBody Material material)
    {
        List<LeilaoPorCooperativa> leiloes = _cooperativaServices.leiloesPorMaterial(idCooperativa, material.get());
        return ResponseEntity.ok(leiloes);
    }
}
