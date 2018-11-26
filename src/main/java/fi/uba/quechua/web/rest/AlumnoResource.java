package fi.uba.quechua.web.rest;

import com.codahale.metrics.annotation.Timed;
import fi.uba.quechua.domain.*;
import fi.uba.quechua.repository.PeriodoRepository;
import fi.uba.quechua.security.SecurityUtils;
import fi.uba.quechua.service.*;
import fi.uba.quechua.service.dto.FirebaseTokenDTO;
import fi.uba.quechua.web.rest.errors.BadRequestAlertException;
import fi.uba.quechua.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;


import javax.validation.Valid;
import javax.validation.constraints.AssertFalse;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Alumno.
 */
@RestController
@RequestMapping("/api")
public class AlumnoResource {

    private final Logger log = LoggerFactory.getLogger(AlumnoResource.class);

    private static final String ENTITY_NAME = "alumno";

    private final AlumnoService alumnoService;

    @Autowired
    private PrioridadService prioridadService;

    private final AlumnoCarreraService alumnoCarreraService;

    private final CursadaService cursadaService;


    private final UserService userService;

    public AlumnoResource(AlumnoService alumnoService, AlumnoCarreraService alumnoCarreraService,
                          UserService userService, CursadaService cursadaService) {
        this.alumnoService = alumnoService;
        this.alumnoCarreraService = alumnoCarreraService;
        this.userService = userService;
        this.cursadaService = cursadaService;
    }

    /**
     * POST  /alumnos : Create a new alumno.
     *
     * @param alumno the alumno to create
     * @return the ResponseEntity with status 201 (Created) and with body the new alumno, or with status 400 (Bad Request) if the alumno has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/alumnos")
    @Timed
    public ResponseEntity<Alumno> createAlumno(@Valid @RequestBody Alumno alumno) throws URISyntaxException {
        log.debug("REST request to save Alumno : {}", alumno);
        if (alumno.getId() != null) {
            throw new BadRequestAlertException("A new alumno cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Alumno result = alumnoService.save(alumno);
        return ResponseEntity.created(new URI("/api/alumnos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /alumnos : Updates an existing alumno.
     *
     * @param alumno the alumno to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated alumno,
     * or with status 400 (Bad Request) if the alumno is not valid,
     * or with status 500 (Internal Server Error) if the alumno couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/alumnos")
    @Timed
    public ResponseEntity<Alumno> updateAlumno(@Valid @RequestBody Alumno alumno) throws URISyntaxException {
        log.debug("REST request to update Alumno : {}", alumno);
        if (alumno.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (alumno.getUserId() == null) {
            throw new BadRequestAlertException("Invalid user id", ENTITY_NAME, "idnull");
        }
        Alumno result = alumnoService.save(alumno);

        User user = userService.getUserWithAuthorities().get();

        userService.updateUser(alumno.getNombre(), alumno.getApellido(), user.getEmail(), user.getLangKey(), user.getImageUrl());

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, alumno.getId().toString()))
            .body(result);
    }

    /**
     * GET  /alumnos : get all the alumnos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of alumnos in body
     */
    @GetMapping("/alumnos")
    @Timed
    public List<Alumno> getAllAlumnos() {
        log.debug("REST request to get all Alumnos");
        return alumnoService.findAll();
    }

    /**
     * GET  /alumnos/:id : get the "id" alumno.
     *
     * @param id the id of the alumno to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the alumno, or with status 404 (Not Found)
     */
    @GetMapping("/alumnos/{id}")
    @Timed
    public ResponseEntity<Alumno> getAlumno(@PathVariable Long id) {
        log.debug("REST request to get Alumno : {}", id);
        Optional<Alumno> alumno = alumnoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(alumno);
    }

    /**
     * DELETE  /alumnos/:id : delete the "id" alumno.
     *
     * @param id the id of the alumno to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/alumnos/{id}")
    @Timed
    public ResponseEntity<Void> deleteAlumno(@PathVariable Long id) {
        log.debug("REST request to delete Alumno : {}", id);
        alumnoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * GET  /alumno-carreras : get all the carreras del alumno.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of alumnoCarreras in body
     */
    @GetMapping("/alumnos/carreras")
    @Timed
    public List<Carrera> getCarrerasDelAlumno() {
        log.debug("REST request to get all Carreras del Alumno");
        Long userId = userService.getUserWithAuthorities().get().getId();
        Optional<Alumno> alumno = alumnoService.findOneByUserId(userId);
        if (!alumno.isPresent()) {
            throw new BadRequestAlertException("No existe un Alumno asociado al usuario logueado", "Alumno", "idnoexists");
        }
        return alumnoCarreraService.findCarrerasByAlumno(alumno.get());
    }

    /**
     * GET  /alumno-carreras : get all the carreras del alumno.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of alumnoCarreras in body
     */
    @GetMapping("/alumnos/cursadasActivas")
    @Timed
    public List<Cursada> getCursadasActivasDelAlumno() {
        Long userId = userService.getUserWithAuthorities().get().getId();
        Optional<Alumno> alumno = alumnoService.findOneByUserId(userId);
        if (!alumno.isPresent()) {
            throw new BadRequestAlertException("No existe un Alumno asociado al usuario logueado", "Alumno", "idnoexists");
        }
        return cursadaService.findCursadasActivasByAlumno(alumno.get());
    }

    /**
     * GET  /alumno-prioridad : get prioridad del alumno.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of alumnoprioridad in body
     */
    @GetMapping("/alumnos/prioridad")
    @Timed
    public List<Prioridad> getPrioridadDelAlumno() {
        Long userId = userService.getUserWithAuthorities().get().getId();
        Optional<Alumno> alumno = alumnoService.findOneByUserId(userId);
        if (!alumno.isPresent()) {
            throw new BadRequestAlertException("No existe un Alumno asociado al usuario logueado", "Alumno", "idnoexists");
        }
        List<Prioridad> listaPrioridad = new ArrayList<Prioridad>();
        Optional<Prioridad> prioridad = prioridadService.findOne(Long.valueOf(alumno.get().getPrioridad().toString()));
        listaPrioridad.add(prioridad.get());
        return listaPrioridad;
    }

    /**
     * GET  /alumnos/data.
     *
     * @return the ResponseEntity with status 200 (OK) and with body the alumno, or with status 404 (Not Found)
     */
    @GetMapping("/alumnos/data")
    @Timed
    public ResponseEntity<Alumno> getAlumnoByUser() {
        log.debug("REST request to get Alumno by user");
        Long userId = userService.getUserWithAuthorities().get().getId();
        Optional<Alumno> alumno = alumnoService.findOneByUserId(userId);
        return ResponseUtil.wrapOrNotFound(alumno);
    }

    @PostMapping("/alumnos/setFirebaseToken")
    @Timed
    public ResponseEntity<Void> setFirebaseToken(@RequestBody FirebaseTokenDTO tokenDTO) {
        Long userId = userService.getUserWithAuthorities().get().getId();
        Optional<Alumno> alumno = alumnoService.findOneByUserId(userId);
        if (!alumno.isPresent()) {
            throw new BadRequestAlertException("No existe un Alumno asociado al usuario logueado", "Alumno", "idnoexists");
        } else {
            alumno.get().setFirebaseToken(tokenDTO.getToken());
            alumnoService.save(alumno.get());
        }
        return ResponseEntity.ok().build();
    }

}
