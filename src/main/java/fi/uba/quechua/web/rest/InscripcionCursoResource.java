package fi.uba.quechua.web.rest;

import com.codahale.metrics.annotation.Timed;
import fi.uba.quechua.domain.InscripcionCurso;
import fi.uba.quechua.service.InscripcionCursoService;
import fi.uba.quechua.web.rest.errors.BadRequestAlertException;
import fi.uba.quechua.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing InscripcionCurso.
 */
@RestController
@RequestMapping("/api")
public class InscripcionCursoResource {

    private final Logger log = LoggerFactory.getLogger(InscripcionCursoResource.class);

    private static final String ENTITY_NAME = "inscripcionCurso";

    private final InscripcionCursoService inscripcionCursoService;

    public InscripcionCursoResource(InscripcionCursoService inscripcionCursoService) {
        this.inscripcionCursoService = inscripcionCursoService;
    }

    /**
     * POST  /inscripcion-cursos : Create a new inscripcionCurso.
     *
     * @param inscripcionCurso the inscripcionCurso to create
     * @return the ResponseEntity with status 201 (Created) and with body the new inscripcionCurso, or with status 400 (Bad Request) if the inscripcionCurso has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/inscripcion-cursos")
    @Timed
    public ResponseEntity<InscripcionCurso> createInscripcionCurso(@RequestBody InscripcionCurso inscripcionCurso) throws URISyntaxException {
        log.debug("REST request to save InscripcionCurso : {}", inscripcionCurso);
        if (inscripcionCurso.getId() != null) {
            throw new BadRequestAlertException("A new inscripcionCurso cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InscripcionCurso result = inscripcionCursoService.save(inscripcionCurso);
        return ResponseEntity.created(new URI("/api/inscripcion-cursos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /inscripcion-cursos : Updates an existing inscripcionCurso.
     *
     * @param inscripcionCurso the inscripcionCurso to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated inscripcionCurso,
     * or with status 400 (Bad Request) if the inscripcionCurso is not valid,
     * or with status 500 (Internal Server Error) if the inscripcionCurso couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/inscripcion-cursos")
    @Timed
    public ResponseEntity<InscripcionCurso> updateInscripcionCurso(@RequestBody InscripcionCurso inscripcionCurso) throws URISyntaxException {
        log.debug("REST request to update InscripcionCurso : {}", inscripcionCurso);
        if (inscripcionCurso.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InscripcionCurso result = inscripcionCursoService.save(inscripcionCurso);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, inscripcionCurso.getId().toString()))
            .body(result);
    }

    /**
     * GET  /inscripcion-cursos : get all the inscripcionCursos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of inscripcionCursos in body
     */
    @GetMapping("/inscripcion-cursos")
    @Timed
    public List<InscripcionCurso> getAllInscripcionCursos() {
        log.debug("REST request to get all InscripcionCursos");
        return inscripcionCursoService.findAll();
    }

    /**
     * GET  /inscripcion-cursos/:id : get the "id" inscripcionCurso.
     *
     * @param id the id of the inscripcionCurso to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the inscripcionCurso, or with status 404 (Not Found)
     */
    @GetMapping("/inscripcion-cursos/{id}")
    @Timed
    public ResponseEntity<InscripcionCurso> getInscripcionCurso(@PathVariable Long id) {
        log.debug("REST request to get InscripcionCurso : {}", id);
        Optional<InscripcionCurso> inscripcionCurso = inscripcionCursoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(inscripcionCurso);
    }

    /**
     * DELETE  /inscripcion-cursos/:id : delete the "id" inscripcionCurso.
     *
     * @param id the id of the inscripcionCurso to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/inscripcion-cursos/{id}")
    @Timed
    public ResponseEntity<Void> deleteInscripcionCurso(@PathVariable Long id) {
        log.debug("REST request to delete InscripcionCurso : {}", id);
        inscripcionCursoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}