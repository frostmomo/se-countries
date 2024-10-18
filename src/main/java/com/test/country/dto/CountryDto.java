package com.test.country.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.sql.Timestamp;
import java.util.Map;

public class CountryDto {
    private Integer id;

    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "Name must not exceed 255 characters")
    private String name;

    private Map<String, String> names;

    @NotBlank(message = "Capital is required")
    @Size(max = 255, message = "Capital must not exceed 255 characters")
    private String capital;

    @NotBlank(message = "Flag is required")
    @Size(max = 255, message = "Flag must not exceed 255 characters")
    private String flag;

    @NotBlank(message = "Code is required")
    @Size(max = 4, message = "Code must not exceed 4 characters")
    @Pattern(regexp = "[A-Z]{2,4}", message = "Code must be between 2 and 4 uppercase letters")
    private String code;

    @Size(max = 4, message = "AltCode must not exceed 4 characters")
    @Pattern(regexp = "[A-Z]{2,4}", message = "AltCode must be between 2 and 4 uppercase letters")
    private String altCode;

    private Timestamp createdAt;

    // Getters and setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getNames() {
        return names;
    }

    public void setNames(Map<String, String> names) {
        this.names = names;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAltCode() {
        return altCode;
    }

    public void setAltCode(String altCode) {
        this.altCode = altCode;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
