package fi.uba.quechua.web.rest;

import fi.uba.quechua.QuechuaApp;

import fi.uba.quechua.domain.Coloquio;
import fi.uba.quechua.repository.ColoquioRepository;
import fi.uba.quechua.service.ColoquioService;
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
 * Test class for the ColoquioResource REST controller.
 *
 * @see ColoquioResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = QuechuaApp.class)
public class ColoquioResourceIntTest {

    private static final Dia DEFAULT_DIA = Dia.LUNES;
    private static final Dia UPDATED_DIA = Dia.MARTES;

    private static final String DEFAULT_AULA = "AAAAAAAAAA";
    private static final String UPDATED_AULA = "BBBBBBBBBB";

    private static final String DEFAULT_HORA_INICIO = "AAAAAAAAAA";
    private static final String UPDATED_HORA_INICIO = "BBBBBBBBBB";

    private static final String DEFAULT_HORA_FIN = "AAAAAAAAAA";
    private static final String UPDATED_HORA_FIN = "BBBBBBBBBB";

    private static final Sede DEFAULT_SEDE = Sede.PC;
    private static final Sede UPDATED_SEDE = Sede.LH;

    @Autowired
    private ColoquioRepository coloquioRepository;

    

    @Autowired
    private ColoquioService coloquioService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restColoquioMockMvc;

    private Coloquio coloquio;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ColoquioResource coloquioResource = new ColoquioResource(coloquioService);
        this.restColoquioMockMvc = MockMvcBuilders.standaloneSetup(coloquioResource)
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
    public static Coloquio createEntity(EntityManager em) {
        Coloquio coloquio = new Coloquio()
            .dia(DEFAULT_DIA)
            .aula(DEFAULT_AULA)
            .horaInicio(DEFAULT_HORA_INICIO)
            .horaFin(DEFAULT_HORA_FIN)
            .sede(DEFAULT_SEDE);
        return coloquio;
    }

    @Before
    public void initTest() {
        coloquio = createEntity(em);
    }

    @Test
    @Transactional
    public void createColoquio() throws Exception {
        int databaseSizeBeforeCreate = coloquioRepository.findAll().size();

        // Create the Coloquio
        restColoquioMockMvc.perform(post("/api/coloquios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coloquio)))
            .andExpect(status().isCreated());

        // Validate the Coloquio in the database
        List<Coloquio> coloquioList = coloquioRepository.findAll();
        assertThat(coloquioList).hasSize(databaseSizeBeforeCreate + 1);
        Coloquio testColoquio = coloquioList.get(coloquioList.size() - 1);
        assertThat(testColoquio.getDia()).isEqualTo(DEFAULT_DIA);
        assertThat(testColoquio.getAula()).isEqualTo(DEFAULT_AULA);
        assertThat(testColoquio.getHoraInicio()).isEqualTo(DEFAULT_HORA_INICIO);
        assertThat(testColoquio.getHoraFin()).isEqualTo(DEFAULT_HORA_FIN);
        assertThat(testColoquio.getSede()).isEqualTo(DEFAULT_SEDE);
    }

    @Test
    @Transactional
    public void createColoquioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = coloquioRepository.findAll().size();

        // Create the Coloquio with an existing ID
        coloquio.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restColoquioMockMvc.perform(post("/api/coloquios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coloquio)))
            .andExpect(status().isBadRequest());

        // Validate the Coloquio in the database
        List<Coloquio> coloquioList = coloquioRepository.findAll();
        assertThat(coloquioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDiaIsRequired() throws Exception {
        int databaseSizeBeforeTest = coloquioRepository.findAll().size();
        // set the field null
        coloquio.setDia(null);

        // Create the Coloquio, which fails.

        restColoquioMockMvc.perform(post("/api/coloquios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coloquio)))
            .andExpect(status().isBadRequest());

        List<Coloquio> coloquioList = coloquioRepository.findAll();
        assertThat(coloquioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAulaIsRequired() throws Exception {
        int databaseSizeBeforeTest = coloquioRepository.findAll().size();
        // set the field null
        coloquio.setAula(null);

        // Create the Coloquio, which fails.

        restColoquioMockMvc.perform(post("/api/coloquios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coloquio)))
            .andExpect(status().isBadRequest());

        List<Coloquio> coloquioList = coloquioRepository.findAll();
        assertThat(coloquioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHoraInicioIsRequired() throws Exception {
        int databaseSizeBeforeTest = coloquioRepository.findAll().size();
        // set the field null
        coloquio.setHoraInicio(null);

        // Create the Coloquio, which fails.

        restColoquioMockMvc.perform(post("/api/coloquios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coloquio)))
            .andExpect(status().isBadRequest());

        List<Coloquio> coloquioList = coloquioRepository.findAll();
        assertThat(coloquioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHoraFinIsRequired() throws Exception {
        int databaseSizeBeforeTest = coloquioRepository.findAll().size();
        // set the field null
        coloquio.setHoraFin(null);

        // Create the Coloquio, which fails.

        restColoquioMockMvc.perform(post("/api/coloquios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coloquio)))
            .andExpect(status().isBadRequest());

        List<Coloquio> coloquioList = coloquioRepository.findAll();
        assertThat(coloquioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSedeIsRequired() throws Exception {
        int databaseSizeBeforeTest = coloquioRepository.findAll().size();
        // set the field null
        coloquio.setSede(null);

        // Create the Coloquio, which fails.

        restColoquioMockMvc.perform(post("/api/coloquios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coloquio)))
            .andExpect(status().isBadRequest());

        List<Coloquio> coloquioList = coloquioRepository.findAll();
        assertThat(coloquioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllColoquios() throws Exception {
        // Initialize the database
        coloquioRepository.saveAndFlush(coloquio);

        // Get all the coloquioList
        restColoquioMockMvc.perform(get("/api/coloquios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(coloquio.getId().intValue())))
            .andExpect(jsonPath("$.[*].dia").value(hasItem(DEFAULT_DIA.toString())))
            .andExpect(jsonPath("$.[*].aula").value(hasItem(DEFAULT_AULA.toString())))
            .andExpect(jsonPath("$.[*].horaInicio").value(hasItem(DEFAULT_HORA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].horaFin").value(hasItem(DEFAULT_HORA_FIN.toString())))
            .andExpect(jsonPath("$.[*].sede").value(hasItem(DEFAULT_SEDE.toString())));
    }
    

    @Test
    @Transactional
    public void getColoquio() throws Exception {
        // Initialize the database
        coloquioRepository.saveAndFlush(coloquio);

        // Get the coloquio
        restColoquioMockMvc.perform(get("/api/coloquios/{id}", coloquio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(coloquio.getId().intValue()))
            .andExpect(jsonPath("$.dia").value(DEFAULT_DIA.toString()))
            .andExpect(jsonPath("$.aula").value(DEFAULT_AULA.toString()))
            .andExpect(jsonPath("$.horaInicio").value(DEFAULT_HORA_INICIO.toString()))
            .andExpect(jsonPath("$.horaFin").value(DEFAULT_HORA_FIN.toString()))
            .andExpect(jsonPath("$.sede").value(DEFAULT_SEDE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingColoquio() throws Exception {
        // Get the coloquio
        restColoquioMockMvc.perform(get("/api/coloquios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateColoquio() throws Exception {
        // Initialize the database
        coloquioService.save(coloquio);

        int databaseSizeBeforeUpdate = coloquioRepository.findAll().size();

        // Update the coloquio
        Coloquio updatedColoquio = coloquioRepository.findById(coloquio.getId()).get();
        // Disconnect from session so that the updates on updatedColoquio are not directly saved in db
        em.detach(updatedColoquio);
        updatedColoquio
            .dia(UPDATED_DIA)
            .aula(UPDATED_AULA)
            .horaInicio(UPDATED_HORA_INICIO)
            .horaFin(UPDATED_HORA_FIN)
            .sede(UPDATED_SEDE);

        restColoquioMockMvc.perform(put("/api/coloquios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedColoquio)))
            .andExpect(status().isOk());

        // Validate the Coloquio in the database
        List<Coloquio> coloquioList = coloquioRepository.findAll();
        assertThat(coloquioList).hasSize(databaseSizeBeforeUpdate);
        Coloquio testColoquio = coloquioList.get(coloquioList.size() - 1);
        assertThat(testColoquio.getDia()).isEqualTo(UPDATED_DIA);
        assertThat(testColoquio.getAula()).isEqualTo(UPDATED_AULA);
        assertThat(testColoquio.getHoraInicio()).isEqualTo(UPDATED_HORA_INICIO);
        assertThat(testColoquio.getHoraFin()).isEqualTo(UPDATED_HORA_FIN);
        assertThat(testColoquio.getSede()).isEqualTo(UPDATED_SEDE);
    }

    @Test
    @Transactional
    public void updateNonExistingColoquio() throws Exception {
        int databaseSizeBeforeUpdate = coloquioRepository.findAll().size();

        // Create the Coloquio

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restColoquioMockMvc.perform(put("/api/coloquios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coloquio)))
            .andExpect(status().isBadRequest());

        // Validate the Coloquio in the database
        List<Coloquio> coloquioList = coloquioRepository.findAll();
        assertThat(coloquioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteColoquio() throws Exception {
        // Initialize the database
        coloquioService.save(coloquio);

        int databaseSizeBeforeDelete = coloquioRepository.findAll().size();

        // Get the coloquio
        restColoquioMockMvc.perform(delete("/api/coloquios/{id}", coloquio.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Coloquio> coloquioList = coloquioRepository.findAll();
        assertThat(coloquioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Coloquio.class);
        Coloquio coloquio1 = new Coloquio();
        coloquio1.setId(1L);
        Coloquio coloquio2 = new Coloquio();
        coloquio2.setId(coloquio1.getId());
        assertThat(coloquio1).isEqualTo(coloquio2);
        coloquio2.setId(2L);
        assertThat(coloquio1).isNotEqualTo(coloquio2);
        coloquio1.setId(null);
        assertThat(coloquio1).isNotEqualTo(coloquio2);
    }
}
