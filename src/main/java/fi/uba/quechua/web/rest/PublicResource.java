package fi.uba.quechua.web.rest;

import com.codahale.metrics.annotation.Timed;
import fi.uba.quechua.domain.*;
import fi.uba.quechua.domain.enumeration.InscripcionCursoEstado;
import fi.uba.quechua.service.AlumnoService;
import fi.uba.quechua.service.CursoService;
import fi.uba.quechua.service.InscripcionCursoService;
import fi.uba.quechua.service.MateriaService;
import fi.uba.quechua.service.dto.CursoDTO;
import fi.uba.quechua.web.rest.errors.BadRequestAlertException;
import fi.uba.quechua.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/public")
public class PublicResource {

    private final Logger log = LoggerFactory.getLogger(CursoResource.class);

    private final CursoService cursoService;

    private final MateriaService materiaService;

    private final AlumnoService alumnoService;

    private final InscripcionCursoService inscripcionCursoService;

    public PublicResource(CursoService cursoService, MateriaService materiaService, AlumnoService alumnoService,
                          InscripcionCursoService inscripcionCursoService) {
        this.cursoService = cursoService;
        this.materiaService = materiaService;
        this.alumnoService = alumnoService;
        this.inscripcionCursoService = inscripcionCursoService;
    }

    /**
     * GET  /cursos : get all the cursos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cursos in body
     */
    @GetMapping("/cursos")
    @Timed
    public List<CursoDTO> getAllCursos() {
        log.debug("REST request to get all Cursos");
        List<Curso> cursos = cursoService.findAllWithHorarios();
        List<CursoDTO> cursoDTOS = new LinkedList<>();
        for (Curso curso: cursos) {
            List<InscripcionCurso> inscripciones = inscripcionCursoService.findByCurso(curso);
            cursoDTOS.add(new CursoDTO(curso, inscripciones));
        }
        return cursoDTOS;
    }

    /**
     * GET  /cursos : get all the cursos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cursos in body
     */
    @GetMapping("/materias/{materiaId}/cursos")
    @Timed
    public List<CursoDTO> getCursosByMateria(@PathVariable Long materiaId) {
        log.debug("REST request to get all Cursos de la materia {}", materiaId);
        Optional<Materia> materia = materiaService.findOne(materiaId);
        if (!materia.isPresent()) {
            throw new BadRequestAlertException("No existe una Materia con id provisto", "Materia", "idnoexists");
        }
        List<Curso> cursos = cursoService.findByMateriaWithHorarios(materia.get());
        List<CursoDTO> cursoDTOS = new LinkedList<>();
        for (Curso curso: cursos) {
            List<InscripcionCurso> inscripciones = inscripcionCursoService.findByCurso(curso);
            cursoDTOS.add(new CursoDTO(curso, inscripciones));
        }
        return cursoDTOS;
    }

    /**
     * GET  /materias : get all the materias.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of materias in body
     */
    @GetMapping("/materias")
    @Timed
    public List<Materia> getAllMaterias() {
        log.debug("REST request to get all Materias");
        return materiaService.findAll();
    }

    @PostMapping("/inscripcion-cursos/{cursoId}/{alumnoId}")
    @Timed
    public ResponseEntity<InscripcionCurso> inscribir(@PathVariable Long cursoId, @PathVariable Long alumnoId) throws URISyntaxException {
        log.debug("REST request to inscribir al alumno {} en el curso {}", alumnoId, cursoId);
        Optional<Alumno> alumno = alumnoService.findOne(alumnoId);
        InscripcionCursoEstado estado = InscripcionCursoEstado.REGULAR;
        if (!alumno.isPresent()) {
            throw new BadRequestAlertException("No existe un alumno con id provisto", "Alumno", "idnoexists");
        }
        Optional<Curso> curso = cursoService.findOne(cursoId);
        if (!curso.isPresent()) {
            throw new BadRequestAlertException("No existe un curso con id provisto", "Curso", "idnoexists");
        }

        List<InscripcionCurso> inscripciones = inscripcionCursoService.findAllRegularesByCurso(curso.get());
        if (curso.get().getVacantes() <= inscripciones.size()) {
            estado = InscripcionCursoEstado.CONDICIONAL;
        }

        Optional<InscripcionCurso> inscripcionCurso = inscripcionCursoService.findByCursoAndAlumno(curso.get(), alumno.get());
        if (inscripcionCurso.isPresent()) {
            throw new BadRequestAlertException("El alumno ya se encuentra inscripto al curso", "Curso", "idexists");
        }
        InscripcionCurso inscripcion = new InscripcionCurso();
        inscripcion.setAlumno(alumno.get());
        inscripcion.setCurso(curso.get());
        inscripcion.estado(estado);
        InscripcionCurso result = inscripcionCursoService.save(inscripcion);
        return ResponseEntity.created(new URI("/api/inscripcion-cursos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("inscripcionCurso", result.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cursos : get curso and inscripciones.
     *
     * @return the ResponseEntity with status 200 (OK) and the curso in body
     */
    @GetMapping("/cursos/{cursoId}/inscripciones")
    @Timed
    public CursoDTO getInscripciones(@PathVariable Long cursoId) {
        log.debug("REST request to get inscripciones");
        Optional<Curso> curso = cursoService.findOneWithHorarios(cursoId);
        if (!curso.isPresent()) {
            throw new BadRequestAlertException("No existe un curso con id provisto", "Curso", "idnoexists");
        }
        List<InscripcionCurso> inscripciones = inscripcionCursoService.findByCurso(curso.get());
        return new CursoDTO(curso.get(), inscripciones);
    }

    @PostMapping("/inscripcion-cursos/{inscripcionCursoId}/regularizar")
    public ResponseEntity<InscripcionCurso> regularizarInscripcion(@PathVariable Long inscripcionCursoId) {
        Optional<InscripcionCurso> inscripcion = inscripcionCursoService.findOne(inscripcionCursoId);
        if (!inscripcion.isPresent()) {
            throw new BadRequestAlertException("No existe la inscripcion con id provisto", "InscripcionCurso", "idnoexists");
        }
        inscripcion.get().setEstado(InscripcionCursoEstado.REGULAR);
        InscripcionCurso result = inscripcionCursoService.save(inscripcion.get());
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("inscripcionCurso", result.getId().toString()))
            .body(result);
    }

    /**
     * POST  /cursos : Create a new curso.
     *
     * @param curso the curso to create
     * @return the ResponseEntity with status 201 (Created) and with body the new curso, or with status 400 (Bad Request) if the curso has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cursos")
    @Timed
    public ResponseEntity<Curso> createCurso(@Valid @RequestBody Curso curso) throws URISyntaxException {
        log.debug("REST request to save Curso : {}", curso);
        if (curso.getId() != null) {
            throw new BadRequestAlertException("A new curso cannot already have an ID", "curso", "idexists");
        }
        Curso result = cursoService.save(curso);
        return ResponseEntity.created(new URI("/api/cursos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("curso", result.getId().toString()))
            .body(result);
    }
}
