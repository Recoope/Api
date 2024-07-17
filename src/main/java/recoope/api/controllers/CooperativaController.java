package recoope.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public ResponseEntity<List<Leilao>> leiloes(@PathVariable Long id) {
        List<Leilao> leiloes = _cooperativaServices.leiloes(id);
        return ResponseEntity.ok(leiloes);
    }
}
