package fi.uba.quechua.repository;

import fi.uba.quechua.domain.Alumno;
import fi.uba.quechua.domain.Coloquio;
import fi.uba.quechua.domain.Cursada;
import fi.uba.quechua.domain.InscripcionColoquio;
import fi.uba.quechua.domain.enumeration.InscripcionColoquioEstado;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the InscripcionColoquio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InscripcionColoquioRepository extends JpaRepository<InscripcionColoquio, Long> {

    Optional<InscripcionColoquio> findByColoquioAndAlumnoAndEstado(Coloquio coloquio, Alumno alumno, InscripcionColoquioEstado estado);

    List<InscripcionColoquio> findAllByAlumnoAndEstado(Alumno alumno, InscripcionColoquioEstado estado);

    List<InscripcionColoquio> findAllByCursadaAndEstado(Cursada cursada, InscripcionColoquioEstado estado);

    List<InscripcionColoquio> findAllByColoquioAndEstado(Coloquio coloquio, InscripcionColoquioEstado estado);
}
