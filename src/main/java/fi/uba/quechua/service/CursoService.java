package fi.uba.quechua.service;

import fi.uba.quechua.domain.*;
import fi.uba.quechua.domain.enumeration.CursoEstado;
import fi.uba.quechua.repository.*;
import fi.uba.quechua.security.AuthoritiesConstants;
import fi.uba.quechua.security.SecurityUtils;
import fi.uba.quechua.service.dto.CursoDTO;
import fi.uba.quechua.web.rest.AdministradorDepartamentoResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.validation.constraints.Null;
import java.util.Iterator;
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

    private final HorarioCursadaRepository horarioCursadaRepository;

    private final UserService userService;

    private final AdministradorDepartamentoRepository administradorDepartamentoRepository;

    private final DepartamentoRepository departamentoRepository;


    public CursoService(CursoRepository cursoRepository, HorarioCursadaRepository horarioCursadaRepository,
                        UserService userService, AdministradorDepartamentoRepository administradorDepartamentoRepository,
                        DepartamentoRepository departamentoRepository) {
        this.cursoRepository = cursoRepository;
        this.horarioCursadaRepository = horarioCursadaRepository;
        this.userService = userService;
        this.administradorDepartamentoRepository = administradorDepartamentoRepository;
        this.departamentoRepository = departamentoRepository;
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
        Optional<User> user = userService.getUserWithAuthorities();
        if (user.isPresent() && SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADM_DPTO)) {
            Optional<AdministradorDepartamento> administradorDepartamento = administradorDepartamentoRepository.findByUserId(user.get().getId());
            Optional<Departamento> departamento = departamentoRepository.findById(administradorDepartamento.get().getDepartamentoId());
            return cursoRepository.findAllByDepartamento(departamento.get());
        }
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
    public List<Curso> findByMateriaWithHorariosEnPeriodo(Materia materia, Periodo periodo) {
        log.debug("Request to get all Cursos");
        List<Curso> cursos = cursoRepository.findAllByMateriaAndPeriodoAndEstado(materia, periodo, CursoEstado.ACTIVO);
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
        Optional<Curso> curso = cursoRepository.findById(id);
        if (curso.isPresent()) {
            curso.get().getHorarios().size();
        }
        return  curso;
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

    /**
     * Save a curso.
     *
     * @param cursoDTO the entity to save
     * @return the persisted entity
     */
    public Curso update(CursoDTO cursoDTO) {
        log.debug("Request to save Curso : {}", cursoDTO);
        Curso curso = cursoRepository.findById(cursoDTO.getId()).get();
        curso.setEstado(cursoDTO.getEstado());
        curso.setNumero(cursoDTO.getNumero());
        curso.setVacantes(cursoDTO.getVacantes());
        curso.getHorarios().size();
        curso.setMateria(cursoDTO.getMateria());
        curso.setPeriodo(cursoDTO.getPeriodo());
        curso.setProfesor(cursoDTO.getProfesor());


        for (HorarioCursada horario: cursoDTO.getHorarios()) {
            if (horario.getId() != null) {
                horarioCursadaRepository.save(horario);
            } else {
                horario.setCurso(curso);
                horarioCursadaRepository.save(horario);
            }
        }
        for (Iterator<HorarioCursada> i = curso.getHorarios().iterator(); i.hasNext();) {
            boolean exists = false;
            HorarioCursada horarioOriginal = i.next();
            for (HorarioCursada horario: cursoDTO.getHorarios()) {
                if (horarioOriginal.getId().equals(horario.getId())) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                horarioCursadaRepository.delete(horarioOriginal);
                i.remove();
            }
        }
        Curso cursoSaved =  cursoRepository.saveAndFlush(curso);
        return cursoSaved;
    }

    /**
     * Save a curso.
     *
     * @param cursoDTO the entity to save
     * @return the persisted entity
     */
    public Curso guardar(CursoDTO cursoDTO) {
        log.debug("Request to save Curso : {}", cursoDTO);
        Curso curso = new Curso();
        curso.setEstado(cursoDTO.getEstado());
        curso.setNumero(cursoDTO.getNumero());
        curso.setVacantes(cursoDTO.getVacantes());
        curso.getHorarios().size();
        curso.setMateria(cursoDTO.getMateria());
        curso.setPeriodo(cursoDTO.getPeriodo());
        curso.setProfesor(cursoDTO.getProfesor());
        Curso cursoSaved =  cursoRepository.saveAndFlush(curso);

        for (HorarioCursada horario: cursoDTO.getHorarios()) {
                horario.setCurso(curso);
                horarioCursadaRepository.save(horario);
        }
        return cursoSaved;
    }
}
