package fi.uba.quechua.web.rest;

import com.codahale.metrics.annotation.Timed;
import fi.uba.quechua.domain.*;
import fi.uba.quechua.repository.AuthorityRepository;
import fi.uba.quechua.repository.CarreraRepository;
import fi.uba.quechua.repository.ProfesorRepository;
import fi.uba.quechua.security.AuthoritiesConstants;
import fi.uba.quechua.service.AlumnoCarreraService;
import fi.uba.quechua.service.AlumnoService;
import fi.uba.quechua.service.UserService;
import fi.uba.quechua.service.dto.CargaMasivaDTO;
import fi.uba.quechua.service.dto.UserDTO;
import liquibase.util.csv.opencsv.CSVReader;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;


@RestController
@RequestMapping("/api")
public class AdministradorResource {

    private final Logger log = LoggerFactory.getLogger(AdministradorResource.class);

    private final UserService userService;

    private final AuthorityRepository authorityRepository;

    private final AlumnoService alumnoService;

    private CarreraRepository carreraRepository;

    private AlumnoCarreraService alumnoCarreraService;

    private final ProfesorRepository profesorRepository;

    public AdministradorResource(UserService userService, AlumnoService alumnoService, ProfesorRepository profesorRepository,
                                 AuthorityRepository authorityRepository, AlumnoCarreraService alumnoCarreraService,
                                 CarreraRepository carreraRepository) {
        this.userService = userService;
        this.authorityRepository = authorityRepository;
        this.alumnoService = alumnoService;
        this.profesorRepository = profesorRepository;
        this.carreraRepository = carreraRepository;
        this.alumnoCarreraService = alumnoCarreraService;
    }

    /**
     * POST  /administradores/cargarAlumnos : Importar alumnos.
     *
     * @param file the file to process
     * @return the ResponseEntity with status 201 (Created) and with body the new alumnoCarrera, or with status 400 (Bad Request) if the alumnoCarrera has already an ID
     */
    @PostMapping("/administradores/cargarAlumnos")
    @Timed
    public CargaMasivaDTO cargarALumnos(@RequestParam("file") MultipartFile file) {
        log.debug("REST request to load : {}", file.getName());

        //Nombre,Apellido,E-mail,Padron,Prioridad,Carrera1, Carrera2
        CSVReader reader;
        CargaMasivaDTO cargaMasivaDTO = new CargaMasivaDTO();
        try {
            reader = new CSVReader(new InputStreamReader(file.getInputStream()));
            String[] line;
            Alumno alumno;
            UserDTO usuario;
            User user;
            String nombre;
            String apellido;
            String email;
            String padron;
            String prioridad;
            List<String> carreraIds;
            int lineNumber = 0;
            int procesadosCorrectmente = 0;

            while ((line = reader.readNext()) != null) {
                lineNumber = lineNumber + 1;
                log.debug("Procesando line {}, cantidad de campos {}", lineNumber, line.length);
                if (line.length != 6) {
                    cargaMasivaDTO.getErrorMessages().add(String.format("Línea %d tiene un número inválido de campos", lineNumber));
                    log.debug("Tiene errores");
                    continue;
                }
                nombre = line[0];
                apellido = line[1];
                email = line[2];
                padron = line[3];
                prioridad = line[4];
                carreraIds = Arrays.asList(line[5].split("\\s*,\\s*"));
                List<Carrera> carreras = new LinkedList<>();
                if (!this.isValidAlumnoRow(nombre, apellido, email, padron, prioridad, lineNumber, carreraIds, cargaMasivaDTO, carreras)) {
                    continue;
                }
                if (alumnoService.findOneByPadron(padron).isPresent()) {
                    cargaMasivaDTO.getErrorMessages().add(String.format("Línea %d el padrón %s ya existe", lineNumber, padron));
                    continue;
                }
                log.debug("Buscando usuario por mail {}", email);
                Optional<User> userOptional = userService.getUserWithAuthoritiesByLogin(email);
                if (userOptional.isPresent()) {
                    log.debug("Usuario existe {}", email);
                    if (userOptional.get().getAuthorities().contains(authorityRepository.findById(AuthoritiesConstants.ALUMNO).get())) {
                        cargaMasivaDTO.getWarningMessages().add(String.format("%s ya existía como alumno", email));
                    } else {
                        UserDTO dto = new UserDTO(userOptional.get());
                        dto.getAuthorities().add(AuthoritiesConstants.ALUMNO);
                        userService.updateUser(dto);
                        cargaMasivaDTO.getWarningMessages().add(String.format("%s ya existía y se le asignó el rol de alumno", email));
                        Optional<Alumno> alumnoOptional = alumnoService.findOneByUserId(userOptional.get().getId());
                        if (!alumnoOptional.isPresent()) {
                            log.debug("No existe el alumno");
                            alumno = new Alumno().nombre(nombre).apellido(apellido).padron(padron)
                                .prioridad(Integer.valueOf(prioridad)).userId(userOptional.get().getId());
                            alumnoService.save(alumno);
                            for (Carrera carrera: carreras) {
                                alumnoCarreraService.save(new AlumnoCarrera().carrera(carrera).alumno(alumno));
                            }
                        }
                        procesadosCorrectmente++;
                    }
                } else {
                    log.debug("Usuario no existe {}", email);
                    usuario = new UserDTO();
                    usuario.setEmail(email);
                    usuario.setFirstName(nombre);
                    usuario.setLastName(apellido);
                    usuario.setEmail(email);
                    usuario.setLogin(email);
                    usuario.setAuthorities(new HashSet<>(Arrays.asList(AuthoritiesConstants.ALUMNO)));
                    user = userService.registerActivatedUser(usuario, email.substring(0, email.indexOf("@")));
                    alumno = new Alumno().nombre(nombre).apellido(apellido).padron(padron)
                        .prioridad(Integer.valueOf(prioridad)).userId(user.getId());
                    alumno = alumnoService.save(alumno);
                    for (Carrera carrera: carreras) {
                        alumnoCarreraService.save(new AlumnoCarrera().carrera(carrera).alumno(alumno));
                    }
                    procesadosCorrectmente++;
                }
            }
            cargaMasivaDTO.setSuccessCount(procesadosCorrectmente);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.debug("Success",cargaMasivaDTO.getSuccessCount());
        log.debug("Warnings",cargaMasivaDTO.getWarningMessages().size());
        log.debug("Errores",cargaMasivaDTO.getErrorMessages().size());

        return cargaMasivaDTO;
    }


    /**
     * POST  /administradores/cargarProfesores : Importar alumnos.
     *
     * @param file the file to process
     * @return the ResponseEntity with status 201 (Created) and with body the new alumnoCarrera, or with status 400 (Bad Request) if the alumnoCarrera has already an ID
     */
    @PostMapping("/administradores/cargarProfesores")
    @Timed
    public CargaMasivaDTO cargaProfesores(@RequestParam("file") MultipartFile file) {
        log.debug("REST request to load : {}", file.getName());

        //Nombre,Apellido,E-mail
        CSVReader reader;
        CargaMasivaDTO cargaMasivaDTO = new CargaMasivaDTO();
        try {
            reader = new CSVReader(new InputStreamReader(file.getInputStream()));
            String[] line;
            Profesor profesor;
            UserDTO usuario;
            User user;
            String nombre;
            String apellido;
            String email;
            int lineNumber = 0;
            int procesadosCorrectmente = 0;

            while ((line = reader.readNext()) != null) {
                lineNumber = lineNumber + 1;
                log.debug("Procesando line {}, cantidad de campos {}", lineNumber, line.length);
                if (line.length != 3) {
                    cargaMasivaDTO.getErrorMessages().add(String.format("Línea %d tiene un número inválido de campos", lineNumber));
                    log.debug("Tiene errores");
                    continue;
                }
                nombre = line[0];
                apellido = line[1];
                email = line[2];
                if (!this.isValidProfesorRow(nombre, apellido, email, lineNumber, cargaMasivaDTO)) {
                    continue;
                }
                log.debug("Buscando usuario por mail {}", email);
                Optional<User> userOptional = userService.getUserWithAuthoritiesByLogin(email);
                if (userOptional.isPresent()) {
                    log.debug("Usuario existe {}", email);
                    if (userOptional.get().getAuthorities().contains(authorityRepository.findById(AuthoritiesConstants.PROFESOR).get())) {
                        cargaMasivaDTO.getWarningMessages().add(String.format("%s ya existía como Profesor", email));
                    } else {
                        UserDTO dto = new UserDTO(userOptional.get());
                        dto.getAuthorities().add(AuthoritiesConstants.PROFESOR);
                        userService.updateUser(dto);
                        cargaMasivaDTO.getWarningMessages().add(String.format("%s ya existía y se le asignó el rol de profesor", email));
                        Optional<Profesor> profesorOptional = profesorRepository.findByUserId(userOptional.get().getId());
                        if (!profesorOptional.isPresent()) {
                            log.debug("No existe el profesor");
                            profesor = new Profesor().nombre(nombre).apellido(apellido).userId(userOptional.get().getId());
                            profesorRepository.save(profesor);
                        }
                        procesadosCorrectmente++;
                    }
                } else {
                    log.debug("Usuario no existe {}", email);
                    usuario = new UserDTO();
                    usuario.setEmail(email);
                    usuario.setFirstName(nombre);
                    usuario.setLastName(apellido);
                    usuario.setEmail(email);
                    usuario.setLogin(email);
                    usuario.setAuthorities(new HashSet<>(Arrays.asList(AuthoritiesConstants.PROFESOR)));
                    user = userService.registerActivatedUser(usuario, email.substring(0, email.indexOf("@")));
                    profesor = new Profesor().nombre(nombre).apellido(apellido).userId(user.getId());
                    profesorRepository.save(profesor);
                    procesadosCorrectmente++;
                }
            }
            cargaMasivaDTO.setSuccessCount(procesadosCorrectmente);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.debug("Success",cargaMasivaDTO.getSuccessCount());
        log.debug("Warnings",cargaMasivaDTO.getWarningMessages().size());
        log.debug("Errores",cargaMasivaDTO.getErrorMessages().size());

        return cargaMasivaDTO;
    }

    private boolean isValidAlumnoRow(String nombre, String apellido, String email, String padron, String prioridad,
                    int lineNumber, List<String> carreraIds, CargaMasivaDTO cargaMasivaDTO, List<Carrera> carreras) {
        boolean hasErrors = false;
        if (!isValidEmail(email)) {
            cargaMasivaDTO.getErrorMessages().add(String.format("Línea %d el e-mail es inválido %s", lineNumber, email));
            hasErrors = true;
        }
        if (!StringUtils.isNumeric(padron)) {
            cargaMasivaDTO.getErrorMessages().add(String.format("Línea %d el padron no es numerico %s", lineNumber, padron));
            hasErrors = true;
        }
        if (!StringUtils.isNumeric(prioridad)) {
            cargaMasivaDTO.getErrorMessages().add(String.format("Línea %d la prioridad no es numerico %s", lineNumber, prioridad));
            hasErrors = true;
        }
        if (nombre == null || nombre.length() < 2) {
            cargaMasivaDTO.getErrorMessages().add(String.format("Línea %d el nombre no es válido %s", lineNumber, nombre));
            hasErrors = true;
        }
        if (apellido == null || apellido.length() < 2) {
            cargaMasivaDTO.getErrorMessages().add(String.format("Línea %d el apellido no es válido %s", lineNumber, apellido));
            hasErrors = true;
        }
        boolean tieneCarreraValida = false;
        log.debug("Cantidad de carreras {}", carreraIds.size());
        for (String carreraId: carreraIds) {
            log.debug("Procesando carrera {}", carreraId);
            Optional<Carrera> carrera = carreraRepository.findById(Long.valueOf(carreraId));
            if (carrera.isPresent()) {
                log.debug("La carrera existe {}", carreraId);
                tieneCarreraValida = true;
                carreras.add(carrera.get());
            } else {
                log.debug("La carrera no existe {}", carreraId);
            }
        }
        if (!tieneCarreraValida) {
            cargaMasivaDTO.getErrorMessages().add(String.format("Línea %d no tiene carreras válidas", lineNumber));
            hasErrors = true;
        }
        return !hasErrors;
    }

    private boolean isValidProfesorRow(String nombre, String apellido, String email, int lineNumber, CargaMasivaDTO cargaMasivaDTO) {
        boolean hasErrors = false;
        if (!isValidEmail(email)) {
            cargaMasivaDTO.getErrorMessages().add(String.format("Línea %d el e-mail es inválido %s", lineNumber, email));
            hasErrors = true;
        }
        if (nombre == null || nombre.length() < 2) {
            cargaMasivaDTO.getErrorMessages().add(String.format("Línea %d el nombre no es válido %s", lineNumber, nombre));
            hasErrors = true;
        }
        if (apellido == null || apellido.length() < 2) {
            cargaMasivaDTO.getErrorMessages().add(String.format("Línea %d el apellido no es válido %s", lineNumber, apellido));
            hasErrors = true;
        }
        return !hasErrors;
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
            "[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
            "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
}
