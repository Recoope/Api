package recoope.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import recoope.api.domain.dtos.LeilaoPorCooperativa;
import recoope.api.domain.entities.Cooperativa;
import recoope.api.domain.entities.Leilao;
import recoope.api.services.CooperativaServices;

import java.util.List;

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

    @GetMapping("/leiloes/{id}")
    public ResponseEntity<List<LeilaoPorCooperativa>> leiloes(@PathVariable Long idCooperativa) {
        List<LeilaoPorCooperativa> leiloes = _cooperativaServices.leiloes(idCooperativa);
        return ResponseEntity.ok(leiloes);
    }
}
