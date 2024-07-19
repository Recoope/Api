package recoope.api.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import recoope.api.domain.ApiResponse;
import recoope.api.services.LeilaoServices;

import java.util.List;

@Tag(name = "Leilao")
@RestController
@RequestMapping("/leilao")
public class LeilaoController {
    private LeilaoServices leilaoServices;

    public LeilaoController(LeilaoServices leilaoServices) {
        this.leilaoServices = leilaoServices;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> pegarPorId(@PathVariable Long id) {
        return leilaoServices.pegarPorId(id).get();
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse> todos() {
        return leilaoServices.todos().get();
    }

    @GetMapping("material/{material}")
    public ResponseEntity<ApiResponse> pegarPorMaterial(@PathVariable String material) {
        return leilaoServices.pegarPorMaterial(material).get();
    }
}
