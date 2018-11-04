package fi.uba.quechua.service;

import fi.uba.quechua.domain.Alumno;
import fi.uba.quechua.domain.Curso;
import fi.uba.quechua.domain.InscripcionCurso;
import fi.uba.quechua.domain.enumeration.InscripcionCursoEstado;
import fi.uba.quechua.repository.InscripcionCursoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
/**
 * Service Implementation for managing InscripcionCurso.
 */
@Service
@Transactional
public class InscripcionCursoService {

    private final Logger log = LoggerFactory.getLogger(InscripcionCursoService.class);

    private final InscripcionCursoRepository inscripcionCursoRepository;

    public InscripcionCursoService(InscripcionCursoRepository inscripcionCursoRepository) {
        this.inscripcionCursoRepository = inscripcionCursoRepository;
    }

    /**
     * Save a inscripcionCurso.
     *
     * @param inscripcionCurso the entity to save
     * @return the persisted entity
     */
    public InscripcionCurso save(InscripcionCurso inscripcionCurso) {
        log.debug("Request to save InscripcionCurso : {}", inscripcionCurso);        return inscripcionCursoRepository.save(inscripcionCurso);
    }

    /**
     * Get all the inscripcionCursos.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<InscripcionCurso> findAll() {
        log.debug("Request to get all InscripcionCursos");
        return inscripcionCursoRepository.findAll();
    }


    /**
     * Get one inscripcionCurso by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<InscripcionCurso> findOne(Long id) {
        log.debug("Request to get InscripcionCurso : {}", id);
        return inscripcionCursoRepository.findById(id);
    }

    /**
     * Delete the inscripcionCurso by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete InscripcionCurso : {}", id);
        inscripcionCursoRepository.deleteById(id);
    }

    public List<InscripcionCurso> findByCurso(Curso curso) {
        log.debug("Request to get InscripcionCursos by Curso {}", curso.getId());
        return inscripcionCursoRepository.findAllNoEliminadasByCursoOrderByAlumnoNombre(curso, InscripcionCursoEstado.ELIMINADA);
    }

    public Optional<InscripcionCurso> findByCursoAndAlumnoNoEliminada(Curso curso, Alumno alumno) {
        log.debug("Request to get InscripcionCursos by Curso {} and Alumno {}", curso.getId(), alumno.getId());
        return inscripcionCursoRepository.findByCursoAndAlumnoAndEstadoNot(curso, alumno, InscripcionCursoEstado.ELIMINADA);
    }

    public List<InscripcionCurso> findAllRegularesByCurso(Curso curso) {
        log.debug("Request to get InscripcionCursos by Curso {}", curso.getId());
        return inscripcionCursoRepository.findAllByCursoAndEstado(curso, InscripcionCursoEstado.REGULAR);
    }

    /**
     * Get all the inscripcionCurso by alumno.
     *
     * @param id the id of the entity
     */
    public List<InscripcionCurso> findAllActivasByAlumno(Alumno alumno) {
        log.debug("Request to get InscripcionCursos by Alumno {}", alumno.getId());
        return inscripcionCursoRepository.findAllByAlumnoAndEstadoNot(alumno, InscripcionCursoEstado.ELIMINADA);
    }
}
