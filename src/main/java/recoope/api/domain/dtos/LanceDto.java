package recoope.api.domain.dtos;

import lombok.Getter;
import lombok.Setter;
import recoope.api.domain.entities.Empresa;
import recoope.api.domain.entities.Leilao;

import java.util.Date;

@Getter
@Setter
public class LanceDto {
    private  Long idLance;

    private Leilao leilao;

    private Empresa empresa;

    private Double valor;

    private Date dataLance;

    public LanceDto(Long idLance, Leilao leilao, Empresa empresa, Double valor, Date dataLance) {
        this.idLance = idLance;
        this.leilao = leilao;
        this.empresa = empresa;
        this.valor = valor;
        this.dataLance = dataLance;
    }
}
