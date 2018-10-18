package fi.uba.quechua.repository;

import fi.uba.quechua.domain.Periodo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the Periodo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PeriodoRepository extends JpaRepository<Periodo, Long> {

    @Query(value = "SELECT * FROM Periodo p WHERE p.anio = YEAR(CURRENT_DATE) AND p.cuatrimestre = IF (MONTH(CURRENT_DATE) BETWEEN 6 AND 8, 'PRIMERO', 'SEGUNDO');", nativeQuery = true)
    Optional<Periodo> findPeriodoActual();
}
