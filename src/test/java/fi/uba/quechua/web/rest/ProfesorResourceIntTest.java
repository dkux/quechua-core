package fi.uba.quechua.web.rest;

import fi.uba.quechua.QuechuaApp;

import fi.uba.quechua.domain.Profesor;
import fi.uba.quechua.repository.ProfesorRepository;
import fi.uba.quechua.service.CursoService;
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
 * Test class for the ProfesorResource REST controller.
 *
 * @see ProfesorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = QuechuaApp.class)
public class ProfesorResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO = "BBBBBBBBBB";

    @Autowired
    private ProfesorRepository profesorRepository;

    @Autowired
    UserService userService;

    @Autowired
    CursoService cursoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProfesorMockMvc;

    private Profesor profesor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProfesorResource profesorResource = new ProfesorResource(profesorRepository, userService, cursoService);
        this.restProfesorMockMvc = MockMvcBuilders.standaloneSetup(profesorResource)
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
    public static Profesor createEntity(EntityManager em) {
        Profesor profesor = new Profesor()
            .nombre(DEFAULT_NOMBRE)
            .apellido(DEFAULT_APELLIDO);
        return profesor;
    }

    @Before
    public void initTest() {
        profesor = createEntity(em);
    }

    @Test
    @Transactional
    public void createProfesor() throws Exception {
        int databaseSizeBeforeCreate = profesorRepository.findAll().size();

        // Create the Profesor
        restProfesorMockMvc.perform(post("/api/profesors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profesor)))
            .andExpect(status().isCreated());

        // Validate the Profesor in the database
        List<Profesor> profesorList = profesorRepository.findAll();
        assertThat(profesorList).hasSize(databaseSizeBeforeCreate + 1);
        Profesor testProfesor = profesorList.get(profesorList.size() - 1);
        assertThat(testProfesor.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testProfesor.getApellido()).isEqualTo(DEFAULT_APELLIDO);
    }

    @Test
    @Transactional
    public void createProfesorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = profesorRepository.findAll().size();

        // Create the Profesor with an existing ID
        profesor.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfesorMockMvc.perform(post("/api/profesors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profesor)))
            .andExpect(status().isBadRequest());

        // Validate the Profesor in the database
        List<Profesor> profesorList = profesorRepository.findAll();
        assertThat(profesorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = profesorRepository.findAll().size();
        // set the field null
        profesor.setNombre(null);

        // Create the Profesor, which fails.

        restProfesorMockMvc.perform(post("/api/profesors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profesor)))
            .andExpect(status().isBadRequest());

        List<Profesor> profesorList = profesorRepository.findAll();
        assertThat(profesorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkApellidoIsRequired() throws Exception {
        int databaseSizeBeforeTest = profesorRepository.findAll().size();
        // set the field null
        profesor.setApellido(null);

        // Create the Profesor, which fails.

        restProfesorMockMvc.perform(post("/api/profesors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profesor)))
            .andExpect(status().isBadRequest());

        List<Profesor> profesorList = profesorRepository.findAll();
        assertThat(profesorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProfesors() throws Exception {
        // Initialize the database
        profesorRepository.saveAndFlush(profesor);

        // Get all the profesorList
        restProfesorMockMvc.perform(get("/api/profesors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profesor.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].apellido").value(hasItem(DEFAULT_APELLIDO.toString())));
    }


    @Test
    @Transactional
    public void getProfesor() throws Exception {
        // Initialize the database
        profesorRepository.saveAndFlush(profesor);

        // Get the profesor
        restProfesorMockMvc.perform(get("/api/profesors/{id}", profesor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(profesor.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.apellido").value(DEFAULT_APELLIDO.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingProfesor() throws Exception {
        // Get the profesor
        restProfesorMockMvc.perform(get("/api/profesors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProfesor() throws Exception {
        // Initialize the database
        profesorRepository.saveAndFlush(profesor);

        int databaseSizeBeforeUpdate = profesorRepository.findAll().size();

        // Update the profesor
        Profesor updatedProfesor = profesorRepository.findById(profesor.getId()).get();
        // Disconnect from session so that the updates on updatedProfesor are not directly saved in db
        em.detach(updatedProfesor);
        updatedProfesor
            .nombre(UPDATED_NOMBRE)
            .apellido(UPDATED_APELLIDO);

        restProfesorMockMvc.perform(put("/api/profesors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProfesor)))
            .andExpect(status().isOk());

        // Validate the Profesor in the database
        List<Profesor> profesorList = profesorRepository.findAll();
        assertThat(profesorList).hasSize(databaseSizeBeforeUpdate);
        Profesor testProfesor = profesorList.get(profesorList.size() - 1);
        assertThat(testProfesor.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testProfesor.getApellido()).isEqualTo(UPDATED_APELLIDO);
    }

    @Test
    @Transactional
    public void updateNonExistingProfesor() throws Exception {
        int databaseSizeBeforeUpdate = profesorRepository.findAll().size();

        // Create the Profesor

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProfesorMockMvc.perform(put("/api/profesors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profesor)))
            .andExpect(status().isBadRequest());

        // Validate the Profesor in the database
        List<Profesor> profesorList = profesorRepository.findAll();
        assertThat(profesorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProfesor() throws Exception {
        // Initialize the database
        profesorRepository.saveAndFlush(profesor);

        int databaseSizeBeforeDelete = profesorRepository.findAll().size();

        // Get the profesor
        restProfesorMockMvc.perform(delete("/api/profesors/{id}", profesor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Profesor> profesorList = profesorRepository.findAll();
        assertThat(profesorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Profesor.class);
        Profesor profesor1 = new Profesor();
        profesor1.setId(1L);
        Profesor profesor2 = new Profesor();
        profesor2.setId(profesor1.getId());
        assertThat(profesor1).isEqualTo(profesor2);
        profesor2.setId(2L);
        assertThat(profesor1).isNotEqualTo(profesor2);
        profesor1.setId(null);
        assertThat(profesor1).isNotEqualTo(profesor2);
    }
}
