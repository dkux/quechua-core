package fi.uba.quechua.service;

import fi.uba.quechua.domain.Alumno;
import fi.uba.quechua.domain.Cursada;
import fi.uba.quechua.domain.InscripcionCurso;
import fi.uba.quechua.domain.Periodo;
import fi.uba.quechua.domain.enumeration.EstadoCursada;
import fi.uba.quechua.domain.enumeration.InscripcionCursoEstado;
import fi.uba.quechua.repository.CursadaRepository;
import fi.uba.quechua.repository.InscripcionCursoRepository;
import fi.uba.quechua.repository.PeriodoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Arrays;
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

    private final InscripcionCursoRepository inscripcionCursoRepository;

    private final PeriodoRepository periodoRepository;

    public CursadaService(CursadaRepository cursadaRepository, InscripcionCursoRepository inscripcionCursoRepository,
                          PeriodoRepository periodoRepository) {
        this.cursadaRepository = cursadaRepository;
        this.inscripcionCursoRepository = inscripcionCursoRepository;
        this.periodoRepository = periodoRepository;
    }

    /**
     * Save a cursada.
     *
     * @param cursada the entity to save
     * @return the persisted entity
     */
    public Cursada save(Cursada cursada) {
        log.debug("Request to save Cursada : {}", cursada);
        return cursadaRepository.save(cursada);
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

    public List<Cursada> findCursadasActivasByAlumno(Alumno alumno) {
        List<EstadoCursada> estadoCursadas = Arrays.asList(EstadoCursada.ACTIVA, EstadoCursada.FINAL_PENDIENTE);
        List<Cursada> cursadas = cursadaRepository.findAllByAlumnoAndEstadoIn(alumno, estadoCursadas);
        for (Cursada cursada: cursadas) {
            cursada.getCurso().getHorarios().size();
        }
        return cursadas;
    }

    public void iniciarCursadas() {
        List<InscripcionCurso> inscripcionesCursos = inscripcionCursoRepository.findByEstadoNot(InscripcionCursoEstado.ELIMINADA);
        Optional<Periodo> periodo = periodoRepository.findPeriodoActual();
        if (!periodo.isPresent())
            return;
        for (InscripcionCurso inscripcionCurso: inscripcionesCursos) {
            Cursada cursada = new Cursada();
            cursada.setAlumno(inscripcionCurso.getAlumno());
            cursada.setCurso(inscripcionCurso.getCurso());
            cursada.setEstado(EstadoCursada.ACTIVA);
            cursada.setPeriodo(periodo.get());
            cursadaRepository.save(cursada);
        }
    }
}
