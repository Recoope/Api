package recoope.api.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "empresa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Empresa {
    @Column(name = "nome_empresa")
    private String nome;

    @Column(name = "email_empresa")
    private String email;

    @JsonIgnore
    @Column(name = "senha_empresa")
    private String senha;

    @Column(name = "telefone_empresa")
    private String telefone;

    @Id
    @Column(name = "cnpj_empresa")
    private String cnpj;
}
