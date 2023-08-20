package com.example.skyengtest.conf;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;

public class TestConf {
    @Bean
    public ModelMapper getMapper() {
        return new ModelMapper();
    }
}
