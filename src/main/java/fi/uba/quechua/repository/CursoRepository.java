package fi.uba.quechua.repository;

import fi.uba.quechua.domain.Curso;
import fi.uba.quechua.domain.Materia;
import fi.uba.quechua.domain.Profesor;
import fi.uba.quechua.domain.enumeration.CursoEstado;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Curso entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

    List<Curso> findAllByEstado(CursoEstado estado);

    List<Curso> findAllByMateriaAndEstado(Materia materia, CursoEstado estado);

    List<Curso> findAllByProfesorAndEstado(Profesor profesor, CursoEstado estado);
}
