package fi.uba.quechua.repository;

import fi.uba.quechua.domain.Alumno;
import fi.uba.quechua.domain.Curso;
import fi.uba.quechua.domain.InscripcionCurso;
import fi.uba.quechua.domain.enumeration.InscripcionCursoEstado;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the InscripcionCurso entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InscripcionCursoRepository extends JpaRepository<InscripcionCurso, Long> {

    Optional<InscripcionCurso> findByCursoAndAlumnoAndEstadoNot(Curso curso, Alumno alumno, InscripcionCursoEstado estado);

    List<InscripcionCurso> findAllByCursoAndEstadoNot(Curso curso, InscripcionCursoEstado estado);

    List<InscripcionCurso> findAllByCursoAndEstado(Curso curso, InscripcionCursoEstado estado);

    @Query("SELECT i FROM InscripcionCurso i " +
        "LEFT JOIN i.alumno a "+
        "WHERE i.curso = :curso and i.estado <> :eliminada "+
        "ORDER BY i.estado DESC, a.apellido ASC, a.nombre ASC ")
    List<InscripcionCurso> findAllNoEliminadasByCursoOrderByAlumnoNombre(@Param("curso") Curso curso , @Param("eliminada") InscripcionCursoEstado eliminada);

    List<InscripcionCurso> findByEstadoNot(InscripcionCursoEstado estado);
}
