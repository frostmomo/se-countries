package com.test.country.repositories;

import com.test.country.entities.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CountryRepository extends JpaRepository<CountryEntity, Integer>, JpaSpecificationExecutor<CountryEntity> {
    // Repo to query code
    Optional<CountryEntity> findByCode(String code);
    //Repo to check existing country code
    boolean existsByCode(String code);

}
