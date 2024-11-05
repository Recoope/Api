package recoope.api.domain.inputs;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlterarSenhaParam {
    private String novaSenha;

    @JsonCreator
    public AlterarSenhaParam(@JsonProperty("novaSenha") String novaSenha) {
        this.novaSenha = novaSenha;
    }}
