package fi.uba.quechua.service;

import fi.uba.quechua.domain.Coloquio;
import fi.uba.quechua.domain.Curso;
import fi.uba.quechua.domain.Periodo;
import fi.uba.quechua.repository.ColoquioRepository;
import fi.uba.quechua.repository.PeriodoRepository;
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

    public ColoquioService(ColoquioRepository coloquioRepository, PeriodoRepository periodoRepository) {
        this.coloquioRepository = coloquioRepository;
        this.periodoRepository = periodoRepository;
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

    public List<Coloquio> findAllByCurso(Curso curso) {
        log.debug("Request to get Coloquios by curso {}", curso.getId());
        Optional<Periodo> periodo = periodoRepository.findPeriodoActual();
        if (!periodo.isPresent()) {
            return new LinkedList<>();
        }
        LocalDate fecha = LocalDate.now().plusDays(2);
        return coloquioRepository.findAllByCursoAndPeriodoAndFechaGreaterThanEqual(curso, periodo.get(), fecha);
    }
}
