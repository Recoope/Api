package recoope.api.domain.inputs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlterarEmpresaParams {
    private String nome;

    private String email;

    private String telefone;
}
