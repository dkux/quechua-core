package fi.uba.quechua.service;

import fi.uba.quechua.domain.Alumno;
import fi.uba.quechua.repository.AlumnoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
/**
 * Service Implementation for managing Alumno.
 */
@Service
@Transactional
public class AlumnoService {

    private final Logger log = LoggerFactory.getLogger(AlumnoService.class);

    private final AlumnoRepository alumnoRepository;

    public AlumnoService(AlumnoRepository alumnoRepository) {
        this.alumnoRepository = alumnoRepository;
    }

    /**
     * Save a alumno.
     *
     * @param alumno the entity to save
     * @return the persisted entity
     */
    public Alumno save(Alumno alumno) {
        log.debug("Request to save Alumno : {}", alumno);        return alumnoRepository.save(alumno);
    }

    /**
     * Get all the alumnos.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Alumno> findAll() {
        log.debug("Request to get all Alumnos");
        return alumnoRepository.findAll();
    }


    /**
     * Get one alumno by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Alumno> findOne(Long id) {
        log.debug("Request to get Alumno : {}", id);
        return alumnoRepository.findById(id);
    }

    /**
     * Get one alumno by userId.
     *
     * @param userId the id of the user
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Alumno> findOneByUserId(Long userId) {
        log.debug("Request to get Alumno by userId : {}", userId);
        return alumnoRepository.findByUserId(userId);
    }

    /**
     * Delete the alumno by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Alumno : {}", id);
        alumnoRepository.deleteById(id);
    }
}
