package recoope.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import recoope.api.domain.entities.Leilao;

import java.util.List;

@Repository
public interface ILeilaoRepository extends JpaRepository<Leilao, Long> {
    @Query(value = "SELECT l FROM Leilao l")
    List<Leilao> pegar();

    @Query(value = "SELECT l FROM Leilao l WHERE l.produto.tipoProduto LIKE %:material%")
    List<Leilao> pegarPorMaterial(@Param("material") String material);

    @Query(value = "SELECT l FROM Leilao l WHERE l.idCooperativa = :id")
    List<Leilao> porCooperativa(@Param("id") Long idCooperativa);

    @Query(value = "SELECT l FROM Leilao l WHERE l.idCooperativa = :idCooperativa AND l.produto.tipoProduto LIKE %:material%")
    List<Leilao> porCooperativaEMaterial(@Param("idCooperativa") Long idCooperativa, @Param("material") String material);
}
