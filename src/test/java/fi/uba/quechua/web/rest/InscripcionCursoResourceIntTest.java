package fi.uba.quechua.web.rest;

import fi.uba.quechua.QuechuaApp;

import fi.uba.quechua.domain.InscripcionCurso;
import fi.uba.quechua.repository.InscripcionCursoRepository;
import fi.uba.quechua.service.AlumnoService;
import fi.uba.quechua.service.CursoService;
import fi.uba.quechua.service.InscripcionCursoService;
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

import fi.uba.quechua.domain.enumeration.InscripcionCursoEstado;
import fi.uba.quechua.domain.enumeration.CursadaEstado;
/**
 * Test class for the InscripcionCursoResource REST controller.
 *
 * @see InscripcionCursoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = QuechuaApp.class)
public class InscripcionCursoResourceIntTest {

    private static final InscripcionCursoEstado DEFAULT_ESTADO = InscripcionCursoEstado.REGULAR;
    private static final InscripcionCursoEstado UPDATED_ESTADO = InscripcionCursoEstado.CONDICIONAL;

    private static final CursadaEstado DEFAULT_CURSADA_ESTADO = CursadaEstado.APROBADA;
    private static final CursadaEstado UPDATED_CURSADA_ESTADO = CursadaEstado.DESAPROBADA;

    @Autowired
    private InscripcionCursoRepository inscripcionCursoRepository;



    @Autowired
    private InscripcionCursoService inscripcionCursoService;

    @Autowired
    private AlumnoService alumnoService;

    @Autowired
    private CursoService cursoService;

    @Autowired
    private UserService userService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restInscripcionCursoMockMvc;

    private InscripcionCurso inscripcionCurso;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InscripcionCursoResource inscripcionCursoResource = new InscripcionCursoResource(inscripcionCursoService, alumnoService, cursoService, userService);
        this.restInscripcionCursoMockMvc = MockMvcBuilders.standaloneSetup(inscripcionCursoResource)
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
    public static InscripcionCurso createEntity(EntityManager em) {
        InscripcionCurso inscripcionCurso = new InscripcionCurso()
            .estado(DEFAULT_ESTADO)
            .cursadaEstado(DEFAULT_CURSADA_ESTADO);
        return inscripcionCurso;
    }

    @Before
    public void initTest() {
        inscripcionCurso = createEntity(em);
    }

    @Test
    @Transactional
    public void createInscripcionCurso() throws Exception {
        int databaseSizeBeforeCreate = inscripcionCursoRepository.findAll().size();

        // Create the InscripcionCurso
        restInscripcionCursoMockMvc.perform(post("/api/inscripcion-cursos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inscripcionCurso)))
            .andExpect(status().isCreated());

        // Validate the InscripcionCurso in the database
        List<InscripcionCurso> inscripcionCursoList = inscripcionCursoRepository.findAll();
        assertThat(inscripcionCursoList).hasSize(databaseSizeBeforeCreate + 1);
        InscripcionCurso testInscripcionCurso = inscripcionCursoList.get(inscripcionCursoList.size() - 1);
        assertThat(testInscripcionCurso.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testInscripcionCurso.getCursadaEstado()).isEqualTo(DEFAULT_CURSADA_ESTADO);
    }

    @Test
    @Transactional
    public void createInscripcionCursoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = inscripcionCursoRepository.findAll().size();

        // Create the InscripcionCurso with an existing ID
        inscripcionCurso.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInscripcionCursoMockMvc.perform(post("/api/inscripcion-cursos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inscripcionCurso)))
            .andExpect(status().isBadRequest());

        // Validate the InscripcionCurso in the database
        List<InscripcionCurso> inscripcionCursoList = inscripcionCursoRepository.findAll();
        assertThat(inscripcionCursoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllInscripcionCursos() throws Exception {
        // Initialize the database
        inscripcionCursoRepository.saveAndFlush(inscripcionCurso);

        // Get all the inscripcionCursoList
        restInscripcionCursoMockMvc.perform(get("/api/inscripcion-cursos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inscripcionCurso.getId().intValue())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())))
            .andExpect(jsonPath("$.[*].cursadaEstado").value(hasItem(DEFAULT_CURSADA_ESTADO.toString())));
    }


    @Test
    @Transactional
    public void getInscripcionCurso() throws Exception {
        // Initialize the database
        inscripcionCursoRepository.saveAndFlush(inscripcionCurso);

        // Get the inscripcionCurso
        restInscripcionCursoMockMvc.perform(get("/api/inscripcion-cursos/{id}", inscripcionCurso.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(inscripcionCurso.getId().intValue()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()))
            .andExpect(jsonPath("$.cursadaEstado").value(DEFAULT_CURSADA_ESTADO.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingInscripcionCurso() throws Exception {
        // Get the inscripcionCurso
        restInscripcionCursoMockMvc.perform(get("/api/inscripcion-cursos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInscripcionCurso() throws Exception {
        // Initialize the database
        inscripcionCursoService.save(inscripcionCurso);

        int databaseSizeBeforeUpdate = inscripcionCursoRepository.findAll().size();

        // Update the inscripcionCurso
        InscripcionCurso updatedInscripcionCurso = inscripcionCursoRepository.findById(inscripcionCurso.getId()).get();
        // Disconnect from session so that the updates on updatedInscripcionCurso are not directly saved in db
        em.detach(updatedInscripcionCurso);
        updatedInscripcionCurso
            .estado(UPDATED_ESTADO)
            .cursadaEstado(UPDATED_CURSADA_ESTADO);

        restInscripcionCursoMockMvc.perform(put("/api/inscripcion-cursos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInscripcionCurso)))
            .andExpect(status().isOk());

        // Validate the InscripcionCurso in the database
        List<InscripcionCurso> inscripcionCursoList = inscripcionCursoRepository.findAll();
        assertThat(inscripcionCursoList).hasSize(databaseSizeBeforeUpdate);
        InscripcionCurso testInscripcionCurso = inscripcionCursoList.get(inscripcionCursoList.size() - 1);
        assertThat(testInscripcionCurso.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testInscripcionCurso.getCursadaEstado()).isEqualTo(UPDATED_CURSADA_ESTADO);
    }

    @Test
    @Transactional
    public void updateNonExistingInscripcionCurso() throws Exception {
        int databaseSizeBeforeUpdate = inscripcionCursoRepository.findAll().size();

        // Create the InscripcionCurso

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restInscripcionCursoMockMvc.perform(put("/api/inscripcion-cursos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inscripcionCurso)))
            .andExpect(status().isBadRequest());

        // Validate the InscripcionCurso in the database
        List<InscripcionCurso> inscripcionCursoList = inscripcionCursoRepository.findAll();
        assertThat(inscripcionCursoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInscripcionCurso() throws Exception {
        // Initialize the database
        inscripcionCursoService.save(inscripcionCurso);

        int databaseSizeBeforeDelete = inscripcionCursoRepository.findAll().size();

        // Get the inscripcionCurso
        restInscripcionCursoMockMvc.perform(delete("/api/inscripcion-cursos/{id}", inscripcionCurso.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<InscripcionCurso> inscripcionCursoList = inscripcionCursoRepository.findAll();
        assertThat(inscripcionCursoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InscripcionCurso.class);
        InscripcionCurso inscripcionCurso1 = new InscripcionCurso();
        inscripcionCurso1.setId(1L);
        InscripcionCurso inscripcionCurso2 = new InscripcionCurso();
        inscripcionCurso2.setId(inscripcionCurso1.getId());
        assertThat(inscripcionCurso1).isEqualTo(inscripcionCurso2);
        inscripcionCurso2.setId(2L);
        assertThat(inscripcionCurso1).isNotEqualTo(inscripcionCurso2);
        inscripcionCurso1.setId(null);
        assertThat(inscripcionCurso1).isNotEqualTo(inscripcionCurso2);
    }
}
