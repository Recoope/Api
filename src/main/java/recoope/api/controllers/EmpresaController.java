package recoope.api.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import recoope.api.domain.ApiResponse;
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

    @PostMapping("/cadastrar")
    public ResponseEntity<ApiResponse> cadastrar(@RequestBody EmpresaRegistroParams empresaRegistroParams){
        return empresaServices.cadastrar(empresaRegistroParams).get();
    }
}
