package recoope.api.domain.entities;

import jakarta.persistence.*;

import java.sql.Time;
import java.util.Date;

@Entity
@Table(name = "leilao")
public class Leilao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_leilao")
    private Long idLeilao;

    @Column(name = "data_inicio_leilao")
    private Date dataInicioLeilao;

    @Column(name = "data_fim_leilao")
    private Date dataFimLeilao;

    @Column(name = "detalhes_leilao")
    private String detalhesLeilao;

    @Column(name = "hora_leilao")
    private Time horaLeilao;

    @ManyToOne
    @JoinColumn(name = "id_endereco")
    private Endereco endereco;

    @ManyToOne
    @JoinColumn(name = "id_produto")
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "id_cooperativa")
    private Cooperativa cooperativa;

    public Leilao(Long idLeilao, Date dataInicioLeilao, Date dataFimLeilao, String detalhesLeilao, Time horaLeilao, Endereco endereco, Produto produto, Cooperativa cooperativa) {
        this.idLeilao = idLeilao;
        this.dataInicioLeilao = dataInicioLeilao;
        this.dataFimLeilao = dataFimLeilao;
        this.detalhesLeilao = detalhesLeilao;
        this.horaLeilao = horaLeilao;
        this.endereco = endereco;
        this.produto = produto;
        this.cooperativa = cooperativa;
    }

    public Leilao() {}

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

    public Cooperativa getCooperativa() {
        return cooperativa;
    }

    public void setCooperativa(Cooperativa cooperativa) {
        this.cooperativa = cooperativa;
    }
}
