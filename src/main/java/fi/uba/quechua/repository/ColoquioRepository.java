package fi.uba.quechua.repository;

import fi.uba.quechua.domain.Coloquio;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Coloquio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ColoquioRepository extends JpaRepository<Coloquio, Long> {

}
