package recoope.api.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

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

    @Column(name = "refresh_token")
    private String refreshToken;
}
