package recoope.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import recoope.api.domain.entities.Empresa;
import recoope.api.domain.entities.Lance;
import recoope.api.domain.entities.Leilao;

import java.util.List;

@Repository
public interface ILanceRepository extends JpaRepository<Lance, Long>  {
    @Query("SELECT COUNT(DISTINCT l.leilao) FROM Lance l WHERE l.empresa.cnpj = ?1")
    int empresaLeiloesParticipados(String cnpj);

    @Query("SELECT l FROM Lance l WHERE l.leilao = ?1 AND " +
           " l.valor = (SELECT MAX(l2.valor) FROM Lance l2 WHERE l2.leilao = ?1)")
    Lance maiorLance(Leilao leilao);

    @Query("SELECT MAX(l.idLance) FROM Lance l")
    Long lastId();

    @Query("SELECT l FROM Lance l WHERE l.empresa = ?1 AND l.leilao = ?2 ORDER BY l.valor DESC")
    List<Lance> pegarLances(Empresa empresa, Leilao leilao);

    @Procedure(procedureName = "insert_lance")
    void inserirEmpresa(
            @Param("e_cnpj") String cnpj,
            @Param("e_nome") String nome,
            @Param("e_email") String email,
            @Param("e_senha") String senha,
            @Param("e_telefone") String telefone
    );

}
