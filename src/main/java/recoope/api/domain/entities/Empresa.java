package recoope.api.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private String nomeEmpresa;

    @Column(name = "email_empresa")
    private String emailEmpresa;

    @JsonIgnore
    @Column(name = "senha_empresa")
    private String senhaEmpresa;

    @Column(name = "telefone_empresa")
    private String telefoneEmpresa;

    @Id
    @Column(name = "cnpj_empresa")
    private String cnpjEmpresa;

    @Column(name = "registro_empresa")
    private Date registroEmpresa;
}
