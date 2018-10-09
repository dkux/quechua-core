package fi.uba.quechua.web.rest;

import fi.uba.quechua.QuechuaApp;

import fi.uba.quechua.domain.AlumnoCarrera;
import fi.uba.quechua.repository.AlumnoCarreraRepository;
import fi.uba.quechua.service.AlumnoCarreraService;
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
 * Test class for the AlumnoCarreraResource REST controller.
 *
 * @see AlumnoCarreraResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = QuechuaApp.class)
public class AlumnoCarreraResourceIntTest {

    @Autowired
    private AlumnoCarreraRepository alumnoCarreraRepository;

    

    @Autowired
    private AlumnoCarreraService alumnoCarreraService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAlumnoCarreraMockMvc;

    private AlumnoCarrera alumnoCarrera;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AlumnoCarreraResource alumnoCarreraResource = new AlumnoCarreraResource(alumnoCarreraService);
        this.restAlumnoCarreraMockMvc = MockMvcBuilders.standaloneSetup(alumnoCarreraResource)
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
    public static AlumnoCarrera createEntity(EntityManager em) {
        AlumnoCarrera alumnoCarrera = new AlumnoCarrera();
        return alumnoCarrera;
    }

    @Before
    public void initTest() {
        alumnoCarrera = createEntity(em);
    }

    @Test
    @Transactional
    public void createAlumnoCarrera() throws Exception {
        int databaseSizeBeforeCreate = alumnoCarreraRepository.findAll().size();

        // Create the AlumnoCarrera
        restAlumnoCarreraMockMvc.perform(post("/api/alumno-carreras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alumnoCarrera)))
            .andExpect(status().isCreated());

        // Validate the AlumnoCarrera in the database
        List<AlumnoCarrera> alumnoCarreraList = alumnoCarreraRepository.findAll();
        assertThat(alumnoCarreraList).hasSize(databaseSizeBeforeCreate + 1);
        AlumnoCarrera testAlumnoCarrera = alumnoCarreraList.get(alumnoCarreraList.size() - 1);
    }

    @Test
    @Transactional
    public void createAlumnoCarreraWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = alumnoCarreraRepository.findAll().size();

        // Create the AlumnoCarrera with an existing ID
        alumnoCarrera.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlumnoCarreraMockMvc.perform(post("/api/alumno-carreras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alumnoCarrera)))
            .andExpect(status().isBadRequest());

        // Validate the AlumnoCarrera in the database
        List<AlumnoCarrera> alumnoCarreraList = alumnoCarreraRepository.findAll();
        assertThat(alumnoCarreraList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAlumnoCarreras() throws Exception {
        // Initialize the database
        alumnoCarreraRepository.saveAndFlush(alumnoCarrera);

        // Get all the alumnoCarreraList
        restAlumnoCarreraMockMvc.perform(get("/api/alumno-carreras?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alumnoCarrera.getId().intValue())));
    }
    

    @Test
    @Transactional
    public void getAlumnoCarrera() throws Exception {
        // Initialize the database
        alumnoCarreraRepository.saveAndFlush(alumnoCarrera);

        // Get the alumnoCarrera
        restAlumnoCarreraMockMvc.perform(get("/api/alumno-carreras/{id}", alumnoCarrera.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(alumnoCarrera.getId().intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingAlumnoCarrera() throws Exception {
        // Get the alumnoCarrera
        restAlumnoCarreraMockMvc.perform(get("/api/alumno-carreras/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAlumnoCarrera() throws Exception {
        // Initialize the database
        alumnoCarreraService.save(alumnoCarrera);

        int databaseSizeBeforeUpdate = alumnoCarreraRepository.findAll().size();

        // Update the alumnoCarrera
        AlumnoCarrera updatedAlumnoCarrera = alumnoCarreraRepository.findById(alumnoCarrera.getId()).get();
        // Disconnect from session so that the updates on updatedAlumnoCarrera are not directly saved in db
        em.detach(updatedAlumnoCarrera);

        restAlumnoCarreraMockMvc.perform(put("/api/alumno-carreras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAlumnoCarrera)))
            .andExpect(status().isOk());

        // Validate the AlumnoCarrera in the database
        List<AlumnoCarrera> alumnoCarreraList = alumnoCarreraRepository.findAll();
        assertThat(alumnoCarreraList).hasSize(databaseSizeBeforeUpdate);
        AlumnoCarrera testAlumnoCarrera = alumnoCarreraList.get(alumnoCarreraList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingAlumnoCarrera() throws Exception {
        int databaseSizeBeforeUpdate = alumnoCarreraRepository.findAll().size();

        // Create the AlumnoCarrera

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAlumnoCarreraMockMvc.perform(put("/api/alumno-carreras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alumnoCarrera)))
            .andExpect(status().isBadRequest());

        // Validate the AlumnoCarrera in the database
        List<AlumnoCarrera> alumnoCarreraList = alumnoCarreraRepository.findAll();
        assertThat(alumnoCarreraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAlumnoCarrera() throws Exception {
        // Initialize the database
        alumnoCarreraService.save(alumnoCarrera);

        int databaseSizeBeforeDelete = alumnoCarreraRepository.findAll().size();

        // Get the alumnoCarrera
        restAlumnoCarreraMockMvc.perform(delete("/api/alumno-carreras/{id}", alumnoCarrera.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AlumnoCarrera> alumnoCarreraList = alumnoCarreraRepository.findAll();
        assertThat(alumnoCarreraList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AlumnoCarrera.class);
        AlumnoCarrera alumnoCarrera1 = new AlumnoCarrera();
        alumnoCarrera1.setId(1L);
        AlumnoCarrera alumnoCarrera2 = new AlumnoCarrera();
        alumnoCarrera2.setId(alumnoCarrera1.getId());
        assertThat(alumnoCarrera1).isEqualTo(alumnoCarrera2);
        alumnoCarrera2.setId(2L);
        assertThat(alumnoCarrera1).isNotEqualTo(alumnoCarrera2);
        alumnoCarrera1.setId(null);
        assertThat(alumnoCarrera1).isNotEqualTo(alumnoCarrera2);
    }
}
