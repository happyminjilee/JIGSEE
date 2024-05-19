package com.sdi.work_order.repository;

import com.sdi.work_order.entity.WorkOrderRDBEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class WorkOrderCriteriaRepository {

    private final EntityManager em;

    public Page<WorkOrderRDBEntity> findByMembers(List<String> memberEmployeeNos, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<WorkOrderRDBEntity> criteriaQuery = criteriaBuilder.createQuery(WorkOrderRDBEntity.class);
        Root<WorkOrderRDBEntity> root = criteriaQuery.from(WorkOrderRDBEntity.class);

        List<Predicate> predicates = new ArrayList<>();
        for (String memberEmployeeNo : memberEmployeeNos) {
            predicates.add(criteriaBuilder.equal(root.get("creatorEmployeeNo"), memberEmployeeNo));
        }

        Predicate condition = criteriaBuilder.or(predicates.toArray(new Predicate[0]));

        criteriaQuery.where(condition);

        List<WorkOrderRDBEntity> result = em.createQuery(criteriaQuery)
                .setFirstResult(pageable.getPageNumber())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        countQuery.select(criteriaBuilder.count(countQuery.from(WorkOrderRDBEntity.class))).where(condition);
        Long totalCount = em.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(result, pageable, totalCount);
    }
}
