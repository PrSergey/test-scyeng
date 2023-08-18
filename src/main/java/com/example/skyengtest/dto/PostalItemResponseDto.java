package com.example.skyengtest.dto;

import com.example.skyengtest.constant.ItemStatus;
import com.example.skyengtest.constant.TypeItem;
import com.example.skyengtest.model.PostOffice;
import lombok.Data;

@Data
public class PostalItemResponseDto {

    private long id;

    private TypeItem type;

    private PostOffice postoffice;

    private ItemStatus itemStatus;


}
