package recoope.api.domain.entities;

import jakarta.persistence.*;

import java.sql.Time;
import java.sql.Date;

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
    private Cooperativa idCooperativa;

    public Leilao(Long idLeilao, Date dataInicioLeilao, Date dataFimLeilao, String detalhesLeilao, Time horaLeilao, Endereco endereco, Produto produto, Cooperativa idCooperativa) {
        this.idLeilao = idLeilao;
        this.dataInicioLeilao = dataInicioLeilao;
        this.dataFimLeilao = dataFimLeilao;
        this.detalhesLeilao = detalhesLeilao;
        this.horaLeilao = horaLeilao;
        this.endereco = endereco;
        this.produto = produto;
        this.idCooperativa = idCooperativa;
    }

    public Leilao() {}
}
