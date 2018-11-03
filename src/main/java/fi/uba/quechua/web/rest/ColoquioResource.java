package fi.uba.quechua.web.rest;

import com.codahale.metrics.annotation.Timed;
import fi.uba.quechua.domain.Coloquio;
import fi.uba.quechua.domain.Profesor;
import fi.uba.quechua.repository.ProfesorRepository;
import fi.uba.quechua.service.ColoquioService;
import fi.uba.quechua.service.UserService;
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
 * REST controller for managing Coloquio.
 */
@RestController
@RequestMapping("/api")
public class ColoquioResource {

    private final Logger log = LoggerFactory.getLogger(ColoquioResource.class);

    private static final String ENTITY_NAME = "coloquio";

    private final ColoquioService coloquioService;

    private final ProfesorRepository profesorRepository;

    private final UserService userService;

    public ColoquioResource(ColoquioService coloquioService, ProfesorRepository profesorRepository,
                            UserService userService) {
        this.coloquioService = coloquioService;
        this.profesorRepository = profesorRepository;
        this.userService = userService;
    }

    /**
     * POST  /coloquios : Create a new coloquio.
     *
     * @param coloquio the coloquio to create
     * @return the ResponseEntity with status 201 (Created) and with body the new coloquio, or with status 400 (Bad Request) if the coloquio has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/coloquios")
    @Timed
    public ResponseEntity<Coloquio> createColoquio(@Valid @RequestBody Coloquio coloquio) throws URISyntaxException {
        log.debug("REST request to save Coloquio : {}", coloquio);
        if (coloquio.getId() != null) {
            throw new BadRequestAlertException("A new coloquio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Coloquio result = coloquioService.save(coloquio);
        return ResponseEntity.created(new URI("/api/coloquios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /coloquios : Updates an existing coloquio.
     *
     * @param coloquio the coloquio to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated coloquio,
     * or with status 400 (Bad Request) if the coloquio is not valid,
     * or with status 500 (Internal Server Error) if the coloquio couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/coloquios")
    @Timed
    public ResponseEntity<Coloquio> updateColoquio(@Valid @RequestBody Coloquio coloquio) throws URISyntaxException {
        log.debug("REST request to update Coloquio : {}", coloquio);
        if (coloquio.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Coloquio result = coloquioService.save(coloquio);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, coloquio.getId().toString()))
            .body(result);
    }

    /**
     * GET  /coloquios : get all the coloquios.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of coloquios in body
     */
    @GetMapping("/coloquios")
    @Timed
    public List<Coloquio> getAllColoquios() {
        log.debug("REST request to get all Coloquios");
        return coloquioService.findAll();
    }

    /**
     * GET  /coloquios/:id : get the "id" coloquio.
     *
     * @param id the id of the coloquio to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the coloquio, or with status 404 (Not Found)
     */
    @GetMapping("/coloquios/{id}")
    @Timed
    public ResponseEntity<Coloquio> getColoquio(@PathVariable Long id) {
        log.debug("REST request to get Coloquio : {}", id);
        Optional<Coloquio> coloquio = coloquioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(coloquio);
    }

    /**
     * DELETE  /coloquios/:id : delete the "id" coloquio.
     *
     * @param id the id of the coloquio to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/coloquios/{id}")
    @Timed
    public ResponseEntity<Void> deleteColoquio(@PathVariable Long id) {
        log.debug("REST request to delete Coloquio : {}", id);
        coloquioService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * POST  /coloquios/:coloquioId/eliminar : Elimina el coloquio.
     *
     * @param coloquioId the coloquio to eliminar
     * @return the ResponseEntity with status 200 (OK)
     */
    @PostMapping("/coloquios/{coloquioId}/eliminar")
    @Timed
    public ResponseEntity<Coloquio> eliminarColoquio(@PathVariable Long coloquioId) {
        log.debug("REST request to eliminar Coloquio : {}", coloquioId);
        Optional<Coloquio> coloquio = coloquioService.findOne(coloquioId);
        if (!coloquio.isPresent()) {
            throw new BadRequestAlertException("No existe un Coloquio con el Id", ENTITY_NAME, "idnoexists");
        }
        Long userId = userService.getUserWithAuthorities().get().getId();
        Optional<Profesor> profesor = profesorRepository.findByUserId(userId);
        if (!profesor.isPresent()) {
            throw new BadRequestAlertException("No existe un Profesor asociado al usuario logueado", ENTITY_NAME, "idnoexists");
        }
        if (coloquio.get().getCurso().getProfesor().getId() != profesor.get().getId()) {
            throw new BadRequestAlertException("El coloquio no pertence al profesor logueado", ENTITY_NAME, "idnoexists");
        }
        coloquioService.eliminar(coloquio.get());
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, coloquioId.toString())).build();
    }
}
