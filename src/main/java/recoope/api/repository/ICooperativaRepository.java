package recoope.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import recoope.api.domain.entities.Cooperativa;

import java.util.List;

@Repository
public interface ICooperativaRepository extends JpaRepository<Cooperativa, String> {
    @Query("SELECT c FROM Cooperativa c WHERE lower(c.nomeCooperativa) LIKE %?1%")
    List<Cooperativa> pegarPorNome(String nome);
}
