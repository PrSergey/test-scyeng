package com.example.skyengtest.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostOffice {

    private long id;

    private String index;

    private String name;

    private Address address;
}
