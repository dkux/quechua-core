package fi.uba.quechua.service;

import fi.uba.quechua.domain.Periodo;
import fi.uba.quechua.repository.PeriodoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
/**
 * Service Implementation for managing Periodo.
 */
@Service
@Transactional
public class PeriodoService {

    private final Logger log = LoggerFactory.getLogger(PeriodoService.class);

    private final PeriodoRepository periodoRepository;

    public PeriodoService(PeriodoRepository periodoRepository) {
        this.periodoRepository = periodoRepository;
    }

    /**
     * Save a periodo.
     *
     * @param periodo the entity to save
     * @return the persisted entity
     */
    public Periodo save(Periodo periodo) {
        log.debug("Request to save Periodo : {}", periodo);        return periodoRepository.save(periodo);
    }

    /**
     * Get all the periodos.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Periodo> findAll() {
        log.debug("Request to get all Periodos");
        return periodoRepository.findAll();
    }


    /**
     * Get one periodo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Periodo> findOne(Long id) {
        log.debug("Request to get Periodo : {}", id);
        return periodoRepository.findById(id);
    }

    /**
     * Delete the periodo by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Periodo : {}", id);
        periodoRepository.deleteById(id);
    }
}
