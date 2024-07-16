package recoope.api.domain.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "endereco")
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_endereco")
    private Long idEndereco;

    @Column(name = "cidade")
    private String cidade;

    @Column(name = "rua")
    private String rua;

    @Column(name = "numero")
    private Integer numero;

    public Endereco(Long idEndereco, String cidade, String rua, Integer numero) {
        this.idEndereco = idEndereco;
        this.cidade = cidade;
        this.rua = rua;
        this.numero = numero;
    }

    public Endereco() {}
}
