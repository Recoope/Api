package recoope.api.controllers;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import recoope.api.domain.Mensagens;
import recoope.api.domain.RespostaApi;
import recoope.api.domain.Validacoes;
import recoope.api.domain.entities.Cooperativa;
import recoope.api.domain.entities.Empresa;
import recoope.api.domain.inputs.LoginParams;
import recoope.api.repository.ICooperativaRepository;
import recoope.api.repository.IEmpresaRepository;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Tag(name = "Auth")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/login")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    public final SecretKey secretKey;
    private final ICooperativaRepository cooperativaRepository;
    private final IEmpresaRepository empresaRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(SecretKey secretKey, ICooperativaRepository cooperativaRepository, IEmpresaRepository empresaRepository, PasswordEncoder passwordEncoder) {
        this.secretKey = secretKey;
        this.cooperativaRepository = cooperativaRepository;
        this.empresaRepository = empresaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Operation(summary = "Login empresa/cooperativa.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Login feito com sucesso."),
            @ApiResponse(responseCode = "400", description = "Credenciais nulas foram passadas."),
            @ApiResponse(responseCode = "404", description = "Credenciais não possuem correspondecia.")
    })
    @PostMapping
    public ResponseEntity<RespostaApi> login(@RequestBody LoginParams params) {
        boolean isViaCnpj = Validacoes.CNPJ(params.getCnpjOuEmail());
        boolean isViaEmail = Validacoes.EMAIL(params.getCnpjOuEmail());

        Map<String, String> token;

        if (isViaCnpj || isViaEmail) {
            Optional<Empresa> empOptional = empresaRepository.login(params.getCnpjOuEmail());

            if (empOptional.isPresent() && empOptional.get().getSenha().equals(params.getSenha()))
                token = Map.of("token", generateToken(empOptional.get().getEmail(), "EMPRESA"));
            else {
                Optional<Cooperativa> coopOptional = cooperativaRepository.login(params.getCnpjOuEmail());

                if (coopOptional.isPresent() && coopOptional.get().getSenha().equals(params.getSenha()))
                    token = Map.of("token", generateToken(coopOptional.get().getEmail(), "COOPERATIVA"));
                else {
                    logger.info("Invalid credentials: " + params);
                    String mensagem = isViaCnpj ?
                            Mensagens.NAO_EXISTE_CNPJ_CORRESPONDENTE_OU_SENHA_INCORRETA :
                            Mensagens.NAO_EXISTE_EMAIL_CORRESPONDENTE_OU_SENHA_INCORRETA;

                    return new RespostaApi<>(401, mensagem).get();
                }
            }

            return new RespostaApi<>(Mensagens.LOGIN_SUCESSO, token).get();

        } return new RespostaApi<>(401, Mensagens.EMAIL_CNPJ_INVALIDO).get();
    }

    private String generateToken(String user, String role) {
        try {
            String token = Jwts.builder()
                    .setSubject(user)
//                    .claim("roles", List.of(role))
                    .setExpiration(new Date(System.currentTimeMillis() + (6 * 60 * 60 * 1000))) // 6 horas
                    .signWith(secretKey, SignatureAlgorithm.HS512)
                    .compact();

            logger.info("Generated token: " +  token);
            return "Bearer " + token;
        } catch (Exception e) {
            logger.info("JWT generation error: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "JWT generation error", e);
        }
    }
}
