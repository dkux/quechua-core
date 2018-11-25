package fi.uba.quechua.repository;

import fi.uba.quechua.domain.*;
import fi.uba.quechua.domain.enumeration.CursoEstado;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Curso entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

    List<Curso> findAllByEstado(CursoEstado estado);

    List<Curso> findAllByMateriaAndPeriodoAndEstado(Materia materia, Periodo periodo, CursoEstado estado);

    List<Curso> findAllByProfesorAndEstadoOrderById(Profesor profesor, CursoEstado estado);

    @Query("SELECT c FROM Curso c LEFT JOIN c.materia m LEFT JOIN m.departamento d WHERE d = :departamento")
    List<Curso> findAllByDepartamento(@Param("departamento")Departamento departamento);
}
