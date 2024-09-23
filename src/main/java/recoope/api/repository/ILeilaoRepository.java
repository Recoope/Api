package recoope.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import recoope.api.domain.entities.Leilao;

import java.util.Date;
import java.util.List;

@Repository
public interface ILeilaoRepository extends JpaRepository<Leilao, Long> {

    @Query("SELECT l FROM Leilao l WHERE lower(l.produto.tipoProduto) LIKE %?1%")
    List<Leilao> pegarPorMaterial(String material);

    @Query("SELECT l FROM Leilao l WHERE l.cooperativa.cnpj = ?1")
    List<Leilao> porCooperativa(String idCooperativa);

    @Query("SELECT l FROM Leilao l WHERE l.cooperativa.cnpj = ?1 AND lower(l.produto.tipoProduto) LIKE %?2%")
    List<Leilao> porCooperativaEMaterial(String idCooperativa, String material);

    @Query("SELECT l FROM Leilao l WHERE l.dataFimLeilao = ?1")
    List<Leilao> porDataDeFim(Date data);
}
