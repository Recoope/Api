package recoope.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import recoope.api.domain.entities.Lance;

@Repository
public interface ILanceRepository extends JpaRepository<Lance, Long>  {
    @Query(value = "SELECT COUNT(DISTINCT l.leilao) FROM Lance l WHERE l.empresa.idEmpresa = ?1")
    int empresaLeiloesParticipados(Long id);
}
