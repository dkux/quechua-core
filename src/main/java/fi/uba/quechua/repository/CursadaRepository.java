package fi.uba.quechua.repository;

import fi.uba.quechua.domain.Cursada;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Cursada entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CursadaRepository extends JpaRepository<Cursada, Long> {

}
