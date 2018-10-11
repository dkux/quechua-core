package fi.uba.quechua.repository;

import fi.uba.quechua.domain.Alumno;
import fi.uba.quechua.domain.AlumnoCarrera;
import fi.uba.quechua.domain.Carrera;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the AlumnoCarrera entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AlumnoCarreraRepository extends JpaRepository<AlumnoCarrera, Long> {

    @Query("SELECT ac.carrera FROM AlumnoCarrera ac WHERE ac.alumno = :alumno")
    List<Carrera> findCarrerasByAlumno(@Param("alumno")Alumno alumno);
}
