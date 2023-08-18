package com.example.skyengtest.dto;

import com.example.skyengtest.constant.DeliveryStatus;
import lombok.Data;

@Data
public class HistoryDto {

    private PostOfficeDto office;

    private PostalItemRequestDto item;

    private DeliveryStatus status;

}
