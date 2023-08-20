package com.example.skyengtest.controller;

import com.example.skyengtest.constant.ItemType;
import com.example.skyengtest.dto.HistoryDto;
import com.example.skyengtest.dto.PostalItemRequestDto;
import com.example.skyengtest.dto.PostalItemResponseDto;
import com.example.skyengtest.service.PostalItemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PostalItemController.class)
class PostalItemControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mvc;


    @MockBean
    PostalItemService postalItemService;

    private static final String PATH = "/item";

    @Test
    @SneakyThrows
    void registrationPostalItem() {
        PostalItemRequestDto itemRequestDto = new PostalItemRequestDto(ItemType.LETTER, 1L, "Name");
        PostalItemResponseDto itemResponseDto = new PostalItemResponseDto();
        when(postalItemService.registrationPostalItem(itemRequestDto))
                .thenReturn(itemResponseDto);

        String contentAsString = mvc.perform(post(PATH + "/registration")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(itemRequestDto)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        verify(postalItemService).registrationPostalItem(itemRequestDto);
        assertEquals(objectMapper.writeValueAsString(itemResponseDto), contentAsString);
    }

    @Test
    @SneakyThrows
    void sendingParcel() {
        PostalItemResponseDto itemResponseDto = new PostalItemResponseDto();
        Long itemId = 1L;
        Long postOfficeId = 1L;
        when(postalItemService.sendingParcel(itemId, postOfficeId))
                .thenReturn(itemResponseDto);

        String contentAsString = mvc.perform(put(PATH + "/sending")
                        .param("itemId", itemId.toString())
                        .param("postOfficeId", postOfficeId.toString())
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        verify(postalItemService).sendingParcel(itemId, postOfficeId);
        assertEquals(objectMapper.writeValueAsString(itemResponseDto), contentAsString);
    }

    @Test
    @SneakyThrows
    void acceptParcel() {
        PostalItemResponseDto itemResponseDto = new PostalItemResponseDto();
        Long itemId = 1L;
        Long postOfficeId = 1L;
        when(postalItemService.acceptParcel(itemId, postOfficeId))
                .thenReturn(itemResponseDto);

        String contentAsString = mvc.perform(put(PATH + "/accept")
                        .param("itemId", itemId.toString())
                        .param("postOfficeId", postOfficeId.toString())
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        verify(postalItemService).acceptParcel(itemId, postOfficeId);
        assertEquals(objectMapper.writeValueAsString(itemResponseDto), contentAsString);
    }


    @Test
    @SneakyThrows
    void deliveredParcel() {
        PostalItemResponseDto itemResponseDto = new PostalItemResponseDto();
        Long itemId = 1L;
        Long postOfficeId = 1L;
        when(postalItemService.deliveredParcel(itemId, postOfficeId))
                .thenReturn(itemResponseDto);

        String contentAsString = mvc.perform(put(PATH + "/delivery")
                        .param("itemId", itemId.toString())
                        .param("postOfficeId", postOfficeId.toString())
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        verify(postalItemService).deliveredParcel(itemId, postOfficeId);
        assertEquals(objectMapper.writeValueAsString(itemResponseDto), contentAsString);
    }

    @Test
    @SneakyThrows
    void getHistoryByParcel() {
        List<HistoryDto> historyList = new ArrayList<>();
        Long itemId = 1L;
        when(postalItemService.getHistoryByParcel(itemId))
                .thenReturn(historyList);

        String contentAsString = mvc.perform(get(PATH + "/history")
                        .param("itemId", itemId.toString())
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        verify(postalItemService).getHistoryByParcel(itemId);
        assertEquals(objectMapper.writeValueAsString(historyList), contentAsString);
    }
}