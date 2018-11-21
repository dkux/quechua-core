package fi.uba.quechua.service;

import fi.uba.quechua.domain.*;
import fi.uba.quechua.repository.AdministradorDepartamentoRepository;
import fi.uba.quechua.repository.DepartamentoRepository;
import fi.uba.quechua.repository.MateriaRepository;
import fi.uba.quechua.security.AuthoritiesConstants;
import fi.uba.quechua.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
/**
 * Service Implementation for managing Materia.
 */
@Service
@Transactional
public class MateriaService {

    private final Logger log = LoggerFactory.getLogger(MateriaService.class);

    private final MateriaRepository materiaRepository;

    private final UserService userService;

    private final AdministradorDepartamentoRepository administradorDepartamentoRepository;

    private final DepartamentoRepository departamentoRepository;

    public MateriaService(MateriaRepository materiaRepository, UserService userService,
                          AdministradorDepartamentoRepository administradorDepartamentoRepository,
                          DepartamentoRepository departamentoRepository) {
        this.materiaRepository = materiaRepository;
        this.userService = userService;
        this.administradorDepartamentoRepository = administradorDepartamentoRepository;
        this.departamentoRepository = departamentoRepository;
    }

    /**
     * Save a materia.
     *
     * @param materia the entity to save
     * @return the persisted entity
     */
    public Materia save(Materia materia) {
        log.debug("Request to save Materia : {}", materia);        return materiaRepository.save(materia);
    }

    /**
     * Get all the materias.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Materia> findAll() {
        log.debug("Request to get all Materias");
        Optional<User> user = userService.getUserWithAuthorities();
        if (user.isPresent() && SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADM_DPTO)) {
            Optional<AdministradorDepartamento> administradorDepartamento = administradorDepartamentoRepository.findByUserId(user.get().getId());
            Optional<Departamento> departamento = departamentoRepository.findById(administradorDepartamento.get().getDepartamentoId());
            return materiaRepository.findAllByDepartamentoOrderByCodigoAsc(departamento.get());
        }
        return materiaRepository.findAll(new Sort(Sort.Direction.ASC, "codigo"));
    }


    /**
     * Get one materia by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Materia> findOne(Long id) {
        log.debug("Request to get Materia : {}", id);
        return materiaRepository.findById(id);
    }

    /**
     * Delete the materia by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Materia : {}", id);
        materiaRepository.deleteById(id);
    }

    public List<Materia> findByFilter(Carrera carrera, String query) {
        return materiaRepository.findAllByCarreraAndNombreStartingWith(carrera, query);
    }
}
