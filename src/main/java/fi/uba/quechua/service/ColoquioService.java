package fi.uba.quechua.service;

import fi.uba.quechua.domain.Coloquio;
import fi.uba.quechua.repository.ColoquioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
/**
 * Service Implementation for managing Coloquio.
 */
@Service
@Transactional
public class ColoquioService {

    private final Logger log = LoggerFactory.getLogger(ColoquioService.class);

    private final ColoquioRepository coloquioRepository;

    public ColoquioService(ColoquioRepository coloquioRepository) {
        this.coloquioRepository = coloquioRepository;
    }

    /**
     * Save a coloquio.
     *
     * @param coloquio the entity to save
     * @return the persisted entity
     */
    public Coloquio save(Coloquio coloquio) {
        log.debug("Request to save Coloquio : {}", coloquio);        return coloquioRepository.save(coloquio);
    }

    /**
     * Get all the coloquios.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Coloquio> findAll() {
        log.debug("Request to get all Coloquios");
        return coloquioRepository.findAll();
    }


    /**
     * Get one coloquio by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Coloquio> findOne(Long id) {
        log.debug("Request to get Coloquio : {}", id);
        return coloquioRepository.findById(id);
    }

    /**
     * Delete the coloquio by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Coloquio : {}", id);
        coloquioRepository.deleteById(id);
    }
}
