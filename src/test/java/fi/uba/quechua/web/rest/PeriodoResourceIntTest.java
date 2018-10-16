package fi.uba.quechua.web.rest;

import fi.uba.quechua.QuechuaApp;

import fi.uba.quechua.domain.Periodo;
import fi.uba.quechua.repository.PeriodoRepository;
import fi.uba.quechua.service.PeriodoService;
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

import fi.uba.quechua.domain.enumeration.Cuatrimestre;
/**
 * Test class for the PeriodoResource REST controller.
 *
 * @see PeriodoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = QuechuaApp.class)
public class PeriodoResourceIntTest {

    private static final Cuatrimestre DEFAULT_CUATRIMESTRE = Cuatrimestre.PRIMERO;
    private static final Cuatrimestre UPDATED_CUATRIMESTRE = Cuatrimestre.SEGUNDO;

    private static final String DEFAULT_ANO = "AAAAAAAAAA";
    private static final String UPDATED_ANO = "BBBBBBBBBB";

    @Autowired
    private PeriodoRepository periodoRepository;



    @Autowired
    private PeriodoService periodoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPeriodoMockMvc;

    private Periodo periodo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PeriodoResource periodoResource = new PeriodoResource(periodoService);
        this.restPeriodoMockMvc = MockMvcBuilders.standaloneSetup(periodoResource)
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
    public static Periodo createEntity(EntityManager em) {
        Periodo periodo = new Periodo()
            .cuatrimestre(DEFAULT_CUATRIMESTRE)
            .anio(DEFAULT_ANO);
        return periodo;
    }

    @Before
    public void initTest() {
        periodo = createEntity(em);
    }

    @Test
    @Transactional
    public void createPeriodo() throws Exception {
        int databaseSizeBeforeCreate = periodoRepository.findAll().size();

        // Create the Periodo
        restPeriodoMockMvc.perform(post("/api/periodos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(periodo)))
            .andExpect(status().isCreated());

        // Validate the Periodo in the database
        List<Periodo> periodoList = periodoRepository.findAll();
        assertThat(periodoList).hasSize(databaseSizeBeforeCreate + 1);
        Periodo testPeriodo = periodoList.get(periodoList.size() - 1);
        assertThat(testPeriodo.getCuatrimestre()).isEqualTo(DEFAULT_CUATRIMESTRE);
        assertThat(testPeriodo.getAnio()).isEqualTo(DEFAULT_ANO);
    }

    @Test
    @Transactional
    public void createPeriodoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = periodoRepository.findAll().size();

        // Create the Periodo with an existing ID
        periodo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPeriodoMockMvc.perform(post("/api/periodos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(periodo)))
            .andExpect(status().isBadRequest());

        // Validate the Periodo in the database
        List<Periodo> periodoList = periodoRepository.findAll();
        assertThat(periodoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCuatrimestreIsRequired() throws Exception {
        int databaseSizeBeforeTest = periodoRepository.findAll().size();
        // set the field null
        periodo.setCuatrimestre(null);

        // Create the Periodo, which fails.

        restPeriodoMockMvc.perform(post("/api/periodos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(periodo)))
            .andExpect(status().isBadRequest());

        List<Periodo> periodoList = periodoRepository.findAll();
        assertThat(periodoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAnoIsRequired() throws Exception {
        int databaseSizeBeforeTest = periodoRepository.findAll().size();
        // set the field null
        periodo.setAnio(null);

        // Create the Periodo, which fails.

        restPeriodoMockMvc.perform(post("/api/periodos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(periodo)))
            .andExpect(status().isBadRequest());

        List<Periodo> periodoList = periodoRepository.findAll();
        assertThat(periodoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPeriodos() throws Exception {
        // Initialize the database
        periodoRepository.saveAndFlush(periodo);

        // Get all the periodoList
        restPeriodoMockMvc.perform(get("/api/periodos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(periodo.getId().intValue())))
            .andExpect(jsonPath("$.[*].cuatrimestre").value(hasItem(DEFAULT_CUATRIMESTRE.toString())))
            .andExpect(jsonPath("$.[*].ano").value(hasItem(DEFAULT_ANO.toString())));
    }


    @Test
    @Transactional
    public void getPeriodo() throws Exception {
        // Initialize the database
        periodoRepository.saveAndFlush(periodo);

        // Get the periodo
        restPeriodoMockMvc.perform(get("/api/periodos/{id}", periodo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(periodo.getId().intValue()))
            .andExpect(jsonPath("$.cuatrimestre").value(DEFAULT_CUATRIMESTRE.toString()))
            .andExpect(jsonPath("$.ano").value(DEFAULT_ANO.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingPeriodo() throws Exception {
        // Get the periodo
        restPeriodoMockMvc.perform(get("/api/periodos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePeriodo() throws Exception {
        // Initialize the database
        periodoService.save(periodo);

        int databaseSizeBeforeUpdate = periodoRepository.findAll().size();

        // Update the periodo
        Periodo updatedPeriodo = periodoRepository.findById(periodo.getId()).get();
        // Disconnect from session so that the updates on updatedPeriodo are not directly saved in db
        em.detach(updatedPeriodo);
        updatedPeriodo
            .cuatrimestre(UPDATED_CUATRIMESTRE)
            .anio(UPDATED_ANO);

        restPeriodoMockMvc.perform(put("/api/periodos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPeriodo)))
            .andExpect(status().isOk());

        // Validate the Periodo in the database
        List<Periodo> periodoList = periodoRepository.findAll();
        assertThat(periodoList).hasSize(databaseSizeBeforeUpdate);
        Periodo testPeriodo = periodoList.get(periodoList.size() - 1);
        assertThat(testPeriodo.getCuatrimestre()).isEqualTo(UPDATED_CUATRIMESTRE);
        assertThat(testPeriodo.getAnio()).isEqualTo(UPDATED_ANO);
    }

    @Test
    @Transactional
    public void updateNonExistingPeriodo() throws Exception {
        int databaseSizeBeforeUpdate = periodoRepository.findAll().size();

        // Create the Periodo

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPeriodoMockMvc.perform(put("/api/periodos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(periodo)))
            .andExpect(status().isBadRequest());

        // Validate the Periodo in the database
        List<Periodo> periodoList = periodoRepository.findAll();
        assertThat(periodoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePeriodo() throws Exception {
        // Initialize the database
        periodoService.save(periodo);

        int databaseSizeBeforeDelete = periodoRepository.findAll().size();

        // Get the periodo
        restPeriodoMockMvc.perform(delete("/api/periodos/{id}", periodo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Periodo> periodoList = periodoRepository.findAll();
        assertThat(periodoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Periodo.class);
        Periodo periodo1 = new Periodo();
        periodo1.setId(1L);
        Periodo periodo2 = new Periodo();
        periodo2.setId(periodo1.getId());
        assertThat(periodo1).isEqualTo(periodo2);
        periodo2.setId(2L);
        assertThat(periodo1).isNotEqualTo(periodo2);
        periodo1.setId(null);
        assertThat(periodo1).isNotEqualTo(periodo2);
    }
}
