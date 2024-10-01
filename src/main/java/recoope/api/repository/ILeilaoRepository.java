package recoope.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import recoope.api.domain.entities.Leilao;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Repository
public interface ILeilaoRepository extends JpaRepository<Leilao, Long> {

    @Query("SELECT l FROM Leilao l WHERE lower(l.produto.tipoProduto) LIKE %?1%")
    List<Leilao> pegarPorMaterial(String material);

    @Query("SELECT l FROM Leilao l WHERE l.cooperativa.cnpj = ?1")
    List<Leilao> porCooperativa(String cnpjEmpresa);

    @Query("SELECT l FROM Leilao l WHERE l.cooperativa.cnpj = ?1 AND lower(l.produto.tipoProduto) LIKE %?2%")
    List<Leilao> porCooperativaEMaterial(String cnpjEmpresa, String material);

    @Query("SELECT l FROM Leilao l WHERE l.dataFimLeilao = ?1")
    List<Leilao> porDataDeFim(Date data);

    @Query("SELECT l FROM Leilao l WHERE (l.dataFimLeilao >= current_date AND l.horaLeilao > current_time) ")
    List<Leilao> pegarTodosAtivos();

    @Query("SELECT l.dataFimLeilao FROM Leilao l JOIN Lance lance ON lance.leilao.idLeilao = l.idLeilao " +
            "WHERE lance.empresa.cnpj = ?1 AND EXTRACT(MONTH FROM l.dataFimLeilao) = ?2")
    Set<Date> pegarPorMes(String cnpjEmpresa, int mes);
}
