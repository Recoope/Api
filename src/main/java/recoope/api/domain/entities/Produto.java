package recoope.api.domain.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "produto")
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

    public Produto(Long idProduto, String tipoProduto, Double valorProduto, Double peso, String fotoLeilao) {
        this.idProduto = idProduto;
        this.tipoProduto = tipoProduto;
        this.valorProduto = valorProduto;
        this.peso = peso;
        this.fotoLeilao = fotoLeilao;
    }

    public Produto() {}

    public Long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Long idProduto) {
        this.idProduto = idProduto;
    }

    public String getTipoProduto() {
        return tipoProduto;
    }

    public void setTipoProduto(String tipoProduto) {
        this.tipoProduto = tipoProduto;
    }

    public Double getValorProduto() {
        return valorProduto;
    }

    public void setValorProduto(Double valorProduto) {
        this.valorProduto = valorProduto;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public String getFotoLeilao() {
        return fotoLeilao;
    }

    public void setFotoLeilao(String fotoLeilao) {
        this.fotoLeilao = fotoLeilao;
    }

}
