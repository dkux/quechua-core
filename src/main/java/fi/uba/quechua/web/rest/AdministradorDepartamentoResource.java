package fi.uba.quechua.web.rest;

import com.codahale.metrics.annotation.Timed;
import fi.uba.quechua.domain.AdministradorDepartamento;
import fi.uba.quechua.repository.AdministradorDepartamentoRepository;
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
 * REST controller for managing AdministradorDepartamento.
 */
@RestController
@RequestMapping("/api")
public class AdministradorDepartamentoResource {

    private final Logger log = LoggerFactory.getLogger(AdministradorDepartamentoResource.class);

    private static final String ENTITY_NAME = "administradorDepartamento";

    private final AdministradorDepartamentoRepository administradorDepartamentoRepository;

    public AdministradorDepartamentoResource(AdministradorDepartamentoRepository administradorDepartamentoRepository) {
        this.administradorDepartamentoRepository = administradorDepartamentoRepository;
    }

    /**
     * POST  /administrador-departamentos : Create a new administradorDepartamento.
     *
     * @param administradorDepartamento the administradorDepartamento to create
     * @return the ResponseEntity with status 201 (Created) and with body the new administradorDepartamento, or with status 400 (Bad Request) if the administradorDepartamento has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/administrador-departamentos")
    @Timed
    public ResponseEntity<AdministradorDepartamento> createAdministradorDepartamento(@Valid @RequestBody AdministradorDepartamento administradorDepartamento) throws URISyntaxException {
        log.debug("REST request to save AdministradorDepartamento : {}", administradorDepartamento);
        if (administradorDepartamento.getId() != null) {
            throw new BadRequestAlertException("A new administradorDepartamento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdministradorDepartamento result = administradorDepartamentoRepository.save(administradorDepartamento);
        return ResponseEntity.created(new URI("/api/administrador-departamentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /administrador-departamentos : Updates an existing administradorDepartamento.
     *
     * @param administradorDepartamento the administradorDepartamento to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated administradorDepartamento,
     * or with status 400 (Bad Request) if the administradorDepartamento is not valid,
     * or with status 500 (Internal Server Error) if the administradorDepartamento couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/administrador-departamentos")
    @Timed
    public ResponseEntity<AdministradorDepartamento> updateAdministradorDepartamento(@Valid @RequestBody AdministradorDepartamento administradorDepartamento) throws URISyntaxException {
        log.debug("REST request to update AdministradorDepartamento : {}", administradorDepartamento);
        if (administradorDepartamento.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AdministradorDepartamento result = administradorDepartamentoRepository.save(administradorDepartamento);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, administradorDepartamento.getId().toString()))
            .body(result);
    }

    /**
     * GET  /administrador-departamentos : get all the administradorDepartamentos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of administradorDepartamentos in body
     */
    @GetMapping("/administrador-departamentos")
    @Timed
    public List<AdministradorDepartamento> getAllAdministradorDepartamentos() {
        log.debug("REST request to get all AdministradorDepartamentos");
        return administradorDepartamentoRepository.findAll();
    }

    /**
     * GET  /administrador-departamentos/:id : get the "id" administradorDepartamento.
     *
     * @param id the id of the administradorDepartamento to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the administradorDepartamento, or with status 404 (Not Found)
     */
    @GetMapping("/administrador-departamentos/{id}")
    @Timed
    public ResponseEntity<AdministradorDepartamento> getAdministradorDepartamento(@PathVariable Long id) {
        log.debug("REST request to get AdministradorDepartamento : {}", id);
        Optional<AdministradorDepartamento> administradorDepartamento = administradorDepartamentoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(administradorDepartamento);
    }

    /**
     * DELETE  /administrador-departamentos/:id : delete the "id" administradorDepartamento.
     *
     * @param id the id of the administradorDepartamento to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/administrador-departamentos/{id}")
    @Timed
    public ResponseEntity<Void> deleteAdministradorDepartamento(@PathVariable Long id) {
        log.debug("REST request to delete AdministradorDepartamento : {}", id);

        administradorDepartamentoRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
