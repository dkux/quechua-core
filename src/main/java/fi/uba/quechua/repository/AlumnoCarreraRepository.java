package fi.uba.quechua.repository;

import fi.uba.quechua.domain.AlumnoCarrera;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AlumnoCarrera entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AlumnoCarreraRepository extends JpaRepository<AlumnoCarrera, Long> {

}
