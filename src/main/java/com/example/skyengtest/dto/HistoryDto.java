package com.example.skyengtest.dto;

import com.example.skyengtest.constant.StatusDelivery;
import lombok.Data;

@Data
public class HistoryDto {

    private PostOfficeDto office;

    private PostalItemRequestDto item;

    private StatusDelivery status;

}
