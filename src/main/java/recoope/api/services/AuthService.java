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
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.crypto.SecretKey;
import java.util.*;

@Service
public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    public final SecretKey secretKey;
    private final IEmpresaRepository empresaRepository;
    private final ICooperativaRepository cooperativaRepository;
    @Value("${spring.data.redis.host}")
    private String redisHost;
    @Value("${spring.data.redis.port}")
    private int redisPort;

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
            Empresa empresa = empOptional.get();
            String cnpj = empresa.getCnpj();

            String refreshToken = generateRefreshToken(empresa);
            token = generateToken(empresa.getEmail());

            response = new HashMap() {
                {
                    put("context", "EMPRESA");
                    put("cnpj", cnpj);
                    put("refreshToken", refreshToken);
                    put("token", "Bearer " + token);
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

            Random random = new Random();
            String codigo = String.valueOf(100000 + random.nextInt(900000));
            logger.info("Código gerado: " + codigo + " para a chave recovery:" + cnpj);

            try (Jedis jedis = new Jedis(redisHost, redisPort)) {
                jedis.set("recovery:" + cnpj, codigo);
                jedis.expire("recovery:" + cnpj, 60 * 15);

                Map<String, String> response = new HashMap() {{
                    put("code", codigo);
                }};

                return new RespostaApi<>(Mensagens.RECUPERACAO_GERADO, response);
            } catch (Exception e) {
                logger.info("Redis error: " + e.getMessage());
                logger.info("Redis URL: " + redisHost + ":" + redisPort);
                return new RespostaApi<>(503, Mensagens.ERRO_REDIS);
            }

        }

        return new RespostaApi<>(404, Mensagens.EMPRESA_NAO_ENCONTRADA);
    }

    public RespostaApi validarRecuperacao(String cnpj, String codigo) {
        String codigoVerdadeiro;

        try (Jedis jedis = new Jedis(redisHost, redisPort)) {
            codigoVerdadeiro = jedis.get("recovery:" + cnpj);
        } catch (Exception e) {
            logger.info("Redis error: " + e.getMessage());
            return new RespostaApi<>(503, Mensagens.ERRO_REDIS);
        }

        return Objects.equals(codigoVerdadeiro, codigo) ?
                new RespostaApi<>(Mensagens.RECUPERACAO_VALIDO) :
                new RespostaApi<>(401, Mensagens.RECUPERACAO_INVALIDO);
    }

    private String generateToken(String user) {
        try {
            String token = Jwts.builder()
                    .setSubject(user)
                    .setExpiration(new Date(System.currentTimeMillis() + (6 * 60 * 60 * 1000))) // 6 horas
                    .signWith(secretKey, SignatureAlgorithm.HS512)
                    .compact();

            logger.info("Generated token: " +  token);
            return token;
        } catch (Exception e) {
            logger.info("JWT generation error: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "JWT generation error", e);
        }
    }

    private String generateRefreshToken(Empresa empresa) {
        String refreshToken = generateToken(empresa.toString());
        empresa.setRefreshToken(refreshToken);

        empresaRepository.save(empresa);
        return refreshToken;
    }

    public RespostaApi<Map<String, String>> refreshToken(String cnpj, String refreshToken) {

        Optional<Empresa> empOptional = empresaRepository.findById(cnpj);

        if (empOptional.isPresent()) {

            if (empOptional.get().getRefreshToken().equals(refreshToken)) {
                String newToken = generateToken(empOptional.get().getEmail());
                logger.info("Refreshed token: " + newToken);

                Map<String, String> response = new HashMap() {{
                    put("token", "Bearer " + newToken);
                }};

                return new RespostaApi<>(Mensagens.REFRESH_TOKEN_SUCESSO, response);
            } else {
                return new RespostaApi<>(401, Mensagens.REFRESH_TOKEN_INVALIDO);
            }
        } else {
            return new RespostaApi<>(404, Mensagens.EMPRESA_NAO_ENCONTRADA);
        }
    }
}
