package recoope.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import recoope.api.domain.entities.Empresa;

import java.util.Date;
import java.util.Optional;

@Repository
public interface IEmpresaRepository extends JpaRepository<Empresa, String> {
    @Query("SELECT e FROM Empresa e WHERE (e.cnpjEmpresa = ?1 OR e.emailEmpresa = ?1) AND e.senhaEmpresa = ?2")
    Optional<Empresa> login(String cnpjOuEmail, String senha);
    @Query("SELECT e FROM Empresa e WHERE (e.emailEmpresa = ?1 OR e.telefoneEmpresa = ?1)")
    Optional<Empresa> findByTelefoneOuEmail(String telefoneOuEmail);
    @Procedure(procedureName = "insert_empresa")
    void inserir(
            @Param("e_cnpj") String cnpj,
            @Param("e_nome") String nome,
            @Param("e_email") String email,
            @Param("e_senha") String senha,
            @Param("e_telefone") String telefone,
            @Param("e_registro") Date dataRegistro
            );
}
