package fi.uba.quechua.web.rest;

import com.codahale.metrics.annotation.Timed;
import fi.uba.quechua.domain.Prioridad;
import fi.uba.quechua.service.PrioridadService;
import fi.uba.quechua.web.rest.errors.BadRequestAlertException;
import fi.uba.quechua.web.rest.util.HeaderUtil;
import fi.uba.quechua.service.dto.PrioridadCriteria;
import fi.uba.quechua.service.PrioridadQueryService;
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
 * REST controller for managing Prioridad.
 */
@RestController
@RequestMapping("/api")
public class PrioridadResource {

    private final Logger log = LoggerFactory.getLogger(PrioridadResource.class);

    private static final String ENTITY_NAME = "prioridad";

    private final PrioridadService prioridadService;

    private final PrioridadQueryService prioridadQueryService;

    public PrioridadResource(PrioridadService prioridadService, PrioridadQueryService prioridadQueryService) {
        this.prioridadService = prioridadService;
        this.prioridadQueryService = prioridadQueryService;
    }

    /**
     * POST  /prioridads : Create a new prioridad.
     *
     * @param prioridad the prioridad to create
     * @return the ResponseEntity with status 201 (Created) and with body the new prioridad, or with status 400 (Bad Request) if the prioridad has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/prioridads")
    @Timed
    public ResponseEntity<Prioridad> createPrioridad(@Valid @RequestBody Prioridad prioridad) throws URISyntaxException {
        log.debug("REST request to save Prioridad : {}", prioridad);
        if (prioridad.getId() != null) {
            throw new BadRequestAlertException("A new prioridad cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Prioridad result = prioridadService.save(prioridad);
        return ResponseEntity.created(new URI("/api/prioridads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /prioridads : Updates an existing prioridad.
     *
     * @param prioridad the prioridad to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated prioridad,
     * or with status 400 (Bad Request) if the prioridad is not valid,
     * or with status 500 (Internal Server Error) if the prioridad couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/prioridads")
    @Timed
    public ResponseEntity<Prioridad> updatePrioridad(@Valid @RequestBody Prioridad prioridad) throws URISyntaxException {
        log.debug("REST request to update Prioridad : {}", prioridad);
        if (prioridad.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Prioridad result = prioridadService.save(prioridad);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, prioridad.getId().toString()))
            .body(result);
    }

    /**
     * GET  /prioridads : get all the prioridads.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of prioridads in body
     */
    @GetMapping("/prioridads")
    @Timed
    public ResponseEntity<List<Prioridad>> getAllPrioridads(PrioridadCriteria criteria) {
        log.debug("REST request to get Prioridads by criteria: {}", criteria);
        List<Prioridad> entityList = prioridadQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * GET  /prioridads/:id : get the "id" prioridad.
     *
     * @param id the id of the prioridad to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the prioridad, or with status 404 (Not Found)
     */
    @GetMapping("/prioridads/{id}")
    @Timed
    public ResponseEntity<Prioridad> getPrioridad(@PathVariable Long id) {
        log.debug("REST request to get Prioridad : {}", id);
        Optional<Prioridad> prioridad = prioridadService.findOne(id);
        return ResponseUtil.wrapOrNotFound(prioridad);
    }

    /**
     * DELETE  /prioridads/:id : delete the "id" prioridad.
     *
     * @param id the id of the prioridad to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/prioridads/{id}")
    @Timed
    public ResponseEntity<Void> deletePrioridad(@PathVariable Long id) {
        log.debug("REST request to delete Prioridad : {}", id);
        prioridadService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
