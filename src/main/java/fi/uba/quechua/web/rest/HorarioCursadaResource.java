package fi.uba.quechua.web.rest;

import com.codahale.metrics.annotation.Timed;
import fi.uba.quechua.domain.HorarioCursada;
import fi.uba.quechua.service.HorarioCursadaService;
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
 * REST controller for managing HorarioCursada.
 */
@RestController
@RequestMapping("/api")
public class HorarioCursadaResource {

    private final Logger log = LoggerFactory.getLogger(HorarioCursadaResource.class);

    private static final String ENTITY_NAME = "horarioCursada";

    private final HorarioCursadaService horarioCursadaService;

    public HorarioCursadaResource(HorarioCursadaService horarioCursadaService) {
        this.horarioCursadaService = horarioCursadaService;
    }

    /**
     * POST  /horario-cursadas : Create a new horarioCursada.
     *
     * @param horarioCursada the horarioCursada to create
     * @return the ResponseEntity with status 201 (Created) and with body the new horarioCursada, or with status 400 (Bad Request) if the horarioCursada has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/horario-cursadas")
    @Timed
    public ResponseEntity<HorarioCursada> createHorarioCursada(@Valid @RequestBody HorarioCursada horarioCursada) throws URISyntaxException {
        log.debug("REST request to save HorarioCursada : {}", horarioCursada);
        if (horarioCursada.getId() != null) {
            throw new BadRequestAlertException("A new horarioCursada cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HorarioCursada result = horarioCursadaService.save(horarioCursada);
        return ResponseEntity.created(new URI("/api/horario-cursadas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /horario-cursadas : Updates an existing horarioCursada.
     *
     * @param horarioCursada the horarioCursada to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated horarioCursada,
     * or with status 400 (Bad Request) if the horarioCursada is not valid,
     * or with status 500 (Internal Server Error) if the horarioCursada couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/horario-cursadas")
    @Timed
    public ResponseEntity<HorarioCursada> updateHorarioCursada(@Valid @RequestBody HorarioCursada horarioCursada) throws URISyntaxException {
        log.debug("REST request to update HorarioCursada : {}", horarioCursada);
        if (horarioCursada.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        HorarioCursada result = horarioCursadaService.save(horarioCursada);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, horarioCursada.getId().toString()))
            .body(result);
    }

    /**
     * GET  /horario-cursadas : get all the horarioCursadas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of horarioCursadas in body
     */
    @GetMapping("/horario-cursadas")
    @Timed
    public List<HorarioCursada> getAllHorarioCursadas() {
        log.debug("REST request to get all HorarioCursadas");
        return horarioCursadaService.findAll();
    }

    /**
     * GET  /horario-cursadas/:id : get the "id" horarioCursada.
     *
     * @param id the id of the horarioCursada to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the horarioCursada, or with status 404 (Not Found)
     */
    @GetMapping("/horario-cursadas/{id}")
    @Timed
    public ResponseEntity<HorarioCursada> getHorarioCursada(@PathVariable Long id) {
        log.debug("REST request to get HorarioCursada : {}", id);
        Optional<HorarioCursada> horarioCursada = horarioCursadaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(horarioCursada);
    }

    /**
     * DELETE  /horario-cursadas/:id : delete the "id" horarioCursada.
     *
     * @param id the id of the horarioCursada to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/horario-cursadas/{id}")
    @Timed
    public ResponseEntity<Void> deleteHorarioCursada(@PathVariable Long id) {
        log.debug("REST request to delete HorarioCursada : {}", id);
        horarioCursadaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
