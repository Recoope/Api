package recoope.api.domain.dtos;

import lombok.Getter;
import lombok.Setter;
import recoope.api.domain.entities.Empresa;
import recoope.api.domain.entities.Leilao;

import java.util.Date;

@Getter
@Setter
public class LanceDto {
    private  Long id;

    private Leilao leilao;

    private Empresa empresa;

    private Double valor;

    private Date data;

    public LanceDto(Long id, Leilao leilao, Empresa empresa, Double valor, Date data) {
        this.id = id;
        this.leilao = leilao;
        this.empresa = empresa;
        this.valor = valor;
        this.data = data;
    }
}
