package com.example.skyengtest.dto;

import com.example.skyengtest.constant.StatusDelivery;
import com.example.skyengtest.constant.TypeItem;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PostalItemRequestDto {

    @NotNull
    private TypeItem type;

    @NotBlank
    private long postOfficeId;

}
