package recoope.api.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.util.Date;

@Entity
@Table(name = "leilao")
@Getter
@Setter
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
    @JoinColumn(name = "cnpj_cooperativa")
    private Cooperativa cooperativa;

    @Column(name = "status")
    private String status;
}
