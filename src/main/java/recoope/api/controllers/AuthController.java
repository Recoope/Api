package recoope.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recoope.api.domain.RespostaApi;
import recoope.api.domain.inputs.AlterarSenhaParam;
import recoope.api.domain.inputs.LoginParams;
import recoope.api.domain.inputs.RefreshTokenParam;
import recoope.api.services.AuthService;

@Tag(name = "Auth")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/login")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Login empresa/cooperativa.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Login feito com sucesso."),
            @ApiResponse(responseCode = "400", description = "Credenciais nulas foram passadas."),
            @ApiResponse(responseCode = "404", description = "Credenciais não possuem correspondecia.")
    })
    @PostMapping
    public ResponseEntity<RespostaApi> login(@RequestBody LoginParams params) {
        return authService.login(params).get();
    }

    @Operation(summary = "Gerar código para recuperar acesso, no esquecer senha.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Código gerado no redis."),
            @ApiResponse(responseCode = "404", description = "Não é possivel gerar o código porque a empresa não existe."),
            @ApiResponse(responseCode = "503", description = "Não é possivel gerar o código porque o redis está inacessível.")
    })
    @PostMapping("/recuperar/{cnpjOuEmail}")
    public ResponseEntity<RespostaApi> recuperacao(@PathVariable String cnpjOuEmail) {
        return authService.gerarRecuperacao(cnpjOuEmail).get();
    }

    @Operation(summary = "Gerar código para recuperar acesso, no esquecer senha.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Código é válido."),
            @ApiResponse(responseCode = "401", description = "Código não é válido."),
            @ApiResponse(responseCode = "503", description = "Não é possivel validar o código porque o redis está inacessível.")
    })
    @PostMapping("/validarRecuperacao/{cnpj}")
    public ResponseEntity<RespostaApi> recuperacao(@PathVariable String cnpj, String code) {
        return authService.validarRecuperacao(cnpj, code).get();
    }


    @Operation(summary = "Refresh token.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Token atualizado com sucesso."),
            @ApiResponse(responseCode = "401", description = "Refresh token invalido."),
            @ApiResponse(responseCode = "404", description = "Empresa não encontrada.")
    })
    @PostMapping("/refreshToken/{cnpj}")
    public ResponseEntity<RespostaApi> alterarSenha(@PathVariable String cnpj, @RequestParam String  refreshTokenParam){
        return authService.refreshToken(cnpj, refreshTokenParam).get();
    }
}
