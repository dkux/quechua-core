package fi.uba.quechua.web.rest;

import com.codahale.metrics.annotation.Timed;
import fi.uba.quechua.domain.Cursada;
import fi.uba.quechua.domain.InscripcionCurso;
import fi.uba.quechua.service.CursadaService;
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
 * REST controller for managing Cursada.
 */
@RestController
@RequestMapping("/api")
public class CursadaResource {

    private final Logger log = LoggerFactory.getLogger(CursadaResource.class);

    private static final String ENTITY_NAME = "cursada";

    private final CursadaService cursadaService;

    public CursadaResource(CursadaService cursadaService) {
        this.cursadaService = cursadaService;
    }

    /**
     * POST  /cursadas : Create a new cursada.
     *
     * @param cursada the cursada to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cursada, or with status 400 (Bad Request) if the cursada has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cursadas")
    @Timed
    public ResponseEntity<Cursada> createCursada(@RequestBody Cursada cursada) throws URISyntaxException {
        log.debug("REST request to save Cursada : {}", cursada);
        if (cursada.getId() != null) {
            throw new BadRequestAlertException("A new cursada cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Cursada result = cursadaService.save(cursada);
        return ResponseEntity.created(new URI("/api/cursadas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cursadas : Updates an existing cursada.
     *
     * @param cursada the cursada to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cursada,
     * or with status 400 (Bad Request) if the cursada is not valid,
     * or with status 500 (Internal Server Error) if the cursada couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cursadas")
    @Timed
    public ResponseEntity<Cursada> updateCursada(@RequestBody Cursada cursada) throws URISyntaxException {
        log.debug("REST request to update Cursada : {}", cursada);
        if (cursada.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Cursada result = cursadaService.save(cursada);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cursada.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cursadas : get all the cursadas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cursadas in body
     */
    @GetMapping("/cursadas")
    @Timed
    public List<Cursada> getAllCursadas() {
        log.debug("REST request to get all Cursadas");
        return cursadaService.findAll();
    }

    /**
     * GET  /cursadas/:id : get the "id" cursada.
     *
     * @param id the id of the cursada to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cursada, or with status 404 (Not Found)
     */
    @GetMapping("/cursadas/{id}")
    @Timed
    public ResponseEntity<Cursada> getCursada(@PathVariable Long id) {
        log.debug("REST request to get Cursada : {}", id);
        Optional<Cursada> cursada = cursadaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cursada);
    }

    /**
     * DELETE  /cursadas/:id : delete the "id" cursada.
     *
     * @param id the id of the cursada to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cursadas/{id}")
    @Timed
    public ResponseEntity<Void> deleteCursada(@PathVariable Long id) {
        log.debug("REST request to delete Cursada : {}", id);
        cursadaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * POST  /cursadas/iniciar : Create cursadas to inscripcionCurso.
     *
     * @return the ResponseEntity with status 201 (Created) and with body the new cursada, or with status 400 (Bad Request) if the cursada has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cursadas/iniciar")
    @Timed
    public ResponseEntity<Void> iniciarCursadas() throws URISyntaxException {
        log.debug("REST request to iniciar Cursadas");
        cursadaService.iniciarCursadas();
        return ResponseEntity.ok().build();
    }
}
