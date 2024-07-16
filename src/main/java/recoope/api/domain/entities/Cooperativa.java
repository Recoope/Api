package recoope.api.domain.entities;

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
}
