package fi.uba.quechua.repository;

import fi.uba.quechua.domain.HorarioCursada;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the HorarioCursada entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HorarioCursadaRepository extends JpaRepository<HorarioCursada, Long> {

}
