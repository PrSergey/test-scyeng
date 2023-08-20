package com.example.skyengtest.dto;

import com.example.skyengtest.constant.ItemStatus;
import com.example.skyengtest.constant.ItemType;
import lombok.Data;

@Data
public class PostalItemResponseDto {

    private long id;

    private ItemType type;

    private PostOfficeDto postoffice;

    private ItemStatus itemStatus;

    private String recipientName;


}
