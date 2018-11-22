package fi.uba.quechua.web.rest;

import afu.org.checkerframework.checker.units.qual.A;
import fi.uba.quechua.QuechuaApp;

import fi.uba.quechua.domain.Alumno;
import fi.uba.quechua.repository.AlumnoRepository;
import fi.uba.quechua.service.AlumnoCarreraService;
import fi.uba.quechua.service.AlumnoService;
import fi.uba.quechua.service.CursadaService;
import fi.uba.quechua.service.UserService;
import fi.uba.quechua.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static fi.uba.quechua.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AlumnoResource REST controller.
 *
 * @see AlumnoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = QuechuaApp.class)
public class AlumnoResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO = "BBBBBBBBBB";

    private static final String DEFAULT_PADRON = "AAAAAAAAAA";
    private static final String UPDATED_PADRON = "BBBBBBBBBB";

    private static final Integer DEFAULT_PRIORIDAD = 1;
    private static final Integer UPDATED_PRIORIDAD = 2;

    private static final String DEFAULT_FIREBASE_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_FIREBASE_TOKEN = "BBBBBBBBBB";

    @Autowired
    private AlumnoRepository alumnoRepository;



    @Autowired
    private AlumnoService alumnoService;

    @Autowired
    private AlumnoCarreraService alumnoCarreraService;

    @Autowired
    private UserService userService;

    @Autowired
    private CursadaService cursadaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAlumnoMockMvc;

    private Alumno alumno;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AlumnoResource alumnoResource = new AlumnoResource(alumnoService, alumnoCarreraService, userService, cursadaService);
        this.restAlumnoMockMvc = MockMvcBuilders.standaloneSetup(alumnoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Alumno createEntity(EntityManager em) {
        Alumno alumno = new Alumno()
            .nombre(DEFAULT_NOMBRE)
            .apellido(DEFAULT_APELLIDO)
            .padron(DEFAULT_PADRON)
            .prioridad(DEFAULT_PRIORIDAD)
            .firebaseToken(DEFAULT_FIREBASE_TOKEN);
        return alumno;
    }

    @Before
    public void initTest() {
        alumno = createEntity(em);
    }

    @Test
    @Transactional
    public void createAlumno() throws Exception {
        int databaseSizeBeforeCreate = alumnoRepository.findAll().size();

        // Create the Alumno
        restAlumnoMockMvc.perform(post("/api/alumnos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alumno)))
            .andExpect(status().isCreated());

        // Validate the Alumno in the database
        List<Alumno> alumnoList = alumnoRepository.findAll();
        assertThat(alumnoList).hasSize(databaseSizeBeforeCreate + 1);
        Alumno testAlumno = alumnoList.get(alumnoList.size() - 1);
        assertThat(testAlumno.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testAlumno.getApellido()).isEqualTo(DEFAULT_APELLIDO);
        assertThat(testAlumno.getPadron()).isEqualTo(DEFAULT_PADRON);
        assertThat(testAlumno.getPrioridad()).isEqualTo(DEFAULT_PRIORIDAD);
        assertThat(testAlumno.getFirebaseToken()).isEqualTo(DEFAULT_FIREBASE_TOKEN);
    }

    @Test
    @Transactional
    public void createAlumnoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = alumnoRepository.findAll().size();

        // Create the Alumno with an existing ID
        alumno.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlumnoMockMvc.perform(post("/api/alumnos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alumno)))
            .andExpect(status().isBadRequest());

        // Validate the Alumno in the database
        List<Alumno> alumnoList = alumnoRepository.findAll();
        assertThat(alumnoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = alumnoRepository.findAll().size();
        // set the field null
        alumno.setNombre(null);

        // Create the Alumno, which fails.

        restAlumnoMockMvc.perform(post("/api/alumnos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alumno)))
            .andExpect(status().isBadRequest());

        List<Alumno> alumnoList = alumnoRepository.findAll();
        assertThat(alumnoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkApellidoIsRequired() throws Exception {
        int databaseSizeBeforeTest = alumnoRepository.findAll().size();
        // set the field null
        alumno.setApellido(null);

        // Create the Alumno, which fails.

        restAlumnoMockMvc.perform(post("/api/alumnos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alumno)))
            .andExpect(status().isBadRequest());

        List<Alumno> alumnoList = alumnoRepository.findAll();
        assertThat(alumnoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPadronIsRequired() throws Exception {
        int databaseSizeBeforeTest = alumnoRepository.findAll().size();
        // set the field null
        alumno.setPadron(null);

        // Create the Alumno, which fails.

        restAlumnoMockMvc.perform(post("/api/alumnos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alumno)))
            .andExpect(status().isBadRequest());

        List<Alumno> alumnoList = alumnoRepository.findAll();
        assertThat(alumnoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrioridadIsRequired() throws Exception {
        int databaseSizeBeforeTest = alumnoRepository.findAll().size();
        // set the field null
        alumno.setPrioridad(null);

        // Create the Alumno, which fails.

        restAlumnoMockMvc.perform(post("/api/alumnos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alumno)))
            .andExpect(status().isBadRequest());

        List<Alumno> alumnoList = alumnoRepository.findAll();
        assertThat(alumnoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAlumnos() throws Exception {
        // Initialize the database
        alumnoRepository.saveAndFlush(alumno);

        // Get all the alumnoList
        restAlumnoMockMvc.perform(get("/api/alumnos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alumno.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].apellido").value(hasItem(DEFAULT_APELLIDO.toString())))
            .andExpect(jsonPath("$.[*].padron").value(hasItem(DEFAULT_PADRON.toString())))
            .andExpect(jsonPath("$.[*].prioridad").value(hasItem(DEFAULT_PRIORIDAD)))
            .andExpect(jsonPath("$.[*].firebaseToken").value(hasItem(DEFAULT_FIREBASE_TOKEN.toString())));
    }


    @Test
    @Transactional
    public void getAlumno() throws Exception {
        // Initialize the database
        alumnoRepository.saveAndFlush(alumno);

        // Get the alumno
        restAlumnoMockMvc.perform(get("/api/alumnos/{id}", alumno.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(alumno.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.apellido").value(DEFAULT_APELLIDO.toString()))
            .andExpect(jsonPath("$.padron").value(DEFAULT_PADRON.toString()))
            .andExpect(jsonPath("$.prioridad").value(DEFAULT_PRIORIDAD))
            .andExpect(jsonPath("$.firebaseToken").value(DEFAULT_FIREBASE_TOKEN.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingAlumno() throws Exception {
        // Get the alumno
        restAlumnoMockMvc.perform(get("/api/alumnos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAlumno() throws Exception {
        // Initialize the database
        alumnoService.save(alumno);

        int databaseSizeBeforeUpdate = alumnoRepository.findAll().size();

        // Update the alumno
        Alumno updatedAlumno = alumnoRepository.findById(alumno.getId()).get();
        // Disconnect from session so that the updates on updatedAlumno are not directly saved in db
        em.detach(updatedAlumno);
        updatedAlumno
            .nombre(UPDATED_NOMBRE)
            .apellido(UPDATED_APELLIDO)
            .padron(UPDATED_PADRON)
            .prioridad(UPDATED_PRIORIDAD)
            .firebaseToken(UPDATED_FIREBASE_TOKEN);

        restAlumnoMockMvc.perform(put("/api/alumnos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAlumno)))
            .andExpect(status().isOk());

        // Validate the Alumno in the database
        List<Alumno> alumnoList = alumnoRepository.findAll();
        assertThat(alumnoList).hasSize(databaseSizeBeforeUpdate);
        Alumno testAlumno = alumnoList.get(alumnoList.size() - 1);
        assertThat(testAlumno.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testAlumno.getApellido()).isEqualTo(UPDATED_APELLIDO);
        assertThat(testAlumno.getPadron()).isEqualTo(UPDATED_PADRON);
        assertThat(testAlumno.getPrioridad()).isEqualTo(UPDATED_PRIORIDAD);
        assertThat(testAlumno.getFirebaseToken()).isEqualTo(UPDATED_FIREBASE_TOKEN);
    }

    @Test
    @Transactional
    public void updateNonExistingAlumno() throws Exception {
        int databaseSizeBeforeUpdate = alumnoRepository.findAll().size();

        // Create the Alumno

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAlumnoMockMvc.perform(put("/api/alumnos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alumno)))
            .andExpect(status().isBadRequest());

        // Validate the Alumno in the database
        List<Alumno> alumnoList = alumnoRepository.findAll();
        assertThat(alumnoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAlumno() throws Exception {
        // Initialize the database
        alumnoService.save(alumno);

        int databaseSizeBeforeDelete = alumnoRepository.findAll().size();

        // Get the alumno
        restAlumnoMockMvc.perform(delete("/api/alumnos/{id}", alumno.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Alumno> alumnoList = alumnoRepository.findAll();
        assertThat(alumnoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Alumno.class);
        Alumno alumno1 = new Alumno();
        alumno1.setId(1L);
        Alumno alumno2 = new Alumno();
        alumno2.setId(alumno1.getId());
        assertThat(alumno1).isEqualTo(alumno2);
        alumno2.setId(2L);
        assertThat(alumno1).isNotEqualTo(alumno2);
        alumno1.setId(null);
        assertThat(alumno1).isNotEqualTo(alumno2);
    }
}
