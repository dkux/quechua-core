package fi.uba.quechua.repository;

import fi.uba.quechua.domain.Prioridad;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Prioridad entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrioridadRepository extends JpaRepository<Prioridad, Long>, JpaSpecificationExecutor<Prioridad> {

}
