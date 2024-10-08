package recoope.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import recoope.api.domain.entities.Leilao;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Repository
public interface ILeilaoRepository extends JpaRepository<Leilao, Long> {
    @Query("SELECT l FROM Leilao l WHERE l.dataFimLeilao >= CURRENT_DATE AND " +
            "(l.dataFimLeilao > CURRENT_DATE OR l.horaLeilao > CURRENT_TIME)")
    List<Leilao> pegarAtivos();

    @Query("SELECT l FROM Leilao l WHERE l.dataFimLeilao >= CURRENT_DATE AND " +
            "(l.dataFimLeilao > CURRENT_DATE OR l.horaLeilao > CURRENT_TIME) " +
            "AND lower(l.produto.tipoProduto) IN (?1)")
    List<Leilao> pegarAtivosPorMaterial(List<String> materiais);

    @Query("SELECT l FROM Leilao l WHERE l.cooperativa.cnpj = ?1")
    List<Leilao> porCooperativa(String cnpjEmpresa);

    @Query("SELECT l FROM Leilao l WHERE l.cooperativa.cnpj = ?1 AND lower(l.produto.tipoProduto) LIKE %?2%")
    List<Leilao> porCooperativaEMaterial(String cnpjEmpresa, String material);

    @Query("SELECT DISTINCT l FROM Leilao l JOIN Lance ln ON ln.leilao.idLeilao = l.idLeilao " +
            "JOIN Empresa e ON ln.empresa.cnpj = e.cnpj WHERE e.cnpj = ?1")
    List<Leilao> participados(String cnpjEmpresa);

    @Query("SELECT DISTINCT l FROM Leilao l JOIN Lance ln ON ln.leilao.idLeilao = l.idLeilao " +
            "JOIN Empresa e ON ln.empresa.cnpj = e.cnpj WHERE e.cnpj = ?1 AND l.dataFimLeilao = ?2")
    List<Leilao> participadosPorDataFim(String cnpjEmpresa, Date dataFim);

    @Query("SELECT l.dataFimLeilao FROM Leilao l JOIN Lance lance ON lance.leilao.idLeilao = l.idLeilao " +
            "WHERE lance.empresa.cnpj = ?1")
    Set<Date> pegarVencimentos(String cnpjEmpresa);
}
