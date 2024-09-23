package recoope.api.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "cooperativa")
@NoArgsConstructor
@Getter
@Setter
public class Cooperativa
{
    @Id
    @Column(name = "cnpj_cooperativa")
    private String cnpj;

    @Column(name = "nome_cooperativa")
    private String nome;

    @Column(name = "email_cooperativa")
    private String email;

    @JsonIgnore
    @Column(name = "senha_cooperativa")
    private String senha;

    @Column(name = "registro_cooperativa")
    private Date registro;
}
