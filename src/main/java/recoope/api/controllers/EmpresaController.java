package recoope.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recoope.api.domain.RespostaApi;
import recoope.api.domain.inputs.EmpresaParams;
import recoope.api.services.EmpresaServices;

@Tag(name = "Empresa")
@RestController
@RequestMapping("/empresa")
public class EmpresaController {
    private final EmpresaServices empresaServices;

    public EmpresaController(EmpresaServices empresaServices) {
        this.empresaServices = empresaServices;
    }

    @Operation(summary = "Pegar empresa pelo ID.")
    @GetMapping("/{id}")
    public ResponseEntity<RespostaApi> pegarPorId(@PathVariable String cnpj) {
        return empresaServices.pegarPorId(cnpj).get();
    }

    @Operation(summary = "Cadastrar empresa.")
    @PostMapping("/cadastrar")
    public ResponseEntity<RespostaApi> cadastrar(@RequestBody EmpresaParams empresaRegistroParams){
        return empresaServices.cadastrar(empresaRegistroParams).get();
    }

    @Operation(summary = "Alterar empresa.")
    @PatchMapping("/alterar/{id}")
    public ResponseEntity<RespostaApi> alterar(@PathVariable String cnpj, @RequestBody EmpresaParams empresaRegistroParams){
        return empresaServices.alterar(cnpj, empresaRegistroParams).get();
    }

    @Operation(summary = "Deletar empresa.")
    @DeleteMapping("/remover/{id}")
    public ResponseEntity<RespostaApi> remover(@PathVariable String cnpj) {
        return empresaServices.remover(cnpj).get();
    }
}
