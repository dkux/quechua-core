package fi.uba.quechua.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import fi.uba.quechua.domain.Prioridad;
import fi.uba.quechua.domain.*; // for static metamodels
import fi.uba.quechua.repository.PrioridadRepository;
import fi.uba.quechua.service.dto.PrioridadCriteria;


/**
 * Service for executing complex queries for Prioridad entities in the database.
 * The main input is a {@link PrioridadCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Prioridad} or a {@link Page} of {@link Prioridad} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PrioridadQueryService extends QueryService<Prioridad> {

    private final Logger log = LoggerFactory.getLogger(PrioridadQueryService.class);

    private final PrioridadRepository prioridadRepository;

    public PrioridadQueryService(PrioridadRepository prioridadRepository) {
        this.prioridadRepository = prioridadRepository;
    }

    /**
     * Return a {@link List} of {@link Prioridad} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Prioridad> findByCriteria(PrioridadCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Prioridad> specification = createSpecification(criteria);
        return prioridadRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Prioridad} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Prioridad> findByCriteria(PrioridadCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Prioridad> specification = createSpecification(criteria);
        return prioridadRepository.findAll(specification, page);
    }

    /**
     * Function to convert PrioridadCriteria to a {@link Specification}
     */
    private Specification<Prioridad> createSpecification(PrioridadCriteria criteria) {
        Specification<Prioridad> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Prioridad_.id));
            }
            if (criteria.getFecha_habilitacion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFecha_habilitacion(), Prioridad_.fecha_habilitacion));
            }
            if (criteria.getPeriodoId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getPeriodoId(), Prioridad_.periodo, Periodo_.id));
            }
        }
        return specification;
    }

}
