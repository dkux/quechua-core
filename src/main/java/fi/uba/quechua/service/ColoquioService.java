package fi.uba.quechua.service;

import fi.uba.quechua.domain.Coloquio;
import fi.uba.quechua.domain.Curso;
import fi.uba.quechua.domain.Periodo;
import fi.uba.quechua.domain.enumeration.ColoquioEstado;
import fi.uba.quechua.domain.enumeration.InscripcionColoquioEstado;
import fi.uba.quechua.repository.ColoquioRepository;
import fi.uba.quechua.repository.InscripcionColoquioRepository;
import fi.uba.quechua.repository.PeriodoRepository;
import fi.uba.quechua.service.dto.ColoquioDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
/**
 * Service Implementation for managing Coloquio.
 */
@Service
@Transactional
public class ColoquioService {

    private final Logger log = LoggerFactory.getLogger(ColoquioService.class);

    private final ColoquioRepository coloquioRepository;

    private final PeriodoRepository periodoRepository;

    private final InscripcionColoquioRepository inscripcionColoquioRepository;

    public ColoquioService(ColoquioRepository coloquioRepository, PeriodoRepository periodoRepository,
                           InscripcionColoquioRepository inscripcionColoquioRepository) {
        this.coloquioRepository = coloquioRepository;
        this.periodoRepository = periodoRepository;
        this.inscripcionColoquioRepository = inscripcionColoquioRepository;
    }

    /**
     * Save a coloquio.
     *
     * @param coloquio the entity to save
     * @return the persisted entity
     */
    public Coloquio save(Coloquio coloquio) {
        log.debug("Request to save Coloquio : {}", coloquio);
        return coloquioRepository.save(coloquio);
    }

    /**
     * Get all the coloquios.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Coloquio> findAll() {
        log.debug("Request to get all Coloquios");
        return coloquioRepository.findAll();
    }


    /**
     * Get one coloquio by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Coloquio> findOne(Long id) {
        log.debug("Request to get Coloquio : {}", id);
        return coloquioRepository.findById(id);
    }

    /**
     * Delete the coloquio by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Coloquio : {}", id);
        coloquioRepository.deleteById(id);
    }

    public List<Coloquio> findAllByCursoParaInscribirse(Curso curso) {
        log.debug("Request to get Coloquios by curso {} para inscribirse", curso.getId());
        Optional<Periodo> periodo = periodoRepository.findPeriodoActual();
        if (!periodo.isPresent()) {
            return new LinkedList<>();
        }
        LocalDate fecha = LocalDate.now().plusDays(2);
        return coloquioRepository.findAllByCursoAndPeriodoAndFechaGreaterThanEqualAndEstadoOrderByFechaDesc(curso, periodo.get(), fecha, ColoquioEstado.ACTIVO);
    }

    public List<ColoquioDTO> findAllColoquiosDTOByCurso(Curso curso) {
        log.debug("Request to get Coloquios by curso {}", curso.getId());
        Optional<Periodo> periodo = periodoRepository.findPeriodoActual();
        if (!periodo.isPresent()) {
            return new LinkedList<>();
        }
        List<Coloquio> coloquios = coloquioRepository.findAllByCursoAndPeriodoAndEstadoOrderByFechaDesc(curso, periodo.get(), ColoquioEstado.ACTIVO);
        List<ColoquioDTO> coloquiosDTO = new LinkedList<>();
        for (Coloquio coloquio: coloquios) {
            Integer inscripciones = inscripcionColoquioRepository.findAllByColoquioAndEstado(coloquio, InscripcionColoquioEstado.ACTIVA).size();
            coloquiosDTO.add(new ColoquioDTO(coloquio, inscripciones));
        }
        return coloquiosDTO;
    }

    public List<ColoquioDTO> findAllColoquiosDTO() {
        log.debug("Request to get Coloquios  {}");
        Optional<Periodo> periodo = periodoRepository.findPeriodoActual();
        if (!periodo.isPresent()) {
            return new LinkedList<>();
        }
        List<Coloquio> coloquios = coloquioRepository.findAll();
        List<ColoquioDTO> coloquiosDTO = new LinkedList<>();
        for (Coloquio coloquio: coloquios) {
            Integer inscripciones = inscripcionColoquioRepository.findAllByColoquioAndEstado(coloquio, InscripcionColoquioEstado.ACTIVA).size();
            coloquiosDTO.add(new ColoquioDTO(coloquio, inscripciones));
        }
        return coloquiosDTO;
    }

    public void eliminar(Coloquio coloquio) {
        coloquio.setEstado(ColoquioEstado.ELIMINADO);
        coloquioRepository.save(coloquio);
        //@TODO Notificar a los alumnos inscriptos
    }
}
