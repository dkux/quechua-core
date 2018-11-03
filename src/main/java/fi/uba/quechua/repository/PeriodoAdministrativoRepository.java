package fi.uba.quechua.repository;

import fi.uba.quechua.domain.PeriodoAdministrativo;
import fi.uba.quechua.domain.enumeration.PeriodoActividad;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


/**
 * Spring Data  repository for the PeriodoAdministrativo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PeriodoAdministrativoRepository extends JpaRepository<PeriodoAdministrativo, Long> {

    @Query("SELECT p.actividad FROM PeriodoAdministrativo p WHERE :fecha BETWEEN p.fechaInicio AND p.fechaFin")
    List<PeriodoActividad> selectActividadesDisponiblesEnFecha(@Param("fecha")LocalDate fecha);
}
