package fi.uba.quechua.web.rest;

import fi.uba.quechua.QuechuaApp;

import fi.uba.quechua.domain.InscripcionColoquio;
import fi.uba.quechua.domain.Cursada;
import fi.uba.quechua.repository.InscripcionColoquioRepository;
import fi.uba.quechua.service.AlumnoService;
import fi.uba.quechua.service.ColoquioService;
import fi.uba.quechua.service.InscripcionColoquioService;
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

import fi.uba.quechua.domain.enumeration.InscripcionColoquioEstado;
/**
 * Test class for the InscripcionColoquioResource REST controller.
 *
 * @see InscripcionColoquioResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = QuechuaApp.class)
public class InscripcionColoquioResourceIntTest {

    private static final InscripcionColoquioEstado DEFAULT_ESTADO = InscripcionColoquioEstado.ACTIVA;
    private static final InscripcionColoquioEstado UPDATED_ESTADO = InscripcionColoquioEstado.ELIMINADA;

    @Autowired
    private InscripcionColoquioRepository inscripcionColoquioRepository;

    @Autowired
    private InscripcionColoquioService inscripcionColoquioService;

    @Autowired
    private UserService userService;

    @Autowired
    private ColoquioService coloquioService;

    @Autowired
    private AlumnoService alumnoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restInscripcionColoquioMockMvc;

    private InscripcionColoquio inscripcionColoquio;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InscripcionColoquioResource inscripcionColoquioResource = new InscripcionColoquioResource(inscripcionColoquioService,
            userService, coloquioService, alumnoService);
        this.restInscripcionColoquioMockMvc = MockMvcBuilders.standaloneSetup(inscripcionColoquioResource)
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
    public static InscripcionColoquio createEntity(EntityManager em) {
        InscripcionColoquio inscripcionColoquio = new InscripcionColoquio()
            .estado(DEFAULT_ESTADO);
        // Add required entity
        Cursada cursada = CursadaResourceIntTest.createEntity(em);
        em.persist(cursada);
        em.flush();
        inscripcionColoquio.setCursada(cursada);
        return inscripcionColoquio;
    }

    @Before
    public void initTest() {
        inscripcionColoquio = createEntity(em);
    }

    @Test
    @Transactional
    public void createInscripcionColoquio() throws Exception {
        int databaseSizeBeforeCreate = inscripcionColoquioRepository.findAll().size();

        // Create the InscripcionColoquio
        restInscripcionColoquioMockMvc.perform(post("/api/inscripcion-coloquios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inscripcionColoquio)))
            .andExpect(status().isCreated());

        // Validate the InscripcionColoquio in the database
        List<InscripcionColoquio> inscripcionColoquioList = inscripcionColoquioRepository.findAll();
        assertThat(inscripcionColoquioList).hasSize(databaseSizeBeforeCreate + 1);
        InscripcionColoquio testInscripcionColoquio = inscripcionColoquioList.get(inscripcionColoquioList.size() - 1);
        assertThat(testInscripcionColoquio.getEstado()).isEqualTo(DEFAULT_ESTADO);
    }

    @Test
    @Transactional
    public void createInscripcionColoquioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = inscripcionColoquioRepository.findAll().size();

        // Create the InscripcionColoquio with an existing ID
        inscripcionColoquio.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInscripcionColoquioMockMvc.perform(post("/api/inscripcion-coloquios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inscripcionColoquio)))
            .andExpect(status().isBadRequest());

        // Validate the InscripcionColoquio in the database
        List<InscripcionColoquio> inscripcionColoquioList = inscripcionColoquioRepository.findAll();
        assertThat(inscripcionColoquioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllInscripcionColoquios() throws Exception {
        // Initialize the database
        inscripcionColoquioRepository.saveAndFlush(inscripcionColoquio);

        // Get all the inscripcionColoquioList
        restInscripcionColoquioMockMvc.perform(get("/api/inscripcion-coloquios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inscripcionColoquio.getId().intValue())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())));
    }


    @Test
    @Transactional
    public void getInscripcionColoquio() throws Exception {
        // Initialize the database
        inscripcionColoquioRepository.saveAndFlush(inscripcionColoquio);

        // Get the inscripcionColoquio
        restInscripcionColoquioMockMvc.perform(get("/api/inscripcion-coloquios/{id}", inscripcionColoquio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(inscripcionColoquio.getId().intValue()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingInscripcionColoquio() throws Exception {
        // Get the inscripcionColoquio
        restInscripcionColoquioMockMvc.perform(get("/api/inscripcion-coloquios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInscripcionColoquio() throws Exception {
        // Initialize the database
        inscripcionColoquioService.save(inscripcionColoquio);

        int databaseSizeBeforeUpdate = inscripcionColoquioRepository.findAll().size();

        // Update the inscripcionColoquio
        InscripcionColoquio updatedInscripcionColoquio = inscripcionColoquioRepository.findById(inscripcionColoquio.getId()).get();
        // Disconnect from session so that the updates on updatedInscripcionColoquio are not directly saved in db
        em.detach(updatedInscripcionColoquio);
        updatedInscripcionColoquio
            .estado(UPDATED_ESTADO);

        restInscripcionColoquioMockMvc.perform(put("/api/inscripcion-coloquios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInscripcionColoquio)))
            .andExpect(status().isOk());

        // Validate the InscripcionColoquio in the database
        List<InscripcionColoquio> inscripcionColoquioList = inscripcionColoquioRepository.findAll();
        assertThat(inscripcionColoquioList).hasSize(databaseSizeBeforeUpdate);
        InscripcionColoquio testInscripcionColoquio = inscripcionColoquioList.get(inscripcionColoquioList.size() - 1);
        assertThat(testInscripcionColoquio.getEstado()).isEqualTo(UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void updateNonExistingInscripcionColoquio() throws Exception {
        int databaseSizeBeforeUpdate = inscripcionColoquioRepository.findAll().size();

        // Create the InscripcionColoquio

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restInscripcionColoquioMockMvc.perform(put("/api/inscripcion-coloquios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inscripcionColoquio)))
            .andExpect(status().isBadRequest());

        // Validate the InscripcionColoquio in the database
        List<InscripcionColoquio> inscripcionColoquioList = inscripcionColoquioRepository.findAll();
        assertThat(inscripcionColoquioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInscripcionColoquio() throws Exception {
        // Initialize the database
        inscripcionColoquioService.save(inscripcionColoquio);

        int databaseSizeBeforeDelete = inscripcionColoquioRepository.findAll().size();

        // Get the inscripcionColoquio
        restInscripcionColoquioMockMvc.perform(delete("/api/inscripcion-coloquios/{id}", inscripcionColoquio.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<InscripcionColoquio> inscripcionColoquioList = inscripcionColoquioRepository.findAll();
        assertThat(inscripcionColoquioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InscripcionColoquio.class);
        InscripcionColoquio inscripcionColoquio1 = new InscripcionColoquio();
        inscripcionColoquio1.setId(1L);
        InscripcionColoquio inscripcionColoquio2 = new InscripcionColoquio();
        inscripcionColoquio2.setId(inscripcionColoquio1.getId());
        assertThat(inscripcionColoquio1).isEqualTo(inscripcionColoquio2);
        inscripcionColoquio2.setId(2L);
        assertThat(inscripcionColoquio1).isNotEqualTo(inscripcionColoquio2);
        inscripcionColoquio1.setId(null);
        assertThat(inscripcionColoquio1).isNotEqualTo(inscripcionColoquio2);
    }
}
