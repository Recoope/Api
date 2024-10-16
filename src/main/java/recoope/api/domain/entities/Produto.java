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
    private Long id;

    @Column(name = "tipo_produto")
    private String tipo;

    @Column(name = "valor_inicial_produto")
    private Double valorInicial;

    @Column(name = "valor_final_produto")
    private Double valorFinal;

    @Column(name = "peso_produto")
    private Double peso;

    @Column(name = "foto_produto")
    private String foto;

    @Column(name = "status")
    private String status;
}
