package com.example.skyengtest.dto;

import com.example.skyengtest.model.Address;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PostOfficeDto {

    private long id;
    @NotBlank
    private String index;
    @NotBlank
    private String title;
    @NotNull
    private AddressDto address;

}
