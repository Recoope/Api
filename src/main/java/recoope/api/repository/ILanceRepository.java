package recoope.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import recoope.api.domain.entities.Empresa;
import recoope.api.domain.entities.Lance;
import recoope.api.domain.entities.Leilao;

import java.util.Date;
import java.util.List;

@Repository
public interface ILanceRepository extends JpaRepository<Lance, Long>  {
    @Query("SELECT COUNT(DISTINCT l.leilao) FROM Lance l WHERE l.empresa.cnpj = ?1 AND l.leilao.status = 'Ativo'")
    int empresaLeiloesParticipados(String cnpj);

    @Query("SELECT l FROM Lance l WHERE l.leilao = ?1 AND " +
           "l.valor = (SELECT MAX(l2.valor) FROM Lance l2 WHERE l2.leilao = ?1)")
    Lance maiorLance(Leilao leilao);

    @Query("SELECT l FROM Lance l " +
            "WHERE l.empresa.cnpj = ?1 AND " +
            "l.valor = (SELECT MAX(l2.valor) FROM Lance l2 WHERE l2.leilao = l.leilao)")
    List<Lance> lancesSobressalentesPorEmpresa(String cnpj);

    @Query("SELECT MAX(l.id) FROM Lance l")
    Long lastId();

    @Query("SELECT l FROM Lance l WHERE l.empresa = ?1 AND l.leilao = ?2 ORDER BY l.valor DESC")
    List<Lance> pegarLancesPorLeilao(Empresa empresa, Leilao leilao);

    @Query("SELECT l FROM Lance l " +
            "WHERE l.empresa = ?1 AND " +
            "l.valor = (SELECT MAX(l2.valor) FROM Lance l2 " +
            "           WHERE l2.leilao = l.leilao AND l2.empresa = ?1) " +
            "ORDER BY l.data DESC")
    List<Lance> pegarLances(Empresa empresa);

    @Query("SELECT l FROM Lance l " +
            "WHERE l.empresa = ?1 AND l.leilao.dataFim = ?2 AND " +
            "l.valor = (SELECT MAX(l2.valor) FROM Lance l2 " +
            "           WHERE l2.leilao = l.leilao AND l2.empresa = ?1) " +
            "ORDER BY l.data DESC")
    List<Lance> pegarLances(Empresa empresa, Date fim);

    @Procedure(procedureName = "insert_lance")
    void inserir(
            @Param("lan_id_lance") int idLance,
            @Param("lan_id_leilao") int idLeilao,
            @Param("lan_cnpj_empresa") String cnpj,
            @Param("lan_valor") Double valor,
            @Param("lan_data") Date data
    );

    @Query("SELECT lc FROM Leilao l JOIN Lance lc ON lc.leilao.id = l.id " +
            "WHERE (l.dataFim < CURRENT_DATE OR l.dataFim = CURRENT_DATE AND l.hora < CURRENT_TIME) AND " +
            "lc.valor = (" +
            "    SELECT MAX(lc2.valor) " +
            "    FROM Lance lc2 " +
            "    WHERE lc2.leilao.id = l.id " +
            ") " +
            "AND lc.empresa.cnpj = ?1 " +
            "ORDER BY l.dataFim DESC")
    List<Lance> pegarVencidosComMaiorLanceRecentes(String cnpj);

    @Query("SELECT lc FROM Leilao l JOIN Lance lc ON lc.leilao.id = l.id " +
            "WHERE (l.dataFim < CURRENT_DATE OR l.dataFim = CURRENT_DATE AND l.hora < CURRENT_TIME) AND " +
            "lc.valor = (" +
            "    SELECT MAX(lc2.valor) " +
            "    FROM Lance lc2 " +
            "    WHERE lc2.leilao.id = l.id " +
            ") " +
            "AND lc.empresa.cnpj = ?1 " +
            "ORDER BY l.dataFim ASC")
    List<Lance> pegarVencidosComMaiorLanceAnteriores(String cnpj);

}
