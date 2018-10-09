package fi.uba.quechua.repository;

import fi.uba.quechua.domain.PeriodoAdministrativo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PeriodoAdministrativo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PeriodoAdministrativoRepository extends JpaRepository<PeriodoAdministrativo, Long> {

}
