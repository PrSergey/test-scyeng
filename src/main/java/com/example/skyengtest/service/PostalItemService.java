package com.example.skyengtest.service;

import com.example.skyengtest.dto.HistoryDto;
import com.example.skyengtest.dto.PostOfficeDto;
import com.example.skyengtest.dto.PostalItemRequestDto;
import com.example.skyengtest.dto.PostalItemResponseDto;

import java.util.List;

public interface PostalItemService {

    PostalItemResponseDto registrationPostalItem(PostalItemRequestDto postalItemDto);

    PostalItemResponseDto sendingParcel(Long itemId, Long postOfficeId);

    PostalItemResponseDto acceptParcel(Long itemId, Long postOfficeId);

    PostalItemResponseDto deliveredParcel(Long itemId, Long postOfficeId);

    List<HistoryDto> getHistoryByParcel(Long itemId);


}
