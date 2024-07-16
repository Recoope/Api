package recoope.api.domain.entities;

import jakarta.persistence.*;

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
    private Date horaLeilao;

    @ManyToOne
    @JoinColumn(name = "id_endereco")
    private Endereco endereco;

    @ManyToOne
    @JoinColumn(name = "id_produto")
    private Produto produto;

    public Leilao(Long idLeilao, Date dataInicioLeilao, Date dataFimLeilao, String detalhesLeilao, Date horaLeilao, Endereco endereco, Produto produto) {
        this.idLeilao = idLeilao;
        this.dataInicioLeilao = dataInicioLeilao;
        this.dataFimLeilao = dataFimLeilao;
        this.detalhesLeilao = detalhesLeilao;
        this.horaLeilao = horaLeilao;
        this.endereco = endereco;
        this.produto = produto;
    }

    public Leilao() {}
}
