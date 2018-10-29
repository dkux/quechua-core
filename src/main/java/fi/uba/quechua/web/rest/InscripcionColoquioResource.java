package fi.uba.quechua.web.rest;

import com.codahale.metrics.annotation.Timed;
import fi.uba.quechua.domain.*;
import fi.uba.quechua.domain.enumeration.InscripcionColoquioEstado;
import fi.uba.quechua.service.*;
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
 * REST controller for managing InscripcionColoquio.
 */
@RestController
@RequestMapping("/api")
public class InscripcionColoquioResource {

    private final Logger log = LoggerFactory.getLogger(InscripcionColoquioResource.class);

    private static final String ENTITY_NAME = "inscripcionColoquio";

    private final InscripcionColoquioService inscripcionColoquioService;

    private final UserService userService;

    private final AlumnoService alumnoService;

    private final ColoquioService coloquioService;

    private CursadaService cursadaService;

    public InscripcionColoquioResource(InscripcionColoquioService inscripcionColoquioService, UserService userService,
                                       ColoquioService coloquioService, AlumnoService alumnoService, CursadaService cursadaService) {
        this.inscripcionColoquioService = inscripcionColoquioService;
        this.userService = userService;
        this.coloquioService = coloquioService;
        this.alumnoService = alumnoService;
        this.cursadaService = cursadaService;
    }

    /**
     * POST  /inscripcion-coloquios : Create a new inscripcionColoquio.
     *
     * @param inscripcionColoquio the inscripcionColoquio to create
     * @return the ResponseEntity with status 201 (Created) and with body the new inscripcionColoquio, or with status 400 (Bad Request) if the inscripcionColoquio has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/inscripcion-coloquios")
    @Timed
    public ResponseEntity<InscripcionColoquio> createInscripcionColoquio(@RequestBody InscripcionColoquio inscripcionColoquio) throws URISyntaxException {
        log.debug("REST request to save InscripcionColoquio : {}", inscripcionColoquio);
        if (inscripcionColoquio.getId() != null) {
            throw new BadRequestAlertException("A new inscripcionColoquio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InscripcionColoquio result = inscripcionColoquioService.save(inscripcionColoquio);
        return ResponseEntity.created(new URI("/api/inscripcion-coloquios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /inscripcion-coloquios : Updates an existing inscripcionColoquio.
     *
     * @param inscripcionColoquio the inscripcionColoquio to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated inscripcionColoquio,
     * or with status 400 (Bad Request) if the inscripcionColoquio is not valid,
     * or with status 500 (Internal Server Error) if the inscripcionColoquio couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/inscripcion-coloquios")
    @Timed
    public ResponseEntity<InscripcionColoquio> updateInscripcionColoquio(@RequestBody InscripcionColoquio inscripcionColoquio) throws URISyntaxException {
        log.debug("REST request to update InscripcionColoquio : {}", inscripcionColoquio);
        if (inscripcionColoquio.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InscripcionColoquio result = inscripcionColoquioService.save(inscripcionColoquio);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, inscripcionColoquio.getId().toString()))
            .body(result);
    }

    /**
     * GET  /inscripcion-coloquios : get all the inscripcionColoquios.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of inscripcionColoquios in body
     */
    @GetMapping("/inscripcion-coloquios")
    @Timed
    public List<InscripcionColoquio> getAllInscripcionColoquios() {
        log.debug("REST request to get all InscripcionColoquios");
        return inscripcionColoquioService.findAll();
    }

    /**
     * GET  /inscripcion-coloquios/:id : get the "id" inscripcionColoquio.
     *
     * @param id the id of the inscripcionColoquio to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the inscripcionColoquio, or with status 404 (Not Found)
     */
    @GetMapping("/inscripcion-coloquios/{id}")
    @Timed
    public ResponseEntity<InscripcionColoquio> getInscripcionColoquio(@PathVariable Long id) {
        log.debug("REST request to get InscripcionColoquio : {}", id);
        Optional<InscripcionColoquio> inscripcionColoquio = inscripcionColoquioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(inscripcionColoquio);
    }

    /**
     * DELETE  /inscripcion-coloquios/:id : delete the "id" inscripcionColoquio.
     *
     * @param id the id of the inscripcionColoquio to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/inscripcion-coloquios/{id}")
    @Timed
    public ResponseEntity<Void> deleteInscripcionColoquio(@PathVariable Long id) {
        log.debug("REST request to delete InscripcionColoquio : {}", id);
        inscripcionColoquioService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @PostMapping("/inscripcion-coloquios/{coloquioId}/inscribir")
    @Timed
    public ResponseEntity<InscripcionColoquio> inscribir(@PathVariable Long coloquioId) throws URISyntaxException {
        Long userId = userService.getUserWithAuthorities().get().getId();
        log.debug("REST request to inscribir al alumno {} en el coloquio {}", userId, coloquioId);
        Optional<Alumno> alumno = alumnoService.findOneByUserId(userId);
        if (!alumno.isPresent()) {
            throw new BadRequestAlertException("No existe un alumno con id provisto", "Alumno", "idnoexists");
        }
        Optional<Coloquio> coloquio = coloquioService.findOne(coloquioId);
        if (!coloquio.isPresent()) {
            throw new BadRequestAlertException("No existe un coloquio con id provisto", "Coloquio", "idnoexists");
        }

        Optional<Cursada> cursada = cursadaService.findCursadaByAlumnoAndCursoColoquioPendiente(alumno.get(), coloquio.get().getCurso());
        if (!cursada.isPresent()) {
            throw new BadRequestAlertException("No puede inscribirse al coloquio", "Coloquio", "idnoexists");
        }

        Optional<InscripcionColoquio> inscripcionColoquio = inscripcionColoquioService.findByColoquioAndAlumnoAndEstado(coloquio.get(), alumno.get(), InscripcionColoquioEstado.ACTIVA);
        if (inscripcionColoquio.isPresent()) {
            throw new BadRequestAlertException("El alumno ya se encuentra inscripto al coloquio", "Coloquio", "idexists");
        }
        InscripcionColoquio inscripcion = new InscripcionColoquio();
        inscripcion.setAlumno(alumno.get());
        inscripcion.setColoquio(coloquio.get());
        inscripcion.estado(InscripcionColoquioEstado.ACTIVA);
        inscripcion.setCursada(cursada.get());
        InscripcionColoquio result = inscripcionColoquioService.save(inscripcion);
        return ResponseEntity.created(new URI("/api/inscripcion-coloquios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("inscripcionColoquio", result.getId().toString()))
            .body(result);
    }


    /**
     * GET  /inscripcion-coloquios : get all the inscripcionColoquios by Alumno.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of inscripcionColoquios in body
     */
    @GetMapping("/inscripcion-coloquios/byAlumno")
    @Timed
    public List<InscripcionColoquio> getAllInscripcionColoquiosByAlumno() {
        Long userId = userService.getUserWithAuthorities().get().getId();
        log.debug("REST request to get all InscripcionColoquios by Almuno {}", userId);
        Optional<Alumno> alumno = alumnoService.findOneByUserId(userId);
        if (!alumno.isPresent()) {
            throw new BadRequestAlertException("No existe un alumno con id provisto", "Alumno", "idnoexists");
        }
        return inscripcionColoquioService.findAllActivasByAlumno(alumno.get());
    }
}
