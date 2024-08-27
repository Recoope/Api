package recoope.api.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import recoope.api.domain.entities.*;

import java.sql.Time;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class LeilaoPorCooperativa {
    private Long idLeilao;

    private Date dataInicioLeilao;

    private Date dataFimLeilao;

    private String detalhesLeilao;

    private Time horaLeilao;

    private Endereco endereco;

    private Produto produto;
}
