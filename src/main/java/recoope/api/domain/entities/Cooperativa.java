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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cnpj_cooperativa")
    private String cnpjCooperativa;

    @Column(name = "nome_cooperativa")
    private String nomeCooperativa;

    @Column(name = "email_cooperativa")
    private String emailCooperativa;

    @JsonIgnore
    @Column(name = "senha_cooperativa")
    private String senhaCooperativa;

    @Column(name = "registro_cooperativa")
    private Date registroCooperativa;
}
