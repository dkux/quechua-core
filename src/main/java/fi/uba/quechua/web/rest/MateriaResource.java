package fi.uba.quechua.web.rest;

import com.codahale.metrics.annotation.Timed;
import fi.uba.quechua.domain.Carrera;
import fi.uba.quechua.domain.Materia;
import fi.uba.quechua.repository.CarreraRepository;
import fi.uba.quechua.service.MateriaService;
import fi.uba.quechua.web.rest.errors.BadRequestAlertException;
import fi.uba.quechua.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Materia.
 */
@RestController
@RequestMapping("/api")
public class MateriaResource {

    private final Logger log = LoggerFactory.getLogger(MateriaResource.class);

    private static final String ENTITY_NAME = "materia";

    private final MateriaService materiaService;

    private CarreraRepository carreraRepository;

    public MateriaResource(MateriaService materiaService, CarreraRepository carreraRepository) {
        this.materiaService = materiaService;
        this.carreraRepository = carreraRepository;
    }

    /**
     * POST  /materias : Create a new materia.
     *
     * @param materia the materia to create
     * @return the ResponseEntity with status 201 (Created) and with body the new materia, or with status 400 (Bad Request) if the materia has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/materias")
    @Timed
    public ResponseEntity<Materia> createMateria(@Valid @RequestBody Materia materia) throws URISyntaxException {
        log.debug("REST request to save Materia : {}", materia);
        if (materia.getId() != null) {
            throw new BadRequestAlertException("A new materia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Materia result = materiaService.save(materia);
        return ResponseEntity.created(new URI("/api/materias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /materias : Updates an existing materia.
     *
     * @param materia the materia to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated materia,
     * or with status 400 (Bad Request) if the materia is not valid,
     * or with status 500 (Internal Server Error) if the materia couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/materias")
    @Timed
    public ResponseEntity<Materia> updateMateria(@Valid @RequestBody Materia materia) throws URISyntaxException {
        log.debug("REST request to update Materia : {}", materia);
        if (materia.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Materia result = materiaService.save(materia);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, materia.getId().toString()))
            .body(result);
    }

    /**
     * GET  /materias/:id : get the "id" materia.
     *
     * @param id the id of the materia to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the materia, or with status 404 (Not Found)
     */
    @GetMapping("/materias/{id}")
    @Timed
    public ResponseEntity<Materia> getMateria(@PathVariable Long id) {
        log.debug("REST request to get Materia : {}", id);
        Optional<Materia> materia = materiaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(materia);
    }

    /**
     * DELETE  /materias/:id : delete the "id" materia.
     *
     * @param id the id of the materia to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/materias/{id}")
    @Timed
    public ResponseEntity<Void> deleteMateria(@PathVariable Long id) {
        log.debug("REST request to delete Materia : {}", id);
        materiaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * GET  /materias : get the materias.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of materias in body
     */
    @GetMapping("/materias")
    @Timed
    public List<Materia> getMateriasByFilter(@RequestParam(name="carrera") Long carreraId,
                                             @RequestParam(name="query", defaultValue = "") String query) {
        log.debug("REST request to get all Materias");
        Optional<Carrera> carrera = carreraRepository.findById(carreraId);
        return materiaService.findByFilter(carrera.get(), query);
    }
}
