package recoope.api.domain.dtos;

import recoope.api.domain.entities.*;

import java.sql.Time;
import java.util.Date;

public class LeilaoPorCooperativa {
    private Long idLeilao;

    private Date dataInicioLeilao;

    private Date dataFimLeilao;

    private String detalhesLeilao;

    private Time horaLeilao;

    private Endereco endereco;

    private Produto produto;

    public LeilaoPorCooperativa(Long idLeilao, Date dataInicioLeilao, Date dataFimLeilao, String detalhesLeilao, Time horaLeilao, Endereco endereco, Produto produto) {
        this.idLeilao = idLeilao;
        this.dataInicioLeilao = dataInicioLeilao;
        this.dataFimLeilao = dataFimLeilao;
        this.detalhesLeilao = detalhesLeilao;
        this.horaLeilao = horaLeilao;
        this.endereco = endereco;
        this.produto = produto;
    }

    public LeilaoPorCooperativa() {}

    public Long getIdLeilao() {
        return idLeilao;
    }

    public Date getDataInicioLeilao() {
        return dataInicioLeilao;
    }

    public Date getDataFimLeilao() {
        return dataFimLeilao;
    }

    public String getDetalhesLeilao() {
        return detalhesLeilao;
    }

    public Time getHoraLeilao() {
        return horaLeilao;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public Produto getProduto() {
        return produto;
    }
}
