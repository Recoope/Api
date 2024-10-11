package recoope.api.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import recoope.api.domain.entities.Cooperativa;
import recoope.api.domain.entities.Endereco;
import recoope.api.domain.entities.Lance;
import recoope.api.domain.entities.Produto;

import java.sql.Time;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class LeilaoDto {
    private Long id;

    private Date dataInicio;

    private Date dataFim;

    private String detalhes;

    private Time hora;

    private Lance maiorLance;

    private Endereco endereco;

    private Produto produto;

    private Cooperativa cooperativa;
}
