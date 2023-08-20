package com.example.skyengtest.controller;

import com.example.skyengtest.dto.AddressDto;
import com.example.skyengtest.dto.PostOfficeDto;
import com.example.skyengtest.service.PostOfficeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PostOfficeController.class)
class PostOfficeControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mvc;

    @MockBean
    PostOfficeService postOfficeService;

    private static final String PATH = "/office";


    @SneakyThrows
    @Test
    void save() {
        PostOfficeDto postOfficeDto = new PostOfficeDto();
        postOfficeDto.setAddress(new AddressDto());
        postOfficeDto.setIndex("xxxxxxx");
        postOfficeDto.setTitle("title");
        when(postOfficeService.savePostOffice(postOfficeDto))
                .thenReturn(postOfficeDto);

        String contentAsString = mvc.perform(post(PATH)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(postOfficeDto)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        verify(postOfficeService).savePostOffice(postOfficeDto);
        assertEquals(objectMapper.writeValueAsString(postOfficeDto), contentAsString);
    }

}