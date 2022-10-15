package com.peertutor.TutorMgr.service;

import com.peertutor.TutorMgr.model.Tutor;
import com.peertutor.TutorMgr.model.Tutor_;
import com.peertutor.TutorMgr.repository.TutorRepository;
import com.peertutor.TutorMgr.service.dto.TutorCriteria;
import com.peertutor.TutorMgr.service.dto.TutorDTO;
import com.peertutor.TutorMgr.service.mapper.TutorMapper;
import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class TutorQueryService extends QueryService<Tutor> {

    private final Logger log = LoggerFactory.getLogger(TutorQueryService.class);

    private final TutorRepository tuitionOrderRepository;

    private final TutorMapper tuitionOrderMapper;

    public TutorQueryService(TutorRepository tuitionOrderRepository, TutorMapper tuitionOrderMapper) {
        this.tuitionOrderRepository = tuitionOrderRepository;
        this.tuitionOrderMapper = tuitionOrderMapper;
    }

    /**
     * Return a {@link List} of {@link TutorDTO} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TutorDTO> findByCriteria(TutorCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Tutor> specification = createSpecification(criteria);
        return tuitionOrderMapper.toDto(tuitionOrderRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TutorDTO} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page     The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TutorDTO> findByCriteria(TutorCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Tutor> specification = createSpecification(criteria);
        return tuitionOrderRepository.findAll(specification, page)
                .map(tuitionOrderMapper::toDto);
    }

    /**
     * Function to convert {@link TutorCriteria} to a {@link Specification}
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Tutor> createSpecification(TutorCriteria criteria) {
        Specification<Tutor> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getAccountName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccountName(), Tutor_.accountName));
            }
            if (criteria.getDisplayName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDisplayName(), Tutor_.displayName));
            }
            if (criteria.getSubjects() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSubjects(), Tutor_.subjects));
            }
            if (criteria.getCertificates() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCertificates(), Tutor_.certificates));
            }

        }
        return specification;
    }
}
