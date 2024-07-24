package recoope.api.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "cooperativa")
public class Cooperativa
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cooperativa")
    private Long idCooperativa;
    @Column(name = "nome_cooperativa")
    private String nomeCooperativa;
    @Column(name = "email_cooperativa")
    private String emailCooperativa;
    @JsonIgnore
    @Column(name = "senha_cooperativa")
    private String senhaCooperativa;

    public Cooperativa(Long idCooperativa, String nomeCooperativa,String emailCooperativa,String senhaCooperativa)
    {
        this.idCooperativa = idCooperativa;
        this.nomeCooperativa = nomeCooperativa;
        this.emailCooperativa = emailCooperativa;
        this.senhaCooperativa = senhaCooperativa;
    }

    public Cooperativa() {}

    public Long getIdCooperativa() {
        return idCooperativa;
    }

    public void setIdCooperativa(Long idCooperativa) {
        this.idCooperativa = idCooperativa;
    }

    public String getNomeCooperativa() {
        return nomeCooperativa;
    }

    public void setNomeCooperativa(String nomeCooperativa) {
        this.nomeCooperativa = nomeCooperativa;
    }

    public String getEmailCooperativa() {
        return emailCooperativa;
    }

    public void setEmailCooperativa(String emailCooperativa) {
        this.emailCooperativa = emailCooperativa;
    }

    public String getSenhaCooperativa() {
        return senhaCooperativa;
    }

    public void setSenhaCooperativa(String senhaCooperativa) {
        this.senhaCooperativa = senhaCooperativa;
    }
}
