package fi.uba.quechua.web.rest;

import com.codahale.metrics.annotation.Timed;
import fi.uba.quechua.domain.PeriodoAdministrativo;
import fi.uba.quechua.service.PeriodoAdministrativoService;
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
 * REST controller for managing PeriodoAdministrativo.
 */
@RestController
@RequestMapping("/api")
public class PeriodoAdministrativoResource {

    private final Logger log = LoggerFactory.getLogger(PeriodoAdministrativoResource.class);

    private static final String ENTITY_NAME = "periodoAdministrativo";

    private final PeriodoAdministrativoService periodoAdministrativoService;

    public PeriodoAdministrativoResource(PeriodoAdministrativoService periodoAdministrativoService) {
        this.periodoAdministrativoService = periodoAdministrativoService;
    }

    /**
     * POST  /periodo-administrativos : Create a new periodoAdministrativo.
     *
     * @param periodoAdministrativo the periodoAdministrativo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new periodoAdministrativo, or with status 400 (Bad Request) if the periodoAdministrativo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/periodo-administrativos")
    @Timed
    public ResponseEntity<PeriodoAdministrativo> createPeriodoAdministrativo(@Valid @RequestBody PeriodoAdministrativo periodoAdministrativo) throws URISyntaxException {
        log.debug("REST request to save PeriodoAdministrativo : {}", periodoAdministrativo);
        if (periodoAdministrativo.getId() != null) {
            throw new BadRequestAlertException("A new periodoAdministrativo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PeriodoAdministrativo result = periodoAdministrativoService.save(periodoAdministrativo);
        return ResponseEntity.created(new URI("/api/periodo-administrativos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /periodo-administrativos : Updates an existing periodoAdministrativo.
     *
     * @param periodoAdministrativo the periodoAdministrativo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated periodoAdministrativo,
     * or with status 400 (Bad Request) if the periodoAdministrativo is not valid,
     * or with status 500 (Internal Server Error) if the periodoAdministrativo couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/periodo-administrativos")
    @Timed
    public ResponseEntity<PeriodoAdministrativo> updatePeriodoAdministrativo(@Valid @RequestBody PeriodoAdministrativo periodoAdministrativo) throws URISyntaxException {
        log.debug("REST request to update PeriodoAdministrativo : {}", periodoAdministrativo);
        if (periodoAdministrativo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PeriodoAdministrativo result = periodoAdministrativoService.save(periodoAdministrativo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, periodoAdministrativo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /periodo-administrativos : get all the periodoAdministrativos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of periodoAdministrativos in body
     */
    @GetMapping("/periodo-administrativos")
    @Timed
    public List<PeriodoAdministrativo> getAllPeriodoAdministrativos() {
        log.debug("REST request to get all PeriodoAdministrativos");
        return periodoAdministrativoService.findAll();
    }

    /**
     * GET  /periodo-administrativos/:id : get the "id" periodoAdministrativo.
     *
     * @param id the id of the periodoAdministrativo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the periodoAdministrativo, or with status 404 (Not Found)
     */
    @GetMapping("/periodo-administrativos/{id}")
    @Timed
    public ResponseEntity<PeriodoAdministrativo> getPeriodoAdministrativo(@PathVariable Long id) {
        log.debug("REST request to get PeriodoAdministrativo : {}", id);
        Optional<PeriodoAdministrativo> periodoAdministrativo = periodoAdministrativoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(periodoAdministrativo);
    }

    /**
     * DELETE  /periodo-administrativos/:id : delete the "id" periodoAdministrativo.
     *
     * @param id the id of the periodoAdministrativo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/periodo-administrativos/{id}")
    @Timed
    public ResponseEntity<Void> deletePeriodoAdministrativo(@PathVariable Long id) {
        log.debug("REST request to delete PeriodoAdministrativo : {}", id);
        periodoAdministrativoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
