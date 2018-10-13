package fi.uba.quechua.repository;

import fi.uba.quechua.domain.Alumno;
import fi.uba.quechua.domain.Cursada;
import fi.uba.quechua.domain.enumeration.CursadaEstado;
import fi.uba.quechua.domain.enumeration.EstadoCursada;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Cursada entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CursadaRepository extends JpaRepository<Cursada, Long> {

    List<Cursada> findAllByAlumnoAndEstado(Alumno alumno, EstadoCursada estado);
}
