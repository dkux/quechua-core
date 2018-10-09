package fi.uba.quechua.web.rest;

import com.codahale.metrics.annotation.Timed;
import fi.uba.quechua.domain.Periodo;
import fi.uba.quechua.service.PeriodoService;
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
 * REST controller for managing Periodo.
 */
@RestController
@RequestMapping("/api")
public class PeriodoResource {

    private final Logger log = LoggerFactory.getLogger(PeriodoResource.class);

    private static final String ENTITY_NAME = "periodo";

    private final PeriodoService periodoService;

    public PeriodoResource(PeriodoService periodoService) {
        this.periodoService = periodoService;
    }

    /**
     * POST  /periodos : Create a new periodo.
     *
     * @param periodo the periodo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new periodo, or with status 400 (Bad Request) if the periodo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/periodos")
    @Timed
    public ResponseEntity<Periodo> createPeriodo(@Valid @RequestBody Periodo periodo) throws URISyntaxException {
        log.debug("REST request to save Periodo : {}", periodo);
        if (periodo.getId() != null) {
            throw new BadRequestAlertException("A new periodo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Periodo result = periodoService.save(periodo);
        return ResponseEntity.created(new URI("/api/periodos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /periodos : Updates an existing periodo.
     *
     * @param periodo the periodo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated periodo,
     * or with status 400 (Bad Request) if the periodo is not valid,
     * or with status 500 (Internal Server Error) if the periodo couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/periodos")
    @Timed
    public ResponseEntity<Periodo> updatePeriodo(@Valid @RequestBody Periodo periodo) throws URISyntaxException {
        log.debug("REST request to update Periodo : {}", periodo);
        if (periodo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Periodo result = periodoService.save(periodo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, periodo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /periodos : get all the periodos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of periodos in body
     */
    @GetMapping("/periodos")
    @Timed
    public List<Periodo> getAllPeriodos() {
        log.debug("REST request to get all Periodos");
        return periodoService.findAll();
    }

    /**
     * GET  /periodos/:id : get the "id" periodo.
     *
     * @param id the id of the periodo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the periodo, or with status 404 (Not Found)
     */
    @GetMapping("/periodos/{id}")
    @Timed
    public ResponseEntity<Periodo> getPeriodo(@PathVariable Long id) {
        log.debug("REST request to get Periodo : {}", id);
        Optional<Periodo> periodo = periodoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(periodo);
    }

    /**
     * DELETE  /periodos/:id : delete the "id" periodo.
     *
     * @param id the id of the periodo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/periodos/{id}")
    @Timed
    public ResponseEntity<Void> deletePeriodo(@PathVariable Long id) {
        log.debug("REST request to delete Periodo : {}", id);
        periodoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
