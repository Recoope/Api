package recoope.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recoope.api.domain.RespostaApi;
import recoope.api.services.ReciboServices;

@Tag(name = "Recibos")
@RestController
@RequestMapping("/recibo")
@CrossOrigin(origins = "*")
public class ReciboController {
    private final ReciboServices reciboServices;

    public ReciboController(ReciboServices reciboServices) {
        this.reciboServices = reciboServices;
    }

    @Operation(summary = "Pegar recibos pelo CNPJ.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Recibos encontrados com sucesso."),
            @ApiResponse(responseCode = "204", description = "Empresa não possui recibos.")
    })
    @GetMapping("/{cnpj}")
    public ResponseEntity<RespostaApi> pegarRecibos(@PathVariable String cnpj) {
        return reciboServices.pegarRecibos(cnpj).get();
    }
}