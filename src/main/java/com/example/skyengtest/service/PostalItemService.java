package com.example.skyengtest.service;

import com.example.skyengtest.dto.HistoryDto;
import com.example.skyengtest.dto.PostOfficeDto;
import com.example.skyengtest.dto.PostalItemRequestDto;
import com.example.skyengtest.dto.PostalItemResponseDto;

import java.util.List;

public interface PostalItemService {

    PostalItemResponseDto registrationPostalItem(PostalItemRequestDto postalItemDto);

    PostalItemResponseDto sendingParcel(Long itemId, PostOfficeDto postOfficeDto);

    PostalItemResponseDto acceptParcel(Long itemId, PostOfficeDto postOfficeDto);

    PostalItemResponseDto deliveredParcel(Long itemId, PostOfficeDto postOfficeDto);

    List<HistoryDto> getHistoryByParcel(Long itemId);


}
