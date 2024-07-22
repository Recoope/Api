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

    public void setIdLeilao(Long idLeilao) {
        this.idLeilao = idLeilao;
    }

    public Date getDataInicioLeilao() {
        return dataInicioLeilao;
    }

    public void setDataInicioLeilao(Date dataInicioLeilao) {
        this.dataInicioLeilao = dataInicioLeilao;
    }

    public Date getDataFimLeilao() {
        return dataFimLeilao;
    }

    public void setDataFimLeilao(Date dataFimLeilao) {
        this.dataFimLeilao = dataFimLeilao;
    }

    public String getDetalhesLeilao() {
        return detalhesLeilao;
    }

    public void setDetalhesLeilao(String detalhesLeilao) {
        this.detalhesLeilao = detalhesLeilao;
    }

    public Time getHoraLeilao() {
        return horaLeilao;
    }

    public void setHoraLeilao(Time horaLeilao) {
        this.horaLeilao = horaLeilao;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
}
