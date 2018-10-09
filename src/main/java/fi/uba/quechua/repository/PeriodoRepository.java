package fi.uba.quechua.repository;

import fi.uba.quechua.domain.Periodo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Periodo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PeriodoRepository extends JpaRepository<Periodo, Long> {

}
