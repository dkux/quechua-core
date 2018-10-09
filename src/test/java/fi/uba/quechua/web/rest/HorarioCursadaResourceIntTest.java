package fi.uba.quechua.web.rest;

import fi.uba.quechua.QuechuaApp;

import fi.uba.quechua.domain.HorarioCursada;
import fi.uba.quechua.repository.HorarioCursadaRepository;
import fi.uba.quechua.service.HorarioCursadaService;
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

import fi.uba.quechua.domain.enumeration.Dia;
import fi.uba.quechua.domain.enumeration.Sede;
/**
 * Test class for the HorarioCursadaResource REST controller.
 *
 * @see HorarioCursadaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = QuechuaApp.class)
public class HorarioCursadaResourceIntTest {

    private static final Dia DEFAULT_DIA = Dia.LUNES;
    private static final Dia UPDATED_DIA = Dia.MARTES;

    private static final Sede DEFAULT_SEDE = Sede.PC;
    private static final Sede UPDATED_SEDE = Sede.LH;

    private static final String DEFAULT_AULA = "AAAAAAAAAA";
    private static final String UPDATED_AULA = "BBBBBBBBBB";

    private static final String DEFAULT_HORA_INICIO = "AAAAAAAAAA";
    private static final String UPDATED_HORA_INICIO = "BBBBBBBBBB";

    private static final String DEFAULT_HORA_FIN = "AAAAAAAAAA";
    private static final String UPDATED_HORA_FIN = "BBBBBBBBBB";

    @Autowired
    private HorarioCursadaRepository horarioCursadaRepository;

    

    @Autowired
    private HorarioCursadaService horarioCursadaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restHorarioCursadaMockMvc;

    private HorarioCursada horarioCursada;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HorarioCursadaResource horarioCursadaResource = new HorarioCursadaResource(horarioCursadaService);
        this.restHorarioCursadaMockMvc = MockMvcBuilders.standaloneSetup(horarioCursadaResource)
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
    public static HorarioCursada createEntity(EntityManager em) {
        HorarioCursada horarioCursada = new HorarioCursada()
            .dia(DEFAULT_DIA)
            .sede(DEFAULT_SEDE)
            .aula(DEFAULT_AULA)
            .horaInicio(DEFAULT_HORA_INICIO)
            .horaFin(DEFAULT_HORA_FIN);
        return horarioCursada;
    }

    @Before
    public void initTest() {
        horarioCursada = createEntity(em);
    }

    @Test
    @Transactional
    public void createHorarioCursada() throws Exception {
        int databaseSizeBeforeCreate = horarioCursadaRepository.findAll().size();

        // Create the HorarioCursada
        restHorarioCursadaMockMvc.perform(post("/api/horario-cursadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(horarioCursada)))
            .andExpect(status().isCreated());

        // Validate the HorarioCursada in the database
        List<HorarioCursada> horarioCursadaList = horarioCursadaRepository.findAll();
        assertThat(horarioCursadaList).hasSize(databaseSizeBeforeCreate + 1);
        HorarioCursada testHorarioCursada = horarioCursadaList.get(horarioCursadaList.size() - 1);
        assertThat(testHorarioCursada.getDia()).isEqualTo(DEFAULT_DIA);
        assertThat(testHorarioCursada.getSede()).isEqualTo(DEFAULT_SEDE);
        assertThat(testHorarioCursada.getAula()).isEqualTo(DEFAULT_AULA);
        assertThat(testHorarioCursada.getHoraInicio()).isEqualTo(DEFAULT_HORA_INICIO);
        assertThat(testHorarioCursada.getHoraFin()).isEqualTo(DEFAULT_HORA_FIN);
    }

    @Test
    @Transactional
    public void createHorarioCursadaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = horarioCursadaRepository.findAll().size();

        // Create the HorarioCursada with an existing ID
        horarioCursada.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHorarioCursadaMockMvc.perform(post("/api/horario-cursadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(horarioCursada)))
            .andExpect(status().isBadRequest());

        // Validate the HorarioCursada in the database
        List<HorarioCursada> horarioCursadaList = horarioCursadaRepository.findAll();
        assertThat(horarioCursadaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDiaIsRequired() throws Exception {
        int databaseSizeBeforeTest = horarioCursadaRepository.findAll().size();
        // set the field null
        horarioCursada.setDia(null);

        // Create the HorarioCursada, which fails.

        restHorarioCursadaMockMvc.perform(post("/api/horario-cursadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(horarioCursada)))
            .andExpect(status().isBadRequest());

        List<HorarioCursada> horarioCursadaList = horarioCursadaRepository.findAll();
        assertThat(horarioCursadaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSedeIsRequired() throws Exception {
        int databaseSizeBeforeTest = horarioCursadaRepository.findAll().size();
        // set the field null
        horarioCursada.setSede(null);

        // Create the HorarioCursada, which fails.

        restHorarioCursadaMockMvc.perform(post("/api/horario-cursadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(horarioCursada)))
            .andExpect(status().isBadRequest());

        List<HorarioCursada> horarioCursadaList = horarioCursadaRepository.findAll();
        assertThat(horarioCursadaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAulaIsRequired() throws Exception {
        int databaseSizeBeforeTest = horarioCursadaRepository.findAll().size();
        // set the field null
        horarioCursada.setAula(null);

        // Create the HorarioCursada, which fails.

        restHorarioCursadaMockMvc.perform(post("/api/horario-cursadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(horarioCursada)))
            .andExpect(status().isBadRequest());

        List<HorarioCursada> horarioCursadaList = horarioCursadaRepository.findAll();
        assertThat(horarioCursadaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHoraInicioIsRequired() throws Exception {
        int databaseSizeBeforeTest = horarioCursadaRepository.findAll().size();
        // set the field null
        horarioCursada.setHoraInicio(null);

        // Create the HorarioCursada, which fails.

        restHorarioCursadaMockMvc.perform(post("/api/horario-cursadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(horarioCursada)))
            .andExpect(status().isBadRequest());

        List<HorarioCursada> horarioCursadaList = horarioCursadaRepository.findAll();
        assertThat(horarioCursadaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHoraFinIsRequired() throws Exception {
        int databaseSizeBeforeTest = horarioCursadaRepository.findAll().size();
        // set the field null
        horarioCursada.setHoraFin(null);

        // Create the HorarioCursada, which fails.

        restHorarioCursadaMockMvc.perform(post("/api/horario-cursadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(horarioCursada)))
            .andExpect(status().isBadRequest());

        List<HorarioCursada> horarioCursadaList = horarioCursadaRepository.findAll();
        assertThat(horarioCursadaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHorarioCursadas() throws Exception {
        // Initialize the database
        horarioCursadaRepository.saveAndFlush(horarioCursada);

        // Get all the horarioCursadaList
        restHorarioCursadaMockMvc.perform(get("/api/horario-cursadas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(horarioCursada.getId().intValue())))
            .andExpect(jsonPath("$.[*].dia").value(hasItem(DEFAULT_DIA.toString())))
            .andExpect(jsonPath("$.[*].sede").value(hasItem(DEFAULT_SEDE.toString())))
            .andExpect(jsonPath("$.[*].aula").value(hasItem(DEFAULT_AULA.toString())))
            .andExpect(jsonPath("$.[*].horaInicio").value(hasItem(DEFAULT_HORA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].horaFin").value(hasItem(DEFAULT_HORA_FIN.toString())));
    }
    

    @Test
    @Transactional
    public void getHorarioCursada() throws Exception {
        // Initialize the database
        horarioCursadaRepository.saveAndFlush(horarioCursada);

        // Get the horarioCursada
        restHorarioCursadaMockMvc.perform(get("/api/horario-cursadas/{id}", horarioCursada.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(horarioCursada.getId().intValue()))
            .andExpect(jsonPath("$.dia").value(DEFAULT_DIA.toString()))
            .andExpect(jsonPath("$.sede").value(DEFAULT_SEDE.toString()))
            .andExpect(jsonPath("$.aula").value(DEFAULT_AULA.toString()))
            .andExpect(jsonPath("$.horaInicio").value(DEFAULT_HORA_INICIO.toString()))
            .andExpect(jsonPath("$.horaFin").value(DEFAULT_HORA_FIN.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingHorarioCursada() throws Exception {
        // Get the horarioCursada
        restHorarioCursadaMockMvc.perform(get("/api/horario-cursadas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHorarioCursada() throws Exception {
        // Initialize the database
        horarioCursadaService.save(horarioCursada);

        int databaseSizeBeforeUpdate = horarioCursadaRepository.findAll().size();

        // Update the horarioCursada
        HorarioCursada updatedHorarioCursada = horarioCursadaRepository.findById(horarioCursada.getId()).get();
        // Disconnect from session so that the updates on updatedHorarioCursada are not directly saved in db
        em.detach(updatedHorarioCursada);
        updatedHorarioCursada
            .dia(UPDATED_DIA)
            .sede(UPDATED_SEDE)
            .aula(UPDATED_AULA)
            .horaInicio(UPDATED_HORA_INICIO)
            .horaFin(UPDATED_HORA_FIN);

        restHorarioCursadaMockMvc.perform(put("/api/horario-cursadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHorarioCursada)))
            .andExpect(status().isOk());

        // Validate the HorarioCursada in the database
        List<HorarioCursada> horarioCursadaList = horarioCursadaRepository.findAll();
        assertThat(horarioCursadaList).hasSize(databaseSizeBeforeUpdate);
        HorarioCursada testHorarioCursada = horarioCursadaList.get(horarioCursadaList.size() - 1);
        assertThat(testHorarioCursada.getDia()).isEqualTo(UPDATED_DIA);
        assertThat(testHorarioCursada.getSede()).isEqualTo(UPDATED_SEDE);
        assertThat(testHorarioCursada.getAula()).isEqualTo(UPDATED_AULA);
        assertThat(testHorarioCursada.getHoraInicio()).isEqualTo(UPDATED_HORA_INICIO);
        assertThat(testHorarioCursada.getHoraFin()).isEqualTo(UPDATED_HORA_FIN);
    }

    @Test
    @Transactional
    public void updateNonExistingHorarioCursada() throws Exception {
        int databaseSizeBeforeUpdate = horarioCursadaRepository.findAll().size();

        // Create the HorarioCursada

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restHorarioCursadaMockMvc.perform(put("/api/horario-cursadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(horarioCursada)))
            .andExpect(status().isBadRequest());

        // Validate the HorarioCursada in the database
        List<HorarioCursada> horarioCursadaList = horarioCursadaRepository.findAll();
        assertThat(horarioCursadaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHorarioCursada() throws Exception {
        // Initialize the database
        horarioCursadaService.save(horarioCursada);

        int databaseSizeBeforeDelete = horarioCursadaRepository.findAll().size();

        // Get the horarioCursada
        restHorarioCursadaMockMvc.perform(delete("/api/horario-cursadas/{id}", horarioCursada.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<HorarioCursada> horarioCursadaList = horarioCursadaRepository.findAll();
        assertThat(horarioCursadaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HorarioCursada.class);
        HorarioCursada horarioCursada1 = new HorarioCursada();
        horarioCursada1.setId(1L);
        HorarioCursada horarioCursada2 = new HorarioCursada();
        horarioCursada2.setId(horarioCursada1.getId());
        assertThat(horarioCursada1).isEqualTo(horarioCursada2);
        horarioCursada2.setId(2L);
        assertThat(horarioCursada1).isNotEqualTo(horarioCursada2);
        horarioCursada1.setId(null);
        assertThat(horarioCursada1).isNotEqualTo(horarioCursada2);
    }
}
