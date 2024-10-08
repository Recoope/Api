package recoope.api.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "produto")
@Getter
@Setter
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produto")
    private Long idProduto;

    @Column(name = "tipo_produto")
    private String tipoProduto;

    @Column(name = "valor_inicial_produto")
    private Double valorInicialProduto;

    @Column(name = "valor_final_produto")
    private Double valorFinalProduto;

    @Column(name = "peso")
    private Double peso;

    @Column(name = "foto_leilao")
    private String fotoLeilao;

    @Column(name = "status")
    private String status;
}
