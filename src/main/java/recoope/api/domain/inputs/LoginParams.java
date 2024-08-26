package recoope.api.domain.inputs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginParams {
    private String cnpjOuEmail;

    private String senha;
}
