package recoope.api.domain.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EmpresaDto {

    private String nome;

    private String email;

    private String telefone;

    private String cnpj;

    private String leiloesParticipados;
}
