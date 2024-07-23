package recoope.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import recoope.api.domain.entities.Lance;
import recoope.api.domain.entities.Leilao;

@Repository
public interface ILanceRepository extends JpaRepository<Lance, Long>  {
    @Query(value = "SELECT COUNT(DISTINCT l.leilao) FROM Lance l WHERE l.empresa.idEmpresa = ?1")
    int empresaLeiloesParticipados(Long id);

    @Query(value = "SELECT l FROM Lance l WHERE l.leilao = ?1 AND " +
           " l.valor = (SELECT MAX(l2.valor) FROM Lance l2 WHERE l2.leilao = ?1)")
    Lance maiorLance(Leilao leilao);
}
