package recoope.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recoope.api.domain.RespostaApi;
import recoope.api.domain.inputs.AlterarEmpresaParams;
import recoope.api.domain.inputs.EmpresaParams;
import recoope.api.services.EmpresaService;

@Tag(name = "Empresa")
@RestController
@RequestMapping("/empresa")
public class EmpresaController {
    private final EmpresaService empresaServices;

    public EmpresaController(EmpresaService empresaServices) {
        this.empresaServices = empresaServices;
    }

    @Operation(summary = "Pegar empresa pelo ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Empresa cadastrada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos ou já existentes.")
    })
    @GetMapping("/{cnpj}")
    public ResponseEntity<RespostaApi> pegarPorId(@PathVariable String cnpj) {
        return empresaServices.pegarPorId(cnpj).get();
    }

    @Operation(summary = "Cadastrar empresa.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Empresa cadastrada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos ou já existentes.")
    })
    @PostMapping("/cadastrar/")
    public ResponseEntity<RespostaApi> cadastrar(@RequestBody EmpresaParams empresaRegistroParams){
        return empresaServices.cadastrar(empresaRegistroParams).get();
    }

    @Operation(summary = "Alterar empresa.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Empresa alterada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos ou já existentes."),
            @ApiResponse(responseCode = "404", description = "Empresa não encontrada.")
    })
    @PatchMapping("/alterar/{cnpj}")
    public ResponseEntity<RespostaApi> alterar(@PathVariable String cnpj, @RequestBody AlterarEmpresaParams empresaRegistroParams){
        return empresaServices.alterar(cnpj, empresaRegistroParams).get();
    }

    @Operation(summary = "Deletar empresa.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Empresa deletada com sucesso."),
            @ApiResponse(responseCode = "404", description = "Empresa não encontrada.")
    })
    @DeleteMapping("/remover/{cpnj}")
    public ResponseEntity<RespostaApi> remover(@PathVariable String cnpj) {
        return empresaServices.remover(cnpj).get();
    }

    @Operation(summary = "Alterar empresa.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Empresa alterada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos ou já existentes."),
            @ApiResponse(responseCode = "404", description = "Empresa não encontrada.")
    })
    @PatchMapping("/alterar/{cnpj}")
    public ResponseEntity<RespostaApi> alterarSenha(@PathVariable String cnpj, @RequestBody String novaSenha){
        return empresaServices.alterarSenha(cnpj, novaSenha).get();
    }
}
