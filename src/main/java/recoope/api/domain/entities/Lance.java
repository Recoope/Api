package recoope.api.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import recoope.api.domain.dtos.LanceDto;

import java.util.Date;

@Entity
@Table(name = "lance")
@Getter
@Setter
@NoArgsConstructor
public class Lance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_lance")
    private Long id;

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
    private Date data;

    public LanceDto toDto() {
        return new LanceDto(
            id,
            leilao,
            empresa,
            valor,
            data
        );
    }
}
