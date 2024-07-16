package recoope.api.domain.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "lance")
public class Lance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_lance")
    private Long idLance;

    @ManyToOne
    @JoinColumn(name = "id_leilao")
    private Leilao leilao;

    @ManyToOne
    @JoinColumn(name = "id_empresa")
    private Empresa empresa;

    @Column(name = "valor")
    private Double valor;

    @Column(name = "data_lance")
    private Date dataLance;

    public Lance(Long idLance, Leilao leilao, Empresa empresa, Double valor, Date dataLance) {
        this.idLance = idLance;
        this.leilao = leilao;
        this.empresa = empresa;
        this.valor = valor;
        this.dataLance = dataLance;
    }

    public Lance() {}
}
