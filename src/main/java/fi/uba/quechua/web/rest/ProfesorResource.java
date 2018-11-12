package fi.uba.quechua.web.rest;

import com.codahale.metrics.annotation.Timed;
import fi.uba.quechua.domain.Curso;
import fi.uba.quechua.domain.Profesor;
import fi.uba.quechua.repository.ProfesorRepository;
import fi.uba.quechua.service.*;
import fi.uba.quechua.web.rest.errors.BadRequestAlertException;
import fi.uba.quechua.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Profesor.
 */
@RestController
@RequestMapping("/api")
public class ProfesorResource {

    private final Logger log = LoggerFactory.getLogger(ProfesorResource.class);

    private static final String ENTITY_NAME = "profesor";

    private final ProfesorRepository profesorRepository;

    private final UserService userService;

    private final CursoService cursoService;

    public ProfesorResource(ProfesorRepository profesorRepository, UserService userService,
                            CursoService cursoService) {
        this.profesorRepository = profesorRepository;
        this.userService = userService;
        this.cursoService = cursoService;
    }

    /**
     * POST  /profesors : Create a new profesor.
     *
     * @param profesor the profesor to create
     * @return the ResponseEntity with status 201 (Created) and with body the new profesor, or with status 400 (Bad Request) if the profesor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/profesors")
    @Timed
    public ResponseEntity<Profesor> createProfesor(@Valid @RequestBody Profesor profesor) throws URISyntaxException {
        log.debug("REST request to save Profesor : {}", profesor);
        if (profesor.getId() != null) {
            throw new BadRequestAlertException("A new profesor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Profesor result = profesorRepository.save(profesor);
        return ResponseEntity.created(new URI("/api/profesors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /profesors : Updates an existing profesor.
     *
     * @param profesor the profesor to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated profesor,
     * or with status 400 (Bad Request) if the profesor is not valid,
     * or with status 500 (Internal Server Error) if the profesor couldn't be updated
     */
    @PutMapping("/profesors")
    @Timed
    public ResponseEntity<Profesor> updateProfesor(@Valid @RequestBody Profesor profesor) {
        log.debug("REST request to update Profesor : {}", profesor);
        if (profesor.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Profesor result = profesorRepository.save(profesor);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, profesor.getId().toString()))
            .body(result);
    }

    /**
     * GET  /profesors : get all the profesors.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of profesors in body
     */
    @GetMapping("/profesors")
    @Timed
    public List<Profesor> getAllProfesors() {
        log.debug("REST request to get all Profesors");
        return profesorRepository.findAll(new Sort(Sort.Direction.ASC, "nombre"));
    }

    /**
     * GET  /profesors/:id : get the "id" profesor.
     *
     * @param id the id of the profesor to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the profesor, or with status 404 (Not Found)
     */
    @GetMapping("/profesors/{id}")
    @Timed
    public ResponseEntity<Profesor> getProfesor(@PathVariable Long id) {
        log.debug("REST request to get Profesor : {}", id);
        Optional<Profesor> profesor = profesorRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(profesor);
    }

    /**
     * DELETE  /profesors/:id : delete the "id" profesor.
     *
     * @param id the id of the profesor to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/profesors/{id}")
    @Timed
    public ResponseEntity<Void> deleteProfesor(@PathVariable Long id) {
        log.debug("REST request to delete Profesor : {}", id);

        profesorRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * GET  /cursadas : get all the cursadas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cursadas in body
     */
    @GetMapping("/profesors/cursos")
    @Timed
    public List<Curso> getAllCursadasByProfesor() {
        log.debug("REST request to get all Cursadas");
        Long userId = userService.getUserWithAuthorities().get().getId();
        Optional<Profesor> profesor = profesorRepository.findByUserId(userId);
        if (!profesor.isPresent()) {
            throw new BadRequestAlertException("No existe un Profesor asociado al usuario logueado", ENTITY_NAME, "idnoexists");
        }
        return cursoService.findByProfesor(profesor.get());
    }

    /**
     * GET  /profesors/data.
     *
     * @return the ResponseEntity with status 200 (OK) and with body the profesor, or with status 404 (Not Found)
    */
    @GetMapping("/profesors/data")
    @Timed
    public ResponseEntity<Profesor> getAlumnoByUser() {
        log.debug("REST request to get Alumno by user");
        Long userId = userService.getUserWithAuthorities().get().getId();
        Optional<Profesor> profesor = profesorRepository.findByUserId(userId);
        return ResponseUtil.wrapOrNotFound(profesor);
    }
}
