package com.example.skyengtest.dto;

import com.example.skyengtest.constant.ItemType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostalItemRequestDto {

    @NotNull
    private ItemType type;

    @NotNull
    private Long postOfficeId;

    @NotBlank
    private String recipientName;

}
