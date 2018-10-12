package fi.uba.quechua.repository;

import fi.uba.quechua.domain.Carrera;
import fi.uba.quechua.domain.Materia;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the Materia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MateriaRepository extends JpaRepository<Materia, Long> {

    @Query("SELECT m FROM Materia m WHERE m.carrera = :carrera AND m.nombre LIKE %:query%")
    List<Materia> fidByFilter(@Param("carrera")Carrera carrera, @Param("query")Optional<String> query);

    List<Materia> findAllByCarreraAndNombreStartingWith(Carrera carrera, String query);
}
