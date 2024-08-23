package recoope.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recoope.api.domain.RespostaApi;
import recoope.api.domain.inputs.AlterarEmpresaParams;
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
    @GetMapping("/{cnpj}")
    public ResponseEntity<RespostaApi> pegarPorId(@PathVariable String cnpj) {
        return empresaServices.pegarPorId(cnpj).get();
    }

    @Operation(summary = "Cadastrar empresa.")
    @PostMapping("/cadastrar")
    public ResponseEntity<RespostaApi> cadastrar(@RequestBody EmpresaParams empresaRegistroParams){
        return empresaServices.cadastrar(empresaRegistroParams).get();
    }

    @Operation(summary = "Alterar empresa.")
    @PatchMapping("/alterar/{cnpj}")
    public ResponseEntity<RespostaApi> alterar(@PathVariable String cnpj, @RequestBody AlterarEmpresaParams empresaRegistroParams){
        return empresaServices.alterar(cnpj, empresaRegistroParams).get();
    }

    @Operation(summary = "Deletar empresa.")
    @DeleteMapping("/remover/{cpnj}")
    public ResponseEntity<RespostaApi> remover(@PathVariable String cnpj) {
        return empresaServices.remover(cnpj).get();
    }
}
