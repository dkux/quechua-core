package fi.uba.quechua.web.rest;

import com.codahale.metrics.annotation.Timed;
import fi.uba.quechua.domain.AlumnoCarrera;
import fi.uba.quechua.service.AlumnoCarreraService;
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
 * REST controller for managing AlumnoCarrera.
 */
@RestController
@RequestMapping("/api")
public class AlumnoCarreraResource {

    private final Logger log = LoggerFactory.getLogger(AlumnoCarreraResource.class);

    private static final String ENTITY_NAME = "alumnoCarrera";

    private final AlumnoCarreraService alumnoCarreraService;

    public AlumnoCarreraResource(AlumnoCarreraService alumnoCarreraService) {
        this.alumnoCarreraService = alumnoCarreraService;
    }

    /**
     * POST  /alumno-carreras : Create a new alumnoCarrera.
     *
     * @param alumnoCarrera the alumnoCarrera to create
     * @return the ResponseEntity with status 201 (Created) and with body the new alumnoCarrera, or with status 400 (Bad Request) if the alumnoCarrera has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/alumno-carreras")
    @Timed
    public ResponseEntity<AlumnoCarrera> createAlumnoCarrera(@RequestBody AlumnoCarrera alumnoCarrera) throws URISyntaxException {
        log.debug("REST request to save AlumnoCarrera : {}", alumnoCarrera);
        if (alumnoCarrera.getId() != null) {
            throw new BadRequestAlertException("A new alumnoCarrera cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AlumnoCarrera result = alumnoCarreraService.save(alumnoCarrera);
        return ResponseEntity.created(new URI("/api/alumno-carreras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /alumno-carreras : Updates an existing alumnoCarrera.
     *
     * @param alumnoCarrera the alumnoCarrera to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated alumnoCarrera,
     * or with status 400 (Bad Request) if the alumnoCarrera is not valid,
     * or with status 500 (Internal Server Error) if the alumnoCarrera couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/alumno-carreras")
    @Timed
    public ResponseEntity<AlumnoCarrera> updateAlumnoCarrera(@RequestBody AlumnoCarrera alumnoCarrera) throws URISyntaxException {
        log.debug("REST request to update AlumnoCarrera : {}", alumnoCarrera);
        if (alumnoCarrera.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AlumnoCarrera result = alumnoCarreraService.save(alumnoCarrera);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, alumnoCarrera.getId().toString()))
            .body(result);
    }

    /**
     * GET  /alumno-carreras : get all the alumnoCarreras.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of alumnoCarreras in body
     */
    @GetMapping("/alumno-carreras")
    @Timed
    public List<AlumnoCarrera> getAllAlumnoCarreras() {
        log.debug("REST request to get all AlumnoCarreras");
        return alumnoCarreraService.findAll();
    }

    /**
     * GET  /alumno-carreras/:id : get the "id" alumnoCarrera.
     *
     * @param id the id of the alumnoCarrera to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the alumnoCarrera, or with status 404 (Not Found)
     */
    @GetMapping("/alumno-carreras/{id}")
    @Timed
    public ResponseEntity<AlumnoCarrera> getAlumnoCarrera(@PathVariable Long id) {
        log.debug("REST request to get AlumnoCarrera : {}", id);
        Optional<AlumnoCarrera> alumnoCarrera = alumnoCarreraService.findOne(id);
        return ResponseUtil.wrapOrNotFound(alumnoCarrera);
    }

    /**
     * DELETE  /alumno-carreras/:id : delete the "id" alumnoCarrera.
     *
     * @param id the id of the alumnoCarrera to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/alumno-carreras/{id}")
    @Timed
    public ResponseEntity<Void> deleteAlumnoCarrera(@PathVariable Long id) {
        log.debug("REST request to delete AlumnoCarrera : {}", id);
        alumnoCarreraService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
