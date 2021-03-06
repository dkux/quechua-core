package fi.uba.quechua.service;

import fi.uba.quechua.domain.Coloquio;
import fi.uba.quechua.domain.Curso;
import fi.uba.quechua.domain.Materia;
import fi.uba.quechua.domain.Periodo;
import fi.uba.quechua.domain.InscripcionColoquio;
import fi.uba.quechua.domain.enumeration.ColoquioEstado;
import fi.uba.quechua.domain.enumeration.InscripcionColoquioEstado;
import fi.uba.quechua.repository.ColoquioRepository;
import fi.uba.quechua.repository.InscripcionColoquioRepository;
import fi.uba.quechua.repository.PeriodoRepository;
import fi.uba.quechua.service.dto.ColoquioDTO;
import fi.uba.quechua.firebase.FirebaseConnectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
/**
 * Service Implementation for managing Coloquio.
 */
@Service
@Transactional
public class ColoquioService {

    private final Logger log = LoggerFactory.getLogger(ColoquioService.class);

    private final ColoquioRepository coloquioRepository;

    private final PeriodoRepository periodoRepository;

    private final InscripcionColoquioRepository inscripcionColoquioRepository;

    public ColoquioService(ColoquioRepository coloquioRepository, PeriodoRepository periodoRepository,
                           InscripcionColoquioRepository inscripcionColoquioRepository) {
        this.coloquioRepository = coloquioRepository;
        this.periodoRepository = periodoRepository;
        this.inscripcionColoquioRepository = inscripcionColoquioRepository;
    }

    /**
     * Save a coloquio.
     *
     * @param coloquio the entity to save
     * @return the persisted entity
     */
    public Coloquio save(Coloquio coloquio) {
        log.debug("Request to save Coloquio : {}", coloquio);
        return coloquioRepository.save(coloquio);
    }

    /**
     * Get all the coloquios.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Coloquio> findAll() {
        log.debug("Request to get all Coloquios");
        return coloquioRepository.findAll();
    }


    /**
     * Get one coloquio by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Coloquio> findOne(Long id) {
        log.debug("Request to get Coloquio : {}", id);
        return coloquioRepository.findById(id);
    }

    /**
     * Delete the coloquio by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Coloquio : {}", id);
        coloquioRepository.deleteById(id);
    }

    public List<Coloquio> findAllByCursoParaInscribirse(Curso curso) {
        log.debug("Request to get Coloquios by curso {} para inscribirse", curso.getId());

        LocalDate fecha = LocalDate.now().plusDays(2);
        return coloquioRepository.findAllByCursoAndFechaGreaterThanEqualAndEstadoOrderByFechaDesc(curso, fecha, ColoquioEstado.ACTIVO);
    }

    public List<ColoquioDTO> findAllColoquiosDTOByCurso(Curso curso) {
        log.debug("Request to get Coloquios by curso {}", curso.getId());

        List<Coloquio> coloquios = coloquioRepository.findAllByCursoAndEstadoOrderByFechaDesc(curso, ColoquioEstado.ACTIVO);
        List<ColoquioDTO> coloquiosDTO = new LinkedList<>();
        for (Coloquio coloquio: coloquios) {
            Integer inscripciones = inscripcionColoquioRepository.findAllByColoquioAndEstado(coloquio, InscripcionColoquioEstado.ACTIVA).size();
            coloquiosDTO.add(new ColoquioDTO(coloquio, inscripciones));
        }
        return coloquiosDTO;
    }

    public List<ColoquioDTO> findAllColoquiosDTO() {
        log.debug("Request to get Coloquios  {}");
        Optional<Periodo> periodo = periodoRepository.findPeriodoActual();
        if (!periodo.isPresent()) {
            return new LinkedList<>();
        }
        LocalDate ayer = LocalDate.now().minusDays(1);
        List<Coloquio> coloquios = coloquioRepository.findAll();
        List<ColoquioDTO> coloquiosDTO = new LinkedList<>();
        for (Coloquio coloquio: coloquios) {
            Integer inscripciones = inscripcionColoquioRepository.findAllByColoquioAndEstado(coloquio, InscripcionColoquioEstado.ACTIVA).size();
            if((coloquio.getEstado() ==  ColoquioEstado.ACTIVO) && (coloquio.getFecha().isAfter(ayer) )) {
                coloquiosDTO.add(new ColoquioDTO(coloquio, inscripciones));
            }
        }
        return coloquiosDTO;
    }

    public void eliminar(Coloquio coloquio) {
        coloquio.setEstado(ColoquioEstado.ELIMINADO);
        coloquioRepository.save(coloquio);

        // Notificar a los alumnos inscriptos
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        Materia materia = coloquio.getCurso().getMateria();
        String message = "La fecha de final " + coloquio.getFecha().format(formatter) + " de la materia "
                        + materia.getCodigo() + " - " + materia.getNombre()
                        + " ha sido eliminada por el docente";

        FirebaseConnectionService fcmService = new FirebaseConnectionService();
        List<InscripcionColoquio> inscripciones = inscripcionColoquioRepository.findAllByColoquioAndEstado(coloquio, InscripcionColoquioEstado.ACTIVA);
        for (InscripcionColoquio inscripcion: inscripciones) {
            String token = inscripcion.getAlumno().getFirebaseToken();
            String notification = fcmService.buildNotificationMessage("Final Eliminado", message, token);
            try {
                fcmService.sendMessage(notification);
            } catch (Exception e) {
                log.error("FirebaseConnectionService: " + e);
            }

            inscripcion.setEstado(InscripcionColoquioEstado.ELIMINADA);
            inscripcionColoquioRepository.save(inscripcion);

        }
    }
}
