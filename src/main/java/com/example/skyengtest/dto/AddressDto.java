package com.example.skyengtest.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddressDto {

    @NotBlank
    private String country;
    @NotBlank
    private String city;
    @NotBlank
    private String houseNumber;

}
