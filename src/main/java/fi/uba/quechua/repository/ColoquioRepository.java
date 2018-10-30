package fi.uba.quechua.repository;

import fi.uba.quechua.domain.Coloquio;
import fi.uba.quechua.domain.Curso;
import fi.uba.quechua.domain.Periodo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


/**
 * Spring Data  repository for the Coloquio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ColoquioRepository extends JpaRepository<Coloquio, Long> {

    List<Coloquio> findAllByCursoAndPeriodoAndFechaGreaterThanEqualOrderByFechaDesc(Curso curso, Periodo periodo, LocalDate fecha);

    List<Coloquio> findAllByCursoAndPeriodoOrderByFechaDesc(Curso curso, Periodo periodo);
}
