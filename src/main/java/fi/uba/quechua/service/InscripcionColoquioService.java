package fi.uba.quechua.service;

import fi.uba.quechua.domain.InscripcionColoquio;
import fi.uba.quechua.repository.InscripcionColoquioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
/**
 * Service Implementation for managing InscripcionColoquio.
 */
@Service
@Transactional
public class InscripcionColoquioService {

    private final Logger log = LoggerFactory.getLogger(InscripcionColoquioService.class);

    private final InscripcionColoquioRepository inscripcionColoquioRepository;

    public InscripcionColoquioService(InscripcionColoquioRepository inscripcionColoquioRepository) {
        this.inscripcionColoquioRepository = inscripcionColoquioRepository;
    }

    /**
     * Save a inscripcionColoquio.
     *
     * @param inscripcionColoquio the entity to save
     * @return the persisted entity
     */
    public InscripcionColoquio save(InscripcionColoquio inscripcionColoquio) {
        log.debug("Request to save InscripcionColoquio : {}", inscripcionColoquio);        return inscripcionColoquioRepository.save(inscripcionColoquio);
    }

    /**
     * Get all the inscripcionColoquios.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<InscripcionColoquio> findAll() {
        log.debug("Request to get all InscripcionColoquios");
        return inscripcionColoquioRepository.findAll();
    }


    /**
     * Get one inscripcionColoquio by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<InscripcionColoquio> findOne(Long id) {
        log.debug("Request to get InscripcionColoquio : {}", id);
        return inscripcionColoquioRepository.findById(id);
    }

    /**
     * Delete the inscripcionColoquio by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete InscripcionColoquio : {}", id);
        inscripcionColoquioRepository.deleteById(id);
    }
}
