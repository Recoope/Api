package recoope.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recoope.api.domain.RespostaApi;
import recoope.api.domain.inputs.LoginParams;
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
            @ApiResponse(responseCode = "201", description = "Login feito com sucesso."),
            @ApiResponse(responseCode = "400", description = "Credenciais nulas foram passadas."),
            @ApiResponse(responseCode = "404", description = "Credenciais não possuem correspondecia.")
    })
    @PostMapping("/recuperar/{cnpj}")
    public ResponseEntity<RespostaApi> token(@PathVariable String cnpj) {
        return authService.gerarRecuperacao(cnpj).get();
    }

}
