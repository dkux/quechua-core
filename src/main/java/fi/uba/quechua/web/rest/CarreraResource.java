package fi.uba.quechua.web.rest;

import com.codahale.metrics.annotation.Timed;
import fi.uba.quechua.domain.Carrera;
import fi.uba.quechua.repository.CarreraRepository;
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
 * REST controller for managing Carrera.
 */
@RestController
@RequestMapping("/api")
public class CarreraResource {

    private final Logger log = LoggerFactory.getLogger(CarreraResource.class);

    private static final String ENTITY_NAME = "carrera";

    private final CarreraRepository carreraRepository;

    public CarreraResource(CarreraRepository carreraRepository) {
        this.carreraRepository = carreraRepository;
    }

    /**
     * POST  /carreras : Create a new carrera.
     *
     * @param carrera the carrera to create
     * @return the ResponseEntity with status 201 (Created) and with body the new carrera, or with status 400 (Bad Request) if the carrera has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/carreras")
    @Timed
    public ResponseEntity<Carrera> createCarrera(@Valid @RequestBody Carrera carrera) throws URISyntaxException {
        log.debug("REST request to save Carrera : {}", carrera);
        if (carrera.getId() != null) {
            throw new BadRequestAlertException("A new carrera cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Carrera result = carreraRepository.save(carrera);
        return ResponseEntity.created(new URI("/api/carreras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /carreras : Updates an existing carrera.
     *
     * @param carrera the carrera to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated carrera,
     * or with status 400 (Bad Request) if the carrera is not valid,
     * or with status 500 (Internal Server Error) if the carrera couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/carreras")
    @Timed
    public ResponseEntity<Carrera> updateCarrera(@Valid @RequestBody Carrera carrera) throws URISyntaxException {
        log.debug("REST request to update Carrera : {}", carrera);
        if (carrera.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Carrera result = carreraRepository.save(carrera);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, carrera.getId().toString()))
            .body(result);
    }

    /**
     * GET  /carreras : get all the carreras.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of carreras in body
     */
    @GetMapping("/carreras")
    @Timed
    public List<Carrera> getAllCarreras() {
        log.debug("REST request to get all Carreras");
        return carreraRepository.findAll();
    }

    /**
     * GET  /carreras/:id : get the "id" carrera.
     *
     * @param id the id of the carrera to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the carrera, or with status 404 (Not Found)
     */
    @GetMapping("/carreras/{id}")
    @Timed
    public ResponseEntity<Carrera> getCarrera(@PathVariable Long id) {
        log.debug("REST request to get Carrera : {}", id);
        Optional<Carrera> carrera = carreraRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(carrera);
    }

    /**
     * DELETE  /carreras/:id : delete the "id" carrera.
     *
     * @param id the id of the carrera to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/carreras/{id}")
    @Timed
    public ResponseEntity<Void> deleteCarrera(@PathVariable Long id) {
        log.debug("REST request to delete Carrera : {}", id);

        carreraRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
