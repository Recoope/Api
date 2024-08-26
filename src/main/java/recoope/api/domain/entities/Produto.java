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

    @Column(name = "valor_produto")
    private Double valorProduto;

    @Column(name = "peso")
    private Double peso;

    @Column(name = "foto_leilao")
    private String fotoLeilao;
}
