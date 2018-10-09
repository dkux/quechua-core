package fi.uba.quechua.service;

import fi.uba.quechua.domain.Cursada;
import fi.uba.quechua.repository.CursadaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
/**
 * Service Implementation for managing Cursada.
 */
@Service
@Transactional
public class CursadaService {

    private final Logger log = LoggerFactory.getLogger(CursadaService.class);

    private final CursadaRepository cursadaRepository;

    public CursadaService(CursadaRepository cursadaRepository) {
        this.cursadaRepository = cursadaRepository;
    }

    /**
     * Save a cursada.
     *
     * @param cursada the entity to save
     * @return the persisted entity
     */
    public Cursada save(Cursada cursada) {
        log.debug("Request to save Cursada : {}", cursada);        return cursadaRepository.save(cursada);
    }

    /**
     * Get all the cursadas.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Cursada> findAll() {
        log.debug("Request to get all Cursadas");
        return cursadaRepository.findAll();
    }


    /**
     * Get one cursada by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Cursada> findOne(Long id) {
        log.debug("Request to get Cursada : {}", id);
        return cursadaRepository.findById(id);
    }

    /**
     * Delete the cursada by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Cursada : {}", id);
        cursadaRepository.deleteById(id);
    }
}