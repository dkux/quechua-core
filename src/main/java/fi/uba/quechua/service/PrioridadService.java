package fi.uba.quechua.service;

import fi.uba.quechua.domain.Prioridad;
import fi.uba.quechua.repository.PrioridadRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
/**
 * Service Implementation for managing Prioridad.
 */
@Service
@Transactional
public class PrioridadService {

    private final Logger log = LoggerFactory.getLogger(PrioridadService.class);

    private final PrioridadRepository prioridadRepository;

    public PrioridadService(PrioridadRepository prioridadRepository) {
        this.prioridadRepository = prioridadRepository;
    }

    /**
     * Save a prioridad.
     *
     * @param prioridad the entity to save
     * @return the persisted entity
     */
    public Prioridad save(Prioridad prioridad) {
        log.debug("Request to save Prioridad : {}", prioridad);        return prioridadRepository.save(prioridad);
    }

    /**
     * Get all the prioridads.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Prioridad> findAll() {
        log.debug("Request to get all Prioridads");
        return prioridadRepository.findAll();
    }


    /**
     * Get one prioridad by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Prioridad> findOne(Long id) {
        log.debug("Request to get Prioridad : {}", id);
        return prioridadRepository.findById(id);
    }

    /**
     * Delete the prioridad by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Prioridad : {}", id);
        prioridadRepository.deleteById(id);
    }
}
