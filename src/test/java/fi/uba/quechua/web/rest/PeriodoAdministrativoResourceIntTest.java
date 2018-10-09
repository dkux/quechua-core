package fi.uba.quechua.web.rest;

import fi.uba.quechua.QuechuaApp;

import fi.uba.quechua.domain.PeriodoAdministrativo;
import fi.uba.quechua.repository.PeriodoAdministrativoRepository;
import fi.uba.quechua.service.PeriodoAdministrativoService;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static fi.uba.quechua.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PeriodoAdministrativoResource REST controller.
 *
 * @see PeriodoAdministrativoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = QuechuaApp.class)
public class PeriodoAdministrativoResourceIntTest {

    private static final LocalDate DEFAULT_FECHA_INICIO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_INICIO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FECHA_FIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_FIN = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_CONSULTAR_PIORIDAD = false;
    private static final Boolean UPDATED_CONSULTAR_PIORIDAD = true;

    private static final Boolean DEFAULT_INSCRIBIR_CURSADA = false;
    private static final Boolean UPDATED_INSCRIBIR_CURSADA = true;

    private static final Boolean DEFAULT_INSCRIBIR_COLOQUIO = false;
    private static final Boolean UPDATED_INSCRIBIR_COLOQUIO = true;

    @Autowired
    private PeriodoAdministrativoRepository periodoAdministrativoRepository;

    

    @Autowired
    private PeriodoAdministrativoService periodoAdministrativoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPeriodoAdministrativoMockMvc;

    private PeriodoAdministrativo periodoAdministrativo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PeriodoAdministrativoResource periodoAdministrativoResource = new PeriodoAdministrativoResource(periodoAdministrativoService);
        this.restPeriodoAdministrativoMockMvc = MockMvcBuilders.standaloneSetup(periodoAdministrativoResource)
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
    public static PeriodoAdministrativo createEntity(EntityManager em) {
        PeriodoAdministrativo periodoAdministrativo = new PeriodoAdministrativo()
            .fechaInicio(DEFAULT_FECHA_INICIO)
            .fechaFin(DEFAULT_FECHA_FIN)
            .consultarPioridad(DEFAULT_CONSULTAR_PIORIDAD)
            .inscribirCursada(DEFAULT_INSCRIBIR_CURSADA)
            .inscribirColoquio(DEFAULT_INSCRIBIR_COLOQUIO);
        return periodoAdministrativo;
    }

    @Before
    public void initTest() {
        periodoAdministrativo = createEntity(em);
    }

    @Test
    @Transactional
    public void createPeriodoAdministrativo() throws Exception {
        int databaseSizeBeforeCreate = periodoAdministrativoRepository.findAll().size();

        // Create the PeriodoAdministrativo
        restPeriodoAdministrativoMockMvc.perform(post("/api/periodo-administrativos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(periodoAdministrativo)))
            .andExpect(status().isCreated());

        // Validate the PeriodoAdministrativo in the database
        List<PeriodoAdministrativo> periodoAdministrativoList = periodoAdministrativoRepository.findAll();
        assertThat(periodoAdministrativoList).hasSize(databaseSizeBeforeCreate + 1);
        PeriodoAdministrativo testPeriodoAdministrativo = periodoAdministrativoList.get(periodoAdministrativoList.size() - 1);
        assertThat(testPeriodoAdministrativo.getFechaInicio()).isEqualTo(DEFAULT_FECHA_INICIO);
        assertThat(testPeriodoAdministrativo.getFechaFin()).isEqualTo(DEFAULT_FECHA_FIN);
        assertThat(testPeriodoAdministrativo.isConsultarPioridad()).isEqualTo(DEFAULT_CONSULTAR_PIORIDAD);
        assertThat(testPeriodoAdministrativo.isInscribirCursada()).isEqualTo(DEFAULT_INSCRIBIR_CURSADA);
        assertThat(testPeriodoAdministrativo.isInscribirColoquio()).isEqualTo(DEFAULT_INSCRIBIR_COLOQUIO);
    }

    @Test
    @Transactional
    public void createPeriodoAdministrativoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = periodoAdministrativoRepository.findAll().size();

        // Create the PeriodoAdministrativo with an existing ID
        periodoAdministrativo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPeriodoAdministrativoMockMvc.perform(post("/api/periodo-administrativos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(periodoAdministrativo)))
            .andExpect(status().isBadRequest());

        // Validate the PeriodoAdministrativo in the database
        List<PeriodoAdministrativo> periodoAdministrativoList = periodoAdministrativoRepository.findAll();
        assertThat(periodoAdministrativoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFechaInicioIsRequired() throws Exception {
        int databaseSizeBeforeTest = periodoAdministrativoRepository.findAll().size();
        // set the field null
        periodoAdministrativo.setFechaInicio(null);

        // Create the PeriodoAdministrativo, which fails.

        restPeriodoAdministrativoMockMvc.perform(post("/api/periodo-administrativos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(periodoAdministrativo)))
            .andExpect(status().isBadRequest());

        List<PeriodoAdministrativo> periodoAdministrativoList = periodoAdministrativoRepository.findAll();
        assertThat(periodoAdministrativoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaFinIsRequired() throws Exception {
        int databaseSizeBeforeTest = periodoAdministrativoRepository.findAll().size();
        // set the field null
        periodoAdministrativo.setFechaFin(null);

        // Create the PeriodoAdministrativo, which fails.

        restPeriodoAdministrativoMockMvc.perform(post("/api/periodo-administrativos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(periodoAdministrativo)))
            .andExpect(status().isBadRequest());

        List<PeriodoAdministrativo> periodoAdministrativoList = periodoAdministrativoRepository.findAll();
        assertThat(periodoAdministrativoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPeriodoAdministrativos() throws Exception {
        // Initialize the database
        periodoAdministrativoRepository.saveAndFlush(periodoAdministrativo);

        // Get all the periodoAdministrativoList
        restPeriodoAdministrativoMockMvc.perform(get("/api/periodo-administrativos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(periodoAdministrativo.getId().intValue())))
            .andExpect(jsonPath("$.[*].fechaInicio").value(hasItem(DEFAULT_FECHA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].fechaFin").value(hasItem(DEFAULT_FECHA_FIN.toString())))
            .andExpect(jsonPath("$.[*].consultarPioridad").value(hasItem(DEFAULT_CONSULTAR_PIORIDAD.booleanValue())))
            .andExpect(jsonPath("$.[*].inscribirCursada").value(hasItem(DEFAULT_INSCRIBIR_CURSADA.booleanValue())))
            .andExpect(jsonPath("$.[*].inscribirColoquio").value(hasItem(DEFAULT_INSCRIBIR_COLOQUIO.booleanValue())));
    }
    

    @Test
    @Transactional
    public void getPeriodoAdministrativo() throws Exception {
        // Initialize the database
        periodoAdministrativoRepository.saveAndFlush(periodoAdministrativo);

        // Get the periodoAdministrativo
        restPeriodoAdministrativoMockMvc.perform(get("/api/periodo-administrativos/{id}", periodoAdministrativo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(periodoAdministrativo.getId().intValue()))
            .andExpect(jsonPath("$.fechaInicio").value(DEFAULT_FECHA_INICIO.toString()))
            .andExpect(jsonPath("$.fechaFin").value(DEFAULT_FECHA_FIN.toString()))
            .andExpect(jsonPath("$.consultarPioridad").value(DEFAULT_CONSULTAR_PIORIDAD.booleanValue()))
            .andExpect(jsonPath("$.inscribirCursada").value(DEFAULT_INSCRIBIR_CURSADA.booleanValue()))
            .andExpect(jsonPath("$.inscribirColoquio").value(DEFAULT_INSCRIBIR_COLOQUIO.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingPeriodoAdministrativo() throws Exception {
        // Get the periodoAdministrativo
        restPeriodoAdministrativoMockMvc.perform(get("/api/periodo-administrativos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePeriodoAdministrativo() throws Exception {
        // Initialize the database
        periodoAdministrativoService.save(periodoAdministrativo);

        int databaseSizeBeforeUpdate = periodoAdministrativoRepository.findAll().size();

        // Update the periodoAdministrativo
        PeriodoAdministrativo updatedPeriodoAdministrativo = periodoAdministrativoRepository.findById(periodoAdministrativo.getId()).get();
        // Disconnect from session so that the updates on updatedPeriodoAdministrativo are not directly saved in db
        em.detach(updatedPeriodoAdministrativo);
        updatedPeriodoAdministrativo
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaFin(UPDATED_FECHA_FIN)
            .consultarPioridad(UPDATED_CONSULTAR_PIORIDAD)
            .inscribirCursada(UPDATED_INSCRIBIR_CURSADA)
            .inscribirColoquio(UPDATED_INSCRIBIR_COLOQUIO);

        restPeriodoAdministrativoMockMvc.perform(put("/api/periodo-administrativos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPeriodoAdministrativo)))
            .andExpect(status().isOk());

        // Validate the PeriodoAdministrativo in the database
        List<PeriodoAdministrativo> periodoAdministrativoList = periodoAdministrativoRepository.findAll();
        assertThat(periodoAdministrativoList).hasSize(databaseSizeBeforeUpdate);
        PeriodoAdministrativo testPeriodoAdministrativo = periodoAdministrativoList.get(periodoAdministrativoList.size() - 1);
        assertThat(testPeriodoAdministrativo.getFechaInicio()).isEqualTo(UPDATED_FECHA_INICIO);
        assertThat(testPeriodoAdministrativo.getFechaFin()).isEqualTo(UPDATED_FECHA_FIN);
        assertThat(testPeriodoAdministrativo.isConsultarPioridad()).isEqualTo(UPDATED_CONSULTAR_PIORIDAD);
        assertThat(testPeriodoAdministrativo.isInscribirCursada()).isEqualTo(UPDATED_INSCRIBIR_CURSADA);
        assertThat(testPeriodoAdministrativo.isInscribirColoquio()).isEqualTo(UPDATED_INSCRIBIR_COLOQUIO);
    }

    @Test
    @Transactional
    public void updateNonExistingPeriodoAdministrativo() throws Exception {
        int databaseSizeBeforeUpdate = periodoAdministrativoRepository.findAll().size();

        // Create the PeriodoAdministrativo

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPeriodoAdministrativoMockMvc.perform(put("/api/periodo-administrativos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(periodoAdministrativo)))
            .andExpect(status().isBadRequest());

        // Validate the PeriodoAdministrativo in the database
        List<PeriodoAdministrativo> periodoAdministrativoList = periodoAdministrativoRepository.findAll();
        assertThat(periodoAdministrativoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePeriodoAdministrativo() throws Exception {
        // Initialize the database
        periodoAdministrativoService.save(periodoAdministrativo);

        int databaseSizeBeforeDelete = periodoAdministrativoRepository.findAll().size();

        // Get the periodoAdministrativo
        restPeriodoAdministrativoMockMvc.perform(delete("/api/periodo-administrativos/{id}", periodoAdministrativo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PeriodoAdministrativo> periodoAdministrativoList = periodoAdministrativoRepository.findAll();
        assertThat(periodoAdministrativoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PeriodoAdministrativo.class);
        PeriodoAdministrativo periodoAdministrativo1 = new PeriodoAdministrativo();
        periodoAdministrativo1.setId(1L);
        PeriodoAdministrativo periodoAdministrativo2 = new PeriodoAdministrativo();
        periodoAdministrativo2.setId(periodoAdministrativo1.getId());
        assertThat(periodoAdministrativo1).isEqualTo(periodoAdministrativo2);
        periodoAdministrativo2.setId(2L);
        assertThat(periodoAdministrativo1).isNotEqualTo(periodoAdministrativo2);
        periodoAdministrativo1.setId(null);
        assertThat(periodoAdministrativo1).isNotEqualTo(periodoAdministrativo2);
    }
}
