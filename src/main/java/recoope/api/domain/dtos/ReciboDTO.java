package recoope.api.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class ReciboDTO {
    private int id;
    private Date dataEmissao;
    private Time horaEmissao;
    private String nomeCooperativa;
    private String cnpjCooperativa;
    private String nomeEmpresa;
    private String cnpjEmpresa;
    private double valor;
}
