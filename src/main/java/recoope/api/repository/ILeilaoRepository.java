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
    @Query("SELECT l FROM Leilao l WHERE l.dataFim >= CURRENT_DATE AND " +
            "(l.dataFim > CURRENT_DATE OR l.hora > CURRENT_TIME)")
    List<Leilao> pegarAtivos();

    @Query("SELECT l FROM Leilao l WHERE l.cooperativa.cnpj = ?1")
    List<Leilao> porCooperativa(String cnpjEmpresa);

    @Query("SELECT l FROM Leilao l WHERE l.cooperativa.cnpj = ?1 AND lower(l.produto.tipo) LIKE %?2%")
    List<Leilao> porCooperativaEMaterial(String cnpjEmpresa, String material);

    @Query("SELECT DISTINCT l FROM Leilao l JOIN Lance ln ON ln.leilao.id = l.id " +
            "JOIN Empresa e ON ln.empresa.cnpj = e.cnpj WHERE e.cnpj = ?1")
    List<Leilao> participados(String cnpjEmpresa);

    @Query("SELECT DISTINCT l FROM Leilao l JOIN Lance ln ON ln.leilao.id = l.id " +
            "JOIN Empresa e ON ln.empresa.cnpj = e.cnpj WHERE e.cnpj = ?1 AND l.dataFim = ?2")
    List<Leilao> participadosPorDataFim(String cnpjEmpresa, Date dataFim);

    @Query("SELECT l.dataFim FROM Leilao l JOIN Lance lance ON lance.leilao.id = l.id " +
            "WHERE lance.empresa.cnpj = ?1")
    Set<Date> pegarVencimentos(String cnpjEmpresa);
}
