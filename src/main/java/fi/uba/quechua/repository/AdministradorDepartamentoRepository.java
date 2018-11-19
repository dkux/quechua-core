package fi.uba.quechua.repository;

import fi.uba.quechua.domain.AdministradorDepartamento;
import fi.uba.quechua.domain.Profesor;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the AdministradorDepartamento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdministradorDepartamentoRepository extends JpaRepository<AdministradorDepartamento, Long> {

    Optional<AdministradorDepartamento> findByUserId(Long id);

}
