package recoope.api.domain.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "empresa")
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empresa")
    private Long idEmpresa;

    @Column(name = "nome_empresa")
    private String nomeEmpresa;

    @Column(name = "email_empresa")
    private String emailEmpresa;

    @Column(name = "senha_empresa")
    private String senhaEmpresa;

    @Column(name = "telefone_empresa")
    private String telefoneEmpresa;

    @Column(name = "cnpj_empresa")
    private String cnpjEmpresa;

    public Empresa(Long idEmpresa, String nomeEmpresa, String emailEmpresa, String senhaEmpresa, String telefoneEmpresa, String cnpjEmpresa) {
        this.idEmpresa = idEmpresa;
        this.nomeEmpresa = nomeEmpresa;
        this.emailEmpresa = emailEmpresa;
        this.senhaEmpresa = senhaEmpresa;
        this.telefoneEmpresa = telefoneEmpresa;
        this.cnpjEmpresa = cnpjEmpresa;
    }

    public Empresa() {}
}
