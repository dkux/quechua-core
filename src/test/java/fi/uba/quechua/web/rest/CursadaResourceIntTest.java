package fi.uba.quechua.web.rest;

import fi.uba.quechua.QuechuaApp;

import fi.uba.quechua.domain.Cursada;
import fi.uba.quechua.domain.Periodo;
import fi.uba.quechua.repository.CursadaRepository;
import fi.uba.quechua.service.CursadaService;
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

import fi.uba.quechua.domain.enumeration.EstadoCursada;
/**
 * Test class for the CursadaResource REST controller.
 *
 * @see CursadaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = QuechuaApp.class)
public class CursadaResourceIntTest {

    private static final Float DEFAULT_NOTA_CURSADA = 1F;
    private static final Float UPDATED_NOTA_CURSADA = 2F;

    private static final String DEFAULT_LIBRO = "AAAAAAAAAA";
    private static final String UPDATED_LIBRO = "BBBBBBBBBB";

    private static final String DEFAULT_FOLIO = "AAAAAAAAAA";
    private static final String UPDATED_FOLIO = "BBBBBBBBBB";

    private static final EstadoCursada DEFAULT_ESTADO = EstadoCursada.APROBADO;
    private static final EstadoCursada UPDATED_ESTADO = EstadoCursada.REPROBADO;

    private static final Float DEFAULT_NOTA_FINAL = 1F;
    private static final Float UPDATED_NOTA_FINAL = 2F;

    @Autowired
    private CursadaRepository cursadaRepository;

    

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

    private MockMvc restCursadaMockMvc;

    private Cursada cursada;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CursadaResource cursadaResource = new CursadaResource(cursadaService);
        this.restCursadaMockMvc = MockMvcBuilders.standaloneSetup(cursadaResource)
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
    public static Cursada createEntity(EntityManager em) {
        Cursada cursada = new Cursada()
            .notaCursada(DEFAULT_NOTA_CURSADA)
            .libro(DEFAULT_LIBRO)
            .folio(DEFAULT_FOLIO)
            .estado(DEFAULT_ESTADO)
            .notaFinal(DEFAULT_NOTA_FINAL);
        // Add required entity
        Periodo periodo = PeriodoResourceIntTest.createEntity(em);
        em.persist(periodo);
        em.flush();
        cursada.setPeriodo(periodo);
        return cursada;
    }

    @Before
    public void initTest() {
        cursada = createEntity(em);
    }

    @Test
    @Transactional
    public void createCursada() throws Exception {
        int databaseSizeBeforeCreate = cursadaRepository.findAll().size();

        // Create the Cursada
        restCursadaMockMvc.perform(post("/api/cursadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cursada)))
            .andExpect(status().isCreated());

        // Validate the Cursada in the database
        List<Cursada> cursadaList = cursadaRepository.findAll();
        assertThat(cursadaList).hasSize(databaseSizeBeforeCreate + 1);
        Cursada testCursada = cursadaList.get(cursadaList.size() - 1);
        assertThat(testCursada.getNotaCursada()).isEqualTo(DEFAULT_NOTA_CURSADA);
        assertThat(testCursada.getLibro()).isEqualTo(DEFAULT_LIBRO);
        assertThat(testCursada.getFolio()).isEqualTo(DEFAULT_FOLIO);
        assertThat(testCursada.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testCursada.getNotaFinal()).isEqualTo(DEFAULT_NOTA_FINAL);
    }

    @Test
    @Transactional
    public void createCursadaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cursadaRepository.findAll().size();

        // Create the Cursada with an existing ID
        cursada.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCursadaMockMvc.perform(post("/api/cursadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cursada)))
            .andExpect(status().isBadRequest());

        // Validate the Cursada in the database
        List<Cursada> cursadaList = cursadaRepository.findAll();
        assertThat(cursadaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCursadas() throws Exception {
        // Initialize the database
        cursadaRepository.saveAndFlush(cursada);

        // Get all the cursadaList
        restCursadaMockMvc.perform(get("/api/cursadas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cursada.getId().intValue())))
            .andExpect(jsonPath("$.[*].notaCursada").value(hasItem(DEFAULT_NOTA_CURSADA.doubleValue())))
            .andExpect(jsonPath("$.[*].libro").value(hasItem(DEFAULT_LIBRO.toString())))
            .andExpect(jsonPath("$.[*].folio").value(hasItem(DEFAULT_FOLIO.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())))
            .andExpect(jsonPath("$.[*].notaFinal").value(hasItem(DEFAULT_NOTA_FINAL.doubleValue())));
    }
    

    @Test
    @Transactional
    public void getCursada() throws Exception {
        // Initialize the database
        cursadaRepository.saveAndFlush(cursada);

        // Get the cursada
        restCursadaMockMvc.perform(get("/api/cursadas/{id}", cursada.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cursada.getId().intValue()))
            .andExpect(jsonPath("$.notaCursada").value(DEFAULT_NOTA_CURSADA.doubleValue()))
            .andExpect(jsonPath("$.libro").value(DEFAULT_LIBRO.toString()))
            .andExpect(jsonPath("$.folio").value(DEFAULT_FOLIO.toString()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()))
            .andExpect(jsonPath("$.notaFinal").value(DEFAULT_NOTA_FINAL.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingCursada() throws Exception {
        // Get the cursada
        restCursadaMockMvc.perform(get("/api/cursadas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCursada() throws Exception {
        // Initialize the database
        cursadaService.save(cursada);

        int databaseSizeBeforeUpdate = cursadaRepository.findAll().size();

        // Update the cursada
        Cursada updatedCursada = cursadaRepository.findById(cursada.getId()).get();
        // Disconnect from session so that the updates on updatedCursada are not directly saved in db
        em.detach(updatedCursada);
        updatedCursada
            .notaCursada(UPDATED_NOTA_CURSADA)
            .libro(UPDATED_LIBRO)
            .folio(UPDATED_FOLIO)
            .estado(UPDATED_ESTADO)
            .notaFinal(UPDATED_NOTA_FINAL);

        restCursadaMockMvc.perform(put("/api/cursadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCursada)))
            .andExpect(status().isOk());

        // Validate the Cursada in the database
        List<Cursada> cursadaList = cursadaRepository.findAll();
        assertThat(cursadaList).hasSize(databaseSizeBeforeUpdate);
        Cursada testCursada = cursadaList.get(cursadaList.size() - 1);
        assertThat(testCursada.getNotaCursada()).isEqualTo(UPDATED_NOTA_CURSADA);
        assertThat(testCursada.getLibro()).isEqualTo(UPDATED_LIBRO);
        assertThat(testCursada.getFolio()).isEqualTo(UPDATED_FOLIO);
        assertThat(testCursada.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testCursada.getNotaFinal()).isEqualTo(UPDATED_NOTA_FINAL);
    }

    @Test
    @Transactional
    public void updateNonExistingCursada() throws Exception {
        int databaseSizeBeforeUpdate = cursadaRepository.findAll().size();

        // Create the Cursada

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCursadaMockMvc.perform(put("/api/cursadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cursada)))
            .andExpect(status().isBadRequest());

        // Validate the Cursada in the database
        List<Cursada> cursadaList = cursadaRepository.findAll();
        assertThat(cursadaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCursada() throws Exception {
        // Initialize the database
        cursadaService.save(cursada);

        int databaseSizeBeforeDelete = cursadaRepository.findAll().size();

        // Get the cursada
        restCursadaMockMvc.perform(delete("/api/cursadas/{id}", cursada.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Cursada> cursadaList = cursadaRepository.findAll();
        assertThat(cursadaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cursada.class);
        Cursada cursada1 = new Cursada();
        cursada1.setId(1L);
        Cursada cursada2 = new Cursada();
        cursada2.setId(cursada1.getId());
        assertThat(cursada1).isEqualTo(cursada2);
        cursada2.setId(2L);
        assertThat(cursada1).isNotEqualTo(cursada2);
        cursada1.setId(null);
        assertThat(cursada1).isNotEqualTo(cursada2);
    }
}
