package recoope.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import recoope.api.domain.entities.Empresa;

@Repository
public interface IEmpresaRepository extends JpaRepository<Empresa, Long> {
}