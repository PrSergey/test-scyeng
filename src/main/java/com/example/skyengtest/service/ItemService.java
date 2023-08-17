package com.example.skyengtest.service;

import com.example.skyengtest.dto.HistoryDto;
import com.example.skyengtest.dto.PostalItemRequestDto;
import com.example.skyengtest.dto.PostalItemResponseDto;
import com.example.skyengtest.model.PostOffice;

import java.util.List;

public interface ItemService {

    PostalItemResponseDto registrationPostalItem(PostalItemRequestDto postalItemDto);

    PostalItemResponseDto sendingParcel(Long itemId, PostOffice postOffice);

    PostalItemResponseDto acceptParcel(Long itemId, PostOffice postOffice);

    PostalItemResponseDto deliveredParcel(Long itemId, PostOffice postOffice);

    List<HistoryDto> getHistoryByParcel(Long itemId);


}
