package recoope.api.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import recoope.api.controllers.AuthController;
import recoope.api.domain.Mensagens;
import recoope.api.domain.RespostaApi;
import recoope.api.domain.Validacoes;
import recoope.api.domain.entities.Cooperativa;
import recoope.api.domain.entities.Empresa;
import recoope.api.domain.inputs.LoginParams;
import recoope.api.repository.ICooperativaRepository;
import recoope.api.repository.IEmpresaRepository;
import redis.clients.jedis.UnifiedJedis;

import javax.crypto.SecretKey;
import java.util.*;

@Service
public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    public final SecretKey secretKey;
    private final IEmpresaRepository empresaRepository;
    private final ICooperativaRepository cooperativaRepository;

    public AuthService (SecretKey secretKey, IEmpresaRepository empresaRepository, ICooperativaRepository cooperativaRepository) {
        this.secretKey = secretKey;
        this.empresaRepository = empresaRepository;
        this.cooperativaRepository = cooperativaRepository;
    }

    public RespostaApi<Map<String, String>> login(LoginParams params) {
        boolean isViaCnpj = Validacoes.CNPJ(params.getCnpjOuEmail());
        boolean isViaEmail = Validacoes.EMAIL(params.getCnpjOuEmail());

        Map<String, String> response;
        String token;

        if (!(isViaCnpj || isViaEmail)) return new RespostaApi<>(401, Mensagens.EMAIL_CNPJ_INVALIDO);

        Optional<Empresa> empOptional = empresaRepository.login(params.getCnpjOuEmail());

        if (empOptional.isPresent() && empOptional.get().getSenha().equals(params.getSenha())) {
            token = generateToken(empOptional.get().getEmail());
            response = new HashMap() {
                {
                    put("context", "EMPRESA");
                    put("cnpj", empOptional.get().getCnpj());
                    put("token", token);
                }
            };

            return new RespostaApi<>(Mensagens.LOGIN_SUCESSO, response);
        } else {
            Optional<Cooperativa> coopOptional = cooperativaRepository.login(params.getCnpjOuEmail());

            if (coopOptional.isPresent() && coopOptional.get().getSenha().equals(params.getSenha())) {
                token = generateToken(coopOptional.get().getEmail());
                response = new HashMap() {
                    {
                        put("context", "COOPERATIVA");
                        put("cnpj", coopOptional.get().getCnpj());
                        put("token", token);
                    }
                };

                return new RespostaApi<>(Mensagens.LOGIN_SUCESSO, response);
            }

        }

        logger.info("Invalid credentials: " + params);
        String mensagem = isViaCnpj ?
                Mensagens.NAO_EXISTE_CNPJ_CORRESPONDENTE_OU_SENHA_INCORRETA :
                Mensagens.NAO_EXISTE_EMAIL_CORRESPONDENTE_OU_SENHA_INCORRETA;

        return new RespostaApi<>(401, mensagem);

    }

    public RespostaApi<Map<String, String>> gerarRecuperacao(String cnpjOuEmail) {

        Optional<Empresa> empOptional = empresaRepository.login(cnpjOuEmail);

        if (empOptional.isPresent()) {
            String cnpj = empOptional.get().getCnpj();

            try (UnifiedJedis jedis = new UnifiedJedis()) {
                Random random = new Random();
                String codigoGerado = String.valueOf(100000 + random.nextInt(900000));
                logger.info("Código gerado: " + codigoGerado + " para a chave recovery:" + cnpj);


                jedis.set("recovery:" + cnpj, codigoGerado);
                jedis.expire("recovery:" + cnpj, 60 * 15);

                Map<String, String> codigo = new HashMap<>(){{
                   put("code", codigoGerado);
                }};

                return new RespostaApi<>(Mensagens.CODIGO_ENVIADO, codigo);
            } catch (Exception e) {
                return new RespostaApi<>(503, Mensagens.ERRO_REDIS);
            }
        }

        return new RespostaApi<>(404, Mensagens.EMPRESA_NAO_ENCONTRADA);
    }

    public RespostaApi<String> confirmarRecuperacao(String cnpj, String code) {
        try(UnifiedJedis jedis = new UnifiedJedis()){
            String recoveredCode = jedis.get("recovery:" + cnpj);
            if (code.equals(recoveredCode)) {
                return new RespostaApi<>(Mensagens.CODIGO_VALIDO, null);
            }
            return new RespostaApi<>(401, Mensagens.CODIGO_INVALIDO);
        } catch (Exception e) {
            return new RespostaApi<>(503, Mensagens.ERRO_REDIS);
        }
    }

    private String generateToken(String user) {
        try {
            String token = Jwts.builder()
                    .setSubject(user)
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