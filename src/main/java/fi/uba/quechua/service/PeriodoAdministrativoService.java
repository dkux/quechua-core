package fi.uba.quechua.service;

import fi.uba.quechua.domain.PeriodoAdministrativo;
import fi.uba.quechua.domain.enumeration.PeriodoActividad;
import fi.uba.quechua.repository.PeriodoAdministrativoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
/**
 * Service Implementation for managing PeriodoAdministrativo.
 */
@Service
@Transactional
public class PeriodoAdministrativoService {

    private final Logger log = LoggerFactory.getLogger(PeriodoAdministrativoService.class);

    private final PeriodoAdministrativoRepository periodoAdministrativoRepository;

    public PeriodoAdministrativoService(PeriodoAdministrativoRepository periodoAdministrativoRepository) {
        this.periodoAdministrativoRepository = periodoAdministrativoRepository;
    }

    /**
     * Save a periodoAdministrativo.
     *
     * @param periodoAdministrativo the entity to save
     * @return the persisted entity
     */
    public PeriodoAdministrativo save(PeriodoAdministrativo periodoAdministrativo) {
        log.debug("Request to save PeriodoAdministrativo : {}", periodoAdministrativo);        return periodoAdministrativoRepository.save(periodoAdministrativo);
    }

    /**
     * Get all the periodoAdministrativos.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PeriodoAdministrativo> findAll() {
        log.debug("Request to get all PeriodoAdministrativos");
        return periodoAdministrativoRepository.findAll();
    }


    /**
     * Get one periodoAdministrativo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<PeriodoAdministrativo> findOne(Long id) {
        log.debug("Request to get PeriodoAdministrativo : {}", id);
        return periodoAdministrativoRepository.findById(id);
    }

    /**
     * Delete the periodoAdministrativo by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PeriodoAdministrativo : {}", id);
        periodoAdministrativoRepository.deleteById(id);
    }

    public List<PeriodoActividad> getActividadesDisponibles() {
        LocalDate today = LocalDate.now();
        log.debug("Request to get Actividades en fecha : {}", today);
        return periodoAdministrativoRepository.selectActividadesDisponiblesEnFecha(today);
    }
}
