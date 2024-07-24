package recoope.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recoope.api.domain.RespostaApi;
import recoope.api.domain.inputs.LanceParams;
import recoope.api.services.LanceServices;

@Tag(name = "Lance")
@RestController
@RequestMapping("/lance")
public class LanceController {
    private LanceServices lanceServices;

    public LanceController(LanceServices lanceServices) {
        this.lanceServices = lanceServices;
    }

    @Operation(summary = "Dar lance em leilão.")
    @PostMapping("/{idLeilao}")
    public ResponseEntity<RespostaApi> darLance(@PathVariable Long idLeilao, @RequestBody LanceParams leilaoParams) {
        return lanceServices.darLance(idLeilao, leilaoParams).get();
    }

    @Operation(summary = "Cancelar lances.")
    @DeleteMapping("/cancelar")
    public ResponseEntity<RespostaApi> cancelarLance(@RequestParam Long idEmpresa, @RequestParam Long idLeilao) {
        return lanceServices.cancelarLance(idEmpresa, idLeilao).get();
    }
}
