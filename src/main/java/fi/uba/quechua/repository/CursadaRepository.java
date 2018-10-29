package fi.uba.quechua.repository;

import fi.uba.quechua.domain.Alumno;
import fi.uba.quechua.domain.Cursada;
import fi.uba.quechua.domain.Curso;
import fi.uba.quechua.domain.enumeration.CursadaEstado;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the Cursada entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CursadaRepository extends JpaRepository<Cursada, Long> {

    List<Cursada> findAllByAlumnoAndEstado(Alumno alumno, CursadaEstado estado);

    List<Cursada> findAllByAlumnoAndEstadoIn(Alumno alumno, List<CursadaEstado> estado);

    Optional<Cursada> findCursadaByAlumnoAndCursoAndEstado(Alumno alumno, Curso curso, CursadaEstado estado);

}
