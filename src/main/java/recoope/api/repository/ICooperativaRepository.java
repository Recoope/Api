package recoope.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import recoope.api.domain.entities.Cooperativa;
import recoope.api.domain.entities.Leilao;

import java.util.List;

@Repository
public interface ICooperativaRepository extends JpaRepository<Cooperativa, Long> {
    @Query(value = "SELECT c FROM Cooperativa c WHERE c.nomeCooperativa LIKE %:nome%")
    List<Cooperativa> pegarPorNome(@Param("nome") String nome);
}
