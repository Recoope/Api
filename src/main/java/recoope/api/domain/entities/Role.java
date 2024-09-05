package recoope.api.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity(name = "roles")
public class Role {
    @Id
    public Long id;
    @Column(name = "role_name")
    public String nome;

    public Role() {}

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
