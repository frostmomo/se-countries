package com.test.country.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.country.dto.CountryDto;
import com.test.country.entities.CountryEntity;
import com.test.country.repositories.CountryRepository;

import com.test.country.repositories.specification.CountrySpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CountryService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CountryRepository countryRepository;

    // Convert CountryEntity to CountryDto
    private CountryDto convertToDto(CountryEntity countryEntity) {
        CountryDto countryDto = new CountryDto();
        countryDto.setId(countryEntity.getId());
        countryDto.setName(countryEntity.getName());
        countryDto.setCapital(countryEntity.getCapital());
        countryDto.setFlag(countryEntity.getFlag());
        countryDto.setCode(countryEntity.getCode());
        countryDto.setAltCode(countryEntity.getAltCode());
        countryDto.setCreatedAt(countryEntity.getCreatedAt());

        try {
            Map<String, String> namesMap = objectMapper.readValue(countryEntity.getNames(), new TypeReference<Map<String, String>>() {});
            countryDto.setNames(namesMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting JSON to Map", e);
        }

        return countryDto;
    }

    // Get all countries
    public List<CountryDto> findAll() {
        List<CountryEntity> countryEntities = countryRepository.findAll();
        return countryEntities.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    // Get paginated countries with filters
    public Page<CountryDto> getCountriesPage(PageRequest pageRequest, String name, String capital, String code) {
        Specification<CountryEntity> spec = Specification.where(null);

        if (!StringUtils.isEmpty(name)) {
            spec = spec.and(CountrySpecification.hasName(name));
        }
        if (!StringUtils.isEmpty(capital)) {
            spec = spec.and(CountrySpecification.hasCapital(capital));
        }
        if (!StringUtils.isEmpty(code)) {
            spec = spec.and(CountrySpecification.hasCode(code));
        }

        Page<CountryEntity> countryEntitiesPage = countryRepository.findAll(spec, pageRequest);
        List<CountryDto> countryDtoList = countryEntitiesPage.getContent()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return new PageImpl<>(countryDtoList, pageRequest, countryEntitiesPage.getTotalElements());
    }


    // Get countries based on country code
    public Optional<CountryDto> findByCode(String code) {
        Optional<CountryEntity> countryEntity = countryRepository.findByCode(code);
        return countryEntity.map(this::convertToDto);
    }

    // Entry new country
    public CountryDto createCountry(CountryDto countryDto) {
        // Check if the country code already exists
        if (countryRepository.existsByCode(countryDto.getCode())) {
            throw new IllegalArgumentException("Country code already exists: " + countryDto.getCode());
        }

        CountryEntity countryEntity = new CountryEntity();
        countryEntity.setName(countryDto.getName());
        countryEntity.setCapital(countryDto.getCapital());
        countryEntity.setFlag(countryDto.getFlag());
        countryEntity.setCode(countryDto.getCode());
        countryEntity.setAltCode(countryDto.getAltCode());

        CountryEntity savedCountry = countryRepository.save(countryEntity);
        return convertToDto(savedCountry);
    }

}
