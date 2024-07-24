package recoope.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recoope.api.domain.RespostaApi;
import recoope.api.domain.inputs.EmpresaParams;
import recoope.api.domain.inputs.LoginParams;
import recoope.api.services.EmpresaServices;

@Tag(name = "Empresa")
@RestController
@RequestMapping("/empresa")
public class EmpresaController {
    private final EmpresaServices empresaServices;

    public EmpresaController(EmpresaServices empresaServices) {
        this.empresaServices = empresaServices;
    }

    @Operation(summary = "Login da empresa.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Login feito com sucesso."),
        @ApiResponse(responseCode = "400", description = "Parametro fornecido não é CNPJ, nem E-mail."),
        @ApiResponse(responseCode = "404", description = "Empresa não encontrada com CNPJ/E-mail fornecido.")
    })
    @PostMapping("/login")
    public ResponseEntity<RespostaApi> login(LoginParams loginParams) {
        return empresaServices.login(loginParams).get();
    }

    @Operation(summary = "Pegar empresa pelo ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Empresa encontrada com sucesso."),
        @ApiResponse(responseCode = "404", description = "Empresa não encontrada"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<RespostaApi> pegarPorId(@PathVariable Long id) {
        return empresaServices.pegarPorId(id).get();
    }

    @Operation(summary = "Cadastrar empresa.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Empresa cadastrada com sucesso."),
        @ApiResponse(responseCode = "400", description = "Algum parâmetro passado é nulo, ou foi inválidado pela verificação de Nome/Telefone/CNPJ/E-mail."),
    })
    @PostMapping("/cadastrar")
    public ResponseEntity<RespostaApi> cadastrar(@RequestBody EmpresaParams empresaRegistroParams){
        return empresaServices.cadastrar(empresaRegistroParams).get();
    }

    @Operation(summary = "Alterar empresa.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Empresa alterada com sucesso."),
        @ApiResponse(responseCode = "400", description = "Algum parâmtro passado foi inválidado pela verificação de Nome/Telefone/CNPJ/E-mail."),
        @ApiResponse(responseCode = "404", description = "Empresa não encontrada."),

    })
    @PatchMapping("/alterar/{id}")
    public ResponseEntity<RespostaApi> alterar(@PathVariable Long id, @RequestBody EmpresaParams empresaRegistroParams){
        return empresaServices.alterar(id, empresaRegistroParams).get();
    }

    @Operation(summary = "Deletar empresa.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Empresa removida com sucesso."),
        @ApiResponse(responseCode = "404", description = "Empresa não existe."),
    })
    @DeleteMapping("/remover/{id}")
    public ResponseEntity<RespostaApi> remover(@PathVariable Long id) {
        return empresaServices.remover(id).get();
    }
}
