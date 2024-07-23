package recoope.api.domain.dtos;

import recoope.api.domain.entities.Cooperativa;
import recoope.api.domain.entities.Endereco;
import recoope.api.domain.entities.Lance;
import recoope.api.domain.entities.Produto;

import java.sql.Time;
import java.util.Date;

public class LeilaoDto {
    private Long idLeilao;

    private Date dataInicioLeilao;

    private Date dataFimLeilao;

    private String detalhesLeilao;

    private Time horaLeilao;

    private String tempoRestante;

    private Lance maiorLance;

    private Endereco endereco;

    private Produto produto;

    private Cooperativa cooperativa;

    public LeilaoDto(Long idLeilao, Date dataInicioLeilao, Date dataFimLeilao, String detalhesLeilao, Time horaLeilao, String tempoRestante, Lance maiorLance, Endereco endereco, Produto produto, Cooperativa cooperativa) {
        this.idLeilao = idLeilao;
        this.dataInicioLeilao = dataInicioLeilao;
        this.dataFimLeilao = dataFimLeilao;
        this.detalhesLeilao = detalhesLeilao;
        this.horaLeilao = horaLeilao;
        this.tempoRestante = tempoRestante;
        this.maiorLance = maiorLance;
        this.endereco = endereco;
        this.produto = produto;
        this.cooperativa = cooperativa;
    }

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

    public Cooperativa getCooperativa() {
        return cooperativa;
    }

    public String getTempoRestante() {
        return tempoRestante;
    }

    public Lance getMaiorLance() {
        return maiorLance;
    }
}
