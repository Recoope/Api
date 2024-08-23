package recoope.api.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import recoope.api.domain.dtos.LanceDto;

import java.sql.Date;

@Entity
@Table(name = "lance")
public class Lance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_lance")
    private Long idLance;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_leilao")
    private Leilao leilao;

    @ManyToOne
    @JoinColumn(name = "cnpj_empresa")
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

    public Long getIdLance() {
        return idLance;
    }

    public void setIdLance(Long idLance) {
        this.idLance = idLance;
    }

    public Leilao getLeilao() {
        return leilao;
    }

    public void setLeilao(Leilao leilao) {
        this.leilao = leilao;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Date getDataLance() {
        return dataLance;
    }

    public void setDataLance(Date dataLance) {
        this.dataLance = dataLance;
    }

    public LanceDto toDto() {
        return new LanceDto(idLance, leilao, empresa, valor, dataLance);
    }
}
