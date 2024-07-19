package recoope.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import recoope.api.domain.RespostaApi;
import recoope.api.domain.inputs.EmpresaRegistroParams;
import recoope.api.services.EmpresaServices;

@Tag(name = "Empresa")
@RestController
@RequestMapping()
public class EmpresaController {
    private final EmpresaServices empresaServices;

    public EmpresaController(EmpresaServices empresaServices) {
        this.empresaServices = empresaServices;
    }

    @Operation(summary = "Cadastrar cooperativa")
    @PostMapping("/cadastrar")
    public ResponseEntity<RespostaApi> cadastrar(@RequestBody EmpresaRegistroParams empresaRegistroParams){
        return empresaServices.cadastrar(empresaRegistroParams).get();
    }
}
