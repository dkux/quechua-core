package fi.uba.quechua.service;

import fi.uba.quechua.domain.Alumno;
import fi.uba.quechua.domain.AlumnoCarrera;
import fi.uba.quechua.domain.Carrera;
import fi.uba.quechua.repository.AlumnoCarreraRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
/**
 * Service Implementation for managing AlumnoCarrera.
 */
@Service
@Transactional
public class AlumnoCarreraService {

    private final Logger log = LoggerFactory.getLogger(AlumnoCarreraService.class);

    private final AlumnoCarreraRepository alumnoCarreraRepository;

    public AlumnoCarreraService(AlumnoCarreraRepository alumnoCarreraRepository) {
        this.alumnoCarreraRepository = alumnoCarreraRepository;
    }

    /**
     * Save a alumnoCarrera.
     *
     * @param alumnoCarrera the entity to save
     * @return the persisted entity
     */
    public AlumnoCarrera save(AlumnoCarrera alumnoCarrera) {
        log.debug("Request to save AlumnoCarrera : {}", alumnoCarrera);
        return alumnoCarreraRepository.save(alumnoCarrera);
    }

    /**
     * Get all the alumnoCarreras.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<AlumnoCarrera> findAll() {
        log.debug("Request to get all AlumnoCarreras");
        return alumnoCarreraRepository.findAll();
    }


    /**
     * Get one alumnoCarrera by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<AlumnoCarrera> findOne(Long id) {
        log.debug("Request to get AlumnoCarrera : {}", id);
        return alumnoCarreraRepository.findById(id);
    }

    /**
     * Delete the alumnoCarrera by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete AlumnoCarrera : {}", id);
        alumnoCarreraRepository.deleteById(id);
    }

    /**
     * Get all the Carreras by Alumno.
     *
     * @param alumno the Alumno
     * @return the list of entities
     */
    public List<Carrera> findCarrerasByAlumno(Alumno alumno) {
        log.debug("Request to get Carreras by Alumno : {}", alumno.getId());
        return alumnoCarreraRepository.findCarrerasByAlumno(alumno);
    }
}
