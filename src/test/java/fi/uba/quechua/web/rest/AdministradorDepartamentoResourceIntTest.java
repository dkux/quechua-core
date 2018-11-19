package fi.uba.quechua.web.rest;

import fi.uba.quechua.QuechuaApp;

import fi.uba.quechua.domain.AdministradorDepartamento;
import fi.uba.quechua.repository.AdministradorDepartamentoRepository;
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
 * Test class for the AdministradorDepartamentoResource REST controller.
 *
 * @see AdministradorDepartamentoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = QuechuaApp.class)
public class AdministradorDepartamentoResourceIntTest {

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    private static final Long DEFAULT_DEPARTAMENTO_ID = 1L;
    private static final Long UPDATED_DEPARTAMENTO_ID = 2L;

    @Autowired
    private AdministradorDepartamentoRepository administradorDepartamentoRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAdministradorDepartamentoMockMvc;

    private AdministradorDepartamento administradorDepartamento;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AdministradorDepartamentoResource administradorDepartamentoResource = new AdministradorDepartamentoResource(administradorDepartamentoRepository);
        this.restAdministradorDepartamentoMockMvc = MockMvcBuilders.standaloneSetup(administradorDepartamentoResource)
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
    public static AdministradorDepartamento createEntity(EntityManager em) {
        AdministradorDepartamento administradorDepartamento = new AdministradorDepartamento()
            .userId(DEFAULT_USER_ID)
            .departamentoId(DEFAULT_DEPARTAMENTO_ID);
        return administradorDepartamento;
    }

    @Before
    public void initTest() {
        administradorDepartamento = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdministradorDepartamento() throws Exception {
        int databaseSizeBeforeCreate = administradorDepartamentoRepository.findAll().size();

        // Create the AdministradorDepartamento
        restAdministradorDepartamentoMockMvc.perform(post("/api/administrador-departamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(administradorDepartamento)))
            .andExpect(status().isCreated());

        // Validate the AdministradorDepartamento in the database
        List<AdministradorDepartamento> administradorDepartamentoList = administradorDepartamentoRepository.findAll();
        assertThat(administradorDepartamentoList).hasSize(databaseSizeBeforeCreate + 1);
        AdministradorDepartamento testAdministradorDepartamento = administradorDepartamentoList.get(administradorDepartamentoList.size() - 1);
        assertThat(testAdministradorDepartamento.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testAdministradorDepartamento.getDepartamentoId()).isEqualTo(DEFAULT_DEPARTAMENTO_ID);
    }

    @Test
    @Transactional
    public void createAdministradorDepartamentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = administradorDepartamentoRepository.findAll().size();

        // Create the AdministradorDepartamento with an existing ID
        administradorDepartamento.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdministradorDepartamentoMockMvc.perform(post("/api/administrador-departamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(administradorDepartamento)))
            .andExpect(status().isBadRequest());

        // Validate the AdministradorDepartamento in the database
        List<AdministradorDepartamento> administradorDepartamentoList = administradorDepartamentoRepository.findAll();
        assertThat(administradorDepartamentoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = administradorDepartamentoRepository.findAll().size();
        // set the field null
        administradorDepartamento.setUserId(null);

        // Create the AdministradorDepartamento, which fails.

        restAdministradorDepartamentoMockMvc.perform(post("/api/administrador-departamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(administradorDepartamento)))
            .andExpect(status().isBadRequest());

        List<AdministradorDepartamento> administradorDepartamentoList = administradorDepartamentoRepository.findAll();
        assertThat(administradorDepartamentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDepartamentoIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = administradorDepartamentoRepository.findAll().size();
        // set the field null
        administradorDepartamento.setDepartamentoId(null);

        // Create the AdministradorDepartamento, which fails.

        restAdministradorDepartamentoMockMvc.perform(post("/api/administrador-departamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(administradorDepartamento)))
            .andExpect(status().isBadRequest());

        List<AdministradorDepartamento> administradorDepartamentoList = administradorDepartamentoRepository.findAll();
        assertThat(administradorDepartamentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAdministradorDepartamentos() throws Exception {
        // Initialize the database
        administradorDepartamentoRepository.saveAndFlush(administradorDepartamento);

        // Get all the administradorDepartamentoList
        restAdministradorDepartamentoMockMvc.perform(get("/api/administrador-departamentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(administradorDepartamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].departamentoId").value(hasItem(DEFAULT_DEPARTAMENTO_ID.intValue())));
    }
    

    @Test
    @Transactional
    public void getAdministradorDepartamento() throws Exception {
        // Initialize the database
        administradorDepartamentoRepository.saveAndFlush(administradorDepartamento);

        // Get the administradorDepartamento
        restAdministradorDepartamentoMockMvc.perform(get("/api/administrador-departamentos/{id}", administradorDepartamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(administradorDepartamento.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.departamentoId").value(DEFAULT_DEPARTAMENTO_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingAdministradorDepartamento() throws Exception {
        // Get the administradorDepartamento
        restAdministradorDepartamentoMockMvc.perform(get("/api/administrador-departamentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdministradorDepartamento() throws Exception {
        // Initialize the database
        administradorDepartamentoRepository.saveAndFlush(administradorDepartamento);

        int databaseSizeBeforeUpdate = administradorDepartamentoRepository.findAll().size();

        // Update the administradorDepartamento
        AdministradorDepartamento updatedAdministradorDepartamento = administradorDepartamentoRepository.findById(administradorDepartamento.getId()).get();
        // Disconnect from session so that the updates on updatedAdministradorDepartamento are not directly saved in db
        em.detach(updatedAdministradorDepartamento);
        updatedAdministradorDepartamento
            .userId(UPDATED_USER_ID)
            .departamentoId(UPDATED_DEPARTAMENTO_ID);

        restAdministradorDepartamentoMockMvc.perform(put("/api/administrador-departamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAdministradorDepartamento)))
            .andExpect(status().isOk());

        // Validate the AdministradorDepartamento in the database
        List<AdministradorDepartamento> administradorDepartamentoList = administradorDepartamentoRepository.findAll();
        assertThat(administradorDepartamentoList).hasSize(databaseSizeBeforeUpdate);
        AdministradorDepartamento testAdministradorDepartamento = administradorDepartamentoList.get(administradorDepartamentoList.size() - 1);
        assertThat(testAdministradorDepartamento.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testAdministradorDepartamento.getDepartamentoId()).isEqualTo(UPDATED_DEPARTAMENTO_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingAdministradorDepartamento() throws Exception {
        int databaseSizeBeforeUpdate = administradorDepartamentoRepository.findAll().size();

        // Create the AdministradorDepartamento

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAdministradorDepartamentoMockMvc.perform(put("/api/administrador-departamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(administradorDepartamento)))
            .andExpect(status().isBadRequest());

        // Validate the AdministradorDepartamento in the database
        List<AdministradorDepartamento> administradorDepartamentoList = administradorDepartamentoRepository.findAll();
        assertThat(administradorDepartamentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAdministradorDepartamento() throws Exception {
        // Initialize the database
        administradorDepartamentoRepository.saveAndFlush(administradorDepartamento);

        int databaseSizeBeforeDelete = administradorDepartamentoRepository.findAll().size();

        // Get the administradorDepartamento
        restAdministradorDepartamentoMockMvc.perform(delete("/api/administrador-departamentos/{id}", administradorDepartamento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AdministradorDepartamento> administradorDepartamentoList = administradorDepartamentoRepository.findAll();
        assertThat(administradorDepartamentoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdministradorDepartamento.class);
        AdministradorDepartamento administradorDepartamento1 = new AdministradorDepartamento();
        administradorDepartamento1.setId(1L);
        AdministradorDepartamento administradorDepartamento2 = new AdministradorDepartamento();
        administradorDepartamento2.setId(administradorDepartamento1.getId());
        assertThat(administradorDepartamento1).isEqualTo(administradorDepartamento2);
        administradorDepartamento2.setId(2L);
        assertThat(administradorDepartamento1).isNotEqualTo(administradorDepartamento2);
        administradorDepartamento1.setId(null);
        assertThat(administradorDepartamento1).isNotEqualTo(administradorDepartamento2);
    }
}
