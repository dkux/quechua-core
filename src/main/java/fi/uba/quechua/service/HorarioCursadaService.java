package fi.uba.quechua.service;

import fi.uba.quechua.domain.HorarioCursada;
import fi.uba.quechua.repository.HorarioCursadaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
/**
 * Service Implementation for managing HorarioCursada.
 */
@Service
@Transactional
public class HorarioCursadaService {

    private final Logger log = LoggerFactory.getLogger(HorarioCursadaService.class);

    private final HorarioCursadaRepository horarioCursadaRepository;

    public HorarioCursadaService(HorarioCursadaRepository horarioCursadaRepository) {
        this.horarioCursadaRepository = horarioCursadaRepository;
    }

    /**
     * Save a horarioCursada.
     *
     * @param horarioCursada the entity to save
     * @return the persisted entity
     */
    public HorarioCursada save(HorarioCursada horarioCursada) {
        log.debug("Request to save HorarioCursada : {}", horarioCursada);        return horarioCursadaRepository.save(horarioCursada);
    }

    /**
     * Get all the horarioCursadas.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<HorarioCursada> findAll() {
        log.debug("Request to get all HorarioCursadas");
        return horarioCursadaRepository.findAll();
    }


    /**
     * Get one horarioCursada by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<HorarioCursada> findOne(Long id) {
        log.debug("Request to get HorarioCursada : {}", id);
        return horarioCursadaRepository.findById(id);
    }

    /**
     * Delete the horarioCursada by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete HorarioCursada : {}", id);
        horarioCursadaRepository.deleteById(id);
    }
}
