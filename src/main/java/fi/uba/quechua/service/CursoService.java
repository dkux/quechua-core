package fi.uba.quechua.service;

import fi.uba.quechua.domain.Curso;
import fi.uba.quechua.domain.Materia;
import fi.uba.quechua.domain.Profesor;
import fi.uba.quechua.domain.enumeration.CursoEstado;
import fi.uba.quechua.repository.CursoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
/**
 * Service Implementation for managing Curso.
 */
@Service
@Transactional
public class CursoService {

    private final Logger log = LoggerFactory.getLogger(CursoService.class);

    private final CursoRepository cursoRepository;

    public CursoService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    /**
     * Save a curso.
     *
     * @param curso the entity to save
     * @return the persisted entity
     */
    public Curso save(Curso curso) {
        log.debug("Request to save Curso : {}", curso);
        return cursoRepository.save(curso);
    }

    /**
     * Get all the cursos.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Curso> findAll() {
        log.debug("Request to get all Cursos");
        return cursoRepository.findAll();
    }

    /**
     * Get all the cursos.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Curso> findAllWithHorarios() {
        log.debug("Request to get all Cursos");
        List<Curso> cursos = cursoRepository.findAllByEstado(CursoEstado.ACTIVO);
        for (Curso curso: cursos) {
            curso.getHorarios().size();
        }
        return cursos;
    }

    /**
     * Get all the cursos.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Curso> findByMateriaWithHorarios(Materia materia) {
        log.debug("Request to get all Cursos");
        List<Curso> cursos = cursoRepository.findAllByMateriaAndEstado(materia, CursoEstado.ACTIVO);
        for (Curso curso: cursos) {
            curso.getHorarios().size();
        }
        return cursos;
    }

    /**
     * Get one curso by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Curso> findOne(Long id) {
        log.debug("Request to get Curso : {}", id);
        return cursoRepository.findById(id);
    }

    /**
     * Get one curso by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Curso> findOneWithHorarios(Long id) {
        log.debug("Request to get Curso : {}", id);
        Optional<Curso> curso = cursoRepository.findById(id);
        if (curso.isPresent()) {
            curso.get().getHorarios().size();
        }
        return  curso;
    }

    /**
     * Delete the curso by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Curso : {}", id);
        cursoRepository.deleteById(id);
    }

    /**
     * Get all the cursos by Profesor.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Curso> findByProfesor(Profesor profesor) {
        log.debug("Request to get all Cursos by profesor {}", profesor.getId());
        List<Curso> cursos = cursoRepository.findAllByProfesorAndEstadoOrderById(profesor, CursoEstado.ACTIVO);
        for (Curso curso: cursos) {
            curso.getHorarios().size();
        }
        return cursos;
    }
}
