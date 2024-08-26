package recoope.api.domain.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class EmpresaDto {

    private String nomeEmpresa;

    private String emailEmpresa;

    private String telefoneEmpresa;

    private String cnpjEmpresa;

    private Date registroEmpresa;

    private String tempoConosco;

    private String leiloesParticipados;
}
