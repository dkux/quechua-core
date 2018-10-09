package fi.uba.quechua.service;

import fi.uba.quechua.domain.Materia;
import fi.uba.quechua.repository.MateriaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
/**
 * Service Implementation for managing Materia.
 */
@Service
@Transactional
public class MateriaService {

    private final Logger log = LoggerFactory.getLogger(MateriaService.class);

    private final MateriaRepository materiaRepository;

    public MateriaService(MateriaRepository materiaRepository) {
        this.materiaRepository = materiaRepository;
    }

    /**
     * Save a materia.
     *
     * @param materia the entity to save
     * @return the persisted entity
     */
    public Materia save(Materia materia) {
        log.debug("Request to save Materia : {}", materia);        return materiaRepository.save(materia);
    }

    /**
     * Get all the materias.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Materia> findAll() {
        log.debug("Request to get all Materias");
        return materiaRepository.findAll();
    }


    /**
     * Get one materia by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Materia> findOne(Long id) {
        log.debug("Request to get Materia : {}", id);
        return materiaRepository.findById(id);
    }

    /**
     * Delete the materia by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Materia : {}", id);
        materiaRepository.deleteById(id);
    }
}
