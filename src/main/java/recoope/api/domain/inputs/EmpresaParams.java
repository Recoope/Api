package recoope.api.domain.inputs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpresaParams {
    private String nome;

    private String cnpj;

    private String email;

    private String telefone;

    private String senha;

    private String confirmacaoSenha;
}
