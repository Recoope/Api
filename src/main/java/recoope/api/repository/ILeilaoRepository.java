package recoope.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import recoope.api.domain.entities.Leilao;

import java.util.List;

@Repository
public interface ILeilaoRepository extends JpaRepository<Leilao, Long> {

    @Query(value = "SELECT l FROM Leilao l WHERE lower(l.produto.tipoProduto) LIKE %?1%")
    List<Leilao> pegarPorMaterial(String material);

    @Query(value = "SELECT l FROM Leilao l WHERE l.cooperativa.idCooperativa = ?1")
    List<Leilao> porCooperativa(Long idCooperativa);

    @Query(value = "SELECT l FROM Leilao l WHERE l.cooperativa.idCooperativa = ?1 AND lower(l.produto.tipoProduto) LIKE %?2%")
    List<Leilao> porCooperativaEMaterial(Long idCooperativa, String material);
}
