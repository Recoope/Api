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
    private Long id;

    private Date dataInicio;

    private Date dataFim;

    private String detalhes;

    private Time hora;

    private Endereco endereco;

    private Produto produto;
}
