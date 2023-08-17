package com.example.skyengtest.dto;

import com.example.skyengtest.model.Address;
import lombok.Data;

@Data
public class PostOfficeDto {

    private long id;


    private String index;

    private String name;

    private Address address;

}
