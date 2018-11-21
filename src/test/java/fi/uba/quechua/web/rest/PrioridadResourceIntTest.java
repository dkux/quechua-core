package fi.uba.quechua.web.rest;

import fi.uba.quechua.QuechuaApp;

import fi.uba.quechua.domain.Prioridad;
import fi.uba.quechua.domain.Periodo;
import fi.uba.quechua.repository.PrioridadRepository;
import fi.uba.quechua.service.PrioridadService;
import fi.uba.quechua.web.rest.errors.ExceptionTranslator;
import fi.uba.quechua.service.dto.PrioridadCriteria;
import fi.uba.quechua.service.PrioridadQueryService;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;


import static fi.uba.quechua.web.rest.TestUtil.sameInstant;
import static fi.uba.quechua.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PrioridadResource REST controller.
 *
 * @see PrioridadResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = QuechuaApp.class)
public class PrioridadResourceIntTest {

    private static final ZonedDateTime DEFAULT_FECHA_HABILITACION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA_HABILITACION = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private PrioridadRepository prioridadRepository;



    @Autowired
    private PrioridadService prioridadService;

    @Autowired
    private PrioridadQueryService prioridadQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPrioridadMockMvc;

    private Prioridad prioridad;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PrioridadResource prioridadResource = new PrioridadResource(prioridadService, prioridadQueryService);
        this.restPrioridadMockMvc = MockMvcBuilders.standaloneSetup(prioridadResource)
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
    public static Prioridad createEntity(EntityManager em) {
        Prioridad prioridad = new Prioridad()
            .fecha_habilitacion(DEFAULT_FECHA_HABILITACION);
        // Add required entity
        Periodo periodo = PeriodoResourceIntTest.createEntity(em);
        em.persist(periodo);
        em.flush();
        prioridad.setPeriodo(periodo);
        return prioridad;
    }

    @Before
    public void initTest() {
        prioridad = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrioridad() throws Exception {
        int databaseSizeBeforeCreate = prioridadRepository.findAll().size();

        // Create the Prioridad
        restPrioridadMockMvc.perform(post("/api/prioridads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prioridad)))
            .andExpect(status().isCreated());

        // Validate the Prioridad in the database
        List<Prioridad> prioridadList = prioridadRepository.findAll();
        assertThat(prioridadList).hasSize(databaseSizeBeforeCreate + 1);
        Prioridad testPrioridad = prioridadList.get(prioridadList.size() - 1);
        assertThat(testPrioridad.getFecha_habilitacion()).isEqualTo(DEFAULT_FECHA_HABILITACION);
    }

    @Test
    @Transactional
    public void createPrioridadWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = prioridadRepository.findAll().size();

        // Create the Prioridad with an existing ID
        prioridad.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrioridadMockMvc.perform(post("/api/prioridads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prioridad)))
            .andExpect(status().isBadRequest());

        // Validate the Prioridad in the database
        List<Prioridad> prioridadList = prioridadRepository.findAll();
        assertThat(prioridadList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFecha_habilitacionIsRequired() throws Exception {
        int databaseSizeBeforeTest = prioridadRepository.findAll().size();
        // set the field null
        prioridad.setFecha_habilitacion(null);

        // Create the Prioridad, which fails.

        restPrioridadMockMvc.perform(post("/api/prioridads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prioridad)))
            .andExpect(status().isBadRequest());

        List<Prioridad> prioridadList = prioridadRepository.findAll();
        assertThat(prioridadList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPrioridads() throws Exception {
        // Initialize the database
        prioridadRepository.saveAndFlush(prioridad);

        // Get all the prioridadList
        restPrioridadMockMvc.perform(get("/api/prioridads?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prioridad.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha_habilitacion").value(hasItem(sameInstant(DEFAULT_FECHA_HABILITACION))));
    }


    @Test
    @Transactional
    public void getPrioridad() throws Exception {
        // Initialize the database
        prioridadRepository.saveAndFlush(prioridad);

        // Get the prioridad
        restPrioridadMockMvc.perform(get("/api/prioridads/{id}", prioridad.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(prioridad.getId().intValue()))
            .andExpect(jsonPath("$.fecha_habilitacion").value(sameInstant(DEFAULT_FECHA_HABILITACION)));
    }

    @Test
    @Transactional
    public void getAllPrioridadsByFecha_habilitacionIsEqualToSomething() throws Exception {
        // Initialize the database
        prioridadRepository.saveAndFlush(prioridad);

        // Get all the prioridadList where fecha_habilitacion equals to DEFAULT_FECHA_HABILITACION
        defaultPrioridadShouldBeFound("fecha_habilitacion.equals=" + DEFAULT_FECHA_HABILITACION);

        // Get all the prioridadList where fecha_habilitacion equals to UPDATED_FECHA_HABILITACION
        defaultPrioridadShouldNotBeFound("fecha_habilitacion.equals=" + UPDATED_FECHA_HABILITACION);
    }

    @Test
    @Transactional
    public void getAllPrioridadsByFecha_habilitacionIsInShouldWork() throws Exception {
        // Initialize the database
        prioridadRepository.saveAndFlush(prioridad);

        // Get all the prioridadList where fecha_habilitacion in DEFAULT_FECHA_HABILITACION or UPDATED_FECHA_HABILITACION
        defaultPrioridadShouldBeFound("fecha_habilitacion.in=" + DEFAULT_FECHA_HABILITACION + "," + UPDATED_FECHA_HABILITACION);

        // Get all the prioridadList where fecha_habilitacion equals to UPDATED_FECHA_HABILITACION
        defaultPrioridadShouldNotBeFound("fecha_habilitacion.in=" + UPDATED_FECHA_HABILITACION);
    }

    @Test
    @Transactional
    public void getAllPrioridadsByFecha_habilitacionIsNullOrNotNull() throws Exception {
        // Initialize the database
        prioridadRepository.saveAndFlush(prioridad);

        // Get all the prioridadList where fecha_habilitacion is not null
        defaultPrioridadShouldBeFound("fecha_habilitacion.specified=true");

        // Get all the prioridadList where fecha_habilitacion is null
        defaultPrioridadShouldNotBeFound("fecha_habilitacion.specified=false");
    }

    @Test
    @Transactional
    public void getAllPrioridadsByFecha_habilitacionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        prioridadRepository.saveAndFlush(prioridad);

        // Get all the prioridadList where fecha_habilitacion greater than or equals to DEFAULT_FECHA_HABILITACION
        defaultPrioridadShouldBeFound("fecha_habilitacion.greaterOrEqualThan=" + DEFAULT_FECHA_HABILITACION);

        // Get all the prioridadList where fecha_habilitacion greater than or equals to UPDATED_FECHA_HABILITACION
        defaultPrioridadShouldNotBeFound("fecha_habilitacion.greaterOrEqualThan=" + UPDATED_FECHA_HABILITACION);
    }

    @Test
    @Transactional
    public void getAllPrioridadsByFecha_habilitacionIsLessThanSomething() throws Exception {
        // Initialize the database
        prioridadRepository.saveAndFlush(prioridad);

        // Get all the prioridadList where fecha_habilitacion less than or equals to DEFAULT_FECHA_HABILITACION
        defaultPrioridadShouldNotBeFound("fecha_habilitacion.lessThan=" + DEFAULT_FECHA_HABILITACION);

        // Get all the prioridadList where fecha_habilitacion less than or equals to UPDATED_FECHA_HABILITACION
        defaultPrioridadShouldBeFound("fecha_habilitacion.lessThan=" + UPDATED_FECHA_HABILITACION);
    }


    @Test
    @Transactional
    public void getAllPrioridadsByPeriodoIsEqualToSomething() throws Exception {
        // Initialize the database
        Periodo periodo = PeriodoResourceIntTest.createEntity(em);
        em.persist(periodo);
        em.flush();
        prioridad.setPeriodo(periodo);
        prioridadRepository.saveAndFlush(prioridad);
        Long periodoId = periodo.getId();

        // Get all the prioridadList where periodo equals to periodoId
        defaultPrioridadShouldBeFound("periodoId.equals=" + periodoId);

        // Get all the prioridadList where periodo equals to periodoId + 1
        defaultPrioridadShouldNotBeFound("periodoId.equals=" + (periodoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultPrioridadShouldBeFound(String filter) throws Exception {
        restPrioridadMockMvc.perform(get("/api/prioridads?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prioridad.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha_habilitacion").value(hasItem(sameInstant(DEFAULT_FECHA_HABILITACION))));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultPrioridadShouldNotBeFound(String filter) throws Exception {
        restPrioridadMockMvc.perform(get("/api/prioridads?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingPrioridad() throws Exception {
        // Get the prioridad
        restPrioridadMockMvc.perform(get("/api/prioridads/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrioridad() throws Exception {
        // Initialize the database
        prioridadService.save(prioridad);

        int databaseSizeBeforeUpdate = prioridadRepository.findAll().size();

        // Update the prioridad
        Prioridad updatedPrioridad = prioridadRepository.findById(prioridad.getId()).get();
        // Disconnect from session so that the updates on updatedPrioridad are not directly saved in db
        em.detach(updatedPrioridad);
        updatedPrioridad
            .fecha_habilitacion(UPDATED_FECHA_HABILITACION);

        restPrioridadMockMvc.perform(put("/api/prioridads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPrioridad)))
            .andExpect(status().isOk());

        // Validate the Prioridad in the database
        List<Prioridad> prioridadList = prioridadRepository.findAll();
        assertThat(prioridadList).hasSize(databaseSizeBeforeUpdate);
        Prioridad testPrioridad = prioridadList.get(prioridadList.size() - 1);
        assertThat(testPrioridad.getFecha_habilitacion()).isEqualTo(UPDATED_FECHA_HABILITACION);
    }

    @Test
    @Transactional
    public void updateNonExistingPrioridad() throws Exception {
        int databaseSizeBeforeUpdate = prioridadRepository.findAll().size();

        // Create the Prioridad

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPrioridadMockMvc.perform(put("/api/prioridads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prioridad)))
            .andExpect(status().isBadRequest());

        // Validate the Prioridad in the database
        List<Prioridad> prioridadList = prioridadRepository.findAll();
        assertThat(prioridadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePrioridad() throws Exception {
        // Initialize the database
        prioridadService.save(prioridad);

        int databaseSizeBeforeDelete = prioridadRepository.findAll().size();

        // Get the prioridad
        restPrioridadMockMvc.perform(delete("/api/prioridads/{id}", prioridad.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Prioridad> prioridadList = prioridadRepository.findAll();
        assertThat(prioridadList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Prioridad.class);
        Prioridad prioridad1 = new Prioridad();
        prioridad1.setId(1L);
        Prioridad prioridad2 = new Prioridad();
        prioridad2.setId(prioridad1.getId());
        assertThat(prioridad1).isEqualTo(prioridad2);
        prioridad2.setId(2L);
        assertThat(prioridad1).isNotEqualTo(prioridad2);
        assertThat(prioridad1).isNotEqualTo(prioridad2);
    }
}
