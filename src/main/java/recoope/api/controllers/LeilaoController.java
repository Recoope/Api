package recoope.api.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import recoope.api.domain.ApiResponse;
import recoope.api.domain.entities.Leilao;
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
    public ApiResponse<Leilao> pegarPorId(Long id) {
        return leilaoServices.pegarPorId(id);
    }

    @GetMapping("/")
    public ApiResponse<List<Leilao>> todos() {
        return leilaoServices.todos();
    }

    @GetMapping("material/{material}")
    public ApiResponse<List<Leilao>> pegarPorMaterial(String material) {
        return leilaoServices.pegarPorMaterial(material);
    }


}
