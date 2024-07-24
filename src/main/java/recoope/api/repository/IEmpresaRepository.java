package recoope.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import recoope.api.domain.entities.Empresa;

import java.util.Optional;

@Repository
public interface IEmpresaRepository extends JpaRepository<Empresa, Long> {
    @Query("SELECT MAX(e.idEmpresa) FROM Empresa e")
    Long lastId();

    @Query("SELECT e FROM Empresa e WHERE (e.cnpjEmpresa = ?1 OR e.emailEmpresa = ?1) AND e.senhaEmpresa = ?2")
    Optional<Empresa> login(String cnpjOuEmail, String senha);
}
