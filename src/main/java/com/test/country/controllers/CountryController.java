package com.test.country.controllers;

import com.test.country.dto.CountryDto;
import com.test.country.dto.ResponseDto;
import com.test.country.entities.CountryEntity;
import com.test.country.services.CountryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/country")
public class CountryController {

    private final CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/all")
    public ResponseDto<List<CountryDto>> getAllCountries() {
        List<CountryDto> countries = countryService.findAll();
        return new ResponseDto<>("Success", countries, 200); // Return a successful response with the list
    }

    // Get paginated countries
    @GetMapping("/paginated")
    public ResponseDto<Page<CountryDto>> getCountries(
            @RequestParam(value = "offset", required = false) Integer offset,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "capital", required = false) String capital,
            @RequestParam(value = "code", required = false) String code) {

        if (offset == null) offset = 0;  // Default offset
        if (pageSize == null) pageSize = 10;  // Default page size
        if (StringUtils.isEmpty(sortBy)) sortBy = "id";  // Default sort by

        Page<CountryDto> countryPage = countryService.getCountriesPage(PageRequest.of(offset, pageSize, Sort.by(sortBy)), name, capital, code);
        return new ResponseDto<>("Success", countryPage, 200);
    }

    // Get country based on code
    @GetMapping("/{code}")
    public ResponseDto<CountryDto> getCountryByCode(@PathVariable String code) {
        Optional<CountryDto> countryDto = countryService.findByCode(code);

        if (countryDto.isPresent()) {
            return new ResponseDto<>("Success", countryDto.get(), 200);
        } else {
            return new ResponseDto<>("No country found with code: " + code, null, 404);
        }
    }

    @PostMapping("/")
    public ResponseDto<CountryDto> createCountry(@Valid @RequestBody CountryDto countryDto) {
        // Validate names before proceeding
        try {
            CountryDto createdCountry = countryService.createCountry(countryDto);
            return new ResponseDto<>("Country created successfully", createdCountry, HttpStatus.CREATED.value());
        } catch (IllegalArgumentException e) {
            return new ResponseDto<>(e.getMessage(), null, HttpStatus.BAD_REQUEST.value());
        }
    }
}