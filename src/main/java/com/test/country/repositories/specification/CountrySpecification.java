package com.test.country.repositories.specification;

import com.test.country.entities.CountryEntity;
import org.springframework.data.jpa.domain.Specification;

public class CountrySpecification {

    public static Specification<CountryEntity> hasName(String name) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<CountryEntity> hasCapital(String capital) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("capital"), "%" + capital + "%");
    }

    public static Specification<CountryEntity> hasCode(String code) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("code"), code);
    }
}