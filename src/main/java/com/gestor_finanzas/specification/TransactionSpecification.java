package com.gestor_finanzas.specification;

import com.gestor_finanzas.dto.TransactionFilter;
import com.gestor_finanzas.model.Transaction;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;


public class TransactionSpecification {

    public static Specification<Transaction> getSpecifications(TransactionFilter filter) {

        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (filter.getType() != null) {
                predicates.add(criteriaBuilder.equal(root.get("type"), filter.getType()));
            }

            if (filter.getCategoryId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("category").get("id"), filter.getCategoryId()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

        };


    }


}

