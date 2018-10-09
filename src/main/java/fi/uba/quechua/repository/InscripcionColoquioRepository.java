package fi.uba.quechua.repository;

import fi.uba.quechua.domain.InscripcionColoquio;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the InscripcionColoquio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InscripcionColoquioRepository extends JpaRepository<InscripcionColoquio, Long> {

}
