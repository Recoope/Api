package recoope.api.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Date;

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

    @JsonIgnore
    @Column(name = "senha_empresa")
    private String senhaEmpresa;

    @Column(name = "telefone_empresa")
    private String telefoneEmpresa;

    @Column(name = "cnpj_empresa")
    private String cnpjEmpresa;

    @Column(name = "registro_empresa")
    private Date registroEmpresa;

    public Empresa(Long idEmpresa, String nomeEmpresa, String emailEmpresa, String senhaEmpresa, String telefoneEmpresa, String cnpjEmpresa, Date registroEmpresa) {
        this.idEmpresa = idEmpresa;
        this.nomeEmpresa = nomeEmpresa;
        this.emailEmpresa = emailEmpresa;
        this.senhaEmpresa = senhaEmpresa;
        this.telefoneEmpresa = telefoneEmpresa;
        this.cnpjEmpresa = cnpjEmpresa;
        this.registroEmpresa = registroEmpresa;
    }

    public Empresa() {}

    public Long getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Long idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

    public String getEmailEmpresa() {
        return emailEmpresa;
    }

    public void setEmailEmpresa(String emailEmpresa) {
        this.emailEmpresa = emailEmpresa;
    }

    public String getSenhaEmpresa() {
        return senhaEmpresa;
    }

    public void setSenhaEmpresa(String senhaEmpresa) {
        this.senhaEmpresa = senhaEmpresa;
    }

    public String getTelefoneEmpresa() {
        return telefoneEmpresa;
    }

    public void setTelefoneEmpresa(String telefoneEmpresa) {
        this.telefoneEmpresa = telefoneEmpresa;
    }

    public String getCnpjEmpresa() {
        return cnpjEmpresa;
    }

    public void setCnpjEmpresa(String cnpjEmpresa) {
        this.cnpjEmpresa = cnpjEmpresa;
    }

    public Date getRegistroEmpresa() {
        return registroEmpresa;
    }

    public void setRegistroEmpresa(Date registroEmpresa) {
        this.registroEmpresa = registroEmpresa;
    }
}
