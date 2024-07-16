package recoope.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import recoope.api.domain.entities.Cooperativa;
import recoope.api.domain.entities.Leilao;

import java.util.List;

public interface ICooperativaRepository extends JpaRepository<Cooperativa, Long> {
    @Query(value = "SELECT c FROM Cooperativa c WHERE c.nomeCooperativa LIKE %:nome%")
    List<Cooperativa> pegarPorNome(String nome);

    // @Query(value = "SELECT l FROM Leilao l WHERE l.idCooperativa = :idCooperativa")
    List<Leilao> leiloes(Long idCooperativa);

    // @Query(value = "SELECT l FROM Leilao l WHERE l.idCooperativa = :idCooperativa AND l.produto.tipoProduto = material")
    List<Leilao> leiloesPorMaterial(Long idCooperativa, String material);

}
