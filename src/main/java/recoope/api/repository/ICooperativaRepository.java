package recoope.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import recoope.api.domain.entities.Cooperativa;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICooperativaRepository extends JpaRepository<Cooperativa, String> {
    @Query("SELECT c FROM Cooperativa c WHERE c.cnpj = ?1 OR c.email = ?1")
    Optional<Cooperativa> login(String cnpjOuEmail);
    @Query("SELECT c FROM Cooperativa c WHERE lower(c.nome) LIKE %?1%")
    List<Cooperativa> pegarPorNome(String nome);
}
